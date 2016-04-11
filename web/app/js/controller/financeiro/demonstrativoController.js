'use strict';

app.controller('DemonstrativoController', ['$scope', '$q', '$filter', 'PlanoContaService', 'LancamentoService', 'ModalService', 'LISTAS',
    function ($scope, $q, $filter,  PlanoContaService, LancamentoService, ModalService, LISTAS) {
            
        var init = function () {
            $scope.estruturasLista = [];
            $scope.meses = LISTAS.meses;
            anos();
        }; 
        
        $scope.changeAno = function(ano) {
            estruturas(ano.codigo);
        }

        var anos = function() {
            LancamentoService.findYearFromLancamento()
                .then(function (data) {
                    $scope.anos = createAnosLista(data);
                    $scope.anoSelected = $scope.anos[$scope.anos.length-1];
                    estruturas($scope.anoSelected.codigo);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };   
        
        var createAnosLista = function(anos) {            
            return _.map(anos, function(ano, index) {
                return {'id': index, 'codigo': ano, 'descricao': ano}
            })
        }
        
        var estruturas = function(ano) {
            $q.all([PlanoContaService.getStructure(), PlanoContaService.getSaldo(ano)])
               .then(function(values) {  
                    $scope.estruturas = values[0];
                    $scope.saldos = values[1];
                    $scope.estruturasLista = PlanoContaService.flatten($scope.estruturas);
                    $scope.totais = {};
                    setSaldos($scope.estruturasLista, $scope.saldos);
                    setSaldoTotal($scope.estruturasLista);
                    setSaldoGrupo($scope.estruturasLista);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var setSaldoGrupo = function(estruturas) {
            estruturas = $filter('orderBy')(estruturas, 'nivel', true);
            angular.forEach(estruturas, function(estrutura) {
                getSaldoGrupo(estruturas, estrutura);   
            });
        }
        
        var getSaldoGrupo = function(estruturas, estruturaGrupo) {
            var estruturaSaldo = _.find(estruturas, function(estrutura) { 
                return estrutura.idPlanoConta === estruturaGrupo.idGrupo;
            });             
            if(estruturaSaldo) { sumSaldo(estruturaSaldo, estruturaGrupo.saldos); }
        } 
        
        var sumSaldo = function(estrutura, saldos) {
            angular.forEach(saldos, function(saldo, index) {
                estrutura.saldos[index] += saldo;
            })
        }
        
        var setSaldos = function(estruturas, saldos) {
            angular.forEach(estruturas, function(estrutura) {
                estrutura.saldos = {};
                getMesSaldo(estrutura, saldos);
            });
        }
        
        var getMesSaldo = function(estrutura, saldos) {
            angular.forEach(LISTAS.meses, function(mes) {
                estrutura.saldos[mes.id] = 0;
                getSaldo(estrutura, mes, saldos);
            });
        }
        
        var getSaldo = function(estrutura, mes, saldos) {
            angular.forEach(saldos, function(saldo) {
                if(estrutura.idPlanoConta === saldo.planoConta.idPlanoConta && mes.id === saldo.mes - 1) {
                    estrutura.saldos[mes.id] = saldo.valor;
                }
            });
        }
        
        var setSaldoTotal = function(estruturas) {
            angular.forEach(estruturas, function(estrutura) {
                getSaldoEstrutura(estrutura);
            });
        }
        
        var getSaldoEstrutura = function(estrutura) {
            angular.forEach(LISTAS.meses, function(mes) {               
                if(!$scope.totais[mes.id]) { $scope.totais[mes.id] = 0; }
                if(estrutura.tipo.codigo === 'receita') { $scope.totais[mes.id] += estrutura.saldos[mes.id]; }
                else if(estrutura.tipo.codigo === 'despesa') { $scope.totais[mes.id] -= estrutura.saldos[mes.id]; }
            });
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        init();

    }]);

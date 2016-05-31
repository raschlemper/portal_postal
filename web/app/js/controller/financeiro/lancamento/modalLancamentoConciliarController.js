'use strict';

app.controller('ModalLancamentoConciliarController', 
    ['$scope', '$modalInstance', 'conta', 'PlanoContaService', 'CentroCustoService', 'ContaService', 'ModalService', 'DatePickerService', 'ListaService', 'LISTAS', "MESSAGES",
    function ($scope, $modalInstance, conta, PlanoContaService, CentroCustoService, ContaService, ModalService, DatePickerService, ListaService, LISTAS, MESSAGES) {

        var init = function () {  
            $scope.conta = conta;
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.tipos = LISTAS.lancamento;
            $scope.lancamentoConciliado = {}; 
        };
        
        $scope.calcularSaldo = function(conta, dataLancamento, saldoReconciliacao) {
            dataLancamento = moment(dataLancamento).format('YYYY-MM-DD HH:mm:ss');
            ContaService.getSaldoLancamento(conta.idConta, dataLancamento)
                .then(function (data) {
                    if(!data) return;
                    $scope.saldo = calcularSaldo(data.saldo, saldoReconciliacao);
                    if($scope.saldo.diferenca > 0) { $scope.lancamentoConciliado.tipo = $scope.tipos[0]; } 
                    else { $scope.lancamentoConciliado.tipo = $scope.tipos[1]; }
                    planoContas($scope.lancamentoConciliado.tipo);
                    centroCustos();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var calcularSaldo = function(saldoAtual, saldoReconciliacao) {
            return {
                atual: saldoAtual,
                reconciliacao: saldoReconciliacao,
                diferenca: saldoReconciliacao - saldoAtual
            }
        }     
        
        var planoContas = function(tipo) {
            PlanoContaService.getStructureByTipo(tipo.id)
                .then(function (data) {
                    $scope.planoContas = data;
                    PlanoContaService.estrutura($scope.planoContas);
                    $scope.planoContas = PlanoContaService.flatten($scope.planoContas);
//                    $scope.planoContas = criarPlanoContasLista($scope.planoContas);
                    if($scope.lancamentoConciliado.planoConta) {
                        $scope.lancamentoConciliado.planoConta = ListaService.getPlanoContaValue(
                                $scope.planoContas, $scope.lancamentoConciliado.planoConta.idPlanoConta) || $scope.planoContas[0];
                    } else {
                         $scope.lancamentoConciliado.planoConta  = $scope.planoContas[0];
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var centroCustos = function() {
            CentroCustoService.getStructure()
                .then(function (data) {
                    $scope.centroCustos = data;
                    CentroCustoService.estrutura($scope.centroCustos);
                    $scope.centroCustos = CentroCustoService.flatten($scope.centroCustos);
//                    $scope.centroCustos = criarCentroCustosLista($scope.centroCustos);
                    if($scope.lancamentoConciliado.centroCusto && $scope.lancamentoConciliado.centroCusto.idCentroCusto) {
                        $scope.lancamentoConciliado.centroCusto = ListaService.getCentroCustoValue(
                                $scope.centroCustos, $scope.lancamentoConciliado.centroCusto.idCentroCusto) || $scope.centroCustos[0];
                    } else {
                         $scope.lancamentoConciliado.centroCusto  = null;
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $scope.lancamentoConciliado = ajustarDados($scope.saldo, $scope.lancamentoConciliado);
            $modalInstance.close($scope.lancamentoConciliado);            
        };
        
        var ajustarDados = function(saldo, lancamentoConciliado) {
            lancamentoConciliado.dataCompetencia = lancamentoConciliado.dataLancamento;
            lancamentoConciliado.dataEmissao = lancamentoConciliado.dataLancamento;
            if(saldo.diferenca < 0) { lancamentoConciliado.valor = saldo.diferenca * -1; }
            else { lancamentoConciliado.valor = saldo.diferenca; }
            return lancamentoConciliado;
        }
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        $scope.validarPlanoConta = function(planoConta) {
            if(planoConta.ehGrupo) {
                modalMessage(MESSAGES.planoConta.info.NAO_PERMITE_GRUPO);
            }
        }
        
        $scope.validarCentroCusto = function(centroCusto) {
            if(centroCusto.ehGrupo) {
                modalMessage(MESSAGES.centroCusto.info.NAO_PERMITE_GRUPO);
            }
        }
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };

        var validarForm = function (form) {
            if($scope.lancamentoConciliado.planoConta && $scope.lancamentoConciliado.planoConta.ehGrupo) {
                alert(MESSAGES.planoConta.info.NAO_PERMITE_GRUPO);
                return false;
            }
            if($scope.lancamentoConciliado.centroCusto && $scope.lancamentoConciliado.centroCusto.ehGrupo) {
                alert(MESSAGES.centroCusto.info.NAO_PERMITE_GRUPO);
                return false;
            }
            if (form.dataLancamento.$error.required) {
                alert(MESSAGES.lancamento.conciliar.validacao.DATA_LANCAMENTO_REQUERIDA);
                return false;
            }       
            if (form.dataLancamento.$modelValue && !moment(form.dataLancamento.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.conciliar.validacao.DATA_LANCAMENTO_VALIDA);
                return false;
            }  
            if (form.historico.$error.required) {
                alert(MESSAGES.lancamento.conciliar.validacao.HISTORICO_REQUERIDA);
                return false;
            }
            return true;
        }     

        init();

    }]);

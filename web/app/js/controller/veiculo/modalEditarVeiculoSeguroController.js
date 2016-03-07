'use strict';

app.controller('ModalEditarVeiculoSeguroController', ['$scope', '$modalInstance', '$filter', 'veiculoSeguro', 'VeiculoService', 'CepService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, $filter, veiculoSeguro, VeiculoService, CepService, DatePickerService, ListaService, LISTAS) {

        var init = function () {  
            $scope.seguros = LISTAS.seguro;
            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
            $scope.datepickerDataFim = angular.copy(DatePickerService.default);      
            $scope.veiculoSeguro = {
                idVeiculoSeguro: (veiculoSeguro && veiculoSeguro.idVeiculoSeguro) || null,
                veiculo: (veiculoSeguro && veiculoSeguro.veiculo) || { idVeiculo: null }, 
                numeroApolice: (veiculoSeguro && veiculoSeguro.numeroApolice) || null,     
                corretora: (veiculoSeguro && veiculoSeguro.corretora) || null,        
                assegurado: (veiculoSeguro && veiculoSeguro.assegurado) || null,     
                valorFranquia: (veiculoSeguro && veiculoSeguro.valorFranquia) || null,    
                indenizacao: (veiculoSeguro && veiculoSeguro.indenizacao) || $scope.seguros[0],   
                dataInicioVigencia: (veiculoSeguro && veiculoSeguro.dataInicioVigencia) || $filter('date')(new Date(), 'yyyy-MM-dd'),
                dataFimVigencia: (veiculoSeguro && veiculoSeguro.dataFimVigencia) || $filter('date')(new Date(), 'yyyy-MM-dd')
            }; 
            getTitle();
            veiculos();
        };
        
        var getTitle = function() {
            if(veiculoSeguro && veiculoSeguro.idVeiculoSeguro) { $scope.title = "Editar Seguro do Veículo"; }
            else { $scope.title = "Inserir Nova Seguro do Veículo"; }
        }

        var veiculos = function () {
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculos = ajustarVeiculos(data);
                    $scope.veiculoSeguro.veiculo = ListaService.getVeiculoValue($scope.veiculos, $scope.veiculoSeguro.veiculo.idVeiculo);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var ajustarVeiculos = function(veiculos) {
            return _.map(veiculos, function(veiculo) {
                veiculo.descricao = veiculo.marca + ' / ' + veiculo.modelo + ' (' + $filter('placa')(veiculo.placa) + ')';
                return veiculo;
            });
        }
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.veiculoSeguro);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {   
            if (form.numeroApolice.$error.required) {
                alert('Preencha a número da apólice do seguro!');
                return false;
            }
            if (form.valorFranquia.$error.required) {
                alert('Preencha o valor do seguro!');
                return false;
            }            
            if (form.corretora.$error.required) {
                alert('Preencha a corretora!');
                return false;
            }    
            if (form.assegurado.$error.required) {
                alert('Preencha o assegurado!');
                return false;
            }    
            if (form.dataInicioVigencia.$error.required) {
                alert('Preencha a data inicio da vigência do seguro!');
                return false;
            }    
            if (form.dataFimVigencia.$error.required) {
                alert('Preencha a data fim da vigência do seguro!');
                return false;
            } 
            return true;
        }   

        init();

    }]);

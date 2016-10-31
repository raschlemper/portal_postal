'use strict';

app.controller('ModalEditarVeiculoMultaController', ['$scope', '$modalInstance', '$filter', 'veiculoMulta', 'VeiculoService', 'CepService', 
    'DatePickerService', 'ListaService', 'ValorService',
    function ($scope, $modalInstance, $filter, veiculoMulta, VeiculoService, CepService, DatePickerService, ListaService, ValorService) {

        var init = function () {  
            $scope.datepicker = DatePickerService.default;      
            $scope.veiculoMulta = veiculoMulta || {};
            $scope.veiculoMulta.data = (veiculoMulta && veiculoMulta.data) || new Date();  
            $scope.veiculoMulta.descontada = (veiculoMulta && veiculoMulta.descontada);
            if($scope.veiculoMulta.descontada == null) { $scope.veiculoMulta.descontada = false; }
            getTitle();
            veiculos();
        };

        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            if(veiculoMulta && veiculoMulta.id) { $scope.title = "Editar Multa Veículo"; }
            else { $scope.title = "Inserir Nova Multa Veículo"; }
        }

        var veiculos = function () {
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculoLista = ajustarVeiculos(data);
                    if($scope.veiculoMulta.veiculo) {
                        $scope.veiculoMulta.veiculo = ListaService.getVeiculoValue($scope.veiculoLista, $scope.veiculoMulta.veiculo.idVeiculo);
                    } else {
                        if($scope.veiculoLista && $scope.veiculoLista.length) { $scope.veiculoMulta.veiculo = $scope.veiculoLista[0]; }
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.pesquisarCep = function(cep) {
            CepService.cep(cep)
                .then(function (data) {
                    $scope.veiculoMulta.local = ValorService.getValueLocalCep(data);
                })
                .catch(function (e) {
                    $scope.veiculoMulta.local = '';
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.veiculoMulta);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
                
        // ***** AJUSTAR ***** // 
        
        var ajustarVeiculos = function(veiculos) {
            return _.map(veiculos, function(veiculo) {
                veiculo.descricao = ValorService.getValueDescricaoVeiculo(veiculo);
                return veiculo;
            });
        };
                
        // ***** VALIDAR ***** // 

        var validarForm = function (form) {      
            if (form.condutor.$error.required) {
                alert('Preencha o nome do condutor da multa!');
                return false;
            }    
            if (form.numero.$error.required) {
                alert('Preencha o número da multa!');
                return false;
            }    
            if (form.valor.$error.required) {
                alert('Preencha o valor da multa!');
                return false;
            }          
            if (form.data.$error.required) {
                alert('Preencha a data de multa!');
                return false;
            }          
            if (form.data.$modelValue && !moment(form.data.$modelValue).isValid()) {
                alert('A data da multa não é válida!');
                return false;
            }    
            return true;
        }   

        init();

    }]);

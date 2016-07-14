'use strict';

app.controller('ModalEditarVeiculoMultaController', ['$scope', '$modalInstance', '$filter', 'veiculoMulta', 'VeiculoService', 'CepService', 'DatePickerService', 'ListaService', 'ValorService',
    function ($scope, $modalInstance, $filter, veiculoMulta, VeiculoService, CepService, DatePickerService, ListaService, ValorService) {

        var init = function () {  
            $scope.datepicker = DatePickerService.default;      
            $scope.veiculoMulta = {
                idVeiculoMulta: (veiculoMulta && veiculoMulta.idVeiculoMulta) || null,
                veiculo: (veiculoMulta && veiculoMulta.veiculo) || { idVeiculo: null },
                condutor: (veiculoMulta && veiculoMulta.condutor) || null,    
                numero: (veiculoMulta && veiculoMulta.numero) || null,       
                data: (veiculoMulta && veiculoMulta.data) || new Date(),   
                valor: (veiculoMulta && veiculoMulta.valor) || null,    
                descontada: (veiculoMulta && veiculoMulta.descontada),
                local: (veiculoMulta && veiculoMulta.local) || null,
                descricao: (veiculoMulta && veiculoMulta.descricao) || null
            }; 
            if($scope.veiculoMulta.descontada == null) { $scope.veiculoMulta.descontada = false; }
            getTitle();
            veiculos();
        };
        
        var getTitle = function() {
            if(veiculoMulta && veiculoMulta.id) { $scope.title = "Editar Multa Veículo"; }
            else { $scope.title = "Inserir Nova Multa Veículo"; }
        }

        var veiculos = function () {
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculos = ajustarVeiculos(data);
                    $scope.veiculoMulta.veiculo = ListaService.getVeiculoValue($scope.veiculos, $scope.veiculoMulta.veiculo.idVeiculo);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var ajustarVeiculos = function(veiculos) {
            return _.map(veiculos, function(veiculo) {
                veiculo.descricao = ValorService.getValueLocalCep(data);
                return veiculo;
            });
        }
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.veiculoMulta);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        $scope.pesquisarCep = function(cep) {
            CepService.cep(cep)
                .then(function (data) {
                    $scope.veiculoMulta.local = data.logradouro + ', ' + data.bairro + ' - ' + data.cidade + '/' + data.estado;
                })
                .catch(function (e) {
                    $scope.veiculoMulta.local = '';
                });
        }

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

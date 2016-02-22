'use strict';

veiculo.controller('VeiculoController', ['$scope', '$uibModal', 'VeiculoService', 'FipeService', 'ModalMessageService', 'LISTAS',
    function ($scope, $uibModal, VeiculoService, FipeService, ModalMessageService, LISTAS) {

        var init = function () {
            $scope.tipos = LISTAS.tipo;
            $scope.combustiveis = LISTAS.combustivel;
            $scope.status = LISTAS.status;
            $scope.situacoes = LISTAS.situacao;
            
            $scope.veiculo = {
                tipo: $scope.tipos[1],
                marca: [],
                modelo: [],
                placa: null,
                anoFabricacao: null,
                anoModelo: null,
                chassis: null,
                renavam: null,
                quilometragem: null,
                combustivel: $scope.combustiveis[0],         
                status: $scope.status[0],          
                situacao: $scope.situacoes[0]
            }; 

            $scope.minVal = 1970;
            $scope.maxVal = (new Date).getFullYear() + 1;

            $scope.changeTipo($scope.veiculo.tipo);
            $scope.todos();
        };

        $scope.changeTipo = function (tipo) {
            FipeService.marcaVeiculo(tipo.key)
                    .then(function (data) {
                        $scope.marcas = data;
                        $scope.veiculo.marca = data[0];
                        $scope.changeMarca($scope.veiculo.tipo, $scope.veiculo.marca);
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
        };

        $scope.changeMarca = function (tipo, marca) {
            FipeService.modeloVeiculo(tipo.key, marca.id)
                    .then(function (data) {
                        $scope.modelos = data;
                        $scope.veiculo.modelo = data[0];
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
        };

        $scope.todos = function () {
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculos = data;
                })
                .catch(function (e) {
                    alertMsg(e.error);
                });
        };

        $scope.salvar = function (form) {
            if (validarForm(form)) {
                VeiculoService.save($scope.veiculo)
                    .then(function (data) {                        
                        $scope.todos();
                        alertMsg("Veículo Inserido " + getMsgToClient(data) +  " com sucesso!");
                        init();
                    })
                    .catch(function(e) {
                        alertMsg(e.error);
                    });
            }
        };

        var validarForm = function (form) {
            if (form.placa.$error.required) {
                alert('Preencha a placa do ve\u00EDculo!');
                return false;
            }
            if (form.anoFabricacao.$error.min || form.anoFabricacao.$error.max) {
                alert('Preencha o ano de fabrica\u00E7\u00E3o do ve\u00EDculo com valores entre ' + $scope.minVal + ' e ' + $scope.maxVal + '!');
                return false;
            }
            if (form.anoModelo.$error.min || form.anoModelo.$error.max) {
                alert('Preencha o ano do modelo do ve\u00EDculo com valores entre ' + $scope.minVal + ' e ' + $scope.maxVal + '!');
                return false;
            }
            if (form.renavam.$error.required) {
                alert('Preencha o renavam do ve\u00EDculo!');
                return false;
            }
            if (form.quilometragem.$error.required) {
                alert('Preencha a quilometragem do ve\u00EDculo!');
                return false;
            }
            return true;
        }           
    
        var getMsgToClient = function(veiculo) {
            return veiculo.modelo + " (" + veiculo.placa + ")";        
        }
        
        var alertMsg = function (message) {
            bootbox.dialog(ModalMessageService.alertMessage(message));
        };

        init();

    }]);

'use strict';

veiculo.controller('VeiculoController', ['$scope', 'VeiculoService', 'FipeService', 'LISTAS',
    function ($scope, VeiculoService, FipeService, LISTAS) {

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
                    console.log(e);
                });
        };

        $scope.salvar = function (form) {
            if (validarForm(form)) {
                VeiculoService.save($scope.veiculo)
                    .then(function (data) {                        
                        $scope.todos();
                        init();
                        telaMsg();
                    })
                    .catch(function (e) {
                        console.log(e);
                    });
            }
        };
        
        var setValueDefault = function(form) {
            return {
                tipo: form.tipo.value,
                marca: form.marca.value,
                modelo: form.modelo.value,
                placa: form.placa.value,
                anoFabricacao: form.anoFabricacao.value,
                anoModelo: form.anoModelo.value,
                chassis: form.chassis.value,
                renavam: form.renavam.value,
                quilometragem: form.quilometragem.value,
                combustivel: form.combustivel.value,
                status: form.status.value,
                situacao: form.situacao.value
            }
        }

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

        init();

    }]);

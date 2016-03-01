'use strict';

app.controller('ModalEditarVeiculoController', ['$scope', '$modalInstance', 'veiculo', 'FipeService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, veiculo, FipeService, ListaService, LISTAS) {

        var init = function () {
            $scope.tipos = LISTAS.tipo;
            $scope.combustiveis = LISTAS.combustivel;
            $scope.status = LISTAS.status;
            $scope.situacoes = LISTAS.situacao;
            
            $scope.veiculo = {
                idVeiculo: (veiculo && veiculo.idVeiculo) || null,
                tipo: (veiculo && ListaService.getValue($scope.tipos, veiculo.tipo)) || $scope.tipos[1],
                marca: (veiculo && veiculo.marca) || [],
                modelo: (veiculo && veiculo.modelo) || [],
                versao: (veiculo && veiculo.versao) || [],
                placa: (veiculo && veiculo.placa.toUpperCase()) || null,
                anoFabricacao: (veiculo && veiculo.anoFabricacao) || null,
                anoModelo: (veiculo && veiculo.anoModelo) || null,
                chassis: (veiculo && veiculo.chassis) || null,
                renavam: (veiculo && veiculo.renavam) || null,
                quilometragem: (veiculo && veiculo.quilometragem) || null,
                combustivel: (veiculo && ListaService.getValue($scope.combustiveis, veiculo.combustivel)) || $scope.combustiveis[0],         
                status: (veiculo && ListaService.getValue($scope.status, veiculo.status)) || $scope.status[0],          
                situacao: (veiculo && ListaService.getValue($scope.situacoes, veiculo.situacao)) || $scope.situacoes[0]
            }; 

            $scope.minVal = 1970;
            $scope.maxVal = (new Date).getFullYear() + 1;

            $scope.changeTipo($scope.veiculo.tipo);
            getTitle();
        };
        
        var getTitle = function() {
            if(veiculo && veiculo.id) { $scope.title = "Editar Ve\u00EDculo"; }
            else { $scope.title = "Inserir novo Ve\u00EDculo"; }
        }

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
                    $scope.changeModelo($scope.veiculo.tipo, $scope.veiculo.marca, $scope.veiculo.modelo);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        $scope.changeModelo = function (tipo, marca, modelo) {
            FipeService.versaoVeiculo(tipo.key, marca.id, modelo.id)
                .then(function (data) {
                    $scope.versoes = data;
                    $scope.veiculo.versao = data[0];
                    $scope.changeVersao($scope.veiculo.tipo, $scope.veiculo.marca, $scope.veiculo.modelo, $scope.veiculo.versao);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        $scope.changeVersao = function (tipo, marca, modelo, versao) {
            FipeService.veiculo(tipo.key, marca.id, modelo.id, versao.id)
                .then(function (data) {
                    console.log(data);
                    $scope.veiculoFipe = data;
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.veiculo);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
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

        init();

    }]);

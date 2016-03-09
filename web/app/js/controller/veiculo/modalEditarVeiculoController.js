'use strict';

app.controller('ModalEditarVeiculoController', ['$scope', '$modalInstance', 'veiculo', 'FipeService', 'ListaService', 'ValorService', 'LISTAS',
    function ($scope, $modalInstance, veiculo, FipeService, ListaService, ValorService, LISTAS) {

        var init = function () {
            $scope.tipos = LISTAS.tipo;
            $scope.combustiveis = LISTAS.combustivel;
            $scope.status = LISTAS.status;
            $scope.situacoes = LISTAS.situacao;
            
            $scope.veiculo = {
                idVeiculo: (veiculo && veiculo.idVeiculo) || null,
                tipo: (veiculo && veiculo.tipo) || $scope.tipos[1],
                idMarca: (veiculo && veiculo.idMarca) || null,
                marca: (veiculo && veiculo.marca) || null,
                idModelo: (veiculo && veiculo.idModelo) || null,
                modelo: (veiculo && veiculo.modelo) || null,
                idVersao: (veiculo && veiculo.idVersao) || null,
                versao: (veiculo && veiculo.versao) || null,
                placa: (veiculo && veiculo.placa.toUpperCase()) || null,
                anoModelo: (veiculo && veiculo.anoModelo) || null,
                chassis: (veiculo && veiculo.chassis) || null,
                renavam: (veiculo && veiculo.renavam) || null,
                quilometragem: (veiculo && veiculo.quilometragem) || null,
                combustivel: (veiculo && veiculo.combustivel) || $scope.combustiveis[0],         
                status: (veiculo && veiculo.status) || $scope.status[0],          
                situacao: (veiculo && veiculo.situacao) || $scope.situacoes[0]
            }; 

            $scope.minVal = 1970;
            $scope.maxVal = (new Date).getFullYear() + 1;

            $scope.changeTipo($scope.veiculo.tipo);
            getTitle();
        };
        
        var getTitle = function() {
            if(veiculo && veiculo.idVeiculo) { $scope.title = "Editar Veículo"; }
            else { $scope.title = "Inserir Novo Veículo"; }
        }

        $scope.changeTipo = function (tipo) {
            FipeService.marcaVeiculo(tipo.codigo)
                .then(function (data) {
                    $scope.marcas = data;
                    $scope.veiculo.marca = ListaService.getValue($scope.marcas, $scope.veiculo.idMarca) || data[0];
                    $scope.veiculo.idMarca = $scope.veiculo.marca.id;
                    $scope.changeMarca($scope.veiculo.tipo, $scope.veiculo.marca);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        $scope.changeMarca = function (tipo, marca) {
            FipeService.modeloVeiculo(tipo.codigo, marca.id)
                .then(function (data) {
                    $scope.modelos = data;
                    $scope.veiculo.modelo = ListaService.getValue($scope.modelos, $scope.veiculo.idModelo) || data[0];
                    $scope.veiculo.idModelo = $scope.veiculo.modelo.id;
                    $scope.changeModelo($scope.veiculo.tipo, $scope.veiculo.marca, $scope.veiculo.modelo);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        $scope.changeModelo = function (tipo, marca, modelo) {
            FipeService.versaoVeiculo(tipo.codigo, marca.id, modelo.id)
                .then(function (data) {
                    $scope.versoes = data;
                    $scope.veiculo.versao = ListaService.getValue($scope.versoes, $scope.veiculo.idVersao) || data[0];
                    $scope.veiculo.idVersao = $scope.veiculo.versao.id;
                    $scope.changeVersao($scope.veiculo.tipo, $scope.veiculo.marca, $scope.veiculo.modelo, $scope.veiculo.versao);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        $scope.changeVersao = function (tipo, marca, modelo, versao) {
            FipeService.veiculo(tipo.codigo, marca.id, modelo.id, versao.id)
                .then(function (data) {
                    if(veiculo) {
                        $scope.veiculo.combustivel = veiculo.combustivel || ListaService.getValueCombustivelFipe($scope.combustiveis, data.combustivel);
                        $scope.veiculo.anoModelo = veiculo.anoModelo || ValorService.getValueAnoFipe(data.ano_modelo);
                        veiculo.combustivel = null;
                        veiculo.anoModelo = null;
                    } else {                        
                        $scope.veiculo.combustivel = ListaService.getValueCombustivelFipe($scope.combustiveis, data.combustivel);
                        $scope.veiculo.anoModelo = ValorService.getValueAnoFipe(data.ano_modelo);
                    }
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
                alert('Preencha a placa do veículo!');
                return false;
            }
            if (form.anoModelo.$error.min || form.anoModelo.$error.max) {
                alert('Preencha o ano do modelo do veículo com valores entre ' + $scope.minVal + ' e ' + $scope.maxVal + '!');
                return false;
            }
            if (form.renavam.$error.required) {
                alert('Preencha o renavam do veículo!');
                return false;
            }
            if (form.quilometragem.$error.required) {
                alert('Preencha a quilometragem do veículo!');
                return false;
            }
            return true;
        }     

        init();

    }]);

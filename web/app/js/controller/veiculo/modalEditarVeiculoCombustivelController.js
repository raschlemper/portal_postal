'use strict';

app.controller('ModalEditarVeiculoCombustivelController', ['$scope', '$modalInstance', 'veiculoCombustivel', 'VeiculoService', 'VeiculoCombustivelService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, veiculoCombustivel, VeiculoService, VeiculoCombustivelService, DatePickerService, ListaService, LISTAS) {

        var init = function () {
            $scope.tipos = LISTAS.combustivel;
            $scope.datepicker = DatePickerService.default;
            
            $scope.veiculoCombustivel = {
                idVeiculoCombustivel: (veiculoCombustivel && veiculoCombustivel.idVeiculoCombustivel) || null,
                veiculo: (veiculoCombustivel && veiculoCombustivel.veiculo) || null,
                tipo: (veiculoCombustivel && veiculoCombustivel.tipo) || $scope.tipos[1],                
                data: (veiculoCombustivel && veiculoCombustivel.data) || new Date(),
                quantidade: (veiculoCombustivel && veiculoCombustivel.quantidade) || null,
                valorUnitario: (veiculoCombustivel && veiculoCombustivel.valorUnitario) || null,
                valorTotal: (veiculoCombustivel && veiculoCombustivel.valorTotal) || null,
                quilometragem: (veiculoCombustivel && veiculoCombustivel.quilometragem) || null
            }; 
            getTitle();
            veiculos();
        };
        
        var getTitle = function() {
            if(veiculoCombustivel && veiculoCombustivel.id) { $scope.title = "Editar Abastecimento do Veículo"; }
            else { $scope.title = "Inserir Novo Abastecimento do Veículo"; }
        }

        var veiculos = function () {
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculos = ajustarVeiculos(data);
                    $scope.veiculoCombustivel.veiculo = ListaService.getVeiculoValue($scope.veiculos, $scope.veiculoCombustivel.veiculo.idVeiculo);
                    $scope.veiculoCombustivel.tipo = $scope.veiculoCombustivel.veiculo.combustivel; //ListaService.getValue($scope.tipos, $scope.veiculoCombustivel.veiculo.combustivel.id)
                    $scope.getLastVeiculoCombustivel($scope.veiculoCombustivel.veiculo);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };

        $scope.getLastVeiculoCombustivel = function (veiculo) {
            VeiculoCombustivelService.getLast(veiculo.idVeiculo)
                .then(function (data) {
                    if(data) { $scope.quilometragemAnterior = data.quilometragem; }
                    else { $scope.quilometragemAnterior = veiculo.quilometragem}
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var ajustarVeiculos = function(veiculos) {
            return _.map(veiculos, function(veiculo) {
                veiculo.descricao = veiculo.marca + ' / ' + veiculo.modelo + ' (' + veiculo.placa + ')';
                return veiculo;
            });
        }
        
        $scope.setValorTotal = function() {
            if($scope.veiculoCombustivel.quantidade && $scope.veiculoCombustivel.valorUnitario) {
                var quantidade = $scope.veiculoCombustivel.quantidade;
                var valorUnitario = $scope.veiculoCombustivel.valorUnitario;
                $scope.veiculoCombustivel.valorTotal = valorUnitario * quantidade; 
            } else { 
                if($scope.veiculoCombustivel.valorTotal) { $scope.setValorUnitario(); }
            }
        }

        $scope.setValorUnitario = function() {
            if($scope.veiculoCombustivel.quantidade && $scope.veiculoCombustivel.valorTotal) {
                var quantidade = $scope.veiculoCombustivel.quantidade;
                var valorTotal = $scope.veiculoCombustivel.valorTotal;
                $scope.veiculoCombustivel.valorUnitario = valorTotal / quantidade;
            } else {
                if($scope.veiculoCombustivel.valorUnitario) { $scope.setValorTotal(); }
            }
        }
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.veiculoCombustivel);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {
            if (form.quantidade.$error.required) {
                alert('Preencha a quantidade de litros abastecidos!');
                return false;
            }
            if (form.valorTotal.$error.required) {
                alert('Preencha o valor total do abastecimento!');
                return false;
            }
            if (form.valorUnitario.$error.required) {
                alert('Preencha o valor unitário do abastecimento!');
                return false;
            }            
            if (form.data.$error.required) {
                alert('Preencha a data de abastecimento!');
                return false;
            }          
            if (_.isDate(form.data.$modelValue)) {
                alert('A data do abstecimento não é válida!');
                return false;
            }          
            if (form.quilometragem.$error.required) {
                alert('Preencha a quilometragem do veículo!');
                return false;
            }    
            if (form.quilometragem.$modelValue <= $scope.quilometragemAnterior) {
                alert('A quilometragem não pode ser inferior ou igual a última quilometragem inserida para este veículo!');
                return false;
            }
            return true;
        }     

        init();

    }]);

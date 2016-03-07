'use strict';

app.controller('ModalEditarVeiculoSinistroController', ['$scope', '$modalInstance', '$filter', 'veiculoSinistro', 'VeiculoService', 'CepService', 'ValorService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, $filter, veiculoSinistro, VeiculoService, CepService, ValorService, DatePickerService, ListaService, LISTAS) {

        var init = function () {  
            $scope.tipos = LISTAS.sinistro;  
            $scope.responsaveis = LISTAS.responsavel; 
            $scope.datepicker = DatePickerService.default;      
            $scope.veiculoSinistro = {
                idVeiculoSinistro: (veiculoSinistro && veiculoSinistro.idVeiculoSinistro) || null,
                veiculo: (veiculoSinistro && veiculoSinistro.veiculo) || { idVeiculo: null },
                tipo: (veiculoSinistro && veiculoSinistro.tipo) || $scope.tipos[0],    
                boletimOcorrencia: (veiculoSinistro && veiculoSinistro.boletimOcorrencia) || null,       
                data: (veiculoSinistro &&  veiculoSinistro.data) || new Date(),  
                responsavel: (veiculoSinistro && veiculoSinistro.responsavel) || $scope.responsaveis[0],    
                local: (veiculoSinistro && veiculoSinistro.local) || null,
                descricao: (veiculoSinistro && veiculoSinistro.descricao) || null
            }; 
            getTitle();
            veiculos();
        };
        
        var getTitle = function() {
            if(veiculoSinistro && veiculoSinistro.id) { $scope.title = "Editar Sinistro do Veículo"; }
            else { $scope.title = "Inserir Novo Sinistro do Veículo"; }
        }

        var veiculos = function () {
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculos = ajustarVeiculos(data);
                    $scope.veiculoSinistro.veiculo = ListaService.getVeiculoValue($scope.veiculos, $scope.veiculoSinistro.veiculo.idVeiculo);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var ajustarVeiculos = function(veiculos) {
            return _.map(veiculos, function(veiculo) {
                veiculo.descricao = ValorService.getValueDescricaoVeiculo(veiculo);
                return veiculo;
            });
        }
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.veiculoSinistro);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        $scope.pesquisarCep = function(cep) {
            CepService.cep(cep)
                .then(function (data) {
                    $scope.veiculoSinistro.local = ValorService.getValueLocalCep(data);
                })
                .catch(function (e) {
                    $scope.veiculoSinistro.local = '';
                });
        }

        var validarForm = function (form) {   
            if (form.boletimOcorrencia.$error.required) {
                alert('Preencha o número BO do sinistro!');
                return false;
            }      
            if (form.data.$error.required) {
                alert('Preencha a data do sinistro!');
                return false;
            }          
            if (!_.isDate(form.data.$modelValue)) {
                alert('A data do sinistro não é válida!');
                return false;
            }    
            return true;
        }   

        init();

    }]);

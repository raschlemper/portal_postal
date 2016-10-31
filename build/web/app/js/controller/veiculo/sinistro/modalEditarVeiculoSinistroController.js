'use strict';

app.controller('ModalEditarVeiculoSinistroController', ['$scope', '$modalInstance', 'veiculoSinistro', 'VeiculoService', 'CepService', 'ValorService', 
    'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, veiculoSinistro, VeiculoService, CepService, ValorService, DatePickerService, ListaService, LISTAS) {

        var init = function () {  
            $scope.tipos = LISTAS.sinistro;  
            $scope.responsaveis = LISTAS.responsavel; 
            $scope.datepicker = DatePickerService.default;      
            $scope.veiculoSinistro = veiculoSinistro || {};
            $scope.veiculoSinistro.tipo = (veiculoSinistro && veiculoSinistro.tipo) || $scope.tipos[0];   
            $scope.veiculoSinistro.data = (veiculoSinistro &&  veiculoSinistro.data) || new Date();  
            $scope.veiculoSinistro.responsavel = (veiculoSinistro && veiculoSinistro.responsavel) || $scope.responsaveis[0];
            getTitle();
            veiculos();
        };

        // ***** CONTROLLER ***** //
                
        var getTitle = function() {
            if(veiculoSinistro && veiculoSinistro.idVeiculoSinistro) { $scope.title = "Editar Sinistro Veículo"; }
            else { $scope.title = "Inserir Novo Sinistro Veículo"; }
        }

        var veiculos = function () {
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculoLista = ajustarVeiculos(data);
                    if($scope.veiculoSinistro.veiculo) {
                        $scope.veiculoSinistro.veiculo = ListaService.getVeiculoValue($scope.veiculoLista, $scope.veiculoSinistro.veiculo.idVeiculo);
                    } else {
                        if($scope.veiculoLista && $scope.veiculoLista.length) { $scope.veiculoSinistro.veiculo = $scope.veiculoLista[0]; }
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.pesquisarCep = function(cep) {
            CepService.cep(cep)
                .then(function (data) {
                    $scope.veiculoSinistro.local = ValorService.getValueLocalCep(data);
                })
                .catch(function (e) {
                    $scope.veiculoSinistro.local = '';
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.veiculoSinistro);
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
            if (form.boletimOcorrencia.$error.required) {
                alert('Preencha o número BO do sinistro!');
                return false;
            }      
            if (form.data.$error.required) {
                alert('Preencha a data do sinistro!');
                return false;
            }          
            if (form.data.$modelValue && !moment(form.data.$modelValue).isValid()) {
                alert('A data do sinistro não é válida!');
                return false;
            }    
            return true;
        }   

        init();

    }]);

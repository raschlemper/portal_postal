'use strict';

app.controller('ModalEditarVeiculoSeguroController', ['$scope', '$modalInstance', 'veiculoSeguro', 'VeiculoService', 'ValorService', 'DatePickerService', 
    'ListaService', 'LISTAS',
    function ($scope, $modalInstance, veiculoSeguro, VeiculoService, ValorService, DatePickerService, ListaService, LISTAS) {

        var init = function () {  
            $scope.seguros = LISTAS.seguro;
            $scope.datepickerDataInicio = angular.copy(DatePickerService.default);      
            $scope.datepickerDataFim = angular.copy(DatePickerService.default);      
            $scope.veiculoSeguro = veiculoSeguro || {};
            $scope.veiculoSeguro.indenizacao = (veiculoSeguro && veiculoSeguro.indenizacao) || $scope.seguros[0]; 
            $scope.veiculoSeguro.dataInicioVigencia = (veiculoSeguro && veiculoSeguro.dataInicioVigencia) || new Date();
            $scope.veiculoSeguro.dataFimVigencia = (veiculoSeguro && veiculoSeguro.dataFimVigencia) || new Date(); 
            getTitle();
            veiculos();
        };

        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            if(veiculoSeguro && veiculoSeguro.idVeiculoSeguro) { $scope.title = "Editar Seguro Veículo"; }
            else { $scope.title = "Inserir Novo Seguro Veículo"; }
        }

        var veiculos = function () {
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculoLista = ajustarVeiculos(data);
                    if($scope.veiculoSeguro.veiculo) {
                        $scope.veiculoSeguro.veiculo = ListaService.getVeiculoValue($scope.veiculoLista, $scope.veiculoSeguro.veiculo.idVeiculo);
                    } else {
                        if($scope.veiculoLista && $scope.veiculoLista.length) { $scope.veiculoSeguro.veiculo = $scope.veiculoLista[0]; }
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.veiculoSeguro);
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
            if (form.dataInicioVigencia.$modelValue && !moment(form.dataInicioVigencia.$modelValue).isValid()) {
                alert('A data inicio da vigência não é válida!');
                return false;
            }      
            if (form.dataFimVigencia.$error.required) {
                alert('Preencha a data fim da vigência do seguro!');
                return false;
            }        
            if (form.dataFimVigencia.$modelValue && !moment(form.dataFimVigencia.$modelValue).isValid()) {
                alert('A data fim da vigência não é válida!');
                return false;
            }    
            return true;
        }   

        init();

    }]);

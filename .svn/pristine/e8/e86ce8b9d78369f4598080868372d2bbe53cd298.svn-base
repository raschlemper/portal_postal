'use strict';

app.controller('ModalEditarVeiculoManutencaoController', ['$scope', '$modalInstance', 'veiculoManutencao', 'VeiculoService', 'DatePickerService', 
    'ListaService', 'ValorService', 'LISTAS',
    function ($scope, $modalInstance, veiculoManutencao, VeiculoService, DatePickerService, ListaService, ValorService, LISTAS) {

        var init = function () {
            $scope.tipos = LISTAS.manutencao;    
            $scope.datepickerManutencao = angular.copy(DatePickerService.default);    
            $scope.datepickerAgendamento = angular.copy(DatePickerService.default);   
            $scope.veiculoManutencao = veiculoManutencao || {};
            $scope.veiculoManutencao.tipo = (veiculoManutencao && veiculoManutencao.tipo) || $scope.tipos[0];
            getTitle();
            veiculos();
        };

        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            if(veiculoManutencao && veiculoManutencao.idVeiculoManutencao) { $scope.title = "Editar Manutenção Veículo"; }
            else { $scope.title = "Inserir Nova Manutenção Veículo"; }
        };

        var veiculos = function () {
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculoLista = ajustarVeiculos(data);
                    if($scope.veiculoManutencao.veiculo) {
                        $scope.veiculoManutencao.veiculo = ListaService.getVeiculoValue($scope.veiculoLista, $scope.veiculoManutencao.veiculo.idVeiculo);
                    } else {
                        if($scope.veiculoLista && $scope.veiculoLista.length) { $scope.veiculoManutencao.veiculo = $scope.veiculoLista[0]; }
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.veiculoManutencao);
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
            if (form.dataManutencao.$modelValue && !moment(form.dataManutencao.$modelValue).isValid()) {
                alert('A data da manutenção não é válida!');
                return false;
            }           
            if (form.dataAgendamento.$modelValue && !moment(form.dataAgendamento.$modelValue).isValid()) {
                alert('A data de agendamento não é válida!');
                return false;
            }    
            return true;
        }     

        init();

    }]);

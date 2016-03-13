'use strict';

app.controller('ModalEditarVeiculoManutencaoController', ['$scope', '$modalInstance', '$filter', 'veiculoManutencao', 'VeiculoService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, $filter, veiculoManutencao, VeiculoService, DatePickerService, ListaService, LISTAS) {

        var init = function () {
            $scope.tipos = LISTAS.manutencao;    
            $scope.datepickerManutencao = angular.copy(DatePickerService.default);    
            $scope.datepickerAgendamento = angular.copy(DatePickerService.default);   
            $scope.veiculoManutencao = {
                idVeiculoManutencao: (veiculoManutencao && veiculoManutencao.idVeiculoManutencao) || null,
                veiculo: (veiculoManutencao && veiculoManutencao.veiculo) || { idVeiculo: null },
                tipo: (veiculoManutencao && veiculoManutencao.tipo) || $scope.tipos[0],    
                quilometragem: (veiculoManutencao && veiculoManutencao.quilometragem) || null,        
                valor: (veiculoManutencao && veiculoManutencao.valor) || null,    
                dataManutencao: (veiculoManutencao && veiculoManutencao.dataManutencao) || null,
                dataAgendamento: (veiculoManutencao && veiculoManutencao.dataAgendamento) || null,
                descricao: (veiculoManutencao && veiculoManutencao.descricao) || null
            }; 
            getTitle();
            veiculos();
        };
        
        var getTitle = function() {
            if(veiculoManutencao && veiculoManutencao.idVeiculoManutencao) { $scope.title = "Editar Manutenção Veículo"; }
            else { $scope.title = "Inserir Nova Manutenção Veículo"; }
        };

        var veiculos = function () {
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculos = ajustarVeiculos(data);
                    $scope.veiculoManutencao.veiculo = ListaService.getVeiculoValue($scope.veiculos, $scope.veiculoManutencao.veiculo.idVeiculo);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var ajustarVeiculos = function(veiculos) {
            return _.map(veiculos, function(veiculo) {
                veiculo.descricao = veiculo.marca + ' / ' + veiculo.modelo + ' (' + $filter('placa')(veiculo.placa) + ')';
                return veiculo;
            });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.veiculoManutencao);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

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

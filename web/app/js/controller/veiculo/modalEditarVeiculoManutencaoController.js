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
                dataManutencao: (veiculoManutencao && veiculoManutencao.dataManutencao) || new Date(),
                dataAgendamento: (veiculoManutencao && veiculoManutencao.dataAgendamento) || new Date(),
                descricao: (veiculoManutencao && veiculoManutencao.descricao) || null
            }; 
            getTitle();
            veiculos();
        };
        
        var getTitle = function() {
            if(veiculoManutencao && veiculoManutencao.id) { $scope.title = "Editar Manutenção do Veículo"; }
            else { $scope.title = "Inserir Nova Manutenção do Veículo"; }
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
            if (form.quilometragem.$error.required) {
                alert('Preencha a quilometragem do veículo!');
                return false;
            }    
            if (form.valor.$error.required) {
                alert('Preencha o valor da manutenção!');
                return false;
            }          
            if (form.dataManutencao.$error.required) {
                alert('Preencha a data de manutenção!');
                return false;
            }          
            if (!_.isDate(form.dataManutencao.$modelValue)) {
                alert('A data da manutenção não é válida!');
                return false;
            }           
            if (!_.isDate(form.dataAgendamento.$modelValue)) {
                alert('A data de agendamento não é válida!');
                return false;
            }    
            return true;
        }     

        init();

    }]);

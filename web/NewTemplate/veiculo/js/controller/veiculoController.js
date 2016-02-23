'use strict';

veiculo.controller('VeiculoController', ['$scope', '$uibModal', 'VeiculoService', 'ModalService',
    function ($scope, $uibModal, VeiculoService, ModalService) {

        var init = function () {
            todos();     
            LoadDataTablesScripts(sortAndSearchTable('datatable-veiculos'));
        };
        
        $scope.columns = [
  {
    header: 'Id', // This string is displayed on table header name.

    value: 'id',  // This string is the name of property in your list declared on your html.

    show: false,  // This property, show or hide this column on your table.

    size: 20      // This property is used to define column size in percentage (%)
                  // If property 'show' is defined 'false', this size is ignored
  },
  { header: 'First Name', value: 'first_name', show: true, size: 40 },
  { header: 'Last Name', value: 'last_name', show: true, size: 40 }
];

$scope.config = {
  id: 'my-table',
  columns: $scope.columns
};
    
        var sortAndSearchTable = function(table) {
            return function() {
                StartDataTable(table);            
                LoadSelect2Script(MakeSelectDataTable(table));    
            }    
        }

        var todos = function() {
            VeiculoService.getAll()
                .then(function (data) {
                    $scope.veiculos = data;
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
        };

        $scope.salvar = function() {
            modalSalvar().then(function(result) {
                VeiculoService.save(result)
                    .then(function(data) {  
                        modalMessage("Veículo Inserido " + getMsgToClient(data) +  " com sucesso!");
                        init();
                    })
                    .catch(function(e) {
                        modalMessage(e.error);
                    });
            });
        };

        $scope.editar = function(idVeiculo) {
            VeiculoService.get(idVeiculo)
                .then(function(veiculo) {
                     modalSalvar(veiculo).then(function(result) {
                        VeiculoService.save(result)
                            .then(function (data) {  
                                modalMessage("Veículo " + getMsgToClient(data) + " Alterado com sucesso!");
                                init();
                            })
                            .catch(function(e) {
                                modalMessage(e.error);
                            });
                    });
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });
           
        };

        $scope.excluir = function(idVeiculo) {
            modalExcluir().then(function() {
                VeiculoService.delete(idVeiculo)
                    .then(function(data) { 
                        modalMessage("Veículo " + getMsgToClient(data) + " Removido com sucesso!");
                        init(); 
                    })
                    .catch(function(e) {
                        modalMessage(e.error);
                    });
            });
        };      
    
        var getMsgToClient = function(veiculo) {
            return veiculo.modelo + " (" + veiculo.placa + ")";        
        }
        
        var modalMessage = function(message) {
            $uibModal.open(ModalService.modalMessage(message));
        };
        
        var modalSalvar = function(veiculo) {
            var modalInstance = $uibModal.open(
                ModalService.modalSalvar('partials/modalVeiculo.html', 'ModalVeiculoController', veiculo)
            );    
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = $uibModal.open(
                ModalService.modalExcluir('Excluir Ve\u00EDculo?', 'Deseja realmente excluir este ve\u00EDculo?')
            );    
            return modalInstance.result;
        };

        init();

    }]);

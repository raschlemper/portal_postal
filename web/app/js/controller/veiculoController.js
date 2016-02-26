'use strict';

app.controller('VeiculoController', ['$scope', '$modal', 'VeiculoService', 'ModalService', 'DTOptionsBuilder', 'LISTAS',
    function ($scope, $modal, VeiculoService, ModalService, DTOptionsBuilder, LISTAS) {

        var init = function () {
            todos(); 
            $scope.sizes = LISTAS.size;
            $scope.sizeTable = LISTAS.size[0];
            $scope.situacoes = LISTAS.situacao;
            $scope.changeTableOptions($scope.sizeTable);
        };
        
        $scope.dtOptions = DTOptionsBuilder.newOptions()
            //.withDOM("<'box-content'<'col-xs-3 col-sm-3' <'teste'> f><'col-xs-3 col-sm-3 pull-right' l><'clearfix'>>rt<'box-content'<'col-xs-6 col-sm-6'i><'col-xs-6 col-sm-6 pull-right'p><'clearfix'>>")
            .withOption("searching", false)
            .withOption("lengthChange", false)
            .withOption("aaSorting", [[ 0, "asc" ]])
            .withBootstrap()
            .withBootstrapOptions({
                pagination: {
                    classes: {
                        ul: 'pagination pagination-sm'
                    }
                }
            })
            .withLanguage({
                "sEmptyTable": "Nenhum registro encontrado",
                "sInfo": "Mostrando de _START_ at\u00e9 _END_ de _TOTAL_ registros",
                "sInfoEmpty": "",
                "sInfoFiltered": "(Filtrados de _MAX_ registros)",
                "sInfoPostFix": "",
                "sInfoThousands": ".",
                "sLengthMenu": "Mostrar: _MENU_ registros",
                "sLoadingRecords": "Carregando...",
                "sProcessing": "Processando...",
                "sSearch": "Pesquisar: ",
                "sZeroRecords": "Nenhum registro encontrado",
                "oPaginate": {
                    "sNext": "Pr\u00f3ximo",
                    "sPrevious": "Anterior",
                    "sFirst": "Primeiro",
                    "sLast": "\u00daltimo"
                }
            });
            
        $scope.changeTableOptions = function(size) {
            $scope.dtOptions.withDisplayLength(size.key);            
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
            $modal.open(ModalService.modalMessage(message));
        };
        
        var modalSalvar = function(veiculo) {
            var modalInstance = $modal.open(
                ModalService.modalSalvar('partials/veiculo/modalVeiculo.html', 'ModalVeiculoController', veiculo)
            );    
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = $modal.open(
                ModalService.modalExcluir('Excluir Ve\u00EDculo?', 'Deseja realmente excluir este ve\u00EDculo?')
            );    
            return modalInstance.result;
        };

        init();

    }]);

'use strict';

app.controller('ColaboradorController', 
    ['$scope', '$q', 'ColaboradorService', 'ModalService', 'ColaboradorHandler', 'InformacaoProfissionalHandler', 'InformacaoBancariaHandler', 'MESSAGES',
    function ($scope, $q, ColaboradorService, ModalService, ColaboradorHandler, InformacaoProfissionalHandler, InformacaoBancariaHandler, MESSAGES) {

        var init = function () {
            $scope.colaboradores = [];
            $scope.colaboradoresLista = [];
            initTable();      
            todos();
        };  

        // ***** TABLE ***** //
        
        var initTable = function() {            
            $scope.colunas = [
                {label: 'Nome', column: 'nome'},
                {label: 'Cargo / Função', column: 'cargoFuncao'},                         
                {label: 'Celular', column: 'celular', filter: {name: 'telefone', args: ''}},                         
                {label: 'Email', column: 'email'},                         
                {label: 'Situação', column: 'situacao'}
            ]            
            $scope.linha = {
                events: { 
                    edit: function(colaborador) {
                        $scope.editar(colaborador);
                    },
                    remove: function(colaborador) {
                        $scope.excluir(colaborador);
                    },
                    view: function(colaborador) {
                        $scope.visualizar(colaborador);
                    }
                }
            }             
        };

        // ***** CONTROLLER ***** //

        var todos = function() {
            ColaboradorService.getAll()
                .then(function (data) {
                    $scope.colaboradores = data;
                    $scope.colaboradoresLista = criarColaboradorsLista($scope.colaboradores);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var criarColaboradorsLista = function(colaboradores) {
            return _.map(colaboradores, function(colaborador) {
                if(colaborador.informacaoProfissional) {
                    colaborador.cargoFuncao = colaborador.informacaoProfissional.cargoFuncao;
                }
                colaborador.situacao = colaborador.status.descricao;
                return _.pick(colaborador, 'idColaborador', 'nome', 'cargoFuncao', 'celular', 'email', 'situacao');
            })
        };

        // ***** VISUALIZAR ***** //

        $scope.visualizar = function(colaborador) {
            ColaboradorService.get(colaborador.idColaborador)
                .then(function(result) {
                     visualizar(result);          
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var visualizar = function(colaborador) {
            modalVisualizar(colaborador).then(function(result) {
                $scope.editar(result);
            })  
        };

        // ***** SALVAR ***** //

        $scope.salvar = function() {
            salvar();
        };

        var salvar = function() {
            modalSalvar()
                .then(function(result) {
                    result = ajustarDados(result);
                    save(result);
                });
        };

        var save = function(colaborador) {
            ColaboradorService.save(colaborador)
                .then(function(data) {  
                    modalMessage("Colaborador " + data.nome +  " Inserido com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EDITAR ***** //

        $scope.editar = function(colaborador) {
            ColaboradorService.get(colaborador.idColaborador)
                .then(function(result) {
                     editar(result);
                })
                .catch(function(e) {
                    modalMessage(e.error);
                });           
        };

        var editar = function(colaborador) {
            modalSalvar(colaborador)
                .then(function(result) {
                    result = ajustarDados(result);
                    update(result);
                });
        };

        var update = function(colaborador) {
            ColaboradorService.update(colaborador.idColaborador, colaborador)
                .then(function (data) {  
                    modalMessage("Colaborador " + data.nome + " Alterado com sucesso!");
                    todos();
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** EXCLUIR ***** //

        $scope.excluir = function(colaborador) {
            $q.all([ColaboradorService.getLancamento(colaborador.idColaborador),
                    ColaboradorService.getLancamentoProgramado(colaborador.idColaborador)])
                .then(function(values) {   
                    if(values[0].lancamentos.length || values[1].lancamentosProgramados.length) {
                        modalMessage("Este colaborador não pode ser excluído! <br/> Existem Lançamentos vinculados a esta colaborador.");
                    } else {
                        excluir(colaborador);
                    }
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        }; 
        
        var excluir = function(colaborador) {
            modalExcluir()
                .then(function() {
                    remove(colaborador);
                });
        };
        
        var remove = function(colaborador) {
            ColaboradorService.delete(colaborador.idColaborador)
                .then(function(data) { 
                    modalMessage("Colaborador " + data.nome + " Removido com sucesso!");
                    todos();                        
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** AJUSTAR ***** //
        
        var ajustarDados = function(data) {  
            var colaborador = ColaboradorHandler.handle(data);            
            if(!_.isEmpty(data.endereco)) {
                colaborador.endereco = data.endereco;
            }
            if(!_.isEmpty(data.informacaoProfissional)) {
                colaborador.informacaoProfissional = InformacaoProfissionalHandler.handle(data.informacaoProfissional);
            }            
            if(!_.isEmpty(data.informacaoBancaria)) {
                colaborador.informacaoBancaria = InformacaoBancariaHandler.handle(data.informacaoBancaria);
            }
            return colaborador;
        };

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        var modalVisualizar = function(colaborador) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/colaborador/modalColaboradorVisualizar.html', 'ModalColaboradorVisualizarController', 'md',
                {
                    colaborador: function() {
                        return colaborador;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvar = function(colaborador) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/colaborador/modalColaboradorEditar.html', 'ModalColaboradorEditarController', 'lg',
                {
                    colaborador: function() {
                        return colaborador;
                    }
                });
            return modalInstance.result;
        };
        
        var modalExcluir = function() {
            var modalInstance = ModalService.modalExcluir('Excluir Colaborador?', 'Deseja realmente excluir este Colaborador?');
            return modalInstance.result;
        };
        
        init();

    }]);

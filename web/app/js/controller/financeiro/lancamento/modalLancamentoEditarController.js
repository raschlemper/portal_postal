'use strict';

app.controller('ModalLancamentoEditarController', ['$scope', '$modalInstance', 'conta', 'lancamento', 'tipo', 'anexo', 'ContaService', 'PlanoContaService', 'CentroCustoService', 'LancamentoConciliadoService', 'LancamentoAnexoService', 'ModalService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, conta, lancamento, tipo, anexo, ContaService, PlanoContaService, CentroCustoService, LancamentoConciliadoService, LancamentoAnexoService, ModalService, DatePickerService, ListaService, LISTAS) {

        var init = function () {  
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.situacoes = LISTAS.situacaoLancamento;
            $scope.lancamento = lancamento || {};
            $scope.lancamento.rateios = (lancamento && lancamento.rateios) || [];
            $scope.lancamento.tipo = (lancamento && lancamento.tipo) || tipo;
            $scope.lancamento.situacao = (lancamento && lancamento.situacao) || $scope.situacoes[0];
            $scope.lancamento.modelo = (lancamento && lancamento.modelo) || $scope.modelos[0];
            getTitle();
            contas();
            centroCustos();
            planoContas($scope.lancamento.tipo);  
        };
                
        var initStep = function() {      
//            if(anexo){                
//                $scope.anexar(lancamento);
//            } else {
                if(existRateio(lancamento)) { $scope.ratear(); }
                else { goToEditar(); }
//            }
        }
        
        var existRateio = function(lancamento) {
            return lancamento && lancamento.rateios && lancamento.rateios.length;
        }
        
        $scope.editConta = function() {
            return (lancamento && lancamento.idLancamento);
        }
        
        var getTitle = function() {
            if(lancamento && lancamento.idLancamento) { $scope.title = "Editar Lançamento " + tipo.descricao; }
            else { $scope.title = "Inserir Lançamento " + tipo.descricao; }
        };
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.lancamento.conta = conta || $scope.lancamento.conta || $scope.contas[0];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var planoContas = function(tipo) {
            PlanoContaService.getStructureByTipo(tipo.id)
                .then(function (data) {
                    $scope.planoContas = data;
                    PlanoContaService.estrutura($scope.planoContas);
                    $scope.planoContas = PlanoContaService.flatten($scope.planoContas);   
//                    $scope.planoContas = criarPlanoContasLista($scope.planoContas);
                    if($scope.lancamento.planoConta) {
                        $scope.lancamento.planoConta = ListaService.getPlanoContaValue($scope.planoContas, $scope.lancamento.planoConta.idPlanoConta) || $scope.planoContas[0];
                    } else {
                         $scope.lancamento.planoConta  = $scope.planoContas[0];
                    }      
                    initStep();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var centroCustos = function() {
            CentroCustoService.getStructure()
                .then(function (data) {
                    $scope.centroCustos = data;
                    CentroCustoService.estrutura($scope.centroCustos);
                    $scope.centroCustos = CentroCustoService.flatten($scope.centroCustos);
//                    $scope.centroCustos = criarCentroCustosLista($scope.centroCustos);
                    if($scope.lancamento.centroCusto && $scope.lancamento.centroCusto.idCentroCusto) {
                        $scope.lancamento.centroCusto = ListaService.getCentroCustoValue($scope.centroCustos, $scope.lancamento.centroCusto.idCentroCusto) || $scope.centroCustos[0];
                    } 
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var criarPlanoContasLista = function(data) {
            return _.filter(data, function(planoConta) { 
                return !planoConta.ehGrupo;
            });            
        }
        
        var criarCentroCustosLista = function(data) {
            return _.filter(data, function(centroCusto) { 
                return !centroCusto.ehGrupo;
            });            
        }
        
        $scope.ratear = function() {
            goToRatear();
            $scope.lancamento.rateios = [];
            setRateioDefault();
        };
        
        $scope.cancelarRatear = function() {
            goToEditar();
            $scope.lancamento.rateios = null;
        };
        
        $scope.salvarRateio = function(rateio) {
            var saldo = saldoRateio();
            if(saldo + rateio.valor > $scope.lancamento.valor) {
                modalMessage("A soma dos valores de rateio é superior ao valor do lançamento!");
                return;
            }
            $scope.lancamento.rateios.push(rateio);
            setRateioDefault();
        }
        
        $scope.editarRateio = function(rateio, index) {
            $scope.rateio = rateio;
            $scope.removerRateio(index);
        }
        
        $scope.removerRateio = function(index) {     
            $scope.lancamento.rateios.splice(index, 1); 
            setRateioDefault();   
        }
        
        var setRateioDefault = function() {
            $scope.rateio = {};
            $scope.rateio.planoConta = $scope.planoContas[0];
            $scope.rateio.valor = $scope.lancamento.valor - saldoRateio();
        }
        
        var saldoRateio = function() {            
            var saldo = 0;
            _.map($scope.lancamento.rateios, function(rateio) {
                saldo += rateio.valor;
            });
            return saldo;
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            var lancamento = setData($scope.lancamento, $scope.data);
            var data = moment($scope.lancamento.dataLancamento).format('YYYY-MM-DD HH:mm:ss');
            LancamentoConciliadoService.getByData(data)
                .then(function(data) {  
                    if(data.length) {
                        modalConfirmarConciliado().then(function() {
                            $modalInstance.close(lancamento);
                        });
                    } else { $modalInstance.close(lancamento); }
                })
                .catch(function(e) {
                    modalMessage(e);
                });             
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

//        $scope.anexar = function(lancamento) {
//            $scope.stepFrom = 'editar'; 
//            $scope.stepTo = 'anexar'; 
//            anexos(lancamento.idLancamento);
//        };
        
//        $scope.setAnexo = function(lancamento, anexo) {            
//            LancamentoAnexoService.upload(lancamento.idLancamento, anexo[0])
//                .done(function (data) {
//                    $scope.anexoFile = null;
//                    anexos(lancamento.idLancamento);
//                }).fail(function (e) {
//                    console.log(e);
//                });
//            anexos(lancamento.idLancamento);           
//        };
        
//        $scope.removeAnexo = function(anexo) {            
//            LancamentoAnexoService.delete(anexo.idLancamentoAnexo);      
//            anexos(lancamento.idLancamento);      
//        };
        
//        $scope.visualizarAnexo = function(anexo) {
//            $scope.contentFile = anexo.anexo;            
//        }
        
//        $scope.voltar = function() {
//            $scope.stepFrom = 'anexar'; 
//            $scope.stepTo = 'editar';             
//        }
        
//        var anexos = function(idLancamento) {
//            LancamentoAnexoService.getLancamento(idLancamento)
//                .then(function (data) {
//                    $scope.anexos = data;
//                })
//                .catch(function (e) {
//                    modalMessage(e);
//                });
//        }
        
        var setData = function(lancamento, data) {
            lancamento.dataEmissao = moment();
            lancamento.dataVencimento = lancamento.dataLancamento;
            return lancamento;
        };
        
        var goToEditar = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'editar';             
        }
        
        var goToRatear = function() {
            $scope.stepFrom = angular.copy($scope.stepTo); 
            $scope.stepTo = 'ratear'; 
        }
        
        var goToAnexar = function() {
            $scope.stepFrom = angular.copy($scope.stepTo); 
            $scope.stepTo = 'anexar'; 
            //anexos(lancamento.idLancamento);
        }
        
        var modalConfirmarConciliado = function() {
            var modalInstance = ModalService.modalConfirmar('Alerta Lançamento', 'Você está inserindo um lançamento em um período reconciliado. <br/> Está inclusão irá impactar no lançamento de conciliação! <br/> Deseja continuar?');
            return modalInstance.result;
        };
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };

        var validarForm = function (form) {
            if ($scope.lancamento.rateios && $scope.lancamento.rateios.length) {
                var saldo = saldoRateio();
                if(saldo !== form.valor.$modelValue) {
                    alert("A soma dos valores de rateio é superior ao valor do lançamento!");
                    return false;                    
                }
            }       
            if (form.dataCompetencia.$error.required) {
                alert('Preencha a competência do lançamento!');
                return false;
            }       
            if (form.dataCompetencia.$modelValue && !moment(form.dataCompetencia.$modelValue).isValid()) {
                alert('A competência do lançamento não é válida!');
                return false;
            }    
            if (form.dataLancamento.$error.required) {
                alert('Preencha a data do lançamento!');
                return false;
            }       
            if (form.dataLancamento.$modelValue && !moment(form.dataLancamento.$modelValue).isValid()) {
                alert('A data do lançamento não é válida!');
                return false;
            }    
            if (form.valor.$error.required) {
                alert('Preencha o valor do lançamento!');
                return false;
            }
            if (form.historico.$error.required) {
                alert('Preencha o histórico do lançamento!');
                return false;
            }
            return true;
        }    

        var validarFormAnexo = function (form) {
            if (form.file.$error.required) {
                alert('Selecione o arquivo!');
                return false;
            }   
            return true;
        }; 

        init();

    }]);

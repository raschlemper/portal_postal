'use strict';

app.controller('ModalLancamentoProgramadoTransferirController', 
    ['$scope', '$modalInstance', 'lancamentoProgramadoTransferencia', 'LancamentoProgramadoTransferenciaService', 'ContaService', 'TipoDocumentoService', 
     'TipoFormaPagamentoService', 'FrequenciaLancamentoService', 'ConfiguracaoService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, lancamentoProgramadoTransferencia, LancamentoProgramadoTransferenciaService, ContaService, TipoDocumentoService, 
    TipoFormaPagamentoService, FrequenciaLancamentoService, ConfiguracaoService, LISTAS, MESSAGES) {

        var init = function () { 
            $scope.frequencias = LISTAS.frequencia;
            $scope.lancamentoProgramadoTransferencia = lancamentoProgramadoTransferencia || {};       
            $scope.lancamentoProgramadoTransferencia.tipo = ($scope.lancamentoProgramadoTransferencia && $scope.lancamentoProgramadoTransferencia.tipo) || $scope.tipo;
            $scope.lancamentoProgramadoTransferencia.frequencia = ($scope.lancamentoProgramadoTransferencia && $scope.lancamentoProgramadoTransferencia.frequencia) || $scope.frequencias[0];
            getTitle(lancamentoProgramadoTransferencia);
            contas();
        };
                
        // ***** CONTROLLER ***** //   
        
        var getTitle = function(lancamentoProgramadoTransferencia) {
            if(lancamentoProgramadoTransferencia && lancamentoProgramadoTransferencia.idLancamentoProgramadoTransferencia) { 
                $scope.title = MESSAGES.lancamento.transferir.title.EDITAR; 
            } else { 
                $scope.title = MESSAGES.lancamento.transferir.title.INSERIR; 
            }
        };
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.lancamentoProgramadoTransferencia.contaOrigem = 
                            ($scope.lancamentoProgramadoTransferencia.lancamentoProgramadoOrigem && $scope.lancamentoProgramadoTransferencia.lancamentoProgramadoOrigem.conta) || $scope.contas[0];
                    $scope.lancamentoProgramadoTransferencia.contaDestino = 
                            ($scope.lancamentoProgramadoTransferencia.lancamentoProgramadoDestino && $scope.lancamentoProgramadoTransferencia.lancamentoProgramadoDestino.conta) || $scope.contas[0];
                    tipoDocumento();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var tipoDocumento = function() {
            TipoDocumentoService.getAll()
                .then(function (data) {
                    $scope.documentos = data;
                    $scope.lancamentoProgramadoTransferencia.documento = $scope.lancamentoProgramadoTransferencia.documento || $scope.documentos[1];                    
                    tipoFormaPagamento();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var tipoFormaPagamento = function() { 
            TipoFormaPagamentoService.getAll()
                .then(function (data) {
                    $scope.formaPagamentos = data;
                    $scope.lancamentoProgramadoTransferencia.formaPagamento = $scope.lancamentoProgramadoTransferencia.formaPagamento || $scope.formaPagamentos[1];
//                    favorecidos();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.eventsOrigem = {
            onblur : function() {
                setDataCompetenciaOrigem($scope.lancamentoProgramadoTransferencia);
                //setDataVencimentoDestino($scope.lancamentoProgramadoTransferencia);
                //setDataCompetenciaDestino($scope.lancamentoProgramadoTransferencia);
            }
        };
        
        $scope.eventsDestino = {
            onblur : function() {
                setDataCompetenciaDestino($scope.lancamentoProgramadoTransferencia);
            }
        };
        
        var setDataCompetenciaOrigem = function(lancamentoProgramadoTransferencia){
            if(lancamentoProgramadoTransferencia.dataCompetenciaOrigem) return;
            lancamentoProgramadoTransferencia.dataCompetenciaOrigem = lancamentoProgramadoTransferencia.dataVencimentoOrigem;            
        };
        
        var setDataVencimentoDestino = function(lancamentoProgramadoTransferencia){
            if(lancamentoProgramadoTransferencia.dataVencimentoDestino) return;
            lancamentoProgramadoTransferencia.dataVencimentoDestino = lancamentoProgramadoTransferencia.dataVencimentoOrigem;            
        };
        
        var setDataCompetenciaDestino = function(lancamentoProgramadoTransferencia){
            if(lancamentoProgramadoTransferencia.dataCompetenciaDestino) return;
            lancamentoProgramadoTransferencia.dataCompetenciaDestino = lancamentoProgramadoTransferencia.dataVencimentoOrigem;            
        };
        
        $scope.gerar = function(form, lancamentoProgramadoTransferencia) {
            if(!$scope.validarForm(form)) return;  
            lancamentoProgramadoTransferencia.gerarLancamento = true;
            $modalInstance.close(lancamentoProgramadoTransferencia); 
        };
        
        $scope.ok = function(form, lancamentoProgramadoTransferencia) {
            return validarLancamento(form, lancamentoProgramadoTransferencia);      
        };
        
        var ok = function(lancamentoProgramadoTransferencia) {
            $modalInstance.close(lancamentoProgramadoTransferencia);            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        // ***** AJUSTAR ***** //


        // ***** VALIDAR ***** //
        
        var validarLancamento = function(form, lancamentoProgramadoTransferencia) {
            if (!validarForm(form, lancamentoProgramadoTransferencia)) return;
            ConfiguracaoService.get()
                .then(function(data) {
                    if(!validarLancamentoByConfiguracao(data, lancamentoProgramadoTransferencia)) return;
                    ok(lancamentoProgramadoTransferencia);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        var validarLancamentoByConfiguracao = function (configuracao, lancamentoProgramadoTransferencia) {
            if (configuracao.historico && !lancamentoProgramadoTransferencia.historico) {
                alert(MESSAGES.lancamento.validacao.HISTORICO_REQUERIDA);
                return false;
            }
            return true;
        }; 

        $scope.validarForm = function (form) {
            if(form.contaOrigem.$modelValue === form.contaDestino.$modelValue) {
                alert(MESSAGES.lancamento.transferir.validacao.CONTA_DIFERENTE);
                return false;                
            }
            if (form.dataCompetenciaOrigem.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_COMPETENCIA_ORIGEM_REQUERIDA);
                return false;
            }        
            if (form.dataCompetenciaOrigem.$modelValue && !moment(form.dataCompetenciaOrigem.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_COMPETENCIA_ORIGEM_VALIDA);
                return false;
            }   
//            if (form.dataCompetenciaDestino.$error.required) {
//                alert(MESSAGES.lancamento.transferir.validacao.DATA_COMPETENCIA_DESTINO_REQUERIDA);
//                return false;
//            }        
//            if (form.dataCompetenciaDestino.$modelValue && !moment(form.dataCompetenciaDestino.$modelValue).isValid()) {
//                alert(MESSAGES.lancamento.transferir.validacao.DATA_COMPETENCIA_DESTINO_VALIDA);
//                return false;
//            }   
            if (form.dataVencimentoOrigem.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_VENCIMENTO_ORIGEM_REQUERIDA);
                return false;
            }       
            if (form.dataVencimentoOrigem.$modelValue && !moment(form.dataVencimentoOrigem.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_VENCIMENTO_ORIGEM_VALIDA);
                return false;
            }    
//            if (form.dataVencimentoDestino.$error.required) {
//                alert(MESSAGES.lancamento.transferir.validacao.DATA_VENCIMENTO_DESTINO_REQUERIDA);
//                return false;
//            }       
//            if (form.dataVencimentoDestino.$modelValue && !moment(form.dataVencimentoDestino.$modelValue).isValid()) {
//                alert(MESSAGES.lancamento.transferir.validacao.DATA_VENCIMENTO_DESTINO_VALIDA);
//                return false;
//            }    
            if (form.valor.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.VALOR_REQUERIDA);
                return false;
            }
//            if (form.historico.$error.required) {
//                alert(MESSAGES.lancamento.transferir.validacao.HISTORICO_REQUERIDA);
//                return false;
//            }
            return true;
        };
        
        init();

    }]);

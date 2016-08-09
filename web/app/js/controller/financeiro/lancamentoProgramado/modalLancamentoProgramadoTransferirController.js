'use strict';

app.controller('ModalLancamentoProgramadoTransferirController', 
    ['$scope', '$modalInstance', 'lancamentoProgramadoTransferencia', 'LancamentoProgramadoTransferenciaService', 'ContaService', 'TipoDocumentoService', 
     'TipoFormaPagamentoService', 'FrequenciaLancamentoService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, lancamentoProgramadoTransferencia, LancamentoProgramadoTransferenciaService, ContaService, TipoDocumentoService, 
    TipoFormaPagamentoService, FrequenciaLancamentoService, LISTAS, MESSAGES) {

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
        
        $scope.events = {
            onblur : function() {
                if($scope.lancamentoProgramadoTransferencia.dataCompetencia) return;
                $scope.lancamentoProgramadoTransferencia.dataCompetencia = $scope.lancamentoProgramadoTransferencia.dataVencimento;
            }
        };
        
        $scope.gerar = function(form, lancamentoProgramadoTransferencia) {
            if(!$scope.validarForm(form)) return;  
            lancamentoProgramadoTransferencia.gerarLancamento = true;
            $scope.close(lancamentoProgramadoTransferencia);      
            transferir()
        };
        
        $scope.ok = function(form, lancamentoProgramadoTransferencia) {
            if (!$scope.validarForm(form)) return;
            $modalInstance.close(lancamentoProgramadoTransferencia);            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        // ***** AJUSTAR ***** //


        // ***** VALIDAR ***** //

        $scope.validarForm = function (form) {
            if(form.contaOrigem.$modelValue === form.contaDestino.$modelValue) {
                alert(MESSAGES.lancamento.transferir.validacao.CONTA_DIFERENTE);
                return false;                
            }
            if (form.dataCompetencia.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_COMPETENCIA_REQUERIDA);
                return false;
            }        
            if (form.dataCompetencia.$modelValue && !moment(form.dataCompetencia.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_COMPETENCIA_VALIDA);
                return false;
            }    
            if (form.dataVencimento.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_VENCIMENTO_REQUERIDA);
                return false;
            }       
            if (form.dataVencimento.$modelValue && !moment(form.dataVencimento.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.transferir.validacao.DATA_VENCIMENTO_VALIDA);
                return false;
            }    
            if (form.valor.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.VALOR_REQUERIDA);
                return false;
            }
            if (form.historico.$error.required) {
                alert(MESSAGES.lancamento.transferir.validacao.HISTORICO_REQUERIDA);
                return false;
            }
            return true;
        };
        
        init();

    }]);

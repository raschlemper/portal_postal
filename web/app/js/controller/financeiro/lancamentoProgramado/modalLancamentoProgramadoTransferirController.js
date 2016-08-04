'use strict';

app.controller('ModalLancamentoProgramadoTransferirController', 
    ['$scope', '$modalInstance', 'lancamentoTransferencia', 'ContaService', 'TipoDocumentoService', 'TipoFormaPagamentoService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, lancamentoTransferencia, ContaService, TipoDocumentoService, TipoFormaPagamentoService, LISTAS, MESSAGES) {

        var init = function () { 
            $scope.frequencias = LISTAS.frequencia;
            $scope.lancamentoTransferencia = lancamentoTransferencia || {};       
            $scope.lancamentoTransferencia.tipo = ($scope.lancamentoTransferencia && $scope.lancamentoTransferencia.tipo) || $scope.tipo;
            $scope.lancamentoTransferencia.frequencia = ($scope.lancamentoTransferencia && $scope.lancamentoTransferencia.frequencia) || $scope.frequencias[0];
            getTitle(lancamentoTransferencia);
            contas();
        };
                
        // ***** CONTROLLER ***** //   
        
        var getTitle = function(lancamentoTransferencia) {
            if(lancamentoTransferencia && lancamentoTransferencia.idLancamentoTransferencia) { 
                $scope.title = MESSAGES.lancamento.transferir.title.EDITAR; 
            } else { 
                $scope.title = MESSAGES.lancamento.transferir.title.INSERIR; 
            }
        };
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.lancamentoTransferencia.contaOrigem = $scope.contas[0];
                    $scope.lancamentoTransferencia.contaDestino = $scope.contas[0];
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
                    $scope.lancamentoTransferencia.documento = $scope.lancamentoTransferencia.documento || $scope.documentos[1];                    
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
                    $scope.lancamentoTransferencia.formaPagamento = $scope.lancamentoTransferencia.formaPagamento || $scope.formaPagamentos[1];
//                    favorecidos();
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.events = {
            onblur : function() {
                if($scope.lancamentoTransferencia.dataCompetencia) return;
                $scope.lancamentoTransferencia.dataCompetencia = $scope.lancamentoTransferencia.dataVencimento;
            }
        };
        
        $scope.ok = function(form, lancamentoTransferencia) {
            if (!validarForm(form)) return;
            $modalInstance.close(lancamentoTransferencia);            
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {
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
        }      

        init();

    }]);

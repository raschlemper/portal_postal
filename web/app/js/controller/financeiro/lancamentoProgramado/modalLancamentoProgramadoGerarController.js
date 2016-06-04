'use strict';

app.controller('ModalLancamentoProgramadoGerarController', ['$scope', 'LancamentoConciliadoService', 'FrequenciaLancamentoService', 'ModalService', 'MESSAGES',
    function ($scope, LancamentoConciliadoService, FrequenciaLancamentoService, ModalService, MESSAGES) {

        var init = function () {  
            getTitle();
            $scope.lancamento = $scope.lancamento || $scope.getLancamento($scope.lancamentoProgramado, null, $scope.modelos[2]);
        };
        
        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            $scope.titleGerar = $scope.title + " - " + MESSAGES.lancamento.title.GERAR; 
        };
        
        $scope.lancar = function(form, lancamentoProgramado, lancamento) {
            if(!$scope.validaConta(lancamentoProgramado.conta)) return;
            if(!$scope.validarForm(form, lancamentoProgramado)) return;   
            if(!validarForm(form, lancamento)) return;     
            lancamento = ajustarLancamento(lancamento);  
            var data = moment(lancamento.dataLancamento).format('YYYY-MM-DD HH:mm:ss');  
            LancamentoConciliadoService.getByData(data)
                .then(function(data) {  
                    if(data.length) {
                        modalConfirmarConciliado().then(function() {
                            lancar(lancamentoProgramado, lancamento);
                        });
                    } else { lancar(lancamentoProgramado, lancamento); }
                })
                .catch(function(e) {
                    $scope.modalMessage(e);
                });    
        };
        
        var lancar = function(lancamentoProgramado, lancamento) {  
            delete $scope.lancamentoProgramado.parcelas;
            lancamentoProgramado = ajustarLancamentoProgramado(lancamentoProgramado);
            lancamentoProgramado.gerarLancamento = true;
            lancamentoProgramado.lancamentos = [];
            lancamentoProgramado.lancamentos.push(lancamento);
            encerrarLancamentoProgramado(lancamentoProgramado, lancamento); 
            $scope.close(lancamentoProgramado);           
        };
        
        var encerrarLancamentoProgramado = function(lancamentoProgramado, lancamento) {
            if(lancamentoProgramado.frequencia.codigo === 'unico') { 
                lancamentoProgramado.situacao = $scope.situacoes[2];
            }
            if(lancamento.numeroParcela === lancamentoProgramado.quantidadeParcela) {
                lancamentoProgramado.situacao = $scope.situacoes[2];                
            }            
        };
        
        // ***** AJUSTAR ***** //
        
        var ajustarLancamentoProgramado = function(lancamentoProgramado) { 
            lancamentoProgramado.dataCompetencia = FrequenciaLancamentoService.addData(lancamentoProgramado.frequencia, lancamentoProgramado.dataCompetencia) || moment();
            lancamentoProgramado.dataVencimento = FrequenciaLancamentoService.addData(lancamentoProgramado.frequencia, lancamentoProgramado.dataVencimento) || moment();
            return lancamentoProgramado;
        };
        
        var ajustarLancamento = function(lancamento) { 
            lancamento.conta = { idConta: lancamento.conta.idConta };
            lancamento.planoConta = { idPlanoConta: lancamento.planoConta.idPlanoConta };
            if(lancamento.centroCusto) {
                lancamento.centroCusto = { idCentroCusto: lancamento.centroCusto.idCentroCusto };
            }
            lancamento.tipo = lancamento.tipo.id;
            lancamento.dataCompetencia = lancamento.dataCompetencia || moment();
            lancamento.dataEmissao = lancamento.dataEmissao || moment();
            lancamento.dataVencimento = lancamento.dataVencimento || moment();
            lancamento.dataLancamento = lancamento.dataLancamento || moment();
            lancamento.situacao = lancamento.situacao.id;
            lancamento.modelo = lancamento.modelo.id;
            return lancamento;
        };
        
        // ***** VALIDAR ***** //

        var validarForm = function (form, lancamento) {
            if(!lancamento.rateios || !lancamento.rateios.length) {
                if(!$scope.validarPlanoConta(lancamento.planoConta)) {
                    return false;
                }
                if(!$scope.validarCentroCusto(lancamento.centroCusto)) {
                    return false;
                }
            } 
            if (form.dataCompetencia.$error.required) {
                alert(MESSAGES.lancamento.validacao.DATA_COMPETENCIA_REQUERIDA);
                return false;
            }       
            if (form.dataCompetencia.$modelValue && !moment(form.dataCompetencia.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.validacao.DATA_COMPETENCIA_VALIDA);
                return false;
            }    
            if (form.dataLancamento.$error.required) {
                alert(MESSAGES.lancamento.validacao.DATA_LANCAMENTO_REQUERIDA);
                return false;
            }       
            if (form.dataLancamento.$modelValue && !moment(form.dataLancamento.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.validacao.DATA_LANCAMENTO_VALIDA);
                return false;
            }    
            if (form.valor.$error.required) {
                alert(MESSAGES.lancamento.validacao.VALOR_REQUERIDA);
                return false;
            }
            if (form.historico.$error.required) {
                alert(MESSAGES.lancamento.validacao.HISTORICO_REQUERIDA);
                return false;
            }
            return true;
        }; 
        
        // ***** MODAL ***** //
        
        var modalConfirmarConciliado = function() {
            var modalInstance = ModalService.modalConfirmar(MESSAGES.lancamento.info.ALERT, MESSAGES.lancamento.conciliar.info.CONFIRMAR_INCLUIR);
            return modalInstance.result;
        };

        init();

    }]);

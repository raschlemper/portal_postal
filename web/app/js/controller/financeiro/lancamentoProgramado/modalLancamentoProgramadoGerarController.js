'use strict';

app.controller('ModalLancamentoProgramadoGerarController', ['$scope', 'LancamentoConciliadoService', 'ModalService', 'MESSAGES',
    function ($scope, LancamentoConciliadoService, ModalService, MESSAGES) {

        var init = function () {  
            getTitle();
            $scope.lancamento = $scope.lancamento || $scope.getLancamento($scope.lancamentoProgramado, null, $scope.modelos[2]);
        };
        
        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            $scope.titleGerar = $scope.title + " - " + MESSAGES.lancamento.title.GERAR; 
        };
        
        $scope.goToRatear = function() {
            $scope.stepFrom = angular.copy($scope.stepTo);
            $scope.stepTo = 'ratear';             
        };
        
        $scope.lancar = function(form, lancamentoProgramado, lancamento) {
            if(!$scope.validaConta(lancamentoProgramado.conta)) return;
            if(!$scope.validarForm(form, lancamentoProgramado)) return;     
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
        
        $scope.ratear = function(form, lancamentoProgramado) {
            if(!$scope.validaConta(lancamentoProgramado.conta)) return;
            if(!$scope.validarForm(form, lancamentoProgramado)) return;     
            $scope.goToRatear();
        };
        
        // ***** AJUSTAR ***** //
        
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
        
        // ***** MODAL ***** //
        
        var modalConfirmarConciliado = function() {
            var modalInstance = ModalService.modalConfirmar(MESSAGES.lancamento.info.ALERT, MESSAGES.lancamento.conciliar.info.CONFIRMAR_INCLUIR);
            return modalInstance.result;
        };

        init();

    }]);

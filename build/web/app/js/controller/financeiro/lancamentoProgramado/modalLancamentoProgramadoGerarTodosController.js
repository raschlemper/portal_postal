'use strict';

app.controller('ModalLancamentoProgramadoGerarTodosController', 
    ['$scope', '$modalInstance', 'conta', 'lancamentos', 'ContaService', 'LancamentoProgramadoService', 'DateService', 'LancamentoProgramadoHandler', 'LancamentoHandler', 
        'LancamentoProgramadoParcelaHandler', 'LancamentoProgramadoRateioHandler', 'LancamentoRateioHandler', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, conta, lancamentos, ContaService, LancamentoProgramadoService, DateService, LancamentoProgramadoHandler, LancamentoHandler, 
    LancamentoProgramadoParcelaHandler, LancamentoProgramadoRateioHandler, LancamentoRateioHandler, LISTAS, MESSAGES) {

        var init = function () {  
            $scope.modelos = LISTAS.modeloLancamento;
            getTitle();
            contas();
        };
                
        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            $scope.titleGerar = "Lançamento Programado - " + MESSAGES.lancamento.title.GERAR; 
        };
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.conta = conta || $scope.contas[0];
                    todos(lancamentos);
                })
                .catch(function (e) {
                    console.log(e);
                });
        }; 

        var todos = function(lancamentos) {
            LancamentoProgramadoService.getAllById(getIds(lancamentos))
                .then(function (data) {
                    $scope.lancamentoProgramados = data;
                })
                .catch(function(e) {
                    console.log(e);
                });
        };
        
        $scope.lancar = function(form, conta, dataLancamento) {               
            if(!validarForm(form)) return; 
            var lancamentoProgramados = [];
            $scope.lancamentoProgramados.map(function(lancamentoProgramado) {
                var lancamento = getLancamentoFromTipo(lancamentoProgramado);
                lancamento = ajustarLancamento(lancamento, conta, DateService.date(dataLancamento));                
//                if(validarLancamentoProgramado(form, lancamentoProgramado, lancamento)) {
                    lancamentoProgramado = gerarLancar(lancamentoProgramado, lancamento);
                    lancamentoProgramado = ajustarDados(lancamentoProgramado);
                    lancamentoProgramado = create(lancamentoProgramado);
                    lancamentoProgramados.push(lancamentoProgramado);
//                }
            });
            $modalInstance.close(lancamentoProgramados);   
        };
        
        var getLancamentoFromTipo = function(lancamentoProgramado) {
            var lancamento;
            var parcela = LancamentoProgramadoService.getFirstParcelaAberta(lancamentoProgramado);
            if(!parcela) { lancamento =  getLancamento(lancamentoProgramado, null, $scope.modelos[2]); }
            else { lancamento =  getLancamento(lancamentoProgramado, parcela, $scope.modelos[4]); } 
            return lancamento;
        };
        
        var gerarLancar = function(lancamentoProgramado, lancamento) { 
            lancamentoProgramado.lancamentos = [];
            lancamentoProgramado.lancamentos.push(lancamento);
            return lancamentoProgramado;
        };
        
        var create = function(lancamentoProgramado) { 
            lancamentoProgramado = LancamentoProgramadoService.ajustarLancamentoProgramadoFrequencia(lancamentoProgramado);
            LancamentoProgramadoService.encerrarLancamentoProgramado(lancamentoProgramado, lancamentoProgramado.lancamentos[0]); 
            return lancamentoProgramado;
        };
        
        var getIds = function(lancamentosProgramados) {
            return lancamentosProgramados.map(function(lancamentoProgramado) {
                return lancamentoProgramado.idLancamentoProgramado;
            })
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        // ***** AJUSTAR ***** //
        
        var ajustarDados = function(data) {   
            var lancamentoProgramado = LancamentoProgramadoHandler.handle(data);
            lancamentoProgramado.lancamentos = data.lancamentos;
            lancamentoProgramado.parcelas = LancamentoProgramadoParcelaHandler.handleList(data.parcelas);
            lancamentoProgramado.rateios = LancamentoProgramadoRateioHandler.handleList(data.rateios);
            return lancamentoProgramado;
        } 
                
        var getLancamento = function(lancamentoProgramado, parcela, modelo) {
            var lancamento = LancamentoHandler.handle(lancamentoProgramado);
            lancamento.planoConta = lancamentoProgramado.planoConta;
            lancamento.centroCusto = lancamentoProgramado.centroCusto;
            lancamento.tipo = lancamentoProgramado.tipo;
            lancamento.numero = (parcela && parcela.numero) || lancamentoProgramado.numero;
            lancamento.numeroParcela = (parcela && parcela.numero) || lancamentoProgramado.numeroParcela;
            lancamento.dataCompetencia = (parcela && parcela.dataCompetencia) || lancamentoProgramado.dataCompetencia;
            lancamento.dataVencimento = (parcela && parcela.dataVencimento) || lancamentoProgramado.dataVencimento;
            lancamento.valor = (parcela && parcela.valor) || lancamentoProgramado.valor;
            lancamento.situacao = (lancamentoProgramado && lancamentoProgramado.situacao) || $scope.situacoes[0]; 
            lancamento.favorecido = lancamentoProgramado.favorecido;
            lancamento.modelo = modelo;
//            lancamento.parcelas = lancamentoProgramado.parcelas;
            lancamento.rateios = lancamentoProgramado.rateios;
            return lancamento;
        };
        
        var ajustarLancamento = function(lancamento, conta, dataLancamento) { 
            lancamento.conta = conta;
            lancamento.dataLancamento = dataLancamento;
            lancamento.dataCompetencia = dataLancamento;
            lancamento.dataVencimento = dataLancamento;
            lancamento.historico = '(' + lancamento.modelo.descricao + ') ' + (lancamento.historico || "");
            var lancamentoHandle = LancamentoHandler.handle(lancamento);
            lancamentoHandle.rateios = LancamentoRateioHandler.handleList(lancamento.rateios);
            return lancamentoHandle;
        };
        
        // ***** VALIDAR ***** //
        
        var validarLancamentoProgramado = function(form, lancamentoProgramado, lancamento) {
            if(!$scope.validaConta(lancamentoProgramado.conta)) return false;
            if(!$scope.validarRateio(lancamento.valor, lancamentoProgramado)) return false;  
            if(!$scope.validarForm(form, lancamentoProgramado)) return false;
            return true;
        }; 

        var validarForm = function (form) {
            if (form.dataLancamento.$error.required) {
                alert(MESSAGES.lancamento.validacao.DATA_LANCAMENTO_REQUERIDA);
                return false;
            }       
            if (form.dataLancamento.$modelValue && !DateService.date(form.dataLancamento.$modelValue).isValid()) {
                alert(MESSAGES.lancamento.validacao.DATA_LANCAMENTO_VALIDA);
                return false;
            }    
            if (form.conta.$error.required) {
                alert(MESSAGES.lancamento.validacao.CONTA_REQUERIDA);
                return false;
            }
            return true;
        }; 

        init();

    }]);

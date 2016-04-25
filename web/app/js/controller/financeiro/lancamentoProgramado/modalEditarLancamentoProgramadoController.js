'use strict';

app.controller('ModalEditarLancamentoProgramadoController', ['$scope', '$modalInstance', 'conta', 'lancamentoProgramado', 'ContaService', 'PlanoContaService', 'TipoDocumentoService', 'TipoFormaPagamentoService', 'FrequenciaLancamentoService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, conta, lancamentoProgramado, ContaService, PlanoContaService, TipoDocumentoService, TipoFormaPagamentoService, FrequenciaLancamentoService, DatePickerService, ListaService, LISTAS) {

        var init = function () {  
            $scope.datepickerCompetencia = angular.copy(DatePickerService.default); 
            $scope.datepickerVencimento = angular.copy(DatePickerService.default); 
            $scope.datepickerLancamento = angular.copy(DatePickerService.default); 
            $scope.tipos = LISTAS.lancamento; 
            $scope.frequencias = LISTAS.frequencia;
            $scope.situacoes = LISTAS.situacaoLancamentoProgramado;
            $scope.situacoesLancamento = LISTAS.situacaoLancamento;
            $scope.modelos = LISTAS.modeloLancamento;
            $scope.lancamentoProgramado = lancamentoProgramado || {}; 
            $scope.lancamentoProgramado.tipo = (lancamentoProgramado && lancamentoProgramado.tipo) || $scope.tipos[0];
            $scope.lancamentoProgramado.frequencia = (lancamentoProgramado && lancamentoProgramado.frequencia) || $scope.frequencias[0];
            $scope.lancamentoProgramado.situacao = (lancamentoProgramado && lancamentoProgramado.situacao) || $scope.situacoes[0];
            if(!lancamentoProgramado || (lancamentoProgramado && !lancamentoProgramado.idLancamentoProgramado)) { 
                $scope.lancamentoProgramado.numeroParcela = 1; 
            }
            initStep(); 
            getTitle();
            contas();
            tipoDocumento();
            tipoFormaPagamento();
            $scope.changeTipo($scope.lancamentoProgramado.tipo);
        };
        
        var initStep = function() {
            if(lancamentoProgramado.quantidadeParcela) {
                parcelarLancamento(lancamentoProgramado);
            } else {                
                $scope.stepFrom = null; 
                $scope.stepTo = 'editar'; 
            }
        }
        
        $scope.editConta = function() {
            return (lancamentoProgramado && lancamentoProgramado.idLancamentoProgramado);
        }
        
        var getTitle = function() {
            if(lancamentoProgramado && lancamentoProgramado.idLancamentoProgramado) { $scope.title = "Editar Lançamento Programado"; }
            else { $scope.title = "Inserir Novo Lançamento Programado"; }
        };
        
        var contas = function() {
            ContaService.getAll()
                .then(function (data) {
                    $scope.contas = data;
                    $scope.lancamentoProgramado.conta = conta || $scope.lancamentoProgramado.conta || $scope.contas[0];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.changeTipo = function(tipo) {
            planoContas(tipo);
        };
        
        var planoContas = function(tipo) {
            PlanoContaService.findContaResultadoByTipo(tipo.id)
                .then(function (data) {
                    $scope.planoContas = data;
                    if($scope.lancamentoProgramado.planoConta) {
                        $scope.lancamentoProgramado.planoConta = ListaService.getPlanoContaValue($scope.planoContas, $scope.lancamentoProgramado.planoConta.idPlanoConta) || data[0];
                    } else {
                         $scope.lancamentoProgramado.planoConta  = data[0];
                    }
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var tipoDocumento = function() {
            TipoDocumentoService.getAll()
                .then(function (data) {
                    $scope.documentos = data;
                    $scope.lancamentoProgramado.documento = $scope.lancamentoProgramado.documento || $scope.documentos[1];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var tipoFormaPagamento = function() { 
            TipoFormaPagamentoService.getAll()
                .then(function (data) {
                    $scope.formaPagamentos = data;
                    $scope.lancamentoProgramado.formaPagamento = $scope.lancamentoProgramado.formaPagamento || $scope.formaPagamentos[1];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.getTotal = function(lancamento) {
            if(!lancamento) return;
            var valor = lancamento.valor || 0;
            var valorJuros = lancamento.valorJuros || 0;
            var valorMulta = lancamento.valorMulta || 0;
            var valorDesconto = lancamento.valorDesconto || 0;
            return valor + valorJuros + valorMulta - valorDesconto;
        }
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            delete $scope.lancamentoProgramado.parcelas;
            lancamentoProgramado.gerarLancamento = false;
            $modalInstance.close($scope.lancamentoProgramado);            
        };
        
        $scope.createParcelas = function(quantidade) {            
            $scope.lancamentoProgramado.parcelas = [];
            var frequencia = lancamentoProgramado.frequencia;
            var competencia = lancamentoProgramado.competencia;
            var dataVencimento = lancamentoProgramado.dataVencimento;
            for(var i=0; i<quantidade; i++) {
                var parcela = {
                    numero: lancamentoProgramado.numero + '-' + (i + 1) ,
                    competencia: competencia,
                    dataVencimento: dataVencimento,
                    valor: lancamentoProgramado.valor
                }
                $scope.lancamentoProgramado.parcelas.push(parcela);
                competencia = FrequenciaLancamentoService.addData(frequencia, competencia);
                dataVencimento = FrequenciaLancamentoService.addData(frequencia, dataVencimento);
            }
        }
        
        $scope.lancarProgramado = function(form, lancamentoProgramado) {
            if (!validarForm(form)) return;
            $scope.stepFrom = 'editar'; 
            $scope.stepTo = 'lancar'; 
            $scope.lancamento = getLancamento(lancamentoProgramado, null, $scope.modelos[2]);
        };
        
        $scope.lancarParcela = function(form, lancamentoProgramado, parcela) {
            if (!validarForm(form)) return;
            $scope.stepFrom = 'parcelar'; 
            $scope.stepTo = 'lancar'; 
            $scope.lancamento = getLancamento(lancamentoProgramado, parcela, $scope.modelos[4]);
        };
        
        $scope.lancar = function(form, lancamentoProgramado, lancamento) {
            if (!validarForm(form)) return;
            delete $scope.lancamentoProgramado.parcelas;
            lancamentoProgramado.gerarLancamento = true;
            lancamento = ajusteLancamento(lancamento);
            $scope.lancamentoProgramado.lancamentos = [];
            $scope.lancamentoProgramado.lancamentos.push(lancamento);
            $modalInstance.close(lancamentoProgramado); 
        };
                
        $scope.parcelar = function(form, lancamentoProgramado) {
            if (!validarForm(form)) return;
            parcelarLancamento(lancamentoProgramado);
        };
        
        var parcelarLancamento = function(lancamentoProgramado) {            
            $scope.stepFrom = 'editar'; 
            $scope.stepTo = 'parcelar'; 
            $scope.createParcelas(lancamentoProgramado.quantidadeParcela);
        }
                
        var getLancamento = function(lancamentoProgramado, parcela, modelo) {
            var numeroParcela = lancamentoProgramado.numeroParcela;
            var lancamento = {
                conta: lancamentoProgramado.conta,
                planoConta: lancamentoProgramado.planoConta,
                tipo: lancamentoProgramado.tipo,
                favorecido: lancamentoProgramado.favorecido,
                numero: parcela.numero || lancamentoProgramado.numero + '-' + lancamentoProgramado.numeroParcela,
                numeroParcela: numeroParcela,
                competencia: parcela.competencia || lancamentoProgramado.competencia,
                dataEmissao: lancamentoProgramado.dataEmissao || moment(),
                dataVencimento: parcela.dataVencimento || lancamentoProgramado.dataVencimento,
                dataLancamento: null,
                dataCompensacao: null,
                valor: parcela.valor || lancamentoProgramado.valor,
                valorDesconto: 0,
                valorJuros: 0,
                valorMulta: 0,
                situacao: $scope.situacoesLancamento[0],
                modelo: modelo,
                historico: '(' + modelo.descricao + ') ' + lancamentoProgramado.historico,
                observacao: null
            }  
            return lancamento;
        };
        
        var ajusteLancamento = function(lancamento) {
            lancamento.conta = { idConta: lancamento.conta.idConta };
            lancamento.planoConta = { idPlanoConta: lancamento.planoConta.idPlanoConta };
            lancamento.tipo = lancamento.tipo.id;
            lancamento.competencia = lancamento.competencia || moment();
            lancamento.dataEmissao = lancamento.dataEmissao || moment();
            lancamento.dataVencimento = lancamento.dataVencimento || moment();
            lancamento.dataLancamento = lancamento.dataLancamento || moment();
            lancamento.situacao = lancamento.situacao.id;
            lancamento.modelo = lancamento.modelo.id;
            return lancamento;
        }
        
        $scope.voltar = function() {
            if($scope.stepFrom === 'lancar') {
                $scope.stepFrom = 'editar'; 
                $scope.stepTo = 'lancar';                     
            } else if($scope.stepFrom === 'parcelar') {
                $scope.stepFrom = 'editar'; 
                $scope.stepTo = 'parcelar';             
            } else {
                $scope.stepFrom = null; 
                $scope.stepTo = 'editar';                        
            }
        }
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        var validarForm = function (form) {
            if (form.competencia.$error.required) {
                alert('Preencha a competência do lançamento programado!');
                return false;
            }       
            if (form.competencia.$modelValue && !moment(form.competencia.$modelValue).isValid()) {
                alert('A competência do lançamento programado não é válida!');
                return false;
            } 
            if (form.dataVencimento.$error.required) {
                alert('Preencha a data de vencimento do lançamento programado!');
                return false;
            }       
            if (form.dataVencimento.$modelValue && !moment(form.dataVencimento.$modelValue).isValid()) {
                alert('A data de vencimento do lançamento programado não é válida!');
                return false;
            } 
            if (form.valor.$error.required) {
                alert('Preencha o valor do lançamento programado!');
                return false;
            }
            if (form.historico.$error.required) {
                alert('Preencha o histórico do lançamento programado!');
                return false;
            }
            return true;
        }     

        init();

    }]);

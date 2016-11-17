'use strict';

app.controller('ConfiguracaoController', ['$scope', 'ConfiguracaoService', 'LancamentoService', 'LancamentoProgramadoService', 'ConfiguracaoFinanceiroHandler', 
    'ModalService', 'ListaService', 'LISTAS',
    function ($scope, ConfiguracaoService, LancamentoService, LancamentoProgramadoService, ConfiguracaoFinanceiroHandler, ModalService, ListaService, LISTAS) {
            
        var init = function () {                        
            $scope.lancamentoPeriodos = LancamentoService.periodos;                
            $scope.lancamentoProgramadoPeriodos = LancamentoProgramadoService.periodos;
            $scope.limites = LISTAS.limites;
            $scope.configuracao = {};
            visualizar();
        };

        // ***** VISUALIZAR ***** //

        var visualizar = function() {
            ConfiguracaoService.get()
                .then(function(data) {
                    $scope.configuracao = data;   
                    $scope.configuracao.favorecido = data.favorecido || false; 
                    $scope.configuracao.historico = data.historico || false; 
                    $scope.configuracao.planoConta = data.planoConta || false; 
                    $scope.configuracao.centroCusto = data.centroCusto || false; 
                    $scope.configuracao.periodoLancamento = data.periodoLancamento || $scope.lancamentoPeriodos[1]; 
                            //ListaService.getValue($scope.periodos, data.periodoLancamento) || $scope.periodos[1]; 
                    $scope.configuracao.periodoLancamentoProgramado = data.periodoLancamentoProgramado || $scope.lancamentoProgramadoPeriodos[1]; 
                            //ListaService.getValue($scope.periodos, data.periodoLancamentoProgramado) || $scope.periodos[1]; 
                    $scope.configuracao.limiteLancamento = data.limiteLancamento || $scope.limites[0]; 
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };

        // ***** SALVAR ***** //

        $scope.salvar = function(form, configuracao) {
            if(!validarForm(form)) return;
            configuracao = ajustarDados(configuracao);
            save(configuracao);
        };
        
        var save = function(configuracao) {
            ConfiguracaoService.save(configuracao)
                .then(function(data) { 
                    visualizar();   
                    modalMessage("Configuração Alterada com sucesso!");
                })
                .catch(function(e) {
                    modalMessage(e);
                });            
        };
                
        // ***** AJUSTAR ***** // 
        
        var ajustarDados = function(data) {
            var configuracao = ConfiguracaoFinanceiroHandler.handle(data);
            return configuracao;
        };
        
        // ***** VALIDAR ***** //  

        var validarForm = function (form) {
            if (form.favorecido.$error.required) {
                alert('Selecione o favorecido!');
                return false;
            }
            if (form.historico.$error.required) {
                alert('Selecione o histórico!');
                return false;
            }
            if (form.planoConta.$error.required) {
                alert('Selecione o plano de contas!');
                return false;
            }
            if (form.centroCusto.$error.required) {
                alert('Selecione o centro de custos!');
                return false;
            }
            if (form.periodoLancamento.$error.required) {
                alert('Selecione o período do lançamento!');
                return false;
            }
            if (form.periodoLancamentoProgramado.$error.required) {
                alert('Selecione o período do lançamento programado!');
                return false;
            }
            return true;
        };

        // ***** MODAL ***** //
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
        init();

    }]);

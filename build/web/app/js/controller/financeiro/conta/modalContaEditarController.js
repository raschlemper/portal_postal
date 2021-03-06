'use strict';

app.controller('ModalContaEditarController', 
    ['$scope', '$modalInstance', 'conta', 'ContaCorrenteService', 'CartaoCreditoService', 'ContaCorrenteHandler', 'ContaHandler', 'DatePickerService', 
     'ModalService', 'ListaService', 'LISTAS', 'MESSAGES',
    function ($scope, $modalInstance, conta, ContaCorrenteService, CartaoCreditoService, ContaCorrenteHandler, ContaHandler, DatePickerService, 
    ModalService, ListaService, LISTAS, MESSAGES) {

        var init = function () {  
            $scope.tipos = LISTAS.tipoConta;
            $scope.status = LISTAS.statusConta;
            $scope.datepicker = DatePickerService.default;      
            $scope.conta = conta || {};
            $scope.conta.tipo = (conta && conta.tipo) || $scope.tipos[0];
            $scope.conta.status = (conta && conta.status) || $scope.status[0];
            getTitle();
            contaCorrente();
            cartaoCredito();
        };
                
        // ***** CONTROLLER ***** //  
        
        var getTitle = function() {
            if(conta && conta.idConta) { $scope.title = "Editar Conta"; }
            else { $scope.title = "Inserir Conta"; }
        };
        
        var contaCorrente = function() {
            ContaCorrenteService.getAllCarteiraCobranca()
                .then(function (data) {
                    $scope.contasCorrentes = data;
                    $scope.changeTipo($scope.conta.tipo);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var cartaoCredito = function() {
            CartaoCreditoService.getAll()
                .then(function (data) {
                    $scope.cartoesCredito = data;
                    $scope.changeTipo($scope.conta.tipo);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.changeTipo = function(tipo) {
            if(tipo.codigo === 'dinheiro') return;
            else if(tipo.codigo === 'cartaocredito') { $scope.cartaoCreditoLista = $scope.cartoesCredito; }
            else if(tipo.codigo === 'poupanca') { $scope.contaCorrenteLista = contaCorrenteListaPoupanca(); }
            else if(tipo.codigo === 'cobranca') { $scope.contaCorrenteLista = contaCorrenteListaCobranca(); }
            else { $scope.contaCorrenteLista = $scope.contasCorrentes; }
            setContaCorrente(); 
            setCartaoCredito();
        };
        
        var setContaCorrente = function() {
            if(!$scope.contaCorrenteLista) return;
            if($scope.conta.contaCorrente) { 
                $scope.conta.contaCorrente = ListaService.getContaCorrenteValue($scope.contaCorrenteLista, $scope.conta.contaCorrente.idContaCorrente); 
            } else {
                $scope.conta.contaCorrente = $scope.contaCorrenteLista[0];
            }
        }
        
        var setCartaoCredito = function() {
            if(!$scope.cartaoCreditoLista) return;
            if($scope.conta.cartaoCredito) { 
                $scope.conta.cartaoCredito = ListaService.getContaCorrenteValue($scope.cartaoCreditoLista, $scope.conta.cartaoCredito.idCartaoCredito);
            } else {
                $scope.conta.cartaoCredito = $scope.cartaoCreditoLista[0];
            }
        }
        
        var contaCorrenteListaPoupanca = function() {
            return _.filter($scope.contasCorrentes, function(contaCorrente) {
                return (contaCorrente.poupanca);
            });
        };
        
        var contaCorrenteListaCobranca = function() {
            return _.filter($scope.contasCorrentes, function(contaCorrente) {
                return (contaCorrente.carteiraCobrancas.length);
            });
        };
        
        $scope.openContaCorrente = function() {            
            modalSalvarContaCorrente().then(function(result) {
                result = ajustarDadosContaCorrente(result);
                ContaCorrenteService.save(result)
                    .then(function(data) {  
                        modalMessage("Conta Corrente " + data.nome +  " Inserida com sucesso!");
                        contaCorrente();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        }
        
        $scope.openCartaoCredito = function() {   
            modalSalvarCartaoCredito().then(function(result) {
                result = ajustarDadosCartaoCredito(result);
                CartaoCreditoService.save(result)
                    .then(function(data) {  
                        modalMessage("Cartão de Crédito " + data.nome +  " Inserido com sucesso!");
                        cartaoCredito();
                    })
                    .catch(function(e) {
                        modalMessage(e);
                    });
            });
        };        
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.conta);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        // ***** AJUSTAR ***** //
        
        var ajustarDadosContaCorrente = function(data) {  
            return ContaCorrenteHandler.handle(data);
        };
        
        var ajustarDadosCartaoCredito = function(data) {  
            if(data.contas) {
                var contas = [];
                data.contas.map(function(conta) {
                    contas.push(ContaHandler.handle(conta));
                });
                data.contas = contas;
            }    
            return data;
        };
        
        // ***** VALIDAR ***** // 

        var validarForm = function (form) {
            if (form.nome.$error.required) {
                alert('Preencha o nome da conta!');
                return false;
            }
            if (form.dataAbertura.$error.required) {
                alert('Preencha a data de abertura da conta!');
                return false;
            }
            if (form.dataAbertura.$modelValue && !moment(form.dataAbertura.$modelValue).isValid()) {
                alert('A data de abertura da conta não é válida!');
                return false;
            }  
            if (form.valorSaldoAbertura.$error.required) {
                alert('Preencha o saldo de abertura da conta!');
                return false;
            }    
            if (form.tipo.$viewValue.codigo !== 'dinheiro' 
                    && form.tipo.$viewValue.codigo !== 'cartaocredito' && !form.contaCorrente.$viewValue) {
                alert('Preencha a conta corrente da conta!');
                return false;
            }       
            if (form.tipo.$viewValue.codigo === 'cartaocredito' && !form.cartaoCredito.$viewValue) {
                alert('Preencha o cartão de crédito da conta!');
                return false;
            }       
            return true;
        };
                
        // ***** MODAL ***** //   
        
        var modalSalvarContaCorrente = function(contaCorrente) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/contaCorrente/modalContaCorrenteEditar.html', 'ModalContaCorrenteEditarController', 'lg',
                {
                    contaCorrente: function() {
                        return contaCorrente;
                    }
                });
            return modalInstance.result;
        };
        
        var modalSalvarCartaoCredito = function(cartaoCredito) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/cartaoCredito/modalCartaoCreditoEditar.html', 'ModalCartaoCreditoEditarController', 'lg',
                {
                    cartaoCredito: function() {
                        return cartaoCredito;
                    }
                });
            return modalInstance.result;
        };
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };   

        init();

    }]);

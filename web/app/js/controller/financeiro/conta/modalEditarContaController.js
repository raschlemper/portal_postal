'use strict';

app.controller('ModalEditarContaController', ['$scope', '$modalInstance', 'conta', 'ContaCorrenteService', 'CartaoCreditoService', 'DatePickerService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, conta, ContaCorrenteService, CartaoCreditoService,  DatePickerService, ListaService, LISTAS) {

        var init = function () {  
            $scope.tipos = LISTAS.tipoConta;
            $scope.status = LISTAS.statusConta;
            $scope.datepicker = DatePickerService.default;      
            $scope.conta = {
                idConta: (conta && conta.idConta) || null,
                contaCorrente: (conta && conta.contaCorrente) || null,
                cartaoCredito: (conta && conta.cartaoCredito) || null,
                nome: (conta && conta.nome) || null,
                tipo: (conta && conta.tipo) || $scope.tipos[0],
                status: (conta && conta.status) || $scope.status[0],
                valorLimiteCredito: (conta && conta.valorLimiteCredito) || null,
                dataAbertura: (conta && conta.dataAbertura) || null,
                valorSaldoAbertura: (conta && conta.valorSaldoAbertura) || null
            }; 
            getTitle();
            contaCorrente();
            cartaoCredito();
        };
        
        var getTitle = function() {
            if(conta && conta.idConta) { $scope.title = "Editar Conta"; }
            else { $scope.title = "Inserir Nova Conta"; }
        };
        
        var contaCorrente = function() {
            ContaCorrenteService.getAll()
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
            else if(tipo.codigo === 'poupanca') { $scope.cartaoCreditoLista = contaCorrenteListaPoupanca(); }
            else if(tipo.codigo === 'cobranca') { $scope.cartaoCreditoLista = contaCorrenteListaCobranca(); }
            else { $scope.contaCorrenteLista = $scope.contasCorrentes; }
            if($scope.conta.contaCorrente) { 
                $scope.conta.contaCorrente = ListaService.getContaCorrenteValue($scope.contaCorrenteLista, $scope.conta.contaCorrente.idContaCorrente); 
            }
            if($scope.conta.cartaoCredito) { 
                $scope.conta.cartaoCredito = ListaService.getContaCorrenteValue($scope.cartaoCreditoLista, $scope.conta.cartaoCredito.idCartaoCredito);
            }
        };
        
        var contaCorrenteListaPoupanca = function() {
            _.filter($scope.contasCorrentes, function(contaCorrente) {
                return (contaCorrente.poupanca);
            });
        };
        
        var contaCorrenteListaCobranca = function() {
            _.filter($scope.contasCorrentes, function(contaCorrente) {
                return (contaCorrente.carteira);
            });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.conta);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

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
            return true;
        }     

        init();

    }]);

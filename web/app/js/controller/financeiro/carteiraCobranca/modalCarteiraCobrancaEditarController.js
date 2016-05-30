'use strict';

app.controller('ModalCarteiraCobrancaEditarController', ['$scope', '$modalInstance', 'carteiraCobranca', 'CarteiraCobrancaService', 'ContaCorrenteService', 'CepService', 'ModalService',
    function ($scope, $modalInstance, carteiraCobranca, CarteiraCobrancaService, ContaCorrenteService, CepService, ModalService) {

        var init = function () {  
            $scope.carteiraCobranca = carteiraCobranca || {};
            $scope.carteiraCobranca.aceite = (carteiraCobranca && carteiraCobranca.aceite);
            $scope.carteiraCobranca.baixa = (carteiraCobranca && carteiraCobranca.baixa);
            if(!$scope.carteiraCobranca.aceite) { $scope.carteiraCobranca.aceite = false; }
            if(!$scope.carteiraCobranca.baixa) { $scope.carteiraCobranca.baixa = false; }
            getTitle();
            contaCorrente();
        };
        
        var getTitle = function() {
            if(carteiraCobranca && carteiraCobranca.idCarteiraCobranca) { $scope.title = "Editar Carteira de Cobrança"; }
            else { $scope.title = "Inserir Carteira de Cobrança"; }
        };
        
        var contaCorrente = function() {
            ContaCorrenteService.getAll()
                .then(function (data) {
                    $scope.contasCorrentes = data;
                    $scope.carteiraCobranca.contaCorrente = $scope.carteiraCobranca.contaCorrente || $scope.contasCorrentes[0];
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            CarteiraCobrancaService.getByCarteiraCobranca($scope.carteiraCobranca.contaCorrente.idContaCorrente,
                $scope.carteiraCobranca.codigoBeneficiario, $scope.carteiraCobranca.codigoBeneficiarioDv, $scope.carteiraCobranca.codigoCarteira)
                .then(function(carteiraCobranca) {
                    if(!carteiraCobranca) { $modalInstance.close($scope.carteiraCobranca); }
                    else if(carteiraCobranca.idCarteiraCobranca == $scope.carteiraCobranca.idCarteiraCobranca) { $modalInstance.close($scope.carteiraCobranca); }
                    else { modalMessage("Esta carteira de cobrança já está cadastrada"); } 
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
        
        $scope.pesquisarCep = function(cep) {
            CepService.cep(cep)
                .then(function (data) {
                    $scope.carteiraCobranca.beneficiarioLogradouro = data.logradouro;
                    $scope.carteiraCobranca.beneficiarioBairro = data.bairro;
                    $scope.carteiraCobranca.beneficiarioCidade = data.cidade;
                    $scope.carteiraCobranca.beneficiarioUf = data.estado;
                })
                .catch(function (e) {
                    $scope.carteiraCobranca.beneficiarioLogradouro = '';
                    $scope.carteiraCobranca.beneficiarioBairro = '';
                    $scope.carteiraCobranca.beneficiarioCidade = '';
                    $scope.carteiraCobranca.beneficiarioUf = '';
                });
        };
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };

        var validarForm = function (form) {
            if (form.nome.$error.required) {
                alert('Preencha o nome da carteira da cobrança!');
                return false;
            }
            if (form.codigoBeneficiario.$error.required) {
                alert('Preencha o código do beneficiário da carteira de cobrança!');
                return false;
            }
            if (form.codigoBeneficiarioDv.$error.required) {
                alert('Preencha o dígito verificador (DV) do código do beneficiário da carteira de cobrança!');
                return false;
            }
            if (form.codigoBeneficiarioDv.$error.min || form.codigoBeneficiarioDv.$error.max) {
                alert('Preencha o dígito verificador (DV) do código do beneficiário da carteira de cobrança com no máximo 2 dígitos!');
                return false;
            }
            if (form.codigoCarteira.$error.required) {
                alert('Preencha o código da carteira de cobrança!');
                return false;
            }
            if (form.especieDocumento.$modelValue && form.especieDocumento.$error.maxlength) {
                alert('Preencha o código da espécie de socumento da carteira de cobrança com no máximo 2 dígitos!');
                return false;
            }
            return true;
        }     

        init();

    }]);

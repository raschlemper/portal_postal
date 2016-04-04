'use strict';

app.controller('ModalEditarCarteiraCobrancaController', ['$scope', '$modalInstance', 'carteiraCobranca', 'ContaCorrenteService', 'CepService',
    function ($scope, $modalInstance, carteiraCobranca, ContaCorrenteService, CepService) {

        var init = function () {  
            $scope.carteiraCobranca = {
                idCarteiraCobranca: (carteiraCobranca && carteiraCobranca.idCarteiraCobranca) || null,
                contaCorrente: (carteiraCobranca && carteiraCobranca.contaCorrente) || null,
                nome: (carteiraCobranca && carteiraCobranca.nome) || null,
                codigoBeneficiario: (carteiraCobranca && carteiraCobranca.codigoBeneficiario) || null,
                codigoBeneficiarioDv: (carteiraCobranca && carteiraCobranca.codigoBeneficiarioDv) || null,
                codigoConvenio: (carteiraCobranca && carteiraCobranca.codigoConvenio) || null,
                codigoCarteira: (carteiraCobranca && carteiraCobranca.codigoCarteira) || null,
                aceite: (carteiraCobranca && carteiraCobranca.aceite) || null,
                baixa: (carteiraCobranca && carteiraCobranca.baixa) || null,
                especieDocumento: (carteiraCobranca && carteiraCobranca.especieDocumento) || null,
                localPagamento: (carteiraCobranca && carteiraCobranca.localPagamento) || null,
                instrucao01: (carteiraCobranca && carteiraCobranca.instrucao01) || null,
                instrucao02: (carteiraCobranca && carteiraCobranca.instrucao02) || null,
                instrucao03: (carteiraCobranca && carteiraCobranca.instrucao03) || null,
                instrucao04: (carteiraCobranca && carteiraCobranca.instrucao04) || null,
                instrucao05: (carteiraCobranca && carteiraCobranca.instrucao05) || null,
                beneficiarioNome: (carteiraCobranca && carteiraCobranca.beneficiarioNome) || null,
                beneficiarioDocumento: (carteiraCobranca && carteiraCobranca.beneficiarioDocumento) || null,
                beneficiarioLogradouro: (carteiraCobranca && carteiraCobranca.beneficiarioLogradouro) || null,
                beneficiarioBairro: (carteiraCobranca && carteiraCobranca.beneficiarioBairro) || null,
                beneficiarioCidade: (carteiraCobranca && carteiraCobranca.beneficiarioCidade) || null,
                beneficiarioUf: (carteiraCobranca && carteiraCobranca.beneficiarioUf) || null,
                beneficiarioCep: (carteiraCobranca && carteiraCobranca.beneficiarioCep) || null
            }; 
            getTitle();
            contaCorrente();
        };
        
        var getTitle = function() {
            if(carteiraCobranca && carteiraCobranca.idCarteiraCobranca) { $scope.title = "Editar Carteira de Cobrança"; }
            else { $scope.title = "Inserir Nova Carteira de Cobrança"; }
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
            ContaCorrenteService.getByCarteiraCobranca($scope.carteiraCobranca.contaCorrente.idContaCorrente,
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
        }

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
            if (form.codigoBeneficiarioDv.$error.maxlength) {
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

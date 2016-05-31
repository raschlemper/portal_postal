'use strict';

app.controller('ModalLancamentoAnexarController', ['$scope', '$modalInstance', '$sce', 'lancamento', 'LancamentoAnexoService', 'ModalService', 'MESSAGES',
    function ($scope, $modalInstance, conta, lancamento, LancamentoAnexoService, ModalService, MESSAGES) {

        var init = function () {  
            $scope.lancamento = lancamento;
            $scope.lancamentoAnexo = {};
            getTitle();
            anexos();
        };
                
        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            $scope.title = MESSAGES.lancamento.anexar.title.INSERIR; 
        };
        
        var anexos = function() {
            LancamentoAnexoService.getAll()
                .then(function (data) {
                    $scope.anexos = data;
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
                
        // ***** ANEXAR ***** //
        
        $scope.anexar = function(lancamento) {            
            LancamentoAnexoService.upload(lancamento.idLancamento, $scope.anexo[0]);            
        };
        
        $scope.visualizar = function(anexo) {
            $scope.contentFile = anexo.anexo;            
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            modalSalvar(conta, lancamento);
            $modalInstance.close($scope.lancamentoAnexo);            
        };
                
        // ***** VALIDAR ***** //

        var validarForm = function (form) {
            if (form.file.$error.required) {
                alert(MESSAGES.lancamento.anexar.validacao.ARQUIVO_REQUERIDA);
                return false;
            }   
            return true;
        };
                
        // ***** MODAL ***** //
        
        var modalSalvar = function(conta, lancamento) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalLancamento.html', 'ModalLancamentoEditarController', 'lg',
                {
                    lancamento: function() {
                        return lancamento;
                    },
                    conta: function() {
                        return conta;
                    }
                });
            return modalInstance.result;
        };

        init();

    }]);

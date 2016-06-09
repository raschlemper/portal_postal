'use strict';

app.controller('ModalLancamentoAnexoController', ['$scope', '$modalInstance', '$sce', 'lancamento', 'LancamentoAnexoService', 'ModalService',
    function ($scope, $modalInstance, conta, lancamento, LancamentoAnexoService, ModalService) {

        var init = function () {  
            $scope.lancamento = lancamento;
            $scope.lancamentoAnexo = {};
            $scope.uploader = LancamentoAnexoService.getInstanceFileUpload();
            anexos();
        };
        
        var anexos = function() {
            LancamentoAnexoService.getAll()
                .then(function (data) {
                    $scope.anexos = data;
                })
                .catch(function (e) {
                    modalMessage(e);
                });
        }
        
        $scope.anexar = function(lancamento) {            
            LancamentoAnexoService.upload(lancamento.idLancamento, $scope.anexo[0]);            
        }
        
        $scope.visualizar = function(anexo) {
            $scope.contentFile = anexo.anexo;            
        }
        
        $scope.uploadComplete = function (content) {
            $scope.response = JSON.parse(content);
            console.log($scope.response);
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            modalSalvar(conta, lancamento);
            $modalInstance.close($scope.lancamentoAnexo);            
        };
        
        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };

        var validarForm = function (form) {
            if (form.file.$error.required) {
                alert('Selecione o arquivo!');
                return false;
            }   
            return true;
        };
        
        var modalSalvar = function(conta, lancamento) {
            var modalInstance = ModalService.modalDefault('partials/financeiro/lancamento/modalLancamento.html', 'ModalEditarLancamentoController', 'lg',
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

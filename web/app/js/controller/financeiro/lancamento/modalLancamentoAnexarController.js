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
                
        // ***** ANEXAR ***** //  

//        $scope.anexar = function(form, lancamento) {
//            if(!validaConta(lancamento.conta)) return;
//            if (!validarForm(form)) return;         
//            $scope.stepFrom = 'editar'; 
//            $scope.stepTo = 'anexar'; 
//            anexos(lancamento.idLancamento);
//        };
        
//        $scope.setAnexo = function(lancamento, anexo) {            
//            LancamentoAnexoService.upload(lancamento.idLancamento, anexo[0])
//                .done(function (data) {
//                    $scope.anexoFile = null;
//                    anexos(lancamento.idLancamento);
//                }).fail(function (e) {
//                    console.log(e);
//                });
//            anexos(lancamento.idLancamento);           
//        };
        
//        $scope.removeAnexo = function(anexo) {            
//            LancamentoAnexoService.delete(anexo.idLancamentoAnexo);      
//            anexos(lancamento.idLancamento);      
//        };
        
//        $scope.visualizarAnexo = function(anexo) {
//            $scope.contentFile = anexo.anexo;            
//        }
        
//        $scope.voltar = function() {
//            $scope.stepFrom = 'anexar'; 
//            $scope.stepTo = 'editar';             
//        }
        
//        var anexos = function(idLancamento) {
//            LancamentoAnexoService.getLancamento(idLancamento)
//                .then(function (data) {
//                    $scope.anexos = data;
//                })
//                .catch(function (e) {
//                    modalMessage(e);
//                });
//        }
                
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

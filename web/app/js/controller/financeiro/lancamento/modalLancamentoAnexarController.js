'use strict';

app.controller('ModalLancamentoAnexarController', ['$scope', 'LancamentoAnexoService', 'ModalService', 'MESSAGES',
    function ($scope, LancamentoAnexoService, ModalService, MESSAGES) {
        
        // TODO: Não mostrar o icone de anexo quando for transferência
        //       Colocar o nome acima da imagem do anexo selecionado
        //       Diminuir o tamanho do arquivo ou validar o tamanho para não permitir inserir arquivos muito grande

        var init = function () {  
            $scope.fileURL = "";
            $scope.lancamentoAnexo = {};
            getTitle();
            anexos();
        };
                
        // ***** CONTROLLER ***** //
        
        var getTitle = function() {
            $scope.title = MESSAGES.lancamento.anexar.title.INSERIR; 
        };
        
        var anexos = function() {
            LancamentoAnexoService.getLancamento($scope.lancamento.idLancamento)
                .then(function (data) {
                    $scope.anexos = createAnexos(data);
                })
                .catch(function (e) {
                    console.log(e);
                });
        };
        
        var createAnexos = function(data) {
            return _.map(data, function(anexo) { 
                anexo.type = getType(anexo.nome);
                anexo.icon = getIcon(anexo.nome);
                anexo.modelo = getModelo(anexo.nome);
                return anexo;
            });
        };
                
        // ***** ANEXAR ***** //
        
        $scope.anexar = function(lancamento, anexoFile) {  
            if(!validarForm(anexoFile)) return; 
            if(!validarType(anexoFile[0])) return; 
            LancamentoAnexoService.upload(lancamento.idLancamento, anexoFile[0])
                .done(function (data) {
                    anexoFile = null;
                    modalMessage(MESSAGES.lancamento.anexar.sucesso.INSERIDO_SUCESSO);
                    anexos(lancamento.idLancamento);
                }).fail(function (e) {
                    console.log(e);
                });        
        };
        
        $scope.remover = function(lancamento, anexo) { 
            LancamentoAnexoService.delete(anexo.idLancamentoAnexo)
                .then(function(data) {  
                    modalMessage(MESSAGES.lancamento.anexar.sucesso.REMOVIDO_SUCESSO);
                    anexos(lancamento.idLancamento);    
                })
                .catch(function(e) {
                    modalMessage(e);
                });       
        };
        
//        $scope.visualizar = function(anexo) {          
//            LancamentoAnexoService.getFile(anexo.idLancamentoAnexo)
//                .then(function(data) {
//                    anexo.file = data;
//                    visualizar(anexo);    
//                })
//                .catch(function(e) {
//                    modalMessage(e);
//                });       
//        };
        
        $scope.visualizar = function(anexo) {     
            $scope.file = {
                type: getType(anexo.nome),
                image: anexo.anexo,
                icon: getIcon(anexo.nome)
            };
//            var file = new Blob([anexo.anexo], { type: type });
//            $scope.anexoFile = anexo;  
//            $scope.anexoFile.fileURL = URL.createObjectURL(file);
//            $scope.anexoFile.fileType = type;
        };
        
        $scope.download = function(anexo) {    
            var modelo = getModelo(anexo.nome);
            LancamentoAnexoService.download(anexo.idLancamentoAnexo, modelo);
        };
        
        var getModelo = function(fileName) {
            if(fileName.toLowerCase().indexOf('.png') > -1) return 'image';             
            if(fileName.toLowerCase().indexOf('.jpg') > -1) return 'image';           
            if(fileName.toLowerCase().indexOf('.gif') > -1) return 'image'; 
            if(fileName.toLowerCase().indexOf('.pdf') > -1) return 'pdf';  
            return null;
        };
        
        var getType = function(fileName) {
            if(fileName.toLowerCase().indexOf('.png') > -1) return 'image/png';             
            if(fileName.toLowerCase().indexOf('.jpg') > -1) return 'image/jpg';           
            if(fileName.toLowerCase().indexOf('.gif') > -1) return 'image/gif';
            return null;
        };
        
        var getIcon = function(fileName) {
            if(fileName.toLowerCase().indexOf('.png') > -1) return 'fa-file-image-o';   
            if(fileName.toLowerCase().indexOf('.jpg') > -1) return 'fa-file-image-o';  
            if(fileName.toLowerCase().indexOf('.gif') > -1) return 'fa-file-image-o';  
            if(fileName.toLowerCase().indexOf('.pdf') > -1) return 'fa-file-pdf-o';  
            return 'fa-file-text-o';
        }
        
        $scope.ok = function(form) {
            $scope.close(lancamento);        
        };  
                
        // ***** VALIDAR ***** //

        var validarForm = function(anexo) {
            if (!anexo || !anexo.length) {
                alert(MESSAGES.lancamento.anexar.validacao.ARQUIVO_REQUERIDA);
                return false;
            }   
            return true;
        }; 

        var validarType = function(anexo) {
            if (!getModelo(anexo.name)) {
                alert(MESSAGES.lancamento.anexar.validacao.ARQUIVO_NAO_PERMITIDO);
                return false;
            }   
            return true;
        }; 

//        var validarFormAnexo = function (form) {
//            if (form.file.$error.required) {
//                alert(MESSAGES.lancamento.anexar.validacao.ARQUIVO_REQUERIDA);
//                return false;
//            }   
//            return true;
//        }; 
                
        // ***** MODAL ***** //

        var modalMessage = function(message) {
            ModalService.modalMessage(message);
        };
        
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

'use strict';

app.controller('ModalLancamentoAnexarController', ['$scope', '$sce', 'LancamentoAnexoService', 'PDFViewerService', 'ModalService', 'MESSAGES',
    function ($scope, $sce, LancamentoAnexoService, PDFViewerService, ModalService, MESSAGES) {

        var init = function () {  
            $scope.fileURL = "";
            $scope.lancamentoAnexo = {};
            $scope.viewer = PDFViewerService.Instance("viewer");
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
                anexo.type = getType(anexo.tipo);
                anexo.icon = getIcon(anexo.tipo);
                anexo.modelo = getModelo(anexo.tipo);
                return anexo;
            });
        };
                
        // ***** ANEXAR ***** //
        
        $scope.anexar = function(lancamento, anexoFile) {  
            if(!validarForm(anexoFile)) return; 
            if(!validarType(anexoFile[0])) return; 
            if(!validarSize(anexoFile[0])) return; 
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
        
        $scope.visualizar = function(anexo) {  
            $scope.file = {
                id: anexo.idLancamentoAnexo,
                name: anexo.nome,
                type: getType(anexo.tipo),
                image: anexo.anexo,
                icon: getIcon(anexo.tipo),
                modelo: getModelo(anexo.tipo)
            }; 
        };
        
        $scope.download = function(anexo) {    
            var modelo = getModelo(anexo.tipo);
            LancamentoAnexoService.download(anexo.idLancamentoAnexo, modelo);
        };
        
        $scope.view = function(file) {    
            LancamentoAnexoService.file(file.id, file.modelo);
        };
        
        var getModelo = function(tipo) {
            if(tipo == 'png' || tipo == 'jpg' || tipo == 'jpeg' || tipo == 'gif') return 'image'; 
            if(tipo == 'pdf') return 'pdf';  
            return null;
        };
        
        var getType = function(tipo) {
            if(tipo == 'png' || tipo == 'pdf') return 'image/png';             
            if(tipo == 'jpg' || tipo == 'jpeg') return 'image/jpg';            
            if(tipo == 'gif') return 'image/gif'; 
            return null;
        };
        
        var getIcon = function(tipo) {
            if(tipo == 'png' || tipo == 'jpg' || tipo == 'jpeg' || tipo == 'gif') return 'fa-file-image-o';  
            if(tipo == 'pdf') return 'fa-file-pdf-o';  
            return 'fa-file-text-o';
        };
        
        var getTypeToSave = function(anexo) {
            if(anexo.type == 'image/png') return 'png';    
            if(anexo.type == 'image/jpg' || anexo.type == 'image/jpeg') return 'jpg';   
            if(anexo.type == 'image/gif') return 'gif';   
            if(anexo.type == 'application/pdf') return 'pdf'; 
            return null;
        };
        
        $scope.ok = function() {
            $scope.close();        
        };                 

        $scope.cancel = function () {
          $scope.close();      
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
            var tipo = getTypeToSave(anexo);
            if (!getModelo(tipo)) {
                alert(MESSAGES.lancamento.anexar.validacao.ARQUIVO_NAO_PERMITIDO);
                return false;
            }   
            return true;
        };  

        var validarSize = function(anexo) {
            if (anexo.size > 512000) {
                alert(MESSAGES.lancamento.anexar.validacao.ARQUIVO_ACIMA_TAMANHO_PERMITIDO);
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

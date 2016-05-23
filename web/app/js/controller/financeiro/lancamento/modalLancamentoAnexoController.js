'use strict';

app.controller('ModalLancamentoAnexoController', ['$scope', '$modalInstance', 'LancamentoAnexoService', 'ModalService',
    function ($scope, $modalInstance, LancamentoAnexoService, ModalService) {

        var init = function () {  
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
                    console.log(e);
                });
        }
        
        $scope.anexar = function(form) {
//            var anexo = form.anexo.$modelValue;
            
            console.log($scope.anexo);
            
            LancamentoAnexoService.upload($scope.anexo[0]);
//            _.map(files, function(file) {
//                LancamentoAnexoService.upload(file);
//            });
//            if(!anexos || !anexos.length) { 
//                modaclMessage("Você deve escolher um arquivo para anexar!");
//                return;
//            }
//            var fd = new FormData();
//            fd.append('file', anexos);
//            fd.append('idLancamento', idLancamento)
//            LancamentoAnexoService.save(fd);
            
        }
        
        $scope.uploadComplete = function (content) {
            $scope.response = JSON.parse(content);
            console.log($scope.response);
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.lancamentoConciliado);            
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
        }     

        init();

    }]);

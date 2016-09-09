'use strict';

app.factory('LancamentoAnexoService', function($http, PromiseService, FileUploader, DownloadService) {

    return {
        
        getInstanceFileUpload: function() {
            var uploader = new FileUploader();
            uploader.url = _contextPath + "/api/financeiro/lancamento/anexo/upload";
            uploader.method = 'POST';
            uploader.headers = { 'Content-Type': 'multipart/form-data'};
            return uploader;
        },

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/anexo/"));
        },
        
        get: function(idLancamentoAnexo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/anexo/" + idLancamentoAnexo));
        },
        
        getFile: function(idLancamentoAnexo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/anexo/" + idLancamentoAnexo + "/file"));
        },
        
        getLancamento: function(idLancamento) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/anexo/lancamento/" + idLancamento));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/lancamento/anexo/", data));
        },

        update: function(idLancamentoAnexo, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/lancamento/anexo/" + idLancamentoAnexo, data));
        },

        delete: function(idLancamentoAnexo) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/lancamento/anexo/" + idLancamentoAnexo));
        },
        
        download: function(idLancamentoAnexo, type) {
            var url = _contextPath + "/api/financeiro/lancamento/anexo/" + idLancamentoAnexo + "/download/" + type;
            DownloadService.execute(url);    
        },
        
        view: function(idLancamentoAnexo, type) {
            var url = _contextPath + "/api/financeiro/lancamento/anexo/" + idLancamentoAnexo + "/view/" + type;
            DownloadService.execute(url);    
        },
        
        file: function(idLancamentoAnexo, type) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/lancamento/anexo/" + idLancamentoAnexo + "/file/" + type));
        },
        
        upload: function(idLancamento, anexo) {
            var fd = new FormData();
            fd.append('file', anexo);            
            return jQuery.ajax({
                url: _contextPath + "/api/financeiro/lancamento/" + idLancamento + "/anexo",
                data: fd,
                cache: false,
                contentType: false,
                processData: false,
                type: 'POST'                                       
            });
//            return PromiseService.execute(
//                    $http.post(_contextPath + "/api/financeiro/lancamento/anexo/upload", fd, {
//                        headers: {
//                            'Content-Type': 'multipart/form-data'
//                        }
//                    }));
//            file.upload();

        }

    }

});
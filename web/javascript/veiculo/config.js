var Configuracao = function() {    
    
    var loadingModal = function() {        
        $( document ).ajaxStart(function() {
            waitMsg();
        }).ajaxStop(function() {
            $('.my-modal').modal('hide'); 
            var isEditOpen = $('.my-modal-edit').modal().is(':visible');
            if(!isEditOpen) telaMsg();
        });
    };  
    
    var getContextPath = function() {
        var location = window.location;
        var paths = location.pathname.split("/");
        return "/" + paths[1];
    };
    
    var getContextPathActual = function() {
        var pathname = window.location.pathname;
        var paths = pathname.split("/");
        pathname = pathname.replace(paths[0], "");
        pathname = pathname.replace(paths[paths.length - 1], "");
        return pathname.slice(0,-1);
    };
    
    return {
        loadingModal: loadingModal,
        contextPath: getContextPath(),
        getContextPathActual: getContextPathActual()
    }
}();


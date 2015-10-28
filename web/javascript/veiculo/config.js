var Configuracao = function() {    
    
    var messageModal = function() {
        telaMsg();        
    }
    
    var loadingModal = function() {        
        $( document ).ajaxStart(function() {
            waitMsg();
        }).ajaxStop(function() {
            $('.my-modal').modal('hide'); 
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
        messageModal: messageModal,
        loadingModal: loadingModal,
        contextPath: getContextPath(),
        getContextPathActual: getContextPathActual()
    }
}();


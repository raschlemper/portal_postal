var Configuracao = function() {
    
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
        contextPath: getContextPath(),
        getContextPathActual: getContextPathActual()
    }
}();


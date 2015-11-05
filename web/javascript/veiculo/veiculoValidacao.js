var VeiculoValidacao = function() {
    var app = {};
    
    app.campoNotNull = function(value, msg) {        
        if(value) return true;
        alert(msg);
        return false;
    }
    
    app.campoData = function(value, msg) {
        if(value) {
            var data = VeiculoFormatador.toDate(value);
            if(!isNaN(data.getDate())) return true;
            alert(msg);
            return false; 
        }
    }; 
    
    app.campoMenorIgualQue = function(value, comparator, msg) {
        var valueInt = parseInt(value.replace("\.", ""));
        var comparatorInt = parseInt(comparator.replace("\.", ""));
        if(valueInt <= comparatorInt) {
            alert(msg);
            return false;
        }
        return true;
    }; 
    
    return app;
}();
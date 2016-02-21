var VeiculoValidacao = function() {
    var app = {};
    
    app.campoNotNull = function(value, msg) {        
        if(value) return true;
        alert(msg);
        return false;
    };
    
    app.campoData = function(value, msg) {
        if(value) {
            try {
                VeiculoFormatador.toDate(value);                
            } catch (e) {
                alert(msg);
                return false; 
            }
        }
        return true;
    }; 
    
    app.campoBetween = function(value, comparatorInitial, comparatorFinal, msg) {
        if(value) {
            if(app.campoLessEqualThen(value, comparatorInitial, msg) || app.campoMoreEqualThen(value, comparatorFinal, msg)) {
                alert(msg);
                return false;                 
            }
        }
        return true;
    }; 
    
    app.campoLessEqualThen = function(value, comparator, msg) {
        var valueInt = parseInt(value.toString().replace("\.", ""));
        var comparatorInt = parseInt(comparator.toString().replace("\.", ""));
        if(valueInt >= comparatorInt) {
            return false;
        }
        return true;
    }; 
    
    app.campoMoreEqualThen = function(value, comparator, msg) {
        var valueInt = parseInt(value.toString().replace("\.", ""));
        var comparatorInt = parseInt(comparator.toString().replace("\.", ""));
        if(valueInt <= comparatorInt) {
            return false;
        }
        return true;
    }; 
    
    return app;
}();
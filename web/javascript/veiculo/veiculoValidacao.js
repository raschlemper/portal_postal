var VeiculoValidacao = function() {
    var app = {};
    
    app.campoNotNull = function(value, msg) {        
        if(value) return true;
        alert(msg);
        return false;
    };
    
    app.campoData = function(value, msg) {
        if(value) {
            var data = VeiculoFormatador.toDate(value);
            if(!isNaN(data.getDate())) return true;
            alert(msg);
            return false; 
        }
    }; 
    
    app.campoBetween = function(value, comparatorInitial, comparatorFinal, msg) {
        if(value) {
            if(app.campoLessEqualThen(value, comparatorInitial) || app.campoMoreEqualThen(value, comparatorFinal)) {
                return false;                 
            }
        }
        return true;
    }; 
    
    app.campoLessEqualThen = function(value, comparator, msg) {
        var valueInt = parseInt(value.replace("\.", ""));
        var comparatorInt = parseInt(comparator.replace("\.", ""));
        if(valueInt >= comparatorInt) {
            alert(msg);
            return false;
        }
        return true;
    }; 
    
    app.campoMoreEqualThen = function(value, comparator, msg) {
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
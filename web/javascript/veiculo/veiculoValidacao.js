var VeiculoValidacao = function() {
    var app = {};
    
    app.validarCampoQuantidade = function(form) {
        var msg = 'Preencha a quantidade de litros abastecidos!';
        return campoNotNull(form.quantidade.value, msg);
    }; 
    
    app.validarCampoValor = function(form) {
        var msg = 'Preencha o valor total do abastecimento!';
        return campoNotNull(form.valorTotal.value, msg);
    };
    
    app.validarCampoData = function(form) {
        var msg = 'Preencha a data de abastecimento!';
        var msgValida = 'A data do abstecimento não é válida!';
        if(!campoNotNull(form.data.value, msg)) { return false; };
        return campoData(form.data.value, msgValida);
    }; 
    
    app.validarCampoQuilometragemFinal = function(form) {
        var msg = 'A quilometragem não pode ser inferior a última quilometragem inserida para este veículo!'
        return campoMenorQue(form.quilometragemInicial.value, form.quilometragemFinal.value, msg);
    }; 
    
    var campoNotNull = function(value, msg) {        
        if(value) return true;
        alert(msg);
        return false;
    }
    
    var campoData = function(value, msg) {
        if(value) {
            var data = toDate(value);
            if(!isNaN(data.getDate())) return true;
            alert(msg);
            return false; 
        }
    }; 
    
    var campoMenorQue = function(value, comparator, msg) {
        var valueInt = parseInt(value);
        var comparatorInt = parseInt(comparator);
        if(valueInt < comparatorInt) {
            alert(msg);
            return false;
        }
        return true;
    }; 
       
    var toDate = function(data) {
        var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
        return new Date(data.replace(pattern,'$3-$2-$1'));
    }
    
    return app;
}();
var VeiculoService = function() {
    var app = {};
    
    app.marcaVeiculo = function(tipo) {
        return $.ajax({
            method: "GET",
            url: "http://fipeapi.appspot.com/api/1/" + tipo + "/marcas.json",
            data: { },
            dataType: 'jsonp'
        });
    };
    
    app.modeloVeiculo = function(tipo, marca) {
        return $.ajax({
            method: "GET",
            url: "http://fipeapi.appspot.com/api/1/" + tipo + "/veiculos/" + marca + ".json",
            data: { },
            dataType: 'jsonp'
        });
    };
    
    app.pesquisarTodosVeiculos = function() {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo?action=all",
            data: { },
            dataType: 'json'
        });
    };
    
    app.pesquisarVeiculo = function(idVeiculo) {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo?action=get",
            data: { idVeiculo: idVeiculo },
            dataType: 'json'
        });
    }; 
    
    app.editarVeiculo = function(idVeiculo) {
        return $.ajax({
            method: "POST",
            url: Configuracao.getContextPathActual + "/veiculo_editar_dialog.jsp",
            data: {idVeiculo: idVeiculo},
            dataType: 'html'
        });
    };
    
    app.pesquisarTodosVeiculosManutencao = function() {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/manutencao?action=all",
            data: { },
            dataType: 'json'
        });
    };    

    app.pesquisarVeiculoManutencao = function(idVeiculoManutencao) {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/manutencao?action=get",
            data: { idVeiculoManutencao: idVeiculoManutencao },
            dataType: 'json'
        });
    }; 

    app.editarVeiculoManutencao = function(idVeiculoManutencao) {
        return $.ajax({
            method: "POST",
            url: Configuracao.getContextPathActual + "/veiculo_manutencao_editar_dialog.jsp",
            data: {idVeiculoManutencao: idVeiculoManutencao},
            dataType: 'html'
        });
    };
    
    app.pesquisarTodosVeiculosCombustivel = function() {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/combustivel?action=all",
            data: { },
            dataType: 'json'
        });
    };    

    app.pesquisarVeiculoCombustivel = function(idVeiculoCombustivel) {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/combustivel?action=get",
            data: { idVeiculoCombustivel: idVeiculoCombustivel },
            dataType: 'json'
        });
    };     

    app.pesquisarUltimoVeiculoCombustivel = function(idVeiculo) {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/combustivel?action=last",
            data: { veiculo: idVeiculo },
            dataType: 'json'
        });
    }; 

    app.editarVeiculoCombustivel = function(idVeiculoCombustivel) {
        return $.ajax({
            method: "POST",
            url: Configuracao.getContextPathActual + "/veiculo_combustivel_editar_dialog.jsp",
            data: {idVeiculoCombustivel: idVeiculoCombustivel},
            dataType: 'html'
        });
    };
    
    return app;
    
}();
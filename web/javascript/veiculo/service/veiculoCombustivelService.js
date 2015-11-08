var VeiculoCombustivelService = function() {
    var app = {};
    
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
var VeiculoMultaService = function() {
    var app = {};
    
    app.pesquisarTodosVeiculosMulta = function() {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/multa?action=all",
            data: { },
            dataType: 'json'
        });
    };    

    app.pesquisarVeiculoMulta = function(idVeiculoMulta) {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/multa?action=get",
            data: { idVeiculoMulta: idVeiculoMulta },
            dataType: 'json'
        });
    };     

    app.pesquisarUltimoVeiculoMulta = function(idVeiculo) {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/multa?action=last",
            data: { veiculo: idVeiculo },
            dataType: 'json'
        });
    }; 

    app.editarVeiculoMulta = function(idVeiculoMulta) {
        return $.ajax({
            method: "POST",
            url: Configuracao.getContextPathActual + "/veiculo_multa_editar_dialog.jsp",
            data: {idVeiculoMulta: idVeiculoMulta},
            dataType: 'html'
        });
    };
    
    return app;
    
}();
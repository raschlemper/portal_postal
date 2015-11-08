var VeiculoManutencaoService = function() {
    var app = {};
    
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
    
    return app;
    
}();
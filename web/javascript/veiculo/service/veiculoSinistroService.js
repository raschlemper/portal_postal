var VeiculoSinistroService = function() {
    var app = {};
    
    app.pesquisarTodosVeiculosSinistro = function() {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/sinistro?action=all",
            data: { },
            dataType: 'json'
        });
    };    

    app.pesquisarVeiculoSinistro = function(idVeiculoSinistro) {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/sinistro?action=get",
            data: { idVeiculoSinistro: idVeiculoSinistro },
            dataType: 'json'
        });
    };     

    app.pesquisarUltimoVeiculoSinistro = function(idVeiculo) {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/sinistro?action=last",
            data: { veiculo: idVeiculo },
            dataType: 'json'
        });
    }; 

    app.editarVeiculoSinistro = function(idVeiculoSinistro) {
        return $.ajax({
            method: "POST",
            url: Configuracao.getContextPathActual + "/veiculo_sinistro_editar_dialog.jsp",
            data: {idVeiculoSinistro: idVeiculoSinistro},
            dataType: 'html'
        });
    };
    
    return app;
    
}();
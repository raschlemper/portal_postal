var VeiculoSeguroService = function() {
    var app = {};
    
    app.pesquisarTodosVeiculosSeguro = function() {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/seguro?action=all",
            data: { },
            dataType: 'json'
        });
    };    

    app.pesquisarVeiculoSeguro = function(idVeiculoSeguro) {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/seguro?action=get",
            data: { idVeiculoSeguro: idVeiculoSeguro },
            dataType: 'json'
        });
    };     

    app.pesquisarUltimoVeiculoSeguro = function(idVeiculo) {
        return $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/seguro?action=last",
            data: { veiculo: idVeiculo },
            dataType: 'json'
        });
    }; 

    app.editarVeiculoSeguro = function(idVeiculoSeguro) {
        return $.ajax({
            method: "POST",
            url: Configuracao.getContextPathActual + "/veiculo_seguro_editar_dialog.jsp",
            data: {idVeiculoSeguro: idVeiculoSeguro},
            dataType: 'html'
        });
    };
    
    return app;
    
}();
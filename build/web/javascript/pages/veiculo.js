var Veiculo = function(form) {
                                
    var tipos = [{'key': 'motos', 'value': 'Moto'},
                 {'key': 'carros', 'value': 'Carro'},
                 {'key': 'caminhoes', 'value': 'Caminhão'}];

    var combustiveis = [{'key': 'gasolina', 'value': 'Gasolina'},  
                        {'key': 'etanol', 'value': 'Etanol'},
                        {'key': 'disel', 'value': 'Disel'},
                        {'key': 'gas', 'value': 'Gás Natural Veicular'},
                        {'key': 'flex', 'value': 'Gasolina/Álcool'}];

    var status = [{'key': 'novo', 'value': 'Novo'},  
                   {'key': 'seminovo', 'value': 'Seminovo'},  
                   {'key': 'usado', 'value': 'Usado'}];

    var situacao = [{'key': 'ativo', 'value': 'Ativo'},  
                    {'key': 'inativo', 'value': 'Inativo'},  
                    {'key': 'manutencao', 'value': 'Manutenção'}];    

    var pesquisarTodos = function(tipo) {
        $.ajax({
            method: "GET",
            url: "/Portal_Postal_Web/veiculo?action=all",
            data: { },
            dataType: 'json',
        }).done(function(data) {
            addVeiculos(data);
        });
    };  

    function editar(idUsuario) {
        $.ajax({
            method: "POST",
            url: "ajax/veiculo_editar_dialog.jsp",
            data: {idUsuario: idUsuario},
            dataType: 'html'
        }).done(function(retorno) {
            editarModal(retorno);
        });
    }

    var editarModal = function(retorno) {
        bootbox.dialog({
            title: "Editar Veículo",
            message: retorno,
            animate: true,
            onEscape: true,
            className: "modal-lgWidth",
            callback: init(),
            buttons: {
                Cancelar: {
                    label:"<i class='fa fa-lg fa-times fa-spc'></i> CANCELAR",
                    className: "btn btn-default",
                    callback: function() {
                        bootbox.hideAll();
                    }
                },
                success: {
                    label: "<i class='fa fa-lg fa-save fa-spc'></i> SALVAR",
                    className: "btn btn-success",
                    callback: function() {                                    
                        veiculo.acoes.salvar(document.veiculoEditForm);
                    }
                }
            }
        });
        return false;
    }

    var addVeiculos = function(veiculos) {                      
        var html = '<tr>' + 
                      '<td>{{modelo}}</td>' +
                      '<td class="placa">{{placa}}</td>' +
                      '<td>{{anoFabricacao}}/{{anoModelo}}</td>' +
                      '<td class="renavam">{{renavam}}</td>' +
                      '<td class="number">{{quilometragem}}</td>' +
                      '<td>{{situacao}}</td>' +
                      '<td align="center">' +
                            '<button class="btn btn-sm btn-warning" onclick="veiculo.acoes.editar({{id}})">' +
                                '<i class="fa fa-lg fa-pencil"></i>' +
                            '</button>' +
                      '</td>' +
                      '<td align="center">' +
                            '<a class="btn btn-sm btn-danger" href="${pageContext.request.contextPath}/veiculo?action=delete&id={{id}}">' +
                                '<i class="fa fa-lg fa-trash"></i>' +
                            '</a>' +
                      '</td>' +
                   '</tr>';
        var template = _.template(html);
        _.map(veiculos, function(veiculo) {
            $('#datatable-veiculos tbody').append(template(veiculo));                        
        });
    }

    var setTipoVeiculo = function(value) {
        addTipos(value);                            
    };

    var addTipos = function(value) {
        var select = form.tipo;
        removeOptions(select);
        _.map(tipos, function(tipo) {
            var option = document.createElement("option");
            option.text = tipo.value;
            option.value = tipo.key;
            select.add(option);
        });
        select.options[value].selected = true;
        setMarcaVeiculo(select.options[value].value);
        console.log(select.options[value].value);
    };  

    var setMarcaVeiculo = function(tipo) {
        $.ajax({
            method: "GET",
            url: "http://fipeapi.appspot.com/api/1/" + tipo + "/marcas.json",
            data: { },
            dataType: 'jsonp',
        }).done(function(data) {
            addMarcas(tipo, data);
        });
    };

    var addMarcas = function(tipo, marcas) {
        var select = form.marca;
        removeOptions(select);
        _.map(marcas, function(marca) {
            var option = document.createElement("option");
            option.text = marca.name;
            option.value = JSON.stringify(marca);
            select.add(option);
        });
        select.options[0].selected = true;
        var marca = JSON.parse(select.options[0].value);
        setModeloVeiculo(tipo, marca.id);
    };

    var setModeloVeiculo = function(tipo, marca) {
        $.ajax({
            method: "GET",
            url: "http://fipeapi.appspot.com/api/1/" + tipo + "/veiculos/" + marca + ".json",
            data: { },
            dataType: 'jsonp',
        }).done(function(data) {
            addModelos(data);  
        });
    };

    var addModelos = function(modelos) {
        var select = form.modelo;
        removeOptions(select);
        _.map(modelos, function(modelo) {
            var option = document.createElement("option");
            option.text = modelo.name;
            option.value = JSON.stringify(modelo);
            select.add(option);
        });
        select.options[0].selected = true;
    };

    var setCombustivel = function() {
        addCombustiveis();                            
    };

    var addCombustiveis = function() {
        var select = form.combustivel;
        removeOptions(select);
        _.map(combustiveis, function(combustivel) {
            var option = document.createElement("option");
            option.text = combustivel.value;
            option.value = combustivel.key;
            select.add(option);
        });
    };  

    var setEstado = function() {
        addEstados();                            
    };

    var addEstados = function() {
        var select = form.status;
        removeOptions(select);
        _.map(status, function(item) {
            var option = document.createElement("option");
            option.text = item.value;
            option.value = item.key;
            select.add(option);
        });
    };   

    var setSituacao = function() {
        addSituacao();                            
    };

    var addSituacao = function() {
        var select = form.situacao;
        removeOptions(select);
        _.map(situacao, function(item) {
            var option = document.createElement("option");
            option.text = item.value;
            option.value = item.key;
            select.add(option);
        });
    };  
    
    var removeOptions = function(select) {
        while(select.options.length > 0) { select.remove(select.options.length - 1); }        
    }

    var addMascaraPlaca = function() {
        $(".placa").mask('SSS-DDDD', {translation: {'S': {pattern: /[A-Za-z]/},
                                                    'D': {pattern: /[0-9]/}}});
        $(".placa").keyup(function() { this.value = this.value.toUpperCase(); });
    };

    var addMascaraAno = function() {
        $(".ano").mask('DDDD', {translation: {'D': {pattern: /[0-9]/}}});
    };

    var addMascaraChassis = function() {
        $(".chassis").mask('XX.XX.XXXXX.X.X.XXXXXX', {translation: {'X': {pattern: /[A-Za-z0-9]/}}, reverse: true});
        $(".chassis").keyup(function() { this.value = this.value.toUpperCase(); });
    };

    var addMascaraRenavam = function() {
        $(".renavam").mask('DDDDDDDDDD-D', {translation: {'D': {pattern: /[0-9]/}}, reverse: true});
    };

    var addMascaraNumber = function() {
        $(".number").mask('DDD.DDD', {translation: {'D': {pattern: /[0-9]/}}, reverse: true});
    };

    var addTipoEventListener = function() {   
        $( ".tipo" ).change(function() {
            setMarcaVeiculo(this.value);
        });
    };

    var addMarcaEventListener = function() {  
        $( ".marca" ).change(function() {
            var tipo = $(".tipo").value;
            var marca = JSON.parse(this.value);
            setModeloVeiculo(tipo, marca.id);
        });
    };

    var addSalvarEventListener = function() {  
        $( "#salvar" ).click(function() {
            return salvar(this.form);
        });
    };

    // Preencher Campos
    var salvar = function(form) {
        if(!form) return;
        if(!validarCampoPlaca(form)) return false;
        if(!validarCampoAnoFabricacao(form)) return false;
        if(!validarCampoAnoModelo(form)) return false;
        if(!validarCampoRenavam(form)) return false;
        if(!validarCampoQuilometragem(form)) return false;
        form.submit();
    };  

    var validarCampoPlaca = function(form) {
        if(form.placa.value) return true;
        alert('Preencha a placa do veículo!');
        return false;
    };  

    var validarCampoAnoFabricacao = function(form) {
        if(!form.anoFabricacao.value) return true;
        var anoCorrente = (new Date).getFullYear() + 1;
        if(form.anoFabricacao.value >= 1970 && form.anoFabricacao.value <= anoCorrente) return true;
        alert('Preencha o ano de fabricação do veículo com valores entre 1970 e ' + anoCorrente + '!');
        return false;
    };  

    var validarCampoAnoModelo = function(form) {
        if(!form.anoModelo.value) return true;
        var anoCorrente = (new Date).getFullYear() + 1;
        if(form.anoModelo.value >= 1970 && form.anoModelo.value <= anoCorrente) return true;
        alert('Preencha o ano do modelo do veículo com valores entre 1970 e ' + anoCorrente + '!');
        return false;
    };

    var validarCampoRenavam = function(form) {
        if(form.renavam.value) return true;
        alert('Preencha o renavam do veículo!');
        return false;
    };    

    var validarCampoQuilometragem = function(form) {
        if(form.quilometragem.value) return true;
        alert('Preencha a quilometragem do veículo!');
        return false;
    };  

    var loading = function() {
        $( document ).ajaxStart(function() {
            waitMsg();
        }).ajaxStop(function() {
            fechaMsg();       
        });
    }

    return {
        listas: {
            tipos: tipos,
            combustiveis: combustiveis,
            status: status,
            situacao: situacao
        },
        acoes: {
            pesquisarTodos: pesquisarTodos,
            editar: editar,                       
            salvar: salvar
        },
        funcoes: {
            loading: loading,
            setTipoVeiculo: setTipoVeiculo,
            setMarcaVeiculo: setMarcaVeiculo,
            setModeloVeiculo: setModeloVeiculo,
            setCombustivel: setCombustivel,
            setEstado: setEstado,
            setSituacao: setSituacao
        },
        mascara: {
            addMascaraPlaca: addMascaraPlaca,
            addMascaraAno: addMascaraAno,
            addMascaraChassis: addMascaraChassis,
            addMascaraRenavam: addMascaraRenavam,
            addMascaraNumber: addMascaraNumber
        },
        eventos: {
            addTipoEventListener: addTipoEventListener,
            addMarcaEventListener: addMarcaEventListener,
            addSalvarEventListener: addSalvarEventListener
        }
    }
};
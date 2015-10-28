var VeiculoController = function(form) {
                                
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
               
    var init = function(veiculo) {  
        Configuracao.loadingModal();
        addListas(veiculo);   
        addValueForm(veiculo);   
        addMascaras();
        addEventos(veiculo);
    }
    
    var addListas = function(veiculo) {  
        setTipoVeiculo(veiculo);
        setCombustivel(veiculo);
        setEstado(veiculo);
        setSituacao(veiculo);              
    }
    
    var addValueForm = function(veiculo) {  
        if(!veiculo) return;
        form.placa.value = veiculo.placa;
        form.anoFabricacao.value = (veiculo.anoFabricacao > 0 ? veiculo.anoFabricacao : null);
        form.anoModelo.value = (veiculo.anoModelo > 0 ? veiculo.anoModelo : null);
        form.chassis.value = veiculo.chassis;
        form.renavam.value = veiculo.renavam;
        var km = veiculo.quilometragem.toString().replace(/(.)(?=(\d{3})+$)/g,'$1.');
        form.quilometragem.value = km;
    }

    var addMascaras = function() {
        addMascaraPlaca();
        addMascaraAno();
        addMascaraChassis();
        addMascaraRenavam();
        addMascaraNumber();
    }

    var addEventos = function() { 
        addTipoEventListener();
        addMarcaEventListener();
        addSalvarEventListener();
    };          
    
    
    // acoes /////////     

    var pesquisarTodos = function() {
        $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo?action=all",
            data: { },
            dataType: 'json',
        }).done(function(data) {
            addVeiculos(data);
        });
    };  
    
    var addVeiculos = function(veiculos) {  
        $('#datatable-veiculos tbody').html('');
        var html = '<tr>' + 
                      '<td>{{modelo}}</td>' +
                      '<td class="placa">{{placa}}</td>' +
                      '<td>{{anoFabricacao}}/{{anoModelo}}</td>' +
                      '<td class="renavam">{{renavam}}</td>' +
                      '<td class="number">{{quilometragem}}</td>' +
                      '<td>{{situacao}}</td>' +
                      '<td align="center">' +
                            '<button class="btn btn-sm btn-warning" onclick="veiculoCtrl.acoes.editar({{id}})">' +
                                '<i class="fa fa-lg fa-pencil"></i>' +
                            '</button>' +
                      '</td>' +
                      '<td align="center">' +
                            '<a class="btn btn-sm btn-danger" href="' + Configuracao.contextPath + '/veiculo?action=delete&idVeiculo={{id}}">' +
                                '<i class="fa fa-lg fa-trash"></i>' +
                            '</a>' +
                      '</td>' +
                   '</tr>';
        var template = _.template(html);
        _.map(veiculos, function(veiculo) {
            $('#datatable-veiculos tbody').append(template(veiculo));                        
        });
    };    

    var pesquisar = function(idVeiculo) {
        $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo?action=get",
            data: { idVeiculo: idVeiculo },
            dataType: 'json',
        }).done(function(data) {
            init(data);
        });
    }; 

    var editar = function(idVeiculo) {
        $.ajax({
            method: "POST",
            url: Configuracao.getContextPathActual + "/veiculo_editar_dialog.jsp",
            data: {idVeiculo: idVeiculo},
            dataType: 'html',
            global: false,
        }).done(function(retorno) {
            editarModal(retorno);
        });
    };

    var editarModal = function(retorno) {
        bootbox.dialog({
            title: "Editar Veículo",
            message: retorno,
            animate: true,
            onEscape: true,
            className: "modal-lgWidth my-modal-edit",
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
                        salvar(veiculoEditForm);
                    }
                }
            }
        });
        return false;
    }
    
    var salvar = function(form) {
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
    
    
    // funcoes /////////

    var setTipoVeiculo = function(veiculo) {
        if(veiculo) addTipos(veiculo.tipo, veiculo);                            
        else addTipos(tipos[1].key, veiculo);                            
    };

    var addTipos = function(value, veiculo) {
        var select = form.tipo;
        removeOptions(select);
        _.map(tipos, function(tipo) {
            var option = document.createElement("option");
            option.text = tipo.value;
            option.value = tipo.key;
            select.add(option);
        });
        select.value = value;
        setMarcaVeiculo(value, veiculo);
    };  

    var setMarcaVeiculo = function(tipo, veiculo) {
        $.ajax({
            method: "GET",
            url: "http://fipeapi.appspot.com/api/1/" + tipo + "/marcas.json",
            data: { },
            dataType: 'jsonp',
        }).done(function(data) {
            addMarcas(tipo, data, veiculo);
        });
    };
    
    var getMarcaSelected = function(marcas, veiculo) {
        if(!veiculo) return JSON.parse(form.marca.value);
        return _.find(marcas, function(marca) {
            return marca.name === veiculo.marca;
        });
    }

    var addMarcas = function(tipo, marcas, veiculo) {
        var select = form.marca;
        removeOptions(select);
        _.map(marcas, function(marca) {
            var option = document.createElement("option");
            option.text = marca.name;
            option.value = JSON.stringify(marca);
            select.add(option);
        });
        var selected = getMarcaSelected(marcas, veiculo);
        select.value = JSON.stringify(selected);
        setModeloVeiculo(tipo, selected.id, veiculo);
    };

    var setModeloVeiculo = function(tipo, marca, veiculo) {
        $.ajax({
            method: "GET",
            url: "http://fipeapi.appspot.com/api/1/" + tipo + "/veiculos/" + marca + ".json",
            data: { },
            dataType: 'jsonp',
        }).done(function(data) {
            addModelos(data, veiculo);  
        });
    };
    
    var getModeloSelected = function(modelos, veiculo) {
        if(!veiculo) return JSON.parse(form.modelo.value);
        return _.find(modelos, function(modelo) {
            return modelo.name === veiculo.modelo;
        });
    }

    var addModelos = function(modelos, veiculo) {
        var select = form.modelo;
        removeOptions(select);
        _.map(modelos, function(modelo) {
            var option = document.createElement("option");
            option.text = modelo.name;
            option.value = JSON.stringify(modelo);
            select.add(option);
        });
        var selected = getModeloSelected(modelos, veiculo);
        select.value = JSON.stringify(selected);
    };

    var setCombustivel = function(veiculo) {
        addCombustiveis(veiculo);                            
    };

    var addCombustiveis = function(veiculo) {
        var select = form.combustivel;
        removeOptions(select);
        _.map(combustiveis, function(combustivel) {
            var option = document.createElement("option");
            option.text = combustivel.value;
            option.value = combustivel.key;
            select.add(option);
        });  
        if(veiculo) select.value = veiculo.combustivel;        
    };  

    var setEstado = function(veiculo) {
        addStatus(veiculo);                            
    };

    var addStatus = function(veiculo) {
        var select = form.status;
        removeOptions(select);
        _.map(status, function(item) {
            var option = document.createElement("option");
            option.text = item.value;
            option.value = item.key;
            select.add(option);
        }); 
        if(veiculo) select.value = veiculo.status;        
    };   

    var setSituacao = function(veiculo) {
        addSituacao(veiculo);                            
    };

    var addSituacao = function(veiculo) {
        var select = form.situacao;
        removeOptions(select);
        _.map(situacao, function(item) {
            var option = document.createElement("option");
            option.text = item.value;
            option.value = item.key;
            select.add(option);
        }); 
        if(veiculo) select.value = veiculo.situacao;  
    };  
    
    var removeOptions = function(select) {
        while(select.options.length > 0) { select.remove(select.options.length - 1); }        
    }


    // mascara /////////
    
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
    
    
    // eventos /////////
    
    var addTipoEventListener = function() {   
        form.tipo.addEventListener('change', function() {
            setMarcaVeiculo(this.value);
        });
    };

    var addMarcaEventListener = function() {  
        form.marca.addEventListener('change', function() {
            var tipo = form.tipo.value;
            var marca = JSON.parse(this.value);
            setModeloVeiculo(tipo.value, marca.id, null);
        });
    };

    var addSalvarEventListener = function() { 
        if(!form.salvar) return;
        form.salvar.addEventListener('click', function() {
            return salvar(form);
        });
    };   
    
    init();
    
    return {     
        listas: {
            tipos: tipos,
            combustiveis: combustiveis,
            status: status,
            situacao: situacao
        },
        acoes: {
            pesquisarTodos: pesquisarTodos,
            pesquisar: pesquisar,
            editar: editar,                       
            salvar: salvar
        },
        funcoes: {
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
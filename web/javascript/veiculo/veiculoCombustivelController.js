var VeiculoCombustivelController = function(form) {
               
    var init = function(veiculo) { 
        Configuracao.loadingModal();
        addListas(veiculo);   
        setValueForm(veiculo);   
        addMascaras();
        addEventos(veiculo);
    }
    
    var addListas = function(combustivel) {  
        setVeiculos(combustivel);        
        setTipoCombustivel(combustivel);
    }

    var addMascaras = function() {
        VeiculoMascara.addMascaraNumber();
        VeiculoMascara.addMascaraNumeric();
        VeiculoMascara.addMascaraDate();
    }

    var addEventos = function() { 
        addVeiculoEventListener();
        addQuilometragemEventListener();
        addSalvarEventListener();
    };          
    
    
    // acoes /////////     

    var pesquisarTodos = function() {
        VeiculoService.pesquisarTodosVeiculosCombustivel()
        .done(function(data) {
            addCombustiveis(data);
        });
    };  
    
    var addCombustiveis = function(combustiveis) {  
        $('#datatable-manutencoes tbody').html('');
        var html = '<tr>' + 
                      '<td class="placa">{{placa}}</td>' +
                      '<td>{{tipo}}</td>' +
                      '<td class="date">{{data}}</td>' +
                      '<td class="number">{{quantidade}}</td>' +
                      '<td class="numeric">{{valorUnitario}}</td>' +
                      '<td class="number">{{media}}</td>' +
                      '<td align="center">' +
                            '<button class="btn btn-sm btn-warning" onclick="veiculoCombustivelCtrl.acoes.editar({{id}})">' +
                                '<i class="fa fa-lg fa-pencil"></i>' +
                            '</button>' +
                      '</td>' +
                      '<td align="center">' +
                            '<a class="btn btn-sm btn-danger" href="' + Configuracao.contextPath + '/veiculo/combustivel?action=delete&idVeiculoCombustivel={{id}}">' +
                                '<i class="fa fa-lg fa-trash"></i>' +
                            '</a>' +
                      '</td>' +
                   '</tr>';
        var template = _.template(html);
        _.map(combustiveis, function(combustivel) {
            $('#datatable-manutencoes tbody').append(template(combustivel));                        
        });
    };    

    var pesquisar = function(idVeiculoCombustivel) {
        VeiculoService.pesquisarVeiculoCombustivel(idVeiculoCombustivel)
        .done(function(data) {
            init(data);
        });
    };   

    var pesquisarUltimo = function(idVeiculo) {
        VeiculoService.pesquisarUltimoVeiculoCombustivel(idVeiculo)
        .done(function(data) {
            form.quilometragemInicial.value = data.quilometragemFinal;
        });
    }; 

    var editar = function(idVeiculoCombustivel) {
        VeiculoService.editarVeiculoCombustivel(idVeiculoCombustivel)
        .done(function(retorno) {
            editarModal(retorno);
        });
    };

    var editarModal = function(retorno) {
        bootbox.dialog({
            title: "Editar Combustível Veículo",
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
                        salvar(veiculoCombustivelEditForm);
                    }
                }
            }
        });
        return false;
    }
    
    var salvar = function(form) {
        if(!validarCampoQuantidade(form)) return false;
        if(!validarCampoData(form)) return false;
        if(!validarCampoValor(form)) return false;
        if(!validarCampoQuilometragemFinal(form)) return false;
        form.submit();
    }; 
    
    var validarCampoQuantidade = function(form) {
        var msg = 'Preencha a quantidade de litros abastecidos!';
        return VeiculoValidacao.campoNotNull(form.quantidade.value, msg);
    }; 
    
    var validarCampoValor = function(form) {
        var msg = 'Preencha o valor total do abastecimento!';
        return VeiculoValidacao.campoNotNull(form.valorTotal.value, msg);
    };
    
    var validarCampoData = function(form) {
        var msg = 'Preencha a data de abastecimento!';
        var msgValida = 'A data do abstecimento não é válida!';
        if(!VeiculoValidacao.campoNotNull(form.data.value, msg)) { return false; };
        return VeiculoValidacao.campoData(form.data.value, msgValida);
    }; 
    
    var validarCampoQuilometragemFinal = function(form) {
        var msg = 'Preencha a quilometragem do veículo!';
        var msgMenor = 'A quilometragem não pode ser inferior ou igual a última quilometragem inserida ' +
                'para este veículo (' + VeiculoFormatador.toNumber(form.quilometragemInicial.value) + ')!';
        if(!VeiculoValidacao.campoNotNull(form.quilometragemFinal.value, msg)) { return false; };
        return VeiculoValidacao.campoMenorIgualQue(form.quilometragemFinal.value, form.quilometragemInicial.value, msgMenor);
    }; 
    
    
    // funcoes ///////// 
    
    var setValueForm = function(combustivel) {  
        if(!combustivel) return;
        form.veiculo.value = combustivel.idVeiculo;
        form.tipo.value = combustivel.tipo;
        form.quantidade.value = VeiculoFormatador.toNumber(combustivel.quantidade);
        form.data.value = combustivel.data;
        form.valorTotal.value = VeiculoFormatador.toNumeric(combustivel.valorTotal);
        form.quilometragemInicial.value = VeiculoFormatador.toNumber(combustivel.quilometragemInicial);
        form.quilometragemFinal.value = VeiculoFormatador.toNumber(combustivel.quilometragemFinal);
    }

    var setVeiculos = function(combustivel) {
        VeiculoService.pesquisarTodosVeiculos()
        .done(function(data) {
            addVeiculos(data, combustivel);
        });
    };
    
    var getVeiculoSelected = function(combustivel) {
        if(!combustivel) return form.veiculo.value;
        return combustivel.idVeiculo;
    };

    var addVeiculos = function(veiculos, combustivel) {
        var select = form.veiculo;
        removeOptions(select);
        _.map(veiculos, function(veiculo) {
            var option = document.createElement("option");
            option.text = veiculo.marca + ' / ' + veiculo.modelo + ' (' + veiculo.placa + ')';
            option.value = veiculo.id;
            select.add(option);
        });
        var selected = getVeiculoSelected(combustivel);
        select.value = selected;
        pesquisarUltimo(form.veiculo.value);
    };
    
    var setTipoCombustivel = function(combustivel) {
        if(combustivel) addTipos(combustivel.tipo, combustivel);                            
        else addTipos(VeiculoConstantes.tiposCombustivel[0].key, combustivel);                            
    };

    var addTipos = function(value) {
        var select = form.tipo;
        removeOptions(select);
        _.map(VeiculoConstantes.tiposCombustivel, function(tipo) {
            var option = document.createElement("option");
            option.text = tipo.value;
            option.value = tipo.key;
            select.add(option);
        });
        select.value = value;
    }; 
    
    var removeOptions = function(select) {
        while(select.options.length > 0) { select.remove(select.options.length - 1); }        
    };
    
    
    // eventos /////////
    
    var addVeiculoEventListener = function() {   
        form.veiculo.addEventListener('change', function() {
            pesquisarUltimo(this.value);
        });
    };
    
    var addQuilometragemEventListener = function() {   
        form.quilometragemFinal.addEventListener('blur', function() {
            validarCampoQuilometragemFinal(form);
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
        acoes: {
            pesquisarTodos: pesquisarTodos,
            pesquisar: pesquisar,
            editar: editar,                       
            salvar: salvar
        },
        funcoes: {
            setValueForm: setValueForm,
            setTipoCombustivel: setTipoCombustivel,
            setVeiculos: setVeiculos
        },
        eventos: {
            addVeiculoEventListener: addVeiculoEventListener,
            addQuilometragemEventListener: addQuilometragemEventListener,
            addSalvarEventListener: addSalvarEventListener
        }
    }
};
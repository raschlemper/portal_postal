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
        addQuilometragemInicialEventListener();
        addQuilometragemFinalEventListener();
        addQuantidadeEventListener();
        addValorTotalEventListener(),
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
            if(!data.quilometragemFinal) { pesquisarVeiculo(idVeiculo); } 
            else { setQuilometragemFinal(data.quilometragemFinal); }
        });
    };   

    var pesquisarVeiculo = function(idVeiculo) {
        VeiculoService.pesquisarVeiculo(idVeiculo)
        .done(function(data) {
            setQuilometragemFinal(data.quilometragem);
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
        if(!form.idVeiculoCombustivel) {
            if(!validarCampoQuilometragemFinal(form)) return false;
        }
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
                'para este veículo (' + VeiculoFormatador.toNumberBr(form.quilometragemInicial.value) + ')!';
        if(!VeiculoValidacao.campoNotNull(form.quilometragemFinal.value, msg)) { return false; };
        return VeiculoValidacao.campoMenorIgualQue(form.quilometragemFinal.value, form.quilometragemInicial.value, msgMenor);
    }; 
    
    
    // funcoes ///////// 
    
    var setValueForm = function(combustivel) {  
        if(!combustivel) return;
        form.veiculo.value = combustivel.idVeiculo;
        form.tipo.value = combustivel.tipo;
        form.data.value = combustivel.data;
        form.quilometragemInicial.value = VeiculoFormatador.toNumberBr(combustivel.quilometragemInicial);
        form.quilometragemFinal.value = VeiculoFormatador.toNumberBr(combustivel.quilometragemFinal);
        form.quilometragemPercorrida.value = VeiculoFormatador.toNumberBr(combustivel.quilometragemPercorrida);
        form.quantidade.value = VeiculoFormatador.toNumberBr(combustivel.quantidade);
        form.valorUnitario.value = VeiculoFormatador.toNumericBr(combustivel.valorUnitario);
        form.valorTotal.value = VeiculoFormatador.toNumericBr(combustivel.valorTotal);
        form.media.value = VeiculoFormatador.toNumberBr(combustivel.media);
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
    
    var setQuilometragemPercorrida = function(form) {
        if(form.quilometragemInicial.value && form.quilometragemFinal.value) {
            var quilometragemInicial = VeiculoFormatador.toNumberUs(form.quilometragemInicial.value);
            var quilometragemFinal = VeiculoFormatador.toNumberUs(form.quilometragemFinal.value);
            var quilometragem = 0;
            if(quilometragemFinal > quilometragemInicial) { 
                quilometragem = quilometragemFinal - quilometragemInicial; 
            }
            form.quilometragemPercorrida.value = VeiculoFormatador.toNumberBr(quilometragem); 
        } else {
            form.quilometragemPercorrida.value = null; 
        }
    }
    
    var setValorUnitario = function(form) {
        if(form.quantidade.value && form.valorTotal.value) {
            var quantidade = VeiculoFormatador.toNumberUs(form.quantidade.value);
            var valorTotal = VeiculoFormatador.toNumericUs(form.valorTotal.value);
            var valor = valorTotal / quantidade; 
            form.valorUnitario.value = VeiculoFormatador.toNumericBr(valor.toFixed(2)); 
        } else {
            form.valorUnitario.value = null; 
        }
    }
    
    var setMedia = function(form) {
        if(form.quilometragemPercorrida.value && form.quantidade.value) {
            var quilometragemPercorrida = VeiculoFormatador.toNumberUs(form.quilometragemPercorrida.value);
            var quantidade = VeiculoFormatador.toNumberUs(form.quantidade.value);
            var valor = quilometragemPercorrida / quantidade; 
            form.media.value = VeiculoFormatador.toNumberBr(valor); 
        }else {
            form.media.value = null;  
        }
    }
    
    var setQuilometragemFinal = function(value) {
        form.quilometragemInicial.value = VeiculoFormatador.toNumberBr(value);
    }
    
    // eventos /////////
    
    var addVeiculoEventListener = function() {   
        form.veiculo.addEventListener('change', function() {
            pesquisarUltimo(this.value);
        });
    };
    
    var addQuilometragemInicialEventListener = function() {   
        form.quilometragemInicial.addEventListener('blur', function() {
            setQuilometragemPercorrida(form);
        });
    };
    
    var addQuilometragemFinalEventListener = function() {   
        form.quilometragemFinal.addEventListener('blur', function() {
            validarCampoQuilometragemFinal(form);
            setQuilometragemPercorrida(form);
            setMedia(form);
        });
    };
    
    var addQuantidadeEventListener = function() {   
        form.quantidade.addEventListener('blur', function() {
            setValorUnitario(form);
            setMedia(form);
        });
    };
    
    var addValorTotalEventListener = function() {   
        form.valorTotal.addEventListener('blur', function() {
            setValorUnitario(form);
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
            addQuilometragemInicialEventListener: addQuilometragemInicialEventListener,
            addQuilometragemFinalEventListener: addQuilometragemFinalEventListener,
            addQuantidadeEventListener: addQuantidadeEventListener,
            addValorTotalEventListener: addValorTotalEventListener,
            addSalvarEventListener: addSalvarEventListener
        }
    }
};
var VeiculoCombustivelController = function(form) {
    
    var init = function(idVeiculoCombustivel) {
        Configuracao.loadingModal();
        if(idVeiculoCombustivel) { pesquisar(idVeiculoCombustivel); }
        else { 
            addListas();
            addMascaras();
            addEventos();
            setValueForm();           
        }   
    }
               
    var initEdit = function(combustivel) { 
        addListas(combustivel);   
        addMascaras(combustivel);
        addEventos(combustivel);
        setValueForm(combustivel);   
    }
    
    var addListas = function(combustivel) {  
        setVeiculos(combustivel);        
        setTipoCombustivel(combustivel);
    }

    var addMascaras = function(combustivel) {
        VeiculoMascara.addMascaraNumber();
        VeiculoMascara.addMascaraNumeric();
        VeiculoMascara.addMascaraDate();
    }

    var addEventos = function(combustivel) { 
        addVeiculoEventListener();
        addValorUnitarioEventListener();
        addQuilometragemFinalEventListener();
        addQuantidadeEventListener();
        addValorTotalEventListener(),
        addSalvarEventListener();
    };          
    
    
    // acoes /////////     

    var pesquisarTodos = function() {
        VeiculoCombustivelService.pesquisarTodosVeiculosCombustivel()
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
                      '<td class="number">{{quilometragemPercorrida}}</td>' +
                      '<td class="number">{{quantidade}}</td>' +
                      '<td class="numeric">{{valorUnitarioFormat}}</td>' +
                      '<td class="numeric">{{valorTotalFormat}}</td>' +
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
            var tipo = VeiculoConstantes.getValue(VeiculoConstantes.combustivel, combustivel.tipo);
            combustivel.tipo = tipo.value;
            $('#datatable-combustiveis tbody').append(template(combustivel));                        
        });
    };    

    var pesquisar = function(idVeiculoCombustivel) {
        VeiculoCombustivelService.pesquisarVeiculoCombustivel(idVeiculoCombustivel)
        .done(function(data) {
            initEdit(data);
        });
    };   

    var pesquisarUltimo = function(idVeiculo) {
        VeiculoCombustivelService.pesquisarUltimoVeiculoCombustivel(idVeiculo)
        .done(function(data) {
            if(!data.quilometragemFinal) { pesquisarVeiculo(idVeiculo); } 
            else { setQuilometragemInicial(data.quilometragemFinal); }
        });
    };   

    var pesquisarVeiculo = function(idVeiculo) {
        VeiculoService.pesquisarVeiculo(idVeiculo)
        .done(function(data) {
            setQuilometragemInicial(data.quilometragem);
        });
    }; 

    var editar = function(idVeiculoCombustivel) {
        VeiculoCombustivelService.editarVeiculoCombustivel(idVeiculoCombustivel)
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
        return VeiculoValidacao.campoMoreEqualThen(form.quilometragemFinal.value, form.quilometragemInicial.value, msgMenor);
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
    }

    var setVeiculos = function(combustivel) {
        if(combustivel) { setVeiculo(combustivel); }
        else { setVeiculosTodos(combustivel); }
    };

    var setVeiculosTodos = function(combustivel) {
        VeiculoService.pesquisarTodosVeiculos()
        .done(function(data) {
            addVeiculos(data, combustivel);
        });
    };

    var setVeiculo = function(combustivel) {
        VeiculoService.pesquisarVeiculo(combustivel.idVeiculo)
        .done(function(data) {
            addVeiculo(data, combustivel);
        });
    };
    
    var getVeiculoSelected = function(combustivel) {
        if(!combustivel) return form.veiculo.value;
        return combustivel.idVeiculo;
    };

    var addVeiculos = function(veiculos, combustivel) {
        var select = form.veiculo;
        VeiculoDropdown.veiculos(select, veiculos);
        select.value = getVeiculoSelected(combustivel);
        if(!combustivel) { pesquisarUltimo(select.value); }
    };

    var addVeiculo = function(veiculo) {
        var select = form.veiculo;
        VeiculoDropdown.veiculo(select, veiculo);
    };
    
    var setTipoCombustivel = function(combustivel) {
        if(combustivel) addTipos(combustivel.tipo, combustivel);                            
        else addTipos(VeiculoConstantes.combustivel[0].key, combustivel);                            
    };

    var addTipos = function(value) {
        var select = form.tipo;
        VeiculoDropdown.tiposCombustivel(select);
        select.value = value;
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
    
    var setValorTotal = function(form) {
        if(form.quantidade.value && form.valorUnitario.value) {
            var quantidade = VeiculoFormatador.toNumberUs(form.quantidade.value);
            var valorUnitario = VeiculoFormatador.toNumericUs(form.valorUnitario.value);
            var valor = valorUnitario * quantidade; 
            form.valorTotal.value = VeiculoFormatador.toNumericBr(valor.toFixed(2)); 
        } else { 
            if(form.valorTotal.value) { setValorUnitario(form); }
        }
    }
    
    var setValorUnitario = function(form) {
        if(form.quantidade.value && form.valorTotal.value) {
            var quantidade = VeiculoFormatador.toNumberUs(form.quantidade.value);
            var valorTotal = VeiculoFormatador.toNumericUs(form.valorTotal.value);
            var valor = valorTotal / quantidade; 
            form.valorUnitario.value = VeiculoFormatador.toNumericBr(valor.toFixed(2)); 
        } else {
            if(form.valorUnitario.value) { setValorTotal(form); }
        }
    }
        
    var setQuilometragemInicial = function(value) {
        form.quilometragemInicial.value = VeiculoFormatador.toNumberBr(value);
    }
    
    // eventos /////////
    
    var addVeiculoEventListener = function() {   
        form.veiculo.addEventListener('change', function() {
            pesquisarUltimo(this.value);
        });
    };
    
    var addQuantidadeEventListener = function() {   
        form.quantidade.addEventListener('blur', function() {
            setValorUnitario(form);
        });
    };
    
    var addValorTotalEventListener = function() {   
        form.valorTotal.addEventListener('blur', function() {
            setValorUnitario(form);
        });
    };
    
    var addValorUnitarioEventListener = function() {   
        form.valorUnitario.addEventListener('blur', function() {
            setValorTotal(form);
        });
    };
    
    var addQuilometragemFinalEventListener = function() {   
        form.quilometragemFinal.addEventListener('blur', function() {
            validarCampoQuilometragemFinal(form);
            setQuilometragemPercorrida(form);
        });
    };

    var addSalvarEventListener = function() { 
        if(!form.salvar) return;
        form.salvar.addEventListener('click', function() {
            return salvar(form);
        });
    };  
    
    return { 
        init: init,
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
            addQuilometragemInicialEventListener: addValorUnitarioEventListener,
            addQuilometragemFinalEventListener: addQuilometragemFinalEventListener,
            addQuantidadeEventListener: addQuantidadeEventListener,
            addValorTotalEventListener: addValorTotalEventListener,
            addSalvarEventListener: addSalvarEventListener
        }
    }
};
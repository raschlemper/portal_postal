var VeiculoSeguroController = function(form) {
               
    var init = function(idVeiculo) {  
        Configuracao.loadingModal();
        if(idVeiculo) { pesquisar(idVeiculo); }
        else {
            addListas();   
            setValueForm();   
            addMascaras();
            addEventos();
        }
    }
               
    var initEdit = function(seguro) { 
        addListas(seguro);   
        addMascaras(seguro);
        addEventos(seguro);
        setValueForm(seguro);   
    }
    
    var addListas = function(seguro) {  
        setTipoIndenizacao(seguro);
        setVeiculos(seguro);   
    }

    var addMascaras = function(seguro) {
        VeiculoMascara.addMascaraNumberFull();
        VeiculoMascara.addMascaraNumeric();
    }

    var addEventos = function(seguro) { 
        addSalvarEventListener();
    };          
    
    
    // acoes /////////     

    var pesquisarTodos = function() {
        VeiculoSeguroService.pesquisarTodosVeiculosSeguro()
        .done(function(data) {
            addSeguros(data);
        });
    };  
    
    var addSeguros = function(seguros) {  
        $('#datatable-seguros tbody').html('');
        var html = '<tr>' + 
                      '<td class="placa">{{placa}}</td>' +
                      '<td class="nunber-full">{{numeroSeguro}}</td>' +
                      '<td>{{assegurado}}</td>' +
                      '<td class="numeric">{{valorFranquia}}</td>' +
                      '<td>{{indenizacao}}</td>' +
                      '<td align="center">' +
                            '<button class="btn btn-sm btn-warning" onclick="veiculoSeguroCtrl.acoes.editar({{id}})">' +
                                '<i class="fa fa-lg fa-pencil"></i>' +
                            '</button>' +
                      '</td>' +
                      '<td align="center">' +
                            '<a class="btn btn-sm btn-danger" href="' + Configuracao.contextPath + '/veiculo/seguro?action=delete&idVeiculoSeguro={{id}}">' +
                                '<i class="fa fa-lg fa-trash"></i>' +
                            '</a>' +
                      '</td>' +
                   '</tr>';
        var template = _.template(html);
        _.map(seguros, function(seguro) {
            var indenizacao = VeiculoConstantes.getValue(VeiculoConstantes.seguro, seguro.indenizacao);
            seguro.indenizacao = indenizacao.value;
            $('#datatable-seguros tbody').append(template(seguro));                        
        });
    };    

    var pesquisar = function(idVeiculoSeguro) {
        VeiculoSeguroService.pesquisarVeiculoSeguro(idVeiculoSeguro)
        .done(function(data) {
            initEdit(data);
        });
    }; 

    var editar = function(idVeiculoSeguro) {
        VeiculoSeguroService.editarVeiculoSeguro(idVeiculoSeguro)
        .done(function(retorno) {
            editarModal(retorno);
        });
    };

    var editarModal = function(retorno) {
        bootbox.dialog({
            title: "Editar Seguro Ve\u00EDculo",
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
                        salvar(veiculoSeguroEditForm);
                    }
                }
            }
        });
        return false;
    }
    
    var salvar = function(form) {
        if(!validarCampoNumeroSeguro(form)) return false;
        if(!validarCampoValorFranquia(form)) return false;
        if(!validarCampoAssegurado(form)) return false;
        form.submit();
    }; 
    
    var validarCampoNumeroSeguro = function(form) {
       var msg = 'Preencha o n\u00FAmero da seguro!';
        return VeiculoValidacao.campoNotNull(form.numeroSeguro.value, msg);
    }; 
    
    var validarCampoAssegurado = function(form) {
        var msg = 'Preencha o assegurado!';
        return VeiculoValidacao.campoNotNull(form.assegurado.value, msg);
    }; 
    
    var validarCampoValorFranquia = function(form) {
        var msg = 'Preencha o valor da franquia!';
        return VeiculoValidacao.campoNotNull(form.valorFranquia.value, msg);
    }; 
    
    
    // funcoes ///////// 
    
    var setValueForm = function(seguro) {  
        if(!seguro) return;
        form.veiculo.value = seguro.idVeiculo;
        form.numeroSeguro.value = seguro.numeroSeguro;
        form.assegurado.value = seguro.assegurado;
        form.valorFranquia.value = VeiculoFormatador.toNumericBr(seguro.valorFranquia);
        form.indenizacao.value = seguro.indenizacao;
    }

    var setVeiculos = function(seguro) {
        if(seguro) { setVeiculo(seguro); }
        else { setVeiculosTodos(seguro); }
    };

    var setVeiculosTodos = function(seguro) {
        VeiculoService.pesquisarTodosVeiculos()
        .done(function(data) {
            addVeiculos(data, seguro);
        });
    };

    var setVeiculo = function(seguro) {
        VeiculoService.pesquisarVeiculo(seguro.idVeiculo)
        .done(function(data) {
            addVeiculo(data, seguro);
        });
    };
    
    var getVeiculoSelected = function(seguro) {
        if(!seguro) return form.veiculo.value;
        return seguro.idVeiculo;
    };

    var addVeiculos = function(veiculos, seguro) {
        var select = form.veiculo;
        VeiculoDropdown.veiculos(select, veiculos);
        select.value = getVeiculoSelected(seguro);
    };

    var addVeiculo = function(veiculo) {
        var select = form.veiculo;
        VeiculoDropdown.veiculo(select, veiculo);
    };
    
    var setTipoIndenizacao = function(seguro) {
        if(seguro) addTipos(seguro.tipo, seguro);                            
        else addTipos(VeiculoConstantes.seguro[0].key, seguro);                            
    };

    var addTipos = function(value) {
        var select = form.indenizacao;
        VeiculoDropdown.tiposSeguro(select);
        select.value = value;
    }; 
    
    
    // eventos /////////

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
            setTipoIndenizacao: setTipoIndenizacao,
            setVeiculos: setVeiculos
        },
        eventos: {
            addSalvarEventListener: addSalvarEventListener
        }
    }
};
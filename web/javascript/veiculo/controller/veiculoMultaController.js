var VeiculoMultaController = function(form) {
               
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
               
    var initEdit = function(multa) { 
        addListas(multa);   
        addMascaras(multa);
        addEventos(multa);
        setValueForm(multa);   
    }
    
    var addListas = function(multa) {  
        setVeiculos(multa);   
    }

    var addMascaras = function(multa) {
        VeiculoMascara.addMascaraNumberFull();
        VeiculoMascara.addMascaraNumeric();
        VeiculoMascara.addMascaraDate();
    }

    var addEventos = function(multa) { 
        addSalvarEventListener();
    };          
    
    
    // acoes /////////     

    var pesquisarTodos = function() {
        VeiculoMultaService.pesquisarTodosVeiculosMulta()
        .done(function(data) {
            addMultas(data);
        });
    };  
    
    var addMultas = function(multas) {  
        $('#datatable-multas tbody').html('');
        var html = '<tr>' + 
                      '<td class="placa">{{placa}}</td>' +
                      '<td class="number-full">{{numeroMulta}}</td>' +
                      '<td class="numeric">{{valor}}</td>' +
                      '<td class="date">{{data}}</td>' +
                      '<td>{{local}}</td>' +
                      '<td align="center">' +
                            '<button class="btn btn-sm btn-warning" onclick="veiculoMultaCtrl.acoes.editar({{id}})">' +
                                '<i class="fa fa-lg fa-pencil"></i>' +
                            '</button>' +
                      '</td>' +
                      '<td align="center">' +
                            '<a class="btn btn-sm btn-danger" href="' + Configuracao.contextPath + '/veiculo/multa?action=delete&idVeiculoMulta={{id}}">' +
                                '<i class="fa fa-lg fa-trash"></i>' +
                            '</a>' +
                      '</td>' +
                   '</tr>';
        var template = _.template(html);
        _.map(multas, function(multa) {
            $('#datatable-multas tbody').append(template(multa));                        
        });
    };    

    var pesquisar = function(idVeiculoMulta) {
        VeiculoMultaService.pesquisarVeiculoMulta(idVeiculoMulta)
        .done(function(data) {
            initEdit(data);
        });
    }; 

    var editar = function(idVeiculoMulta) {
        VeiculoMultaService.editarVeiculoMulta(idVeiculoMulta)
        .done(function(retorno) {
            editarModal(retorno);
        });
    };

    var editarModal = function(retorno) {
        bootbox.dialog({
            title: "Editar Multa Veículo",
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
                        salvar(veiculoMultaEditForm);
                    }
                }
            }
        });
        return false;
    }
    
    var salvar = function(form) {
        if(!validarCampoNumeroMulta(form)) return false;
        if(!validarCampoValor(form)) return false;
        if(!validarCampoData(form)) return false;
        form.submit();
    }; 
    
    var validarCampoNumeroMulta = function(form) {
       var msg = 'Preencha o numero da multa!';
        return VeiculoValidacao.campoNotNull(form.numeroMulta.value, msg);
    }; 
    
    var validarCampoValor = function(form) {
        var msg = 'Preencha o valor da multa!';
        return VeiculoValidacao.campoNotNull(form.valor.value, msg);
    }; 
    
    var validarCampoData = function(form) {
        var msg = 'Preencha a data da multa!';
        var msgValida = 'A data da manutenção não é válida!';
        if(!VeiculoValidacao.campoNotNull(form.data.value, msg)) { return false; };
        return VeiculoValidacao.campoData(form.data.value, msgValida);
    }; 
    
    
    // funcoes ///////// 
    
    var setValueForm = function(multa) {  
        if(!multa) return;
        form.veiculo.value = multa.idVeiculo;
        form.numeroMulta.value = multa.numeroMulta;
        form.valor.value = VeiculoFormatador.toNumericBr(multa.valor);
        form.data.value = multa.data;
        form.local.value = multa.local;
        form.descricao.value = multa.descricao;
    }

    var setVeiculos = function(multa) {
        if(multa) { setVeiculo(multa); }
        else { setVeiculosTodos(multa); }
    };

    var setVeiculosTodos = function(multa) {
        VeiculoService.pesquisarTodosVeiculos()
        .done(function(data) {
            addVeiculos(data, multa);
        });
    };

    var setVeiculo = function(multa) {
        VeiculoService.pesquisarVeiculo(multa.idVeiculo)
        .done(function(data) {
            addVeiculo(data, multa);
        });
    };
    
    var getVeiculoSelected = function(multa) {
        if(!multa) return form.veiculo.value;
        return multa.idVeiculo;
    };

    var addVeiculos = function(veiculos, multa) {
        var select = form.veiculo;
        VeiculoDropdown.veiculos(select, veiculos);
        select.value = getVeiculoSelected(multa);
    };

    var addVeiculo = function(veiculo) {
        var select = form.veiculo;
        VeiculoDropdown.veiculo(select, veiculo);
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
            setVeiculos: setVeiculos
        },
        eventos: {
            addSalvarEventListener: addSalvarEventListener
        }
    }
};
var VeiculoManutencaoController = function(form) {
               
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
               
    var initEdit = function(manutencao) { 
        addListas(manutencao);   
        addMascaras(manutencao);
        addEventos(manutencao);
        setValueForm(manutencao);   
    }
    
    var addListas = function(manutencao) {  
        setVeiculos(manutencao);        
        setTipoManutencao(manutencao);
    }

    var addMascaras = function(manutencao) {
        VeiculoMascara.addMascaraNumber();
        VeiculoMascara.addMascaraNumeric();
        VeiculoMascara.addMascaraDate();
    }

    var addEventos = function(manutencao) { 
        addSalvarEventListener();
    };          
    
    
    // acoes /////////     

    var pesquisarTodos = function() {
        VeiculoManutencaoService.pesquisarTodosVeiculosManutencao()
        .done(function(data) {
            addManutencoes(data);
        });
    };  
    
    var addManutencoes = function(manutencoes) {  
        $('#datatable-manutencoes tbody').html('');
        var html = '<tr>' + 
                      '<td class="placa">{{placa}}</td>' +
                      '<td>{{tipo}}</td>' +
                      '<td class="numeric">{{valor}}</td>' +
                      '<td class="date">{{data}}</td>' +
                      '<td class="date">{{dataAgendamento}}</td>' +
                      '<td class="date">{{dataEntrega}}</td>' +
                      '<td align="center">' +
                            '<button class="btn btn-sm btn-warning" onclick="veiculoManutencaoCtrl.acoes.editar({{id}})">' +
                                '<i class="fa fa-lg fa-pencil"></i>' +
                            '</button>' +
                      '</td>' +
                      '<td align="center">' +
                            '<a class="btn btn-sm btn-danger" href="' + Configuracao.contextPath + '/veiculo/manutencao?action=delete&idVeiculoManutencao={{id}}">' +
                                '<i class="fa fa-lg fa-trash"></i>' +
                            '</a>' +
                      '</td>' +
                   '</tr>';
        var template = _.template(html);
        _.map(manutencoes, function(manutencao) {
            var tipo = VeiculoConstantes.getValue(VeiculoConstantes.manutencao, manutencao.tipo);
            manutencao.tipo = tipo.value;
            $('#datatable-manutencoes tbody').append(template(manutencao));                        
        });
    };    

    var pesquisar = function(idVeiculoManutencao) {
        VeiculoManutencaoService.pesquisarVeiculoManutencao(idVeiculoManutencao)
        .done(function(data) {
            initEdit(data);
        });
    }; 

    var editar = function(idVeiculoManutencao) {
        VeiculoManutencaoService.editarVeiculoManutencao(idVeiculoManutencao)
        .done(function(retorno) {
            editarModal(retorno);
        });
    };

    var editarModal = function(retorno) {
        bootbox.dialog({
<<<<<<< HEAD
            title: "Editar Manuten\u00E7\u00E3o Ve\u00EDculo",
=======
            title: "Editar Manuten��o Ve�culo",
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
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
                        salvar(veiculoManutencaoEditForm);
                    }
                }
            }
        });
        return false;
    }
    
    var salvar = function(form) {
        if(!validarCampoQuilometragem(form)) return false;
        if(!validarCampoValor(form)) return false;
        if(!validarCampoData(form)) return false;
        if(!validarCampoDataAgendamento(form)) return false;
        if(!validarCampoDataEntrega(form)) return false;
        form.submit();
    }; 
    
    var validarCampoQuilometragem = function(form) {
<<<<<<< HEAD
        var msg = 'Preencha a quilometragem do ve\u00EDculo!';
=======
        var msg = 'Preencha a quilometragem do ve�culo!';
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
        return VeiculoValidacao.campoNotNull(form.quilometragem.value, msg);
    }; 
    
    var validarCampoValor = function(form) {
<<<<<<< HEAD
        var msg = 'Preencha o valor da manuten\u00E7\u00E3o!';
=======
        var msg = 'Preencha o valor da manuten��o!';
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
        return VeiculoValidacao.campoNotNull(form.valor.value, msg);
    }; 
    
    var validarCampoData = function(form) {
<<<<<<< HEAD
        var msg = 'Preencha a data da manuten\u00E7\u00E3o!';
        var msgValida = 'A data da manuten\u00E7\u00E3o n\u00E3o \u00E9 v\u00E1lida!';
=======
        var msg = 'Preencha a data da manuten��o!';
        var msgValida = 'A data da manuten��o n�o � v�lida!';
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
        if(!VeiculoValidacao.campoNotNull(form.data.value, msg)) { return false; };
        return VeiculoValidacao.campoData(form.data.value, msgValida);
    }; 
    
    var validarCampoDataAgendamento = function(form) {
<<<<<<< HEAD
        var msg = 'A data de agendamento da manuten\u00E7\u00E3o n\u00E3o \u00E9 v\u00E1lida!';
=======
        var msg = 'A data de agendamento da manuten��o n�o � v�lida!';
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
        return VeiculoValidacao.campoData(form.dataAgendamento.value, msg);
    };   
    
    var validarCampoDataEntrega = function(form) {
<<<<<<< HEAD
        var msg = 'A data de entrega da manuten\u00E7\u00E3o n\u00E3o \u00E9 v\u00E1lida!';
=======
        var msg = 'A data de entrega da manuten��o n�o � v�lida!';
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
        return VeiculoValidacao.campoData(form.dataEntrega.value, msg);
    };  
    
    
    // funcoes ///////// 
    
    var setValueForm = function(manutencao) {  
        if(!manutencao) return;
        form.veiculo.value = manutencao.idVeiculo;
        form.tipo.value = manutencao.tipo;
        form.quilometragem.value = VeiculoFormatador.toNumberBr(manutencao.quilometragem);
        form.valor.value = VeiculoFormatador.toNumericBr(manutencao.valor);
        form.data.value = manutencao.data;
        form.dataAgendamento.value = manutencao.dataAgendamento;
        form.dataEntrega.value = manutencao.dataEntrega;
        form.descricao.value = manutencao.descricao;
    }

    var setVeiculos = function(manutencao) {
        if(manutencao) { setVeiculo(manutencao); }
        else { setVeiculosTodos(manutencao); }
    };

    var setVeiculosTodos = function(manutencao) {
        VeiculoService.pesquisarTodosVeiculos()
        .done(function(data) {
            addVeiculos(data, manutencao);
        });
    };

    var setVeiculo = function(manutencao) {
        VeiculoService.pesquisarVeiculo(manutencao.idVeiculo)
        .done(function(data) {
            addVeiculo(data, manutencao);
        });
    };
    
    var getVeiculoSelected = function(manutencao) {
        if(!manutencao) return form.veiculo.value;
        return manutencao.idVeiculo;
    };

    var addVeiculos = function(veiculos, manutencao) {
        var select = form.veiculo;
        VeiculoDropdown.veiculos(select, veiculos);
        select.value = getVeiculoSelected(manutencao);
    };

    var addVeiculo = function(veiculo) {
        var select = form.veiculo;
        VeiculoDropdown.veiculo(select, veiculo);
    };
    
    var setTipoManutencao = function(manutencao) {
        if(manutencao) addTipos(manutencao.tipo, manutencao);                            
        else addTipos(VeiculoConstantes.manutencao[0].key, manutencao);                            
    };

    var addTipos = function(value) {
        var select = form.tipo;
        VeiculoDropdown.tiposManutencao(select);
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
            setTipoManutencao: setTipoManutencao,
            setVeiculos: setVeiculos
        },
        eventos: {
            addSalvarEventListener: addSalvarEventListener
        }
    }
};
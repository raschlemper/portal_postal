var VeiculoSinistroController = function(form) {
               
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
               
    var initEdit = function(sinistro) { 
        addListas(sinistro);   
        addMascaras(sinistro);
        addEventos(sinistro);
        setValueForm(sinistro);   
    }
    
    var addListas = function(sinistro) {  
        setTipoSinistro(sinistro);
        setResponsavel(sinistro);
        setVeiculos(sinistro);   
    }

    var addMascaras = function(sinistro) {
        VeiculoMascara.addMascaraNumberFull();
        VeiculoMascara.addMascaraDate();
    }

    var addEventos = function(sinistro) { 
        addSalvarEventListener();
    };          
    
    
    // acoes /////////     

    var pesquisarTodos = function() {
        VeiculoSinistroService.pesquisarTodosVeiculosSinistro()
        .done(function(data) {
            addSinistros(data);
        });
    };  
    
    var addSinistros = function(sinistros) {  
        $('#datatable-sinistros tbody').html('');
        var html = '<tr>' + 
                      '<td class="placa">{{placa}}</td>' +
                      '<td class="number-full">{{boletimOcorrencia}}</td>' +
                      '<td class="date">{{data}}</td>' +
                      '<td>{{local}}</td>' +
                      '<td>{{tipo}}</td>' +
                      '<td>{{responsavel}}</td>' +
                      '<td align="center">' +
                            '<button class="btn btn-sm btn-warning" onclick="veiculoSinistroCtrl.acoes.editar({{id}})">' +
                                '<i class="fa fa-lg fa-pencil"></i>' +
                            '</button>' +
                      '</td>' +
                      '<td align="center">' +
                            '<a class="btn btn-sm btn-danger" href="' + Configuracao.contextPath + '/veiculo/sinistro?action=delete&idVeiculoSinistro={{id}}">' +
                                '<i class="fa fa-lg fa-trash"></i>' +
                            '</a>' +
                      '</td>' +
                   '</tr>';
        var template = _.template(html);
        _.map(sinistros, function(sinistro) {
            var tipo = VeiculoConstantes.getValue(VeiculoConstantes.sinistro, sinistro.tipo);
            sinistro.tipo = tipo.value;
            var responsavel = VeiculoConstantes.getValue(VeiculoConstantes.responsavel, sinistro.responsavel);
            sinistro.responsavel = responsavel.value;
            $('#datatable-sinistros tbody').append(template(sinistro));                        
        });
    };    

    var pesquisar = function(idVeiculoSinistro) {
        VeiculoSinistroService.pesquisarVeiculoSinistro(idVeiculoSinistro)
        .done(function(data) {
            initEdit(data);
        });
    }; 

    var editar = function(idVeiculoSinistro) {
        VeiculoSinistroService.editarVeiculoSinistro(idVeiculoSinistro)
        .done(function(retorno) {
            editarModal(retorno);
        });
    };

    var editarModal = function(retorno) {
        bootbox.dialog({
            title: "Editar Sinistro Veículo",
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
                        salvar(veiculoSinistroEditForm);
                    }
                }
            }
        });
        return false;
    }
    
    var salvar = function(form) {
        if(!validarCampoNumeroSinistro(form)) return false;
        if(!validarCampoData(form)) return false;
        form.submit();
    }; 
    
    var validarCampoNumeroSinistro = function(form) {
       var msg = 'Preencha o numero da sinistro!';
        return VeiculoValidacao.campoNotNull(form.boletimOcorrencia.value, msg);
    }; 
    
    var validarCampoData = function(form) {
        var msg = 'Preencha a data da sinistro!';
        var msgValida = 'A data da manutenção não é válida!';
        if(!VeiculoValidacao.campoNotNull(form.data.value, msg)) { return false; };
        return VeiculoValidacao.campoData(form.data.value, msgValida);
    }; 
    
    
    // funcoes ///////// 
    
    var setValueForm = function(sinistro) {  
        if(!sinistro) return;
        form.veiculo.value = sinistro.idVeiculo;
        form.boletimOcorrencia.value = sinistro.boletimOcorrencia;
        form.data.value = sinistro.data;
        form.local.value = sinistro.local;
        form.tipo.value = sinistro.tipo;
        form.responsavel.value = sinistro.responsavel;
        form.descricao.value = sinistro.descricao;
    }

    var setVeiculos = function(sinistro) {
        if(sinistro) { setVeiculo(sinistro); }
        else { setVeiculosTodos(sinistro); }
    };

    var setVeiculosTodos = function(sinistro) {
        VeiculoService.pesquisarTodosVeiculos()
        .done(function(data) {
            addVeiculos(data, sinistro);
        });
    };

    var setVeiculo = function(sinistro) {
        VeiculoService.pesquisarVeiculo(sinistro.idVeiculo)
        .done(function(data) {
            addVeiculo(data, sinistro);
        });
    };
    
    var getVeiculoSelected = function(sinistro) {
        if(!sinistro) return form.veiculo.value;
        return sinistro.idVeiculo;
    };

    var addVeiculos = function(veiculos, sinistro) {
        var select = form.veiculo;
        VeiculoDropdown.veiculos(select, veiculos);
        select.value = getVeiculoSelected(sinistro);
    };

    var addVeiculo = function(veiculo) {
        var select = form.veiculo;
        VeiculoDropdown.veiculo(select, veiculo);
    };
    
    var setTipoSinistro = function(sinistro) {
        if(sinistro) addTipos(sinistro.tipo, sinistro);                            
        else addTipos(VeiculoConstantes.sinistro[0].key, sinistro);                            
    };

    var addTipos = function(value) {
        var select = form.tipo;
        VeiculoDropdown.tiposSinistro(select);
        select.value = value;
    }; 
    
    var setResponsavel = function(sinistro) {
        if(sinistro) addResponsavel(sinistro.responsavel, sinistro);                            
        else addResponsavel(VeiculoConstantes.responsavel[0].key, sinistro);                            
    };

    var addResponsavel = function(value) {
        var select = form.responsavel;
        VeiculoDropdown.tiposResponsavel(select);
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
            setTipoSinistro: setTipoSinistro,
            setResponsavel: setResponsavel,
            setVeiculos: setVeiculos
        },
        eventos: {
            addSalvarEventListener: addSalvarEventListener
        }
    }
};
var VeiculoController = function(form) {
               
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
               
    var initEdit = function(veiculo) { 
        addListas(veiculo);   
        addMascaras(veiculo);
        addEventos(veiculo);
        setValueForm(veiculo);   
    }
    
    var addListas = function(veiculo) {  
        setTipoVeiculo(veiculo);
        setCombustivel(veiculo);
        setEstado(veiculo);
        setSituacao(veiculo);              
    }

    var addMascaras = function(veiculo) {
        VeiculoMascara.addMascaraPlaca();
        VeiculoMascara.addMascaraAno();
        VeiculoMascara.addMascaraChassis();
        VeiculoMascara.addMascaraRenavam();
        VeiculoMascara.addMascaraNumber();
    }

    var addEventos = function(veiculo) { 
        addTipoEventListener();
        addMarcaEventListener();
        addSalvarEventListener();
    };          
    
    
    // acoes /////////     

    var pesquisarTodos = function() {
        VeiculoService.pesquisarTodosVeiculos()
        .done(function(data) {
            addVeiculos(data);
        });
    };  
    
    var addVeiculos = function(veiculos) {  
        $('#datatable-veiculos tbody').html('');
        var html = '<tr>' + 
                      '<td>{{modelo}}</td>' +
                      '<td class="placa">{{placa}}</td>' +
                      '<td>{{anoFabricacaoModelo}}</td>' +
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
            var tipo = VeiculoConstantes.getValue(VeiculoConstantes.tipo, veiculo.tipo);
            veiculo.tipo = tipo.value;
            var combustivel = VeiculoConstantes.getValue(VeiculoConstantes.combustivel, veiculo.combustivel);
            veiculo.combustivel = combustivel.value;
            var status = VeiculoConstantes.getValue(VeiculoConstantes.status, veiculo.status);
            veiculo.status = status.value;
            var situacao = VeiculoConstantes.getValue(VeiculoConstantes.situacao, veiculo.situacao);
            veiculo.situacao = situacao.value;
            $('#datatable-veiculos tbody').append(template(veiculo));                        
        });
    };    

    var pesquisar = function(idVeiculo) {
        VeiculoService.pesquisarVeiculo(idVeiculo)
        .done(function(data) {
            initEdit(data);
        });
    }; 

    var editar = function(idVeiculo) {
        VeiculoService.editarVeiculo(idVeiculo)
        .done(function(retorno) {
            editarModal(retorno);
        });
    };

    var editarModal = function(retorno) {
        bootbox.dialog({
<<<<<<< HEAD
            title: "Editar Ve\u00EDculo",
=======
            title: "Editar Veículo",
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
<<<<<<< HEAD
        var msg = 'Preencha a placa do ve\u00EDculo!';
=======
        var msg = 'Preencha a placa do veículo!';
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
        return VeiculoValidacao.campoNotNull(form.placa.value, msg);
    };  

    var validarCampoAnoFabricacao = function(form) {
<<<<<<< HEAD
        var msg = 'Preencha o ano de fabrica\u00E7\u00E3o do ve\u00EDculo com valores entre 1970 e ' + anoCorrente + '!';
=======
        var msg = 'Preencha o ano de fabricaÃ§Ã£o do veículo com valores entre 1970 e ' + anoCorrente + '!';
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
        var anoCorrente = (new Date).getFullYear() + 1;
        return VeiculoValidacao.campoBetween(form.anoFabricacao.value, 1970, anoCorrente, msg);
    };  

    var validarCampoAnoModelo = function(form) {
<<<<<<< HEAD
        var msg = 'Preencha o ano do modelo do ve\u00EDculo com valores entre 1970 e ' + anoCorrente + '!';
=======
        var msg = 'Preencha o ano do modelo do veículo com valores entre 1970 e ' + anoCorrente + '!';
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
        var anoCorrente = (new Date).getFullYear() + 1;
        return VeiculoValidacao.campoBetween(form.anoModelo.value, 1970, anoCorrente, msg);
    };

    var validarCampoRenavam = function(form) {
<<<<<<< HEAD
        var msg = 'Preencha o renavam do ve\u00EDculo!';
=======
        var msg = 'Preencha o renavam do veículo!';
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
        return VeiculoValidacao.campoNotNull(form.renavam.value, msg);
    };  
    
    var validarCampoQuilometragem = function(form) {
<<<<<<< HEAD
        var msg = 'Preencha a quilometragem do ve\u00EDculo!';
=======
        var msg = 'Preencha a quilometragem do veículo!';
>>>>>>> 175902c9d28a837edc35f1159eb3fc0bbaaa09ae
        return VeiculoValidacao.campoNotNull(form.quilometragem.value, msg);
    }; 
    
    
    // funcoes /////////
    
    var setValueForm = function(veiculo) {  
        if(!veiculo) return;
        form.placa.value = veiculo.placa;
        form.anoFabricacao.value = veiculo.anoFabricacaoFormat;
        form.anoModelo.value = veiculo.anoModeloFormat;
        form.chassis.value = veiculo.chassis;
        form.renavam.value = veiculo.renavam;
        form.quilometragem.value = VeiculoFormatador.toNumberBr(veiculo.quilometragem);
    }

    var setTipoVeiculo = function(veiculo) {
        if(veiculo) addTipos(veiculo.tipo, veiculo);                            
        else addTipos(VeiculoConstantes.tipo[1].key, veiculo);                            
    };

    var addTipos = function(value, veiculo) {
        var select = form.tipo;
        VeiculoDropdown.tiposVeiculo(select);
        select.value = value;
        setMarcaVeiculo(value, veiculo);
    };  

    var setMarcaVeiculo = function(tipo, veiculo) {
        VeiculoService.marcaVeiculo(tipo)
        .done(function(data) {
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
        VeiculoDropdown.marcasVeiculo(select, marcas);
        var selected = getMarcaSelected(marcas, veiculo);
        select.value = JSON.stringify(selected);
        setModeloVeiculo(tipo, selected.id, veiculo);
    };

    var setModeloVeiculo = function(tipo, marca, veiculo) {
        VeiculoService.modeloVeiculo(tipo, marca)
        .done(function(data) {
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
        VeiculoDropdown.modelosVeiculo(select, modelos);
        var selected = getModeloSelected(modelos, veiculo);
        select.value = JSON.stringify(selected);
    };

    var setCombustivel = function(veiculo) {
        addCombustiveis(veiculo);                            
    };

    var addCombustiveis = function(veiculo) {
        var select = form.combustivel;
        VeiculoDropdown.tiposCombustivel(select);
        if(veiculo) select.value = veiculo.combustivel;        
    };  

    var setEstado = function(veiculo) {
        addStatus(veiculo);                            
    };

    var addStatus = function(veiculo) {
        var select = form.status;
        VeiculoDropdown.tiposStatus(select);
        if(veiculo) select.value = veiculo.status;        
    };   

    var setSituacao = function(veiculo) {
        addSituacao(veiculo);                            
    };

    var addSituacao = function(veiculo) {
        var select = form.situacao;
        VeiculoDropdown.tiposSituacao(select);
        if(veiculo) select.value = veiculo.situacao;  
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
            setTipoVeiculo: setTipoVeiculo,
            setMarcaVeiculo: setMarcaVeiculo,
            setModeloVeiculo: setModeloVeiculo,
            setCombustivel: setCombustivel,
            setEstado: setEstado,
            setSituacao: setSituacao
        },
        eventos: {
            addTipoEventListener: addTipoEventListener,
            addMarcaEventListener: addMarcaEventListener,
            addSalvarEventListener: addSalvarEventListener
        }
    }
};
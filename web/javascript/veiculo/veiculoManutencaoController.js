var VeiculoManutencaoController = function(form) {
               
    var init = function(veiculo) { 
        Configuracao.loadingModal();
        addListas(veiculo);   
        setValueForm(veiculo);   
        addMascaras();
        addEventos(veiculo);
    }
    
    var addListas = function(manutencao) {  
        setVeiculos(manutencao);        
        setTipoManutencao(manutencao);
    }

    var addMascaras = function() {
        VeiculoMascara.addMascaraNumber();
        VeiculoMascara.addMascaraNumeric();
        VeiculoMascara.addMascaraDate();
    }

    var addEventos = function() { 
        addSalvarEventListener();
    };          
    
    
    // acoes /////////     

    var pesquisarTodos = function() {
        VeiculoService.pesquisarTodosVeiculosManutencao()
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
            $('#datatable-manutencoes tbody').append(template(manutencao));                        
        });
    };    

    var pesquisar = function(idVeiculoManutencao) {
        VeiculoService.pesquisarVeiculoManutencao(idVeiculoManutencao)
        .done(function(data) {
            init(data);
        });
    }; 

    var editar = function(idVeiculoManutencao) {
        VeiculoService.editarVeiculoManutencao(idVeiculoManutencao)
        .done(function(retorno) {
            editarModal(retorno);
        });
    };

    var editarModal = function(retorno) {
        bootbox.dialog({
            title: "Editar Manutenção Veículo",
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
        if(form.quilometragem.value) return true;
        alert('Preencha a quilometragem do veículo!');
        return false;
    }; 
    
    var validarCampoValor = function(form) {
        if(form.valor.value) return true;
        alert('Preencha o valor da manutenção!');
        return false;
    }; 
    
    var validarCampoData = function(form) {
        if(form.data.value) {
            var data = toDate(form.data.value);
            if(!isNaN(data.getDate())) return true;
            alert('A data da manutenção não é válida!');
            return false; 
        }
        alert('Preencha a data da manutenção!');
        return false;
    };  
    
    var validarCampoDataAgendamento = function(form) {
        if(!form.dataAgendamento.value) return true;
        var data = toDate(form.dataAgendamento.value);
        if(!isNaN(data.getDate())) return true;
        alert('A data de agndamento da manutenção não é válida!');
        return false; 
    };   
    
    var validarCampoDataEntrega = function(form) {
        if(!form.dataEntrega.value) return true;
        var data = toDate(form.dataEntrega.value);
        if(!isNaN(data.getDate())) return true;
        alert('A data de entrega da manutenção não é válida!');
        return false; 
    };  
       
    var toDate = function(data) {
        var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
        return new Date(data.replace(pattern,'$3-$2-$1'));
    }
    
    
    // funcoes ///////// 
    
    var setValueForm = function(manutencao) {  
        if(!manutencao) return;
        form.veiculo.value = manutencao.idVeiculo;
        form.tipo.value = manutencao.tipo;
        var km = manutencao.quilometragem.toString().replace(/(.)(?=(\d{3})+$)/g,'$1.');
        form.quilometragem.value = km;
        var valor = manutencao.valor.toString().replace(/(.)(?=(\d{3})+$)/g,'$1.');
        form.valor.value = valor;
        form.data.value = manutencao.data;
        form.dataAgendamento.value = manutencao.dataAgendamento;
        form.dataEntrega.value = manutencao.dataEntrega;
        form.descricao.value = manutencao.descricao;
    }

    var setVeiculos = function(manutencao) {
        VeiculoService.pesquisarTodosVeiculos()
        .done(function(data) {
            addVeiculos(data, manutencao);
        });
    };
    
    var getVeiculoSelected = function(manutencao) {
        if(!manutencao) return form.veiculo.value;
        return manutencao.idVeiculo;
    };

    var addVeiculos = function(veiculos, manutencao) {
        var select = form.veiculo;
        removeOptions(select);
        _.map(veiculos, function(veiculo) {
            var option = document.createElement("option");
            option.text = veiculo.marca + ' / ' + veiculo.modelo + ' (' + veiculo.placa + ')';
            option.value = veiculo.id;
            select.add(option);
        });
        var selected = getVeiculoSelected(manutencao);
        select.value = selected;
    };
    
    var setTipoManutencao = function(manutencao) {
        if(manutencao) addTipos(manutencao.tipo, manutencao);                            
        else addTipos(VeiculoConstantes.tiposManutencao[0].key, manutencao);                            
    };

    var addTipos = function(value) {
        var select = form.tipo;
        removeOptions(select);
        _.map(VeiculoConstantes.tiposManutencao, function(tipo) {
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
            setTipoManutencao: setTipoManutencao,
            setVeiculos: setVeiculos
        },
        eventos: {
            addSalvarEventListener: addSalvarEventListener
        }
    }
};
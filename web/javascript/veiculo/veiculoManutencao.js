var VeiculoManutencaoController = function(form) {
                                
    var tipos = [{'key': 'programada', 'value': 'Programada'},
                 {'key': 'rotina', 'value': 'Rotina'},
                 {'key': 'trocaoleo', 'value': 'Troca de Óleo'}];
               
    var init = function(veiculo) { 
        Configuracao.messageModal();
        Configuracao.loadingModal();
        addListas(veiculo);   
        addValueForm(veiculo);   
        addMascaras();
        addEventos(veiculo);
    }
    
    var addListas = function(manutencao) {  
        setVeiculos(manutencao);        
        setTipoManutencao(manutencao);
    }
    
    var addValueForm = function(manutencao) {  
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

    var addMascaras = function() {
        addMascaraNumber();
        addMascaraNumeric();
        addMascaraDate();
    }

    var addEventos = function() { 
        addSalvarEventListener();
    };          
    
    
    // acoes /////////     

    var pesquisarTodos = function() {
        $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/manutencao?action=all",
            data: { },
            dataType: 'json',
        }).done(function(data) {
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
        $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/manutencao?action=get",
            data: { idVeiculoManutencao: idVeiculoManutencao },
            dataType: 'json',
        }).done(function(data) {
            init(data);
        });
    }; 

    var editar = function(idVeiculoManutencao) {
        $.ajax({
            method: "POST",
            url: Configuracao.getContextPathActual + "/manutencao/veiculo_manutencao_editar_dialog.jsp",
            data: {idVeiculoManutencao: idVeiculoManutencao},
            dataType: 'html',
            global: false,
        }).done(function(retorno) {
            editarModal(retorno);
        });
    };

    var editarModal = function(retorno) {
        Configuracao.closeModal();
        bootbox.dialog({
            title: "Editar Manutenção Veículo",
            message: retorno,
            animate: true,
            onEscape: true,
            className: "modal-lgWidth",
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
    
    var toDate = function(data) {
        var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
        return new Date(data.replace(pattern,'$3-$2-$1'));
    }
    
    
    // funcoes ///////// 

    var setVeiculos = function(manutencao) {
        $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo?action=all",
            data: { },
            dataType: 'json',
        }).done(function(data) {
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
        else addTipos(tipos[0].key, manutencao);                            
    };

    var addTipos = function(value) {
        var select = form.tipo;
        removeOptions(select);
        _.map(tipos, function(tipo) {
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


    // mascara /////////

    var addMascaraNumber = function() {
        $(".number").mask('DDD.DDD', {translation: {'D': {pattern: /[0-9]/}}, reverse: true});
    };

    var addMascaraNumeric = function() {
        $(".numeric").mask('#.##D,DD', {translation: {'D': {pattern: /[0-9]/}}, reverse: true});
    };
    
    var addMascaraDate = function() {
        $(".date").mask('DD/DD/DDDD', {translation: {'D': {pattern: /[0-9]/}}});
        $(".date").datepicker({
            showAnim: 'slideDown',
            numberOfMonths: 1
        });
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
        listas: {
            tipos: tipos
        },
        acoes: {
            pesquisarTodos: pesquisarTodos,
            pesquisar: pesquisar,
            editar: editar,                       
            salvar: salvar
        },
        funcoes: {
            setTipoManutencao: setTipoManutencao,
            setVeiculos: setVeiculos
        },
        mascara: {
            addMascaraNumber: addMascaraNumber,
            addMascaraNumeric: addMascaraNumeric,
            addMascaraDate: addMascaraDate
        },
        eventos: {
            addSalvarEventListener: addSalvarEventListener
        }
    }
};
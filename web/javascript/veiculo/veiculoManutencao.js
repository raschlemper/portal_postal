var VeiculoManutencaoController = function(form) {
                                
    var tipos = [{'key': 'programada', 'value': 'Programada'},
                 {'key': 'rotina', 'value': 'Rotina'},
                 {'key': 'trocaoleo', 'value': 'Troca de Óleo'}];
               
    var init = function(veiculo) {  
        loading();
        addListas(veiculo);   
        addValueForm(veiculo);   
        addMascaras();
        addEventos(veiculo);
    }
    
    var addListas = function(manutencao) {  
        setTipoManutencao(manutencao);
        setVeiculos(manutencao);        
    }
    
    var addValueForm = function(veiculo) {  
        if(!veiculo) return;
        form.placa.value = veiculo.placa;
        form.anoFabricacao.value = (veiculo.anoFabricacao > 0 ? veiculo.anoFabricacao : null);
        form.anoModelo.value = (veiculo.anoModelo > 0 ? veiculo.anoModelo : null);
        form.chassis.value = veiculo.chassis;
        form.renavam.value = veiculo.renavam;
        var km = veiculo.quilometragem.toString().replace(/(.)(?=(\d{3})+$)/g,'$1.');
        form.quilometragem.value = km;
    }

    var addMascaras = function() {
        addMascaraPlaca();
        addMascaraAno();
        addMascaraChassis();
        addMascaraRenavam();
        addMascaraNumber();
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
    
    var addManutencoes = function(veiculos) {  
        $('#datatable-veiculos tbody').html('');
        var html = '<tr>' + 
                      '<td>{{modelo}}</td>' +
                      '<td class="placa">{{placa}}</td>' +
                      '<td>{{anoFabricacao}}/{{anoModelo}}</td>' +
                      '<td class="renavam">{{renavam}}</td>' +
                      '<td class="number">{{quilometragem}}</td>' +
                      '<td>{{situacao}}</td>' +
                      '<td align="center">' +
                            '<button class="btn btn-sm btn-warning" onclick="veiculoManutencaoCtrl.acoes.editar({{id}})">' +
                                '<i class="fa fa-lg fa-pencil"></i>' +
                            '</button>' +
                      '</td>' +
                      '<td align="center">' +
                            '<a class="btn btn-sm btn-danger" href="' + Configuracao.contextPath + '/veiculo/manutencao?action=delete&idVeiculo={{id}}">' +
                                '<i class="fa fa-lg fa-trash"></i>' +
                            '</a>' +
                      '</td>' +
                   '</tr>';
        var template = _.template(html);
        _.map(veiculos, function(veiculo) {
            $('#datatable-veiculos tbody').append(template(veiculo));                        
        });
    };    

    var pesquisar = function(idVeiculo) {
        $.ajax({
            method: "GET",
            url: Configuracao.contextPath + "/veiculo/manutencao?action=get",
            data: { idVeiculo: idVeiculo },
            dataType: 'json',
        }).done(function(data) {
            init(data);
        });
    }; 

    var editar = function(idVeiculo) {
        $.ajax({
            method: "POST",
            url: Configuracao.getContextPathActual + "/manutencao/veiculo_manutencao_editar_dialog.jsp",
            data: {idVeiculo: idVeiculo},
            dataType: 'html',
            global: false,
        }).done(function(retorno) {
            editarModal(retorno);
        });
    };

    var editarModal = function(retorno) {
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
        if(!validarCampoPlaca(form)) return false;
        if(!validarCampoAnoFabricacao(form)) return false;
        if(!validarCampoAnoModelo(form)) return false;
        if(!validarCampoRenavam(form)) return false;
        if(!validarCampoQuilometragem(form)) return false;
        form.submit();
    };  

    var validarCampoPlaca = function(form) {
        if(form.placa.value) return true;
        alert('Preencha a placa do veículo!');
        return false;
    };  

    var validarCampoAnoFabricacao = function(form) {
        if(!form.anoFabricacao.value) return true;
        var anoCorrente = (new Date).getFullYear() + 1;
        if(form.anoFabricacao.value >= 1970 && form.anoFabricacao.value <= anoCorrente) return true;
        alert('Preencha o ano de fabricação do veículo com valores entre 1970 e ' + anoCorrente + '!');
        return false;
    };  

    var validarCampoAnoModelo = function(form) {
        if(!form.anoModelo.value) return true;
        var anoCorrente = (new Date).getFullYear() + 1;
        if(form.anoModelo.value >= 1970 && form.anoModelo.value <= anoCorrente) return true;
        alert('Preencha o ano do modelo do veículo com valores entre 1970 e ' + anoCorrente + '!');
        return false;
    };

    var validarCampoRenavam = function(form) {
        if(form.renavam.value) return true;
        alert('Preencha o renavam do veículo!');
        return false;
    };    

    var validarCampoQuilometragem = function(form) {
        if(form.quilometragem.value) return true;
        alert('Preencha a quilometragem do veículo!');
        return false;
    }; 
    
    
    // funcoes /////////

    var loading = function() {        
        $( document ).ajaxStart(function() {
            waitMsg();
        }).ajaxStop(function() {
            $('.my-modal').modal('hide'); 
        });
    }  

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
    }

    var addVeiculos = function(veiculos, manutencao) {
        var select = form.veiculo;
        removeOptions(select);
        _.map(veiculos, function(veiculo) {
            var option = document.createElement("option");
            option.text = veiculo.modelo;
            option.value = veiculo.id;
            select.add(option);
        });
        var selected = getVeiculoSelected(manutencao);
        select.value = selected;
    };
    
    var removeOptions = function(select) {
        while(select.options.length > 0) { select.remove(select.options.length - 1); }        
    }


    // mascara /////////
    
    var addMascaraPlaca = function() {
        $(".placa").mask('SSS-DDDD', {translation: {'S': {pattern: /[A-Za-z]/},
                                                    'D': {pattern: /[0-9]/}}});
        $(".placa").keyup(function() { this.value = this.value.toUpperCase(); });
    };

    var addMascaraAno = function() {
        $(".ano").mask('DDDD', {translation: {'D': {pattern: /[0-9]/}}});
    };

    var addMascaraChassis = function() {
        $(".chassis").mask('XX.XX.XXXXX.X.X.XXXXXX', {translation: {'X': {pattern: /[A-Za-z0-9]/}}, reverse: true});
        $(".chassis").keyup(function() { this.value = this.value.toUpperCase(); });
    };

    var addMascaraRenavam = function() {
        $(".renavam").mask('DDDDDDDDDD-D', {translation: {'D': {pattern: /[0-9]/}}, reverse: true});
    };

    var addMascaraNumber = function() {
        $(".number").mask('DDD.DDD', {translation: {'D': {pattern: /[0-9]/}}, reverse: true});
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
            loading: loading,
            setTipoManutencao: setTipoManutencao,
            setVeiculos: setVeiculos
        },
        mascara: {
            addMascaraPlaca: addMascaraPlaca,
            addMascaraAno: addMascaraAno,
            addMascaraChassis: addMascaraChassis,
            addMascaraRenavam: addMascaraRenavam,
            addMascaraNumber: addMascaraNumber
        },
        eventos: {
            addSalvarEventListener: addSalvarEventListener
        }
    }
};
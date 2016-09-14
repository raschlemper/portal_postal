'use strict';

app.factory('PlanoContaService', function($http, PromiseService) { 
    
    var report = function(planoContas) {
        var dados = [];
        _.map(planoContas, function(planoConta) {
            var estruturas = [];
            flatten(planoConta, estruturas); 
            //estruturas = identing(estruturas);
            _.map(estruturas, function(estrutura) { 
                var report = {}; 
                report.tipo = estrutura.tipo.id;
                report.nivel = estrutura.nivel;
                report.ehGrupo = estrutura.ehGrupo;
                report.descricao = estrutura.descricao;
                dados.push(report);
            });            
        })  
        return dados;
    };
    
    var identing = function(estruturas) {        
        angular.forEach(estruturas, function(estrutura) {            
            estrutura.descricao = formatingEstrutura(estrutura.nivel) + estrutura.descricao;
        });
    };
    
    var formatingEstrutura = function(count) {
        if(count>1) { count = count * 4; }
        var result = "";
        for(var i=1; i<count; i++){
            result+= String.fromCharCode(160);
        }
        return result;
    }
        
    var flatten = function(estruturas, estruturasLista) {
        angular.forEach(estruturas, function(estrutura) {
            estruturasLista.push({ 
                idPlanoConta: estrutura.idPlanoConta,
                id: estrutura.idPlanoConta,
                tipo: estrutura.tipo,
                nivel: estrutura.nivel, 
                ehGrupo: (estrutura.contas ? true : false),
                idGrupo: (estrutura.grupo ? estrutura.grupo.idPlanoConta : null),
                codigo: estrutura.estrutura,
                keep: (estrutura.contas ? true : false),
                descricao: estrutura.descricao,
                parent: estrutura.parent
            });
            if(estrutura.contas) { flatten(estrutura.contas, estruturasLista); }
        });
    };
    
    var getEstrutura = function(estruturas) {
        angular.forEach(estruturas, function(estrutura) {     
            estrutura.descricao = getCode(estrutura.estrutura) + ' - ' + estrutura.nome; 
            estrutura.id = estrutura.idPlanoConta; 
            estrutura.grupos = estrutura.contas; 
            estrutura.parent = (estrutura.grupo && estrutura.grupo.idPlanoConta);
            if(estrutura.contas) { getEstrutura(estrutura.contas); }
        });
    }
            
    var getCode = function(estruturas) {
        var code = '';
        angular.forEach(estruturas, function(estrutura) {
            if(code) { code = code + '.' + estrutura; }
            else { code = estrutura; }
        });
        return code;
    }
        
    return {

        getAll: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/"));
        },

        getStructure: function() {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/structure"));
        },

        getByTipo: function(tipo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/tipo/" + tipo));
        },
        
        findContaResultadoByTipo: function(tipo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/tipo/" + tipo + "/resultado"));
        },

        getStructureByTipo: function(tipo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/tipo/" + tipo + "/structure"));
        },

        getByTipoGrupoCodigo: function(tipo, grupo, codigo) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/tipo/" + tipo + "/grupo/" + grupo + "/codigo/" + codigo));
        },

        get: function(idPlanoConta) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/" + idPlanoConta));
        },

        getLancamento: function(idPlanoConta) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/" + idPlanoConta + '/lancamento'));
        },

        getLancamentoProgramado: function(idPlanoConta) {
            return PromiseService.execute(
                    $http.get(_contextPath + "/api/financeiro/planoconta/" + idPlanoConta + '/lancamento/programado'));
        },

        save: function(data) {
            return PromiseService.execute(
                    $http.post(_contextPath + "/api/financeiro/planoconta/", data));
        },

        update: function(idPlanoConta, data) {
            return PromiseService.execute(
                    $http.put(_contextPath + "/api/financeiro/planoconta/" + idPlanoConta, data));
        },

        delete: function(idPlanoConta) {
            return PromiseService.execute(
                    $http.delete(_contextPath + "/api/financeiro/planoconta/" + idPlanoConta));
        },
        
        identing: function(estruturas) {
            identing(estruturas);
        },
        
        flatten: function(estruturas) {
            var estruturasLista = [];
            flatten(estruturas, estruturasLista);
            return estruturasLista;
        }, 
        
        estrutura: function(estruturas) {
            getEstrutura(estruturas);
        }, 
        
        report: report

    }

});
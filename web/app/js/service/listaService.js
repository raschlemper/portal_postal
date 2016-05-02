'use strict';

app.factory('ListaService', function() {

    return {

        getValue: function(list, key) {
            var value = _.find(list, function(item){ return item.id == key; });
            if(!value) { value = list[0]; }
            return value;
        },

        getVeiculoValue: function(list, key) {
            var value = _.find(list, function(item){ return item.idVeiculo == key; });
            if(!value) { value = list[0]; }
            return value;
        },

        getPlanoContaValue: function(list, key) {
            var value = _.find(list, function(item){ return item.idPlanoConta == key; });
            if(!value) { value = list[0]; }
            return value;
        },

        getContaCorrenteValue: function(list, key) {
            var value = _.find(list, function(item){ return item.idContaCorrente == key; });
            if(!value) { value = list[0]; }
            return value;
        },

        getCartaoCreditoValue: function(list, key) {
            var value = _.find(list, function(item){ return item.idCartaoCredito == key; });
            if(!value) { value = list[0]; }
            return value;
        },

        getLancamentoValue: function(list, key) {
            var value = _.find(list, function(item){ return item.idLancamento == key; });
            if(!value) { value = list[0]; }
            return value;
        },
        
        getValueCombustivelFipe: function(list, key) {
            var value = _.find(list, function(item){ return item.descricao.toLowerCase() == key.toLowerCase(); });
            if(!value) { value = list[0]; }
            return value;
        }

    }

});
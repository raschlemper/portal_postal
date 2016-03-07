'use strict';

app.factory('ValorService', function($filter) {

    return {
        
        getValueDescricaoVeiculo: function(value) {
            return value.marca + ' / ' + value.modelo + ' (' + $filter('placa')(value.placa) + ')';
        },
        
        getValueMsgVeiculo: function(value) {
            return value.modelo + " (" + $filter('placa')(value.placa) + ")";
        },
        
        getValueLocalCep: function(value) {
            return value.logradouro + ', ' + value.bairro + ' - ' + value.cidade + '/' + value.estado;
        },
        
        getValueAnoFipe: function(value) {
            if(!_.isNumber(value)) { value = parseInt(value); }
            if(value === 32000) { value = (new Date).getFullYear(); }
            return value;
        }

    }

});
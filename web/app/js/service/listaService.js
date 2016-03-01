'use strict';

app.factory('ListaService', function() {

    return {

        getValue: function(list, key) {
            var value = _.find(list, function(item){ return item.id == key; });
            if(!value) { value = list[0]; }
            return value;
        },
        
        getValueCombustivelFipe: function(list, key) {
            var value = _.find(list, function(item){ return item.descricao == key; });
            if(!value) { value = list[0]; }
            return value;
        }

    }

});
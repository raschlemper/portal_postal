'use strict';

app.factory('ListaService', function() {

    return {

        getValue: function(list, key) {
            var value = _.find(list, function(item){ return item.value == key; });
            if(!value) { value = {'key': 'nenhum', 'value': ''} }
            return value;
        }

    }

});
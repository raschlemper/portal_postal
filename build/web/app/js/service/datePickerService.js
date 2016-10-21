'use strict';

app.factory('DatePickerService', function() {
    
    return {

        default: {
            format: 'dd/MM/yyyy',
            'close': 'Fechar',
            'clear': 'Limpar',
            'today': 'Hoje',
            opened: false,
            open: function ($event) {
                $event.preventDefault();
                $event.stopPropagation();
                this.opened = true;
            }
        }

    }

});
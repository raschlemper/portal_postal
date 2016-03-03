'use strict';

app.factory('DatePickerService', function() {

    return {

        default: {
            format: 'dd/MM/yyyy',
            opened: false,
            open: function ($event) {
                $event.preventDefault();
                $event.stopPropagation();
                this.opened = true;
            }
        }

    }

});
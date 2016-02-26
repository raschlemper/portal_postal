'use strict';

veiculo.factory('ModalMessageService', function() {

    return {

        alertMessage: function (message) {
            return {
                title: "Mesagem do Sistema!",
                message: message,
                animate: true,
                onEscape: true,
                buttons: {
                    "Cancelar": {
                        label:"<i class='fa fa-lg fa-spc fa-times'></i>FECHAR",
                        className: "btn btn-default",
                        callback: function() {
                            bootbox.hideAll();
                        }
                    }
                }
            };
        }

    }

});
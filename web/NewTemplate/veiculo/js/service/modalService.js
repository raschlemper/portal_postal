'use strict';

veiculo.factory('ModalService', function() {

    return {

        modalMessage: function (message) {
            return {
                animation: true,
                templateUrl: 'partials/modalMessage.html',
                controller: 'ModalMessageController',
                resolve: {
                    message: function () {
                        return message;
                    }
                }
            }
        },

        modalSalvar: function (templateUrl, controller, veiculo) {
            return {
                animation: true,
                templateUrl: templateUrl,
                controller: controller,
                size: 'lg',
                resolve: {
                    veiculo: function() {
                        return veiculo;
                    }
                }
            }
        },

        modalExcluir: function (title, message) {
            return {
                animation: true,
                templateUrl: 'partials/modalExcluir.html',
                controller: 'ModalExcluirController',
                resolve: {
                    title: function() {
                        return title;
                    },
                    message: function() {
                        return message;
                    }
                }
            }
        }

    }

});
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

        modalEditar: function (templateUrl, controller, title, message) {
            return {
                animation: true,
                templateUrl: templateUrl,
                controller: controller,
                size: 'lg',
                resolve: {
                    title: function() {
                        return title;
                    },
                    message: function() {
                        return message;
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
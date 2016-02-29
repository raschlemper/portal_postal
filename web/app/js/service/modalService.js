'use strict';

app.factory('ModalService', function($modal) {

    return {

        modalDefault: function (templateUrl, controller, veiculo) {
            return $modal.open({
                animation: true,
                templateUrl: templateUrl,
                controller: controller,
                size: 'lg',
                resolve: {
                    veiculo: function() {
                        return veiculo;
                    }
                }
            })
        },

        modalLoading: function () {
            return $modal.open({
                animation: true,
                templateUrl: 'partials/modal/modalLoading.html',
                controller: function($scope, $modalInstance) {
                    $scope.cancel = function() {
                        $modalInstance.dismiss('cancel');
                    };
                },
                resolve: {}
            });
        },

        modalMessage: function (message) {
            return $modal.open({
                animation: true,
                templateUrl: 'partials/modal/modalMessage.html',
                controller: 'ModalMessageController',
                resolve: {
                    message: function () {
                        return message;
                    }
                }
            })
        },

        modalExcluir: function (title, message) {
            return $modal.open({
                animation: true,
                templateUrl: 'partials/modal/modalExcluir.html',
                controller: 'ModalExcluirController',
                resolve: {
                    title: function() {
                        return title;
                    },
                    message: function() {
                        return message;
                    }
                }
            })
        }

    }

});
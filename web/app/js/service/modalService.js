'use strict';

app.factory('ModalService', function($modal) {

    return {

        modalDefault: function (templateUrl, controller, size, resolve) {
            return $modal.open({
                animation: true,
                templateUrl: templateUrl,
                controller: controller,
                size: size,
                resolve: resolve,
                windowClass: 'modal-window'
            })
        },

        modalLoading: function () {
            return $modal.open({
                animation: true,
                templateUrl: 'partials/modal/modalLoading.html',
                controller: function($scope, $modalInstance) {
                    $scope.cancel = function() { $modalInstance.dismiss('cancel'); };
                },
                resolve: {}
            });
        },

        modalMessage: function (message) {
            return $modal.open({
                animation: true,
                templateUrl: 'partials/modal/modalMessage.html',
                controller: function($scope, $modalInstance) {
                    $scope.message = message;        
                    $scope.cancel = function () { $modalInstance.dismiss('cancel'); };
                },
                resolve: {}
            })
        },

        modalExcluir: function (title, message) {
            return $modal.open({
                animation: true,
                templateUrl: 'partials/modal/modalExcluir.html',
                controller: function($scope, $modalInstance) {
                    $scope.title = title;
                    $scope.message = message;        
                    $scope.ok = function() { $modalInstance.close(); };
                    $scope.cancel = function () { $modalInstance.dismiss('cancel'); };
                },
                resolve: {}
            })
        }

    }

});
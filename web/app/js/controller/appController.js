'use strict';

app.controller('AppController', ['$rootScope', '$scope',
    function ($rootScope, $scope) {
            
        var init = function () {
            addButtonHelp();
        }; 
        
        var addButtonHelp = function() {
            var url = _contextPath + '/app/help/';
            var top = 0;
            var left = screen.width;
            var width = screen.width * 0.3;
            var height = screen.height * 0.8;
            var options = 'toolbar=no,scrollbars=yes,resizable=yes,top=' + top + ',left=' + left + ',width=' + width + ',height=' + height;
            var grupo = angular.element('#navbar-group');
            var html = '<li>' +
                           '<button class="navbar-toggle collapse in" data-toggle="collapse" style="border-radius: 15px;" ' +
                                    'onClick="window.open(\'' + url + '\', \'_blank\', \'' + options + '\')">' +
                               '<i class="fa fa-lg fa-question" aria-hidden="true"></i>' + 
                           '</button>' +
                       '</li>';
            grupo.prepend(html);            
        }
        
        init();

    }]);

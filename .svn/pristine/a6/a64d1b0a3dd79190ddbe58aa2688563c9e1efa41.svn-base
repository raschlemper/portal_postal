'use strict';

app.factory('ReportService', function($http, $state, $location) {

    return {

        pdf: function(name, params) {
            $http.post(_contextPath + "/report/" + name + "/pdf", params, {responseType:'arraybuffer'})
                .success(function (data) {
                    var file = new Blob([data], {type: 'application/pdf'});
                    var fileURL = URL.createObjectURL(file);
                    window.open(fileURL);    
                })
                .error(function(err) {
                    console.log(err);
                });
        }

    }

});
'use strict';

app.factory('ReportService', function($http, $state, $location) {
    
    var openByUrl = function(data, type) {
        var file = new Blob([data], {type: type});
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL);
    }
    
    var openByReader = function(data, type) {
        var file = new Blob([data], {type: type});
        var reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onloadend = function (e) {
            window.open(reader.result, '_blank');
        }   
    }

    return {

        pdf: function(name, params) {
            $http.post(_contextPath + "/report/" + name + "/pdf", params, {responseType:'arraybuffer'})
                .success(function (data) {
                    openByUrl(data, 'application/pdf');
//                    window.open(fileURL);   
                })
                .error(function(err) {
                    console.log(err);
                });
        },

        excel: function(name, params) {
            $http.post(_contextPath + "/report/" + name + "/excel", params, {responseType:'arraybuffer'})
                .success(function (data) {
//                    var reader = new FileReader();
                    openByReader(data, 'application/vnd.ms-excel');
//                    window.open(fileURL, 'Excel');

//                    var file = new Blob([data], {type: 'application/vnd.ms-excel'});
//                    reader.readAsDataURL(file);
//                    reader.onloadend = function (e) {
//                        window.open(reader.result, 'Excel', 'width=20,height=10,toolbar=0,menubar=0,scrollbars=no', '_blank');
//                    }   
                })
                .error(function(err) {
                    console.log(err);
                });
        }

    }

});
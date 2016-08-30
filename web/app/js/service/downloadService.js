'use strict';

app.factory('DownloadService', function($http, $state, $location) {

    return {

        execute: function(url) {
            window.open(url);   
        }

    }

});
'use strict';

app.factory('DateService', function($modal) {

    return {

        date: function(date) {
            if(!date) return null;
            if(!angular.isDate(date)) {
                return moment(date, "DD/MM/YYYY"); 
            } 
            if(moment.isDate(date)) {
                return date
            }
            return null;
        }


    }

});
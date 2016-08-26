'use strict';

app.filter('anexo', function() {
    return function(input, events, item) {
        if(input) { 
            return '<div ng-click="' + events + '" style="cursor: pointer;" title="Anexos do Lançamento"><i class="fa fa-lg fa-paperclip"></i></div>'; 
        }   
        return '<div ng-click="' + events + '" style="color:#CCC; cursor: pointer;" title="Anexos do Lançamento"><i class="fa fa-lg fa-paperclip"></i></div>';
    };
});
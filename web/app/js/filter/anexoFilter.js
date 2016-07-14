'use strict';

app.filter('anexo', function() {
    return function(input, events) {
        if(!input) return "<span></span>";
        return '<div ng-click="' + events + '" title="Anexos do Lançamento" style="cursor: pointer;"><i class="fa fa-lg fa-paperclip"></i></div>'; 
    };
});
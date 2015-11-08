var VeiculoMascara = function() {
    var app = {};   
    
    app.addMascaraPlaca = function() {
        $(".placa").mask('SSS-DDDD', {translation: {'S': {pattern: /[A-Za-z]/},
                                                    'D': {pattern: /[0-9]/}}});
        $(".placa").keyup(function() { this.value = this.value.toUpperCase(); });
    };

    app.addMascaraAno = function() {
        $(".ano").mask('DDDD', {translation: {'D': {pattern: /[0-9]/}}});
    };

    app.addMascaraChassis = function() {
        $(".chassis").mask('XX.XX.XXXXX.X.X.XXXXXX', {translation: {'X': {pattern: /[A-Za-z0-9]/}}, reverse: true});
        $(".chassis").keyup(function() { this.value = this.value.toUpperCase(); });
    };

    app.addMascaraRenavam = function() {
        $(".renavam").mask('DDDDDDDDDD-D', {translation: {'D': {pattern: /[0-9]/}}, reverse: true});
    };

    app.addMascaraNumberFull = function() {
        $(".number-full").mask('DDD.DDD.DDD.DDD.DDD', {translation: {'D': {pattern: /[0-9]/}}, reverse: true});
    };

    app.addMascaraNumber = function() {
        $(".number").mask('DDD.DDD', {translation: {'D': {pattern: /[0-9]/}}, reverse: true});
    };

    app.addMascaraNumeric = function() {
        $(".numeric").mask('#.##D,DD', {translation: {'D': {pattern: /[0-9]/}}, reverse: true});
    };
    
    app.addMascaraDate = function() {
        $(".date").mask('DD/DD/DDDD', {translation: {'D': {pattern: /[0-9]/}}});
        $(".date").datepicker({
            showAnim: 'slideDown',
            numberOfMonths: 1
        });
    };
    
    return app;
    
}();
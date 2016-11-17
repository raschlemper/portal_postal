'use strict';

app.config(['$provide', function ($provide) {
    $provide.decorator('$locale', ['$delegate', function ($delegate) {
            if ($delegate.id == 'pt-br') {
                $delegate.NUMBER_FORMATS.PATTERNS[1].negPre = '(\u00A4';
                $delegate.NUMBER_FORMATS.PATTERNS[1].negSuf = ')';
            }
            return $delegate;
        }]);

    $provide.decorator('datepickerPopupDirective', function ($delegate) {
        var directive = $delegate[0];
        var link = directive.link;

        directive.compile = function () {
            return function (scope, element, attrs) {
                link.apply(this, arguments);
                element.mask("99/99/9999");
            };
        };

        return $delegate;
    });
}]);



app.config(['$provide', function ($provide) {
    var cacheBuster = Date.now().toString();

    function templateFactoryDecorator($delegate) {
        var fromUrl = angular.bind($delegate, $delegate.fromUrl);
        $delegate.fromUrl = function (url, params) {
            if (url !== null && angular.isDefined(url) && angular.isString(url)) {
                url += (url.indexOf("?") === -1 ? "?" : "&");
                url += "nocache=" + cacheBuster;
            }

            return fromUrl(url, params);
        };

        return $delegate;
    }

    $provide.decorator('$templateFactory', ['$delegate', templateFactoryDecorator]);
}]);
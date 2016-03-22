app.directive('appTree', function($filter) {
    
    return {
        restrict: 'E',
        templateUrl: 'js/directive/html/appTree.html',
        scope: {
            lista: '=',
            colunas: '=',
            events: '='
        },
        link: function(scope, element, attr, controller) { 
            
            var tree;
            
            var init = function () {
                tree = element.find('.tree');
            };
        
            scope.$watchCollection('lista', function(newValue) {
                tree.append(createStructure(null, newValue));
            });
            
            var createStructure = function(code, items) {
                var html = '<ul>';
                angular.forEach(items, function(item) {
                    var codigo = item.codigo;
                    if(code) { codigo = code + '.' + codigo; }
                    html += '<li>' + codigo + ' - ' + item.nome;
                    if(item.contas) { html += createStructure(codigo, item.contas); }                    
                    html += '</li>';
                });
                html += '</ul>';
                return html;
            }
            
            init();
        }
    }
});
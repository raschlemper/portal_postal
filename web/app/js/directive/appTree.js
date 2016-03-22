app.directive('appTree', function($compile) {
    
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
                scope.open = [];
            };

            scope.openFolder = function(predicate) {
                angular.element('.group_' + predicate).children('ul').toggle();
                scope.open['group_' + predicate] = !scope.open['group_' + predicate];
            };
        
            scope.$watchCollection('lista', function(newValue) {
                tree.html($compile(createStructure(null, newValue))(scope));
            });
            
            var createStructure = function(code, items) {
                if(!items.length) return;
                var html = '<ul>';
                angular.forEach(items, function(item) {                    
                    var codigo = item.codigo;
                    if(code) { codigo = code + '.' + codigo; }
                    
                    if(item.contas) { 
                        scope.open['group_' + item.idPlanoConta] = true;
                        html += '<li class="group_' + item.idPlanoConta + '">';
                        html += '<i class="fa" ' +
                                   'ng-class="{\'fa-folder-open\': open.group_' + item.idPlanoConta + ',' + 
                                              '\'fa-folder\': !open.group_' + item.idPlanoConta + '}"></i>';
                        html += '<span ng-click="openFolder(' + item.idPlanoConta + ')">' + 
                                    codigo + ' - ' + item.nome + 
                                '</span>';
                        html += createStructure(codigo, item.contas); 
                        html += '<span class="pull-right"><i class="fa fa-file"></i></span></li>';
                    } else {          
                        html += '<li>';              
                        html +=  codigo + ' - ' + item.nome;
                        html += '</li>';
                    } 
                    
                });
                html += '</ul>';
                return html;
            }
            
            init();
        }
    }
});
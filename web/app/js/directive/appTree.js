app.directive('appTree', function($compile) {
    
    return {
        restrict: 'E',
        template: '<div class="tree"></div>',
        scope: {
            lista: '=',
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
                if(!items || !items.length) return;
                var html = '<ul>';
                angular.forEach(items, function(item) {                    
                    var codigo = item.codigo;
                    if(code) { codigo = code + '.' + codigo; }
                    
                    if(item.contas) { 
                        scope.open['group_' + item.idPlanoConta] = true;
                        html += '<li class="group_' + item.idPlanoConta + '">';
                        html += '<i class="fa" ng-click="openFolder(' + item.idPlanoConta + ')" ' +
                                   'ng-class="{\'fa-folder-open\': open.group_' + item.idPlanoConta + ',' + 
                                              '\'fa-folder\': !open.group_' + item.idPlanoConta + '}"></i>';
                        if(item.grupo) { 
                            html += '<div class="name">' + codigo + ' - ' + item.nome;
                        } else {
                            html += '<div class="name text-uppercase" style="font-size: 13pt;"><strong>' + codigo + ' - ' + item.nome + '</strong>';
                        }          
                        html += '<span class="pull-right action">';
                        if(item.grupo) {   
                            html += '<i class="fa fa-lg fa-pencil-square" ng-click="events.edit(' + item.idPlanoConta + ')"></i>';                                  
                        }
                        html += '<i class="fa fa-lg fa-plus-square" ng-click="events.add(' + item.idPlanoConta + ')"></i>';
                        html += '</span>';
                        html += '</div>';
                        html += createStructure(codigo, item.contas); 
                        html += '</li>';
                    } else {          
                        html += '<li>';              
                        html += '<i class="fa fa-folder-o"></i>';
                        html += '<div class="name">' + codigo + ' - ' + item.nome;        
                        html += '<span class="pull-right action">';
                        if(item.grupo) {   
                            html += '<i class="fa fa-lg fa-trash" ng-click="events.remove(' + item.idPlanoConta + ')"></i>' +
                                    '<i class="fa fa-lg fa-pencil-square" ng-click="events.edit(' + item.idPlanoConta + ')"></i>';                                  
                        }
                        html += '<i class="fa fa-lg fa-plus-square" ng-click="events.add(' + item.idPlanoConta + ')"></i>';
                        html += '</span>';
                        html += '</div>';
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
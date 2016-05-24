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
                scope.editGrupo = false;  
                scope.open = [];
                tree = element.find('.tree');
                setEditGrupo();
            };
                        
            var setEditGrupo = function() {
                if(attr.editGrupo) { scope.editGrupo = (attr.editGrupo === "true"); }
            };

            scope.openFolder = function(predicate) {
                angular.element('.group_' + predicate).children('ul').toggle();
                scope.open['group_' + predicate] = !scope.open['group_' + predicate];
            };
            
            scope.$watchCollection('lista', function(newValue) {
                tree.html($compile(createStructure(newValue))(scope));
            });
            
            var createStructure = function(items) {
                if(!items || !items.length) return;
                var html = '<ul>';
                angular.forEach(items, function(item) {                     
                    if(item.grupos) { 
                        scope.open['group_' + item.id] = true;
                        html += '<li class="group_' + item.id + '">';
                        html += '<i class="fa" ng-click="openFolder(' + item.id + ')" ' +
                                   'ng-class="{\'fa-folder-open\': open.group_' + item.id + ',' + 
                                              '\'fa-folder\': !open.group_' + item.id + '}"></i>';
                        if(item.grupo) { 
                            html += '<div class="name">' + item.descricao;
                        } else {
                            html += '<div class="name text-uppercase" style="font-size: 13pt;"><strong>' + item.descricao + '</strong>';
                        }          
                        html += '<span class="pull-right action">';
                        if(item.grupo || scope.editGrupo) {   
                            html += '<i class="fa fa-lg fa-pencil-square" ng-click="events.edit(' + item.id + ')"></i>';                                  
                        }
                        html += '<i class="fa fa-lg fa-plus-square" ng-click="events.add(' + item.id + ')"></i>';
                        html += '</span>';
                        html += '</div>';
                        html += createStructure(item.grupos); 
                        html += '</li>';
                    } else {          
                        html += '<li>';              
                        html += '<i class="fa fa-folder-o"></i>';
                        if(item.grupo) { 
                            html += '<div class="name">' + item.descricao;
                        } else {
                            html += '<div class="name text-uppercase" style="font-size: 13pt;"><strong>' + item.descricao + '</strong>';
                        }        
                        html += '<span class="pull-right action">';
                        if(item.grupo || scope.editGrupo) {   
                            html += '<i class="fa fa-lg fa-trash" ng-click="events.remove(' + item.id + ')"></i>';
                            html += '<i class="fa fa-lg fa-pencil-square" ng-click="events.edit(' + item.id + ')"></i>';                                  
                        }
                        html += '<i class="fa fa-lg fa-plus-square" ng-click="events.add(' + item.id + ')"></i>';
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
app.directive('appTypeahead', function($filter) {
    
    return {
        restrict: 'E',
        templateUrl: 'js/directive/html/appTypeahead.html',
        scope: {
            items: '=',
            itemModel: '=',
            filter: '=',
            events: '=?',
            field: "@"
        },
        link: function($scope, element, attr, controller) {                
            
            var init = function () { 
                $scope.showTypeFilter = false;  
                $scope.dropdownVisible = false;
                $scope.items = $scope.items || [];
                setPlaceholder();
                setName();
                setKeepGroup();
                setTypeFilter();
            };
                        
            var setPlaceholder = function() {
                $scope.placeholder = attr.placeholder || "";
            };
                        
            var setName = function() {
                $scope.name = attr.name || "";
            };
                        
            var setKeepGroup = function() {
                if(attr.keepGroup) { $scope.keepGroup = (attr.keepGroup === "true"); }
                else { $scope.keepGroup = false; }
            };     
                        
            var setTypeFilter = function() {
                if(attr.showTypeFilter) { $scope.showTypeFilter = (attr.showTypeFilter === "true"); }
            };  
            
            $scope.setVisible = function(visible) {
                $scope.dropdownVisible = visible; 
            };
            
            $scope.selectItem = function(item) {
                var itemSelected = angular.copy(item);
                if($scope.events) { $scope.itemModel = $scope.events.selectItem(itemSelected); }
                else { $scope.itemModel = itemSelected; }
                $scope.setVisible(false);
            };                      
            
            $scope.selectItemEmpty = function(value) {
                if(value) return;
                $scope.itemModel = null;
            };
            
            $scope.filterList = function(lista, search) {
                if($scope.keepGroup) { return filterByExclude(lista, search); }
                else { return $filter('filter')(lista, search); }
            };
            
            var filterByExclude = function(lista, search) {
                if(!lista || !search) return lista;
                var parents = [];
                var results = lista.filter(function(item) {
                    if(item.keep) return true;
                    var itemLabel = removeAccents(angular.copy(item[$scope.field]));
                    var searchLabel = removeAccents(angular.copy(search));
                    var parse = (itemLabel.toLowerCase().indexOf(searchLabel.toLowerCase()) > -1);
                    if(parse) { parents.push(item.parent); }
                    return parse;
                });
                return ExcludeByEmpty(results, parents);
            };
            
            var ExcludeByEmpty = function(lista, parents) {
                if(!lista || !parents) return lista;
                return lista.filter(function(item) {
                    if(!item.keep) return true;
                    if(_.contains(parents, item.id)) return true;
                    return false;
                })
            }
            
            $scope.getItem = function(item) {
                var value = angular.copy(item[$scope.field]);
                if($scope.filter){ value = $filter($scope.filter.name)(item, $scope.filter.args); }
                return value;
            };
            
            var removeAccents = function(source) {
                var accent = [
                    /[\300-\306]/g, /[\340-\346]/g, // A, a
                    /[\310-\313]/g, /[\350-\353]/g, // E, e
                    /[\314-\317]/g, /[\354-\357]/g, // I, i
                    /[\322-\330]/g, /[\362-\370]/g, // O, o
                    /[\331-\334]/g, /[\371-\374]/g, // U, u
                    /[\321]/g, /[\361]/g, // N, n
                    /[\307]/g, /[\347]/g, // C, c
                ],
                noaccent = ['A','a','E','e','I','i','O','o','U','u','N','n','C','c'];

                for (var i = 0; i < accent.length; i++){
                    source = source.replace(accent[i], noaccent[i]);
                }

                return source;
            }
            
            init();
        }
    }
});
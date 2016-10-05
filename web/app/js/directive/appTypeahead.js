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
                $scope.dropdownVisible = false;
                $scope.items = $scope.items || [];
                setPlaceholder();
                setName();
                setKeepGroup();
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
                    var parse = (item[$scope.field].toLowerCase().indexOf(search.toLowerCase()) > -1);
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
                var value = item[$scope.field];
                if($scope.filter){ value = $filter($scope.filter.name)(item, $scope.filter.args); }
                return value;
            };
            
            init();
        }
    }
});
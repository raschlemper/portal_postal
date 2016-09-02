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
                setPlaceholder();
                setName();
            };
                        
            var setPlaceholder = function() {
                $scope.placeholder = attr.placeholder || "";
            };
                        
            var setName = function() {
                $scope.name = attr.name || "";
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
                return $filter('filter')(lista, search);
            };
            
            $scope.getItem = function(item) {
                var value = item[$scope.field];
                if($scope.filter){ value = $filter($scope.filter.name)(item, $scope.filter.args); }
                return value;
            };
            
            init();
        }
    }
});
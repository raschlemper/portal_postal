app.directive('appTable', function($filter) {
    
    return {
        restrict: 'E',
        templateUrl: 'js/directive/html/appTable.html',
        scope: {
            lista: '=',
            colunas: '=',
            events: '='
        },
        link: function(scope, element, attr, controller) { 
            
            scope.limits = [
                {name: '10', value: 10},
                {name: '25', value: 25},
                {name: '50', value: 50},
                {name: '100', value: 100}];
            
            var init = function () {
                scope.listaFiltrada = [];
                scope.currentPage = 1; 
                scope.maxSize = 5; 
                scope.limitTo = scope.limits[0];
                scope.predicate = scope.colunas[0].column;
                scope.reverse = false;     
            };
            
            var reset = function() {
                getSizeTotal();
                getStart();
                getFinish();
            };
        
            var getStart = function() {
                scope.start = ((scope.currentPage - 1) * scope.limitTo.value);
                scope.from = scope.start + 1;
            } ; 

            var getFinish = function() {
                scope.to = scope.from + scope.limitTo.value - 1;
                if(scope.to > scope.total) { scope.to = scope.total; }
            };
            
            var getSizeTotal = function() {
                scope.total = scope.listaFiltrada.length || scope.lista.length;
            };
            
            scope.column = function(item, coluna) {
                var value = item[coluna.column];
                if(coluna.filter){ value = $filter(coluna.filter.name)(value, coluna.filter.args); }
                return value;
            }
            
            scope.class = function(coluna) {
                return coluna.class;
            }

            scope.order = function(predicate) {
                scope.reverse = (scope.predicate === predicate) ? !scope.reverse : false;
                scope.predicate = predicate;
            };
            
            scope.$watchCollection('listaFiltrada', function(newValue, oldValue) {
                reset();
            });
            
            scope.$watch('currentPage', function(newValue, oldValue) {
                if(newValue === oldValue) return;
                reset();
            });
            
            scope.$watch('limitTo', function(newValue, oldValue) {
                if(newValue === oldValue) return;
                reset();
            });
            
            scope.$watch('filters', function(newValue, oldValue) {
                if(newValue === oldValue) return;
                reset();
            });
            
            init();
        }
    }
});
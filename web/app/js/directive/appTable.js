app.directive('appTable', function($filter) {
    
    return {
        restrict: 'E',
        templateUrl: 'js/directive/html/appTable.html',
        scope: {
            lista: '=',
            colunas: '=',
            events: '=',
            filters: '=?filters'
        },
        link: function(scope, element, attr, controller) { 
                        
            scope.limits = [
                {name: '10', value: 10},
                {name: '25', value: 25},
                {name: '50', value: 50},
                {name: '100', value: 100}];
            
            var init = function () {   
                scope.defaultFilter = true;
                scope.columnSearchNumberPage = 2;                
                scope.currentPage = 1; 
                scope.maxSize = 5; 
                scope.limitTo = scope.limits[0];
                scope.predicate = scope.colunas[0].column;
                scope.reverse = false; 
                setFilterDefault();
                calculateColumnSearchDefault();
            };
            
            var setFilterDefault = function() {
                if(scope.filters) { scope.search = {}; }
                if(attr.defaultFilter) { scope.defaultFilter = (attr.defaultFilter == "true"); }
            }
            
            var calculateColumnSearchDefault = function() {
                var columnTotal = scope.columnSearchNumberPage;
                angular.forEach(scope.filters, function(filter) {
                    columnTotal += filter.numberColumn;
                });
                var rest = columnTotal % 12;
                scope.columnSearchDefault = 12 - rest;
            }
            
            var reset = function() {
                getSizeTotal();
                getStart();
                getFinish();
            };
        
            var getStart = function() {
                scope.start = ((scope.currentPage - 1) * scope.limitTo.value);
                scope.from = 0;
                if(scope.total) { scope.from = scope.start + 1; }
            } ; 

            var getFinish = function() {
                scope.to = scope.from + scope.limitTo.value - 1;
                if(scope.to > scope.total) { scope.to = scope.total; }
            };
            
            var getSizeTotal = function() {
                scope.total = scope.listaFiltrada.length;
            };
            
            scope.column = function(item, coluna) {
                var colunas = coluna.column.split('.');
                var value = getColumn(item, angular.copy(colunas));
                if(scope.filters) { getSearchColumn(scope.search, angular.copy(colunas)); }
                if(coluna.filter){ value = $filter(coluna.filter.name)(value, coluna.filter.args); }
                return value;
            }
            
            var getColumn = function(item, colunas) {
                var value = item[colunas[0]];
                colunas.splice(0, 1);
                if(!colunas.length) return value;
                return getColumn(value, colunas);
            };
            
            var getSearchColumn = function(item, colunas) {
                var coluna = colunas[0]; 
                if(!item[coluna]) { item[coluna] = ''; };              
                colunas.splice(0, 1);
                if(!colunas.length) return;
                getSearchColumn(item[coluna], colunas);
            };
            
            scope.filterList = function(lista) {
                if(scope.searchAll) { lista = $filter('filter')(lista, scope.searchAll); }
                if(scope.search) { lista = $filter('filter')(lista, scope.search); }
                return lista;
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
            
            init();
        }
    }
});
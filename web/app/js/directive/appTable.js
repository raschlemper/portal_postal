app.directive('appTable', function($filter) {
    
    return {
        restrict: 'E',
        templateUrl: 'js/directive/html/appTable.html',
        scope: {
            lista: '=',
            colunas: '=',
            linha: '=',
            filter: '=',
            events: '=',
            search: '='
        },
        transclude: {
            'filterContent': '?filterContent'
        },
        link: function(scope, element, attr, controller, transclude) { 
                        
            scope.limits = [
                {name: '10', value: 10},
                {name: '25', value: 25},
                {name: '50', value: 50},
                {name: '100', value: 100}];
            
            var init = function () {   
                scope.showCheckbox = false;  
                scope.showMenu = false;        
                scope.defaultFilter = true;      
                scope.currentPage = 1; 
                scope.maxSize = 5; 
                scope.limitTo = scope.limits[0];
                scope.predicate = getPredicate(scope.colunas);
                scope.reverse = false; 
                setCheckBox();
                setFilterDefault();
            };
            
            var getPredicate = function(colunas) {
                if(attr.orderByColumn) { return attr.orderByColumn; }
                var colunaSorter = null;
                angular.forEach(colunas, function(coluna, i) {
                    if(colunaSorter) return;
                    if(!coluna.class) { colunaSorter = coluna; }
                    else if(coluna.class.indexOf("no-sort") == -1) { colunaSorter = coluna; }
                })
                return colunaSorter.column;
            }
                        
            var setCheckBox = function() {
                if(attr.showCheckbox) { scope.showCheckbox = (attr.showCheckbox === "true"); }
            };
            
            var setFilterDefault = function() {
                if(attr.defaultFilter) { scope.defaultFilter = (attr.defaultFilter === "true"); }
            };
            
            var reset = function() {
                getSizeTotal();
                getStart();
                getFinish();
                scope.orderBy();
                eventTable(scope.listaFiltrada);
            };
            
            var eventTable = function(items) {                
                if(scope.events && scope.events.table) { scope.events.table(items); }
            }
        
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
                if(!scope.listaFiltrada) { scope.total = 0; }
                else { scope.total = scope.listaFiltrada.length; }
            };
            
            scope.column = function(item, coluna) {
                var colunas = coluna.column.split('.');
                var value = getColumn(item, angular.copy(colunas));
                if(coluna.filter){ value = $filter(coluna.filter.name)(value, coluna.filter.args); }
                return value;
            }
            
            var getColumn = function(item, colunas) {
                var value = item[colunas[0]];
                colunas.splice(0, 1);
                if(!colunas.length) return value;
                return getColumn(value, colunas);
            };
            
            scope.filterList = function(lista, search) {
                if(scope.searchAll) { lista = $filter('filter')(lista, scope.searchAll); }
                if(scope.filter) { lista = scope.filter(lista, search); }
                return lista;
            }
            
            scope.class = function(coluna) {
                return coluna.class;
            }

            scope.order = function(lista, predicate, reverse) {
                scope.reverse = (scope.predicate === predicate) ? !reverse : false;
                scope.predicate = predicate;
                reset();
            };

            scope.orderBy = function() {
                scope.listaFiltrada = $filter('orderBy')(scope.listaFiltrada, scope.predicate, scope.reverse);
            };
            
            scope.events.list = function() {
                return scope.listaFiltrada;
            }
            
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
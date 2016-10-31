app.directive('appTable', function($filter) {
    
    return {
        restrict: 'E',
        templateUrl: 'js/directive/html/appTable.html',
        scope: {
            lista: '=',
            colunas: '=',
            linha: '=',
            filter: '=',
            events: '=?',
            search: '=',
            title: '@'
        },
        transclude: {
            'filterContent': '?filterContent',
            'titleContent': '?titleContent',
            'rodapeContent': '?rodapeContent'
        },
        link: function(scope, element, attr, controller, transclude) {            
                        
            scope.limits = [
                {name: '25',  value: 25 },
                {name: '50',  value: 50 },
                {name: '75',  value: 75 },
                {name: '100', value: 100}];
            
            scope.events = scope.events || {};
            
            var init = function () {   
                scope.showCheckbox = false;  
                scope.showMenu = false;        
                scope.defaultFilter = true;    
                scope.showColumnMenu = false;        
                scope.currentPage = 1; 
                scope.maxSize = 5; 
                scope.limitTo = scope.limits[0];
                scope.predicate = getPredicate(scope.colunas);
                scope.reverse = false; 
                scope.colunasSelected = [];
                setCheckBox();
                setMenu();
                setFilterDefault();
                setColumnMenu();
                setSelected();
                setLastPage();
            };
            
            var setSelected = function() {
                _.map(scope.colunas, function(coluna) {
                    if(angular.isUndefined(coluna.showColumn)) { coluna.showColumn = false; }   
                    if(angular.isUndefined(coluna.selected)) { coluna.selected = true; }
                })
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
            }; 
                        
            var setCheckBox = function() {
                if(attr.showCheckbox) { scope.showCheckbox = (attr.showCheckbox === "true"); }
            };
                        
            var setMenu = function() {
                if(attr.showMenu) { scope.showMenu = (attr.showMenu === "true"); }
            };
            
            var setFilterDefault = function() {
                if(attr.defaultFilter) { scope.defaultFilter = (attr.defaultFilter === "true"); }
            };
            
            var setColumnMenu = function() {
                if(attr.showColumnMenu) { scope.showColumnMenu = (attr.showColumnMenu === "true"); }
            };
            
            var setLastPage = function() {
                if(attr.lastPage) { scope.lastPage = (attr.lastPage === "true"); }
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
                if(coluna.filter && coluna.filter.callback){ value = $filter(coluna.filter.name)(value, coluna.filter.callback); }
                else if(coluna.filter){ value = $filter(coluna.filter.name)(value, coluna.filter.args); }
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
            
            scope.showColumn = function(event, coluna) {
                event.stopPropagation();
                coluna.selected = !coluna.selected;
            }
            
            scope.$watchCollection('selectAll', function(newValue, oldValue) {
                _.map(scope.listaFiltrada, function(item) {
                    item.selected = newValue;
                });    
            });
            
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
            
            scope.$watch('numPages', function(newValue, oldValue) {
                if(!scope.lastPage) return;
                if(newValue === oldValue) return;   
                scope.currentPage = scope.numPages; 
            });
            
            init();
        }
    }
});
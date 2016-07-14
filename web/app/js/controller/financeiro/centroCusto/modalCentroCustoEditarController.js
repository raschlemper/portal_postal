'use strict';

app.controller('ModalCentroCustoEditarController', 
    ['$scope', '$modalInstance', '$filter', 'centroCusto', 'action', 'CentroCustoService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, $filter, centroCusto, action, CentroCustoService, ListaService, LISTAS) {

        var init = function () {  
            $scope.action = action;
            $scope.grupos = [];
            $scope.gruposList = [];
            $scope.isGroup = isGroup();
            $scope.centroCusto = angular.copy(centroCusto) || {};
            if(action === 'save') { create(); }
            getTitle();  
            centroCusto();          
        };    
                
        // ***** CONTROLLER ***** //   
        
        var getTitle = function() {
            if(action && action === 'edit') { $scope.title = "Editar Centro Custo"; }
            else { $scope.title = "Inserir Centro Custo"; }
        }    
        
        var isGroup = function() {
            if(action === 'save') return false;
            if(action === 'grupo') return true;
            if(!centroCusto.grupo) return true;
            if(centroCusto.centros) return true;
            return false;            
        }
        
        var create = function() {
            $scope.centroCusto.idCentroCusto = null;
            $scope.centroCusto.codigo = null;
            $scope.centroCusto.nome = null;     
            $scope.centroCusto.grupo = centroCusto;            
        }

        var centroCusto = function() {
            CentroCustoService.getStructure()
                .then(function(data) {
                    $scope.grupos = data;
                    CentroCustoService.estrutura($scope.grupos);
                    $scope.gruposList = CentroCustoService.flatten($scope.grupos);
                    $scope.gruposList = $filter('orderBy')($scope.gruposList, 'descricao', false);
                    if($scope.centroCusto.grupo) { 
                        $scope.centroCusto.grupo = ListaService.getCentroCustoValue($scope.gruposList, $scope.centroCusto.grupo.idCentroCusto);
                    }
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.centroCusto);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
                
        // ***** VALIDAR ***** //  

        var validarForm = function (form) {
            if (form.nome.$error.required) {
                alert('Preencha o nome do centro de custo!');
                return false;
            }
            if (form.codigo.$error.required) {
                alert('Preencha o codigo do centro de custo!');
                return false;
            }
            return true;
        }     

        init();

    }]);

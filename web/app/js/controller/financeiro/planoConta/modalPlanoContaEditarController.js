'use strict';

app.controller('ModalPlanoContaEditarController', 
    ['$scope', '$modalInstance', '$filter', 'planoConta', 'action', 'PlanoContaService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, $filter, planoConta, action, PlanoContaService, ListaService, LISTAS) {

        var init = function () {  
            $scope.tipos = LISTAS.planoConta; 
            $scope.action = action;
            $scope.grupos = [];
            $scope.gruposList = [];
            $scope.isGroup = isGroup();
            $scope.planoConta = angular.copy(planoConta) || {};
            $scope.planoConta.tipo = (planoConta && planoConta.tipo) || $scope.tipos[0]; 
            if(action === 'save') { create(); }
            getTitle();      
            todos();      
        };      
                
        // ***** CONTROLLER ***** //  
        
        var getTitle = function() {
            if(action && action === 'edit') { $scope.title = "Editar Plano Conta"; }
            else { $scope.title = "Inserir Plano Conta"; }
        }   
        
        var isGroup = function() {
            if(action === 'save') return false;
            if(planoConta.contas) return true;
            return false;            
        }
        
        var create = function() {
            $scope.planoConta.idPlanoConta = null;
            $scope.planoConta.codigo = null;
            $scope.planoConta.nome = null;     
            $scope.planoConta.grupo = planoConta;            
        }

        var todos = function() {
            PlanoContaService.getStructureByTipo(planoConta.tipo.id)
                .then(function(data) {
                    $scope.grupos = data;
                    PlanoContaService.estrutura($scope.grupos);
                    $scope.gruposList = PlanoContaService.flatten($scope.grupos);
                    $scope.gruposList = $filter('orderBy')($scope.gruposList, ['codigo','descricao'], false);
                    $scope.planoConta.grupo = ListaService.getPlanoContaValue($scope.gruposList, $scope.planoConta.grupo.idPlanoConta);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.planoConta);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
                
        // ***** VALIDAR ***** //  

        var validarForm = function (form) {
            if (form.nome.$error.required) {
                alert('Preencha o nome do plano de conta!');
                return false;
            }
            if (form.codigo.$error.required) {
                alert('Preencha o codigo do plano de conta!');
                return false;
            }
            return true;
        }     

        init();

    }]);

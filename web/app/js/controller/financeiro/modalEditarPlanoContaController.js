'use strict';

app.controller('ModalEditarPlanoContaController', ['$scope', '$modalInstance', 'planoConta', 'action', 'PlanoContaService', 'ListaService', 'LISTAS',
    function ($scope, $modalInstance, planoConta, action, PlanoContaService, ListaService, LISTAS) {

        var init = function () {  
            $scope.tipos = LISTAS.planoConta; 
            $scope.action = action;
            if(action === 'save') { create(); }
            else { todos(); }
            getTitle();
            
        }; 

        var todos = function() {
            PlanoContaService.getAll()
                .then(function(data) {
                    $scope.grupos = data;
                    planoConta.grupo = ListaService.getPlanoContaValue($scope.grupos, planoConta.grupo.idPlanoConta);
                    edit(data);
                })
                .catch(function(e) {
                    modalMessage(e);
                });
        };
        
        var create = function() {
            $scope.planoConta = {
                idPlanoConta: null,
                tipo: (planoConta && planoConta.tipo) || $scope.tipos[0],            
                codigo: null,
                nome: null,
                grupo: planoConta.nome 
            }; 
        }
        
        var edit = function(data) {
            $scope.planoConta = {
                idPlanoConta: (planoConta && planoConta.idPlanoConta) || null,
                tipo: (planoConta && planoConta.tipo) || $scope.tipos[0],   
                codigo: (planoConta && planoConta.codigo) || null,
                nome: (planoConta && planoConta.nome) || null,                
                grupo: (planoConta && planoConta.grupo) || null
            }; 
        }
        
        var getTitle = function() {
            if(action && action === 'edit') { $scope.title = "Editar Plano Conta"; }
            else { $scope.title = "Inserir Novo Plano Conta"; }
        }
        
        $scope.ok = function(form) {
            if (!validarForm(form)) return;
            $modalInstance.close($scope.planoConta);
        };
        
        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

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

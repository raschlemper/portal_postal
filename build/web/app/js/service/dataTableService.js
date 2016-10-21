'use strict';

app.factory('DataTableService', function(DTOptionsBuilder) {
    
    var loadDataTableScript = function(table) {
        LoadDataTablesScripts(sortAndSearchTable(table));
    }
    
    var sortAndSearchTable = function(table) {
        return function() {
            StartDataTable(table);            
            LoadSelect2Script(MakeSelectDataTable(table));    
        }    
    }

    return {
        
        origin: function(table) {
            loadDataTableScript(table);
        },

        default: function() {
            return DTOptionsBuilder.newOptions()
            .withDOM("<'box-content'<'col-xs-6 col-sm-6' f><'col-xs-6 col-sm-6 text-right'l><'clearfix'>>rt<'box-content'<'col-xs-6 col-sm-6'i><'col-xs-6 col-sm-6 text-right'p><'clearfix'>>")
            .withOption("aaSorting", [[ 0, "asc" ]])
            .withBootstrap()
            .withBootstrapOptions({
                pagination: {
                    classes: {
                        ul: 'pagination pagination-sm'
                    }
                }
            })
            .withLanguage({
                "sSearch": "Pesquisar: ",
                "sEmptyTable": "Nenhum registro encontrado",
                "sInfo": "Mostrando de _START_ at\u00e9 _END_ de _TOTAL_ registros",
                "sInfoEmpty": "",
                "sInfoFiltered": "(Filtrados de _MAX_ registros)",
                "sInfoPostFix": "",
                "sInfoThousands": ".",
                "sLengthMenu": "_MENU_",
                "sLoadingRecords": "Carregando...",
                "sProcessing": "Processando...",
                "sZeroRecords": "Nenhum registro encontrado",
                "oPaginate": {
                    "sNext": "Pr\u00f3ximo",
                    "sPrevious": "Anterior",
                    "sFirst": "Primeiro",
                    "sLast": "\u00daltimo"
                }
            });
        }

    }

});
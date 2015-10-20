<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>simpleAutoComplete JQuery Plugin</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <meta http-equiv="Content-Language" content="pt-BR en">
        <script type="text/javascript" src="js/jquery.js"></script>


        <script type="text/javascript" src="js/simpleAutoComplete.js"></script>
        <link rel="stylesheet" type="text/css" href="css/simpleAutoComplete.css" />
        <script type="text/javascript">
            $(document).ready(function(){

                $('#estado_autocomplete').simpleAutoComplete('ajax_query.jsp',{
                    autoCompleteClassName: 'autocomplete',
                    selectedClassName: 'sel',
                    attrCallBack: 'rel',
                    identifier: 'estado'
                },estadoCallback);

            });
	
            function estadoCallback( par ){
                $("#id_estado").val( par[0] );
                $("#uf1").val( par[1] );
                $("#cidade_autocomplete").removeAttr("disabled");
                $("#cidade_autocomplete, #uf2, #id_cidade").val("");
            }
	
        </script>
    </head>
    <body>
        <%--Pagina do plugin --> http://blog.idealmind.com.br/projetos/simple-autocomplete-jquery-plugin/--%>
        <div>
            <a href=""></a>
        </div>
        <div style="margin-left:100px;">
            <h2>Digite um cliente:</h2>
            <input type="text" id="estado_autocomplete" name="estado" autocomplete="on" style="width: 250px; height: 23px;" />
            <input type="text" name="uf1" id="uf1" disabled />
            <input type="text" id="id_estado" name="id_estado" disabled />
        </div>
    </body>
</html>
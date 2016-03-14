<%@page import="Entidade.LogAtualizacaoContratos"%>
<%@page import="Controle.ContrLogAtualizacaoContrato"%>
<%@page import="Entidade.Clientes"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else {
        String nomeBD = (String) session.getAttribute("empresa");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>
    <body>   
        <script type="text/javascript">
            waitMsg();
        </script>
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                        <div class="row">
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears"></i> Cadastros</b> > <small>Criar Login do Portal Postal para os Clientes</small></h4>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-sm-12">
                                <ul class="list-group">
                                    <li class="list-group-item list-group-heading">
                                        <div class="row form-horizontal">
                                            <div class="col-sm-12 col-md-6 col-lg-6">
                                                <label class="small">Senha</label>                                            
                                                <div class="input-group">
                                                    <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                                                    <input class="form-control" type="text" name="senha" placeholder="Digite uma senha em comum para todos os logins..." onkeyup="defineSenha(this.value);" onblur="defineSenha(this.value);" />                            
                                                </div>
                                            </div>
                                            <div class="col-sm-12 col-md-12 col-lg-12" style="color:red;">
                                                <label class="small">*Esta senha acima será definida para todos os logins da tabela abaixo.</label>  <br/>
                                                <label class="small">*Caso queira alterar um login/senha em específico, altere diretamente na tabela abaixo.</label>  
                                                <label class="small">*Os usuários criados serão Administradores e terão todos os acessos liberados no Portal Postal.</label>  
                                            </div>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <form class="form-inline" name="form1" action="../../ServClienteLoginMassa" method="post">
                            <div class="row">
                                <div class="col-md-12">   
                                    <div class="panel panel-default">
                                        <div class="panel-heading">Lista de Todos os Clientes</div>
                                        <div class="panel-body">
                                            <div class="dataTable_wrapper no-padding">
                                                <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                    <thead>
                                                        <tr>
                                                            <th width="70">Nº</th>
                                                            <th>Razão Social</th>
                                                            <th>Nome Fantasia</th>
                                                            <th width="120">Login Sugerido</th>
                                                            <th width="120">Senha</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%
                                                            ArrayList<Clientes> listaCliente = Controle.contrCliente.consultaClientesSemLoginPortalPostal(nomeBD);
                                                            for (int j = 0; j < listaCliente.size(); j++) {
                                                                Clientes cli = listaCliente.get(j);
                                                        %>
                                                        <tr>
                                                            <td align="right"><%= cli.getCodigo()%></td>
                                                            <td style="font-size: 11px;"><%= cli.getNome()%></td>
                                                            <td style="font-size: 11px;"><%= cli.getNomeFantasia()%></td>
                                                            <td>
                                                                <input class="form-control" name="idCliente" id="idCliente" type="hidden" value="<%= cli.getCodigo()%>" />
                                                                <input class="form-control" name="nome<%= cli.getCodigo()%>" id="nome" type="hidden" value="<%= cli.getNome()%>" />
                                                                <input class="form-control" name="login<%= cli.getCodigo()%>" id="login" type="text" value="<%= cli.getLogin_correio().toLowerCase()%>" />
                                                            </td>
                                                            <td><input class="form-control" name="senha<%= cli.getCodigo()%>" id="senha" type="text" /></td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>                                                
                            <div class="row">
                                <div class="well well-md"> 
                                    <a href="#"  class="btn btn-success export"> <i class="fa fa-lg fa-spc fa-file-excel-o"></i> <b>SALVAR ARQUIVO E GERAR SENHAS!</b></a>                                                                     
                                </div>
                            </div>
                        </form>

                        <div class="row spacer-xlg"></div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">

            // This must be a hyperlink
            $(".export").on('click', function (event) {
                    //CSV
                    var ok = 1;
                    var csv = "CODIGO;NOME;LOGIN;SENHA\r\n";
                    $('[name^="idCliente"]').each(function () {
                        if($('[name=login' + this.value + ']').val() === ''){
                            console.log('sdasda');
                            ok = 0;
                        }else if($('[name=senha' + this.value + ']').val() === '') {
                            console.log('sd12312321asda');
                            ok = 0;
                        }                        
                        
                        csv += this.value;
                        csv += ";" + $('[name=nome' + this.value + ']').val();
                        csv += ";" + $('[name=login' + this.value + ']').val();
                        csv += ";" + $('[name=senha' + this.value + ']').val();
                        csv += "\r\n";
                    });

                    if(ok === 1){

                        //Data URI
                        var csvData = 'data:application/csv;charset=utf-8,' + encodeURIComponent(csv);

                        //IF CSV, don't do event.preventDefault() or return false
                        //We actually need this to be a typical hyperlink
                        $(this).attr({
                            'download': "Logins Criados.csv",
                            'href': csvData,
                            'target': '_blank'
                        });
                        
                        waitMsg();
                        document.form1.submit();
                    
                    }else{
                        alert('Defina login e senha para todos os clientes!');                        
                    }
                    
            });


            function verificaCampos() {
                $('[name^="login"]').each(function () {
                    if (this.value === '') {
                        alert('Defina login e senha para todos os clientes!');
                        return false;
                    }
                });
                $('[name^="senha"]').each(function () {
                    if (this.value === '') {
                        alert('Defina login e senha para todos os clientes!');
                        return false;
                    }
                });
                return true;
            }

            function defineSenha(senha) {
                $('[name^="senha"]').each(function () {
                    $(this).val(senha);
                });
            }

            function AllTables() {
                StartDataTableOptions('dataTables-example', -1, 0, 'asc', true, 'simple_numbers');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
                fechaMsg();
            }

            $(document).ready(function () {
                LoadDataTablesScripts(AllTables);
            });
        </script>
    </body>
</html>
<%}%>
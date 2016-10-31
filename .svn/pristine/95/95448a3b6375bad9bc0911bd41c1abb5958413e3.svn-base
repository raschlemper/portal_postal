
<%@page import="Entidade.Contato"%>
<%@page import="Entidade.ClienteSMTP"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="Entidade.GrupoFaturamento"%>
<%@page import="Controle.ContrGrupoFaturamento"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Entidade.ServicoECT"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevent caching at the proxy server

    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("405")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {
        empresas agf_empresa = (empresas) session.getAttribute("agf_empresa");
        int idClienteInc = Integer.parseInt(request.getParameter("idCliente"));
        Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idClienteInc, agf_empresa.getCnpj());

%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />     
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>

        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
        <script type="text/javascript">

            $(document).ready(function () {
                // load();
                fechaMsg();
            });

            function confirmExcluir() {
                bootbox.confirm({
                    title: 'Excluir Contato do Cliente?',
                    message: 'Deseja realmente excluir este contato?',
                    buttons: {
                        'cancel': {
                            label: '<i class="fa fa-lg fa-times fa-spc"></i> CANCELAR',
                            className: 'btn btn-default pull-left'
                        },
                        'confirm': {
                            label: '<i class="fa fa-lg fa-trash fa-spc"></i> EXCLUIR',
                            className: 'btn btn-danger pull-right'
                        }
                    },
                    callback: function (result) {
                        if (result) {
                            $('#delet').submit();
                        }
                    }
                });

            }
            function validaForm() {
                if (!$('#nome').val()) {
                    alert('Preencha o nome');
                    return false;
                } else if (!$('#telefone').val()) {
                    alert('Preencha o telefone');
                    return false;
                } else if (!$('#email').val()) {
                    alert('Preencha o email');
                    return false;
                } else if (!$('#aniversario').val()) {
                    $('#aniversario').val('00/00/0000');

                } else if ($('#aniversario').val().length < 10) {
                    alert('Data de aniverssario incorreta ' + $('#aniversario').val().length);
                    return false;
                } else {
                    document.form1.submit();
                }
            }
            function mostraCampos() {
                 $('#campos').toggleClass('hidden');
            }


        </script>
       
        <style>
            .evenrow {
                background:#fff
            }
            .oddrow {
                background: #EEEEEE 
            }
            a.editable-click { 
                color: black;
                border-bottom: none;
            }
            a.editable-empty {
                color: #9e9e9e;
            }
        </style>
    </head>        
    <body>   
        <script type="text/javascript">
            // waitMsg();
        </script> 
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                        <jsp:include page="cliente_menu_b.jsp" >
                            <jsp:param name="nomeBDTab" value="<%= agf_empresa.getCnpj()%>" />
                            <jsp:param name="activeTab" value="1" />
                            <jsp:param name="idClienteTab" value="<%= idClienteInc%>" />
                            <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato()%>" />
                            <jsp:param name="nomeClienteTab" value="<%= cliInc.getNomeFantasia()%>" />
                        </jsp:include>  
                        <div class="row">
                            <div class="well well-md"> 
                                <button class="btn btn-success" type="button" onclick="mostraCampos();" name="save" id="sub"> 
                                    <i class="fa fa-user-plus" aria-hidden="true"></i> ADCIONAR NOVO CONTATO</button>
                            </div>
                        </div>
                        <div class="row hidden" id="campos">
                            <div class="col-xs-12">
                                <form name="form1" action="../../ServInsertContato" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <b><i class="fa fa-lg fa-spc fa-user"></i>&nbsp;&nbsp;&nbsp;DADOS DO NOVO CONTATO</b>
                                        </li>
                                        <li class="list-group-item" >
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Nome *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-user fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="nome" id="nome" value="" maxlength="60" />
                                                    </div>
                                                </div>

                                                <div class="col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">E-mail *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-at fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="email" id="email" value="" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">Telefone *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-phone fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="telefone" id="telefone" value="" onKeyPress="mascara(this, maskTelefone)"  />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Setor</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-object-group fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="setor" value="" maxlength="25" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Aniversario</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-smile-o fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="aniversario" id="aniversario" value="" onKeyPress="mascara(this, maskData)" maxlength="10" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">Nível de Relacionamento</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-hart-o fa-fw"></i></span>  
                                                        <select class="form-control input-sm" name="nivel">
                                                            <option value="0">Baixo</option>
                                                            <option value="1">Médio</option>
                                                            <option value="2">Alto</option>
                                                            <option value="3">Altissimo</option>
                                                        </select>                                                     
                                                    </div>
                                                </div>
                                                <div class="col-sm-8 col-md-8 col-lg-8">
                                                    <label class="small">site [url]</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-facebook-official"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="site"  value="" maxlength="100" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-8 col-md-8 col-lg-8">
                                                    <label class="small">Obs</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-plus-circle fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="observ"  value="" maxlength="100" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-3 col-md-3 col-lg-3">  
                                                    <input type="hidden" name="idCli" value="<%=idClienteInc%>"/>                                               
                                                    <button type="button" style="float: right;" onclick="validaForm();" class="btn btn-success">SALVAR</button>
                                                </div>
                                            </div>
                                        </li>                
                                    </ul>

                                </form> 
                            </div>
                        </div>



                        <div class="row">
                            <div class="col-xs-12">                               
                                <ul class="list-unstyled">
                                    <li class="list-group-item list-group-heading">
                                        <b><i class="fa fa-lg fa-spc fa-user"></i>&nbsp;&nbsp;&nbsp;CONTATOS DO CLIENTE</b>
                                    </li>
                                    <li class="list-group-item" >
                                        <table class="table table-condensed">
                                            <thead>
                                                <tr>
                                                    <th>Nome</th>
                                                    <th>e-mail</th>
                                                    <th>Telefone</th>
                                                    <th>Setor</th>
                                                    <th>Aniver.</th>
                                                    <th>Relacionamento</th>
                                                    <th>Excluir</th>
                                                </tr>
                                            </thead>
                                            <tbody>    

                                                <%
                                                    ArrayList<Contato> ctt = Controle.contrContato.consultaContatos(idClienteInc, agf_empresa.getCnpj());
                                                    int i = 0;
                                                    String odeve = "oddrow";
                                                    for (Contato contato : ctt) {
                                                        if (i % 2 == 0) {
                                                            odeve = "evenrow";
                                                        } else {
                                                            odeve = "oddrow";
                                                        }
                                                        i++;

                                                        String dt = "00/00/0000";
                                                        try {
                                                            String aux[] = contato.getAniver().split("-");
                                                            dt = aux[2] + "/" + aux[1] + "/" + aux[0];

                                                        } catch (Exception ex) {

                                                        }
                                                        if (contato.getEmail() == null) {
                                                            contato.setEmail("");
                                                        }
                                                        if (contato.getFoneramal() == null) {
                                                            contato.setFoneramal("");
                                                        }
                                                        if (contato.getSetor() == null) {
                                                            contato.setSetor("");
                                                        }
                                                        if (contato.getUrl() == null) {
                                                            contato.setUrl("");
                                                        }
                                                        if (contato.getObs() == null) {
                                                            contato.setObs("");
                                                        }

                                                %>
                                                <tr>
                                                    <td rowspan ="3" class="<%=odeve%>"><a href="#" class="edita" data-type="text" data-pk="<%=contato.getIdContato()%>" data-name="contato" data-url="ajax/editContato.jsp" ><%=contato.getContato()%></a></td>
                                                    <td class="<%=odeve%>"></td>
                                                    <td class="<%=odeve%>"></td>
                                                    <td class="<%=odeve%>"></td>
                                                    <td class="<%=odeve%>"></td>
                                                    <td class="<%=odeve%>"></td>
                                                    <td rowspan ="3" class="<%=odeve%>">
                                                        <form id="delet" name="delet" action="../../ServDeleteContato" method="post">
                                                            <input type="hidden" name="idContato" value="<%=contato.getIdContato()%>"/>
                                                            <button type="button" class="btn btn-danger" onclick="confirmExcluir()"><i class="fa fa-trash"></i></button>
                                                        </form>
                                                    </td>
                                                </tr>
                                                <tr>                                                   
                                                    <td style="border-top: none;" class="<%=odeve%>"><a href="#" class="edita" data-type="text" data-pk="<%=contato.getIdContato()%>" data-name="email" data-url="ajax/editContato.jsp" ><%=contato.getEmail()%></a></td>
                                                    <td style="border-top: none;" class="<%=odeve%>"><a href="#" class="tel" data-type="text" data-pk="<%=contato.getIdContato()%>" data-name="foneRamal" data-url="ajax/editContato.jsp" dita ><%=contato.getFoneramal()%></a></td>
                                                    <td style="border-top: none;" class="<%=odeve%>"><a href="#" class="edita" data-type="text" data-pk="<%=contato.getIdContato()%>" data-name="setor" data-url="ajax/editContato.jsp" ><%=contato.getSetor()%></a></td>
                                                    <td style="border-top: none;" class="<%=odeve%>"><a href="#" class="aniver" data-type="date" data-pk="<%=contato.getIdContato()%>" data-name="aniver" data-url="ajax/editContato.jsp" data-format="dd/mm/yyyy"><%=dt%></a></td>                                                   
                                                    <td style="border-top: none;" class="<%=odeve%>"><a href="#" class="nivel" data-type="select" data-pk="<%=contato.getIdContato()%>" data-name="nivel" data-value="<%=contato.getNivel()%>" ></a></td>                                                   
                                                </tr>
                                                <tr>
                                                    <td colspan="2" class="<%=odeve%>">[url] <a href="#" class="edita" data-type="text" data-pk="<%=contato.getIdContato()%>" data-name="url" data-url="ajax/editContato.jsp" ><%=contato.getUrl()%></a></td>  
                                                    <td colspan="3" class="<%=odeve%>">[obs] <a href="#" class="edita" data-type="text" data-pk="<%=contato.getIdContato()%>" data-name="obs" data-url="ajax/editContato.jsp" ><%=contato.getObs()%></a></td>  
                                                </tr>
                                                <% }%>
                                            </tbody>
                                        </table>
                                    </li> 
                                </ul>
                            </div>
                        </div>


                    </div>
                </div>
            </div>
        </div>
    </body>
    <script>

        function maskTelefone(v) {
            if (v.length < 15) {
                v = v.replace(/\D/g, "")                 //Remove tudo o que nao e digito
                v = v.replace(/^(\d\d)(\d)/g, "($1) $2") //Coloca parenteses em volta dos dois primeiros digitos
                v = v.replace(/(\d{4})(\d)/, "$1-$2")    //Coloca hifen entre o quarto e o quinto digitos        
            } else {
                v = v.replace(/\D/g, "")                 //Remove tudo o que nao e digito
                v = v.replace(/^(\d\d)(\d)/g, "($1) $2") //Coloca parenteses em volta dos dois primeiros digitos
                v = v.replace(/(\d{5})(\d)/, "$1-$2")    //Coloca hifen entre o quarto e o quinto digitos
            }
            v = v.substring(0, 15);
            return v
        }

        function maskData(v) {

            v = v.replace(/\D/g, "")                 //Remove tudo o que nao e digito
            v = v.replace(/(\d{2})(\d)/, "$1/$2")    //Coloca um ponto entre o terceiro e o quarto digitos
            v = v.replace(/(\d{2})(\d)/, "$1/$2")    //Coloca um ponto entre o terceiro e o quarto digitos
            v = v.substring(0, 10);
            return v
        }
        
        $.fn.editable.defaults.mode = 'inline';
        $.fn.editable.defaults.showbuttons = false;
        $('.edita').editable();
        $('.tel').editable();
        $('.tel').on('shown', function (e, editable) {
            $(this).data('editable').input.$input.keyup(function (event) {
                var v = maskTelefone($(this).val());
                $(this).val(v);
            });
        });

        $('.aniver').editable();
        $('.aniver').on('shown', function () {
            $(this).data('editable').input.$input.keyup(function (event) {
                var v = maskData($(this).val());
                $(this).val(v);
            });
        });

        $('.nivel').editable({
            url: 'ajax/editContato.jsp',
            source: [
                {value: 0, text: 'baixo'},
                {value: 1, text: 'médio'},
                {value: 2, text: 'alto'},
                {value: 3, text: 'altissimo'}
            ]
        });


    </script>
</html>
<%}%>

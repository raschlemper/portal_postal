<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Entidade.ServicoECT"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    
    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("405")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {
        String nomeBD = (String) session.getAttribute("empresa");
        int idClienteInc = Integer.parseInt(request.getParameter("idCliente"));
        Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idClienteInc, nomeBD);
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

                        <jsp:include page="cliente_menu_b.jsp" >
                            <jsp:param name="nomeBDTab" value="<%= nomeBD%>" />
                            <jsp:param name="activeTab" value="5" />
                            <jsp:param name="idClienteTab" value="<%= idClienteInc%>" />
                            <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato()%>" />
                            <jsp:param name="nomeClienteTab" value="<%= cliInc.getNomeFantasia()%>" />
                        </jsp:include>    

                        <div class="row">
                            <div class="col-xs-12">

                                <form name="form1" action="../../ServClienteOutrosServ" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <b>ESCOLHA OS OUTROS SERVIÇOS QUE SERVEM PARA ESTE CLIENTE</b>
                                        </li>
                                        <li class="list-group-item">
                                            <ul class="list-inline">
                                                <li>
                                                    <ul class="list-unstyled"> 
                                                        <li class="list-group-item list-group-item-danger">
                                                            <label>SERVIÇOS DISPONÍVEIS</label>
                                                        </li>
                                                        <li class="list-group-item list-group-item-danger">
                                                            <select class="form-control" style="width:300px;" name="servico_1" id="servico_1" size="10">
                                                                <%
                                                                    ArrayList<Integer> listaOutros = ContrClienteContrato.consultaOutrosServicosCliente(idClienteInc, nomeBD);
                                                                    ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicos(0, 1, "OSV");
                                                                    if (listaServ != null) {
                                                                        for (int i = 0; i < listaServ.size(); i++) {
                                                                            ServicoECT sv = listaServ.get(i);
                                                                            if (!listaOutros.contains(sv.getCodECT())) {
                                                                                out.println("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + "'>" + sv.getNomeServico() + "</option>");
                                                                            }
                                                                        }
                                                                    }
                                                                %>
                                                            </select>
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li>
                                                    <button onclick="trocaServ('servico_1', 'servico_2');" type="button" class="btn btn-success" style="width: 135px;" ><i class="fa fa-lg fa-plus fa-spc"></i> ADICIONAR</button>
                                                    <br/><br/>
                                                    <button onclick="trocaServ('servico_2', 'servico_1');" type="button" class="btn btn-danger" style="width: 135px;" ><i class="fa fa-lg fa-minus fa-spc"></i> REMOVER</button>
                                                </li>
                                                <li>
                                                    <ul class="list-unstyled"> 
                                                        <li class="list-group-item list-group-item-success">
                                                            <label>SERVIÇOS LIBERADOS PARA O CLIENTE</label>
                                                        </li>
                                                        <li class="list-group-item list-group-item-success">
                                                            <select class="form-control" style="width:300px;" name="servico_2" id="servico_2" size="10"> 
                                                                <%
                                                                    if (listaServ != null) {
                                                                        for (int i = 0; i < listaServ.size(); i++) {
                                                                            ServicoECT sv = listaServ.get(i);
                                                                            if (listaOutros.contains(sv.getCodECT())) {
                                                                                out.print("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + "'>" + sv.getNomeServico() + "</option>");
                                                                            }
                                                                        }
                                                                    }
                                                                %>                                
                                                            </select>
                                                        </li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </li>
                                        <li class="list-group-item">
                                            <input type="hidden" name="idCliente" id="idCliente" value="<%= idClienteInc%>" />
                                            <input type="hidden" name="servicos" id="servicos" value="" />
                                            <button type="button" class="btn btn-success" onclick="return preencherCampos();"><i class="fa fa-lg fa-spc fa-save"></i> SALVAR DADOS</button>
                                        </li>
                                    </ul>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <script type="text/javascript">
            function trocaServ(listRemove, listAdiciona) {
                var selIndex = document.getElementById(listRemove).selectedIndex;
                var idServ = document.getElementById(listRemove).options[selIndex].value;
                var nomeServ = document.getElementById(listRemove).options[selIndex].text;

                document.getElementById(listRemove).remove(selIndex);

                var novaOpcao = new Option(nomeServ, idServ);
                document.getElementById(listAdiciona).options[document.getElementById(listAdiciona).length] = novaOpcao;

                OrdenarLista(listRemove);
                OrdenarLista(listAdiciona);

                document.getElementById(listRemove).focus();
                if (document.getElementById(listRemove).length == selIndex) {
                    document.getElementById(listRemove).selectedIndex = selIndex - 1;
                } else {
                    document.getElementById(listRemove).selectedIndex = selIndex;
                }
            }

            function OrdenarLista(combo) {
                var lb = document.getElementById(combo);
                arrTexts = new Array();

                for (i = 0; i < lb.length; i++) {
                    arrTexts[i] = lb.options[i].text + "@" + lb.options[i].value;
                }

                arrTexts.sort();

                for (i = 0; i < lb.length; i++) {
                    var aux = arrTexts[i].split("@");
                    lb.options[i].text = aux[0];
                    lb.options[i].value = aux[1];
                }
            }

            function preencherCampos() {
                var form = document.form1;

                var lb = document.getElementById('servico_2');
                var servicos = "";
                for (i = 0; i < lb.length; i++) {
                    if (i == 0) {
                        servicos += lb.options[i].value;
                    } else {
                        servicos += "@" + lb.options[i].value;
                    }
                }
                document.getElementById('servicos').value = servicos;

                if (servicos == "") {
                    alert('Selecione algum serviço para o Contrato do Cliente!');
                    return false;
                }

                form.submit();
            }

            /************************************/

            $(document).ready(function() {
                fechaMsg();
            });

        </script>

    </body>
</html>
<%}%>
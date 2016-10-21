
<%@page import="Entidade.ServicoPrefixo"%>
<%@page import="Controle.ContrServicoPrefixo"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Entidade.ServicoECT"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("401")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {
        String nomeBD = (String) session.getAttribute("empresa");
        int idEmpresa = (Integer) session.getAttribute("idEmpresa");
        String servicosel = request.getParameter("servicosel");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>

    <body onload="fechaMsg();">   
        <script>
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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears"></i> Cadastros</b> > <small>Prefixos de Etiquetas</small></h4>
                            </div>
                        </div>

                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-12">
                                <form class="form-inline" name="form2" action="servicos_prefixos_b.jsp" method="post">
                                    <ul class="list-group">
                                        <li class="list-group-item list-group-heading"><strong>Escolha um Servico para Gerenciar os Prefixos:</strong></li>
                                        <li class="list-group-item">
                                            <select class="form-control input-lg" name="servicosel" id="servicosel" onchange="javascript:document.form2.submit();">
                                                <option value="">-- Escolha um Serviço --</option>
                                                <%                                                
                                                    ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicosPorGrupo();
                                                    for (int i = 0; i < listaServ.size(); i++) {
                                                        ServicoECT sv = listaServ.get(i);
                                                        if (!sv.getGrupoServico().equals("SIMPLES")) {
                                                            if (servicosel != null && servicosel.equals(sv.getGrupoServico())) {
                                                                out.println("<option selected value='" + sv.getGrupoServico() + "'>" + sv.getNomeSimples() + "</option>");
                                                            } else {
                                                                out.println("<option value='" + sv.getGrupoServico() + "'>" + sv.getNomeSimples() + "</option>");
                                                            }
                                                        }
                                                    }
                                                %>
                                            </select>
                                        </li>
                                    </ul>
                                </form>

                                <form name="form1" action="../../ServServicosPrefixo" method="post">
                                    <ul class="list-group"  <%if (servicosel == null || servicosel.equals("")) {%>style="display: none" <%} else {%> style="display: block"<%}%>>   
                                        <li class="list-group-item list-group-heading">
                                            <strong>Defina os Prefixos do Serviço Selecionado:</strong>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="form-inline">
                                                <label>Prefixo</label>
                                                <input class="form-control text-uppercase" type="text" name="prefixo" id="prefixo" size="1" maxlength="2" />

                                                <label>&nbsp;</label>
                                                <label><input type="checkbox" name="avista" value="1" id="b" /> FÍSICA</label>

                                                <label>&nbsp;</label>
                                                <label><input type="checkbox" name="faturar" value="" id="a" /> LÓGICA</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item">                                    
                                            <div class="buttons">
                                                <button type="button" class="btn btn-primary" onclick="addRow();"><i class="fa fa-lg fa-spc fa-plus"></i> ADICIONAR PREFIXO</button>
                                            </div>                                    
                                        </li>
                                        <li class="list-group-item">
                                            <table style="width: 400px;" class="table table-striped table-bordered table-hover table-condensed" id="table" name="table">
                                                <thead>
                                                    <tr>
                                                        <th> PREFIXOS </th>
                                                        <th> FÍSICA </th>
                                                        <th> LÓGICA </th>
                                                        <th> SUSPENDER </th>
                                                        <th> EXCLUIR </th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList<ServicoPrefixo> lista = ContrServicoPrefixo.consultaTodosAmarracao(servicosel, nomeBD);
                                                        for (int i = 0; i < lista.size(); i++) {
                                                            ServicoPrefixo s = lista.get(i);
                                                            String suspenso = "fa fa-lg fa-refresh";
                                                            if (s.getStatus() == 1) {
                                                                suspenso = "fa fa-lg fa-pause";
                                                            }
                                                    %>
                                                    <tr>
                                                        <td align="center"><input class="form-control text-center" type="input" id="prefixo<%= i + 1%>" name="prefixo<%= i + 1%>" size="5" maxlength="9" readonly value="<%= s.getPrefixo()%>" /></td>
                                                        <td align="center"><input type="checkbox" id="avista<%= i + 1%>" name="avista<%= i + 1%>" <%if (s.getAvista() == 1) {%>checked<%}%> /></td>
                                                        <td align="center">
                                                            <input type="checkbox" id="faturar<%= i + 1%>" name="faturar<%= i + 1%>" <%if (s.getAvista() == 0) {%>checked<%}%> />
                                                            <input type="hidden" id="suspenso<%= i + 1%>" name="suspenso<%= i + 1%>" value="<%= s.getStatus()%>" />
                                                        </td>
                                                        <td align="center"><button type="button" class="btn btn-info btn-sm" onclick='suspender(<%= i + 1%>);'><span id="img_sus<%= i + 1%>" class="<%= suspenso%>"></span></button></td>
                                                        <td align="center"><button type="button" class="btn btn-danger btn-sm" onclick='delRow(this);'><span class="fa fa-lg fa-trash"></span></button></td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>
                                        </li>
                                        <li  class="list-group-item">
                                            <dd>
                                                <div>
                                                    <input type="hidden" name="contador" id="contador" value="<%= lista.size()%>" />
                                                    <input type="hidden" name="servico" id="servico" value="<%= servicosel%>" />
                                                    <button type="submit" class="btn btn-success" name="save" id="sub" onclick="return validateRow();"><i class="fa fa-lg fa-spc fa-save"></i> SALVAR PREFIXOS DO SERVIÇO</button>
                                                </div>
                                            </dd>
                                        </li>
                                    </ul>    
                                </form>     

                            </div>
                        </div>
                        <!-- /.row -->
                    </div>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->

        <!-- /#wrapper -->
        <script type="text/javascript">

            function addRow() {
                var cont = document.getElementById('contador').value;
                var newCont = parseInt(cont) + 1;

                var prefixo = document.getElementById('prefixo').value.toUpperCase();
                var letters = /^[A-Za-z]+$/;

                if (prefixo == '' || prefixo.length != 2 || !prefixo.match(letters)) {

                    bootbox.dialog({
                        title: "<span style='color:red; font-weight: bold;'>ATENÇÃO</span>",
                        message: "<label>O Prefixo deve ter 2 Digitos e deve conter apenas letras!</label>"

                    }).on('hidden.bs.modal', function () {
                        $('#prefixo').focus();
                        $('#prefixo').css("background", "#F5F6CE");
                    });

                    return false;

                }

                for (var i = 1; i <= cont; i++) {
                    if (document.getElementById('prefixo' + i).value == prefixo) {

                        bootbox.dialog({
                            title: "<span style='color:red; font-weight: bold;'>ATENÇÃO</span>",
                            message: "<label>Este Prefixo já está inserido na tabela!</label>"
                        }).on('hidden.bs.modal', function () {
                            $('#prefixo').focus();
                            $('#prefixo').css("background", "#F5F6CE");
                        });
                        return false;
                    }
                }

                var fat = '';
                if (document.getElementById('a').checked) {
                    fat = 'checked'
                }

                var vis = '';
                if (document.getElementById('b').checked) {
                    vis = 'checked'
                }

                if (fat == '' && vis == '') {

                    bootbox.dialog({
                        title: "<span style='color:red; font-weight: bold;'>ATENÇÃO</span>",
                        message: "<label>Selecione se o Prefixo é para etiqueta física, lógica ou AMBAS!</label>"
                    }).on('hidden.bs.modal', function () {
                        $('#prefixo').focus();
                        $('#prefixo').css("background", "#F5F6CE");
                    });
                    return false;
                }

                var nrLinha = document.getElementById('table').rows.length;
                var linha = document.getElementById('table').insertRow(nrLinha);

                var cell0 = linha.insertCell(0)
                cell0.innerHTML = "<input class='form-control text-center' type='input' id='prefixo" + newCont + "' name='prefixo" + newCont + "' size='5' readonly value='" + prefixo + "' />";
                cell0.align = "center";
                var cell1 = linha.insertCell(1)
                cell1.innerHTML = "<input type='checkbox' id='avista" + newCont + "' name='avista" + newCont + "' " + vis + " />";
                cell1.align = "center";
                var cell2 = linha.insertCell(2)
                cell2.innerHTML = "<input type='checkbox' id='faturar" + newCont + "' name='faturar" + newCont + "' " + fat + " />"
                        + "<input type='hidden' id='suspenso" + newCont + "' name='suspenso" + newCont + "' value='1' />";
                cell2.align = "center";
                var cell3 = linha.insertCell(3)
                cell3.innerHTML = "<button type='button' class='btn btn-info btn-sm disabled'><span class='fa fa-lg fa-pause'></span></button>";
                cell3.align = "center";
                var cell4 = linha.insertCell(4)
                cell4.innerHTML = "<button type='button' class='btn btn-danger btn-sm' onclick='delRow(this);'><span class='fa fa-lg fa-trash'></span></button>";
                cell4.align = "center";

                linha.className = "faixas";

                document.getElementById('contador').value = newCont;
                document.getElementById('prefixo').value = '';
                document.getElementById('prefixo').focus();

            }


            function delRow(linha) {
                var nrLinha = document.getElementById('table').rows.length;
                if (nrLinha > 2) {
                    bootbox.confirm({
                        title: 'Excluir Prefixo de Etiqueta?',
                        message: 'Deseja realmente excluir este prefixo de etiqueta?',
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
                        callback: function(result) {
                            if(result){
                                var tabela = document.getElementById('table');
                                linha = linha.parentNode.parentNode;
                                id = linha.rowIndex;
                                tabela.deleteRow(id);
                            }
                        }
                    });
                } else {
                    bootbox.dialog({                        
                        title: "<span style='color:red; font-weight: bold;'>ATENÇÃO</span>",
                        message: "<label>A tabela deve conter pelo menos uma linha!</label>",
                        onEscape: true
                    }).on('hidden.bs.modal', function () {
                        $('#prefixo').focus();
                        $('#prefixo').css("background", "#F5F6CE");
                    });
                    return false;
                }
            }


            function validateRow() {
                var contador = document.getElementById('contador').value;
                //PERCORRE TODOS OS CAMPOS DA TABELA DE PESOS E PRECOS
                for (var i = 1; i <= contador; i++) {
                    //VERIFICA SE O CEP INICIAL FOI PREENCHIDO
                    if (!document.getElementById('avista' + i).checked && !document.getElementById('faturar' + i).checked) {
                        bootbox.dialog({
                            title: "<span style='color:red; font-weight: bold;'>ATENÇÃO</span>",
                            message: "<label>Selecione na tabela se o Prefixo é para etiqueta física, lógica ou AMBAS!!</label>"
                        }).on('hidden.bs.modal', function () {
                            $('#prefixo').focus();
                            $('#prefixo').css("background", "#F5F6CE");
                        });
                        return false;
                    }
                    var pre = document.getElementById('prefixo' + i).value;
                    for (var j = 1; j <= contador; j++) {
                        if (i != j && pre == document.getElementById('prefixo' + j).value) {
                            bootbox.dialog({
                                title: "<span style='color:red; font-weight: bold;'>ATENÇÃO</span>",
                                message: "<label>Este Prefixo já está inserido na tabela!</label>"
                            }).on('hidden.bs.modal', function () {
                                $('#prefixo').focus();
                                $('#prefixo').css("background", "#F5F6CE");
                            });
                            return false;
                        }
                    }
                }
                return true;
            }

            function suspender(id) {
                var sus = document.getElementById('suspenso' + id).value;
                if (sus === '1') {
                    //document.getElementById('img_sus' + id).src = '../../imagensNew/refresh.png';
                    document.getElementById('img_sus' + id).className = 'fa fa-lg fa-refresh';
                    document.getElementById('suspenso' + id).value = '0';
                } else {
                    //document.getElementById('img_sus' + id).src = '../../imagensNew/pause.png';
                    document.getElementById('img_sus' + id).className = 'fa fa-lg fa-pause';
                    document.getElementById('suspenso' + id).value = '1';
                }
            }

        </script>
    </body>
</html>
<%}%>

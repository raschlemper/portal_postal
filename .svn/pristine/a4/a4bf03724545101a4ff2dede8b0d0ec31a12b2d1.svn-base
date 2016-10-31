
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrAmarracaoServico"%>
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<%
    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("205")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");
        int idEmpresa = (Integer) session.getAttribute("idEmpresa");

        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        Coleta.Entidade.HoraColeta hc = Coleta.Controle.contrHoraColeta.consultaHoraColetaById(nomeBD);
        String horaFimAcesso = "", horaIniColeta = "", horaFimColeta = "", antecedencia = "";
        if (hc != null) {
            horaFimAcesso = formatador.format(hc.getHoraFimAcesso());
            horaIniColeta = formatador.format(hc.getHoraIniColeta());
            horaFimColeta = formatador.format(hc.getHoraFimColeta());
            antecedencia = String.valueOf(hc.getMinAntecedencia());
        }

        int tipoEscolhaColeta = Coleta.Controle.contrColetaFixa.consultaTipoEscolhaColetaDoCliente(idEmpresa);

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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears"></i> Cadastros</b> > <small>Horário Coleta</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">    
                                <form name="form1" action="../../ServHoraColeta" method="post">
                                    <div class="panel panel-default">   
                                        <div class="panel-heading">
                                            <label>HORÁRIO DE INICIO E TÉRMINO DAS COLETAS EM SUA AGÊNCIA <small>(PARA SOLICITAÇÕES DE COLETAS VIA WEB)</small>:</label>
                                        </div>
                                        <div class="panel-body panel-form form-horizontal">  
                                            <div class="col-sm-4 col-md-2 col-lg-2">
                                                <label class="control-label small" for="timefield2">Horário de Início:</label>
                                                <div class="input-group">
                                                    <input class="form-control" placeholder="Horário de Início" name="timefield2" type="text" id="timefield2" size="8" maxlength="5" value="<%= horaIniColeta%>" onkeypress="mascara(this, maskHora)" onblur="return valida_hora(this);" />
                                                    <div class="input-group-addon"><i class="fa fa-clock-o"></i></div>
                                                </div>
                                            </div>
                                            <div class="col-sm-4 col-md-2 col-lg-2">
                                                <label class="control-label small" for="timefield3">Horário de Término:</label>
                                                <div class="input-group">
                                                    <input class="form-control" placeholder="Horário de Término" name="timefield3" type="text" id="timefield3" size="8" maxlength="5" value="<%= horaFimColeta%>" onkeypress="mascara(this, maskHora)" onblur="return valida_hora(this);" />
                                                    <div class="input-group-addon"><i class="fa fa-clock-o"></i></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">   
                                        <div class="panel-heading">
                                            <label>HORÁRIO LIMITE QUE O CLIENTE PODE SOLICITAR AS COLETAS VIA WEB:</label>
                                        </div>
                                        <div class="panel-body panel-form form-horizontal">  
                                            <div class="col-sm-4 col-md-2 col-lg-2">
                                                <label class="control-label small" for="input_time">Horário Limite:</label>
                                                <div class="input-group">
                                                    <input class="form-control" placeholder="Horário Limite" name="timefield1" type="text" id="timefield1" size="8" maxlength="5" value="<%= horaFimAcesso%>" onkeypress="mascara(this, maskHora)" onblur="return valida_hora(this);" />
                                                    <div class="input-group-addon"><i class="fa fa-clock-o"></i></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>                                           
                                    <div class="panel panel-default">   
                                        <div class="panel-heading">
                                            <label>ANTECEDÊNCIA MÍNIMA PARA O CLIENTE SOLICITAR A COLETA PELA WEB <small>(EM MINUTOS)</small>:</label>
                                        </div>
                                        <div class="panel-body panel-form form-horizontal">  
                                            <div class="col-sm-4 col-md-2 col-lg-2">
                                                <label class="control-label small" for="antecedencia">Antecedência:</label>
                                                <div class="input-group">
                                                    <input class="form-control" placeholder="Min. de Antecedência" name="antecedencia" type="text" id="antecedencia" size="8" maxlength="3" value="<%= antecedencia%>" onkeypress="mascara(this, maskNumero)" />
                                                    <div class="input-group-addon"><i class="fa fa-clock-o"></i></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">   
                                        <div class="panel-heading">
                                            <label>OPÇÕES DE ESCOLHA DE HORÁRIO DA COLETA <small>(CLIENTE)</small>:</label>
                                        </div>
                                        <div class="panel-body panel-form form-horizontal">  
                                            <div class="col-sm-6">
                                                <label class="control-label small" for="escolha">Opção do cliente:</label>
                                                <div class="input-group">
                                                    <select id="optHorario" class="form-control" name="tipoEscolhaCli" onchange="tocarOpcao(this);" >
                                                        <option value="1" <%if (tipoEscolhaColeta == 1) {%>selected<%}%>>ESCOLHE HORA DA COLETA</option>
                                                        <option value="3" <%if (tipoEscolhaColeta == 3) {%>selected<%}%>>NÃO ESCOLHE HORA DA COLETA</option>
                                                        <option value="2" <%if (tipoEscolhaColeta == 2) {%>selected<%}%>>MAIS CEDO x MAIS TARDE POSSIVEL</option>
                                                        <option value="4" <%if (tipoEscolhaColeta == 4) {%>selected<%}%>>PERIODO DA MANHÃ x PERIODO DA TARDE</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <input type="hidden" name="rota" value="0" />
                                    <button type="button" class="btn btn-success" onclick="return preencherCampos();"><i class="fa fa-lg fa-spc fa-save"></i> SALVAR DADOS</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->

        <!-- /#wrapper -->
        <script type="text/javascript">
            function TimePicker() {
                $('#timefield1').timepicker({setDate: new Date()});
                $('#timefield2').timepicker({setDate: new Date()});
                $('#timefield3').timepicker({setDate: new Date()});
            }
            $(document).ready(function () {
                LoadTimePickerScript(TimePicker);
            });

            function preencherCampos() {
                var form = document.form1;
                if (!valida_hora(form.timefield1)) {
                    return false;
                }
                if (!valida_hora(form.timefield2)) {
                    return false;
                }
                if (!valida_hora(form.timefield3)) {
                    return false;
                }
                if (form.antecedencia.value === "") {
                    alert('Preencha o tempo de antecedência!');
                    return false;
                }
                form.submit();
            }

            function  tocarOpcao(v) {
                $.ajax({
                    url: 'ajax/tocarTipoImpressao.jsp',
                    type: 'POST',
                    data: {PrintType: v.value},
                   // contentType: 'application/text; charset=utf-8',
                    /*success: function (response) {
                        alert(response);
                    },*/
                    error: function () {
                        alert("Ocoreu um erro inesperado !");
                    }
                });
            }
        </script>
    </body>
</html>
<%}%>

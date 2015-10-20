
<%@page import="Entidade.Endereco"%>
<%@page import="Entidade.TelegramaPostal"%>
<%@page import="Emporium.Controle.ContrTelegramaPostal"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Entidade.Contato"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.ArrayList, java.util.Date, java.text.SimpleDateFormat, java.sql.*, java.util.Calendar, java.util.Locale" %>

<%
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {

        String vDataAtual = sdf.format(new Date());
        if (request.getParameter("data") != null) {
            vDataAtual = request.getParameter("data");
        }
        String vData2 = sdf.format(new Date());
        if (request.getParameter("data2") != null) {
            vData2 = request.getParameter("data2");
        }

        String dataBD = Util.FormatarData.DateToBD(vDataAtual);
        String dataBD2 = Util.FormatarData.DateToBD(vData2);
%>

<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title> Portal Postal</title>
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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-file-text"></i> Telegramas</b> > <small>Telegramas não enviados</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">                        
                                <div id="ow-server-footer">
                                    <a href="telegrama_naoenviados_b.jsp" style="color: gray;" class="col-xs-4 col-sm-4 col-md-2 col-lg-2 text-center btn-default "><i class="fa fa-lg fa-file-text"></i> <span>TELEGRAMAS<br/>NÃO ENVIADOS</span></a>
                                    <a href="telegrama_enviados_b.jsp" class="col-xs-4 col-sm-4 col-md-2 col-lg-2 text-center btn-success"><i class="fa fa-lg fa-file-text"></i> <span>TELEGRAMAS<br/>ENVIADOS</span></a>
                                </div>
                            </div>
                        </div>

                        <div class="row">                            
                                <div class="well well-md"> 
                                    <div>
                                        <h4 class="subtitle">Pesquisar Telegramas Enviados</h4>
                                    </div>                                                
                                    <form class="form-inline" action="telegrama_enviados_b.jsp" method="post">
                                        <div class="form-group" >                                                
                                            <label for="from">De</label>
                                            <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                <input class="form-control" size="8" type="text" id="data" name="data" value="<%= vDataAtual%>" onkeypress="mascara(this, maskData);"/>
                                            </div>
                                            <label for="to">à</label>
                                            <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                <input class="form-control" size="8" type="text" id="data2" name="data2" value="<%= vData2%>" onkeypress="mascara(this, maskData);" />
                                            </div>
                                            <button type="submit" class="btn btn-sm btn-primary form-control" onclick="waitMsg()"><i class="fa fa-lg fa-search"></i></button>
                                        </div>
                                    </form>
                                </div>   
                        </div>
                        <div class="row">                            
                            <div class="col-lg-12">
                                <%
                                    ArrayList<TelegramaPostal> lista = ContrTelegramaPostal.consultaEnviados(dataBD, dataBD2, nomeBD);
                                    for (TelegramaPostal t : lista) {
                                        Endereco ed = t.getEnderecoDes();
                                        Endereco er = t.getEnderecoRem();
                                        String adicionais = "";
                                        if (t.getAdicionais().contains("AR")) {
                                            adicionais = "<br/>- AVISO DE ENTREGA";
                                        }
                                        if (t.getAdicionais().contains("CP")) {
                                            adicionais += "<br/>- CÓPIA DE TELEGRAMA - VIA " + t.getEnvioCopia() + ": " + t.getEmailCopia();
                                        }
                                %>
                                <ul class="list-unstyled">
                                    <li class="list-group-item list-group-heading">
                                        <div class="row vdivide">
                                            <div class="col-xs-6">
                                                <b>Remetente:</b><br/>
                                                <%= er.getNome()%><br/>
                                                <%= t.getDepartamento()%><br/>
                                                <%= er.getLogradouro() + " " + er.getNumero()%><br/>
                                                <%= er.getComplemento()%><br/>
                                                <%= er.getBairro()%><br/>
                                                <%= er.getCidade() + "/" + er.getUf()%><br/>
                                                <%= er.getCep()%>
                                            </div>
                                            <div class="col-xs-6">
                                                <b>Destinatario:</b><br/>
                                                <%= ed.getNome()%><br/>
                                                <%= ed.getLogradouro() + " " + ed.getNumero()%><br/>
                                                <%= ed.getComplemento()%><br/>
                                                <%= ed.getBairro()%><br/>
                                                <%= ed.getCidade() + "/" + ed.getUf()%><br/>
                                                <%= ed.getCep()%>
                                            </div>
                                        </div>
                                    </li>
                                    <li class="list-group-item">
                                        <b>Solicitado Em:</b> <%= sdf2.format(t.getDataHora())%><br/>
                                        <b>Agendado Para:</b> <%= sdf2.format(t.getDataHoraAgendado())%> por <%= t.getUserAgendado()%><br/>
                                        <b>Enviado Em:</b> <%= sdf2.format(t.getDataHoraEnviado())%> por <%= t.getUserEnviado()%><br/><br/>
                                        <b>N° do Telegrama:</b> <a style="font-weight: bold; color: blue;" href='http://websro.correios.com.br/sro_bin/txect01$.QueryList?P_LINGUA=001&P_TIPO=001&P_COD_UNI=<%= t.getSro()%>' target=_blank><%= t.getSro()%></a><br/><br/>
                                        <b>Adicionais:</b> <%= adicionais%><br/><br/>
                                        <b>Mensagem:</b><br/>
                                        <%= t.getMensagem()%>   
                                    </li>
                                    <li class="list-group-item">
                                        <button type="button" class="btn btn-info" onclick="window.open('telegrama_impressao_b.jsp?id=<%= t.getId()%>', '_blank');" class="regular" ><i class="fa fa-lg fa-spc fa-print"></i> IMPRIMIR TELEGRAMA</button>                                        
                                    </li>                                        
                                </ul>
                                <%}%>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" charset="utf-8">
            $(document).ready(function() {
                $("#data").datepicker({
                    showAnim: 'slideDown',
                    maxDate: new Date(),
                    numberOfMonths: 3,
                    onClose: function(selectedDate) {
                        $("#data2").datepicker("option", "minDate", selectedDate);
                    }
                });
                $("#data2").datepicker({
                    showAnim: 'slideDown',
                    maxDate: new Date(),
                    numberOfMonths: 3,
                    onClose: function(selectedDate) {
                        $("#data").datepicker("option", "maxDate", selectedDate);
                    }
                });
                fechaMsg();
            });
        </script>
    </body>
</html>
<%}%>
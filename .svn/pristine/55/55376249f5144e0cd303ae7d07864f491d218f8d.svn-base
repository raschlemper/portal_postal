
<%@page import="Entidade.Endereco"%>
<%@page import="Entidade.TelegramaPostal"%>
<%@page import="Emporium.Controle.ContrTelegramaPostal"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Entidade.Contato"%>
<%@page import="java.util.ArrayList, java.util.Date, java.text.SimpleDateFormat, java.sql.*, java.util.Calendar, java.util.Locale" %>

<%
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    if (session.getAttribute("agf_empresa") == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {
        Usuario user = (Usuario) session.getAttribute("agf_usuario");
        empresas agf = (empresas) session.getAttribute("agf_empresa");

        String vDataAtual = sdf.format(new Date());
        if (request.getParameter("data") != null) {
            vDataAtual = request.getParameter("data");
        }
        String vData2 = sdf.format(new Date());
        if (request.getParameter("data2") != null) {
            vData2 = request.getParameter("data2");
        }
        
        String filterData = "dataHoraEnviado";
        if (request.getParameter("filterData") != null) {
            filterData = request.getParameter("filterData");
        }
        
        String dataBD = Util.FormatarData.DateToBD(vDataAtual);
        String dataBD2 = Util.FormatarData.DateToBD(vData2);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-file-text"></i> Telegramas</b> > <small>Telegramas enviados</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div  class="well well-md">  
                                <h4 class="subtitle">Selecionar aquivo para importação</h4>
                                <form action="../../ServTelegramaImportacao" method="post" enctype="multipart/form-data">
                                    <span    class="btn btn-default btn-file">
                                        <i class="fa fa-folder-open"></i> Selecionar arquivo 
                                        <input name="fileImportacao" id="fileImportacao" type="file"/>
                                    </span>
                                    <button style="margin-left:10px;" onclick="return validaImportacao();" class="btn btn-success"><i class="fa fa-cloud-upload fa-spc"></i> IMPORTAR</button>
                                </form>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">                        
                                <div id="ow-server-footer">
                                    <a href="telegrama_naoenviados_b.jsp" style="color: gray;" class="col-xs-4 col-sm-4 col-md-2 col-lg-2 text-center btn-default "><i class="fa fa-lg fa-file-text"></i> <span>TELEGRAMAS<br/>SOLICITADOS<br/><br/></span></a>
                                    <a href="telegrama_exportados.jsp" style="color: gray;" class="col-xs-4 col-sm-4 col-md-2 col-lg-2 text-center btn-default "><i class="fa fa-lg fa-file-text"></i> <span>TELEGRAMAS<br/>EXPORTADOS/NÃO ENVIADOS</span></a>
                                    <a href="telegrama_enviados_b.jsp" class="col-xs-4 col-sm-4 col-md-2 col-lg-2 text-center btn-success"><i class="fa fa-lg fa-file-text"></i> <span>TELEGRAMAS<br/>ENVIADOS<br/><br/></span></a>
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
                                        <select class="form-control" name="filterData">
                                            <option value="dataHoraEnviado" <%= filterData.equals("dataHoraEnviado") ? "selected" : "" %>>Data envio</option>
                                            <option value="dataHora" <%= !filterData.equals("dataHoraEnviado") ? "selected" : "" %>>Data solicitação</option>
                                        </select>
                                        <button type="submit" class="btn btn-sm btn-primary form-control" onclick="waitMsg()"><i class="fa fa-lg fa-search"></i></button>
                                    </div>
                                </form>
                            </div>   
                        </div>
                        <div class="row">                            
                            <div class="col-lg-12">
                                <%
                                    ArrayList<TelegramaPostal> lista = ContrTelegramaPostal.consultaEnviadosFilter(dataBD, dataBD2,filterData, agf.getCnpj());
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
                                        String msga = t.getMensagem().replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").replace("\n", "<br/>"); 
                                %>
                                <form action="../../ServTelegramaEnvia">
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
                                            <b>N° do Telegrama:</b>
                                            <a href='#' onclick="pesqSro('<%= t.getSro()%>');"><%= t.getSro()%></a>                                          <br/><br/>
                                            <b>Adicionais:</b> <%= adicionais%><br/><br/>
                                            <b>Mensagem:</b><br/>
                                            <%= msga%>   
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row">
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                    <label>N° do Telegrama</label>
                                                    <input type="text" size="10" maxlength="13" class="form-control" name="sro" value="<%= t.getSro()%>" />                                                       
                                                    <input type="hidden" name="id" value="<%= t.getId()%>"/>
                                                    <input type="hidden" name='nomeUser' value="<%= user.getNome()%>"/>
                                                    <input type="hidden" name='nomeBD' value="<%= agf.getCnpj()%>"/>                                                    
                                                </div>
                                                <div class="col-xs-12 col-sm-3 col-md-2 col-lg-2">
                                                    <label>Valor (R$)</label>
                                                    <input type="text" size="3" maxlength="6" class="form-control" name="valor" value="<%= t.getValor()%>"  onkeypress="mascara(this, maskReal)" />                                              
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <button type="submit" onclick="waitMsg();" class="btn btn-success" ><i class="fa fa-lg fa-spc fa-pencil-square-o"></i> ALTERAR SRO E VALOR</button>
                                            <button type="button" class="btn btn-info" onclick="window.open('telegrama_impressao_b.jsp?id=<%= t.getId()%>', '_blank');" class="regular" ><i class="fa fa-lg fa-spc fa-print"></i> IMPRIMIR TELEGRAMA</button>                                        
                                        </li>          
                                    </ul>
                                </form>
                                <%}%>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <form name="frmSRO" id="frmSRO" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
            <input type="hidden" name="objetos" id="objetos" value="" />
        </form>  

        <script type="text/javascript" charset="utf-8">
            $(document).ready(function () {
                $("#data").datepicker({
                    showAnim: 'slideDown',
                    maxDate: new Date(),
                    numberOfMonths: 3,
                    onClose: function (selectedDate) {
                        $("#data2").datepicker("option", "minDate", selectedDate);
                    }
                });
                $("#data2").datepicker({
                    showAnim: 'slideDown',
                    maxDate: new Date(),
                    numberOfMonths: 3,
                    onClose: function (selectedDate) {
                        $("#data").datepicker("option", "maxDate", selectedDate);
                    }
                });
                fechaMsg();
            });
            function pesqSro(param) {
                $('#objetos').val(param);
                $('#frmSRO').submit();
            }
            
            function validaImportacao(){
                if(fileImportacao.value==""){
                    alert('Selecione um arquivo para importação');
                    return false;
                }
                
                if(fileImportacao.value.indexOf(".txt")<1 && fileImportacao.value.indexOf(".TXT") < 1){
                    alert('Selecione um arquivo no formato .txt');
                    return false;
                }
                
                return true;
            }            

        </script>
    </body>
</html>
<%}%>

<%@page import="Entidade.SRO"%>
<%@page import="Entidade.EDI"%>
<%@page import="Util.FormataString"%>
<%@page import="Util.FaixaCep"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page import = "java.util.Calendar, java.util.Locale, java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%
            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD == null) {
                response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
            } else {

                int nivelUsuarioEmp2 = (Integer) session.getAttribute("nivelUsuarioEmp");
                String nomeEmpresa = Controle.contrEmpresa.nomeEmpresaByNomeBD(nomeBD);

                int idCli = (Integer) session.getAttribute("idCliente");
                ArrayList<EDI> listaEDI = Controle.ContrlEDI.consultaEDIByCliente(idCli, nomeBD);
                                       
        %>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Meu Cadastro</title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>
        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]><link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" /><![endif]-->

        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <style>
            .faixas input{  border: 1px solid #aaa; height: 16px; font-size: 12px;}
            .faixas td{border-right: 1px solid silver;border-bottom: 1px solid silver;padding: 2px;}            
            .faixas th{border-right: 1px solid silver;border-bottom: 1px solid silver;padding: 5px 2px;background-color:#e7e7e7;}     
            .disabled {
                pointer-events: none;
                cursor: default;
            }
        </style>

    </head>
    <body>
        <div id="divInteracao" class="esconder" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_cliente.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    <div id="titulo1">Relação SRO x EDI</div>

                    <ul class="ul_formulario" style="width: 90%;">                                                       

                       <li class="titulo">
                           <dd>
                               Cadastrar os Codigos EDI para cada evento SRO
                           </dd>
                        </li>
                        <li>
                            <dd>
                                <table style="margin: 0 auto; border-left:1px solid silver;border:1px solid silver;" id="table" name="table" cellspacing="0" cellpadding="2">
                                    <tr class="faixas">
                                        <th align="center"><b>TIPO SRO</b></th>
                                        <th align="center"><b>STATUS SRO</b></th>
                                        <th align="center"><b>DESCRIÇÃO EVENTO</b></th>
                                        <th align="center"><b>CODIGO EDI</b></th>
                                    </tr>

                                    <%
                                        ArrayList<SRO> listaSRO = Controle.ContrlEDI.consultaSRO(nomeBD); 
                                       
                                        for (SRO se : listaSRO) {
                                            // erro falta corrigir
                                        int flag = Controle.ContrlEDI.consultaSelected(se.getIdSRO(), idCli, nomeBD);
                                    %>                                    
                                    <tr class="faixas">
                                        <td>                                                            
                                          <%=se.getTipo()%>
                                        </td>
                                        <td align="center">
                                         <%=se.getStatus()%>    
                                        </td>
                                        <td align="center">
                                         <%=se.getDescricao()%>    
                                        </td>
                                        <td>
                                            <select name="<%=se.getIdSRO()%>" onfocus="prevEDI(this.value);" onchange="salvaOcorrencia(this.value,<%=se.getIdSRO()%>);">
                                                <option value="e">Escolha uma ocorrencia EDI</option>
                                         <%
                                         for (EDI e : listaEDI) {
                                            %><option value='<%=e.getCodigo()%>'<% if(flag == e.getCodigo()){%>selected<% }%>><%=e.getCodigo()%> - <%=e.getDescricao()%></option>
                                            
                                            <%}%>
                                            </select>
                                        </td>
                                    </tr>
                                   
                                      <%}%>
                                    <input type="hidden" name="contador" id="contador" value="<%=listaEDI.size()%>" />
                                </table>
                            </dd>
                        </li>
                    </ul>

                </div>
            </div>
        </div>

        <script>
            var aux = -1;
            function prevEDI(edi_prev){
                aux = edi_prev;                
            }
            function salvaOcorrencia(codEDI, idSRO) {
                $.ajax({
                    method: 'POST',
                    url: '../AjaxPages/alter_sro_edi.jsp',
                    data: {idCli: '<%=idCli%>', codigo_edi: codEDI, codigo_sro: idSRO, edi_p: aux}
                })
                        .done(function (msg) {
                            alert(msg);
                        });

            }
        </script>
    </body>
</html>
<%}%>

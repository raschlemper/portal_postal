<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Entidade.Endereco"%>
<%@page import="Entidade.TelegramaPostal"%>
<%@page import="Emporium.Controle.ContrTelegramaPostal"%>
<%            
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String nomeBD = (String) session.getAttribute("nomeBD");
        if (nomeBD == null) {
            response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
        } else {
            
            int id = Integer.parseInt(request.getParameter("id"));
            
        %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Telegrama Postal</title>
    </head>
    <body onload="window.print();">
        
        <%                                
                        TelegramaPostal t = ContrTelegramaPostal.consultaById(id, nomeBD);
                        if (t != null) {
                            Endereco ed = t.getEnderecoDes();
                            Endereco er = t.getEnderecoRem();
                            String adicionais = "";
                            if (t.getAdicionais().contains("AR")) {
                                adicionais = "<br/>- PEDIDO DE CONFIRMAÇÃO";
                            }
                            if (t.getAdicionais().contains("CP")) {
                                adicionais += "<br/>- CÓPIA DE TELEGRAMA - VIA " + t.getEnvioCopia() + ": " + t.getEmailCopia();
                            }
                            String msg = t.getMensagem().replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;").replace("\n", "<br/>"); 
                    %>
                    <table width="100%" cellpadding="0" cellspacing="0" border="0">
                        <tbody>     
                            <tr>
                                <td colspan="2" align="center">
                                    <h2>TELEGRAMA POSTAL</h2>
                                    <hr/><br/>
                                </td>
                            </tr>
                            <tr>
                                <td style="width: 50%;">
                                    <b>Remetente:</b><br/>
                                    <%= er.getNome()%><br/>
                                    <%= t.getDepartamento()%><br/>
                                    <%= er.getLogradouro() + " " + er.getNumero()%><br/>
                                    <%= er.getComplemento()%><br/>
                                    <%= er.getBairro()%><br/>
                                    <%= er.getCidade() + "/" + er.getUf()%><br/>
                                    <%= er.getCep()%>
                                </td>
                                <td style="width: 50%;">
                                    <b>Destinatario:</b><br/>
                                    <%= ed.getNome()%><br/>
                                    <%= ed.getLogradouro() + " " + ed.getNumero()%><br/>
                                    <%= ed.getComplemento()%><br/>
                                    <%= ed.getBairro()%><br/>
                                    <%= ed.getCidade() + "/" + ed.getUf()%><br/>
                                    <%= ed.getCep()%>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">  
                                    <br/><hr/><br/>
                                    <b>Solicitado Em:</b> <%= sdf.format(t.getDataHora())%><br/>
                                    <b>Agendado Para:</b> <%= sdf.format(t.getDataHoraAgendado())%> por <%= t.getUserAgendado() %><br/><br/>
                                    <b>N° do Telegrama:</b> <%= t.getSro() %><br/><br/>
                                    <b>Adicionais:</b> <%= adicionais%><br/><br/>
                                    <b>Mensagem:</b><br/>
                                    <%= msg %>                                    
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <%}%>
        
        
    </body>
</html>
<%}%>
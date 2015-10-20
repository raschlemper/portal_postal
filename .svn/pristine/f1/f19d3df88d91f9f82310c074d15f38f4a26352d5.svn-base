<%-- 
    Document   : imprimirRota
    Created on : 29/05/2009, 16:16:33
    Author     : SCC4
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>

<%
            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD == null) {
                response.sendRedirect("../index.jsp?msgLog=3");
            } else {


//pega os parametros passados para a pagina
                String dataAtual = request.getParameter("dataPesquisa");
                String vIdColet = request.getParameter("idColetador");
//cria uma data atual
                Date dataPesquisa = new Date();
//declara os simple date format
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
//verifica se a data passada pelo parametro nao eh nula nem vazia
                if (dataAtual == null || dataAtual.equals("")) {
                    dataAtual = sdf1.format(dataPesquisa);
                } else {
                    dataPesquisa = df.parse(dataAtual);
                }

                String vDataPesquisa = sdf3.format(dataPesquisa);
                int idColetador = Integer.parseInt(vIdColet);
                String coletador = Coleta.Controle.contrColetador.consultaNomeColetadoresById(idColetador, nomeBD);
%>

<html>
    <head>
        <%
                        response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
                        response.setHeader("Pragma", "no-cache"); //HTTP 1.0
                        response.setDateHeader("Expires", 0); //prevent caching at the proxy server
%>
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="Expires" content="-1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Coletador: <%= coletador%></title>
        <style>
            body{font-family:Verdana, Geneva, sans-serif; font-size:12px;}
        </style>
    </head>
    <body>
        <div style="margin-top:20px;">
            <div style="font-weight:bold;font-size:medium;padding-bottom:10px;">
                Coletador: <%= coletador%> - Rota das Coletas, gerado em <%= sdf2.format(new Date())%><br/><br/>
                * HORÁRIO DE CHEGADA NA AGF ATÉ ÀS ____:____ hs SEM FALTA!
            </div>
            <table border="1" style="border: 1px solid black;" width="100%" cellspacing="0">
                <tr>
                    <th width="2%"><b>Nº</b></th>
                    <th width="2%"><b>Hora</b></th>
                    <th width="20%"><b>Fantasia</b></th>
                    <th width="30%"><b>Endereço</b></th>
                    <th width="8%"><b>Tipo</b></th>
                    <th width="25%"><b>Obs.</b></th>
                    <th width="15%"><b>Assinatura</b></th>
                </tr>
                <%
                                int cont = 0;
                                ArrayList listaColetasRep = Coleta.Controle.contrColeta.consultaColetasPeloStatus(2, idColetador, vDataPesquisa, "dataHoraColeta, cliente.endereco", nomeBD);
                                for (int j = 0; j < listaColetasRep.size(); j++) {
                                    Coleta.Entidade.Coleta col = (Coleta.Entidade.Coleta) listaColetasRep.get(j);
                                    String tipo = col.getTipoColeta();
                                    Timestamp horaColeta = col.getDataHoraColeta();
                                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                                    String horario = sdf.format(horaColeta);
                                    String obs = col.getObs();
                                    int idCliente = col.getIdCliente();
                                    Entidade.Clientes cli = Controle.contrCliente.consultaClienteById(idCliente, nomeBD);
                                    String cliente = "Cliente não encontrado!", rua = "", complemento = "", nomeFantasia = "";
                                    if (cli != null) {
                                        cliente = cli.getNome();
                                        rua = cli.getEndereco();
                                        complemento = cli.getComplemento();
                                        nomeFantasia = cli.getNomeFantasia().trim();
                                        if (nomeFantasia.equals("")) {
                                            nomeFantasia = cliente;
                                        }
                                    }
                                    cont++;
                %>
                <tr height="30" style="text-align:center;">
                    <td><b><%= cont%></b></td>
                    <td><b><%= horario %></b></td>
                    <td><b><%= nomeFantasia%></b></td>
                    <td><%= rua%> - <%= complemento%></td>
                    <td><%= tipo%></td>
                    <td><%= obs%></td>
                    <td>&nbsp;</td>
                </tr>
                <%}%>
                <tr>
                    <td colspan="7" height="5" style="background:gray;">&nbsp;</td>
                </tr>
                <%
                                for (int i = 0; i < 15; i++) {
                                    cont++;
                %>
                <tr height="30" style="text-align:center;">
                    <td><b><%= cont%></b></td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
                <%}%>
            </table>
        </div>
    </body>
</html>
<%}%>
<%@page import="Emporium.Controle.ContrVpne"%>
<%@page import="Entidade.Vpne"%>
<%@page import="Entidade.Destinatario"%>
<%@page import="Controle.contrDestinatario"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="Util.FormataString"%>
<%@ page import="java.sql.*, javax.swing.*, java.util.*, java.text.SimpleDateFormat, java.text.DecimalFormat, java.util.Date" %>
<%
    response.setContentType("application/xls");
    response.setHeader("Content-disposition", "attachment; filename=relatorio_vpne.xls");
    response.setHeader("Cache-Control", "no-cache");

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String nomeBD = request.getParameter("nomeBD");
    String where = request.getParameter("where");

    int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));

   
        ArrayList<Vpne> listaVpne = ContrVpne.listaVpne(where, nomeBD, idCli);
    if (listaVpne.size() > 0) {

%>
<table cellpadding="0" cellspacing="0" border="0">
    <thead>
         <tr>
                                <th><h3>SRO</h3></th>
                                <th><h3>Destinatario</h3></th>
                                <th><h3>CPF/CNPJ</h3></th>
                                <th><h3>Endereço</h3></th>
                                <th><h3>CEP</h3></th>
                                <th><h3>Cidade / UF - ID.</h3></th>
                                <th><h3>Data</h3></th>
                                <th><h3>Valor</h3></th>
                                <th><h3>Centro Custo</h3></th>
                            </tr>
</thead>
     <tbody>
                            <%
                                for (int i = 0; i < listaVpne.size(); i++) {
                                    Vpne vp = listaVpne.get(i);
                                    Destinatario rem = vp.getRemVpne();
                                    Destinatario des = vp.getDestVpne();

                                    String city = des.getCidade();
                                    String uf = des.getUf();

                                    if (city == null || city.trim().equals("")) {
                                        city = "CEP inexiste no DNE";
                                    }
                                    if (uf == null || uf.trim().equals("")) {
                                        uf = " - Falta no movimento.";
                                    } else {
                                        uf = " - id. " + uf;
                                    }
                                    String dt[] = vp.getData().split("-");

                            %>
                            <tr>
                                <td ><%= vp.getSro()%>"
                              </td>
                                <td><%= des.getNome()%></td>
                                <td><%= des.getCpf_cnpj()%></td>
                                <td><%= des.getEndereco() + ", " + des.getNumero()%></td>
                                <td><%= des.getCep()%></td>
                                <td><%= city + uf%></td>
                                <td><%= dt[2] + "/" + dt[1] + "/" + dt[0]%></td>
                                <td><%= vp.getValor()%></td>                              
                                <td><%= vp.getNomeDepto() %></td>                              
                            </tr>
                            <% }%>
                        </tbody>
</table>
<%} else {%>
<div align='center' style='padding-top:25px;background:#fc8878;color:black;height:50px;width:100%;font-size:20px;font-weight:bold;'>Nenhum Objeto Encontrado!</div>
<%}%>
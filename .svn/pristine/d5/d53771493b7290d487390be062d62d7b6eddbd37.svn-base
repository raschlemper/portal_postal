<%@page import="Entidade.empresas"%>
<%
    if (session.getAttribute("emp") != null) {
        String usMenu = (String) session.getAttribute("nome");
        empresas empMenu = (empresas) session.getAttribute("emp");
        int nvMenu = (Integer) session.getAttribute("nivel");
%>
<div align="center" style=" width:100%; min-width:1200px; height:125px; margin: 0 auto; z-index: -1;"><%--//ANTIGO WIDTH 1000--%>
    <div align="center" style="height: 85px; width: 1200px;"><%--//ANTIGO WIDTH 1000--%>
        <table width="100%" style="height: 85px;">
            <tr>
                <td valign="middle" style="font-size: 16px; text-align: left;">
                    <b style="font-size: 24px;"><%= empMenu.getFantasia().toUpperCase()%></b><br>
                    <b>CÓD. DA AGÊNCIA: <span style="color: #b02c2c;"><%= empMenu.getIdEmpresa()%></span></b><br>
                    <b>USUÁRIO: <span style="color: #b02c2c;"><%= usMenu.toUpperCase()%></span></b>
                </td>
                <td valign="middle" align="right">
                    <img src="../imagensNew/PortalPostal_logo.png" height="80" />
                </td>
            </tr>
        </table>
    </div>
    <div align="center" style="width: 1200px; height: 25px; background: #2191c0;"><%--//ANTIGO WIDTH 1000--%>
        <ul class="dropdown">
            
            <li><a href="#">Objetos Pendentes</a>
                <ul class="sub_menu">
                    <li><a href="../SROpi/painel_nao_entregues.jsp">Objetos não Entregues</a></li>   
                    <li><a href="../SROpi/painel_nao_entregues.jsp">PI Abertos</a></li>                        
                    <li><a href="../SROpi/painel_nao_entregues.jsp">PI Encerrados</a></li>  
                    <li><a href="../../Agencia/Relatorio/painel_etiquetas_pesq.jsp">Pesquisar PI</a></li>                     
                    <li><a href="">Atualizar SRO</a></li> 
                  
                </ul>
            </li>
                 
          
            <li><a href="../ServLogout">Sair</a></li>
        </ul>
    </div>
</div>
<%}%>
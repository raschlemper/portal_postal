<%@page import="Entidade.Usuario"%>
<%@page import="Entidade.Clientes"%>
<%@page import="java.util.ArrayList"%>
<%
    String usMenu = (String) session.getAttribute("nomeUser");
    String senhaOp = (String) session.getAttribute("senhaUser");
    
    String empMenu = (String) session.getAttribute("nomeEmpresa");
    int idAgM = (Integer) session.getAttribute("idEmpresa");
    int temCol = (Integer) session.getAttribute("temColeta");
    int temPV = (Integer) session.getAttribute("temPV");
    int temETQ = (Integer) session.getAttribute("temETQ");
    int nvMenu = (Integer) session.getAttribute("nivelUsuarioEmp");
    ArrayList<Integer> acessosMn = (ArrayList<Integer>) session.getAttribute("acessos");
    Clientes cliMenu = (Clientes) session.getAttribute("cliente");

    ArrayList<Usuario> lsNomesBd = (ArrayList<Usuario>) session.getAttribute("userMaster");
 
%>
<script>
    
     function getval(sel) {
          
          $('#agenciaHoito').val(sel.value);
          $('#logar').submit();
          
    }
</script>
<div align="center" style=" width:100%; min-width:1200px; height:125px; margin: 0 auto; z-index: -1;">
    <div align="center" style="height: 85px; width: 1200px;">

        <form action="http://www.portalpostal.com.br/ServLoginEmporium" method="post" id="logar">
            <table width="100%" style="height: 85px;">
                <tr>           
                    <td valign="middle" style="font-size: 16px; text-align: left;">

                        <% if (lsNomesBd != null && !lsNomesBd.isEmpty()) {%>

                        <select style="font-size: 24px; font-weight: bold;" onchange="getval(this);">

                            <% for (int j = 0; j < lsNomesBd.size(); j++) {%>
                            <option value="<%= lsNomesBd.get(j).getIdEmpresa() %>" <%if(lsNomesBd.get(j).getIdEmpresa() == idAgM){ %>selected="true"<%}%> ><%= lsNomesBd.get(j).getNome()%></option>
                            <%}%>

                        </select>
                        <br>
                        <%} else {%>
                        <b style="font-size: 24px;"><%= empMenu.toUpperCase()%></b><br>
                        <%}%>
                        <b>CÓD. DA AGÊNCIA: <span style="color: #b02c2c;"><%= idAgM%></span></b><br>
                        <b>USUÁRIO: <span style="color: #b02c2c;"><%= usMenu.toUpperCase()%></span></b>
                    </td>

                    <td valign="middle" align="right">
                        <img src="../../imagensNew/PortalPostal_logo.png" height="80" />
                    </td>
                </tr>
            </table>
                    
                    <input type="hidden" id="agenciaHoito" name="agenciaHoito" value="" />
                    <input type="hidden" name="senhaHoito" value="<%= senhaOp%>" />
                    <input type="hidden" name="loginHoito" value="<%= usMenu%>" />
                    <input type="hidden" name="caminho" value="" />
        </form>
    </div>
    <div align="center" style="width: 1200px; height: 25px; background: #2191c0;">
        <ul class="dropdown">
            <% if (acessosMn.contains(1) || acessosMn.contains(2) || acessosMn.contains(3) || nvMenu != 99) { %>
            <li><a href="#">Postagens</a>
                <ul class="sub_menu">
                    <%if (acessosMn.contains(1)) {%><li><a href="../../Cliente/Postagens/consultas.jsp">Pesquisa</a></li><%}%>
                    <%if (acessosMn.contains(2)) {%><li><a href="../../Cliente/Postagens/consultaAr.jsp">Controle de A.R.</a></li><%}%>
                    <%if (acessosMn.contains(1)) {%><li><a href="../../Cliente/Postagens/relatorios.jsp">Relatórios</a></li><%}%>
                    <%if (acessosMn.contains(1)) {%><li><a href="../../Cliente/Postagens/graf_objetos.jsp">Situação dos Objetos</a></li><%}%>
                    <%if (acessosMn.contains(3)) {%><li><a href="../../Cliente/Postagens/graf_despesas.jsp">Evolução das Despesas</a></li><%}%>
                </ul>
            </li>
            <%}%>

            <%if ((temCol == 1 || temPV == 1) && (acessosMn.contains(4) || acessosMn.contains(5) || nvMenu == 99)) {%>
            <li><a href="#">Serviços</a>
                <ul class="sub_menu">
                    <%if ((acessosMn.contains(4) || nvMenu != 99) && temCol == 1) {%><li><a href="../../Cliente/Servicos/coleta.jsp">Solicitar Coleta</a></li><%}%>
                        <%if ((acessosMn.contains(5) || nvMenu == 99) && temPV == 1 && temETQ == 1) {%>
                    <li><a href="../../Cliente/Servicos/telegrama_postal.jsp">Solicitar Telegrama Postal</a></li>
                        <%}%>
                </ul>
            </li>
            <li><a href="#">Etiquetas</a>
                <ul class="sub_menu">
                    <%if ((acessosMn.contains(5) || nvMenu == 99) && temPV == 1 && temETQ == 1) {%>
                    <%if (nvMenu != 99) {%>
                    <li><a href="../../Cliente/Servicos/pre_postagem.jsp">Gerar Etiqueta</a></li>
                    <li><a href="../../Cliente/Servicos/pre_postagem_medias.jsp">Gerar Etiquetas Mult.</a></li>
                    <li><a href="../../Cliente/Servicos/imp_postagem.jsp">Importar Postagens</a></li>
                    <li><a href="../../Cliente/Servicos/reimpressao.jsp">Etiquetas Impressas</a></li>
                    <li><a href="../../Cliente/Servicos/etq_naoutilizadas.jsp">Etiquetas Não Utilizadas</a></li>
                    <li><a href="../../Cliente/Servicos/importa_plp.jsp">Imprimir Etiquetas de PLP</a></li>
                        <%}%>
                        <%}%>
                </ul>
            </li>
            <li><a href="#">Lista de Postagem</a>
                <ul class="sub_menu">
                    <%if ((acessosMn.contains(5) || nvMenu == 99) && temPV == 1 && temETQ == 1) {%>
                    <%if (nvMenu != 99) {%>
                    <li><a href="../../Cliente/Servicos/lista_postagem_gerar.jsp">Gerar Lista de Postagem</a></li>
                    <li><a href="../../Cliente/Servicos/pesq_os.jsp">Pesquisar Lista de Post.</a></li>
                        <%}%>
                        <%}%>
                </ul>
            </li>
            <%}%>

            <%if (cliMenu.getTemContrato() == 1 && nvMenu != 99) {%>
            <li><a href="#">Logística Reversa</a>
                <ul class="sub_menu">
                    <li><a href="../../Cliente/Servicos/logistica_reversa.jsp">Gerar Aut. de Postagem</a></li>
                    <li><a href="../../Cliente/Servicos/lista_reversa.jsp">Autorizações Geradas</a></li>
                </ul>
            </li>
            <%}%>

            <%if (nvMenu != 99) {%><li><a href="../../Cliente/Servicos/precos_prazos.jsp">Preços e Prazos</a></li><%}%>

            <% if (acessosMn.contains(6) || acessosMn.contains(7) || nvMenu == 1 || nvMenu != 99) { %>
            <li><a href="#">Cadastros</a>
                <ul class="sub_menu">
                    <%if (acessosMn.contains(6)) {%><li><a href="../../Cliente/Cadastros/cadastro.jsp">Dados da Empresa</a></li><%}%>
                    <%if (nvMenu == 1) {%><li><a href="../../Cliente/Cadastros/usuario_lista.jsp">Usuários</a></li><%}%>
                    <%if (acessosMn.contains(7) && temPV == 1 && temETQ == 1) {%><li><a href="../../Cliente/Cadastros/destinatario_lista.jsp">Destinatários</a></li><%}%>
                </ul>
            </li>            
            <%}%>

            <li><a href="../../ServLogoutEmporium">Sair</a></li>
        </ul>
    </div>
</div>


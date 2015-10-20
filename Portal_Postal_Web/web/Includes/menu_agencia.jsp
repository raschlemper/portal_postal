<%@page import="java.util.ArrayList"%>
<%@page import="Entidade.empresas"%>
<%
    if (session.getAttribute("emp") != null) {
        String usMenu = (String) session.getAttribute("nome");
        empresas empMenu = (empresas) session.getAttribute("emp");
        //int nvMenu = (Integer) session.getAttribute("nivel");
        ArrayList<Integer> listaAcessos = (ArrayList<Integer>) session.getAttribute("acessosAgencia");
%>
<div align="center" style="width:100%; min-width:1200px; height:125px; margin: 0 auto; z-index: -1;">
    <div align="center" style="height: 85px; width: 1200px;">
        <table width="100%" style="height: 85px;">
            <tr>
                <td valign="middle" style="font-size: 16px; text-align: left;">
                    <b style="font-size: 24px;"><%= empMenu.getFantasia().toUpperCase()%></b><br>
                    <b>CÓD. DA AGÊNCIA: <span style="color: #b02c2c;"><%= empMenu.getIdEmpresa()%></span></b><br>
                    <b>USUÁRIO: <span style="color: #b02c2c;"><%= usMenu.toUpperCase()%></span></b>
                </td>
                <td valign="middle" align="right">
                    <img src="../../imagensNew/PortalPostal_logo.png" alt="" height="80" />
                </td>
            </tr>
        </table>
    </div>
    <div align="center" style="width: 1200px; height: 25px; background: #2191c0;"><%--//ANTIGO WIDTH 1000--%>
        <ul class="dropdown">
            <%if (empMenu.getChamada() == 1) {%>
            <li><a href="#">Gerenciar Etiquetas</a>
                <ul class="sub_menu">
                    <%if (listaAcessos.contains(101)) {%><li><a href="../../Agencia/Relatorio/painel_etiquetas.jsp">Etiquetas Restantes</a></li><%}%>
                    <%if (listaAcessos.contains(102)) {%><li><a href="../../Agencia/Relatorio/painel_etiquetas_geradas.jsp">Sequências Geradas</a></li><%}%>
                    <%if (listaAcessos.contains(103)) {%><li><a href="../../Agencia/Relatorio/painel_etiquetas_pesq.jsp">Pesquisar Etiqueta</a></li><%}%>
                    <%if (listaAcessos.contains(104)) {%><li><a href="../../Agencia/Relatorio/painel_etiquetas_pend.jsp">Etiquetas Pendentes</a></li><%}%>
                    <%if (listaAcessos.contains(105)) {%><li><a href="../../Agencia/Relatorio/painel_etiquetas_inut.jsp">Etiquetas Inutilizadas</a></li><%}%>                    
                </ul>
            </li>
            <%}%>
            <%if (empMenu.getColeta() == 1) {%>
            <li><a href="#">Coleta</a>
                <ul class="sub_menu">
                    <%if (listaAcessos.contains(201)) {%><li><a href="../../Agencia/Coleta/acompanhamento.jsp">Acompanhamento</a></li><%}%>
                    <%if (listaAcessos.contains(202)) {%><li><a href="../../Agencia/Coleta/novaColeta.jsp">Nova Coleta</a></li><%}%>
                    <%if (listaAcessos.contains(203)) {%><li><a href="../../Agencia/Coleta/coletador_lista.jsp">Coletadores</a></li><%}%>
                    <%if (listaAcessos.contains(204)) {%><li><a href="../../Agencia/Coleta/tipo_coleta_lista.jsp">Tipos de Coleta</a></li><%}%>
                    <%if (listaAcessos.contains(205)) {%><li><a href="../../Agencia/Configuracao/config_hora_coleta.jsp">Horário da Coleta</a></li><%}%>
                </ul>
            </li>
            <%}%>
            <li><a href="#">Importações</a>
                <ul class="sub_menu">
                    <%if (empMenu.getIdEmpresa() == -99){%>
                        <%if (listaAcessos.contains(301)) {%><li><a href="../../Agencia/Importacao/imp_movimento_visual.jsp" >Arquivo de Movimentação</a></li><%}%>
                    <%}else{%>
                        <%if (listaAcessos.contains(301)) {%><li><a href="../../Agencia/Importacao/imp_movimento.jsp" >Arquivo de Movimentação</a></li><%}%>
                        <%if (listaAcessos.contains(302)) {%><li><a href="../../Agencia/Importacao/imp_ar.jsp">Arquivo de AR's</a></li><%}%>
                        <%if (listaAcessos.contains(303)) {%><li><a href="../../Agencia/Importacao/imp_cliente.jsp">Arquivo de Clientes</a></li><%}%>
                        <%if (listaAcessos.contains(304)) {%><li><a href="../../Agencia/Importacao/imp_deptos.jsp">Arquivo de Departamentos</a></li><%}%>
                    <%}%>
                </ul>
            </li>
            <li><a href="../../Agencia/Telegrama/telegrama_naoenviados.jsp">Telegramas</a></li>
            <li><a href="#">Cadastros</a>
                <ul class="sub_menu">
                    <%if (empMenu.getChamada() == 1 && listaAcessos.contains(401)) {%><li><a href="../../Agencia/Configuracao/servicos_prefixos.jsp">Prefixos de Etiquetas</a></li><%}%>
                    <%if (empMenu.getChamada() == 1 && listaAcessos.contains(402)) {%><li><a href="../../Agencia/Configuracao/servicos_abrangencia.jsp">Abrangência de Serviços</a></li><%}%>
                    <%if (empMenu.getChamada() == 1 && listaAcessos.contains(403)) {%><li><a href="../../Agencia/Configuracao/amarracao_lista.jsp">Amarrações</a></li><%}%>
                    <%if (listaAcessos.contains(404)) {%><li><a href="../../Agencia/Configuracao/usuario_lista.jsp">Usuários</a></li><%}%>
                    <%if (listaAcessos.contains(405)) {%><li><a href="../../Agencia/Configuracao/cliente_lista.jsp">Clientes</a></li><%}%>
                    <%if (listaAcessos.contains(406)) {%><li><a href="../../Agencia/Configuracao/cliente_log_contrato.jsp">Verificação de Contratos</a></li><%}%>
                    <%--if (empMenu.getChamada() == 1) {%><li><a href="../../Agencia/Configuracao/atualizar_SRO.jsp">Atualizar SRO</a></li><%}--%>
                </ul>
            </li>
            <li><a href="../../ServLogout">Sair</a></li>
        </ul>
    </div>
</div>
<%}%>
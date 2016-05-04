<%@page import="Emporium.Controle.ContrTelegramaPostal"%>
<%@page import="Entidade.empresas"%>
<%
    if (session.getAttribute("emp") != null) {
        String usMenu = (String) session.getAttribute("nome");
        empresas empMenu = (empresas) session.getAttribute("emp");
        int nvMenu = (Integer) session.getAttribute("nivel");
        int qtdTelegPend = ContrTelegramaPostal.consultaQtdNaoEnviados(empMenu.getCnpj());
%>

<!-- Sidebar -->
<div id="sidebar-wrapper">
    <ul class="sidebar-nav nav-pills nav-stacked" id="menu">   
        <li>
            <b><span class="fa-stack fa-lg pull-left"></span> <%= empMenu.getFantasia().toUpperCase()%></b>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/NewTemplate/Dashboard/index.jsp"><span class="fa-stack fa-lg pull-left"><i class="fa fa-dashboard  fa-stack-1x "></i></span> Dashboard</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/NewTemplate/Telegrama/telegrama_naoenviados_b.jsp"><span class="fa-stack fa-lg pull-left"><i class="fa fa-file-text fa-stack-1x "></i></span> Telegramas <%if(qtdTelegPend>0){%><span class="label label-danger"><%= qtdTelegPend %></span><%}%></a>
        </li>
        <%if (empMenu.getChamada() == 1) {%>
        <li><a href="#"><span class="fa-stack fa-lg pull-left"><i class="fa fa-barcode fa-stack-1x "></i></span> Gerenciar Etiquetas <span style="margin: 5px 10px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span></a>
            <ul class="nav-pills nav-stacked" style="list-style-type:none;">
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Etiquetas/painel_etiquetas_b.jsp">Etiquetas Restantes</a></li>   
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Etiquetas/painel_etiquetas_geradas_b.jsp">Sequências Geradas</a></li>   
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Etiquetas/painel_etiquetas_pesq_b.jsp">Pesquisar Etiqueta</a></li>   
                    <%if (nvMenu == 1 || nvMenu == 2) {%>                 
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Etiquetas/painel_etiquetas_pend_b.jsp">Etiquetas Pendentes</a></li>
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Etiquetas/painel_etiquetas_inut_b.jsp">Etiquetas Inutilizadas</a></li>
                    <%}%>
            </ul>
        </li>
        <%}%>
        <%if (empMenu.getColeta() == 1) {%> 
        <li><a href="#"><span class="fa-stack fa-lg pull-left"><i class="fa fa-truck fa-stack-1x "></i></span> Coleta <span style="margin: 5px 10px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x"></i></span></a>
            <ul class="nav-pills nav-stacked" style="list-style-type:none;">
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Coleta/acompanhamento_b.jsp">Acompanhamento</a></li>     
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Coleta/pesquisar_b.jsp">Gerenciar Rotas</a></li>  
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Coleta/novaColeta_b.jsp">Nova Coleta</a></li>
                    <%if (nvMenu == 1 || nvMenu == 2) {%>
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Coleta/coletador_lista_b.jsp">Coletadores</a></li>
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Coleta/tipo_coleta_lista_b.jsp">Tipos de Coleta</a></li>
                    <%}%>
            </ul>
        </li>
        <%}%>  
        <li>
            <a href="#"><span class="fa-stack fa-lg pull-left"><i class="fa fa-cloud-upload fa-stack-1x "></i></span> Importações <span style="margin: 5px 10px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span></a>
            <ul class="nav-pills nav-stacked" style="list-style-type:none;">              
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Importacao/imp_movimento_b.jsp" >Arquivo de Movimentação</a></li>
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Importacao/imp_ar_b.jsp">Arquivo de Retorno de AR</a></li>
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Importacao/imp_cliente_b.jsp">Arquivo de Clientes</a></li>
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Importacao/imp_deptos_b.jsp">Arquivo de Departamentos</a></li> 
            </ul>
        </li>

        <%if (nvMenu == 1 || nvMenu == 2) {%>
        <li><a href="#"><span class="fa-stack fa-lg pull-left"><i class="fa fa-gears fa-stack-1x "></i></span> Cadastros <span style="margin: 5px 10px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span></a>
            <ul class="nav-pills nav-stacked" style="list-style-type:none;">
                <%if (nvMenu == 1) {%>
                <%if (empMenu.getChamada() == 1) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/servicos_prefixos_b.jsp">Prefixos de Etiquetas</a></li><%}%>
                <%if (empMenu.getChamada() == 1) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/servicos_abrangencia_b.jsp">Abrangência de Serviços</a></li><%}%>
                <%if (empMenu.getChamada() == 1) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/amarracao_lista_b.jsp">Amarrações</a></li><%}%>
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/usuario_lista_b.jsp">Usuários da Agência</a></li>
                    <%}%>
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/cliente_lista_b.jsp">Clientes</a></li>
                <%if (empMenu.getColeta() == 1) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/config_hora_coleta_b.jsp">Horário da Coleta</a></li><%}%>
                <%if (empMenu.getChamada() == 1) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/grupo_faturamento_lista_b.jsp">Grupos de Faturamento</a></li><%}%>
                <%if (empMenu.getChamada() == 1) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/cliente_log_contrato_b.jsp">Verificação de Contratos</a></li><%}%>
            </ul>
        </li>
        <%}%>
        
        <li><a href="#">
                <span class="fa-stack fa-lg pull-left"><i class="fa fa-car fa-stack-1x "></i></span> Veículos <span style="margin: 5px 10px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span>
            </a>
            <ul class="nav-pills nav-stacked" style="list-style-type:none;">
                <li><a href="${pageContext.request.contextPath}/app/veiculo/cadastro">Cadastro</a></li>
                <li><a href="${pageContext.request.contextPath}/app/veiculo/manutencao">Manutenção</a></li>
                <li><a href="${pageContext.request.contextPath}/app/veiculo/combustivel">Combustível</a></li>
                <li><a href="${pageContext.request.contextPath}/app/veiculo/multa">Multa</a></li>
                <li><a href="${pageContext.request.contextPath}/app/veiculo/seguro">Seguro</a></li>
                <li><a href="${pageContext.request.contextPath}/app/veiculo/sinistro">Sinistro</a></li>
            </ul>
        </li> 
        
        <li><a href="#">
                <span class="fa-stack fa-lg pull-left"><i class="fa fa-money fa-stack-1x "></i></span> Financeiro <span style="margin: 5px 10px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span>
            </a>
            <ul class="nav-pills nav-stacked" style="list-style-type:none;">
                <li><a href="${pageContext.request.contextPath}/app/financeiro/">Inicio</a></li>
                <li><a href="${pageContext.request.contextPath}/app/financeiro/lancamento">Lançamento</a></li>
                <li><a href="${pageContext.request.contextPath}/app/financeiro/lancamento/programado">Programação</a></li>
                <li><a href="${pageContext.request.contextPath}/app/financeiro/demonstrativo">Demonstrativo</a></li>
                <li>
                    <a href="#">
                        Cadastros <span style="margin: 5px 10px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span>
                    </a> 
                    <ul class="nav-pills nav-stacked" style="list-style-type:none;">
                        <li><a href="${pageContext.request.contextPath}/app/financeiro/banco">Banco</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/financeiro/planoconta">Plano Conta</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/financeiro/contacorrente">Conta Corrente</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/financeiro/carteiracobranca">Carteira Cobrança</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/financeiro/cartaocredito">Cartão Crédito</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/financeiro/conta">Conta</a></li>
                    </ul>
                </li>
            </ul>
        </li> 
        <%--<li>
            <a href="${pageContext.request.contextPath}/Agencia/Relatorio/painel_etiquetas.jsp"><span class="fa-stack fa-lg pull-left"><i class="fa fa-info-circle fa-stack-1x "></i></span> Acessar Layout Antigo</a>
        </li>--%>
        <li>
            <a class="danger" href="${pageContext.request.contextPath}/ServLogout"><span class="fa-stack fa-lg pull-left"><i class="fa fa-power-off fa-stack-1x "></i></span> Sair</a>
        </li>
    </ul>
        
</div>
<!-- /#sidebar-wrapper -->

<%}%>

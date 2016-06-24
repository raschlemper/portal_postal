<%@page import="Coleta.Controle.contrColeta"%>
<%@page import="Entidade.Usuario"%>
<%@page import="Entidade.empresas"%>
<%@page import="Emporium.Controle.ContrTelegramaPostal"%>
<%
    if (session.getAttribute("emp") != null) {
        Usuario usrMenu = (Usuario) session.getAttribute("agf_usuario");
        empresas empMenu = (empresas) session.getAttribute("agf_empresa");
        int qtdTelegPend = ContrTelegramaPostal.consultaQtdNaoEnviados(empMenu.getCnpj());

        String qtdWeb = contrColeta.consultaQtdColetasSolicitadas(empMenu.getCnpj());

%>

<!-- Sidebar -->
<div id="sidebar-wrapper">
    <ul class="sidebar-nav nav-pills nav-stacked" id="menu">   
        <li style="line-height: normal;border-bottom: 1px solid silver;">    
            <br/>
            <a class="noclass" href="#">
                <span class="fa-stack fa-lg pull-left" style="margin: -3px 10px 0 -10px;"><i class="fa fa-home fa-stack-2x "></i></span> 
                Código da AGF: <%= empMenu.getIdEmpresa()%>
            </a>
            <a class="noclass" href="#">
                <b><%= empMenu.getFantasia().toUpperCase()%></b> 
            </a>      
            <br/>
        </li>
        <li>
            <a href="#"><span class="fa-stack fa-lg pull-left"><i class="fa fa-dashboard fa-stack-1x "></i></span> Dashboard <span style="margin: 5px 20px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span>
            </a>
            <ul class="nav-pills nav-stacked" style="list-style-type:none;">
                <li><a href="${pageContext.request.contextPath}/NewTemplate/Dashboard/index.jsp">Overview AGF</a></li>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(201)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Coleta/acompanhamento_b.jsp">Overview Coletas</a></li><%}%>  
                <%if (usrMenu.getListaAcessosPortalPostal().contains(701)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Dashboard/relatorios.jsp">Relatórios</a></li><%}%> 
            </ul>
        </li>
        <%if (usrMenu.getListaAcessosPortalPostal().contains(106)) {%>
        <li>
            <a href="${pageContext.request.contextPath}/NewTemplate/Telegrama/telegrama_naoenviados_b.jsp"><span class="fa-stack fa-lg pull-left"><i class="fa fa-file-text fa-stack-1x "></i></span> Telegramas <%if (qtdTelegPend > 0) {%><span class="label label-danger"> <%= qtdTelegPend%></span><%}%></a>
        </li>
        <%}%>
        <li><a href="#"><span class="fa-stack fa-lg pull-left"><i class="fa fa-barcode fa-stack-1x "></i></span> Gerenciar Etiquetas <span style="margin: 5px 20px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span></a>
            <ul class="nav-pills nav-stacked" style="list-style-type:none;">
                <%if (usrMenu.getListaAcessosPortalPostal().contains(101)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Etiquetas/painel_etiquetas_b.jsp">Etiquetas Restantes</a></li><%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(102)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Etiquetas/painel_etiquetas_geradas_b.jsp">Sequências Geradas</a></li>   <%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(103)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Etiquetas/painel_etiquetas_pesq_b.jsp">Pesquisar Etiqueta</a></li>   <%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(104)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Etiquetas/painel_etiquetas_pend_b.jsp">Etiquetas Pendentes</a></li><%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(105)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Etiquetas/painel_etiquetas_inut_b.jsp">Etiquetas Inutilizadas</a></li><%}%>
            </ul>
        </li>
        <li><a href="#"><span class="fa-stack fa-lg pull-left"><i class="fa fa-truck fa-stack-1x "></i></span> Coleta <%if (!qtdWeb.equals("0")) {%><span class="label label-danger"> <%= qtdWeb%></span><%}%> <span style="margin: 5px 20px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x"></i></span></a>
            <ul class="nav-pills nav-stacked" style="list-style-type:none;">
                <%if (usrMenu.getListaAcessosPortalPostal().contains(206)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Coleta/pesquisar_b_1.jsp">Acompanhamento<%if (!qtdWeb.equals("0")) {%><span class="label label-danger"><%= qtdWeb%></span><%}%> </a></li>  <%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(202)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Coleta/novaColeta_b.jsp">Nova Coleta</a></li><%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(203)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Coleta/coletador_lista_b.jsp">Coletadores</a></li><%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(204)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Coleta/tipo_coleta_lista_b.jsp">Tipos de Coleta</a></li><%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(205)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/config_hora_coleta_b.jsp">Horários da Coleta</a></li><%}%>
            </ul>
        </li>
        <li>
            <a href="#"><span class="fa-stack fa-lg pull-left"><i class="fa fa-cloud-upload fa-stack-1x "></i></span> Importações <span style="margin: 5px 20px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span></a>
            <ul class="nav-pills nav-stacked" style="list-style-type:none;">              
                <%if (usrMenu.getListaAcessosPortalPostal().contains(302)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Importacao/imp_ar_b.jsp">Arquivo de Retorno de AR</a></li><%}%>
                <%if (!empMenu.getTipo_sistema().equals("PORTALPOSTAL")) {%>
                    <%if (usrMenu.getListaAcessosPortalPostal().contains(301)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Importacao/imp_movimento_b.jsp" >Arquivo de Movimentação</a></li><%}%>
                    <%if (usrMenu.getListaAcessosPortalPostal().contains(303)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Importacao/imp_cliente_b.jsp">Arquivo de Clientes</a></li><%}%>
                    <%if (usrMenu.getListaAcessosPortalPostal().contains(304)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Importacao/imp_deptos_b.jsp">Arquivo de Departamentos</a></li><%}%>
                <%}%>
            </ul>
        </li>
        <li><a href="#"><span class="fa-stack fa-lg pull-left"><i class="fa fa-gears fa-stack-1x "></i></span> Cadastros <span style="margin: 5px 20px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span></a>
            <ul class="nav-pills nav-stacked" style="list-style-type:none;">
                <%if (usrMenu.getListaAcessosPortalPostal().contains(401)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/servicos_prefixos_b.jsp">Prefixos de Etiquetas</a></li><%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(402)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/servicos_abrangencia_b.jsp">Abrangência de Serviços</a></li><%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(403)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/amarracao_lista_b.jsp">Amarrações</a></li><%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(404)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/usuario_lista_b.jsp">Usuários da Agência</a></li><%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(405)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/cliente_lista_b.jsp">Clientes</a></li><%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(407)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/grupo_faturamento_lista_b.jsp">Grupos de Faturamento</a></li><%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(406)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/cliente_log_contrato_b.jsp">Verificação de Contratos</a></li><%}%>
                <%if (usrMenu.getListaAcessosPortalPostal().contains(408)) {%><li><a href="${pageContext.request.contextPath}/NewTemplate/Cadastros/vendedor_lista_b.jsp">Vendedores</a></li><%}%>
            </ul>
        </li>
        
        
        <li>
            <a href="#">
                <span class="fa-stack fa-lg pull-left"><i class="fa fa-car fa-stack-1x "></i></span> Veículos <span style="margin: 5px 20px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span>
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

        <li>
            <a href="#">
                <span class="fa-stack fa-lg pull-left"><i class="fa fa-money fa-stack-1x "></i></span> Financeiro <span style="margin: 5px 20px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span>
            </a>
            <ul class="nav-pills nav-stacked" style="list-style-type:none;">
                <li><a href="${pageContext.request.contextPath}/app/financeiro/">Inicio</a></li>
                <li><a href="${pageContext.request.contextPath}/app/financeiro/lancamento">Lançamento</a></li>
                <li><a href="${pageContext.request.contextPath}/app/financeiro/lancamento/programado">Programação</a></li>
                <li><a href="${pageContext.request.contextPath}/app/financeiro/demonstrativo">Demonstrativo</a></li>
                <li>
                    <a href="#">
                        Cadastros <span style="margin: 5px 20px 0 0;" class="fa-stack fa-fw pull-right"><i id="arrow" class="fa fa-chevron-down fa-stack-1x "></i></span>
                    </a> 
                    <ul class="nav-pills nav-stacked" style="list-style-type:none;">
                        <li style="display:none;"><a href="${pageContext.request.contextPath}/app/financeiro/banco">Banco</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/financeiro/planoconta">Plano Conta</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/financeiro/centrocusto">Centro Custo</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/financeiro/contacorrente">Conta Corrente</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/financeiro/carteiracobranca">Carteira Cobrança</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/financeiro/cartaocredito">Cartão Crédito</a></li>
                        <li><a href="${pageContext.request.contextPath}/app/financeiro/conta">Conta</a></li>
                    </ul>
                </li>
            </ul>
        </li>
        <li>
            <a class="danger" href="${pageContext.request.contextPath}/ServLogout"><span class="fa-stack fa-lg pull-left"><i class="fa fa-power-off fa-stack-1x "></i></span> Sair</a>
        </li>
    </ul>

</div>
<!-- /#sidebar-wrapper -->

<%}%>

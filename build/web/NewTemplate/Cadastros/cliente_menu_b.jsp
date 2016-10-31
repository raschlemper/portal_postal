<%
    
    int idCliTabMenu = Integer.parseInt(request.getParameter("idClienteTab"));
    String activeTabMenu = request.getParameter("activeTab");
    String nomeClienteTab = request.getParameter("nomeClienteTab");
    int temContratoTabMenu = Integer.parseInt(request.getParameter("temContratoTab"));

    String mnUser = "btn-default";
    String mnCli = "btn-default";
    String mnCrm = "btn-default";
    String mnDepto = "btn-default";
    String mnContr = "btn-default";
    String mnEtq = "btn-default";
    String mnServ = "btn-default";
    String nomeBarra = "<i class='fa fa-users fa-fw'></i> Cliente";
    if (activeTabMenu.equals("0")) {
        mnCli = "btn-primary";
        nomeBarra = "<i class='fa fa-home fa-fw'></i> Cadastro do Cliente";
    } else if (activeTabMenu.equals("1")) {
        mnCrm = "btn-primary";
        nomeBarra = "<i class='fa fa-gratipay fa-fw'></i> CRM";
    } else if (activeTabMenu.equals("2")) {
        mnDepto = "btn-primary";
        nomeBarra = "<i class='fa fa-sitemap fa-fw'></i> Departamentos";
    } else if (activeTabMenu.equals("3")) {
        mnUser = "btn-primary";
        nomeBarra = "<i class='fa fa-users fa-fw'></i> Usuários";
    } else if (activeTabMenu.equals("4")) {
        mnContr = "btn-primary";
        nomeBarra = "<i class='fa fa-clipboard fa-fw'></i> Contrato ECT";
    } else if (activeTabMenu.equals("5")) {
        mnEtq  = "btn-primary";
        nomeBarra = "<i class='fa fa-barcode fa-fw'></i> Etiquetas";        
    }else if (activeTabMenu.equals("6")) {
        mnServ = "btn-primary";
       nomeBarra = "<i class='fa fa-envelope fa-fw'></i> + Serviços";
    }   
    
    /*
    if (temContratoTabMenu == 0) {
        mnEtq = "btn-disabled";
    }
    */
%>
<div class="row">
    <div class="col-md-12">
        <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears"></i> Cadastros</b> > <a href="cliente_lista_b.jsp">Clientes</a> > <small><%= nomeBarra%> <i class="fa fa-long-arrow-right fa-fw"></i> Cód. <%= idCliTabMenu%> - <%= nomeClienteTab%></small></h4>
    </div>
</div>
<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">                        
        <div id="ow-server-footer">
            <a href="cliente_cadastro_b.jsp?idCliente=<%= idCliTabMenu%>" class="col-xs-3 col-sm-3 col-md-2 col-lg-2 text-center <%= mnCli%> "><i class="fa fa-lg fa-home"></i> <span>Cadastro</span></a>
            <a href="cliente_crm_b.jsp?idCliente=<%= idCliTabMenu%>" class="col-xs-3 col-sm-3 col-md-1 col-lg-1 text-center <%= mnCrm%> "><i class="fa fa-lg fa-gratipay"></i> <span>CRM</span></a>
            <a href="cliente_departamentos_b.jsp?idCliente=<%= idCliTabMenu%>" class="col-xs-3 col-sm-3 col-md-2 col-lg-2 text-center <%= mnDepto%> "><i class="fa fa-lg fa-sitemap"></i> <span>Departamentos</span></a>
            <a href="cliente_usuarios_b.jsp?idCliente=<%= idCliTabMenu%>" class="col-xs-3 col-sm-3 col-md-2 col-lg-2 text-center <%= mnUser%> "><i class="fa fa-lg fa-users"></i> <span>Usuários</span></a>
            <a href="cliente_contrato_b.jsp?idCliente=<%= idCliTabMenu%>" class="col-xs-4 col-sm-4 col-md-2 col-lg-2 text-center <%= mnContr%> "><i class="fa fa-lg fa-clipboard"></i> <span>Contrato ECT</span></a>
            <%--<a <%if (temContratoTabMenu == 1) {%>href="cliente_etiquetas_b.jsp?idCliente=<%= idCliTabMenu%>"<%} else {%> onclick="bootbox.alert('<b>Este cliente não possui Contrato ECT!</b><br/><br/>Para solicitar etiquetas cadastre um contrato para este cliente!');"  <%}%> class="col-xs-4 col-sm-4 col-md-1 col-lg-1 text-center <%= mnEtq%> "><i class="fa fa-lg fa-barcode"></i> <span>Etiquetas</span></a>--%>
            <a href="cliente_etiquetas_b.jsp?idCliente=<%= idCliTabMenu%>" class="col-xs-4 col-sm-4 col-md-1 col-lg-1 text-center <%= mnEtq%> "><i class="fa fa-lg fa-barcode"></i> <span>Etiquetas</span></a>
            <a href="cliente_outros_serv_b.jsp?idCliente=<%= idCliTabMenu%>" class="col-xs-4 col-sm-4 col-md-2 col-lg-2 text-center <%= mnServ%> "><i class="fa fa-lg fa-envelope"></i> <span>+ Serviços</span></a>           
        </div>
    </div>
</div>
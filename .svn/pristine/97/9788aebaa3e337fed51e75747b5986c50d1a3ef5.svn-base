<%
    String nomeBDTabMenu = request.getParameter("nomeBDTab");
    int idCliTabMenu = Integer.parseInt(request.getParameter("idClienteTab"));
    String activeTabMenu = request.getParameter("activeTab");
    int temContratoTabMenu = Integer.parseInt(request.getParameter("temContratoTab"));

    Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idCliTabMenu, nomeBDTabMenu);
    int codigoInc = cliInc.getCodigo();
    String nomeClienteInc = cliInc.getNome();
    String enderecoInc = cliInc.getEndereco();
    String telefoneInc = cliInc.getTelefone();
    String bairroInc = cliInc.getBairro();
    String cidadeInc = cliInc.getCidade();
    String ufInc = cliInc.getUf();
    int cepInc = cliInc.getCep();
    String emailInc = cliInc.getEmail();
    String cnpjInc = cliInc.getCnpj();
    String tempInc = "";
    if (cnpjInc != null && cnpjInc.trim().length() > 13) {
        tempInc = cnpjInc.substring(0, 2) + ".";
        tempInc += cnpjInc.substring(2, 5) + ".";
        tempInc += cnpjInc.substring(5, 8) + "/";
        tempInc += cnpjInc.substring(8, 12) + "-";
        tempInc += cnpjInc.substring(12, 14);
    }
        //String complementoInc = cliInc.getComplemento();
%>
<ul class="ul_tab" style="width: 1150px;">
    <li>
        <dl style="width: 142px; border-left: 1px solid #CCC; " <%if (activeTabMenu.equals("0")) {%>class="ativo"<%}%> onclick="javascript:window.location = 'cliente_usuarios.jsp?idCliente=<%= idCliTabMenu%>';">
            <dd><img src="../../imagensNew/32_user.png" border="0" /></dd>                                    
            <dd><p><b>USUÁRIOS</b></p></dd>                                                
        </dl>
        <dl style="width: 142px;" <%if (activeTabMenu.equals("1")) {%>class="ativo"<%}%> onclick="javascript:window.location = 'cliente_departamentos.jsp?idCliente=<%= idCliTabMenu%>';">
            <dd><img src="../../imagensNew/32_depto.png" border="0" /></dd>                                    
            <dd><p><b>DEPARTAMENTOS</b></p></dd>                                                
        </dl>
        <dl style="width: 142px;" <%if (activeTabMenu.equals("2")) {%>class="ativo"<%}%> onclick="javascript:window.location = 'cliente_contatos.jsp?idCliente=<%= idCliTabMenu%>';">
            <dd><img src="../../imagensNew/32_contato.png" border="0" /></dd>                                    
            <dd><p><b>CONTATOS</b></p></dd>                                                
        </dl>
        <dl style="width: 142px;" <%if (activeTabMenu.equals("3")) {%>class="ativo"<%}%> onclick="javascript:window.location = 'cliente_outros_serv.jsp?idCliente=<%= idCliTabMenu%>';">
            <dd><img src="../../imagensNew/32_data_table.png" border="0" /></dd>                                    
            <dd><p><b>OUTROS SERVIÇOS</b></p></dd>                                                
        </dl>
        <dl style="width: 142px;" <%if (activeTabMenu.equals("4")) {%>class="ativo"<%}%> onclick="javascript:window.location = 'cliente_contrato.jsp?idCliente=<%= idCliTabMenu%>';">
            <dd><img src="../../imagensNew/32_contrato.png" border="0" /></dd>                                    
            <dd><p><b>CONTRATO ECT</b></p></dd>                                                
        </dl>
        <dl style="width: 142px;" <%if (activeTabMenu.equals("5")) {%>class="ativo"<%}%> <%if (temContratoTabMenu == 1) {%>onclick="javascript:window.location = 'cliente_etiquetas.jsp?idCliente=<%= idCliTabMenu%>';"<%} else {%> onclick="javascript:alert('Este Cliente não possui contrato!');"  <%}%> >
            <dd><img src="../../imagensNew/32_label.png" border="0" /></dd>                                    
            <dd><p><b>ETIQUETAS</b></p></dd>                                                
        </dl>
        <dl style="width: 142px;" <%if (activeTabMenu.equals("6")) {%>class="ativo"<%}%> onclick="javascript:window.location = 'cliente_mapa.jsp?idCliente=<%= idCliTabMenu%>';">
            <dd><img src="../../imagensNew/32_map.png" border="0" /></dd>                                    
            <dd><p><b>LOCALIZAÇÃO</b></p></dd>                                                
        </dl>
    </li>
</ul>            
<ul class="ul_dados">
    <li>
        <dd class="titulo"><span>Dados do Cliente</span></dd>
    </li>
    <li>
        <dd style="width: 380px;"><label>Nome:</label><%= nomeClienteInc%></dd>
        <dd style="width: 100px;"><label>Código:</label><%= codigoInc%></dd>
        <dd style="width: 180px;"><label>CNPJ:</label><%= tempInc%></dd>
        <dd style="width: 290px;"><label>Email:</label><%= emailInc%></dd>
        <dd style="width: 140px;"><label>Telefone:</label><%= telefoneInc%></dd>
    </li>
    <li>
        <dd style="width: 380px;"><label>Endereço:</label><%= enderecoInc%></dd>
        <dd style="width: 280px;"><label>Bairro:</label><%= bairroInc%></dd>
        <dd style="width: 290px;"><label>Cidade/UF:</label><%= cidadeInc + "/" + ufInc%></dd>
        <dd style="width: 140px;"><label>CEP:</label><%= cepInc%></dd>
    </li>
</ul>
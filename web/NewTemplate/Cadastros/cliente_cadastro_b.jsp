
<%@page import="Entidade.ClienteSMTP"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="Entidade.GrupoFaturamento"%>
<%@page import="Controle.ContrGrupoFaturamento"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Entidade.ServicoECT"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevent caching at the proxy server

    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        if (idNivelDoUsuario == 3) {
            response.sendRedirect("../Importacao/imp_movimento.jsp?msg=Acesso Negado!");
        }

        String nomeBD = (String) session.getAttribute("empresa");
        int idEmpresa = (Integer) session.getAttribute("idEmpresa");
        int idClienteInc = Integer.parseInt(request.getParameter("idCliente"));

        Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idClienteInc, nomeBD);
        String cnpj = cliInc.getCnpj();
        String nome = cliInc.getNome();
        String fantasia = cliInc.getNomeFantasia();
        String email = cliInc.getEmail();
        String telefone = cliInc.getTelefone();
        String logradouro = cliInc.getEndereco();
        String numero = cliInc.getNumero();
        String complemento = cliInc.getComplemento();
        String bairro = cliInc.getBairro();
        String cidade = cliInc.getCidade();
        String uf = cliInc.getUf();
        String cep = cliInc.getCep() + "";

        //dados para gerar o mapa
        double lat = cliInc.getLatitude();
        double lng = cliInc.getLongitude();
        String endereco = logradouro + " , " + cidade + " - " + uf;
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />     
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>

        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>
        <script type="text/javascript">
            var geocoder;
            var map;
            var marker;
            function load() {
                geocoder = new google.maps.Geocoder();
                var center = new google.maps.LatLng(<%= lat%>, <%= lng%>);
                var myOptions = {
                    zoom: 16,
                    center: center,
                    scrollwheel: false,
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                };
                map = new google.maps.Map(document.getElementById("map"), myOptions);
                marker = new google.maps.Marker({
                    map: map,
                    position: map.getCenter(),
                    animation: google.maps.Animation.DROP,
                    draggable: true
                });
                document.getElementById("lat").value = center.lat().toFixed(6);
                document.getElementById("lng").value = center.lng().toFixed(6);

                google.maps.event.addListener(marker, "dragend", function () {
                    var point = marker.getPosition();
                    map.panTo(point);
                    document.getElementById("lat").value = point.lat().toFixed(6);
                    document.getElementById("lng").value = point.lng().toFixed(6);
                });

                /*google.maps.event.addListener(map, "center_changed", function() {
                 var center = map.getCenter();
                 marker.setPosition(center);
                 document.getElementById("lat").value = center.lat().toFixed(6);
                 document.getElementById("lng").value = center.lng().toFixed(6); 
                 });*/

            <%if (lat == 0 && lng == 0) {%>
                showAddress("<%= endereco%>");
            <%}%>

            }

            function showAddress(address) {
                geocoder.geocode({'address': address}, function (results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        map.setCenter(results[0].geometry.location);
                        marker.setPosition(results[0].geometry.location);
                        marker.setAnimation(google.maps.Animation.DROP);
                        var center = map.getCenter();
                        document.getElementById("lat").value = center.lat().toFixed(6);
                        document.getElementById("lng").value = center.lng().toFixed(6);
                    } else {
                        alert("Geocode was not successful for the following reason: " + status);
                    }
                });
            }

            function mostraCampos() {
                $('#campos').toggleClass("hidden");

            }
            function mostraCampos2() {
                $('#camposSMTP').toggleClass("hidden")

            }

            function mostraTipoServer() {
                $('#is_cadastro').prop('checked', true);                
                $('#is_cadastro').attr("disabled", true);
                
                $('#mostra_smtp').toggleClass("hidden");
                $('#cad_email').toggleClass("hidden");
            }
            function meTireDaqui() {
                $('#is_cadastro').prop('checked', false);

            }
            $(document).ready(function () {
                load();
                fechaMsg();
            });

            function confirmExcluir(button) {
                bootbox.confirm({
                    title: 'Excluir Usuário do Cliente?',
                    message: 'Deseja realmente excluir este cadastro?',
                    buttons: {
                        'cancel': {
                            label: '<i class="fa fa-lg fa-times fa-spc"></i> CANCELAR',
                            className: 'btn btn-default pull-left'
                        },
                        'confirm': {
                            label: '<i class="fa fa-lg fa-trash fa-spc"></i> EXCLUIR',
                            className: 'btn btn-danger pull-right'
                        }
                    },
                    callback: function (result) {
                        if (result) {
                            button.form.submit();
                        }
                    }
                });

            }


            function confirmaCadastro() {

                bootbox.dialog({
                    message: "Ao efetuar este o cadastro você estará autorizando a SCC4 efetuar  a cobrança dos valores <br>relativos aos custos desse serviço em suas proximas faturas.",
                    title: "CADASTRAR ENVIO DE EMAIL COM ATUALIZAÇÃO DO SRO?",
                    onEscape: function () {
                    },
                    show: true,
                    backdrop: true,
                    closeButton: true,
                    animate: true,
                    className: "my-modal",
                    buttons: {
                        success: {
                            label: "<i class='fa fa-lg fa-check fa-spc'></i> CONCORDO",
                            className: "btn-success",
                            callback: function () {
                                mostraTipoServer();
                            }
                        },
                        "ME TIRE DAQUI!": {
                            className: "btn-danger",
                            callback: function () {
                                meTireDaqui();
                            }
                        },
                    }
                });
            }






        </script>
    </head>        
    <body>   
        <script type="text/javascript">
            waitMsg();
        </script> 
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                        <jsp:include page="cliente_menu_b.jsp" >
                            <jsp:param name="nomeBDTab" value="<%= nomeBD%>" />
                            <jsp:param name="activeTab" value="0" />
                            <jsp:param name="idClienteTab" value="<%= idClienteInc%>" />
                            <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato()%>" />
                            <jsp:param name="nomeClienteTab" value="<%= cliInc.getNomeFantasia()%>" />
                        </jsp:include>    

                        <div class="row">
                            <div class="col-xs-12">
                                <form name="form1" action="../../ServClienteLatLng" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <b><i class="fa fa-lg fa-spc fa-user"></i>&nbsp;&nbsp;&nbsp;DADOS CADASTRAIS DO CLIENTE</b>
                                        </li>
                                        <li class="list-group-item" >
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">CNPJ</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-key fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="cnpj" id="cnpj" value="<%= cnpj%>" maxlength="18" onKeyPress="mascara(this, maskCpfCnpj)"  />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-5 col-lg-5">
                                                    <label class="small">Razão Social</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-user fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="razao" id="razao" value="<%= nome%>" maxlength="60" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Fantasia</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-user fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="fantasia" id="fantasia" value="<%= fantasia%>" maxlength="25" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">E-mail</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-at fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="email" id="email" value="<%= email%>" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">Telefone</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-phone fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="telefone" id="telefone" value="<%= telefone%>" onKeyPress="mascara(this, maskTelefone)"  />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-5">
                                                    <label class="small">Grupo de Faturamento</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-group fa-fw"></i></span>                                                             
                                                        <select class="form-control" name="grupo_fat" id="grupo_fat">
                                                            <option value="0">-- SELECIONE --</option>
                                                            <%
                                                                ArrayList<GrupoFaturamento> listaGrupo = ContrGrupoFaturamento.consultaTodosTipoColeta(nomeBD);
                                                                for (int i = 0; i < listaGrupo.size(); i++) {
                                                                    GrupoFaturamento gf = listaGrupo.get(i);
                                                                    String sel = "";
                                                                    if (cliInc.getIdGrupoFaturamento() == gf.getId()) {
                                                                        sel = " selected='true' ";
                                                                    }
                                                                    out.println("<option " + sel + " value='" + gf.getId() + "'>" + gf.getSigla() + " - " + gf.getNome() + "</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">CEP</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-search fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="cep" id="cep" value="<%= cep%>" maxlength="25" onKeyPress="mascara(this, maskCep)"  />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-5 col-lg-5">
                                                    <label class="small">Logradouro</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="logradouro" id="logradouro" value="<%= logradouro%>" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">Número</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="numero" id="numero" value="<%= numero%>" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">Complemento</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="complemento" id="complemento" value="<%= complemento%>" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-5 col-lg-3">
                                                    <label class="small">Bairro</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="bairro" id="bairro" value="<%= bairro%>" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Cidade</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="cidade" id="cidade" value="<%= cidade%>" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">UF</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                             
                                                        <select class="form-control" name="uf" id="uf">
                                                            <option value="" selected='true'>SELECIONE</option>
                                                            <option value="AC" <%if ("AC".equals(uf)) {%> selected='true' <%}%>>AC</option>
                                                            <option value="AL" <%if ("AL".equals(uf)) {%> selected='true' <%}%>>AL</option>
                                                            <option value="AP" <%if ("AP".equals(uf)) {%> selected='true' <%}%>>AP</option>
                                                            <option value="AM" <%if ("AM".equals(uf)) {%> selected='true' <%}%>>AM</option>
                                                            <option value="BA" <%if ("BA".equals(uf)) {%> selected='true' <%}%>>BA</option>
                                                            <option value="CE" <%if ("CE".equals(uf)) {%> selected='true' <%}%>>CE</option>
                                                            <option value="DF" <%if ("DF".equals(uf)) {%> selected='true' <%}%>>DF</option>
                                                            <option value="ES" <%if ("ES".equals(uf)) {%> selected='true' <%}%>>ES</option>
                                                            <option value="GO" <%if ("GO".equals(uf)) {%> selected='true' <%}%>>GO</option>
                                                            <option value="MA" <%if ("MA".equals(uf)) {%> selected='true' <%}%>>MA</option>
                                                            <option value="MT" <%if ("MT".equals(uf)) {%> selected='true' <%}%>>MT</option>
                                                            <option value="MS" <%if ("MS".equals(uf)) {%> selected='true' <%}%>>MS</option>
                                                            <option value="MG" <%if ("MG".equals(uf)) {%> selected='true' <%}%>>MG</option>
                                                            <option value="PA" <%if ("PA".equals(uf)) {%> selected='true' <%}%>>PA</option>
                                                            <option value="PB" <%if ("PB".equals(uf)) {%> selected='true' <%}%>>PB</option>
                                                            <option value="PR" <%if ("PR".equals(uf)) {%> selected='true' <%}%>>PR</option>
                                                            <option value="PE" <%if ("PE".equals(uf)) {%> selected='true' <%}%>>PE</option>
                                                            <option value="PI" <%if ("PI".equals(uf)) {%> selected='true' <%}%>>PI</option>
                                                            <option value="RJ" <%if ("RJ".equals(uf)) {%> selected='true' <%}%>>RJ</option>
                                                            <option value="RN" <%if ("RN".equals(uf)) {%> selected='true' <%}%>>RN</option>
                                                            <option value="RS" <%if ("RS".equals(uf)) {%> selected='true' <%}%>>RS</option>
                                                            <option value="RO" <%if ("RO".equals(uf)) {%> selected='true' <%}%>>RO</option>
                                                            <option value="RR" <%if ("RR".equals(uf)) {%> selected='true' <%}%>>RR</option>
                                                            <option value="SC" <%if ("SC".equals(uf)) {%> selected='true' <%}%>>SC</option>
                                                            <option value="SP" <%if ("SP".equals(uf)) {%> selected='true' <%}%>>SP</option>
                                                            <option value="SE" <%if ("SE".equals(uf)) {%> selected='true' <%}%>>SE</option>
                                                            <option value="TO" <%if ("TO".equals(uf)) {%> selected='true' <%}%>>TO</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-sm-6 col-md-4 col-lg-3">
                                                    <label class="small">&nbsp;</label>
                                                    <div>                                                        
                                                        <button style="width: 230px;" type="button" class="btn btn-info form-control" onclick="showAddress(document.getElementById('address').value);"><i class="fa fa-search fa-spc"></i>Mostrar Endereço no Mapa</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </li> 
                                    </ul>

                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <b><i class="fa fa-lg fa-spc fa-map-marker"></i>&nbsp;&nbsp;&nbsp;LOCALIZAÇÃO DO CLIENTE</b>
                                        </li>
                                        <li class="hidden" >
                                            <div class="row form-horizontal">

                                                <div class="col-sm-6 col-md-4 col-lg-2">
                                                    <label class="small">Latitude</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-compass fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" readonly="true" name="lat" id="lat" value="0" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-2">
                                                    <label class="small">Longitude</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-compass fa-fw"></i></span>  
                                                        <input class="form-control" type="texte" readonly="true" name="lng" id="lng" value="0" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-8 col-lg-5">
                                                    <label class="small">Longitude</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-map-marker fa-fw"></i></span>  
                                                        <input class="form-control" type="text" size="120" name="address" id="address" value="<%= endereco%>" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-3">
                                                    <label class="small">&nbsp;</label>
                                                    <div>                                                        
                                                        <button style="width: 180px;" type="button" class="btn btn-info form-control" onclick="showAddress(document.getElementById('address').value);"><i class="fa fa-search fa-spc"></i>Procurar Endereço</button>
                                                    </div>
                                                </div>

                                            </div>


                                        </li>
                                        <li class="list-group-item" style="padding: 0;">
                                            <div align="center" id="map" style="width: 100%; height: 450px"></div>
                                        </li>

                                    </ul>
                                    <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                    <button type="button" class="btn btn-success" onclick="document.form1.submit();"><i class="fa fa-lg fa-spc fa-save"></i> SALVAR DADOS</button>
                                </form>
                            </div>
                        </div>
                        <div class="row spacer-sm"></div>
                        <div class="row">
                            <div class="col-md-12"> 
                                <ul class="list-group">
                                    <li class="list-group-item list-group-item-danger">
                                        <div class="form-inline">
                                            <label>&nbsp;</label>
                                            <label><input type="checkbox" name="is_cadastro" value="1" id="is_cadastro" readonly onclick="confirmaCadastro()"/> CADASTRAR SERVIDOR PARA E-MAILS COM ATUALIZAÇÕES DO SRO </label>
                                            <label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(ATENÇÃO AO EFETUAR ESSE CADASTRO SERÃO GERADOS ENCARGOS)</label>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <form name="form2" action="../../ServCriaSMTP" method="post">
                            <div class="row hidden" id="mostra_smtp">
                                <div class="col-md-12"> 
                                    <ul class="list-group">
                                        <li class="list-group-item list-group-item-warning">
                                            <div class="form-inline">
                                                <label>&nbsp;</label>
                                                <label><input type="radio" name="is_smtp_client" value="1" id="is_smtp_client" onclick="mostraCampos2()"/> CADASTRAR SERVIDOR SMTP (CUSTO R$ 0,10/objeto)</label>
                                            </div>
                                            <div class="form-inline">
                                                <label>&nbsp;</label>
                                                <label><input type="radio" name="is_smtp_client" value="0" id="is_smtp_client" checked="checked" onclick="mostraCampos2()"/>UTILIZAR SERVIDOR DO PORTALPOSTAL (R$40,00/mil e-mails)</label>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="row" >
                                <div class="col-md-12">   

                                    <ul class="list-group hidden" id="camposSMTP">
                                        <li class="list-group-item list-group-heading">
                                            <label>CADASTRE O SERVIDOR SMTP DO CLIENTE OU DA AGF</label>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">SMTP</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-envelope"></i></span>
                                                        <input class="form-control" type="text" autocomplete="off" placeholder="smtp.provedor.com.br" name="smtp" />
                                                    </div>   
                                                    <div id="foo"></div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">PORTA</label>                                            
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-globe"></i></span>
                                                        <input class="form-control" placeholder="porta smtp" type="text" name="porta_smtp" onkeypress="mascara(this, maskNumero)" />
                                                    </div>
                                                </div>
                                            </div>
                                        </li> 
                                        <li class="list-group-item list-group-item-warning">
                                            <div class="form-inline">
                                                <label>&nbsp;</label>
                                                <label><input type="checkbox" name="is_autenticacao"  id="is_autenticacao" value="1" onclick="mostraCampos()"/> UTILIZA AUTENTICAÇÃO</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item hidden" id="campos">
                                            <div class="row form-horizontal">   
                                                <div class="col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">USUARIO</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-user"></i></span>
                                                        <input class="form-control" type="text" autocomplete="off" placeholder="usuario" name="usuario" />
                                                    </div>   
                                                    <div id="foo"></div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">SENHA</label>                                            
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-globe"></i></span>
                                                        <input class="form-control" placeholder="senha" type="password" name="senha" />
                                                    </div>
                                                </div> 
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">TIPO SEGURANÇA</label>                                                   
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-lock"></i></span>
                                                        <select class="form-control" name="tipo_seg" >
                                                            <option value="NENHUMA">NENHUMA</option>
                                                            <option value="SSL">SSL</option>
                                                            <option value="TLS">TLS</option>
                                                            <option value="STARTTLS">STARTTLS</option>
                                                        </select>
                                                    </div> 
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">PORTA SSL*</label>                                            
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-globe"></i></span>
                                                        <input class="form-control" placeholder="porta ssl/tsl" type="text" name="porta_ssl" onkeypress="mascara(this, maskNumero)" />
                                                    </div>
                                                </div>
                                            </div>                                        
                                        </li>
                                    </ul>

                                    <ul class="list-group hidden" id="cad_email">
                                        <li class="list-group-item">
                                            <div class="form-inline">
                                                <label>&nbsp;</label>
                                                <label><input type="checkbox" name="is_destinatario" value="1" id="is_destinatario" checked="checked"/> ENVIAR PARA O E-MAIL DO DESTINATARIO (deve estar cadastrado)</label>
                                            </div>
                                        </li>
                                        <li class="list-group-item" id="campos">
                                            <div class="row form-horizontal">                                             
                                                <div class="col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small"> ADICIONAR OUTROS E-MAILS (separados por ;)</label>                                            
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-inbox"></i></span>
                                                        <input class="form-control" placeholder="digite os e-mails (separe por ;)" type="text" name="add_mail"  />
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">

                                                <div class="col-sm-12 col-md-4 col-lg-4">
                                                    <label class="small">
                                                        DEPARTAMENTOS:<br/>
                                                        <a style="color:blue;font-size: 9px;" onclick="selectAllCombo(document.getElementById('departamentos'), true);">MARCAR TUDO</a><br/>
                                                        <a style="color:red;font-size: 9px;" onclick="selectAllCombo(document.getElementById('departamentos'), false);">DESMARCAR TUDO</a>
                                                    </label>
                                                    <select class="form-control" name='departamentos' id='departamentos' multiple='true' onclick="controleCombobox1(this)" size=10 >
                                                        <%
                                                            ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idClienteInc, nomeBD);
                                                            for (int i = 0; i < listaDep.size(); i++) {
                                                                ClientesDeptos cd = listaDep.get(i);
                                                        %>
                                                        <option value="<%=cd.getIdDepartamento()%>"><%= cd.getNomeDepartamento()%></option>
                                                        <%}%>
                                                    </select>
                                                    <script language="javascript">
                                                        function selectAllCombo(combo, flag) {
                                                            for (i = 0; i < combo.length; i++) {
                                                                combo.options[i].selected = flag;
                                                            }
                                                        }
                                                        function controleCombobox1(combo) {
                                                            combo_aux1[combo.selectedIndex] = !combo_aux1[combo.selectedIndex];
                                                            for (i = 0; i < combo.length; i++) {
                                                                combo.options[i].selected = combo_aux1[i];
                                                            }
                                                        }
                                                        var combo_aux1 = new Array(document.getElementById("departamentos").options.length);
                                                        for (i = 0; i < document.getElementById("departamentos").options.length; i++) {
                                                            combo_aux1[i] = document.getElementById("departamentos").options[i].selected;
                                                        }
                                                    </script>
                                                </div>

                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <input type="hidden" name="local" value="1" />
                                            <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                            <button type="button" class="btn btn-success" onclick="document.form2.submit();" ><i class="fa fa-lg fa-spc fa-save"></i> SALVAR DADOS</button>                                        
                                        </li>
                                    </ul>
                                    </form>

                                    <div class="panel panel-default">
                                        <div class="panel-heading"><label>Lista dos SMTP/Departamentos Cadastrados</label></div>
                                        <div class="panel-body">
                                            <div class="dataTable_wrapper no-padding">
                                                <table class="table table-striped table-bordered table-hover table-condensed" style="table-layout: fixed;word-wrap: break-word;" id="dataTables-example">
                                                    <thead>
                                                        <tr>
                                                            <th>SMTP SERVER</th>
                                                            <th style="width: 125px;">AUTENTICAÇÃO </th>
                                                            <th>DEPARTAMENTOS</th>
                                                            <th class="no-sort" style="width: 65px;">Excluir</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%
                                                            ArrayList<ClienteSMTP> lista = Controle.ContrClienteSMTP.consultaCadastroSMTP(idClienteInc, nomeBD);
                                                            for (int i = 0; i < lista.size(); i++) {
                                                                ClienteSMTP smtp = lista.get(i);

                                                        %>
                                                        <tr>
                                                            <td><%= smtp.getSmtp()%></td>
                                                            <td><%= smtp.getTipo_seguranca()%></td>
                                                            <td style="max-width: 200px;"><%= smtp.getIdDepartamento()%></td>                                


                                                            <td align="center">
                                                                <form action="../../ServExcluirSMTP" method="post" name="formDel">
                                                                    <input type="hidden" name="local" value="1" />
                                                                    <input type="hidden" name="id_smtp" value="<%= smtp.getId()%>" />  
                                                                    <button type="button" class="btn btn-sm btn-danger" onClick="confirmExcluir(this);" ><i class="fa fa-trash fa-lg"></i></button>
                                                                </form>
                                                            </td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row spacer-xlg"></div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
<%}%>

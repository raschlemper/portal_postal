
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


    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("405")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {

        empresas agf_empresa = (empresas) session.getAttribute("agf_empresa");
        
        int idClienteInc = Integer.parseInt(request.getParameter("idCliente"));
        
        ClienteSMTP cliSmtp = Controle.ContrClienteSMTP.consultaCadastroSMTP(idClienteInc, agf_empresa.getCnpj());

        Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idClienteInc, agf_empresa.getCnpj());
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
        String obs = cliInc.getObs();
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

                //$('#mostra_smtp').toggleClass("hidden");
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
                    title: 'Excluir Usu�rio do Cliente?',
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
                    message: "Ao efetuar este o cadastro voc� estar� autorizando a SCC4 efetuar  a cobran�a dos valores <br>relativos aos custos desse servi�o em suas proximas faturas. <br/>\n\
        <br/><b>CUSTO PARA UTILIZA��O DESTE SERVI�O (V�LIDO PARA TODOS OS CLIENTES) </b><br></br>- R$ 50,00 mensal com direito de 4.000 objetos/m�s. <br></br>- Acima de 4.000 objetos/m�s custo adicional de R$0,05 por objeto.\n\
<br/><br/> <span style='color:red'>*Esta ativa��o � valida para todos os clientes da sua AGF.<br/>*Caso j� tenha ativado este servi�o no cadastro de outro cliente, n�o ser�o gerados encargos adicionais respeitando os limites de envios de e-mails descritos acima.</span>",
                    title: "CADASTRAR ENVIO DE EMAIL COM ATUALIZA��ES DO SRO?",
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

            function desativaCli() {
                var desat = $("#ck_destivar").val();
                var desat2 = $("#nomeBD").val();
                alert(desat);
                $.ajax({
                    url: '../../ServDesativaCliente',
                    data: {nome: desat, nomeBD: desat2},
                    type: 'get',
                    cache: false,
                    success: function (data) {
                        alert(data);                             
                        window.location= "cliente_lista_b.jsp";
                    },
                    error: function () {
                        alert('Erro na requisi��o');
                    }
                }
                );
            }
            
               
            function validarDados(){
                
              if(!$('#cnpj').val()){
                  alert('Preencha o CNPJ');
                  return false;
              }else if(!$('#razao').val()){
                  alert('Preencha a raz�o social ');
                  return false;
              }else if(!$('#fantasia').val()){
                  alert('Preencha o nome fantasia ');
                  return false;
              }else if(!$('#telefone').val()){
                  alert('Preencha o telefone');
                  return false;
              }else if(!$('#email').val()){
                  alert('Preencha o email');
                  return false;
              }else if(!$('#cep').val()){
                  alert('Preencha o CEP');
                  return false;
              }else if(!$('#logradouro').val()){
                  alert('Preencha a rua ');
                  return false;
              }else if(!$('#numero').val()){
                  alert('Preencha o numero');
                  return false;
              }else if(!$('#bairro').val()){
                  alert('Preencha o bairro');
                  return false;
              }else if(!$('#cidade').val()){
                  alert('Preencha a cidade ');
                  return false;
              }else if(!$('#uf').val()){
                  alert('escolha o estado');
                  return false;
              }else {
                  document.form1.submit();
              }                
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
                            <jsp:param name="nomeBDTab" value="<%= agf_empresa.getCnpj()%>" />
                            <jsp:param name="activeTab" value="0" />
                            <jsp:param name="idClienteTab" value="<%= idClienteInc%>" />
                            <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato()%>" />
                            <jsp:param name="nomeClienteTab" value="<%= cliInc.getNomeFantasia()%>" />
                        </jsp:include>   

                        <%if(agf_empresa.getTipo_sistema().equals("PORTALPOSTAL")){%>
                            <div class="row">
                                <div class="col-xs-12">
                                <div id="destiva"> </div>
                                <div class="form-inline">
                                    <label><input id="ck_destivar" name="ck_desativar" type="checkbox" value="<%= idClienteInc%>" onclick="desativaCli();" >&nbsp;&nbsp;DESATIVAR ESTE CLIENTE</label>
                                    <input type="hidden" name="nomeBD" value="<%=agf_empresa.getCnpj()%>" id="nomeBD" />
                                </div>
                                </div>
                            </div>
                        <%}%>

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
                                                    <label class="small">CPF/CNPJ</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-key fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="cnpj" id="cnpj" value="<%= cnpj%>" maxlength="18" onKeyPress="mascara(this, maskCpfCnpj)"  />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-5 col-lg-5">
                                                    <label class="small">Raz�o Social</label>
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
                                                                ArrayList<GrupoFaturamento> listaGrupo = ContrGrupoFaturamento.consultaTodosTipoColeta(agf_empresa.getCnpj());
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
                                                    <label class="small">N�mero</label>
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
                                                        <button style="width: 230px;" type="button" class="btn btn-info form-control" onclick="showAddress(document.getElementById('address').value);"><i class="fa fa-search fa-spc"></i>Mostrar Endere�o no Mapa</button>
                                                    </div>
                                                </div>
                                                <div class="col-sm-12 col-md-12 col-lg-12">
                                                    <label class="small">Observa��es</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                                                                             
                                                        <textarea class="form-control" name="obs" id="obs" ><%= obs %></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </li> 
                                    </ul>

                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <b><i class="fa fa-lg fa-spc fa-map-marker"></i>&nbsp;&nbsp;&nbsp;LOCALIZA��O DO CLIENTE</b>
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
                                                        <button style="width: 180px;" type="button" class="btn btn-info form-control" onclick="showAddress(document.getElementById('address').value);"><i class="fa fa-search fa-spc"></i>Procurar Endere�o</button>
                                                    </div>
                                                </div>

                                            </div>


                                        </li>
                                        <li class="list-group-item" style="padding: 0;">
                                            <div align="center" id="map" style="width: 100%; height: 450px"></div>
                                        </li>

                                    </ul>
                                    <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                    <button type="button" class="btn btn-success" onclick="validarDados();"><i class="fa fa-lg fa-spc fa-save"></i> SALVAR DADOS</button>
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
                                            <label><input type="checkbox" name="is_cadastro" value="1" id="is_cadastro" readonly onclick="confirmaCadastro()" <%if(cliSmtp != null){%> checked="checked" disabled<%}%>/> ENVIAR E-MAILS COM ATUALIZA��ES DO SRO </label>
                                            <label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(ATEN��O AO EFETUAR ESSE CADASTRO SER�O GERADOS ENCARGOS)</label>
                                        </div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <form name="form2" action="../../ServCriaSMTP" method="post">
                            
                            <div class="row" >
                                <div class="col-md-12">   
                                    <input type="hidden" name="is_smtp_client" value="0" id="is_smtp_client" checked="checked" onclick="mostraCampos2()"/> 
                                           
                                
                                    <%
                                    boolean dest = true;
                                    if(cliSmtp != null){
                                       if(cliSmtp.getEnvia_destinatario() == 0){
                                           dest = false;
                                       }
                                    }
                                    %>
                                    <ul class="list-group <%if(cliSmtp == null){%>hidden<%}%>" id="cad_email">
                                        <li class="list-group-item">
                                            <div class="form-inline">
                                                <!-- verificar se ja que tipo est� cadastrado -->
                                                <label>&nbsp;</label>
                                                <label><input type="radio" name="is_destinatario" value="1" id="is_destinatario" <%if(dest){%> checked="checked" <%}%>/> ENVIAR PARA O E-MAIL DO DESTINATARIO (deve estar cadastrado na venda)</label>
                                            </div>
                                            <div class="form-inline">
                                                <label>&nbsp;</label>
                                                <label><input type="radio" name="is_destinatario" value="0" id="is_destinatario" <%if(!dest){%> checked="checked" <%}%>/> ENVIAR PARA O E-MAIL DESTE CLIENTE (deve estar cadastrado acima) </label>
                                            </div>
                                        </li>
                                    
                                        <li class="list-group-item">
                                            <input type="hidden" name="local" value="1" />
                                            <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                            <button type="button" class="btn btn-success" onclick="document.form2.submit();" ><i class="fa fa-lg fa-spc fa-save"></i> CONFIRMAR ENVIOS</button>   
                                            <input type="hidden" id="cancelar" name="cancelar" value="0"/>            
                                          <%if(cliSmtp != null){%>                                            
                                          <button type="button" class="btn btn-danger" onclick="document.getElementById('cancelar').value = '1';document.form2.submit();" ><i class="fa fa-lg fa-spc fa-trash"></i> CANCELAR ENVIOS</button>   
                                          <%}%>                                     
                                        </li> 
                                    </ul>
                                    </form>
  
                                
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

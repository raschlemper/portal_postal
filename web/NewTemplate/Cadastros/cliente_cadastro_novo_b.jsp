
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

        String nomeBD = (String) session.getAttribute("empresa");
        empresas emp = (empresas) session.getAttribute("emp");

        //dados para gerar o mapa
        double lat = 0;
        double lng = 0;
        String endereco = emp.getCidade() + " / " + emp.getUf();
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
                    zoom: 15,
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

                if (address === '') {
                    address = document.getElementById("logradouro_").value +
                            " , " +
                            document.getElementById("numero_").value +
                            " , " +
                            document.getElementById("cidade_").value +
                            " - " +
                            document.getElementById("uf_").value;
                }

                geocoder.geocode({'address': address}, function (results, status) {
                    if (status === google.maps.GeocoderStatus.OK) {
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

            $(document).ready(function () {
                load();
                fechaMsg();
            });
            
            
            function validarDados(){
                
              if(!$('#cnpj').val()){
                  alert('Preencha o CNPJ');
                  return false;
              }else if(!$('#razao').val()){
                  alert('Preencha a razão social ');
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
              }else if(!$('#logradouro_').val()){
                  alert('Preencha a rua ');
                  return false;
              }else if(!$('#numero_').val()){
                  alert('Preencha o numero');
                  return false;
              }else if(!$('#bairro').val()){
                  alert('Preencha o bairro');
                  return false;
              }else if(!$('#cidade_').val()){
                  alert('Preencha a cidade ');
                  return false;
              }else if(!$('#uf_').val()){
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

                        <%--<jsp:include page="cliente_menu_b.jsp" >
                            <jsp:param name="nomeBDTab" value="<%= nomeBD%>" />
                            <jsp:param name="activeTab" value="0" />
                            <jsp:param name="idClienteTab" value="<%= idClienteInc%>" />
                            <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato()%>" />
                            <jsp:param name="nomeClienteTab" value="<%= cliInc.getNomeFantasia()%>" />
                        </jsp:include>   --%>

                        <div class="row">
                            <div class="col-xs-12">
                                <form name="form1" action="../../ServClienteInserir" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <b><i class="fa fa-lg fa-spc fa-user"></i>&nbsp;&nbsp;&nbsp;DADOS CADASTRAIS DO CLIENTE</b>
                                        </li>
                                        <li class="list-group-item" >
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">CPF/CNPJ *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-key fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="cnpj" id="cnpj" maxlength="18" value="" onKeyPress="mascara(this, maskCpfCnpj)" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-5 col-lg-5">
                                                    <label class="small">Razão Social *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-user fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="razao" id="razao" value="" maxlength="60" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Fantasia *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-user fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="fantasia" id="fantasia" value="" maxlength="25" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">E-mail *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-at fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="email" id="email" maxlength="40" value="" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">Telefone *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-phone fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="telefone" id="telefone" value="" maxlength="25" onKeyPress="mascara(this, maskTelefone)" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-5">
                                                    <label class="small">Grupo de Faturamento</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-group fa-fw"></i></span>                                                             
                                                        <select class="form-control" name="grupo_fat" id="grupo_fat">
                                                            <option value="0" selected='true'>-- SELECIONE --</option>
                                                            <%
                                                                ArrayList<GrupoFaturamento> listaGrupo = ContrGrupoFaturamento.consultaTodosTipoColeta(nomeBD);
                                                                for(int i=0; i<listaGrupo.size(); i++){
                                                                    GrupoFaturamento gf = listaGrupo.get(i);
                                                                    out.println("<option value='"+gf.getId()+"'>"+gf.getSigla()+" - "+gf.getNome()+"</option>");
                                                                }
                                                            %>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row form-horizontal">
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">CEP *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-search fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="cep" id="cep" value="" maxlength="9" onKeyPress="mascara(this, maskCep)" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-5 col-lg-5">
                                                    <label class="small">Logradouro *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="logradouro" id="logradouro_" maxlength="100" value="" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">Número *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="numero" id="numero_" maxlength="10" value="" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">Complemento</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="complemento" id="complemento" maxlength="40" value="" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-5 col-lg-3">
                                                    <label class="small">Bairro *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="bairro" id="bairro" maxlength="50" value="" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Cidade *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                                                                             
                                                        <input class="form-control" type="text" name="cidade" id="cidade_" maxlength="60" value="" />
                                                    </div>
                                                </div>
                                                <div class="col-sm-6 col-md-3 col-lg-2">
                                                    <label class="small">UF *</label>
                                                    <div class="input-group">                                                        
                                                        <span class="input-group-addon" ><i class="fa fa-home fa-fw"></i></span>                                                             
                                                        <select class="form-control" name="uf" id="uf_">
                                                            <option value="" selected='true'>SELECIONE</option>
                                                            <option value="AC" >AC</option>
                                                            <option value="AL" >AL</option>
                                                            <option value="AP" >AP</option>
                                                            <option value="AM" >AM</option>
                                                            <option value="BA" >BA</option>
                                                            <option value="CE" >CE</option>
                                                            <option value="DF" >DF</option>
                                                            <option value="ES" >ES</option>
                                                            <option value="GO" >GO</option>
                                                            <option value="MA" >MA</option>
                                                            <option value="MT" >MT</option>
                                                            <option value="MS" >MS</option>
                                                            <option value="MG" >MG</option>
                                                            <option value="PA" >PA</option>
                                                            <option value="PB" >PB</option>
                                                            <option value="PR" >PR</option>
                                                            <option value="PE" >PE</option>
                                                            <option value="PI" >PI</option>
                                                            <option value="RJ" >RJ</option>
                                                            <option value="RN" >RN</option>
                                                            <option value="RS" >RS</option>
                                                            <option value="RO" >RO</option>
                                                            <option value="RR" >RR</option>
                                                            <option value="SC" >SC</option>
                                                            <option value="SP" >SP</option>
                                                            <option value="SE" >SE</option>
                                                            <option value="TO" >TO</option>
                                                        </select>
                                                    </div>
                                                </div>

                                                <div class="col-sm-6 col-md-4 col-lg-3">
                                                    <label class="small">&nbsp;</label>
                                                    <div>                                                        
                                                        <button style="width: 230px;" type="button" class="btn btn-info form-control" onclick="showAddress('');"><i class="fa fa-search fa-spc"></i>Mostrar Endereço no Mapa</button>
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
                                    <button type="button" class="btn btn-success" onclick="validarDados(); "><i class="fa fa-lg fa-spc fa-save"></i> SALVAR DADOS</button>
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
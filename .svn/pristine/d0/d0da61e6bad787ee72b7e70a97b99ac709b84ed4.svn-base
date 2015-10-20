
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
        int idClienteInc = Integer.parseInt(request.getParameter("idCliente"));
        
        Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idClienteInc, nomeBD);
        String enderecoInc = cliInc.getEndereco();
        String cidadeInc = cliInc.getCidade();
        String ufInc = cliInc.getUf();
        double lat = cliInc.getLatitude();
        double lng = cliInc.getLongitude();

        String endereco = enderecoInc + " , " + cidadeInc + " - " + ufInc;
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="Expires" content="-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />

        <!-- Menu -->
        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <!-- Menu -->

        <title>Portal Postal | Sequências Lógicas</title>

        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />        
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

                google.maps.event.addListener(marker, "dragend", function() {
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
                geocoder.geocode({'address': address}, function(results, status) {
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
        </script>
    </head>
    <body onload="load()" >
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Localização do Cliente <span><%= cliInc.getNomeFantasia()%></span></div>

                    <jsp:include page="cliente_tab_menu.jsp" >
                        <jsp:param name="nomeBDTab" value="<%= nomeBD%>" />
                        <jsp:param name="activeTab" value="6" />
                        <jsp:param name="idClienteTab" value="<%= idClienteInc%>" />
                        <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato()%>" />
                    </jsp:include>


                    <form name="form1" action="../../ServClienteLatLng" method="post">
                        <ul class="ul_dados">
                            <li>
                                <dd class="titulo"><span>Localização do Cliente</span></dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Latitude</label>
                                    <input type="text" readonly="true" name="lat" id="lat" value="0" />
                                &nbsp;&nbsp;
                                </dd>
                                <dd>
                                    <label>Longitude</label>
                                    <input type="texte" readonly="true" name="lng" id="lng" value="0" />
                                </dd>
                            </li>                            
                            <li>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                        <button type="button" class="positive" onclick="document.form1.submit();"><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>

                    <div id="titulo2" style="border:0px;">        
                        <input type="text" size="120" name="address" id="address" value="<%= endereco%>" />
                        <input type="button" style="border: 1px solid silver; padding: 3px 5px;" value="Procurar Endereço" onclick="showAddress(document.getElementById('address').value);" />
                    </div>
                    <p>
                        <div align="center" id="map" style="width: 100%; height: 450px"><br/></div>
                    </p>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
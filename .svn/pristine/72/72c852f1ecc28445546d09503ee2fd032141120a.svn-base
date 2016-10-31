<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("../index.jsp?msgLog=3");
            } else {
                String nomeBD = (String) session.getAttribute("empresa");


                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                Entidade.Clientes cli = Controle.contrCliente.consultaClienteById(idCliente, nomeBD);
                String nomeCliente = cli.getNome();
                String cidade = cli.getCidade().trim();
                String uf = cli.getUf().trim();
                String endereco = cli.getEndereco().trim();
                String complemento = cli.getComplemento().trim();
                String aux[] = endereco.split(",");
                String rua = endereco, numero = "";
                if (aux.length > 1) {
                    rua = aux[0];
                    numero = aux[1];
                    //String aux2[] = aux[1].split(" ");
                }
                endereco = numero + " " + rua + " , " + cidade + " - " + uf;
%>
  
<html>
    <head>
        <title> Portal Postal</title>   
        <script src="http://maps.google.com/maps?file=api&amp;v=2.x&amp;key=ABQIAAAAlJVKHaE-vcwZVqBrPdeTCRQtpG-lJVXecy3lwOZXVoU2jM-mXRT0zkCraFwHz9ShlAOJRH2d-1jy7w" type="text/javascript"></script>
        <script type="text/javascript">

            var map = null;
            var geocoder = null;
            var gdir;
            var addressMarker;
            var side_bar_html = "";
            var gmarkers = [];
            var htmls = [];
            var Icon = new GIcon(G_DEFAULT_ICON);
            var bounds = new GLatLngBounds();
            var address = "<%= endereco%>";
            
            function initialize() {
                if (GBrowserIsCompatible()) {
                    map = new GMap2(document.getElementById("map_canvas"));
                    map.setCenter(new GLatLng(-27.595859,-48.547039), 15);
                    geocoder = new GClientGeocoder();

                    //map.setCenter(new GLatLng(0,0,0));
                    map.addControl(new GLargeMapControl());
                    map.addControl(new GMapTypeControl());
                    map.addControl(new GOverviewMapControl());
                }
                if (geocoder) {
                    geocoder.getLatLng(
                    address,
                    function(point) {
                        if (!point) {
                            alert("Endereço não encontrado: <%= endereco%>");
                        } else {
                            map.setCenter(point, 16);
                            map.addOverlay(new GMarker(point));
                            map.addOverlay(createMarker(point,"<%= nomeCliente%><br><%= endereco + " , " + complemento%><br />",0));
                            side_bar_html += '<a href="javascript:myclick(0);" onmouseover="gmarkers[0].setImage(\'../imagensNew/marker_vermelho.png\');" onmouseout="gmarkers[0].setImage(\'imagensNew/ns/marker_azul.png\');">- <%= nomeCliente%></a><br><br>';
                            bounds.extend(point);
                            latitude = 0;
                            longitude = 0;

                            // Adiciona texto na barra lateral
                            document.getElementById("side_bar").innerHTML = side_bar_html;
                            // ===== determine the zoom level from the bounds =====
                            map.setZoom(map.getBoundsZoomLevel(bounds));
                            // ===== determine the centre from the bounds ======
                            map.setCenter(bounds.getCenter());
                        }
                    }
                );
                }
            }
            function createMarker(point,html,contador) {
                var marker = new GMarker(point,Icon);
                GEvent.addListener(marker, "click", function() {
                    marker.openInfoWindowHtml(html);
                });
                // Switch icon on marker mouseover and mouseout
                GEvent.addListener(marker, "mouseover", function() {
                    marker.setImage("../imagensNew/marker_vermelho.png");
                });
                GEvent.addListener(marker, "mouseout", function() {
                    marker.setImage("../imagensNew/marker_azul.png");
                });
                htmls[contador] = html;
                gmarkers[contador] = marker;
                return marker;
            }
            function myclick(i) {
                gmarkers[i].openInfoWindowHtml(htmls[i]);
            }
        </script>
    </head>
    <body onLoad="initialize()" onUnload="GUnload(); showAddress();">
        <b style="display:none;" id="side_bar"></b>
        <div class="no-margin no-padding" id="map_canvas" style="width:100%; height:500px;"></div>
    </body>
</html>
<%}%>
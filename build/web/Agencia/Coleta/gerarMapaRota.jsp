<%--
    Document   : imprimirRota
    Created on : 29/05/2009, 16:16:33
    Author     : SCC4
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>

<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevent caching at the proxy server

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

//pega os parametros passados para a pagina
        String dataAtual = request.getParameter("dataPesquisa");
        String vIdColet = request.getParameter("idColetador");
//cria uma data atual
        Date dataPesquisa = new Date();
//declara os simple date format
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
//verifica se a data passada pelo parametro nao eh nula nem vazia
        if (dataAtual == null || dataAtual.equals("")) {
            dataAtual = sdf1.format(dataPesquisa);
        } else {
            dataPesquisa = df.parse(dataAtual);
        }

        String vDataPesquisa = sdf3.format(dataPesquisa);
        int idColetador = Integer.parseInt(vIdColet);
        String coletador = Coleta.Controle.contrColetador.consultaNomeColetadoresById(idColetador, nomeBD);

        String listaEndereco = "";
        ArrayList listaColetasRep = Coleta.Controle.contrColeta.consultaColetasPeloStatus(2, idColetador, vDataPesquisa, "dataHoraColeta, cliente.endereco", nomeBD);
        for (int j = 0; j < listaColetasRep.size(); j++) {
            Coleta.Entidade.Coleta col = (Coleta.Entidade.Coleta) listaColetasRep.get(j);
            int idCliente = col.getIdCliente();
            Entidade.Clientes cli = Controle.contrCliente.consultaClienteById(idCliente, nomeBD);
            String nomeCliente = cli.getNomeFantasia();
            String cidade = cli.getCidade().trim();
            String uf = cli.getUf().trim();
            String endereco = cli.getEndereco().trim();
            String end = endereco + " , " + cidade + " - " + uf;
            if (j == 0) {
                listaEndereco += "\nnew objEnd('" + nomeCliente + "', '" + end + "')";
            } else {
                listaEndereco += "\n, new objEnd('" + nomeCliente + "', '" + end + "')";
            }

        }
%>

<html>
    <head>
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="Expires" content="-1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Coletador: <%= coletador%> - Rota das Coletas de <%= dataAtual%></title>

        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />        
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>

        <script type="text/javascript">
            function objEnd(nome, endereco){
                this.nome = nome;
                this.endereco = endereco;
            }
            
            var lista = new Array(<%= listaEndereco%>);
            var iterator = 0;
            var markers = [];
            var geocoder;            
            var map;
            var side_bar = '';
            function load() {                
                geocoder = new google.maps.Geocoder();        
                var center = new google.maps.LatLng(0, 0);
                var myOptions = {
                    zoom: 16,
                    center: center,
                    mapTypeId: google.maps.MapTypeId.ROADMAP,
                    disableDefaultUI: true
                };                   
                map = new google.maps.Map(document.getElementById("map"), myOptions);                   
                       
                for(var i =0; i<lista.length; i++){
                    setTimeout(function() {  
                        addMarker();
                    }, i * 450);  
                }
                
            }
                                   
            function addMarker(){
                geocoder.geocode( { 'address': lista[iterator].endereco}, function(results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {    
                        var contentString = '<div id="content">'+
                                        '<h3>'+lista[iterator].nome+'</h3>'+
                                        '<p>'+lista[iterator].endereco+'</p>'+
                                        '</div>';
                        var marker = new google.maps.Marker({
                            map: map,
                            draggable: false,
                            position: results[0].geometry.location,
                            animation: google.maps.Animation.DROP,
                            title: lista[iterator].nome
                        });     
                        google.maps.event.addListener(marker, 'click', function() {
                            new google.maps.InfoWindow({content: contentString, disableAutoPan:true}).open(map,marker);
                        });
                        
                        side_bar += '<a id="mark_'+iterator+'">' + iterator + ' - '+lista[iterator].nome+'</a><br><br>';                        
                        document.getElementById('side_bar').innerHTML = side_bar;
                        
                        google.maps.event.addDomListener(document.getElementById('mark_'+iterator), 'click', function() {
                            new google.maps.InfoWindow({content: contentString, disableAutoPan:true}).open(map,marker);
                        });
                        
                        markers.push(marker);
                        map.setCenter(results[0].geometry.location);   
                        
                        iterator++;
                    } else {
                        alert("Geocode was not successful for the following reason: " + status);
                    }
                });             
            }




















            <%--
            var map = null;
            var geocoder = null;
            var gdir;
            var addressMarker;
            var side_bar_html = "";
            var gmarkers = [];
            var htmls = [];
            var Icon = new GIcon(G_DEFAULT_ICON);
            var bounds = new GLatLngBounds();
            Icon.image = "../imagensNew/marker_azul.png";

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
            <%
                ArrayList listaColetasRep = Coleta.Controle.contrColeta.consultaColetasPeloStatus(2, idColetador, vDataPesquisa, "dataHoraColeta", nomeBD);
                for (int j = 0; j < listaColetasRep.size(); j++) {
                    Coleta.Entidade.Coleta col = (Coleta.Entidade.Coleta) listaColetasRep.get(j);
                    int idCliente = col.getIdCliente();
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
                    var address = "<%= endereco%>";
                    if (geocoder) {
                        geocoder.getLatLng(
                        address,
                        function(point) {
                            if (!point) {
                                alert("Endereço não encontrado: <%= endereco%>");
                            } else {
                                map.setCenter(point, 16);
                                map.addOverlay(new GMarker(point));
                                map.addOverlay(createMarker(point,"<%= nomeCliente%><br><%= endereco + " , " + complemento%><br />",<%= j%>));
                                side_bar_html += '<a href="javascript:myclick(<%=j%>);" onmouseover="gmarkers[<%=j%>].setImage(\'../imagensNew/marker_vermelho.png\');" onmouseout="gmarkers[<%=j%>].setImage(\'../imagensNew/marker_azul.png\');">- <%= nomeCliente%></a><br><br>';
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
            <%}%>
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
                }--%>
        </script> 
    </head>
    <body onload="load()">
        <table align="center" width="100%">
            <tr bgcolor="#46A0D2">
                <th>Clientes</th>
                <th>Mapa</th>
            </tr>
            <tr bgcolor="#D2E6F0">
                <td valign="top" width="300"><div id="side_bar" style="width:100%; height:550px; overflow:auto;"></div></td>
                <td valign="top" width="600" align="center"><div id="map" style="width:100%; height:550px;"></div></td>
            </tr>
        </table>
    </body>
</html>
<%}%>
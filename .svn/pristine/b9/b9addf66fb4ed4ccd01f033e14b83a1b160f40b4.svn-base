
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
        <title>Portal Postal</title>

        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />        
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>

        <script type="text/javascript">
            function objEnd(nome, endereco) {
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
                $('#map').height($(document).innerHeight() - 40);

                geocoder = new google.maps.Geocoder();
                var center = new google.maps.LatLng(0, 0);
                var myOptions = {
                    zoom: 16,
                    center: center,
                    mapTypeId: google.maps.MapTypeId.ROADMAP,
                    disableDefaultUI: true
                };
                map = new google.maps.Map(document.getElementById("map"), myOptions);

                for (var i = 0; i < lista.length; i++) {
                    setTimeout(function () {
                        addMarker();
                    }, i * 450);
                }

            }

            function addMarker() {
                geocoder.geocode({'address': lista[iterator].endereco}, function (results, status) {
                    if (status == google.maps.GeocoderStatus.OK) {
                        var contentString = '<div id="content">' +
                                '<h3>' + lista[iterator].nome + '</h3>' +
                                '<p>' + lista[iterator].endereco + '</p>' +
                                '</div>';
                        var marker = new google.maps.Marker({
                            map: map,
                            draggable: false,
                            position: results[0].geometry.location,
                            animation: google.maps.Animation.DROP,
                            title: lista[iterator].nome
                        });
                        google.maps.event.addListener(marker, 'click', function () {
                            new google.maps.InfoWindow({content: contentString, disableAutoPan: true}).open(map, marker);
                        });

                        side_bar = '<li class="list-group-item" id="mark_' + iterator + '"><strong style="cursor:pointer">' + lista[iterator].nome + '</strong></li>';

                        $('#side_bar').append(side_bar);
                        //document.getElementById('side_bar').innerHTML = side_bar;

                        google.maps.event.addDomListener(document.getElementById('mark_' + iterator), 'click', function () {
                            new google.maps.InfoWindow({content: contentString, disableAutoPan: true}).open(map, marker);
                        });

                        markers.push(marker);
                        map.setCenter(results[0].geometry.location);

                        iterator++;
                    } else {
                        alert("Geoloacalizaçã não encontrada devido ao erro: " + status);
                    }

                });
            }

        </script> 

        <%@ include file="../includes/Css_js.jsp" %>
    </head>
    <body onload="load()">
        <div class="container-fluid">
            <div class="row">
                <div class="col-xs-12">
                    <div class="col-md-3 col-lg-2">   
                            <div>
                                <ul id="side_bar" class="list-group">
                                    <li class="list-group-item list-group-item-info"><strong>CLIENTES</strong></li>
                                </ul>
                            </div>
                        
                    </div>
                    <div class="col-md-9 col-lg-10">
                        <div class="box">
                            <div class="box-header">
                                <i class="fa fa-map-marker"></i>
                                <span>Coletador: <%= coletador%> - Mapa das Coletas de <%= dataAtual%></span>  
                                <div class="no-move"></div>
                            </div>
                            <div  id="map">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--
                <table align="center" width="100%">
                    <tr bgcolor="#46A0D2">
                        <th>Clientes</th>
                        <th>Mapa</th>
                    </tr>
                    <tr bgcolor="#D2E6F0">
                        <td valign="top" width="300">
                            <div id="side_bar" style="width:100%; height:550px; overflow:auto;"></div>
                        </td>
                        <td valign="top" width="600" align="center">
                            <div id="map" style="width:100%; height:550px;"></div>
                        </td> 
                    </tr>
                </table>
        -->
    </body>
</html>
<%}%>
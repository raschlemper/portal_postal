<%@page import="Entidade.Usuario"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="Entidade.empresas"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%
    response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0
    response.setDateHeader("Expires", 0); //prevent caching at the proxy server

    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else {

        //pega os parametros passados para a pagina
        String nomeBD = (String) session.getAttribute("nomeBD");
        String dataAtual = request.getParameter("dataPesquisa");
        String vIdColet = request.getParameter("idColetador");

        //cria uma data atual
        Date dataPesquisa = new Date();
        //declara os simple date format
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");

        //verifica se a data passada pelo parametro nao eh nula nem vazia
        if (dataAtual == null || dataAtual.equals("")) {
            dataAtual = sdf1.format(dataPesquisa);
        } else {
            dataPesquisa = df.parse(dataAtual);
        }

        empresas agf = Controle.contrEmpresa.consultaEmpresaCnpj(nomeBD);
        int idColetador = Integer.parseInt(vIdColet);
        String coletador = Coleta.Controle.contrColetador.consultaNomeColetadoresById(idColetador, nomeBD);

        String geoLoc = "";
        String endAgf = agf.getEndereco() + " - " + agf.getBairro() + " - " + agf.getCidade();

%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
        <meta charset="utf-8">
        <title>Mapa - <%=coletador%></title>
        <link rel="icon" href="${pageContext.request.contextPath}/imagensNew/favicon.ico" />


        <script type="text/javascript" src="../../plugins/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>
        <style>
            #right-panel {
                font-family: 'Roboto','sans-serif';
                line-height: 30px;
                padding-left: 10px;
                padding-right: 10px;
                margin-left: 10px ;
                margin-top : 10px ;

                position: absolute;
                width: 200px;
                height: 98%;
                background: black;
                opacity: .95;
                top: 0;
                right: 38px;
                overflow: auto;
                border-radius: 5px;

                border-width: 2px;
                width: 20%;
                float: left;
                text-align: left;

            }

            #right-panel select, #right-panel input {
                font-size: 15px;
            }

            #right-panel select {
                width: 100%;
            }

            #right-panel i {
                font-size: 12px;
            }
            html, body {
                height: 100%;
                width: 100%;
                margin:0;
            }
            #map {
                height: 100%;
                width: 100%;
            }

            #directions-panel {
                font-size: 11px;
                line-height: 15px;
                margin-top: 10px;
                background-color: #FFEE77;
                padding: 10px;                
                border-radius: 5px;
            }
            #erro {
                background-color: #FF7171;
                padding: 10px;
                color: white;                
                border-radius: 5px;
            }

            #overlayContent {
                color: white;
            }

            .btn {
                margin-top: 10px;
                background: #3498db;
                background-image: -webkit-linear-gradient(top, #3498db, #2980b9);
                background-image: -moz-linear-gradient(top, #3498db, #2980b9);
                background-image: -ms-linear-gradient(top, #3498db, #2980b9);
                background-image: -o-linear-gradient(top, #3498db, #2980b9);
                background-image: linear-gradient(to bottom, #3498db, #2980b9);
                -webkit-border-radius: 28;
                -moz-border-radius: 28;
                border-radius: 5px;
                font-family: Arial;
                color: #ffffff;
                font-size: 15px;
                padding: 10px 20px 10px 20px;
                text-decoration: none;
            }

            .btn:hover {
                background: #3cb0fd;
                background-image: -webkit-linear-gradient(top, #3cb0fd, #3498db);
                background-image: -moz-linear-gradient(top, #3cb0fd, #3498db);
                background-image: -ms-linear-gradient(top, #3cb0fd, #3498db);
                background-image: -o-linear-gradient(top, #3cb0fd, #3498db);
                background-image: linear-gradient(to bottom, #3cb0fd, #3498db);
                text-decoration: none;
            }

        </style>
    </head>
    <body>
        <div id="map"></div>
        <div id="right-panel">
            <div id="overlayContent">

                <b>ROTA</b> <br>
                <i>(Ctrl-Click para multipla selecão/deseleção)</i> <br>
                <select multiple id="waypoints" size='8' style="font-size: 12px;">
                    <option value="<%=agf.getLatitude()%>@<%=agf.getLongitude()%>@<%=agf.getFantasia()%>@<%=endAgf%>@48ffa0" id="inicio" selected><%=agf.getFantasia()%></option>
                    <%
                        //contador de itens na tabela da coleta fixa
                        int cont = Integer.parseInt(request.getParameter("contador"));
                        float totalRota = 0;
                        //for que percorre a quantidade de itens que contem na tabela da coleta fixa
                        for (int i = 0; i < cont; i++) {
                            String vIdCliente = request.getParameter("cliente" + i);
                            if (vIdCliente != null && !vIdCliente.equals("")) {
                                int idCliente = Integer.parseInt(vIdCliente);
                                int idTipo = Integer.parseInt(request.getParameter("select" + i));
                                int fixo = Integer.parseInt(request.getParameter("fixo" + i));
                                String hora = request.getParameter("hora" + i);

                                Entidade.Clientes cli = Controle.contrCliente.consultaClienteFaturamentoById(idCliente, nomeBD);

                                if (cli != null) {

                                    String nomeCliente = cli.getNomeFantasia();

                                    Float fatu = cli.getFat_mes();
                                    totalRota += fatu;
                                    String fat = Util.FormatarDecimal.formatarFloat(fatu);
                                    //Usar DEPOIS NO googleMarker
                                    String cidade = cli.getCidade().trim();
                                    String uf = cli.getUf().trim();
                                    String endereco = cli.getEndereco().trim();
                                    String end = endereco + " , " + cli.getNumero() + " - " + cidade + " - " + uf;
                                    // String complemento = cli.getComplemento().trim();

                                    String lat = cli.getLatitude() + "";
                                    String longi = cli.getLongitude() + "";
                                    geoLoc = lat + "@" + longi;

                                    String corMarker = "ffa348";
                                    if (fixo == 1) {
                                        corMarker = "48a4ff";
                                    }
                    %>                    
                    <option value="<%=geoLoc%>@<%=nomeCliente%>@<%=end%>@<%=corMarker%>" data-foo="<%=fatu%>" selected><%=nomeCliente%></option>
                    <%}
                            }
                        }%>
                    <option value="<%=agf.getLatitude()%>@<%=agf.getLongitude()%>@<%=agf.getFantasia()%>@<%=endAgf%>@48ffa0" id="fim" selected><%=agf.getFantasia()%></option>

                </select>
            </div>
            <div>
                <label style="color: white">Espera estimado : </label>
                <input type="number" id="espera" value="10" style="margin-top: 10px;" size="4" min="1" max="120"/>
                <span style="color: white"> minutos</span>   
            </div>

            <div>
                <input type="button" class="btn" onclick="initMap();"id="submit" value="VISUALIZAR ROTA">
            </div>
            <div>
                <label style="color: #FFEE77">Rentabilidade : R$ <%= Util.FormatarDecimal.formatarFloat(totalRota)%><span style="font-size: 9px;">&nbsp;&nbsp;(~ 2 últ. meses)</span></label>
            </div>

            <div id="directions-panel"></div>
            <br>
            <div id="erro">
                <b> Erro no cadastro de Localização !</b><br>
                <b> Corrija o endereço dos clientes abaixo:</b>

                <% //for que percorre a quantidade de itens que contem na tabela da coleta fixa
                    for (int j = 0; j < cont; j++) {
                        String vIdCliente2 = request.getParameter("cliente" + j);
                        if (vIdCliente2 != null && !vIdCliente2.equals("")) {
                            int idCliente = Integer.parseInt(vIdCliente2);
                            Entidade.Clientes cli = Controle.contrCliente.consultaClienteById(idCliente, nomeBD);
                            if (cli != null) {
                                String nomeCliente = cli.getNomeFantasia();
                                String lat = cli.getLatitude() + "";
                                String longi = cli.getLongitude() + "";
                                geoLoc = lat + "," + longi;
                                if (lat.equals("0.0")) {%>                    
                <br><a href="../Cadastros/cliente_cadastro_b.jsp?idCliente=<%=cli.getCodigo()%>" target="_blank"><%=nomeCliente%></a>
                <%}
                            }
                        }
                    }%>
            </div>
        </div>
        <script>

            var map = null;
            function initMap() {
                $('#erro').hide();
                var directionsService = new google.maps.DirectionsService;
                var directionsDisplay = new google.maps.DirectionsRenderer({suppressMarkers: true});
                map = new google.maps.Map(document.getElementById('map'), {
                    zoom: 6,
                    center: {lat: 0.00, lng: 0.00}
                });
                var geocoder = new google.maps.Geocoder();
                var location = "<%= agf.getEndereco()%> - <%= agf.getCidade()%>";
                        geocoder.geocode({'address': location}, function (results, status) {
                            if (status == google.maps.GeocoderStatus.OK) {
                                map.setCenter(results[0].geometry.location);
                            } else {
                                map.setCenter(<%=agf.getLatitude()%>,<%=agf.getLongitude()%>);
                            }
                        });
                        var trafficLayer = new google.maps.TrafficLayer();
                        trafficLayer.setMap(map);
                        Tour_startUp();
                        window.tour.loadMap(map, directionsDisplay);
                        window.tour.fitBounds(map);
                        window.tour.calcRoute(directionsService, directionsDisplay);
                    }



                    function Tour_startUp() {

                        var stops = [];
                        window.tour = null;
                        var selected = [];
                        var selectedAdress = [];
                        var markColors = [];
                        var fatMensal = [];


                        var checkboxArray = document.getElementById('waypoints');
                        for (var i = 0; i < checkboxArray.length; i++) {
                            if (checkboxArray.options[i].selected) {
                                console.log(checkboxArray[i].value);
                                var aux = (checkboxArray[i].value).split('@');
                                var lat = aux[0];
                                var lon = aux[1];
                                selected.push(aux[2]);
                                selectedAdress.push(aux[3]);
                                markColors.push(aux[4]);
                                fatMensal.push(checkboxArray[i].getAttribute('data-foo'));
                                stops.push({
                                    "Geometry": {
                                        "Latitude": lat,
                                        "Longitude": lon
                                    }

                                });
                            }
                        }


                        if (!window.tour)
                            window.tour = {
                                updateStops: function (newStops) {
                                    stops = newStops;
                                },
                                // map: google map object
                                // directionsDisplay: google directionsDisplay object (comes in empty)
                                loadMap: function (map, directionsDisplay) {
                                    var myOptions = {
                                        zoom: 13,
                                        mapTypeId: window.google.maps.MapTypeId.ROADMAP
                                    };
                                    map.setOptions(myOptions);
                                    directionsDisplay.setMap(map);
                                },
                                // desenha o tamanho do mapa
                                fitBounds: function (map) {
                                    var bounds = new window.google.maps.LatLngBounds();

                                    // extend bounds for each record
                                    jQuery.each(stops, function (key, val) {
                                        var myLatlng = new window.google.maps.LatLng(val.Geometry.Latitude, val.Geometry.Longitude);
                                        bounds.extend(myLatlng);
                                    });
                                    map.fitBounds(bounds);
                                },
                                calcRoute: function (directionsService, directionsDisplay) {
                                    var batches = [];
                                    var itemsPerBatch = 10; // google API max = 10 - 1 start, 1 stop, and 8 waypoints
                                    var itemsCounter = 0;
                                    var wayptsExist = stops.length > 0;

                                    while (wayptsExist) {
                                        var subBatch = [];
                                        var subitemsCounter = 0;

                                        for (var j = itemsCounter; j < stops.length; j++) {
                                            subitemsCounter++;
                                            subBatch.push({
                                                location: new window.google.maps.LatLng(stops[j].Geometry.Latitude, stops[j].Geometry.Longitude),
                                                stopover: true
                                            });
                                            if (subitemsCounter == itemsPerBatch)
                                                break;
                                        }

                                        itemsCounter += subitemsCounter;
                                        batches.push(subBatch);
                                        wayptsExist = itemsCounter < stops.length;
                                        // If it runs again there are still points. Minus 1 before continuing to 
                                        // start up with end of previous tour leg
                                        itemsCounter--;
                                    }

                                    // now we should have a 2 dimensional array with a list of a list of waypoints
                                    var combinedResults;
                                    var unsortedResults = [{}]; // to hold the counter and the results themselves as they come back, to later sort
                                    var directionsResultsReturned = 0;

                                    for (var k = 0; k < batches.length; k++) {
                                        var lastIndex = batches[k].length - 1;
                                        var start = batches[k][0].location;
                                        var end = batches[k][lastIndex].location;

                                        // trim first and last entry from array
                                        var waypts = [];
                                        waypts = batches[k];
                                        waypts.splice(0, 1);
                                        waypts.splice(waypts.length - 1, 1);

                                        var request = {
                                            origin: start,
                                            destination: end,
                                            waypoints: waypts,
                                            travelMode: window.google.maps.TravelMode.DRIVING
                                        };
                                        (function (ordem) {
                                            directionsService.route(request, function (result, status) {
                                                if (status == window.google.maps.DirectionsStatus.OK) {

                                                    var unsortedResult = {order: ordem, result: result};
                                                    unsortedResults.push(unsortedResult);

                                                    directionsResultsReturned++;


                                                    if (directionsResultsReturned == batches.length) // we've received all the results. put to map
                                                    {
                                                        // sort the returned values into their correct order
                                                        unsortedResults.sort(function (a, b) {
                                                            return parseFloat(a.order) - parseFloat(b.order);
                                                        });
                                                        var count = 0;
                                                        for (var key in unsortedResults) {
                                                            if (unsortedResults[key].result != null) {
                                                                if (unsortedResults.hasOwnProperty(key)) {
                                                                    if (count == 0) // first results. new up the combinedResults object
                                                                        combinedResults = unsortedResults[key].result;
                                                                    else {
                                                                        // only building up legs, overview_path, and bounds in my consolidated object. This is not a complete 
                                                                        // directionResults object, but enough to draw a path on the map, which is all I need
                                                                        combinedResults.routes[0].legs = combinedResults.routes[0].legs.concat(unsortedResults[key].result.routes[0].legs);
                                                                        combinedResults.routes[0].overview_path = combinedResults.routes[0].overview_path.concat(unsortedResults[key].result.routes[0].overview_path);

                                                                        combinedResults.routes[0].bounds = combinedResults.routes[0].bounds.extend(unsortedResults[key].result.routes[0].bounds.getNorthEast());
                                                                        combinedResults.routes[0].bounds = combinedResults.routes[0].bounds.extend(unsortedResults[key].result.routes[0].bounds.getSouthWest());
                                                                    }

                                                                    count++;
                                                                }
                                                            }
                                                        }

                                                        directionsDisplay.setDirections(combinedResults);

                                                        var route = combinedResults.routes[0];
                                                        var summaryPanel = document.getElementById('directions-panel');
                                                        summaryPanel.innerHTML = '';
                                                        // For each route, display summary information.
                                                        var total = 0;
                                                        var totalTime = 0;
                                                        var totalFat = 0;
                                                        var espera = parseInt($('#espera').val());
                                                        for (var i = 0; i < route.legs.length; i++) {
                                                            var routeSegment = i + 1;
                                                            summaryPanel.innerHTML += '<b>' + selected[i] + ' até ' + selected[i ma +1] + ':</b><br>';
                                                            //summaryPanel.innerHTML += route.legs[i].start_address + ' to ';
                                                            // summaryPanel.innerHTML += route.legs[i].end_address + '<br>';
                                                            summaryPanel.innerHTML += route.legs[i].distance.text + ' - ' + route.legs[i].duration.text + '<br>';
                                                            total += route.legs[i].distance.value;
                                                            totalTime += (route.legs[i].duration.value) + espera * 60;
                                                            console.log(" F >"+fatMensal[i])
                                                            if(fatMensal[i] !== null){
                                                                totalFat += parseFloat(fatMensal[i]);
                                                            }
                                                            
                                                            // console.log('LATITUDE : ' + route.legs[i].start_location.lat());

                                                            var myLatlng = new google.maps.LatLng(route.legs[i].start_location.lat(), route.legs[i].start_location.lng());
                                                            criarmarkers(map, myLatlng, selectedAdress[i], selected[i], markColors[i], fatMensal[i]);

                                                        }


                                                        var hours = parseInt(totalTime / 3600) % 24;
                                                        var minutes = parseInt(totalTime / 60) % 60;
                                                        var seconds = totalTime % 60;

                                                        var result = (hours < 10 ? "0" + hours : hours) + "h " + (minutes < 10 ? "0" + minutes : minutes) + "min";
                                                        var vlr = numeroParaMoeda(totalFat,2,',','.');
                                                        summaryPanel.innerHTML += '<br><hr><span style="color: red; font-size: 14px;"><b>DISTÂNCIA TOTAL : ' + (total / 1000).toFixed(1) + ' km</b></span>';
                                                        summaryPanel.innerHTML += '<br><hr><span style="color: red;  font-size: 14px;"><b>TEMPO ESTIMADO TOTAL : ' + result;// + ' / (' + totalTime + ' min)</b></span>';
                                                        summaryPanel.innerHTML += '<br><hr><span style="color: red;  font-size: 14px;">'+
                                                                                    '<b>FAT. (MÉDIA ÚLT. 2 MÊSES) : R$ ' + vlr;

                                                    }

                                                } else {
                                                    $('#erro').show();
                                                    //window.alert('Aconteceu um erro ao desenhar o mapa ' + status);
                                                }
                                            });
                                        })(k);
                                    }
                                }
                            };
                    }

                    function criarmarkers(map, myLatlng, adress, title, pinColor, fat) {

                        var pinImage = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_letter&chld=%E2%80%A2|" + pinColor,
                                new google.maps.Size(21, 34),
                                new google.maps.Point(0, 0),
                                new google.maps.Point(10, 34));
                        var pinShadow = new google.maps.MarkerImage("http://chart.apis.google.com/chart?chst=d_map_pin_shadow",
                                new google.maps.Size(40, 37),
                                new google.maps.Point(0, 0),
                                new google.maps.Point(12, 35));
                        var contentString = '<div id="content">' +
                                '<div id="siteNotice">' +
                                '</div>' +
                                '<h1 id="firstHeading" class="firstHeading">' + title + '</h1>' +
                                'Faturamento médio últimos 2 meses<h1 id="firstHeading" class="firstHeading">R$' + fat + '</h1>' +
                                '<div id="bodyContent">' +
                                '<p><b>Endreço :</b><br>' + adress + '</p>' +
                                '</div>' +
                                '</div>';

                        var infowindow = new google.maps.InfoWindow({
                            content: contentString
                        });
                        var marker = new google.maps.Marker({
                            position: myLatlng,
                            map: map,
                            title: title,
                            icon: pinImage,
                            shadow: pinShadow
                        });
                        marker.addListener('click', function () {
                            infowindow.open(map, marker);
                        });
                    }
        </script>
        <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCBS02tZxaCu7zzJdlzWQuhqFrh6YvuKzk&callback=initMap">
        </script>
    </body>
</html>
<%}%>
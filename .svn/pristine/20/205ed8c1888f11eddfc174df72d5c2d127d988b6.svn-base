
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
        <meta name="keywords" content="api, google maps, exemplos, guia ruas, endereços, mapas, traçar rota, "/>
        <meta name="description" content="Exemplos simples para quem quer aprender utilizar a api Google Maps V3."/>
        <meta name="robots"  content="all"/>
        <link rel="stylesheet" type="text/css" href="css/maps.css" />
        <title>Exemplos Rota com varios pontos de parada - Google Maps API v3</title>
        <style>

            label.inputStart,label.inputEnd,.revRoute,.inputAdd{ 
                background-image:url(../../imagensNew/cancel.png); background-repeat:no-repeat; 
            }
            #mapa { width:940px; height: 500px; }
            .form{padding:10px 0 10px 10px;	}  
            input.text {font-size:12px;margin-bottom:5px;outline:medium none;padding:5px;text-indent:5px;width:300px;}  
            .revRoute{margin:18px 5px 0 0;width:18px;height:18px;float: left;border:0;background-position:0 -32px; cursor :pointer ;}
            label{float:left;display:block;width:16px;height:16px; margin:5px 5px 0 0;}
            .inputStart{background-position:0 -50px;}
            .inputEnd{background-position:0 -50px;}
            .inputAdd{background-position:0 -50px;}
            .left{float:left;} 
            .addInput{margin-left:20px;}
            .close{ cursor:pointer;width:22px; height:22px; background:transparent url(../../imagensNew/cancel.png) no-repeat;  border:0; }
        </style>
    </head>
    <body onload="initialize()"> 
        <div class="container_12">
            <div class="grid_12 bg"><h1>Traçar Rota com pontos de parada</h1>
                <div class="form">
                    <div class="left">
                        <form onsubmit="calcRoute();
                        return false;" id="myForm" >
                            <label for="inputStart" class="inputStart" ></label> 
                            <input type="text" class="text" value="Av Rio Branco, 772, Florianópolis, SC" id="inputStart"  />
                            <br />
                            <div id="moreInput"></div>
                            <label for="inputEnd" class="inputEnd"></label> <input type="text" class="text" value="Av Rio Branco, 772, Florianópolis, SC" id="inputEnd" />
                            <input type="submit" value="Rota" />
                            <br />
                            <input type="button" value="adicionar parada" class="addInput" onclick="addInput();">  
                        </form>  
                    </div>
                </div>
                <br /><br />
                <div id="mapa" class="mapa"></div>
                <div id="directionsPanel"></div> 

                <hr />
                <h2>Google Directions API</h2>
                <a href="http://code.google.com/intl/sv-SE/apis/maps/documentation/directions/" target="_blank" />http://code.google.com/intl/sv-SE/apis/maps/documentation/directions/</a>
                <hr />
                
                <br /><br />
                <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
                <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
                <script type="text/javascript">
                                            var directionDisplay;
                                            var directionsService = new google.maps.DirectionsService();
                                            var route = false;
                                            var map;
                                            var marker;
                                            var geocoder;
                                            function initialize() {
                                                directionsDisplay = new google.maps.DirectionsRenderer();
                                                geocoder = new google.maps.Geocoder();
                                                var myLatlng = new google.maps.LatLng(-27.591845,-48.5505158);
                                                var myOptions = {
                                                    zoom: 17,
                                                    center: myLatlng,
                                                    mapTypeControl: true,
                                                    mapTypeId: google.maps.MapTypeId.ROADMAP
                                                }
                                                map = new google.maps.Map(document.getElementById("mapa"), myOptions);
                                                directionsDisplay.setMap(map);
                                                directionsDisplay.setPanel(document.getElementById("directionsPanel"));
                                            }
                                            function calcRoute() {
                                                if (marker)
                                                    marker.setMap(null);
                                                route = true;
                                                var start = document.getElementById("inputStart").value;
                                                var end = document.getElementById("inputEnd").value;

                                                var waypts = [];
                                                $('.waypoints').each(function () {
                                                    waypts.push({
                                                        location: $(this).val(),
                                                        stopover: true});
                                                }
                                                );

                                                var request = {
                                                    origin: start,
                                                    destination: end,
                                                    waypoints: waypts,
                                                    optimizeWaypoints: false,
                                                    travelMode: google.maps.DirectionsTravelMode.DRIVING
                                                };
                                                directionsService.route(request, function (response, status) {
                                                    if (status == google.maps.DirectionsStatus.OK) {
                                                        directionsDisplay.setDirections(response);
                                                    } else {
                                                        alert("Erro: " + status);
                                                    }
                                                });
                                            }

                                            function revRoute() {
                                                var divStart = document.getElementById("inputStart");
                                                var divEnd = document.getElementById("inputEnd");
                                                var start = divStart.value;
                                                var end = divEnd.value;
                                                divStart.value = end;
                                                divEnd.value = start
                                                if (route == true) {
                                                    calcRoute();
                                                }
                                            }

                                            /* Inputs */
                                            var numberInput = 0;
                                            function addInput() {
                                                $("#moreInput").append('<div id="div' + numberInput + '"> <label for="inputAdd' + numberInput + '" class="inputAdd" ></label> <input type="text" class="text waypoints" name="inputAdd' + numberInput + '" id="inputAdd' + numberInput + '" value="" /><input type="button" onclick="javascript:removeInput(\'div' + numberInput + '\');" class="close" /> <br />');
                                                numberInput++;
                                            }
                                            function removeInput(removeInput) {
                                                $("#" + removeInput).remove();
                                            }
                </script>
                <br class="clear" />
                
            </div>
        </div>
        <script type="text/javascript">  var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-15168843-1']);
    _gaq.push(['_setDomainName', '.feopt.com.br']);
    _gaq.push(['_trackPageview']);
    (function () {
        var ga = document.createElement('script');
        ga.type = 'text/javascript';
        ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(ga, s);
    })();</script>
    </body>
</html>

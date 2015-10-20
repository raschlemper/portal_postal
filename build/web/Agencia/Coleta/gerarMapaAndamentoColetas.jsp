<%--
    Document   : imprimirRota
    Created on : 29/05/2009, 16:16:33
    Author     : SCC4
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>

<%
            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD == null) {
                response.sendRedirect("../index.jsp?msgLog=3");
            } else {

//pega os parametros passados para a pagina
                String dataAtual = request.getParameter("dataPesquisa");
//cria uma data atual
                Date dataPesquisa = new Date();
//declara os simple date format
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
//verifica se a data passada pelo parametro nao eh nula nem vazia
                if (dataAtual == null || dataAtual.equals("")) {
                    dataAtual = sdf1.format(dataPesquisa);
                } else {
                    dataPesquisa = df.parse(dataAtual);
                }

                String vDataPesquisa = sdf3.format(dataPesquisa);
                //id do coletador
                int idColetador = Integer.parseInt(request.getParameter("idColetador"));
                String coletador = Coleta.Controle.contrColetador.consultaNomeColetadoresById(idColetador, nomeBD);

                ArrayList<Integer> listaIdColetadores = null;
                int cont = 0;
                //verifica se o idColetador passado pelo parametro nao eh nulo nem 0
                if (idColetador == 0) {
                    listaIdColetadores = Coleta.Controle.contrColetador.consultaIdColetadores(idColetador, nomeBD);
                } else {
                    listaIdColetadores = Coleta.Controle.contrColetador.consultaIdColetadores(idColetador, nomeBD);
                }
%>

<html>
    <head>
        <style>
            a{
                font-family:Verdana, Geneva, sans-serif; font-size:12px; text-decoration:none; color:blue;
            }
        </style>
        <%
                        response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
                        response.setHeader("Pragma", "no-cache"); //HTTP 1.0
                        response.setDateHeader("Expires", 0); //prevent caching at the proxy server
%>
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="Expires" content="-1">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Coletador: <%= coletador%> - Rota das Coletas de <%= dataAtual%></title>
        <script src="http://maps.google.com/maps?file=api&amp;v=2.x&amp;key=ABQIAAAAlJVKHaE-vcwZVqBrPdeTCRQtpG-lJVXecy3lwOZXVoU2jM-mXRT0zkCraFwHz9ShlAOJRH2d-1jy7w" type="text/javascript"></script>
        <script type="text/javascript">

            var map = null;
            var geocoder = null;
            var gdir;
            var addressMarker;
            var side_bar_html = "";
            var gmarkers = [];
            var htmls = [];
            var bounds = new GLatLngBounds();
            var Icon = new GIcon(G_DEFAULT_ICON);
            Icon.image = "../imagensNew/marker_verde.png";
            var Icon2 = new GIcon(G_DEFAULT_ICON);
            Icon2.image = "../imagensNew/marker_azul.png";

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
                        //for de coletadores
                        for (int i = 0; i < listaIdColetadores.size(); i++) {
                            int idCol = listaIdColetadores.get(i);
                            String nomeColetador = Coleta.Controle.contrColetador.consultaNomeColetadoresById(idCol, nomeBD);
                            ArrayList listaColetas = Coleta.Controle.contrColeta.consultaTodasColetasDoColetador(idCol, vDataPesquisa, "dataHoraColeta", nomeBD);
                            //for de coletas do coletador
                            for (int j = 0; j < listaColetas.size(); j++) {
                                Coleta.Entidade.Coleta col = (Coleta.Entidade.Coleta) listaColetas.get(j);
                                int idCliente = col.getIdCliente();
                                int status = col.getStatus();
                                Entidade.Clientes cli = Controle.contrCliente.consultaClienteById(idCliente, nomeBD);
                                String nomeCliente = cli.getNome().trim();
                                String cidade = cli.getCidade().trim();
                                String uf = cli.getUf().trim();
                                String endereco = cli.getEndereco().trim();
                                String complemento = cli.getComplemento().trim();
                                String aux[] = endereco.split(",");
                                String rua = endereco, numero = "";
                                if (aux.length > 1) {
                                    rua = aux[0];
                                    numero = aux[1];
                                }
                                endereco = numero + " " + rua + " , " + cidade + " - " + uf;

                                String corMarker = "", horaDaColeta = "";
                                if (status == 2) {
                                    horaDaColeta = "Ainda Não Coletado!";
                                    corMarker = "../imagensNew/marker_azul.png";
                                } else if (status == 3) {
                                    String dataHoraBaixa = sdf2.format(col.getDataHoraBaixa());
                                    horaDaColeta = "Data e Hora da Baixa: " + dataHoraBaixa;
                                    corMarker = "../imagensNew/marker_verde.png";
                                }
            %>
                        var address = "<%= endereco%>";
                        if (geocoder) {
                            geocoder.getLatLng(
                            address,
                            function(point) {
                                if (!point) {
                                    alert("Endereço não encontrado: \"<%= endereco%>\"");
                                } else {
                                    map.setCenter(point, 16);
                                    map.addOverlay(new GMarker(point));
                                    map.addOverlay(createMarker(point,"<%= nomeCliente + "<br>" + endereco + " " + complemento + "<br>Coletador " + nomeColetador + "<br>" + horaDaColeta%>",<%= cont%>,<%= status%>));
            <%if (status == 3) {%>
                                                        side_bar_html += '<a style="color:green;" href="javascript:myclick(<%=cont%>);" onmouseover="gmarkers[<%=cont%>].setImage(\'../imagensNew/marker_vermelho.png\');" onmouseout="gmarkers[<%=cont%>].setImage(\'<%=corMarker%>\');">- <%= nomeCliente%></a><br><br>';
            <%} else {%>
                                                        side_bar_html += '<a href="javascript:myclick(<%=cont%>);" onmouseover="gmarkers[<%=cont%>].setImage(\'../imagensNew/marker_vermelho.png\');" onmouseout="gmarkers[<%=cont%>].setImage(\'<%=corMarker%>\');">- <%= nomeCliente%></a><br><br>';
            <%}%>
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
            <%cont++;
                                }
                            }%>
                                }
                                function createMarker(point,html,contador,status) {
                                    status = parseInt(status);
                                    if(status==3){
                                        var marker = new GMarker(point,Icon);
                                    }else{
                                        var marker = new GMarker(point,Icon2);
                                    }

                                    GEvent.addListener(marker, "click", function() {
                                        marker.openInfoWindowHtml(html);
                                    });
                                    // Switch icon on marker mouseover and mouseout
                                    GEvent.addListener(marker, "mouseover", function() {
                                        marker.setImage("../imagensNew/marker_vermelho.png");
                                    });
                                    if(status==3){
                                        GEvent.addListener(marker, "mouseout", function() {
                                            marker.setImage("../imagensNew/marker_verde.png");
                                        });
                                    }else{
                                        GEvent.addListener(marker, "mouseout", function() {
                                            marker.setImage("../imagensNew/marker_azul.png");
                                        });
                                    }

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
        <table align="center" width="100%">
            <tr style="color:white;" bgcolor="#46A0D2">
                <td colspan="2">
                    <table align="right" border="0" cellpadding="5" cellspacing="0">
                        <tr>
                            <th valign="middle"><img src="../imagensNew/marker_verde.png"></th>
                            <th valign="middle">= Coletas Coletadas</th>
                            <th valign="middle"><img src="../imagensNew/marker_azul.png"></th>
                            <th valign="middle">= Coletas Aguardando</th>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr bgcolor="#46A0D2">
                <th>Clientes</th>
                <th>Mapa</th>
            </tr>
            <tr bgcolor="#D2E6F0">
                <td valign="top" width="300"><div id="side_bar" style="width:100%; height:550px; overflow:auto;"></div></td>
                <td valign="top" width="600" align="center"><div id="map_canvas" style="width:100%; height:550px;"></div></td>
            </tr>
        </table>
    </body>
</html>
<%}%>
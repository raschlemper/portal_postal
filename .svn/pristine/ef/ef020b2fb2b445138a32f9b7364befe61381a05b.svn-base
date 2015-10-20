<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");

        //pega os parametros passados para a pagina
        String dataAtual = request.getParameter("dataPesquisa");
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
        //id do coletador
        int idColetador = Integer.parseInt(request.getParameter("idColetador"));
        String coletador = Coleta.Controle.contrColetador.consultaNomeColetadoresById(idColetador, nomeBD);
%>
<html>
    <head>
        <title> Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>

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
            Icon.image = "../../imagensNew/marker_verde.png";
            var Icon2 = new GIcon(G_DEFAULT_ICON);
            Icon2.image = "../../imagensNew/marker_azul.png";

            function initialize() {
                if (GBrowserIsCompatible()) {
                    map = new GMap2(document.getElementById("map_canvas"));
                    map.setCenter(new GLatLng(-27.595859, -48.547039), 15);
                    geocoder = new GClientGeocoder();

                    //map.setCenter(new GLatLng(0,0,0));
                    map.addControl(new GLargeMapControl());
                    map.addControl(new GMapTypeControl());
                    map.addControl(new GOverviewMapControl());
                }
            <%
                //contador de itens na tabela da coleta fixa
                int cont = Integer.parseInt(request.getParameter("contador"));

                //for que percorre a quantidade de itens que contem na tabela da coleta fixa
                for (int i = 0; i < cont; i++) {
                    String vIdCliente = request.getParameter("cliente" + i);
                    if (vIdCliente != null && !vIdCliente.equals("")) {
                        int idCliente = Integer.parseInt(vIdCliente);
                        int idTipo = Integer.parseInt(request.getParameter("select" + i));
                        int fixo = Integer.parseInt(request.getParameter("fixo" + i));
                        String hora = request.getParameter("hora" + i);
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
                        String corMarker = "../../imagensNew/marker_azul.png";
                        if (fixo == 1) {
                            corMarker = "../../imagensNew/marker_verde.png";
                        }%>
                var address = "<%= endereco%>";
                if (geocoder) {
                    geocoder.getLatLng(
                            address,
                            function(point) {
                                if (!point) {
                                    alert("Endereço não Encontrado:\n\n\"<%= endereco%>\"");
                                } else {
                                    map.setCenter(point, 16);
                                    map.addOverlay(new GMarker(point));
                                    map.addOverlay(createMarker(point, "<%= nomeCliente%><br><%= endereco + " " + complemento%><br />",<%= i%>,<%= fixo%>));
                                    side_bar_html += '<li><a href="javascript:myclick(<%=i%>);" onmouseover="gmarkers[<%=i%>].setImage(\'../../imagensNew/marker_vermelho.png\');" onmouseout="gmarkers[<%=i%>].setImage(\'<%=corMarker%>\');"><%= nomeCliente%></a></li>';
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
            <%}
                }%>
            }
            function createMarker(point, html, contador, fixo) {
                if (fixo === 1) {
                    var marker = new GMarker(point, Icon);
                } else {
                    var marker = new GMarker(point, Icon2);
                }

                GEvent.addListener(marker, "click", function() {
                    marker.openInfoWindowHtml(html);
                });
                // Switch icon on marker mouseover and mouseout
                GEvent.addListener(marker, "mouseover", function() {
                    marker.setImage("../../imagensNew/marker_vermelho.png");
                });
                if (fixo === 1) {
                    GEvent.addListener(marker, "mouseout", function() {
                        marker.setImage("../../imagensNew/marker_verde.png");
                    });
                } else {
                    GEvent.addListener(marker, "mouseout", function() {
                        marker.setImage("../../imagensNew/marker_azul.png");
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
    <body onLoad="initialize()" onUnload="GUnload();showAddress();">
                <ul class="list-unstyled">
                    <li class="list-group-item list-group-heading">   
                        <div class="row">
                        <table align="left" border="0" cellpadding="5" cellspacing="0">
                            <tr>
                                <th valign="middle">&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../imagensNew/marker_verde.png"></th>
                                <th valign="middle">&nbsp;&nbsp;= Coletas Fixas</th>
                                <th valign="middle">&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../imagensNew/marker_azul.png"></th>
                                <th valign="middle">&nbsp;&nbsp;= Coletas Eventuais</th>
                            </tr>
                        </table>
                            </div>
                    </li> 
                    <li class="list-group-item">
                        <div class="row form-horizontal">
                            <div class="col-sm-12 col-md-4 col-lg-4"><ol id="side_bar" ></ol></div>
                            <div class="col-sm-12 col-md-8 col-lg-8">
                                <div id="map_canvas" style="width:100%; height:550px;"></div>
                            </div>
                        </div>
                    </li> 
                </ul>

    </body>
</html>
<%}%>
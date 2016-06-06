<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Coleta.Entidade.Coleta"%>
<%@page import="Coleta.Controle.contrColeta"%>
<%@page import="Coleta.Entidade.Coletador"%>
<%@page import="Coleta.Controle.contrColetador"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {
%>
<script>
    <%
        SimpleDateFormat hr1 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

        int situacao = 2;
        if (request.getParameter("situacao") != null) {
            situacao = Integer.parseInt(request.getParameter("situacao"));
        }

        String dataPesquisaBD = sdf2.format(new Date());

        ArrayList<Coletador> listaColetadores = contrColetador.consultaTodosColetadoresColeta(dataPesquisaBD, situacao, nomeBD);

        String qtdSolicitadas = contrColeta.consultaQtdColetasSolicitadas(nomeBD);

        ArrayList<Coleta> listaUltimas = contrColeta.consultaUltimasColetas(nomeBD, dataPesquisaBD);

        String titulos = "";
        String table = "";

        for (Coletador cltdr : listaColetadores) {
            String nome = cltdr.getNome(); //nome
            int idColtdr = cltdr.getIdColetador();
            ArrayList<Coleta> listColAberto = contrColeta.consultaTodasColetasDoColetador(idColtdr, dataPesquisaBD, "status", nomeBD);
            ArrayList<Coleta> listColFinalizadas = contrColeta.consultaTodasFinalizadas(idColtdr, dataPesquisaBD, "status", nomeBD);

            int totalColetas = listColAberto.size(); //totalColetas
            int coletadas = listColFinalizadas.size(); //coletadas
            table += " <tr><td>" + nome + "</td><td><strong>" + coletadas + "/" + totalColetas + "</strong></td><td><b style='color: red'>" + (totalColetas - coletadas) + "</b></td> </tr>";

            titulos += ",{'coletadores': '" + nome + "', 'color2': '#FF0F00', 'totalColetas': " + totalColetas + ", 'color': '#31C349', 'coletadas': " + coletadas + "}";
        }
        titulos = titulos.substring(1);
        
        String horarios ="";
        
        
        Map solicitada = contrColeta.pesquisaColetaSolicitada(nomeBD,dataPesquisaBD);
        Map coletada = contrColeta.pesquisaColetaEfetuada(nomeBD,dataPesquisaBD);
        
        horarios = "{'hora': '13~14h', 'solicitada': "+contrColeta.vMap(solicitada, 13) +", 'efetuada': "+contrColeta.vMap(coletada, 13)+"}"+
                ",{'hora': '14~15h', 'solicitada': "+contrColeta.vMap(solicitada, 14) +", 'efetuada': "+contrColeta.vMap(coletada,14)+"}"+
                ",{'hora': '15~16h', 'solicitada': "+contrColeta.vMap(solicitada, 15) +", 'efetuada': "+contrColeta.vMap(coletada,15)+"}"+
                ",{'hora': '16~17h', 'solicitada': "+contrColeta.vMap(solicitada, 16) +", 'efetuada': "+contrColeta.vMap(coletada,16)+"}"+
                ",{'hora': '17~18h', 'solicitada': "+contrColeta.vMap(solicitada, 17) +", 'efetuada': "+contrColeta.vMap(coletada,17)+"}";
        
    %>
      
        
    var colt = [<%=titulos%>];
    var chart = AmCharts.makeChart("chartdiv", {
        "theme": "none",
        "type": "serial",
        "dataProvider": colt,
        "valueAxes": [{
                "position": "left",
                "title": "Quantidade"
            }],
        "startDuration": 1,
        "graphs": [{
                "balloonText": "Coletas desta data: <b>[[value]]</b>",
                "fillColorsField": "color2",
                "fillAlphas": 0.9,
                "lineAlpha": 0.2,
                "type": "column",
                "valueField": "totalColetas"
            }, {
                "balloonText": "Coletas realizadas [[category]]: <b>[[value]]</b>",
                "fillColorsField": "color",
                "fillAlphas": 0.9,
                "lineAlpha": 0.2,
                "type": "column",
                "clustered": false,
                "columnWidth": 0.7,
                "valueField": "coletadas"
            }],
        "categoryField": "coletadores",
        "categoryAxis": {
            "gridPosition": "start"
        }

    });

var colt2 = [<%=horarios%>];
    var chart2 = AmCharts.makeChart("chartdivH", {
        "type": "serial",
        "theme": "light",
        "marginRight": 30,
        "legend": {
            "equalWidths": false,
            "periodValueText": "total: [[value.sum]]",
            "position": "top",
            "valueAlign": "left",
            "valueWidth": 100
        },
        "dataProvider": colt2,
        "valueAxes": [{                
                "gridAlpha": 0.07,
                "position": "left",
                "title": "Coletas"
            }],
        "graphs": [{
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "bulletColor": "#FFFFFF",
                "bulletSize": 5,
                "hideBulletsCount": 50,
                "lineThickness": 2,
                "title": "Efetuadas",
                "useLineColorForBulletBorder": true,
                "valueField": "efetuada",
                "balloonText": "<div style='margin:5px;'><span style='font-size:13px;'>Efetuadas - [[category]]</span><br><b>[[value]]</b> coletas</div>"

            }, {
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "bulletColor": "#FFFFFF",
                "bulletSize": 5,
                "hideBulletsCount": 50,
                "lineThickness": 2,
                "title": "Solicitadas",
                "useLineColorForBulletBorder": true,
                "valueField": "solicitada",
                "balloonText": "<div style='margin:5px;'><span style='font-size:13px;'>Solicitadas - [[category]]</span><br><b>[[value]]</b> coletas</div>"

            }],
        "plotAreaBorderAlpha": 0,
        "marginTop": 10,
        "marginLeft": 0,
        "marginBottom": 0,
        "chartScrollbar": {},
        "chartCursor": {
            "cursorAlpha": 0
        },
        "categoryField": "hora",
        "categoryAxis": {
            "startOnAxis": true,
            "axisColor": "#DADADA",
            "gridAlpha": 0.07,
            "title": "Hora"
        },
        "export": {
            "enabled": true
        }
    });


</script>
<%
    if (!qtdSolicitadas.equals("0")) {

%>
<div class="row">
    <div class="col-lg-12">
        <div class="alert alert-danger text-center">
            <a href="#" class="close" data-dismiss="alert">&times;</a>                                   
            <span class="fa fa-warning"></span>&nbsp;                             
            <strong> EXISTE(M) <b style="color: #000000"><%= qtdSolicitadas%></b> NOVA(S) COLETA(S) SOLICITADA(S) PELA WEB!</strong>

        </div>
    </div>
</div>
<%}%>
<div class="row">
    <div class="col-md-12">
        <div class="col-md-7">
            <div id="chartdiv" style="height: 300px; width: 100%;"></div> 
        </div>
        <div class="col-md-5">
            <ul class="list-unstyled" style="overflow-y: scroll; height: 40vH;">
                <li class="list-group-item list-group-condensed list-group-heading">
                    <labeL> Últimas coletas realizadas</labeL>
                </li>
                <%
                    for (Coleta c : listaUltimas) {
                        String horaBaixa = "";
                        if(c.getDataHoraBaixa()!=null){
                        horaBaixa = hr1.format(c.getDataHoraBaixa());
                        }
                %>
                <li class="list-group-item list-group-condensed">
                    <%=contrColetador.consultaNomeColetadoresById(c.getIdColetador(), nomeBD)%>&nbsp;-&nbsp;<b><%=contrCliente.consultaClienteById(c.getIdCliente(), nomeBD).getNomeFantasia()%></b>&nbsp;às&nbsp;<%= horaBaixa %>&nbsp;-&nbsp;<span style="font-weight: bold;<% if (c.getStatus() == 5) {%>  color: #008000; <%} else {%>  color: #D8000C <%}%>"> <%=contrColeta.consultaStatusById(c.getStatus(), nomeBD)%></span>
                </li>
                <%}%>
            </ul>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="well-sm"></div>
    </div>
    <div class="col-md-12">
        <div class="col-md-4">
            <div class="dataTable_wrapper text-center padding-15">
                <table class="table table-striped table-bordered table-hover table-condensed">
                    <thead>
                        <tr>
                            <th><label>Coletador</label></th>
                            <th><label>Coletas Realizadas</label></th>
                            <th><label>Falta Coletar</label></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%=table%>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="col-md-8">
            <div id="chartdivH" style="width: 100%; height:300px;"></div>
        </div>

    </div>                                            
</div>

<%}%>
<%@page import="Util.FormatarData"%>
<%@page import="Controle.contrEmpresa"%>
<%@page import="Entidade.Contato"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.ArrayList, java.util.Date, java.text.SimpleDateFormat, java.sql.*, java.util.Calendar, java.util.Locale" %>

<%
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat hrf1 = new SimpleDateFormat("HH:mm");
    SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
    SimpleDateFormat sdf4 = new SimpleDateFormat("MM");
    SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy");
    SimpleDateFormat sdf6 = new SimpleDateFormat("HH");
    SimpleDateFormat sdf7 = new SimpleDateFormat("mm");

    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {

        String numCliente = String.valueOf(session.getAttribute("idCliente"));
        int idCliente = Integer.parseInt(numCliente);
        int idAgf = (Integer) session.getAttribute("idEmpresa");

        String dia = sdf3.format(new Date());
        String mes = sdf4.format(new Date());
        String ano = sdf5.format(new Date());
        String hora = sdf6.format(new Date());
        Date dataAtual = new Date();
        if (contrEmpresa.consultaoSemHrVerao(idAgf)) {
            hora = sdf6.format(FormatarData.somarHorasNaData(new Date(), -1));
            dataAtual = FormatarData.somarHorasNaData(new Date(), -1);
        }
        String minuto = sdf7.format(new Date());

        Coleta.Entidade.HoraColeta hc = Coleta.Controle.contrHoraColeta.consultaHoraColetaById(nomeBD);
        Time hrFimAcesso = null, hrIniColeta = null, hrFimColeta = null;
        int antecedencia = 0;
        if (hc != null) {
            hrFimAcesso = hc.getHoraFimAcesso();
            hrIniColeta = hc.getHoraIniColeta();
            hrFimColeta = hc.getHoraFimColeta();
            antecedencia = hc.getMinAntecedencia();
        }

        Date dataAtualComAntecedencia = FormatarData.somarMinutosNaData(new Date(), antecedencia);

        int coleEve = Coleta.Controle.contrColetaFixa.consultaColetadorEventualDoCliente(idCliente, nomeBD);
        int coleEvTipo = Coleta.Controle.contrColetaFixa.consultaTipoColetaEventualDoCliente(idCliente, nomeBD);
        String hrEventual = Coleta.Controle.contrColetaFixa.consultaHoraEventualDoCliente(dataAtualComAntecedencia, idCliente, nomeBD);
        int tipoEscolhaColeta = Coleta.Controle.contrColetaFixa.consultaTipoEscolhaColetaDoCliente((Integer) session.getAttribute("idEmpresa"));
%>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Solicitar Coleta</title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>
        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/jsContato.js"></script>

        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>
        <script type="text/javascript" src="../../javascript/plugins/timepicker/jquery.clockpick.1.2.7.js"></script>
        <link href="../../javascript/plugins/timepicker/jquery.clockpick.1.2.7.css" rel="stylesheet" type="text/css" />

        <!-- FUSION CHARTS -->
        <script type="text/javascript" src="../../javascript/plugins/HighCharts/js/highcharts.js"></script>
        <script type="text/javascript" src="../../javascript/plugins/HighCharts/js/themes/grid.js"></script>
        <!-- FIM FUSION CHARTS -->

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]><link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" /><![endif]-->
        <script type="text/javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>


        <script type="text/javascript" charset="utf-8">

            $(function () {
                $("#dataColeta").datepicker({
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });

                $("#clockpick1").clockpick({
                    valuefield: 'horaColeta',
                    starthour: 0,
                    endhour: 23,
                    showminutes: true,
                    military: true,
                    minutedivisions: 6
                });
            });

            function preencherCampos() {
                var form = document.form1;
                if (form.contato.value === "") {
                    alert('Preencha o seu Nome!');
                    return false;
                }
                if (form.dataColeta.value === "") {
                    alert('Preencha a Data da Coleta!');
                    return false;
                }

                if (!valida_data(form.dataColeta)) {
                    return false;
                }
                if (!valida_hora(form.horaColeta)) {
                    return false;
                }

                if (!verificaHorarioColeta()) {

                    return false;
                }


                form.submit();
            }
            function verificaHorarioColeta() {

                console.log('verificaHorarioColeta2 ');

                var curDay = '<%= dia%>';
                var curMonth = '<%= mes%>';
                var curYear = '<%= ano%>';
                var curHour = '<%= hora%>';
                var curMin = '<%= minuto%>';
                // data e hora atual
                var curDateTime = new Date(curYear, parseInt(curMonth) - 1, curDay, curHour, curMin);

                var hrLimite = '<%= hrFimAcesso%>';
                var hrIniColeta = '<%= hrIniColeta%>';
                var hrFimColeta = '<%= hrFimColeta%>';


                var hrsIni = hrIniColeta.substring(0, 2);
                if (hrsIni < 10) {
                    hrsIni = hrsIni.replace('0', '');
                }
                var minIni = hrIniColeta.substring(3, 5);
                if (minIni < 10) {
                    minIni = minIni.replace('0', '');
                }
                // data hora inicio das coletas
                var dateTimeIniCol = new Date(curYear, parseInt(curMonth) - 1, curDay, hrsIni, minIni);


                var hrsFim = hrFimColeta.substring(0, 2);
                if (hrsFim < 10) {
                    hrsFim = hrsFim.replace('0', '');
                }
                var minFim = hrFimColeta.substring(3, 5);
                if (minFim < 10) {
                    minFim = minFim.replace('0', '');
                }
                // data hora fim das coletas
                var dateTimeFimCol = new Date(curYear, parseInt(curMonth) - 1, curDay, hrsFim, minFim);

                var hrsLim = hrLimite.substring(0, 2);
                if (hrsLim < 10) {
                    hrsLim = hrsLim.replace('0', '');
                }
                //  parseInt(hrsAcesso);
                var minLim = hrLimite.substring(3, 5);
                if (minLim < 10) {
                    minLim = minLim.replace('0', '');
                }
                //data hora limite
                var dateTimeLimite = new Date(curYear, parseInt(curMonth) - 1, curDay, hrsLim, minLim);

                // data hora antecencia minimima

                var ant = '<%= antecedencia%>';

                var data = document.getElementById("dataColeta").value;
                var hora = document.getElementById("horaColeta").value;

                var dia = data.substring(0, 2);
                if (dia < 10) {
                    dia = dia.replace('0', '');
                }
                // dia = parseInt(dia);
                var mes = data.substring(3, 5);
                if (mes < 10) {
                    mes = mes.replace('0', '');
                }
                mes = parseInt(mes) - 1;
                // var ano = parseInt(data.substring(6, 10));
                var ano = data.substring(6, 10);

                var hrs = hora.substring(0, 2);
                if (hrs < 10) {
                    hrs = hrs.replace('0', '');
                }
                parseInt(hrs);
                var min = hora.substring(3, 5);
                if (min < 10) {
                    min = min.replace('0', '');
                }

                var dateSolicitacao = new Date(ano, mes, dia, hrs, min);

                var dateAtualAnteced = addMinutes(curDateTime, ant);
                console.log('atual ' + curDateTime);
                console.log('atual - antecedencia ' + dateAtualAnteced);
                console.log('inicio coletas ' + dateTimeIniCol);
                console.log('fim coletas ' + dateTimeFimCol);
                console.log('limite coletas ' + dateTimeLimite);
                console.log('solictacao ' + dateSolicitacao);

                var todayDateOnly = new Date(curDateTime.getFullYear(), curDateTime.getMonth(), curDateTime.getDate()); //This will write a Date with time set to 00:00:00 so you kind of have date only
                var dDateOnly = new Date(dateSolicitacao.getFullYear(), dateSolicitacao.getMonth(), dateSolicitacao.getDate());

                /* console.log(todayDateOnly.getDate());
                 console.log(document.getElementById('isEvent') === null);
                 console.log('XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX');
                 
                 console.log('DIA hoje ' + todayDateOnly.getTime());
                 console.log('YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY');
                 */
                /* solicitada é eventual */
                if (document.getElementById('isEvent')) {
                    /* solicitada para o dia de hoje */
                    if (dDateOnly.getTime() === todayDateOnly.getTime()) {
                        /* solicitada para o dia de hoje e a hora da solicitação eventual é menor que hora atual*/
                        if (dateSolicitacao.getTime() < curDateTime.getTime()) {
                            alert("ATENÇÃO !!!! <br/><br/>O horario pré-cadastrado em nosso sistema para sua coléta é " + dateSolicitacao.getHours() + ":" + dateSolicitacao.getMinutes() + "<br/>\n\
                                Com já passou deste horário por favor entre em contato com a sua Agência.");
                            return false;
                            /* dateSolicitacao = dateAtualAnteced;
                             console.log('nova data solicitacao ' + dateSolicitacao);
                             console.log('HH ' + dateSolicitacao);
                             var hhmm = dateSolicitacao.getHours() + ':' + dateSolicitacao.getMinutes();
                             $('#horaColeta').val(hhmm);
                             console.log('HH:mm ' + hhmm);*/
                        }

                    }
                }

                if (curDateTime.getTime() > dateSolicitacao.getTime()) {
                    alert("Somente podem ser solicitadas coletas de hoje em diante!!!");
                    return false;
                }

                if (dDateOnly.getTime() === todayDateOnly.getTime() && dateSolicitacao.getTime() > dateTimeFimCol.getTime() && document.getElementById('isEvent') === null) {
                    alert("Coletas para o mesmo dia devem ser solicitadas até às " + hrFimColeta + "hrs!");
                    return false;
                }
                if (dateSolicitacao.getTime() < dateAtualAnteced.getTime()) {
                    alert("A Coleta deve ser solicitada com " + ant + " minutos de antecedencia!");
                    return false;
                }
                if (document.getElementById('isEvent') === null) {
                    if (dateSolicitacao.getHours() < dateTimeIniCol.getHours() || dateSolicitacao.getHours() > dateTimeFimCol.getHours()) {
                        alert("A Coletas estão disponíveis somente entre " + hrIniColeta + "hrs e " + hrFimColeta + "hrs !");
                        return false;
                    }
                    if ((dateSolicitacao.getHours() === dateTimeIniCol.getHours() && dateSolicitacao.getMinutes() < dateTimeIniCol.getMinutes()) ||
                            (dateSolicitacao.getHours() === dateTimeFimCol.getHours() && dateSolicitacao.getMinutes() > dateTimeFimCol.getMinutes())) {
                        alert("*A Coletas estão disponíveis somente entre " + hrIniColeta + "hrs e " + hrFimColeta + "hrs !");
                        return false;
                    }
                }

                return true;

            }

            function addMinutes(date, minutes) {
                return new Date(date.getTime() + minutes * 60000);
            }

        </script>
    </head>
    <body>
        <div id="divInteracao" class="esconder" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_cliente.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    <form name="form1" action="../../ServAgendaColeta" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd><span>Dados do Contato </span></dd>
                            </li>
                            <li id="liContato">
                                <dd>
                                    <label>Contato <a onClick="adicionarContato();"><img src="../../imagensNew/plus_circle.png" />Novo</a></label>
                                    <div id="divContato">
                                        <select style="width:250px;" name="contato" id="contato" onChange="buscarDadosContato(<%= idCliente%>);">
                                            <option value="">-- Selecione --</option>
                                            <%
                                                ArrayList<Contato> listaContatos = Controle.contrContato.consultaContatos(idCliente, nomeBD);
                                                for (int j = 0; j < listaContatos.size(); j++) {
                                                    Contato con = listaContatos.get(j);
                                            %>
                                            <option value="<%= con.getIdContato()%>"><%= con.getContato()%></option>
                                            <%}%>
                                        </select>
                                    </div>
                                </dd>
                                <dd>
                                    <label>Telefone</label>
                                    <input style="width:180px;" type="text" readonly id="telefone" name="telefone"/>
                                </dd>
                                <dd>
                                    <label>E-mail</label>
                                    <input style="width:250px;" type="text" readonly id="email" name="email"/>
                                </dd>
                                <dd>
                                    <label>Setor</label>
                                    <input style="width:180px;" type="text" readonly id="setor" name="setor"/>
                                </dd>
                            </li>
                            <li class="titulo">
                                <dd><span>Dados da Coleta</span></dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Data da Coleta</label>
                                    <input type="text" name="dataColeta" id="dataColeta" style="width:100px;" value="<%= sdf2.format(new Date())%>" maxlength="10" onKeyPress="mascara(this, maskData)" />
                                </dd>

                                <%
                                    if (tipoEscolhaColeta == 1) {%>
                                <dd>
                                    <label>Hora da Coleta</label>
                                    <input type="text" name="horaColeta" id="horaColeta" style="width:100px;" value="<%= hrf1.format(new Date())%>" maxlength="5" onKeyPress="mascara(this, maskHora)" />
                                    <img src="../../javascript/plugins/timepicker/clock.png" id="clockpick1" class="link_img" />
                                </dd>
                                <%}
                                    if (tipoEscolhaColeta == 2) {%>
                                <dd>
                                    <label>Hora da Coleta</label>
                                    <select name="horaColeta" id="horaColeta">
                                        <option value="<%= hrf1.format(hrIniColeta)%>">Mais cedo possivel</option>
                                        <option value="<%= hrf1.format(hrFimColeta)%> ">Mais tarde possível</option>                                    
                                    </select>
                                </dd>
                                <%}
                                    if (tipoEscolhaColeta == 3) {%>
                                <dd>                       
                                    <%if (!hrEventual.equals("")) {%>
                                    <input type="hidden" name="horaColeta" id="horaColeta" style="width:100px;" value="<%= hrEventual%>" maxlength="5"/>
                                    <input type="hidden" name="isEvent" id="isEvent" value="1"/>
                                    <%} else {%>
                                    <input type="hidden" name="horaColeta" id="horaColeta" style="width:100px;" value="<%= hrf1.format(hrFimColeta)%>" maxlength="5"/>
                                    <%}%>
                                </dd>
                                <%}
                                    if (tipoEscolhaColeta == 4) {%>
                                <dd>
                                    <label>Hora da Coleta</label>
                                    <select name="horaColeta" id="horaColeta">
                                        <option value="<%= hrf1.format(hrIniColeta)%>">Periodo da manhã</option>
                                        <option value="<%= hrf1.format(hrFimColeta)%> ">Periodo da tarde</option>                                    
                                    </select>
                                </dd> 
                                <%}%>
                            </li>
                            <li>
                                <dd>
                                    <label>Digite abaixo as possíveis observações para esta coleta:</label>
                                    <textarea style="width:800px;height:50px;" name="obs" id="textointeracao"></textarea>
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="conteudoLiContato" id="conteudoLiContato" value=""/>
                                        <input type="hidden" name="idCliente" value="<%= idCliente%>"/>
                                        <input type="hidden" name="idUsuario" value="0"/>
                                        <input type="hidden" name="idColetador" value="<%= coleEve%>"/>
                                        <input type="hidden" name="idTipo" value="<%= coleEvTipo%>"/>
                                        <button type="button" <%if (hc == null) {%> class="disabled" disabled <%} else {%> class="positive" <%}%> onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> SOLICITAR COLETA</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                    <div id="titulo1">Últimas 5 Coletas</div>
                    <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                        <thead>
                            <tr>
                                <th><h3>Nº</h3></th>
                                <th><h3>Tipo</h3></th>
                                <th><h3>Coletador</h3></th>
                                <th><h3>Solicitado em</h3></th>
                                <th><h3>Coletar até</h3></th>
                                <th><h3>Status da Coleta</h3></th>
                                <th><h3>Cadastrado por</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList listaColetas = Coleta.Controle.contrColeta.consultaUltimasColetasDoCliente(idCliente, nomeBD);
                                for (int j = 0; j < listaColetas.size(); j++) {
                                    Coleta.Entidade.Coleta col = (Coleta.Entidade.Coleta) listaColetas.get(j);
                                    int idColeta = col.getIdColeta();
                                    int idTipo = col.getIdTipo();
                                    String tipo = Coleta.Controle.contrTipoColeta.consultaNomeTipoColetaById(idTipo, nomeBD);
                                    int idColetador = col.getIdColetador();
                                    String coletador = Coleta.Controle.contrColetador.consultaNomeColetadoresById(idColetador, nomeBD);
                                    int idUsuarios = col.getIdUsuario();
                                    String user = "Web";
                                    if (idUsuarios != 0) {
                                        user = Controle.contrUsuario.consultaNomeUsuarioById(idUsuarios, nomeBD);
                                    }
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    String dataSolicitacao = sdf.format(col.getDataHoraSolicitacao());
                                    String dataColeta = sdf.format(col.getDataHoraColeta());
                                    String dataBaixa = "";
                                    if (col.getDataHoraBaixa() != null) {
                                        dataBaixa = sdf.format(col.getDataHoraBaixa());
                                    }
                                    int status = col.getStatus();
                                    String nomeStatus = "";
                                    switch (status) {
                                        case 1:
                                            nomeStatus = "SOLICITADA WEB";
                                            break;
                                        case 2:
                                            nomeStatus = "AGUARDANDO COLETA";
                                            break;
                                        case 3:
                                            nomeStatus = "CANCELADA ÀS " + dataBaixa;
                                            break;
                                        case 4:
                                            nomeStatus = "CLIENTE AUSENTE";
                                            break;
                                        case 5:
                                            nomeStatus = "COLETADO ÀS " + dataBaixa;
                                            break;
                                        case 6:
                                            nomeStatus = "AGUARDANDO CLIENTE";
                                            break;
                                    }
                                    String obs = col.getObs();

                            %>
                            <tr>
                                <td><b><%= idColeta%></b></td>
                                <td><%= tipo%></td>
                                <td><%= coletador%></td>
                                <td><%= dataSolicitacao%></td>
                                <td><%= dataColeta%></td>
                                <td><%= nomeStatus%></td>
                                <td><%= user%></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                    <script type="text/javascript">
                        var sorter2 = new TINY.table.sorter('sorter2', 'table2', {
                            headclass: 'head',
                            ascclass: 'asc',
                            descclass: 'desc',
                            evenclass: 'evenrow',
                            oddclass: 'oddrow',
                            evenselclass: 'evenselected',
                            oddselclass: 'oddselected',
                            paginate: false,
                            size: 10,
                            hoverid: 'selectedrowPointer',
                            sortcolumn: 3,
                            sortdir: -1,
                            init: true
                        });
                    </script>



                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
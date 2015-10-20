
<%@ page import = "java.sql.Time,java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        if (idNivelDoUsuario != 1) {
            response.sendRedirect("../jsp/imp_movimento.jsp?msg=Acesso Negado!");
        }

        String nomeBD = (String) session.getAttribute("empresa");
        int idEmpresa = (Integer) session.getAttribute("idEmpresa");

        SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
        Coleta.Entidade.HoraColeta hc = Coleta.Controle.contrHoraColeta.consultaHoraColetaById(nomeBD);
        String horaFimAcesso = "", horaIniColeta = "", horaFimColeta = "", antecedencia = "";
        if (hc != null) {
            horaFimAcesso = formatador.format(hc.getHoraFimAcesso());
            horaIniColeta = formatador.format(hc.getHoraIniColeta());
            horaFimColeta = formatador.format(hc.getHoraFimColeta());
            antecedencia = String.valueOf(hc.getMinAntecedencia());
        }

%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/mascara.js" charset="utf-8"></script>
        

        <!-- TimePicker -->
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/plugins/timepicker/jquery.clockpick.1.2.7.js"></script>
        <link href="../../javascript/plugins/timepicker/jquery.clockpick.1.2.7.css" rel="stylesheet" type="text/css" />
        <!-- Fim TimePicker -->

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript">

            $(document).ready(function() {
                $("#clockpick1").clockpick({
                    valuefield : 'timefield1',
                    starthour : 0,
                    endhour : 23,
                    showminutes : true,
                    military: true,
                    minutedivisions: 6
                });
                $("#clockpick2").clockpick({
                    valuefield : 'timefield2',
                    starthour : 0,
                    endhour : 23,
                    showminutes : true,
                    military: true,
                    minutedivisions: 6
                });
                $("#clockpick3").clockpick({
                    valuefield : 'timefield3',
                    starthour : 0,
                    endhour : 23,
                    showminutes : true,
                    military: true,
                    minutedivisions: 6
                });
            });

            function preencherCampos(){
                var form = document.form1;
                if(!valida_hora(form.timefield1)){
                    return false;
                }
                if(!valida_hora(form.timefield2)){
                    return false;
                }
                if(!valida_hora(form.timefield3)){
                    return false;
                }
                if(form.antecedencia.value==""){
                    alert('Preencha o tempo de antecedência!');
                    return false;
                }
                form.submit();
            }
        </script>
        <title>Portal Postal | Horarios da Coleta</title>
    </head>
    <body>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Horários para solicitações de coleta pela web.</div>
                    <form name="form1" action="../../ServHoraColeta" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd>
                                    <span>Horário limite que o cliente pode solicitar coletas via web:</span>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Limite:</label>
                                    <input name="timefield1" type="text" id="timefield1" size="8" maxlength="5" value="<%= horaFimAcesso%>" onKeyPress="mascara(this,maskHora)" onblur="return valida_hora(this);" />
                                    <img src="../../javascript/plugins/timepicker/clock.png" id="clockpick1" class="link_img" />
                                </dd>
                            </li>
                            <li class="titulo">
                                <dd>
                                    <span>Horario de inicio e término da coletas em sua agência (para solicitaçõe de coleta via web):</span>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Inicio:</label>
                                    <input name="timefield2" type="text" id="timefield2" size="8" maxlength="5" value="<%= horaIniColeta%>" onKeyPress="mascara(this,maskHora)" onblur="return valida_hora(this);" />
                                    <img src="../../javascript/plugins/timepicker/clock.png" id="clockpick2" class="link_img" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Termino:</label>
                                    <input name="timefield3" type="text" id="timefield3" size="8" maxlength="5" value="<%= horaFimColeta%>" onKeyPress="mascara(this,maskHora)" onblur="return valida_hora(this);" />
                                    <img src="../../javascript/plugins/timepicker/clock.png" id="clockpick3" class="link_img" />
                                </dd>
                            </li>
                            <li class="titulo">
                                <dd>
                                    <span>Antecedência minima para o cliente solicitar a coleta pela web:</span>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Antc.:</label>
                                    <input name="antecedencia" type="text" id="antecedencia" size="8" maxlength="3" value="<%= antecedencia%>" onKeyPress="mascara(this,maskNumero)" />
                                    &nbsp;minutos.
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%; text-align: center;">
                                    <div class="buttons">
                                        <input type="hidden" name="rota" value="0" />
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
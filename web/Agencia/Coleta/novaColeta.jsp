
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
            response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0
            response.setDateHeader("Expires", 0); //prevent caching at the proxy server

            SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD == null) {
                response.sendRedirect("../index.jsp?msgLog=3");
            } else {

                int idEmpresa = (Integer) session.getAttribute("idEmpresa");
                int idUsuario = (Integer) session.getAttribute("idUsuario");
                int idCliente = 0;
                if (request.getParameter("idCliente") != null && !request.getParameter("idCliente").equals("")) {
                    idCliente = Integer.parseInt(request.getParameter("idCliente"));
                }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="cache-control" content="no-cache"/>
        <meta http-equiv="Expires" content="-1"/>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>
        
        <script type="text/javascript" src="../../javascript/plugins/autocomplete/js/simpleAutoComplete.js"></script>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/autocomplete/css/simpleAutoComplete.css" />
        <script type="text/javascript" src="../../javascript/plugins/timepicker/jquery.clockpick.1.2.7.js"></script>
        <link href="../../javascript/plugins/timepicker/jquery.clockpick.1.2.7.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/jsContato.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js" charset="utf-8"></script>

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript">

            $(document).ready(function(){
                $('#nome_cliente').simpleAutoComplete('../../AjaxPages/ajax_autocomplete_cliente.jsp',{
                    autoCompleteClassName: 'autocomplete',
                    selectedClassName: 'sel',
                    attrCallBack: 'rel',
                    identifier: 'estado'
                },estadoCallback);
            });
            
            $(function() {
                $( "#dataColeta" ).datepicker({
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });

                $("#clockpick1").clockpick({
                    valuefield : 'horaColeta',
                    starthour : 0,
                    endhour : 23,
                    showminutes : true,
                    military: true,
                    minutedivisions: 6
                });
            });
            
            function preencherCamposColeta(){
                var form = document.form1;
                if(form.idCliente.value=="0" || form.idCliente.value==""){
                    alert('Escolha um Cliente para a Coleta!');
                    return false;
                }
                if(form.contato.value==""){
                    alert('Escolha um contato ou cadastre um novo contato para esta coleta!');
                    return false;
                }
                if(form.idColetador.value=="0"){
                    alert('Escolha o Coletador da Coleta!');
                    return false;
                }
                if(form.idTipo.value=="0"){
                    alert('Escolha o Tipo da Coleta!');
                    return false;
                }
                if(form.dataColeta.value == ""){
                    alert('Preencha a Data da Coleta!');
                    return false;
                }
                if(!valida_data(form.dataColeta)){
                    return false;
                }
                if(!valida_hora(form.horaColeta)){
                    return false;
                }

                form.submit();
            }

            function estadoCallback( par ){
                document.getElementById("cli_cod").innerHTML = par[0];
                document.getElementById("cli_nome").innerHTML = par[1];
                document.getElementById("cli_endereco").innerHTML = par[2];
                document.getElementById("cli_cep").innerHTML = par[3];
                document.getElementById("cli_bairro").innerHTML = par[4] + ", " + par[5];
                //document.getElementById("cli_complemento").value = par[5];
                document.getElementById("cli_cidade").innerHTML = par[6] + "/" + par[7];
                //document.getElementById("cli_estado").value = par[7];
                document.getElementById("cli_email").innerHTML = par[8];
                document.getElementById("cli_fone").innerHTML = par[9];
                document.getElementById("cli_cnpj").innerHTML = par[10];

                document.getElementById("idCliente").value = par[0];
                montaComboContato(par[0]);
            }

            function disableEnterKey(e){
                var key;
                if(window.event)
                    key = window.event.keyCode;     //IE
                else
                    key = e.which;     //firefox
                if(key == 13)
                    return false;
                else
                    return true;
            }
        </script>

        <title>Portal Postal | Nova Coleta</title>
    </head>
    <body>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <div id="titulo1">Solicitar Nova Coleta</div>
                    <ul class="ul_formulario" style="margin-bottom: 0px;border-bottom: 1px solid #ccc;">
                        <li class="titulo">
                            <dd><span>Dados do Cliente</span></dd>
                        </li>
                        <li>
                            <dd>
                                <label>Selecione um Cliente</label>
                                <input style="width: 450px;" type="text" name="nome_cliente" id="nome_cliente" /> <a style=" color: blue; cursor: pointer; font-weight: bold;"><img src="../../imagensNew/lupa.png" class="link_img" />Pesquisar</a>
                            </dd>
                        </li>
                    </ul>
                    <ul class="ul_dados">
                        <li>
                            <dd style="width: 380px;"><label>Nome:</label><div id="cli_nome"></div></dd>
                            <dd style="width: 100px;"><label>Código:</label><div id="cli_cod"></div></dd>
                            <dd style="width: 180px;"><label>CNPJ:</label><div id="cli_cnpj"></div></dd>
                            <dd style="width: 290px;"><label>Email:</label><div id="cli_email"></div></dd>
                            <dd style="width: 140px;"><label>Telefone:</label><div id="cli_fone"></div></dd>
                        </li>
                        <li>
                            <dd style="width: 380px;"><label>Endereço:</label><div id="cli_endereco"></div></dd>
                            <dd style="width: 280px;"><label>Bairro:</label><div id="cli_bairro"></div></dd>
                            <dd style="width: 290px;"><label>Cidade/UF:</label><div id="cli_cidade"></div></dd>
                            <dd style="width: 140px;"><label>CEP:</label><div id="cli_cep"></div></dd>
                        </li>
                    </ul>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                    <form name="form1" action="../../ServInserirColeta" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd><span>Dados do Contato</span></dd>
                            </li>
                            <li id="liContato">
                                <dd>
                                    <label>Contato <a onClick="adicionarContato();"><img src="../../imagensNew/plus_circle.png" />Novo</a></label>
                                    <div id="divContato">
                                    <select style="width:250px;" name="contato" id="contato" onChange="buscarDadosContato(<%= idCliente%>);">
                                        <option value="">-- Selecione --</option>
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
                                    <label>Coletador</label>
                                    <select name="idColetador">
                                        <option value="0">-- SELECIONE --</option>
                                        <%
                                                        int coletadorEventual = Coleta.Controle.contrColetaFixa.consultaColetadorEventualDoCliente(idCliente, nomeBD);
                                                        ArrayList listaColetador = Coleta.Controle.contrColetador.consultaTodosColetadores(nomeBD);
                                                        for (int i = 0; i < listaColetador.size(); i++) {
                                                            Coleta.Entidade.Coletador col = (Coleta.Entidade.Coletador) listaColetador.get(i);
                                                            String nomeColetador = col.getNome();
                                                            int idColetador = col.getIdColetador();
                                        %>
                                        <option value="<%= idColetador%>" <%if (coletadorEventual == idColetador) {%> selected <%}%> ><%= nomeColetador%></option>
                                        <%}%>
                                    </select>
                                </dd>
                                <dd>
                                    <label>Tipo da Coleta</label>
                                    <select name="idTipo">
                                        <option value="0">-- SELECIONE --</option>
                                        <%
                                                        ArrayList listaTipo = Coleta.Controle.contrTipoColeta.consultaTodosTipoColeta(nomeBD);
                                                        for (int i = 0; i < listaTipo.size(); i++) {
                                                            Coleta.Entidade.TipoColeta tip = (Coleta.Entidade.TipoColeta) listaTipo.get(i);
                                                            String nomeTipo = tip.getTipo();
                                                            int idTipo = tip.getIdTipoColeta();
                                        %>
                                        <option value="<%= idTipo%>"><%= nomeTipo%></option>
                                        <%}%>
                                    </select>
                                </dd>
                                <dd>
                                    <label>Data da Coleta</label>
                                    <input type="text" name="dataColeta" id="dataColeta" style="width:100px;" value="<%= sdf1.format(new Date()) %>" maxlength="10" onKeyPress="mascara(this,maskData)" />
                                </dd>
                                <dd>
                                    <label>Hora da Coleta</label>
                                    <input type="text" name="horaColeta" id="horaColeta" style="width:100px;" value="<%= sdf2.format(new Date()) %>" maxlength="5" onKeyPress="mascara(this,maskHora)" />
                                    <img src="../../javascript/plugins/timepicker/clock.png" id="clockpick1" class="link_img" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Digite abaixo as possíveis observações para esta coleta:</label>
                                    <textarea style="width:800px;height:50px;" name="obs" id="textointeracao"></textarea>
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%;">
                                    <input type="hidden" name="conteudoLiContato" id="conteudoLiContato" value="" />
                                    <input type="hidden" name="idCliente" id="idCliente" value="<%= idCliente%>" />
                                    <input type="hidden" name="idUsuario" id="idUsuario" value="<%= idUsuario%>" />
                                    <div class="buttons">
                                        <button type="button" class="positive" onclick="return (preencherCamposColeta());"><img src="../../imagensNew/tick_circle.png" /> SOLICITAR COLETA</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                    <div id="titulo2">Últimas 5 Coletas deste Cliente</div>
                    <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                        <thead>
                            <tr>
                                <th><h3>Nº</h3></th>
                                <th><h3>Tipo</h3></th>
                                <th><h3>Coletador</h3></th>
                                <th><h3>Solicitado em</h3></th>
                                <th><h3>Coleta para</h3></th>
                                <th><h3>Status da Coleta</h3></th>
                                <th><h3>Obs</h3></th>
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
                                                String obs = col.getObs();
                                                String user = Controle.contrUsuario.consultaNomeUsuarioById(idUsuarios, nomeBD);
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
                                                        nomeStatus = "Solicitada";
                                                        break;
                                                    case 2:
                                                        nomeStatus = "Aguardando Coleta";
                                                        break;
                                                    case 3:
                                                        nomeStatus = "Coletado em " + dataBaixa;
                                                        break;
                                                }
                            %>
                            <tr>
                                <td><b><%= idColeta%></b></td>
                                <td><%= tipo%></td>
                                <td><%= coletador%></td>
                                <td><%= dataSolicitacao%></td>
                                <td><%= dataColeta%></td>
                                <td><%= nomeStatus%></td>
                                <td><%= obs%></td>
                                <td><%= user%></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                    <script type="text/javascript">
                        var sorter2 = new TINY.table.sorter('sorter2','table2',{
                            headclass:'head',
                            ascclass:'asc',
                            descclass:'desc',
                            evenclass:'evenrow',
                            oddclass:'oddrow',
                            evenselclass:'evenselected',
                            oddselclass:'oddselected',
                            paginate:false,
                            size:20,
                            currentid:'currentpage2',
                            totalid:'totalpages2',
                            hoverid:'selectedrowPointer',
                            pageddid:'pagedropdown2',
                            navid:'tablenav2',
                            sortcolumn:0,
                            sortdir:-1,
                            init:true
                        });
                    </script>


                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
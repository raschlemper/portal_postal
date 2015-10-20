<%@page import="Coleta.Entidade.Coletador"%>
<%@page import="Coleta.Controle.contrColetador"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("../../index.jsp?msgLog=3");
            } else {

                int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
                if (idNivelDoUsuario != 1) {
                    response.sendRedirect("../jsp/imp_movimento.jsp?msg=Acesso Negado!");
                }

                String nomeBD = (String) session.getAttribute("empresa");

                int idColetador = Integer.parseInt(request.getParameter("idColetador"));
                String nomeColetador = Coleta.Controle.contrColetador.consultaNomeColetadoresById(idColetador, nomeBD);
                ArrayList listaColetaFixa = Coleta.Controle.contrColetaFixa.consultaColetasFixasPeloColetador(idColetador, nomeBD);
                int numColetaFixa = listaColetaFixa.size();
                ArrayList listaTipo = Coleta.Controle.contrTipoColeta.consultaTodosTipoColeta(nomeBD);
                String optTipo = "";
                for (int i = 0; i < listaTipo.size(); i++) {
                    Coleta.Entidade.TipoColeta tip = (Coleta.Entidade.TipoColeta) listaTipo.get(i);
                    String nomeTipo = tip.getTipo();
                    int idTipo = tip.getIdTipoColeta();
                    optTipo += "<option value='" + idTipo + "'>" + nomeTipo + "</option>";
                }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Portal Postal | Rotas Fixas de <%= nomeColetador%></title>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js" charset="utf-8"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/plugins/autocomplete/js/simpleAutoComplete.js"></script>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/autocomplete/css/simpleAutoComplete.css" />

        <script language="Javascript" type="text/javascript">

            $(document).ready(function(){
                $('#nomeCliente').simpleAutoComplete('../../AjaxPages/ajax_autocomplete_cliente.jsp',{
                    autoCompleteClassName: 'autocomplete',
                    selectedClassName: 'sel',
                    attrCallBack: 'rel',
                    identifier: 'estado'
                },estadoCallback);
            });

            function estadoCallback( par ){
                document.getElementById('idCliAux').value = par[0];
                document.getElementById('nomeCliAux').value = par[1];

                document.getElementById("cli_endereco").innerHTML = par[2];
                document.getElementById("cli_cep").innerHTML = par[3];
                document.getElementById("cli_bairro").innerHTML = par[4] + ", " + par[5];
                document.getElementById("cli_cidade").innerHTML = par[6] + "/" + par[7];

                habilitaBotao();
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



            var popup;
            function abrirPopMapa() {
                var cont = document.getElementById('contador').value;
                var idCliente = document.getElementById('cliente'+cont).value;
                var endereco = "coletador_rota_mapa_cliente.jsp?idCliente="+idCliente;
                popup = window.open(endereco, "popup", "resizable=1,width=800,height=600,scrollbars=auto");
                popup.moveTo(0,0);
            }

            function habilitaBotao(){
                document.getElementById('add').disabled = false;
                document.getElementById('verMapa').disabled = false;
                document.getElementById('add').className = "regular";
                document.getElementById('verMapa').className = "regular";
            }

            function desabilitaBotao(){
                document.getElementById('add').disabled = true;
                document.getElementById('verMapa').disabled = true;
                document.getElementById('add').className = "disabled";
                document.getElementById('verMapa').className = "disabled";
            }

            function addRow(){
                
                desabilitaBotao();

                var linha = document.getElementById('table').insertRow(document.getElementById('table').rows.length);
                var cont = document.getElementById('contador').value;
                var newCont = parseInt(cont) + 1;

                var nomeCli = document.getElementById('nomeCliAux').value;
                var idCli = document.getElementById('idCliAux').value;

                // insere as linhas na tabela
                var coluna0 = linha.insertCell(0);
                coluna0.innerHTML = "---";
                coluna0.align = "center";
                
                linha.insertCell(1).innerHTML = "<input type='hidden' id='cliente"+cont+"' name='cliente"+cont+"' value='"+idCli+"' /><b>"+nomeCli+"</b>";

                var coluna1 = linha.insertCell(2);
                coluna1.innerHTML = "<select id='select"+cont+"' name='select"+cont+"'> <%= optTipo%> </select>";
                coluna1.align = "center";

                var coluna2 = linha.insertCell(3);
                coluna2.innerHTML = "<input type='text' id='hora"+cont+"' name='hora"+cont+"' value='' size='5' maxlength='5' onKeyPress='mascara(this,maskHora)' onblur='valida_hora(this);' />";
                coluna2.align = "center";

                var coluna3 = linha.insertCell(4);
                coluna3.innerHTML = "<select id='fixo"+ cont +"' name='fixo"+ cont +"'>"
                    +"<option value='1' selected >Fixa</option>"
                    +"<option value='0'>Eventual</option>"
                    +"</select>";
                coluna3.align = "center"

                var coluna4 = linha.insertCell(5);
                coluna4.innerHTML = "<input type='button' value='Remover' id='del' onclick='delRow(this);' />";
                coluna4.align = "center"

                linha.className = 'red';

                document.getElementById('contador').value = newCont;


                document.getElementById('idCliAux').value = 0;
                document.getElementById('nomeCliAux').value = "";

                document.getElementById("cli_endereco").innerHTML = "<b style='color:red;'>Escolha um Cliente!</b>";
                document.getElementById("cli_cep").innerHTML = "";
                document.getElementById("cli_bairro").innerHTML = "";
                document.getElementById("cli_cidade").innerHTML = "";
                document.getElementById('nomeCliente').value = "";
                document.getElementById('nomeCliente').focus();

            }

            function delRow(linha){
                if (confirm('Tem certeza que deseja excluir?')){
                    var tabela = document.getElementById('table');
                    linha = linha.parentNode.parentNode;
                    id = linha.rowIndex;
                    tabela.deleteRow(id - 1);
                }else{
                    return false;
                }
            }

            function validateRow(){
                var lastRow = document.getElementById('table').rows.length;
                if(lastRow>0){
                    for (var i=0; i<lastRow; i++) {
                        var campoHora = document.getElementById('hora' + i);
                        if (campoHora!=null && campoHora.value.length < 5) {
                            alert('Preencha todos os Horarios das Coletas!');
                            campoHora.focus();
                            return false;
                        }
                    }
                }else{
                    return false
                }
                return true;
            }

            function addDelList(idColetaFixa, linha){
                if (confirm('Tem certeza que deseja excluir?')){
                    var tabela = document.getElementById('table');

                    linha = linha.parentNode.parentNode;
                    id = linha.rowIndex;
                    tabela.deleteRow(id - 1);
                    var lista = document.getElementById('delList').value;
                    if(lista==''){
                        document.getElementById('delList').value += idColetaFixa;
                    }else{
                        document.getElementById('delList').value += ';'+idColetaFixa;
                    }
                }else{
                    return false;
                }
            }
        </script>
    </head>
    <body>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <%if (listaTipo.size() <= 0) {%>
                    <p style='margin: 20px;'>
                        <img style="float: left;" src='../../imagensNew/big_alert.png' align='top' border='0'/>
                        <div style="color: red; margin-left: 80px;">
                            <b style="font-size: 14px;"> Para inserir uma rota para este coletador, cadastre primeiramente os tipos de coleta! </b><br/>
                            <a href="tipo_coleta_lista.jsp" >&raquo; Clique aqui para cadastrar um novo tipo de coleta.</a>
                        </div>
                    </p>
                    <%} else {%>


                    <div id="titulo1">Montar Rota Fixa de <span><%= nomeColetador%></span></div>
                    <ul class="ul_formulario">
                        <li>
                            <dd>
                                <label>Selecione o Cliente</label>
                                <input style="width:450px;" type="text" id="nomeCliente" name="nomeCliente" onclick="javascript:this.value='';" />
                            </dd>
                        </li>
                        <li style="height: 20px;">
                            <dd style="width: 450px;"><label>Endereço:</label><div style="font-size: 11px;" id="cli_endereco"><b style="color:red;">Escolha um Cliente!</b></div></dd>
                            <dd style="width: 250px;"><label>Bairro:</label><div style="font-size: 11px;" id="cli_bairro"></div></dd>
                            <dd style="width: 350px;"><label>Cidade/UF:</label><div style="font-size: 11px;" id="cli_cidade"></div></dd>
                            <dd style="width: 100px;"><label>CEP:</label><div style="font-size: 11px;" id="cli_cep"></div></dd>
                        </li>
                        <li>
                            <dd>
                                <div class="buttons">
                                    <input type="hidden" id="nomeCliAux" name="nomeCliAux" />
                                    <input type="hidden" id="idCliAux" name="idCliAux" />
                                    <button type="submit" disabled class="disabled" id="add" onclick="addRow();"><img src="../../imagensNew/plus_circle.png" /> ADICIONAR NA ROTA</button>
                                    <button type="submit" disabled class="disabled" id="verMapa" onclick="abrirPopMapa();" ><img src="../../imagensNew/map_pin.png" /> GERAR MAPA</button>
                                </div>
                            </dd>
                        </li>
                    </ul>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>



                    <form name="form1" action="../../ServInserirColetaFixa" method="post">
                        <table width="100%" cellspacing="0" class="tb1">
                            <thead>
                                <tr>
                                    <th>CK</th>
                                    <th>Cliente</th>
                                    <th width="120">Tipo da Coleta</th>
                                    <th width="60">Horario</th>
                                    <th width="120">Coleta Fixa?</th>
                                    <th width="60">Remover</th>
                                </tr>
                            </thead>
                            <tbody id="table">
                                <%
                                    for (int i = 0; i < numColetaFixa; i++) {
                                        Coleta.Entidade.ColetaFixa cf = (Coleta.Entidade.ColetaFixa) listaColetaFixa.get(i);
                                        int idColetaFixa = cf.getIdColetaFixa();
                                        int idCliente = cf.getIdCliente();
                                        int idTipoColeta = cf.getIdTipo();
                                        int fixo = cf.getFixo();
                                        String horaColeta = cf.getHora();
                                        Entidade.Clientes cli = Controle.contrCliente.consultaClienteById(idCliente, nomeBD);
                                        String nomeCliente = cli.getNome();
                                        String className = "odd";
                                        if (i % 2 == 0) {
                                            className = "even";
                                        }
                                %>
                                <tr class="<%= className%>">
                                    <td align="center"><input type="checkbox" name="ids" value="<%=idColetaFixa%>" /></td>
                                    <td>
                                        <%= nomeCliente%>
                                        <input type="hidden" id="cliente<%=i%>" value="<%= idCliente%>" name="cliente<%=i%>" />
                                    </td>
                                    <td align="center">
                                        <select id="select<%=i%>" name="select<%=i%>">
                                            <%
                                                                                    for (int j = 0; j < listaTipo.size(); j++) {
                                                                                        Coleta.Entidade.TipoColeta tip = (Coleta.Entidade.TipoColeta) listaTipo.get(j);
                                                                                        String nomeTipo = tip.getTipo();
                                                                                        int idTipo = tip.getIdTipoColeta();
                                            %>
                                            <option value="<%= idTipo%>" <%if (idTipo == idTipoColeta) {%> selected <%}%> ><%= nomeTipo%></option>
                                            <%}%>
                                        </select>
                                    </td>
                                    <td align="center"><input type="text" id="hora<%=i%>" name="hora<%=i%>" value="<%= horaColeta%>" size="5" maxlength="5" onkeypress="mascara(this,maskHora)" onblur="valida_hora(this);" /></td>
                                    <td align="center">
                                        <select id="fixo<%=i%>" name="fixo<%=i%>">
                                            <option value="0" <%if (fixo == 0) {%> selected <%}%> >Eventual</option>
                                            <option value="1" <%if (fixo == 1) {%> selected <%}%> >Fixa</option>
                                        </select>
                                    </td>
                                    <td align="center"><input type="button" value="Remover" id="del" onclick="addDelList('<%= idColetaFixa%>',this);" /></td>
                                </tr>
                                <%}%>
                            </tbody>
                        </table>

                        <div class="buttons" style="text-align: center; margin-bottom: 25px;">
                            <input type="hidden" name="idColetador" id="idColetador" value="<%= idColetador%>" />
                            <input type="hidden" name="contador" id="contador" value="<%= numColetaFixa%>" />
                            <input type="hidden" name="delList" id="delList" value="" />
                            <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD %>" />
                            <button type="submit" <%if (listaTipo.size() <= 0) {%> disabled class="disabled" <%} else {%> class="positive" <%}%> onclick="javascript:document.form1.target='';document.form1.action='../../ServInserirColetaFixa'; return validateRow();" ><img src="../../imagensNew/tick_circle.png" /> SALVAR ROTA</button>
                            <button type="submit" <%if (listaTipo.size() <= 0) {%> disabled class="disabled" <%} else {%> class="regular" <%}%> onclick="javascript:document.form1.target='_blank';document.form1.action='coletador_rota_mapa.jsp';" ><img src="../../imagensNew/map_pin.png" /> GERAR MAPA</button>
                        </div>
                        <div class="buttons" style="text-align: center; border-top: 1px solid silver; margin-bottom: 50px;">
                            <div style="width: 100%; font-size: 14px;"><b>ESCOLHA UM COLETADOR PARA TRANSFERIR AS ROTAS SELECIONADAS</b></div>
                            <select name="idColetador2" style="width:200px;">
                                <option value="0">-- SELECIONE UM COLETADOR --</option>
                                <%
                                    ArrayList listaColetadores = contrColetador.consultaTodosColetadores(nomeBD);
                                    for (int i = 0; i < listaColetadores.size(); i++) {
                                        Coletador coletador = (Coletador) listaColetadores.get(i);
                                        String nomeCol = coletador.getNome();
                                        int idCol = coletador.getIdColetador();
                                %>
                                <option value="<%= idCol%>"><%= nomeCol%></option>
                                <%}%>
                            </select>
                            <button type="submit" <%if (listaTipo.size() <= 0) {%> disabled class="disabled" <%} else {%> class="positive" <%}%> onclick="javascript:document.form1.target='';document.form1.action='../../ServAlterarBoyColetaFixa'; return validateRow();" ><img src="../../imagensNew/users.png" /> ALTERAR COLETADOR</button>
                        </div>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>
                    <%}%>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
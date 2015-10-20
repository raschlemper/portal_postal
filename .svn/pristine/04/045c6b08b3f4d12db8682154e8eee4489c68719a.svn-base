
<%@page import="Controle.ContrAmarracaoServico"%>
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Controle.ContrServicoECT"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
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
        
        int idAmarracao = Integer.parseInt(request.getParameter("idAmarracao"));
        Entidade.Amarracao am = Controle.ContrAmarracao.consultaAmarracaoById(idAmarracao, nomeBD);
        ArrayList<Entidade.AmarracaoCep> listaCob = Controle.ContrAmarracaoCep.consultaTodosByIdAmarracao(idAmarracao, nomeBD);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

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
            
            function addRow(){
                var nrLinha = document.getElementById('table').rows.length;
                var linha = document.getElementById('table').insertRow(nrLinha);
                var cont = document.getElementById('contador').value;
                var newCont = parseInt(cont) + 1;

                linha.insertCell(0).appendChild(document.createTextNode("De"));
                linha.insertCell(1).innerHTML = "<input type='input' id='cepIni"+newCont+"' name='cepIni"+newCont+"' size='15' maxlength='9' onKeyPress='mascara(this,maskCep)'>";
                linha.insertCell(2).appendChild(document.createTextNode("Até"));
                linha.insertCell(3).innerHTML = "<input type='input' id='cepFim"+newCont+"' name='cepFim"+newCont+"' size='15' maxlength='9' onKeyPress='mascara(this,maskCep)'>";
                var cell4 = linha.insertCell(4)
                cell4.innerHTML = "<img src='../../imagensNew/cross_circle.png' style='cursor:pointer;' onclick='delRow(this);'>";
                cell4.align = "center";
                /*var cell5 = linha.insertCell(5)
                cell5.innerHTML = "<img src='../../imagensNew/lupa.png' style='cursor:pointer;' onclick='conferirFaixaCep("+newCont+", 0);'>";
                cell5.align = "center";*/

                linha.className = "faixas";

                document.getElementById('contador').value = newCont;
            }

            function validateRow(){
                var contador = document.getElementById('contador').value;

                if(document.form1.nome.value==""){
                    alert("Preencha nome da Amarração!");
                    document.form1.nome.focus();
                    return false;
                }

                if(document.form1.sigla.value==""){
                    alert("Preencha a sigla da Amarração!");
                    document.form1.sigla.focus();
                    return false;
                }
                
                var lb = document.getElementById('servico_2');
                var servicos = "";
                for(i=0; i<lb.length; i++)  {
                    if(i == 0){
                        servicos += lb.options[i].value;                        
                    }else{
                        servicos += ";" + lb.options[i].value;                        
                    }
                }
                document.getElementById('servicos').value = servicos;

                //PERCORRE TODOS OS CAMPOS DA TABELA DE PESOS E PRECOS
                for (var i=1; i<=contador; i++) {
                    if(document.getElementById('cepIni'+i)!=null){
                        var cepIni = document.getElementById('cepIni'+i).value;
                        var cepFim = document.getElementById('cepFim'+i).value;

                        //VERIFICA SE O CEP INICIAL FOI PREENCHIDO
                        if(cepIni == "" || cepIni.length < 9){
                            alert("Preencha corretamente o CEP inicial!");
                            document.getElementById('cepIni'+i).focus();
                            return false;
                        }

                        //VERIFICA SE O CEP FINAL FOI PREENCHIDO
                        if(cepFim == "" || cepFim.length < 9){
                            alert("Preencha corretamente o CEP final!");
                            document.getElementById('cepFim'+i).focus();
                            return false;
                        }
                        
                        //VERIFICA SE O CEP INICIAL É MENOR QUE O FINAL
                        cepIni = parseInt(cepIni.replace("-", ""),10);
                        cepFim = parseInt(cepFim.replace("-", ""),10);
                        
                        if(cepIni >= cepFim){
                            alert("O CEP Inicial deve ser menor que o CEP Final!");
                            document.getElementById('cepIni'+i).focus();
                            return false;
                        }
                    }
                }

                for (var i=1; i<=contador; i++) {
                    if(document.getElementById('cepIni'+i)!=null){
                        var cepIni = parseInt(document.getElementById('cepIni'+i).value.replace("-", ""));
                        var cepFim = parseInt(document.getElementById('cepFim'+i).value.replace("-", ""));
                        for (var j=1; j<=contador; j++) {
                            if(j != i && document.getElementById('cepIni'+j) != null){
                                var cepIniAux = parseInt(document.getElementById('cepIni'+j).value.replace("-", ""));
                                var cepFimAux = parseInt(document.getElementById('cepFim'+j).value.replace("-", ""));
                                if( (cepIni >= cepIniAux && cepIni <= cepFimAux) || (cepFim >= cepIniAux && cepFim <= cepFimAux) || (cepIni <= cepIniAux && cepFim >= cepFimAux) ){
                                    alert('Alguma faixa de CEP está se repitindo na tabela!\n\nNão é permitido que a amarração repita faixas de CEP!');
                                    document.getElementById('cepIni'+j).focus();
                                    return false;
                                }
                            }
                        }
                    }
                }

                return true;
            }

            function delRow(linha){
                var nrLinha = document.getElementById('table').rows.length;
                if(nrLinha>2){
                    if (confirm('Tem certeza que deseja excluir?')){
                        var tabela = document.getElementById('table');
                        linha = linha.parentNode.parentNode;
                        id = linha.rowIndex;
                        tabela.deleteRow(id);
                    }else{
                        return false;
                    }
                }else{
                    alert('A tabela deve conter pelo menos uma faixa de CEP!');
                    return false;
                }
            }
            
            function trocaServ(listRemove, listAdiciona){
                var selIndex = document.getElementById(listRemove).selectedIndex;
                var idServ = document.getElementById(listRemove).options[selIndex].value;
                var nomeServ = document.getElementById(listRemove).options[selIndex].text;
                
                if(listAdiciona == 'servico_2'){
                    if(verificarGrupoServ(listAdiciona, idServ)){
                        alert('Este serviço já está na lista do contrato!\n\nPara adicionar, remova o serviço mesmo do contrato!');
                        return false;
                    }
                }

                document.getElementById(listRemove).remove(selIndex);

                var novaOpcao = new Option(nomeServ, idServ);
                document.getElementById(listAdiciona).options[document.getElementById(listAdiciona).length] = novaOpcao;

                OrdenarLista(listRemove);
                OrdenarLista(listAdiciona);
                    
                document.getElementById(listRemove).focus();
                if(document.getElementById(listRemove).length == selIndex){
                    document.getElementById(listRemove).selectedIndex = selIndex-1;                    
                }else{
                    document.getElementById(listRemove).selectedIndex = selIndex;
                }
            }

            function verificarGrupoServ(combo, grupo) {
                var lb = document.getElementById(combo);
                var flag = false;
                for(i=0; i<lb.length; i++)  {
                    var aux = lb.options[i].value.split(";");
                    if(aux[1] == grupo){
                        flag = true;
                    }
                }
                return flag;
            }

            function OrdenarLista(combo) {
                var lb = document.getElementById(combo);
                arrTexts = new Array();

                for(i=0; i<lb.length; i++)  {
                    arrTexts[i] = lb.options[i].text + "@" + lb.options[i].value;
                }

                arrTexts.sort();

                for(i=0; i<lb.length; i++)  {
                    var aux = arrTexts[i].split("@");
                    lb.options[i].text = aux[0];
                    lb.options[i].value = aux[1];
                }
            }
        </script>

        <title>Portal Postal | Nova Amarração</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divInteracao3" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <div id="titulo1">Cadastro de Amarrações</div>
                    <form name="form1" action="../../ServEditarAmarracao" method="post">
                        <ul class="ul_formulario">                            
                            <li class="titulo"><dd>Defina o Nome e a Sigla para esta amarração:</dd></li>
                            <li>
                                <dd>
                                    <label>Descrição</label>
                                    <input type="text" name="nome" value="<%= am.getNomeAmarracao()%>" maxlength="50" size="35" />
                                </dd>
                                <dd>
                                    <label>Sigla</label>
                                    <input type="text" name="sigla" value="<%= am.getSiglaAmarracao()%>" maxlength="3" size="5" />
                                </dd>
                            </li>
                            <li class="titulo"><dd>Selecione os Serviços que fazem parte desta amarração:</dd></li>
                            <li id="divServ" style="clear: both;">
                                <dd>
                                    <label>Serviços da ECT</label>
                                    <select style="width: 220px;" name="servico_1" id="servico_1" size="6">
                                        <%
                                            ArrayList<String> lista = ContrAmarracaoServico.consultaTodosServicosByIdAmarracao(idAmarracao, nomeBD);
                                            ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicosPorGrupo();
                                            for (int i = 0; i < listaServ.size(); i++) {
                                                ServicoECT sv = listaServ.get(i);
                                                if(!lista.contains(sv.getGrupoServico())){
                                                    out.println("<option value='" + sv.getGrupoServico() + "'>" + sv.getNomeServico() + "</option>");
                                                }
                                            }
                                        %>
                                    </select>
                                    <div id="foo"></div>
                                </dd>
                                <dd>
                                    <div class="buttons2" style="margin: 15px 10px;">
                                        <button onclick="trocaServ('servico_1', 'servico_2');" type="button" class="positive" style="width: 120px;" ><img class="link_img" src="../../imagensNew/plus_circle.png" /> ADICIONAR</button>
                                        <br/><br/>
                                        <button onclick="trocaServ('servico_2', 'servico_1');" type="button" class="negative" style="width: 120px;" ><img class="link_img" src="../../imagensNew/minus_circle.png" /> REMOVER</button>
                                    </div>
                                </dd>
                                <dd>
                                    <label>SERVIÇOS QUE ESTÃO NA AMARRAÇÃO</label>
                                    <select style="width: 220px;" name="servico_2" id="servico_2" size="6">
                                        <%
                                            for (int i = 0; i < listaServ.size(); i++) {
                                                ServicoECT sv = listaServ.get(i);
                                                if(lista.contains(sv.getGrupoServico())){
                                                    out.println("<option value='" + sv.getGrupoServico() + "'>" + sv.getNomeServico() + "</option>");
                                                }
                                            }
                                        %>
                                    </select>
                                </dd>
                            </li>
                            <li class="titulo"><dd>Defina as faixas de CEP relacionadas a esta amarração:</dd></li>
                            <li>
                                <dd>
                                    <span style="color:red; font-weight: bold;">Atenção!</span><br/><br/>
                                    <b>1.</b> As faixas de CEP digitadas não podem repetir e nem coincidir com a de outra amarração de mesmo serviço!<br/>
                                    <%--b>2.</b> Para conferir se as faixas coincidem com outra amarração clique em <i>"Conferir"</i> antes de clicar em <i>"Salvar"</i>!<br/--%>
                                    <b>2.</b> Caso clicar em <i>"Salvar"</i> sem conferir as faixas e alguma faixa coincidir com a de outra amarração esta faixa não será inserida!
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <div class="buttons" style="margin:20px 0 20px 0;">
                                        <input type="hidden" name="contador" id="contador" value="<%= listaCob.size()%>">
                                        <input type="hidden" name="idAmarracao" id="idAmarracao" value="<%= idAmarracao %>">
                                        <a class="regular" onclick="addRow();"><img src="../../imagensNew/plus_circle.png"> Adicionar Faixa de CEP</a>
                                    </div>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <table style="border:1px solid silver;" id="table" name="table">
                                        <tr style="background-color:#e7e7e7;">
                                            <th colspan="2"><b>CEP Inicial</b></th>
                                            <th colspan="2"><b>CEP Final</b></th>
                                            <th width="65"><b>Remover</b></th>
                                            <%--<th width="65"><b>Conferir</b></th>--%>
                                        </tr>
                                        <%
                                        for (int i = 0; i < listaCob.size(); i++) {
                                            Entidade.AmarracaoCep cob = listaCob.get(i);
                                            String cepInicial = Util.FormataString.formataCep(cob.getCepInicial()+"");
                                            String cepFinal = Util.FormataString.formataCep(cob.getCepFinal()+"");
                                        %>
                                        <tr class="faixas">
                                            <td>De</td>
                                            <td><input type="input" id="cepIni<%= i + 1%>" name="cepIni<%= i + 1%>" value="<%= cepInicial%>" size="15" maxlength="9" onKeyPress="mascara(this,maskCep)"></td>
                                            <td>Até</td>
                                            <td><input type="input" id="cepFim<%= i + 1%>" name="cepFim<%= i + 1%>" value="<%= cepFinal%>" size="15" maxlength="9" onKeyPress="mascara(this,maskCep)"></td>
                                            <td align="center"><img src='../../imagensNew/cross_circle.png' style='cursor:pointer;' onclick='delRow(this);'></td>
                                            <%--<td align="center"><img src="../../imagensNew/lupa.png" style='cursor:pointer;' onclick="conferirFaixaCep(<%= i + 1%>, <%= idAmarracao %>);"></td>--%>
                                        </tr>
                                        <%}%>
                                    </table>
                                </dd>
                            </li>                            
                            <li class="titulo"><dd></dd></li>
                            <li>
                                <dd style="width: 100%; text-align: center;">
                                    <div class="buttons">
                                        <input type="hidden" name="servicos" id="servicos" />
                                        <button type="submit" name="save" id="sub" class="positive" onclick="return validateRow();"><img src="../../imagensNew/tick_circle.png" alt=""/> SALVAR AMARRAÇÃO</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
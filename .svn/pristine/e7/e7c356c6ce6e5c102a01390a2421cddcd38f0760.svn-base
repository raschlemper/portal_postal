
<%@page import="Entidade.ServicoAbrangencia"%>
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

        ArrayList<ServicoAbrangencia> listaEsedex = Controle.ContrServicoAbrangencia.consultaTodosByServico("ESEDEX", nomeBD);
        ArrayList<ServicoAbrangencia> listaSedex10 = Controle.ContrServicoAbrangencia.consultaTodosByServico("SEDEX10", nomeBD);
        ArrayList<ServicoAbrangencia> listaSedex12 = Controle.ContrServicoAbrangencia.consultaTodosByServico("SEDEX12", nomeBD);
        ArrayList<ServicoAbrangencia> listaPax = Controle.ContrServicoAbrangencia.consultaTodosByServico("PAX", nomeBD);
        
        int sus_esedex = 0;
        if(listaEsedex != null && listaEsedex.size()>0){
            sus_esedex = listaEsedex.get(0).getServico_suspenso();
        }
        int sus_sedex10 = 0;
        if(listaSedex10 != null && listaSedex10.size()>0){
            sus_sedex10 = listaSedex10.get(0).getServico_suspenso();
        }
        int sus_sedex12 = 0;
        if(listaSedex12 != null && listaSedex12.size()>0){
            sus_sedex12 = listaSedex12.get(0).getServico_suspenso();
        }
        int sus_pax = 0;
        if(listaPax != null && listaPax.size()>0){
            sus_pax = listaPax.get(0).getServico_suspenso();
        }
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
        
        <style>
            .faixas input{  border: 1px solid #aaa; height: 16px; font-size: 12px;}
            .faixas td{border-right: 1px solid silver;border-bottom: 1px solid silver;padding: 2px;}            
            .faixas th{border-right: 1px solid silver;border-bottom: 1px solid silver;padding: 5px 2px;background-color:#e7e7e7;}            
        </style>
        
        <script type="text/javascript">

            function validateRow(){
                
                var e_contador = document.getElementById('e_contador').value;                
                //PERCORRE TODOS OS CAMPOS DA TABELA DE PESOS E PRECOS
                for (var i=1; i<=e_contador; i++) {
                    if(document.getElementById('e_cepIni'+i)!=null){
                        var e_cepIni = document.getElementById('e_cepIni'+i).value;
                        var e_cepFim = document.getElementById('e_cepFim'+i).value;

                        //VERIFICA SE O CEP INICIAL FOI PREENCHIDO
                        if(e_cepIni == "" || e_cepIni.length < 9){
                            alert("Preencha corretamente o CEP inicial!");
                            document.getElementById('e_cepIni'+i).focus();
                            return false;
                        }

                        //VERIFICA SE O CEP FINAL FOI PREENCHIDO
                        if(e_cepFim == "" || e_cepFim.length < 9){
                            alert("Preencha corretamente o CEP final!");
                            document.getElementById('e_cepFim'+i).focus();
                            return false;
                        }
                        
                        //VERIFICA SE O CEP INICIAL É MENOR QUE O FINAL
                        e_cepIni = parseInt(e_cepIni.replace("-", ""),10);
                        e_cepFim = parseInt(e_cepFim.replace("-", ""),10);
                        
                        if(e_cepIni >= e_cepFim){
                            alert("O CEP Inicial deve ser menor que o CEP Final!");
                            document.getElementById('e_cepIni'+i).focus();
                            return false;
                        }
                    }
                }

                for (var i=1; i<=e_contador; i++) {
                    if(document.getElementById('e_cepIni'+i)!=null){
                        var e_cepIni = parseInt(document.getElementById('e_cepIni'+i).value.replace("-", ""));
                        var e_cepFim = parseInt(document.getElementById('e_cepFim'+i).value.replace("-", ""));
                        for (var j=1; j<=e_contador; j++) {
                            if(j != i && document.getElementById('e_cepIni'+j) != null){
                                var e_cepIniAux = parseInt(document.getElementById('e_cepIni'+j).value.replace("-", ""));
                                var e_cepFimAux = parseInt(document.getElementById('e_cepFim'+j).value.replace("-", ""));
                                if( (e_cepIni >= e_cepIniAux && e_cepIni <= e_cepFimAux) || (e_cepFim >= e_cepIniAux && e_cepFim <= e_cepFimAux) || (e_cepIni <= e_cepIniAux && e_cepFim >= e_cepFimAux) ){
                                    alert('Alguma faixa de CEP está se repitindo na tabela!\n\nNão é permitido que a amarração repita faixas de CEP!');
                                    document.getElementById('e_cepIni'+j).focus();
                                    return false;
                                }
                            }
                        }
                    }
                }
                
                
                var contador = document.getElementById('contador').value;
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
                
                abrirTelaEspera();
                return true;
            }
            
            function addRow(){
                var nrLinha = document.getElementById('table').rows.length;
                var linha = document.getElementById('table').insertRow(nrLinha);
                var cont = document.getElementById('contador').value;
                var newCont = parseInt(cont) + 1;

                linha.insertCell(0).appendChild(document.createTextNode("De"));
                linha.insertCell(1).innerHTML = "<input type='input' id='cepIni"+newCont+"' name='cepIni"+newCont+"' size='8' maxlength='9' onKeyPress='mascara(this,maskCep)' />";
                linha.insertCell(2).appendChild(document.createTextNode("Até"));
                linha.insertCell(3).innerHTML = "<input type='input' id='cepFim"+newCont+"' name='cepFim"+newCont+"' size='8' maxlength='9' onKeyPress='mascara(this,maskCep)' />"
                                                + "<input type='hidden' id='suspenso_"+newCont+"' name='suspenso_"+newCont+"' value='0' />";
                var cell4 = linha.insertCell(4)
                cell4.innerHTML = "<img src='../../imagensNew/cancel.png' style='cursor:pointer;' onclick='delRow(this);' />";
                cell4.align = "center";
                var cell5 = linha.insertCell(5)
                cell5.innerHTML = "<img src='../../imagensNew/pause.png' style='cursor:pointer;' id='img_sus_"+newCont+"' onclick='suspender(\"SEDEX10\", "+newCont+");' />";
                cell5.align = "center";

                linha.className = "faixas";

                document.getElementById('contador').value = newCont;
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
            
            function e_addRow(){
                var nrLinha = document.getElementById('e_table').rows.length;
                var linha = document.getElementById('e_table').insertRow(nrLinha);
                var cont = document.getElementById('e_contador').value;
                var newCont = parseInt(cont) + 1;

                linha.insertCell(0).appendChild(document.createTextNode("De"));
                linha.insertCell(1).innerHTML = "<input type='input' id='e_cepIni"+newCont+"' name='e_cepIni"+newCont+"' size='8' maxlength='9' onKeyPress='mascara(this,maskCep)' />";
                linha.insertCell(2).appendChild(document.createTextNode("Até"));
                linha.insertCell(3).innerHTML = "<input type='input' id='e_cepFim"+newCont+"' name='e_cepFim"+newCont+"' size='8' maxlength='9' onKeyPress='mascara(this,maskCep)' />"
                                                + "<input type='hidden' id='e_suspenso_"+newCont+"' name='e_suspenso_"+newCont+"' value='0' />";
                var cell4 = linha.insertCell(4)
                cell4.innerHTML = "<img src='../../imagensNew/cancel.png' style='cursor:pointer;' onclick='e_delRow(this);' />";
                cell4.align = "center";
                var cell5 = linha.insertCell(5)
                cell5.innerHTML = "<img src='../../imagensNew/pause.png' style='cursor:pointer;' id='e_img_sus_"+newCont+"' onclick='suspender(\"ESEDEX\", "+newCont+");' />";
                cell5.align = "center";

                linha.className = "faixas";

                document.getElementById('e_contador').value = newCont;
            }

            function e_delRow(linha){
                var nrLinha = document.getElementById('e_table').rows.length;
                if(nrLinha>2){
                    if (confirm('Tem certeza que deseja excluir?')){
                        var tabela = document.getElementById('e_table');
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
            
            function d_addRow(){
                var nrLinha = document.getElementById('d_table').rows.length;
                var linha = document.getElementById('d_table').insertRow(nrLinha);
                var cont = document.getElementById('d_contador').value;
                var newCont = parseInt(cont) + 1;

                linha.insertCell(0).appendChild(document.createTextNode("De"));
                linha.insertCell(1).innerHTML = "<input type='input' id='d_cepIni"+newCont+"' name='d_cepIni"+newCont+"' size='8' maxlength='9' onKeyPress='mascara(this,maskCep)' />";
                linha.insertCell(2).appendChild(document.createTextNode("Até"));
                linha.insertCell(3).innerHTML = "<input type='input' id='d_cepFim"+newCont+"' name='d_cepFim"+newCont+"' size='8' maxlength='9' onKeyPress='mascara(this,maskCep)' />"
                                                + "<input type='hidden' id='d_suspenso_"+newCont+"' name='d_suspenso_"+newCont+"' value='0' />";
                var cell4 = linha.insertCell(4)
                cell4.innerHTML = "<img src='../../imagensNew/cancel.png' style='cursor:pointer;' onclick='d_delRow(this);' />";
                cell4.align = "center";
                var cell5 = linha.insertCell(5)
                cell5.innerHTML = "<img src='../../imagensNew/pause.png' style='cursor:pointer;' id='d_img_sus_"+newCont+"' onclick='suspender(\"SEDEX12\", "+newCont+");' />";
                cell5.align = "center";

                linha.className = "faixas";

                document.getElementById('d_contador').value = newCont;
            }

            function d_delRow(linha){
                var nrLinha = document.getElementById('d_table').rows.length;
                if(nrLinha>2){
                    if (confirm('Tem certeza que deseja excluir?')){
                        var tabela = document.getElementById('d_table');
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
            
            
            function pax_addRow(){
                var nrLinha = document.getElementById('pax_table').rows.length;
                var linha = document.getElementById('pax_table').insertRow(nrLinha);
                var cont = document.getElementById('pax_contador').value;
                var newCont = parseInt(cont) + 1;

                linha.insertCell(0).appendChild(document.createTextNode("De"));
                linha.insertCell(1).innerHTML = "<input type='input' id='pax_cepIni"+newCont+"' name='pax_cepIni"+newCont+"' size='8' maxlength='9' onKeyPress='mascara(this,maskCep)' />";
                linha.insertCell(2).appendChild(document.createTextNode("Até"));
                linha.insertCell(3).innerHTML = "<input type='input' id='pax_cepFim"+newCont+"' name='pax_cepFim"+newCont+"' size='8' maxlength='9' onKeyPress='mascara(this,maskCep)' />"
                                                + "<input type='hidden' id='pax_suspenso_"+newCont+"' name='pax_suspenso_"+newCont+"' value='0' />";
                var cell4 = linha.insertCell(4)
                cell4.innerHTML = "<img src='../../imagensNew/cancel.png' style='cursor:pointer;' onclick='pax_delRow(this);' />";
                cell4.align = "center";
                var cell5 = linha.insertCell(5)
                cell5.innerHTML = "<img src='../../imagensNew/pause.png' style='cursor:pointer;' id='pax_img_sus_"+newCont+"' onclick='suspender(\"PAX\", "+newCont+");' />";
                cell5.align = "center";

                linha.className = "faixas";

                document.getElementById('pax_contador').value = newCont;
            }

            function pax_delRow(linha){
                var nrLinha = document.getElementById('pax_table').rows.length;
                if(nrLinha>2){
                    if (confirm('Tem certeza que deseja excluir?')){
                        var tabela = document.getElementById('pax_table');
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
            
            function suspender(serv, id){
                if(serv == 'ESEDEX'){
                    var sus = document.getElementById('e_suspenso_'+id).value;
                    if(sus == 0){
                        document.getElementById('e_img_sus_'+id).src = '../../imagensNew/refresh.png';
                        document.getElementById('e_suspenso_'+id).value = '1';
                    }else{
                        document.getElementById('e_img_sus_'+id).src = '../../imagensNew/pause.png';
                        document.getElementById('e_suspenso_'+id).value = '0';
                    }
                }
                if(serv == 'SEDEX10'){
                    var sus = document.getElementById('suspenso_'+id).value;
                    if(sus == 0){
                        document.getElementById('img_sus_'+id).src = '../../imagensNew/refresh.png';
                        document.getElementById('suspenso_'+id).value = '1';
                    }else{
                        document.getElementById('img_sus_'+id).src = '../../imagensNew/pause.png';
                        document.getElementById('suspenso_'+id).value = '0';
                    }
                }
                if(serv == 'SEDEX12'){
                    var sus = document.getElementById('d_suspenso_'+id).value;
                    if(sus == 0){
                        document.getElementById('d_img_sus_'+id).src = '../../imagensNew/refresh.png';
                        document.getElementById('d_suspenso_'+id).value = '1';
                    }else{
                        document.getElementById('d_img_sus_'+id).src = '../../imagensNew/pause.png';
                        document.getElementById('d_suspenso_'+id).value = '0';
                    }
                }
                if(serv == 'PAX'){
                    var sus = document.getElementById('pax_suspenso_'+id).value;
                    if(sus == 0){
                        document.getElementById('pax_img_sus_'+id).src = '../../imagensNew/refresh.png';
                        document.getElementById('pax_suspenso_'+id).value = '1';
                    }else{
                        document.getElementById('pax_img_sus_'+id).src = '../../imagensNew/pause.png';
                        document.getElementById('pax_suspenso_'+id).value = '0';
                    }
                }
            }
            
            function suspender_serv(serv){
                document.getElementById('sus_servico').value = serv;
                document.getElementById('formSusServico').submit();
            }
        </script>

        <title>Portal Postal | Abrangência de Serviços</title>

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


                    <div id="titulo1">Abrangência de Serviços</div>
                    <form name="form1" action="../../ServServicoAbrangencia" method="post">
                        <table width="100%" border="0" style="background: white;">
                            <tr>
                                <td valign="top" width="25%" style="border-right: 1px solid black;">
                                    <ul class="ul_formulario" style="width: 90%;">                                                         
                                        <li class="titulo"><dd>E-SEDEX:</dd></li>
                                        <li>
                                            <dd>
                                                <img src="../../imagensNew/esedex.png" />
                                            </dd>
                                            <dd>
                                                <%if(sus_esedex == 1){%>
                                                <span style="color:red; font-weight: bold; font-size: 20px;">SUSPENSO !!!</span>
                                                <%}%>
                                            </dd>
                                        </li>
                                        <li>
                                            <dd>
                                                <div class="buttons" style="margin:20px 0 20px 0;">
                                                    <input type="hidden" name="e_contador" id="e_contador" value="<%= listaEsedex.size()%>" />
                                                    <%if(sus_esedex == 0){%>
                                                    <a class="negative" onclick="suspender_serv('ESEDEX');"><img src="../../imagensNew/pause.png" /> Suspender Serviço</a>
                                                    <%}else{%>
                                                    <a class="positive" onclick="suspender_serv('ESEDEX');"><img src="../../imagensNew/refresh.png" /> Reativar Serviço</a>
                                                    <%}%><br/><br/><br/>
                                                    <a class="regular" onclick="e_addRow();"><img src="../../imagensNew/plus_circle.png" /> Adicionar Faixa de CEP</a>
                                                </div>
                                            </dd>
                                        </li>
                                        <li>
                                            <dd>
                                                <table style="margin: 0 auto; border-left:1px solid silver;border-top:1px solid silver;" id="e_table" name="e_table" cellspacing="0" cellpadding="2">
                                                    <tr class="faixas">
                                                        <th align="center" colspan="2"><b>CEP Inicial</b></th>
                                                        <th align="center" colspan="2"><b>CEP Final</b></th>
                                                        <th align="center" width="65"><b>REM.</b></th>
                                                        <th align="center" width="65"><b>SUS.</b></th>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < listaEsedex.size(); i++) {
                                                            ServicoAbrangencia cob = listaEsedex.get(i);
                                                            String cepInicial = Util.FormataString.formataCep(cob.getCep_inicial() + "");
                                                            String cepFinal = Util.FormataString.formataCep(cob.getCep_final() + "");
                                                            int fs = cob.getFaixa_suspensa();                                                            
                                                            String img = "pause.png";
                                                            if(fs==1){
                                                                img = "refresh.png";
                                                            }
                                                            sus_esedex = cob.getServico_suspenso();
                                                    %>
                                                    <tr <%if(fs==1){%>style="background: khaki;"<%}%> class="faixas">
                                                        <td>De</td>
                                                        <td><input type="input" id="e_cepIni<%= i + 1%>" name="e_cepIni<%= i + 1%>" value="<%= cepInicial%>" size="8" maxlength="9" onKeyPress="mascara(this,maskCep)" /></td>
                                                        <td>Até</td>
                                                        <td>
                                                            <input type="input" id="e_cepFim<%= i + 1%>" name="e_cepFim<%= i + 1%>" value="<%= cepFinal%>" size="8" maxlength="9" onKeyPress="mascara(this,maskCep)" />
                                                            <input type="hidden" name="e_suspenso_<%= i + 1%>" id="e_suspenso_<%= i + 1%>" value="<%= fs %>" />
                                                        </td>
                                                        <td align="center"><img src='../../imagensNew/cancel.png' style='cursor:pointer;' onclick='e_delRow(this);' /></td>
                                                        <td align="center"><img src='../../imagensNew/<%=img%>' style='cursor:pointer;' id="e_img_sus_<%= i+1 %>" onclick='suspender("ESEDEX", <%= i+1 %>);' /></td>
                                                    </tr>
                                                    <%}%>
                                                </table>
                                            </dd>
                                        </li>        
                                    </ul>
                                </td>
                                <td valign="top" width="25%" style="border-right: 1px solid black;">
                                    <ul class="ul_formulario" style="width: 90%;">                                                        
                                        <li class="titulo"><dd>SEDEX 10:</dd></li>
                                        <li>
                                            <dd>
                                                <img src="../../imagensNew/sedex10.png" height="" />
                                            </dd>
                                            <dd>
                                                <%if(sus_sedex10 == 1){%>
                                                <span style="color:red; font-weight: bold; font-size: 20px;">SUSPENSO !!!</span>
                                                <%}%>
                                            </dd>
                                        </li>
                                        <li>
                                            <dd>
                                                <div class="buttons" style="margin:20px 0 20px 0;">
                                                    <input type="hidden" name="contador" id="contador" value="<%= listaSedex10.size()%>" />
                                                    <%if(sus_sedex10 == 0){%>
                                                    <a class="negative" onclick="suspender_serv('SEDEX10');"><img src="../../imagensNew/pause.png" /> Suspender Serviço</a>
                                                    <%}else{%>
                                                    <a class="positive" onclick="suspender_serv('SEDEX10');"><img src="../../imagensNew/refresh.png" /> Reativar Serviço</a>
                                                    <%}%><br/><br/><br/>
                                                    <a class="regular" onclick="addRow();"><img src="../../imagensNew/plus_circle.png" /> Adicionar Faixa de CEP</a>
                                                </div>
                                            </dd>
                                        </li>
                                        <li>
                                            <dd>
                                                <table style="margin: 0 auto; border-left:1px solid silver;border-top:1px solid silver;" id="table" name="table" cellspacing="0" cellpadding="2">
                                                    <tr class="faixas">
                                                        <th align="center" colspan="2"><b>CEP Inicial</b></th>
                                                        <th align="center" colspan="2"><b>CEP Final</b></th>
                                                        <th align="center" width="65"><b>REM.</b></th>
                                                        <th align="center" width="65"><b>SUS.</b></th>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < listaSedex10.size(); i++) {
                                                            ServicoAbrangencia cob = listaSedex10.get(i);
                                                            String cepInicial = Util.FormataString.formataCep(cob.getCep_inicial() + "");
                                                            String cepFinal = Util.FormataString.formataCep(cob.getCep_final() + "");
                                                            int fs = cob.getFaixa_suspensa();                                                            
                                                            String img = "pause.png";
                                                            if(fs==1){
                                                                img = "refresh.png";
                                                            }
                                                            sus_sedex10 = cob.getServico_suspenso();
                                                    %>
                                                    <tr <%if(fs==1){%>style="background: khaki;"<%}%> class="faixas">
                                                        <td>De</td>
                                                        <td><input type="input" id="cepIni<%= i + 1%>" name="cepIni<%= i + 1%>" value="<%= cepInicial%>" size="8" maxlength="9" onKeyPress="mascara(this,maskCep)" /></td>
                                                        <td>Até</td>
                                                        <td>                                                            
                                                            <input type="input" id="cepFim<%= i + 1%>" name="cepFim<%= i + 1%>" value="<%= cepFinal%>" size="8" maxlength="9" onKeyPress="mascara(this,maskCep)" />
                                                            <input type="hidden" name="suspenso_<%= i + 1%>" id="suspenso_<%= i + 1%>" value="<%= fs %>" />
                                                        </td>
                                                        <td align="center"><img src='../../imagensNew/cancel.png' style='cursor:pointer;' onclick='delRow(this);' /></td>
                                                        <td align="center"><img src='../../imagensNew/<%=img%>' style='cursor:pointer;' id="img_sus_<%= i+1 %>" onclick='suspender("SEDEX10", <%= i+1 %>);' /></td>
                                                    </tr>
                                                    <%}%>
                                                </table>
                                            </dd>
                                        </li>        
                                    </ul>
                                </td>
                                <td valign="top" width="25%" style="border-right: 1px solid black;">
                                    <ul class="ul_formulario" style="width: 90%;">                                                        
                                        <li class="titulo"><dd>SEDEX 12:</dd></li>
                                        <li>
                                            <dd>
                                                <img src="../../imagensNew/sedex12.png" height="" />
                                            </dd>
                                            <dd>
                                                <%if(sus_sedex12 == 1){%>
                                                <span style="color:red; font-weight: bold; font-size: 20px;">SUSPENSO !!!</span>
                                                <%}%>
                                            </dd>
                                        </li>
                                        <li>
                                            <dd>
                                                <div class="buttons" style="margin:20px 0 20px 0;">
                                                    <input type="hidden" name="d_contador" id="d_contador" value="<%= listaSedex12.size()%>" />
                                                    <%if(sus_sedex12 == 0){%>
                                                    <a class="negative" onclick="suspender_serv('SEDEX12');"><img src="../../imagensNew/pause.png" /> Suspender Serviço</a>
                                                    <%}else{%>
                                                    <a class="positive" onclick="suspender_serv('SEDEX12');"><img src="../../imagensNew/refresh.png" /> Reativar Serviço</a>
                                                    <%}%><br/><br/><br/>
                                                    <a class="regular" onclick="d_addRow();"><img src="../../imagensNew/plus_circle.png" /> Adicionar Faixa de CEP</a>
                                                </div>
                                            </dd>
                                        </li>
                                        <li>
                                            <dd>
                                                <table style="margin: 0 auto; border-left:1px solid silver;border-top:1px solid silver;" id="d_table" name="d_table" cellspacing="0" cellpadding="2">
                                                    <tr class="faixas">
                                                        <th align="center" colspan="2"><b>CEP Inicial</b></th>
                                                        <th align="center" colspan="2"><b>CEP Final</b></th>
                                                        <th align="center" width="65"><b>REM.</b></th>
                                                        <th align="center" width="65"><b>SUS.</b></th>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < listaSedex12.size(); i++) {
                                                            ServicoAbrangencia cob = listaSedex12.get(i);
                                                            String cepInicial = Util.FormataString.formataCep(cob.getCep_inicial() + "");
                                                            String cepFinal = Util.FormataString.formataCep(cob.getCep_final() + "");
                                                            int fs = cob.getFaixa_suspensa();                                                            
                                                            String img = "pause.png";
                                                            if(fs==1){
                                                                img = "refresh.png";
                                                            }
                                                            sus_sedex12 = cob.getServico_suspenso();
                                                    %>
                                                    <tr <%if(fs==1){%>style="background: khaki;"<%}%> class="faixas">
                                                        <td>De</td>
                                                        <td><input type="input" id="d_cepIni<%= i + 1%>" name="d_cepIni<%= i + 1%>" value="<%= cepInicial%>" size="8" maxlength="9" onKeyPress="mascara(this,maskCep)" /></td>
                                                        <td>Até</td>
                                                        <td>                                                            
                                                            <input type="input" id="d_cepFim<%= i + 1%>" name="d_cepFim<%= i + 1%>" value="<%= cepFinal%>" size="8" maxlength="9" onKeyPress="mascara(this,maskCep)" />
                                                            <input type="hidden" name="d_suspenso_<%= i + 1%>" id="d_suspenso_<%= i + 1%>" value="<%= fs %>" />
                                                        </td>
                                                        <td align="center"><img src='../../imagensNew/cancel.png' style='cursor:pointer;' onclick='d_delRow(this);' /></td>
                                                        <td align="center"><img src='../../imagensNew/<%=img%>' style='cursor:pointer;' id="d_img_sus_<%= i+1 %>" onclick='suspender("SEDEX12", <%= i+1 %>);' /></td>
                                                    </tr>
                                                    <%}%>
                                                </table>
                                            </dd>
                                        </li>        
                                    </ul>
                                </td>
                                                
                                <td valign="top" width="25%">
                                    <ul class="ul_formulario" style="width: 90%;">                                                        
                                        <li class="titulo"><dd>PAC GRANDES FORMATOS:</dd></li>
                                        <li>
                                            <dd>
                                                <img src="../../imagensNew/pax.png" height="" />
                                            </dd>
                                            <dd>
                                                <%if(sus_pax == 1){%>
                                                <span style="color:red; font-weight: bold; font-size: 20px;">SUSPENSO !!!</span>
                                                <%}%>
                                            </dd>
                                        </li>
                                        <li>
                                            <dd>
                                                <div class="buttons" style="margin:20px 0 20px 0;">
                                                    <input type="hidden" name="pax_contador" id="pax_contador" value="<%= listaPax.size()%>" />
                                                    <%if(sus_pax == 0){%>
                                                    <a class="negative" onclick="suspender_serv('PAX');"><img src="../../imagensNew/pause.png" /> Suspender Serviço</a>
                                                    <%}else{%>
                                                    <a class="positive" onclick="suspender_serv('PAX');"><img src="../../imagensNew/refresh.png" /> Reativar Serviço</a>
                                                    <%}%><br/><br/><br/>
                                                    <a class="regular" onclick="pax_addRow();"><img src="../../imagensNew/plus_circle.png" /> Adicionar Faixa de CEP</a>
                                                </div>
                                            </dd>
                                        </li>
                                        <li>
                                            <dd>
                                                <table style="margin: 0 auto; border-left:1px solid silver;border-top:1px solid silver;" id="pax_table" name="pax_table" cellspacing="0" cellpadding="2">
                                                    <tr class="faixas">
                                                        <th align="center" colspan="2"><b>CEP Inicial</b></th>
                                                        <th align="center" colspan="2"><b>CEP Final</b></th>
                                                        <th align="center" width="65"><b>REM.</b></th>
                                                        <th align="center" width="65"><b>SUS.</b></th>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < listaPax.size(); i++) {
                                                            ServicoAbrangencia cob = listaPax.get(i);
                                                            String cepInicial = Util.FormataString.formataCep(cob.getCep_inicial() + "");
                                                            String cepFinal = Util.FormataString.formataCep(cob.getCep_final() + "");
                                                            int fs = cob.getFaixa_suspensa();                                                            
                                                            String img = "pause.png";
                                                            if(fs==1){
                                                                img = "refresh.png";
                                                            }
                                                            sus_pax = cob.getServico_suspenso();
                                                    %>
                                                    <tr <%if(fs==1){%>style="background: khaki;"<%}%> class="faixas">
                                                        <td>De</td>
                                                        <td><input type="input" id="pax_cepIni<%= i + 1%>" name="pax_cepIni<%= i + 1%>" value="<%= cepInicial%>" size="8" maxlength="9" onKeyPress="mascara(this,maskCep)" /></td>
                                                        <td>Até</td>
                                                        <td>                                                            
                                                            <input type="input" id="pax_cepFim<%= i + 1%>" name="pax_cepFim<%= i + 1%>" value="<%= cepFinal%>" size="8" maxlength="9" onKeyPress="mascara(this,maskCep)" />
                                                            <input type="hidden" name="pax_suspenso_<%= i + 1%>" id="pax_suspenso_<%= i + 1%>" value="<%= fs %>" />
                                                        </td>
                                                        <td align="center"><img src='../../imagensNew/cancel.png' style='cursor:pointer;' onclick='pax_delRow(this);' /></td>
                                                        <td align="center"><img src='../../imagensNew/<%=img%>' style='cursor:pointer;' id="pax_img_sus_<%= i+1 %>" onclick='suspender("PAX", <%= i+1 %>);' /></td>
                                                    </tr>
                                                    <%}%>
                                                </table>
                                            </dd>
                                        </li>        
                                    </ul>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="4" align="center" style="border-top: 1px solid silver;">
                                    <div class="buttons">
                                        <button type="submit" name="save" id="sub" class="positive" onclick="return validateRow();"><img src="../../imagensNew/tick_circle.png" alt=""/> SALVAR ABRANGÊNCIA DOS SERVIÇOS</button>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </form>
                    <form action="../../ServSuspenderServico" method="post" name="formSusServico" id="formSusServico">
                        <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD %>" />
                        <input type="hidden" name="sus_servico" id="sus_servico" value="" />
                        <input type="hidden" name="sus_esedex" id="sus_esedex" value="<%= sus_esedex %>" />
                        <input type="hidden" name="sus_sedex10" id="sus_sedex10" value="<%= sus_sedex10 %>" />
                        <input type="hidden" name="sus_sedex12" id="sus_sedex12" value="<%= sus_sedex12 %>" />
                        <input type="hidden" name="sus_pax" id="sus_pax" value="<%= sus_pax %>" />
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
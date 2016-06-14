
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.contrDestinatario"%>
<%@page import="Entidade.Destinatario"%>
<%@page import="Entidade.SenhaCliente"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>

<%
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {

        int idEmp = (Integer) session.getAttribute("idEmpresa");
        int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));
        ArrayList<Integer> dps = (ArrayList<Integer>) session.getAttribute("departamentos");
        String nomeCli = contrCliente.consultaNomeById(idCli, nomeBD);
        int nivel = (Integer) session.getAttribute("nivelUsuarioEmp");
        int idUser = (Integer) session.getAttribute("idUsuarioEmp");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>

        <!-- Menu -->
        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <!-- Menu -->

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->

        <script type="text/javascript">
            function chamaDivProtecao(){
                var classe = document.getElementById("divProtecao").className;
                if(classe == "esconder"){
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao").className = "mostrar";
                }else{
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao").className = "esconder";
                }
            }
                   
            function validaForm(){       
                abrirTelaEspera();
                var form = document.form1;
                if(form.arquivo.value === ""){
                    fecharTelaEspera();
                    alert("Escolha o arquivo a ser importado!");
                    return false;
                }else if($('#tipo_serv option:selected').text() === 'SELECOINE UM SERVICO'){;
                    fecharTelaEspera();
                    alert(" --- Por favor, escolha um serviço para as NF-E ! --- No caso de varios, você poderá alterar-los depois !");
                    return false;
                }else{
                    var indexA = form.arquivo.value.lastIndexOf(".");
                    var indexB = form.arquivo.value.length;
                    var ext = form.arquivo.value.substring(indexA, indexB).toUpperCase();
                    var tipo = document.getElementById('tipo').value;
                    if((tipo === 'CSV' || tipo === 'INTERLOGIC' || tipo === 'TRAY' || tipo === 'LINX') && ext !== ".CSV") {
                        fecharTelaEspera();
                        alert("O arquivo a ser importado deve ser '.CSV' !");
                        return false;
                    } else if((tipo === 'XML' || tipo === 'NFE'|| tipo === 'PLP') && ext !== ".XML") {
                        fecharTelaEspera();
                        alert("O arquivo a ser importado deve ser '.XML' !");
                        return false;
                    } else if((tipo === 'LADOAVESSO' || tipo === 'EDI') && ext !== ".TXT") {                
                        fecharTelaEspera();
                        alert("O arquivo a ser importado deve ser '.XML' ou '.CSV' !");
                        return false;
                    } else if((tipo === 'PS') && ext !== ".XLS") {                
                        fecharTelaEspera();
                        alert("O arquivo a ser importado deve ser '.XLS' !");
                        return false;
                    }
                }
                form.submit();
                return true;
            }
            
            function mudaFileOpts(tipo){
                if(tipo == 'CSV' || tipo == 'INTERLOGIC' || tipo == 'WEBVENDAS'){
                    document.getElementById('arquivo').accept = '.csv';
                    document.getElementById('arquivo').multiple = false;               
                    document.getElementById('dd_vd').className = 'esconder'; 
                    document.getElementById('dd_ar').className = 'esconder'; 
                    document.getElementById('form1').action = '../../ServPreVendaImportar'; 
                    $('#tipo_serv option[value="ARQUIVO"]').text("DEFINIDO NO ARQUIVO");
                }else if (tipo == 'NFE'){ 
                    document.getElementById('arquivo').accept = '.xml'; 
                    document.getElementById('arquivo').multiple = true;                   
                    document.getElementById('dd_vd').className = 'mostrar'; 
                    document.getElementById('dd_ar').className = 'mostrar'; 
                    document.getElementById('form1').action = '../../ServPreVendaImportarNFe';   
                    $('#tipo_serv option[value="ARQUIVO"]').text("SELECOINE UM SERVICO");    
                }else if (tipo == 'TRAY' || tipo == 'LINX'){
                    document.getElementById('arquivo').accept = '.csv'; 
                    document.getElementById('arquivo').multiple = false;                   
                    document.getElementById('dd_vd').className = 'mostrar'; 
                    document.getElementById('dd_ar').className = 'mostrar'; 
                    document.getElementById('form1').action = '../../ServPreVendaImportar';   
                    $('#tipo_serv option[value="ARQUIVO"]').text("DEFINIDO NO ARQUIVO");
                }else if (tipo == 'EDI'){
                    document.getElementById('arquivo').accept = '.txt'; 
                    document.getElementById('arquivo').multiple = false;                   
                    document.getElementById('dd_vd').className = 'mostrar'; 
                    document.getElementById('dd_ar').className = 'mostrar'; 
                    document.getElementById('form1').action = '../../ServPreVendaImportar';  
                    $('#tipo_serv option[value="ARQUIVO"]').text("DEFINIDO NO ARQUIVO");
                }else if (tipo == 'XML' || tipo == 'PLP'){
                    document.getElementById('arquivo').accept = '.xml';
                    document.getElementById('arquivo').multiple = false;               
                    document.getElementById('dd_vd').className = 'esconder'; 
                    document.getElementById('dd_ar').className = 'esconder';  
                    document.getElementById('form1').action = '../../ServPreVendaImportar';     
                    $('#tipo_serv option[value="ARQUIVO"]').text("DEFINIDO NO ARQUIVO");
                }else if (tipo == 'LADOAVESSO'){
                    document.getElementById('arquivo').accept = '.txt'; 
                    document.getElementById('arquivo').multiple = false;               
                    document.getElementById('dd_vd').className = 'esconder';  
                    document.getElementById('dd_ar').className = 'esconder';  
                    document.getElementById('form1').action = '../../ServPreVendaImportar';  
                    $('#tipo_serv option[value="ARQUIVO"]').text("DEFINIDO NO ARQUIVO");
                }else if (tipo == 'PS'){
                    document.getElementById('arquivo').accept = '.xls'; 
                    document.getElementById('arquivo').multiple = false;               
                    document.getElementById('dd_vd').className = 'esconder';  
                    document.getElementById('dd_ar').className = 'esconder';  
                    document.getElementById('form1').action = '../../ServPreVendaImportar';  
                    $('#tipo_serv option[value="ARQUIVO"]').text("DEFINIDO NO ARQUIVO");
                }
            }
        </script>

        <title>Portal Postal | Importação de Postagens</title>

    </head>
    <body onload="javascript:document.getElementById('nome').focus();">
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Importação de Pré-Postagens</div>

                    <form method="post" action="../../ServPreVendaImportar" id="form1" name="form1" accept-charset="ISO-8859-1" enctype="multipart/form-data">
                        <ul class="ul_formulario" style="width: 1140px;">
                            <li class="titulo">
                                <dd style="font-size: 12px;">SELECIONE O ARQUIVO, SERVIÇO E DEPARTAMENTO.</dd>
                            </li>
                            <li>
                              
                                <dd>
                                    <label>TIPO DE IMPORTÇÃO:</label>
                                    <select style="width: 300px;" name="tipo" id="tipo" onchange="mudaFileOpts(this.value);">
                                        <option value="CSV">ARQUIVO .CSV PORTAL POSTAL</option>
                                        <option value="XML">ARQUIVO .XML PORTAL POSTAL</option>
                                        <option value="NFE">ARQUIVO .XML NF-e</option>
                                        <option value="PLP">ARQUIVO .XML PLP</option>
                                        <option value="TRAY">ARQUIVO TRAY COMMERCE</option>
                                        <option value="EDI">ARQUIVO EDI</option>
                                        <option value="LINX">ARQUIVO LINX</option>
                                        <option value="WEBVENDAS">ARQUIVO WEBVENDAS</option>
                                        <option value="PS">ARQUIVO PS SERVICE EXCELL</option>
                                        <option value="PSN">ARQUIVO PS SERVICE NOVO</option>
                                        <%if(idEmp == 236505){%><option value="LADOAVESSO">ARQUIVO LADOAVESSO</option><%}%>
                                        <%if(idEmp == 236505){%><option value="INTERLOGIC">ARQUIVO INTERLOGIC</option><%}%>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>ESCOLHA UM ARQUIVO PARA IMPORTAR:</label>
                                    <input style="width:300px;" type="file" name="arquivo" id="arquivo" accept=".csv"/>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>SERVIÇO:</label>
                                    <select style="width: 300px;" name="servico" id="tipo_serv">
                                       <option value="ARQUIVO">DEFINIDO NO ARQUIVO</option>
                                        <option value="PAC">PAC</option>
                                        <option value="PAX">PAC GRANDES FORMATOS</option>
                                        <option value="SEDEX">SEDEX</option>
                                        <option value="ESEDEX">E-SEDEX</option>
                                        <option value="CARTA">CARTA REGISTRADA</option>
                                        <option value="SIMPLES">CARTA SIMPLES</option>
                                        <option value="SEDEX10">SEDEX 10</option>
                                        <option value="SEDEX12">SEDEX 12</option>
                                    </select>
                                </dd>
                                <dd id="dd_vd" class="esconder">
                                    <label>VALOR DECLAR.?</label>
                                    <select style="width: 100px;" name="vd">
                                        <option value="0">NÃO</option>
                                        <option value="1">SIM</option>
                                    </select>
                                </dd>
                                <dd id="dd_ar" class="esconder">
                                    <label>A.R.?</label>
                                    <select style="width: 100px;" name="ar">
                                        <option value="0">NÃO</option>
                                        <option value="1">SIM</option>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Departamento</label>
                                    <select style="width: 300px;" name="departamento">
                                        <option value="">NENHUM DEPARTAMENTO</option>
                                        <%
                                            ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCli, nomeBD);
                                            for (int i = 0; i < listaDep.size(); i++) {
                                                ClientesDeptos cd = listaDep.get(i);
                                                String cartao = "0";
                                                if (cd.getCartaoPostagem() != null && !cd.getCartaoPostagem().trim().equals("") ) {
                                                    cartao = cd.getCartaoPostagem();
                                                }
                                                if (dps.contains(cd.getIdDepartamento())) {
                                        %>
                                        <option value="<%=cd.getIdDepartamento() + ";" + cd.getNomeDepartamento() + ";" + cartao%>"><%= cd.getNomeDepartamento()%></option>
                                        <%}
                                        }%>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD%>" />
                                        <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                        <input type="hidden" name="idUser" value="<%= idUser%>" />
                                        <button type="button" class="positive" onclick="return validaForm();"><img src="../../imagensNew/tick_circle.png" /> IMPORTAR PRÉ-POSTAGEM</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>

                    <div id="titulo2">Instruções para importação de Arquivo.</div>                    
                    <div style="padding:8px 5px; background: white;">
                        <b>1) Layout de Exemplo do Arquivo CSV para Importação de Postagens:</b><br/><br/>
                            <div style="margin-left: 25px;">
                                <b>Arquivo de importação sem peso:</b><br/>
                                <a href="ARQUIVO_EXEMPLO_PORTALPOSTAL.csv" target="_blank">- Clique aqui para baixar o 'ARQUIVO_EXEMPLO_PORTALPOSTAL.csv'</a><br/>
                                <%--
                                <b>Arquivo de importação com peso e dimensões:</b><br/>
                                <a href="ARQUIVO_EXEMPLO_PORTALPOSTAL_PESO.csv" target="_blank">- Clique aqui para baixar o 'ARQUIVO_EXEMPLO_PORTALPOSTAL_PESO.csv'</a><br/><br/>
                                --%>
                            </div>
                        <b>2) Colunas de preenchimento Obrigatórios:</b><br/><br/>
                        <b style="margin-left: 25px;">NOME, CEP, ENDEREÇO, NUMERO, BAIRRO, CIDADE, UF</b><br/><br/>
                        <b>3) A coluna SERVIÇO deve ser escrito o serviço a ser utilizado para cada objeto (caso já tenha definido).</b><br/><br/>
                        <div style="margin-left: 25px;">
                            <b>ESEDEX</b> = Para Encomenda ESEDEX<br/>
                            <b>SEDEX</b> = Para Encomenda SEDEX.<br/>
                            <%--<b>SEDEX10</b> = Para Encomenda SEDEX 10.<br/>
                            <b>SEDEX12</b> = Para Encomenda SEDEX 12.<br/>--%>
                            <b>PAC</b> = Para Encomenda PAC<br/>
                            <b>PAX</b> = Para Encomenda PAC Grandes Formatos<br/>
                            <b>CARTA</b> = Para Carta Comercial Registrada<br/>
                            <b>SIMPLES</b> = Para Carta Comercial Simples<br/>
                            <b style="color: red;">*Caso a postagem não tenha nenhum serviço pré-definido deixe o campo vazio</b><br/>
                            <b style="color: red;">**Caso o serviço pré-definido seja E-SEDEX, SEDEX 10, SEDEX 12, e a região de destino não abrangir o serviço, o mesmo será alterado automaticamente para SEDEX.</b>
                        </div><br/>
                        <b>4) A coluna SERV_ADICIONAIS deve ser preenchida somente com as siglas dos seguintes serviços adicionais (caso a postagem possua):</b><br/><br/>
                        <div style="padding-left: 25px;">
                            <b>AR</b> = Aviso de Recebimento<br/>
                            <b>MP</b> = Mão Própria<br/>
                            <b>VD</b> = Valor Declarado (Caso tenha VD, a coluna VALOR_DECLARADO deve ser preenchida com o valor da nota)<br/>
                            <b style="color: red;">*Caso a postagem não tenha nenhum serviço adicional deixe o campo vazio</b>
                        </div><br/><br/>
                    </div>


                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
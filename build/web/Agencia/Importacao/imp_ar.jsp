
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<%
            if (session.getAttribute("usuario") == null) {
                response.sendRedirect("../index.jsp?msgLog=3");
            } else {

                int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
                if (idNivelDoUsuario > 2) {
                    response.sendRedirect("../jsp/imp_movimento.jsp?msg=Acesso Negado!");
                }

                int idUsuario = (Integer) session.getAttribute("idUsuario");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dataAtual = new Date();
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(dataAtual);
                cal.set(cal.YEAR - 1, cal.MONTH + 1, cal.DAY_OF_MONTH);
                Date dataAntes = cal.getTime();

                String nomeBD = (String) session.getAttribute("empresa");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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

        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]><link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" /><![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript" charset="utf-8">

            $(function() {
                $( "#data3" ).datepicker({
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
                $( "#data4" ).datepicker({
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });


            function validaForm(){
                var form = document.form1;
                if(form.data3.value == ""){
                    alert("Preencha a Data Inicial do Periodo!");
                    return false;
                }else{
                    if(valida_data(form.data3) == false){
                        return false;
                    }
                }

                if(form.data4.value == ""){
                    alert("Preencha a Data Final do Periodo!");
                    return false;
                }else{
                    if(valida_data(form.data4) == false){
                        return false;
                    }
                }

                //VERIFICA A DIFERENÇA DE DIAS ENTRE AS DATAS!
                var dataIni = form.data3.value;
                var dataFim = form.data4.value;
                var aux1 = dataIni.split("/");
                var aux2 = dataFim.split("/");

                var dia1 = aux1[0];
                var mes1 = aux1[1];
                mes1 = Math.floor(mes1)-1;
                var ano1 = aux1[2];
                var data1 = new Date(ano1,mes1,dia1);

                var dia2 = aux2[0];
                var mes2 = aux2[1];
                mes2 = Math.floor(mes2)-1;
                var ano2 = aux2[2];
                var data2 = new Date(ano2,mes2,dia2);

                var diferenca = data2.getTime() - data1.getTime();
                diferenca = Math.floor(diferenca / (1000 * 60 * 60 * 24));

                if(data1<=data2){
                    if(diferenca > 31){
                        alert("Periodo máximo de importação por vez é de 31 dias!")
                        return false;
                    }
                }else{
                    alert("A data inicial deve ser menor ou igual a data final!");
                    return false;
                }


                if(form.arquivo.value == ""){
                    alert("Escolha o arquivo de AR's a ser importado!\nGeralmente encontrado em 'C:/avreceb.txt'.");
                    return false;
                }else{
                    var indexA = form.arquivo.value.lastIndexOf(".");
                    var indexB = form.arquivo.value.length;
                    var ext = form.arquivo.value.substring(indexA, indexB).toUpperCase();
                    if(ext != ".TXT"){
                        alert("O arquivo a ser importado deve ser '.TXT' !");
                        return false;
                    }
                }

                form.submit();
            }
        </script>
        <title>Portal Postal | Importação de Retorno A.R</title>
    </head>
    <body>
        <div id="divInteracao" class="esconder" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <div id="titulo1">Importar Retorno de ARs - Compátivel com a versão 28/10/2009a do SECT.</div>

                    <form method="post" action="../../ServImportacaoAr" id="form1" name="form1" enctype="multipart/form-data">
                        <ul class="ul_formulario">
                            <li>
                                <dd>
                                    <label>Período da Importação</label>
                                    <input type="hidden" id="nada" name="nada" value="nada" />
                                    <input type="hidden" id="idUsuario" name="idUsuario" value="<%= idUsuario%>" />
                                    <input type="text" id="data3" name="data3" class="date-pick" value="" size="12" maxlength="10" onKeyPress="mascara(this,maskData)" />
                                    até
                                    <input type="text" id="data4" name="data4" size="12" class="date-pick" value=""  maxlength="10" onKeyPress="mascara(this,maskData)" />

                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Escolha o Arquivo de AR</label>
                                    <input type="file" name="arquivo" />
                                    <input type="hidden" id="tipoForm" name="tipoForm" value="imagem" />
                                </dd>
                            </li>
                            <li>
                                <dd style="color:red;">
                                    *Após gerado no SECT é encontrado em "C:/avrecebe.TXT". Para alterar este caminho, no SECT vá em 'Arquivos>>Exportação>>Destino dos Arquivos'<br/>
                                    *O arquivo não pode ser maior que 2MB
                                    <div class="buttons">
                                        <button type="button" class="positive" onclick="return validaForm();"><img src="../../imagensNew/download2.png"/> IMPORTAR RECEBIMENTO DE A.R.</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>



                    <div id="titulo2">Histórico das importações de ar</div>
                    <table id="barraAtendimento" border="0">
                        <tr>
                            <td align="left" style="font-weight:bold;font-size:12px;">
                                Pesquisa Rápida:
                                <select style='min-width:150px;' id="columns2" onchange="sorter2.search('query2')"></select>
                                <input type="text" id="query2" onkeyup="sorter2.search('query2')" placeholder="Digite aqui a sua pesquisa..." />
                                <a style="text-decoration:none;font-weight:bold;font-size:11px;" href="javascript:document.getElementById('query2').value='';sorter2.reset()">RESTAURAR PADRÕES</a>
                            </td>
                            <td align="right">
                                <div class="details" style="clear:both;">
                                    <div>Resultado <span id="startrecord2"></span>-<span id="endrecord2"></span> de <span id="totalrecords2"></span></div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                        <thead>
                            <tr>
                                <th><h3>Usuário que Importou</h3></th>
                                <th><h3>Data da Importação</h3></th>
                                <th><h3>Período das Postagens</h3></th>
                                <th><h3>QTD. de Clientes Importados</h3></th>
                                <th><h3>QTD. de AR's Importados</h3></th>
                                <th><h3>QTD. de AR's Excluidos</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                            ArrayList listaHistImport2 = Controle.contrBaixaAr.consultaHistoricoImportAr(nomeBD);
                                            for (int j = 0; j < listaHistImport2.size(); j++) {
                                                Entidade.HistoricoImport hst = (Entidade.HistoricoImport) listaHistImport2.get(j);
                                                String vDataIni = "", vDataFim = "", vDataImportacao = "";
                                                Date dataIni = hst.getDataInicio();
                                                if (dataIni != null) {
                                                    vDataIni = sdf.format(dataIni);
                                                }
                                                Date dataFim = hst.getDataFim();
                                                if (dataFim != null) {
                                                    vDataFim = sdf.format(dataFim);
                                                }
                                                Date dataImportacao = hst.getDataImportacao();
                                                if (dataImportacao != null) {
                                                    vDataImportacao = sdf2.format(dataImportacao);
                                                }
                                                int tamanho = hst.getTamanho();
                                                int qtdClientes = hst.getQtdCliente();
                                                int qtdExcluido = hst.getQtdExcluido();
                                                int idUsuarioRealizou = hst.getIdUsuario();
                                                String nomeUsuario = Controle.contrUsuario.consultaNomeUsuarioById(idUsuarioRealizou, nomeBD);
                            %>
                            <tr>
                                <td><%= nomeUsuario%></td>
                                <td><%= vDataImportacao%></td>
                                <td><%= vDataIni%> - <%= vDataFim%></td>
                                <td><%= qtdClientes%></td>
                                <td><%= tamanho%></td>
                                <td><%= qtdExcluido%></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                    <div id="tablefooter" align='center'>
                        <div align="left" style='float:left; width:20%;'>
                            <select onchange="sorter2.size(this.value)">
                                <option value="5">5</option>
                                <option value="10" selected="selected">10</option>
                                <option value="20">20</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                            </select>
                            <span>Linhas por Página</span>
                        </div>
                        <div id="tablenav2" class="tablenav">
                            <div>
                                <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1,true)" />
                                <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1,true)" />
                                <select style="margin-left:5px;" id="pagedropdown2"></select>
                                <a style="margin-left:10px;" href="javascript:sorter2.showall()">Ver Tudo</a>
                            </div>
                        </div>
                        <div id="tablelocation">
                            <div class="page">Página <span id="currentpage2"></span> de <span id="totalpages2"></span></div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        var sorter2 = new TINY.table.sorter('sorter2','table2',{
                            headclass:'head',
                            ascclass:'asc',
                            descclass:'desc',
                            evenclass:'evenrow',
                            oddclass:'oddrow',
                            evenselclass:'evenselected',
                            oddselclass:'oddselected',
                            paginate:true,
                            size:10,
                            colddid:'columns2',
                            currentid:'currentpage2',
                            totalid:'totalpages2',
                            startingrecid:'startrecord2',
                            endingrecid:'endrecord2',
                            totalrecid:'totalrecords2',
                            hoverid:'selectedrowPointer',
                            pageddid:'pagedropdown2',
                            navid:'tablenav2',
                            sortcolumn:1,
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

<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<%
            if (session.getAttribute("emp") == null) {
                response.sendRedirect("../../index.jsp?msgLog=3");
            } else {

                int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
                int idUsuario = (Integer) session.getAttribute("idUsuario");
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date dataAtual = new Date();
                GregorianCalendar cal = new GregorianCalendar();
                cal.setTime(dataAtual);
                cal.set(cal.YEAR - 1, cal.MONTH + 1, cal.DAY_OF_MONTH);
                //Date dataAntes = cal.getTime();

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
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript" charset="utf-8">

            $(function() {
                $( "#data" ).datepicker({
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
                $( "#data2" ).datepicker({
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });

            function validaForm(){
                var form = document.form1;
                if(form.data.value == ""){
                    alert("Preencha a Data Inicial do Periodo!");
                    return false;
                }else{
                    if(valida_data(form.data) == false){
                        return false;
                    }
                }

                if(form.data2.value == ""){
                    alert("Preencha a Data Final do Periodo!");
                    return false;
                }else{
                    if(valida_data(form.data2) == false){
                        return false;
                    }
                }        

                //VERIFICA A DIFERENÇA DE DIAS ENTRE AS DATAS!
                var dataIni = form.data.value;
                var dataFim = form.data2.value;
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
                    alert("Escolha o arquivo .NET do movimento a ser importado!");
                    return false;
                }else{
                    var indexA = form.arquivo.value.lastIndexOf(".");
                    var indexB = form.arquivo.value.length;
                    var ext = form.arquivo.value.substring(indexA, indexB).toUpperCase();
                    if(ext != ".NET"){
                        alert("O arquivo a ser importado deve ser '.NET' !");
                        return false;
                    }
                }

                form.submit();
            }
        </script>
        <title>Portal Postal | Importação de Movimento</title>
    </head>
    <body>
        <div id="divInteracao" class="esconder" align="center"></div>
        <div id="divProtecao" class="esconder"></div>
        <%@ include file="../../Includes/menu_agencia.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">



                    <div id="titulo1">Importar Movimentação Visual - Arquivo .NET</div>
                    <form method="post" action="../../ServImportacaoMovVisual" id="form1" name="form1" enctype="multipart/form-data">
                        <ul class="ul_formulario">
                            <li>
                                <dd>
                                    <label>Periodo da Importação</label>
                                    <input type="hidden" id="nada" name="nada" value="nada" />
                                    <input type="hidden" id="idUsuario" name="idUsuario" value="<%= idUsuario%>" />
                                    <input type="text" id="data" name="data" class="date-pick" value="" size="12" maxlength="10" onKeyPress="mascara(this,maskData)" />
                                    até
                                    <input type="text" id="data2" name="data2" size="12" class="date-pick" value=""  maxlength="10" onKeyPress="mascara(this,maskData)" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Escolha o Arquivo de Movimentação</label>
                                    <input type="file" name="arquivo" />
                                    <input type="hidden" id="tipoForm" name="tipoForm" value="imagem" />
                                </dd>
                            </li>
                            <li>
                                <dd style="color: red;">
                                    * O arquivo não pode ser maior que 2MB
                                    <div class="buttons">
                                        <button type="button" class="positive" onclick="return validaForm();"><img src="../../imagensNew/download2.png"/> IMPORTAR MOVIMENTO</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>                                    
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                    <div id="titulo2">Histórico das importações de movimentação</div>

                    <table id="barraAtendimento" border="0">
                        <tr>
                            <td align="left" style="font-weight:bold;font-size:12px;">
                                Pesquisa Rápida:
                                <select style='min-width:150px;' id="columns1" onchange="sorter2.search('query1')"></select>
                                <input type="text" id="query1" onkeyup="sorter1.search('query1')" placeholder="Digite aqui a sua pesquisa..." />
                                <a style="text-decoration:none;font-weight:bold;font-size:11px;" href="javascript:document.getElementById('query1').value='';sorter1.reset()">RESTAURAR PADRÕES</a>
                            </td>
                            <td align="right">
                                <div class="details" style="clear:both;">
                                    <div>Resultado <span id="startrecord1"></span>-<span id="endrecord1"></span> de <span id="totalrecords1"></span></div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table cellpadding="0" cellspacing="0" border="0" id="table1" class="tinytable">
                        <thead>
                            <tr>
                                <th><h3>Usuário que Importou</h3></th>
                                <th><h3>Data da Importação</h3></th>
                                <th><h3>Período das Postagens</h3></th>
                                <th><h3>QTD. de Clientes Importados</h3></th>
                                <th><h3>QTD. de Objetos Importados</h3></th>
                                <th><h3>QTD. de Objetos Excluidos</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                            ArrayList listaHistImport = Controle.contrMovimentacao.consultaHistoricoImport(nomeBD);
                                            for (int j = 0; j < listaHistImport.size(); j++) {
                                                Entidade.HistoricoImport hst = (Entidade.HistoricoImport) listaHistImport.get(j);
                                                String vDataIni = "", vDataFim = "", vDataImportacao = "";
                                                Date dataIni = hst.getDataInicio();
                                                if (dataIni != null) {
                                                    vDataIni = sdf.format(dataIni);
                                                }
                                                Date dataFim = hst.getDataFim();
                                                if (dataFim != null) {
                                                    vDataFim = sdf.format(dataFim);
                                                }
                                                Timestamp dataImportacao = hst.getDataImportacao();
                                                if (dataImportacao != null) {
                                                    vDataImportacao = sdf2.format(dataImportacao);
                                                }
                                                int tamanho = hst.getTamanho();
                                                int qtdClientes = hst.getQtdCliente();
                                                int qtdExcluidos = hst.getQtdExcluido();
                                                int idUsuarioRealizou = hst.getIdUsuario();
                                                String nomeUsuario = Controle.contrUsuario.consultaNomeUsuarioById(idUsuarioRealizou, nomeBD);
                            %>
                            <tr>
                                <td><%= nomeUsuario%></td>
                                <td><%= vDataImportacao%></td>
                                <td><%= vDataIni%> - <%= vDataFim%></td>
                                <td><%= qtdClientes%></td>
                                <td><%= tamanho%></td>
                                <td><%= qtdExcluidos%></td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                    <div id="tablefooter" align='center'>
                        <div align="left" style='float:left; width:20%;'>
                            <select onchange="sorter1.size(this.value)">
                                <option value="5">5</option>
                                <option value="10" selected="selected">10</option>
                                <option value="20">20</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                            </select>
                            <span>Linhas por Página</span>
                        </div>
                        <div id="tablenav1" class="tablenav">
                            <div>
                                <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1,true)" />
                                <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1,true)" />
                                <select style="margin-left:5px;" id="pagedropdown1"></select>
                                <a style="margin-left:10px;" href="javascript:sorter1.showall()">Ver Tudo</a>
                            </div>
                        </div>
                        <div id="tablelocation">
                            <div class="page">Página <span id="currentpage1"></span> de <span id="totalpages1"></span></div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        var sorter1 = new TINY.table.sorter('sorter1','table1',{
                            headclass:'head',
                            ascclass:'asc',
                            descclass:'desc',
                            evenclass:'evenrow',
                            oddclass:'oddrow',
                            evenselclass:'evenselected',
                            oddselclass:'oddselected',
                            paginate:true,
                            size:10,
                            colddid:'columns1',
                            currentid:'currentpage1',
                            totalid:'totalpages1',
                            startingrecid:'startrecord1',
                            endingrecid:'endrecord1',
                            totalrecid:'totalrecords1',
                            hoverid:'selectedrowPointer',
                            pageddid:'pagedropdown1',
                            navid:'tablenav1',
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
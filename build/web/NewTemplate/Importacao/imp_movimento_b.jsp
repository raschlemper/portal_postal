
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
    if (session.getAttribute("emp") == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else {

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
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>

    <body>   
        <script type="text/javascript">
            waitMsg();
        </script>
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                        <div class="row">
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-save"></i> Importações</b> > <small>Arquivo de Movimentação</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="well well-md">  
                                <form class="form-inline" name="form1" action="../../ServImportacaoMov" method="post" enctype="multipart/form-data">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item">
                                            <div>
                                                <h4 class="page">Período da Importação</h4>
                                            </div>                                                         
                                            <div class="form-group" >                                                
                                                <label for="from">De</label>
                                                <div class="input-group">
                                                    <input type="hidden" id="nada" name="nada" value="nada" />
                                                    <input type="hidden" id="idUsuario" name="idUsuario" value="<%= idUsuario%>" />
                                                    <input type="text" id="data" name="data" class="form-control" value="" size="10" maxlength="10" onkeypress="mascara(this, maskData)" />
                                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                </div>
                                                <label for="to">à</label>
                                                <div class="input-group">
                                                    <input type="text" id="data2" name="data2" size="10" class="form-control" value=""  maxlength="10" onkeypress="mascara(this, maskData)" />
                                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <label>Escolha o arquivo para importar</label><br/>
                                            <span class="btn btn-default btn-file"><i class="fa fa-folder-open"></i> Selecionar arquivo (movimento.txt) <input type="file" name="arquivo" /></span>
                                            <input type="hidden" id="tipoForm" name="tipoForm" value="imagem" />
                                        </li>
                                        <li class="list-group-item">
                                            <div class="alert alert-danger no-margin">
                                                * Após a exportação no SECT o arquivo geralmente é encontrado em "C:/movimento.TXT".<br/>
                                                * Para alterar este caminho no SECT vá em "Arquivos > Exportação > Destino dos Arquivos".<br/>
                                                * O arquivo não pode ser maior que 2MB.
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                                <button type="button" class="btn btn-success" onclick="return validaForm();"><i class="fa fa-lg fa-spc fa-upload"></i> IMPORTAR MOVIMENTO</button>
                                        </li>
                                    </ul>   
                                </form>
                            </div>   
                        </div>                          

                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading" >Histórico das importações de movimentação</div>
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-importArq">

                                                <thead>
                                                    <tr>
                                                        <th>Importado Por</th>
                                                        <th>Data da Importação</th>
                                                        <th>Período das Postagens</th>
                                                        <th>Clientes Importados</th>
                                                        <th>Objetos Importados</th>
                                                        <th>Objetos Excluidos</th>
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
                                                        <td align="center"><%= vDataImportacao%></td>
                                                        <td align="center"><%= vDataIni%> - <%= vDataFim%></td>
                                                        <td align="right"><%= qtdClientes%></td>
                                                        <td align="right"><%= tamanho%></td>
                                                        <td align="right"><%= qtdExcluidos%></td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>                                                                                 
                                </div>
                            </div>

                        </div>

                        <!-- /.row -->
                    </div>
                </div>
            </div>
            </div>
            <!-- /#page-wrapper -->

            <!-- /#wrapper -->

            <script type="text/javascript">

                function validaForm() {

                    var form = document.form1;
                    if (form.data.value === "") {
                        alert("Preencha a Data Inicial do Periodo!");
                        return false;
                    } else {
                        if (valida_data(form.data) === false) {
                            return false;
                        }
                    }

                    if (form.data2.value === "") {
                        alert("Preencha a Data Final do Periodo!");
                        return false;
                    } else {
                        if (valida_data(form.data2) === false) {
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
                    mes1 = Math.floor(mes1) - 1;
                    var ano1 = aux1[2];
                    var data1 = new Date(ano1, mes1, dia1);

                    var dia2 = aux2[0];
                    var mes2 = aux2[1];
                    mes2 = Math.floor(mes2) - 1;
                    var ano2 = aux2[2];
                    var data2 = new Date(ano2, mes2, dia2);

                    var diferenca = data2.getTime() - data1.getTime();
                    diferenca = Math.floor(diferenca / (1000 * 60 * 60 * 24));

                    if (data1 <= data2) {
                        if (diferenca > 31) {
                            alert("Periodo máximo de importação por vez é de 31 dias!")
                            return false;
                        }
                    } else {
                        alert("A data inicial deve ser menor ou igual a data final!");
                        return false;
                    }


                    if (form.arquivo.value === "") {
                        alert("Escolha o arquivo do movimento a ser importado!\nGeralmente encontrado em 'C:/movimento.txt'.");
                        return false;
                    } else {
                        var indexA = form.arquivo.value.lastIndexOf(".");
                        var indexB = form.arquivo.value.length;
                        var ext = form.arquivo.value.substring(indexA, indexB).toUpperCase();
                        if (ext !== ".TXT") {
                            alert("O arquivo a ser importado deve ser '.TXT' !");
                            return false;
                        }
                    }
                    
                    waitMsg();
                    form.submit();
                }


                function AllTables() {
                    StartDataTable('dataTables-importArq');
                    LoadSelect2Script(MakeSelectDataTable('dataTables-importArq'));
                    fechaMsg();
                }

                $(document).ready(function() {                    
                    $("#data").datepicker({
                        showAnim: 'slideDown',
                        maxDate: new Date(),
                        numberOfMonths: 1,
                        onClose: function(selectedDate) {
                            $("#data2").datepicker("option", "minDate", selectedDate);
                        }
                    });
                    $("#data2").datepicker({
                        showAnim: 'slideDown',
                        maxDate: new Date(),
                        numberOfMonths: 1,
                        onClose: function(selectedDate) {
                            $("#data").datepicker("option", "maxDate", selectedDate);
                        }
                    });

                    LoadDataTablesScripts(AllTables);
                });
            </script>

    </body>

</html>
<%}%>

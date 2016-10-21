
<%@page import="Entidade.Clientes"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
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
        int idCliente = 0;
        if (request.getParameter("idCliente") != null) {
            idCliente = Integer.parseInt(request.getParameter("idCliente"));
        }

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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-save"></i> Importações</b> > <small>Arquivo de Retorno AR</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="well well-md">  
                                <form class="form-inline" name="form1" action="../../ServImportacaoArImg" method="post" enctype="multipart/form-data">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item">
                                            <div>
                                                <h4 class="subtitle">Digite o numero (SRO) da Etiqueta</h4>
                                            </div>                        
                                            <div class="form-group" >           
                                                  <div class="input-group">
                                                <input class="form-control"  id="obj" type="text" name="sroRec"  maxlength="13" onkeypress="mascara(this, maskObjetoSRO);" placeholder="Digite a etiqueta aqui..." />
                                                <span class="input-group-addon"><i class="fa fa-check"></i></span>
                                                  </div>
                                            </div>
                                            <div>
                                                <h4 class="subtitle">Digite o nome do recebedor</h4>
                                            </div>                        
                                            <div class="form-group" >           
                                                <input id="obj" class="form-control" size="60" type="text" name="nomeRec"  maxlength="40" onkeypress="" placeholder="Digite o nome do recebedor aqui..." />
                                            </div>
                                            <div>
                                                <h4 class="subtitle">Data do recebimento do AR</h4>
                                            </div> 
                                            <div class="form-group" >                        
                                                <div class="input-group">
                                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                    <input type="text" id="dataRec" name="dataRec" class="form-control" value="" size="12" maxlength="10" onKeyPress="mascara(this, maskData)" />

                                                </div>
                                            </div>

                                            <div>
                                                <h4 class="subtitle">Selecione o Cliente</h4>
                                            </div> 
                                            <div class="row">
                                                <div class="form-group col-md-5" >
                                                    <label class="small">Selecione um Cliente</label>
                                                    <select class="populate placeholder" name="idClienteRec" id="idCliente">
                                                        <option value="">-- Selecione um Cliente --</option>
                                                        <%
                                                            ArrayList<Clientes> listaCliente = Controle.contrCliente.getNomeCodigoMetodo(nomeBD, false);
                                                            for (Clientes c : listaCliente) {
                                                                if (idCliente == c.getCodigo()) {
                                                                    out.println("<option selected value='" + c.getCodigo() + "'>[" + c.getCodigo() + "] " + c.getNome() + "</option>");
                                                                } else {
                                                                    out.println("<option value='" + c.getCodigo() + "'>[" + c.getCodigo() + "] " + c.getNome() + "</option>");
                                                                }
                                                            }
                                                        %>
                                                    </select>
                                                </div>  
                                            </div>

                                        </li>

                                        <li class="list-group-item">
                                            <label>Escolha o arquivo da imagem do AR para importar</label><br/>
                                            <span class="btn btn-default btn-file"><i class="fa fa-folder-open"></i> Selecionar imagem <input type="file" name="arquivoRec" /></span>
                                            <input type="hidden" id="tipoForm" name="tipoFormRec" value="imagem" />
                                        </li>

                                        <li class="list-group-item">
                                            <button type="button" class="btn btn-success" onclick="return validaFormArImg();"><i class="fa fa-lg fa-spc fa-upload"></i> IMPORTAR AR</button>
                                        </li>
                                    </ul>   
                                </form>

                            </div>    
                        </div>   

                        <!-- /.row -->
                        <div class="row">
                            <div class="col-lg-12">
                                <div class="panel panel-default">
                                    <div class="panel-heading" >Histórico das importações dos retornos de AR</div>
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-importArq">
                                                <thead>
                                                    <tr>
                                                        <th>Importado Por</th>
                                                        <th>Data da Importação</th>
                                                        <th>Período das Postagens</th>
                                                        <th>Clientes Importados</th>
                                                        <th>AR's Importados</th>
                                                        <th>AR's Excluidos</th>
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
                                                        <td align="center"><%= vDataImportacao%></td>
                                                        <td align="center"><%= vDataIni%> - <%= vDataFim%></td>
                                                        <td align="rigth"><%= qtdClientes%></td>
                                                        <td align="right"><%= tamanho%></td>
                                                        <td align="right"><%= qtdExcluido%></td>
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
            function selectCliente() {
                $('#idCliente').select2();
            }
            function validaFormArImg() {


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
                var dataIni = form.data3.value;
                var dataFim = form.data4.value;
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

                if (data <= data2) {
                    if (diferenca > 31) {
                        alert("Periodo máximo de importação por vez é de 31 dias!")
                        return false;
                    }
                } else {
                    alert("A data inicial deve ser menor ou igual a data final!");
                    return false;
                }


                if (form.arquivo.value === "") {
                    alert("Escolha a imagem do AR a ser importado!");
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


            $(document).ready(function () {
                LoadSelect2Script(selectCliente);
                $("#dataRec").datepicker({
                    showAnim: 'slideDown',
                    maxDate: new Date(),
                    numberOfMonths: 1,
                    onClose: function (selectedDate) {
                        $("#dataRec").datepicker("option", "minDate", selectedDate);
                    }
                });

                LoadDataTablesScripts(AllTables);
            });
        </script>

    </body>

</html>
<%}%>

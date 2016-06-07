
<%@page import="Util.XTrustProvider"%>
<%@page import="org.dom4j.Node"%>
<%@page import="java.util.List"%>
<%@page import="org.dom4j.Document"%>
<%@page import="java.io.StringReader"%>
<%@page import="org.dom4j.io.SAXReader"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String vDataAtual = sdf.format(new Date());
        if (request.getParameter("data") != null) {
            vDataAtual = request.getParameter("data");
        }
        String vData2 = sdf.format(new Date());
        if (request.getParameter("data2") != null) {
            vData2 = request.getParameter("data2");
        }


%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Portal Postal - Relatorios</title>
        <%@ include file="../includes/Css_js.jsp" %>
        <!--  <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.11/css/jquery.dataTables.css"/>
  
        <!--  <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>-->


        <script type="text/javascript" charset="utf8" src="//cdn.datatables.net/1.10.11/js/jquery.dataTables.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.1.2/js/dataTables.buttons.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.1.2/js/buttons.flash.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
        <script src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
        <script src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.1.2/js/buttons.html5.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.1.2/js/buttons.print.min.js"></script>



       <!-- data table css fix -->
       <link href="../../NewTemplate/dist/css/jquery.dataTables.css" rel="stylesheet"/>
        <script>

            function callTableJs() {
                $('#excelDataTable').DataTable({
                    "language": {
                        "lengthMenu": "Exibir _MENU_ por pagina",
                        "zeroRecords": "Nada encontrado - desculpe",
                        "info": "_MAX_ linhas",
                        "infoEmpty": "Nenhum dado disponivel",
                        "infoFiltered": "(filtrado de _MAX_ no total)"
                    },
                    "paging": false,
                    dom: 'Bfrtip',
                    buttons: [
                        {
                            extend: 'copyHtml5',
                            text: '<i class="fa fa-spc fa-files-o"></i> COPY',
                            titleAttr: 'Copy'
                        },
                        {
                            extend: 'excelHtml5',
                            text: '<i class="fa fa-spc fa-file-excel-o"></i> EXCELL',
                            titleAttr: 'Excel'
                        },
                        {
                            extend: 'csvHtml5',
                            text: '<i class="fa fa-spc fa-file-text-o"></i> CSV',
                            titleAttr: 'CSV'
                        },
                        {
                            extend: 'pdfHtml5',
                            orientation: 'landscape',
                            pageSize: 'LEGAL',
                            text: '<i class="fa fa-spc fa-file-pdf-o"></i> PDF',
                            titleAttr: 'PDF'
                        }
                    ],
                    aaSorting: [[0, 'asc']]
                });
            }


            function ajaxtable() {
                if ($('#tipoRel').val() === '0') {
                    alert('SELECIONE UM RELATÓRIO');
                    $('#tipoRel').focus();
                    return false;
                }
                var sqlT = $('#tipoRel').val();
                var dataIni = $('#data').val();
                var dataFim = $('#data2').val();
                waitMsg();

                $.ajax({
                    method: "GET",
                    url: "ajax/montaRelatorios.jsp",
                    data: {'sql': sqlT, 'dataI': dataIni, 'dataF': dataFim
                    },
                    dataType: "json"
                })
                        .done(function (ret) {

                            if (jQuery.isEmptyObject(ret)) {
                                $("#tbWrapper").html(" <div class='dataTable_wrapper no-padding'> <table id='excelDataTable' class='display compact'>"+
                                                        "<thead id='excelDataTableThead'> <th>"+
                                                        "<div class='alert alert-danger'>  <strong>OPS!</strong> NÃO HOUVERAM RESULTADOS PARA A PESQUISA</div>"+
                                                        "</th> </thead></table></div>");
                            } else {
                                buildHtmlTable(ret);
                                callTableJs();
                                fechaMsg();
                            }
                            $('.dt-button').switchClass('dt-button', 'btn btn-default pad pull-left');
                            // $('#excelDataTable_filter').find('input').addClass('form-control');
                            
                        });

            }


            function buildHtmlTable(myJson) {
                $("#tbWrapper").html(" <div class='dataTable_wrapper no-padding'> <table id='excelDataTable' class='display compact'><thead id='excelDataTableThead'>  </thead></table></div>");

                var columns = addAllColumnHeaders(myJson);

                for (var i = 0; i < myJson.length; i++) {
                    var row$ = $('<tr/>');
                    for (var colIndex = 0; colIndex < columns.length; colIndex++) {
                        var cellValue = myJson[i][columns[colIndex]];

                        if (cellValue === null) {
                            cellValue = "";
                        }
                        row$.append($('<td/>').html(cellValue));
                    }
                    $("#excelDataTable").append(row$);
                }
            }

            function addAllColumnHeaders(myJson) {
                var columnSet = [];
                var headerTr$ = $('<tr/>');
                var lg = '';
                for (var i = 0; i < myJson.length; i++) {
                    var rowHash = myJson[i];

                    for (var key in rowHash) {
                        if ($.inArray(key, columnSet) === -1) {
                            lg = key;
                            if (key.match('_')) {
                                lg = key.split('_').join(' ');
                            } else if (key.startsWith('$')) {
                                lg = key.replace('$', '');
                                lg += ' (R$)'
                            }
                            columnSet.push(key);
                            headerTr$.append($('<th/>').html(lg));
                        }
                    }
                }
                $("#excelDataTableThead").append(headerTr$);
                return columnSet;
            }
        </script>

        <style>
            .pad{
                margin: 10px 10px 0px 10px;

            }

        </style>

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
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-dashboard"></i> Dashborad</b> > <small>Relatórios</small></h4>
                            </div>
                        </div>
                        <div class="row">

                            <div class="well well-md"> 

                                <div>
                                    <h4 class="subtitle">Selecione o Relatório e o Periodo</h4>
                                </div> 
                                <form action="painel_etiquetas_pesq_b.jsp" method="post" name="form2">
                                    <div class="row">
                                        <div class="form-group col-md-8" >
                                            <label class="small">Selecione um dos Relatórios</label>
                                            <select class="populate placeholder" name="tipoRel" id="tipoRel" onchange="validaCampos(this)">
                                                <option value="0">-- Selecione um Relatório --</option>                                                
                                                <option value='1'>Vendas - Cliente por período</option>
                                                <option value='2'>Vendas - Cliente comparativo mês a mês</option>
                                                <option value='3'>Vendas - Serviço por Periodo</option>
                                                <option value='4'>Vendas - Serviço comparativo mês a mês</option>
                                                <option value='5'>Vendas - Faturado AGF por período (sintético)</option>
                                                <option value='6'>Vendas - Contrato ECT por período (sintético)</option>
                                                <option value='7'>Objetos - Atrasados</option>
                                            </select>
                                        </div>  
                                    </div>
                                    <div class="row">
                                        <div class="form-inline col-md-8" >
                                            <label class="small">Selecione o Periodo da Impressão</label>
                                            <div class="clearfix"></div>
                                            <label for="data">De</label>
                                            <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                <input type="text" id="data" name="data" class="form-control" value="<%= vDataAtual%>" size="10" maxlength="10" onkeypress="mascara(this, maskData)"/>
                                            </div>
                                            <label for="data2">à</label>
                                            <div class="input-group">
                                                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                <input type="text" id="data2" name="data2" size="10" class="form-control" value="<%= vData2%>"  maxlength="10" onkeypress="mascara(this, maskData)" />
                                            </div>
                                            <input type="hidden" style="width:100px;" name="obj" />
                                            <button type="button" class="btn btn-sm btn-primary form-control" onclick="ajaxtable();">
                                                <i class="fa fa-lg fa-spc fa-file-text"></i>GERAR
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>   
                        </div>  
                        <div class="row">
                            <form action="../../ServPreVendaInutilizar" method="post" name="formDel">
                                <div class="col-lg-12">
                                    <div class="panel panel-default">  
                                        <div class="panel-heading" >Resultado da Pesquisa</div>
                                        <div class="panel-body" id="tbWrapper" style="margin-left: 10px; margin-right: 10px; ">
                                            <div class="dataTable_wrapper no-padding" >                                                
                                                <table id="excelDataTable" class="display compact">
                                                    <thead id="excelDataTableThead"> 
                                                        <th>SELECIONE UM RELATÓRIO E CLIQUE NO BOTÃO GERAR</th>
                                                    </thead>
                                                </table>                                          
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <script type="text/javascript">


                function select() {
                    $('#tipoRel').select2();
                }


                $(document).ajaxStop(function () {
                    fechaMsg();
                });

                $(document).ready(function () {
                    LoadSelect2Script(select);
                    $("#data").datepicker({
                        showAnim: 'slideDown',
                        maxDate: new Date(),
                        numberOfMonths: 3,
                        onClose: function (selectedDate) {
                            $("#data2").datepicker("option", "minDate", selectedDate);
                        }
                    });
                    $("#data2").datepicker({
                        showAnim: 'slideDown',
                        maxDate: new Date(),
                        numberOfMonths: 3,
                        onClose: function (selectedDate) {
                            $("#data").datepicker("option", "maxDate", selectedDate);
                        }
                    });

                    $('[id^=detail-]').hide();
                    $('#detail-1').show();
                    $('.toggle').click(function () {
                        $input = $(this);
                        $target = $('#' + $input.attr('data-toggle'));
                        $target.slideToggle();
                    });


                });

                function validaCampos(sel) {
                    var value = sel.value;

                    if (value === "2" || value === "4" || value === "7") {
                        $('#data').attr('disabled', 'disabled');
                        $('#data2').attr('disabled', 'disabled');

                    } else if (value === "1" || value === "3" || value === "5" || value === "6") {
                        $('#data').removeAttr('disabled');
                        $('#data2').removeAttr('disabled');
                    }
                }
            </script>

    </body>
</html>
<%}%>
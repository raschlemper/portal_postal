
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
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <meta name="description" content=""/>
        <meta name="author" content=""/>
        <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-8">

            <!--[if IE]><link rel="shortcut icon" href="/Portal_Postal_Web/imagensNew/favicon.ico" /><![endif]-->
            <link rel="icon" href="/Portal_Postal_Web/imagensNew/favicon.ico" />

            <%--
            <link href="/Portal_Postal_Web/plugins/bootstrap/dist/css/bootstrap.css" rel="stylesheet" type="text/css"/>
            <!--<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/themes/smoothness/jquery-ui.css" />-->
            <link href="/Portal_Postal_Web/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet">

                <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.6.2/css/font-awesome.min.css" rel="stylesheet">
                    <link href='http://fonts.googleapis.com/css?family=Righteous' rel='stylesheet' type='text/css'>
                        <link href="/Portal_Postal_Web/plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
                            <link href="/Portal_Postal_Web/plugins/fullcalendar/fullcalendar.css" rel="stylesheet">
                                <link href="/Portal_Postal_Web/plugins/xcharts/xcharts.min.css" rel="stylesheet">
                                    <link href="/Portal_Postal_Web/plugins/select2/select2.css" rel="stylesheet">
                                        <link href="/Portal_Postal_Web/plugins/justified-gallery/justifiedGallery.css" rel="stylesheet">
                                            <!-- <link href="css/style_v1.css" rel="stylesheet"> -->
                                            <link href="/Portal_Postal_Web/plugins/chartist/chartist.min.css" rel="stylesheet">
                                                <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
                                                <!--[if lt IE 9]>
                                                                <script src="http://getbootstrap.com/docs-assets/js/html5shiv.js"></script>
                                                                <script src="http://getbootstrap.com/docs-assets/js/respond.min.js"></script>
                                                <![endif]-->

                                                <!--<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/themes/smoothness/jquery-ui.css" /> -->
                                                <!-- Custom CSS -->
                                                <link href="/Portal_Postal_Web/NewTemplate/dist/css/sb-admin-2.css" rel="stylesheet" type="text/css"/>
                                                <link href="/Portal_Postal_Web/NewTemplate/dist/css/timeline.css" rel="stylesheet" type="text/css"/>
                                                <link href="/Portal_Postal_Web/NewTemplate/dist/css/simple-sidebar.css" rel="stylesheet">



                                                    <!-- jQuery -->
                                                    <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script> -->
                                                    <script src="/Portal_Postal_Web/plugins/jquery/jquery.min.js"></script>

                                                    <script src="/Portal_Postal_Web/plugins/jquery-ui/jquery-ui.js"></script>
                                                    <!-- Include all compiled plugins (below), or include individual files as needed -->

                                                    <script src="/Portal_Postal_Web/plugins/justified-gallery/jquery.justifiedGallery.min.js"></script>
                                                    <!--<script src="/Portal_Postal_Web/plugins/tinymce/tinymce.min.js"></script>
                                                    <script src="/Portal_Postal_Web/plugins/tinymce/jquery.tinymce.min.js"></script>
                                                    -->
                                                    <script type="text/javascript" language="javascript" src="/Portal_Postal_Web/NewTemplate/dist/js/devoops.js" ></script>
                                                    <!--
                                                    <script type="text/javascript" language="javascript" src="/Portal_Postal_Web/plugins/underscore/underscore.js"></script>
                                                    <script type="text/javascript" language="javascript" src="/Portal_Postal_Web/plugins/underscore/underscoreConfig.js"></script>
                                                    <script type="text/javascript" language="javascript" src="/Portal_Postal_Web/plugins/jquery-mask/jquery-mask.js"></script>
                                                    <script type="text/javascript" language="javascript" src="/Portal_Postal_Web/plugins/watch/watch.js"></script>
                                                    <script type="text/javascript" language="javascript" src="/Portal_Postal_Web/plugins/numeral/numeral.js"></script>
                                                    <script type="text/javascript" language="javascript" src="/Portal_Postal_Web/plugins/numeral/pt-br.js"></script>
                                                    -->
                                                    <!-- Custom Theme JavaScript -->
                                                    <script src="/Portal_Postal_Web/plugins/metisMenu/dist/metisMenu.js" type="text/javascript"></script>
                                                    <script src="/Portal_Postal_Web/NewTemplate/dist/js/bootbox.min.js" type="text/javascript"></script>
                                                    <script src="/Portal_Postal_Web/NewTemplate/dist/js/sb-admin-2.js" type="text/javascript"></script>
                                                    <script src="/Portal_Postal_Web/NewTemplate/dist/js/sidebar_menu.js" type="text/javascript"></script>

                                                    <!-- MASCARAS-->
                                                    <script type="text/javascript" src="/Portal_Postal_Web/javascript/mascara_bootStrap.js" charset="UTF-8"></script>
                                                    <script type="text/javascript" src="/Portal_Postal_Web/Cliente/js/ajax_portal.js"></script>
                                                    <script type="text/javascript" src="/Portal_Postal_Web/javascript/ddslick.js"></script>

                                                    <!-- Bootstrap Core JavaScript -->
                                                    <script src="/Portal_Postal_Web/plugins/bootstrap/dist/js/bootstrap.min.js" type="text/javascript"></script>

                                                    <!-- JQUERY NUMERIC }); -->
                                                    <!--- X -editable -->
                                                    <!-- 
                                                           <link href="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/css/bootstrap-editable.css" rel="stylesheet"/>
                                                           <script src="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap3-editable/js/bootstrap-editable.min.js"></script>
                                                    -->
                                                    <!--- X -editable fix -->
                                                    <!--    <style>
                                                          .input-sm{
                                                              height: 23px;
                                                          }
                                                          .editable-clear-x{
                                                              top: 12px;
                                                          }
                                                      </style>-->
--%>
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
                                                  <!--   <script src="https://cdn.datatables.net/plug-ins/1.10.12/sorting/formatted-numbers.js"></script>
                                                    
                                                    <script src="https://cdn.datatables.net/plug-ins/1.10.12/sorting/numeric-comma.js"></script>
                                                     <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.11/css/jquery.dataTables.css"/>
                                                    -->
                                                    <!--  <script src="https://code.jquery.com/jquery-1.12.0.min.js"></script>-->

                                                    <!-- data table css fix -->
                                                    <link href="../../NewTemplate/dist/css/jquery.dataTables.css" rel="stylesheet"/>
            
            
            
            <script>

                function callTableJs(titulo) {

                    $('#excelDataTable').DataTable({
                        // "columnDefs": [{ type: 'formatted-num', targets: 6 }
                        //  ],
                        "language": {
                            "lengthMenu": "Exibir _MENU_ por pagina",
                            "zeroRecords": "Nada encontrado - desculpe",
                            "info": "_MAX_ linhas",
                            "infoEmpty": "Nenhum dado disponivel",
                            "infoFiltered": "(filtrado de _MAX_ no total)",
                            "decimal": ",",
                            "thousands": "."
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
                                titleAttr: 'PDF',
                                title: titulo
                            }
                        ],
                        aaSorting: [[0, 'asc']]
                    });
                }


                function ajaxtable() {
                    if ($('#tipoRel').val() === '0') {
                        alert('SELECIONE UM RELAT�RIO');
                        $('#tipoRel').focus();
                        return false;
                    }
                    var titulo = $('#tipoRel option:selected').html();
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
                                    $("#tbWrapper").html(" <div class='dataTable_wrapper no-padding'> <table id='excelDataTable' class='display compact'>" +
                                            "<thead id='excelDataTableThead'> <th>" +
                                            "<div class='alert alert-danger'>  <strong>OPS!</strong> N�O HOUVERAM RESULTADOS PARA A PESQUISA</div>" +
                                            "</th> </thead></table></div>");
                                } else {
                                    buildHtmlTable(ret);
                                    callTableJs("RELATORIO " + titulo + " - " + dataIni + " - " + dataFim);
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

                function pesqSro(param) {
                    $('#objetos').val(param);
                    $('#frmSRO').submit();
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

            <form name="frmSRO" id="frmSRO" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
                <input type="hidden" name="objetos" id="objetos" value="" />
            </form> 

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                        <div class="row">
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-dashboard"></i> Dashborad</b> > <small>Relat�rios</small></h4>
                            </div>
                        </div>
                        <div class="row">

                            <div class="well well-md"> 

                                <div>
                                    <h4 class="subtitle">Selecione o Relat�rio e o Periodo</h4>
                                </div> 
                                <form action="painel_etiquetas_pesq_b.jsp" method="post" name="form2">
                                    <div class="row" >
                                        <div class="form-group col-md-8" >
                                            <label class="small">Selecione um dos Relat�rios</label>
                                            <select class="populate placeholder" name="tipoRel" id="tipoRel" onchange="validaCampos(this)">
                                                <option value="0">-- Selecione um Relat�rio --</option>                                                
                                                <option value='1'>Vendas - Cliente por per�odo</option>
                                                <option value='2'>Vendas - Cliente comparativo m�s a m�s</option>
                                                <option value='3'>Vendas - Servi�o por Periodo</option>
                                                <option value='4'>Vendas - Servi�o comparativo m�s a m�s</option>
                                                <option value='5'>Vendas - Faturado AGF por per�odo (sint�tico)</option>
                                                <option value='6'>Vendas - Contrato ECT por per�odo (sint�tico)</option>
                                                <option value='8'>Vendas - Contrato/Cart�o de postagem ECT por per�odo (sint�tico)</option>
                                                <option value='7'>Objetos - Atrasados</option>
                                            </select>
                                        </div>  
                                    </div>
                                    <div class="row" >
                                        <div class="form-inline col-md-8" >
                                            <span id="select_data">
                                                <label class="small">Selecione o Periodo da Impress�o</label>
                                                <div class="clearfix"></div>
                                                <label for="data">De</label>
                                                <div class="input-group">
                                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                    <input type="text" id="data" name="data" class="form-control" value="<%= vDataAtual%>" size="10" maxlength="10" onkeypress="mascara(this, maskData)"/>
                                                </div>
                                                <label for="data2">�</label>
                                                <div class="input-group">
                                                    <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                                    <input type="text" id="data2" name="data2" size="10" class="form-control" value="<%= vData2%>"  maxlength="10" onkeypress="mascara(this, maskData)" />
                                                </div>
                                            </span>
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
                                                        <th>SELECIONE UM RELAT�RIO E CLIQUE NO BOT�O GERAR</th>
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

                        $('#select_data').hide();

                        //$('#data').attr('disabled', 'disabled');
                        //$('#data2').attr('disabled', 'disabled');

                    } else if (value === "1" || value === "3" || value === "5" || value === "6") {
                        $('#select_data').show();
                        // $('#data').removeAttr('disabled');
                        // $('#data2').removeAttr('disabled');
                    }
                }
            </script>

    </body>
</html>
<%}%>
<%@page import="Entidade.Clientes"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int idCliente = request.getParameter("idCliente") != null ? Integer.parseInt(request.getParameter("idCliente")) : 0;
    String nomeBD = (String) session.getAttribute("empresa");
%>
<html>
    <head>
        <title>Portal Postal - Relatorios</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <meta name="description" content=""/>
        <meta name="author" content=""/>
        <meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-8">
        <script src="https://cdn.datatables.net/buttons/1.1.2/js/dataTables.buttons.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.1.2/js/buttons.flash.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/2.5.0/jszip.min.js"></script>
        <script src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/pdfmake.min.js"></script>
        <script src="https://cdn.rawgit.com/bpampuch/pdfmake/0.1.18/build/vfs_fonts.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.1.2/js/buttons.html5.min.js"></script>
        <script src="https://cdn.datatables.net/buttons/1.1.2/js/buttons.print.min.js"></script>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script src="../../javascript/jx.js" type="text/javascript"></script>
        <link href="../../NewTemplate/dist/css/jquery.dataTables.css" rel="stylesheet"/>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>
    <body>
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">
                        <div class="row">
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-barcode"></i> Dashboard</b> > <small>Vendas Por Cliente</small></h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="well well-md"> 
                                <div>
                                    <h4 class="subtitle">Vendas Por Cliente</h4>
                                </div> 
                                <form class="form-inline" action="" method="post"> 
                                    <div class="row">
                                        <div class="form-group col-md-5" >
                                            <label class="small">Selecione um Cliente</label>
                                            <select class="populate placeholder" name="idCliente" id="idCliente">
                                                <option value="0">-- Selecione um Cliente --</option>
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
                                        <div class="col-md-7">
                                            <br>
                                            <button type="button" style="margin-top:5px;" class="btn btn-sm btn-primary form-inline" onclick="gerarRelatorio()">
                                                <i class="fa fa-lg fa-spc fa-file-text"></i>GERAR
                                            </button>  
                                        </div>
                                    </div>
                                </form>
                                
                            </div>                      
                        </div>   
                                            
                        <div class="row">
                                <div class="col-lg-12">
                                    <div class="panel panel-default">  
                                        <div class="panel-heading" >Resultado da Pesquisa</div>
                                        <div class="panel-body" id="tbWrapper" style="margin-left: 10px; margin-right: 10px; ">
                                            </br>
                                            <div id="reportContent" class="dataTable_wrapper no-padding" >                                                
                                                <P class="alert alert-warning text-center">SELECIONE UM CLIENTE E CLIQUE NO BOTÃO GERAR</P>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            function selectCliente() {
                $('#idCliente').select2();
            }

            $(document).ready(function () {
                LoadSelect2Script(selectCliente);
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

                LoadDataTablesScripts(AllTables);

            });
            
            
            function gerarRelatorio(){
                var idCliente = $('#idCliente').val();
                if(idCliente==0){
                   alert('Cliente inválido');
                    return;
                }
                var parameter = "idCliente="+idCliente;
                JxPost('reportContent', JxResult, 'ajax/relatorioVendasPorCliente.jsp',parameter, false);
            }
            
      function filtroServicos(){
          var idServicosFilter="";
          $("#reportContent input[type=checkbox]").each(function(){
              if(this.checked){
                  idServicosFilter+=this.value+",";
              }
          });
          
          if(idServicosFilter.length>0){
              idServicosFilter = idServicosFilter.substr(0,idServicosFilter.length-1);
              var parameter = "idServicos="+idServicosFilter+
                               "&idCliente="+$('#idCliente').val();
             
              JxPost('', JxResult, 'ajax/relatorioVendasPorClienteFilter.jsp',parameter, false);
          }else{
              alert("É necessario selecionar pelo menos um serviço para visualizar os graficos.");
          }
          
      }
      
     
      function showCharts(dados){
        google.charts.load('current', {'packages':['bar']});
        google.charts.setOnLoadCallback(drawChart);
        
        function drawChart() {
          var data = google.visualization.arrayToDataTable(dados);
          
         
          var options = {
            chart: {
              title: 'Grafico de Vendas',
              subtitle: 'Vendas do período'
            }
            };
              
          var chart = new google.charts.Bar(document.getElementById('columnchart'));
          chart.draw(data, options);
        }
    }            
            
    function showCharts2(dados){
        google.charts.load('current', {'packages':['bar']});
        google.charts.setOnLoadCallback(drawChart);


        function drawChart() {
          var data = google.visualization.arrayToDataTable(dados);
          var options = {
            chart: {
              title: 'Quantidade de Objetos por Serviço',
              legend: { position: "none" },
            },
             hAxis: { 
                format:'currency'
              }
          };

          var chart = new google.charts.Bar(document.getElementById('columnchart2'));
          chart.draw(data, options);
        }
    }    
    
    function showCharts3(dados){
        google.charts.load('current', {'packages':['bar']});
        google.charts.setOnLoadCallback(drawChart);


        function drawChart() {
          var data = google.visualization.arrayToDataTable(dados);
          var options = {
            chart: {
              title: 'Valor Vendido por Serviço',
              legend: { position: "none" },
            }
            
          };

          var chart = new google.charts.Bar(document.getElementById('columnchart3'));
          chart.draw(data, options);
        }
    }            
            

        </script>
    </body>

</html>

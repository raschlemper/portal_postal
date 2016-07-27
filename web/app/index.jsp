<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    if (session.getAttribute("usuario") == null || session.getAttribute("emp") == null) {
        response.sendRedirect( request.getContextPath() + "/index.jsp?msgLog=3");
    }
%>
<html lang="en" ng-app="App">
    <head>
        <title>Portal Postal</title>
        
        <base href="${pageContext.request.contextPath}/app/"/>
        <%@ include file="/NewTemplate/includes/Css_js.jsp" %>
            
        <script type="text/javascript">
            var _contextPath = "${pageContext.request.contextPath}";
        </script>
    </head> 
    <body> 
        
        <%@ include file="/NewTemplate/includes/navBarTop.jsp" %>
        <div id="wrapper">
            <%@ include file="/NewTemplate/includes/menu_agencia_bootstrap.jsp" %>
            <div ui-view></div>
        </div> 

        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular-cookies/angular-cookies.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular-resource/angular-resource.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular-sanitize/angular-sanitize.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular-animate/angular-animate.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular-locale-pt-br/angular-locale_pt-br.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular-ui-router/angular-ui-router.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular-bootstrap/ui-bootstrap-tpls.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular-ui-mask/mask.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular-ui-input-mask/angular-input-masks-standalone.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/datatables/jquery.dataTables.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular-datatables/angular-datatables.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular-datatables/plugins/bootstrap/angular-datatables.bootstrap.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/moment/moment-with-locales.min.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/highcharts-ng/highcharts.src.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/highcharts-ng/highcharts-ng.min.js"></script> 
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/plugins/angular/angular-file-upload/angular-file-upload.min.js"></script>         
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/app.js"></script>         
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/app.config.js"></script>  
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/app.listas.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/app.messages.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/interceptor/loadingInterceptor.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/interceptor/authInterceptor.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/run/stateRun.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/anexoFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/telefoneFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/placaFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/renavamFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/tipoLancamentoFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/situacaoLancamentoFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/conciliadoLancamentoFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/saldoLancamentoFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/numeroCartaoCreditoFilter.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/animate/listOrdered.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/directive/datepickerPopupDirective.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/directive/appTable.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/directive/appTree.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/directive/appRatear.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/directive/appParcelar.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/directive/appDatepicker.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/directive/fileModel.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/directive/compile.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/directive/trim.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/colaboradorHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/fornecedorHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/informacaoProfissionalHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/informacaoBancariaHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/planoContaHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/centroCustoHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/contaCorrenteHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/contaHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/lancamentoHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/lancamentoConciliadoHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/lancamentoRateioHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/lancamentoTransferenciaHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/lancamentoProgramadoHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/lancamentoProgramadoParcelaHandler.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/handler/lancamentoProgramadoRateioHandler.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/validation/financeiroValidation.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/modalController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/helpController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/appController.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/veiculoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/modalVisualizarVeiculoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/modalEditarVeiculoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/veiculoManutencaoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/modalVisualizarVeiculoManutencaoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/modalEditarVeiculoManutencaoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/veiculoCombustivelController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/modalVisualizarVeiculoCombustivelController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/modalEditarVeiculoCombustivelController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/veiculoMultaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/modalVisualizarVeiculoMultaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/modalEditarVeiculoMultaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/veiculoSeguroController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/modalVisualizarVeiculoSeguroController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/modalEditarVeiculoSeguroController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/veiculoSinistroController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/modalVisualizarVeiculoSinistroController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/veiculo/modalEditarVeiculoSinistroController.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/financeiroController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/demonstrativoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/fluxoCaixaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/colaborador/colaboradorController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/colaborador/modalColaboradorEditarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/colaborador/modalColaboradorVisualizarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/fornecedor/fornecedorController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/fornecedor/modalFornecedorEditarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/fornecedor/modalFornecedorVisualizarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/banco/bancoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/banco/modalBancoEditarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/contaCorrente/contaCorrenteController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/contaCorrente/modalContaCorrenteEditarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/contaCorrente/modalContaCorrenteVisualizarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/carteiraCobranca/carteiraCobrancaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/carteiraCobranca/modalCarteiraCobrancaEditarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/carteiraCobranca/modalCarteiraCobrancaVisualizarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/cartaoCredito/cartaoCreditoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/cartaoCredito/modalCartaoCreditoEditarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/cartaoCredito/modalCartaoCreditoVisualizarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/planoConta/planoContaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/planoConta/modalPlanoContaEditarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/centroCusto/centroCustoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/centroCusto/modalCentroCustoEditarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/conta/contaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/conta/modalContaEditarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/conta/modalContaVisualizarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamento/lancamentoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamento/modalLancamentoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamento/modalLancamentoAnexarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamento/modalLancamentoConciliarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamento/modalLancamentoEditarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamento/modalLancamentoTransferirController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamento/modalLancamentoVisualizarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamentoProgramado/lancamentoProgramadoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamentoProgramado/modalLancamentoProgramadoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamentoProgramado/modalLancamentoProgramadoEditarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamentoProgramado/modalLancamentoProgramadoGerarController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamentoProgramado/modalLancamentoProgramadoTransferirController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamentoProgramado/modalLancamentoProgramadoVisualizarController.js"></script>
                
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/promiseService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/fipeService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/cepService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/modalService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/dataTableService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/datePickerService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/listaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/valorService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/groupService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/periodoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/reportService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/stringService.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/veiculo/veiculoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/veiculo/veiculoCombustivelService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/veiculo/veiculoManutencaoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/veiculo/veiculoMultaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/veiculo/veiculoSeguroService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/veiculo/veiculoSinistroService.js"></script>

        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/favorecidoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/colaboradorService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/fornecedorService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/bancoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/contaCorrenteService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/carteiraCobrancaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/cartaoCreditoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/planoContaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/centroCustoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/contaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/lancamentoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/lancamentoTransferenciaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/lancamentoProgramadoTransferenciaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/lancamentoConciliadoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/lancamentoProgramadoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/lancamentoAnexoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/frequenciaLancamentoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/tipoCategoriaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/tipoDocumentoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/tipoFormaPagamentoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/saldoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/demonstrativoService.js"></script>

    </body>
</html>

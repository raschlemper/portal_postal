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

        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/app.js"></script>         
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/app.config.js"></script>  
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/app.constants.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/interceptor/loadingInterceptor.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/interceptor/authInterceptor.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/run/stateRun.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/placaFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/renavamFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/tipoLancamentoFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/situacaoLancamentoFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/filter/conciliadoLancamentoFilter.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/animate/listOrdered.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/directive/datepickerPopupDirective.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/directive/appTable.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/directive/appTree.js"></script>
        
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
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/banco/bancoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/banco/modalEditarBancoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/contaCorrente/contaCorrenteController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/contaCorrente/modalEditarContaCorrenteController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/contaCorrente/modalVisualizarContaCorrenteController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/carteiraCobranca/carteiraCobrancaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/carteiraCobranca/modalEditarCarteiraCobrancaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/carteiraCobranca/modalVisualizarCarteiraCobrancaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/cartaoCredito/cartaoCreditoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/cartaoCredito/modalEditarCartaoCreditoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/cartaoCredito/modalVisualizarCartaoCreditoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/planoConta/planoContaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/planoConta/modalEditarPlanoContaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/conta/contaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/conta/modalEditarContaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/conta/modalVisualizarContaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamento/lancamentoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamento/modalEditarLancamentoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamento/modalVisualizarLancamentoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamento/modalLancamentoTransferenciaController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamento/modalLancamentoConciliadoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamentoProgramado/lancamentoProgramadoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamentoProgramado/modalEditarLancamentoProgramadoController.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/controller/financeiro/lancamentoProgramado/modalVisualizarLancamentoProgramadoController.js"></script>
                
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/promiseService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/fipeService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/cepService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/modalService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/dataTableService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/datePickerService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/listaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/valorService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/groupService.js"></script>
        
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/veiculo/veiculoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/veiculo/veiculoCombustivelService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/veiculo/veiculoManutencaoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/veiculo/veiculoMultaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/veiculo/veiculoSeguroService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/veiculo/veiculoSinistroService.js"></script>

        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/bancoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/contaCorrenteService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/carteiraCobrancaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/cartaoCreditoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/planoContaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/contaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/lancamentoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/lancamentoTransferenciaService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/lancamentoConciliadoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/lancamentoProgramadoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/frequenciaLancamentoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/tipoDocumentoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/tipoFormaPagamentoService.js"></script>
        <script type="text/javascript" charset="UTF-8" src="${pageContext.request.contextPath}/app/js/service/financeiro/saldoService.js"></script>

    </body>
</html>

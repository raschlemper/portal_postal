
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect( request.getContextPath() + "/index.jsp?msgLog=3");
    }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title> Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>
    <body> 
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
        <div id="wrapper">
            <jsp:include page="../includes/menu_agencia_bootstrap.jsp"></jsp:include>
            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">
                        <div class="row">
                            <div class="col-md-12">
                                <h4 class="page-header">
                                    <b class="text-primary"><i class="fa fa-gears"></i> Veículos</b> > <small>Combustível</small>
                                </h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <form name="veiculoCombustivelForm" action="${pageContext.request.contextPath}/veiculo/combustivel?action=save" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <label>Inserir combustível veículo</label>
                                        </li>
                                        <li class="list-group-item" id="campos">
                                            <div class="row form-horizontal">
                                                <div class="col-xs-12 col-sm-6 col-md-8 col-lg-8">
                                                    <label class="small">Veículo</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-car"></i></span>
                                                        <select class="form-control" name="veiculo"></select>
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Tipo</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <select class="form-control tipo" name="tipo"></select>
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">Data</label>
                                                    <div class="input-group">
                                                        <input class="form-control date" type="text" name="data" placeholder="Data" />
                                                        <span class="input-group-addon" ><i class="fa fa-calendar"></i></span>
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">Quantidade</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input type="text" autocomplete="off" name="quantidade" class="form-control number" placeholder="Quantidade"/>                                    
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">Valor Total</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-usd"></i></span>
                                                        <input type="text" autocomplete="off" name="valorTotal" class="form-control numeric" placeholder="Valor Total"/>                                    
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                                                    <label class="small">Valor Unitário</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-usd"></i></span>
                                                        <input type="text" autocomplete="off" name="valorUnitario" class="form-control numeric" placeholder="Valor Unitário"/>                                    
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Quilometragem Anterior</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input type="text" autocomplete="off" name="quilometragemInicial" class="form-control number" readonly/>
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Quilometragem Atual</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input type="text" autocomplete="off" name="quilometragemFinal" class="form-control number" placeholder="Quilometragem Atual" />
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Quilometragem Percorrida</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input type="text" autocomplete="off" name="quilometragemPercorrida" class="form-control number" readonly/>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <dd style="width: 100%;">
                                                <div class="buttons">
                                                    <button type="button" class="btn btn-success" name="salvar"><i class="fa fa-lg fa-spc fa-save"></i> SALVAR DADOS</button>
                                                </div>
                                            </dd>
                                        </li>
                                    </ul>
                                </form>     
                                
                                <div class="panel panel-default">
                                    <div class="panel-heading" >Lista com todos os abastecimentos</div>
                                    <div class="panel-body no-padding">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="datatable-manutencoes">
                                                <thead>
                                                    <tr>
                                                        <th>Placa</th>
                                                        <th>Tipo</th>
                                                        <th>Data</th>
                                                        <th>Km</th>
                                                        <th>Quantidade</th>
                                                        <th>Valor Unitário</th>
                                                        <th>Valor Total</th>
                                                        <th class="no-sort" width="100">Alterar</th>
                                                        <th class="no-sort" width="100">Excluir</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                </tbody>
                                            </table>
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
            var veiculoCombustivelCtrl = new VeiculoCombustivelController(veiculoCombustivelForm); 
            veiculoCombustivelCtrl.acoes.pesquisarTodos();
            Configuracao.messageModal();
        </script>
    </body>
</html>

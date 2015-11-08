
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
                                    <b class="text-primary"><i class="fa fa-gears"></i> Veículos</b> > <small>Seguro</small>
                                </h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <form name="veiculoSeguroForm" action="${pageContext.request.contextPath}/veiculo/seguro?action=save" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <label>Inserir seguro veículo</label>
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
                                                    <label class="small">Número</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input type="text" autocomplete="off" name="numeroSeguro" class="form-control number-full" placeholder="Número do seguro"/>                                    
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Assegurado</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input class="form-control" type="text" name="assegurado" placeholder="Assegurado" />
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Valor Franquia</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-usd"></i></span>
                                                        <input type="text" autocomplete="off" name="valorFranquia" class="form-control numeric" placeholder="Valor da franquia"/>                                    
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Indenização</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <select class="form-control" name="indenizacao"></select>
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
                                    <div class="panel-heading" >Lista com todos os seguros</div>
                                    <div class="panel-body no-padding">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="datatable-seguros">
                                                <thead>
                                                    <tr>
                                                        <th>Veículo</th>
                                                        <th>Número</th>
                                                        <th>Assegurado</th>
                                                        <th>Valor Franquia</th>
                                                        <th>Indenizacao</th>
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
            var veiculoSeguroCtrl = new VeiculoSeguroController(veiculoSeguroForm); 
            veiculoSeguroCtrl.init();
            veiculoSeguroCtrl.acoes.pesquisarTodos();
            Configuracao.messageModal();
        </script>
    </body>
</html>

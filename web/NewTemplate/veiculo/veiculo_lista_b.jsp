
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
                                    <b class="text-primary"><i class="fa fa-gears"></i> Cadastro</b> > <small>Veículos</small>
                                </h4>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <form name="veiculoForm" action="${pageContext.request.contextPath}/veiculo?action=save" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <label>Inserir novo veículo</label>
                                        </li>
                                        <li class="list-group-item" id="campos">
                                            <div class="row form-horizontal">
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Tipo</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-car"></i></span>
                                                        <select class="form-control" name="tipo"></select>
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Marca</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-car"></i></span>
                                                        <select class="form-control" name="marca"></select>
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Modelo</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-car"></i></span>
                                                        <select class="form-control" name="modelo"></select>
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Placa</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input type="text" autocomplete="off" name="placa" class="form-control placa" placeholder="Placa do veículo"/>                                    
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Ano Fabricação</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input type="text" autocomplete="off" name="anoFabricacao" class="form-control ano" placeholder="Ano de fabricação do veículo"/>                                    
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Ano Modelo</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input type="text" autocomplete="off" name="anoModelo" class="form-control ano" placeholder="Ano do modelo do veículo"/>                                    
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Chassis</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input type="text" autocomplete="off" name="chassis" class="form-control chassis" placeholder="Chassis do veículo"/>                                    
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Renavam</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input type="text" autocomplete="off" name="renavam" class="form-control renavam" placeholder="Renavam do veículo"/>                                    
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Quilometragem</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                                                        <input type="text" autocomplete="off" name="quilometragem" class="form-control number" placeholder="Quilometragem do veículo"/>                                    
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Tipo Combustível</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-info"></i></span>
                                                        <select class="form-control" name="combustivel"></select>
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Estado Veículo</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-info"></i></span>
                                                        <select class="form-control" name="status"></select>
                                                    </div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                                                    <label class="small">Situação</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-info"></i></span>
                                                        <select class="form-control" name="situacao"></select>
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
                                    <div class="panel-heading" >Lista com todos os veículos</div>
                                    <div class="panel-body no-padding">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="datatable-veiculos">
                                                <thead>
                                                    <tr>
                                                        <th>Modelo</th>
                                                        <th>Placa</th>
                                                        <th>Fab./Mod.</th>
                                                        <th>Renavam</th>
                                                        <th>Km</th>
                                                        <th>Situação</th>
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
            var veiculoCtrl = new VeiculoController(veiculoForm); 
            veiculoCtrl.init();
            veiculoCtrl.acoes.pesquisarTodos();
            Configuracao.messageModal();

            function AllTables() {
                StartDataTable('dataTables-example');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
            }

            $(document).ready(function() {
                LoadDataTablesScripts(AllTables);
            });
        </script>
    </body>
</html>

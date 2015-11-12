<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%
    /*AJAX*/
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    String resultado = "sessaoexpirada";

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) return;
    Integer idVeiculoSeguro = Integer.parseInt(request.getParameter("idVeiculoSeguro"));
%>
<form name="veiculoSeguroEditForm" action="${pageContext.request.contextPath}/veiculo/seguro?action=save" method="post">
    <ul class="list-unstyled">
        <li class="list-group-item">
            <div class="row form-horizontal">
                <input type="hidden" name="idVeiculoSeguro" value="<%= idVeiculoSeguro %>"/>
                <div class="col-xs-12 col-sm-6 col-md-8 col-lg-8">
                    <label class="small">Veículo</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-car"></i></span>
                        <select class="form-control" name="veiculo" disabled="true"></select>
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
                        <input type="text" autocomplete="off" name="valorFranquia" class="form-control numeric" placeholder="Valor da franquia do seguro"/>                                    
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
    </ul>
</form>   
<script type="text/javascript"> 
    $(function() {            
        var veiculoSeguroCtrl = new VeiculoSeguroController(veiculoSeguroEditForm); 
        veiculoSeguroCtrl.init(<%= idVeiculoSeguro %>);
    });            
</script>
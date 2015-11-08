<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%
    /*AJAX*/
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    String resultado = "sessaoexpirada";

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) return;
    Integer idVeiculoMulta = Integer.parseInt(request.getParameter("idVeiculoMulta"));
%>
<form name="veiculoMultaEditForm" action="${pageContext.request.contextPath}/veiculo/multa?action=save" method="post">
    <ul class="list-unstyled">
        <li class="list-group-item">
            <div class="row form-horizontal">
                <input type="hidden" name="idVeiculoMulta" value="<%= idVeiculoMulta %>"/>
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
                        <span class="input-group-addon" ><i class="fa fa-usd"></i></span>
                        <input type="text" autocomplete="off" name="numeroMulta" class="form-control number-full" placeholder="Número da multa"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Valor</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <input type="text" autocomplete="off" name="valor" class="form-control numeric" placeholder="Valor da multa"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Data</label>
                    <div class="input-group">
                        <input class="form-control date" type="text" name="data" placeholder="Data da multa" />
                        <span class="input-group-addon" ><i class="fa fa-calendar"></i></span>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Local</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <input class="form-control" type="text" name="local" placeholder="Local da multa (endereço)" />
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <label class="small">Descrição</label>
                    <textarea class="form-control" rows="3" name="descricao" placeholder="Descrição da multa"></textarea>       
                </div>
            </div>
        </li>
    </ul>
</form>   
<script type="text/javascript"> 
    $(function() {            
        var veiculoMultaCtrl = new VeiculoMultaController(veiculoMultaEditForm); 
        veiculoMultaCtrl.init(<%= idVeiculoMulta %>);
    });            
</script>
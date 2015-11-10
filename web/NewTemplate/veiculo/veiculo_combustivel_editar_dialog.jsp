<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%
    /*AJAX*/
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    String resultado = "sessaoexpirada";

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) return;
    Integer idVeiculoCombustivel = Integer.parseInt(request.getParameter("idVeiculoCombustivel"));
%>
<form name="veiculoCombustivelEditForm" action="${pageContext.request.contextPath}/veiculo/combustivel?action=save" method="post">
    <ul class="list-unstyled">
        <li class="list-group-item">
            <div class="row form-horizontal">
                <input type="hidden" name="idVeiculoCombustivel" value="<%= idVeiculoCombustivel %>"/>
                <div class="col-xs-12 col-sm-6 col-md-8 col-lg-8">
                    <label class="small">Veículo</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-car"></i></span>
                        <select class="form-control" name="veiculo" disabled="true"></select>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Tipo</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <select class="form-control" name="tipo"></select>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                    <label class="small">Data</label>
                    <div class="input-group">
                        <input class="form-control date" type="text" name="data" placeholder="Data do abastecimento" />
                        <span class="input-group-addon" ><i class="fa fa-calendar"></i></span>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                    <label class="small">Quantidade</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <input type="text" autocomplete="off" name="quantidade" class="form-control number" placeholder="Quantidade do abastecimento em litros"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                    <label class="small">Valor Total</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-usd"></i></span>
                        <input type="text" autocomplete="off" name="valorTotal" class="form-control numeric" placeholder="Valor total do abastecimento"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                    <label class="small">Valor Unitário</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-usd"></i></span>
                        <input type="text" autocomplete="off" name="valorUnitario" class="form-control numeric" placeholder="Valor unitário do abastecimento"/>                                    
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
                        <input type="text" autocomplete="off" name="quilometragemFinal" class="form-control number" readonly/>
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
            </div>
        </li>
    </ul>
</form>   
<script type="text/javascript"> 
    $(function() {            
        var veiculoCombustivelCtrl = new VeiculoCombustivelController(veiculoCombustivelEditForm); 
        veiculoCombustivelCtrl.init(<%= idVeiculoCombustivel %>);
    });            
</script>
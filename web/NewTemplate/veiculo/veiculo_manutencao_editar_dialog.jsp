<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%
    /*AJAX*/
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    String resultado = "sessaoexpirada";

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) return;
    Integer idVeiculoManutencao = Integer.parseInt(request.getParameter("idVeiculoManutencao"));
%>
<form name="veiculoManutencaoEditForm" action="${pageContext.request.contextPath}/veiculo/manutencao?action=save" method="post">
    <ul class="list-unstyled">
        <li class="list-group-item">
            <div class="row form-horizontal">
                <input type="hidden" name="idVeiculoManutencao" value="<%= idVeiculoManutencao %>"/>
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
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                    <label class="small">Quilometragem</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <input type="text" autocomplete="off" name="quilometragem" class="form-control number" placeholder="Quilometragem"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                    <label class="small">Valor</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-usd"></i></span>
                        <input type="text" autocomplete="off" name="valor" class="form-control numeric" placeholder="Valor"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Data</label>
                    <div class="input-group">
                        <input class="form-control date" type="text" name="data" placeholder="Data da manutenção" />
                        <span class="input-group-addon" ><i class="fa fa-calendar"></i></span>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Data Agendamento</label>
                    <div class="input-group">
                        <input class="form-control date" type="text" name="dataAgendamento" placeholder="Data de prevista para manutenção"/>
                        <span class="input-group-addon" ><i class="fa fa-calendar"></i></span>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Data Entrega</label>
                    <div class="input-group">
                        <input class="form-control date" type="text" name="dataEntrega" placeholder="Data de prevista para entrega"/>
                        <span class="input-group-addon" ><i class="fa fa-calendar"></i></span>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <label class="small">Descrição</label>
                    <textarea class="form-control" rows="3" name="descricao" placeholder="Descrição da manutenção"></textarea>       
                </div>
            </div>
        </li>
    </ul>
</form>   
<script type="text/javascript"> 
    $(function() {            
        var veiculoManutencaoCtrl = new VeiculoManutencaoController(veiculoManutencaoEditForm); 
        veiculoManutencaoCtrl.init(<%= idVeiculoManutencao %>);
    });            
</script>
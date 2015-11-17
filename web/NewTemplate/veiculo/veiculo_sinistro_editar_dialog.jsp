<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%
    /*AJAX*/
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");
    String resultado = "sessaoexpirada";

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD == null) return;
    Integer idVeiculoSinistro = Integer.parseInt(request.getParameter("idVeiculoSinistro"));
%>
<form name="veiculoSinistroEditForm" action="${pageContext.request.contextPath}/veiculo/sinistro?action=save" method="post">
    <ul class="list-unstyled">
        <li class="list-group-item">
            <div class="row form-horizontal">
                <input type="hidden" name="idVeiculoSinistro" value="<%= idVeiculoSinistro %>"/>
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
                        <input type="text" autocomplete="off" name="boletimOcorrencia" class="form-control number-full" placeholder="Número do boletim de ocorrência"/>                                    
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                    <label class="small">Data</label>
                    <div class="input-group">
                        <input class="form-control date" type="text" name="data" placeholder="Data do sinistro" />
                        <span class="input-group-addon" ><i class="fa fa-calendar"></i></span>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                    <label class="small">Local</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <input class="form-control" type="text" name="local" placeholder="Local do sinistro (endereço)" />
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                    <label class="small">Tipo</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <select class="form-control" name="tipo"></select>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
                    <label class="small">Responsável</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-asterisk"></i></span>
                        <select class="form-control" name="responsavel"></select>
                    </div>
                </div>
                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <label class="small">Descrição</label>
                    <textarea class="form-control" rows="3" name="descricao" placeholder="Descrição da sinistro"></textarea>       
                </div>
            </div>
        </li>
    </ul>
</form>   
<script type="text/javascript"> 
    $(function() {            
        var veiculoSinistroCtrl = new VeiculoSinistroController(veiculoSinistroEditForm); 
        veiculoSinistroCtrl.init(<%= idVeiculoSinistro %>);
    });            
</script>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    /*AJAX*/
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {

        int idTipoColeta = Integer.parseInt(request.getParameter("idTipoColeta"));
        Coleta.Entidade.TipoColeta tipoColeta = Coleta.Controle.contrTipoColeta.consultaTipoColetaById(idTipoColeta, nomeBD);
        String tipo = tipoColeta.getTipo();
%>
<form name='form5' action='../../ServEditarTipoColeta' method='post'>
    <ul class="list-unstyled">
        <li>
            <div class="row form-horizontal">
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Tipo da Coleta</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-truck"></i></span>
                        <input type="text" name="tipo" value='<%= tipo%>' class="form-control" placeholder="Tipo de Coleta" />
                    </div>
                    <input type='hidden' name='idTipoColeta' value='<%= idTipoColeta%>' />
                </div>
            </div>
        </li>
    </ul>
</form>
<%}%>
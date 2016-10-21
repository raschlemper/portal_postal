<%@page import="Controle.ContrGrupoFaturamento"%>
<%@page import="Entidade.GrupoFaturamento"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    /*AJAX*/
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {

        int idGrupoFaturamento = Integer.parseInt(request.getParameter("idGrupoFaturamento"));
        GrupoFaturamento grupo = ContrGrupoFaturamento.consultaById(idGrupoFaturamento, nomeBD);
        //String tipo = tipoColeta.get();
%>
<form name='form5' action='../../ServEditarGrupoFat' method='post'>
    <ul class="list-unstyled">
        <li>
            <div class="row form-horizontal">
                <div class="col-xs-12 col-sm-8 col-md-6 col-lg-6">
                    <label class="small">Nome do Grupo</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-group"></i></span>
                        <input type="text" name="nome" value='<%= grupo.getNome() %>' class="form-control" placeholder="Nome do grupo" />
                    </div>
                </div>
            </div>
            <div class="row form-horizontal">
                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
                    <label class="small">Sigla do Grupo</label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-tag"></i></span>
                        <input type="text" name="sigla" value='<%= grupo.getSigla() %>' class="form-control" placeholder="Sigla do grupo" />
                    </div>
                    <input type='hidden' name='idGrupoFaturamento' value='<%= idGrupoFaturamento%>' />
                </div>
            </div>
        </li>
    </ul>
</form>
<%}%>
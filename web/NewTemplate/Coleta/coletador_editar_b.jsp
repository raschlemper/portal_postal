<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%
    /*AJAX*/
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {

        int idColetador = Integer.parseInt(request.getParameter("idColetador"));
        Coleta.Entidade.Coletador col = Coleta.Controle.contrColetador.consultaColetadoresById(idColetador, nomeBD);
        String nome = col.getNome();
        String telefone = col.getTelefone();
        String login = col.getLogin();
        int rota = col.getRota();
%>
<form name='form5' action='../../ServEditarColetador' method='post'>
    <ul class="list-unstyled">
        <li class="list-group-item list-group-heading">
            <label><%= nome%></label>
        </li>
        <li class="list-group-item ">
            <div class="row">
            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                <label class="small">Nome do Coletador</label>
                <div class="input-group">
                    <span class="input-group-addon" ><i class="fa fa-user"></i></span>
                    <input type="text" name="nome"  class="form-control" placeholder="Nome do Coletador"  value='<%= nome%>' />
                </div>
            </div>
            </div>
        </li>                         
        <li class="list-group-item">
            <div class="row">
            <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                <label class="small">Celular do Coletador</label>
                <div class="input-group">
                    <span class="input-group-addon" ><i class="fa fa-phone"></i></span>
                    <input type="text" name="telefone" maxlength="14" onKeyPress="mascara(this, maskTelefone)"  class="form-control" placeholder="Celular" value='<%= telefone%>' />
                </div>
            </div>
            </div>
        </li>
    </ul>
    <div class="buttons">
        <input type="hidden" name="senha2" />
        <input type="hidden" name="senha" />
        <input type='hidden' name='rota' value='<%= rota%>' />
        <input type='hidden' name='login' value='login' />
        <input type='hidden' name='loginaux' value='<%= login%>' />
        <input type='hidden' name='idColetador' value='<%= idColetador%>'/>
    </div>
</form>
<%}%>
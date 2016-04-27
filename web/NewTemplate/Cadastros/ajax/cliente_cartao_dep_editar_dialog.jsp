<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache");
    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idDep = Integer.parseInt(request.getParameter("idDepartamento"));
        ClientesDeptos dep = ContrClienteDeptos.consultaDeptoById(idCliente, idDep, nomeBD);
        String cartao = request.getParameter("cartao");
        String depto = request.getParameter("depto");
%>
<form name='form5' action='../../ServEditarCartaoDep' method='post'>
    <ul class="list-unstyled">
        <li class="list-group-item">
            <div class="row form-horizontal">
                <div class="col-xs-12 col-sm-8 col-md-8 col-lg-8">
                    <label class="small">Nome do Departamento: </label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-sitemap"></i></span>
                        <input class="form-control" autocomplete="off" type='text' name='nome' placeholder="Nome do Depto." value='<%= depto%>' maxlength="40" />
                    </div>
                </div>
                <div class="col-xs-12 col-sm-4 col-md-4 col-lg-4">
                    <label class="small">Cartão de Postagem: </label>
                    <div class="input-group">
                        <span class="input-group-addon" ><i class="fa fa-credit-card"></i></span>
                        <input class="form-control" autocomplete="off" type='text' name='cartao' placeholder="Depto. sem cartão" value='<%= cartao%>' maxlength="10" />
                    </div>
                </div>
            </div>
        </li>                
        <li class="list-group-item list-group-heading">
            <b>Possui endereço diferente? </b>
            <input type="checkbox" name="temEndereco" id="temEndereco" value="1" <%if (dep.getTemEndereco() == 1) {%>checked="true"<%}%> data-size="mini" data-toggle="toggle" data-on="SIM" data-off="NÃO" data-onstyle="success" data-offstyle="danger" />
        </li>
        <li class="list-group-item" id="liTemEndereco" <%if (dep.getTemEndereco() == 0) {%> style="display:none;" <%}%>>
            <div class="row form-horizontal">
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9">
                    <label class="small">Nome: </label>
                    <input class="form-control" autocomplete="off" type='text' name='nomeEndereco' placeholder="Nome do Endereço" value='<%= dep.getNomeEndereco()%>' maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                    <label class="small">CEP: </label>
                    <input class="form-control" autocomplete="off" type='text' name='cep' placeholder="CEP" value='<%= dep.getCep()%>' maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9">
                    <label class="small">Logradouro: </label>
                    <input class="form-control" autocomplete="off" type='text' name='logradouro' placeholder="Logradouro" value='<%= dep.getLogradouro()%>' maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                    <label class="small">Número: </label>
                    <input class="form-control" autocomplete="off" type='text' name='numero' placeholder="Número" value='<%= dep.getNumero()%>' maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                    <label class="small">Complemento: </label>
                    <input class="form-control" autocomplete="off" type='text' name='complemento' placeholder="Complemento" value='<%= dep.getComplemento()%>' maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                    <label class="small">Bairro: </label>
                    <input class="form-control" autocomplete="off" type='text' name='bairro' placeholder="Bairro" value='<%= dep.getBairro()%>' maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-9 col-md-9 col-lg-9">
                    <label class="small">Cidade: </label>
                    <input class="form-control" autocomplete="off" type='text' name='cidade' placeholder="Cidade" value='<%= dep.getCidade()%>' maxlength="40" />
                </div>
                <div class="col-xs-12 col-sm-3 col-md-3 col-lg-3">
                    <label class="small">UF: </label>
                    <select name="uf" class="form-control">
                        <option value="" selected='true'>--</option>
                        <option value="AC" <%if(dep.getUf().equals("AC")){%>selected='true'<%}%> >AC</option>
                        <option value="AL" <%if(dep.getUf().equals("AL")){%>selected='true'<%}%> >AL</option>
                        <option value="AP" <%if(dep.getUf().equals("AP")){%>selected='true'<%}%> >AP</option>
                        <option value="AM" <%if(dep.getUf().equals("AM")){%>selected='true'<%}%> >AM</option>
                        <option value="BA" <%if(dep.getUf().equals("BA")){%>selected='true'<%}%> >BA</option>
                        <option value="CE" <%if(dep.getUf().equals("CE")){%>selected='true'<%}%> >CE</option>
                        <option value="DF" <%if(dep.getUf().equals("DF")){%>selected='true'<%}%> >DF</option>
                        <option value="ES" <%if(dep.getUf().equals("ES")){%>selected='true'<%}%> >ES</option>
                        <option value="GO" <%if(dep.getUf().equals("GO")){%>selected='true'<%}%> >GO</option>
                        <option value="MA" <%if(dep.getUf().equals("MA")){%>selected='true'<%}%> >MA</option>
                        <option value="MT" <%if(dep.getUf().equals("MT")){%>selected='true'<%}%> >MT</option>
                        <option value="MS" <%if(dep.getUf().equals("MS")){%>selected='true'<%}%> >MS</option>
                        <option value="MG" <%if(dep.getUf().equals("MG")){%>selected='true'<%}%> >MG</option>
                        <option value="PA" <%if(dep.getUf().equals("PA")){%>selected='true'<%}%> >PA</option>
                        <option value="PB" <%if(dep.getUf().equals("PB")){%>selected='true'<%}%> >PB</option>
                        <option value="PR" <%if(dep.getUf().equals("PR")){%>selected='true'<%}%> >PR</option>
                        <option value="PE" <%if(dep.getUf().equals("PE")){%>selected='true'<%}%> >PE</option>
                        <option value="PI" <%if(dep.getUf().equals("PI")){%>selected='true'<%}%> >PI</option>
                        <option value="RJ" <%if(dep.getUf().equals("RJ")){%>selected='true'<%}%> >RJ</option>
                        <option value="RN" <%if(dep.getUf().equals("RN")){%>selected='true'<%}%> >RN</option>
                        <option value="RS" <%if(dep.getUf().equals("RS")){%>selected='true'<%}%> >RS</option>
                        <option value="RO" <%if(dep.getUf().equals("RO")){%>selected='true'<%}%> >RO</option>
                        <option value="RR" <%if(dep.getUf().equals("RR")){%>selected='true'<%}%> >RR</option>
                        <option value="SC" <%if(dep.getUf().equals("SC")){%>selected='true'<%}%> >SC</option>
                        <option value="SP" <%if(dep.getUf().equals("SP")){%>selected='true'<%}%> >SP</option>
                        <option value="SE" <%if(dep.getUf().equals("SE")){%>selected='true'<%}%> >SE</option>
                        <option value="TO" <%if(dep.getUf().equals("TO")){%>selected='true'<%}%> >TO</option>
                    </select>     
                </div>
            </div>
        </li>
    </ul>
    <div class="alert alert-danger no-margin">
        <b>ATENÇÃO!</b> Caso não tenha cartão de postagem deixe o campo em branco!
    </div>
    <input type='hidden' name='idCliente' value='<%= idCliente%>' />
    <input type='hidden' name='idDepartamento' value='<%= idDep%>' />
</form>

<script>
    $(function () {
        $('#temEndereco').bootstrapToggle({
            width: 50
        });
    });
    $('#temEndereco').change(function () {
        $('#liTemEndereco').slideToggle();
    });
</script>
<%} else {%>
sessaoexpirada
<%}%>
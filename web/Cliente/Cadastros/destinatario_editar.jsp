
<%@page import="Util.FormataString"%>
<%@page import="Entidade.Destinatario"%>
<%@page import="Controle.contrDestinatario"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    /*
     * AJAX
     */
    response.setContentType("text/xml");
    response.setHeader("Cache-Control", "no-cache");

    String nomeBD = (String) session.getAttribute("empresa");
    if (nomeBD != null) {

        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idDestinatario = Integer.parseInt(request.getParameter("idDestinatario"));
        Destinatario des = contrDestinatario.consultaDestinatarioById(idDestinatario, idCliente, nomeBD);
        
      
        String email ="";
        if(des.getEmail() != null){
            email = des.getEmail();
        }
        String cel ="";
        if(des.getCelular() != null){
            cel = des.getCelular();
        }
        String tag ="";
        if(des.getTags() != null){
            tag = des.getTags();
        }
%>
<div style="width: 100%; margin: 15px;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Editar Destinatário</div>
    </div>
    <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>

    <form name='form5' action='../../ServEditarDestinatario' method='post'>
        <ul style="width: 95%; text-align: left;" class="ul_formulario">
            <li>
                <dd>
                    <label>Nome / Razão Social</label>
                    <input type="text" name="nome" size="70" value="<%= des.getNome()%>" />
                </dd>
                <dd>
                    <label>CPF / CNPJ</label>
                    <input type="text" name="cpf_cnpj" maxlength="18" value="<%= des.getCpf_cnpj()%>"  onkeydown="mascara(this, maskCpfCnpj);" />
                </dd>
                <dd>
                    <label>Empresa</label>
                    <input type="text" name="empresa" maxlength="40" value="<%= des.getEmpresa()%>" />
                </dd>
                <dd>
                    <label>CEP<b class="obg">*</b><a onclick="window.open('http://www.buscacep.correios.com.br', 'CORREIOS');" ><img src="../../imagensNew/question.png" /></a></label>
                    <input type="text" name="cep" id="cep2" size="8" value="<%= FormataString.formataCep(des.getCep())%>" maxlength="9" onkeypress="mascara(this, maskCep);
                            funcEnter(event);" onblur="verPesquisarCepDestEdit(this.value);" />
                </dd>
            </li>

            <li>
                <dd>
                    <label>Endereço</label>
                    <input type="text" name="endereco" id="endereco2" size="80" value="<%= des.getEndereco()%>" />
                </dd>
                <dd>
                    <label>Número</label>
                    <input type="text" name="numero" id="numero2" size="10" maxlength="8" value="<%= des.getNumero()%>" onkeypress="mascara(this, maskNumero)" />
                    <input type="checkbox" name="sn" id="sn2" value="S/N" onclick="semNumero2();" /> <span style="font-size: 14px;font-weight: bold">S/N</span>
                </dd>
                <dd>
                    <label>Complemento</label>
                    <input type="text" name="complemento" id="complemento2"  maxlength="50" value="<%= des.getComplemento()%>" />
                </dd>
                <dd>
                    <label>Bairro</label>
                    <input type="text" name="bairro" size="40" id="bairro2"  maxlength="50" value="<%= des.getBairro()%>" />
                </dd>
            </li>
            <li>
                <dd>
                    <label>Cidade<b class="obg">*</b></label>
                    <input type="text" name="cidade" id="cidade2"  maxlength="50" readonly value="<%= des.getCidade()%>" />
                </dd>


                <dd>
                    <label>UF<b class="obg">*</b></label>
                    <input type="hidden" name="uf" id="uf3" value="<%= des.getUf()%>" />
                    <select name="uf2" id="uf4" disabled >
                        <option value="AC" <%if (des.getUf().equals("AC")) {%> selected <%}%> >AC</option>
                        <option value="AL" <%if (des.getUf().equals("AL")) {%> selected <%}%>>AL</option>
                        <option value="AP" <%if (des.getUf().equals("AP")) {%> selected <%}%>>AP</option>
                        <option value="AM" <%if (des.getUf().equals("AM")) {%> selected <%}%>>AM</option>
                        <option value="BA" <%if (des.getUf().equals("BA")) {%> selected <%}%>>BA</option>
                        <option value="CE" <%if (des.getUf().equals("CE")) {%> selected <%}%>>CE</option>
                        <option value="DF" <%if (des.getUf().equals("DF")) {%> selected <%}%>>DF</option>
                        <option value="ES" <%if (des.getUf().equals("ES")) {%> selected <%}%>>ES</option>
                        <option value="GO" <%if (des.getUf().equals("GO")) {%> selected <%}%>>GO</option>
                        <option value="MA" <%if (des.getUf().equals("MA")) {%> selected <%}%>>MA</option>
                        <option value="MT" <%if (des.getUf().equals("MT")) {%> selected <%}%>>MT</option>
                        <option value="MS" <%if (des.getUf().equals("MS")) {%> selected <%}%>>MS</option>
                        <option value="MG" <%if (des.getUf().equals("MG")) {%> selected <%}%>>MG</option>
                        <option value="PA" <%if (des.getUf().equals("PA")) {%> selected <%}%>>PA</option>
                        <option value="PB" <%if (des.getUf().equals("PB")) {%> selected <%}%>>PB</option>
                        <option value="PR" <%if (des.getUf().equals("PR")) {%> selected <%}%>>PR</option>
                        <option value="PE" <%if (des.getUf().equals("PE")) {%> selected <%}%>>PE</option>
                        <option value="PI" <%if (des.getUf().equals("PI")) {%> selected <%}%>>PI</option>
                        <option value="RJ" <%if (des.getUf().equals("RJ")) {%> selected <%}%>>RJ</option>
                        <option value="RN" <%if (des.getUf().equals("RN")) {%> selected <%}%>>RN</option>
                        <option value="RS" <%if (des.getUf().equals("RS")) {%> selected <%}%>>RS</option>
                        <option value="RO" <%if (des.getUf().equals("RO")) {%> selected <%}%>>RO</option>
                        <option value="RR" <%if (des.getUf().equals("RR")) {%> selected <%}%>>RR</option>
                        <option value="SC" <%if (des.getUf().equals("SC")) {%> selected <%}%>>SC</option>
                        <option value="SP" <%if (des.getUf().equals("SP")) {%> selected <%}%>>SP</option>
                        <option value="SE" <%if (des.getUf().equals("SE")) {%> selected <%}%>>SE</option>
                        <option value="TO" <%if (des.getUf().equals("TO")) {%> selected <%}%>>TO</option>
                    </select>
                </dd> 
            </li>
            <li>
                <dd>
                    <label>E-mail</label>
                    <input type="text" name="email" size="35" value="<%=email %>"  maxlength="100" />
                </dd>
                <dd>
                    <label>Celular</label>
                    <input type="text" name="celular" value="<%=cel %>"  maxlength="15" onkeypress="mascara(this, maskTelefone);" />
                </dd>
            </li>
            <li>
                <dd>
                    <label>Tags </label>
                    <input type="text" name="tags" id="tags" size="120" placeholder=" cadstre aqui suas tags" value="<%=tag%>" />
                </dd>
            </li>

            <li>
                <div class="buttons">
                    <input type='hidden' name='idCliente' value='<%= idCliente%>' />
                    <input type='hidden' name='idDestinatario' value='<%= idDestinatario%>' />
                    <button type="button" class="positive" onclick="preencherCamposEdit();"><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
                    <button type="button" class="negative" onClick='chamaDivProtecao();'><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
                </div>
            </li>
    </form>

</div>
<%}%>
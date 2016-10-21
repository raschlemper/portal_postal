
<%@page import="Util.FormataString"%>
<%@page import="Entidade.Remetente"%>
<%@page import="Controle.contrRemetente"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            /*AJAX*/
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");

            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD != null) {

                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                int idRemetente = Integer.parseInt(request.getParameter("idRemetente"));
                Remetente rem = contrRemetente.consultaRemetenteById(idRemetente, idCliente, nomeBD);
%>

<div style="width: 100%; margin: 15px;">
    <div style="width: 95%; text-align: left;">
        <div style='float:right;'><a onclick='chamaDivProtecao();' href='#' class='botaoClose'>Fechar</a></div>
        <div id='titulo1'>Editar Destinatário</div>
    </div>
    <img style="margin-bottom: 25px;" width="100%" src="../../imagensNew/linha.jpg"/>

    <form name='form5' action='../../ServEditarRemetente' method='post' enctype="multipart/form-data">
        <ul style="width: 95%; text-align: left;" class="ul_formulario">
            <li class="titulo">
                <dd>LOGO DO REMETENTE</dd>
            </li>
            <li>
                <dd>
                    <img src="../../<%= rem.getUrl_logo() %>" border="0" height="100" />
                </dd>
                <dd>
                    <label>ESCOLHA UMA IMAGEM</label>
                    <input type="file" name="url_logo" />
                    <span style="color:red;">*escolha uma imagem <b>SOMENTE</b> se você deseja alterar a logo.</span>
                </dd>
            </li>
            <li class="titulo">
                <dd>DADOS DO REMETENTE</dd>
            </li>
            <li>
                <dd>
                    <label>Nome / Razão Social</label>
                    <input type="text" name="nome" size="50" value="<%= rem.getNome() %>" />
                </dd>
                <dd>
                    <label>CPF / CNPJ</label>
                    <input type="text" name="cpf_cnpj" value="<%= rem.getCpf_cnpj() %>" />
                </dd>
                <dd>
                    <label>CEP</label>
                    <input type="text" name="cep" size="10" value="<%= FormataString.formataCep(rem.getCep()) %>" maxlength="9" onkeypress="mascara(this, maskCep)" />
                </dd>
            </li>
            <li>
                <dd>
                    <label>Endereço</label>
                    <input type="text" name="endereco" size="50" value="<%= rem.getEndereco() %>" />
                </dd>
                <dd>
                    <label>Número</label>
                    <input type="text" name="numero" size="10" value="<%= rem.getNumero() %>" maxlength="5" onkeypress="mascara(this, maskNumero)" />
                </dd>
                <dd>
                    <label>Complemento</label>
                    <input type="text" name="complemento" value="<%= rem.getComplemento() %>" />
                </dd>
                <dd>
                    <label>Bairro</label>
                    <input type="text" name="bairro" value="<%= rem.getBairro() %>" />
                </dd>
                <dd>
                    <label>Cidade</label>
                    <input type="text" name="cidade" value="<%= rem.getCidade() %>" />
                </dd>
                <dd>
                    <label>UF</label>
                    <input type="text" name="uf" size="2" value="<%= rem.getUf() %>" />
                </dd>
            </li>
        </ul>

        <div class="buttons">
            <input type='hidden' name='url_old' value='<%= rem.getUrl_logo() %>' />
            <input type='hidden' name='idCliente' value='<%= idCliente%>' />
            <input type='hidden' name='idRemetente' value='<%= idRemetente %>' />
            <button type="button" class="positive" onclick="preencherCamposEdit();"><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
            <button type="button" class="negative" onClick='chamaDivProtecao();'><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
        </div>
    </form>

</div>
<%}%>
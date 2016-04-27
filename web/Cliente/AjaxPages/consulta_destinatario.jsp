<%@page import="Entidade.Clientes"%>
<%@page import="Entidade.ClientesUsuario"%>
<%@page import="Util.FormataString"%>
<%@page import="Controle.contrDestinatario"%>
<%@page import="Entidade.Destinatario"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD != null) {
        
        ClientesUsuario us = (ClientesUsuario) session.getAttribute("usuario_sessao_cliente");
        Clientes cli = (Clientes) session.getAttribute("cliente");
        ArrayList<Integer> dps = us.getDepartamentos();

        int idCliente = (Integer) session.getAttribute("idCliente");
        String cod = request.getParameter("codigo");
        String nom = request.getParameter("nome");
        String emp = request.getParameter("empresa");
        String end = request.getParameter("endereco");
        String cep1 = request.getParameter("cep");
        String tags = request.getParameter("tags");
        String multi = request.getParameter("multi");

        ArrayList<Destinatario> lista = contrDestinatario.pesquisa(idCliente, cod, nom, "", end, end, cep1, emp, end, nomeBD, tags);                                    
        if (lista.size() >= 1) {
%>

<table id="barraAtendimento" border="0" style="width: 100%;">
    <tr>
        <td align="left" style="font-weight:bold;font-size:12px;">
            Pesquisa Rápida:
            <select style='min-width:150px;' id="columnsDes" onchange="sorterDes.search('queryDes')"></select>
            <input type="text" id="queryDes" onkeyup="sorterDes.search('queryDes')" placeholder="Digite aqui a sua pesquisa..." />
            <a style="text-decoration:none;font-weight:bold;font-size:11px;" href="javascript:document.getElementById('queryDes').value='';sorterDes.reset()">RESTAURAR PADRÕES</a>
        </td>
        <td align="right">
            <div class="details" style="clear:both;">
                <div>Resultado <span id="startrecordDes"></span>-<span id="endrecordDes"></span> de <span id="totalrecordsDes"></span></div>
            </div>
        </td>
    </tr>
</table>
<div style='max-width:100%;overflow:auto;'>
    <table cellpadding="0" cellspacing="0" border="0" id="tableDes" class="tinytable">
        <thead>
            <tr>
                <th width='10' class="nosort"><h3><input type="checkbox" id="allCheck" onclick="myFunction();"/></h3></th>
                <th width='30'><h3>CÓD.</h3></th>
                <th><h3>NOME / RAZÃO SOCIAL</h3></th>
                <th><h3>EMPRESA</h3></th>
                <th><h3>CIDADE/UF</h3></th>
                <th><h3>ENDEREÇO</h3></th>
                <th width='80'><h3>CEP</h3></th>
                <th><h3>TAGS</h3></th>
            </tr>
        </thead>
        <tbody>
            <%
                for (int i = 0; i < lista.size(); i++) {
                    Destinatario dest = lista.get(i);
                    if (cli.getSeparar_destinatarios() == 0 || dest.getIdDepartamento() == 0 || dps.contains(dest.getIdDepartamento())) {
                                        
                    int id = dest.getIdDestinatario();
                    String nome = FormataString.removeAccentsToUpper(dest.getNome());
                    String empresa = FormataString.removeAccentsToUpper(dest.getEmpresa());
                    String cidade = FormataString.removeAccentsToUpper(dest.getCidade());
                    String uf = dest.getUf();
                    String endereco = FormataString.removeAccentsToUpper(dest.getEndereco());
                    String numero = dest.getNumero();
                    String cpf = dest.getCpf_cnpj();
                    String bairro = FormataString.removeAccentsToUpper(dest.getBairro());
                    String complemento = FormataString.removeAccentsToUpper(dest.getComplemento());
                    String cep = dest.getCep();
                    String tag = "";
                    if (dest.getTags() != null) {
                        tag = dest.getTags();
                    }

            %>
            <tr align='center' style="font-size: 10px;">
                <%if (multi.equals("multi")) {%>
                <td>
                    <input type="checkbox" name="cks_dest" id="cks_dest" value="<%= dest.getIdDestinatario()%>" />
                    <input type="hidden" name="nome_multi_<%= id%>" id="nome_multi_<%= id%>" value="<%= nome%>" />
                    <input type="hidden" name="empresa_multi_<%= id%>" id="empresa_multi_<%= id%>" value="<%= empresa%>" />
                    <input type="hidden" name="endereco_multi_<%= id%>" id="endereco_multi_<%= id%>" value="<%= endereco%>" />
                    <input type="hidden" name="numero_multi_<%= id%>" id="numero_multi_<%= id%>" value="<%= numero%>" />
                    <input type="hidden" name="compl_multi_<%= id%>" id="compl_multi_<%= id%>" value="<%= complemento%>" />
                    <input type="hidden" name="cidade_multi_<%= id%>" id="cidade_multi_<%= id%>" value="<%= cidade%>" />
                    <input type="hidden" name="bairro_multi_<%= id%>" id="bairro_multi_<%= id%>" value="<%= bairro%>" />
                    <input type="hidden" name="uf_multi_<%= id%>" id="uf_multi_<%= id%>" value="<%= uf%>" />
                    <input type="hidden" name="cpf_multi_<%= id%>" id="cpf_multi_<%= id%>" value="<%= cpf%>" />
                    <input type="hidden" name="cep_multi_<%= id%>" id="cep_multi_<%= id%>" value="<%= cep%>" />
                    <input type="hidden" name="tags_multi_<%= id%>" id="tags_multi_<%= id%>" value="<%= tag%>" />
                </td>
                <%} else {%>
                <td><img style="cursor: pointer;" class="link_img" src="../../imagensNew/user_plus.png" onclick="copiaDadosDest('<%= dest.getIdDestinatario()%>', '<%= nome%>', '<%= empresa%>', '<%= cpf%>', '<%= cidade%>', '<%= uf%>', '<%= bairro%>', '<%= endereco%>', '<%= numero%>', '<%= complemento%>', '<%= cep%>');" /></td>
                <%}%>
                <td><%= dest.getIdDestinatario()%></td>
                <td><%= nome%></td>
                <td><%= empresa%></td>
                <td><%= cidade + "/" + uf%></td>
                <td><%= endereco + ", " + numero%></td>
                <td><%= cep%></td>
                <td><%= tag%></td>
            </tr>
            <%}}%>
        </tbody>
    </table>
</div>
<div id="tablefooter" align='center'>
    <div align="left" style='float:left; width:20%;'>
        <select onchange="sorterDes.size(this.value)">
            <option value="5">5</option>
            <option value="10">10</option>
            <option value="20" selected="selected">20</option>
            <option value="50">50</option>
            <option value="100">100</option>
        </select>
        <span>Linhas por Página</span>
    </div>
    <div id="tablenavDes" class="tablenav">
        <div>
            <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1, true)" />
            <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)" />
            <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)" />
            <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1, true)" />
            <select style="margin-left:5px;" id="pagedropdownDes"></select>
            <a style="margin-left:10px;" href="javascript:sorterDes.showall()">Ver Tudo</a>
        </div>
    </div>
    <div id="tablelocation">
        <div class="page">Página <span id="currentpageDes"></span> de <span id="totalpagesDes"></span></div>
    </div>
    <br/><br/><br/>
    <img width="100%" src="../../imagensNew/linha.jpg"/>
    <%if (multi.equals("multi")) {%>
    <div style="margin: 30px 0 60px 0;" class="buttons">
        <button type="button" class="positive" onclick="copiaDadosDestMulti(document.getElementsByName('cks_dest'));"><img src="../../imagensNew/tick_circle.png" /> ADICIONAR DESTINATÁRIOS</button>
        <button type="button" class="negative" onclick="chamaDivProtecao();"><img src="../../imagensNew/cross_circle.png" /> CANCELAR</button>
    </div>
    <br/>
    <%}%>
</div>
<%} else {%>
<div align='center' style='padding-top:25px;background:#fc8878;color:black;height:50px;width:100%;font-size:20px;font-weight:bold;'>Nenhum Destinatário Encontrado!</div>
<%}%>
<%} else {%>
sessaoexpirada
<%}%>
<%@page import="caixapostal.entity.DadosAdicionais"%>
<%@page import="caixapostal.entity.ObjetoInterno"%>
<%@page import="caixapostal.filter.FilterObjetos"%>
<%@page import="caixapostal.dao.CaixaPostalDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
String nomeBD = (String) session.getAttribute("nomeBD");
Integer idObjeto = Integer.parseInt(request.getParameter("idObjeto"));

FilterObjetos filter = new FilterObjetos();
filter.setIdObjeto(idObjeto);
filter.setNomeDB(nomeBD);
CaixaPostalDao caixaPostalDao = new CaixaPostalDao(filter.getNomeDB());
ObjetoInterno objetoInterno = caixaPostalDao.findById(filter);
DadosAdicionais destinatario = objetoInterno.getDestinatario();


%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>
    </head>
    <body>
        <form id="alteracaoEndereco" method="post" name="alteracaoEndereco" action="../../ServAlteracaoEndereco">
            <input type="hidden" name="idObjetoInterno" value="<%=idObjeto%>">
         <ul style="width: 95%; text-align: left;" class="ul_formulario">
            <li class="titulo">
            <dd>DADOS DO DESTINATÁRIO</dd>
            </li>
        <li>
            <dd>
                <label>Nome</label>
                <input id="nome" readonly="true" type="text" name="nome" size="101" value="<%=destinatario.getNome()%>" />
            </dd>
        </li>
        <li>
             <dd>
                <label>CEP</label>
                <input id="cep" type="text" name="cep" value="<%=destinatario.getCep()%>" maxlength="9" onkeypress="mascara(this, maskCep);" onblur="verPesquisarCepDest(this.value)"/>
            </dd>
            <dd>
                <label>Logradouro</label>
                <input id="endereco" type="text" name="endereco" size="50" value="<%=destinatario.getLogradouro()%>" />
            </dd>
            <dd>
                <label>Numero</label>
                <input id="numero" type="text" name="numero" value="<%=destinatario.getNumero()%>" />
            </dd>
        </li>
        <li>
             <dd>
                <label>Complemento</label>
                <input type="text" name="complemento" value="<%=destinatario.getComplemento()==null?"":destinatario.getComplemento()%>" />
            </dd>
            <dd>
                <label>Bairro</label>
                <input id="bairro" type="text" size="50" name="bairro" value="<%=destinatario.getBairro()%>" />
            </dd>
            <dd>
                <label>Cidade</label>
                <input readonly="true" id="cidade" type="text" name="cidade" value="<%=destinatario.getCidade()%>" />
            </dd>
        </li>
        <li>
            <dd>
            <label>UF</label>
            <input readonly="true" id="uf" type="text" name="uf" maxlength="2" value="<%=destinatario.getUf()%>" />
                <input id="uf2" type="hidden" name="uf" maxlength="2"  />
            </dd>
        </li>
        <li>
            <dd style="color:red;font-size: 12px;">
                <b>*Conferir os dados do endereço antes de enviar.</b>
            </dd>
        </li>
        </ul>
        <p align="center">
        <button style="padding:5px;" type="button" class="positive" onclick="alterarEndereco();"><img src="../../imagensNew/tick_circle.png">Alterar endereço</button>
        <button style="padding:5px;margin-left:50px;" type="button" class="negative" onclick="chamaDivProtecao();"><img src="../../imagensNew/cross_circle.png"> CANCELAR</button>
        </p>
    </form>
</body>
</html>

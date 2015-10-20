<%@page import="Util.FormataString"%>
<%@page import="Util.FaixaCep"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page import = "java.util.Calendar, java.util.Locale, java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%
            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD == null) {
                response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
            } else {

                int nivelUsuarioEmp2 = (Integer) session.getAttribute("nivelUsuarioEmp");
                String nomeEmpresa = Controle.contrEmpresa.nomeEmpresaByNomeBD(nomeBD);

                int idCli = (Integer) session.getAttribute("idCliente");
                Entidade.Clientes cli = Controle.contrCliente.consultaClienteById(idCli, nomeBD);
                int codigo = cli.getCodigo();
                String nomeCliente = cli.getNome();
                String nomeFantasia = cli.getNomeFantasia();
                String endereco = cli.getEndereco();
                String telefone = cli.getTelefone();
                String bairro = cli.getBairro();
                String cidade = cli.getCidade();
                String uf = cli.getUf();
                int cep = cli.getCep();
                String numero = cli.getNumero();
                String url_logo = cli.getUrl_logo();
                String email = cli.getEmail();
                String cnpj = cli.getCnpj();
                String complemento = cli.getComplemento();
                String temp = "";
                if (cnpj != null && cnpj.trim().length() > 13) {
                    temp = cnpj.substring(0, 2) + ".";
                    temp += cnpj.substring(2, 5) + ".";
                    temp += cnpj.substring(5, 8) + "/";
                    temp += cnpj.substring(8, 12) + "-";
                    temp += cnpj.substring(12, 14);
                }

        %>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Meu Cadastro</title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>

        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]><link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" /><![endif]-->
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript">
            function preencherCampos() {
                var form = document.form1;

                if (form.url_logo.value != "") {
                    var indexA = form.url_logo.value.lastIndexOf(".");
                    var indexB = form.url_logo.value.length;
                    var ext = form.url_logo.value.substring(indexA, indexB).toUpperCase();
                    if (ext != ".PNG" && ext != ".JPG" && ext != ".JPEG" && ext != ".GIF" && ext != ".BMP") {
                        alert("A imagem da logo deve ser uma imagem !");
                        return false;
                    }
                }
                /*
                 if(form.nome.value==""){
                 alert('Preencha o NOME do remetente!');
                 return false;
                 }
                 if(form.cep.value==""){
                 alert('Preencha o CEP do remetente!');
                 return false;
                 }
                 if(form.endereco.value==""){
                 alert('Preencha o ENDEREÇO do remetente!');
                 return false;
                 }
                 if(form.numero.value==""){
                 alert('Preencha o NÚMERO do remetente!');
                 return false;
                 }
                 if(form.cidade.value==""){
                 alert('Preencha a CIDADE do remetente!');
                 return false;
                 }
                 if(form.uf.value==""){
                 alert('Preencha a UF do remetente!');
                 return false;
                 }*/
                form.submit();
            }
        </script>

    </head>
    <body>
        <div id="divInteracao" class="esconder" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_cliente.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">


                    <div id="titulo1">Dados da Empresa</div>
                    <form name="form1" action="../../ServEditarCadastro" method="post" enctype="multipart/form-data">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd>LOGO DA EMPRESA</dd>
                            </li>
                            <li>
                                <dd>
                                    <%if (url_logo == null) {%>
                                    <img src="../../imagensNew/sua_logo_aqui.png" height="100" />
                                    <%} else {%>
                                    <img src="../../<%= url_logo%>" height="100" />
                                    <%}%>
                                </dd>
                            </li>
                            <li>
                                <dd style="padding: 15px 0px;">
                                    <label>SELECIONE UMA IMAGEM</label>
                                    <input type="file" name="url_logo" accept=".jpg,.jpeg,.gif,.bmp" />

                                </dd>
                            </li>
                            <li class="titulo">
                                <dd>DADOS DA EMPRESA</dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Código</label>
                                    <input readonly name="idCliente" type="text" size="5" value="<%= codigo%>" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Razão Social</label>
                                    <input readonly type="text" name="nome" size="50" value="<%= nomeCliente%>" />
                                </dd>
                                <dd style="padding-top: 18px;">
                                    <label for="a"><input type="radio" name="nome_etq" id="a" value="0" <%if (cli.getNome_etq() == 0) {%>checked<%}%> /> Utilizar Razão Social como Remetente.</label>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Nome Fantasia</label>
                                    <input readonly type="text" name="nome" size="50" value="<%= nomeFantasia%>" />
                                </dd>
                                <dd style="padding-top: 18px;">
                                    <label for="b"><input type="radio" name="nome_etq" id="b" value="1" <%if (cli.getNome_etq() == 1) {%>checked<%}%> /> Utilizar Nome Fantasia como Remetente.</label>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Endereço</label>
                                    <input readonly type="text" name="endereco" size="50" value="<%= endereco%>" />
                                    <input type="hidden" value="<%= numero%>" name="numero" onkeypress="mascara(this, maskNumero)"/>
                                </dd>
                                <dd>
                                    <label>Complemento</label>
                                    <input readonly type="text" name="complemento" value="<%= complemento%>" />
                                </dd>
                                <dd>
                                    <label>CEP</label>
                                    <input readonly type="text" name="cep" value="<%= FormataString.formataCep(cep + "")%>" maxlength="9" onkeypress="mascara(this, maskCep)"/>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Bairro</label>
                                    <input readonly type="text" size="50" name="bairro" value="<%= bairro%>" />
                                </dd>
                                <dd>
                                    <label>Cidade</label>
                                    <input readonly type="text" name="cidade" value="<%= cidade%>" />
                                </dd>
                                <dd>
                                    <label>UF</label>
                                    <input readonly type="text" name="uf" maxlength="2" value="<%= uf%>" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Email</label>
                                    <input readonly type="text" size="50" value="<%= email%>" />
                                </dd>
                                <dd>
                                    <label>CNPJ</label>
                                    <input readonly type="text" value="<%= temp%>" />
                                </dd>
                                <dd>
                                    <label>Telefone</label>
                                    <input readonly type="text" value="<%= telefone%>" />
                                </dd>
                            </li>
                            <li>
                                <dd style="color:red;font-size: 12px;">
                                    <b>*PARA ALTERAR OS DADOS DA EMPRESA ENTRE EM CONTATO COM A SUA AGÊNCIA DE CORREIOS.</b>
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="url_old" value="<%= url_logo%>" />
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" />SALVAR DADOS</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
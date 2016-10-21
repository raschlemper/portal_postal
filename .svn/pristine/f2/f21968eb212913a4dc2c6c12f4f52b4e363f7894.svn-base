
<%@page import="java.math.BigInteger"%>
<%@page import="java.security.MessageDigest"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@ page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {

        int nivelUsuarioEmp2 = (Integer) session.getAttribute("nivelUsuarioEmp");
        int idEmpresa = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));
        int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));
        String senhaUser = String.valueOf(session.getAttribute("senhaUser"));
        String nomeUser = String.valueOf(session.getAttribute("nomeUser"));

        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(senhaUser.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
// Now we need to zero pad it if you actually want the full 32 chars.
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>

        <!-- Menu -->
        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>
        <!--[if lte IE 7]>
        <link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" />
        <![endif]-->
        <!-- Menu -->

        <!-- TableSorter -->
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
        <!-- TableSorter -->
        <script type="text/javascript" src="../../javascript/md5.js"></script>
        <script>
            var hash = CryptoJS.MD5("Message");
        </script>
        <script type="text/javascript">
            function preencherCampos() {

                var form = document.form1;
                
                var hash = CryptoJS.MD5($('#senhaOld').val());
               
                if (''+hash !== '<%=hashtext%>') {
                    console.log(''+hash);
                    console.log('<%=hashtext%>');
                    alert('A senha atual digitada está errada!');
                    return false;
                }               
                if (form.senhaOld.value == "") {
                    alert('Digite sua senha !');
                    return false;
                }
                if (form.senha.value == "") {
                    alert('Insira uma nova senha para o usuário!');
                    return false;
                }
                if (form.senha2.value == "") {
                    alert('Repita a nova senha para o usuário!');
                    return false;
                }
                if (form.senha.value != form.senha2.value) {
                    alert('As novas senhas digitadas não conferem!');
                    return false;
                }

                form.submit();
            }



            function chamaDivProtecao() {
                var classe = document.getElementById("divProtecao").className;
                if (classe === "esconder") {
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao").className = "mostrar";
                } else {
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao").className = "esconder";
                }
            }


            function controleSelecao() {
                if (document.frm.acc[0].checked) {
                    document.getElementById('persons').disabled = true;
                    document.getElementById('usuario').disabled = true;
                }
                else if (document.frm.acc[1].checked) {
                    document.getElementById('persons').disabled = false;
                    document.getElementById('usuario').disabled = true;
                }
                else if (document.frm.acc[2].checked) {
                    document.getElementById('persons').disabled = true;
                    document.getElementById('usuario').disabled = false;
                }
            }
        </script>

        <title>Portal Postal | Usuários do Sistema</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">TROCA DE SENHA - <%=senhaUser%></div>

                    <form name="form1" action="../../ServTrocaSenhaPortal" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd><span>No primeiro acesso é necessario alterar sua senha</span></dd>
                            </li>
                            <li>                             
                                <dd>
                                    <label>Senha atual</label>
                                    <input style='width:200px;' type="password" name="senhaOld" id="senhaOld" />
                                </dd>                                                        
                            </li>
                            <li>                             
                                <dd>
                                    <label>Nova Senha</label>
                                    <input style='width:200px;' type="password" name="senha" id="senha"/>
                                </dd>
                                <dd>
                                    <label>Repita a nova Senha</label>
                                    <input style='width:200px;' type="password" name="senha2" id="senha2"/>
                                </dd>                            
                            </li>

                            <li>
                                <dd style="width: 100%">
                                    <div class="buttons">
                                        <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                        <input type="hidden" name="nomeUser" value="<%= nomeUser%>" />
                                        <input type="hidden" name="senhaUser" value="<%= senhaUser%>" />
                                        <button type="button" class="positive" onclick="preencherCampos();"><img src="../../imagensNew/tick_circle.png" /> SALVAR DADOS</button>
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
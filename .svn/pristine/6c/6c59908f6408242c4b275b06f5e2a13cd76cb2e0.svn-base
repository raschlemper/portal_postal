
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <title>Portal Postal</title>
        
        <!--[if lte IE 8]>

	<script type="text/javascript">

	var htmlshim='abbr,article,aside,audio,canvas,details,figcaption,figure,footer,header,mark,meter,nav,output,progress,section,summary,time,video'.split(',');

	var htmlshimtotal=htmlshim.length;

	for(var i=0;i<htmlshimtotal;i++) document.createElement(htmlshim[i]);

	</script>

	<![endif]-->

        <link rel="stylesheet" type="text/css" href="css/estilo.css" />
        <script type="text/javascript" src="javascript/cookie.js"></script>
        <script type="text/javascript" src="javascript/ajax.js"></script>
        <script type="text/javascript" src="../javascript/mascara.js"></script>
        <script>
            (function (i, s, o, g, r, a, m) {
                i['GoogleAnalyticsObject'] = r;
                i[r] = i[r] || function () {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
                a = s.createElement(o),
                        m = s.getElementsByTagName(o)[0];
                a.async = 1;
                a.src = g;
                m.parentNode.insertBefore(a, m)
            })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

            ga('create', 'UA-4959866-4', 'auto');
            ga('send', 'pageview');

            function chamaDivProtecao() {
                var classe = document.getElementById("esqueciSenha").className;
                if (classe == "esconder") {
                    document.getElementById("esqueciSenha").className = "mostrar";
                } else {
                    document.getElementById("esqueciSenha").className = "esconder";
                }
            }

            function apareceDiv() {
                document.getElementById('escondida').className = 'mostrar';
            }

            function escreve() {
                document.getElementById('escondida2').innerHTML = '<br>Depois que você clicar em confirmar, um e-mail será enviado para o e-mail acima com a sua senha!';
                document.getElementById('escondida3').innerHTML = '';
            }

            function escreve2() {
                document.getElementById('escondida3').innerHTML = "<br>O e-mail acima é o e-mail que está cadastrado em sua agência de correio, se estiver errado informe o e-mail certo no campo abaixo e aperte no Confirmar, a sua agência irá receber um e-mail com a solicitação de ajuste de seu cadastro!<br>Informe o seu email certo para o ajuste: <input id='emailArrumar' name='emailArrumar' type='text' />";
                document.getElementById('escondida2').innerHTML = '';
            }
        </script>
    </head>
    <body onload="LerCookie('hoitoLogin');" style="background: white;">
        <%@ include file="Includes/telaMsg.jsp" %>
        <div id="divPrincipal" align="center" style="margin-top:50px;">
            <table style="height: 600px;" border="0" cellspacing="0"  >
                <tr>
                    <td style="height: 250px;" colspan="2" align="center">
                        <img src="imagensNew/PortalPostal_logo.png" height="200"/>
                    </td>
                </tr>
                <tr>
                    <td width="420" align="center" valign="top">
                        <div style="background: #f5f5f5; min-height: 200px; width: 350px; padding: 25px; border: 1px solid silver;">
                            <div id="titulo1" onclick="javascript:document.getElementById('logAgencia').className = 'mostrar';
                                    document.getElementById('cliqueAqui').className = 'esconder';
                                    document.getElementById('hoitoLogin').focus();">
                                Login da Agência<br/><br/>
                                <div style="padding: 5px; border: 1px solid red; font-size: 12px;color:black;background-color: #fbc2c4;font-weight: bold;">Para acessar como agência,<br/>acesse pelo site <a href="http://agf.portalpostal.com.br">agf.portalpostal.com.br</a><br/>
                                    <b id="cliqueAqui" style="font-size: 12px;color:blue; cursor: pointer;">ou clique aqui.</b>
                                </div>                                
                            </div>
                            <div id="logAgencia" class="esconder">
                                <form method="post" action="ServLogin" name="form1">
                                    <table>
                                        <tr>
                                            <td align="right"><b>Login:</b></td>
                                            <td><input type="text" name="login" id="hoitoLogin" style="width:200px;" /></td>
                                        </tr>
                                        <tr>
                                            <td align="right"><b>Senha:</b></td>
                                            <td><input type="password" name="senha" id="hoitoSenha" style="width:200px;" /></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td><input type="checkbox" id="checkLembrar"> Lembrar meu login.</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" align="center">
                                                <br><input type="submit"  style="padding: 5px 10px; border: 1px solid #157efb;font-weight: bold; background-color: #d1e4fb;" value="ENTRAR" class="botaoLogin" onclick="GerarCookie('hoitoLogin', document.getElementById('hoitoLogin').value, 30)"/>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                        </div>
                    </td>
                    <td width="420" align="center" valign="top">
                        <div style="background: #f5f5f5; min-height: 200px; width: 350px; padding: 25px; border: 1px solid silver;">
                            <div id="titulo1">Login do Cliente</div>
                            <form action="ServLoginEmporium" method="post" name="formLoginHoito">
                                <table>
                                    <tr>
                                        <td align="right"><b>Código da Agência:</b></td>
                                        <td align="left"><input type="text" name="agenciaHoito" id="agenciaHoito" style="width:200px;" maxlength="10" onkeydown="mascara(this, maskNumero);"></td>

                                    </tr>
                                    <tr>
                                        <td align="right"><b>E-mail/Login:</b></td>
                                        <td align="left"><input type="text" name="loginHoito" id="loginHoito" style="width:200px;" maxlength="90"></td>
                                    </tr>
                                    <tr>
                                        <td align="right"><b>Senha:</b></td>
                                        <td align="left"><input type="password" name="senhaHoito" id="senhaHoito" style="width:200px;" /></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="right" style="font-size: 10px; font-weight: bold; font-style: italic; color:red;">*Em caso de duvidas, entre em contato com sua agência.</td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center"><br><input type="submit" style="padding: 5px 10px; border: 1px solid #157efb;font-weight: bold; background-color: #d1e4fb;" value="ENTRAR"></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2" align="center">
                                            <input type="hidden" id="urlHoito" name="urlHoito" value="www.hoito.com.br/Portal/portal.jsp" />
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center" style="font-size: 12px;">Desenvolvido por <a style="color:blue;" target="_blank" href="http://www.scc4.com.br">SCC4 - Desenvolvimento de Software.</a></td>
                </tr>
            </table>
        </div>
    </body>
</html>
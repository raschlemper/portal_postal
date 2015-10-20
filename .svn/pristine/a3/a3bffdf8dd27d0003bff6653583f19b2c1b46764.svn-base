<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

        <title>Portal Postal</title>

        <link rel="stylesheet" type="text/css" href="http://www.portalpostal.com.br/css/estilo.css" />
        <script type="text/javascript" src="http://www.portalpostal.com.br/javascript/cookie.js"></script>
        <script type="text/javascript" src="http://www.portalpostal.com.br/javascript/ajax.js"></script>
        <script type="text/javascript" src="http://www.portalpostal.com.br/javascript/mascara.js"></script>

        <!-- Script do Menu Slide do Login-->
        <script type="text/javascript">
            function chamaDivProtecao(){
                var classe = document.getElementById("esqueciSenha").className;
                if(classe == "esconder"){
                    document.getElementById("esqueciSenha").className = "mostrar";
                }else{
                    document.getElementById("esqueciSenha").className = "esconder";
                }
            }

            function apareceDiv(){
                document.getElementById('escondida').className='mostrar';
            }

            function escreve(){
                document.getElementById('escondida2').innerHTML='<br>Depois que você clicar em confirmar, um e-mail será enviado para o e-mail acima com a sua senha!';
                document.getElementById('escondida3').innerHTML='';
            }

            function escreve2(){
                document.getElementById('escondida3').innerHTML="<br>O e-mail acima é o e-mail que está cadastrado em sua agência de correio, se estiver errado informe o e-mail certo no campo abaixo e aperte no Confirmar, a sua agência irá receber um e-mail com a solicitação de ajuste de seu cadastro!<br>Informe o seu email certo para o ajuste: <input id='emailArrumar' name='emailArrumar' type='text' />";
                document.getElementById('escondida2').innerHTML='';
            }
        </script>
    </head>
    <body onload="LerCookie('hoitoLogin');" style="background: white;">
        <div id="divPrincipal" align="center" style="margin-top:50px;">
            <table style="height: 600px;" border="0" cellspacing="0"  >
                <tr>
                    <td style="height: 250px;" colspan="2" align="center">
                        <img src="http://www.portalpostal.com.br/imagensNew/PortalPostal_logo.png" height="200"/>
                    </td>
                </tr>
                <tr>
                    <td width="420" align="center" valign="top">
                        <div style="background: #f5f5f5; min-height: 200px; width: 350px; padding: 25px; border: 1px solid silver;">
                            <div id="titulo1" onclick="javascript:document.getElementById('logAgencia').className='mostrar';document.getElementById('cliqueAqui').className='esconder';document.getElementById('hoitoLogin').focus();">
                                Login da Agência<br/><br/>
                            </div>
                            <div id="logAgencia">
                                <form method="post" action="http://www.portalpostal.com.br/ServLogin" name="form1">
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
                </tr>
                <tr>
                    <td align="center" style="font-size: 12px;">Desenvolvido por <a style="color:blue;" target="_blank" href="http://www.scc4.com.br">SCC4 - Desenvolvimento de Software.</a></td>
                </tr>
            </table>
        </div>
    </body>
</html>
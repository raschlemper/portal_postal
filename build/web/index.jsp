<<<<<<< HEAD
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

        <title>Portal Postal</title>

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

        </script>
        <!-- Script do Menu Slide do Login-->
        <script type="text/javascript">
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
                document.getElementById('escondida2').innerHTML = '<br>Depois que vocÃª clicar em confirmar, um e-mail serÃ¡ enviado para o e-mail acima com a sua senha!';
                document.getElementById('escondida3').innerHTML = '';
            }

            function escreve2() {
                document.getElementById('escondida3').innerHTML = "<br>O e-mail acima Ã© o e-mail que estÃ¡ cadastrado em sua agÃªncia de correio, se estiver errado informe o e-mail certo no campo abaixo e aperte no Confirmar, a sua agÃªncia irÃ¡ receber um e-mail com a solicitaÃ§Ã£o de ajuste de seu cadastro!<br>Informe o seu email certo para o ajuste: <input id='emailArrumar' name='emailArrumar' type='text' />";
                document.getElementById('escondida2').innerHTML = '';
            }
        </script>
    </head>
    <body onload="LerCookie('hoitoLogin');" style="background: white;">
        <%@ include file="../../Includes/telaMsg.jsp" %>
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
                                Login da AgÃªncia<br/><br/>
                                <div style="padding: 5px; border: 1px solid red; font-size: 12px;color:black;background-color: #fbc2c4;font-weight: bold;">Para acessar como agÃªncia,<br/>acesse pelo site <a href="http://agf.portalpostal.com.br">agf.portalpostal.com.br</a><br/>
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
                                        <td align="right"><b>CÃ³digo da AgÃªncia:</b></td>
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
                                        <td colspan="2" align="right" style="font-size: 10px; font-weight: bold; font-style: italic; color:red;">*Em caso de duvidas, entre em contato com sua agÃªncia.</td>
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
=======

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>Portal Postal</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <meta name="viewport" content="width=device-width, initial-scale=1"/>
        <meta name="description" content="Portal Postal Sistema para Gestão de Pré Postagem para AGF Agência Franqueada dos Correios"/>
        <meta name="author" content="SCC4"/>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <!--[if IE]><link rel="shortcut icon" href="imagensNew/favicon.ico" /><![endif]-->
        <link rel="icon" href="imagensNew/favicon.ico" />
        <title>Portal Postal</title>


        <link href="plugins/bootstrap/dist/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <!--<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.3/themes/smoothness/jquery-ui.css" />-->
        <link href="plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet" />
        <script type="text/javascript" src="javascript/mascara_bootStrap.js" charset="UTF-8"></script>

        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.js"></script>
        <script type="text/javascript" src="plugins/cookie/jquery.cookie.js" charset="UTF-8"></script>
        <script src="plugins/bootstrap/dist/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="NewTemplate/dist/js/bootbox.min.js" type="text/javascript"></script>
        <style>
            body {
                border: none;
                margin: 0;
                padding: 30px;
                //background: #1f1f1f;
                font: normal 16px/20px Lekton, sans-serif;
            }

            #debug {
                position: absolute;
                width: 350px;
                padding: 5px;
                height: 100%;
                top: 0;
                right: 0;
                background: rgba(0, 0, 0, 0.4);
                overflow: auto;
                color: #fff;
                font-size: 13px;
            }

            button {
                padding: 5px;
                font-size: 16px;
                display: inline-block;
                background: #000;
                color: #fff;
                line-height: 30px;
                margin: 3px;
                border: none;
                border-radius: 10px;
            }

            .card-container.card {
                width: 350px;
                padding: 30px 30px;
            }

            .btn {
                font-weight: 700;
                height: 36px;
                -moz-user-select: none;
                -webkit-user-select: none;
                user-select: none;
                cursor: default;
            }

            /*
             * Card component
             */
            .card {
                background-color: #F7F7F7;
                /* just in case there no content*/
                padding: 20px 25px 30px;
                margin: 0 auto 25px;
                margin-top: 50px;
                /* shadows and rounded borders */
                -moz-border-radius: 2px;
                -webkit-border-radius: 2px;
                border-radius: 2px;
                -moz-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
                -webkit-box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
                box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
            }

            .profile-img-card {
                width: 96px;
                height: 96px;
                margin: 0 auto 10px;
                display: block;
                -moz-border-radius: 50%;
                -webkit-border-radius: 50%;
                border-radius: 50%;
            }

            /*
             * Form styles
             */
            .profile-name-card {
                font-size: 16px;
                font-weight: bold;
                text-align: center;
                margin: 10px 0 0;
                min-height: 1em;
            }

            .reauth-email {
                display: block;
                color: #404040;
                line-height: 2;
                margin-bottom: 10px;
                font-size: 14px;
                text-align: center;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                -moz-box-sizing: border-box;
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
            }

            .form-signin #inputCodigo,
            .form-signin #inputEmail,
            .form-signin #inputPassword {
                direction: ltr;
                height: 44px;
                font-size: 16px;
            }

            .form-signin input[type=password],
            .form-signin input[type=text],
            .form-signin button {
                width: 100%;
                display: block;
                margin-bottom: 10px;
                z-index: 1;
                position: relative;
                -moz-box-sizing: border-box;
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
            }

            .form-signin .form-control:focus {
                border-color: rgb(104, 145, 162);
                outline: 0;
                -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgb(104, 145, 162);
                box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgb(104, 145, 162);
            }

            .btn-signin {
                padding: 0px;
                font-weight: 700;
                font-size: 14px;
                height: 36px;
                -moz-border-radius: 3px;
                -webkit-border-radius: 3px;
                border-radius: 3px;
                border: none;
                -o-transition: all 0.218s;
                -moz-transition: all 0.218s;
                -webkit-transition: all 0.218s;
                transition: all 0.218s;
            }

        </style>
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
                m.parentNode.insertBefore(a, m);
            })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

            ga('create', 'UA-4959866-4', 'auto');
            ga('send', 'pageview');

            function GerarCookie() {
                var loginPortalPostal = document.getElementById('inputEmail').value;
                var agenciaPortalPostal = document.getElementById('inputCodigo').value;
                if (document.getElementById("checkLembrar").checked) {
                    $.cookie("LoginPortalPostal", loginPortalPostal, {expires: 30});
                    $.cookie("AgenciaPortalPostal", agenciaPortalPostal, {expires: 30});
                } else {
                    $.removeCookie("LoginPortalPostal");
                    $.removeCookie("AgenciaPortalPostal");
                }
            }

            // Função para ler o cookie.
            function LerCookie() {
                if ($.cookie("AgenciaPortalPostal") !== undefined) {
                    document.getElementById('inputEmail').value = $.cookie("LoginPortalPostal");
                    document.getElementById('inputCodigo').value = $.cookie("AgenciaPortalPostal");
                    document.getElementById("checkLembrar").checked = true;
                    document.getElementById('inputPass')
                }
            }

        </script>


        <%
            //MENSAGEM
            String msg = (String) session.getAttribute("msg");
            String msg2 = "";
            if (request.getParameter("msg") != null) {
                msg2 = request.getParameter("msg").replaceAll(";", "<br>");
            } else if (msg != null) {
                msg2 = msg.replaceAll(";", "<br>");
                session.setAttribute("msg", null);
            }

        %>
    </head>
    <body onload="LerCookie();">
        <%if (!msg2.equals("")) {%>
        <script type="text/javascript">
            alert("<%= msg2%>");
        </script>                 
        <%}%>
        <div id="wrapper" style="position:relative; padding:0px 20px 30px 20px;z-index:10">
            <div class="container">
                <div class="text-center" style="font-size: 10px;">
                    <a target="_blank" href="http://www.scc4.com.br"><img src="imagensNew/logo_scc4.png" width="150" /></a>
                </div>
                <div class="card card-container">                    
                    <img class="profile-img" src="imagensNew/logoNova.png" style="padding-left: 40px;" height="130" alt=""/>
                    <p id="profile-name" class="profile-name-card"></p>
                    <form action="ServLoginEmporium" method="post" class="form-signin">
                        <span id="reauth-email" class="reauth-email"></span>
                        <input type="text" name="agenciaHoito" id="inputCodigo" class="form-control" placeholder="Código da Agência" onkeydown="mascara(this, maskNumero);" required autofocus />
                        <input type="text" name="loginHoito" id="inputEmail" class="form-control" placeholder="Login" required />
                        <input type="password" name="senhaHoito" id="inputPassword" class="form-control" placeholder="Senha" required />
                        <input type="checkbox" id="checkLembrar" value="remember-me" /> Lembrar login.
                        <button onclick="GerarCookie();" class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Entrar</button>
                        <i style="color:red;font-size: 11px;">*Em caso de dúvidas, entre em contato com sua agência.</i>
                    </form><!-- /form -->
                </div><!-- /card-container -->
                <div class="text-center">
                    Para acessar como agência entre em<br/><a href="http://agf.portalpostal.com.br">agf.portalpostal.com.br</a>
                </div>
            </div>
        </div>
    </body>
</html>
>>>>>>> 15fb54610b7beb8f76f41222d8398fe1b55016ba

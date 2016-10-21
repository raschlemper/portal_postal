
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>Portal Postal</title>
        
        <!--[if lte IE 8]>

	<script type="text/javascript">

	var htmlshim='abbr,article,aside,audio,canvas,details,figcaption,figure,footer,header,mark,meter,nav,output,progress,section,summary,time,video'.split(',');

	var htmlshimtotal=htmlshim.length;

	for(var i=0;i<htmlshimtotal;i++) document.createElement(htmlshim[i]);

	</script>

	<![endif]-->
        
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
    <body onload="LerCookie();" style="background-color: black; background-image: url('imagensNew/background.jpg');  background-repeat:no-repeat;">
     
        <%if (!msg2.equals("")) {%>
        <script type="text/javascript">
            alert("<%= msg2%>");
        </script>                 
        <%}%>
        <div id="wrapper" style="position:relative; padding:0px 20px 30px 20px;z-index:10;">
            <div class="container" >
                <div class="text-center" style="font-size: 10px;">
                    <a target="_blank" href="http://www.scc4.com.br"><img src="imagensNew/scc4_campanha.png" width="150" /></a>
                </div>
                <div class="card card-container" style='background-color: white;'>                    
                    <img class="profile-img" src="imagensNew/logo_campanhaS.png" style="" width="290" alt=""/>
                                        <p id="profile-name" class="profile-name-card"></p>
                    <form action="ServLoginEmporium" method="post" class="form-signin">
                        <span id="reauth-email" class="reauth-email"></span>
                        <input type="text" name="agenciaHoito" id="inputCodigo" class="form-control" placeholder="Código da Agência" onkeydown="mascara(this, maskNumero);" required autofocus />
                        <input type="text" name="loginHoito" id="inputEmail" class="form-control" placeholder="Login" required />
                        <input type="password" name="senhaHoito" id="inputPassword" class="form-control" placeholder="Senha" required />
                        <input type="checkbox" id="checkLembrar" value="remember-me" /> Lembrar login.
                        <button onclick="GerarCookie();" class="btn btn-lg btn-success btn-block btn-signin" type="submit">Entrar</button>
                        <!--<i style="color:yellow;font-size: 11px;">*Em caso de dúvidas, entre em contato com sua agência.</i>-->
                    </form><!-- /form -->
                </div><!-- /card-container -->
                <div class="text-center" >
                    <span style="color:white;">Para acessar como agência entre em</span><br/><a href="http://agf.portalpostal.com.br">agf.portalpostal.com.br</a>
                </div>
            </div>
        </div>
    </body>
</html>

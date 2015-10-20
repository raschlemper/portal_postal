
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>

        <!--<link href="css/YTPlayer.css" media="all" rel="stylesheet" type="text/css">
        
        <script src="inc/jquery.mb.YTPlayer.js"></script>-->
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.js"></script>
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

            .form-signin #inputEmail,
            .form-signin #inputPassword {
                direction: ltr;
                height: 44px;
                font-size: 16px;
            }

            .form-signin input[type=email],
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
                /*background-color: #4d90fe; */
                //background-color: rgb(104, 145, 162);
                //background-color: linear-gradient(rgb(104, 145, 162), rgb(12, 97, 33));
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
           /* var isIframe = function () {
                var isIframe = false;
                try {
                    //try to access the document object
                    if (self.location.href != top.location.href)
                        isIframe = true;
                } catch (e) {
                    //We don't have access, it's cross-origin!
                    isIframe = true;
                }
                return isIframe;
            };

            jQuery(function () {
                var myPlayer = jQuery("#bgndVideo").YTPlayer({
                    onReady: function (player) {
                        YTPConsole.append(player.id + " player is ready");
                        YTPConsole.append("<br>");
                    }
                });


                var YTPConsole = jQuery("#eventListener");
                // EVENT: YTPStart YTPEnd YTPLoop YTPPause YTPBuffering
                myPlayer.on("YTPStart YTPEnd YTPLoop YTPPause YTPBuffering", function (e) {
                    YTPConsole.append(e.type + " - " + jQuery("#bgndVideo").getPlayer().getPlayerState() + " - time: " + e.time);
                    YTPConsole.append("<br>");
                });
                // EVENT: YTPChanged
                myPlayer.on("YTPChanged", function (e) {
                    YTPConsole.html("");
                });

                // EVENT: YTPData
                myPlayer.on("YTPData", function (e) {
                    YTPConsole.append("******************************");
                    YTPConsole.append("<br>");
                    YTPConsole.append(e.type);
                    YTPConsole.append("<br>");
                    YTPConsole.append(e.prop.title);
                    YTPConsole.append("<br>");
                    YTPConsole.append(e.prop.description.replace(/\n/g, "<br/>"));
                    YTPConsole.append("<br>");
                    YTPConsole.append("******************************");
                    YTPConsole.append("<br>");
                });

                // EVENT: YTPTime
                myPlayer.on("YTPTime", function (e) {
                    var currentTime = e.time;
                    var player = e.target.wrapper;
                    var traceLog = currentTime / 5 == Math.floor(currentTime / 5);

                    if (traceLog && YTPConsole.is(":visible")) {
                        YTPConsole.append(e.type + " actual time is: " + currentTime);
                        YTPConsole.append("<br>");
                    }
                });

                

            });

            var v = false;
            function changeVideo() {
                var vID = v ? "Plw2AU50DAQ" : "Plw2AU50DAQ";
                jQuery('#bgndVideo').changeMovie({videoURL: vID});
                $("#vidData").toggle(1000);
                v = !v;
            }*/
        </script>
    </head>
    <body>
        <div id="wrapper" style="position:relative; padding:0px 20px 30px 20px;z-index:10">
            <div class="container">
                <div class="text-center" style="font-size: 10px;">
                    <img src="../../imagensNew/logo_scc4.png" width="150" />
                </div>
                <div class="card card-container">                    
                    <img class="profile-img" src="../../imagensNew/logoNova.png" style="padding-left: 40px;" height="130" alt=""/>
                    <p id="profile-name" class="profile-name-card"></p>
                    <form action="../../ServLoginEmporium" method="post" class="form-signin">
                        <span id="reauth-email" class="reauth-email"></span>
                        <input type="text" name="agenciaHoito" id="inputEmail" class="form-control" placeholder="Código da Agência" onkeydown="mascara(this, maskNumero);" required autofocus>
                        <input type="text" name="loginHoito" id="inputEmail" class="form-control" placeholder="Login" required>
                        <input type="password" name="senhaHoito" id="inputPassword" class="form-control" placeholder="Senha" required>
                        <input type="checkbox" value="remember-me"> Lembrar login.
                        <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">Entrar</button>
                        <i style="color:red;font-size: 11px;">*Em caso de duvidas, entre em contato com sua agência.</i>
                    </form><!-- /form -->
                </div><!-- /card-container -->
                <div class="text-center">
                    Para acessar como agência entre em<br/><a href="http://agf.portalpostal.com.br">agf.portalpostal.com.br</a>
                </div>
            </div>
        </div>
        <!--<a id="bgndVideo" class="player"data-property="{videoURL:'Plw2AU50DAQ',containment:'body', showControls:true, autoPlay:true, loop:true, vol:50, mute:true, startAt:10, opacity:1, addRaster:true, quality:'default', optimizeDisplay:true}">My video</a> 
        BsekcY04xvQ-->
    </body>
</html>

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
<div <%if (!msg2.equals("")) {%>class="mostrar"<%}else{%>class="esconder" <%}%> id="divEsperaMsg">
    <div class="msg">
        <div class="title">
            Aviso do Sistema!
            <div style="float: right;">
                <a class="fechar" onclick="javascript:document.getElementById('divEsperaMsg').className='esconder';"></a>
            </div>
        </div>
        <table cellspacing="8" style="color:black;">
            <tr>
                <td valign="middle" style="padding-top: 5px;">
                    <img src="../imagensNew/big_alert.png" alt="Alerta" />
                </td>
                <td valign="middle" id="tdMsg">
                    <%= msg2%>
                </td>
            </tr>
        </table>
    </div>
</div>


<script type="text/javascript">
    function fecharTelaEspera(){
        document.getElementById('protecaoTelaEspera').className = 'esconder';
    }
    function abrirTelaEspera(){
        document.getElementById('protecaoTelaEspera').className = 'mostrar';
    }
</script>
<div class="esconder" id="protecaoTelaEspera">
    <div id="telaEspera">Acessando os serviços
                    <br/>do servidor da ECT...
                    <br/>Pode sofrer lentidão
                    <br/>Por Favor, Aguarde...<br/><img src="../imagensNew/loader.gif" /></div>
</div>
<%@page import="com.portal.componentes.nfe.ParametrosFormularioNFE"%>
<%@page import="com.portal.componentes.nfe.CarregaDadosNFE"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
ParametrosFormularioNFE parametros = new ParametrosFormularioNFE();
CarregaDadosNFE dadosNFE = new CarregaDadosNFE(parametros);
String captcha = dadosNFE.getCaptcha();
request.getSession().setAttribute("PARAMETRONFE",parametros);

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="javascript/jx.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.js"></script>
        <script>
            function pesquisar(){
                JxPost('',JxResult, 'ServDadosNFE',getParameter(),false);
            }
            
            function getParameter(){
                var captcha = document.getElementById('captcha');
                var keyNF = document.getElementById('keyNF');
                var parametro = "captcha="+captcha.value+
                                "&keyNF="+keyNF.value;
                return parametro;
            }
            
            function carregaResultado(json){
                
                var dadosDaNota = JSON.parse(json);
                $('#nfNumeroNota').val(dadosDaNota.numero);
                $('#nfValor').val(dadosDaNota.valorTotal);
                $('#nfDescricao').val(dadosDaNota.informacoesComplementares);
                $('#nfnome').val(dadosDaNota.destinatario.nome);
                $('#nfCNPJ').val(dadosDaNota.destinatario.cnpj);
                $('#nfEndereco').val(dadosDaNota.destinatario.logradouro);
                $('#nfBairro').val(dadosDaNota.destinatario.bairro);
                $('#nfCep').val(dadosDaNota.destinatario.cep);
                $('#nfMunicipio').val(dadosDaNota.destinatario.municipio);
                $('#nfTelefone').val(dadosDaNota.destinatario.telefone);
                $('#nfUF').val(dadosDaNota.destinatario.uf);
                $('#nfPais').val(dadosDaNota.destinatario.pais);
            }
        </script>
        <title>JSP Page</title>
    </head>
    <body>
        <div>
            <img src="<%=captcha%>"/>
            <p>
                <input size="50" id="captcha"/>
            </p>
            <p>
            <input size="50" id="keyNF"/>
            </p>
            <p>
                <button onclick="pesquisar()">Enviar</button>
            </p>
        </div>
        <br>
        <br>
        
        <p><input type="text" size="50" id="nfNumeroNota" /></p>
        <p><input type="text" size="50" id="nfValor" /></p>
        <p><input type="text" size="50" id="nfDescricao" /></p>
        <p><input type="text" size="50" id="nfnome" /></p>
        <p><input type="text" size="50" id="nfCNPJ" /></p>
        <p><input type="text" size="50" id="nfEndereco" /></p>
        <p><input type="text" size="50" id="nfBairro" /></p>
        <p><input type="text" size="50" id="nfCep" /></p>
        <p><input type="text" size="50" id="nfMunicipio" /></p>
        <p><input type="text" size="50" id="nfTelefone" /></p>
        <p><input type="text" size="50" id="nfUF" /></p>
        <p><input type="text" size="50" id="nfPais" /></p>
        
        
        
        
    </body>
</html>

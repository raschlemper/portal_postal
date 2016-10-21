<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Entidade.empresas"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String nomeBD = (String) session.getAttribute("nomeBD");
                    if (nomeBD == null) {
                        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
                    } else {
                        int idEmpresa = (Integer) session.getAttribute("idEmpresa");
                        int idCliente = (Integer) session.getAttribute("idCliente");
                        empresas emp = Controle.contrEmpresa.consultaEmpresa(idEmpresa);
                        Clientes cli = (Clientes) session.getAttribute("cliente");
                        String[] acc = Controle.contrCliente.consultaLoginPrecosPrazosCorreios(idCliente, nomeBD);
                        String login = "";
                        String senha = "";
                        if(acc != null){
                            login = acc[0];
                            senha = acc[1];
                        }
        %>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Precos e Prazos</title>
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
            
            $(function() {
                $("#data").datepicker({
                    maxDate: '<%= sdf.format(new Date()) %>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });
            
            function mostrarContrato(sto){
                if(sto == "41068" || sto == "40096" || sto == "40436" || sto == "40444" || sto == "81019"){
                    document.getElementById("contrato1").className = "mostrar";
                    document.getElementById("contrato2").className = "mostrar";
                    document.getElementById("contrato3").className = "mostrar";
                }else{
                    document.getElementById("contrato1").className = "esconder";
                    document.getElementById("contrato2").className = "esconder";
                    document.getElementById("contrato3").className = "esconder";
                }
            }
            
            function adicionarPacotes(){
                var qtd = document.getElementById("quantidade").value;
                var html = "";
                for (i = 0; i < qtd; i++) {                    
                    html += "<li>"+
                            "<dd>" +
                            "    <h3 style='padding-top:18px;'>PACOTE "+(i+1)+":</h3>" +
                            "</dd>" +
                            "<dd>" +
                            "    <label>Peso<span style='color:red;'>(Kg)</span></label>" +
                            "    <input type='text' name='peso"+i+"' id='peso"+i+"' size='5' maxlength='6' onkeypress='mascara(this, maskKilo)' />" +
                            "</dd>" +
                            "<dd>" +
                            "    <label>Alt.<span style='color:red;'>(cm)</span></label>" +
                            "    <input type='text' name='altura"+i+"' id='altura"+i+"' size='5' maxlength='3' onkeypress='mascara(this, maskNumero)' />" +
                            "</dd>" +
                            "<dd>" +
                            "    <label>Larg.<span style='color:red;'>(cm)</span></label>" +
                            "    <input type='text' name='largura"+i+"' id='largura"+i+"' size='5' maxlength='3' onkeypress='mascara(this, maskNumero)' />" +
                            "</dd>" +
                            "<dd>" +
                            "    <label>Comp.<span style='color:red;'>(cm)</span></label>" +
                            "    <input type='text' name='comprimento"+i+"' id='comprimento"+i+"' size='5' maxlength='3' onkeypress='mascara(this, maskNumero)' />" +
                            "</dd>"+
                            "<dd>" +
                            "    <label>V.D.</label>" +
                            "    <input type='text' name='vd"+i+"' id='vd"+i+"' value='0.00' size='7' maxlength='8' onkeypress='mascara(this, maskReal)' />" +
                            "</dd>"+
                            "</li>";    
                }
                document.getElementById("liAgrupado").innerHTML = html;
                
            }
            
            function verificaServicoSelecionado(cbServico){
                var str = cbServico.options[cbServico.selectedIndex].innerHTML;
                document.getElementById("liAgrupado").innerHTML = "";
                document.getElementById("quantidade").value = "2";
                document.getElementById("peso").value = "";
                document.getElementById("largura").value = "";
                document.getElementById("altura").value = "";
                document.getElementById("comprimento").value = "";
                if(str.indexOf("AGRUPADO") > 0 ){
                    document.getElementById("ddQtd").className = "mostrar";
                    document.getElementById("ddBotao").className = "mostrar";
                    document.getElementById("ddPeso").className = "esconder";
                    document.getElementById("ddLarg").className = "esconder";
                    document.getElementById("ddAlt").className = "esconder";
                    document.getElementById("ddComp").className = "esconder";
                    document.getElementById("ddVd").className = "esconder";
                }else{
                    document.getElementById("ddQtd").className = "esconder";
                    document.getElementById("ddBotao").className = "esconder";
                    document.getElementById("ddPeso").className = "mostrar";
                    document.getElementById("ddLarg").className = "mostrar";
                    document.getElementById("ddAlt").className = "mostrar";
                    document.getElementById("ddComp").className = "mostrar";                  
                    document.getElementById("ddVd").className = "mostrar";
                }
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



                    <div id="titulo1">Cálculo de Preços e Prazos</div>
                    <ul class="ul_formulario">
                        <li <%if(cli.getTemContrato() == 0){%>class="esconder"<%}%>>
                            <dd id="contrato1">
                                <label>Cód. Administrativo</label>
                                <input type="text" name="codEmpresa" id="codEmpresa" size="25" value="<%= login %>" />
                            </dd>
                            <dd id="contrato2">
                                <label>Senha</label>
                                <input type="password" name="senha" id="senha" size="25" value="<%= senha %>" />                                
                            </dd>
                            <dd id="contrato3">
                                <b>Informe nos campos ao lado o seu Código Administrativo e Senha.<br/></b>
                                - O Código Administrativo consta no seu Cartão de Postagem do Contrato dos Correios.<br/>
                                - A senha é formada pelos 8 (oito) primeiros dígitos do CNPJ/CGC da sua empresa.
                            </dd>
                        </li>
                        <li>
                            <dd>
                                <label>Data da Postagem:</label>
                                <input type="text" style="width:60px;" name="data" id="data" value="<%= sdf.format(new Date()) %>" maxlength="10" onkeypress="mascara(this, maskData);" />
                            </dd>
                        </li>
                        <li>
                            <dd>
                                <label>Serviço</label>
                                <%
                                    ArrayList<Integer> listaContrato = ContrClienteContrato.consultaContratoCliente(idCliente, nomeBD);
                                %>
                                <select name="servico" id="servico" onchange="verificaServicoSelecionado(this);">
                                        <%if(listaContrato.contains(40126)){%>
                                        <option value="40126">SEDEX A COBRAR | 40.126</option>
                                        <%} else if(listaContrato.contains(40630)){%>
                                        <option value="40630">SEDEX PAG. NA ENTREGA | 40.630</option>
                                        <%} else if(listaContrato.contains(40432)){%>
                                        <option value="40432">SEDEX PAG. NA ENTREGA | 40.432</option>
                                        <%} else if(listaContrato.contains(40440)){%>
                                        <option value="40440">SEDEX PAG. NA ENTREGA | 40.440</option>
                                        <%}else{%>
                                        <option value="40819">SEDEX PAG. NA ENTREGA</option>
                                        <%}%>
                                        
                                        <%if(listaContrato.contains(41238)){%>
                                        <option value="41238">PAC PAG. NA ENTREGA | 41.238</option>
                                        <%}else{%>
                                        <option value="41262">PAC PAG. NA ENTREGA</option>
                                        <%}%>
                                        
                                        <%if(listaContrato.contains(40215)){%>
                                        <option value="40215">SEDEX 10 | 40.215</option>
                                        <%} else if(listaContrato.contains(40789)){%>
                                        <option value="40789">SEDEX 10 | 40.789</option>
                                        <%}else{%>
                                        <option value="40215">SEDEX 10</option>
                                        <%}%>
                                        
                                        <option value="40169">SEDEX 12</option>  
                                        
                                        <%if(listaContrato.contains(41300)){%>
                                        <option value="41300">PAC GRANDES FORMATOS | 41.300</option> 
                                        <%}%>        
                                        
                                        <%if(listaContrato.contains(41068)){%>
                                        <option value="41068">PAC AGRUPADO | 41.068</option>
                                        <%} else if(listaContrato.contains(41211)){%>
                                        <option value="41211">PAC AGRUPADO | 41.211</option>
                                        <%} else if(listaContrato.contains(41491)){%>
                                        <option value="41491">PAC AGRUPADO | 41.491</option>
                                        <%}%>
                                        
                                        <%if(listaContrato.contains(41068)){%>
                                        <option value="41068">PAC | 41.068</option>
                                        <%} else if(listaContrato.contains(41211)){%>
                                        <option value="41211">PAC | 41.211</option>
                                        <%} else if(listaContrato.contains(41491)){%>
                                        <option value="41491">PAC | 41.491</option>
                                        <%}else{%>
                                        <option selected value="41106">PAC</option>  
                                        <%}%>
                                        
                                        <%if(listaContrato.contains(40096)){%>
                                        <option value="40096">SEDEX | 40.096</option>
                                        <%} else if(listaContrato.contains(40436)){%>
                                        <option value="40436">SEDEX | 40.436</option>
                                        <%} else if(listaContrato.contains(40444)){%>
                                        <option value="40444">SEDEX | 40.444</option>
                                        <%} else if(listaContrato.contains(40568)){%>
                                        <option value="40568">SEDEX | 40.568</option>
                                        <%} else if(listaContrato.contains(41408)){%>
                                        <option value="41408">SEDEX | 41.408</option>
                                        <%} else {%>
                                        <option value="40010">SEDEX</option>
                                        <%}%>
                                        
                                        <%if(listaContrato.contains(81019)){%>
                                        <option value="81019">e-SEDEX | 81.019</option>
                                        <%} else if(listaContrato.contains(81833)){%>
                                        <option value="81833">e-SEDEX | 81.833</option>
                                        <%}%>
                                </select>
                            </dd>
                        </li>
                        <li>
                            <dd>
                                <label>Mão Própria</label>
                                <select name="mp" id="mp">
                                    <option value="N">Não</option>
                                    <option value="S">Sim</option>
                                </select>
                            </dd>
                            <dd>
                                <label>Aviso de Recebimento</label>
                                <select name="ar" id="ar">
                                    <option value="N">Não</option>
                                    <option value="S">Sim</option>
                                </select>
                            </dd>
                            <dd id="ddVd">
                                <label>Valor Declarado<span style="color:red;">(R$)</span></label>
                                <input type="text" name="vd" id="vd" value="0.00" onkeypress="mascara(this, maskReal)" />
                            </dd>
                        </li>
                        <li>
                            <dd>
                                <label>Cep Destino</label>
                                <input type="hidden" name="cepOrigem" id="cepOrigem" size="10" maxlength="9" onkeypress="mascara(this, maskCep)" value="<%= emp.getCep() %>" />
                                <input type="text" name="cepDestino" id="cepDestino" size="10" maxlength="9" onkeypress="mascara(this, maskCep)" />
                            </dd>
                            <dd id="ddPeso">
                                <label>Peso<span style="color:red;">(Kg)</span></label>
                                <input type="text" name="peso" id="peso" size="5" maxlength="6" onkeypress="mascara(this, maskKilo)" />
                            </dd>
                            <dd id="ddAlt">
                                <label>Alt.<span style="color:red;">(cm)</span></label>
                                <input type="text" name="altura" id="altura" size="5" maxlength="3" onkeypress="mascara(this, maskNumero)" />
                            </dd>
                            <dd id="ddLarg">
                                <label>Larg.<span style="color:red;">(cm)</span></label>
                                <input type="text" name="largura" id="largura" size="5" maxlength="3" onkeypress="mascara(this, maskNumero)" />
                            </dd>
                            <dd id="ddComp">
                                <label>Comp.<span style="color:red;">(cm)</span></label>
                                <input type="text" name="comprimento" id="comprimento" size="5" maxlength="3" onkeypress="mascara(this, maskNumero)" />
                                <input type="hidden" name="formato" id="formato" size="5" value="1" />
                                <input type="hidden" name="diametro" id="diametro" size="5" value="0" />
                            </dd>
                            <dd id="ddQtd" class="esconder">
                                <label>Quantidade de Pacotes</label>
                                <input type="text" name="quantidade" id="quantidade" size="5" maxlength="3" onkeypress="mascara(this, maskNumero)" />
                            </dd>
                            <dd id="ddBotao" class="esconder">
                                <div class="buttons" style="padding-top: 10px;">
                                    <a type="button" class="positive" onclick="adicionarPacotes();"><img src="../../imagensNew/plus_circle.png"/> ADICIONAR PACOTES</a>
                                </div>
                            </dd>
                        </li>
                        <li id="liAgrupado">
                            
                        </li>
                        <li>
                            <dd>
                                <div class="buttons">
                                    <button type="button" class="regular" onclick="pesquisaPrecoPrazo();"><img src="../../imagensNew/lupa.png"/> CALCULAR PREÇO E PRAZO</button>
                                </div>
                            </dd>
                        </li>
                    </ul>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>

                    <div style="width: 100%;clear: both;">
                        <div id="titulo2" >Resultado da Pesquisa</div>
                        <div id="tableObjeto"></div>
                    </div>


                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
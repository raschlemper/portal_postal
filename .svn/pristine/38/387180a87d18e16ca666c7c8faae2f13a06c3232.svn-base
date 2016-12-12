
<%@page import="java.util.List"%>
<%@page import="br.com.portalpostal.providers.ProviderLayoutImportacao"%>
<%@page import="br.com.portalpostal.entity.LayoutImportacao"%>
<%@page import="br.com.portalpostal.entity.Cliente"%>
<%@page import="br.com.portalpostal.componentes.ConexaoEntityManager"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="br.com.portalpostal.providers.ProviderCliente"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import="Controle.contrDestinatario"%>
<%@page import="Entidade.Destinatario"%>
<%@page import="Entidade.SenhaCliente"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>

<%
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {
        int idCli = Integer.parseInt(String.valueOf(session.getAttribute("idCliente")));
        ArrayList<Integer> dps = (ArrayList<Integer>) session.getAttribute("departamentos");
        EntityManager manager = ConexaoEntityManager.getConnection(nomeBD);
        ProviderCliente providerCliente = new ProviderCliente(manager);
        ProviderLayoutImportacao layoutImportacao = new ProviderLayoutImportacao(manager);
        Cliente cliente = providerCliente.findClienteById(idCli);
        List<String> layouts = layoutImportacao.findAllDistinctName();
        int idUser = (Integer) session.getAttribute("idUsuarioEmp");
        
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>

      
        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <script type="text/javascript" language="javascript" src="../../javascript/plugins/dropdown/js/jquery.dropdownPlain.js"></script>
      
        <link rel="stylesheet" href="../../javascript/plugins/TableSorter/styleSorterV3.css" />
        <script type="text/javascript" src="../../javascript/plugins/TableSorter/scriptSorterV3.js"></script>
      
        

        <script type="text/javascript">
            function chamaDivProtecao(){
                var classe = document.getElementById("divProtecao").className;
                if(classe == "esconder"){
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao").className = "mostrar";
                }else{
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao").className = "esconder";
                }
            }
                   
            function validaForm(){       
                abrirTelaEspera();
                var form = document.form1;
                
                if(document.getElementById('tipo').value==""){
                    fecharTelaEspera();
                    alert("Escolha um modelo de importação.");
                    return false;
                }
                
                if(form.arquivo.value === ""){
                    fecharTelaEspera();
                    alert("Escolha o arquivo a ser importado!");
                    return false;
                }else if($('#tipo_serv option:selected').text() === 'SELECOINE UM SERVICO'){
                    fecharTelaEspera();
                    alert(" --- Por favor, escolha um serviço para as NF-E ! --- No caso de varios, você poderá alterar-los depois !");
                    return false;
                }else if($('#departamento').val() === '-1'){
                    fecharTelaEspera();
                     alert(" --- Por favor, selecione um departamento !");
                    return false;
                }else{
                    var indexA = form.arquivo.value.lastIndexOf(".");
                    var indexB = form.arquivo.value.length;
                    var ext = form.arquivo.value.substring(indexA, indexB).toUpperCase();
                    if(ext.toUpperCase() !=".CSV" && ext.toUpperCase()!=".TXT") {
                        fecharTelaEspera();
                        alert("O formato do arquivo a ser importado dever ser do tipo CSV ou TXT!");
                        return false;
                    } 
                }
                form.submit();
                return true;
            }
            
        </script>

        <title>Portal Postal | Importação de Postagens</title>

    </head>
    <body onload="javascript:document.getElementById('nome').focus();">
        <div id="divInteracao" class="esconder" style="top:10%; left:10%; right:10%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Importação de Pré-Postagens</div>
                 <ul class="ul_tab" style="width: 1160px;height: 50px;">
                        <li>
                            <dl  style='width:200px; border-left: 1px solid #CCC;'>
                                <dd><b class='serv' onclick="location.href='imp_postagem.jsp'">Modelo Padrão</b></dd>
                            </dl>
                            <dl class="ativo" style='width:200px;' onclick="location.href='importacaoPrePostagem.jsp'">
                                <dd><b class='serv'>Modelo Cadastrado</b></dd>
                            </dl>
                            <dl style="width: 750px; background: white;border-top: 1px solid white;border-right: 1px solid white; cursor: default;" ></dl>
                        </li>
                    </ul>
                    
                    <form method="post" action="../../ServletPreVendaImportar" id="form1" name="form1" accept-charset="ISO-8859-1" enctype="multipart/form-data">
                        <ul class="ul_formulario" style="width: 1140px;">
                            <li class="titulo">
                                <dd style="font-size: 12px;">SELECIONE O ARQUIVO, SERVIÇO E DEPARTAMENTO.</dd>
                            </li>
                            <li>
                              
                                <dd>
                                    <label>MODELO DE IMPORTAÇÃO:</label>
                                    <select style="width: 300px;" name="tipo" id="tipo">
                                        <option value=''></option>
                                     <%
                                        for(String layout : layouts){
                                            out.println("<option value='"+layout+"'>"+layout+"</option>");
                                        }
                                     %>
                                    </select>
                                </dd>
                                
                            </li>
                            <li>
                                <dd>
                                    <label>ESCOLHA UM ARQUIVO PARA IMPORTAR:</label>
                                    <input style="width:300px;" type="file" name="arquivo" id="arquivo" accept=".csv,.txt"/>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>SERVIÇO:</label>
                                    <select style="width: 300px;" name="servico" id="tipo_serv"  onchange="mudaServiceOpts(this.value);">
                                        <%=cliente.getContratos().contains("PAX")?"<option value=\"PAX\">PAC GRANDES FORMATOS</option>":""%>
                                        <%=cliente.getContratos().contains("ESEDEX")?"<option value=\"ESEDEX\">E-SEDEX</option>":""%>
                                        <option value="ARQUIVO">DEFINIDO NO ARQUIVO</option>
                                        <option value="PAC">PAC</option>
                                        <option value="SEDEX">SEDEX</option>
                                        <option value="CARTA">CARTA REGISTRADA</option>
                                        <option value="SIMPLES">CARTA SIMPLES</option>
                                        <option value="SEDEX10">SEDEX 10</option>
                                        <option value="SEDEX12">SEDEX 12</option>
                                        <option value="SEDEXHJ">SEDEX HOJE</option>
                                        <option value="MDPB">MDPB</option>
                                        <option value="IMPRESSO">IMPRESSO</option>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Departamento</label>
                                    <select style="width: 300px;" name="departamento" id="departamento">
                                        <%
                                            ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(idCli, nomeBD); 
                                            if(listaDep.size() > 0){%>
                                            <option value="-1">SELECIONE UM DEPARTAMENTO</option>
                                            <%}else{%>
                                        <option value="">NENHUM DEPARTAMENTO</option>
                                        <%}
                                            for (int i = 0; i < listaDep.size(); i++) {
                                                ClientesDeptos cd = listaDep.get(i);
                                                String cartao = "0";
                                                if (cd.getCartaoPostagem() != null && !cd.getCartaoPostagem().trim().equals("") ) {
                                                    cartao = cd.getCartaoPostagem();
                                                }
                                                if (dps.contains(cd.getIdDepartamento())) {
                                        %>
                                        <option value="<%=cd.getIdDepartamento() + ";" + cd.getNomeDepartamento() + ";" + cartao%>"><%= cd.getNomeDepartamento()%></option>
                                        <%}
                                        }%>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD%>" />
                                        <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                        <input type="hidden" name="idUser" value="<%= idUser%>" />
                                        <button type="button" class="positive" onclick="return validaForm();"><img src="../../imagensNew/tick_circle.png" /> IMPORTAR PRÉ-POSTAGEM</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>

                    <div id="titulo2">Instruções para importação de Arquivo.</div>                    
                    <div style="padding:8px 5px; background: white;">
                        <b>1) Layout de Exemplo do Arquivo CSV para Importação de Postagens:</b><br/><br/>
                            <div style="margin-left: 25px;">
                                <b>Arquivo de importação sem peso:</b><br/>
                                <a href="ARQUIVO_EXEMPLO_PORTALPOSTAL.csv" target="_blank">- Clique aqui para baixar o 'ARQUIVO_EXEMPLO_PORTALPOSTAL.csv'</a><br/>
                            </div>
                        <b>2) Colunas de preenchimento Obrigatórios:</b><br/><br/>
                        <b style="margin-left: 25px;">NOME, CEP, ENDEREÇO, NUMERO, BAIRRO, CIDADE, UF</b><br/><br/>
                        <b>3) A coluna SERVIÇO deve ser escrito o serviço a ser utilizado para cada objeto (caso já tenha definido).</b><br/><br/>
                        <div style="margin-left: 25px;">
                            <b>ESEDEX</b> = Para Encomenda ESEDEX<br/>
                            <b>PAC</b> = Para Encomenda PAC<br/>
                            <b>PAX</b> = Para Encomenda PAC Grandes Formatos<br/>
                            <b>CARTA</b> = Para Carta Comercial Registrada<br/>
                            <b>SIMPLES</b> = Para Carta Comercial Simples<br/>
                            <b style="color: red;">*Caso a postagem não tenha nenhum serviço pré-definido deixe o campo vazio</b><br/>
                            <b style="color: red;">**Caso o serviço pré-definido seja E-SEDEX, SEDEX 10, SEDEX 12, e a região de destino não abrangir o serviço, o mesmo será alterado automaticamente para SEDEX.</b>
                        </div><br/>
                        <b>4) A coluna SERV_ADICIONAIS deve ser preenchida somente com as siglas dos seguintes serviços adicionais (caso a postagem possua):</b><br/><br/>
                        <div style="padding-left: 25px;">
                            <b>AR</b> = Aviso de Recebimento<br/>
                            <b>MP</b> = Mão Própria<br/>
                            <b>VD</b> = Valor Declarado (Caso tenha VD, a coluna VALOR_DECLARADO deve ser preenchida com o valor da nota)<br/>
                            <b style="color: red;">*Caso a postagem não tenha nenhum serviço adicional deixe o campo vazio</b>
                        </div><br/><br/>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
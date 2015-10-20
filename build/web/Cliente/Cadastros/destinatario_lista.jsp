<%@page import="Controle.contrDestinatario"%>
<%@page import="Entidade.Destinatario"%>
<%@page import="Entidade.SenhaCliente"%>
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
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
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

        <script type="text/javascript">
            function preencherCampos(){
                var form = document.form1;
                if(form.nome.value==""){
                    alert('Preencha o NOME do destinatário!');
                    return false;
                }
                if(form.cep.value==""){
                    alert('Preencha o CEP do destinatário!');
                    return false;
                }
                if(form.endereco.value==""){
                    alert('Preencha o ENDEREÇO do destinatário!');
                    return false;
                }
                if(form.numero.value==""){
                    alert('Preencha o NÚMERO do destinatário!');
                    return false;
                }
                if(form.cidade.value==""){
                    alert('Preencha a CIDADE do destinatário!');
                    return false;
                }
                if(form.uf.value==""){
                    alert('Preencha a UF do destinatário!');
                    return false;
                }
                form.submit();
            }

            function preencherCamposEdit(){
                var form = document.form5;
                if(form.nome.value==""){
                    alert('Preencha o NOME do destinatário!');
                    return false;
                }
                if(form.cep.value==""){
                    alert('Preencha o CEP do destinatário!');
                    return false;
                }
                if(form.endereco.value==""){
                    alert('Preencha o ENDEREÇO do destinatário!');
                    return false;
                }
                if(form.cidade.value==""){
                    alert('Preencha a CIDADE do destinatário!');
                    return false;
                }
                if(form.uf.value==""){
                    alert('Preencha a UF do destinatário!');
                    return false;
                }
                form.submit();
            }

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
            
            $(document).ready(function(){
                /* ao pressionar uma tecla em um campo que seja de class="pula" */
                $('#cep').keypress(function(e){
                    /* 
                     * verifica se o evento é Keycode (para IE e outros browsers)
                     * se não for pega o evento Which (Firefox)
                     */
                    var tecla = (e.keyCode?e.keyCode:e.which);
                    
                    /* verifica se a tecla pressionada foi o ENTER */
                    if(tecla == 13){
                        verPesquisarCepDest($('#cep').val());
                    }
                    /* impede o sumbit caso esteja dentro de um form */
                    //e.preventDefault(e);
                    //return false;
                })
            });
            
            function funcEnter(e){
                var tecla = (e.keyCode?e.keyCode:e.which);                    
                /* verifica se a tecla pressionada foi o ENTER */
                if(tecla == 13){
                    verPesquisarCepDest($('#cep2').val());
                }
            }            

            function semNumero() {
                if (document.getElementById("sn").checked) {
                    document.getElementById("numero").value = "S/N";
                    document.getElementById("numero").readOnly = true;
                } else {
                    document.getElementById("numero").value = "";
                    document.getElementById("numero").readOnly = false;
                }
            }

            function semNumero2() {
                if (document.getElementById("sn2").checked) {
                    document.getElementById("numero2").value = "S/N";
                    document.getElementById("numero2").readOnly = true;
                } else {
                    document.getElementById("numero2").value = "";
                    document.getElementById("numero2").readOnly = false;
                }
            }

        </script>

        <title>Portal Postal | Cadastro de Destinatários</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Cadastro de Destinatários</div>

                    <form name="form1" action="../../ServInserirDestinatario" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd>DADOS DO DESTINATÁRIO</dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Nome / Razão Social<b class="obg">*</b></label>
                                    <input type="text" name="nome" size="50" value="" />
                                </dd>
                                <dd>
                                    <label>CPF / CNPJ</label>
                                    <input type="text" name="cpf_cnpj" value="" />
                                </dd>
                                <dd>
                                    <label>Empresa</label>
                                    <input type="text" name="empresa" value="" />
                                </dd>
                                <dd>
                                    <label>CEP<b class="obg">*</b><a onclick="window.open('http://www.buscacep.correios.com.br', 'CORREIOS');" ><img src="../../imagensNew/question.png" /></a></label>
                                    <input type="text" name="cep" id="cep" size="8" value="" maxlength="9" onkeypress="mascara(this, maskCep);handleEnter();" onblur="verPesquisarCepDest(this.value);" />
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Endereço</label>
                                    <input type="text" name="endereco" id="endereco" size="50" value="" />
                                </dd>
                                <dd>
                                    <label>Número</label>
                                    <input type="text" name="numero" id="numero" size="10" value="" maxlength="5" onkeypress="mascara(this, maskNumero)" />
                                        <input type="checkbox" name="sn" id="sn" value="S/N" onclick="semNumero();" /> <span style="font-size: 14px;font-weight: bold">S/N</span>
                                </dd>
                                <dd>
                                    <label>Complemento</label>
                                    <input type="text" name="complemento" id="complemento" value="" />
                                </dd>
                                <dd>
                                    <label>Bairro</label>
                                    <input type="text" name="bairro" id="bairro" value="" />
                                </dd>
                                <dd>
                                    <label>Cidade<b class="obg">*</b></label>
                                    <input type="text" name="cidade" id="cidade" readonly value="" />
                                </dd>
                                <dd>
                                    <label>UF<b class="obg">*</b></label>
                                    <input type="hidden" name="uf" id="uf" />
                                    <select name="uf2" id="uf2" disabled >
                                        <option value="AC">AC</option>
                                        <option value="AL">AL</option>
                                        <option value="AP">AP</option>
                                        <option value="AM">AM</option>
                                        <option value="BA">BA</option>
                                        <option value="CE">CE</option>
                                        <option value="DF">DF</option>
                                        <option value="ES">ES</option>
                                        <option value="GO">GO</option>
                                        <option value="MA">MA</option>
                                        <option value="MT">MT</option>
                                        <option value="MS">MS</option>
                                        <option value="MG">MG</option>
                                        <option value="PA">PA</option>
                                        <option value="PB">PB</option>
                                        <option value="PR">PR</option>
                                        <option value="PE">PE</option>
                                        <option value="PI">PI</option>
                                        <option value="RJ">RJ</option>
                                        <option value="RN">RN</option>
                                        <option value="RS">RS</option>
                                        <option value="RO">RO</option>
                                        <option value="RR">RR</option>
                                        <option value="SC">SC</option>
                                        <option value="SP">SP</option>
                                        <option value="SE">SE</option>
                                        <option value="TO">TO</option>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd style="width: 100%;">
                                    <div class="buttons">
                                        <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" />CADASTRAR DESTINATÁRIO</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                    <div id="titulo2">Lista de todos os destinatários</div>
                    <table id="barraAtendimento" border="0">
                        <tr>
                            <td align="left" style="font-weight:bold;font-size:12px;">
                                Pesquisa Rápida:
                                <select style='min-width:150px;' id="columns2" onchange="sorter2.search('query2')"></select>
                                <input type="text" id="query2" onkeyup="sorter2.search('query2')" placeholder="Digite aqui a sua pesquisa..." />
                                <a style="text-decoration:none;font-weight:bold;font-size:11px;" href="javascript:document.getElementById('query2').value='';sorter2.reset()">RESTAURAR PADRÕES</a>
                            </td>
                            <td align="right">
                                <div class="details" style="clear:both;">
                                    <div>Resultado <span id="startrecord2"></span>-<span id="endrecord2"></span> de <span id="totalrecords2"></span></div>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <table cellpadding="0" cellspacing="0" border="0" id="table2" class="tinytable">
                        <thead>
                            <tr>
                                <th><h3>Cod.</h3></th>
                                <th><h3>Nome</h3></th>
                                <th><h3>Empresa</h3></th>
                                <th><h3>Endereço</h3></th>
                                <th><h3>Bairro</h3></th>
                                <th><h3>Cidade / UF</h3></th>
                                <th class="nosort" width="60"><h3>Alterar</h3></th>
                                <th class="nosort" width="60"><h3>Excluir</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Destinatario> lista = contrDestinatario.pesquisa(idCli, "", "", "", "", "", "", "", "", nomeBD);
                                for (int i = 0; i < lista.size(); i++) {
                                    Destinatario des = lista.get(i);
                            %>
                            <tr style="cursor:default;">
                                <td><%= des.getIdDestinatario()%></td>
                                <td><%= des.getNome()%></td>
                                <td><%= des.getEmpresa()%></td>
                                <td><%= des.getEndereco() + ", " + des.getNumero()%></td>
                                <td><%= des.getBairro()%></td>
                                <td><%= des.getCidade() + " / " + des.getUf()%></td>
                                <td align="center"><a onclick="verEditarDestinatario(<%= des.getIdDestinatario()%>, <%= idCli%>);" style="cursor:pointer;" ><img src="../../imagensNew/pencil.png" /></a></td>
                                <td align="center">
                                    <form action="../../ServExcluirDestinatario" method="post" name="formDel">
                                        <input type="hidden" name="idCliente" value="<%= idCli%>" />
                                        <input type="hidden" name="idDestinatario" value="<%= des.getIdDestinatario()%>" />
                                        <input type="image" src="../../imagensNew/cancel.png" border="0" onClick="javascript:if (confirm('Tem certeza que deseja excluir?')){return true;}else{return false;}" />
                                    </form>
                                </td>
                            </tr>
                            <%}%>
                        </tbody>
                    </table>
                    <div id="tablefooter" align='center'>
                        <div align="left" style='float:left; width:20%;'>
                            <select onchange="sorter2.size(this.value)">
                                <option value="5">5</option>
                                <option value="10">10</option>
                                <option value="20" selected="selected">20</option>
                                <option value="50">50</option>
                                <option value="100">100</option>
                            </select>
                            <span>Linhas por Página</span>
                        </div>
                        <div id="tablenav2" class="tablenav">
                            <div>
                                <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1,true)" />
                                <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1,true)" />
                                <select style="margin-left:5px;" id="pagedropdown2"></select>
                                <a style="margin-left:10px;" href="javascript:sorter2.showall()">Ver Tudo</a>
                            </div>
                        </div>
                        <div id="tablelocation">
                            <div class="page">Página <span id="currentpage2"></span> de <span id="totalpages2"></span></div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        var sorter2 = new TINY.table.sorter('sorter2','table2',{
                            headclass:'head',
                            ascclass:'asc',
                            descclass:'desc',
                            evenclass:'evenrow',
                            oddclass:'oddrow',
                            evenselclass:'evenselected',
                            oddselclass:'oddselected',
                            paginate:true,
                            size:20,
                            colddid:'columns2',
                            currentid:'currentpage2',
                            totalid:'totalpages2',
                            startingrecid:'startrecord2',
                            endingrecid:'endrecord2',
                            totalrecid:'totalrecords2',
                            hoverid:'selectedrowDefault',
                            pageddid:'pagedropdown2',
                            navid:'tablenav2',
                            sortcolumn:0,
                            sortdir:1,
                            init:true
                        });
                    </script>


                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
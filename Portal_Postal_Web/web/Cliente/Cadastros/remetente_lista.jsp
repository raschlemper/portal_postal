
<%@page import="Controle.contrRemetente"%>
<%@page import="Entidade.Remetente"%>
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

                if(form.url_logo.value != ""){
                    var indexA = form.url_logo.value.lastIndexOf(".");
                    var indexB = form.url_logo.value.length;
                    var ext = form.url_logo.value.substring(indexA, indexB).toUpperCase();
                    if(ext != ".PNG" && ext != ".JPG" && ext != ".JPEG" && ext != ".GIF" && ext != ".BMP"){
                        alert("A imagem da logo deve ser uma imagem !");
                        return false;
                    }
                }

                if(form.nome.value==""){
                    alert('Preencha o NOME do remetente!');
                    return false;
                }
                if(form.cep.value==""){
                    alert('Preencha o CEP do remetente!');
                    return false;
                }
                if(form.endereco.value==""){
                    alert('Preencha o ENDEREÇO do remetente!');
                    return false;
                }
                if(form.numero.value==""){
                    alert('Preencha o NÚMERO do remetente!');
                    return false;
                }
                if(form.cidade.value==""){
                    alert('Preencha a CIDADE do remetente!');
                    return false;
                }
                if(form.uf.value==""){
                    alert('Preencha a UF do remetente!');
                    return false;
                }
                form.submit();
            }

            function preencherCamposEdit(){
                var form = document.form5;

                if(form.url_logo.value != ""){
                    var indexA = form.url_logo.value.lastIndexOf(".");
                    var indexB = form.url_logo.value.length;
                    var ext = form.url_logo.value.substring(indexA, indexB).toUpperCase();
                    if(ext != ".PNG" && ext != ".JPG" && ext != ".JPEG" && ext != ".GIF" && ext != ".BMP"){
                        alert("A imagem da logo deve ser uma imagem !");
                        return false;
                    }
                }

                if(form.nome.value==""){
                    alert('Preencha o NOME do remetente!');
                    return false;
                }
                if(form.cep.value==""){
                    alert('Preencha o CEP do remetente!');
                    return false;
                }
                if(form.endereco.value==""){
                    alert('Preencha o ENDEREÇO do remetente!');
                    return false;
                }
                if(form.numero.value==""){
                    alert('Preencha o NÚMERO do remetente!');
                    return false;
                }
                if(form.cidade.value==""){
                    alert('Preencha a CIDADE do remetente!');
                    return false;
                }
                if(form.uf.value==""){
                    alert('Preencha a UF do remetente!');
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

        </script>

        <title>Portal Postal | Cadastro de Remetentes</title>

    </head>
    <body>
        <div id="divInteracao" class="esconder" style="top:10%; left:20%; right:20%; bottom:10%;" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/menu_cliente.jsp" %>
        <%@ include file="../../Includes/telaMsg.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">

                    <div id="titulo1">Cadastro de Remetentes</div>

                    <form name="form1" action="../../ServInserirRemetente" method="post" enctype="multipart/form-data">
                    <ul class="ul_formulario">
                        <li class="titulo">
                            <dd>LOGO DO REMETENTE</dd>
                        </li>
                        <li>
                            <dd>
                                <label>ESCOLHA UMA IMAGEM</label>
                                <input type="file" name="url_logo" />
                                <span style="color:red;">*escolha uma imagem caso o remetente tenha um logo.</span>
                            </dd>
                        </li>
                        <li class="titulo">
                            <dd>DADOS DO REMETENTE</dd>
                        </li>
                        <li>
                            <dd>
                                <label>Nome / Razão Social</label>
                                <input type="text" name="nome" size="50" value="" />
                            </dd>
                            <dd>
                                <label>CPF / CNPJ</label>
                                <input type="text" name="cpf_cnpj" value="" />
                            </dd>
                            <dd>
                                <label>CEP</label>
                                <input type="text" name="cep" size="10" value="" maxlength="9" onkeypress="mascara(this, maskCep)" />
                            </dd>
                        </li>
                        <li>
                            <dd>
                                <label>Endereço</label>
                                <input type="text" name="endereco" size="50" value="" />
                            </dd>
                            <dd>
                                <label>Número</label>
                                <input type="text" name="numero" size="10" value="" maxlength="5" onkeypress="mascara(this, maskNumero)" />
                            </dd>
                            <dd>
                                <label>Complemento</label>
                                <input type="text" name="complemento" value="" />
                            </dd>
                            <dd>
                                <label>Bairro</label>
                                <input type="text" name="bairro" value="" />
                            </dd>
                            <dd>
                                <label>Cidade</label>
                                <input type="text" name="cidade" value="" />
                            </dd>
                            <dd>
                                <label>UF</label>
                                <input type="text" name="uf" size="2" value="" />
                            </dd>
                        </li>
                        <li>
                            <dd style="width: 100%;">
                                <div class="buttons">
                                    <input type="hidden" name="idCliente" value="<%= idCli %>" />
                                    <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/tick_circle.png" />CADASTRAR REMETENTE</button>
                                </div>
                            </dd>
                        </li>
                    </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>


                    <div id="titulo2">Lista de todos os remetentes</div>
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
                                <th><h3>Endereço</h3></th>
                                <th><h3>Bairro</h3></th>
                                <th><h3>Cidade / UF</h3></th>
                                <th class="nosort" width="60"><h3>Alterar</h3></th>
                                <th class="nosort" width="60"><h3>Excluir</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                            ArrayList<Remetente> lista = contrRemetente.pesquisa(idCli, "", "", "", "", "", "", nomeBD);
                                            for (int i = 0; i < lista.size(); i++) {
                                                Remetente rem = lista.get(i);
                            %>
                            <tr style="cursor:default;">
                                <td><%= rem.getIdRemetente() %></td>
                                <td><%= rem.getNome() %></td>
                                <td><%= rem.getEndereco() +", "+ rem.getNumero() %></td>
                                <td><%= rem.getBairro() %></td>
                                <td><%= rem.getCidade() +" / "+ rem.getUf() %></td>
                                <td align="center"><a onclick="verEditarRemetente(<%= rem.getIdRemetente() %>, <%= idCli %>);" style="cursor:pointer;" ><img src="../../imagensNew/pencil.png" /></a></td>
                                <td align="center">
                                    <form action="../../ServExcluirRemetente" method="post" name="formDel">
                                        <input type="hidden" name="url_logo" value="<%= rem.getUrl_logo() %>" />
                                        <input type="hidden" name="idCliente" value="<%= idCli %>" />
                                        <input type="hidden" name="idRemetente" value="<%= rem.getIdRemetente() %>" />
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
<%@page import="Entidade.Vpne"%>
<%@page import="Emporium.Controle.ContrVpne"%>
<%@page import="Entidade.ClientesUsuario"%>
<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="Entidade.ClientesDeptos"%>
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
        ClientesUsuario us = (ClientesUsuario) session.getAttribute("usuario_sessao_cliente");
        Clientes cli = (Clientes) session.getAttribute("cliente");
        ArrayList<Integer> dps = us.getDepartamentos();
        ArrayList<ClientesDeptos> listaDep = ContrClienteDeptos.consultaDeptos(cli.getCodigo(), nomeBD);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dataAtual = new Date();
        String vDataAtual = sdf.format(dataAtual);
        String dataI = Util.SomaData.SomarDiasDatas(dataAtual, -60);
        String dataInicioCalendario = Util.SomaData.SomarDiasDatas(dataAtual, -180); // diminui 2 meses

        String where = "";

        String w_nome = request.getParameter("nome");
        String w_cpf = request.getParameter("cpf_cnpj");
        String w_rua = request.getParameter("endereco");
        String w_cep = request.getParameter("cep");
        String w_dataI = request.getParameter("dataIni");
        String w_dataF = request.getParameter("dataFim");
        if (w_dataI != null) {
            w_dataI = Util.FormatarData.DateToBD(w_dataI);
        } else {
            w_dataI = Util.FormatarData.DateToBD(dataI);
        }
        if (w_dataF != null) {
            w_dataF = Util.FormatarData.DateToBD(w_dataF);
        } else {
            w_dataF = Util.FormatarData.DateToBD(vDataAtual);
        }
        int w_idCc = 0;
        try {
            w_idCc = Integer.parseInt(request.getParameter("idDepartamento"));
        } catch (Exception e) {

        }
        if (w_nome != null && !w_nome.equals("")) {
            where += " AND destinatario LIKE '%" + w_nome + "%' ";
        }
        if (w_cpf != null && !w_cpf.equals("")) {
            where += " AND cpf_cnpj_dest LIKE '%" + w_cpf + "%' ";
        }
        if (w_rua != null && !w_rua.equals("")) {
            where += " AND dlogradouro LIKE '%" + w_rua + "%' ";
        }
        if (w_cep != null && !w_cep.equals("")) {
            where += " AND dcep LIKE '%" + w_cep + "%' ";
        }
        if (w_idCc != 0) {
            where += " AND idDepartamento = " + w_idCc + " ";
        }

        where += " AND data > '" + w_dataI + "' AND data < '" + w_dataF + "' ";
                
        ArrayList<Vpne> listaVpne = ContrVpne.listaVpne(where, nomeBD, cli.getCodigo());
        
        
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

        <script type="text/javascript" src="../../javascript/ajax.js"></script>
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        
        <link type="text/css" href="../../javascript/jquery/css/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>        
        <script type="text/javascript" src="../../javascript/jquery/js/jquery-ui-1.8.16.custom.min.js"></script>
        <script type="text/javascript" src="../../javascript/jquery/js/jquery.ui.datepicker-pt-BR.js"></script>

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


            $(function () {
                $("#dataIni").datepicker({
                    minDate: '<%=dataInicioCalendario%>',
                    maxDate: '<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
                $("#dataFim").datepicker({
                    minDate: '<%=dataInicioCalendario%>',
                    maxDate: '<%= vDataAtual%>',
                    showOn: "button",
                    buttonImage: "../../imagensNew/calendario.png",
                    buttonImageOnly: true,
                    showAnim: "slideDown"
                });
            });



            function preencherCampos() {
                var form = document.form1;
                if (form.nome.value.length > 70) {
                    alert('Tamanho máximo de 70 caracteres para o NOME do destinatário!');
                    return false;
                }
                if (form.cep.value.length > 9) {
                    alert('Tamanho máximo de 9 caracteres para o CEP do destinatário!');
                    return false;
                }
                if (form.nome.value.length > 80) {
                    alert('Tamanho máximo de 80 caracteres para o ENDEREÇO do destinatário!');
                    return false;
                }

                form.submit();
            }

            function chamaDivProtecao() {
                var classe = document.getElementById("divProtecao").className;
                if (classe == "esconder") {
                    document.getElementById("divProtecao").className = "mostrar";
                    document.getElementById("divInteracao").className = "mostrar";
                } else {
                    document.getElementById("divProtecao").className = "esconder";
                    document.getElementById("divInteracao").className = "esconder";
                }
            }

            $(document).ready(function () {

                /* ao pressionar uma tecla em um campo que seja de class="pula" */
                $('#cep').keypress(function (e) {
                    /* 
                     * verifica se o evento é Keycode (para IE e outros browsers)
                     * se não for pega o evento Which (Firefox)
                     */
                    var tecla = (e.keyCode ? e.keyCode : e.which);

                    /* verifica se a tecla pressionada foi o ENTER */
                    if (tecla == 13) {
                        verPesquisarCepDest($('#cep').val());
                    }
                    /* impede o sumbit caso esteja dentro de um form */
                    //e.preventDefault(e);
                    //return false;
                })
            });

            function funcEnter(e) {
                var tecla = (e.keyCode ? e.keyCode : e.which);
                /* verifica se a tecla pressionada foi o ENTER */
                if (tecla == 13) {
                    verPesquisarCepDest($('#cep2').val());
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

                    <div id="titulo1">Controle de VPNe</div>

                    <form action="../../ServImportaVpne" method="post"  id="formArq" name="formArq" accept-charset="ISO-8859-1" enctype="multipart/form-data">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd>IMPORTAR ARQUIVO DE RETONRO DO VPNe</dd>
                            </li>
                            <li>
                                <dd>
                                    <label>ESCOLHA UM ARQUIVO PARA IMPORTAR:</label>
                                    <input style="width:300px;" type="file" name="arquivo" id="arquivo" accept=".txt" multiple=""/>
                                </dd>

                            </li>
                            <li>                                
                                <dd>
                                    <label>Departamento / Centro de Custo</label>
                                    <select name="idDepartamento" style="width: 230px;">
                                        <%
                                            if (listaDep != null && listaDep.size() > 0) {
                                                for (int i = 0; i < listaDep.size(); i++) {
                                                    ClientesDeptos cd = listaDep.get(i);
                                                    if (dps.contains(cd.getIdDepartamento())) {
                                        %>
                                        <option value="<%=cd.getIdDepartamento()%>"><%= cd.getNomeDepartamento()%></option>
                                        <%}
                                            }
                                        } else {%>
                                        <option value="0">SEM DEPARTAMENTO</option>
                                        <%}%>
                                    </select>
                                </dd>
                            </li>
                            <li>
                                <dd>
                                    <div class="buttons">
                                        <input type="hidden" name="idCliente" value="<%= cli.getCodigo()%>" />
                                        <button type="submit" class="positive"><img src="../../imagensNew/tick_circle.png" />IMPORTAR</button>
                                    </div>
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <form name="form1" action="vpne.jsp" method="post">
                        <ul class="ul_formulario">
                            <li class="titulo">
                                <dd>PESQUISAR VPNe</dd>
                            </li>
                            <li>
                                <dd>
                                    <label>Periodo de Data</label>
                                    <input type="text" style="width:60px;" name="dataIni" id="dataIni" value="<%= dataI%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                                    até
                                    <input type="text" style="width:60px;" name="dataFim" id="dataFim" value="<%=vDataAtual%>" maxlength="10" onkeypress="mascara(this, maskData);" />
                                </dd>
                                <dd>
                                    <label>Nome / Razão Social<b class="obg">*</b></label>
                                    <input type="text" name="nome" size="50"  maxlength="70" value="" />
                                </dd>
                                <dd>
                                    <label>CPF / CNPJ<b style="color: red;font-size: xx-small">*apenas num.</b></label>
                                    <input type="text" name="cpf_cnpj"  maxlength="18" value=""  onkeydown="mascara(this, maskCpfCnpj);" />
                                </dd>                               
                                <dd>
                                    <label>CEP<b class="obg">*</b><a onclick="window.open('http://www.buscacep.correios.com.br', 'CORREIOS');" ><img src="../../imagensNew/question.png" /></a></label>
                                    <input type="text" name="cep" id="cep" size="8" value="" maxlength="9" onkeypress="mascara(this, maskCep);
                                            handleEnter();" />
                                </dd>                             
                            </li>
                            <li>

                                <dd>
                                    <label>Endereço</label>
                                    <input type="text" name="endereco" id="endereco"  maxlength="80" size="50" value="" />
                                </dd>                            

                                <dd>
                                    <label>Departamento / Centro de Custo</label>
                                    <select name="idDepartamento" style="width: 230px;">
                                        <%
                                            if (listaDep != null && listaDep.size() > 0) {
                                                %> <option value="0">TODOS</option> <%
                                                for (int i = 0; i < listaDep.size(); i++) {
                                                    ClientesDeptos cd = listaDep.get(i);
                                                    if (dps.contains(cd.getIdDepartamento())) {
                                        %>
                                        
                                        <option value="<%=cd.getIdDepartamento()%>"><%= cd.getNomeDepartamento()%></option>
                                        <%}
                                            }
                                        } else {%>
                                        <option value="0">SEM DEPARTAMENTO</option>
                                        <%}%>
                                    </select>
                                </dd>  
                            </li>
                            <li>
                                <dd>
                                    <div class="buttons" >
                                        <input type="hidden" name="idCliente" value="<%= cli.getCodigo()%>" />
                                        <button type="button" class="positive" onclick="return preencherCampos();"><img src="../../imagensNew/lupa.png" />PESQUISAR</button>
                                    </div>  
                                </dd>
                            </li>
                        </ul>
                    </form>
                    <img width="100%" src="../../imagensNew/linha.jpg"/>
                    <div id="titulo2" style="padding-top: 8px;">
                        Lista de todos os VPNe
                        <div style="float: right;">
                             <a style="color: white; font-size: 12px; background: #008ED6; border: 1px solid grey; padding: 4px;" href="#" onclick="document.formExcelDest.action = '../AjaxPages/xls_vpne.jsp';
                                      document.formExcelDest.submit()"><img class="link_img" src="../../imagensNew/excel.png" /> EXPORTAR .XLS</a>
                              <form name="formExcelDest" action="#">
                                  <input type="hidden" name="idCliente" value="<%= cli.getCodigo()%>" />
                                  <input type="hidden" name="nomeBD" value="<%= nomeBD%>" />
                                  <input type="hidden" name="where" value="<%= where%>" />
                              </form> 
                        </div>
                    </div>
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
                                <th><h3>SRO</h3></th>
                                <th><h3>Destinatario</h3></th>
                                <th><h3>CPF/CNPJ</h3></th>
                                <th><h3>Endereço</h3></th>
                                <th><h3>CEP</h3></th>
                                <th><h3>Cidade / UF - ID</h3></th>
                                <th><h3>Data</h3></th>
                                <th><h3>Valor</h3></th>
                                <th><h3>Centro Custo</h3></th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (int i = 0; i < listaVpne.size(); i++) {
                                    Vpne vp = listaVpne.get(i);
                                    Destinatario rem = vp.getRemVpne();
                                    Destinatario des = vp.getDestVpne();

                                    String city = des.getCidade();
                                    String uf = des.getUf();

                                    if (city == null || city.trim().equals("")) {
                                        city = "CEP inexiste no DNE";
                                    }
                                    if (uf == null || uf.trim().equals("")) {
                                        uf = " - Falta no movimento.";
                                    } else {
                                        uf = " - id. " + uf;
                                    }
                                    String dt[] = vp.getData().split("-");

                            %>
                            <tr style="cursor:default;">
                                <td >

                                    <form name=" <%= "frm" + vp.getSro()%>" id="<%= "frm" + vp.getSro()%>" method="post" action="http://www2.correios.com.br/sistemas/rastreamento/Resultado.cfm" target="_blank">
                                        <input type="hidden" name="objetos" id="objetos" value="<%= vp.getSro()%>">
                                    </form>                    
                                    <a href="#" onclick="document.getElementById('<%= "frm" + vp.getSro()%>').submit();"><%= vp.getSro()%></a>
                                </td>
                                <td><%= des.getNome()%></td>
                                <td><%= des.getCpf_cnpj()%></td>
                                <td><%= des.getEndereco() + ", " + des.getNumero()%></td>
                                <td><%= des.getCep()%></td>
                                <td><%= city + uf%></td>
                                <td><%= dt[2] + "/" + dt[1] + "/" + dt[0]%></td>
                                <td><%= vp.getValor()%></td>                              
                                <td><%= vp.getNomeDepto() %></td>                              
                            </tr>
                            <% }%>
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
                                <img src="../../javascript/plugins/TableSorter/images/left_end.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1, true)" />
                                <img src="../../javascript/plugins/TableSorter/images/left.png" width="20" height="20" alt="First Page" onclick="sorter2.move(-1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right.png" width="20" height="20" alt="First Page" onclick="sorter2.move(1)" />
                                <img src="../../javascript/plugins/TableSorter/images/right_end.png" width="20" height="20" alt="Last Page" onclick="sorter2.move(1, true)" />
                                <select style="margin-left:5px;" id="pagedropdown2"></select>
                                <a style="margin-left:10px;" href="javascript:sorter2.showall()">Ver Tudo</a>
                            </div>
                        </div>
                        <div id="tablelocation">
                            <div class="page">Página <span id="currentpage2"></span> de <span id="totalpages2"></span></div>
                        </div>
                    </div>
                    <script type="text/javascript">
                        var sorter2 = new TINY.table.sorter('sorter2', 'table2', {
                            headclass: 'head',
                            ascclass: 'asc',
                            descclass: 'desc',
                            evenclass: 'evenrow',
                            oddclass: 'oddrow',
                            evenselclass: 'evenselected',
                            oddselclass: 'oddselected',
                            paginate: true,
                            size: 20,
                            colddid: 'columns2',
                            currentid: 'currentpage2',
                            totalid: 'totalpages2',
                            startingrecid: 'startrecord2',
                            endingrecid: 'endrecord2',
                            totalrecid: 'totalrecords2',
                            hoverid: 'selectedrowDefault',
                            pageddid: 'pagedropdown2',
                            navid: 'tablenav2',
                            sortcolumn: 0,
                            sortdir: 1,
                            init: true
                        });
                    </script>


                </div>
            </div>
        </div>
    </body>
</html>
<%}%>
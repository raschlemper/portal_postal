
<%@page import="Entidade.EDI"%>
<%@page import="Util.FormataString"%>
<%@page import="Util.FaixaCep"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page import = "java.util.Calendar, java.util.Locale, java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat" %>

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <%
            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD == null) {
                response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
            } else {

                int nivelUsuarioEmp2 = (Integer) session.getAttribute("nivelUsuarioEmp");
                String nomeEmpresa = Controle.contrEmpresa.nomeEmpresaByNomeBD(nomeBD);

                int idCli = (Integer) session.getAttribute("idCliente");
                

        %>
        <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
        <title>Portal Postal | Meu Cadastro</title>
        <meta name="keywords" content="" />
        <meta name="description" content="" />

        <link href="../../css/estilo.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="../js/ajax_portal.js"></script>
        <script type="text/javascript" src="../../javascript/mascara.js"></script>
        <link rel="stylesheet" href="../../javascript/plugins/dropdown/css/style.css" type="text/css" media="screen, projection"/>
        <!--[if lte IE 7]><link rel="stylesheet" type="text/css" href="../../javascript/plugins/dropdown/css/ie.css" media="screen" /><![endif]-->

        <script type="text/javascript" src="../../javascript/jquery/js/jquery-1.6.2.min.js"></script>
        <style>
            .faixas input{  border: 1px solid #aaa; height: 16px; font-size: 12px;}
            .faixas td{border-right: 1px solid silver;border-bottom: 1px solid silver;padding: 2px;}            
            .faixas th{border-right: 1px solid silver;border-bottom: 1px solid silver;padding: 5px 2px;background-color:#e7e7e7;}     
            .disabled {
                pointer-events: none;
                cursor: default;
            }
        </style>

    </head>
    <body>
        <div id="divInteracao" class="esconder" align="center"><input id="textointeracao" /></div>
        <div id="divProtecao" class="esconder"></div>

        <%@ include file="../../Includes/telaMsg.jsp" %>
        <%@ include file="../../Includes/menu_cliente.jsp" %>

        <div id="divPrincipal" align="center">
            <div id="container">
                <div id="conteudo">
                    <div id="titulo1">Codigos EDI utilizados</div>

                    <ul class="ul_formulario" style="width: 90%;">                                                       

                        <li>
                            <dd>
                                <div class="buttons" style="margin:20px 0 20px 0;">

                                    <a class="regular" onclick="addRow();"><img src="../../imagensNew/plus_circle.png" /> Adicionar Codigo EDI</a>
                                </div>
                            </dd>
                        </li>
                        <li>
                            <dd>
                                <table style="margin: 0 auto; border-left:1px solid silver;border:1px solid silver;" id="table" name="table" cellspacing="0" cellpadding="2">
                                    <tr class="faixas">
                                        <th align="center"><b>CODIGO</b></th>
                                        <th align="center"><b>DESCRIÇÂO</b></th>
                                        <th align="center" width="65"><b>REMOVER</b></th>
                                        <th align="center" width="65"><b>SALVAR</b></th>
                                    </tr>

                                    <%
                                        ArrayList<EDI> listaEDI = Controle.ContrlEDI.consultaEDIByCliente(idCli, nomeBD);
                                        int c = 0;
                                        for (EDI e : listaEDI) {

                                    %>                                    
                                    <tr class="faixas">
                                        <td>
                                            <input type="input" id="codigo<%=c%>" name="codigo<%=c%>" value="<%=e.getCodigo()%>" readonly size="6" maxlength="4" onKeyPress="mascara(this, maskNumero)"/>
                                        </td>
                                        <td>                                                            
                                            <input type="input" id="descricao<%=c%>" name="descricao<%=c%>" value="<%=e.getDescricao()%>" onkeypress="changeImg(<%=c%>)" size="60" maxlength="120" />

                                        </td>
                                        <td align="center"><img src='../../imagensNew/cancel.png' style='cursor:pointer;' onclick='delRow(this, <%=c%>);' /></td>
                                        <td align="center"> <a id="a_img<%=c%>" class="disabled" onclick="alterCod($('#codigo<%=c%>').val(), $('#descricao<%=c%>').val(), false,<%=c%>)"><img src='../../imagensNew/pause.png' id="img<%=c%>"/></a></td>
                                    </tr>
                                    <%c++;
                                        }%>
                                    <input type="hidden" name="contador" id="contador" value="<%=listaEDI.size()%>" />
                                </table>
                            </dd>
                        </li>
                    </ul>

                </div>
            </div>
        </div>

        <script>
            var aux = -1;
            function changeImg(id) {
                if (aux !== id) {
                    aux = id;
                    $('#img' + id).attr("src", "../../imagensNew/plus-button.png");
                    $('#a_img' + id).toggleClass('disabled');
                }


            }

            function addRow() {
                var nrLinha = document.getElementById('table').rows.length;
                var linha = document.getElementById('table').insertRow(nrLinha);
                var cont = document.getElementById('contador').value;
                var newCont = parseInt(cont) + 1;
                linha.insertCell(0).innerHTML = "<input type='input' id='codigo" + newCont + "' name='codigo" + newCont + "' size='6' maxlength='4' onKeyPress='mascara(this, maskNumero)'  />";
                linha.insertCell(1).innerHTML = "<input type='input' id='descricao" + newCont + "' name='descricao" + newCont + "' size='60' maxlength='120'  />";
                var cell4 = linha.insertCell(2)
                cell4.innerHTML = "<img src='../../imagensNew/cancel.png' style='cursor:pointer;' onclick='delRow(this," + newCont + " );' />";
                cell4.align = "center";
                var cell4 = linha.insertCell(3)
               // cell4.innerHTML = "<img src='../../imagensNew/plus-button.png' id=" + newCont + " style='cursor:pointer;' onclick=\"alterCod($('#codigo" + newCont + "').val(), $('#descricao" + newCont + "').val(), false)\" />";
                cell4.innerHTML = "<a id='a_img"+newCont+"' onclick=\"alterCod($('#codigo" + newCont + "').val(), $('#descricao" + newCont + "').val(), false, "+newCont+")\"><img src='../../imagensNew/plus-button.png'  id='img"+newCont+"'/></a></td>";
                cell4.align = "center";
                linha.className = "faixas";
                document.getElementById('contador').value = newCont;
            }

            function delRow(linha, c) {
                var nrLinha = document.getElementById('table').rows.length;
                if (nrLinha > 2) {
                    if (confirm('Tem certeza que deseja excluir?')) {
                        var tabela = document.getElementById('table');
                        linha = linha.parentNode.parentNode;
                        id = linha.rowIndex;
                        alert($('#codigo' + c).val());
                        alterCod($('#codigo' + c).val(), '_', true);
                        tabela.deleteRow(id);

                    } else {
                        return false;
                    }
                } else {
                    alert('A tabela deve conter pelo menos um CODIGO!');
                    return false;
                }
            }

            function alterCod(cod, desc, flag, id) {

                $.ajax({
                    method: 'POST',
                    url: '../AjaxPages/alter_edi.jsp',
                    data: {idCli: '<%=idCli%>', codigo: cod, descricao: desc, deletar: flag}
                })
                        .done(function (msg) {
                            alert(msg);
                            $('#img' + id).attr("src", "../../imagensNew/pause.png");
                            $('#a_img' + id).toggleClass('disabled');
                            $('#codigo' + id).prop('readonly', true);;
                        });

            }



        </script>
    </body>
</html>
<%}%>

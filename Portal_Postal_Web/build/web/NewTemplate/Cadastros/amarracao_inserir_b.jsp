
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrAmarracaoServico"%>
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Controle.contrCliente"%>
<%@page import="Emporium.Controle.ContrPreVenda"%>
<%@page import="Entidade.PreVenda"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@ page import = "java.text.DateFormat,java.util.ArrayList,java.sql.Timestamp,java.util.Calendar, java.util.GregorianCalendar, java.util.Date, java.text.SimpleDateFormat"%>

<%
    if (session.getAttribute("usuario") == null) {
        response.sendRedirect("../index.jsp?msgLog=3");
    } else {

        int idNivelDoUsuario = (Integer) session.getAttribute("nivel");
        if (idNivelDoUsuario != 1) {
            response.sendRedirect("../jsp/imp_movimento.jsp?msg=Acesso Negado!");
        }

        String nomeBD = (String) session.getAttribute("empresa");
        int idEmpresa = (Integer) session.getAttribute("idEmpresa");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>

    <body onload="fechaMsg();">   
        <script>
            waitMsg();
        </script>
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                <div class="row">
                    <div class="col-xs-12">                       
                        <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears"></i> Cadastros</b> > <small>Amarrações</small></h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <form name="form1" action="../../ServInserirAmarracao" method="post">
                            <ul class="list-unstyled">   
                                <li class="list-group-item list-group-heading">
                                    <label>DEFINA UMA DESCRIÇÃO UMA SIGLA E O CEP DO CTE PARA ESTA AMARRAÇÃO:</label>
                                </li>
                                <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-xs-6 col-sm-3 col-md-3 col-lg-3">
                                            <label class="small">Descrição: </label>
                                            <div class="input-group">
                                                <span class="input-group-addon" ><i class="fa fa-file-text"></i></span>
                                                <input type="text" name="nome"  class="form-control" placeholder="Descrição" aria-describedby="basic-addon1">
                                            </div>                                        
                                        </div>                    
                                        <div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
                                            <label class="small">Sigla: </label>
                                            <div class="input-group">
                                                <span class="input-group-addon" ><i class="fa fa-adn"></i></span>
                                                <input type="text" name="sigla"  class="form-control text-uppercase" placeholder="Sigla" aria-describedby="basic-addon1" maxlength="3">
                                            </div>                                        
                                        </div>                    
                                        <div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
                                            <label class="small">CEP do CTE: </label>
                                            <div class="input-group">
                                                <span class="input-group-addon" ><i class="fa fa-map-marker"></i></span>
                                                <input type="text" name="cepCTE"  class="form-control" placeholder="Cep do CTE" aria-describedby="basic-addon1" maxlength="9" onkeypress="mascara(this, maskCep)">
                                            </div>                                        
                                        </div>                    
                                        <div class="col-xs-6 col-sm-3 col-md-3 col-lg-2">
                                            <label class="small">Nome do CTE: </label>
                                            <div class="input-group">
                                                <span class="input-group-addon" ><i class="fa fa-building-o"></i></span>
                                                <input type="text" name="nomeCTE"  class="form-control" placeholder="Nome do CTE" aria-describedby="basic-addon1" maxlength="20" >
                                            </div>                                        
                                        </div>                    
                                    </div>                    
                                </li>
                            </ul>
                            <ul class="list-unstyled">   
                                <li class="list-group-item list-group-heading">
                                    <label>SELECIONE OS SERVIÇOS QUE FAZEM PARTE DESTA AMARRAÇÃO:</label>
                                </li>
                                <li class="list-group-item">
                                    <ul class="list-inline"> 
                                        <li class="" id="divServ" style="clear: both;">
                                            <ul class="list-unstyled"> 
                                                <li class="list-group-item list-group-item-danger">
                                                    <label>SERVIÇOS DA ECT</label>
                                                </li>
                                                <li class="list-group-item list-group-item-danger">
                                                    <%                                                    ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicosPorGrupo();
                                                    %>
                                                    <select style="min-width:250px; width: 100%;" name="servico_1" id="servico_1" size="<%=listaServ.size()%>">
                                                        <%

                                                            for (int i = 0; i < listaServ.size(); i++) {
                                                                ServicoECT sv = listaServ.get(i);
                                                                out.println("<option value='" + sv.getGrupoServico() + "'>" + sv.getNomeServico() + "</option>");
                                                            }
                                                        %>
                                                    </select>
                                                    <div id="foo"></div>
                                                </li>
                                            </ul>
                                        </li> 
                                        <li>
                                            <button onclick="trocaServ('servico_1', 'servico_2');" type="button" class="btn btn-success" style="width: 130px;" ><i class="fa fa-lg fa-spc fa-plus"></i> ADICIONAR</button>
                                            <br/><br/>
                                            <button onclick="trocaServ('servico_2', 'servico_1');" type="button" class="btn btn-danger" style="width: 130px;" ><i class="fa fa-lg fa-spc fa-minus"></i> REMOVER</button>
                                        </li>    
                                        <li>
                                            <ul class="list-unstyled"> 
                                                <li class="list-group-item list-group-item-success">
                                                    <label>SERVIÇOS QUE ESTÃO NA AMARRAÇÃO</label>
                                                </li>
                                                <li class="list-group-item list-group-item-success">
                                                    <select style="min-width:250px;  ;width: 100%;" name="servico_2" id="servico_2" size="<%=listaServ.size()%>"></select>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </li>
                            </ul>
                            <ul class="list-unstyled">   
                                <li class="list-group-item list-group-heading">
                                    <label>DEFINA AS FAIXAS DE CEP RELACIONADAS A ESTA AMARRAÇÃO:</label>
                                </li>
                                <li class="list-group-item">
                                    <input type="hidden" name="contador" id="contador" value="1" />
                                    <button type="button" class="btn btn-primary " onclick="addRow();"><i class="fa fa-lg fa-spc fa-plus"></i> ADICIONAR FAIXA DE CEP</button>
                                </li>
                                <li class="list-group-item">
                                    <div style="width: 400px;">
                                        <table class="table table-striped table-bordered table-hover table-condensed" id="table" name="table">
                                            <thead>
                                                <tr>
                                                    <th colspan="2"><b>CEP Inicial</b></th>
                                                    <th colspan="2"><b>CEP Final</b></th>
                                                    <th width="65"><b>Remover</b></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>De</td>
                                                    <td><input class="form-control" type="input" id="cepIni1" name="cepIni1" size="15" maxlength="9" onkeypress="mascara(this, maskCep)" /></td>
                                                    <td>até</td>
                                                    <td><input class="form-control" type="input" id="cepFim1" name="cepFim1" size="15" maxlength="9" onkeypress="mascara(this, maskCep)" /></td>
                                                    <td align="center"><button type='button' class='btn btn-danger btn-sm' onclick='delRow(this);'><i class='fa fa-lg fa-trash'></i></button></td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div style="clear: both"></div>
                                </li>              

                                <li class="list-group-item">
                                    <div class="alert alert-danger">
                                        <span style="color:red; font-weight: bold;">Atenção!</span><br/><br/>
                                        <b>1.</b> As faixas de CEP digitadas não podem coincidir com faixas de CEP de outra amarração com o mesmo serviço!<br/>
                                        <b>2.</b> Ao clicar em <b>"SALVAR AMARRAÇÃO"</b> se a(s) faixa(s) coincidir(em) com alguma faixa de CEP de outra amarração e do mesmo serviço, esta faixa de CEP não será inserida!
                                    </div>
                                    <input type="hidden" name="servicos" id="servicos" />
                                    <button type="submit" name="save" id="sub"  class="btn btn-success" onclick="return validateRow();"><i class="fa fa-lg fa-spc fa-save"></i> SALVAR AMARRAÇÃO</button>
                                </li>
                            </ul>
                        </form>
                        <div class="row spacer-xlg"></div>


                    </div>
                    <!-- /.row -->


                    <!-- /.row -->
                </div>

            </div>   
        </div>
        </div>
        </div>
        <!-- /#page-wrapper -->

        <!-- /#wrapper -->

        <script type="text/javascript">

            function addRow() {
                var nrLinha = document.getElementById('table').rows.length;
                var linha = document.getElementById('table').insertRow(nrLinha);
                var cont = document.getElementById('contador').value;
                var newCont = parseInt(cont) + 1;

                linha.insertCell(0).appendChild(document.createTextNode("De"));
                linha.insertCell(1).innerHTML = "<input class='form-control' type='input' id='cepIni" + newCont + "' name='cepIni" + newCont + "' size='15' maxlength='9' onkeypress='mascara(this,maskCep)' />";
                linha.insertCell(2).appendChild(document.createTextNode("até"));
                linha.insertCell(3).innerHTML = "<input class='form-control' type='input' id='cepFim" + newCont + "' name='cepFim" + newCont + "' size='15' maxlength='9' onkeypress='mascara(this,maskCep)' />";
                var cell4 = linha.insertCell(4)
                cell4.innerHTML = "<button type='button' class='btn btn-danger btn-sm' onclick='delRow(this);'><i class='fa fa-lg fa-trash'></i></button>";
                cell4.align = "center";

                document.getElementById('contador').value = newCont;
            }

            function validateRow() {
                var contador = document.getElementById('contador').value;

                if (document.form1.nome.value == "") {
                    alert("Preencha nome da Amarração!");
                    document.form1.nome.focus();
                    return false;
                }

                if (document.form1.sigla.value == "") {
                    alert("Preencha a sigla da Amarração!");
                    document.form1.sigla.focus();
                    return false;
                }

                var lb = document.getElementById('servico_2');
                var servicos = "";
                for (i = 0; i < lb.length; i++) {
                    if (i == 0) {
                        servicos += lb.options[i].value;
                    } else {
                        servicos += ";" + lb.options[i].value;
                    }
                }
                document.getElementById('servicos').value = servicos;

                if (servicos == "") {
                    alert('Selecione os serviços que esta amarração pertence!')
                    return false;
                }

                //PERCORRE TODOS OS CAMPOS DA TABELA DE PESOS E PRECOS
                for (var i = 1; i <= contador; i++) {
                    if (document.getElementById('cepIni' + i) != null) {
                        var cepIni = document.getElementById('cepIni' + i).value;
                        var cepFim = document.getElementById('cepFim' + i).value;

                        //VERIFICA SE O CEP INICIAL FOI PREENCHIDO
                        if (cepIni == "" || cepIni.length < 9) {
                            alert("Preencha corretamente o CEP inicial!");
                            document.getElementById('cepIni' + i).focus();
                            return false;
                        }

                        //VERIFICA SE O CEP FINAL FOI PREENCHIDO
                        if (cepFim == "" || cepFim.length < 9) {
                            alert("Preencha corretamente o CEP final!");
                            document.getElementById('cepFim' + i).focus();
                            return false;
                        }

                        //VERIFICA SE O CEP INICIAL É MENOR QUE O FINAL
                        cepIni = parseInt(cepIni.replace("-", ""), 10);
                        cepFim = parseInt(cepFim.replace("-", ""), 10);

                        if (cepIni >= cepFim) {
                            alert("O CEP Inicial deve ser menor que o CEP Final!");
                            document.getElementById('cepIni' + i).focus();
                            return false;
                        }
                    }
                }

                for (var i = 1; i <= contador; i++) {
                    if (document.getElementById('cepIni' + i) != null) {
                        var cepIni = parseInt(document.getElementById('cepIni' + i).value.replace("-", ""));
                        var cepFim = parseInt(document.getElementById('cepFim' + i).value.replace("-", ""));
                        for (var j = 1; j <= contador; j++) {
                            if (j != i && document.getElementById('cepIni' + j) != null) {
                                var cepIniAux = parseInt(document.getElementById('cepIni' + j).value.replace("-", ""));
                                var cepFimAux = parseInt(document.getElementById('cepFim' + j).value.replace("-", ""));
                                if ((cepIni >= cepIniAux && cepIni <= cepFimAux) || (cepFim >= cepIniAux && cepFim <= cepFimAux) || (cepIni <= cepIniAux && cepFim >= cepFimAux)) {
                                    alert('Alguma faixa de CEP está se repitindo na tabela!\n\nNão é permitido que a amarração repita faixas de CEP!');
                                    document.getElementById('cepIni' + j).focus();
                                    return false;
                                }
                            }
                        }
                    }
                }

                return true;
            }

            function delRow(linha) {
                var nrLinha = document.getElementById('table').rows.length;
                if (nrLinha > 2) {
                    bootbox.confirm({
                        title: 'Excluir Prefixo de Etiqueta?',
                        message: 'Deseja realmente excluir este prefixo de etiqueta?',
                        buttons: {
                            'cancel': {
                                label: '<i class="fa fa-lg fa-times fa-spc"></i> CANCELAR',
                                className: 'btn btn-default pull-left'
                            },
                            'confirm': {
                                label: '<i class="fa fa-lg fa-trash fa-spc"></i> EXCLUIR',
                                className: 'btn btn-danger pull-right'
                            }
                        },
                        callback: function(result) {
                            if(result){
                                var tabela = document.getElementById('table');
                                linha = linha.parentNode.parentNode;
                                id = linha.rowIndex;
                                tabela.deleteRow(id);
                            }
                        }
                    });
                } else {
                    bootbox.dialog({                        
                        title: "<span style='color:red; font-weight: bold;'>ATENÇÃO</span>",
                        message: "<label>A tabela deve conter pelo menos uma faixa de CEP!</label>",
                        onEscape: true
                    });
                }                 
            }

            function trocaServ(listRemove, listAdiciona) {
                var selIndex = document.getElementById(listRemove).selectedIndex;
                var idServ = document.getElementById(listRemove).options[selIndex].value;
                var nomeServ = document.getElementById(listRemove).options[selIndex].text;

                if (listAdiciona == 'servico_2') {
                    if (verificarGrupoServ(listAdiciona, idServ)) {
                        alert('Este serviço já está na lista do contrato!\n\nPara adicionar, remova o serviço mesmo do contrato!');
                        return false;
                    }
                }

                document.getElementById(listRemove).remove(selIndex);

                var novaOpcao = new Option(nomeServ, idServ);
                document.getElementById(listAdiciona).options[document.getElementById(listAdiciona).length] = novaOpcao;

                OrdenarLista(listRemove);
                OrdenarLista(listAdiciona);

                document.getElementById(listRemove).focus();
                if (document.getElementById(listRemove).length == selIndex) {
                    document.getElementById(listRemove).selectedIndex = selIndex - 1;
                } else {
                    document.getElementById(listRemove).selectedIndex = selIndex;
                }
            }

            function verificarGrupoServ(combo, grupo) {
                var lb = document.getElementById(combo);
                var flag = false;
                for (i = 0; i < lb.length; i++) {
                    var aux = lb.options[i].value.split(";");
                    if (aux[1] == grupo) {
                        flag = true;
                    }
                }
                return flag;
            }

            function OrdenarLista(combo) {
                var lb = document.getElementById(combo);
                arrTexts = new Array();

                for (i = 0; i < lb.length; i++) {
                    arrTexts[i] = lb.options[i].text + "@" + lb.options[i].value;
                }

                arrTexts.sort();

                for (i = 0; i < lb.length; i++) {
                    var aux = arrTexts[i].split("@");
                    lb.options[i].text = aux[0];
                    lb.options[i].value = aux[1];
                }
            }

        </script>
    </body>
</html>
<%}%>

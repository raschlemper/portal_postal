<%@page import="Controle.ContrClienteDeptos"%>
<%@page import="Entidade.ClientesDeptos"%>
<%@page import="Controle.ContrClienteEtiquetas"%>
<%@page import="Entidade.ClienteLogEtiqueta"%>
<%@page import="Entidade.ServicoECT"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import = "java.util.ArrayList,java.sql.Timestamp, java.util.Date, java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%
    
    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("405")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        empresas emp = (empresas) session.getAttribute("emp");
        String nomeBD = (String) session.getAttribute("empresa");

        int idClienteInc = Integer.parseInt(request.getParameter("idCliente"));
        Entidade.Clientes cliInc = Controle.contrCliente.consultaClienteById(idClienteInc, nomeBD);
        String cnpjInc = cliInc.getCnpj();
        String cnpjNum = cnpjInc.replace(".", "").replaceAll("-", "").replaceAll("/", "");

        /*if (cliInc.getTemContrato() == 0) {
            response.sendRedirect("cliente_usuarios.jsp?idCliente=" + idClienteInc + "&msg=Este Cliente nao Possui Contrato ECT");
        } else {*/
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>        
    <body>   
        <script type="text/javascript">
            waitMsg();
        </script> 
        <jsp:include page="../includes/navBarTop.jsp"></jsp:include>
            <div id="wrapper">
            <%@ include file="../includes/menu_agencia_bootstrap.jsp" %>

            <div id="page-content-wrapper">
                <div class="container-fluid">
                    <div id="page-wrapper">

                        <jsp:include page="cliente_menu_b.jsp" >
                            <jsp:param name="nomeBDTab" value="<%= nomeBD%>" />
                            <jsp:param name="activeTab" value="5" />
                            <jsp:param name="idClienteTab" value="<%= idClienteInc%>" />
                            <jsp:param name="temContratoTab" value="<%= cliInc.getTemContrato()%>" />
                            <jsp:param name="nomeClienteTab" value="<%= cliInc.getNomeFantasia()%>" />
                        </jsp:include>    

                        <div class="row">
                            <div class="col-xs-12">

                                <%if (!ContrClienteContrato.consultaStatusContrato(idClienteInc, nomeBD)) {%>
                                <div class="alert alert-danger no-margin">
                                    <a href="#" class="close" data-dismiss="alert">&times;</a>
                                    <strong>ATENÇÃO CONTRATO SUSPENSO!</strong>
                                    <br/><br/>
                                    Este cliente está com o Contrato ECT suspenso ou com a validade vencida!<br/>
                                    Só será possível inserir etiquetas ao regularizar a situação!
                                </div>
                                <br/>
                                <br/>
                                <%}else{%>
                                <form name="form1" action="../../ServInserirFaixaEtiqueta" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <b>Inserir Nova Faixa de Etiquetas Manualmente</b>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                    <label class="small">SERVIÇO</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-envelope"></i></span>                                                
                                                        <select style="max-width: 250px;" class="form-control" name="servico" onchange="verificaTipoReg(this);">
                                                            <option value="0">ESCOLHA UM SERVIÇO</option>
                                                            <%   
                                                                ArrayList<Integer> listaContrato = ContrClienteContrato.consultaContratoClienteGroupByServico(idClienteInc, nomeBD);
                                                                ArrayList<Integer> listaOutros = ContrClienteContrato.consultaOutrosServicosCliente(idClienteInc, nomeBD);
                                                                ArrayList<ServicoECT> listaServ = ContrServicoECT.consultaServicosSigepWEB();                                                             
                                                                if (cliInc.getTemContrato() == 0) {
                                                                    out.print("<option value='0;CARTA'>CARTA</option>");
                                                                    out.print("<option value='0;PAC'>PAC</option>");
                                                                    out.print("<option value='0;SEDEX'>SEDEX</option>");
                                                                    out.print("<option value='0;SEDEX10'>SEDEX 10</option>");
                                                                    out.print("<option value='0;SEDEX12'>SEDEX 12</option>");
                                                                    out.print("<option value='0;PPI'>PROTOCOLO POSTAL</option>");
                                                                    out.print("<option value='0;IMPRESSO'>IMPRESSO</option>");
                                                                    out.print("<option value='0;MDPB_L'>MDPB LOCAL</option>");
                                                                    out.print("<option value='0;MDPB_E'>MDPB ESTADUAL</option>");
                                                                    out.print("<option value='0;MDPB_N'>MDPB NACIONAL</option>");
                                                                }else{
                                                                    for (int i = 0; i < listaServ.size(); i++) {
                                                                        ServicoECT sv = listaServ.get(i);
                                                                        if (listaContrato.contains(sv.getCodECT()) || listaOutros.contains(sv.getCodECT())) {
                                                                            out.print("<option value='0;" + sv.getGrupoServico() + "'>" + sv.getNomeSimples() + "</option>");
                                                                        }
                                                                    }
                                                                }
                                                            %>
                                                        </select>
                                                    </div>   
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                    <label class="small">FAIXA INICIAL</label>
                                                    <div class="input-group">                                              
                                                        <input class="form-control text-uppercase" style="width: 45px;" type="text" autocomplete="off" name="prefixo_inicial" maxlength="2" onkeypress="return maskLetras(event)" />
                                                        <input class="form-control text-uppercase" style="width: 100px;" type="text" autocomplete="off" name="faixa_inicial" maxlength="9" onKeyPress="mascara(this, maskNumero)" />
                                                        <input class="form-control text-uppercase" style="width: 45px;" value="BR" readonly="true" type="text" autocomplete="off" name="sufixo_inicial" maxlength="2" />
                                                    </div> 
                                                    <div id="foo"></div>
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-4 col-lg-3">
                                                    <label class="small">FAIXA FINAL</label>
                                                    <div class="input-group">                                            
                                                        <input class="form-control text-uppercase" style="width: 45px;" type="text" autocomplete="off" name="prefixo_final" maxlength="2" onkeypress="return maskLetras(event)" />
                                                        <input class="form-control text-uppercase" style="width: 100px;" type="text" autocomplete="off" name="faixa_final" maxlength="9" onkeypress="mascara(this, maskNumero)" />
                                                        <input class="form-control text-uppercase" style="width: 45px;" value="BR" readonly="true" type="text" autocomplete="off" name="sufixo_final" maxlength="2" />
                                                    </div> 
                                                    <div id="foo2"></div>
                                                </div>
                                                <div class="col-xs-12 col-sm-4 col-md-3 col-lg-2">
                                                    <label class="small">QUANTIDADE</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-list-ol"></i></span>  
                                                        <input class="form-control" type="text" autocomplete="off" name="qtd" maxlength="5" onkeypress="mascara(this, maskNumero)" />
                                                    </div>   
                                                </div>
                                            </div>
                                           <!--<div class="row form-horizontal hidden" id="tipoRegitro">
                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                    <label class="small">TIPO REGISTRO</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-barcode"></i></span>                                                
                                                        <select style="max-width: 250px;" class="form-control" name="tipo_reg">
                                                            <option value="1">NORMAL</option>
                                                            <option value="0">MÓDICO</option>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>-->
                                        </li>
                                        <li class="list-group-item">
                                            <input type="hidden" name="temContrato" value="<%= cliInc.getTemContrato() %>" />
                                            <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                            <button type="button" class="btn btn-success" onclick="return preencherCampos();"><i class="fa fa-lg fa-save fa-spc"></i> INSERIR ETIQUETAS MANUAL</button>
                                        </li>
                                    </ul>
                                </form>
                                            
                                <%if (cliInc.getTemContrato() == 1) {%>
                                <form name="form2" action="../../ServInserirFaixaEtiquetaWS" method="post">
                                    <ul class="list-unstyled">
                                        <li class="list-group-item list-group-heading">
                                            <b>Inserir Nova Faixa de Etiquetas via SigepWEB</b>
                                        </li>
                                        <li class="list-group-item">
                                            <div class="row form-horizontal">
                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-3">
                                                    <label class="small">Destino da Etiqueta</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-question"></i></span>                                                
                                                        <select class="form-control" style="max-width: 250px;" name="uso">
                                                            <option selected="true" value="0">PARA PORTAL POSTAL</option>
                                                            <option value="1">FORNECIDA PARA CLIENTE</option>
                                                        </select>
                                                    </div>   
                                                </div>
                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-3">
                                                    <label class="small">Serviço</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-envelope"></i></span>     
                                                        <select class="form-control" style="max-width: 250px;" name="servico">
                                                            <option value="0">ESCOLHA UM SERVIÇO</option>
                                                            <%
                                                                for (int i = 0; i < listaServ.size(); i++) {
                                                                    ServicoECT sv = listaServ.get(i);
                                                                    if (listaContrato.contains(sv.getCodECT()) || listaOutros.contains(sv.getCodECT())) {
                                                                        out.print("<option value='" + sv.getCodECT() + ";" + sv.getGrupoServico() + ";" + sv.getIdServicoECT() + "'>" + sv.getNomeSimples() + "</option>");
                                                                    }
                                                                }
                                                            %>
                                                        </select>
                                                    </div>   
                                                </div>
                                                <div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
                                                    <label class="small">QUANTIDADE</label>
                                                    <div class="input-group">
                                                        <span class="input-group-addon" ><i class="fa fa-list-ol"></i></span>                                                          
                                                        <select class="form-control" name="qtd">
                                                            <option value="50">50</option>
                                                            <option value="100">100</option>
                                                            <option value="250">250</option>
                                                            <option value="500">500</option>
                                                            <option value="1000">1000</option>
                                                            <option value="5000">5000</option>
                                                        </select>
                                                    </div>   
                                                </div>
                                            </div>
                                        </li>
                                        <li class="list-group-item">
                                            <%if (cliInc.getLogin_sigep() != null && !cliInc.getLogin_sigep().equals("") && !cliInc.getLogin_sigep().toLowerCase().equals("null")) {%>
                                            <input type="hidden" name="tipoDestinatario" value="C" />
                                            <input type="hidden" name="login" value="<%= emp.getLogin_ws_sigep()%>" />
                                            <input type="hidden" name="senha" value="<%= emp.getSenha_ws_sigep()%>" />
                                            <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />             
                                            <input type="hidden" name="cnpj" maxlength="14" value="<%= cnpjNum%>" onkeypress="mascara(this, maskNumero)" />
                                            <button type="button" class="btn btn-success" onclick="return preencherCampos2();"><i class="fa fa-lg fa-save fa-spc"></i> INSERIR ETIQUETAS SIGEP WEB</button>
                                            <%} else {%>
                                            <div class="alert alert-danger no-margin">
                                                Para habilitar esta funcionalidade, solicite o login do SIGEP WEB para o seu Cliente!<br/>
                                                Após recebida a senha, altere o cadastro do Contrato ECT do cliente no Portal Postal!
                                            </div>
                                            <%}%>
                                        </li>
                                    </ul>
                                </form>
                                <%}}%>

                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <label>Lista de Todas as Faixas de Etiquetas</label>                                        
                                        <div class="pull-right">
                                            <a target="_blank" href="../../ServRelatorioLogEtq?idCliente=<%= idClienteInc%>"><i class="fa fa-file-pdf-o fa-spc fa-lg"></i>&nbsp;&nbsp;RELATÓRIO DE ETIQUETAS CADASTRADAS</a>
                                        </div>
                                    </div>
                                    <div class="panel-body">
                                        <div class="dataTable_wrapper no-padding">
                                            <table class="table table-striped table-bordered table-hover table-condensed" id="dataTables-example">
                                                <thead>
                                                    <tr>
                                                        <th>Nº</th>
                                                        <th>Serviço</th>
                                                        <th>Faixa Inicial</th>
                                                        <th>Faixa Final</th>
                                                        <th>Total</th>
                                                        <th>Utilizadas</th>
                                                        <th>Restantes</th>
                                                        <th>Usuário</th>
                                                        <th>Data Inserção</th>
                                                        <th class="no-sort" width="60">Excluir</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        ArrayList<ClienteLogEtiqueta> listaLog = Controle.ContrClienteEtiquetas.consultaLogFaixas(idClienteInc, 60, nomeBD);
                                                        for (int i = 0; i < listaLog.size(); i++) {
                                                            ClienteLogEtiqueta log = listaLog.get(i);
                                                            int qtdUt = log.getQtdUtilizada();//ContrClienteEtiquetas.contaQtdUtilizadaPorIdLog(log.getIdLog(), 1, nomeBD);
                                                            String nomeServ = log.getServico() + "";

                                                            double dias = Util.FormatarData.diferencaEmDias(log.getDataHora(), new Date());
                                                    %>
                                                    <tr style="cursor:default;">
                                                        <td><%= log.getIdLog()%></td>
                                                        <td><%= nomeServ%></td>
                                                        <td><%= log.getFaixaIni()%></td>
                                                        <td><%= log.getFaixaFim()%></td>
                                                        <td><%= log.getQtd()%></td>
                                                        <td><%= qtdUt%></td>
                                                        <td><%= log.getQtd() - qtdUt%></td>
                                                        <td><%= log.getNomeUsuario()%></td>
                                                        <td <%if (dias > 90) {%>class='text-danger'<%}%>><%= sdf.format(log.getDataHora())%></td>
                                                        <td align="center">
                                                            <%if (qtdUt == 0) {%>
                                                            <form action="../../ServExcluirFaixaEtiqueta" method="post" name="formDel">
                                                                <input type="hidden" name="idLog" value="<%= log.getIdLog()%>" />
                                                                <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                                                <button type="button" class="btn btn-sm btn-danger" onclick="confirmExcluir(this, 'Tem certeza que deseja excluir esta faixa?', 'Deseja excluir a faixa?');" ><i class="fa fa-lg fa-trash"></i></button>
                                                            </form>
                                                            <%} else if ((log.getQtd() - qtdUt) > 0) {%>
                                                            <form action="../../ServSuspenderFaixaEtiqueta" method="post" name="formDel">
                                                                <input type="hidden" name="idLog" value="<%= log.getIdLog()%>" />
                                                                <input type="hidden" name="idCliente" value="<%= idClienteInc%>" />
                                                                <button type="button" class="btn btn-sm btn-info" onclick="confirmSuspender(this, 'Tem certeza que deseja suspender esta faixa?', 'Deseja suspender a faixa?');" ><i class="fa fa-lg fa-pause"></i></button>
                                                            </form>
                                                            <%} else {%>
                                                            <b class="text-danger">Suspensa</b>
                                                            <%}%>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>


                            </div>
                        </div>


                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
            function confirmExcluir(button, pergunta, titulo) {
                bootbox.confirm({
                    title: titulo,
                    message: pergunta,
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
                    callback: function (result) {
                        if (result) {
                            button.form.submit();
                        }
                    }
                });
            }
            function confirmSuspender(button, pergunta, titulo) {
                bootbox.confirm({
                    title: titulo,
                    message: pergunta,
                    buttons: {
                        'cancel': {
                            label: '<i class="fa fa-lg fa-times fa-spc"></i> CANCELAR',
                            className: 'btn btn-default pull-left'
                        },
                        'confirm': {
                            label: '<i class="fa fa-lg fa-pause fa-spc"></i> SUSPENDER',
                            className: 'btn btn-danger pull-right'
                        }
                    },
                    callback: function (result) {
                        if (result) {
                            button.form.submit();
                        }
                    }
                });
            }

            function preencherCampos() {
                var form = document.form1;
                var pi = form.prefixo_inicial.value.toString().length;
                var pf = form.prefixo_final.value.toString().length;
                var fi = form.faixa_inicial.value.toString().length;
                var ff = form.faixa_final.value.toString().length;

                //VERIFICA SE ESCOLHEU SERVIÇO
                if (form.servico.value === '0') {
                    alert('Escolha um serviço para a faixa de etiqueta!');
                    return false;
                }
                //VEROFOCA QTD CARACTERES DOS PREFIXOS
                if (pi !== 2) {
                    alert('Preencha Corretamente o Prefixo Inicial');
                    return false;
                }
                if (pf !== 2) {
                    alert('Preencha Corretamente o Prefixo Final');
                    return false;
                }
                //VERIFICA SE PREFIXOS ESTAO IGUAIS
                if (form.prefixo_inicial.value !== form.prefixo_final.value) {
                    alert('Os Prefixos não estão iguais!\n\nConfira com atenção se estão corretamente preenchidos!');
                    return false;
                }
                //VERIFICA QTD DE CARACTERES
                if (fi !== 9) {
                    alert('Preencha Completamente a Faixa Inicial!');
                    return false;
                }
                if (ff !== 9) {
                    alert('Preencha Completamente a Faixa Final!');
                    return false;
                }
                //VERIFICA DIGITO VERIFICADOR
                var dig1 = valida_SRO(form.faixa_inicial.value);
                if (dig1 !== 'ok') {
                    alert("Erro na Faixa de Etiqueta Inicial!\n\nConfira com atenção se está corretamente preenchida!\nVerifique se o Digito Verificador está correto!\nDigito calculado pelo sistema: " + dig1 + "");
                    return false;
                }
                var dig2 = valida_SRO(form.faixa_final.value);
                if (dig2 !== 'ok') {
                    alert("Erro na Faixa de Etiqueta Final!\n\nConfira com atenção se está corretamente preenchida!\nVerifique se o Digito Verificador está correto!\nDigito calculado pelo sistema: " + dig2 + "");
                    return false;
                }
                //VERIFICA SE QTD DE ETIQUESTAS ESTA CORRETO COM A QTD DIGITADA
                var nIni = form.faixa_inicial.value.substring(0, 8);
                var nFim = form.faixa_final.value.substring(0, 8);
                var qtd = parseInt(nFim - nIni + 1);

                if (parseInt(form.qtd.value) !== qtd) {
                    alert('Quantidade de Etiquetas não Confere!\n\nQuantidade Calculada pelo Sistema: ' + qtd);
                    return false;
                }

                waitMsg();
                form.submit();
            }

            function preencherCampos2() {
                var form = document.form2;

                if (form.servico.value === '0') {
                    alert('Escolha um Serviço para a Faixa de Etiqueta!');
                    return false;
                }
                if (form.cnpj.value === '' || form.cnpj.value.length !== 14) {
                    alert('Preencha o CNPJ completo do seu Cliente!');
                    return false;
                }
                if (form.login.value === '') {
                    alert('Preencha o Login do Sigep WEB de sua Agência!');
                    return false;
                }
                if (form.senha.value === '') {
                    alert('Preencha a Senha do Sigep WEB de sua Agência!');
                    return false;
                }

                waitMsg();
                form.submit();
            }


            function objDepto(cartao, depto) {
                this.cartao = cartao;
                this.depto = depto;
            }

            /************************************/

            function AllTables() {
                StartDataTable('dataTables-example');
                LoadSelect2Script(MakeSelectDataTable('dataTables-example'));
                fechaMsg();
            }

            $(document).ready(function () {
                LoadDataTablesScripts(AllTables);
            });
        </script>
    </body>
</html>
<%}%>
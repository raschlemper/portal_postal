
<%@page import="Entidade.ServicoAbrangencia"%>
<%@page import="Entidade.ServicoPrefixo"%>
<%@page import="Controle.ContrServicoPrefixo"%>
<%@page import="Controle.ContrServicoECT"%>
<%@page import="Entidade.ServicoECT"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
    Usuario usrSessao = (Usuario) session.getAttribute("agf_usuario");
    if (usrSessao == null) {
        response.sendRedirect("../../index.jsp?msgLog=3");
    } else if (usrSessao.getListaAcessosPortalPostal().contains("402")) {
        response.sendRedirect("../../NewTemplate/Dashboard/index.jsp?msg=Usuario sem permissao!");
    } else {

        String nomeBD = (String) session.getAttribute("empresa");
        int idEmpresa = (Integer) session.getAttribute("idEmpresa");

        ArrayList<ServicoAbrangencia> listaEsedex = Controle.ContrServicoAbrangencia.consultaTodosByServico("ESEDEX", nomeBD);
        ArrayList<ServicoAbrangencia> listaSedex10 = Controle.ContrServicoAbrangencia.consultaTodosByServico("SEDEX10", nomeBD);
        ArrayList<ServicoAbrangencia> listaSedex12 = Controle.ContrServicoAbrangencia.consultaTodosByServico("SEDEX12", nomeBD);
        ArrayList<ServicoAbrangencia> listaSedexHJ = Controle.ContrServicoAbrangencia.consultaTodosByServico("SEDEXHJ", nomeBD);
        ArrayList<ServicoAbrangencia> listaPax = Controle.ContrServicoAbrangencia.consultaTodosByServico("PAX", nomeBD);
        ArrayList<ServicoAbrangencia> listaMdpbL = Controle.ContrServicoAbrangencia.consultaTodosByServico("MDPB_L", nomeBD);
        ArrayList<ServicoAbrangencia> listaMdpbE = Controle.ContrServicoAbrangencia.consultaTodosByServico("MDPB_E", nomeBD);
        ArrayList<ServicoAbrangencia> listaMdpbN = Controle.ContrServicoAbrangencia.consultaTodosByServico("MDPB_N", nomeBD);

        int sus_esedex = 0;
        if (listaEsedex != null && listaEsedex.size() > 0) {
            sus_esedex = listaEsedex.get(0).getServico_suspenso();
        }
        int sus_sedex10 = 0;
        if (listaSedex10 != null && listaSedex10.size() > 0) {
            sus_sedex10 = listaSedex10.get(0).getServico_suspenso();
        }
        int sus_sedex12 = 0;
        if (listaSedex12 != null && listaSedex12.size() > 0) {
            sus_sedex12 = listaSedex12.get(0).getServico_suspenso();
        }
        int sus_sedexHJ = 0;
        if (listaSedexHJ != null && listaSedexHJ.size() > 0) {
            sus_sedexHJ = listaSedexHJ.get(0).getServico_suspenso();
        }
        int sus_pax = 0;
        if (listaPax != null && listaPax.size() > 0) {
            sus_pax = listaPax.get(0).getServico_suspenso();
        }
        int sus_mdpbL = 0;
        if (listaMdpbL != null && listaMdpbL.size() > 0) {
            sus_mdpbL = listaMdpbL.get(0).getServico_suspenso();
        }
        int sus_mdpbE = 0;
        if (listaMdpbE != null && listaMdpbE.size() > 0) {
            sus_mdpbE = listaMdpbE.get(0).getServico_suspenso();
        }
        int sus_mdpbN = 0;
        if (listaMdpbN != null && listaMdpbN.size() > 0) {
            sus_mdpbN = listaMdpbN.get(0).getServico_suspenso();
        }
        // Clocar um combobox igua da pagina prefixos de etiqueta e trocar no onchange com jquery o style display none>> block dependo do serviço

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <title>Portal Postal</title>
        <%@ include file="../includes/Css_js.jsp" %>
    </head>

    <body onload="fechaMsg();hideAll();">   
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
                            <div class="col-md-12">                       
                                <h4 class="page-header"><b class="text-primary"><i class="fa fa-gears"></i> Cadastros</b> > <small>Abrangência de Serviços</small></h4>
                            </div>
                        </div>
                        <div class="col-lg-12">

                            <!-- /.row -->
                            <div class="row">
                                <form name="form1" action="../../ServServicoAbrangencia" method="post">
                                <div class="col-xs-12 col-sm-12 col-md-5 col-lg-4">
                                    <ul class="list-unstyled">                            
                                        <li class="list-group-item">
                                            <div id="demoBasic"></div>
                                        </li> 
                                        <li class="list-group-item" id="salvar_abragencia" style="display: none;">                                
                                            <div>
                                                <button type="submit" name="save" id="sub" class="btn btn-success" onclick="return validateRow();"><i class="fa fa-lg fa-spc fa-save"></i> SALVAR ABRANGÊNCIAS</button>
                                            </div>                                
                                        </li>
                                    </ul>
                                </div>
                                    <div style="display: none;" class="col-xs-12 col-sm-12 col-md-7 col-lg-6" id="esedex">
                                        <ul class="list-unstyled">                                                         
                                            <li class="list-group-item list-group-heading">
                                                <label>E-SEDEX:</label>
                                                <%if (sus_esedex == 1) {%>
                                                <span style="font-size: 16px;" class="text-danger"><strong>ESTE SERVIÇO ESTÁ SUSPENSO !!!</strong></span>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">
                                                <img src="../../imagensNew/esedex.png" /> 
                                                <%if (sus_esedex == 0) {%>
                                                <button type="button" class="btn btn-danger pull-right" onclick="suspender_serv('ESEDEX');"><i class="fa fa-lg fa-spc fa-pause"></i> SUSPENDER SERVIÇO</button>
                                                <%} else {%>
                                                <button type="button" class="btn btn-success pull-right" onclick="suspender_serv('ESEDEX');"><i class="fa fa-lg fa-spc fa-refresh"></i> REATIVAR SERVIÇO</button>
                                                <%}%>

                                            </li>
                                            <li class="list-group-item">
                                                <input type="hidden" name="e_contador" id="e_contador" value="<%= listaEsedex.size()%>" />                                                 
                                                <button type="button" class="btn btn-primary center-block" onclick="addRow('ESEDEX', 'e_');"><i class="fa fa-lg fa-spc fa-plus"></i> ADICIONAR FAIXA DE CEP</button>
                                            </li>
                                            <li class="list-group-item">

                                                <table class="table table-striped table-bordered table-hover table-condensed" id="e_table" name="e_table">
                                                    <tr>
                                                        <th align="center" colspan="2"><b>CEP Inicial</b></th>
                                                        <th align="center" colspan="2"><b>CEP Final</b></th>
                                                        <th align="center"><b>REM.</b></th>
                                                        <th align="center"><b>SUS.</b></th>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < listaEsedex.size(); i++) {
                                                            ServicoAbrangencia cob = listaEsedex.get(i);
                                                            String cepInicial = Util.FormataString.formataCep(cob.getCep_inicial() + "");
                                                            String cepFinal = Util.FormataString.formataCep(cob.getCep_final() + "");
                                                            int fs = cob.getFaixa_suspensa();
                                                            String img = "fa fa-lg fa-pause";
                                                            if (fs == 1) {
                                                                img = "fa fa-lg fa-refresh";
                                                            }
                                                            sus_esedex = cob.getServico_suspenso();
                                                    %>
                                                    <tr <%if (fs == 1) {%>style="background: khaki;"<%}%> class="faixas">
                                                        <td>De</td>
                                                        <td><input class="form-control" type="input" id="e_cepIni<%= i + 1%>" name="e_cepIni<%= i + 1%>" value="<%= cepInicial%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" /></td>
                                                        <td>Até</td>
                                                        <td>
                                                            <input class="form-control" type="input" id="e_cepFim<%= i + 1%>" name="e_cepFim<%= i + 1%>" value="<%= cepFinal%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" />
                                                            <input type="hidden" name="e_suspenso_<%= i + 1%>" id="e_suspenso_<%= i + 1%>" value="<%= fs%>" />
                                                        </td>
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-danger" onclick="delRow(this, 'e_');"><i class="fa fa-lg fa-trash"></i></button>
                                                        </td>
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-info" onclick='suspender("e_", <%= i + 1%>);'><i id="e_img_sus_<%= i + 1%>" class="<%= img%>"></i></button>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </table>

                                            </li>        
                                        </ul>
                                    </div>
                                    <div style="display: none;" class="col-xs-12 col-sm-12 col-md-7 col-lg-6" id="sedex10" >
                                        <ul class="list-unstyled">                                                         
                                            <li class="list-group-item list-group-heading">
                                                <label>SEDEX 10:</label>                                        
                                                <%if (sus_sedex10 == 1) {%>
                                                <span style="font-size: 16px;" class="text-danger"><strong>ESTE SERVIÇO ESTÁ SUSPENSO !!!</strong></span>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">
                                                <img src="../../imagensNew/sedex10.png" height="" />
                                                <%if (sus_sedex10 == 0) {%>
                                                <button type="button" class="btn btn-danger pull-right" onclick="suspender_serv('SEDEX10');"><i class="fa fa-lg fa-spc fa-pause"></i> SUSPENDER SERVIÇO</button>
                                                <%} else {%>
                                                <button type="button" class="btn btn-success pull-right" onclick="suspender_serv('SEDEX10');"><i class="fa fa-lg fa-spc fa-refresh"></i> REATIVAR SERVIÇO</button>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">

                                                <div class="buttons">
                                                    <input type="hidden" name="contador" id="contador" value="<%= listaSedex10.size()%>" />
                                                    <button type="button" class="btn btn-primary center-block" onclick="addRow('SEDEX10', '');"><i class="fa fa-lg fa-spc fa-plus"></i> ADICIONAR FAIXA DE CEP</button>
                                                </div>

                                            </li>
                                            <li class="list-group-item">

                                                <table class="table table-striped table-bordered table-hover table-condensed" id="table" name="table">
                                                    <tr>
                                                        <th align="center" colspan="2"><b>CEP Inicial</b></th>
                                                        <th align="center" colspan="2"><b>CEP Final</b></th>
                                                        <th align="center" ><b>REM.</b></th>
                                                        <th align="center" ><b>SUS.</b></th>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < listaSedex10.size(); i++) {
                                                            ServicoAbrangencia cob = listaSedex10.get(i);
                                                            String cepInicial = Util.FormataString.formataCep(cob.getCep_inicial() + "");
                                                            String cepFinal = Util.FormataString.formataCep(cob.getCep_final() + "");
                                                            int fs = cob.getFaixa_suspensa();
                                                            String img = "fa fa-lg fa-pause";
                                                            if (fs == 1) {
                                                                img = "fa fa-lg fa-refresh";
                                                            }
                                                            sus_sedex10 = cob.getServico_suspenso();
                                                    %>
                                                    <tr <%if (fs == 1) {%>style="background: khaki;"<%}%> class="faixas">
                                                        <td>De</td>
                                                        <td><input class="form-control" type="input" id="cepIni<%= i + 1%>" name="cepIni<%= i + 1%>" value="<%= cepInicial%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" /></td>
                                                        <td>Até</td>
                                                        <td>                                                            
                                                            <input class="form-control" type="input" id="cepFim<%= i + 1%>" name="cepFim<%= i + 1%>" value="<%= cepFinal%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" />
                                                            <input type="hidden" name="suspenso_<%= i + 1%>" id="suspenso_<%= i + 1%>" value="<%= fs%>" />
                                                        </td>
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-danger" onclick="delRow(this, '');"><i class="fa fa-lg fa-trash"></i></button>
                                                        </td>
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-info" onclick='suspender("", <%= i + 1%>);'><i id="img_sus_<%= i + 1%>" class="<%= img%>"></i></button>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </table>

                                            </li>        
                                        </ul>
                                    </div>
                                    <div style="display: none;" class="col-xs-12 col-sm-12 col-md-7 col-lg-6" id="sedex12">
                                        <ul class="list-unstyled">                                                        
                                            <li class="list-group-item list-group-heading">
                                                <label>SEDEX 12:</label>
                                                <%if (sus_sedex12 == 1) {%>
                                                <span style="font-size: 16px;" class="text-danger"><strong>ESTE SERVIÇO ESTÁ SUSPENSO !!!</strong></span>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">
                                                <img src="../../imagensNew/sedex12.png" height="" />
                                                <%if (sus_sedex12 == 0) {%>
                                                <button type="button" class="btn btn-danger pull-right" onclick="suspender_serv('SEDEX12');"><i class="fa fa-lg fa-spc fa-pause"></i> SUSPENDER SERVIÇO</button>
                                                <%} else {%>
                                                <button type="button" class="btn btn-success pull-right" onclick="suspender_serv('SEDEX12');"><i class="fa fa-lg fa-spc fa-refresh"></i> REATIVAR SERVIÇO</button>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">

                                                <div class="buttons">
                                                    <input type="hidden" name="d_contador" id="d_contador" value="<%= listaSedex12.size()%>" />                        
                                                    <button type="button" class="btn btn-primary center-block" onclick="addRow('SEDEX12', 'd_')"><i class="fa fa-lg fa-spc fa-plus"></i> ADICIONAR FAIXA DE CEP</button>                                            
                                                </div>

                                            </li>
                                            <li class="list-group-item">

                                                <table class="table table-striped table-bordered table-hover table-condensed" id="d_table" name="d_table" cellspacing="0" cellpadding="2">
                                                    <tr>
                                                        <th align="center" colspan="2"><b>CEP Inicial</b></th>
                                                        <th align="center" colspan="2"><b>CEP Final</b></th>
                                                        <th align="center" ><b>REM.</b></th>
                                                        <th align="center"><b>SUS.</b></th>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < listaSedex12.size(); i++) {
                                                            ServicoAbrangencia cob = listaSedex12.get(i);
                                                            String cepInicial = Util.FormataString.formataCep(cob.getCep_inicial() + "");
                                                            String cepFinal = Util.FormataString.formataCep(cob.getCep_final() + "");
                                                            int fs = cob.getFaixa_suspensa();
                                                            String img = "fa fa-lg fa-pause";
                                                            if (fs == 1) {
                                                                img = "fa fa-lg fa-refresh";
                                                            }
                                                            sus_sedex12 = cob.getServico_suspenso();
                                                    %>
                                                    <tr <%if (fs == 1) {%>style="background: khaki;"<%}%> class="faixas">
                                                        <td>De</td>
                                                        <td><input class="form-control" type="input" id="d_cepIni<%= i + 1%>" name="d_cepIni<%= i + 1%>" value="<%= cepInicial%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" /></td>
                                                        <td>Até</td>
                                                        <td>                                                            
                                                            <input class="form-control" type="input" id="d_cepFim<%= i + 1%>" name="d_cepFim<%= i + 1%>" value="<%= cepFinal%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" />
                                                            <input type="hidden" name="d_suspenso_<%= i + 1%>" id="d_suspenso_<%= i + 1%>" value="<%= fs%>" />
                                                        </td>

                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-danger" onclick="delRow(this, 'd_');"><i class="fa fa-lg fa-trash"></i></button>
                                                        </td>
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-info" onclick='suspender("d_", <%= i + 1%>);'><i id="d_img_sus_<%= i + 1%>" class="<%= img%>"></i></button>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </table>

                                            </li>        
                                        </ul>
                                    </div>
                                    <div style="display: none;" class="col-xs-12 col-sm-12 col-md-7 col-lg-6" id="pax">
                                        <ul class="list-unstyled">                                                        
                                            <li class="list-group-item list-group-heading">
                                                <label>PAC GRANDE:</label>
                                                <%if (sus_pax == 1) {%>
                                                <span style="font-size: 16px;" class="text-danger"><strong>ESTE SERVIÇO ESTÁ SUSPENSO !!!</strong></span>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">
                                                <img src="../../imagensNew/pax.png" height="" />
                                                <%if (sus_pax == 0) {%>
                                                <button type="button" class="btn btn-danger pull-right" onclick="suspender_serv('PAX');"><i class="fa fa-lg fa-spc fa-pause"></i> SUSPENDER SERVIÇO</button>
                                                <%} else {%>
                                                <button type="button" class="btn btn-success pull-right" onclick="suspender_serv('PAX');"><i class="fa fa-lg fa-spc fa-refresh"></i> REATIVAR SERVIÇO</button>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">

                                                <div class="buttons">
                                                    <input type="hidden" name="pax_contador" id="pax_contador" value="<%= listaPax.size()%>" />
                                                    <button type="button" class="btn btn-primary center-block" onclick="addRow('PAX', 'pax_')"><i class="fa fa-lg fa-spc fa-plus"></i> ADICIONAR FAIXA DE CEP</button>
                                                </div>

                                            </li>
                                            <li class="list-group-item">

                                                <table class="table table-striped table-bordered table-hover table-condensed" id="pax_table" name="pax_table">
                                                    <tr>
                                                        <th align="center" colspan="2"><b>CEP Inicial</b></th>
                                                        <th align="center" colspan="2"><b>CEP Final</b></th>
                                                        <th align="center" ><b>REM.</b></th>
                                                        <th align="center" ><b>SUS.</b></th>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < listaPax.size(); i++) {
                                                            ServicoAbrangencia cob = listaPax.get(i);
                                                            String cepInicial = Util.FormataString.formataCep(cob.getCep_inicial() + "");
                                                            String cepFinal = Util.FormataString.formataCep(cob.getCep_final() + "");
                                                            int fs = cob.getFaixa_suspensa();
                                                            String img = "fa fa-lg fa-pause";
                                                            if (fs == 1) {
                                                                img = "fa fa-lg fa-refresh";
                                                            }
                                                            sus_pax = cob.getServico_suspenso();
                                                    %>
                                                    <tr <%if (fs == 1) {%>style="background: khaki;"<%}%> class="faixas">
                                                        <td>De</td>
                                                        <td><input class="form-control" type="input" id="pax_cepIni<%= i + 1%>" name="pax_cepIni<%= i + 1%>" value="<%= cepInicial%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" /></td>
                                                        <td>Até</td>
                                                        <td>                                                            
                                                            <input class="form-control" type="input" id="pax_cepFim<%= i + 1%>" name="pax_cepFim<%= i + 1%>" value="<%= cepFinal%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" />
                                                            <input type="hidden" name="pax_suspenso_<%= i + 1%>" id="pax_suspenso_<%= i + 1%>" value="<%= fs%>" />
                                                        </td>                                                
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-danger" onclick="delRow(this, 'pax_');"><i class="fa fa-lg fa-trash"></i></button>
                                                        </td>
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-info" onclick='suspender("pax_", <%= i + 1%>);'><i id="pax_img_sus_<%= i + 1%>" class="<%= img%>"></i></button>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </table>

                                            </li>        
                                        </ul>
                                    </div>  
                                    <div style="display: none;" class="col-xs-12 col-sm-12 col-md-7 col-lg-6" id="sedexhj">
                                        <ul class="list-unstyled">                                                        
                                            <li class="list-group-item list-group-heading">
                                                <label>SEDEX HOJE:</label>
                                                <%if (sus_sedexHJ == 1) {%>
                                                <span style="font-size: 16px;" class="text-danger"><strong>ESTE SERVIÇO ESTÁ SUSPENSO !!!</strong></span>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">
                                                <img src="../../imagensNew/sedexhj.png" height="" />
                                                <%if (sus_sedexHJ == 0) {%>
                                                <button type="button" class="btn btn-danger pull-right" onclick="suspender_serv('SEDEXHJ');"><i class="fa fa-lg fa-spc fa-pause"></i> SUSPENDER SERVIÇO</button>
                                                <%} else {%>
                                                <button type="button" class="btn btn-success pull-right" onclick="suspender_serv('SEDEXHJ');"><i class="fa fa-lg fa-spc fa-refresh"></i> REATIVAR SERVIÇO</button>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">

                                                <div class="buttons">
                                                    <input type="hidden" name="sedexhj_contador" id="sedexhj_contador" value="<%= listaSedexHJ.size()%>" />
                                                    <button type="button" class="btn btn-primary center-block" onclick="addRow('SEDEXHJ', 'sedexhj_')"><i class="fa fa-lg fa-spc fa-plus"></i> ADICIONAR FAIXA DE CEP</button>
                                                </div>

                                            </li>
                                            <li class="list-group-item">

                                                <table class="table table-striped table-bordered table-hover table-condensed" id="sedexhj_table" name="sedexhj_table">
                                                    <tr>
                                                        <th align="center" colspan="2"><b>CEP Inicial</b></th>
                                                        <th align="center" colspan="2"><b>CEP Final</b></th>
                                                        <th align="center" ><b>REM.</b></th>
                                                        <th align="center" ><b>SUS.</b></th>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < listaSedexHJ.size(); i++) {
                                                            ServicoAbrangencia cob = listaSedexHJ.get(i);
                                                            String cepInicial = Util.FormataString.formataCep(cob.getCep_inicial() + "");
                                                            String cepFinal = Util.FormataString.formataCep(cob.getCep_final() + "");
                                                            int fs = cob.getFaixa_suspensa();
                                                            String img = "fa fa-lg fa-pause";
                                                            if (fs == 1) {
                                                                img = "fa fa-lg fa-refresh";
                                                            }
                                                            sus_pax = cob.getServico_suspenso();
                                                    %>
                                                    <tr <%if (fs == 1) {%>style="background: khaki;"<%}%> class="faixas">
                                                        <td>De</td>
                                                        <td><input class="form-control" type="input" id="sedexhj_cepIni<%= i + 1%>" name="sedexhj_cepIni<%= i + 1%>" value="<%= cepInicial%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" /></td>
                                                        <td>Até</td>
                                                        <td>                                                            
                                                            <input class="form-control" type="input" id="sedexhj_cepFim<%= i + 1%>" name="sedexhj_cepFim<%= i + 1%>" value="<%= cepFinal%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" />
                                                            <input type="hidden" name="sedexhj_suspenso_<%= i + 1%>" id="sedexhj_suspenso_<%= i + 1%>" value="<%= fs%>" />
                                                        </td>                                                
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-danger" onclick="delRow(this, 'sedexhj_');"><i class="fa fa-lg fa-trash"></i></button>
                                                        </td>
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-info" onclick='suspender("sedexhj_", <%= i + 1%>);'><i id="sedexhj_img_sus_<%= i + 1%>" class="<%= img%>"></i></button>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </table>

                                            </li>        
                                        </ul>
                                    </div>  
                                    <div style="display: none;" class="col-xs-12 col-sm-12 col-md-7 col-lg-6" id="mdpbl">
                                        <ul class="list-unstyled">                                                        
                                            <li class="list-group-item list-group-heading">
                                                <label>MDPB LOCAL:</label>
                                                <%if (sus_mdpbL == 1) {%>
                                                <span style="font-size: 16px;" class="text-danger"><strong>ESTE SERVIÇO ESTÁ SUSPENSO !!!</strong></span>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">
                                                <img src="../../imagensNew/chancelas/MDPB_L.png" height="" />
                                                <%if (sus_mdpbL == 0) {%>
                                                <button type="button" class="btn btn-danger pull-right" onclick="suspender_serv('MDPB_L');"><i class="fa fa-lg fa-spc fa-pause"></i> SUSPENDER SERVIÇO</button>
                                                <%} else {%>
                                                <button type="button" class="btn btn-success pull-right" onclick="suspender_serv('MDPB_L');"><i class="fa fa-lg fa-spc fa-refresh"></i> REATIVAR SERVIÇO</button>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">

                                                <div class="buttons">
                                                    <input type="hidden" name="mdpbl_contador" id="mdpbl_contador" value="<%= listaMdpbL.size()%>" />
                                                    <button type="button" class="btn btn-primary center-block" onclick="addRow('MDPB_L', 'mdpbl_')"><i class="fa fa-lg fa-spc fa-plus"></i> ADICIONAR FAIXA DE CEP</button>
                                                </div>

                                            </li>
                                            <li class="list-group-item">

                                                <table class="table table-striped table-bordered table-hover table-condensed" id="mdpbl_table" name="mdpbl_table">
                                                    <tr>
                                                        <th align="center" colspan="2"><b>CEP Inicial</b></th>
                                                        <th align="center" colspan="2"><b>CEP Final</b></th>
                                                        <th align="center" ><b>REM.</b></th>
                                                        <th align="center" ><b>SUS.</b></th>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < listaMdpbL.size(); i++) {
                                                            ServicoAbrangencia cob = listaMdpbL.get(i);
                                                            String cepInicial = Util.FormataString.formataCep(cob.getCep_inicial() + "");
                                                            String cepFinal = Util.FormataString.formataCep(cob.getCep_final() + "");
                                                            int fs = cob.getFaixa_suspensa();
                                                            String img = "fa fa-lg fa-pause";
                                                            if (fs == 1) {
                                                                img = "fa fa-lg fa-refresh";
                                                            }
                                                            sus_mdpbL = cob.getServico_suspenso();
                                                    %>
                                                    <tr <%if (fs == 1) {%>style="background: khaki;"<%}%> class="faixas">
                                                        <td>De</td>
                                                        <td><input class="form-control" type="input" id="mdpbl_cepIni<%= i + 1%>" name="mdpbl_cepIni<%= i + 1%>" value="<%= cepInicial%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" /></td>
                                                        <td>Até</td>
                                                        <td>                                                            
                                                            <input class="form-control" type="input" id="mdpbl_cepFim<%= i + 1%>" name="mdpbl_cepFim<%= i + 1%>" value="<%= cepFinal%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" />
                                                            <input type="hidden" name="mdpbl_suspenso_<%= i + 1%>" id="mdpbl_suspenso_<%= i + 1%>" value="<%= fs%>" />
                                                        </td>                                                
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-danger" onclick="delRow(this, 'mdpbl_');"><i class="fa fa-lg fa-trash"></i></button>
                                                        </td>
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-info" onclick='suspender("mdpbl_", <%= i + 1%>);'><i id="mdpbl_img_sus_<%= i + 1%>" class="<%= img%>"></i></button>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </table>
                                            </li>        
                                        </ul>
                                    </div>
                                     <div style="display: none;" class="col-xs-12 col-sm-12 col-md-7 col-lg-6" id="mdpbe">
                                        <ul class="list-unstyled">                                                        
                                            <li class="list-group-item list-group-heading">
                                                <label>MDPB Estadual:</label>
                                                <%if (sus_mdpbE == 1) {%>
                                                <span style="font-size: 16px;" class="text-danger"><strong>ESTE SERVIÇO ESTÁ SUSPENSO !!!</strong></span>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">
                                                <img src="../../imagensNew/chancelas/MDPB_E.png" height="" />
                                                <%if (sus_mdpbE == 0) {%>
                                                <button type="button" class="btn btn-danger pull-right" onclick="suspender_serv('MDPB_E');"><i class="fa fa-lg fa-spc fa-pause"></i> SUSPENDER SERVIÇO</button>
                                                <%} else {%>
                                                <button type="button" class="btn btn-success pull-right" onclick="suspender_serv('MDPB_E');"><i class="fa fa-lg fa-spc fa-refresh"></i> REATIVAR SERVIÇO</button>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">

                                                <div class="buttons">
                                                    <input type="hidden" name="mdpbe_contador" id="mdpbe_contador" value="<%= listaMdpbE.size()%>" />
                                                    <button type="button" class="btn btn-primary center-block" onclick="addRow('MDPB_E', 'mdpbe_')"><i class="fa fa-lg fa-spc fa-plus"></i> ADICIONAR FAIXA DE CEP</button>
                                                </div>

                                            </li>
                                            <li class="list-group-item">

                                                <table class="table table-striped table-bordered table-hover table-condensed" id="mdpbe_table" name="mdpbe_table">
                                                    <tr>
                                                        <th align="center" colspan="2"><b>CEP Inicial</b></th>
                                                        <th align="center" colspan="2"><b>CEP Final</b></th>
                                                        <th align="center" ><b>REM.</b></th>
                                                        <th align="center" ><b>SUS.</b></th>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < listaMdpbE.size(); i++) {
                                                            ServicoAbrangencia cob = listaMdpbE.get(i);
                                                            String cepInicial = Util.FormataString.formataCep(cob.getCep_inicial() + "");
                                                            String cepFinal = Util.FormataString.formataCep(cob.getCep_final() + "");
                                                            int fs = cob.getFaixa_suspensa();
                                                            String img = "fa fa-lg fa-pause";
                                                            if (fs == 1) {
                                                                img = "fa fa-lg fa-refresh";
                                                            }
                                                            sus_mdpbE = cob.getServico_suspenso();
                                                    %>
                                                    <tr <%if (fs == 1) {%>style="background: khaki;"<%}%> class="faixas">
                                                        <td>De</td>
                                                        <td><input class="form-control" type="input" id="mdpbe_cepIni<%= i + 1%>" name="mdpbe_cepIni<%= i + 1%>" value="<%= cepInicial%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" /></td>
                                                        <td>Até</td>
                                                        <td>                                                            
                                                            <input class="form-control" type="input" id="mdpbe_cepFim<%= i + 1%>" name="mdpbe_cepFim<%= i + 1%>" value="<%= cepFinal%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" />
                                                            <input type="hidden" name="mdpbe_suspenso_<%= i + 1%>" id="mdpbe_suspenso_<%= i + 1%>" value="<%= fs%>" />
                                                        </td>                                                
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-danger" onclick="delRow(this, 'mdpbe_');"><i class="fa fa-lg fa-trash"></i></button>
                                                        </td>
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-info" onclick='suspender("mdpbe_", <%= i + 1%>);'><i id="mdpbe_img_sus_<%= i + 1%>" class="<%= img%>"></i></button>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </table>
                                            </li>        
                                        </ul>
                                    </div>                   
                                     <div style="display: none;" class="col-xs-12 col-sm-12 col-md-7 col-lg-6" id="mdpbn">
                                        <ul class="list-unstyled">                                                        
                                            <li class="list-group-item list-group-heading">
                                                <label>MDPB NACIONAL:</label>
                                                <%if (sus_mdpbN == 1) {%>
                                                <span style="font-size: 16px;" class="text-danger"><strong>ESTE SERVIÇO ESTÁ SUSPENSO !!!</strong></span>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">
                                                <img src="../../imagensNew/chancelas/MDPB_N.png" height="" />
                                                <%if (sus_mdpbN == 0) {%>
                                                <button type="button" class="btn btn-danger pull-right" onclick="suspender_serv('MDPB_N');"><i class="fa fa-lg fa-spc fa-pause"></i> SUSPENDER SERVIÇO</button>
                                                <%} else {%>
                                                <button type="button" class="btn btn-success pull-right" onclick="suspender_serv('MDPB_N');"><i class="fa fa-lg fa-spc fa-refresh"></i> REATIVAR SERVIÇO</button>
                                                <%}%>
                                            </li>
                                            <li class="list-group-item">

                                                <div class="buttons">
                                                    <input type="hidden" name="mdpbn_contador" id="mdpbn_contador" value="<%= listaMdpbN.size()%>" />
                                                    <button type="button" class="btn btn-primary center-block" onclick="addRow('MDPB_N', 'mdpbn_')"><i class="fa fa-lg fa-spc fa-plus"></i> ADICIONAR FAIXA DE CEP</button>
                                                </div>

                                            </li>
                                            <li class="list-group-item">

                                                <table class="table table-striped table-bordered table-hover table-condensed" id="mdpbn_table" name="mdpbn_table">
                                                    <tr>
                                                        <th align="center" colspan="2"><b>CEP Inicial</b></th>
                                                        <th align="center" colspan="2"><b>CEP Final</b></th>
                                                        <th align="center" ><b>REM.</b></th>
                                                        <th align="center" ><b>SUS.</b></th>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < listaMdpbN.size(); i++) {
                                                            ServicoAbrangencia cob = listaMdpbN.get(i);
                                                            String cepInicial = Util.FormataString.formataCep(cob.getCep_inicial() + "");
                                                            String cepFinal = Util.FormataString.formataCep(cob.getCep_final() + "");
                                                            int fs = cob.getFaixa_suspensa();
                                                            String img = "fa fa-lg fa-pause";
                                                            if (fs == 1) {
                                                                img = "fa fa-lg fa-refresh";
                                                            }
                                                            sus_mdpbN = cob.getServico_suspenso();
                                                    %>
                                                    <tr <%if (fs == 1) {%>style="background: khaki;"<%}%> class="faixas">
                                                        <td>De</td>
                                                        <td><input class="form-control" type="input" id="mdpbn_cepIni<%= i + 1%>" name="mdpbn_cepIni<%= i + 1%>" value="<%= cepInicial%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" /></td>
                                                        <td>Até</td>
                                                        <td>                                                            
                                                            <input class="form-control" type="input" id="mdpbn_cepFim<%= i + 1%>" name="mdpbn_cepFim<%= i + 1%>" value="<%= cepFinal%>" size="7" maxlength="9" onkeypress="mascara(this, maskCep)" />
                                                            <input type="hidden" name="mdpbn_suspenso_<%= i + 1%>" id="mdpbl_suspenso_<%= i + 1%>" value="<%= fs%>" />
                                                        </td>                                                
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-danger" onclick="delRow(this, 'mdpbn_');"><i class="fa fa-lg fa-trash"></i></button>
                                                        </td>
                                                        <td align="center">
                                                            <button type="button" class="btn btn-sm btn-info" onclick='suspender("mdpbn_", <%= i + 1%>);'><i id="mdpbn_img_sus_<%= i + 1%>" class="<%= img%>"></i></button>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </table>
                                            </li>        
                                        </ul>
                                    </div>                   
                                                
                                                
                                </form>
                                <form action="../../ServSuspenderServico" method="post" name="formSusServico" id="formSusServico">
                                    <input type="hidden" name="nomeBD" id="nomeBD" value="<%= nomeBD%>" />
                                    <input type="hidden" name="sus_servico" id="sus_servico" value="" />
                                    <input type="hidden" name="sus_esedex" id="sus_esedex" value="<%= sus_esedex%>" />
                                    <input type="hidden" name="sus_sedex10" id="sus_sedex10" value="<%= sus_sedex10%>" />
                                    <input type="hidden" name="sus_sedex12" id="sus_sedex12" value="<%= sus_sedex12%>" />
                                    <input type="hidden" name="sus_sedexhj" id="sus_sedexhj" value="<%= sus_sedexHJ%>" />
                                    <input type="hidden" name="sus_pax" id="sus_pax" value="<%= sus_pax%>" />
                                    <input type="hidden" name="sus_mdpbl" id="sus_mdpbl" value="<%= sus_mdpbL%>" />
                                    <input type="hidden" name="sus_mdpbe" id="sus_mdpbl" value="<%= sus_mdpbE%>" />
                                    <input type="hidden" name="sus_mdpbn" id="sus_mdpbl" value="<%= sus_mdpbN%>" />
                                </form>

                            </div>
                            <!-- /.row -->
                        </div>
                    </div>     
                </div>
            </div>
        </div>
        <!-- /#page-wrapper -->

        <!-- /#wrapper -->
        <script type="text/javascript">

            var ddData = [
                {
                    value: "esedex",
                    selected: false,
                    imageSrc: "../../imagensNew/esedex.png"
                },
                {
                    value: "sedex10",
                    selected: false,
                    imageSrc: "../../imagensNew/sedex10.png"
                },
                {
                    value: "sedex12",
                    selected: false,
                    imageSrc: "../../imagensNew/sedex12.png"
                },
                {
                    value: "pax",
                    selected: false,
                    imageSrc: "../../imagensNew/pax.png"
                },
                {
                    value: "sedexhj",
                    selected: false,
                    imageSrc: "../../imagensNew/sedexhj.png"
                },
                {
                    value: "mdpbl",
                    selected: false,
                    imageSrc: "../../imagensNew/chancelas/MDPB_L.png"
                },
                {
                    value: "mdpbe",
                    selected: false,
                    imageSrc: "../../imagensNew/chancelas/MDPB_E.png"
                },
                {
                    value: "mdpbn",
                    selected: false,
                    imageSrc: "../../imagensNew/chancelas/MDPB_N.png"
                }
            ];

            $('#demoBasic').ddslick({
                data: ddData,
                width: 250,
                selectText: "-- Selecione um Servico --",
                onSelected: function (data) {
                    hideAll();
                    $('#' + ddData[data.selectedIndex].value).show();
                    $('#salvar_abragencia').show();
                }
            });

            function hideAll() {
                $('#esedex').hide();
                $('#sedex10').hide();
                $('#sedex12').hide();
                $('#sedexhj').hide();
                $('#pax').hide();
                $('#mdpbl').hide();
                $('#mdpbe').hide();
                $('#mdpbn').hide();
                $('#salvar_abragencia').hide();
            }

            function validateRow() {

                var e_contador = document.getElementById('e_contador').value;
                //PERCORRE TODOS OS CAMPOS DA TABELA DE PESOS E PRECOS
                for (var i = 1; i <= e_contador; i++) {
                    if (document.getElementById('e_cepIni' + i) !== null) {
                        var e_cepIni = document.getElementById('e_cepIni' + i).value;
                        var e_cepFim = document.getElementById('e_cepFim' + i).value;
                        
                        //VERIFICA SE O CEP INICIAL FOI PREENCHIDO
                        if (e_cepIni === "" || e_cepIni.length < 9) {
                            alert("Preencha corretamente o CEP inicial!");
                            document.getElementById('e_cepIni' + i).focus();
                            return false;
                        }

                        //VERIFICA SE O CEP FINAL FOI PREENCHIDO
                        if (e_cepFim === "" || e_cepFim.length < 9) {
                            alert("Preencha corretamente o CEP final!");
                            document.getElementById('e_cepFim' + i).focus();
                            return false;
                        }

                        //VERIFICA SE O CEP INICIAL É MENOR QUE O FINAL
                        e_cepIni = parseInt(e_cepIni.replace("-", ""), 10);
                        e_cepFim = parseInt(e_cepFim.replace("-", ""), 10);

                        if (e_cepIni >= e_cepFim) {
                            alert("O CEP Inicial deve ser menor que o CEP Final!");
                            document.getElementById('e_cepIni' + i).focus();
                            return false;
                        }
                    }
                }
                
                for (var i = 1; i <= e_contador; i++) {
                    if (document.getElementById('e_cepIni' + i) !== null) {
                        var e_cepIni = parseInt(document.getElementById('e_cepIni' + i).value.replace("-", ""));
                        var e_cepFim = parseInt(document.getElementById('e_cepFim' + i).value.replace("-", ""));
                        for (var j = 1; j <= e_contador; j++) {
                            if (j !== i && document.getElementById('e_cepIni' + j) !== null) {
                                var e_cepIniAux = parseInt(document.getElementById('e_cepIni' + j).value.replace("-", ""));
                                var e_cepFimAux = parseInt(document.getElementById('e_cepFim' + j).value.replace("-", ""));
                                if ((e_cepIni >= e_cepIniAux && e_cepIni <= e_cepFimAux) || (e_cepFim >= e_cepIniAux && e_cepFim <= e_cepFimAux) || (e_cepIni <= e_cepIniAux && e_cepFim >= e_cepFimAux)) {
                                    alert('Alguma faixa de CEP está se repitindo na tabela!\n\nNão é permitido que a amarração repita faixas de CEP!');
                                    document.getElementById('e_cepIni' + j).focus();
                                    return false;
                                }
                            }
                        }
                    }
                }

                var contador = document.getElementById('contador').value;
                
                //PERCORRE TODOS OS CAMPOS DA TABELA DE PESOS E PRECOS
                for (var i = 1; i <= contador; i++) {
                    if (document.getElementById('cepIni' + i) !== null) {
                        var cepIni = document.getElementById('cepIni' + i).value;
                        var cepFim = document.getElementById('cepFim' + i).value;

                        //VERIFICA SE O CEP INICIAL FOI PREENCHIDO
                        if (cepIni === "" || cepIni.length < 9) {
                            alert("Preencha corretamente o CEP inicial!");
                            document.getElementById('cepIni' + i).focus();
                            return false;
                        }

                        //VERIFICA SE O CEP FINAL FOI PREENCHIDO
                        if (cepFim === "" || cepFim.length < 9) {
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
                    if (document.getElementById('cepIni' + i) !== null) {
                        var cepIni = parseInt(document.getElementById('cepIni' + i).value.replace("-", ""));
                        var cepFim = parseInt(document.getElementById('cepFim' + i).value.replace("-", ""));
                        for (var j = 1; j <= contador; j++) {
                            if (j !== i && document.getElementById('cepIni' + j) !== null) {
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

                waitMsg();                
                return true;
            }

            function addRow(servico, pref) {

                var nrLinha = document.getElementById(pref + 'table').rows.length;
                var linha = document.getElementById(pref + 'table').insertRow(nrLinha);
                var cont = document.getElementById(pref + 'contador').value;
                var newCont = parseInt(cont) + 1;

                linha.insertCell(0).appendChild(document.createTextNode("De"));
                linha.insertCell(1).innerHTML = "<input class='form-control' type='input' id='" + pref + "cepIni" + newCont + "' name='" + pref + "cepIni" + newCont + "' size='8' maxlength='9' onKeyPress='mascara(this,maskCep)' />";
                linha.insertCell(2).appendChild(document.createTextNode("Até"));
                linha.insertCell(3).innerHTML = "<input class='form-control' type='input' id='" + pref + "cepFim" + newCont + "' name='" + pref + "cepFim" + newCont + "' size='8' maxlength='9' onKeyPress='mascara(this,maskCep)' />"
                        + "<input type='hidden' id='" + pref + "suspenso_" + newCont + "' name='" + pref + "suspenso_" + newCont + "' value='0' />";
                var cell4 = linha.insertCell(4);
                cell4.innerHTML = "<button type='button' class='btn btn-sm btn-danger' onclick=\"delRow(this,'" + pref + "');\"><i class='fa fa-lg fa-trash'></i></button>";
                cell4.align = "center";
                var cell5 = linha.insertCell(5);

                cell5.innerHTML = "<button type='button' class='btn btn-sm btn-info' onclick=\"suspender('" + pref + "', " + newCont + ");\"><i id='" + pref + "img_sus_" + newCont + "' class='fa fa-lg fa-pause'></i></button>";
                cell5.align = "center";

                document.getElementById(pref + 'contador').value = newCont;
            }

            function delRow(linha, pref) {
                var nrLinha = document.getElementById(pref + 'table').rows.length;
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
                                var tabela = document.getElementById(pref + 'table');
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
                    return false;
                }
            }

            function suspender(pref, id) {
                var sus = document.getElementById(pref + 'suspenso_' + id).value;
                if (sus === '0') {
                    //document.getElementById('e_img_sus_' + id).src = '../../imagensNew/refresh.png';
                    document.getElementById(pref + 'img_sus_' + id).className = 'fa fa-lg fa-refresh';
                    document.getElementById(pref + 'suspenso_' + id).value = '1';
                } else {
                    //document.getElementById('e_img_sus_' + id).src = '../../imagensNew/pause.png';
                    document.getElementById(pref + 'img_sus_' + id).className = 'fa fa-lg fa-pause';
                    document.getElementById(pref + 'suspenso_' + id).value = '0';
                }
            }

            function suspender_serv(serv) {
                document.getElementById('sus_servico').value = serv;
                document.getElementById('formSusServico').submit();
            }
        </script>
    </body>
</html>
<%}%>

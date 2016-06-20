<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, javax.swing.*, java.util.*, java.text.SimpleDateFormat, java.text.DecimalFormat, java.util.Date" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">

<%
    DecimalFormat df = new DecimalFormat("#,##0.00");
    DecimalFormat df2 = new DecimalFormat("#,##0");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    response.setContentType("text/html");
    response.setHeader("Cache-Control", "no-cache");

    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD == null) {
        response.sendRedirect("../../index.jsp?msg=Sua sessao expirou! Para voltar ao Portal faça seu login novamente!");
    } else {

        int idEmpresa = (Integer) session.getAttribute("idEmpresa");
        int idCliente = (Integer) session.getAttribute("idCliente");
        String idMov = request.getParameter("idmov");

//DADOS DA EMPRESA(ACF) PARA O CABEÇALHO DO TICKET
        Entidade.empresas emp = Controle.contrEmpresa.consultaEmpresa(idEmpresa);
        String nomeEmp = emp.getEmpresa();
        String cnpjEmp = emp.getCpf_cnpj();
        String fantasiaEmp = emp.getFantasia();
        String enderecoEmp = emp.getEndereco();
        String cepEmp = emp.getCep();
        String cidadeEmp = emp.getCidade();
        String estadoEmp = emp.getUf();

//DADOS DO CLIENTE DA EMPRESA(ACF)
        Entidade.Clientes cli = Controle.contrCliente.consultaClienteById(idCliente, nomeBD);
        String nomeCli = cli.getNome();
        String cnpjCli = cli.getCnpj();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Portal Postal | Ticket</title>
    </head>

    <body>               
        <%
            Entidade.Movimentacao objeto = Controle.contrMovimentacao.getMovimentacaoById(idMov, nomeBD);
                        //arraylist de todas as vendos do numCaixa e numVenda passados

            //verifica se o arraylist nao é vazio nem nulo
            if (objeto != null) {

                            //pega a primeira venda
                String contrato = objeto.getContratoEct();
                Date dataPostagem = objeto.getDataPostagem();

                //inicia a variavedo do total da venda
                float total = 0;
        %>
        <p style="font-family: Verdana; font-style: italic; font-size: 10px; color: red;">*Relatório para simples conferência.</p>
        <p style="font-family: Verdana; font-style: italic; font-size: 10px; color: red;">*Não tem realação com comprovante original.</p>
        <table width="282" cellspacing='0' cellpadding='0'>
            <tr>
                <td><img src='../../imagensNew/ticket_topo.jpg'></td>
            </tr>
            <tr>
                <td style='padding:0 15px 0 15px;font-family:courier;font-size:9px;color:#8a8a8a; letter-spacing:0; border-left: 1px solid #EEEFFF;  border-right:  1px solid #EEEFFF;'>
                    <table width='100%' cellspacing='0' border='0' style='font-family:courier;font-size:9px;color:#8a8a8a; letter-spacing:0;  '>
                        <tr>
                            <td colspan='4'>
                                Não tem relação com o ticket do <br/>
                                atendimento original<br/><br/>
                                <%= nomeEmp%><br>
                                CNPJ <%= cnpjEmp%><br>
                                <%= fantasiaEmp%><br>
                                <%= enderecoEmp%> CEP <%= cepEmp%><br>
                                <%= cidadeEmp%> - <%= estadoEmp%>
                                <br>
                            </td>
                        </tr>
                        <tr>
                            <td colspan='4'>---------------------------------------------</td>
                        </tr>
                        <tr>
                            <%if (contrato.length() == 10) {%>
                            <td colspan='3' align='center'>SERVIÇO A FATURAR</td>
                            <%} else {%>
                            <td colspan='3' align='center'>SERVIÇO A VISTA</td>
                            <%}%>
                            <td align='right'><%= objeto.getCodigoEct()%></td>
                        </tr>
                        <%--
                        <tr>
                            <td>Subcaixa</td>
                            <td colspan='3'><%= numCaixa%></td>
                        </tr>
                        --%>
                        <tr>
                            <td>Cx|Venda</td>
                            <td align="center" ><%= objeto.getNumCaixa()%> | <%= objeto.getNumVenda()%></td>
                            <td colspan='2' align='right'><%= sdf.format(dataPostagem)%></td>
                        </tr>
                        <tr>
                            <td>Cliente</td>
                            <td colspan='3'><%= nomeCli%></td>
                        </tr>
                        <tr>
                            <td align='center'><%= idCliente%></td>
                            <td colspan='3'>CNPJ <%= cnpjCli%></td>
                        </tr>
                        <%if (contrato.length() == 10) {%>
                        <tr>
                            <td></td>
                            <td>Contrato <%= contrato%></td>
                            <td colspan='2' align='right'></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td></td>
                            <td colspan='2' align='right'>A FATURAR</td>
                        </tr>
                        <%}%>
                        <tr>
                            <td>Servico</td>
                            <td colspan='3' align='right'>Valor</td>
                        <tr>
                            <%
                            //faz o FOR de todos os objetos da venda

                                String numObjeto = objeto.getNumObjeto();
                                String descServico = objeto.getDescServico();
                                String destinatario = objeto.getDestinatario();
                                String cep = objeto.getCep();
                                String paisDestino = objeto.getPaisDestino();
                                String servicosAdicionais = objeto.getSiglaServAdicionais();
                                float peso = objeto.getPeso();
                                float altura = objeto.getAltura();
                                float largura = objeto.getLargura();
                                float comprimento = objeto.getComprimento();
                                float subTotal = objeto.getValorServico();
                                float valorDestino = objeto.getValorDestino();
                                float valorDeclarado = objeto.getValorDeclarado();

                                //soma o subTotal ao total da venda
                                total += subTotal;

                                //verifica os servicos adicionais da venda
                                ArrayList listaDeServicos = Util.ServicosAdicionais.listaDeServicos(servicosAdicionais.replace("VDVD", "VD"), paisDestino, dataPostagem, nomeBD, valorDestino, valorDeclarado);

                                //for para calcular o valor somente do Serviço diminuindo os serviços adicionais do total
                                float valorServico = objeto.getValorServico();

                                if (listaDeServicos != null && listaDeServicos.size() > 0) {
                                    for (int j = 0; j < listaDeServicos.size(); j++) {
                                        Entidade.PrecoServAdicional psa = (Entidade.PrecoServAdicional) listaDeServicos.get(j);
                                        if (psa != null) {
                                            valorServico -= psa.getValor();
                                        }
                                    }
                                }

                            %>
                        <tr>
                            <td colspan='4'>---------------------------------------------</td>
                        </tr>
                        <tr>
                            <td colspan='3'><%= numObjeto%>&nbsp;&nbsp;<%= descServico%></td>
                            <td align='right'><%= df.format(valorServico)%></td>
                        </tr>
                        <tr>
                            <td width='15%' align='right'>Dest:</td>
                            <td width='45%'>CEP <%= cep%></td>
                            <td width='22%' align='right'><%= df2.format(peso)%> g</td>
                            <td width='18%'></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan='3'>Dimensoes (cm): <%= df.format(altura)%> x <%= df.format(largura)%> x <%= df.format(comprimento)%></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td colspan='3'><%= destinatario%></td>
                        </tr>
                        <tr>
                            <td colspan='4' align='center'>------------------------------------</td>
                        </tr>
                        <%
                            if (listaDeServicos != null && listaDeServicos.size() > 0) {
                                //for para imprimir os servicos adicionais na tela
                                for (int j = 0; j < listaDeServicos.size(); j++) {
                                    Entidade.PrecoServAdicional psa = (Entidade.PrecoServAdicional) listaDeServicos.get(j);
                                    String siglaServAdicional = "";
                                    String descServAdicional = "";
                                    float valorServAdicional = 0;
                                    String qtdServAdicional = "1";
                                    if (psa != null) {
                                        siglaServAdicional = psa.getSigla();
                                        descServAdicional = psa.getDesc();
                                        valorServAdicional = psa.getValor();
                                        if (siglaServAdicional.equals("VD")) {
                                            qtdServAdicional = String.valueOf(df.format(valorDeclarado));
                                        }
                                        if (siglaServAdicional.equals("VP")) {
                                            qtdServAdicional = String.valueOf(df.format(valorDestino));
                                        }

                        %>
                        <tr>
                            <td colspan='2'><%= descServAdicional%></td>
                            <td align='right'><%= qtdServAdicional%></td>
                            <td align='right'><%= df.format(valorServAdicional)%></td>
                        </tr>
                        <%}
                            }
                        } else {%>
                        <tr>
                            <td colspan='4' align='center'>*** VALOR DECLARADO NAO SOLICITADO ***</td>
                        </tr>
                        <%}%>
                        <tr>
                            <td colspan='3' align='right'>Sub-total</td>
                            <td align='right'><%= df.format(subTotal)%></td>
                        </tr>

                        <tr>
                            <td colspan='4'>---------------------------------------------</td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>TOTAL</td>
                            <td align='right'></td>
                            <td align='right'><%= df.format(total)%></td>
                        <tr>
                            <td colspan='4'>---------------------------------------------</td>
                        </tr>
                        <tr>
                            <td colspan='4'>
                                ATENÇÃO: ESTE CUPOM NÃO INDICA O HORÁRIO <br>
                                DA VENDA.<BR>
                                POSTAGENS APÓS O HORÁRIO LIMITE DE POSTAGEM, <BR>
                                FICAM COM DH, PARA O PRÓXIMO DIA! <BR>
                                CONSULTE SUA AGÊNCIA CASO NECESSITE <BR>
                                DO CUPOM ORIGINAL, ONDE ESTARÁ INFORMADO <BR>
                                O HORÁRIO DA POSTAGEM, O ATENDIMENTO, <BR>
                                E TERÁ OU NÃO A MENÇÃO DO DH.
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td><img src='../../imagensNew/ticket_footer.jpg'></td>
            </tr>
        </table>
        <p style="font-family: Verdana; font-style: italic; font-size: 10px; color: red;">*Relatório para simples conferência.</p>
        <%}%>
    </body>
</html>
<%}%>
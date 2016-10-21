<%@page import="Entidade.empresas"%>
<%@page import="Entidade.ClientesUsuario"%>
<%@page import="Util.FormataString"%><%@ page import="java.sql.*, javax.swing.*, java.util.*, java.text.SimpleDateFormat, java.text.DecimalFormat, java.util.Date" %><%
    response.setContentType("application/xls");
    response.setHeader("Content-disposition", "attachment; filename=relatorio_analitico.csv");
    response.setHeader("Cache-Control", "no-cache");

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    if (session.getAttribute("usuario_sessao_cliente") != null) {

        ClientesUsuario us = (ClientesUsuario) session.getAttribute("usuario_sessao_cliente");
        empresas emp = (empresas) session.getAttribute("agencia");

        String sql = request.getParameter("sql");
        ArrayList movimentacao = Controle.contrMovimentacao.getConsultaAnalitica(sql, emp.getCnpj());

        if (movimentacao.size() >= 1) {
            if (us.getAcessos().contains(3)) {
                out.println("OBJETO;SERVIÇO;PESO;QTD;POSTAGEM;VALOR;DECLARADO;A COBRAR;DESTINATÁRIO;CEP;SITUAÇÃO;DATA SIT.;NF;DEPARTAMENTO;ADICIONAIS;CONTEÚDO;CONTRATO ECT;DESTINO");
            } else {
                out.println("OBJETO;SERVIÇO;PESO;QTD;POSTAGEM;DESTINATÁRIO;CEP;SITUAÇÃO;DATA SIT.;NF;DEPARTAMENTO;ADICIONAIS;CONTEÚDO;CONTRATO ECT;DESTINO");
            }

            for (int i = 0; i < movimentacao.size(); i++) {
                Entidade.Movimentacao mov = (Entidade.Movimentacao) movimentacao.get(i);
                String servico2 = mov.getDescServico();
                int peso = (int) mov.getPeso();
                int qtd = (int) mov.getQuantidade();
                float valor = mov.getValorServico();
                String vValor = Util.FormatarDecimal.formatarFloat(valor);
                float valorDec = mov.getValorDeclarado();
                String vValorDec = Util.FormatarDecimal.formatarFloat(valorDec);
                float valorCob = mov.getValorDestino();
                String vValorCob = Util.FormatarDecimal.formatarFloat(valorCob);

                Date data = mov.getDataPostagem();
                String vData = sdf.format(data);

                String notaFiscal = mov.getNotaFiscal();
                String numeroRegistro = mov.getNumObjeto();
                String destinatario = mov.getDestinatario();
                String cepDestino = FormataString.formataCep(mov.getCep());
                String departamento2 = mov.getDepartamento();
                String status = mov.getStatus();

                Date dataSit = mov.getDataEntrega();
                String dtSit = sdf.format(mov.getDataPostagem());
                if (dataSit != null) {
                    dtSit = sdf.format(dataSit);
                }

                if (us.getAcessos().contains(3)) {
                    out.println(numeroRegistro + ";" + servico2 + ";" + peso + ";" + qtd + ";" + vData + ";" + vValor + ";" + vValorDec + ";" + vValorCob + ";" + destinatario + ";" + cepDestino + ";" + status + ";" + dtSit + ";" + notaFiscal + ";" + departamento2 + ";" + mov.getSiglaServAdicionais() + ";" + mov.getConteudoObjeto() + ";" + mov.getContratoEct() + ";" + mov.getPaisDestino());
                } else {
                    out.println(numeroRegistro + ";" + servico2 + ";" + peso + ";" + qtd + ";" + vData + ";" + destinatario + ";" + cepDestino + ";" + status + ";" + dtSit + ";" + notaFiscal + ";" + departamento2 + ";" + mov.getSiglaServAdicionais() + ";" + mov.getConteudoObjeto() + ";" + mov.getContratoEct() + ";" + mov.getPaisDestino());
                }
            }
        } else {
            out.println("Nenhum Objeto Encontrado!");
        }
    } else {
        out.println("Sessão expirada! Faça o login novamente!");
    }
%>
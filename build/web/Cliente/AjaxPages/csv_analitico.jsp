<%@page import="Controle.ContrReclamacao"%><%@page import="Entidade.empresas"%><%@page import="Entidade.ClientesUsuario"%><%@page import="Util.FormataString"%><%@ page import="java.sql.*, javax.swing.*, java.util.*, java.text.SimpleDateFormat, java.text.DecimalFormat, java.util.Date" %><%
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
            
            String cabecalho = "";
            if (us.getAcessos().contains(3)) {
                cabecalho += "OBJETO;SERVIÇO;PESO;QTD;POSTAGEM;VALOR;DECLARADO;A COBRAR;DESTINATÁRIO;CEP;SITUAÇÃO;DATA SIT.;NF;DEPARTAMENTO;ADICIONAIS;CONTEÚDO;CONTRATO ECT;DESTINO;OBS";
            } else {
                cabecalho += "OBJETO;SERVIÇO;PESO;QTD;POSTAGEM;DESTINATÁRIO;CEP;SITUAÇÃO;DATA SIT.;NF;DEPARTAMENTO;ADICIONAIS;CONTEÚDO;CONTRATO ECT;DESTINO;OBS";
            }
            if(us.getAcessos().contains(8)){
                 cabecalho += ";PREV ENTREGA";
            }
            out.println(cabecalho);

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
                String obs = mov.getObs();

                
                    
                /*    
                    String pz_cumprido = "---";
                    String atrasado = "";
                    
                    if(acessosUs.contains(8)){
                        if (mov.getPrazo_estimado() != null && mov.getPrazo_cumprido_date() != null) {
                            pz_estimado = sdf.format(mov.getPrazo_estimado());
                            pz_cumprido = sdf.format(mov.getPrazo_cumprido_date());
                            if (mov.getPrazo_estimado().before(mov.getPrazo_cumprido_date())) {
                                atrasado = "color:red;font-weight:bold;";
                            }
                        } else if (mov.getPrazo_estimado() != null) {
                            pz_estimado = sdf.format(mov.getPrazo_estimado());
                            Date date = new Date();
                            try {
                                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                date = (java.util.Date) formatter.parse(formatter.format(date));
                            } catch (ParseException e) {
                                //System.out.println(e.getMessage());
                            }
                            if (mov.getPrazo_estimado().before(date)) {
                                atrasado = "color:red;font-weight:bold;";
                            }
                        }
                    }
                 */
                
                Date dataSit = mov.getLast_status_date();
                String dtSit = sdf.format(mov.getDataPostagem());
                if (dataSit != null) {
                    dtSit = sdf.format(dataSit);
                }
                String status = mov.getLast_status_name();

                String linha = "";
                if (us.getAcessos().contains(3)) {
                    linha += numeroRegistro + ";" + servico2 + ";" + peso + ";" + qtd + ";" + vData + ";" + vValor + ";" + vValorDec + ";" + vValorCob + ";" + destinatario + ";" + cepDestino + ";" + status + ";" + dtSit + ";" + notaFiscal + ";" + departamento2 + ";" + mov.getSiglaServAdicionais() + ";" + mov.getConteudoObjeto() + ";" + mov.getContratoEct() + ";" + mov.getPaisDestino()+";"+obs;                    
                } else {
                    linha += numeroRegistro + ";" + servico2 + ";" + peso + ";" + qtd + ";" + vData + ";" + destinatario + ";" + cepDestino + ";" + status + ";" + dtSit + ";" + notaFiscal + ";" + departamento2 + ";" + mov.getSiglaServAdicionais() + ";" + mov.getConteudoObjeto() + ";" + mov.getContratoEct() + ";" + mov.getPaisDestino()+";"+obs;
                }
                if(us.getAcessos().contains(8)){
                    String pz_estimado = "---";
                    if (mov.getPrazo_estimado() != null) {
                        ContrReclamacao cr = new ContrReclamacao();
                        Calendar novadataPrevisaoEntrega = cr.recalculaDataEstimada(cr.dateToCalendar(mov.getDataPostagem()), cr.dateToCalendar(mov.getPrazo_estimado()), "");
                        if(novadataPrevisaoEntrega != null){
                            pz_estimado = sdf.format(novadataPrevisaoEntrega.getTime());                    
                        }
                    }
                    linha += ";"+pz_estimado; 
                }
                
                out.println(linha);
            }
        } else {
            out.println("Nenhum Objeto Encontrado!");
        }
    } else {
        out.println("Sessão expirada! Faça o login novamente!");
    }
%>
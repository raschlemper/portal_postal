<%@page import="Entidade.Endereco"%><%@page import="Util.FormataString"%><%@ page import="java.sql.*, javax.swing.*, java.util.*, java.text.SimpleDateFormat, java.text.DecimalFormat, java.util.Date" %><%
    response.setContentType("application/xls");
    response.setHeader("Content-disposition", "attachment; filename=relatorio.txt");
    response.setHeader("Cache-Control", "no-cache");

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    int nivel = Integer.parseInt(request.getParameter("nivel"));
    String nomeBD = request.getParameter("nomeBD");
    String sql = request.getParameter("sql");
     ArrayList movimentacao = Controle.contrMovimentacao.getConsultaAnalitica(sql, nomeBD);

    if (movimentacao.size() >= 1) {
       /* if (nivel != 3) {
            out.println("OBJETO;SERVIÇO;PESO;QTD;POSTAGEM;VALOR;DESTINATÁRIO;CEP;SITUAÇÃO;NF;DEPARTAMENTO");
        } else {
            out.println("OBJETO;SERVIÇO;PESO;QTD;POSTAGEM;DESTINATÁRIO;CEP;SITUAÇÃO;NF;DEPARTAMENTO");
        }*/
        out.println("datax;descri;destinatario;cep;uf;peso;cubico;qtde;registro;adicionais;obs;contalote;nf;declarado;unitario;valor");

        for (int i = 0; i < movimentacao.size(); i++) {
            Entidade.Movimentacao mov = (Entidade.Movimentacao) movimentacao.get(i);
            String servico2 = mov.getDescServico();
            int peso = (int) mov.getPeso();
            int qtd = (int) mov.getQuantidade();

            float valor = mov.getValorServico();
            String vValor = Util.FormatarDecimal.formatarFloatComPonto(valor);
            
            float vd = mov.getValorDeclarado();
            String vVd = "0.00";
            if(vd != 0){
                 vVd = Util.FormatarDecimal.formatarFloatComPonto(vd);
            }
          

            Date data = mov.getDataPostagem();
            String vData = sdf.format(data);

            String notaFiscal = mov.getNotaFiscal();
            String numeroRegistro = mov.getNumObjeto();
            String destinatario = mov.getDestinatario();
            
            String cepDestino = FormataString.formataCep(mov.getCep());
            Endereco en  =  Controle.ContrCep.pesquisaCep(mov.getCep());
            
            String departamento2 = mov.getDepartamento();
            String status = mov.getStatus();
            String adc = mov.getSiglaServAdicionais();
            String obs = mov.getConteudoObjeto();
            /*if (nivel != 3) {
                out.println(numeroRegistro + ";" + servico2 + ";" + peso + ";" + qtd + ";" + vData + ";" + vValor + ";" + destinatario + ";" + cepDestino + ";" + status + ";" + notaFiscal + ";" + departamento2);
            }else{
                out.println(numeroRegistro + ";" + servico2 + ";" + peso + ";" + qtd + ";" + vData + ";" + destinatario + ";" + cepDestino + ";" + status + ";" + notaFiscal + ";" + departamento2);
            }*/
            out.println(vData+ ";" + servico2+ ";" + destinatario + ";" +cepDestino + ";" +en.getUf()+" ;" + peso + "; ;" +qtd+ ";" +numeroRegistro+ ";" +adc+ ";" +obs+"; ;"+notaFiscal+ ";" +vVd+";0.00;"+vValor);
        }
    } else {
        out.println("Nenhum Objeto Encontrado!");
    }
%>
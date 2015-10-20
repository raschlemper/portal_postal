<%@page import="java.util.Date"%><%@page import="java.text.SimpleDateFormat"%><%@page import="java.util.ArrayList"%><%
    response.setContentType("application/xls");
    response.setHeader("Content-disposition", "attachment; filename=relatorio_ar.csv");
    response.setHeader("Cache-Control", "no-cache");

    int idCliente = Integer.parseInt(request.getParameter("idCliente"));
    String nomeBD = request.getParameter("nomeBD");
    String dataInicio = request.getParameter("inicio");
    String dataFinal = request.getParameter("final");
    String departamento = request.getParameter("departamento");

    int ar = Integer.parseInt(request.getParameter("ar"));

    ArrayList movimentacao = Controle.contrMovimentacao.getMovimentacaoAR(dataInicio, dataFinal, idCliente, ar, departamento, nomeBD);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    out.println("DATA DA POSTAGEM;OBJETO;STATUS;CEP;DESTINATÁRIO;NOME DO RECEBEDOR;DATA RECEBIMENTO");

    for (int i = 0; i < movimentacao.size(); i++) {
        Entidade.Movimentacao mov = (Entidade.Movimentacao) movimentacao.get(i);

        Date data = mov.getDataPostagem();
        String vData = sdf.format(data);

        String numeroRegistro = mov.getNumObjeto();
        String destinatario = mov.getDestinatario();
        String cep = mov.getCep();
        String status = mov.getStatus();
        String nomeRecebAr = mov.getNomeRecebAr();
        String dataRecebAr = mov.getDataBaixaAr();

        out.println(vData + ";" + numeroRegistro + ";" + status + ";" + cep + ";" + destinatario + ";" + nomeRecebAr + ";" + dataRecebAr);
    }
%>
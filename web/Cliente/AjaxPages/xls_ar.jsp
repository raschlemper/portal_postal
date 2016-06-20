<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>
<%
    response.setContentType("application/xls");
    response.setHeader("Content-disposition", "attachment; filename=relatorio_ar.xls");
    response.setHeader("Cache-Control", "no-cache");

    int idCliente = Integer.parseInt(request.getParameter("idCliente"));
    String nomeBD = request.getParameter("nomeBD");
    String dataInicio = request.getParameter("inicio");
    String dataFinal = request.getParameter("final");
    String departamento = request.getParameter("departamento");
    
    int ar = Integer.parseInt(request.getParameter("ar"));

    ArrayList movimentacao = Controle.contrMovimentacao.getMovimentacaoAR(dataInicio, dataFinal, idCliente, ar, departamento, nomeBD);
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
%>
<table width='120%' align='center' cellspacing='1'> 
    <thead>
        <tr style='background:#fcfaaa;'>
            <th align='left'>DATA DA POSTAGEM</th>
            <th align='left'>OBJETO</th>
            <th align='left'>STATUS</th>
            <th align='left'>CEP</th>
            <th align='left'>DESTINATÁRIO</th>
            <th align='left'>NOME DO RECEBEDOR</th>
            <th align='left'>DATA RECEBIMENTO</th>
        </tr>
    </thead>
    <tbody>
        <%
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
        %>
        <tr align='center'>
            <td nowrap align='left'><%= vData%></td>
            <td nowrap align='left'><%= numeroRegistro%></td>
            <td nowrap align='left'><%= status%></td>
            <td nowrap align='left'><%= cep%></td>
            <td nowrap align='left'><%= destinatario%></td>
            <td nowrap align='left'><%= nomeRecebAr%></td>
            <td nowrap align='left'><%= dataRecebAr%></td>
        </tr>
        <% }%>
    </tbody>
</table>
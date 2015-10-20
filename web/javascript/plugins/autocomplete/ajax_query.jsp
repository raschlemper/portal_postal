<%@page import="Entidade.Clientes"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%

            String nomeBD = (String) session.getAttribute("empresa");
    String pesq = request.getParameter("query");


    if (pesq != null && !pesq.equals("") && nomeBD != null) {
        ArrayList<Clientes> lista = Controle.contrCliente.consultaClienteByRazaoSocial(pesq, nomeBD);
        if (lista != null && lista.size() > 0) {
            out.print("<ul>\n");
            for (int idx = 0; idx < lista.size(); idx++) {
                Clientes cli = lista.get(idx);
                String nome = cli.getNome().replaceAll("(?i)"+pesq, "<span style='font-weight:bold;'>" + pesq + "</span>").toUpperCase();
                out.println("\t<li id='autocomplete_" + cli.getCodigo() + "' rel='" + cli.getCodigo() + "_" + cli.getNome() + "_" + cli.getEndereco() + "_" + cli.getCep() + "_" + cli.getBairro() + "_" + cli.getComplemento() + "_" + cli.getCidade() + "_" + cli.getUf() + "_" + cli.getEmail() + "_" + cli.getTelefone() + "_" + cli.getCnpj() + "'>" + nome + " - Cod. "+cli.getCodigo()+"</li>\n");
            }
            out.print("</ul>");
        }
    }

%>
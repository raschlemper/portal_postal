

<%@page import="Entidade.Clientes"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.*;" %>
<%
            String resultado = "sessaoexpirada";
            String nomeBD = (String) session.getAttribute("empresa");

            if (nomeBD != null) {
                resultado = "";
                int id = Integer.parseInt(request.getParameter("idCli"));
                Clientes cli = Controle.contrCliente.consultaClienteById(id, nomeBD);
                String rua = cli.getEndereco();
                String compl = cli.getComplemento();
                String bairro = cli.getBairro();
                String cid = cli.getCidade();
                String tel = cli.getTelefone();

                resultado += rua + " - " + compl + "<br/>" + bairro + " - " + cid + "<br/>" + tel ;
                resultado += ";" + request.getParameter("idColeta");

            }
            response.getWriter().write(resultado);


%>

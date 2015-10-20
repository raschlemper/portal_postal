<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, javax.swing.*, java.util.*" %>
<%
        /*AJAX*/
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        String login = request.getParameter("login").toString();
        String resultado = "<b style='color:red' id='x'>Login Inválido!</b>";

        Connection conn = Util.Conexao.conectarGeral();
        String sql = "SELECT login FROM logincoletador WHERE login = '" + login + "';";

        PreparedStatement valores = conn.prepareStatement(sql);
        ResultSet result = (ResultSet) valores.executeQuery();
        if (login != null && login.length() > 0 && result.next()) {
            String login2 = result.getString("login");
            resultado = "<b style='color:red' id='x'>Login Existente!</b>";
        } else if (login != null && login.length() > 3) {
            resultado = "<b style='color:green' id='w'>Login Válido!</b>";
        }

        response.getWriter().write(resultado);
%>
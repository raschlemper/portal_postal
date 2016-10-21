<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
            /*AJAX*/
            request.setCharacterEncoding("ISO-8859-1");
            response.setCharacterEncoding("ISO-8859-1");
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            String resultado = "sessaoexpirada";

            String nomeBD = (String) session.getAttribute("empresa");
            if (nomeBD != null) {

                resultado = "0";
                if (nomeBD != null) {
                    String login = request.getParameter("login");
                    String senha = request.getParameter("senha");
                    int nivel = Integer.parseInt(request.getParameter("nivel"));
                    if (!login.equals("") && !senha.equals("")) {
                        if (Controle.contrLogin.verificaLoginSenha(login, senha, nivel, nomeBD)) {
                            resultado = "1";
                        }
                    }
                }

            }

            response.getWriter().write(resultado);
%>
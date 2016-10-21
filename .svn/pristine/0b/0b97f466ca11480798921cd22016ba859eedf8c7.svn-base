<%@page import="Controle.ContrBoxCubo"%>
<%@page import="Util.FormataString"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%  Date dtHoje = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String token = request.getParameter("token");
    String tkSys = FormataString.MD5(sdf.format(dtHoje)).trim();
    String retorno = "1";

    if (token != null && !token.isEmpty()&& token.equals(tkSys)) {
        String mac = request.getParameter("mac");
        String dtErro = request.getParameter("dterro");
        String erro = request.getParameter("erro");
        // pegar ip do clint

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        //salvar log em boxcubo.log_erro (String mac, String datErro, String erro, String ip)
        retorno = ContrBoxCubo.inserirLog(mac, dtErro, erro, ip);

          }


%><%=retorno%>
<%
    String resultado = "Codigo cadastrado!";
    String nomeBD = (String) session.getAttribute("nomeBD");
    if (nomeBD != null) {
        String codigo_edi = request.getParameter("codigo_edi");
        String codigo_sro = request.getParameter("codigo_sro");
        String cli = request.getParameter("idCli");
        String edi_p = request.getParameter("edi_p");
        if(!edi_p.equals("e")){
            Controle.ContrlEDI.delete_sro_edi(Integer.parseInt(cli), Integer.parseInt(edi_p), Integer.parseInt(codigo_sro), nomeBD);
        }
        
        Controle.ContrlEDI.insert_sro_edi(Integer.parseInt(cli), Integer.parseInt(codigo_edi), Integer.parseInt(codigo_sro), nomeBD);
    }
%><%=resultado%>

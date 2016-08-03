
<%
         
            String resultado =" Codigo EDI deletado!";
            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD != null) { 
               if(Boolean.valueOf(request.getParameter("deletar"))){
                   String cod = request.getParameter("codigo");
                   String cli = request.getParameter("idCli");     
                   Controle.ContrlEDI.deletar(Integer.parseInt(cli), Integer.parseInt(cod),nomeBD);
               }else{
               String codigo = request.getParameter("codigo");
               String descricao = request.getParameter("descricao");  
               String cli = request.getParameter("idCli");  
               Controle.ContrlEDI.inserir(Integer.parseInt(cli), Integer.parseInt(codigo), descricao, nomeBD);
               resultado = codigo +" - "+ descricao +" : salvo!"; 
               }
               
            }
%><%=resultado%>

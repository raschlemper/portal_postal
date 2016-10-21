<%@page import="Controle.ContrServicoAbrangencia"%>
<%    
    response.setHeader("Cache-Control", "no-cache");
    
    String resultado = "OK";    
    String troca_servico = request.getParameter("troca_servico");
    String servico = request.getParameter("servico");
    String servico_novo = servico;
    String cep = request.getParameter("cep");
    System.out.println(servico +" > "+cep);
    if(cep == null || cep.trim().equals("")){
        cep = "0";
        resultado = "CEP inválido!";
    }else{
        Integer[] CEPS_SUSPENSOS = {-1};
        if(servico.equals("SEDEX10") || servico.equals("SEDEX12") || servico.equals("SEDEXHJ") || servico.equals("ESEDEX") || servico.equals("PAX")){
            String nomeBD = (String) session.getAttribute("nomeBD");
            if (nomeBD != null) {            
                int cepa = Integer.parseInt(cep.replace("-", "").replace(".", "")); 
                if(!ContrServicoAbrangencia.verificaByCepServico(cepa, servico, nomeBD)){
                    resultado = "O "+servico+" não abrange o CEP!";   
                    if(troca_servico.equals("SIM")){
                        resultado = "TROCA";
                        if(servico.equals("PAX")){
                            servico_novo = "PAC";
                        } else {
                            servico_novo = "SEDEX";
                        }
                    }
                } else { 
                    for (int cs : CEPS_SUSPENSOS) {
                        if (cs == cepa) {
                            resultado = "A entrega esta suspensa para este CEP!";
                        }
                    }               
                }               
            }else{
                resultado = "A sessao expirou, refaça o login para gerar a etiqueta!";
            }
        }
    }
    
    response.getWriter().write("{\"resultado\":\"" + resultado + "\",\"servico_novo\":\""+servico_novo+"\",\"servico_antigo\":\""+servico+"\"}");
%>
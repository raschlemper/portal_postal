<%@page import="Coleta.Controle.contrColeta"%>
<%@page import="Coleta.Controle.contrColetador"%>
<%@page import="Controle.ContrStatusEntrega"%>
<%@page import="Controle.ContrLogColeta"%>
<%@page import="Controle.contrEmpresa"%>
<%
    try {
        int idColeta = Integer.parseInt(request.getParameter("idColeta"));
        int idColetador = Integer.parseInt(request.getParameter("idColetador"));
        int idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));
        int idStatus = Integer.parseInt(request.getParameter("idStatus"));

        double lat = Double.parseDouble(request.getParameter("latitude"));
        double lng = Double.parseDouble(request.getParameter("longitude"));

        String nomeBD = contrEmpresa.cnpjEmpresa(idEmpresa);
        String status = ContrStatusEntrega.consultaNomeStatus(idStatus, nomeBD);
        String nomeCol = contrColetador.consultaNomeColetadoresById(idColetador, nomeBD);

        ContrLogColeta.inserirGPS(idColeta, idColetador, nomeCol, status, lat, lng, nomeBD);

        contrColeta.alterarStatus(idColeta, idStatus, nomeBD);
        if (idStatus == 6) {
            contrColeta.alterarHoraAguardandoCliente(idColeta, nomeBD);
        } else if (idStatus == 5) {
            contrColeta.darBaixa(idColeta, idStatus, 0, nomeBD);
        }
        out.println("STATUS ALTERADO COM SUCESSO!");
    } catch (Exception e) {
        out.println("FALHA AO TROCAR STATUS!"+e.getMessage());
    }
%>
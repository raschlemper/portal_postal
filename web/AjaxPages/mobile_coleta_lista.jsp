<%@page import="Entidade.Clientes"%><%@page import="Controle.contrCliente"%><%@page import="java.util.Date"%><%@page import="java.text.SimpleDateFormat"%><%@page import="Coleta.Controle.contrColeta"%><%@page import="java.util.ArrayList"%><%@page import="Controle.contrEmpresa"%><%@page contentType="text/html" pageEncoding="UTF-8"%><%
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat hr1 = new SimpleDateFormat("HH:mm");

    int idCol = Integer.parseInt(request.getParameter("idColetador"));
    int idEmpresa = Integer.parseInt(request.getParameter("idEmpresa"));
    String nomeBD = contrEmpresa.cnpjEmpresa(idEmpresa);
    String dataPesquisaBD = sdf2.format(new Date());

    ArrayList listaColetas = contrColeta.consultaColetasPeloStatus(2, idCol, dataPesquisaBD, "dataHoraColeta", nomeBD);
    for (int i = 0; i < listaColetas.size(); i++) {
        Coleta.Entidade.Coleta col = (Coleta.Entidade.Coleta) listaColetas.get(i);
        int idColeta = col.getIdColeta();
        int idCli = col.getIdCliente();
        Clientes cli = contrCliente.consultaClienteById(idCli, nomeBD);
        String nomeFantasia = col.getNomeFantasia();
        String obs = col.getObs().replace(";", " ").replace(".", " ").replace("\n", " ").replace("\r", " ").replace("\t", " ").trim();
        if(obs.length() > 50){
            obs = obs.substring(0, 50);
        }        
        String hrDaColeta = hr1.format(col.getDataHoraColeta());
        String hrAguardandoColeta = "00:00";
        String hrColetaRealizada = "00:00";
        String numero = "";
        if (cli.getNumero() != null && !cli.getNumero().trim().equals("") && !cli.getNumero().equals("null")) {
            numero = ", " + cli.getNumero();
        }
        String endereco = cli.getEndereco() + numero + ", " + cli.getComplemento() + " - " + cli.getBairro() + " - " + cli.getCidade() + "/" + cli.getUf();

        double latitude = cli.getLatitude();
        double longitude = cli.getLongitude();

        out.println(idColeta + ";" + col.getStatusEntrega() + ";" + nomeFantasia + ";" + endereco + ";" + obs + ";" + latitude + ";" + longitude + ";" + hrDaColeta + ";" + hrAguardandoColeta + ";" + hrColetaRealizada);
    }
%>
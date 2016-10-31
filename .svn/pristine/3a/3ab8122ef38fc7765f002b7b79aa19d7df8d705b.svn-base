<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Util.FormataString"%>
<%@page import="Entidade.Endereco"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="org.tempuri.CServico"%>
<%@page import="org.tempuri.ArrayOfCServico"%>
<%

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String nomeBD = (String) session.getAttribute("nomeBD");
    int idCliente = (Integer) session.getAttribute("idCliente");
    String[] acc = Controle.contrCliente.consultaLoginPrecosPrazosCorreios(idCliente, nomeBD);
    String login = "";
    String senha = "";
    if (acc != null) {
        login = acc[0];
        senha = acc[1];
    }
    // TODO initialize WS operation arguments here
    String nCdEmpresa = login;
    try {
        int codAdm = Integer.parseInt(nCdEmpresa);
        nCdEmpresa = codAdm + "";
    } catch (NumberFormatException e) {
    }
    try {

        String sDataPostagem = sdf.format(new Date());
        String sDsSenha = senha;

        String sCepOrigem = request.getParameter("sCepOrigem");
        String sCepDestino = request.getParameter("sCepDestino");

        int nCdFormato = Integer.parseInt(request.getParameter("nCdFormato"));
        //  int nCdFormato = 1;
        String sCdMaoPropria = request.getParameter("sCdMaoPropria");
        String sCdAvisoRecebimento = request.getParameter("sCdAvisoRecebimento");
        String vVlValorDeclarado = request.getParameter("nVlValorDeclarado");

        String nVlPeso = "0";
        String vVlComprimento = "20";
        String vVlAltura = "20";
        String vVlLargura = "20";

        float pesoReal = 0;
        float pesoCubico = 0;

        nVlPeso = request.getParameter("nVlPeso");
        if (nVlPeso.equals("")) {
            nVlPeso = "0";
        }
        vVlComprimento = request.getParameter("nVlComprimento");
        if (vVlComprimento.equals("")) {
            vVlComprimento = "0";
        }
        vVlAltura = request.getParameter("nVlAltura");
        if (vVlAltura.equals("")) {
            vVlAltura = "0";
        }
        vVlLargura = request.getParameter("nVlLargura");
        if (vVlLargura.equals("")) {
            vVlLargura = "0";
        }


        String vVlDiametro = request.getParameter("nVlDiametro");
        java.math.BigDecimal nVlComprimento = new BigDecimal(vVlComprimento);
        java.math.BigDecimal nVlAltura = new BigDecimal(vVlAltura);
        java.math.BigDecimal nVlLargura = new BigDecimal(vVlLargura);
        java.math.BigDecimal nVlDiametro = new BigDecimal(vVlDiametro);
        vVlValorDeclarado = vVlValorDeclarado.replace(',', '.');
        java.math.BigDecimal nVlValorDeclarado = new BigDecimal(vVlValorDeclarado);

        /**
         * **********************************************************************
         */
        // String nCdServico = request.getParameter("nCdServico");//"41106,40010";
        //if tem conrato
        ArrayList<Integer> listaContrato = ContrClienteContrato.consultaContratoCliente(idCliente, nomeBD);

        //SE FOR UM SERVICO COM AMBITO NACIONAL, OBTIVER COM E SEM CONTRATO E NÃO FOR PAC AGRUPADO
        //ENTÃO CAPTA OS CÓDIGOS ECT DOS SERVIÇOS SEDEX10, SEDEX12, SEDEX E PAC E E-SEDEX(CONTRATO)        
        //sedex 12
        String nCdServico = "40169";
        //sedex 10
        if (listaContrato.contains(40789)) {
            nCdServico += ",40789";
        } else {
            nCdServico += ",40215";
        }
        //SEDEX
        if (listaContrato.contains(40096)) {
            nCdServico += ",40096";
        } else if (listaContrato.contains(40436)) {
            nCdServico += ",40436";
        } else if (listaContrato.contains(40444)) {
            nCdServico += ",40444";
        } else if (listaContrato.contains(40568)) {
            nCdServico += ",40568";
        } else if (listaContrato.contains(41408)) {
            nCdServico += ",41408";
        } else {
            nCdServico += ",40010";
        }
        //E-SEDEX
        if (listaContrato.contains(81019)) {
            nCdServico += ",81019";
        } else if (listaContrato.contains(81833)) {
            nCdServico += ",81833";
        }
        //PAC
        if (listaContrato.contains(41068)) {
            nCdServico += ",41068";
        } else if (listaContrato.contains(41211)) {
            nCdServico += ",41211";
        } else if (listaContrato.contains(41491)) {
            nCdServico += ",41491";
        } else {
            nCdServico += ",41106";
        }

        // TODO process result here
        org.tempuri.CalcPrecoPrazoWS service = new org.tempuri.CalcPrecoPrazoWS();
        org.tempuri.CalcPrecoPrazoWSSoap port = service.getCalcPrecoPrazoWSSoap();

        System.out.println(nCdServico);
        org.tempuri.CResultado result = port.calcPrecoPrazoData(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, nVlPeso, nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento, sDataPostagem);

        ArrayOfCServico a1 = result.getServicos();
        
        out.print("[");  
        StringBuffer sbf = new StringBuffer();
        for (CServico s1 : a1.getCServico()) {
            
            System.out.println(s1.getCodigo()+" - "+ s1.getValor());
            
            if (!(s1.getValor().equals("0") || s1.getValor().equals("0,00"))) {
                
                if (s1.getCodigo() == 40169) {
                   sbf.append("{\"tag\" : \"coS12\", ");

                } else if (s1.getCodigo() == 40789 || s1.getCodigo() == 40215) {
                    sbf.append("{\"tag\" : \"coS10\" , ");

                } else if (s1.getCodigo() == 40010 || s1.getCodigo() == 40096 || s1.getCodigo() == 40436 || s1.getCodigo() == 40444 || s1.getCodigo() == 40568 || s1.getCodigo() == 41408) {
                    sbf.append("{\"tag\" : \"coSdx\" ,");

                } else if (s1.getCodigo() == 41068 || s1.getCodigo() == 41211 || s1.getCodigo() == 41491 || s1.getCodigo() == 41106) {
                   sbf.append("{\"tag\" : \"coPac\" ,");

                } else if (s1.getCodigo() == 81019 || s1.getCodigo() == 81833) {
                    sbf.append("{\"tag\" : \"coEsx\" ,");
                }
              
                sbf.append("\"prazo\" : " + s1.getPrazoEntrega() + ",");
                sbf.append("\"valorTotal\" : " + s1.getValor().replace(",", ".") + ",");
                sbf.append("\"valorAR\" : " + s1.getValorAvisoRecebimento().replace(",", ".") + ",");
                sbf.append("\"valorMP\" : " + s1.getValorMaoPropria().replace(",", ".") + ",");
                sbf.append("\"valorVD\" : " + s1.getValorValorDeclarado().replace(",", ".")+ ",") ;
                sbf.append("\"valorSemAd\" : " + s1.getValorSemAdicionais().replace(",", ".")) ;
                sbf.append("},");
            }
        }
        out.print(sbf.toString().substring(0,  sbf.toString().lastIndexOf(",")));
        
      out.print("]");

    } catch (Exception e) {
        System.out.println(e.getMessage());
    }
%>


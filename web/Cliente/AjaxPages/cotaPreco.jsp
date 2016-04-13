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

    /*
     - dataPostagem=11/04/2016
     - nCdEmpresa=14175215
     - sDsSenha=06114935
     nCdServico=40436
     sCepOrigem=88015-976
     sCepDestino=06474-100
     nVlPeso=2
     nCdFormato=1
     nVlComprimento=30
     nVlAltura=10
     nVlLargura=20
     nVlDiametro=0
     sCdMaoPropria=N
     nVlValorDeclarado=0.00
     sCdAvisoRecebimento=N
     agrupado=0
     quantidade=2
    
     */
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
    // String nCdEmpresa = request.getParameter("nCdEmpresa").trim();
    String nCdEmpresa = login;
    try {
        int codAdm = Integer.parseInt(nCdEmpresa);
        nCdEmpresa = codAdm + "";
    } catch (NumberFormatException e) {
    }
    try {

        //String sDataPostagem = request.getParameter("dataPostagem").trim();
        //String sDsSenha = request.getParameter("sDsSenha").trim();
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

        float p = Float.parseFloat(nVlPeso);
        float a = Float.parseFloat(vVlAltura);
        float l = Float.parseFloat(vVlLargura);
        float c = Float.parseFloat(vVlComprimento);
        float cub = (a * l * c) / 6000;
        pesoCubico = cub;
        pesoReal = p;

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
       // ArrayList<String> listaServUnicos = new ArrayList<String>();
        //pac grande
        // listaServUnicos.add("41300");
        //sedex cobrar
        // listaServUnicos.add("40126");
        // listaServUnicos.add("40630");
        // listaServUnicos.add("40432");
        // listaServUnicos.add("40440");
        // listaServUnicos.add("40819");
        //pac cobrar
        // listaServUnicos.add("41238");
        // listaServUnicos.add("41262");

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

        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormat df3 = new DecimalFormat("#0.000");

        //org.tempuri.CResultado r1 = port.calcPrazo("41068", "89110000", "65073510");
        //org.tempuri.CResultado r1 = port.calcPrazoData("41068", "89110000", "65073510", "02/03/2016");
        //org.tempuri.CResultado r1 = port.calcPrazoRestricao("41068", "89110000", "65073510", "05/02/2016");  
        /*ArrayOfCServico a1 = r1.getServicos();
         for (CServico s1 : a1.getCServico()) {
         System.out.println("******************");
         System.out.println("codigo : "+s1.getCodigo());
         System.out.println("ent dom: "+s1.getEntregaDomiciliar());
         System.out.println("ent sab: "+s1.getEntregaSabado());
         System.out.println("erro   : "+s1.getErro());
         System.out.println("msg err: "+s1.getMsgErro());
         System.out.println("obs fim: "+s1.getObsFim());
         System.out.println("prazo e: "+s1.getPrazoEntrega() + " Dias");
         System.out.println("valor  : "+s1.getValor());
         System.out.println("valorAR: "+s1.getValorAvisoRecebimento());
         System.out.println("valorMP: "+s1.getValorMaoPropria());
         System.out.println("valorSA: "+s1.getValorSemAdicionais());
         System.out.println("valorVD: "+s1.getValorValorDeclarado());
         System.out.println("******************");
         }*/
        System.out.println(nCdServico);
        org.tempuri.CResultado result = port.calcPrecoPrazoData(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, nVlPeso, nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento, sDataPostagem);

        ArrayOfCServico a1 = result.getServicos();

        
        
        out.print("[");
        
        String sb = "";
        
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
              
              //  sbf.append("{");
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


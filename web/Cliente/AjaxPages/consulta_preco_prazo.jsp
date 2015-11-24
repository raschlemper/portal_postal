<%@page import="java.text.DecimalFormat"%>
<%@page import="Controle.ContrClienteContrato"%>
<%@page import="java.util.ArrayList"%>
<%@page import="Util.FormataString"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="org.tempuri.CServico"%>
<%@page import="org.tempuri.ArrayOfCServico"%>
<%@page import="Controle.contrCliente"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="Util.FormatarDecimal"%>
<%@page import="java.util.Date"%>
<%@page import="Entidade.Endereco"%>
<%@page import="org.dom4j.Node"%>
<%@page import="java.util.List"%>
<%@page import="org.dom4j.Document"%>
<%@page import="java.io.StringReader"%>
<%@page import="org.dom4j.io.SAXReader"%>
<%@page import="Util.ServicosAdicionais"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<br/><br/>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    String msgErro = "";
    try {

        String nomeBD = (String) session.getAttribute("nomeBD");
        int idCliente = (Integer) session.getAttribute("idCliente");

        // TODO initialize WS operation arguments here
        String nCdEmpresa = request.getParameter("nCdEmpresa").trim();
        try{
            int codAdm = Integer.parseInt(nCdEmpresa);
            nCdEmpresa = codAdm+"";
        }catch(NumberFormatException e){            
        }
        
        String sDsSenha = request.getParameter("sDsSenha").trim();
        String sCepOrigem = request.getParameter("sCepOrigem");
        String sCepDestino = request.getParameter("sCepDestino"); 
        int nCdFormato = Integer.parseInt(request.getParameter("nCdFormato"));  
        String sCdMaoPropria = request.getParameter("sCdMaoPropria");
        String sCdAvisoRecebimento = request.getParameter("sCdAvisoRecebimento");
        String vVlValorDeclarado = request.getParameter("nVlValorDeclarado");
        
        String nVlPeso = "0";
        String vVlComprimento = "20";
        String vVlAltura = "20";
        String vVlLargura = "20";
        
        String agrupado = request.getParameter("agrupado");
        float pesoAgrupado = 1;
        float pesoCubicoAgrupado = 0;
        
        if(agrupado.equals("0")){
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
        }else{   
            float somaVd = 0;
            int quantidade = Integer.parseInt(request.getParameter("quantidade"));            
            for(int i=0; i<quantidade;i++){
                float p = Float.parseFloat(request.getParameter("p"+i));
                float a = Float.parseFloat(request.getParameter("a"+i));
                float l = Float.parseFloat(request.getParameter("l"+i));
                float c = Float.parseFloat(request.getParameter("c"+i));
                try{
                    float vvd = Float.parseFloat(request.getParameter("vd"+i));
                    somaVd += vvd;
                }catch(NumberFormatException e){                    
                }                
                
                float cub = (a*l*c)/6000;
                if(cub > 10 && cub > p){
                    p = cub;
                }                             
                pesoAgrupado += p;
            }
            vVlValorDeclarado = somaVd+"";
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
        String nCdServico = request.getParameter("nCdServico");//"41106,40010";
        //if tem conrato
        ArrayList<Integer> listaContrato = ContrClienteContrato.consultaContratoCliente(idCliente, nomeBD);
        ArrayList<String> listaServUnicos = new ArrayList<String>();
        //pac grande
        listaServUnicos.add("41300");
        //sedex cobrar
        listaServUnicos.add("40126");
        listaServUnicos.add("40630");
        listaServUnicos.add("40432");
        listaServUnicos.add("40440");
        listaServUnicos.add("40819");
        //pac cobrar
        listaServUnicos.add("41238");
        listaServUnicos.add("41262");
        
        if (!listaServUnicos.contains(nCdServico) && agrupado.equals("0")) {
            //sedex 12
            nCdServico = "40169";            
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
            }else if (listaContrato.contains(81833)) {
                nCdServico += ",81833";
            }
            //PAC
            if (listaContrato.contains(41068)) {
                nCdServico += ",41068";
            }else if (listaContrato.contains(41211)) {
                nCdServico += ",41211";
            }else if (listaContrato.contains(41491)) {
                nCdServico += ",41491";
            } else {
                nCdServico += ",41106";
            }
        }

        // TODO process result here
        org.tempuri.CalcPrecoPrazoWS service = new org.tempuri.CalcPrecoPrazoWS();
        org.tempuri.CalcPrecoPrazoWSSoap port = service.getCalcPrecoPrazoWSSoap();
        
        float valorServicoAgrup = 0;
        DecimalFormat df = new DecimalFormat("#.##");
        if(agrupado.equals("1")){
            if(pesoAgrupado > 30){
                org.tempuri.CResultado resultTest1 = port.calcPrecoPrazo(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, "29", nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento);
                ArrayOfCServico at1 = resultTest1.getServicos();
                float p1 = 0;
                for (CServico ss1 : at1.getCServico()) {
                    //System.out.println("valor 29 = " + ss1.getValorSemAdicionais());
                    p1 = Float.parseFloat(ss1.getValorSemAdicionais().replace(",", "."));
                }

                org.tempuri.CResultado resultTest = port.calcPrecoPrazo(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, "30", nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento);
                ArrayOfCServico at = resultTest.getServicos();
                float p2 = 0;
                for (CServico ss : at.getCServico()) {
                    //System.out.println("valor sem 30 = " + ss.getValorSemAdicionais());
                    p2 = Float.parseFloat(ss.getValorSemAdicionais().replace(",", "."));
                }
                
                //System.out.println("valor dif = " + (p2 - p1));
                //System.out.println("peso dif = " + ((int) pesoAgrupado - 30));
                valorServicoAgrup = p2 + ((int) pesoAgrupado - 30) * (p2 - p1);
                //System.out.println(df.format(valorServicoAgrup));
                nVlPeso = "30";
            }else{
                //System.out.println("peso menor que 30 ");
                nVlPeso = pesoAgrupado+"";
            }
            
        }
        
        
        org.tempuri.CResultado result = port.calcPrecoPrazo(nCdEmpresa, sDsSenha, nCdServico, sCepOrigem, sCepDestino, nVlPeso, nCdFormato, nVlComprimento, nVlAltura, nVlLargura, nVlDiametro, sCdMaoPropria, nVlValorDeclarado, sCdAvisoRecebimento);
        ArrayOfCServico a = result.getServicos();

        contrCliente.alterarLoginPrecoPrazo(nCdEmpresa, sDsSenha, idCliente, nomeBD);
        Endereco endo = Util.PesquisarCep.pesquisaCEP(sCepOrigem);
        Endereco endd = Util.PesquisarCep.pesquisaCEP(sCepDestino);

        out.println("<table style='margin: 0 auto;border-left:0;' cellspacing='0' class='tb1'>");
        for (int i = 0; i < 8; i++) {
            out.println("<tr>");
            if (i == 0) {
                out.println("<td style='width:150px;background:white;'></td>");
            } else if (i == 1) {
                out.println("<th>Prazo de Entrega<br/><span style='font-size:11px;font-weight:normal;'>Para postagem em " + sdf.format(new Date()) + "</span></th>");                
            } else if (i == 2) {
                out.println("<th>Dias com Entrega</th>");
            } else if (i == 3) {
                if(agrupado.equals("1")){
                    out.println("<th>Valor da Remessa Agrupada</th>");                    
                }else{                    
                    out.println("<th>Valor do Serviço</th>");
                }
            } else if (i == 4) {
                out.println("<th>Mão Própria");
                if(agrupado.equals("1")){
                    out.println(" por Pacote");
                }
                out.println("</th>");
            } else if (i == 5) {
                out.println("<th>Aviso Recebimento");
                if(agrupado.equals("1")){
                    out.println(" por Pacote");
                }
                out.println("</th>");
            } else if (i == 6) {
                out.println("<th>Valor Declarado");
                if(agrupado.equals("1")){
                    //out.println(" por Pacote");
                }
                out.println("</th>");
            } else if (i == 7 && agrupado.equals("0")) {
                out.println("<th>Valor Total</th>");
            }

            for (CServico s : a.getCServico()) {
                boolean erro = false;
                if (!s.getErro().equals("0")) {
                    erro = true;
                    msgErro = s.getMsgErro();
                }

                if (i == 0) {
                    String[] serv = FormataString.getServicoPorCodigoECT(s.getCodigo());
                    out.println("<td align='center' valign='middle' style='width:150px;height:75px; border-top:1px solid #ccc;background:#f2f2f2;'>");
                    if(agrupado.equals("1")){
                        out.println("<img src='../../" + serv[2] + "' /><br/>PAC AGRUPADO | " + serv[0]);                        
                    }else{
                        out.println("<img src='../../" + serv[2] + "' /><br/>" + serv[1] + " | " + serv[0]);                        
                    }
                    out.println("</td>");
                } else if (i == 1) {
                    if (erro) {
                        out.println("<td align='center'><b style='color:red;'>" + msgErro + "</b></td>");
                    } else {
                        String prazo = s.getPrazoEntrega() + " Dias Úteis";
                        if(s.getCodigo() == 40215 || s.getCodigo() == 40789){
                            prazo = "Entrega até às 10:00 da manhã do dia útil seguinte ao da postagem.";
                        } else if(s.getCodigo() == 40169){
                            prazo = "Entrega até às 12:00 da manhã do dia útil seguinte ao da postagem.";
                        }
                        out.println("<td style='background: #dceeff;' align='center'><b>" + prazo + "</b></td>");
                    }
                }else if (i == 2) {
                    if (erro) {
                        out.println("<td></td>");
                    } else {
                        String ent = "de segunda a ";
                        if (s.getEntregaSabado().equals("S")) {
                            ent += "sábado";
                        } else {
                            ent += "sexta-feira";
                        }
                        out.println("<td align='center'>" + ent + "</td>");
                    }
                } else if (i == 3) {
                    if (erro) {
                        out.println("<td></td>");
                    } else if(valorServicoAgrup > 0) {
                        out.println("<td style='background: #dceeff;' align='center'><b>R$ " + df.format(valorServicoAgrup) + "</b></td>");
                    } else {
                        out.println("<td style='background: #dceeff;' align='center'><b>R$ " + s.getValorSemAdicionais() + "</b></td>");
                    }
                } else if (i == 4) {
                    if (erro) {
                        out.println("<td></td>");
                    } else {
                        String cor = "";
                        if (sCdMaoPropria.equals("N")) {
                            cor = "color:silver;";
                        }
                        out.println("<td style='" + cor + "' align='center'>R$ " + s.getValorMaoPropria() + "</td>");
                    }
                } else if (i == 5) {
                    if (erro) {
                        out.println("<td></td>");
                    } else {
                        String cor = "";
                        if (sCdAvisoRecebimento.equals("N")) {
                            cor = "color:silver;";
                        }
                        out.println("<td style='background: #dceeff;" + cor + "' align='center'>R$ " + s.getValorAvisoRecebimento() + "</td>");
                    }
                } else if (i == 6) {
                    if (erro) {
                        out.println("<td></td>");
                    } else {
                        String cor = "";
                        if (nVlValorDeclarado.floatValue() <= 0) {
                            cor = "color:silver;";
                        }
                        out.println("<td style='" + cor + "' align='center'>R$ " + s.getValorValorDeclarado() + "</td>");
                    }
                } else if (i == 7 && agrupado.equals("0")) {
                    out.println("<td style='background: #dceeff;' align='center'><b style='color:red;' >R$ " + s.getValor() + "</b></td>");                    
                }

            }

            out.println("</tr>");
        }
        out.println("</table>");
%>
<br/><br/>
<table style="margin: 0 auto;" cellspacing="0" class="tb1">
    <thead>
        <tr>
            <th></th>
            <th style="text-align: center; width: 300px;">ORIGEM</th>
            <th style="text-align: center; width: 300px;">DESTINO</th>
        </tr>
    </thead>
    <tbody style="font-size: 11px;">
        <tr class="even">
            <td><b>CEP</b></td>
            <td style="text-align: center; font-size: 12px;"><%= sCepOrigem%></td>
            <td style="text-align: center; font-size: 12px;"><%= sCepDestino%></td>
        </tr>
        <tr class="odd">
            <td><b>ENDEREÇO</b></td>
            <td style="text-align: center"><%= endo.getLogradouro()%></td>
            <td style="text-align: center"><%= endd.getLogradouro()%></td>
        </tr>
        <tr class="even">
            <td><b>BAIRRO</b></td>
            <td style="text-align: center"><%= endo.getBairro()%></td>
            <td style="text-align: center"><%= endd.getBairro()%></td>
        </tr>
        <tr class="odd">
            <td><b>CIDADE / UF</b></td>
            <td style="text-align: center"><%= endo.getCidade() + " / " + endo.getUf()%></td>
            <td style="text-align: center"><%= endd.getCidade() + " / " + endd.getUf()%></td>
        </tr>
</table>
<%
} catch (Exception ex) {
%>
<div style="width: 100%; text-align: center; color: red; font-weight: bold; font-size: 14px; text-transform: uppercase;">
    O Serviço dos Correios está temporariamente fora do ar!
    <br/>Tente novamente em alguns instantes.
    <br/><br/>
    Mensagem do Serviço: <%= ex.getMessage()%>
</div>
<%}%>
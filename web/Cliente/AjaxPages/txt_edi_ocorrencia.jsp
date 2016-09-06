<%@page import="Entidade.empresas"%>
<%@page import="java.util.Date"%><%@page import="java.text.SimpleDateFormat"%><%@page import="java.sql.Timestamp"%><%@page import="java.sql.ResultSet"%><%@page import="java.sql.PreparedStatement"%><%@page import="java.sql.SQLException"%><%@page import="Util.Conexao"%><%@page import="java.sql.Connection"%><%@page import="Controle.ContrlEDI"%><%@page import="java.util.Map"%><%@page import="Controle.contrCliente"%><%@page import="Entidade.Clientes"%><%

    //OCORENddMMyyyyHHmmsss
    SimpleDateFormat sdf1 = new SimpleDateFormat("ddMMyyHHmm");
    SimpleDateFormat sdf2 = new SimpleDateFormat("ddMMHHmm");
    SimpleDateFormat sdf3 = new SimpleDateFormat("ddMMyyyyHHmm");
    SimpleDateFormat sdf4 = new SimpleDateFormat("ddMMyyyyHHmmsss");
    SimpleDateFormat sdf5 = new SimpleDateFormat("ddMMyyyy");
    
    String modelo = request.getParameter("modelo");
    String nomeArquivo = "OCORENCORREIOS" + sdf4.format(new Date()) + ".txt";
    if (modelo.equals("EDI_EMBARC_3.0")) {
        nomeArquivo = "CONEMBCORREIOS" + sdf4.format(new Date()) + ".txt";     
    }

    response.setContentType("text/plain");
    response.setHeader("Content-disposition", "attachment; filename=" + nomeArquivo);
    response.setHeader("Cache-Control", "no-cache");

    empresas emp = (empresas) session.getAttribute("agencia");
    //System.out.println(emp.getCpf_cnpj().replace(".", "").replace("-", "").replace("/", ""));
    //System.out.println(emp.getEmpresa());
    String nomeBD = (String) session.getAttribute("empresa");
    String tipoPesquisa = request.getParameter("tipoPesquisa");
    
    int idCliente = Integer.parseInt(request.getParameter("idCliente"));
    String data = Util.FormatarData.DateToBD(request.getParameter("data"));
    String data2 = Util.FormatarData.DateToBD(request.getParameter("data2"));

    //Consulta cadastro do cliente
    Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
    //Consulta ocorrencias edi vs sro / key = codigo;tipo, Ex.: 1;BDE
    Map<String, Integer> mapOcor = ContrlEDI.consultaSROxEDI(idCliente, nomeBD);
    Map<Integer, String> mapOcorEdi = ContrlEDI.consultaOcorEDI(idCliente, nomeBD);

    //Consulta movimentacao tracking
    Connection conn = Conexao.conectar(nomeBD);
    String sql = "SELECT m.numObjeto, m.notaFiscal, t.last_status_date, t.last_status_code, t.last_status_type"
        + " FROM movimentacao AS m"
        + " LEFT JOIN movimentacao_tracking AS t ON t.numObjeto = m.numObjeto"
        + " WHERE idCliente = ? AND dataPostagem >= DATE_SUB(DATE(NOW()), INTERVAL 90 DAY) "
        + " AND (last_edi_date <> last_status_date OR last_edi_date IS NULL)";
    if (tipoPesquisa.equals("1")) {
        sql = "SELECT m.numObjeto, m.notaFiscal, t.last_status_date, t.last_status_code, t.last_status_type"
            + " FROM movimentacao AS m"
            + " LEFT JOIN movimentacao_tracking AS t ON t.numObjeto = m.numObjeto"
            + " WHERE idCliente = ? AND dataPostagem BETWEEN '" + data + "' AND '" + data2 + "' ";
    }

    try {
        PreparedStatement valores = conn.prepareStatement(sql);
        valores.setInt(1, idCliente);
        ResultSet result = (ResultSet) valores.executeQuery();

        if (modelo.equals("EDI_3.0")) {
            //HEADER 1
            out.print("000"); //codigo da linha
            out.print(Util.FormataString.preencheStringCom(emp.getEmpresa(), " ", 35, 1));//nome AGF
            out.print(Util.FormataString.preencheStringCom(cli.getNome(), " ", 35, 1));//nome cliente
            out.print(sdf1.format(new Date()));//data atual 
            out.print("OCO");
            out.print(sdf2.format(new Date()));//data atual
            out.print("0");
            out.println(Util.FormataString.preencheStringCom("", " ", 25, 1)); // FILLER
            //HEADER 2
            out.print("340"); //codigo da linha
            out.print("OCORR");
            out.print(sdf2.format(new Date()));//data atual
            out.print("0");
            out.println(Util.FormataString.preencheStringCom("", " ", 103, 1)); // FILLER
            //HEADER 3
            out.print("341"); //codigo da linha
            out.print(emp.getCpf_cnpj().replace(".", "").replace("-", "").replace("/", ""));//cnpj AGF
            out.print(Util.FormataString.preencheStringCom(emp.getEmpresa(), " ", 50, 1)); //nome AGF
            out.println(Util.FormataString.preencheStringCom("", " ", 53, 1)); // FILLER

            while (result.next()) {
                Timestamp date = result.getTimestamp("t.last_status_date");
                int code = result.getInt("t.last_status_code");
                String type = result.getString("t.last_status_type");
                String nf = result.getString("m.notaFiscal");
                int edi_code = 0;
                if (mapOcor.containsKey(code + ";" + type)) {
                    edi_code = mapOcor.get(code + ";" + type);
                }

                //BODY EDI 3.0
                out.print("342"); // codigo da linha
                out.print(Util.FormataString.preencheStringCom(cli.getCnpj(), " ", 14, 1));//cnpj do cliente
                out.print(Util.FormataString.preencheStringCom("2", " ", 3, 1)); //Serie NF
                out.print(Util.FormataString.preencheStringCom(nf, "0", 8, -1)); //Numero NF
                out.print(Util.FormataString.preencheStringCom(edi_code + "", "0", 2, -1)); //Código Ocorrencia EDI
                out.print(Util.FormataString.preencheStringCom(sdf3.format(date), "0", 12, -1)); //Data Ocorrencia
                out.print(Util.FormataString.preencheStringCom("00", "0", 2, -1)); //
                out.println(Util.FormataString.preencheStringCom(mapOcorEdi.get(edi_code), " ", 76, 1)); // FILLER
            }
        } else if (modelo.equals("EDI_5.0")) {
            //HEADER 1
            out.print("000"); //codigo da linha
            out.print(Util.FormataString.preencheStringCom(emp.getEmpresa(), " ", 35, 1));//nome AGF
            out.print(Util.FormataString.preencheStringCom(cli.getNome(), " ", 35, 1));//nome cliente
            out.print(sdf1.format(new Date()));//data atual 
            out.print("OCO");
            out.print(sdf2.format(new Date()));//data atual
            out.print("0");
            out.println(Util.FormataString.preencheStringCom("", " ", 25, 1)); // FILLER
            //HEADER 2
            out.print("540"); //codigo da linha
            out.print("OCORR");
            out.print(sdf2.format(new Date()));//data atual
            out.print("0");
            out.println(Util.FormataString.preencheStringCom("", " ", 103, 1)); // FILLER
            //HEADER 3
            out.print("541"); //codigo da linha
            out.print(emp.getCpf_cnpj().replace(".", "").replace("-", "").replace("/", ""));//cnpj AGF
            out.print(Util.FormataString.preencheStringCom(emp.getEmpresa(), " ", 50, 1)); //nome AGF
            out.println(Util.FormataString.preencheStringCom("", " ", 53, 1)); // FILLER

            int contador = 0;
            while (result.next()) {
                contador++;
                Timestamp date = result.getTimestamp("t.last_status_date");
                int code = result.getInt("t.last_status_code");
                String type = result.getString("t.last_status_type");
                String nf = result.getString("m.notaFiscal");
                int edi_code = 0;
                if (mapOcor.containsKey(code + ";" + type)) {
                    edi_code = mapOcor.get(code + ";" + type);
                }

                //BODY EDI 5.0
                out.print("542"); //codigo da linha
                out.print(Util.FormataString.preencheStringCom(cli.getCnpj(), " ", 14, 1));//cnpj do cliente
                out.print(Util.FormataString.preencheStringCom("", " ", 3, 1)); //Serie NF
                out.print(Util.FormataString.preencheStringCom(nf, "0", 9, -1)); //Numero NF
                out.print(Util.FormataString.preencheStringCom(edi_code + "", "0", 3, -1)); //Código Ocorrencia EDI
                out.print(Util.FormataString.preencheStringCom(sdf3.format(date), "0", 12, -1)); //Data Ocorrencia
                out.print(Util.FormataString.preencheStringCom("00", "0", 2, -1)); //Codigo da OBS
                out.print(Util.FormataString.preencheStringCom("0", " ", 80, 1)); //Informaçoes irrelevantes
                out.print(Util.FormataString.preencheStringCom("0", "0", 10, 1)); //Filial emissora do conhecimento
                out.print(Util.FormataString.preencheStringCom("0", " ", 5, 1)); //Serie do Conhecimento
                out.print(Util.FormataString.preencheStringCom("0", "0", 12, -1)); //Numero do Conhecimento
                out.print(Util.FormataString.preencheStringCom("1", "", 1, -1)); //Identificação do tipo da entrega 1 = primeira entrega
                out.print(Util.FormataString.preencheStringCom(" ", " ", 58, 1)); // dados de carregamento e saida
                out.print(Util.FormataString.preencheStringCom("0", "0", 14, 1)); // cnpj emissor da nota de devolucao
                out.print(Util.FormataString.preencheStringCom(" ", " ", 3, 1)); // serie da nota de devolucao 
                out.print(Util.FormataString.preencheStringCom("0", "0", 9, 1)); // numero da nota de devolucao
                out.println(Util.FormataString.preencheStringCom(" ", " ", 12, 1)); // FILLER
                out.println(Util.FormataString.preencheStringCom("543", " ", 250, 1)); //codigo da linha/FILLER

            }
            out.print(Util.FormataString.preencheStringCom("549", " ", 3, 1)); //codigo da linha
            out.print(Util.FormataString.preencheStringCom(contador + "", "0", 4, -1)); // quantidade do arquivo
            out.print(Util.FormataString.preencheStringCom(" ", " ", 243, 1)); // FILLER
        } else if (modelo.equals("EDI_EMBARC_3.0")) {
            //HEADER 1
            out.print("000"); //codigo da linha
            out.print(Util.FormataString.preencheStringCom(emp.getEmpresa(), " ", 35, 1));//nome AGF
            out.print(Util.FormataString.preencheStringCom(cli.getNome(), " ", 35, 1));//nome cliente
            out.print(sdf1.format(new Date()));//data atual 
            out.print("CON");
            out.print(sdf2.format(new Date()));//data atual
            out.print("0");
            out.println(Util.FormataString.preencheStringCom("", " ", 585, 1)); // FILLER ATE 680
            //HEADER 2
            out.print("320"); //codigo da linha
            out.print("CONHE");
            out.print(sdf2.format(new Date()));//data atual
            out.print("0");
            out.println(Util.FormataString.preencheStringCom("", " ", 663, 1)); // FILLER ATE 680
            //HEADER 3
            out.print("321"); //codigo da linha
            out.print(emp.getCpf_cnpj().replace(".", "").replace("-", "").replace("/", ""));//cnpj AGF
            out.print(Util.FormataString.preencheStringCom(emp.getEmpresa(), " ", 50, 1)); //nome AGF
            out.println(Util.FormataString.preencheStringCom("", " ", 623, 1)); // FILLER ATE 680

            int contador = 0;
            while (result.next()) {
                contador++;
                Timestamp date = result.getTimestamp("t.last_status_date");
                //int code = result.getInt("t.last_status_code");
                //String type = result.getString("t.last_status_type");
                String nf = result.getString("m.notaFiscal");
                /*int edi_code = 0;
                if (mapOcor.containsKey(code + ";" + type)) {
                    edi_code = mapOcor.get(code + ";" + type);
                }*/

                //BODY EDI 5.0
                out.print("322"); //codigo da linha
                out.print(Util.FormataString.preencheStringCom("", "0", 10, 1));//mcu agencia
                out.print(Util.FormataString.preencheStringCom("U", " ", 5, 1));//cnpj do cliente
                out.print(Util.FormataString.preencheStringCom(nf, "0", 12, -1)); //Numero objeto
                out.print(Util.FormataString.preencheStringCom(sdf5.format(date), "0", 8, -1)); //Data Ocorrencia 
                out.print(Util.FormataString.preencheStringCom("C", "", 1, -1)); //Codigo da OBS
                out.print(Util.FormataString.preencheStringCom("1", "0", 7, -1)); //peso
                out.print(Util.FormataString.preencheStringCom("1", "0", 15, -1)); //valor
                out.print(Util.FormataString.preencheStringCom("0", "0", 34, -1)); 
                out.print(Util.FormataString.preencheStringCom("1", "0", 15, -1)); //valor
                out.print(Util.FormataString.preencheStringCom("1", "0", 15, -1)); //valor
                out.print(Util.FormataString.preencheStringCom("0", "0", 75, -1)); 
                out.print(Util.FormataString.preencheStringCom("2", "0", 1, -1)); 
                out.print(Util.FormataString.preencheStringCom("0", "3", 1, -1)); 
                out.print(Util.FormataString.preencheStringCom(emp.getCpf_cnpj().replace(".", "").replace("-", "").replace("/", ""), "14", 1, -1)); //cnpj agf
                out.print(Util.FormataString.preencheStringCom(cli.getCnpj().replace(".", "").replace("-", "").replace("/", ""), "14", 1, -1)); //cnpj cliente
                out.print(Util.FormataString.preencheStringCom("2", "0", 3, -1)); //serie nf 2
                out.print(Util.FormataString.preencheStringCom(nf, "0", 8, -1)); //nf
                out.print(Util.FormataString.preencheStringCom("", "   00000000", 429, -1)); //serie + nf - 39 vezes
                out.print(Util.FormataString.preencheStringCom("I", "", 1, -1)); //serie + nf - 39 vezes
                out.print(Util.FormataString.preencheStringCom("N", "", 1, -1)); //serie + nf - 39 vezes
                out.print(Util.FormataString.preencheStringCom("0000", "", 1, -1)); //sect preencheu com 5352
            }
            out.print(Util.FormataString.preencheStringCom("323", " ", 3, 1)); //codigo da linha
            out.print(Util.FormataString.preencheStringCom(contador + "", "0", 4, -1)); // quantidade do arquivo
            out.print(Util.FormataString.preencheStringCom("0", "0", 15, -1)); //valor total
            out.print(Util.FormataString.preencheStringCom(" ", " ", 658, 1)); // FILLER ATE 680
        }

        valores.close();

    } catch (SQLException e) {
        out.print(e);
    } finally {
        Conexao.desconectar(conn);
    }


%>
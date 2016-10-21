<%@page import="java.util.Date,java.text.SimpleDateFormat,Entidade.ClientesUsuario,Controle.contrEmpresa,Entidade.empresas,Entidade.Clientes,java.sql.PreparedStatement,java.sql.ResultSet,java.sql.SQLException,java.sql.Connection,Util.Conexao"%><%

    //http://localhost:8084/Portal_Postal_Web/Cliente/Servicos/ws_consulta_postagem.jsp    
    //http://www.portalpostal.com.br/Cliente/Servicos/ws_consulta_postagem.jsp    
    response.setHeader("Cache-Control", "no-cache");

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HH.mm.ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    //parametros para a autenticacao 
    String codigoAGF = request.getParameter("codigoAGF");//"4";         //código da AGF
    String usuario = request.getParameter("usuario");//"encomendas";    //usuario WS
    String senha = request.getParameter("senha");//"quantity.123";      //senha WS

    //parametros para montagem do arquivo
    String tipoArquivo = request.getParameter("tipoArquivo");//"RAW";                                     //RAW = text/plain, FILE = attachment
    String formatoArquivo = request.getParameter("formatoArquivo");//"TAB";                               //CSV = separado por ponto e virgula, TAB = tabulado
    String separadorArquivo = request.getParameter("separadorArquivo");//";";                             //caractere separador do CSV
    String cabecalhoArquivo = request.getParameter("cabecalhoArquivo");//"S";                             //S = ecreve cabecalho, N = não escreve cabecalho
    String camposArquivo = request.getParameter("camposArquivo");//"SRO|NF|SITUACAO|DATA_SITUACAO|CEP";   //campos que serao escritos no arquivo
    String tamanhoArquivo = request.getParameter("tamanhoArquivo");//"20|30|20|30|10";                    //tamanho dos campos que serao escritos no arquivo

    //parametros de pesquisa
    String cep = request.getParameter("cep");//"";
    String data_inicio = request.getParameter("data_inicio");//"2016-09-01";
    String data_fim = request.getParameter("data_fim");//"2016-09-01";
    String sro = request.getParameter("sro");//"";
    String nf = request.getParameter("nf");//"";

    try {
        empresas agf = contrEmpresa.consultaEmpresa(Integer.parseInt(codigoAGF));
        String nomeBD = agf.getCnpj();

        if (agf == null) {
            out.print("ERRO: Codigo de agencia inexistente!");
        } else {
            ClientesUsuario us = Controle.contrSenhaCliente.usuarioEmp(usuario, senha, nomeBD);
            //Clientes cli = Emporium.Controle.ContrLoginEmporium.login(usuario, senha, nomeBD);
            if (us == null) {// || cli == null) {
                out.print("ERRO: Login ou senha esta incorreto! Verifique ainda se o codigo de sua agencia esta correto!");
            } else if (us.getNivel() != 99) {
                out.print("ERRO: Este usuario nao tem permissao para acessar o WS!");
            } else {
                String camposArray[] = camposArquivo.split("\\|");
                String tamanhosArray[] = tamanhoArquivo.split("\\|");
                if (formatoArquivo.equals("TAB") && camposArray.length != tamanhosArray.length) {
                    out.print("ERRO: Os parametros camposArquivo e tamanhoArquivos devem conter a mesma quantidade!");
                    out.print(camposArray.length + "!=" + tamanhosArray.length);
                } else {

                    if (tipoArquivo.equals("RAW")) {
                        response.setContentType("text/plain");
                    } else if (formatoArquivo.equals("CSV")) {
                        response.setContentType("application/xls");
                        response.setHeader("Content-disposition", "attachment; filename=arquivo_ws_" + sdf.format(new Date()) + ".csv");
                    } else {
                        response.setContentType("text/plain");
                        response.setHeader("Content-disposition", "attachment; filename=arquivo_ws_" + sdf.format(new Date()) + ".txt");
                    }

                    String sql = "SELECT m.*, last_status_name, last_status_date FROM movimentacao AS m"
                            + " LEFT JOIN movimentacao_tracking AS t ON t.numObjeto = m.numObjeto"
                            + " WHERE idCliente = ? ";

                    boolean temPesquisa = false;
                    if (sro != null && !sro.equals("")) {
                        temPesquisa = true;
                        sql += " AND m.numObjeto LIKE '%" + sro + "%'";
                    } else if (nf != null && !nf.equals("")) {
                        temPesquisa = true;
                        sql += " AND notaFiscal LIKE '%" + nf + "%'";
                    } else if (data_inicio != null && !data_inicio.equals("") && data_fim != null && !data_fim.equals("")) {
                        temPesquisa = true;
                        sql += " AND dataPostagem BETWEEN '" + data_inicio + "' AND '" + data_fim + "' ";
                        if (cep != null && !cep.equals("")) {
                            sql += " AND (cep LIKE '%" + cep + "%' OR cep LIKE '%" + cep.replaceAll("-", "") + "%') ";
                        }
                    }
                    if (!temPesquisa) {
                        out.print("ERRO: Nenhum parametro para pesquisa foi informado. sro, nf ou periodo de data (data_inicial e data_fim)!");
                    } else {

                        Connection conn = Conexao.conectar(nomeBD);
                        try {
                            PreparedStatement valores = conn.prepareStatement(sql);
                            valores.setInt(1, us.getIdCliente());
                            ResultSet result = (ResultSet) valores.executeQuery();

                            if (cabecalhoArquivo.equals("S")) {
                                if (formatoArquivo.equals("TAB")) {
                                    for (int i = 0; i < camposArray.length; i++) {
                                        String campo = camposArray[i];
                                        int tam = Integer.parseInt(tamanhosArray[i]);
                                        out.print(Util.FormataString.preencheStringCom(campo, " ", tam, 1));
                                    }
                                } else {
                                    out.print(camposArquivo.replace("|", separadorArquivo));
                                }
                                out.print("\r\n");
                            }

                            while (result.next()) {
                                for (int i = 0; i < camposArray.length; i++) {
                                    String campo = camposArray[i];
                                    //SRO|NF|SITUACAO|DATA_SITUACAO|CEP
                                    switch (campo.toUpperCase()) {
                                        case "SRO":
                                            campo = result.getString("m.numObjeto");
                                            break;
                                        case "CODIGO_ECT":
                                            campo = result.getString("m.codigoEct");
                                            break;
                                        case "SERVICO":
                                            campo = result.getString("m.descServico");
                                            break;
                                        case "ADICIONAIS":
                                            campo = result.getString("m.siglaServAdicionais");
                                            break;
                                        case "NF":
                                            campo = result.getString("m.notaFiscal");
                                            break;
                                        case "CARTAO_POSTAGEM":
                                            campo = result.getString("m.cartaoPostagem");
                                            break;
                                        case "OBS":
                                            campo = result.getString("m.obs");
                                            break;
                                        case "CONTEUDO":
                                            campo = result.getString("m.conteudoObjeto");
                                            break;
                                        case "CEP":
                                            campo = result.getString("m.cep");
                                            break;
                                        case "DESTINATARIO":
                                            campo = result.getString("m.destinatario");
                                            break;
                                        case "QTD":
                                            campo = result.getString("m.quantidade");
                                            break;
                                        case "VALOR":
                                            campo = result.getString("m.valorServico");
                                            break;
                                        case "VALOR_DECLR":
                                            campo = result.getString("m.valorDeclarado");
                                            break;
                                        case "VALOR_COBRAR":
                                            campo = result.getString("m.valorDestino");
                                            break;
                                        case "PESO":
                                            campo = result.getString("m.peso");
                                            break;
                                        case "ALTURA":
                                            campo = result.getString("m.altura");
                                            break;
                                        case "LARGURA":
                                            campo = result.getString("m.largura");
                                            break;
                                        case "COMPRIMENTO":
                                            campo = result.getString("m.comprimento");
                                            break;
                                        case "SITUACAO":
                                            campo = result.getString("last_status_name");
                                            break;
                                        case "DATA_SITUACAO":
                                            campo = "-";
                                            Date data = result.getDate("last_status_date");
                                            if (data != null) {
                                                campo = sdf3.format(data);
                                            }
                                            break;
                                        case "DATA_POSTAGEM":
                                            campo = "-";
                                            Date data1 = result.getDate("dataPostagem");
                                            if (data1 != null) {
                                                campo = sdf2.format(data1);
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                    if (formatoArquivo.equals("TAB")) {
                                        //limitar tamanho tabulado
                                        int tam = Integer.parseInt(tamanhosArray[i]);
                                        out.print(Util.FormataString.preencheStringCom(campo, " ", tam, 1));
                                    } else {
                                        if (i > 0) {
                                            out.print(separadorArquivo);
                                        }
                                        out.print(campo);
                                    }
                                }
                                out.print("\r\n");
                            }
                        } catch (SQLException e) {
                            out.print("ERRO: Falha ao consultar os dados! " + e.getMessage());
                        } finally {
                            Conexao.desconectar(conn);
                        }
                    }
                }
            }
        }
    } catch (Exception e) {
        out.print("ERRO: Falha ao processar os dados! " + e.getMessage());
    }
%>
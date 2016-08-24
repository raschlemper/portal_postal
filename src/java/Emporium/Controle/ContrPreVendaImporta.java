/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Controle;

import Controle.*;
import static Controle.ContrCep.estadoCep;
import Entidade.Amarracao;
import Entidade.ArquivoImportacao;
import Entidade.Clientes;
import Entidade.ClientesDeptos;
import Entidade.Destinatario;
import Entidade.ServicoECT;
import Util.CalculoEtiqueta;
import Util.Conexao;
import Util.FormatarDecimal;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.fileupload.FileItem;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

/**
 *
 * @author RICARDINHO
 */
public class ContrPreVendaImporta {

    private static boolean TEM_PEDIDO = false;
    private static String FALHA = "";
    private static String AVISO = "";
    private static final int MAX_ALLOWED = 1000;
    private static final Integer[] CEPS_SUSPENSOS = {-1};

    private static float verificaVD(float vd, String servico) {
        if (vd > 0 && vd < 17) {
            return 17;
        } else if ((servico.startsWith("IMPRESSO") || servico.startsWith("MDPB") || servico.equals("CARTA")) && vd > 500) {
            return 500;
        } else if ((servico.startsWith("PAC") || servico.equals("PAX")) && vd > 500) {
            return 3000;
        } else if (vd > 10000) {
            return 10000;
        }
        return vd;
    }

    public static ArrayList<ArquivoImportacao> validaDadosArquivo(ArrayList<ArquivoImportacao> listaAi, int idCliente, String servicoEscolhido, String nomeBD) {
        AVISO = "";
        FALHA = "";
        //MAP DE SERVICOS A VISTA
        Map<String, ServicoECT> listaServAvista = ContrServicoECT.consultaMapServicosAvista();
        //MAP DE SERVICOS DO CONTRATO
        Map<String, Integer> listaContratoCli = ContrClienteContrato.consultaMapContratoClienteByIdCliente(idCliente, nomeBD);

        for (ArquivoImportacao ai : listaAi) {
            try {
                //VALIDA O CEP
                int cepInteiro = 0;
                String cep = ai.getCep().replace(".", "").replace("-", "");
                if (cep.length() < 7 || cep.length() > 8) {
                    //CEP INVÁLIDO NA LINHA X
                    FALHA += "<br/>Linha n." + ai.getNrLinha() + " - CEP " + ai.getCep() + " invalido!";
                }
                try {
                    cepInteiro = Integer.parseInt(cep);
                } catch (NumberFormatException e) {
                    FALHA += "<br/>Linha n." + ai.getNrLinha() + " - CEP " + ai.getCep() + " invalido!";
                }

                int linha = 0;
                try {
                    linha = Integer.parseInt(ai.getNrLinha()) - 1;
                } catch (Exception e) {
                }
                //VERIFICA QUAL O SERVICO POSTADO
                String servico = "";
                if (!servicoEscolhido.equals("ARQUIVO")) {
                    servico = servicoEscolhido;
                    if (servico.startsWith("MDPB")) {
                        servico = "CARTA";
                        String resultado = ContrServicoAbrangencia.verificaMDPBxCep(cepInteiro, nomeBD);
                        if (resultado != null && resultado.equals("erro")) {
                            AVISO += "<br/>Linha n." + linha + " - CEP " + ai.getCep() + " nao aceita MDPB! O servico foi alterado para CARTA!";
                        } else {
                            servico = resultado;
                        }
                    }
                } else if (ai.getServico().startsWith("PAC")) {
                    servico = "PAC";
                } else if (ai.getServico().startsWith("CARTA")) {
                    servico = "CARTA";
                } else if (ai.getServico().startsWith("SIMPLES")) {
                    servico = "SIMPLES";
                } else if (ai.getServico().startsWith("PAX")) {
                    //VERIFICAR SE CEP POSSUI PAX
                    if (!ai.getContrato().equals("") && listaContratoCli.containsKey("PAX") && ContrServicoAbrangencia.verificaByCepServico(cepInteiro, "PAX", nomeBD)) {
                        servico = "PAX";
                    } else {
                        AVISO += "<br/>Linha n." + linha + " - CEP " + ai.getCep() + " nao aceita PAC GRANDES FORMATOS! O servico foi alterado para PAC!";
                        servico = "PAC";
                    }
                } else if (ai.getServico().startsWith("ESEDEX") || ai.getServico().startsWith("E-SEDEX")) {
                    //VERIFICAR SE CEP POSSUI ESEDEX
                    if (!ai.getContrato().equals("") && listaContratoCli.containsKey("ESEDEX") && ContrServicoAbrangencia.verificaByCepServico(cepInteiro, "ESEDEX", nomeBD)) {
                        servico = "ESEDEX";
                    } else {
                        AVISO += "<br/>Linha n." + linha + " - CEP " + ai.getCep() + " nao aceita E-Sedex! O servico foi alterado para Sedex!";
                        servico = "SEDEX";
                    }
                } else if (ai.getServico().replace(" ", "").startsWith("SEDEX10")) {
                    //VERIFICAR SE CEP POSSUI SEDEX10
                    if (ContrServicoAbrangencia.verificaByCepServico(cepInteiro, "SEDEX10", nomeBD)) {
                        servico = "SEDEX10";
                    } else {
                        AVISO += "<br/>Linha n." + linha + " - CEP " + ai.getCep() + " nao aceita Sedex 10! O servico foi alterado para Sedex!";
                        servico = "SEDEX";
                    }
                } else if (ai.getServico().replace(" ", "").startsWith("SEDEX12")) {
                    //VERIFICAR SE CEP POSSUI SEDEX12.
                    if (ContrServicoAbrangencia.verificaByCepServico(cepInteiro, "SEDEX12", nomeBD)) {
                        servico = "SEDEX12";
                    } else {
                        AVISO += "<br/>Linha n." + linha + " - CEP " + ai.getCep() + " nao aceita Sedex 12! O servico foi alterado para Sedex!";
                        servico = "SEDEX";
                    }
                } else if (ai.getServico().replace(" ", "").startsWith("SEDEXHJ")) {
                    //VERIFICAR SE CEP POSSUI SEDEXHJ.
                    if (ContrServicoAbrangencia.verificaByCepServico(cepInteiro, "SEDEXHJ", nomeBD)) {
                        servico = "SEDEXHJ";
                    } else {
                        AVISO += "<br/>Linha n." + linha + " - CEP " + ai.getCep() + " nao aceita Sedex Hoje! O servico foi alterado para Sedex!";
                        servico = "SEDEX";
                    }
                } else if (ai.getServico().startsWith("SEDEX")) {
                    servico = "SEDEX";
                } else if (ai.getServico().startsWith("MDPB")) {
                    servico = "CARTA";
                    String resultado = ContrServicoAbrangencia.verificaMDPBxCep(cepInteiro, nomeBD);
                    if (resultado != null && resultado.equals("erro")) {
                        AVISO += "<br/>Linha n." + linha + " - CEP " + ai.getCep() + " nao aceita MDPB! O servico foi alterado para CARTA!";
                    } else {
                        servico = resultado;
                    }
                }

                float vd = 0;
                try {
                    vd = Float.parseFloat(ai.getVd().replace(",", ".").trim());
                    vd = verificaVD(vd, servico);
                } catch (NumberFormatException e) {
                    FALHA += "<br/>Linha n." + ai.getNrLinha() + " - Valor Declr. " + ai.getVd() + " invalido!";
                }

                for (int cs : CEPS_SUSPENSOS) {
                    if (!servico.equals("CARTA") && cs == cepInteiro) {
                        servico = "MSGRIO";
                    }
                }

                if (servico.equals("MSGRIO")) {
                    FALHA += "<br/>Linha n." + linha + " <br/>Os envios para o CEP " + ai.getCep() + " estao suspensao ate 20/08/2016!<br/>Motivo: Jogos Olimpicos Rio 2016!<br/>Remova este objeto do arquivo para importar.";
                } else if (servico.equals("")) {
                    FALHA += "<br/>Linha n." + ai.getNrLinha() + " - Servico " + ai.getServico() + " invalido!";
                } else if (!ai.getNrObjeto().equals("avista") && !CalculoEtiqueta.validaNumObjeto(ai.getNrObjeto())) {
                    FALHA += "<br/>Linha n." + ai.getNrLinha() + " - Etiqueta " + ai.getNrObjeto() + " invalida!";
                } else {

                    float peso = 0;
                    float altura = 0;
                    float largura = 0;
                    float comprimento = 0;
                    try {
                        peso = Integer.parseInt(ai.getPeso());
                        altura = Integer.parseInt(ai.getAltura());
                        largura = Integer.parseInt(ai.getLargura());
                        comprimento = Integer.parseInt(ai.getComprimento());
                    } catch (NumberFormatException e) {
                        peso = 0;
                        altura = 0;
                        largura = 0;
                        comprimento = 0;
                    }

                    //consulta o codigo ect para o servico escolhido
                    int codECT = 0;
                    if (ai.getContrato().equals("") || !listaContratoCli.containsKey(servico)) {
                        codECT = listaServAvista.get(servico).getCodECT();
                        if (servico.equals("SIMPLES")) {
                            codECT = listaServAvista.get("CARTA").getCodECT();
                        }
                    } else {
                        codECT = listaContratoCli.get(servico);
                    }

                    ai.setCodECT(codECT);
                    ai.setServico(servico);
                    ai.setVd(vd + "");
                    ai.setCep(cep);
                    ai.setPeso(peso + "");
                    ai.setAltura(altura + "");
                    ai.setLargura(largura + "");
                    ai.setComprimento(comprimento + "");

                }
            } catch (Exception e) {
                //System.out.println("FALHA AO MONTAR DADOS DO SQL = " + e);
                FALHA += "<br/>FALHA AO MONTAR DADOS DO SQL = " + e;
            }

        }

        return listaAi;
    }

    public static String montaSqlPedido(ArrayList<ArquivoImportacao> listaAi, String nomeBD) {
        String sql = "INSERT INTO pre_venda (numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, registro, chave, metodo_insercao, registro_modico) VALUES ";
        for (ArquivoImportacao ai : listaAi) {

            //verifica se tem registo para carta a vista
            int registro = 0;
            int registro_modico = 0;
            if ((ai.getCodECT() == 10014 && ai.getServico().equals("CARTA")) || ai.getServico().startsWith("MDPB")) {
                registro = 1;
            }
            if (ai.getRm() != null && ai.getRm().equals("1") && (ai.getServico().equals("CARTA") || ai.getServico().startsWith("MDPB"))) {
                registro = 0;
                registro_modico = 1;
            }

            //consulta a amarração do objeto
            Amarracao am = ContrAmarracao.consultaAmarracaoByCep(ai.getCep(), ai.getServico(), nomeBD);
            String siglaAmarracao = "- - -";
            if (am != null) {
                siglaAmarracao = am.getSiglaAmarracao();
            }

            int idRemetente = 1;
            float vlrCobrar = 0;
            String tipo = "SERVICO";

            //INSERE O DESTINATARIO
            int idDestinatario = ContrPreVendaDest.inserir(ai.getIdCliente(), ai.getNome(), ai.getCpf(), ai.getEmpresa(), ai.getCep(), ai.getEndereco(), ai.getNumero(), ai.getComplemento(), ai.getBairro(), ai.getCidade(), ai.getUf(), ai.getEmail(), ai.getCelular(), "Brasil", nomeBD);
            //MONTA SQL PRE_VENDA
            sql += "\n('" + ai.getNrObjeto() + "', " + ai.getIdCliente() + ", " + idDestinatario + ", " + idRemetente + ", '" + ai.getCodECT() + "', '" + ai.getContrato() + "', '" + ai.getDepartamento() + "', '" + ai.getAosCuidados() + "', '" + ai.getObs() + "', '" + ai.getConteudo() + "', " + ai.getPeso() + ", " + ai.getAltura() + ", " + ai.getLargura() + ", " + ai.getComprimento() + ", " + ai.getVd() + ", " + ai.getAr() + ", " + ai.getMp() + ", '" + siglaAmarracao + "', '" + ai.getServico() + "', '" + ai.getNotaFiscal() + "', " + vlrCobrar + ", '" + tipo + "', " + ai.getIdDepartamento() + ", NOW(), '" + ai.getCartaoPostagem() + "', " + registro + ", '" + ai.getChave() + "', '" + ai.getMetodoInsercao() + "', " + registro_modico + "),";

        }
        return sql.substring(0, sql.lastIndexOf(","));
    }

    /**
     * *************************************
     */
    /**
     * *************************************
     */
    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static String importaPedido(FileItem item, int idCliente, int idDepartamento, String departamento, String contrato, String cartaoPostagem, String servicoEscolhido, int rm, String nomeBD) {

        try {
            //CONTADOR DE LINHA
            int qtdLinha = 1;
            ArrayList<ArquivoImportacao> listaAi = new ArrayList<ArquivoImportacao>();
            BufferedReader le = new BufferedReader(new InputStreamReader(item.getInputStream(), "ISO-8859-1"));
            // LE UMA LINHA DO ARQUIVO PARA PULAR O CABEÇALHO
            le.readLine();
            while (le.ready()) {
                //LE UMA LINHA DO ARQUIVO E DIVIDE A LINHA POR PONTO E VIRGULA
                String[] aux = le.readLine().replace(";", " ; ").split(";");
                //ADICIONA CONTADOR DE LINHA
                qtdLinha++;
                //VERIFICA QUANTIDADE MAXIMA DE LINHAS PERMITIDAS
                if (qtdLinha > MAX_ALLOWED) {
                    return "Quantidade maxima de importacao de " + MAX_ALLOWED + " objetos por importacao!";
                } else if (aux != null && aux.length >= 17) {
                    ArquivoImportacao ai = new ArquivoImportacao();
                    ai.setIdCliente(idCliente);
                    ai.setIdDepartamento(idDepartamento);
                    ai.setDepartamento(departamento);
                    ai.setContrato(contrato);
                    ai.setCartaoPostagem(cartaoPostagem);
                    ai.setMetodoInsercao("IMPORTACAO_CSV");
                    ai.setCodECT(0);

                    ai.setNrLinha(qtdLinha + "");
                    ai.setNrObjeto("avista");
                    ai.setNome(aux[0].trim());
                    ai.setEmpresa(aux[1].trim());
                    ai.setCpf(aux[2].trim());
                    ai.setCep(aux[3].trim());
                    ai.setEndereco(aux[4].trim());
                    ai.setNumero(aux[5].trim());
                    ai.setComplemento(aux[6].trim());
                    ai.setBairro(aux[7].trim());
                    ai.setCidade(aux[8].trim());
                    ai.setUf(aux[9].trim());
                    ai.setEmail("");
                    ai.setCelular("");
                    if (aux.length >= 19 && aux[18] != null) {
                        ai.setCelular(aux[17].trim() + aux[18].trim());
                    } else {
                        ai.setCelular("");
                    }

                    if (aux.length >= 20 && aux[19] != null) {
                        ai.setEmail(aux[19].trim());
                    } else {
                        ai.setEmail("");
                    }

                    ai.setAosCuidados(aux[10].trim());
                    ai.setNotaFiscal(aux[11].trim());
                    ai.setServico(aux[12].trim().toUpperCase());

                    // limita o tamanho da observação e do conteudo
                    String obs = aux[15].trim();
                    String cont = aux[16].trim();

                    if (obs.length() > 50) {
                        obs = obs.substring(0, 49);
                    }
                    if (cont.length() > 50) {
                        cont = cont.substring(0, 49);
                    }

                    ai.setObs(obs);
                    ai.setConteudo(cont);

                    if (aux.length >= 21 && aux[20] != null) {
                        ai.setChave(aux[20].trim());
                    } else {
                        ai.setChave("");
                    }

                    if (aux.length >= 22 && aux[21] != null) {
                        ai.setPeso(aux[21].trim());
                    } else {
                        ai.setPeso("0");
                    }

                    if (aux.length >= 23 && aux[22] != null) {
                        ai.setAltura(aux[22].trim());
                    } else {
                        ai.setAltura("0");
                    }

                    if (aux.length >= 24 && aux[23] != null) {
                        ai.setLargura(aux[23].trim());
                    } else {
                        ai.setLargura("0");
                    }

                    if (aux.length >= 25 && aux[24] != null) {
                        ai.setComprimento(aux[24].trim());
                    } else {
                        ai.setComprimento("0");
                    }

                    if (aux[13].trim().toUpperCase().contains("AR")) {
                        ai.setAr("1");
                    } else {
                        ai.setAr("0");
                    }

                    if (aux[13].trim().toUpperCase().contains("MP")) {
                        ai.setMp("1");
                    } else {
                        ai.setMp("0");
                    }

                    if (aux[13].trim().toUpperCase().contains("VD")) {
                        ai.setVd(aux[14].trim());
                    } else {
                        ai.setVd("0");
                    }

                    if (aux[13].trim().toUpperCase().contains("RM") || rm == 1) {
                        ai.setRm("1");
                    } else {
                        ai.setRm("0");
                    }

                    if (aux[13].trim().toUpperCase().contains("PR")) {
                        ai.setPr("1");
                    } else {
                        ai.setPr("0");
                    }

                    listaAi.add(ai);

                }
            }
            le.close();

            if (listaAi.size() > 0) {
                //valida os dados do arquivo para efetuar a importacao
                listaAi = validaDadosArquivo(listaAi, idCliente, servicoEscolhido, nomeBD);
                if (!FALHA.equals("")) {
                    //retorna mensagem de FALHA
                    return FALHA;
                } else {
                    //MONTA SQL
                    String sql = montaSqlPedido(listaAi, nomeBD);
                    boolean flag = inserir(sql, nomeBD);
                    if (flag) {
                        return "Pedidos Importados Com Sucesso!<br/>" + AVISO;
                    } else {
                        return "Falha ao importar Pedidos!";
                    }
                }
            } else {
                return "Nenhum pedido no arquivo para importar!";
            }

        } catch (IOException e) {
            return "Não foi possivel ler o arquivo: " + e;
        } catch (Exception e) {
            return "Falha na importacao dos pedidos: " + e;
        }

    }

    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static String importaPedidoPS(FileItem item, int idCliente, int idDepartamento, String departamento, String contrato, String cartaoPostagem, String servicoEscolhido, String nomeBD) {
        System.out.println("importa pedidos");
        try {
            //CONTADOR DE LINHA
            int qtdLinha = 1;
            ArrayList<ArquivoImportacao> listaAi = new ArrayList<ArquivoImportacao>();
            //LER EXCELL

            // For storing data into CSV files
            StringBuffer data = new StringBuffer();
            try {

                // Get the workbook object for XLS file        
                HSSFWorkbook workbook = new HSSFWorkbook(item.getInputStream());
                // Get first sheet from the workbook
                HSSFSheet sheet = workbook.getSheetAt(0);
                Cell cell;
                Row row;

                // Iterate through each rows from first sheet
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    row = rowIterator.next();
                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        cell = cellIterator.next();

                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_BOOLEAN:
                                data.append(cell.getBooleanCellValue()).append(";");
                                break;

                            case Cell.CELL_TYPE_NUMERIC:
                                data.append(cell.getNumericCellValue()).append(";");
                                break;

                            case Cell.CELL_TYPE_STRING:
                                data.append(cell.getStringCellValue()).append(";");
                                break;

                            case Cell.CELL_TYPE_BLANK:
                                data.append("" + ";");
                                break;

                            default:
                                data.append(cell).append(";");
                        }

                        data.append('\n');
                    }
                }
                System.out.println(data.toString());
            } catch (FileNotFoundException e) {
                System.out.println(e);
            } catch (IOException e) {
                System.out.println(e);
            }

            InputStream is = new ByteArrayInputStream(data.toString().getBytes());
            BufferedReader le = new BufferedReader(new InputStreamReader(is));

            // LE UMA LINHA DO ARQUIVO PARA PULAR O CABEÇALHO
            le.readLine();
            while (le.ready()) {
                //LE UMA LINHA DO ARQUIVO E DIVIDE A LINHA POR PONTO E VIRGULA
                String[] aux = le.readLine().replace(";", " ; ").split(";");
                //ADICIONA CONTADOR DE LINHA
                qtdLinha++;
                //VERIFICA QUANTIDADE MAXIMA DE LINHAS PERMITIDAS
                if (qtdLinha > MAX_ALLOWED) {
                    return "Quantidade maxima de importacao de " + MAX_ALLOWED + " objetos por importacao!";
                } else if (aux != null && aux.length >= 17) {
                    ArquivoImportacao ai = new ArquivoImportacao();
                    ai.setIdCliente(idCliente);
                    ai.setIdDepartamento(idDepartamento);
                    ai.setDepartamento(departamento);
                    ai.setContrato(contrato);
                    ai.setCartaoPostagem(cartaoPostagem);
                    ai.setMetodoInsercao("IMPORTACAO_PS");
                    ai.setCodECT(0);
                    ai.setNrLinha(qtdLinha + "");
                    ai.setNrObjeto("avista");
                    ai.setNome(aux[0].trim());
                    ai.setEmpresa("");
                    ai.setCpf("");
                    ai.setCep(aux[7].trim());
                    ai.setEndereco(aux[1].trim());
                    ai.setNumero(aux[2].trim());
                    ai.setComplemento(aux[3].trim());
                    ai.setBairro(aux[4].trim());
                    ai.setCidade(aux[5].trim());
                    ai.setUf(aux[6].trim());
                    ai.setEmail("");
                    ai.setCelular("");
                    ai.setAosCuidados("");
                    ai.setNotaFiscal(aux[10].trim());
                    ai.setServico(aux[8].trim().toUpperCase());

                    // limita o tamanho da observação e do conteudo
                    String obs = aux[13].trim();
                    String cont = aux[13].trim();

                    if (obs.length() > 50) {
                        obs = obs.substring(0, 49);
                    }
                    if (cont.length() > 50) {
                        cont = cont.substring(0, 49);
                    }
                    ai.setObs(obs);
                    ai.setConteudo(cont);
                    ai.setChave("");
                    ai.setPeso(aux[14].trim());
                    ai.setAltura("4");
                    ai.setLargura("11");
                    ai.setComprimento("24");
                    ai.setAr("0");
                    ai.setMp("0");
                    ai.setVd("0");
                    listaAi.add(ai);

                }
            }
            le.close();

            if (listaAi.size() > 0) {
                //valida os dados do arquivo para efetuar a importacao
                listaAi = validaDadosArquivo(listaAi, idCliente, servicoEscolhido, nomeBD);
                if (!FALHA.equals("")) {
                    //retorna mensagem de FALHA
                    return FALHA;
                } else {
                    //MONTA SQL
                    String sql = montaSqlPedido(listaAi, nomeBD);
                    boolean flag = inserir(sql, nomeBD);
                    if (flag) {
                        return "Pedidos Importados Com Sucesso!<br/>" + AVISO;
                    } else {
                        return "Falha ao importar Pedidos!";
                    }
                }
            } else {
                return "Nenhum pedido no arquivo para importar!";
            }

        } catch (IOException e) {
            return "Não foi possivel ler o arquivo: " + e;
        } catch (Exception e) {
            return "Falha na importacao dos pedidos: " + e;
        }

    }

    public static String importaPedidoPSN(FileItem item, int idCliente, int idDepartamento, String departamento, String contrato, String cartaoPostagem, String servicoEscolhido, String nomeBD) {

        try {
            //System.out.println("entrou importaPedidoPSN");
            //CONTADOR DE LINHA
            int qtdLinha = 1;
            ArrayList<ArquivoImportacao> listaAi = new ArrayList<ArquivoImportacao>();
            BufferedReader le = new BufferedReader(new InputStreamReader(item.getInputStream(), "ISO-8859-1"));
            // LE UMA LINHA DO ARQUIVO PARA PULAR O CABEÇALHO
            le.readLine();
            while (le.ready()) {
                //System.out.println("linha "+ qtdLinha);
                //LE UMA LINHA DO ARQUIVO E DIVIDE A LINHA POR PONTO E VIRGULA
                String[] aux = le.readLine().replace(";", " ; ").split(";");
                //ADICIONA CONTADOR DE LINHA
                qtdLinha++;
                //VERIFICA QUANTIDADE MAXIMA DE LINHAS PERMITIDAS
                if (qtdLinha > MAX_ALLOWED) {
                    return "Quantidade maxima de importacao de " + MAX_ALLOWED + " objetos por importacao!";
                } else if (aux != null && aux.length >= 15) {
                    ArquivoImportacao ai = new ArquivoImportacao();
                    ai.setIdCliente(idCliente);
                    ai.setIdDepartamento(idDepartamento);
                    ai.setDepartamento(departamento);
                    ai.setContrato(contrato);
                    ai.setCartaoPostagem(cartaoPostagem);
                    ai.setMetodoInsercao("IMPORTACAO_PSN");
                    ai.setCodECT(0);
                    ai.setNrLinha(qtdLinha + "");
                    ai.setNrObjeto(aux[9].trim());
                    //System.out.println("linha "+ qtdLinha+" sro "+ aux[9].trim());
                    ai.setNome(aux[1].trim());
                    ai.setEmpresa("");
                    ai.setCpf("");
                    ai.setCep(aux[8].trim());
                    ai.setEndereco(aux[2].trim());
                    ai.setNumero(aux[3].trim());
                    ai.setComplemento(aux[4].trim());
                    ai.setBairro(aux[5].trim());
                    ai.setCidade(aux[6].trim());
                    ai.setUf(aux[7].trim());
                    ai.setEmail("");
                    ai.setCelular(aux[16].trim());
                    ai.setAosCuidados("");
                    ai.setNotaFiscal(aux[11].trim());
                    String serv = aux[10].trim().toUpperCase();
                    if (serv.equals("109819")) {
                        serv = "PAC";
                    } else if (serv.equals("109810")) {
                        serv = "SEDEX";
                    } else if (serv.equals("104672")) {
                        serv = "ESEDEX";
                    }
                    ai.setServico(serv);

                    // limita o tamanho da observação e do conteudo
                    String obs = aux[12].trim();
                    String cont = aux[12].trim();

                    if (obs.length() > 50) {
                        obs = obs.substring(0, 49);
                    }
                    if (cont.length() > 50) {
                        cont = cont.substring(0, 49);
                    }
                    ai.setObs(obs);
                    ai.setConteudo(cont);
                    ai.setChave("");
                    ai.setPeso(aux[13].trim());
                    ai.setAltura("4");
                    ai.setLargura("11");
                    ai.setComprimento("24");
                    ai.setAr("0");
                    ai.setMp("0");
                    ai.setVd("0");
                    listaAi.add(ai);

                }
            }
            le.close();

            if (listaAi.size() > 0) {
                //valida os dados do arquivo para efetuar a importacao
                listaAi = validaDadosArquivo(listaAi, idCliente, servicoEscolhido, nomeBD);
                if (!FALHA.equals("")) {
                    //retorna mensagem de FALHA
                    return FALHA;
                } else {
                    //MONTA SQL
                    String sql = montaSqlPedido(listaAi, nomeBD);
                    boolean flag = inserir(sql, nomeBD);
                    if (flag) {
                        return "Pedidos Importados Com Sucesso!<br/>" + AVISO;
                    } else {
                        return "Falha ao importar Pedidos!";
                    }
                }
            } else {
                return "Nenhum pedido no arquivo para importar!";
            }

        } catch (IOException e) {
            return "Não foi possivel ler o arquivo: " + e;
        } catch (Exception e) {
            return "Falha na importacao dos pedidos: " + e;
        }

    }

    private static String getNomeDestinatario(String dests[], int position) {

        return (dests[position]).split("\r\n")[2].replace(" ", " ");
    }

    private static String getLinhaEndereco(String dests[], int position) {
        String lines[] = (dests[position]).split("\r\n");
        int pos = 0;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("pedido")) {
                pos = i;
            }
        }
        String[] arr2 = Arrays.copyOfRange(lines, 3, pos - 2);
        String res = "";
        for (String linha : arr2) {
            res += " " + linha.trim();
        }

        return res.trim().replace(" ", " ");
    }

    private static String getEndereco(String linhaEnd) {
        String res[] = linhaEnd.split(",");
        return res[0].trim().replace(" ", " ");

    }

    private static String getComplemento(String linhaEnd) {
        String res[] = linhaEnd.split(",");
        String retorno = "";
        for (int i = 1; i < res.length - 2; i++) {
            retorno += res[i].trim() + " ";
        }
        return retorno.trim().replace(" ", " ");

    }

    private static String getCidade(String linhaEnd) {
        String res[] = linhaEnd.split(",");
        return res[(res.length) - 2].trim().replace(" ", " ");

    }

    private static String getCep(String dests[], int position) {
        String lines[] = (dests[position]).split("\r\n");
        String cep = "";
        for (String line : lines) {
            if (!line.contains("pedido")) {
                line = replaceNonDigits(line);
                if (line.length() == 8) {
                    cep = line;
                }
            }
        }

        return cep;
    }

    private static String getNumPedido(String dests[], int position) {
        String lines[] = (dests[position]).split("\r\n");
        String res = "";
        for (String line : lines) {
            if (line.contains("pedido")) {
                res = line.split(":")[1].trim();
            }
        }
        return res;
    }

    private static String getEnvio(String dests[], int position) {
        String lines[] = (dests[position]).split("\r\n");
        String res = "";
        String aux = "";
        for (String line : lines) {
            if (line.contains("envio")) {
                aux = line.split("envio")[1].trim();
                if (aux.contains("Impresso")) {
                    res = "MDPB";
                } else {
                    res = "1";
                }
            }
        }
        return res;
    }

    private static String getTipoReg(String dests[], int position) {
        String lines[] = (dests[position]).split("\r\n");
        String res = "";
        String aux = "";
        for (String line : lines) {
            if (line.contains("envio")) {
                aux = line.split("envio")[1].trim();
                if (aux.contains("módico")) {
                    res = "RM";
                } else {
                    res = "0";
                }
            }

        }
        return res;
    }

    public static String replaceNonDigits(String string) {
        if (string == null || string.length() == 0) {
            return "0";
        }
        String ret = string.replaceAll("[^0-9]+", "");
        if (ret.equals("")) {
            return "0";
        } else {
            return ret;
        }
    }

    public static String importaPedidoEV(FileItem item, int idCliente, int idDepartamento, String departamento, String contrato, String cartaoPostagem, String servicoEscolhido, String nomeBD) {

        ArrayList<ArquivoImportacao> listaAi = new ArrayList<ArquivoImportacao>();
        PDDocument document = null;
        try {
            document = PDDocument.load(item.getInputStream());
            document.getClass();
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper Tstripper = new PDFTextStripper();
                String st = Tstripper.getText(document);
                document.close();
                String dests[] = st.split("Destinatário");
                if (dests.length > MAX_ALLOWED) {
                    return "Quantidade maxima de importacao de " + MAX_ALLOWED + " objetos por importacao!";
                } else {
                    for (int i = 1; i < dests.length; i++) {                       
                        ArquivoImportacao ai = new ArquivoImportacao();
                        ai.setIdCliente(idCliente);
                        ai.setIdDepartamento(idDepartamento);
                        ai.setDepartamento(departamento);
                        ai.setContrato(contrato);
                        ai.setCartaoPostagem(cartaoPostagem);
                        ai.setMetodoInsercao("IMPORTACAO_EV");
                        ai.setCodECT(0);
                        ai.setNrLinha(i + "");

                        ai.setNrObjeto("avista");
                        ai.setNome(getNomeDestinatario(dests, i));
                        ai.setEmpresa("");
                        ai.setCpf("");
                        ai.setCep(getCep(dests, i));
                        ai.setEndereco(getEndereco(getLinhaEndereco(dests, i)));
                        ai.setNumero("");
                        ai.setComplemento(getComplemento(getLinhaEndereco(dests, i)));
                        ai.setBairro("");
                        ai.setCidade(getCidade(getLinhaEndereco(dests, i)));

                        ai.setUf(estadoCep(getCep(dests, i)));
                        ai.setEmail("");
                        ai.setCelular("");
                        ai.setAosCuidados("");
                        ai.setNotaFiscal(getNumPedido(dests, i));
                        String serv = getEnvio(dests, i);
                        if (serv.equals("MDPB")) {
                            ai.setServico("MDPB");
                        } else {
                            ai.setServico("SEDEX");
                        }
                        String rg = getTipoReg(dests, i);
                        if (serv.equals("0")) {
                            ai.setRm("0");
                        } else {
                            ai.setRm("1");
                        }
                        ai.setObs("");
                        ai.setConteudo("");
                        ai.setChave("");
                        ai.setPeso("0");
                        ai.setAltura("4");
                        ai.setLargura("11");
                        ai.setComprimento("24");
                        ai.setAr("0");
                        ai.setMp("0");
                        ai.setVd("0");
                        listaAi.add(ai);
                    }

                    if (listaAi.size() > 0) {
                        //valida os dados do arquivo para efetuar a importacao
                        listaAi = validaDadosArquivo(listaAi, idCliente, servicoEscolhido, nomeBD);
                        if (!FALHA.equals("")) {
                            //retorna mensagem de FALHA
                            return FALHA;
                        } else {
                            //MONTA SQL
                            String sql = montaSqlPedido(listaAi, nomeBD);
                            boolean flag = inserir(sql, nomeBD);
                            if (flag) {
                                return "Pedidos Importados Com Sucesso!<br/>" + AVISO;
                            } else {
                                return "Falha ao importar Pedidos!";
                            }
                        }
                    } else {
                        return "Nenhum pedido no arquivo para importar!";
                    }

                }

            } else {
                return "Falha ao importar Pedidos!";
            }

        } catch (IOException ex) {
            return "Não foi possivel ler o arquivo: " + ex;
        } catch (Exception e) {
            return "Falha na importacao dos pedidos: " + e;
        }

    }

    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static String importaPedidoWebVendas(FileItem item, int idCliente, int idDepartamento, String departamento, String contrato, String cartaoPostagem, String servicoEscolhido, String nomeBD) {

        try {
            //CONTADOR DE LINHA
            int qtdLinha = 1;
            ArrayList<ArquivoImportacao> listaAi = new ArrayList<ArquivoImportacao>();
            BufferedReader le = new BufferedReader(new InputStreamReader(item.getInputStream(), "ISO-8859-1"));
            // LE 2 LINHAS DO ARQUIVO PARA PULAR O CABEÇALHO
            le.readLine();
            le.readLine();
            while (le.ready()) {
                //LE UMA LINHA DO ARQUIVO E DIVIDE A LINHA POR PONTO E VIRGULA
                String[] aux = le.readLine().replace(";", " ; ").split(";");
                //ADICIONA CONTADOR DE LINHA
                qtdLinha++;
                //VERIFICA QUANTIDADE MAXIMA DE LINHAS PERMITIDAS
                if (qtdLinha > MAX_ALLOWED) {
                    return "Quantidade maxima de importacao de " + MAX_ALLOWED + " objetos por importacao!";
                } else if (aux != null && aux.length == 39) {
                    ArquivoImportacao ai = new ArquivoImportacao();
                    ai.setIdCliente(idCliente);
                    ai.setIdDepartamento(idCliente);
                    ai.setDepartamento(departamento);
                    ai.setContrato(contrato);
                    ai.setCartaoPostagem(cartaoPostagem);
                    ai.setMetodoInsercao("IMPORTACAO_WEBVENDAS");
                    ai.setCodECT(0);

                    ai.setNrLinha(qtdLinha + "");
                    ai.setNrObjeto("avista");
                    ai.setNome(aux[11].trim());
                    ai.setEmpresa("");
                    ai.setCpf(aux[10].trim());
                    ai.setCep(aux[18].trim());
                    ai.setEndereco(aux[12].trim());
                    ai.setNumero(aux[13].trim());
                    ai.setComplemento(aux[14].trim());
                    ai.setBairro(aux[15].trim());
                    ai.setCidade(aux[16].trim());
                    ai.setUf(aux[17].trim());
                    ai.setEmail(aux[22].trim());
                    ai.setCelular(aux[20].trim());
                    ai.setAosCuidados("");
                    ai.setNotaFiscal(aux[0].trim());
                    ai.setServico("");
                    ai.setObs(aux[26].trim());
                    ai.setConteudo("Frete cobrado Cliente: " + aux[4].trim());//aux[26].trim()
                    ai.setChave(aux[0].trim());

                    ai.setPeso("0");
                    ai.setAltura("0");
                    ai.setLargura("0");
                    ai.setComprimento("0");
                    ai.setAr("0");
                    ai.setMp("0");

                    float vd = FormatarDecimal.floatParser(aux[29].replace("R$ ", "").trim());
                    if (vd > 12) {
                        ai.setVd(vd + "");
                    } else {
                        ai.setVd("12");
                    }

                    listaAi.add(ai);

                }
            }
            le.close();

            if (listaAi.size() > 0) {
                //valida os dados do arquivo para efetuar a importacao
                listaAi = validaDadosArquivo(listaAi, idCliente, servicoEscolhido, nomeBD);
                if (!FALHA.equals("")) {
                    //retorna mensagem de FALHA
                    return FALHA;
                } else {
                    //MONTA SQL
                    String sql = montaSqlPedido(listaAi, nomeBD);
                    boolean flag = inserir(sql, nomeBD);
                    if (flag) {
                        return "Pedidos Importados Com Sucesso!<br/>" + AVISO;
                    } else {
                        return "Falha ao importar Pedidos!";
                    }
                }
            } else {
                return "Nenhum pedido no arquivo para importar!";
            }

        } catch (IOException e) {
            return "Não foi possivel ler o arquivo: " + e;
        } catch (Exception e) {
            return "Falha na importacao dos pedidos: " + e;
        }

    }

    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static String importaPedidoLINX(FileItem item, int idCliente, String departamento, String servico, int temVD, int temAR, String nomeBD) {
        String sql = "INSERT INTO pre_venda (numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, registro, chave, metodo_insercao) VALUES ";

        String buffer = "";
        try {
            Scanner in = new Scanner(item.getInputStream());
            int qtdLinha = 0;
            while (in.hasNextLine()) {
                qtdLinha++;
                in.nextLine();
            }
            if (qtdLinha > MAX_ALLOWED) {
                return "Quantidade maxima de importacao de 200 objetos por importacao!";
            }

            String condicao = "";
            //BufferedReader le = new BufferedReader(new FileReader(caminho));
            BufferedReader le = new BufferedReader(new InputStreamReader(item.getInputStream(), "ISO-8859-1"));

            Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
            String contrato = "";
            String cartaoPostagem = "";
            int idDepartamento = 0;

            if (!departamento.equals("")) {
                String aux[] = departamento.split(";");
                idDepartamento = Integer.parseInt(aux[0]);
                departamento = aux[1];
                cartaoPostagem = aux[2];
            }

            if (cli != null && cli.getTemContrato() == 1) {
                contrato = cli.getNumContrato();
                if (cartaoPostagem.equals("0") || cartaoPostagem.equals("")) {
                    cartaoPostagem = cli.getCartaoPostagem();
                }
            }

            while (le.ready()) {

                if (condicao.equals("")) {
                    buffer = le.readLine(); // LE A LINHA DO ARQUIVO                    
                }
                buffer = le.readLine(); // LE A LINHA DO ARQUIVO
                String[] aux = buffer.replace(";", " ; ").split(";"); //DIVIDE A LINHA POR PONTO E VIRGULA

                if (aux == null || aux.length == 0) {
                    condicao = "";
                } else {
                    condicao = "Pedidos";
                }

                /**
                 * ******************************************************************
                 */
                if (condicao.contains("Pedidos") && aux.length >= 29) {
                    sql += montaSqlPedidoLINX(aux, nomeBD, cartaoPostagem, contrato, idCliente, idDepartamento, departamento, servico, temVD, temAR);
                }
                /**
                 * ******************************************************************
                 */
            }
            le.close();

            sql = sql.substring(0, sql.lastIndexOf(","));
            boolean flag = inserir(sql, nomeBD);
            //System.out.println(sql);
            if (flag && TEM_PEDIDO) {
                return "Pedidos Importados Com Sucesso!<br/>" + AVISO;
            } else if (!TEM_PEDIDO) {
                return "Nenhum Pedido Novo Para Importar!";
            } else if (!FALHA.equals("")) {
                return FALHA;
            } else {
                return "Falha ao importar Pedidos!";
            }

        } catch (IOException e) {
            return "Não foi possivel ler o arquivo: " + e;
        } catch (Exception e) {
            return "Falha na importacao dos pedidos: " + e;
        }

    }

    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static String importaPedidoTRAY(FileItem item, int idCliente, String departamento, String servico, int temVD, int temAR, String nomeBD) {
        String sql = "INSERT INTO pre_venda (numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, registro, chave, metodo_insercao) VALUES ";

        String buffer = "";
        try {
            int qtdLinha = 0;

            String condicao = "";
            //BufferedReader le = new BufferedReader(new FileReader(caminho));
            BufferedReader le = new BufferedReader(new InputStreamReader(item.getInputStream(), "ISO-8859-1"));

            Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
            String contrato = "";
            String cartaoPostagem = "";
            int idDepartamento = 0;

            if (!departamento.equals("")) {
                String aux[] = departamento.split(";");
                idDepartamento = Integer.parseInt(aux[0]);
                departamento = aux[1];
                cartaoPostagem = aux[2];
            }

            if (cli != null && cli.getTemContrato() == 1) {
                contrato = cli.getNumContrato();
                if (cartaoPostagem.equals("0") || cartaoPostagem.equals("")) {
                    cartaoPostagem = cli.getCartaoPostagem();
                }
            }

            while (le.ready()) {

                buffer = le.readLine(); // LE A LINHA DO ARQUIVO
                //System.out.println(">>> " + buffer.toString());
                String[] aux = buffer.split(";"); //DIVIDE A LINHA POR PONTO E VIRGULA
                //System.out.println("TAMANHO " + aux.length);

                if (aux == null || aux.length == 0) {
                    condicao = "";
                    //System.out.println("entro 1 - "+aux.length +" - "+buffer);
                } else if (aux[0].equals("Pedidos")) {
                    condicao = "Pedidos";
                    buffer = le.readLine(); // LE A LINHA DO ARQUIVO
                    buffer = le.readLine(); // LE A LINHA DO ARQUIVO
                    aux = buffer.split(";"); //DIVIDE A LINHA POR PONTO E VIRGULA                      
                    //System.out.println("entro 2 - "+aux.length +" - "+buffer);
                } else if (condicao.equals("Pedidos") && aux.length > 0) {
                    condicao = "Pedidos";
                    //System.out.println("entro 3 - "+aux.length +" - "+buffer);
                } else {
                    condicao = "";
                    //System.out.println("entro 4 - "+aux.length +" - "+buffer);
                }
                //System.out.println(condicao);

                if (aux.length != 42 && condicao.equals("Pedidos")) {
                    buffer += le.readLine();
                    aux = buffer.split(";");
                }
                /**
                 * ******************************************************************
                 */
                if (condicao.contains("Pedidos") && aux.length == 42) {
                    String vsql = montaSqlPedidoTRAY(aux, nomeBD, cartaoPostagem, contrato, idCliente, idDepartamento, departamento, servico, temVD, temAR);
                    sql += vsql;
                    if (!vsql.equals("")) {
                        qtdLinha++;
                    }
                }
                /**
                 * ******************************************************************
                 */
            }
            le.close();

            if (qtdLinha > 205) {
                return "Quantidade maxima de importacao de 200 objetos por importacao!";
            }

            sql = sql.substring(0, sql.lastIndexOf(","));
            boolean flag = inserir(sql, nomeBD);
            //System.out.println(sql);
            if (flag && TEM_PEDIDO) {
                return "Pedidos Importados Com Sucesso!<br/>" + AVISO;
            } else if (!TEM_PEDIDO) {
                return "Nenhum Pedido Novo Para Importar!";
            } else if (!FALHA.equals("")) {
                return FALHA;
            } else {
                return "Falha ao importar Pedidos!";
            }

        } catch (IOException e) {
            return "Não foi possivel ler o arquivo: " + e;
        } catch (Exception e) {
            return "Falha na importacao dos pedidos: " + e;
        }

    }

    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static String importaPedidoINTERLOGIC(FileItem item, int idCliente, String departamento, String servico, String nomeBD) {
        String sql = "INSERT INTO pre_venda (numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, registro, metodo_insercao) VALUES ";

        String buffer = "";
        try {

            String condicao = "";
            BufferedReader le = new BufferedReader(new InputStreamReader(item.getInputStream(), "ISO-8859-1"));

            Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
            String contrato = "";
            String cartaoPostagem = "";
            int idDepartamento = 0;

            if (!departamento.equals("")) {
                String aux[] = departamento.split(";");
                idDepartamento = Integer.parseInt(aux[0]);
                departamento = aux[1];
                cartaoPostagem = aux[2];
            }

            if (cli != null && cli.getTemContrato() == 1) {
                contrato = cli.getNumContrato();
                if (cartaoPostagem.equals("0") || cartaoPostagem.equals("")) {
                    cartaoPostagem = cli.getCartaoPostagem();
                }
            }

            while (le.ready()) {

                if (condicao.equals("")) {
                    buffer = le.readLine(); // LE A LINHA DO ARQUIVO                    
                }
                buffer = le.readLine(); // LE A LINHA DO ARQUIVO
                String[] aux = buffer.replace(";", " ; ").split(";"); //DIVIDE A LINHA POR PONTO E VIRGULA

                if (aux == null || aux.length == 0) {
                    condicao = "";
                } else {
                    condicao = "Pedidos";
                }

                /**
                 * ******************************************************************
                 */
                if (condicao.contains("Pedidos")) {
                    sql += montaSqlPedidoINTERLOGIC(aux, nomeBD, cartaoPostagem, contrato, idCliente, idDepartamento, departamento, servico);
                }
                /**
                 * ******************************************************************
                 */
            }
            le.close();

            sql = sql.substring(0, sql.lastIndexOf(","));
            boolean flag = inserir(sql, nomeBD);
            //System.out.println(sql);
            if (flag && TEM_PEDIDO) {
                return "Pedidos Importados Com Sucesso!<br/>" + AVISO;
            } else if (!TEM_PEDIDO) {
                return "Nenhum Pedido Novo Para Importar!";
            } else if (!FALHA.equals("")) {
                return FALHA;
            } else {
                return "Falha ao importar Pedidos!";
            }

        } catch (IOException e) {
            return "Não foi possivel ler o arquivo: " + e;
        } catch (Exception e) {
            return "Falha na importacao dos pedidos: " + e;
        }

    }

    public static String montaSqlPedidoLINX(String[] aux, String nomeBD, String cartaoPostagem, String contrato, int idCli, int idDepto, String depto, String serv, int temVD, int temAR) {
        String sql = "";
        FALHA = "";

        try {

            String servico = "";
            int registro = 0;
            if (serv.equals("ARQUIVO")) {
                if (aux[22].trim().toUpperCase().startsWith("PAC")) {
                    servico = "PAC";
                } else if (aux[22].trim().toUpperCase().startsWith("ESEDEX") || aux[12].trim().toUpperCase().startsWith("E-SEDEX")) {
                    servico = "ESEDEX";
                } else if (aux[22].trim().toUpperCase().startsWith("SEDEX")) {
                    servico = "SEDEX";
                } else if (aux[22].trim().toUpperCase().startsWith("CARTA")) {
                    servico = "CARTA";
                } else if (aux[22].trim().toUpperCase().startsWith("SIMPLES")) {
                    servico = "SIMPLES";
                } else if (aux[22].trim().toUpperCase().startsWith("PAX")) {
                    servico = "PAX";
                }
            } else {
                servico = serv;
            }

            String notaFiscal = aux[11].trim(); //idDoPedido
            boolean flag = true;//verificarExistencia(notaFiscal, idCli, nomeBD);

            if (!servico.equals("") && flag) {

                TEM_PEDIDO = true;
                int idRemetente = 1;
                int idCliente = idCli;
                int idDepartamento = idDepto;
                String departamento = depto;
                String nome = aux[2].trim();
                String empresa = "";
                String cpf = "";
                String cep = aux[6].trim().replace(".", "");
                String endereco = aux[7].trim();
                String numero = aux[8].trim();
                String complemento = aux[9].trim();
                String bairro = aux[10].trim();
                String cidade = aux[11].trim();
                String uf = aux[28].trim();
                String aosCuidados = "";
                String email = "";//aux[3].trim();
                String celular = "";

                int idDestinatario = ContrPreVendaDest.inserir(idCliente, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, email, celular, "Brasil", nomeBD);

                String obs = "";//aux[15].trim();
                String tipo = "SERVICO";
                String conteudo = "";//aux[16].trim();

                String chave = "";
                if (aux.length >= 18 && aux[17] != null) {
                    chave = aux[17].trim();
                }

                int peso = 0;
                int altura = 0;
                int largura = 0;
                int comprimento = 0;
                float vlrCobrar = 0;

                int ar = temAR;
                int mp = 0;
                float vd = 0;
                if (temVD == 1) {
                    try {
                        String vvd = aux[21].trim().replaceAll(",", ".");
                        if (!vvd.equals("")) {
                            vd = Float.parseFloat(vvd);
                        }
                    } catch (NumberFormatException e) {
                        vd = 0;
                    }
                }

                //VERIFICAR SE CEP POSSUI ESEDEX CASO O SERVICO ESCOLHIDO SEJA ESEDEX.
                //SE NAO POSSUIR O ESEDEX TROCAR PARA SEDEX.
                cep = cep.replace("-", "").replace(".", "").trim();
                if (servico.equals("ESEDEX")) {
                    int cep2 = Integer.parseInt(cep);
                    if (!ContrServicoAbrangencia.verificaByCepServico(cep2, servico, nomeBD)) {
                        servico = "SEDEX";
                    }
                }

                Amarracao am = ContrAmarracao.consultaAmarracaoByCep(cep, servico, nomeBD);
                String siglaAmarracao = "- - -";
                if (am != null) {
                    siglaAmarracao = am.getSiglaAmarracao();
                }

                int codECT = ContrClienteContrato.consultaContratoClienteGrupoServ(idCliente, servico, nomeBD);
                int qtdEtq = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ(servico, 0, idCliente, nomeBD);

                String numObjeto = "avista";
                if (codECT != 0 && qtdEtq != 0) { // && !servico.equals("CARTA")
                    //numObjeto = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServ(idCliente, servico, nomeBD);
                    //ContrClienteEtiquetas.alteraUtilizadaEtiqueta(numObjeto, 1, nomeBD);
                } else if (codECT == 0) { // || servico.equals("CARTA")
                    if (servico.equals("ESEDEX")) {
                        servico = "SEDEX";
                    }
                    ServicoECT se = ContrServicoECT.consultaAvistaByGrupo(servico);
                    codECT = se.getCodECT();
                    contrato = "";
                    if (codECT == 10014 && servico.equals("CARTA")) {
                        registro = 1;
                    }
                }

                /**
                 * ************************************************************************
                 */
                sql += "\n('" + numObjeto + "', " + idCliente + ", " + idDestinatario + ", " + idRemetente + ", '" + codECT + "', '" + contrato + "', '" + departamento + "', '" + aosCuidados + "', '" + obs + "', '" + conteudo + "', " + peso + ", " + altura + ", " + largura + ", " + comprimento + ", " + vd + ", " + ar + ", " + mp + ", '" + siglaAmarracao + "', '" + servico + "', '" + notaFiscal + "', " + vlrCobrar + ", '" + tipo + "', " + idDepartamento + ", NOW(), '" + cartaoPostagem + "', " + registro + ", '" + chave + "', 'IMPORTACAO_CSV'),";

            }
        } catch (Exception e) {
            //System.out.println("FALHA AO MONTAR DADOS DO SQL = " + e);
            FALHA = "FALHA AO MONTAR DADOS DO SQL = " + e;
        }

        return sql;

    }

    public static String montaSqlPedidoTRAY(String[] aux, String nomeBD, String cartaoPostagem, String contrato, int idCli, int idDepto, String depto, String serv, int temVD, int temAR) {
        String sql = "";
        FALHA = "";

        try {

            String servico = "";
            int registro = 0;
            if (serv.equals("ARQUIVO")) {
                if (aux[6].toUpperCase().startsWith("ENCOMENDA PAC")) {
                    servico = "PAC";
                } else if (aux[6].toUpperCase().startsWith("ESEDEX") || aux[6].toUpperCase().startsWith("E-SEDEX")) {
                    servico = "ESEDEX";
                } else if (aux[6].toUpperCase().startsWith("SEDEX")) {
                    servico = "SEDEX";
                }
            } else {
                servico = serv;
            }

            String notaFiscal = aux[0]; //idDoPedido
            String chave = aux[0];
            boolean flag = verificarExistenciaChave(chave, idCli, nomeBD);

            if (!servico.equals("") && flag) {

                TEM_PEDIDO = true;
                int idRemetente = 1;
                int idCliente = idCli;
                int idDepartamento = idDepto;
                String departamento = depto;

                String nome = aux[21];
                String empresa = "";
                String cpf = aux[24]; //cpf
                if (!aux[29].trim().equals("")) {
                    cpf = aux[24];
                }
                String cep = aux[35];
                String endereco = aux[31];
                String numero = aux[32];
                String complemento = aux[36];
                String bairro = aux[37];
                String cidade = aux[33];
                String uf = aux[34];
                String aosCuidados = "";
                String email = "";
                String celular = "";

                int idDestinatario = ContrPreVendaDest.inserir(idCliente, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, email, celular, "Brasil", nomeBD);

                String obs = aux[9].toUpperCase();
                String tipo = "SERVICO";
                String conteudo = "";

                int peso = 0;
                int altura = 0;
                int largura = 0;
                int comprimento = 0;
                float vlrCobrar = 0;

                int ar = temAR;
                int mp = 0;
                float vd = 0;
                if (temVD == 1) {
                    try {
                        String vvd = aux[3].trim().replaceAll(",", ".");
                        if (!vvd.equals("")) {
                            vd = Float.parseFloat(vvd);
                        }
                    } catch (NumberFormatException e) {
                        vd = 0;
                    }
                }

                //VERIFICAR SE CEP POSSUI ESEDEX CASO O SERVICO ESCOLHIDO SEJA ESEDEX.
                //SE NAO POSSUIR O ESEDEX TROCAR PARA SEDEX.
                cep = cep.replace("-", "").replace(".", "").trim();
                if (servico.equals("ESEDEX")) {
                    int cep2 = Integer.parseInt(cep);
                    if (!ContrServicoAbrangencia.verificaByCepServico(cep2, servico, nomeBD)) {
                        servico = "SEDEX";
                    }
                }

                Amarracao am = ContrAmarracao.consultaAmarracaoByCep(cep, servico, nomeBD);
                String siglaAmarracao = "- - -";
                if (am != null) {
                    siglaAmarracao = am.getSiglaAmarracao();
                }

                int codECT = ContrClienteContrato.consultaContratoClienteGrupoServ(idCliente, servico, nomeBD);
                int qtdEtq = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ(servico, 0, idCliente, nomeBD);

                String numObjeto = "avista";
                if (codECT == 0 && qtdEtq != 0) {
                    if (servico.equals("ESEDEX")) {
                        servico = "SEDEX";
                    }
                    ServicoECT se = ContrServicoECT.consultaAvistaByGrupo(servico);
                    codECT = se.getCodECT();
                    contrato = "";
                    if (codECT == 10014 && servico.equals("CARTA")) {
                        registro = 1;
                    }
                }

                /**
                 * ************************************************************************
                 */
                sql += "\n('" + numObjeto + "', " + idCliente + ", " + idDestinatario + ", " + idRemetente + ", '" + codECT + "', '" + contrato + "', '" + departamento + "', '" + aosCuidados + "', '" + obs + "', '" + conteudo + "', " + peso + ", " + altura + ", " + largura + ", " + comprimento + ", " + vd + ", " + ar + ", " + mp + ", '" + siglaAmarracao + "', '" + servico + "', '" + notaFiscal + "', " + vlrCobrar + ", '" + tipo + "', " + idDepartamento + ", NOW(), '" + cartaoPostagem + "', " + registro + ", '" + chave + "', 'IMPORTACAO_CSV'),";

            }
        } catch (Exception e) {
            //System.out.println("FALHA AO MONTAR DADOS DO SQL = " + e);
            FALHA = "FALHA AO MONTAR DADOS DO SQL = " + e;
        }

        return sql;

    }

    public static String montaSqlPedidoINTERLOGIC(String[] aux, String nomeBD, String cartaoPostagem, String contrato, int idCli, int idDepto, String depto, String serv) {
        String sql = "";
        FALHA = "";

        try {

            String servico = "";
            int registro = 0;
            if (serv.equals("ARQUIVO")) {
                if (aux[25].trim().toUpperCase().startsWith("PAC")) {
                    servico = "PAC";
                } else if (aux[25].trim().toUpperCase().startsWith("ESEDEX") || aux[25].trim().toUpperCase().startsWith("E-SEDEX")) {
                    servico = "ESEDEX";
                } else if (aux[25].trim().toUpperCase().startsWith("SEDEX")) {
                    servico = "SEDEX";
                } else if (aux[25].trim().toUpperCase().startsWith("CARTA")) {
                    servico = "CARTA";
                } else if (aux[25].trim().toUpperCase().startsWith("SIMPLES")) {
                    servico = "SIMPLES";
                } else if (aux[25].trim().toUpperCase().startsWith("PAX")) {
                    servico = "PAX";
                }
            } else {
                servico = serv;
            }

            String notaFiscal = aux[1].trim(); //idDoPedido
            boolean flag = true;//verificarExistencia(notaFiscal, idCli, nomeBD);

            if (!servico.equals("") && flag) {

                TEM_PEDIDO = true;
                int idRemetente = 1;
                int idCliente = idCli;
                int idDepartamento = idDepto;
                String departamento = depto;
                String nome = aux[5].trim() + " " + aux[6].trim();
                String empresa = "";//aux[1].trim();
                String cpf = aux[2].trim(); //cpf
                String cep = aux[12].trim().replace(".", "");
                String endereco = aux[7].trim();
                String numero = aux[8].trim();
                String complemento = aux[9].trim();
                String bairro = aux[14].trim();
                String cidade = aux[10].trim();
                String uf = aux[11].trim();
                String aosCuidados = "";//aux[10].trim();
                String email = "";
                String celular = "";

                int idDestinatario = ContrPreVendaDest.inserir(idCliente, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, email, celular, "Brasil", nomeBD);

                String obs = "";//aux[15].trim();
                String tipo = "SERVICO";
                String conteudo = aux[23].trim();
                int peso = 0;//Integer.parseInt(request.getParameter("peso"));
                int altura = 0;//Integer.parseInt(request.getParameter("altura"));
                int largura = 0;//Integer.parseInt(request.getParameter("largura"));
                int comprimento = 0;//Integer.parseInt(request.getParameter("comprimento"));
                float vlrCobrar = 0;

                int ar = 0;
                /*if (aux[13].trim().toUpperCase().contains("AR")) {
                 ar = 1;
                 }*/
                int mp = 0;
                /*if (aux[13].trim().toUpperCase().contains("MP")) {
                 mp = 1;
                 }*/
                float vd = 0;
                /*if (aux[13].trim().toUpperCase().contains("VD")) {
                 vd = Float.parseFloat(aux[21].replace(",", ".").trim());
                 if(vd < 12){
                 vd = 12;
                 }
                 }*/

                //VERIFICAR SE CEP POSSUI ESEDEX CASO O SERVICO ESCOLHIDO SEJA ESEDEX.
                //SE NAO POSSUIR O ESEDEX TROCAR PARA SEDEX.
                cep = cep.replace("-", "").replace(".", "").trim();
                if (servico.equals("ESEDEX")) {
                    int cep2 = Integer.parseInt(cep);
                    if (!ContrServicoAbrangencia.verificaByCepServico(cep2, servico, nomeBD)) {
                        servico = "SEDEX";
                    }
                }

                Amarracao am = ContrAmarracao.consultaAmarracaoByCep(cep, servico, nomeBD);
                String siglaAmarracao = "- - -";
                if (am != null) {
                    siglaAmarracao = am.getSiglaAmarracao();
                }

                int codECT = ContrClienteContrato.consultaContratoClienteGrupoServ(idCliente, servico, nomeBD);
                int qtdEtq = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ(servico, 0, idCliente, nomeBD);

                String numObjeto = "avista";
                if (codECT != 0 && qtdEtq != 0) { // && !servico.equals("CARTA")
                    //numObjeto = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServ(idCliente, servico, nomeBD);
                    //ContrClienteEtiquetas.alteraUtilizadaEtiqueta(numObjeto, 1, nomeBD);
                } else if (codECT == 0) { // || servico.equals("CARTA")
                    if (servico.equals("ESEDEX")) {
                        servico = "SEDEX";
                    }
                    ServicoECT se = ContrServicoECT.consultaAvistaByGrupo(servico);
                    codECT = se.getCodECT();
                    contrato = "";
                    if (codECT == 10014 && servico.equals("CARTA")) {
                        registro = 1;
                    }
                }

                /**
                 * ************************************************************************
                 */
                sql += "\n('" + numObjeto + "', " + idCliente + ", " + idDestinatario + ", " + idRemetente + ", '" + codECT + "', '" + contrato + "', '" + departamento + "', '" + aosCuidados + "', '" + obs + "', '" + conteudo + "', " + peso + ", " + altura + ", " + largura + ", " + comprimento + ", " + vd + ", " + ar + ", " + mp + ", '" + siglaAmarracao + "', '" + servico + "', '" + notaFiscal + "', " + vlrCobrar + ", '" + tipo + "', " + idDepartamento + ", NOW(), '" + cartaoPostagem + "', " + registro + ", 'IMPORTACAO_INTERLOGIC'),";

            }
        } catch (Exception e) {
            //System.out.println("FALHA AO MONTAR DADOS DO SQL = " + e);
            FALHA = "FALHA AO MONTAR DADOS DO SQL = " + e;
        }

        return sql;

    }

    public static String concertaData(String data) {
        if (data != null && !data.contains("/") && data.length() == 8) {
            data = data.substring(0, 2) + "/" + data.substring(2, 4) + "/" + data.substring(4, 8);
        }
        return data;
    }

    public static boolean inserir(String sql, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        try {
            //System.out.println("SQL PEDIDO:\n" + sql);
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ContrPreVendaImporta.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean excluirNaoConfirmados(int idCli, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "DELETE FROM pre_venda WHERE idRemetente = 1 AND userPreVenda = 0 AND impresso = 0 AND idCliente = " + idCli + ";";
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            valores.executeUpdate();
            valores.close();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(ContrPreVendaImporta.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean verificarExistencia(String notaFiscal, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT id FROM pre_venda WHERE idCliente = " + idCliente + " AND notaFiscal = " + notaFiscal;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            boolean flag = true;
            if (result.next()) {
                flag = false;
            }
            valores.close();
            return flag;
        } catch (SQLException e) {
            Logger.getLogger(ContrPreVendaImporta.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    public static boolean verificarExistenciaChave(String chave, int idCliente, String nomeBD) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "SELECT id FROM pre_venda WHERE idCliente = " + idCliente + " AND chave = " + chave;
        try {
            PreparedStatement valores = conn.prepareStatement(sql);
            ResultSet result = (ResultSet) valores.executeQuery();
            boolean flag = true;
            if (result.next()) {
                flag = false;
            }
            valores.close();
            return flag;
        } catch (SQLException e) {
            Logger.getLogger(ContrPreVendaImporta.class.getName()).log(Level.WARNING, e.getMessage(), e);
            return false;
        } finally {
            Conexao.desconectar(conn);
        }
    }

    /**
     * **************************************************************************************
     */
    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static String importaPedidoEDI(FileItem item, int idCliente, String departamento, String servico, int temVD, int temAR, String nomeBD) {
        String sql = "INSERT INTO pre_venda (numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, registro, metodo_insercao) VALUES ";

        String buffer = "";
        try {

            BufferedReader le = new BufferedReader(new InputStreamReader(item.getInputStream(), "ISO-8859-1"));

            Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
            String contrato = "";
            String cartaoPostagem = "";
            int idDepartamento = 0;

            if (!departamento.equals("")) {
                String aux[] = departamento.split(";");
                idDepartamento = Integer.parseInt(aux[0]);
                departamento = aux[1];
                cartaoPostagem = aux[2];
            }

            if (cli != null && cli.getTemContrato() == 1) {
                contrato = cli.getNumContrato();
                if (cartaoPostagem.equals("0") || cartaoPostagem.equals("")) {
                    cartaoPostagem = cli.getCartaoPostagem();
                }
            }

            Destinatario rom = null;
            int auxCod = 0, cod = 0;
            String vvd = "0", notaFiscal = "";
            while (le.ready()) {

                buffer = le.readLine(); // LE A LINHA DO ARQUIVO
                cod = Integer.parseInt(buffer.substring(0, 3).trim());
                /**
                 * **********************************************************
                 */

                if (cod == 312) {
                    if (rom != null) {
                        sql += montaSqlPedidoEDI(rom, nomeBD, cartaoPostagem, contrato, idCliente, idDepartamento, departamento, servico, vvd, notaFiscal, temAR);
                    }

                    auxCod = 312;
                    rom = new Destinatario(0, 0, 0, "", "", "", "", "", "", "", "", "", "", "", "", "");
                    rom.setCep(buffer.substring(167, 176).trim());
                    rom.setNome(buffer.substring(3, 43).trim());

                    String e = buffer.substring(72, 112).trim();
                    String end = e.substring(0, e.lastIndexOf(" "));
                    String num = e.substring(e.lastIndexOf(" "));
                    rom.setEndereco(end);
                    rom.setNumero(num);

                    rom.setBairro(buffer.substring(112, 132).trim());
                    rom.setCidade(buffer.substring(132, 167).trim());
                    rom.setUf(buffer.substring(185, 194).trim());
                    rom.setIdCliente(idCliente);
                } else if (cod == 313 && (auxCod == 312 || auxCod == 319)) {
                    auxCod = 313;
                    notaFiscal = buffer.substring(32, 40).trim();
                    if (temVD == 1) {
                        vvd = buffer.substring(85, 100).trim();
                    }
                    //rom.setPeso(Float.parseFloat(buffer.substring(100, 107).trim()));
                    //rom.setPesoCubado(Float.parseFloat(buffer.substring(107, 112).trim()));
                } else if (cod == 319 && (auxCod == 312 || auxCod == 313)) {
                    auxCod = 319;
                    //rom.setNrAWB(buffer.substring(65, 105).trim());
                    //rom.setComprimento(floatParser(buffer.substring(44, 51).trim()));
                    //rom.setLargura(floatParser(buffer.substring(51, 58).trim()));
                    //rom.setAltura(floatParser(buffer.substring(58, 65).trim()));
                }

                /**
                 * **********************************************************
                 */
            }
            if (rom != null) {
                sql += montaSqlPedidoEDI(rom, nomeBD, cartaoPostagem, contrato, idCliente, idDepartamento, departamento, servico, vvd, notaFiscal, temAR);
            }
            le.close();

            sql = sql.substring(0, sql.lastIndexOf(","));
            boolean flag = inserir(sql, nomeBD);
            //System.out.println(sql);
            if (flag && TEM_PEDIDO) {
                return "Pedidos Importados Com Sucesso!<br/>" + AVISO;
            } else if (!TEM_PEDIDO) {
                return "Nenhum Pedido Novo Para Importar!";
            } else if (!FALHA.equals("")) {
                return FALHA;
            } else {
                return "Falha ao importar Pedidos!";
            }

        } catch (IOException e) {
            return "Não foi possivel ler o arquivo: " + e;
        } catch (Exception e) {
            return "Falha na importacao dos pedidos: " + e;
        }

    }

    public static String montaSqlPedidoEDI(Destinatario aux, String nomeBD, String cartaoPostagem, String contrato, int idCli, int idDepto, String depto, String serv, String vvd, String notaFiscal, int temAR) {
        String sql = "";
        FALHA = "";

        try {

            String servico = "";
            int registro = 0;
            if (!serv.equals("ARQUIVO")) {
                servico = serv;

                boolean flag = true;//verificarExistencia(notaFiscal, idCli, nomeBD);

                if (!servico.equals("") && flag) {

                    TEM_PEDIDO = true;
                    int idRemetente = 1;
                    int idCliente = idCli;
                    int idDepartamento = idDepto;
                    String departamento = depto;
                    String nome = aux.getNome().trim();
                    String empresa = "";//aux[1].trim();
                    String cpf = "";//aux[2].trim(); //cpf
                    String cep = aux.getCep().trim().replace(".", "");
                    String endereco = aux.getEndereco().trim();
                    String numero = aux.getNumero().trim();
                    String complemento = "";//aux[6].trim();
                    String bairro = aux.getBairro().trim();
                    String cidade = aux.getCidade().trim();
                    String uf = aux.getUf().trim();
                    String aosCuidados = "";//aux[10].trim();
                    String email = "";
                    String celular = "";

                    int idDestinatario = ContrPreVendaDest.inserir(idCliente, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, email, celular, "Brasil", nomeBD);

                    String obs = "";//aux[15].trim();
                    String tipo = "SERVICO";
                    String conteudo = "";//aux[16].trim();
                    int peso = 0;//Integer.parseInt(request.getParameter("peso"));
                    int altura = 0;//Integer.parseInt(request.getParameter("altura"));
                    int largura = 0;//Integer.parseInt(request.getParameter("largura"));
                    int comprimento = 0;//Integer.parseInt(request.getParameter("comprimento"));
                    float vlrCobrar = 0;

                    int ar = temAR;
                    /*if (aux[13].trim().toUpperCase().contains("AR")) {
                     ar = 1;
                     }*/
                    int mp = 0;
                    /*if (aux[13].trim().toUpperCase().contains("MP")) {
                     mp = 1;
                     }*/
                    float vd = 0;
                    /*if (aux[13].trim().toUpperCase().contains("VD")) {*/
                    vd = Float.parseFloat(vvd.replace(",", ".").trim());
                    vd = vd / 100;

                    if (vd < 12) {
                        vd = 12;
                    }
                    //}

                    //VERIFICAR SE CEP POSSUI ESEDEX CASO O SERVICO ESCOLHIDO SEJA ESEDEX.
                    //SE NAO POSSUIR O ESEDEX TROCAR PARA SEDEX.
                    cep = cep.replace("-", "").replace(".", "").trim();
                    if (servico.equals("ESEDEX")) {
                        int cep2 = Integer.parseInt(cep);
                        if (!ContrServicoAbrangencia.verificaByCepServico(cep2, servico, nomeBD)) {
                            servico = "SEDEX";
                        }
                    }

                    Amarracao am = ContrAmarracao.consultaAmarracaoByCep(cep, servico, nomeBD);
                    String siglaAmarracao = "- - -";
                    if (am != null) {
                        siglaAmarracao = am.getSiglaAmarracao();
                    }

                    int codECT = ContrClienteContrato.consultaContratoClienteGrupoServ(idCliente, servico, nomeBD);
                    int qtdEtq = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ(servico, 0, idCliente, nomeBD);

                    String numObjeto = "avista";
                    if (codECT != 0 && qtdEtq != 0) { // && !servico.equals("CARTA")
                        //numObjeto = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServ(idCliente, servico, nomeBD);
                        //ContrClienteEtiquetas.alteraUtilizadaEtiqueta(numObjeto, 1, nomeBD);
                    } else if (codECT == 0) { // || servico.equals("CARTA")
                        if (servico.equals("ESEDEX")) {
                            servico = "SEDEX";
                        }
                        ServicoECT se = ContrServicoECT.consultaAvistaByGrupo(servico);
                        codECT = se.getCodECT();
                        contrato = "";
                        if (codECT == 10014 && servico.equals("CARTA")) {
                            registro = 1;
                        }
                    }

                    /**
                     * ************************************************************************
                     */
                    sql += "\n('" + numObjeto + "', " + idCliente + ", " + idDestinatario + ", " + idRemetente + ", '" + codECT + "', '" + contrato + "', '" + departamento + "', '" + aosCuidados + "', '" + obs + "', '" + conteudo + "', " + peso + ", " + altura + ", " + largura + ", " + comprimento + ", " + vd + ", " + ar + ", " + mp + ", '" + siglaAmarracao + "', '" + servico + "', '" + notaFiscal + "', " + vlrCobrar + ", '" + tipo + "', " + idDepartamento + ", NOW(), '" + cartaoPostagem + "', " + registro + ", 'IMPORTACAO_LADOAVESSO'),";

                }

            } else {
                System.out.println("ESCOLHA UM SERVICO!!");
                FALHA = "ESCOLHA UM SERVICO!!";
            }
        } catch (Exception e) {
            System.out.println("FALHA AO MONTAR DADOS DO SQL = " + e);
            FALHA = "FALHA AO MONTAR DADOS DO SQL = " + e;
        }

        return sql;

    }

    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static String importaPedidoXML(FileItem item, int idCliente, String departamento, String serv, String nomeBD) {
        try {

            String xml = "";
            BufferedReader le = new BufferedReader(new InputStreamReader(item.getInputStream(), "ISO-8859-1"));
            while (le.ready()) {
                xml += le.readLine(); // LE A LINHA DO ARQUIVO     
            }
            le.close();

            Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
            String sql = "INSERT INTO pre_venda (numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, userPreVenda, chave, impresso, registro, metodo_insercao) VALUES ";

            if (xml != null && !xml.isEmpty()) {

                SAXReader reader = new SAXReader();
                StringReader sr = new StringReader(xml);
                Document doc = reader.read(sr);
                List<Node> eventos = (List<Node>) doc.selectNodes("//portalpostal/pre_postagem");

                if (eventos.size() > MAX_ALLOWED) {
                    return "Quantidade maxima de importacao de " + MAX_ALLOWED + " objetos por importacao!";
                }

                // processa eventos
                for (Node node : eventos) {
                    String nome = node.valueOf("nome");
                    String endereco = node.valueOf("endereco");
                    String bairro = node.valueOf("bairro");
                    String numero = node.valueOf("numero");
                    String cidade = node.valueOf("cidade");
                    String uf = node.valueOf("estado");
                    String complemento = node.valueOf("complemento");
                    String cep = node.valueOf("cep");
                    String notaFiscal = node.valueOf("nota_fiscal");
                    String chave = node.valueOf("chave");
                    String servico = node.valueOf("servico");
                    String servico_adicional = node.valueOf("servico_adicional");
                    String valor = node.valueOf("valor_declarado");
                    String depto = node.valueOf("centro_custo");
                    String aos_cuidados = node.valueOf("aos_cuidados");
                    String num_objeto = node.valueOf("num_objeto");
                    String peso = node.valueOf("peso");
                    String altura = node.valueOf("altura");
                    String largura = node.valueOf("largura");
                    String comprimento = node.valueOf("comprimento");
                    String celular = node.valueOf("celular");
                    String email = node.valueOf("email");

                    if (!serv.equals("ARQUIVO")) {
                        servico = serv;
                    }

                    String contrato = "";
                    String cartaoPostagem = "";
                    if (cli != null && cli.getTemContrato() == 1) {
                        contrato = cli.getNumContrato();
                        if (cartaoPostagem.equals("0") || cartaoPostagem.equals("")) {
                            cartaoPostagem = cli.getCartaoPostagem();
                        }
                    }

                    int idDepartamento = 0;
                    if (!departamento.equals("")) {
                        String aux[] = departamento.split(";");
                        idDepartamento = Integer.parseInt(aux[0]);
                        departamento = aux[1];
                        cartaoPostagem = aux[2];
                    } else if (!depto.equals("")) {
                        idDepartamento = Integer.parseInt(depto);
                        ClientesDeptos cd = ContrClienteDeptos.consultaDeptoById(cli.getCodigo(), idDepartamento, nomeBD);
                        if (cd != null) {
                            departamento = cd.getNomeDepartamento();
                            if (cd.getCartaoPostagem() != null && !cd.getCartaoPostagem().equals("") && !cd.getCartaoPostagem().equals("null")) {
                                cartaoPostagem = cd.getCartaoPostagem(); //pegar cartao de postagem
                            }
                        }
                    }

                    /**
                     * ******************************************************************
                     */
                    String aux = montaSqlPedidoXML(nome, endereco, cep, numero, complemento, bairro, cidade, uf, aos_cuidados, notaFiscal, nomeBD, cartaoPostagem, contrato, cli.getCodigo(), idDepartamento, departamento, servico, servico_adicional, valor, 0, true, num_objeto, peso, altura, largura, comprimento, chave, email, celular);
                    if (!aux.startsWith("ERRO")) {
                        sql += aux;
                    }
                    /**
                     * ******************************************************************
                     */
                }

                sql = sql.substring(0, sql.lastIndexOf(","));
                boolean flag = inserir(sql, nomeBD);
                if (flag) {
                    return "Pedidos Importados Com Sucesso";
                } else {
                    return "Falha ao importar pedidos ou Nenhum pedido para importar";
                }
            } else {
                return "xml vazio ou nulo";
            }

        } catch (IOException e) {
            return "Não foi possivel ler o arquivo: " + e;
        } catch (Exception e) {
            return "Falha na importacao dos pedidos: " + e;
        }

    }

    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static String importaPedidoPLP(FileItem item, int idCliente, int idDepartamento, String departamento, String contrato, String cartaoPostagem, String servicoEscolhido, String nomeBD) {
        try {

            String xml = "";
            BufferedReader le = new BufferedReader(new InputStreamReader(item.getInputStream(), "ISO-8859-1"));
            while (le.ready()) {
                xml += le.readLine(); // LE A LINHA DO ARQUIVO     
            }
            le.close();
            //String sql = "INSERT INTO pre_venda (numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, registro, chave, metodo_insercao) VALUES ";
            //String sql = "INSERT INTO pre_venda (numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, userPreVenda, chave, impresso, registro, metodo_insercao) VALUES ";

            ArrayList<ArquivoImportacao> listaAi = new ArrayList<ArquivoImportacao>();
            if (!xml.isEmpty()) {

                SAXReader reader = new SAXReader();
                StringReader sr = new StringReader(xml);
                Document doc = reader.read(sr);
                List<Node> eventos = (List<Node>) doc.selectNodes("//correioslog/objeto_postal");
                if (eventos.size() > MAX_ALLOWED) {
                    return "Quantidade maxima de importacao de " + MAX_ALLOWED + " objetos por importacao!";
                }

                // processa eventos
                for (Node node : eventos) {

                    ArquivoImportacao ai = new ArquivoImportacao();

                    ai.setNrLinha(node.valueOf("numero_etiqueta").trim());
                    ai.setIdCliente(idCliente);
                    ai.setIdDepartamento(idDepartamento);
                    ai.setDepartamento(departamento);
                    ai.setContrato(contrato);
                    ai.setCartaoPostagem(cartaoPostagem);
                    ai.setMetodoInsercao("IMPORTACAO_PLP");
                    ai.setCodECT(0);

                    ai.setNrObjeto(node.valueOf("numero_etiqueta").trim());
                    ai.setChave(node.valueOf("numero_etiqueta").trim());
                    ai.setNome(node.valueOf("destinatario/nome_destinatario"));
                    ai.setEmpresa("");
                    ai.setCpf("");
                    ai.setCep(node.valueOf("nacional/cep_destinatario"));
                    ai.setEndereco(node.valueOf("destinatario/logradouro_destinatario"));
                    ai.setNumero(node.valueOf("destinatario/numero_end_destinatario"));
                    ai.setComplemento(node.valueOf("destinatario/complemento_destinatario"));
                    ai.setBairro(node.valueOf("nacional/bairro_destinatario"));
                    ai.setCidade(node.valueOf("nacional/cidade_destinatario"));
                    ai.setUf(node.valueOf("nacional/uf_destinatario"));
                    ai.setEmail(node.valueOf("destinatario/email_destinatario"));
                    ai.setCelular(node.valueOf("destinatario/celular_destinatario"));
                    if (ai.getCelular() == null || ai.getCelular().trim().equals("")) {
                        ai.setCelular(node.valueOf("destinatario/telefone_destinatario"));
                    }
                    ai.setNotaFiscal(node.valueOf("nacional/numero_nota_fiscal"));

                    ai.setAosCuidados("");
                    ai.setObs("");
                    ai.setConteudo("");
                    ai.setPeso("0");//node.valueOf("objeto_postal/peso");
                    ai.setAltura("0");//node.valueOf("altura");                        
                    ai.setLargura("0");//node.valueOf("largura");
                    ai.setComprimento("0");//node.valueOf("comprimento");

                    String servico = node.valueOf("codigo_servico_postagem").trim();
                    if (!servicoEscolhido.equals("ARQUIVO")) {
                        servico = servicoEscolhido;
                    } else if (servico.equals("00654911") || servico.equals("81019") || servico.equals("81833")) {
                        servico = "ESEDEX";
                    } else if (servico.equals("00654913") || servico.equals("40010") || servico.equals("40096") || servico.equals("40436") || servico.equals("40444") || servico.equals("40568")) {
                        servico = "SEDEX";
                    } else if (servico.equals("00654912") || servico.equals("41068") || servico.equals("41106") || servico.equals("41211")) {
                        servico = "PAC";
                    } else if (servico.equals("41300")) {
                        servico = "PAX";
                    } else if (servico.equals("40886") || servico.equals("40215")) {
                        servico = "SEDEX10";
                    } else if (servico.equals("40169")) {
                        servico = "SEDEX12";
                    } else if (servico.equals("10138") || servico.equals("10707") || servico.equals("10014")) {
                        servico = "CARTA";
                    }
                    ai.setServico(servico);

                    List<Node> evtAdicionais = (List<Node>) node.selectNodes("servico_adicional");
                    ai.setAr("0");
                    ai.setMp("0");
                    ai.setVd("0");
                    for (Node nda : evtAdicionais) {
                        int codAd = 0;
                        try {
                            codAd = Integer.parseInt(nda.valueOf("codigo_servico_adicional").trim());
                        } catch (NumberFormatException e) {
                        }
                        if (codAd == 1) {
                            ai.setAr("1");
                        } else if (codAd == 2) {
                            ai.setMp("1");
                        } else if (codAd == 19) {
                            String vd = nda.valueOf("valor_declarado").trim().replace(",", ".");
                            ai.setVd(vd);
                        }
                    }

                    listaAi.add(ai);
                }
            } else {
                return "Não foi encontrado nenhum objeto na PLP!";
            }

            if (listaAi.size() > 0) {
                //valida os dados do arquivo para efetuar a importacao
                listaAi = validaDadosArquivo(listaAi, idCliente, servicoEscolhido, nomeBD);
                if (!FALHA.equals("")) {
                    //retorna mensagem de FALHA
                    return FALHA;
                } else {
                    //MONTA SQL
                    String sql = montaSqlPedido(listaAi, nomeBD);
                    boolean flag = inserir(sql, nomeBD);
                    if (flag) {
                        return "Pedidos Importados Com Sucesso!<br/>" + AVISO;
                    } else {
                        return "Falha ao importar Pedidos!";
                    }
                }
            } else {
                return "Nenhum pedido no arquivo para importar!";
            }

        } catch (IOException e) {
            return "Não foi possivel ler a PLP: " + e;
        } catch (DocumentException e) {
            return "Falha na importacao da PLP: " + e;
        }

    }

    public static String montaSqlPedidoXML(String nome, String endereco, String cep, String numero, String complemento, String bairro, String cidade, String uf, String aosCuidados, String notaFiscal, String nomeBD, String cartaoPostagem, String contrato, int idCli, int idDepto, String depto, String serv, String serv_ad, String valor_declarado, int idUser, boolean postagemCompleta, String numObj, String pesoxml, String alt, String larg, String comp, String chave, String email, String celular) {
        String sql = "";

        try {

            String servico = "";
            int registro = 0;
            if (serv.trim().toUpperCase().startsWith("PAC")) {
                servico = "PAC";
            } else if (serv.trim().toUpperCase().replace("-", "").replace(" ", "").startsWith("ESEDEX")) {
                servico = "ESEDEX";
            } else if (serv.trim().toUpperCase().startsWith("SEDEX10")) {
                servico = "SEDEX10";
            } else if (serv.trim().toUpperCase().startsWith("SEDEX12")) {
                servico = "SEDEX12";
            } else if (serv.trim().toUpperCase().startsWith("CARTA")) {
                servico = "CARTA";
            } else if (serv.trim().toUpperCase().startsWith("SIMPLES")) {
                servico = "SIMPLES";
            } else if (serv.trim().toUpperCase().startsWith("PAX")) {
                servico = "PAX";
            } else if (serv.trim().toUpperCase().startsWith("SEDEX")) {
                servico = "SEDEX";
            }

            if (!servico.equals("")) {
                String empresa = "";
                String cpf = "";
                String obs = "";
                String tipo = "SERVICO";
                String conteudo = "";
                float vlrCobrar = 0;

                int idRemetente = 1;
                int idCliente = idCli;
                int idDepartamento = idDepto;
                String departamento = depto;

                int idDestinatario = ContrPreVendaDest.inserir(idCliente, nome, cpf, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, email, celular, "Brasil", nomeBD);

                String numObjeto = "avista";
                int peso = 0;
                int altura = 0;
                int largura = 0;
                int comprimento = 0;
                int impresso = 0;
                if (CalculoEtiqueta.validaNumObjeto(numObj)) {
                    numObjeto = numObj;
                    impresso = 1;
                    peso = (int) Float.parseFloat(pesoxml.replace(",", "."));     //gr
                    altura = (int) Float.parseFloat(alt.replace(",", "."));       //cm
                    largura = (int) Float.parseFloat(larg.replace(",", "."));     //cm
                    comprimento = (int) Float.parseFloat(comp.replace(",", ".")); //cm
                }

                int ar = 0;
                if (serv_ad.trim().toUpperCase().contains("AR")) {
                    ar = 1;
                }
                int mp = 0;
                if (serv_ad.trim().toUpperCase().contains("MP")) {
                    mp = 1;
                }
                float vd = 0;
                if (serv_ad.trim().toUpperCase().contains("VD")) {
                    vd = Float.parseFloat(valor_declarado.replace(",", ".").trim());
                    if (vd < 12) {
                        vd = 12;
                    }
                }

                cep = cep.replace("-", "").replace(".", "").trim();
                //VERIFICAR SE CEP POSSUI ESEDEX CASO O SERVICO ESCOLHIDO SEJA ESEDEX.
                //SE NAO POSSUIR O ESEDEX TROCAR PARA SEDEX.
                if (servico.equals("ESEDEX")) {
                    int cep2 = Integer.parseInt(cep);
                    if (!ContrServicoAbrangencia.verificaByCepServico(cep2, servico, nomeBD)) {
                        servico = "SEDEX";
                    }
                }

                Amarracao am = ContrAmarracao.consultaAmarracaoByCep(cep, servico, nomeBD);
                String siglaAmarracao = "- - -";
                if (am != null) {
                    siglaAmarracao = am.getSiglaAmarracao();
                }

                int codECT = ContrClienteContrato.consultaContratoClienteGrupoServ(idCliente, servico, nomeBD);
                int qtdEtq = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ(servico, 0, idCliente, nomeBD);

                if (codECT != 0 && qtdEtq != 0 && !servico.equals("CARTA") && !postagemCompleta) {
                    //numObjeto = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServ(idCliente, servico, nomeBD);
                    //ContrClienteEtiquetas.alteraUtilizadaEtiqueta(numObjeto, 1, nomeBD);
                } else if (codECT == 0 || servico.equals("CARTA")) {
                    ServicoECT se = ContrServicoECT.consultaAvistaByGrupo(servico);
                    codECT = se.getCodECT();
                    contrato = "";
                    if (codECT == 10014 && servico.equals("CARTA")) {
                        registro = 1;
                    }
                }

                /**
                 * ************************************************************************
                 */
                sql += "\n('" + numObjeto + "', " + idCliente + ", " + idDestinatario + ", " + idRemetente + ", '" + codECT + "', '" + contrato + "', '" + departamento + "', '" + aosCuidados + "', '" + obs + "', '" + conteudo + "', " + peso + ", " + altura + ", " + largura + ", " + comprimento + ", " + vd + ", " + ar + ", " + mp + ", '" + siglaAmarracao + "', '" + servico + "', '" + notaFiscal + "', " + vlrCobrar + ", '" + tipo + "', " + idDepartamento + ", NOW(), '" + cartaoPostagem + "', " + idUser + ", '" + chave + "', " + impresso + ", " + registro + ", 'IMPORTACAO_XML_PLP'),";

            } else {
                //System.out.println("FALHA AO MONTAR DADOS DO SQL = " + servico);
            }
        } catch (Exception e) {
            //System.out.println("FALHA AO MONTAR DADOS DO SQL = " + e);
            return "ERRO: Objeto: " + numObj + ", nota_fiscal: " + notaFiscal + " - Falha: " + e;
        }

        return sql;

    }

    /**
     * ***************************************************************************
     */
    public static String importaPedidoNFe(ArrayList<FileItem> listaFiles, int idCliente, String departamento, String servico, int vd, int ar, String nomeBD) {
        String sql = "INSERT INTO pre_venda (numObjeto, idCliente, idDestinatario, idRemetente, codECT, contrato, departamento, aos_cuidados, observacoes, conteudo, peso, altura, largura, comprimento, valor_declarado, aviso_recebimento, mao_propria, siglaAmarracao, nomeServico, notaFiscal, valor_cobrar, tipoEncomenda, idDepartamento, dataPreVenda, cartaoPostagem, registro, metodo_insercao) VALUES ";

        try {

            Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
            String contrato = "";
            String cartaoPostagem = "";
            int idDepartamento = 0;

            if (!departamento.equals("")) {
                String aux[] = departamento.split(";");
                idDepartamento = Integer.parseInt(aux[0]);
                departamento = aux[1];
                cartaoPostagem = aux[2];
            }

            if (cli != null && cli.getTemContrato() == 1) {
                contrato = cli.getNumContrato();
                if (cartaoPostagem.equals("0") || cartaoPostagem.equals("")) {
                    cartaoPostagem = cli.getCartaoPostagem();
                }
            }

            /**
             * ******************************************************************
             */
            for (FileItem item : listaFiles) {
                sql += lerNotaFiscal(item, idCliente, idDepartamento, departamento, servico, cartaoPostagem, contrato, vd, ar, nomeBD);

            }
            /**
             * ******************************************************************
             */

            sql = sql.substring(0, sql.lastIndexOf(","));
            boolean flag = inserir(sql, nomeBD);
            if (flag && TEM_PEDIDO) {
                return "Pedidos Importados Com Sucesso!<br/>" + AVISO;
            } else if (!TEM_PEDIDO) {
                return "ERRO: Nenhuma NF-e Para Importar!";
            } else if (!FALHA.equals("")) {
                return "ERRO: " + FALHA;
            } else {
                return "Falha ao importar Pedidos!";
            }

        } catch (IOException e) {
            return "ERRO: Não foi possivel ler o arquivo:\nIoException " + e;
        } catch (Exception e) {
            return "ERRO: Falha na importacao dos pedidos:\nException: " + e;
        }

    }

    public static String lerNotaFiscal(FileItem item, int idCli, int idDepto, String depto, String serv, String cartaoPostagem, String contrato, int temvd, int temar, String nomeBD) throws FileNotFoundException, UnsupportedEncodingException, IOException, DocumentException {

        SAXReader reader = new SAXReader();
        Document doc = reader.read(item.getInputStream());
        //System.out.println("path - " + path);

        //tipo Versão 2.0
        Map uris = new HashMap();
        uris.put("nfe", "http://www.portalfiscal.inf.br/nfe");

        //String basePath = "//nfe:nfeProc/nfe:NFe/nfe:infNFe/";
        String basePath = "//nfe:NFe/nfe:infNFe/";

        XPath xPath = doc.createXPath(basePath + "nfe:ide/nfe:nNF");
        xPath.setNamespaceURIs(uris);
        String nNF = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));
        //System.out.println(nNF);

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:CNPJ");
        xPath.setNamespaceURIs(uris);
        Element ecnpj = (Element) xPath.selectSingleNode(doc.getRootElement());
        if (ecnpj == null) {
            xPath = doc.createXPath(basePath + "nfe:dest/nfe:CPF");
            xPath.setNamespaceURIs(uris);
            ecnpj = (Element) xPath.selectSingleNode(doc.getRootElement());
        }
        String cnpj = elementToText(ecnpj);
        //System.out.println(cnpj);

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:xNome");
        xPath.setNamespaceURIs(uris);
        Element nome = (Element) xPath.selectSingleNode(doc.getRootElement());

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:email");
        xPath.setNamespaceURIs(uris);
        String email = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));
        //System.out.println(email);

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:fone");
        xPath.setNamespaceURIs(uris);
        String fone = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));
        //System.out.println(fone);

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:xLgr");
        xPath.setNamespaceURIs(uris);
        String lgr = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));
        //System.out.println(lgr);

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:nro");
        xPath.setNamespaceURIs(uris);
        String nro = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));
        //System.out.println(nro);

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:xCpl");
        xPath.setNamespaceURIs(uris);
        String cpl = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));
        //System.out.println(cpl);

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:xBairro");
        xPath.setNamespaceURIs(uris);
        String bairro = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));
        //System.out.println(bairro);

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:xMun");
        xPath.setNamespaceURIs(uris);
        String municipio = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:UF");
        xPath.setNamespaceURIs(uris);
        String uf = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:CEP");
        xPath.setNamespaceURIs(uris);
        String cep = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));

        xPath = doc.createXPath(basePath + "nfe:total/nfe:ICMSTot/nfe:vNF");
        xPath.setNamespaceURIs(uris);
        String vNF = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));
        //System.out.println(vNF);
        xPath = doc.createXPath(basePath + "nfe:infAdic/nfe:infCpl");
        xPath.setNamespaceURIs(uris);
        String obs = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));
        if (obs.length() > 50) {
            obs = obs.substring(0, 49);
        }

        int qtt = 1;
        try {
            xPath = doc.createXPath(basePath + "nfe:transp/nfe:vol/nfe:qVol");
            xPath.setNamespaceURIs(uris);
            String qtd = elementToText((Element) xPath.selectSingleNode(doc.getRootElement()));
            qtt = Integer.parseInt(qtd);
            if (qtt <= 0) {
                qtt = 1;
            }
        } catch (Exception e) {
        }

        String servico = "";
        int registro = 0;
        if (serv.trim().toUpperCase().startsWith("PAC")) {
            servico = "PAC";
        } else if (serv.trim().toUpperCase().replace("-", "").replace(" ", "").startsWith("ESEDEX")) {
            servico = "ESEDEX";
        } else if (serv.trim().toUpperCase().startsWith("SEDEX10")) {
            servico = "SEDEX10";
        } else if (serv.trim().toUpperCase().startsWith("SEDEX12")) {
            servico = "SEDEX12";
        } else if (serv.trim().toUpperCase().startsWith("SEDEX")) {
            servico = "SEDEX";
        } else if (serv.trim().toUpperCase().startsWith("CARTA")) {
            servico = "CARTA";
        } else if (serv.trim().toUpperCase().startsWith("SIMPLES")) {
            servico = "SIMPLES";
        } else if (serv.trim().toUpperCase().startsWith("PAX")) {
            servico = "PAX";
        }

        String sql = "";
        //System.out.println("servico _ " + servico);
        for (int i = 0; i < qtt; i++) {

            if (!servico.equals("")) {
                String aosCuidados = "";
                String tipo = "SERVICO";
                String conteudo = "";
                float vlrCobrar = 0;

                int idRemetente = 1;
                int idCliente = idCli;
                int idDepartamento = idDepto;
                String departamento = depto;
                String celular = fone;

                int idDestinatario = ContrPreVendaDest.inserir(idCliente, nome.getText(), cnpj, "", cep, lgr, nro, cpl, bairro, municipio, uf, email, celular, "Brasil", nomeBD);
                //System.out.println("id Destinatario" + idDestinatario);

                String numObjeto = "avista";
                int peso = 0;
                int altura = 0;
                int largura = 0;
                int comprimento = 0;

                int ar = temar;

                int mp = 0;

                float vd = 0;
                if (temvd == 1) {
                    vd = Float.parseFloat(vNF.replace(",", ".").trim());
                    if (vd < 12) {
                        vd = 12;
                    }
                }

                //VERIFICAR SE CEP POSSUI ESEDEX CASO O SERVICO ESCOLHIDO SEJA ESEDEX.
                //SE NAO POSSUIR O ESEDEX TROCAR PARA SEDEX.
                cep = cep.replace("-", "").replace(".", "").trim();
                if (servico.equals("ESEDEX")) {
                    int cep2 = Integer.parseInt(cep);
                    if (!ContrServicoAbrangencia.verificaByCepServico(cep2, servico, nomeBD)) {
                        servico = "SEDEX";
                    }
                }

                Amarracao am = ContrAmarracao.consultaAmarracaoByCep(cep, servico, nomeBD);
                String siglaAmarracao = "- - -";
                if (am != null) {
                    siglaAmarracao = am.getSiglaAmarracao();
                }

                int codECT = ContrClienteContrato.consultaContratoClienteGrupoServ(idCliente, servico, nomeBD);
                int qtdEtq = ContrClienteEtiquetas.contaQtdUtilizadaPorGrupoServ(servico, 0, idCliente, nomeBD);

                if (codECT != 0 && qtdEtq != 0 && !servico.equals("CARTA")) {
                    //numObjeto = ContrClienteEtiquetas.pegaEtiquetaNaoUtilizadaPorGrupoServ(idCliente, servico, nomeBD);
                    //ContrClienteEtiquetas.alteraUtilizadaEtiqueta(numObjeto, 1, nomeBD);
                } else if (codECT == 0 || servico.equals("CARTA")) {
                    ServicoECT se = ContrServicoECT.consultaAvistaByGrupo(servico);
                    codECT = se.getCodECT();
                    contrato = "";
                    if (codECT == 10014 && servico.equals("CARTA")) {
                        registro = 1;
                    }
                }

                /**
                 * ************************************************************************
                 */
                if (obs.length() > 50) {
                    obs = obs.substring(0, 49);
                }
                sql += "\n('" + numObjeto + "', " + idCliente + ", " + idDestinatario + ", " + idRemetente + ", '" + codECT + "', '" + contrato + "', '" + departamento + "', '" + aosCuidados + "', '" + obs + "', '" + conteudo + "', " + peso + ", " + altura + ", " + largura + ", " + comprimento + ", " + vd + ", " + ar + ", " + mp + ", '" + siglaAmarracao + "', '" + servico + "', '" + nNF + "', " + vlrCobrar + ", '" + tipo + "', " + idDepartamento + ", NOW(), '" + cartaoPostagem + "', " + registro + ", 'IMPORTACAO_NFE'),";
                TEM_PEDIDO = true;
            }
        }
        //System.out.println("sql lerNotaFiscal - " + sql);
        return sql;

    }

    public static String elementToText(Element elem) {
        if (elem == null) {
            return "";
        } else {
            return elem.getTextTrim();
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controle;
/*
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
*/
/**
 *
 * @author Fernando
 */
public class ContrImportacoesVisual {
    
    /*public static void main(String[] args) {
        ArrayList<String> lista = new ArrayList<String>();
        //lista.add("C:\\ARQUIVOS_VISUAL\\AVISTA\\R02616.htm");
        //lista.add("C:\\ARQUIVOS_VISUAL\\FATURAR\\E00597.htm");
        //lista.add("C:\\ARQUIVOS_VISUAL\\FATURAR\\E00476.htm");
        lista.add("C:\\ARQUIVOS_VISUAL\\FATURAR\\E00616.htm");
        importaMovimentoVisualHTML(lista, null);
    }
    
    public static String importaMovimentoVisualHTML(ArrayList<String> listaCaminhos, String nomeBD) {
        String sql = "REPLACE INTO movimentacao (id, dataPostagem, descServico, numObjeto, destinatario, notaFiscal, peso, cep, paisDestino, valorServico, valorDestino, quantidade, valorDeclarado, departamento, codCliente, conteudoObjeto, contratoEct, altura, largura, comprimento, siglaServAdicionais, codigoEct, idDepartamento) VALUES ";
        try {
            //********************************************************************
            for (String path : listaCaminhos) {
                File arquivo = new File(path);
                System.out.println("\n"+arquivo.getName()+"\n");
                String departamento = "";
                String servico = "";
                String contrato = "";
                String cartao = "";
                
                Document doc = Jsoup.parse(arquivo, "WINDOWS-1252");                
                Elements TRs = doc.getElementsByTag("tr");
                for (Element tr : TRs) {
                    Elements TDs = tr.getElementsByTag("td");
                    if(TDs.size() == 1 && !TDs.first().text().startsWith("DEMONSTRATIVO") && !TDs.first().text().startsWith("Obs")){
                        if(TDs.first().text().startsWith("CONTRATO")){     
                            String aux[] = TDs.first().text().split(":");
                            contrato = aux[1].replaceAll("\\D+","");
                            cartao = aux[2].trim();
                            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + contrato +" - "+ cartao);
                        }else if(tr.attr("bgcolor").equals("#FFCC99")){
                            servico = TDs.first().text();
                            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + servico);
                        }else{     
                            departamento = TDs.first().text().replace("Departamento: ", "");
                            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + departamento);
                        }
                    }else if(TDs.size() == 11 && !TDs.first().text().startsWith("Data") && !TDs.first().text().startsWith("Total")){
                        System.out.println("Data: "+TDs.get(0).text());
                        System.out.println("Dest: "+TDs.get(1).text());
                        System.out.println("CEP: "+TDs.get(2).text());
                        System.out.println("UF: "+TDs.get(3).text());
                        System.out.println("Adic: "+TDs.get(4).text());
                        System.out.println("Peso: "+TDs.get(5).text());
                        System.out.println("Cubagem: "+TDs.get(6).text());
                        System.out.println("Qtd/Reg.: "+TDs.get(7).text());
                        System.out.println("N.F.: "+TDs.get(8).text());
                        System.out.println("Decl.: "+TDs.get(9).text());
                        System.out.println("Valor: "+TDs.get(10).text());
                        System.out.println("---");
                        sql += ",(id, dataPostagem, descServico, numObjeto, destinatario, notaFiscal, peso, cep, paisDestino, valorServico, valorDestino, quantidade, valorDeclarado, departamento, codCliente, conteudoObjeto, contratoEct, altura, largura, comprimento, siglaServAdicionais, codigoEct, idDepartamento)";
                    }   else if(TDs.size() == 10 && !TDs.first().text().startsWith("Data") && !TDs.first().text().startsWith("Total")){
                        //ID = data_idCliente_porte_serv
                        System.out.println("Data: "+TDs.get(0).text());
                        System.out.println("Peso: "+TDs.get(4).text());
                        System.out.println("Qtd: "+TDs.get(6).text());
                        System.out.println("Decl.: "+TDs.get(8).text());
                        System.out.println("Valor: "+TDs.get(9).text());
                        System.out.println("---");
                        sql += ",(id, dataPostagem, descServico, numObjeto, destinatario, notaFiscal, peso, cep, paisDestino, valorServico, valorDestino, quantidade, valorDeclarado, departamento, codCliente, conteudoObjeto, contratoEct, altura, largura, comprimento, siglaServAdicionais, codigoEct, idDepartamento)";
                    }                  
                }
                //sql += "";
            }
            //******************************************************************

            //sql = sql.substring(0, sql.lastIndexOf(","));
            //boolean flag = inserir(sql, nomeBD);
            //System.out.println(sql);
            
            return "Falha ao importar Pedidos!";

        } catch (IOException e) {
            System.out.println(e);
            return "ERRO: Não foi possivel ler o arquivo:\nIoException " + e;
        } catch (Exception e) {
            System.out.println(e);
            return "ERRO: Falha na importacao dos pedidos:\nException: " + e;
        }

    }*/
    
}

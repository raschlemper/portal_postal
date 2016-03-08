/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Controle;

import Entidade.ArquivoImportacao;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author Ricardinho
 */
public class ContrDestinatarioImporta {
    
    private static String falha = "";
    
    public static ArrayList<ArquivoImportacao> validaCepArquivo(ArrayList<ArquivoImportacao> listaAi) {
        falha = "";

        for (ArquivoImportacao ai : listaAi) {
            try {
                //VALIDA O CEP
                int cepInteiro = 0;
                String cep = ai.getCep().replace(".", "").replace("-", "");
                if (cep.length() < 7 || cep.length() > 8) {
                    //CEP INVÁLIDO NA LINHA X
                    falha += "Linha n." + ai.getNrLinha() + " - CEP " + ai.getCep() + " invalido!";
                }
                try {
                    cepInteiro = Integer.parseInt(cep);
                } catch (NumberFormatException e) {
                    falha += "Linha n." + ai.getNrLinha() + " - CEP " + ai.getCep() + " invalido!";
                }
            } catch (Exception e) {
                System.out.println("FALHA AO MONTAR DADOS DO SQL = " + e);
                falha += "FALHA AO MONTAR DADOS DO SQL = " + e;
            }

        }

        return listaAi;
    }

    public static void insereDestinatarios(ArrayList<ArquivoImportacao> listaAi, String nomeBD) {
        for (ArquivoImportacao ai : listaAi) {
            //INSERE O DESTINATARIO
            int idDestinatario = ContrPreVendaDest.inserir(ai.getIdCliente(), ai.getNome(), ai.getCpf(), ai.getEmpresa(), ai.getCep(), ai.getEndereco(), ai.getNumero(), ai.getComplemento(), ai.getBairro(), ai.getCidade(), ai.getUf(), ai.getEmail(), ai.getCelular(), "Brasil", nomeBD);            
        }
    }
    //Importa arquivos tipo .TXT separados com campos com tamanhos determinados
    public static String importaPedido(FileItem item, int idCliente, String nomeBD) {

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
                if (aux != null && aux.length >= 10) {
                    
                    ArquivoImportacao ai = new ArquivoImportacao();
                    ai.setIdCliente(idCliente);
                    ai.setNrLinha(qtdLinha + "");
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
                    ai.setCelular("");
                    if (aux.length >= 12 && aux[11] != null) {
                        ai.setCelular(aux[10].trim() + aux[11].trim());
                    }
                    ai.setEmail("");
                    if (aux.length >= 13 && aux[12] != null) {
                        ai.setEmail(aux[12].trim());
                    }

                    listaAi.add(ai);

                }
            }
            le.close();

            if (listaAi.size() > 0) {
                //valida os dados do arquivo para efetuar a importacao
                listaAi = validaCepArquivo(listaAi);
                if (!falha.equals("")) {
                    //retorna mensagem de falha
                    return falha;
                } else {
                    //MONTA SQL
                    insereDestinatarios(listaAi, nomeBD);
                    return "Destinatarios Importados Com Sucesso!";
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
    
}

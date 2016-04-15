/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Controle;

import Controle.ContrErroLog;
import Entidade.ArquivoImportacao;
import Util.Conexao;
import Util.FormataString;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            //System.out.println("Entro " + ai.getNome());
            //INSERE O DESTINATARIO
            inserir(ai.getIdCliente(), ai.getNome(), ai.getCpf(), ai.getEmpresa(), ai.getCep(), ai.getEndereco(), ai.getNumero(), ai.getComplemento(), ai.getBairro(), ai.getCidade(), ai.getUf(), ai.getEmail(), ai.getCelular(), "Brasil", nomeBD, ai.getObs());            
        }
    }
    public static int inserir(int idCliente, String nome, String cpf_cnpj, String empresa, String cep, String endereco, String numero, String complemento, String bairro, String cidade, String uf, String email, String celular, String pais, String nomeBD, String tags) {
        Connection conn = Conexao.conectar(nomeBD);
        String sql = "INSERT INTO cliente_destinatario (idCliente, nome, cpf_cnpj, empresa, cep, endereco, numero, complemento, bairro, cidade, uf, email, celular, pais, tags) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        //System.out.println("inserir Destinatario -----------------\n"+sql+"\n---------------");
        
        try {
            PreparedStatement valores = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            valores.setInt(1, idCliente);
            valores.setString(2, FormataString.removeSpecialChars(nome));
            valores.setString(3, cpf_cnpj);
            valores.setString(4, empresa);
            valores.setString(5, cep);
            valores.setString(6, FormataString.removeSpecialChars(endereco));
            valores.setString(7, numero);
            valores.setString(8, complemento);
            valores.setString(9, bairro);
            valores.setString(10, cidade);
            valores.setString(11, uf);
            valores.setString(12, email);
            valores.setString(13, celular);
            valores.setString(14, pais);
            valores.setString(15, tags);
            valores.executeUpdate();
            int autoIncrementKey = 0;
            ResultSet rs = valores.getGeneratedKeys();
            if (rs.next()) {
                autoIncrementKey = rs.getInt(1);
            }
            valores.close();
            return autoIncrementKey;
        } catch (SQLException e) {
            //System.out.println("ERRO > "+e);
            ContrErroLog.inserir("HOITO - ContrPreVendaDest.inserir", "SQLException", sql, e.toString());
            return 0;
        } finally {
            Conexao.desconectar(conn);
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
                //System.out.println("aaa "+aux[0]);
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
                    ai.setObs("");//campo usado para tags (Entidade reaproveitada)
                    if (aux.length >= 14 && aux[13] != null) {
                        ai.setObs(aux[13].trim());
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

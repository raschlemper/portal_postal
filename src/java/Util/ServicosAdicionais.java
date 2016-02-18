/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Entidade.PrecoServAdicional;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SCC4
 */
public class ServicosAdicionais {

    public static ArrayList listaDeServicos(String siglas, String destino, Date data, String nomeBD, float valorDestino, float valorDeclarado) {
        siglas = siglas.trim();
        if (siglas != null && !siglas.equals("")) {
            if (valorDestino > 0) {
                siglas += "VP";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            destino = destino.toUpperCase();
            if (destino.equals("BR")) {
                destino = "NAC";
            } else {
                destino = "INT";
            }
            String dataBd = sdf.format(data);
            ArrayList listaDeServicos = new ArrayList();

            if (siglas.length() % 2 == 0) {

                char array[] = siglas.toCharArray();
                String arrayDeString[] = new String[siglas.length() / 2];
                int cont = 0;

                //Separa a String de siglas num array separado de siglas
                for (int i = 0; i < array.length; i = i + 2) {
                    arrayDeString[cont] = array[i] + "" + array[i + 1];
                    cont++;
                }

                //Monta array de serviços adicionais
                try {
                    for (int i = 0; i < arrayDeString.length; i++) {
                        PrecoServAdicional psa = Controle.contrPrecoServAdicional.consultaSigla(arrayDeString[i], dataBd, destino, nomeBD);
                        if (arrayDeString[i].equals("VD")) {
                            double valor = (valorDeclarado-75) * 0.010;
                            float valor2 = (float) valor;
                            psa.setValor(valor2);
                        }
                        listaDeServicos.add(psa);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ServicosAdicionais.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return listaDeServicos;
        } else {
            return null;
        }
    }

    public static String pegaHTML(URL url) {
        try{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");

            String resultado = "";
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                DataInputStream inStream = new DataInputStream(connection.getInputStream());
                String aux;
                while ((aux = inStream.readLine()) != null) {
                    resultado += aux;
                }
                inStream.close();
            }

            return resultado;
        }catch(IOException e){
            return e.getMessage();
        }
    }
}

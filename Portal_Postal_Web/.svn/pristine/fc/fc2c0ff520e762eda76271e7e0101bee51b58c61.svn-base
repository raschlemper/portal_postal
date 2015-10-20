/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Entidade.PrecoServAdicional;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SCC4
 */
public class NewClass {

    public static void main(String[] args) throws ParseException {

        float valorDeclarado = 10;
        float valorDestino = 1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String siglas = "VDARRG";
        if(valorDestino>0){
            siglas += "VP";
        }
        String destino = "Br";
        destino = destino.toUpperCase();
        if(destino.equals("BR")){
            destino = "NAC";
        }else{
            destino = "INT";
        }
        String nomeBD = "03762480000116";
        Date data = new Date();
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
            System.out.println("\n\n");
            try {
                for (int i = 0; i < arrayDeString.length; i++) {
                    PrecoServAdicional psa = Controle.contrPrecoServAdicional.consultaSigla(arrayDeString[i], dataBd, destino, nomeBD);
                    if(arrayDeString[i].equals("VD")){
                        double valor = valorDeclarado * 0.01;
                        float valor2 = (float) valor;
                        psa.setValor(valor2);
                    }
                    System.out.println(psa.getDesc()+": R$"+psa.getValor());
                    listaDeServicos.add(psa);
                }
            } catch (SQLException ex) {
                Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("\n\n");
        }
    }
}
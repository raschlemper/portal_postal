/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.text.DecimalFormat;

/**
 *
 * @author Avell
 */
public class FormatarDecimal {
    
    public static void main(String[] args) {
        System.out.println(formatarFloat((float) 254165460.55));
    }

    public static String formatarFloat(float numero) {
        String retorno = "";
        DecimalFormat formatter = new DecimalFormat("###,###.00");
        try {
            retorno = formatter.format(numero);
            if(retorno.indexOf(",") == 0){
                retorno = "0"+retorno;
            }
        } catch (Exception ex) {
            System.err.println("Erro ao formatar numero: " + ex);
        }
        return retorno;
    }

    public static String formatarFloatComPonto(float numero) {
        String retorno = "";
        DecimalFormat formatter = new DecimalFormat("#.00");
        try {
            retorno = formatter.format(numero);
            retorno = retorno.replaceAll(",", ".");
        } catch (Exception ex) {
            System.err.println("Erro ao formatar numero: " + ex);
        }
        return retorno;
    }

    public static float floatParser(String numero) throws Exception {
        numero = numero.trim().replaceAll(",", ".");
        return Float.parseFloat(numero);
    }

    public static int intParser(String v) throws Exception {
        return Integer.parseInt(v.trim());
    }
    
}

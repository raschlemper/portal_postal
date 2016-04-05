/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Avell
 */
public class FormatarDecimal {

    public static String formatarFloat(float numero) {
        String retorno = "";
        Locale ptBr = new Locale("pt", "BR");
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(ptBr);
        otherSymbols.setDecimalSeparator(',');
        otherSymbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("###,###.00", otherSymbols);
        try {
            retorno = formatter.format(numero);
            if (retorno.indexOf(",") == 0) {
                retorno = "0" + retorno;
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

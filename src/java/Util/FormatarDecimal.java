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

    public static float floatConverter(String valor) {
        float x = 0;
        try {
            x = Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            x = 0;
        }
        return x;
    }

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

    public static String somaDecimal(String d1, String d2){
        
        double s1 = Double.parseDouble(d1);
        double s2 = Double.parseDouble(d2);        
        double sum = s1 + s2;
        
        return  Double.toString(sum);
    }

    public static String formataValorMonetario(double valor) {
        DecimalFormat fomater = new DecimalFormat("###,##0.00");
        return fomater.format(valor);
    }

    public static Integer formataInteiro(String valor){
        try{
            return Integer.valueOf(valor);
        }catch(NumberFormatException numberException){
            return 0;
        }

    }
    
     public static Double formataDouble(String valor){
        try{
            return Double.valueOf(valor);
        }catch(NumberFormatException numberException){
            return 0d;
        }

    }

}

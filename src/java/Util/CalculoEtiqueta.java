/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class CalculoEtiqueta {
    
    public static void main(String[] args) {
        System.out.println(calculaDigito(Integer.parseInt("66202117")));
    }

    public static String calculaDigito(int numRegistro) {
        try {
            String numero = String.valueOf(numRegistro);
            int max = 8 - numero.length();
            for (int i = 0; i < max; i++) {
                numero = "0" + numero;
            }

            char array[] = numero.toCharArray();
            int soma = 0;
            soma += (int) array[0] * 8;
            soma += (int) array[1] * 6;
            soma += (int) array[2] * 4;
            soma += (int) array[3] * 2;
            soma += (int) array[4] * 3;
            soma += (int) array[5] * 5;
            soma += (int) array[6] * 9;
            soma += (int) array[7] * 7;

            int dig = 11 - (soma % 11);

            if (dig == 11) {
                dig = 5;
            }
            if (dig == 10) {
                dig = 0;
            }

            return String.valueOf(dig);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao Calcular digito Verificador!\n\nERRO: " + e.getMessage());
            return "99";
        }

    }

    public static String concertaTamanhoNum(int num) {
        String numero = String.valueOf(num);
        int max = 8 - numero.length();
        for (int i = 0; i < max; i++) {
            numero = "0" + numero;
        }
        return numero;
    }

    public static String concertaTamanhoNumComDigito(String numero) {
        int max = 9 - numero.length();
        for (int i = 0; i < max; i++) {
            numero = "0" + numero;
        }
        return numero;
    }

    
    
    public static boolean validaNumObjeto(String numObj) {
        String dig = "";
        String num = "";

        try {
            if (numObj == null) {
                return false;
            }
            
            numObj = numObj.trim();

            if (numObj.length() == 13) {
                dig = numObj.substring(10, 11);
                num = numObj.substring(2, 10);
            } else {
                return false;
            }

            int numero = Integer.parseInt(num);
            String digVer = calculaDigito(numero);

            if (dig.equals(digVer)) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }
    
}

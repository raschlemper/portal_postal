/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author RICADINHO
 */
public class Situacao {

    private static String listDevolvido[] = {"4", "5", "6", "7", "8", "10", "18", "19", "20", "21", "22", "23", "25", "26", "27", "34", "42"};
    private static String listExtraviado[] = {"9", "12", "28", "31", "43", "44", "50", "51", "52", "69"};

    public static String consultaGrupoStatus(String codStatus, String status) {

        if (codStatus.equals("0")) {
            return "POSTADO";
        }

        if (codStatus.equals("1") || (codStatus.equals("99") && (status.toLowerCase().contains("entregue") || status.toLowerCase().contains("retirada")))) {
            return "ENTREGUE";
        }

        for (int i = 0; i < listDevolvido.length; i++) {
            if (listDevolvido[i].equals(codStatus)) {
                return "DEVOLVIDO";
            }
        }

        for (int i = 0; i < listExtraviado.length; i++) {
            if (listExtraviado[i].equals(codStatus)) {
                return "EXTRAVIADO";
            }
        }

        return "ENCAMINHADO";

    }
}

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

    private static final int CODIGOS_ENTREGUE[] = {0, 1};
    private static final int CODIGOS_EXTRAVIADO[] = {9, 12, 28, 37, 43, 50, 51, 52, 69};
    private static final int CODIGOS_DEVOLVIDO[] = {4, 5, 6, 7, 8, 10, 18, 19, 20, 21, 22, 23, 25, 26, 27, 33, 34, 36, 40, 42, 48, 49, 56};
    
    private static final String[] LIST_DEVOLVIDO = {"4", "5", "6", "7", "8", "10", "18", "19", "20", "21", "22", "23", "25", "26", "27", "34", "42"};
    private static final String[] LIST_EXTRAVIADO = {"9", "12", "28", "31", "43", "44", "50", "51", "52", "69"};

    public static String consultaGrupoStatus(String codStatus, String status) {

        if (codStatus.equals("0")) {
            return "POSTADO";
        }

        if (codStatus.equals("1") || (codStatus.equals("99") && (status.toLowerCase().contains("entregue") || status.toLowerCase().contains("retirada")))) {
            return "ENTREGUE";
        }

        for (int i = 0; i < LIST_DEVOLVIDO.length; i++) {
            if (LIST_DEVOLVIDO[i].equals(codStatus)) {
                return "DEVOLVIDO";
            }
        }

        for (int i = 0; i < LIST_EXTRAVIADO.length; i++) {
            if (LIST_EXTRAVIADO[i].equals(codStatus)) {
                return "EXTRAVIADO";
            }
        }

        return "ENCAMINHADO";

    }

    public static String consultaGrupoStatusNovo(int codStatus, String statusType, String status) {

        if (statusType == null || statusType.equals("")) {
            return "POSTADO";
        }

        for (int i = 0; i < CODIGOS_ENTREGUE.length; i++) {
            if (CODIGOS_ENTREGUE[i] == codStatus && (statusType.equals("BDE") || statusType.equals("BDI") || statusType.equals("BDR"))) {
                return "ENTREGUE";
            }
        }

        for (int i = 0; i < CODIGOS_EXTRAVIADO.length; i++) {
            if (CODIGOS_EXTRAVIADO[i] == codStatus && (statusType.equals("BDE") || statusType.equals("BDI") || statusType.equals("BDR"))) {
                return "EXTRAVIADO";
            }
        }

        for (int i = 0; i < CODIGOS_DEVOLVIDO.length; i++) {
            if (CODIGOS_DEVOLVIDO[i] == codStatus && (statusType.equals("BDE") || statusType.equals("BDI") || statusType.equals("BDR"))) {
                return "DEVOLVIDO";
            }
        }

        return "ENCAMINHADO";

    }
}

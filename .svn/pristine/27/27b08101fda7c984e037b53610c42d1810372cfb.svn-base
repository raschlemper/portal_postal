/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.text.Normalizer;
import java.text.ParseException;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Administrador
 */
public class FormataString {
    
    /**
     * 
     * @param texto String a ser completada.
     * @param letra Caracter para preenchimento dos espaços faltantes.
     * @param tamanho Tamanho que deve ter a String final.
     * @param direcao 1 = Preenche a direita, -1 = Preenche a esquerda.
     * @return 
     */
    public static String preencheStringCom(String texto, String letra, int tamanho, int direcao) {
        //Checa se Linha a preencher é nula ou branco
        if (texto == null || "".equals(texto.trim())) {
            texto = "";
        }
        texto = texto.trim();
        texto = texto.replaceAll("&", "");

        StringBuilder sb = new StringBuilder(texto);
        if (sb.length() > tamanho) {
            sb = new StringBuilder(sb.substring(0, tamanho));
        } else if (direcao == -1) { //a Esquerda
            for (int i = sb.length(); i < tamanho; i++) {
                sb.insert(0, letra);
            }
        } else if (direcao == 1) {//a Direita
            for (int i = sb.length(); i < tamanho; i++) {
                sb.append(letra);
            }
        }

        return sb.toString();

    }
    
    public static String preencheCom(String linha_a_preencher, String letra, int tamanho, int direcao){
        try{
            //Checa se Linha a preencher é nula ou branco
            if (linha_a_preencher == null || linha_a_preencher.trim().equals("")) {
                linha_a_preencher = "";
            }     

            //Enquanto Linha a preencher possuir 2 espaços em branco seguidos, substitui por 1 espaço apenas
            while (linha_a_preencher.contains(" ")) {
                linha_a_preencher = linha_a_preencher.replaceAll(" "," ").trim();
            }       

            //Retira caracteres estranhos
            linha_a_preencher = linha_a_preencher.replaceAll("[./-]","");
            StringBuilder sb = new StringBuilder(linha_a_preencher);
            if (direcao==1){ //a Esquerda
                for (int i=sb.length() ; i<tamanho ; i++){
                    sb.insert(0,letra);
                }
            } else if (direcao==2) {//a Direita
                for (int i=sb.length() ; i<tamanho ; i++){
                    sb.append(letra);
                }
            }

            return sb.toString();
        }catch(Exception e){
            return linha_a_preencher;
        }
    } 
    
    public static String getGrupoServ(int codEct){
        switch(codEct){
            case 10014: return "CARTA";
            case 10138: return "CARTA";
            case 10707: return "CARTA";
            case 81019: return "ESEDEX";
            case 41068: return "PAC";
            case 41106: return "PAC";
            case 41300: return "PAX";
            case 40444: return "SEDEX";
            case 40436: return "SEDEX";
            case 40096: return "SEDEX";
            case 40010: return "SEDEX";
            case 40843: return "SEDEX";
            case 40215: return "SEDEX10";
            case 40886: return "SEDEX10";
            case 40169: return "SEDEX12";
            case 40126: return "SEDEXC";
            case 40045: return "SEDEXC";
            case 10065: return "SIMPLES";
            case 10715: return "SIMPLES";
            default: return "";
        }
    }
    
    public static String[] getServicoPorCodigoECT(int codEct){
        String[] ret = new String[3];
        switch(codEct){
            case 10014: 
                ret[0] = "10.014";
                ret[1] = "CARTA";
                ret[2] = "imagensNew/carta.png";
                return ret;
            case 10138:
                ret[0] = "10.138";
                ret[1] = "CARTA REG.";
                ret[2] = "imagensNew/carta.png";
                return ret;
            case 10707: 
                ret[0] = "10.707";
                ret[1] = "CARTA REG.";
                ret[2] = "imagensNew/carta.png";
                return ret;
            case 81833: 
                ret[0] = "81.833";
                ret[1] = "E-SEDEX";
                ret[2] = "imagensNew/esedex.png";
                return ret;
            case 81019: 
                ret[0] = "81.019";
                ret[1] = "E-SEDEX";
                ret[2] = "imagensNew/esedex.png";
                return ret;
            case 41238: 
                ret[0] = "41.238";
                ret[1] = "PAC PAG. NA ENTREGA";
                ret[2] = "imagensNew/pac.png";
                return ret;
            case 41262: 
                ret[0] = "41.262";
                ret[1] = "PAC PAG. NA ENTREGA";
                ret[2] = "imagensNew/pac.png";
                return ret;
            case 41068: 
                ret[0] = "41.068";
                ret[1] = "PAC";
                ret[2] = "imagensNew/pac.png";
                return ret;
            case 41106: 
                ret[0] = "41.106";
                ret[1] = "PAC";
                ret[2] = "imagensNew/pac.png";
                return ret;
            case 41211: 
                ret[0] = "41.211";
                ret[1] = "PAC";
                ret[2] = "imagensNew/pac.png";
                return ret;
            case 41491: 
                ret[0] = "41.491";
                ret[1] = "PAC";
                ret[2] = "imagensNew/pac.png";
                return ret;
            case 41300: 
                ret[0] = "41.300";
                ret[1] = "PAC GRANDES FORMATOS";
                ret[2] = "imagensNew/pax.png";
                return ret;
            case 40444: 
                ret[0] = "40.444";
                ret[1] = "SEDEX";
                ret[2] = "imagensNew/sedex.png";
                return ret;
            case 40436: 
                ret[0] = "40.436";
                ret[1] = "SEDEX";
                ret[2] = "imagensNew/sedex.png";
                return ret;
            case 40096: 
                ret[0] = "40.096";
                ret[1] = "SEDEX";
                ret[2] = "imagensNew/sedex.png";
                return ret;
            case 40010: 
                ret[0] = "40.010";
                ret[1] = "SEDEX";
                ret[2] = "imagensNew/sedex.png";
                return ret;
            case 40568: 
                ret[0] = "40.568";
                ret[1] = "SEDEX";
                ret[2] = "imagensNew/sedex.png";
                return ret;
            case 41408: 
                ret[0] = "41.408";
                ret[1] = "SEDEX";
                ret[2] = "imagensNew/sedex.png";
                return ret;
            case 40843: 
                ret[0] = "40.843";
                ret[1] = "SEDEX";
                ret[2] = "imagensNew/sedex.png";
                return ret;
            case 40215: 
                ret[0] = "40.215";
                ret[1] = "SEDEX 10";
                ret[2] = "imagensNew/sedex10.png";
                return ret;
            case 40789: 
                ret[0] = "40.789";
                ret[1] = "SEDEX 10";
                ret[2] = "imagensNew/sedex10.png";
                return ret;
            case 40886: 
                ret[0] = "40.886";
                ret[1] = "SEDEX 10";
                ret[2] = "imagensNew/sedex10.png";
                return ret;
            case 40169: 
                ret[0] = "40.169";
                ret[1] = "SEDEX 12";
                ret[2] = "imagensNew/sedex12.png";
                return ret;
            case 40126: 
                ret[0] = "40.126";
                ret[1] = "SEDEX A COBRAR";
                ret[2] = "imagensNew/sedex_cobrar.png";
                return ret;
            case 40045: 
                ret[0] = "40.045";
                ret[1] = "SEDEX A COBRAR";
                ret[2] = "imagensNew/sedex_cobrar.png";
                return ret;
            case 40630: 
                ret[0] = "40.630";
                ret[1] = "SEDEX PAG. NA ENTREGA";
                ret[2] = "imagensNew/sedex_cobrar.png";
                return ret;
            case 40432: 
                ret[0] = "40.432";
                ret[1] = "SEDEX PAG. NA ENTREGA";
                ret[2] = "imagensNew/sedex_cobrar.png";
                return ret;
            case 40440: 
                ret[0] = "40.440";
                ret[1] = "SEDEX PAG. NA ENTREGA";
                ret[2] = "imagensNew/sedex_cobrar.png";
                return ret;
            case 40819: 
                ret[0] = "40.819";
                ret[1] = "SEDEX PAG. NA ENTREGA";
                ret[2] = "imagensNew/sedex_cobrar.png";
                return ret;
            case 10065:
                ret[0] = "10.065";
                ret[1] = "CARTA SIMPLES";
                ret[2] = "imagensNew/carta.png";
                return ret;
            case 10715:
                ret[0] = "10.715";
                ret[1] = "CARTA SIMPLES";
                ret[2] = "imagensNew/carta.png";
                return ret;
            default: 
                ret[0] = codEct+"";
                ret[1] = "";
                ret[2] = "imagensNew/carta.png";
                return ret;
        }
    }
    
    public static String getUfDiretoria(int codDir){
        switch(codDir){
            case 1: return "AC";
            case 3: return "ACR";
            case 4: return "AL";
            case 6: return "AM";
            case 5: return "AP";
            case 8: return "BA";
            case 10: return "BSB";
            case 12: return "CE";
            case 14: return "ES";
            case 16: return "GO";
            case 18: return "MA";
            case 20: return "MG";
            case 22: return "MS";
            case 24: return "MT";
            case 28: return "PA";
            case 30: return "PB";
            case 32: return "PE";
            case 34: return "PI";
            case 36: return "PR";
            case 50: return "RJ";
            case 60: return "RN";
            case 26: return "RO";
            case 65: return "RR";
            case 64: return "RS";
            case 68: return "SC";
            case 70: return "SE";
            case 74: return "SP";
            case 72: return "SP";
            case 75: return "TO";
            default: return "";
        }
    }
    
    public static String replaceNonDigits(String string) {
        if (string == null || string.length() == 0) {
            return "0";
        }
        String ret = string.replaceAll("[^0-9]+", "");
        if (ret.equals("")) {
            return "0";
        } else {
            return ret;
        }
    }

    public static String formatarCep(String pattern, String value) {
        MaskFormatter mask;
        if (value != null) {
            value = replaceNonDigits(value);
            value = String.format("%08d", Long.parseLong(value));
            try {
                mask = new MaskFormatter(pattern);
                mask.setValueContainsLiteralCharacters(false);
                return mask.valueToString(value);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Falha ao formatar o CEP " + value + "\n\nFunção: Util.formatarCep()");
                return value;
            }
        } else {
            return "0";
        }
    }

    public static String removeAccentsToUpper(String str) {
        try{
            if(str!=null){
                str = Normalizer.normalize(str, Normalizer.Form.NFD);
                str = str.replaceAll("[^\\p{ASCII}]", "");
                str = str.replaceAll("Ç", "C");
                str = str.replaceAll("ç", "c");
                str = str.replaceAll("&", "");
                str = str.replaceAll("#", "");
                str = str.replaceAll("'", "");
                str = str.replaceAll("<", "");
                str = str.replaceAll(">", "");
                str = str.replaceAll("\"", "");
                str = str.replaceAll("\\\\", "/");
                return str.toUpperCase();
            }else{
                return "";
            }
        }catch(Exception e){
            return "";
        }
    }

    public static String removeSpecialChars(String str) {
        try{
            if(str!=null){
                str = str.replaceAll("&", "");
                str = str.replaceAll("#", "");
                str = str.replaceAll("\'", "");
                str = str.replaceAll("\"", "");
                str = str.replaceAll("\\\\", "/");
                return str;
            }else{
                return "";
            }
        }catch(Exception e){
            return "";
        }
    }

    public static String Capitalize(String palavra) {
        if (palavra != null) {
            int len = palavra.length();
            String out = "";
            out += palavra.substring(0, 1).toUpperCase();
            out += palavra.substring(1, len).toLowerCase();
            return out;
        }
        return palavra;
    }

    public static String limitaTamanhoString(String texto, int limite) {
        String resposta = null;
        int num = texto.length();
        if (num > limite) {
            resposta = texto.substring(0, limite);
            resposta += " ...";
        } else {
            resposta = texto;
        }
        return resposta;
    }

    public static String formataCep(String num) {
        String numero = String.valueOf(num);
        numero = numero.replaceAll("-", "").replaceAll("\\.", "");
        int max = 8 - numero.trim().length();
        for (int i = 0; i < max; i++) {
            numero = "0" + numero;
        }
        return numero.substring(0, 5) + "-" + numero.substring(5);
    }

    public static String montaWhereServicos(String serv) {
        String sql = "";
        if (serv.equals("SEDEX")) {
            sql = " AND codigoEct IN (40010, 40096, 40436, 40444, 40843, 40568) ";
        } else if (serv.equals("ESEDEX")) {
            sql = " AND codigoEct IN (81019, 81833) ";
        } else if (serv.equals("SEDEXC")) {
            sql = " AND codigoEct IN (40045, 40126) ";
        } else if (serv.equals("SEDEX10")) {
            sql = " AND codigoEct IN (40215, 40886, 40789) ";
        } else if (serv.equals("SEDEX12")) {
            sql = " AND codigoEct IN (40169) ";
        } else if (serv.equals("PAC")) {
            sql = " AND codigoEct IN (41068, 41106, 41211) ";
        } else if (serv.equals("CARTA")) {
            sql = " AND (codigoEct IN (10138, 10707) OR (codigoEct = 10014 AND siglaServAdicionais LIKE '%RG%'))";
        } else if (serv.equals("SIMPLES")) {
            sql = " AND (codigoEct IN (10065, 10715) OR (codigoEct = 10014 AND siglaServAdicionais NOT LIKE '%RG%'))"; //10014
        } else if (serv.equals("OUTROS")) {
            sql = " AND codigoEct NOT IN (40010, 40096, 40436, 40444, 40843, 81019, 40045, 40126, 40215, 40886, 41068, 41106, 10014, 10138, 10707, 10065, 10715) ";
        }
        return sql;
    }

    public static boolean isCNPJ(String CNPJ) {
        // considera-se erro CNPJ's formados por uma sequencia de numeros iguais
        if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111")
                || CNPJ.equals("22222222222222") || CNPJ.equals("33333333333333")
                || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555")
                || CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777")
                || CNPJ.equals("88888888888888") || CNPJ.equals("99999999999999")
                || (CNPJ.length() != 14)) {
            return (false);
        }

        char dig13, dig14;
        int sm, i, r, num, peso;

        // "try" - protege o código para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                // converte o i-ésimo caractere do CNPJ em um número:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posição de '0' na tabela ASCII)
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig13 = '0';
            } else {
                dig13 = (char) ((11 - r) + 48);
            }

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (CNPJ.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig14 = '0';
            } else {
                dig14 = (char) ((11 - r) + 48);
            }

            // Verifica se os dígitos calculados conferem com os dígitos informados.
            if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }
    }
    
    public static String MD5(String md5) {
   try {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        byte[] array = md.digest(md5.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
       }
        return sb.toString();
    } catch (java.security.NoSuchAlgorithmException e) {
    }
    return null;
}
}

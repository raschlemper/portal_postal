/*
 * FormatarData.java
 *
 * Created on 22 de Outubro de 2007, 16:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package Util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author aluno
 */
public class FormatarData {

    public static String nomeMes(int mes) {
        String monthString = "";
        switch (mes) {
            case 1:
                monthString = "Janeiro";
                break;
            case 2:
                monthString = "Fevereiro";
                break;
            case 3:
                monthString = "Marco";
                break;
            case 4:
                monthString = "Abril";
                break;
            case 5:
                monthString = "Maio";
                break;
            case 6:
                monthString = "Junho";
                break;
            case 7:
                monthString = "Julho";
                break;
            case 8:
                monthString = "Agosto";
                break;
            case 9:
                monthString = "Setembro";
                break;
            case 10:
                monthString = "Outubro";
                break;
            case 11:
                monthString = "Novembro";
                break;
            case 12:
                monthString = "Dezembro";
                break;

        }
        return monthString;
    }

    public static String formataStringToString(String data, String patternEntrada, String patternSaida) throws Exception {
        try {
            DateFormat formatter = new SimpleDateFormat(patternEntrada);
            SimpleDateFormat sdf = new SimpleDateFormat(patternSaida);
            Date date = new java.sql.Date(((java.util.Date) formatter.parse(data.trim())).getTime());
            return sdf.format(date);
        } catch (ParseException e) {
            throw e;
        }
    }

    public static Date formataStringToDate(String data, String patternEntrada) throws Exception {
        try {
            DateFormat formatter = new SimpleDateFormat(patternEntrada);
            Date date = new java.sql.Date(((java.util.Date) formatter.parse(data.trim())).getTime());
            return date;
        } catch (ParseException e) {
            throw e;
        }
    }

    /**
     * ***********************************************************************
     */

    /* Recebe String no formato dd/MM/yyyy HH:mm */
    public static Timestamp formataDateTime(String dataEntrada) throws Exception {
        if (dataEntrada == null || dataEntrada.equals("")) {
            return null;
        }
        Timestamp timest = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = sdf.parse(dataEntrada);
            timest = new Timestamp(date.getTime());
        } catch (ParseException e) {
            throw e;
        }
        return timest;
    }

    public static Date getDateFromString(String data, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(data);
    }

    public static String DateToBDcomAspas(String dataAtual) {
        try {
            if (dataAtual == null || dataAtual.equals("") || dataAtual.length() != 10) {
                return "NULL";
            } else {
                String[] temp = dataAtual.split("/");
                String dataNova = temp[2].trim() + "-" + temp[1].trim() + "-" + temp[0].trim();
                return "'" + dataNova + "'";
            }
        } catch (Exception e) {
            return "NULL";
        }
    }

    public static String DateToBD(String dataAtual) throws Exception {
        if (dataAtual == null || dataAtual.equals("")) {
            return "";
        } else {

            String[] temp = dataAtual.split("/");
            String dataNova = temp[2].trim() + "-" + temp[1].trim() + "-" + temp[0].trim();

            return dataNova;
        }
    }

    public static Date formataData(String data) throws Exception {
        if (data == null || data.equals("")) {
            return null;
        }
        Date date = null;

        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public static String DateToString(String vVencimento) throws Exception {
        if (vVencimento == null || vVencimento.equals("")) {
            return "";
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String data = formatter.format(vVencimento);
            if (data.equals("30/11/0002")) {
                return "";
            } else {
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String vencimento = sdf.format(vVencimento);
                    return vencimento;
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }

    public static String DateToStringExtenso(Date vVencimento) throws Exception {
        Locale brasil = new Locale("pt", "BR");
        if (vVencimento == null || vVencimento.equals("")) {
            return "";
        } else {
            DateFormat formatter = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", brasil);
            String data = formatter.format(vVencimento);
            if (data.equals("30 de novembro de 0002")) {
                return "";
            } else {
                try {
                    DateFormat sdf = new SimpleDateFormat("d 'de' MMMM 'de' yyyy", brasil);
                    String vencimento = sdf.format(vVencimento);
                    return vencimento;
                } catch (Exception e) {
                    throw e;
                }
            }
        }
    }

    public static String getDiaSemana(Date data) {

        String dt = "";
        try {
            Calendar cal = new GregorianCalendar();
            cal.setTime(data);

            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek == 1) {
                dt = "Dom";
            }
            if (dayOfWeek == 2) {
                dt = "Seg";
            }
            if (dayOfWeek == 3) {
                dt = "Ter";
            }
            if (dayOfWeek == 4) {
                dt = "Qua";
            }
            if (dayOfWeek == 5) {
                dt = "Qui";
            }
            if (dayOfWeek == 6) {
                dt = "Sex";
            }
            if (dayOfWeek == 7) {
                dt = "Sáb";
            }

            return dt;

        } catch (Exception e) {
            return dt;
        }

    }

    public static String getDiaSemanaMinuscula(Date data) {

        String dt = "";
        try {
            Calendar cal = new GregorianCalendar();
            cal.setTime(data);

            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek == 1) {
                dt = "dom";
            }
            if (dayOfWeek == 2) {
                dt = "seg";
            }
            if (dayOfWeek == 3) {
                dt = "ter";
            }
            if (dayOfWeek == 4) {
                dt = "qua";
            }
            if (dayOfWeek == 5) {
                dt = "qui";
            }
            if (dayOfWeek == 6) {
                dt = "sex";
            }
            if (dayOfWeek == 7) {
                dt = "sab";
            }

            return dt;

        } catch (Exception e) {
            return dt;
        }

    }

    public static int getDiaSemanaInt(Date data) {

        int dt = 0;
        try {
            Calendar cal = new GregorianCalendar();
            cal.setTime(data);

            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek == 1) {
                dt = 1;  //Domingo
            }
            if (dayOfWeek == 2) {
                dt = 2;
            }
            if (dayOfWeek == 3) {
                dt = 3;
            }
            if (dayOfWeek == 4) {
                dt = 4;
            }
            if (dayOfWeek == 5) {
                dt = 5;
            }
            if (dayOfWeek == 6) {
                dt = 6;
            }
            if (dayOfWeek == 7) {
                dt = 7;
            }

            return dt;

        } catch (Exception e) {
            return dt;
        }

    }

    public static String getNomeMes(String dt) {

        try {

            String mes = "";

            if (dt.equals("01") || dt.equals("1")) {
                mes = "Janeiro";
            }
            if (dt.equals("02") || dt.equals("2")) {
                mes = "Fevereiro";
            }
            if (dt.equals("03") || dt.equals("3")) {
                mes = "Março";
            }
            if (dt.equals("04") || dt.equals("4")) {
                mes = "Abril";
            }
            if (dt.equals("05") || dt.equals("5")) {
                mes = "Maio";
            }
            if (dt.equals("06") || dt.equals("6")) {
                mes = "Junho";
            }
            if (dt.equals("07") || dt.equals("7")) {
                mes = "Julho";
            }
            if (dt.equals("08") || dt.equals("8")) {
                mes = "Agosto";
            }
            if (dt.equals("09") || dt.equals("9")) {
                mes = "Setembro";
            }
            if (dt.equals("10")) {
                mes = "Outubro";
            }
            if (dt.equals("11")) {
                mes = "Novembro";
            }
            if (dt.equals("12")) {
                mes = "Dezembro";
            }

            return mes;

        } catch (Exception e) {
            return dt;
        }

    }

    public static String getDateName(int monthNumber) {  // Este metodo serve para retornar o nome do mês rapidamente
        String strReturn = "";
        switch (monthNumber) {
            case 0:
                strReturn = "Janeiro";
                break;
            case 1:
                strReturn = "Fevereiro";
                break;
            case 2:
                strReturn = "Março";
                break;
            case 3:
                strReturn = "Abril";
                break;
            case 4:
                strReturn = "Maio";
                break;
            case 5:
                strReturn = "Junho";
                break;
            case 6:
                strReturn = "Julho";
                break;
            case 7:
                strReturn = "Agosto";
                break;
            case 8:
                strReturn = "Setembro";
                break;
            case 9:
                strReturn = "Outubro";
                break;
            case 10:
                strReturn = "Novembro";
                break;
            case 11:
                strReturn = "Dezembro";
                break;
        }
        return strReturn;
    }

    public static boolean isDate(int m, int d, int y) { // Este método é usado para checar se uma data é VALIDA
        m -= 1;
        Calendar c = Calendar.getInstance();
        c.setLenient(false);

        try {
            c.set(y, m, d);
            Date dt = c.getTime();
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public static Date formataRetornaDate(String data) throws Exception {
        if (data == null || data.equals("")) {
            return null;
        }
        Date date = null;

        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = new java.sql.Date(((java.util.Date) formatter.parse(data)).getTime());
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    /**
     * Calcula a diferença de duas datas em dias
     * <br>
     * <b>Importante:</b> Quando realiza a diferença em dias entre duas datas,
     * este método considera as horas restantes e as converte em fração de dias.
     *
     * @param dataInicial
     * @param dataFinal
     * @return quantidade de dias existentes entre a dataInicial e dataFinal.
     */
    public static double diferencaEmDias(Date dataInicial, Date dataFinal) {
        double result = 0;
        if (dataInicial != null && dataFinal != null) {
            long diferenca = dataFinal.getTime() - dataInicial.getTime();
            double diferencaEmDias = (diferenca / 1000) / 60 / 60 / 24; //resultado é diferença entre as datas em dias  
            long horasRestantes = (diferenca / 1000) / 60 / 60 % 24; //calcula as horas restantes  
            result = diferencaEmDias + (horasRestantes / 24d); //transforma as horas restantes em fração de dias  
        }
        return result;
    }

    public static String dateTimeFormat(Date data){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        return dateFormat.format(data);
    }
}

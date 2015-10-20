/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Util;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author SCC4
 */
public class SomaData {
    
    public static String SomarDiasDatas (Date dataEnt, int diasSomados) throws Exception{
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        String dataEntrada = sdf.format(dataEnt);
        
        int dia = Integer.parseInt(dataEntrada.substring(0, 2));
        int vmes = Integer.parseInt(dataEntrada.substring(3, 5));
        int ano = Integer.parseInt(dataEntrada.substring(6, 10));
        int mes = vmes - 1;
                
         Calendar c = new GregorianCalendar(ano, mes, dia);
         c.add(Calendar.DATE, diasSomados);         
         String dataFut = sdf.format(c.getTime());
         
         return dataFut;
    
    }

    public static int getDiaSemana(Date data){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        return calendar.get(GregorianCalendar.DAY_OF_WEEK);
    }

    public static String getDiaSemanaDescritivo(Date data){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(data);
        String[] diaSemana = new String[]{"Domingo","Segunda-feira",
        "Terça-feira","Quarta-feira","Quinta-feira","Sexta-feira","Sábado"};
        return diaSemana[calendar.get(GregorianCalendar.DAY_OF_WEEK)];
    }

    public static Date SomarDiaData (Date dataEnt, int diasSomados) throws Exception{

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String dataEntrada = sdf.format(dataEnt);

        int dia = Integer.parseInt(dataEntrada.substring(0, 2));
        int vmes = Integer.parseInt(dataEntrada.substring(3, 5));
        int ano = Integer.parseInt(dataEntrada.substring(6, 10));
        int mes = vmes - 1;

         Calendar c = new GregorianCalendar(ano, mes, dia);
         c.add(Calendar.DATE, diasSomados);
         return c.getTime();
    }


    public static Date SomarMesData (Date dataEnt, int mesesSomados) throws Exception{

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        String dataEntrada = sdf.format(dataEnt);

        int dia = Integer.parseInt(dataEntrada.substring(0, 2));
        int vmes = Integer.parseInt(dataEntrada.substring(3, 5));
        int ano = Integer.parseInt(dataEntrada.substring(6, 10));
        int mes = vmes - 1;

         Calendar c = new GregorianCalendar(ano, mes, dia);
         c.add(Calendar.MONTH, mesesSomados);
         return c.getTime();
    }

    public static String SomarHorasDatas (Date dataEnt, int horasSomados) throws Exception{

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        String dataEntrada = sdf.format(dataEnt);

        int dia = Integer.parseInt(dataEntrada.substring(0, 2));
        int vmes = Integer.parseInt(dataEntrada.substring(3, 5));
        int ano = Integer.parseInt(dataEntrada.substring(6, 10));
        int hora = Integer.parseInt(dataEntrada.substring(11, 13));
        int minuto = Integer.parseInt(dataEntrada.substring(14, 16));
        int mes = vmes - 1;

         Calendar c = new GregorianCalendar(ano, mes, dia, hora, minuto);
         c.add(Calendar.HOUR, horasSomados);
         String dataFut = sdf.format(c.getTime());

         return dataFut;

    }
    
        /** 
        * Calcula a diferença de duas datas em dias 
        * <br> 
        * <b>Importante:</b> Quando realiza a diferença em dias entre duas datas, este método considera as horas restantes e as converte em fração de dias. 
        * @param dataInicial 
        * @param dataFinal 
        * @return quantidade de dias existentes entre a dataInicial e dataFinal. 
        */  
       public static double diferencaEmDias(Date dataInicial, Date dataFinal){  
           double result = 0;  
           long diferenca = dataFinal.getTime() - dataInicial.getTime();  
           double diferencaEmDias = (diferenca /1000) / 60 / 60 /24; //resultado é diferença entre as datas em dias  
           long horasRestantes = (diferenca /1000) / 60 / 60 %24; //calcula as horas restantes  
           result = diferencaEmDias + (horasRestantes /24d); //transforma as horas restantes em fração de dias  
         
           return result;  
       }  
         
       /** 
        * Calcula a diferença de duas datas em horas 
        * <br> 
        * <b>Importante:</b> Quando realiza a diferença em horas entre duas datas, este método considera os minutos restantes e os converte em fração de horas. 
        * @param dataInicial 
        * @param dataFinal 
        * @return quantidade de horas existentes entre a dataInicial e dataFinal. 
        */  
       public static double diferencaEmHoras(Date dataInicial, Date dataFinal){  
           double result = 0;  
           long diferenca = dataFinal.getTime() - dataInicial.getTime();  
           long diferencaEmHoras = (diferenca /1000) / 60 / 60;  
           long minutosRestantes = (diferenca / 1000)/60 %60;  
           double horasRestantes = minutosRestantes / 60d;  
           result = diferencaEmHoras + (horasRestantes);  
             
           return result;  
       }  
         
       /** 
        * Calcula a diferença de duas datas em minutos 
        * <br> 
        * <b>Importante:</b> Quando realiza a diferença em minutos entre duas datas, este método considera os segundos restantes e os converte em fração de minutos. 
        * @param dataInicial 
        * @param dataFinal 
        * @return quantidade de minutos existentes entre a dataInicial e dataFinal. 
        */  
       public static double diferencaEmMinutos(Date dataInicial, Date dataFinal){  
           double result = 0;  
           long diferenca = dataFinal.getTime() - dataInicial.getTime();  
           double diferencaEmMinutos = (diferenca /1000) / 60; //resultado é diferença entre as datas em minutos  
           long segundosRestantes = (diferenca / 1000)%60; //calcula os segundos restantes  
           result = diferencaEmMinutos + (segundosRestantes /60d); //transforma os segundos restantes em minutos  
         
           return result;  
       }  

}

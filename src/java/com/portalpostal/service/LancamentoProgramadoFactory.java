package com.portalpostal.service;

import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.dd.TipoSituacaoLancamentoProgramado;
import java.util.Calendar;
import java.util.Date;

public class LancamentoProgramadoFactory {
    
    public static LancamentoProgramado execute(LancamentoProgramado lancamentoProgramado) {
        if(lancamentoProgramado == null) return lancamentoProgramado;
        Integer numeroParcela = lancamentoProgramado.getNumeroParcela() + 1;
        switch (lancamentoProgramado.getFrequencia()) {
            case UNICO: 
                lancamentoProgramado.setSituacao(TipoSituacaoLancamentoProgramado.ENCERRADO);
                break;
            case DIARIO: 
                lancamentoProgramado.setNumeroParcela(numeroParcela);
                lancamentoProgramado.setDataCompetencia(addDaily(lancamentoProgramado.getDataCompetencia()));
                lancamentoProgramado.setDataVencimento(addDaily(lancamentoProgramado.getDataVencimento()));
                break;
            case SEMANAL: 
                lancamentoProgramado.setNumeroParcela(numeroParcela);
                lancamentoProgramado.setDataCompetencia(addWeekly(lancamentoProgramado.getDataCompetencia()));
                lancamentoProgramado.setDataVencimento(addWeekly(lancamentoProgramado.getDataVencimento()));
                break;
            case QUINZENAL: 
                lancamentoProgramado.setNumeroParcela(numeroParcela);
                lancamentoProgramado.setDataCompetencia(addBiweekly(lancamentoProgramado.getDataCompetencia()));
                lancamentoProgramado.setDataVencimento(addBiweekly(lancamentoProgramado.getDataVencimento()));
                break;
            case MENSAL: 
                lancamentoProgramado.setNumeroParcela(numeroParcela);
                lancamentoProgramado.setDataCompetencia(addMonthly(lancamentoProgramado.getDataCompetencia()));
                lancamentoProgramado.setDataVencimento(addMonthly(lancamentoProgramado.getDataVencimento()));
                break;
            case BIMESTRAL: 
                lancamentoProgramado.setNumeroParcela(numeroParcela);
                lancamentoProgramado.setDataCompetencia(addBimonthly(lancamentoProgramado.getDataCompetencia()));
                lancamentoProgramado.setDataVencimento(addBimonthly(lancamentoProgramado.getDataVencimento()));
                break;
            case TRIMESTRAL: 
                lancamentoProgramado.setNumeroParcela(numeroParcela);
                lancamentoProgramado.setDataCompetencia(addQuarterly(lancamentoProgramado.getDataCompetencia()));
                lancamentoProgramado.setDataVencimento(addQuarterly(lancamentoProgramado.getDataVencimento()));
                break;
            case QUADRIMESTRAL: 
                lancamentoProgramado.setNumeroParcela(numeroParcela);
                lancamentoProgramado.setDataCompetencia(addFourMonths(lancamentoProgramado.getDataCompetencia()));
                lancamentoProgramado.setDataVencimento(addFourMonths(lancamentoProgramado.getDataVencimento()));
                break;
            case SEMESTRAL: 
                lancamentoProgramado.setNumeroParcela(numeroParcela);
                lancamentoProgramado.setDataCompetencia(addSemiannual(lancamentoProgramado.getDataCompetencia()));
                lancamentoProgramado.setDataVencimento(addSemiannual(lancamentoProgramado.getDataVencimento()));
                break;
            case ANUAL: 
                lancamentoProgramado.setNumeroParcela(numeroParcela);
                lancamentoProgramado.setDataCompetencia(addYearly(lancamentoProgramado.getDataCompetencia()));
                lancamentoProgramado.setDataVencimento(addYearly(lancamentoProgramado.getDataVencimento()));
                break;
            default:
                lancamentoProgramado.setSituacao(TipoSituacaoLancamentoProgramado.ENCERRADO);
                break;
        }
        return lancamentoProgramado;
    }
    
    private static Date addDaily(Date data) {
        Calendar calendar = getCalendar(data);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
    
    private static Date addWeekly(Date data) {
        Calendar calendar = getCalendar(data);
        calendar.add(Calendar.DATE, 7);
        return calendar.getTime();
    }
    
    private static Date addBiweekly(Date data) {
        Integer days = 15;
        Calendar calendar = getCalendar(data);
        if(calendar.get(Calendar.DATE) + days > calendar.getActualMaximum(Calendar.DATE)) {
            if(calendar.getActualMaximum(Calendar.DATE) > 30) {
                days += calendar.getActualMaximum(Calendar.DATE) - 30;
            } else if(calendar.getActualMaximum(Calendar.DATE) < 30) {
                days -= 30 - calendar.getActualMaximum(Calendar.DATE);      
            }
        }
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }
    
    private static Date addMonthly(Date data) {
        Calendar calendar = getCalendar(data);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }
    
    private static Date addBimonthly(Date data) {
        Calendar calendar = getCalendar(data);
        calendar.add(Calendar.MONTH, 2);
        return calendar.getTime();
    }
    
    private static Date addQuarterly(Date data) {
        Calendar calendar = getCalendar(data);
        calendar.add(Calendar.MONTH, 3);
        return calendar.getTime();
    }
    
    private static Date addFourMonths(Date data) {
        Calendar calendar = getCalendar(data);
        calendar.add(Calendar.MONTH, 4);
        return calendar.getTime();
    }
    
    private static Date addSemiannual(Date data) {
        Calendar calendar = getCalendar(data);
        calendar.add(Calendar.MONTH, 6);
        return calendar.getTime();
    }
    
    private static Date addYearly(Date data) {
        Calendar calendar = getCalendar(data);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }
    
    private static Calendar getCalendar(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        return calendar;
    }
}

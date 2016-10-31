package com.portalpostal.validation;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class Validation<E> {  
    
    private String msg = null;
    private final Calendar calendar = new GregorianCalendar();
    private final NumberFormat number = NumberFormat.getCurrencyInstance();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }    
    
    protected Integer getAnoCorrente() {
        return calendar.get(Calendar.YEAR);
    }
    
    protected boolean campoNotNull(Object value) {        
        if(value != null && value != "") return true;
        return false;
    }
    
    protected boolean campoBetween(Object value, Integer comparatorInitial, Integer comparatorFinal) {
        if(value == null || value == "") return true;
        if(campoLessEqualThen(value, comparatorInitial) || campoMoreEqualThen(value, comparatorFinal)) {
            return false;                 
        }
        return true;
    }
    
    protected boolean campoLessEqualThen(Object value, Integer comparator) {
        Integer valueInt = Integer.parseInt(value.toString());
        if(valueInt >= comparator) { return false; }
        return true;
    }
    
    protected boolean campoMoreEqualThen(Object value, Integer comparator) {
        Integer valueInt = Integer.parseInt(value.toString());
        if(valueInt <= comparator) { return false; }
        return true;
    }
    
    protected String toNumberFormat(Integer value) {
        return number.format(value);
    }
        
    public abstract boolean validar(E object);
    
}

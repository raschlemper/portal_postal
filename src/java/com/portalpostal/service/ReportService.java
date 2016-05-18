package com.portalpostal.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ReportService {
    
    private String jrxml;
    private Map parameters;
    private Collection collection;
    private JasperPrint jasperPrint;

    private ReportService() { }   
    
    public static ReportService create() {
        return new ReportService();
    }    
    
    public ReportService jrxml(String jrxml) {
        this.jrxml = jrxml;
        return this;
    }  
    
    public ReportService parameter(Map params) {
        this.parameters = params;
        return this;
    }
    
    public ReportService collection(Collection collection) {
        this.collection = collection;
        return this;
    }  
    
    public ReportService jasper() throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(getJasperDesign());
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
        return this;
    }   
    
    public OutputStream report() throws IOException, JRException {        
        File pdf = File.createTempFile("temp_", ".pdf"); 
        OutputStream output = new FileOutputStream(pdf);
        JasperExportManager.exportReportToPdfStream(jasperPrint, output);
        return output;
    }
    
    private JasperDesign getJasperDesign() throws JRException {        
        InputStream in = getClass().getResourceAsStream(jrxml);
        return JRXmlLoader.load(in);    
    }
    
    private JRBeanCollectionDataSource getCollectionDataSource(Collection collection) {
        if(collection == null) return null;
        return new JRBeanCollectionDataSource(collection);
    }
}

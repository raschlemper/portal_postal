package com.portalpostal.report;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;
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
    
    private final String jrxml;
    private Map parameters;
    private Collection collection;

    private ReportService(String jrxml) {
        this.jrxml = jrxml;
    }    
    
    public static ReportService create(String jrxml) {
        return new ReportService(getReportName(jrxml));
    }  
    
    public ReportService parameter(Map params) {
        this.parameters = params;
        return this;
    }
    
    public ReportService collection(Collection collection) {
        this.collection = collection;
        return this;
    }    
    
    public StreamingOutput report() throws IOException, JRException { 
        return getStreamingOutput(getJasperPrint());
    }
    
    public JasperPrint getJasperPrint() throws JRException {
        JasperReport jasperReport = JasperCompileManager.compileReport(getJasperDesign());
        return JasperFillManager.fillReport(jasperReport, parameters, getCollectionDataSource(collection));
    } 
    
    private StreamingOutput getStreamingOutput(final JasperPrint jasperPrint) throws FileNotFoundException, JRException {
        return new StreamingOutput() {
            public void write(OutputStream output) throws IOException, WebApplicationException {
                try {
                    output.write(JasperExportManager.exportReportToPdf(jasperPrint));
                } catch (JRException ex) {
                    Logger.getLogger(ReportService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }
    
    private JasperDesign getJasperDesign() throws JRException {        
        InputStream in = getClass().getResourceAsStream(jrxml);
        return JRXmlLoader.load(in);    
    }
    
    private JRBeanCollectionDataSource getCollectionDataSource(Collection collection) {
        if(collection == null) return null;
        return new JRBeanCollectionDataSource(collection);
    }
    
    private static String getReportName(String name) {
        return "/iReports/" + name + ".jrxml";
    }
}

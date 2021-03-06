package com.portalpostal.report;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

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
        if(params == null) { params = new HashMap(); }
        params.put("REPORT_LOCALE", new Locale("pt","BR"));
        this.parameters = params;
        return this;
    }
    
    public ReportService collection(Collection collection) {
        this.collection = collection;
        return this;
    }    
    
    public StreamingOutput report(TypeReport type) throws Exception { 
        switch(type){
            case PDF: { return toStreamingOutput(pdf(getJasperPrint())); }
            case EXCEL: { return toStreamingOutput(excel(getJasperPrint())); }
            default: { return toStreamingOutput(pdf(getJasperPrint())); }
        }
    }  
    
    public JasperPrint getJasperPrint() throws Exception {
        JasperReport jasperReport = JasperCompileManager.compileReport(getJasperDesign());
        return JasperFillManager.fillReport(jasperReport, parameters, getCollectionDataSource(collection));
    } 
    
    private StreamingOutput toStreamingOutput(final byte[] file) throws FileNotFoundException, JRException {
        return new StreamingOutput() {
            public void write(OutputStream output) throws IOException, WebApplicationException {
                output.write(file);
            }
        };
    }
    
    private JasperDesign getJasperDesign() throws Exception {        
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
    
    private byte[] pdf(final JasperPrint jasperPrint) throws Exception {
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
    
    public byte[] excel(final JasperPrint jasperPrint) throws Exception { 
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        JRXlsExporter exporterXLS = new JRXlsExporter();
//        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
//        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//        exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
//        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
//        exporterXLS.exportReport();
        exporterXLS.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporterXLS.setExporterOutput(new SimpleOutputStreamExporterOutput(output));
        SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
        xlsReportConfiguration.setOnePagePerSheet(false);
        xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
        xlsReportConfiguration.setDetectCellType(false);
        xlsReportConfiguration.setWhitePageBackground(false);
        exporterXLS.setConfiguration(xlsReportConfiguration);
        exporterXLS.exportReport();
        return output.toByteArray();
    }
}

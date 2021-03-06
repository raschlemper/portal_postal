package com.portalpostal.report;

import com.portalpostal.report.dto.LancamentoReportDTO;
import Controle.ContrErroLog;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@Path("/lancamento")
public class LancamentoReportController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    private String nameReport;
    
    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        nameReport = "lancamento";
    }
    
    @POST
    @Path("/pdf")
    @Produces("application/pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pdf(List<LancamentoReportDTO> params) {
        try {
            init(); 
            return Response.ok(getReport(params, TypeReport.PDF)).build();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/excel")
    @Produces("application/vnd.ms-excel")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response excel(List<LancamentoReportDTO> params) {
        try {
            init(); 
            return Response.ok(getReport(params, TypeReport.EXCEL)).build();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    private StreamingOutput getReport(Collection collection, TypeReport type) throws Exception {
        return ReportService.create(nameReport)
                    .parameter(null)
                    .collection(collection)
                    .report(type); 
    }
            
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServPlanoConta", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
    
}

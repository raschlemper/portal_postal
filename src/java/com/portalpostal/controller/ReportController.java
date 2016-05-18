package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.model.PlanoConta;
import com.portalpostal.service.PlanoContaService;
import com.portalpostal.service.ReportService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

@Path("/report")
public class ReportController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
    }
    
    @GET
    @Path("/pdf/")
    @Produces("application/pdf")
    public Response pdf() {
        try {
            init(); 
            String teste = "deu certo!!!";
            OutputStream jasper = ReportService.create().jrxml("/iReports/demostrativo.jrxml").parameter(null).jasper().report();
            
            return Response.ok(jasper).build();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    private StreamingOutput getPdf(final String teste) {
        return new StreamingOutput() {
            public void write(OutputStream output) throws IOException, WebApplicationException {
                try {
                    output.write(teste.getBytes(), 0, teste.getBytes().length);
                } catch (Exception e) {
                    throw new WebApplicationException(e);
                }
            }
        };
    }
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServPlanoConta", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
    
}

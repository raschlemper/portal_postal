package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.LancamentoTransferencia;
import com.portalpostal.service.LancamentoTransferenciaService;
import com.portalpostal.validation.LancamentoTransferenciaValidation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/financeiro/lancamentotransferencia")
public class LancamentoTransferenciaController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private LancamentoTransferenciaService lancamentoTransferenciaService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        lancamentoTransferenciaService = new LancamentoTransferenciaService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LancamentoTransferencia> findAll() {
        try {
            init();    
            return lancamentoTransferenciaService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idLancamentoTransferencia}")
    @Produces(MediaType.APPLICATION_JSON)
    public LancamentoTransferencia find(@PathParam("idLancamentoTransferencia") Integer idLancamentoTransferencia) {
        try {
            init();    
            return lancamentoTransferenciaService.find(idLancamentoTransferencia);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoTransferencia save(LancamentoTransferencia lancamentoTransferencia) {
        try {
            init();
            validation(lancamentoTransferencia);
            return lancamentoTransferenciaService.save(lancamentoTransferencia);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idLancamentoTransferencia}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoTransferencia update(LancamentoTransferencia lancamentoTransferencia) {
        try {
            init();
            validation(lancamentoTransferencia);
            return lancamentoTransferenciaService.update(lancamentoTransferencia);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idLancamentoTransferencia}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoTransferencia delete(@PathParam("idLancamentoTransferencia") Integer idLancamentoTransferencia) {
        try {
            init();
            return lancamentoTransferenciaService.delete(idLancamentoTransferencia);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(LancamentoTransferencia lancamentoTransferencia) throws Exception {  
        Validation validacao = new LancamentoTransferenciaValidation();
        if(!validacao.validar(lancamentoTransferencia)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServLancamentoTransferencia", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

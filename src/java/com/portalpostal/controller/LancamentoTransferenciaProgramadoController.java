package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.LancamentoTransferenciaProgramado;
import com.portalpostal.service.LancamentoTransferenciaProgramadoService;
import com.portalpostal.validation.LancamentoTransferenciaProgramadoValidation;
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

@Path("/financeiro/lancamento/programado/transferencia")
public class LancamentoTransferenciaProgramadoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private LancamentoTransferenciaProgramadoService lancamentoTransferenciaProgramadoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        lancamentoTransferenciaProgramadoService = new LancamentoTransferenciaProgramadoService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LancamentoTransferenciaProgramado> findAll() {
        try {
            init();    
            return lancamentoTransferenciaProgramadoService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idLancamentoTransferenciaProgramado}")
    @Produces(MediaType.APPLICATION_JSON)
    public LancamentoTransferenciaProgramado find(@PathParam("idLancamentoTransferenciaProgramado") Integer idLancamentoTransferenciaProgramado) {
        try {
            init();    
            return lancamentoTransferenciaProgramadoService.find(idLancamentoTransferenciaProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoTransferenciaProgramado save(LancamentoTransferenciaProgramado lancamentoTransferenciaProgramado) {
        try {
            init();
            validation(lancamentoTransferenciaProgramado);
            return lancamentoTransferenciaProgramadoService.save(lancamentoTransferenciaProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idLancamentoTransferenciaProgramado}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoTransferenciaProgramado update(LancamentoTransferenciaProgramado lancamentoTransferenciaProgramado) {
        try {
            init();
            validation(lancamentoTransferenciaProgramado);
            return lancamentoTransferenciaProgramadoService.update(lancamentoTransferenciaProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idLancamentoTransferenciaProgramado}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoTransferenciaProgramado delete(@PathParam("idLancamentoTransferenciaProgramado") Integer idLancamentoTransferenciaProgramado) {
        try {
            init();
            return lancamentoTransferenciaProgramadoService.delete(idLancamentoTransferenciaProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(LancamentoTransferenciaProgramado lancamentoTransferenciaProgramado) throws Exception {  
        Validation validacao = new LancamentoTransferenciaProgramadoValidation();
        if(!validacao.validar(lancamentoTransferenciaProgramado)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServLancamentoTransferenciaProgramado", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

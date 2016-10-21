package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.LancamentoParcelado;
import com.portalpostal.service.LancamentoParceladoService;
import com.portalpostal.validation.LancamentoParceladoValidation;
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

@Path("/financeiro/lancamento/parcelado")
public class LancamentoParceladoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private LancamentoParceladoService lancamentoParceladoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        lancamentoParceladoService = new LancamentoParceladoService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LancamentoParcelado> findAll() {
        try {
            init();    
            return lancamentoParceladoService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idLancamentoParcelado}")
    @Produces(MediaType.APPLICATION_JSON)
    public LancamentoParcelado find(@PathParam("idLancamentoParcelado") Integer idLancamentoParcelado) {
        try {
            init();    
            return lancamentoParceladoService.find(idLancamentoParcelado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoParcelado save(LancamentoParcelado lancamentoParcelado) {
        try {
            init();
            validation(lancamentoParcelado);
            return lancamentoParceladoService.save(lancamentoParcelado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idLancamentoParcelado}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoParcelado update(LancamentoParcelado lancamentoParcelado) {
        try {
            init();
            validation(lancamentoParcelado);
            return lancamentoParceladoService.update(lancamentoParcelado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idLancamentoParcelado}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoParcelado delete(@PathParam("idLancamentoParcelado") Integer idLancamentoParcelado) {
        try {
            init();
            return lancamentoParceladoService.delete(idLancamentoParcelado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(LancamentoParcelado lancamentoParcelado) throws Exception {  
        Validation validacao = new LancamentoParceladoValidation();
        if(!validacao.validar(lancamentoParcelado)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServLancamentoParcelado", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

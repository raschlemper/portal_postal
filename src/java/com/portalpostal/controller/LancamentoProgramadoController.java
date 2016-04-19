package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.service.LancamentoProgramadoService;
import com.portalpostal.validation.LancamentoProgramadoValidation;
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

@Path("/financeiro/lancamento/programado")
public class LancamentoProgramadoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private LancamentoProgramadoService lancamentoProgramadoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LancamentoProgramado> findAll() {
        try {
            init();    
            return lancamentoProgramadoService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idLancamentoProgramado}")
    @Produces(MediaType.APPLICATION_JSON)
    public LancamentoProgramado find(@PathParam("idLancamentoProgramado") Integer idLancamentoProgramado) {
        try {
            init();    
            return lancamentoProgramadoService.find(idLancamentoProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoProgramado save(LancamentoProgramado lancamentoProgramado) {
        try {
            init();
            validation(lancamentoProgramado);
            return lancamentoProgramadoService.save(lancamentoProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idLancamentoProgramado}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoProgramado update(LancamentoProgramado lancamentoProgramado) {
        try {
            init();
            validation(lancamentoProgramado);
            return lancamentoProgramadoService.update(lancamentoProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoProgramado create(LancamentoProgramado lancamentoProgramado) {
        try {
            init();
            validation(lancamentoProgramado);
            return lancamentoProgramadoService.createLancamento(lancamentoProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idLancamentoProgramado}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoProgramado delete(@PathParam("idLancamentoProgramado") Integer idLancamentoProgramado) {
        try {
            init();
            return lancamentoProgramadoService.delete(idLancamentoProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(LancamentoProgramado lancamentoProgramado) throws Exception {  
        Validation validacao = new LancamentoProgramadoValidation();
        if(!validacao.validar(lancamentoProgramado)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServLancamentoProgramado", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.Colaborador;
import com.portalpostal.service.ColaboradorService;
import com.portalpostal.validation.ColaboradorValidation;
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

@Path("/financeiro/colaborador")
public class ColaboradorController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private ColaboradorService colaboradorService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        colaboradorService = new ColaboradorService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Colaborador> findAll() {
        try {
            init();    
            return colaboradorService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Colaborador find(@PathParam("idColaborador") Integer idColaborador) {
        try {
            init();    
            return colaboradorService.find(idColaborador);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @GET
    @Path("/{idColaborador}/lancamento")
    @Produces(MediaType.APPLICATION_JSON)
    public Colaborador findLancamento(@PathParam("idColaborador") Integer idColaborador) {
        try {
            init();    
            return colaboradorService.findLancamento(idColaborador);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idColaborador}/lancamento/programado")
    @Produces(MediaType.APPLICATION_JSON)
    public Colaborador findLancamentoProgramado(@PathParam("idColaborador") Integer idColaborador) {
        try {
            init();    
            return colaboradorService.findLancamentoProgramado(idColaborador);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Colaborador save(Colaborador colaborador) {
        try {
            init();
            validation(colaborador);
            return colaboradorService.save(colaborador);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Colaborador update(Colaborador colaborador) {
        try {
            init();
            validation(colaborador);
            return colaboradorService.update(colaborador);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idColaborador}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Colaborador delete(@PathParam("idColaborador") Integer idColaborador) {
        try {
            init();
            return colaboradorService.delete(idColaborador);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(Colaborador colaborador) throws Exception {  
        Validation validacao = new ColaboradorValidation();
        if(!validacao.validar(colaborador)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServColaborador", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

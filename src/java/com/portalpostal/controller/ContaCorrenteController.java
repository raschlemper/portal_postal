package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.ContaCorrente;
import com.portalpostal.service.ContaCorrenteService;
import com.portalpostal.validation.ContaCorrenteValidation;
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

@Path("/financeiro/contacorrente")
public class ContaCorrenteController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private ContaCorrenteService conteCorrenteService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        conteCorrenteService = new ContaCorrenteService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ContaCorrente> findAll() {
        try {
            init();    
            return conteCorrenteService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idContaCorrente}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContaCorrente find(@PathParam("idContaCorrente") Integer idContaCorrente) {
        try {
            init();    
            return conteCorrenteService.find(idContaCorrente);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/banco/{banco}/agencia/{agencia}/{agenciaDv}/contacorrente/{contacorrente}/{contacorrenteDv}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContaCorrente findByContaCorrente(@PathParam("banco") Integer banco, @PathParam("agencia") Integer agencia,
            @PathParam("agenciaDv") Integer agenciaDv, @PathParam("contacorrente") Integer contaCorrente, 
            @PathParam("contacorrenteDv") Integer contaCorrenteDv) {
        try {
            init();  
            return conteCorrenteService.findByContaCorrente(banco, agencia, agenciaDv, contaCorrente, contaCorrenteDv);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ContaCorrente save(ContaCorrente conteCorrente) {
        try {
            init();
            validation(conteCorrente);
            return conteCorrenteService.save(conteCorrente);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idContaCorrente}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ContaCorrente update(ContaCorrente conteCorrente) {
        try {
            init();
            validation(conteCorrente);
            return conteCorrenteService.update(conteCorrente);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idContaCorrente}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ContaCorrente delete(@PathParam("idContaCorrente") Integer idContaCorrente) {
        try {
            init();
            return conteCorrenteService.delete(idContaCorrente);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(ContaCorrente conteCorrente) throws Exception {  
        Validation validacao = new ContaCorrenteValidation();
        if(!validacao.validar(conteCorrente)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServContaCorrente", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

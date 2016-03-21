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
    
    private ContaCorrenteService cartaoCreditoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        cartaoCreditoService = new ContaCorrenteService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ContaCorrente> findAll() {
        try {
            init();    
            return cartaoCreditoService.findAll();
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
            return cartaoCreditoService.find(idContaCorrente);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/banco/{banco}/agencia/{agencia}/contacorrente/{contacorrente}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContaCorrente findByContaCorrente(@PathParam("banco") Integer banco, 
            @PathParam("agencia") Integer agencia, @PathParam("contacorrente") Integer contaCorrente) {
        try {
            init();    
            return cartaoCreditoService.findByContaCorrente(banco, agencia, contaCorrente);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @GET
    @Path("/banco/{banco}/agencia/{agencia}/contacorrente/{contacorrente}/carteira/{carteira}")
    @Produces(MediaType.APPLICATION_JSON)
    public ContaCorrente findByCarteira(@PathParam("banco") Integer banco, @PathParam("agencia") Integer agencia, 
            @PathParam("contacorrente") Integer contaCorrente, @PathParam("carteira") Integer carteira) {
        try {
            init();    
            return cartaoCreditoService.findByCarteira(banco, agencia, contaCorrente, carteira);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ContaCorrente save(ContaCorrente cartaoCredito) {
        try {
            init();
            validation(cartaoCredito);
            return cartaoCreditoService.save(cartaoCredito);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idContaCorrente}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ContaCorrente update(ContaCorrente cartaoCredito) {
        try {
            init();
            validation(cartaoCredito);
            return cartaoCreditoService.update(cartaoCredito);
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
            return cartaoCreditoService.delete(idContaCorrente);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(ContaCorrente cartaoCredito) throws Exception {  
        Validation validacao = new ContaCorrenteValidation();
        if(!validacao.validar(cartaoCredito)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServContaCorrente", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

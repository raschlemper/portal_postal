package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.CartaoCredito;
import com.portalpostal.service.CartaoCreditoService;
import com.portalpostal.validation.CartaoCreditoValidation;
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

@Path("/financeiro/cartaocredito")
public class CartaoCreditoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private CartaoCreditoService cartaoCreditoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        cartaoCreditoService = new CartaoCreditoService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CartaoCredito> findAll() {
        try {
            init();    
            return cartaoCreditoService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idCartaoCredito}")
    @Produces(MediaType.APPLICATION_JSON)
    public CartaoCredito find(@PathParam("idCartaoCredito") Integer idCartaoCredito) {
        try {
            init();    
            return cartaoCreditoService.find(idCartaoCredito);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CartaoCredito save(CartaoCredito cartaoCredito) {
        try {
            init();
            validation(cartaoCredito);
            return cartaoCreditoService.save(cartaoCredito);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idCartaoCredito}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CartaoCredito update(CartaoCredito cartaoCredito) {
        try {
            init();
            validation(cartaoCredito);
            return cartaoCreditoService.update(cartaoCredito);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idCartaoCredito}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CartaoCredito delete(@PathParam("idCartaoCredito") Integer idCartaoCredito) {
        try {
            init();
            return cartaoCreditoService.delete(idCartaoCredito);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(CartaoCredito cartaoCredito) throws Exception {  
        Validation validacao = new CartaoCreditoValidation();
        if(!validacao.validar(cartaoCredito)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServCartaoCredito", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

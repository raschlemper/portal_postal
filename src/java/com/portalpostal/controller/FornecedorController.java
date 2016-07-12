package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.Fornecedor;
import com.portalpostal.service.FornecedorService;
import com.portalpostal.validation.FornecedorValidation;
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

@Path("/financeiro/fornecedor")
public class FornecedorController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private FornecedorService fornecedorService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        fornecedorService = new FornecedorService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fornecedor> findAll() {
        try {
            init();    
            return fornecedorService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idFornecedor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Fornecedor find(@PathParam("idFornecedor") Integer idFornecedor) {
        try {
            init();    
            return fornecedorService.find(idFornecedor);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @GET
    @Path("/{idFornecedor}/lancamento")
    @Produces(MediaType.APPLICATION_JSON)
    public Fornecedor findLancamento(@PathParam("idFornecedor") Integer idFornecedor) {
        try {
            init();    
            return fornecedorService.findLancamento(idFornecedor);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idFornecedor}/lancamento/programado")
    @Produces(MediaType.APPLICATION_JSON)
    public Fornecedor findLancamentoProgramado(@PathParam("idFornecedor") Integer idFornecedor) {
        try {
            init();    
            return fornecedorService.findLancamentoProgramado(idFornecedor);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Fornecedor save(Fornecedor fornecedor) {
        try {
            init();
            validation(fornecedor);
            return fornecedorService.save(fornecedor);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idFornecedor}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Fornecedor update(Fornecedor fornecedor) {
        try {
            init();
            validation(fornecedor);
            return fornecedorService.update(fornecedor);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idFornecedor}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Fornecedor delete(@PathParam("idFornecedor") Integer idFornecedor) {
        try {
            init();
            return fornecedorService.delete(idFornecedor);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(Fornecedor fornecedor) throws Exception {  
        Validation validacao = new FornecedorValidation();
        if(!validacao.validar(fornecedor)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServFornecedor", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

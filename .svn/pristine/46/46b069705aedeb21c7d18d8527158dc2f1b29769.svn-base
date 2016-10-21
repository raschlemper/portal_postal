package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.TipoFormaPagamento;
import com.portalpostal.service.TipoFormaPagamentoService;
import com.portalpostal.validation.TipoFormaPagamentoValidation;
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

@Path("/financeiro/tipo/formapagamento")
public class TipoFormaPagamentoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private TipoFormaPagamentoService tipoFormaPagamentoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        tipoFormaPagamentoService = new TipoFormaPagamentoService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoFormaPagamento> findAll() {
        try {
            init();    
            return tipoFormaPagamentoService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idTipoFormaPagamento}")
    @Produces(MediaType.APPLICATION_JSON)
    public TipoFormaPagamento find(@PathParam("idTipoFormaPagamento") Integer idTipoFormaPagamento) {
        try {
            init();    
            return tipoFormaPagamentoService.find(idTipoFormaPagamento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/descricao/{descricao}")
    @Produces(MediaType.APPLICATION_JSON)
    public TipoFormaPagamento findByNumero(@PathParam("descricao") String descricao) {
        try {
            init();    
            return tipoFormaPagamentoService.findByDescricao(descricao);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TipoFormaPagamento save(TipoFormaPagamento tipoFormaPagamento) {
        try {
            init();
            validation(tipoFormaPagamento);
            return tipoFormaPagamentoService.save(tipoFormaPagamento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idTipoFormaPagamento}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TipoFormaPagamento update(TipoFormaPagamento tipoFormaPagamento) {
        try {
            init();
            validation(tipoFormaPagamento);
            return tipoFormaPagamentoService.update(tipoFormaPagamento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idTipoFormaPagamento}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TipoFormaPagamento delete(@PathParam("idTipoFormaPagamento") Integer idTipoFormaPagamento) {
        try {
            init();
            return tipoFormaPagamentoService.delete(idTipoFormaPagamento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(TipoFormaPagamento tipoFormaPagamento) throws Exception {  
        Validation validacao = new TipoFormaPagamentoValidation();
        if(!validacao.validar(tipoFormaPagamento)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServTipoFormaPagamento", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

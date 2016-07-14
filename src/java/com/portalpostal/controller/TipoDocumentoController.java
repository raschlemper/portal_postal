package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.TipoDocumento;
import com.portalpostal.service.TipoDocumentoService;
import com.portalpostal.validation.TipoDocumentoValidation;
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

@Path("/financeiro/tipo/documento")
public class TipoDocumentoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private TipoDocumentoService tipoDocumentoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        tipoDocumentoService = new TipoDocumentoService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoDocumento> findAll() {
        try {
            init();    
            return tipoDocumentoService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idTipoDocumento}")
    @Produces(MediaType.APPLICATION_JSON)
    public TipoDocumento find(@PathParam("idTipoDocumento") Integer idTipoDocumento) {
        try {
            init();    
            return tipoDocumentoService.find(idTipoDocumento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/descricao/{descricao}")
    @Produces(MediaType.APPLICATION_JSON)
    public TipoDocumento findByDescricao(@PathParam("descricao") String descricao) {
        try {
            init();    
            return tipoDocumentoService.findByDescricao(descricao);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TipoDocumento save(TipoDocumento tipoDocumento) {
        try {
            init();
            validation(tipoDocumento);
            return tipoDocumentoService.save(tipoDocumento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idTipoDocumento}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TipoDocumento update(TipoDocumento tipoDocumento) {
        try {
            init();
            validation(tipoDocumento);
            return tipoDocumentoService.update(tipoDocumento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idTipoDocumento}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TipoDocumento delete(@PathParam("idTipoDocumento") Integer idTipoDocumento) {
        try {
            init();
            return tipoDocumentoService.delete(idTipoDocumento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(TipoDocumento tipoDocumento) throws Exception {  
        Validation validacao = new TipoDocumentoValidation();
        if(!validacao.validar(tipoDocumento)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServTipoDocumento", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

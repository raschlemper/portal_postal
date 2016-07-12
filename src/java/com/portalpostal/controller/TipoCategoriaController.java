package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.TipoCategoria;
import com.portalpostal.service.TipoCategoriaService;
import com.portalpostal.validation.TipoCategoriaValidation;
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

@Path("/financeiro/tipo/categoria")
public class TipoCategoriaController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private TipoCategoriaService tipoCategoriaService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        tipoCategoriaService = new TipoCategoriaService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TipoCategoria> findAll() {
        try {
            init();    
            return tipoCategoriaService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idTipoCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public TipoCategoria find(@PathParam("idTipoCategoria") Integer idTipoCategoria) {
        try {
            init();    
            return tipoCategoriaService.find(idTipoCategoria);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/descricao/{descricao}")
    @Produces(MediaType.APPLICATION_JSON)
    public TipoCategoria findByDescricao(@PathParam("descricao") String descricao) {
        try {
            init();    
            return tipoCategoriaService.findByDescricao(descricao);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TipoCategoria save(TipoCategoria tipoCategoria) {
        try {
            init();
            validation(tipoCategoria);
            return tipoCategoriaService.save(tipoCategoria);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idTipoCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TipoCategoria update(TipoCategoria tipoCategoria) {
        try {
            init();
            validation(tipoCategoria);
            return tipoCategoriaService.update(tipoCategoria);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idTipoCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TipoCategoria delete(@PathParam("idTipoCategoria") Integer idTipoCategoria) {
        try {
            init();
            return tipoCategoriaService.delete(idTipoCategoria);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(TipoCategoria tipoCategoria) throws Exception {  
        Validation validacao = new TipoCategoriaValidation();
        if(!validacao.validar(tipoCategoria)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServTipoCategoria", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

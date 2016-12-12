package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.LancamentoConciliado;
import com.portalpostal.service.LancamentoConciliadoService;
import com.portalpostal.validation.LancamentoConciliadoValidation;
import java.text.SimpleDateFormat;
import java.util.Date;
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

@Path("/financeiro/lancamento/conciliado")
public class LancamentoConciliadoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    private String usuario;
    
    private LancamentoConciliadoService lancamentoConciliadoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        usuario = (String) sessao.getAttribute("usuario");  
        lancamentoConciliadoService = new LancamentoConciliadoService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LancamentoConciliado> findAll() {
        try {
            init();    
            return lancamentoConciliadoService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/data/{data}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LancamentoConciliado> findByData(@PathParam("data") String data) {
        try {
            init();    
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date dataCorte = format.parse(data);
            return lancamentoConciliadoService.findByData(dataCorte);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idLancamentoConciliado}")
    @Produces(MediaType.APPLICATION_JSON)
    public LancamentoConciliado find(@PathParam("idLancamentoConciliado") Integer idLancamentoConciliado) {
        try {
            init();    
            return lancamentoConciliadoService.find(idLancamentoConciliado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @GET
    @Path("/conta/{idConta}/last")
    @Produces(MediaType.APPLICATION_JSON)
    public LancamentoConciliado findLastByConta(@PathParam("idConta") Integer idConta) {
        try {
            init();    
            return lancamentoConciliadoService.findLastByConta(idConta);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoConciliado save(LancamentoConciliado lancamentoConciliado) {
        try {
            init();
            validation(lancamentoConciliado);
            lancamentoConciliado.setUsuario(usuario);
            return lancamentoConciliadoService.save(lancamentoConciliado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idLancamentoConciliado}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoConciliado update(LancamentoConciliado lancamentoConciliado) {
        try {
            init();
            validation(lancamentoConciliado);
            lancamentoConciliado.setUsuario(usuario);
            return lancamentoConciliadoService.update(lancamentoConciliado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoConciliado create(LancamentoConciliado lancamentoConciliado) {
        try {
            init();
            validation(lancamentoConciliado);
            lancamentoConciliado.setUsuario(usuario);
            return lancamentoConciliadoService.createLancamento(lancamentoConciliado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idLancamentoConciliado}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoConciliado delete(@PathParam("idLancamentoConciliado") Integer idLancamentoConciliado) {
        try {
            init();
            return lancamentoConciliadoService.delete(idLancamentoConciliado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(LancamentoConciliado lancamentoConciliado) throws Exception {  
        Validation validacao = new LancamentoConciliadoValidation();
        if(!validacao.validar(lancamentoConciliado)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServLancamentoConciliado", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

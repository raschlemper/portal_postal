package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.LancamentoProgramadoTransferencia;
import com.portalpostal.service.LancamentoProgramadoTransferenciaService;
import com.portalpostal.validation.LancamentoProgramadoTransferenciaValidation;
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

@Path("/financeiro/lancamento/programado/transferencia")
public class LancamentoProgramadoTransferenciaController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    private String usuario;
    
    private LancamentoProgramadoTransferenciaService lancamentoProgramadoTransferenciaService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        usuario = (String) sessao.getAttribute("usuario");  
        lancamentoProgramadoTransferenciaService = new LancamentoProgramadoTransferenciaService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LancamentoProgramadoTransferencia> findAll() {
        try {
            init();    
            return lancamentoProgramadoTransferenciaService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idLancamentoProgramadoTransferencia}")
    @Produces(MediaType.APPLICATION_JSON)
    public LancamentoProgramadoTransferencia find(@PathParam("idLancamentoProgramadoTransferencia") Integer idLancamentoProgramadoTransferencia) {
        try {
            init();    
            return lancamentoProgramadoTransferenciaService.find(idLancamentoProgramadoTransferencia);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoProgramadoTransferencia save(LancamentoProgramadoTransferencia lancamentoProgramadoTransferencia) {
        try {
            init();
            validation(lancamentoProgramadoTransferencia);
            lancamentoProgramadoTransferencia.setUsuario(usuario);
            return lancamentoProgramadoTransferenciaService.save(lancamentoProgramadoTransferencia);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idLancamentoProgramadoTransferencia}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoProgramadoTransferencia update(LancamentoProgramadoTransferencia lancamentoProgramadoTransferencia) {
        try {
            init();
            validation(lancamentoProgramadoTransferencia);
            lancamentoProgramadoTransferencia.setUsuario(usuario);
            return lancamentoProgramadoTransferenciaService.update(lancamentoProgramadoTransferencia);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idLancamentoProgramadoTransferencia}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoProgramadoTransferencia delete(@PathParam("idLancamentoProgramadoTransferencia") Integer idLancamentoProgramadoTransferencia) {
        try {
            init();
            return lancamentoProgramadoTransferenciaService.delete(idLancamentoProgramadoTransferencia);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(LancamentoProgramadoTransferencia lancamentoProgramadoTransferencia) throws Exception {  
        Validation validacao = new LancamentoProgramadoTransferenciaValidation();
        if(!validacao.validar(lancamentoProgramadoTransferencia)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServLancamentoProgramadoTransferencia", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

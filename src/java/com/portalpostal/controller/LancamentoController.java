package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.PlanoContaSaldo;
import com.portalpostal.service.LancamentoService;
import com.portalpostal.validation.LancamentoValidation;
import java.util.Calendar;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/financeiro/lancamento")
public class LancamentoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private LancamentoService lancamentoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        lancamentoService = new LancamentoService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lancamento> findAll() {
        try {
            init();    
            return lancamentoService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }    
    
    @GET
    @Path("/planoconta/saldo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlanoContaSaldo> findPlanoContaSaldo(@QueryParam("ano") Integer ano, 
            @QueryParam("mesInicio") Integer mesInicio, @QueryParam("mesFim") Integer mesFim) {
        try {
            init();    
            if(ano == null) { ano = Calendar.getInstance().get(Calendar.YEAR); }
            if(mesInicio == null) { mesInicio = 1; }
            if(mesFim == null) { mesFim = 12; }
            return lancamentoService.findPlanoContaSaldo(ano, mesInicio, mesFim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/tipo/{tipo}/saldo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lancamento> findSaldoByTipo(@PathParam("tipo") Integer tipo, @QueryParam("ano") Integer ano, 
            @QueryParam("mesInicio") Integer mesInicio, @QueryParam("mesFim") Integer mesFim) {
        try {
            init();    
            if(ano == null) { ano = Calendar.getInstance().get(Calendar.YEAR); }
            if(mesInicio == null) { mesInicio = 1; }
            if(mesFim == null) { mesFim = 12; }
            return lancamentoService.findSaldoByTipo(tipo, ano, mesInicio, mesFim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @GET
    @Path("/anos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Integer> findYearFromLancamento() {
        try {
            init();    
            return lancamentoService.findYearFromLancamento();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @GET
    @Path("/{idLancamento}")
    @Produces(MediaType.APPLICATION_JSON)
    public Lancamento find(@PathParam("idLancamento") Integer idLancamento) {
        try {
            init();    
            return lancamentoService.find(idLancamento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Lancamento save(Lancamento lancamento) {
        try {
            init();
            validation(lancamento);
            return lancamentoService.save(lancamento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idLancamento}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Lancamento update(Lancamento lancamento) {
        try {
            init();
            validation(lancamento);
            return lancamentoService.update(lancamento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idLancamento}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Lancamento delete(@PathParam("idLancamento") Integer idLancamento) {
        try {
            init();
            return lancamentoService.delete(idLancamento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(Lancamento lancamento) throws Exception {  
        Validation validacao = new LancamentoValidation();
        if(!validacao.validar(lancamento)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServLancamento", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

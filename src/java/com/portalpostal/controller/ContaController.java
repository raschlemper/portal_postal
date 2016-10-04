package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.Conta;
import com.portalpostal.service.ContaService;
import com.portalpostal.service.LancamentoService;
import com.portalpostal.validation.ContaValidation;
import java.text.ParseException;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/financeiro/conta")
public class ContaController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private ContaService contaService;
    private LancamentoService lancamentoService; 

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        contaService = new ContaService(nomeBD);
        lancamentoService = new LancamentoService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Conta> findAll() {
        try {
            init();    
            return contaService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/visivel")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Conta> findAllVisivel() {
        try {
            init();    
            return contaService.findAllVisivel();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/saldo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Conta> findSaldo() {
        try {
            init();    
            return contaService.findSaldo();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idConta}/saldo/lancamento")
    @Produces(MediaType.APPLICATION_JSON)
    public Conta findSaldoLancamento(@PathParam("idConta") Integer idConta,
            @QueryParam("data") String data) {
        try {
            init(); 
            return contaService.findSaldoLancamento(idConta, formatDate(data));
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idConta}")
    @Produces(MediaType.APPLICATION_JSON)
    public Conta find(@PathParam("idConta") Integer idConta) {
        try {
            init();    
            return contaService.find(idConta);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idConta}/lancamento")
    @Produces(MediaType.APPLICATION_JSON)
    public Conta findLancamento(@PathParam("idConta") Integer idConta, 
            @QueryParam("dataInicio") String dataInicio, @QueryParam("dataFim") String dataFim) {
        try {
            init();  
            Date inicio = formatDate(dataInicio);
            Date fim = formatDate(dataFim);   
            return contaService.findLancamento(idConta, inicio, fim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idConta}/lancamento/programado")
    @Produces(MediaType.APPLICATION_JSON)
    public Conta findLancamentoProgramado(@PathParam("idConta") Integer idConta, 
            @QueryParam("dataInicio") String dataInicio, @QueryParam("dataFim") String dataFim) {
        try {
            init();   
            Date inicio = formatDate(dataInicio);
            Date fim = formatDate(dataFim);    
            return contaService.findLancamentoProgramado(idConta, inicio, fim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Conta save(Conta conta) {
        try {
            init();
            validation(conta);
            return contaService.save(conta);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idConta}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Conta update(Conta conta) {
        try {
            init();
            validation(conta);
            return contaService.update(conta);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idConta}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Conta delete(@PathParam("idConta") Integer idConta) {
        try {
            init();
            return contaService.delete(idConta);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(Conta conta) throws Exception {  
        Validation validacao = new ContaValidation();
        if(!validacao.validar(conta)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServConta", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
        
    private Date formatDate(String data) throws ParseException {
        if(data == null) return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(data);
    }
}

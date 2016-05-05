package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.CarteiraCobranca;
import com.portalpostal.service.CarteiraCobrancaService;
import com.portalpostal.validation.CarteiraCobrancaValidation;
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

@Path("/financeiro/carteiracobranca")
public class CarteiraCobrancaController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private CarteiraCobrancaService carteiraCobrancaService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        carteiraCobrancaService = new CarteiraCobrancaService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CarteiraCobranca> findAll() {
        try {
            init();    
            return carteiraCobrancaService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idCarteiraCobranca}")
    @Produces(MediaType.APPLICATION_JSON)
    public CarteiraCobranca find(@PathParam("idCarteiraCobranca") Integer idCarteiraCobranca) {
        try {
            init();    
            return carteiraCobrancaService.find(idCarteiraCobranca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @GET
    @Path("/{idCarteiraCobranca}/conta")
    @Produces(MediaType.APPLICATION_JSON)
    public CarteiraCobranca findConta(@PathParam("idCarteiraCobranca") Integer idCarteiraCobranca) {
        try {
            init();    
            return carteiraCobrancaService.findConta(idCarteiraCobranca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @GET
    @Path("/{idContaCorrente}/beneficiario/{beneficiario}/{beneficiarioDv}/carteira/{carteira}")
    @Produces(MediaType.APPLICATION_JSON)
    public CarteiraCobranca findByCarteiraCobranca(@PathParam("idContaCorrente") Integer idContaCorrente, 
            @PathParam("beneficiario") Integer beneficiario, @PathParam("beneficiarioDv") Integer beneficiarioDv, 
            @PathParam("carteira") Integer carteira) {
        try {
            init();  
            return carteiraCobrancaService.findByCarteiraCobranca(idContaCorrente, beneficiario, beneficiarioDv, carteira);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CarteiraCobranca save(CarteiraCobranca carteiraCobranca) {
        try {
            init();
            validation(carteiraCobranca);
            return carteiraCobrancaService.save(carteiraCobranca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idCarteiraCobranca}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CarteiraCobranca update(CarteiraCobranca carteiraCobranca) {
        try {
            init();
            validation(carteiraCobranca);
            return carteiraCobrancaService.update(carteiraCobranca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idCarteiraCobranca}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CarteiraCobranca delete(@PathParam("idCarteiraCobranca") Integer idCarteiraCobranca) {
        try {
            init();
            return carteiraCobrancaService.delete(idCarteiraCobranca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(CarteiraCobranca carteiraCobranca) throws Exception {  
        Validation validacao = new CarteiraCobrancaValidation();
        if(!validacao.validar(carteiraCobranca)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServCarteiraCobranca", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

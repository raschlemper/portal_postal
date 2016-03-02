package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.VeiculoMulta;
import com.portalpostal.service.VeiculoMultaService;
import com.portalpostal.validation.VeiculoMultaValidation;
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

@Path("/veiculo/multa")
public class VeiculoMultaController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private VeiculoMultaService veiculoMultaService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        veiculoMultaService = new VeiculoMultaService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VeiculoMulta> findAll() {
        try {
            init();    
            return veiculoMultaService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idVeiculoMulta}")
    @Produces(MediaType.APPLICATION_JSON)
    public VeiculoMulta find(@PathParam("idVeiculoMulta") Integer idVeiculoManutenca) {
        try {
            init();    
            return veiculoMultaService.find(idVeiculoManutenca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VeiculoMulta save(VeiculoMulta veiculo) {
        try {
            init();
            validation(veiculo);
            return veiculoMultaService.save(veiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idVeiculoMulta}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VeiculoMulta update(VeiculoMulta veiculo) {
        try {
            init();
            validation(veiculo);
            return veiculoMultaService.update(veiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idVeiculoMulta}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VeiculoMulta delete(@PathParam("idVeiculoMulta") Integer idVeiculoManutenca) {
        try {
            init();
            return veiculoMultaService.delete(idVeiculoManutenca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(VeiculoMulta veiculo) throws Exception {  
        Validation validacao = new VeiculoMultaValidation();
        if(!validacao.validar(veiculo)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServVeiculo", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

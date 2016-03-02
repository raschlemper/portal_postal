package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.VeiculoSeguro;
import com.portalpostal.service.VeiculoSeguroService;
import com.portalpostal.validation.VeiculoSeguroValidation;
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

@Path("/veiculo/seguro")
public class VeiculoSeguroController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private VeiculoSeguroService veiculoSeguroService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        veiculoSeguroService = new VeiculoSeguroService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VeiculoSeguro> findAll() {
        try {
            init();    
            return veiculoSeguroService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idVeiculoSeguro}")
    @Produces(MediaType.APPLICATION_JSON)
    public VeiculoSeguro find(@PathParam("idVeiculoSeguro") Integer idVeiculoManutenca) {
        try {
            init();    
            return veiculoSeguroService.find(idVeiculoManutenca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VeiculoSeguro save(VeiculoSeguro veiculo) {
        try {
            init();
            validation(veiculo);
            return veiculoSeguroService.save(veiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idVeiculoSeguro}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VeiculoSeguro update(VeiculoSeguro veiculo) {
        try {
            init();
            validation(veiculo);
            return veiculoSeguroService.update(veiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idVeiculoSeguro}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VeiculoSeguro delete(@PathParam("idVeiculoSeguro") Integer idVeiculoManutenca) {
        try {
            init();
            return veiculoSeguroService.delete(idVeiculoManutenca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(VeiculoSeguro veiculo) throws Exception {  
        Validation validacao = new VeiculoSeguroValidation();
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

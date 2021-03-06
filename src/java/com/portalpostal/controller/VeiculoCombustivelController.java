package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.VeiculoCombustivel;
import com.portalpostal.service.VeiculoCombustivelService;
import com.portalpostal.validation.VeiculoCombustivelValidation;
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

@Path("/veiculo/combustivel")
public class VeiculoCombustivelController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private VeiculoCombustivelService veiculoCombustivelService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        veiculoCombustivelService = new VeiculoCombustivelService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VeiculoCombustivel> findAll() {
        try {
            init();    
            return veiculoCombustivelService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idVeiculoCombustivel}")
    @Produces(MediaType.APPLICATION_JSON)
    public VeiculoCombustivel find(@PathParam("idVeiculoCombustivel") Integer idVeiculoManutenca) {
        try {
            init();    
            return veiculoCombustivelService.find(idVeiculoManutenca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @GET
    @Path("/last/{idVeiculo}")
    @Produces(MediaType.APPLICATION_JSON)
    public VeiculoCombustivel findLastCombustivelByIdVeiculo(@PathParam("idVeiculo") Integer idVeiculo) {
        try {
            init();    
            return veiculoCombustivelService.findLastCombustivelByIdVeiculo(idVeiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VeiculoCombustivel save(VeiculoCombustivel veiculo) {
        try {
            init();
            validation(veiculo);
            return veiculoCombustivelService.save(veiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idVeiculoCombustivel}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VeiculoCombustivel update(VeiculoCombustivel veiculo) {
        try {
            init();
            validation(veiculo);
            return veiculoCombustivelService.update(veiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idVeiculoCombustivel}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VeiculoCombustivel delete(@PathParam("idVeiculoCombustivel") Integer idVeiculoManutenca) {
        try {
            init();
            return veiculoCombustivelService.delete(idVeiculoManutenca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(VeiculoCombustivel veiculo) throws Exception {  
        Validation validacao = new VeiculoCombustivelValidation();
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

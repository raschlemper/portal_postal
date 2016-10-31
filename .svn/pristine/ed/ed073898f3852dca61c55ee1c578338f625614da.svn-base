package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.VeiculoSinistro;
import com.portalpostal.service.VeiculoSinistroService;
import com.portalpostal.validation.VeiculoSinistroValidation;
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

@Path("/veiculo/sinistro")
public class VeiculoSinistroController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private VeiculoSinistroService veiculoSinistroService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        veiculoSinistroService = new VeiculoSinistroService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VeiculoSinistro> findAll() {
        try {
            init();    
            return veiculoSinistroService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idVeiculoSinistro}")
    @Produces(MediaType.APPLICATION_JSON)
    public VeiculoSinistro find(@PathParam("idVeiculoSinistro") Integer idVeiculoManutenca) {
        try {
            init();    
            return veiculoSinistroService.find(idVeiculoManutenca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VeiculoSinistro save(VeiculoSinistro veiculo) {
        try {
            init();
            validation(veiculo);
            return veiculoSinistroService.save(veiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idVeiculoSinistro}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VeiculoSinistro update(VeiculoSinistro veiculo) {
        try {
            init();
            validation(veiculo);
            return veiculoSinistroService.update(veiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idVeiculoSinistro}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public VeiculoSinistro delete(@PathParam("idVeiculoSinistro") Integer idVeiculoManutenca) {
        try {
            init();
            return veiculoSinistroService.delete(idVeiculoManutenca);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(VeiculoSinistro veiculo) throws Exception {  
        Validation validacao = new VeiculoSinistroValidation();
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

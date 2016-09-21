package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.PlanoConta;
import com.portalpostal.service.PlanoContaService;
import com.portalpostal.validation.PlanoContaValidation;
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

@Path("/financeiro/planoconta")
public class PlanoContaController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private PlanoContaService planoContaService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        planoContaService = new PlanoContaService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlanoConta> findAll() {
        try {
            init();    
            return planoContaService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/structure")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlanoConta> findStructure() {
        try {
            init();    
            return planoContaService.findStructure();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/tipo/{tipo}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlanoConta> findByTipo(@PathParam("tipo") Integer tipo) {
        try {
            init();    
            return planoContaService.findByTipo(tipo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @GET
    @Path("/tipo/{tipo}/resultado")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlanoConta> findContaResultadoByTipo(@PathParam("tipo") Integer tipo) {
        try {
            init();    
            return planoContaService.findContaResultadoByTipo(tipo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @GET
    @Path("/tipo/{tipo}/structure")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PlanoConta> findStructureByTipo(@PathParam("tipo") Integer tipo) {
        try {
            init();    
            return planoContaService.findStructureByTipo(tipo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/tipo/{tipo}/grupo/{grupo}/codigo/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public PlanoConta findByTipoGrupoCodigo(@PathParam("tipo") Integer tipo, 
            @PathParam("grupo") Integer grupo, @PathParam("codigo") Integer codigo) {
        try {
            init();    
            return planoContaService.findByTipoGrupoCodigo(tipo, grupo, codigo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }    
    
    @GET
    @Path("/{idPlanoConta}")
    @Produces(MediaType.APPLICATION_JSON)
    public PlanoConta find(@PathParam("idPlanoConta") Integer idPlanoConta) {
        try {
            init();    
            return planoContaService.find(idPlanoConta);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @GET
    @Path("/{idPlanoConta}/lancamento")
    @Produces(MediaType.APPLICATION_JSON)
    public PlanoConta findLancamento(@PathParam("idPlanoConta") Integer idPlanoConta) {
        try {
            init();    
            return planoContaService.findLancamento(idPlanoConta);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }
    
    @GET
    @Path("/{idPlanoConta}/lancamento/programado")
    @Produces(MediaType.APPLICATION_JSON)
    public PlanoConta findLancamentoProgramado(@PathParam("idPlanoConta") Integer idPlanoConta) {
        try {
            init();    
            return planoContaService.findLancamentoProgramado(idPlanoConta);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PlanoConta save(PlanoConta planoConta) {
        try {
            init();
            validation(planoConta);
            return planoContaService.save(planoConta);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idPlanoConta}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PlanoConta update(PlanoConta planoConta) {
        try {
            init();
            validation(planoConta);
            return planoContaService.update(planoConta);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idPlanoConta}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PlanoConta delete(@PathParam("idPlanoConta") Integer idPlanoConta) {
        try {
            init();
            return planoContaService.delete(idPlanoConta);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @GET
    @Path("/default")
    @Consumes(MediaType.APPLICATION_JSON)
    public void setDefault() {
        try {
            init();
            planoContaService.setDefault();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(PlanoConta planoConta) throws Exception {  
        Validation validacao = new PlanoContaValidation();
        if(!validacao.validar(planoConta)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServPlanoConta", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

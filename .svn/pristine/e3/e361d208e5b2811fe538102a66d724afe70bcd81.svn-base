package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.CentroCusto;
import com.portalpostal.service.CentroCustoService;
import com.portalpostal.validation.CentroCustoValidation;
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

@Path("/financeiro/centrocusto")
public class CentroCustoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private CentroCustoService centroCustoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        centroCustoService = new CentroCustoService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CentroCusto> findAll() {
        try {
            init();    
            return centroCustoService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/structure")
    @Produces(MediaType.APPLICATION_JSON)
    public List<CentroCusto> findStructure() {
        try {
            init();    
            return centroCustoService.findStructure();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
//    @GET
//    @Path("/tipo/{tipo}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<CentroCusto> findByTipo(@PathParam("tipo") Integer tipo) {
//        try {
//            init();    
//            return centroCustoService.findByTipo(tipo);
//        } catch (Exception ex) {
//            throw new WebApplicationException(getMessageError(ex.getMessage()));
//        }
//    }   
    
//    @GET
//    @Path("/tipo/{tipo}/resultado")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<CentroCusto> findContaResultadoByTipo(@PathParam("tipo") Integer tipo) {
//        try {
//            init();    
//            return centroCustoService.findContaResultadoByTipo(tipo);
//        } catch (Exception ex) {
//            throw new WebApplicationException(getMessageError(ex.getMessage()));
//        }
//    }   
    
//    @GET
//    @Path("/tipo/{tipo}/structure")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<CentroCusto> findStructureByTipo(@PathParam("tipo") Integer tipo) {
//        try {
//            init();    
//            return centroCustoService.findStructureByTipo(tipo);
//        } catch (Exception ex) {
//            throw new WebApplicationException(getMessageError(ex.getMessage()));
//        }
//    }  
    
    @GET
    @Path("/grupo/{grupo}/codigo/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public CentroCusto findByGrupoCodigo(@PathParam("grupo") Integer grupo, @PathParam("codigo") Integer codigo) {
        try {
            init();    
            return centroCustoService.findByGrupoCodigo(grupo, codigo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }    
    
    @GET
    @Path("/{idCentroCusto}")
    @Produces(MediaType.APPLICATION_JSON)
    public CentroCusto find(@PathParam("idCentroCusto") Integer idCentroCusto) {
        try {
            init();    
            return centroCustoService.find(idCentroCusto);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @GET
    @Path("/{idCentroCusto}/lancamento")
    @Produces(MediaType.APPLICATION_JSON)
    public CentroCusto findLancamento(@PathParam("idCentroCusto") Integer idCentroCusto) {
        try {
            init();    
            return centroCustoService.findLancamento(idCentroCusto);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }
    
    @GET
    @Path("/{idCentroCusto}/lancamento/programado")
    @Produces(MediaType.APPLICATION_JSON)
    public CentroCusto findLancamentoProgramado(@PathParam("idCentroCusto") Integer idCentroCusto) {
        try {
            init();    
            return centroCustoService.findLancamentoProgramado(idCentroCusto);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CentroCusto save(CentroCusto centroCusto) {
        try {
            init();
            validation(centroCusto);
            return centroCustoService.save(centroCusto);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idCentroCusto}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CentroCusto update(CentroCusto centroCusto) {
        try {
            init();
            validation(centroCusto);
            return centroCustoService.update(centroCusto);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idCentroCusto}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public CentroCusto delete(@PathParam("idCentroCusto") Integer idCentroCusto) {
        try {
            init();
            return centroCustoService.delete(idCentroCusto);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(CentroCusto centroCusto) throws Exception {  
        Validation validacao = new CentroCustoValidation();
        if(!validacao.validar(centroCusto)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServCentroCusto", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

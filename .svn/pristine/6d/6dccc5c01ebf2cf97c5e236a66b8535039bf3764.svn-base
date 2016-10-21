package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.validation.VeiculoValidation;
import com.portalpostal.model.Veiculo;
import com.portalpostal.model.VeiculoCombustivel;
import com.portalpostal.model.VeiculoManutencao;
import com.portalpostal.model.VeiculoMulta;
import com.portalpostal.model.VeiculoSeguro;
import com.portalpostal.model.VeiculoSinistro;
import com.portalpostal.service.VeiculoCombustivelService;
import com.portalpostal.service.VeiculoManutencaoService;
import com.portalpostal.service.VeiculoMultaService;
import com.portalpostal.service.VeiculoSeguroService;
import com.portalpostal.service.VeiculoService;
import com.portalpostal.service.VeiculoSinistroService;
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

@Path("/veiculo")
public class VeiculoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private VeiculoService veiculoService;
    private VeiculoCombustivelService veiculoCombustivelService;
    private VeiculoManutencaoService veiculoManutencaoService;
    private VeiculoMultaService veiculoMultaService;
    private VeiculoSeguroService veiculoSeguroService;
    private VeiculoSinistroService veiculoSinistroService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        veiculoService = new VeiculoService(nomeBD);
        veiculoCombustivelService = new VeiculoCombustivelService(nomeBD);
        veiculoManutencaoService = new VeiculoManutencaoService(nomeBD);
        veiculoMultaService = new VeiculoMultaService(nomeBD);
        veiculoSeguroService = new VeiculoSeguroService(nomeBD);
        veiculoSinistroService = new VeiculoSinistroService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Veiculo> findAll() {
        try {
            init();    
            return veiculoService.findAll();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idVeiculo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Veiculo find(@PathParam("idVeiculo") Integer idVeiculo) {
        try {
            init();    
            return veiculoService.find(idVeiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/placa/{placa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Veiculo findByPlaca(@PathParam("placa") String placa) {
        try {
            init();    
            return veiculoService.findByPlaca(placa);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idVeiculo}/combustivel")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VeiculoCombustivel> findCombustivelByIdVeiculo(@PathParam("idVeiculo") Integer idVeiculo) {
        try {
            init();    
            return veiculoCombustivelService.findByIdVeiculo(idVeiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @GET
    @Path("/{idVeiculo}/manutencao")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VeiculoManutencao> findManutencaoByIdVeiculo(@PathParam("idVeiculo") Integer idVeiculo) {
        try {
            init();    
            return veiculoManutencaoService.findByIdVeiculo(idVeiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }    
    
    @GET
    @Path("/{idVeiculo}/multa")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VeiculoMulta> findMultaByIdVeiculo(@PathParam("idVeiculo") Integer idVeiculo) {
        try {
            init();    
            return veiculoMultaService.findByIdVeiculo(idVeiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }    
    
    @GET
    @Path("/{idVeiculo}/seguro")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VeiculoSeguro> findSeguroByIdVeiculo(@PathParam("idVeiculo") Integer idVeiculo) {
        try {
            init();    
            return veiculoSeguroService.findByIdVeiculo(idVeiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }     
    
    @GET
    @Path("/{idVeiculo}/sinistro")
    @Produces(MediaType.APPLICATION_JSON)
    public List<VeiculoSinistro> findSinistroByIdVeiculo(@PathParam("idVeiculo") Integer idVeiculo) {
        try {
            init();    
            return veiculoSinistroService.findByIdVeiculo(idVeiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Veiculo save(Veiculo veiculo) {
        try {
            init();
            validation(veiculo);
            return veiculoService.save(veiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idVeiculo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Veiculo update(Veiculo veiculo) {
        try {
            init();
            validation(veiculo);
            return veiculoService.update(veiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idVeiculo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Veiculo delete(@PathParam("idVeiculo") Integer idVeiculo) {
        try {
            init();
            return veiculoService.delete(idVeiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(Veiculo veiculo) throws Exception {  
        Validation validacao = new VeiculoValidation();
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

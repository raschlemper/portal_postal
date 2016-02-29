package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.model.Veiculo;
import com.portalpostal.dao.VeiculoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

@Path("/veiculo")
public class VeiculoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private VeiculoDAO veiculoDAO;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        veiculoDAO = new VeiculoDAO(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Veiculo> getAll() {
        init();    
        List<Veiculo> listaVeiculos = new ArrayList<Veiculo>();
        try {
            listaVeiculos = veiculoDAO.consultaTodos(nomeBD);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex));
        }
        return listaVeiculos;
    }  
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Veiculo get(@PathParam("id") Integer id) {
        init();    
        Veiculo veiculo = new Veiculo();
        try {
            veiculo = veiculoDAO.consulta(nomeBD, id);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex));
        }
        return veiculo;
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Veiculo save(Veiculo veiculo) {
        init();
        try {
            veiculo = veiculoDAO.inserir(nomeBD, veiculo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex));
        }
        return veiculo;
    } 
    
    private Response getMessageError(Exception ex) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServVeiculo", "Exception", null, ex.toString());
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                    .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

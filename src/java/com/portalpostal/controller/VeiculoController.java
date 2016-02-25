package com.portalpostal.controller;

import Veiculo.Controle.ContrVeiculo;
import Veiculo.Entidade.Veiculo;
import Veiculo.Entidade.VeiculoDTO;
import Veiculo.builder.VeiculoBuilder;
import com.portalpostal.dao.VeiculoDAO;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/veiculo")
public class VeiculoController {
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Veiculo> get(@Context HttpServletRequest request) throws Exception {
        HttpSession sessao = request.getSession();        
        List<Veiculo> listaVeiculos = VeiculoDAO.consultaTodos((String) sessao.getAttribute("nomeBD"));
        List<VeiculoDTO> listaDTO = new ArrayList<VeiculoDTO>();
        for (Veiculo veiculo : listaVeiculos) { listaDTO.add(getVeiculoDTO(veiculo)); }
        return listaVeiculos;
    }    
    
    private VeiculoDTO getVeiculoDTO(Veiculo veiculo) throws Exception {
        VeiculoBuilder builder = new VeiculoBuilder();
        return builder.toDTO(veiculo);
    }
}

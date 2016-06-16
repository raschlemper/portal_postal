package com.portalpostal.filter;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class AppFilter implements ContainerRequestFilter {
    
    @Context
    private HttpServletRequest request;    
    private HttpSession sessao;

    public ContainerRequest filter(ContainerRequest containerRequest) {         
        this.sessao = request.getSession(); 
        try {
            sessao.setAttribute("nomeBD", "06895434000183");
            sessao.setAttribute("usuario", "test");  
            
            empresaFilter();
            usuarioFilter();
        } catch(Exception ex) {
            throw new WebApplicationException(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .type(MediaType.TEXT_PLAIN).entity(ex.getMessage()).build());
        }
        return containerRequest;
    }
    
    private void empresaFilter() throws Exception {
        String empresa = (String) sessao.getAttribute("empresa");  
        if(empresa == null) { 
            throw new Exception("Nenhuma empresa existente.");
        }
    }
    
    private void usuarioFilter() throws Exception {
        String usuario = (String) sessao.getAttribute("usuario");  
        if(usuario == null) { 
            throw new Exception("Nenhum usuário existente.");
        }
    }
    
}

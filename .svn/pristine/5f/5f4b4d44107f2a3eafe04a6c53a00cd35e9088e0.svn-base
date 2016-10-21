package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.ConfiguracaoFinanceiro;
import com.portalpostal.service.ConfiguracaoService;
import com.portalpostal.validation.ConfiguracaoFinanceiroValidation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/configuracao")
public class ConfiguracaoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private ConfiguracaoService configuracaoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        configuracaoService = new ConfiguracaoService(nomeBD);
    }
    
    @GET
    @Path("/financeiro/")
    @Produces(MediaType.APPLICATION_JSON)
    public ConfiguracaoFinanceiro findFinanceiro() {
        try {
            init();    
            return configuracaoService.findFinanceiro();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/financeiro/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ConfiguracaoFinanceiro saveFinanceiro(ConfiguracaoFinanceiro configuracao) {
        try {
            init();
            validation(configuracao);
            return configuracaoService.saveFinanceiro(configuracao);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(ConfiguracaoFinanceiro configuracao) throws Exception {  
        Validation validacao = new ConfiguracaoFinanceiroValidation();
        if(!validacao.validar(configuracao)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServConfiguracao", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
}

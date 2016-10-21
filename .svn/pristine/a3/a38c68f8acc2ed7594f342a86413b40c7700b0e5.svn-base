package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.model.Lancamento;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.LancamentoProgramado;
import com.portalpostal.model.dd.TipoModeloLancamento;
import com.portalpostal.service.LancamentoProgramadoService;
import com.portalpostal.service.LancamentoService;
import com.portalpostal.validation.LancamentoProgramadoValidation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/financeiro/lancamento/programado")
public class LancamentoProgramadoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    private String usuario;
    
    private LancamentoService lancamentoService;
    private LancamentoProgramadoService lancamentoProgramadoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        usuario = (String) sessao.getAttribute("usuario");  
        lancamentoService = new LancamentoService(nomeBD);
        lancamentoProgramadoService = new LancamentoProgramadoService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LancamentoProgramado> findAll(@QueryParam("dataInicio") String dataInicio, 
            @QueryParam("dataFim") String dataFim) {
        try {
            init();    
            Date inicio = formatDate(dataInicio);
            Date fim = formatDate(dataFim);   
            return lancamentoProgramadoService.findAll(inicio, fim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @GET
    @Path("/ativo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LancamentoProgramado> findAllAtivo(@QueryParam("dataInicio") String dataInicio, 
            @QueryParam("dataFim") String dataFim) {
        try {
            init();  
            Date inicio = formatDate(dataInicio);
            Date fim = formatDate(dataFim);   
            return lancamentoProgramadoService.findAllAtivo(inicio, fim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idLancamentoProgramado}")
    @Produces(MediaType.APPLICATION_JSON)
    public LancamentoProgramado find(@PathParam("idLancamentoProgramado") Integer idLancamentoProgramado) {
        try {
            init();    
            LancamentoProgramado lancamentoProgramado = lancamentoProgramadoService.find(idLancamentoProgramado);
            if(lancamentoProgramado.getModelo() == TipoModeloLancamento.TRANSFERENCIA_PROGRAMADO) {
                return lancamentoProgramadoService.findTransferencia(idLancamentoProgramado);
            }
            return lancamentoProgramado;
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @GET
    @Path("/{idLancamentoProgramado}/lancamento")
    @Produces(MediaType.APPLICATION_JSON)
    public LancamentoProgramado findLancamento(@PathParam("idLancamentoProgramado") Integer idLancamentoProgramado) {
        try {
            init();    
            return lancamentoProgramadoService.findLancamento(idLancamentoProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idLancamentoProgramado}/last")
    @Produces(MediaType.APPLICATION_JSON)
    public Lancamento findLastByLancamentoProgramado(@PathParam("idLancamentoProgramado") Integer idLancamentoProgramado) {
        try {
            init();    
            return lancamentoService.findLastByLancamentoProgramado(idLancamentoProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idLancamentoProgramado}/parcela/{numeroParcela}")
    @Produces(MediaType.APPLICATION_JSON)
    public Lancamento findByNumeroParcela(@PathParam("idLancamentoProgramado") Integer idLancamentoProgramado, 
            @PathParam("numeroParcela") Integer numeroParcela) {
        try {
            init();    
            return lancamentoService.findByNumeroParcela(idLancamentoProgramado, numeroParcela);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoProgramado save(LancamentoProgramado lancamentoProgramado) {
        try {
            init();
            validation(lancamentoProgramado);
            lancamentoProgramado.setUsuario(usuario);
            return lancamentoProgramadoService.save(lancamentoProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idLancamentoProgramado}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoProgramado update(LancamentoProgramado lancamentoProgramado) {
        try {
            init();
            validation(lancamentoProgramado);
            lancamentoProgramado.setUsuario(usuario);
            return lancamentoProgramadoService.update(lancamentoProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoProgramado create(LancamentoProgramado lancamentoProgramado) {
        try {
            init();
            validation(lancamentoProgramado);
            lancamentoProgramado.setUsuario(usuario);
            return lancamentoProgramadoService.createLancamento(lancamentoProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idLancamentoProgramado}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoProgramado delete(@PathParam("idLancamentoProgramado") Integer idLancamentoProgramado) {
        try {
            init();
            return lancamentoProgramadoService.delete(idLancamentoProgramado);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteAll(List<LancamentoProgramado> lancamentosProgramados) {
        try {
            init();
            for (LancamentoProgramado lancamentoProgramado : lancamentosProgramados) {   
                lancamentoProgramadoService.delete(lancamentoProgramado.getIdLancamentoProgramado());
            }
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(LancamentoProgramado lancamentoProgramado) throws Exception {  
        Validation validacao = new LancamentoProgramadoValidation();
        if(!validacao.validar(lancamentoProgramado)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServLancamentoProgramado", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
        
    private Date formatDate(String data) throws ParseException {
        if(data == null) return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(data);
    }
}

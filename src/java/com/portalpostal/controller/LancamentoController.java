package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.Lancamento;
import com.portalpostal.model.LancamentoAnexo;
import com.portalpostal.model.Saldo;
import com.portalpostal.service.ImageService;
import com.portalpostal.service.LancamentoAnexoService;
import com.portalpostal.service.LancamentoService;
import com.portalpostal.validation.LancamentoAnexoValidation;
import com.portalpostal.validation.LancamentoValidation;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import java.io.InputStream;
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

@Path("/financeiro/lancamento")
public class LancamentoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    private String usuario;
    
    private LancamentoService lancamentoService;
    private LancamentoAnexoService lancamentoAnexoService;
    private ImageService imageService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        lancamentoService = new LancamentoService(nomeBD);
        usuario = (String) sessao.getAttribute("usuario");  
        lancamentoAnexoService = new LancamentoAnexoService(nomeBD);
        imageService = new ImageService();
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Lancamento> findAll(@QueryParam("dataInicio") String dataInicio, 
            @QueryParam("dataFim") String dataFim) {
        try {
            init();    
            Date inicio = formatDate(dataInicio);
            Date fim = formatDate(dataFim);
            return lancamentoService.findAll(inicio, fim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @GET
    @Path("/saldo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Saldo> findSaldo(@QueryParam("dataInicio") String dataInicio, 
            @QueryParam("dataFim") String dataFim) {
        try {
            init(); 
            Date inicio = formatDate(dataInicio);
            Date fim = formatDate(dataFim);
            return lancamentoService.findSaldo(inicio, fim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/planoconta/saldo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Saldo> findSaldoPlanoConta(@QueryParam("dataInicio") String dataInicio, 
            @QueryParam("dataFim") String dataFim) {
        try {
            init(); 
            Date inicio = formatDate(dataInicio);
            Date fim = formatDate(dataFim);
            return lancamentoService.findSaldoPlanoConta(inicio, fim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/centrocusto/saldo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Saldo> findSaldoCentroCusto(@QueryParam("dataInicio") String dataInicio, 
            @QueryParam("dataFim") String dataFim) {
        try {
            init(); 
            Date inicio = formatDate(dataInicio);
            Date fim = formatDate(dataFim);
            return lancamentoService.findSaldoCentroCusto(inicio, fim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @GET
    @Path("/planoconta/saldo/competencia")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Saldo> findSaldoPlanoContaCompetencia(@QueryParam("dataInicio") String dataInicio, 
            @QueryParam("dataFim") String dataFim) {
        try {
            init(); 
            Date inicio = formatDate(dataInicio);
            Date fim = formatDate(dataFim);
            return lancamentoService.findSaldoPlanoContaCompetencia(inicio, fim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }
    
    @GET
    @Path("/centrocusto/saldo/competencia")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Saldo> findSaldoCentroCustoCompetencia(@QueryParam("dataInicio") String dataInicio, 
            @QueryParam("dataFim") String dataFim) {
        try {
            init(); 
            Date inicio = formatDate(dataInicio);
            Date fim = formatDate(dataFim);
            return lancamentoService.findSaldoCentroCustoCompetencia(inicio, fim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }
    
    @GET
    @Path("/tipo/saldo")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Saldo> findSaldoTipo(@QueryParam("dataInicio") String dataInicio, 
            @QueryParam("dataFim") String dataFim) {
        try {
            init(); 
            Date inicio = formatDate(dataInicio);
            Date fim = formatDate(dataFim);
            return lancamentoService.findSaldoTipo(inicio, fim);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @GET
    @Path("/anos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Integer> findYearFromLancamento() {
        try {
            init();    
            return lancamentoService.findYearFromLancamento();
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @GET
    @Path("/{idLancamento}")
    @Produces(MediaType.APPLICATION_JSON)
    public Lancamento find(@PathParam("idLancamento") Integer idLancamento) {
        try {
            init();    
            return lancamentoService.find(idLancamento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Lancamento save(Lancamento lancamento) {
        try {
            init();
            validation(lancamento);
            lancamento.setUsuario(usuario);
            return lancamentoService.save(lancamento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idLancamento}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Lancamento update(Lancamento lancamento) {
        try {
            init();
            validation(lancamento);
            lancamento.setUsuario(usuario);
            return lancamentoService.update(lancamento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/situacao")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateSituacao(List<Lancamento> lancamentos) {
        try {
            init();
            for (Lancamento lancamento : lancamentos) {                 
                validation(lancamento);
                lancamento.setUsuario(usuario);
                lancamentoService.updateSituacao(lancamento);
            }
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateAll(List<Lancamento> lancamentos) {
        try {
            init();
            for (Lancamento lancamento : lancamentos) {                
                validation(lancamento);
                lancamento.setUsuario(usuario);
                lancamentoService.update(lancamento);
            }
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idLancamento}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Lancamento delete(@PathParam("idLancamento") Integer idLancamento) {
        try {
            init();
            return lancamentoService.delete(idLancamento);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteAll(List<Lancamento> lancamentos) {
        try {
            init();
            for (Lancamento lancamento : lancamentos) {   
                lancamentoService.delete(lancamento.getIdLancamento());
            }
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @POST
    @Path("/{idLancamento}/anexo")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public LancamentoAnexo upload(@PathParam("idLancamento") Integer idLancamento,
                                  @FormDataParam("file") InputStream fileInputString,
                                  @FormDataParam("file") FormDataContentDisposition fileInputDetails,
                                  @FormDataParam("file") FormDataBodyPart bodyPart) {
        try {
            init();             
            LancamentoAnexo lancamentoAnexo = new LancamentoAnexo();
            lancamentoAnexo.setLancamento(getLancamento(idLancamento));
            lancamentoAnexo.setNome(fileInputDetails.getFileName());
            lancamentoAnexo.setAnexo(
                    imageService.trataInputStream(bodyPart.getMediaType().getSubtype(), fileInputString, 200, 300));
            validation(lancamentoAnexo);
            return lancamentoAnexoService.save(lancamentoAnexo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private Lancamento getLancamento(Integer idLancamento) throws Exception {
        return lancamentoService.find(idLancamento);
    }
    
    private void validation(Lancamento lancamento) throws Exception {  
        Validation validacao = new LancamentoValidation();
        if(!validacao.validar(lancamento)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private void validation(LancamentoAnexo lancamentoAnexo) throws Exception {  
        Validation validacao = new LancamentoAnexoValidation();
        if(!validacao.validar(lancamentoAnexo)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    } 
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServLancamento", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    }
    
    private Date formatDate(String data) throws ParseException {
        if(data == null) return null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.parse(data);
    }
}

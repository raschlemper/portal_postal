package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.LancamentoAnexo;
import com.portalpostal.service.ImageService;
import com.portalpostal.service.LancamentoAnexoService;
import com.portalpostal.validation.LancamentoAnexoValidation;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.imageio.ImageIO;
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
import javax.ws.rs.core.StreamingOutput;
import sun.misc.BASE64Decoder;

@Path("/financeiro/lancamento/anexo")
public class LancamentoAnexoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    private String usuario;
    
    private LancamentoAnexoService lancamentoAnexoService;
    private ImageService imageService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        usuario = (String) sessao.getAttribute("usuario");  
        lancamentoAnexoService = new LancamentoAnexoService(nomeBD);
        imageService = new ImageService();
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LancamentoAnexo> findAll() {
        try {
            init();    
            List<LancamentoAnexo> anexos = lancamentoAnexoService.findAll();
//            for (LancamentoAnexo anexo : anexos) {
//                anexo.setFile(toStreamingOutput(anexo.getAnexo()));
//            }
            return anexos;
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idLancamentoAnexo}")
    @Produces(MediaType.APPLICATION_JSON)
    public LancamentoAnexo find(@PathParam("idLancamentoAnexo") Integer idLancamentoAnexo) {
        try {
            init();    
            LancamentoAnexo anexo = lancamentoAnexoService.find(idLancamentoAnexo);
            return anexo;
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }   
    
    @GET
    @Path("/{idLancamentoAnexo}/file")
    @Produces(MediaType.APPLICATION_JSON)
    public StreamingOutput findFile(@PathParam("idLancamentoAnexo") Integer idLancamentoAnexo) {
        try {
            init();    
            final LancamentoAnexo anexo = lancamentoAnexoService.find(idLancamentoAnexo);
            return toStreamingOutput(anexo.getAnexo());            
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @GET
    @Path("/{idLancamentoAnexo}/download/image")
    @Produces({"image/png", "image/jpg", "image/jpeg", "image/gif"})
    public Response downloadImage(@PathParam("idLancamentoAnexo") Integer idLancamentoAnexo) {
        try {
            init();    
            final LancamentoAnexo anexo = lancamentoAnexoService.find(idLancamentoAnexo);            
            return DownloadHandler.image(anexo.getAnexo(), anexo.getNome());
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @GET
    @Path("/{idLancamentoAnexo}/download/pdf")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadPdf(@PathParam("idLancamentoAnexo") Integer idLancamentoAnexo) {
        try {
            init();    
            final LancamentoAnexo anexo = lancamentoAnexoService.find(idLancamentoAnexo);            
            return DownloadHandler.pdf(anexo.getAnexo(), anexo.getNome());
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @GET
    @Path("/lancamento/{idLancamento}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LancamentoAnexo> findByLancamento(@PathParam("idLancamento") Integer idLancamento) {
        try {
            init();    
            List<LancamentoAnexo> anexos = lancamentoAnexoService.findByLancamento(idLancamento);
//            for (LancamentoAnexo anexo : anexos) {
//                anexo.setFile(toStreamingOutput(anexo.getAnexo()));
//            }
            return anexos;
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    }  
    
    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoAnexo save(LancamentoAnexo lancamentoAnexo) {
        try {
            init();
            validation(lancamentoAnexo);
            lancamentoAnexo.setUsuario(usuario);
            return lancamentoAnexoService.save(lancamentoAnexo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @PUT
    @Path("/{idLancamentoAnexo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoAnexo update(LancamentoAnexo lancamentoAnexo) {
        try {
            init();
            validation(lancamentoAnexo);
            lancamentoAnexo.setUsuario(usuario);
            return lancamentoAnexoService.update(lancamentoAnexo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @DELETE
    @Path("/{idLancamentoAnexo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LancamentoAnexo delete(@PathParam("idLancamentoAnexo") Integer idLancamentoAnexo) {
        try {
            init();
            return lancamentoAnexoService.delete(idLancamentoAnexo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    private void validation(LancamentoAnexo lancamentoAnexo) throws Exception {  
        Validation validacao = new LancamentoAnexoValidation();
        if(!validacao.validar(lancamentoAnexo)) {
            throw new WebApplicationException(getMessageError(validacao.getMsg()));
        } 
    }  
    
    private Response getMessageError(String msg) {  
        int idErro = ContrErroLog.inserir("Portal Postal - ServLancamentoAnexo", "Exception", null, msg);
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN)
                .entity("SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!").build();
    } 
    
    private byte[] toByte(InputStream file) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(file);        
    }
    
    private BufferedImage toImage(InputStream file) throws IOException {
        return ImageIO.read(file);       
    }
    
    private StreamingOutput toStreamingOutput(final InputStream file) throws IOException {
        return new StreamingOutput() {
            public void write(OutputStream output) throws IOException, WebApplicationException {
                output.write(toByte(file));
            }
        };      
    }
    
}

package com.portalpostal.controller;

import Controle.ContrErroLog;
import com.portalpostal.validation.Validation;
import com.portalpostal.model.LancamentoAnexo;
import com.portalpostal.service.LancamentoAnexoService;
import com.portalpostal.validation.LancamentoAnexoValidation;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@Path("/financeiro/lancamento/anexo")
public class LancamentoAnexoController {
    
    @Context
    private HttpServletRequest request;
    
    private HttpSession sessao;
    private String nomeBD;
    
    private LancamentoAnexoService lancamentoAnexoService;

    private void init() {
        sessao = request.getSession();
        nomeBD = (String) sessao.getAttribute("nomeBD");
        lancamentoAnexoService = new LancamentoAnexoService(nomeBD);
    }
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LancamentoAnexo> findAll() {
        try {
            init();    
            return lancamentoAnexoService.findAll();
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
            return lancamentoAnexoService.find(idLancamentoAnexo);
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
            return lancamentoAnexoService.save(lancamentoAnexo);
        } catch (Exception ex) {
            throw new WebApplicationException(getMessageError(ex.getMessage()));
        }
    } 
    
    @POST
    @Path("/upload")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.MULTIPART_FORM_DATA})
    public LancamentoAnexo upload(@FormDataParam("file") InputStream fileInputString,
                                  @FormDataParam("file") FormDataContentDisposition fileInputDetails) {
        try {
            init();
            String fileLocation = fileInputDetails.getFileName();
            String status = null;
            NumberFormat myFormat = NumberFormat.getInstance();
            myFormat.setGroupingUsed(true);

            // Save the file 
            try {
             OutputStream out = new FileOutputStream(new File(fileLocation));
             byte[] buffer = new byte[1024];
             int bytes = 0;
             long file_size = 0; 
             while ((bytes = fileInputString.read(buffer)) != -1) {
              out.write(buffer, 0, bytes);
              file_size += bytes;
             }
             out.flush();  
             out.close();

             status = "File has been uploaded to:" + fileLocation 
                         + ", size: " + myFormat.format(file_size) + " bytes";
            } catch (IOException ex) {
              ex.printStackTrace();
            }
            
            LancamentoAnexo lancamentoAnexo = new LancamentoAnexo();
            lancamentoAnexo.setLancamento(null);
//            lancamentoAnexo.setNome(fileMetaData.getName());
//            lancamentoAnexo.setAnexo(uploadedInputStream);
            validation(lancamentoAnexo);
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
}

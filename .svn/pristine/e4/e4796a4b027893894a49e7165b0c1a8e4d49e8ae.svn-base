/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Emporium.Controle.ContrPreVendaImporta;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Fernando
 */
public class ServImportacaoMovVisualHTML extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        String nomeBD = (String) sessao.getAttribute("nomeBD");

        boolean isMultiPart = FileUpload.isMultipartContent(request);

        String departamento = "", servico = "", tipo = "";
        int idCliente = 0, vd = 0;

        if (nomeBD != null) {
            if (isMultiPart) {
                try {
                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setSizeMax(1024 * 1024 * 2);

                    List items = upload.parseRequest(request);
                    Iterator iter = items.iterator();
                    ArrayList<FileItem> listaArq = new ArrayList<FileItem>();

                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();
                        if (item.isFormField()) {
                            if (item.getFieldName().equals("tipo")) {
                                tipo = item.getString();
                            }
                            if (item.getFieldName().equals("departamento")) {
                                departamento = item.getString();
                            }
                            if (item.getFieldName().equals("servico")) {
                                servico = item.getString();
                            }
                            if (item.getFieldName().equals("idCliente")) {
                                idCliente = Integer.parseInt(item.getString());
                            }
                            if (item.getFieldName().equals("vd")) {
                                vd = Integer.parseInt(item.getString());
                            }
                        }

                        if (!item.isFormField()) {
                            if (item.getName().length() > 0) {
                                listaArq.add(item);
                            }
                        }
                    }

                    ArrayList<String> listaCaminhos = inserirDiretorio(listaArq, nomeBD);
                    if (listaCaminhos == null || listaCaminhos.isEmpty()) {
                        response.sendRedirect("Cliente/Servicos/imp_postagem.jsp?msg=Escolha um arquivo para importacao !");
                    } else if (listaArq.size() > 200) {
                        response.sendRedirect("Cliente/Servicos/imp_postagem.jsp?msg=Importacao maxima de 200 arquivos de cada vez!");
                    } else {
                        String condicao = ContrPreVendaImporta.importaPedidoNFe(listaCaminhos, idCliente, departamento, servico, vd, nomeBD);
                        if (condicao.startsWith("ERRO")) {
                            response.sendRedirect("Cliente/Servicos/imp_postagem.jsp?msg=" + condicao);
                        } else {
                            response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                        }
                    }
                } catch (FileUploadException ex) {
                    response.sendRedirect("Cliente/Servicos/imp_postagem.jsp?msg=Falha na importacao!\n" + ex);
                } catch (Exception ex) {
                    response.sendRedirect("Cliente/Servicos/imp_postagem.jsp?msg=Falha na importacao!\n" + ex);
                }

            } else {
                response.sendRedirect("Cliente/Servicos/imp_postagem.jsp?msg=is not a multipart form");
            }
        } else {
            response.sendRedirect("Cliente/Servicos/imp_postagem.jsp?msg=Sua sessao expirou!");
        }

    }

    private ArrayList<String> inserirDiretorio(ArrayList<FileItem> listaArq, String nomeBD) throws IOException, Exception {

        ArrayList<String> listaCaminhos = new ArrayList<String>();

        // Cria o diretório caso ele não exista
        String caminho = getServletContext().getRealPath("FileUploads\\"+nomeBD+"\\Movimento");
        //caminho = "C:\\Users\\Fernando\\Documents\\NetBeansProjects\\PortalPostal_Web\\build\\web\\ClientesImport";
        File diretorio = new File(caminho);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        for (FileItem item : listaArq) {
            //verifica se eh .HTM
            if (!item.getName().toUpperCase().endsWith(".HTM")) {
                return null;
            }

            //Salva o arquivo na pasta
            File file = new File(diretorio, FilenameUtils.getName(item.getName()));
            item.write(file);

            //adiciona caminho no array
            listaCaminhos.add(file.getAbsolutePath().replace('\\', '/'));
        }
        
        return listaCaminhos;

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Controle.contrCliente;
import Emporium.Controle.ContrDestinatarioImporta;
import Emporium.Controle.ContrPreVendaImporta;
import Entidade.Clientes;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
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

/**
 *
 * @author Ricardinho
 */
public class ServImportarDestinatario extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sessao = request.getSession();
        String nomeBD = (String) sessao.getAttribute("nomeBD");
        if (nomeBD != null) {
            boolean isMultiPart = FileUpload.isMultipartContent(request);
            int idCliente = 0;
            if (isMultiPart) {
                try {
                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setSizeMax(1024 * 1024 * 2);

                    List items = upload.parseRequest(request);
                    Iterator iter = items.iterator();
                    FileItem itemImg = null;
                    int vd = 0, ar = 0;

                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();
                        if (item.isFormField()) {
                            if (item.getFieldName().equals("idCliente")) {
                                idCliente = Integer.parseInt(item.getString());
                            }
                        }
                        if (!item.isFormField()) {
                            if (item.getName().length() > 0) {
                                itemImg = item;
                            }
                        }
                    }

                    if (!itemImg.getName().toUpperCase().endsWith(".CSV") && !itemImg.getName().toUpperCase().endsWith(".TXT")) {
                        response.sendRedirect("Cliente/Cadastros/destinatario_lista.jsp?msg=Escolha um arquivo para importacao !");
                    } else {
                        //CONSULTA DADOS DO CLIENTE/DEPARTAMENTO/CONTRATO
                        Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
                        if (cli != null) {
                            String condicao = ContrDestinatarioImporta.importaPedido(itemImg, idCliente, nomeBD);
                            response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                        } else {
                            response.sendRedirect("Cliente/Servicos/imp_postagem.jsp?msg=Cliente nao encontrado no banco de dados!");
                        }
                    }
                } catch (FileUploadException ex) {
                    response.sendRedirect("Cliente/Servicos/imp_postagem.jsp?msg=Falha no Upload do Arquivo de Importacao!\n" + ex);
                } catch (Exception ex) {
                    response.sendRedirect("Cliente/Servicos/imp_postagem.jsp?msg=Falha na importacao!\n" + ex);
                }

            }
        } else {
            response.sendRedirect("Cliente/Servicos/imp_postagem.jsp?msg=Sua sessao expirou!");
        }
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

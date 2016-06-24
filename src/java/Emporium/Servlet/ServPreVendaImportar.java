/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Controle.contrCliente;
import Emporium.Controle.ContrPreVendaImporta;
import Entidade.Clientes;
import java.io.*;
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
 * @author RICARDINHO
 */
public class ServPreVendaImportar extends HttpServlet {

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
        if (nomeBD != null) {
            boolean isMultiPart = FileUpload.isMultipartContent(request);
            String campoDepartamento = "", servico = "", tipo = "";
            int idCliente = 0;
            if (isMultiPart) {
                try {
                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setSizeMax(1024 * 1024 * 2);

                    List items = upload.parseRequest(request);
                    Iterator iter = items.iterator();
                    FileItem fileItem = null;
                    int vd = 0, ar = 0;

                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();
                        if (item.isFormField()) {
                            if (item.getFieldName().equals("tipo")) {
                                tipo = item.getString();
                            }
                            if (item.getFieldName().equals("departamento")) {
                                campoDepartamento = item.getString();
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
                            if (item.getFieldName().equals("ar")) {
                                ar = Integer.parseInt(item.getString());
                            }
                        }

                        if (!item.isFormField()) {
                            //System.out.println("5");
                            if (item.getName().length() > 0) {
                                //System.out.println("6");
                                fileItem = item;
                            }
                        }
                    }
                    System.out.println("chegou aqui");
                   
                    if (fileItem == null || (!fileItem.getName().toUpperCase().endsWith(".CSV") && !fileItem.getName().toUpperCase().endsWith(".XML") && !fileItem.getName().toUpperCase().endsWith(".TXT") && !fileItem.getName().toUpperCase().endsWith(".XLS"))) {
                        response.sendRedirect("Cliente/Servicos/imp_postagem.jsp?msg=Escolha um arquivo para importacao !");
                    } else {
                        //DELETA IMPORTACOES NAO CONCLUIDAS
                        ContrPreVendaImporta.excluirNaoConfirmados(idCliente, nomeBD);

                        //CONSULTA DADOS DO CLIENTE/DEPARTAMENTO/CONTRATO
                        Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
                        if (cli != null) {
                            String departamento = "";
                            String contrato = "";
                            String cartaoPostagem = "";
                            int idDepartamento = 0;
                            if (!campoDepartamento.equals("")) {
                                String aux[] = campoDepartamento.split(";");
                                idDepartamento = Integer.parseInt(aux[0]);
                                departamento = aux[1];
                                cartaoPostagem = aux[2];
                            }
                            if (cli.getTemContrato() == 1 && cli.getStatusCartaoPostagem() != 0 && cli.getDtVigenciaFimContrato().after(new Date())) {
                                contrato = cli.getNumContrato();
                                if (cartaoPostagem.equals("0") || cartaoPostagem.equals("")) {
                                    cartaoPostagem = cli.getCartaoPostagem();
                                }
                            } else {
                                contrato = "";
                                cartaoPostagem = "";
                            }
                                    

                            if (tipo.equals("XML")) { // ARQUIVO TIPO .XML -> SISTEMA PORTAL POSTAL
                                String condicao = ContrPreVendaImporta.importaPedidoXML(fileItem, idCliente, campoDepartamento, servico, nomeBD);
                                response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                            } else if (tipo.equals("PLP")) {  //ARQUIVO TIPO .XML -> PLP SISTEMA SARA CORREIOS
                                String condicao = ContrPreVendaImporta.importaPedidoPLP(fileItem, idCliente, idDepartamento, departamento, contrato, cartaoPostagem, servico, nomeBD);
                                response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                            } else if (tipo.equals("TRAY")) {  //ARQUIVO TIPO .CSV -> SISTEMA TRAY
                                String condicao = ContrPreVendaImporta.importaPedidoTRAY(fileItem, idCliente, campoDepartamento, servico, vd, ar, nomeBD);
                                response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                            } else if (tipo.equals("LINX")) {  //ARQUIVO TIPO .CSV -> SISTEMA LINX
                                String condicao = ContrPreVendaImporta.importaPedidoLINX(fileItem, idCliente, campoDepartamento, servico, vd, ar, nomeBD);
                                response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                            } else if (tipo.equals("LADOAVESSO")) { //ARQUIVO TIPO .TXT -> EDI DO SISTEMA TIVIT
                                String condicao = ContrPreVendaImporta.importaPedidoEDI(fileItem, idCliente, campoDepartamento, servico, 1, ar, nomeBD);
                                response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                            } else if (tipo.equals("EDI")) { //ARQUIVO TIPO .TXT -> EDI DO SISTEMA TIVIT
                                String condicao = ContrPreVendaImporta.importaPedidoEDI(fileItem, idCliente, campoDepartamento, servico, vd, ar, nomeBD);
                                response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                            } else if (tipo.equals("INTERLOGIC")) { //ARQUIVO TIPO .CSV -> SISTEMA PRÓPRIO
                                String condicao = ContrPreVendaImporta.importaPedidoINTERLOGIC(fileItem, idCliente, campoDepartamento, servico, nomeBD);
                                response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                            } else if (tipo.equals("WEBVENDAS")) { // ARQUIVO TIPO .CSV -> SISTEMA PORTAL POSTAL                            
                                String condicao = ContrPreVendaImporta.importaPedidoWebVendas(fileItem, idCliente, idDepartamento, departamento, contrato, cartaoPostagem, servico, nomeBD);
                                response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                            } else if (tipo.equals("PS")) { // ARQUIVO TIPO .XLS -> SISTEMA PORTAL POSTAL   
                                System.out.println("FOI PS");
                                String condicao = ContrPreVendaImporta.importaPedidoPS(fileItem, idCliente, idDepartamento, departamento, contrato, cartaoPostagem, servico, nomeBD);
                                response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                            }else if (tipo.equals("PSN")) { // ARQUIVO TIPO .CSV -> SISTEMA PORTAL POSTAL                            
                                System.out.println("entrou PSN");
                                String condicao = ContrPreVendaImporta.importaPedidoPSN(fileItem, idCliente, idDepartamento, departamento, contrato, cartaoPostagem, servico, nomeBD);
                                response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                            } else { // ARQUIVO TIPO .CSV -> SISTEMA PORTAL POSTAL                            
                                String condicao = ContrPreVendaImporta.importaPedido(fileItem, idCliente, idDepartamento, departamento, contrato, cartaoPostagem, servico, nomeBD);
                                response.sendRedirect("Cliente/Servicos/imp_confirma.jsp?msg=" + condicao);
                            }
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

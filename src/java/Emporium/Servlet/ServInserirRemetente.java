/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Controle.contrRemetente;
import Entidade.Remetente;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;

/**
 *
 * @author RICADINHO
 */
public class ServInserirRemetente extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServInserirRemetente</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServInserirRemetente at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession();
        String nomeBD = (String) sessao.getAttribute("empresa");

        if (nomeBD == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {

            boolean isMultiPart = FileUpload.isMultipartContent(request);
            if (isMultiPart) {

                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setSizeMax(1024 * 1024 * 1); //2MB

                Remetente rem = new Remetente(0, 0, null, null, null, null, null, null, null, null, null, null);

                try {

                    List items = upload.parseRequest(request);
                    Iterator iter = items.iterator();
                    FileItem itemImg = null;

                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();

                        if (item.isFormField()) {
                            if (item.getFieldName().equals("idCliente")) {
                                rem.setIdCliente(Integer.parseInt(item.getString()));
                            }
                            if (item.getFieldName().equals("nome")) {
                                rem.setNome(item.getString());
                            }
                            if (item.getFieldName().equals("cpf_cnpj")) {
                                rem.setCpf_cnpj(item.getString());
                            }
                            if (item.getFieldName().equals("cep")) {
                                rem.setCep(item.getString());
                            }
                            if (item.getFieldName().equals("endereco")) {
                                rem.setEndereco(item.getString());
                            }
                            if (item.getFieldName().equals("numero")) {
                                rem.setNumero(item.getString());
                            }
                            if (item.getFieldName().equals("complemento")) {
                                rem.setComplemento(item.getString());
                            }
                            if (item.getFieldName().equals("bairro")) {
                                rem.setBairro(item.getString());
                            }
                            if (item.getFieldName().equals("cidade")) {
                                rem.setCidade(item.getString());
                            }
                            if (item.getFieldName().equals("uf")) {
                                rem.setUf(item.getString());
                            }
                        }

                        if (!item.isFormField()) {
                            if (item.getName().length() > 0) {
                                itemImg = item;
                            }
                        }
                    }

                    inserirImagemDiretorio(itemImg, rem, nomeBD);
                    sessao.setAttribute("msg", "Remetente Inserido com Sucesso!");

                } catch (SizeLimitExceededException e) {
                    sessao.setAttribute("msg", "Tamanho Máximo do Arquivo Excedido!");
                } catch (FileUploadException ex) {
                    sessao.setAttribute("msg", "Falha ao Inserir!<br><br>ERRO: " + ex);
                } catch (Exception ex) {
                    sessao.setAttribute("msg", "Falha ao Inserir!<br><br>ERRO: " + ex);
                }

                response.sendRedirect("Cliente/Cadastros/remetente_lista.jsp");
            } else {
                sessao.setAttribute("msg", "FORM IS NOT MULTIPART TYPE");
                response.sendRedirect("Cliente/Cadastros/remetente_lista.jsp");
            }
        }
    }

    private String inserirImagemDiretorio(FileItem item, Remetente rem, String nomeBD) throws IOException, Exception {

        int idRem = contrRemetente.inserir(rem.getIdCliente(), rem.getNome(), rem.getCpf_cnpj(), rem.getCep(), rem.getEndereco(), rem.getNumero(), rem.getComplemento(), rem.getBairro(), rem.getCidade(), rem.getUf(), rem.getUrl_logo(), nomeBD);

        //Pega o diretório de onde a aplicação está rodando
        String caminho = getServletContext().getRealPath("img_bd") + "/" + nomeBD + "/";
        //Cria o diretório caso ele não exista
        File diretorio = new File(caminho);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        // Mandar o arquivo para o diretório informado
        if (item != null && item.getName() != null && !item.getName().equals("") && !item.getName().equals("null") && item.getSize() > 0) {
            //UPLOAD DA IMAGEM PEQUENA
            String nome = item.getName();
            String nome_tumb = rem.getIdCliente() + "_" + idRem + "_logo" + nome.substring(nome.lastIndexOf("."));
            File file_1 = new File(diretorio, nome_tumb);
            FileOutputStream output_1 = new FileOutputStream(file_1);
            InputStream is_1 = Util.ResizeImage.resizeImage(item.getInputStream(), 300, 100);
            byte[] buffer_1 = new byte[2048];
            int nLidos_1;
            while ((nLidos_1 = is_1.read(buffer_1)) >= 0) {
                output_1.write(buffer_1, 0, nLidos_1);
            }
            output_1.flush();
            output_1.close();

            caminho = "/img_bd/" + nomeBD + "/" + nome_tumb;
            caminho = caminho.replace('\\', '/');

            //altera url_logo
            contrRemetente.alterarLogo(caminho, idRem, rem.getIdCliente(), nomeBD);

        }
        return caminho;
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

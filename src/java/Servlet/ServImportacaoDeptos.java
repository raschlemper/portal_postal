/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrErroLog;
import java.io.*;
import java.nio.charset.Charset;
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

/**
 *
 * @author RICARDINHO
 */
public class ServImportacaoDeptos extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
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
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServImportacaoDeptos</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServImportacaoDeptos at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("/index.jsp?msgLog=3");
        } else {
            try {

                String arquivo = "";
                String nomeBD = (String) sessao.getAttribute("empresa");
                int idUsuario = (Integer) sessao.getAttribute("idUsuario");
                boolean isMultiPart = FileUpload.isMultipartContent(request);

                if (isMultiPart) {
                    
                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setSizeMax(1024 * 1024 * 10);
                    String vCaminho = "";

                    List items = upload.parseRequest(request);
                    Iterator iter = items.iterator();
                    FileItem fileItem = (FileItem) iter.next();

                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();
                        if (item.isFormField()) {
                            if (item.getFieldName().equals("arquivo")) {
                                arquivo = item.getString();
                            }
                        }
                        if (!item.isFormField()) {
                            if (item.getName().length() > 0) {
                                fileItem = item;
                            }
                        }
                    }

                    
                    if (!fileItem.getContentType().equals("text/plain")) {  // troquei a informação -> if(!aa.equals("application/vnd.ms-excel")){
                         sessao.setAttribute("msg", "O Arquivo deve ser um arquivo texto!");
                    } else{
                    /*vCaminho = inserirDiretorio(itemImg);
                    if (vCaminho.equals("")) {
                        sessao.setAttribute("msg", "Escolha um arquivo para importacao!");
                    } else {*/                    
                        String mensagem = importaCli(fileItem, nomeBD, idUsuario);
                        sessao.setAttribute("msg", mensagem);
                    //}
                    }
                }

            } catch (FileUploadException ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServImportacao", "FileUploadException", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
                response.sendRedirect("Agencia/Importacao/imp_deptos.jsp");
            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServImportacao", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
                response.sendRedirect("Agencia/Importacao/imp_deptos.jsp");
            }

            //response.sendRedirect("Agencia/Importacao/imp_deptos.jsp");
            response.sendRedirect(request.getHeader("referer"));
        }

    }

    public static String importaCli(FileItem item, String nomeBD, int idUsuario) {
        int linha = 0;
        //caminho = caminho.replace("\\", "/");

        ArrayList<String> listaQuerys = new ArrayList<String>();

        String sqlBase = "INSERT INTO cliente_departamentos (idDepartamento, idCliente, nomeDepartamento, ativo) VALUES ";
        StringBuilder sqlValues = new StringBuilder();
        String sqlDuplicated = " ON DUPLICATE KEY UPDATE nomeDepartamento = VALUES(nomeDepartamento), ativo = 1;";


        try {
            
            InputStreamReader is = new InputStreamReader(item.getInputStream(), Charset.forName("ISO-8859-1"));
            BufferedReader le = new BufferedReader(is);
            //BufferedReader le = new BufferedReader(new FileReader(caminho));
            while (le.ready()) {
                linha++;
                String buffer = le.readLine();

                if (linha % 500 == 0) {
                    String sqlQuery = sqlBase + sqlValues.substring(0, sqlValues.toString().lastIndexOf(",")) + sqlDuplicated;
                    listaQuerys.add(sqlQuery);
                    sqlValues = new StringBuilder();
                }

                StringBuilder strBuf = new StringBuilder();
                strBuf.append(" (\"").append(toStr(buffer.substring(14, 23))).append("\", ");
                strBuf.append("\"").append(toStr(buffer.substring(0, 14))).append("\", ");
                strBuf.append("\"").append(toStr(buffer.substring(23, 53))).append("\", 1),\n");

                sqlValues.append(strBuf);

            }
            le.close();

            if (!sqlValues.toString().equals("")) {
                String sqlQuery = sqlBase + sqlValues.substring(0, sqlValues.toString().lastIndexOf(",")) + sqlDuplicated;
                listaQuerys.add(sqlQuery);
            }

            Controle.ContrClienteDeptos.importarDeptos(listaQuerys, sqlBase, sqlDuplicated, nomeBD, idUsuario);

            return "Atualização de Departamentos Realizada Com Sucesso!";

        } catch (IOException e) {
            return "Erro na linha <b style='color:red;'>" + linha + "</b> do arquivo!<br><br>Falha: Não foi possivel ler o arquivo!<br>Detalhes:" + e;
        } catch (Exception e) {
            return "Erro na linha <b style='color:red;'>" + linha + "</b> do arquivo!<br><br>Falha: Problema no tratamento do arquivo!<br>Detalhes: " + e;
        }
    }

    private static String toStr(String str) {
        return str.trim().replaceAll("\"", "");
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

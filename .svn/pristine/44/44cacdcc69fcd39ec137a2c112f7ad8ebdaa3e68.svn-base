/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrErroLog;
import Util.FormatarData;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author SCC4
 */
@MultipartConfig(maxFileSize = 505000)    // upload file's size up to 505kb // 505000 bytes
public class ServSalvarARImg extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("/index.jsp?msgLog=3");
        } else {
            try {

                String nomeBD = (String) sessao.getAttribute("empresa");
                int idUsuario = (Integer) sessao.getAttribute("idUsuario");

                String nomeRec = request.getParameter("nomeRec");
                String idCli = request.getParameter("codCli");
                int idCliente = Integer.parseInt(idCli);
                String sroRec = request.getParameter("sroRec");
                Date data1 = FormatarData.formataRetornaDate(request.getParameter("dataRec"));

                Part filePart = request.getPart("arquivoRec"); // Retrieves <input type="file" name="file"> 
                if (filePart != null) {
                    //prints out some information for debugging
                    System.out.println(filePart.getName());
                    System.out.println(filePart.getSize());
                    System.out.println(filePart.getContentType());
                    if (filePart.getSize()>0 && !filePart.getContentType().equals("image/jpeg") && !filePart.getContentType().equals("image/gif") && !filePart.getContentType().equals("image/png")) {
                        sessao.setAttribute("msg", "O Arquivo deve ser um arquivo de imagem .JPG ou .PNG!");
                    } else {
                        // obtains input stream of the upload file
                        if (filePart.getSize() <= 0) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String dataBaixa = sdf.format(data1);
                            Controle.contrMovimentacao.darBaixaAr(1, sroRec, nomeRec, dataBaixa, idCliente, nomeBD);
                            sessao.setAttribute("msg", "Atualização de AR's Realizada Com Sucesso!");
                        } else {
                            InputStream inputStream = filePart.getInputStream();
                            String mensagem = salvarAR(inputStream, data1, sroRec, nomeRec, nomeBD, idUsuario, idCliente);
                            sessao.setAttribute("msg", mensagem);
                        }

                    }
                } else {
                    sessao.setAttribute("msg", "Selecione um arquivo para importar o AR!");
                }

            } catch (SQLException ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServImportacaoMov", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + ";Ocorreu um erro inesperado!;" + ex.toString());
            } catch (Exception ex) {
                if (ex.getMessage().contains("FileSizeLimitExceededException")) {
                    sessao.setAttribute("msg", "Tamanho máximo de imagem (500 KB) excedido!");
                } else {
                    int idErro = ContrErroLog.inserir("HOITO - ServImportacaoMov", "Exception", null, ex.toString());
                    sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + ";Ocorreu um erro inesperado!;" + ex.toString());
                }
            }
        }

        response.sendRedirect(request.getHeader("referer"));

    }

    // <editor-fold defaultstate="collapsed" desc="Métodos HttpServlet. Clique no sinal de + à esquerda para editar o código.">
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

    public static String salvarAR(InputStream inputStream, Date dataRec, String obj, String nomeRec, String nomeBD, int idUsuario, int idCliente) throws SQLException {

        Controle.contrBaixaAr.salvarAR(inputStream, dataRec, obj, nomeRec, idUsuario, nomeBD, idCliente);

        return "Atualização de AR's Realizada Com Sucesso!";
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

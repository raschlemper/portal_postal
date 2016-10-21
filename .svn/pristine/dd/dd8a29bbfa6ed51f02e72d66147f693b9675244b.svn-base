/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrErroLog;
import static Servlet.ServImportacaoMov.importaMovimento;
import Util.FormatarData;
import Util.FormatarDecimal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Ricardo
 */
public class ServImportacaoMovVisual extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServImportacaoMovVisual</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServImportacaoMovVisual at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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

        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("/index.jsp?msgLog=3");
        } else {
            try {

                String nomeBD = (String) sessao.getAttribute("empresa");
                boolean isMultiPart = FileUpload.isMultipartContent(request);

                if (isMultiPart) {

                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setSizeMax(1024 * 1024 * 2);

                    String vData1 = "", vData2 = "";
                    String vCaminho = "";
                    int idUsuario = 0;
                    Date data1 = null, data2 = null;

                    List items = upload.parseRequest(request);

                    Iterator iter = items.iterator();

                    FileItem itemImg = (FileItem) iter.next();

                    while (iter.hasNext()) {

                        FileItem item = (FileItem) iter.next();

                        if (item.isFormField()) {
                            if (item.getFieldName().equals("data")) {
                                vData1 = item.getString();
                                data1 = FormatarData.formataRetornaDate(vData1);
                            } else if (item.getFieldName().equals("data2")) {
                                vData2 = item.getString();
                                data2 = FormatarData.formataRetornaDate(vData2);
                            } else if (item.getFieldName().equals("idUsuario")) {
                                idUsuario = Integer.parseInt(item.getString());
                            }

                        }
                        if (!item.isFormField()) {
                            if (item.getName().length() > 0) {
                                itemImg = item;
                            }
                        }
                    }

                    vCaminho = "";//inserirDiretorio(itemImg);

                    if (vCaminho.equals("")) {
                        sessao.setAttribute("msg", "Escolha um arquivo para importacao!");
                    } else {
                        if (data1 != null && data2 != null && (data1.before(data2) || data1.equals(data2)) && Util.SomaData.diferencaEmDias(data1, data2) <= 31) {
                            String mensagem = importaMovimento(itemImg, data1, nomeBD, idUsuario);
                            sessao.setAttribute("msg", mensagem);
                        } else {
                            sessao.setAttribute("msg", "Data incorreta, ou periodo maior que 1 mes!");
                        }
                    }
                }

            } catch (FileUploadBase.SizeLimitExceededException e) {
                sessao.setAttribute("msg", "Tamanho Máximo do Arquivo Excedido!");
            } catch (FileUploadException ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServImportacaoMov", "FileUploadException", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServImportacaoMov", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            }

            //response.sendRedirect("./Agencia/Importacao/imp_movimento_visual.jsp");
            response.sendRedirect(request.getHeader("referer"));
        }
    }

    public static String importaMovimento(FileItem item, Date dataIni, String nomeBD, int idUsuario) {

        int linha = 0;
        int qtdMov = 0;
        int qtdServ = 0;

        ArrayList<String> listaQuerys = new ArrayList<String>();
        ArrayList<String> listaIDS = new ArrayList<String>();

        String sqlBase = "INSERT INTO visual_movimento (data, cliente, codCliente, codServico, numObjeto, nomeDestinatario, cep, campo1, campo2, campo3"
                + ", valorAdicional, inteiro2, valor1, campo4, inteiro3, valor2, campo5, campo6, campo7, campo8) VALUES ";
        String sqlBaseServ = "REPLACE INTO visual_servicos (id, nomeServ, sto) VALUES ";

        StringBuilder sqlValues = new StringBuilder();
        StringBuilder sqlValuesServ = new StringBuilder();
        try {
            InputStreamReader is = new InputStreamReader(item.getInputStream(), Charset.forName("ISO-8859-1"));
            BufferedReader le = new BufferedReader(is);
            while (le.ready()) {
                linha++;
                String buffer = le.readLine().trim();
                if (buffer.startsWith("1")) {
                    if(!buffer.endsWith("'20911424' )")){
                        qtdMov++;                                    
                        sqlValues.append(buffer.substring(1)).append(",");
                        if (qtdMov % 500 == 0) {
                            String sqlQuery = sqlBase + sqlValues.substring(0, sqlValues.toString().lastIndexOf(","));// + sqlDuplicated;
                            listaQuerys.add(sqlQuery);
                            sqlValues = new StringBuilder();
                        }
                    }                    
                } else if (buffer.startsWith("4")) {
                    qtdServ++;                    
                    sqlValuesServ.append(buffer.substring(1).replace(", '' )", ")")).append(",");
                    if (qtdServ % 500 == 0) {
                        String sqlQuery = sqlBaseServ + sqlValuesServ.substring(0, sqlValuesServ.toString().lastIndexOf(","));// + sqlDuplicated;
                        listaQuerys.add(sqlQuery);
                        sqlValuesServ = new StringBuilder();
                    }
                    
                }
            }
            le.close();

            if (!sqlValues.toString().equals("")) {
                String sqlQuery = sqlBase + sqlValues.substring(0, sqlValues.toString().lastIndexOf(","));// + sqlDuplicated;
                listaQuerys.add(sqlQuery);
            }
            if (!sqlValuesServ.toString().equals("")) {
                String sqlQuery = sqlBaseServ + sqlValuesServ.substring(0, sqlValuesServ.toString().lastIndexOf(","));// + sqlDuplicated;
                listaQuerys.add(sqlQuery);
            }

            Controle.contrMovimentacao.importarMovVisual(listaIDS, listaQuerys, sqlBase, dataIni, nomeBD, idUsuario);

            return "Movimentação Importada Com Sucesso!";

        } catch (IOException e) {
            return "Erro na linha <b style='color:red;'>" + linha + "</b> do arquivo!<br><br>Falha: Não foi possivel ler o arquivo!<br>Detalhes:" + e;
        } catch (Exception e) {
            return "Erro na linha <b style='color:red;'>" + linha + "</b> do arquivo!<br><br>Falha: Problema no tratamento do arquivo!<br>Detalhes: " + e;
        }

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

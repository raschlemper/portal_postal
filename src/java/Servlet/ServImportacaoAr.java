/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrErroLog;
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
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author SCC4
 */
public class ServImportacaoAr extends HttpServlet {

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
            out.println("<title>Servlet ServImportacaoAr</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServImportacaoAr at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Métodos HttpServlet. Clique no sinal de + à esquerda para editar o código.">
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
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp");
        } else {
            try {

                String nomeBD = (String) sessao.getAttribute("empresa");
                int idUsuario = (Integer) sessao.getAttribute("idUsuario");
                boolean isMultiPart = FileUpload.isMultipartContent(request);

                if (isMultiPart) {

                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    upload.setSizeMax(1024 * 1024 * 10);

                    String vData1 = "", vData2 = "", vCaminho = "";
                    Date data1 = null, data2 = null;

                    List items = upload.parseRequest(request);
                    Iterator iter = items.iterator();
                    FileItem fileItem = (FileItem) iter.next();
                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();
                        if (item.isFormField()) {
                            if (item.getFieldName().equals("data3")) {
                                vData1 = item.getString();
                                data1 = FormatarData.formataRetornaDate(vData1);
                            } else if (item.getFieldName().equals("data4")) {
                                vData2 = item.getString();
                                data2 = FormatarData.formataRetornaDate(vData2);
                            }
                        }
                        if (!item.isFormField()) {
                            if (item.getName().length() > 0) {
                                fileItem = item;
                            }
                        }
                    }

                    //int tamanho = (int) fileItem.getSize();
                    //tamanho = tamanho / (1024 * 1024 * 1024);
                    
                    
                    if (!fileItem.getContentType().equals("text/plain")) {  // troquei a informação -> if(!aa.equals("application/vnd.ms-excel")){
                         sessao.setAttribute("msg", "O Arquivo deve ser um arquivo texto!");
                    } else{
                    /*vCaminho = inserirDiretorio(itemImg);
                    if (vCaminho.equals("")) {
                        sessao.setAttribute("msg", "Escolha um arquivo para importacao!");
                    } else {*/                    
                        String mensagem = importaAR(fileItem, data1, data2, nomeBD, idUsuario);
                        sessao.setAttribute("msg", mensagem);
                    //}
                    }
                }

            } catch (SizeLimitExceededException e) {
                sessao.setAttribute("msg", "Tamanho Máximo do Arquivo Excedido!");
            } catch (FileUploadException ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServImportacaoAr", "FileUploadException", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServImportacaoAr", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            }
            //response.sendRedirect("Agencia/Importacao/imp_ar.jsp");
            response.sendRedirect(request.getHeader("referer"));
        }
    }

    /*
    private String inserirDiretorio(FileItem item) throws IOException {

        String caminho = getServletContext().getRealPath("MovimentacaoImport");
        caminho = "/var/lib/tomcat/webapps/PortalPostal/MovimentacaoImport";

        // Cria o diretório caso ele não exista
        File diretorio = new File(caminho);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }

        // Mandar o arquivo para o diretório informado
        String aa = item.getContentType();
        if (!aa.equals("text/plain")) {  // troquei a informação -> if(!aa.equals("application/vnd.ms-excel")){
            return "";
        }

        String nome = "movimentacaoar.txt"; // troquei o nome do arquivo -> cliente.csv
        String arq[] = nome.split("\\\\");
        for (int i = 0; i < arq.length; i++) {
            nome = arq[i];
        }

        File file = new File(diretorio, nome);
        FileOutputStream output = new FileOutputStream(file);
        InputStream is = item.getInputStream();
        byte[] buffer = new byte[2048];
        int nLidos;
        while ((nLidos = is.read(buffer)) >= 0) {
            output.write(buffer, 0, nLidos);
        }

        caminho = caminho.replace('\\', '/');
        caminho += "/" + nome;

        output.flush();
        output.close();
        return caminho;
    }*/

    public static String importaAR(FileItem item, Date dataIni, Date dataFim, String nomeBD, int idUsuario) {
        Date dataVerIni = null, dataVerFim = null;
        int linha = 0;
        //caminho = caminho.replace("\\", "/");

        ArrayList<String> listaQuerys = new ArrayList<String>();
        ArrayList<String> listaIDS = new ArrayList<String>(); //para fazer query => DELETE FROM movimentacao WHERE id NOT IN (1,2,3,...) AND dataPostagem BETWEEN dataIni AND dataFim

        String sqlBase = "INSERT INTO movimentacao_ar (numObjeto, dataBaixaAr, nomeRecebAr, daraRecebRem, tipoRetorno, codCliente"
                + ", codigoSTO, valorReceber, destinatarioDoDoc, loteDevolucao) VALUES ";
        StringBuilder sqlValues = new StringBuilder();
        String sqlDuplicated = " ON DUPLICATE KEY UPDATE dataBaixaAr = VALUES(dataBaixaAr), nomeRecebAr = VALUES(nomeRecebAr)"
                + ", daraRecebRem = VALUES(daraRecebRem), tipoRetorno = VALUES(tipoRetorno), codCliente = VALUES(codCliente), codigoSTO = VALUES(codigoSTO)"
                + ", valorReceber = VALUES(valorReceber), destinatarioDoDoc = VALUES(destinatarioDoDoc), loteDevolucao = VALUES(loteDevolucao);";
        

        try {
            
            InputStreamReader is = new InputStreamReader(item.getInputStream(), Charset.forName("ISO-8859-1"));
            BufferedReader le = new BufferedReader(is);
            //BufferedReader le = new BufferedReader(new FileReader(caminho));
            while (le.ready()) {
                linha++;
                String buffer = le.readLine();
                String aux[] = buffer.split(";");

                if (linha % 500 == 0) {
                    String sqlQuery = sqlBase + sqlValues.substring(0, sqlValues.toString().lastIndexOf(",")) + sqlDuplicated;
                    listaQuerys.add(sqlQuery);
                    sqlValues = new StringBuilder();
                }
                                
                StringBuilder strBuf = new StringBuilder();
                strBuf.append(" (\"").append(toStr(aux[0])).append("\", "); //numObjeto - string
                strBuf.append("\"").append(aux[1]).append("\", "); //dataBaixaAr (formato no arquivo 'yyyyMMdd')
                strBuf.append("\"").append(toStr(aux[2])).append("\", "); //nomerecebedor - string
                strBuf.append("\"").append(aux[3]).append("\", "); //dataBaixaAr (formato no arquivo 'yyyyMMdd')
                strBuf.append("\"").append(toStr(aux[4])).append("\", "); //nomerecebedor - string
                strBuf.append("\"").append(FormatarDecimal.intParser(aux[5])).append("\", "); //nomerecebedor - string
                strBuf.append("\"").append(FormatarDecimal.intParser(aux[6])).append("\", "); //nomerecebedor - string
                strBuf.append("\"").append(toStr(aux[7])).append("\", "); //nomerecebedor - string
                strBuf.append("\"").append(toStr(aux[8])).append("\", "); //nomerecebedor - string
                strBuf.append("\"").append(toStr(aux[9])).append("\"),\n"); //nomerecebedor - string
                                
                sqlValues.append(strBuf);
                listaIDS.add(toStr(aux[0]));

                //VERIFICA A MAIOR E MENOR DATA DO ARQUIVO
                Date dataAux = FormatarData.formataStringToDate(aux[1], "yyyyMMdd");
                Date data = FormatarData.formataStringToDate(aux[3], "yyyyMMdd");
                if (dataVerIni == null || dataVerIni.after(data)) {
                    dataVerIni = data;
                }
                if (dataVerFim == null || dataVerFim.before(data)) {
                    dataVerFim = data;
                }
            }
            le.close();

            //VERIFICA SE O PERIODO DE DATA DO ARQUIVO É IGUAL AO DIGITADO
            if (verificaPeriodoData(dataIni, dataFim, dataVerIni, dataVerFim)) {
                System.out.println("PERIODO DIGITADO = " + dataIni + " - " + dataFim + "\nPERIODO ARQUIVO = " + dataVerIni + " - " + dataVerFim);
                return "O periodo da Data do Arquivo não condiz com o Digitado!";
            }

            if (!sqlValues.toString().equals("")) {
                String sqlQuery = sqlBase + sqlValues.substring(0, sqlValues.toString().lastIndexOf(",")) + sqlDuplicated;
                listaQuerys.add(sqlQuery);
            }

            Controle.contrBaixaAr.importarAR(listaIDS, listaQuerys, sqlBase, sqlDuplicated, dataIni, dataFim, nomeBD, idUsuario);

            return "Atualização de AR's Realizada Com Sucesso!";

        } catch (IOException e) {
            return "Erro na linha <b style='color:red;'>" + linha + "</b> do arquivo!<br><br>Falha: Não foi possivel ler o arquivo!<br>Detalhes:" + e;
        } catch (Exception e) {
            return "Erro na linha <b style='color:red;'>" + linha + "</b> do arquivo!<br><br>Falha: Problema no tratamento do arquivo!<br>Detalhes: " + e;
        }
    }

    private static String toStr(String str) {
        return str.trim().replaceAll("\"", "");
    }

    private static boolean verificaPeriodoData(Date dataIni, Date dataFim, Date dataVerIni, Date dataVerFim) throws Exception {
        if (!dataIni.equals(dataVerIni) || !dataFim.equals(dataVerFim)) {
            return true;
        } else {
            return false;
        }
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

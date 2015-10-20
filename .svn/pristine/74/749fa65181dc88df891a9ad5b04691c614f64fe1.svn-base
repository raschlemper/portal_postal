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
import java.io.PrintWriter;
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
 * @author Rico
 */
public class ServImportacaoMov extends HttpServlet {

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
            out.println("<title>Servlet ServImportacaoMov</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServImportacaoMov at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {
            out.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
                    upload.setSizeMax(1024 * 1024 * 10);

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
                                //System.out.println("data1  = "+vData1);
                                data1 = FormatarData.formataRetornaDate(vData1);
                                //System.out.println("data1  = "+ data1);
                            } else if (item.getFieldName().equals("data2")) {
                                vData2 = item.getString();
                                //System.out.println("data2  = "+vData2);
                                data2 = FormatarData.formataRetornaDate(vData2);
                                //System.out.println("data1  = "+ data2);
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

                    vCaminho = inserirDiretorio(itemImg);

                    if (vCaminho.equals("")) {
                        sessao.setAttribute("msg", "Escolha um arquivo para importacao!");
                    } else {
                        if (data1 != null && data2 != null && (data1.before(data2) || data1.equals(data2)) && Util.SomaData.diferencaEmDias(data1, data2) <= 31) {
                            String mensagem = importaMovimento(vCaminho, data1, data2, nomeBD, idUsuario);
                            sessao.setAttribute("msg", mensagem);
                        } else {
                            sessao.setAttribute("msg", "Data incorreta, ou periodo maior que 1 mes!");
                        }
                    }
                }

            } catch (SizeLimitExceededException e) {
                sessao.setAttribute("msg", "Tamanho Máximo do Arquivo Excedido!");
            } catch (FileUploadException ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServImportacaoMov", "FileUploadException", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServImportacaoMov", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            }

            //response.sendRedirect("./Agencia/Importacao/imp_movimento.jsp?msgAlert=Escolha um arquivo para importacao !");
            response.sendRedirect(request.getHeader("referer"));
        }
    }

    private String inserirDiretorio(FileItem item) throws IOException {

        String caminho = getServletContext().getRealPath("MovimentacaoImport");
        System.out.println(caminho);
        caminho = "/var/lib/tomcat/webapps/PortalPostal/MovimentacaoImport";
        System.out.println(caminho);    
        //Cria o diretório caso ele não exista
        File diretorio = new File(caminho);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }

        // Mandar o arquivo para o diretório informado
        String aa = item.getContentType();
        if (!aa.equals("text/plain")) {  // troquei a informação -> if(!aa.equals("application/vnd.ms-excel")){
            return "";
        }

        String nome = "movimentacao.txt"; // troquei o nome do arquivo -> cliente.csv
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
    }

    public static String importaMovimento(String caminho, Date dataIni, Date dataFim, String nomeBD, int idUsuario) {

        Date dataVerIni = null, dataVerFim = null;
        int linha = 0;
        caminho = caminho.replace("\\", "/");

        ArrayList<String> listaQuerys = new ArrayList<String>();
        ArrayList<String> listaIDS = new ArrayList<String>(); //para fazer query => DELETE FROM movimentacao WHERE id NOT IN (1,2,3,...) AND dataPostagem BETWEEN dataIni AND dataFim

        String sqlBase = "INSERT INTO movimentacao (id, numCaixa, numVenda, seqVenda, dataPostagem, descServico"
                + ", numObjeto, destinatario, notaFiscal, peso, cep, paisDestino, valorServico, valorDestino"
                + ", quantidade, valorDeclarado, departamento, codCliente, codSto, conteudoObjeto, contratoEct"
                + ", altura, largura, comprimento, siglaServAdicionais, codigoEct, idDepartamento) VALUES ";
        StringBuilder sqlValues = new StringBuilder();
        String sqlDuplicated = " ON DUPLICATE KEY UPDATE numCaixa = VALUES(numCaixa), numVenda = VALUES(numVenda), seqVenda = VALUES(seqVenda)"
                + ", dataPostagem = VALUES(dataPostagem), descServico = VALUES(descServico), numObjeto = VALUES(numObjeto), destinatario = VALUES(destinatario)"
                + ", notaFiscal = VALUES(notaFiscal), peso = VALUES(peso), cep = VALUES(cep), paisDestino = VALUES(paisDestino), valorServico = VALUES(valorServico)"
                + ", valorDestino = VALUES(valorDestino), quantidade = VALUES(quantidade), valorDeclarado = VALUES(valorDeclarado), departamento = VALUES(departamento), codCliente = VALUES(codCliente)"
                + ", codSto = VALUES(codSto), conteudoObjeto = VALUES(conteudoObjeto), contratoEct = VALUES(contratoEct), altura = VALUES(altura), largura = VALUES(largura)"
                + ", comprimento = VALUES(comprimento), siglaServAdicionais = VALUES(siglaServAdicionais), codigoEct = VALUES(codigoEct), idDepartamento = VALUES(idDepartamento);";
        
        try {
            BufferedReader le = new BufferedReader(new FileReader(caminho));
            while (le.ready()) {
                linha++;
                String buffer = le.readLine();
                String aux[] = buffer.split(";");


                if (linha % 500 == 0) {
                    String sqlQuery = sqlBase + sqlValues.substring(0, sqlValues.toString().lastIndexOf(",")) + sqlDuplicated;
                    listaQuerys.add(sqlQuery);
                    sqlValues = new StringBuilder();
                }
                
                int idDepartamento = 0;
                if(aux.length > 36 && !toStr(aux[36]).equals("")){
                    idDepartamento = FormatarDecimal.intParser(aux[36]);
                }

                StringBuilder strBuf = new StringBuilder();
                strBuf.append(" (\"").append(aux[0].trim()).append(aux[1].trim()).append(aux[2].trim()).append("\", "); //id
                strBuf.append(FormatarDecimal.intParser(aux[0])).append(", "); //numCaixa - int
                strBuf.append(FormatarDecimal.intParser(aux[1])).append(", "); //numVenda - int
                strBuf.append(FormatarDecimal.intParser(aux[2])).append(", \""); //seqVenda - int
                strBuf.append(aux[3]).append("\", \""); //dataPostagem (formato no arquivo 'yyyyMMdd')
                strBuf.append(toStr(aux[4])).append("\", \""); //descServico - string
                strBuf.append(toStr(aux[5])).append("\", \""); //numObjeto - string
                strBuf.append(toStr(aux[6])).append("\", \"");// destinatario - string
                strBuf.append(toStr(aux[7])).append("\", "); // notaFiscal - string
                strBuf.append(FormatarDecimal.floatParser(aux[8])).append(", \""); // peso - float (formato no arquivo '0.00')
                strBuf.append(toStr(aux[9])).append("\", \""); //cep - string (0 = não tem cep)
                strBuf.append(toStr(aux[10])).append("\", "); //paisDestino - string
                strBuf.append(FormatarDecimal.floatParser(aux[11])).append(", "); //valorServico - float (formato no arquivo '0.00')
                strBuf.append(FormatarDecimal.floatParser(aux[12])).append(", "); //valorDestino - float (formato no arquivo '0.00')
                strBuf.append((int) FormatarDecimal.floatParser(aux[16])).append(", "); //quantidade - int (formato no arquivo '0.00')
                strBuf.append(FormatarDecimal.floatParser(aux[17])).append(", \""); //valorDeclarado - float (formato no arquivo '0.00')
                strBuf.append(toStr(aux[18])).append("\", "); // departamento - string
                strBuf.append(FormatarDecimal.intParser(aux[19])).append(", "); //codCliente - int
                strBuf.append(FormatarDecimal.intParser(aux[20])).append(", \""); //codSto - int (codigo da agencia)
                strBuf.append(toStr(aux[24])).append("\", \""); // conteudoObjeto - string (seria A/C ???)
                strBuf.append(toStr(aux[27])).append("\", "); // contratoEct - string
                strBuf.append(FormatarDecimal.floatParser(aux[28])).append(", "); //altura - float (formato no arquivo '0.00')
                strBuf.append(FormatarDecimal.floatParser(aux[29])).append(", "); //largura - float (formato no arquivo '0.00')
                strBuf.append(FormatarDecimal.floatParser(aux[30])).append(", \""); //comprimento - float (formato no arquivo '0.00')
                strBuf.append(SeparaServicosAdicionais(aux[31])).append("\", \""); // siglaServAdicionais - string (sigla dos servicos adicionais, identificados a cada 2 caracteres, EX.: ARVD)
                strBuf.append(toStr(aux[32])).append("\", "); // codigoECT - string
                strBuf.append(idDepartamento).append("), "); // codigoECT - string

                sqlValues.append(strBuf);

                String id = aux[0].trim() + aux[1].trim() + aux[2].trim();
                listaIDS.add(id);
                //VERIFICA A MAIOR E MENOR DATA DO ARQUIVO
                Date data = FormatarData.formataStringToDate(aux[3], "yyyyMMdd");
                if (dataVerIni == null || dataVerIni.after(data)) {
                    dataVerIni = data;
                }
                if (dataVerFim == null || dataVerFim.before(data)) {                    
                    //System.out.println("dt arquivo = "+aux[3]);
                    dataVerFim = data;
                }
            }
            le.close();

            //VERIFICA SE O PERIODO DE DATA DO ARQUIVO É IGUAL AO DIGITADO
            if (verificaPeriodoData(dataIni, dataFim, dataVerIni, dataVerFim)) {
                return "O periodo da Data do Arquivo não condiz com o Digitado!";
            }

            if (!sqlValues.toString().equals("")) {
                String sqlQuery = sqlBase + sqlValues.substring(0, sqlValues.toString().lastIndexOf(",")) + sqlDuplicated;
                listaQuerys.add(sqlQuery);
            }

            Controle.contrMovimentacao.importarMov(listaIDS, listaQuerys, sqlBase, sqlDuplicated, dataIni, dataFim, nomeBD, idUsuario);

            return "Movimentação Importada Com Sucesso!";

        } catch (IOException e) {
            return "Erro na linha <b style='color:red;'>" + linha + "</b> do arquivo!<br><br>Falha: Não foi possivel ler o arquivo!<br>Detalhes:" + e;
        } catch (Exception e) {
            return "Erro na linha <b style='color:red;'>" + linha + "</b> do arquivo!<br><br>Falha: Problema no tratamento do arquivo!<br>Detalhes: " + e;
        }

    }

    private static String toStr(String str) {
        return str.trim().replaceAll("\"", "");
    }

    private static String SeparaServicosAdicionais(String str) {
        String result = "";
        str = str.trim().replaceAll("\"", "");

        int max = str.length();
        for (int i = 0; i < max; i += 2) {
            if (i % 2 == 0 && max >= i + 2) {
                if (i == 0) {
                    result += str.substring(i, i + 2);
                } else {
                    result += ";" + str.substring(i, i + 2);
                }
            }
        }

        return result;
    }

    private static boolean verificaPeriodoData(Date dataIni, Date dataFim, Date dataVerIni, Date dataVerFim) throws Exception {
        System.out.println(dataIni + " - " + dataVerIni + " | " + dataFim + " - " + dataVerFim);
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
    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrErroLog;
import Controle.ContrServicoECT;
import static Controle.contrCliente.criaClienteBalcao;
import Entidade.ServicoECT;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
 * @author Rico
 */
public class ServImportacao extends HttpServlet {
    
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
            out.println("<title>Servlet ServUploadArquivoPonto</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServUploadArquivoPonto at " + request.getContextPath () + "</h1>");
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
        // processRequest(request, response);

        doGet(request, response);

    }

//Process the HTTP Get request
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
                    FileItem itemImg = (FileItem) iter.next();

                    while (iter.hasNext()) {
                        FileItem item = (FileItem) iter.next();
                        if (item.isFormField()) {
                            if (item.getFieldName().equals("arquivo")) {
                                arquivo = item.getString();
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
                        String mensagem = importaCli(vCaminho, nomeBD, idUsuario);
                        sessao.setAttribute("msg", mensagem);
                    }
                }
                criaClienteBalcao(nomeBD);

            } catch (FileUploadException ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServImportacao", "FileUploadException", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServImportacao", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            }

            //response.sendRedirect("Agencia/Importacao/imp_cliente.jsp");
            response.sendRedirect(request.getHeader("referer"));
        }

    }

    private String inserirDiretorio(FileItem item) throws IOException {

        String caminho = getServletContext().getRealPath("ClientesImport");
      caminho = "/var/lib/tomcat/webapps/PortalPostal/ClientesImport";
        //  caminho = "C:\\Users\\Fernando\\Downloads";

        // Cria o diretório caso ele não exista
        File diretorio = new File(caminho);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }

        // Mandar o arquivo para o diretório informado
        String aa = item.getContentType();
        if (!aa.equals("text/plain")) {  // troquei a informação -> if(!aa.equals("application/vnd.ms-excel")){
            return null;
        }
        String nome = "cliente.txt"; // troquei o nome do arquivo -> cliente.csv

        String arq[] = nome.split("\\\\");

        for (int i = 0; i < arq.length; i++) {
            nome = arq[i];
        }

        int nLidos;
        byte[] buffer = new byte[2048];
        File file = new File(diretorio, nome);
        InputStream is = item.getInputStream();
        
        //BufferedReader br = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
                
        OutputStream output = new FileOutputStream(file) ;
        while ((nLidos = is.read(buffer)) >= 0) {
            output.write(buffer, 0, nLidos);
        }
        output.flush();
        output.close();
        
        caminho = caminho.replace('\\', '/');
        caminho += "/" + nome;
        return caminho;
    }

    public static String importaCli(String caminho, String nomeBD, int idUsuario) {
        int linha = 0;
        caminho = caminho.replace("\\", "/");

        ArrayList<String> listaQuerys = new ArrayList<String>();
        ArrayList<String> listaIDS = new ArrayList<String>();

        String sqlBase = "INSERT INTO cliente (codigo, nome, cnpj, endereco, bairro, cep, cidade, uf, email, codSTO, telefone"
                + ", nomeFantasia, complemento, temContrato, numContrato, codAdministrativo, cartaoPostagem, dtVigenciaFimContrato"
                + ", login_sigep, senha_sigep, ufContrato, statusCartaoPostagem, nomeContrato, anoContrato) VALUES ";
        StringBuilder sqlValues = new StringBuilder();
        String sqlDuplicated = " ON DUPLICATE KEY UPDATE nome = VALUES(nome), endereco = VALUES(endereco)"
                + ", telefone = VALUES(telefone), bairro = VALUES(bairro), cidade = VALUES(cidade)"
                + ", uf = VALUES(uf), cep = VALUES(cep), email = VALUES(email), cnpj = VALUES(cnpj)"
                + ", nomeFantasia = VALUES(nomeFantasia), complemento = VALUES(complemento), codSTO = VALUES(codSTO);";
        
        Map<Integer, ServicoECT> mapServicos = ContrServicoECT.consultaMapServicosDeContrato();
        ArrayList<String> listaQuerysServicos = new ArrayList<String>();
        StringBuilder sqlValuesServicos = new StringBuilder();
        String sqlContratoServ = "REPLACE INTO cliente_contrato (idCliente, codECT, grupoServico) VALUES ";

        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(caminho), Charset.forName("ISO-8859-1"));
            BufferedReader le = new BufferedReader(is);
            while (le.ready()) {
                
                linha++;
                String buffer = le.readLine();
                if(!buffer.trim().equals("")){                    
                    String ativo = buffer.substring(524, 525);
                    if(ativo == null || ativo.trim().equals("N") || ativo.trim().equals("")){
                        
                       if (linha % 500 == 0) {
                      
                            String sqlQuery = sqlBase + sqlValues.substring(0, sqlValues.toString().lastIndexOf(",")) + sqlDuplicated;
                            listaQuerys.add(sqlQuery);
                            sqlValues = new StringBuilder();
                            
                          if(!sqlValuesServicos.toString().equals("")){
                              String sqlQueryServico = sqlContratoServ + sqlValuesServicos.substring(0, sqlValuesServicos.toString().lastIndexOf(","));
                            listaQuerysServicos.add(sqlQueryServico);
                            sqlValuesServicos = new StringBuilder();
                          }
                            
                        }
                       
                        StringBuilder strBuf = new StringBuilder();
                        strBuf.append(" (\"").append(toStr(buffer.substring(0, 14), 0)).append("\", "); //codigo
                        strBuf.append("\"").append(toStr(buffer.substring(14, 74), 0)).append("\", "); //nome                       
                        strBuf.append("\"").append(toStr(buffer.substring(74, 88), 0)).append("\", "); //cnpj                        
                        strBuf.append("\"").append(toStr(buffer.substring(88, 188), 0)).append("\", "); //endereco                        
                        strBuf.append("\"").append(toStr(buffer.substring(188, 238), 0)).append("\", "); //bairro                        
                        strBuf.append("\"").append(toStr(buffer.substring(238, 246), 1)).append("\", "); //cep                        
                        strBuf.append("\"").append(toStr(buffer.substring(246, 306), 0)).append("\", "); //cidade                        
                        strBuf.append("\"").append(toStr(buffer.substring(306, 326), 0)).append("\", "); //uf                        
                        strBuf.append("\"").append(toStr(buffer.substring(326, 426), 0)).append("\", "); //email                        
                        strBuf.append("\"").append(toStr(buffer.substring(426, 434), 0)).append("\", "); //codSTO                        
                        strBuf.append("\"").append(toStr(buffer.substring(434, 459), 0)).append("\", "); //telefone                        
                        strBuf.append("\"").append(toStr(buffer.substring(459, 484), 0)).append("\", "); //nomeFantasia
                        
                        if(buffer.length() > 525){ 
                            String temContrato = buffer.substring(525, 526);
                            if(temContrato.equals("1")){
                                strBuf.append("\"").append(toStr(buffer.substring(484, 524), 0)).append("\", "); //complemento    
                                strBuf.append("\"").append(toStr(buffer.substring(525, 526), 0)).append("\", "); //temContrato       
                                strBuf.append("\"").append(toStr(buffer.substring(526, 541), 0)).append("\", "); //contrato
                               
                                strBuf.append("\"").append(toStr(buffer.substring(541, 556), 0)).append("\", "); //cod administrativo
                                strBuf.append("\"").append(toStr(buffer.substring(556, 571), 0)).append("\", "); //cartao postagem
                            
                                strBuf.append("\"").append(toStr(buffer.substring(571, 581), 0)).append("\", "); //data Vigencia
                               
                                strBuf.append("\"").append(toStr(buffer.substring(601, 651), 0)).append("\", "); //user sigepweb
                                strBuf.append("\"").append(toStr(buffer.substring(651, 671), 0)).append("\", "); //senha sigepweb    
                                strBuf.append("\"").append(toStr(buffer.substring(306, 308), 0)).append("\", "); //uf contrato                            
                                strBuf.append("\"").append(toStr("1", 0)).append("\", "); //status cartao postagem       
                                strBuf.append("\"").append(toStr(buffer.substring(459, 479), 0)).append("\", "); //nome chancela                     
                                strBuf.append("\"").append(toStr("2015", 0)).append("\"),\n"); //nome chancela         
                                
                                String servicos = buffer.substring(671).trim();
                                if(servicos.contains(",")){
                                    //System.out.println(servicos);
                                    String srv[] = servicos.split(",");                                
                                    for (int i = 0; i < srv.length; i++) {
                                        //System.out.println(srv[i]);
                                        int codECT = Integer.parseInt(srv[i]);
                                        //System.out.println(srv[i]);
                                        if(mapServicos.containsKey(codECT)){                                        
                                            StringBuilder strBuf2 = new StringBuilder();    
                                            strBuf2.append(" (\"").append(toStr(buffer.substring(0, 14), 0)).append("\", "); //id cliente            
                                            strBuf2.append("\"").append(toStr(codECT+"", 0)).append("\", "); //codECT                  
                                            strBuf2.append("\"").append(toStr(mapServicos.get(codECT).getGrupoServico(), 0)).append("\"),\n"); //grupo servico 
                                            sqlValuesServicos.append(strBuf2);
                                        }
                                    }
                                }
                            }else{
                                strBuf.append("\"").append(toStr(buffer.substring(484, 524), 0)).append("\","); //complemento    
                                strBuf.append("\"").append(toStr("0", 0)).append("\","); //temContrato       
                                strBuf.append("\"").append(toStr("", 0)).append("\","); //contrato
                                strBuf.append("\"").append(toStr("", 0)).append("\","); //cod administrativo
                                strBuf.append("\"").append(toStr("", 0)).append("\","); //cartao postagem
                                strBuf.append("\"").append(toStr("0000-00-00", 0)).append("\","); //data Vigencia
                                strBuf.append("\"").append(toStr("", 0)).append("\","); //user sigepweb
                                strBuf.append("\"").append(toStr("", 0)).append("\","); //senha sigepweb    
                                strBuf.append("\"").append(toStr("", 0)).append("\", "); //uf contrato                            
                                strBuf.append("\"").append(toStr("0", 0)).append("\", "); //status cartao postagem    
                                strBuf.append("\"").append(toStr("", 0)).append("\", "); //nome chancela                     
                                strBuf.append("\"").append(toStr("2015", 0)).append("\"),\n"); //nome chancela          
                            }
                        }else{ 
                            strBuf.append("\"").append(toStr(buffer.substring(484, 524), 0)).append("\","); //complemento         
                            strBuf.append("\"").append(toStr("0", 0)).append("\","); //temContrato    
                            strBuf.append("\"").append(toStr("", 0)).append("\","); //contrato
                            strBuf.append("\"").append(toStr("", 0)).append("\","); //cod administrativo
                            strBuf.append("\"").append(toStr("", 0)).append("\","); //cartao postagem                            
                            strBuf.append("\"").append(toStr("0000-00-00", 0)).append("\","); //data Vigencia
                            strBuf.append("\"").append(toStr("", 0)).append("\","); //user sigepweb
                            strBuf.append("\"").append(toStr("", 0)).append("\","); //senha sigepweb                                
                            strBuf.append("\"").append(toStr("", 0)).append("\", "); //uf contrato    
                            strBuf.append("\"").append(toStr("0", 0)).append("\", "); //status cartao postagem   
                            strBuf.append("\"").append(toStr("", 0)).append("\", "); //nome chancela  
                            strBuf.append("\"").append(toStr("2015", 0)).append("\"),\n"); //nome chancela                           
                        }

                        sqlValues.append(strBuf);
                        listaIDS.add(toStr(buffer.substring(0, 14), 0));
                    }
                }

            }
            le.close();

            if (!sqlValues.toString().equals("")) {
                String sqlQuery = sqlBase + sqlValues.substring(0, sqlValues.toString().lastIndexOf(",")) + sqlDuplicated;
                listaQuerys.add(sqlQuery);
                //System.out.println(sqlQuery);
            }
            if (!sqlValuesServicos.toString().equals("")) {
                String sqlQueryServicos = sqlContratoServ + sqlValuesServicos.substring(0, sqlValuesServicos.toString().lastIndexOf(","));
                listaQuerysServicos.add(sqlQueryServicos);
               // System.out.println(sqlQueryServicos);
            }

            Controle.contrCliente.importarCli(listaIDS, listaQuerys, listaQuerysServicos, sqlBase, sqlDuplicated, nomeBD, idUsuario);

            return "Atualização de Clientes Realizada Com Sucesso!";

        } catch (IOException e) {
            return "Erro na linha <b style='color:red;'>" + linha + "</b> do arquivo!<br><br>Falha: Não foi possivel ler o arquivo!<br>Detalhes:" + e;
        } catch (Exception e) {
            System.out.println(e);
            return "Erro na linha <b style='color:red;'>" + linha + "</b> do arquivo!<br><br>Falha: Problema no tratamento do arquivo!<br>Detalhes: " + e.getLocalizedMessage();
        }
    }

    private static String toStr(String str, int isInt) {
        String rst = str.trim().replaceAll("\"", "").replace("\\", "");
        if(isInt == 1 && rst.equals("")){
            rst="0";
        }
        return rst;
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

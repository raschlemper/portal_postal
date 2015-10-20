/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

/**
 *
 * @author scc4
 */
public class ServTestMultUpload extends HttpServlet {

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

        boolean isMultiPart = FileUpload.isMultipartContent(request);
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
                        //do nothing
                    }
                    if (!item.isFormField()) {
                        if (item.getName().length() > 0) {
                            listaArq.add(item);
                        }
                    }
                }

                inserirDiretorio(listaArq);

                response.sendRedirect("Cliente/Cadastros/teste.jsp?msg=ok");

            } catch (FileUploadException ex) {
                response.sendRedirect("Cliente/Cadastros/teste.jsp?msg="+ex);
            } catch (Exception ex) {
                response.sendRedirect("Cliente/Cadastros/teste.jsp?msg="+ex);
            }

        } else {
            response.sendRedirect("Cliente/Cadastros/teste.jsp?msg=is not a multipart form");
        }

    }

    private String inserirDiretorio(ArrayList<FileItem> listaArq) throws IOException, Exception {

        // Cria o diretório caso ele não exista
        String caminho = getServletContext().getRealPath("ClientesImport");
        File diretorio = new File(caminho);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }

        for (FileItem item : listaArq) {

            //verifica se eh .TXT
            if (!item.getName().toUpperCase().endsWith(".XML")) {
                System.out.println("nao eh txt");
                return "";
            }

            //Salva o arquivo na pasta
            File file = new File(diretorio, FilenameUtils.getName(item.getName()));
            item.write(file);

            lerNotaFiscal(file.getAbsolutePath().replace('\\', '/'));

        }

        return "";

    }

    private void lerNotaFiscal(String path) throws FileNotFoundException, UnsupportedEncodingException, IOException, DocumentException {

        SAXReader reader = new SAXReader();
        Document doc = reader.read(path);

        /*
         //tipo SCC4 - Versão 1.0
         List<Node> eventos = (List<Node>) doc.selectNodes("//ns2:NFPSe/ns2:InfNFPSe");
         for (Node node : eventos) {
         String nNF = node.valueOf("ns2:Versao");
         System.out.println(nNF);
         }
         */
        //tipo Versão 2.0
        Map uris = new HashMap();
        uris.put("nfe", "http://www.portalfiscal.inf.br/nfe");
        /*XPath xpath = doc.createXPath(".//nfe:nfeProc/nfe:NFe/nfe:infNFe");
         xpath.setNamespaceURIs(uris);
         List<Node> eventos = xpath.selectNodes(doc);
         for (Node node : eventos) {
         System.out.println("entro");
         String nNF = node.valueOf("/nfe:ide/nfe:nNF");
         System.out.println(nNF);
         //System.out.println(node.asXML());
         }*/

        // AQUI ESTÁ O MAIOR SEGREDO 
        String basePath = "//nfe:nfeProc/nfe:NFe/nfe:infNFe/";

        XPath xPath = doc.createXPath(basePath + "nfe:ide/nfe:nNF");
        xPath.setNamespaceURIs(uris);
        Element nNF = (Element) xPath.selectSingleNode(doc.getRootElement());

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:CNPJ");
        xPath.setNamespaceURIs(uris);
        Element cnpj = (Element) xPath.selectSingleNode(doc.getRootElement());
        if(cnpj == null){
            xPath = doc.createXPath(basePath + "nfe:dest/nfe:CPF");
            xPath.setNamespaceURIs(uris);
            cnpj = (Element) xPath.selectSingleNode(doc.getRootElement());
        }

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:xNome");
        xPath.setNamespaceURIs(uris);
        Element nome = (Element) xPath.selectSingleNode(doc.getRootElement());

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:email");
        xPath.setNamespaceURIs(uris);
        Element email = (Element) xPath.selectSingleNode(doc.getRootElement());

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:xLgr");
        xPath.setNamespaceURIs(uris);
        Element lgr = (Element) xPath.selectSingleNode(doc.getRootElement());

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:nro");
        xPath.setNamespaceURIs(uris);
        Element nro = (Element) xPath.selectSingleNode(doc.getRootElement());

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:xCpl");
        xPath.setNamespaceURIs(uris);
        Element cpl = (Element) xPath.selectSingleNode(doc.getRootElement());

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:xBairro");
        xPath.setNamespaceURIs(uris);
        Element bairro = (Element) xPath.selectSingleNode(doc.getRootElement());

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:xMun");
        xPath.setNamespaceURIs(uris);
        Element municipio = (Element) xPath.selectSingleNode(doc.getRootElement());

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:UF");
        xPath.setNamespaceURIs(uris);
        Element uf = (Element) xPath.selectSingleNode(doc.getRootElement());

        xPath = doc.createXPath(basePath + "nfe:dest/nfe:enderDest/nfe:CEP");
        xPath.setNamespaceURIs(uris);
        Element cep = (Element) xPath.selectSingleNode(doc.getRootElement());

        xPath = doc.createXPath(basePath + "nfe:total/nfe:ICMSTot/nfe:vNF");
        xPath.setNamespaceURIs(uris);
        Element vNF = (Element) xPath.selectSingleNode(doc.getRootElement());

        System.out.println("- - -");
        System.out.println(nNF.getText());
        System.out.println(cnpj.getText());
        System.out.println(nome.getText());
        System.out.println(email.getText());
        System.out.println(lgr.getText());
        System.out.println(nro.getText());
        System.out.println(cpl.getText());
        System.out.println(bairro.getText());
        System.out.println(municipio.getText());
        System.out.println(uf.getText());
        System.out.println(cep.getText());
        System.out.println(vNF.getText());
        System.out.println("- - -");

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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iReports;

import Controle.contrCliente;
import Emporium.Controle.ContrOrdemServico;
import Entidade.Clientes;
import Entidade.OrdemServico;
import Util.Conexao;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author Correios
 */
public class ServOrdemServico extends HttpServlet {

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
        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String nomeBD = (String) sessao.getAttribute("empresa");
            int idCliente = (Integer) sessao.getAttribute("idCliente");
            int idUser = (Integer) sessao.getAttribute("idUsuarioEmp");
            String nomeUser = (String) sessao.getAttribute("nomeUser");
            
            int idOs = Integer.parseInt(request.getParameter("idOs"));
            int qtd = Integer.parseInt(request.getParameter("qtd"));
            String ids = request.getParameter("ids");
            if(idOs == 0){
                if(qtd > 0){
                    idOs = ContrOrdemServico.inserir(idCliente, idUser, nomeUser, qtd, ids, nomeBD);
                }
            }
                    
            if(idOs > 0){
                try {
                    Clientes cli = contrCliente.consultaClienteById(idCliente, nomeBD);
                    HashMap map = ContrOrdemServico.consultaQtdServicosOS(idOs, nomeBD);
                    OrdemServico os = ContrOrdemServico.consultaOS(idOs, nomeBD);
                    int sdx=0;
                    if(map.containsKey("SEDEX")){
                        sdx = (Integer) map.get("SEDEX");
                    }
                    int esdx = 0;
                    if(map.containsKey("ESEDEX")){
                        esdx = (Integer) map.get("ESEDEX");
                    }
                    int sdxc = 0;
                    if(map.containsKey("SEDEXC")){
                        sdxc = (Integer) map.get("SEDEXC");
                    }
                    int sdx10 = 0;
                    if(map.containsKey("SEDEX10")){
                        sdx10 = (Integer) map.get("SEDEX10");
                    }
                    int qpac = 0;
                    if(map.containsKey("PAC")){
                        qpac = (Integer) map.get("PAC");
                    }
                    int car = 0;
                    if(map.containsKey("CARTA")){
                        car = (Integer) map.get("CARTA");
                    }
                    int sim = 0;
                    if(map.containsKey("SIMPLES")){
                        sim = (Integer) map.get("SIMPLES");
                    }
                                        
                    // mapa de parâmetros do relatório (ainda vamos aprender a usar)
                    Map parametros = new HashMap();                    
                    parametros.put("idCliente", idCliente+"");
                    parametros.put("nomeCliente", cli.getNome());
                    parametros.put("userOs", os.getNomeUsuario());
                    parametros.put("dataOs", sdf.format(os.getDataOs()));
                    parametros.put("idOs", idOs+"");               
                    parametros.put("QTD_SEDEX", sdx+"");               
                    parametros.put("QTD_SEDEXC", sdxc+"");               
                    parametros.put("QTD_ESEDEX", esdx+"");               
                    parametros.put("QTD_SEDEX10", sdx10+"");               
                    parametros.put("QTD_PAC", qpac+"");               
                    parametros.put("QTD_CARTA", (car + sim)+"");               
                    parametros.put("TOTAL", os.getQtdObjetos()+"");               

                    byte[] bytes = null;
                    try {

                        InputStream in = getClass().getResourceAsStream("ordem_servico.jrxml");
                        JasperDesign jasperDesign = JRXmlLoader.load(in);
                        JasperReport jr = JasperCompileManager.compileReport(jasperDesign);
                        JasperPrint impressao = JasperFillManager.fillReport(jr, parametros, Conexao.conectar(nomeBD));
                        bytes = JasperExportManager.exportReportToPdf(impressao);

                    } catch (Exception e) {
                        System.out.println(e);
                        e.printStackTrace();
                        return;
                    }

                    //  
                    if (bytes != null && bytes.length > 0) {
                        response.setContentType("application/pdf");
                        response.setContentLength(bytes.length);
                        ServletOutputStream ouputStream = response.getOutputStream();
                        ouputStream.write(bytes, 0, bytes.length);
                        ouputStream.flush();
                        ouputStream.close();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean urlExist(String vurl) {
        try {
            URL url = new URL(vurl);
            url.openStream();
            return true;
        } catch (Exception e) {
            return false;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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

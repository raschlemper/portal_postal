/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Emporium.Controle.ContrLogisticaReversa;
import br.com.correios.logisticareversa.service.ColetasSolicitadas;
import br.com.correios.logisticareversa.service.ComponenteException;
import br.com.correios.logisticareversa.service.ObjetoPostal;
import br.com.correios.logisticareversa.service.RetornoAcompanhamento;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ricardo
 */
public class ServReversaAtualizar extends HttpServlet {

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

        String nomeBD = request.getParameter("nomeBD");
        final String usuario = request.getParameter("usuario");//"jcsbrasil";
        final String senha = request.getParameter("senha");//"jcs.123";

        java.net.Authenticator.setDefault(new java.net.Authenticator() {
            @Override
            protected java.net.PasswordAuthentication getPasswordAuthentication() {
                return new java.net.PasswordAuthentication(usuario, senha.toCharArray());
            }
        });

        int idCli = Integer.parseInt(request.getParameter("idCli").trim());//10399127;
        String codAdm = request.getParameter("codAdm").trim();//10399127;
        String tipoBusca = "U"; //<!-- H (Todos) - U (Último) -->
        String tipoSolicitacao = "A";//<!-- L (Domiciliar) - A (Autorização) - C (Coleta) -->
        List<String> listaAP = ContrLogisticaReversa.consultaReversasPendByCliente(idCli, nomeBD);
        List<String> listaAP_30 = new ArrayList<String>();
        for (int i = 0; i < listaAP.size(); i++) {
            listaAP_30.add(listaAP.get(i));
            if ((i + 1) % 30 == 0) {

                //System.out.println("entroo " + (i + 1));
                try {
                    RetornoAcompanhamento ret = acompanharPedido(codAdm, tipoBusca, tipoSolicitacao, listaAP_30);
                    //System.out.println(ret.getCodErro() + " = " + ret.getMsgErro());
                    for (ColetasSolicitadas c : ret.getColeta()) {
                        //System.out.println(c.getNumeroPedido());
                        int idRev = 0;
                        String obj = "- - -";
                        String desc = "";
                        String ultStatus = "";
                        String data = "";
                        String hora = "";

                        for (ObjetoPostal o : c.getObjeto()) {
                            //System.out.println("Obj: " + o.getNumeroEtiqueta());
                            //System.out.println("ID2: " + o.getControleObjetoCliente());
                            //System.out.println("Data: " + o.getDataUltimaAtualizacao());
                            //System.out.println("Hora: " + o.getHoraUltimaAtualizacao());
                            //System.out.println("Desc: " + o.getDescricaoStatus());
                            //System.out.println("Ult Status: " + o.getUltimoStatus());

                            if (idRev == 0) {
                                obj = o.getNumeroEtiqueta();
                                desc = o.getDescricaoStatus();
                                ultStatus = o.getUltimoStatus();
                                data = o.getDataUltimaAtualizacao();
                                hora = o.getHoraUltimaAtualizacao();
                                String aux[] = o.getControleObjetoCliente().split("_");
                                idRev = Integer.parseInt(aux[0].trim());
                            } else {
                                obj += "<br/>\n" + o.getNumeroEtiqueta();
                                desc += "<br/>\n" + o.getDescricaoStatus();
                                ultStatus += "<br/>\n" + o.getUltimoStatus();
                                data += "<br/>\n" + o.getDataUltimaAtualizacao();
                                hora += "<br/>\n" + o.getHoraUltimaAtualizacao();
                            }
                        }
                        if (idRev > 0) {
                            ContrLogisticaReversa.alterarSituacao(idRev, ultStatus, desc, obj, data, hora, nomeBD);
                        }
                    }
                } catch (Exception e) {
                   // System.out.println(e);
                }
                listaAP_30 = new ArrayList<String>();
            }
        }

        response.sendRedirect("Cliente/Servicos/lista_reversa.jsp?msg=Status Atualizados!");

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

    /*private static RetornoAcompanhamentoTO acompanharPedido(java.lang.String usuario, java.lang.String senha, java.lang.Integer codAdministrativo, java.lang.String tipoBusca, java.lang.String tipoSolicitacao, java.util.List<java.lang.String> numeroPedido) {
        br.com.correios.scol.webservice.WebServiceScol_Service service = new br.com.correios.scol.webservice.WebServiceScol_Service();
        br.com.correios.scol.webservice.WebServiceScol port = service.getWebServiceScolPort();
        return port.acompanharPedido(usuario, senha, codAdministrativo, tipoBusca, tipoSolicitacao, numeroPedido);
    }*/
    private static RetornoAcompanhamento acompanharPedido(java.lang.String codAdministrativo, java.lang.String tipoBusca, java.lang.String tipoSolicitacao, java.util.List<java.lang.String> numeroPedido) throws ComponenteException {
        br.com.correios.logisticareversa.service.LogisticaReversaService service = new br.com.correios.logisticareversa.service.LogisticaReversaService();
        br.com.correios.logisticareversa.service.LogisticaReversaWS port = service.getLogisticaReversaWSPort();
        return port.acompanharPedido(codAdministrativo, tipoBusca, tipoSolicitacao, numeroPedido);
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Coleta.Servlet;

import Controle.ContrErroLog;
import Controle.contrEmpresa;
import Util.FormatarData;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author SCC4
 */
public class ServCarregaColetaFixa extends HttpServlet {

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
            /* TODO output your page here
             out.println("<html>");
             out.println("<head>");
             out.println("<title>Servlet ServCarregaColetaFixa</title>");  
             out.println("</head>");
             out.println("<body>");
             out.println("<h1>Servlet ServCarregaColetaFixa at " + request.getContextPath () + "</h1>");
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

        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("/index.jsp?msgLog=3");
        } else {
            try {

                String nomeBD = (String) sessao.getAttribute("empresa");
                int idEmpresa = (Integer) sessao.getAttribute("idEmpresa");
                try {
                    ArrayList listaColetaFixa = Coleta.Controle.contrColetaFixa.consultaTodasColetasFixas(nomeBD);
                    int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                    String nomeUsuario = Controle.contrUsuario.consultaNomeUsuarioById(idUsuario, nomeBD);

                    for (int i = 0; i < listaColetaFixa.size(); i++) {

                        Coleta.Entidade.ColetaFixa cf = (Coleta.Entidade.ColetaFixa) listaColetaFixa.get(i);
                        int idColetador = cf.getIdColetador();
                        int idCliente = cf.getIdCliente();
                        int idTipo = cf.getIdTipo();
                        String hora = cf.getHora();
                        String dias = cf.getDias();
                        Date data = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String dataB = sdf.format(data);
                        String dataHoraColeta = dataB + " " + hora;
                        Timestamp vDataHoraColeta = null;
                        Timestamp vDataHoraAtual = new Timestamp(new Date().getTime());
                        if (contrEmpresa.consultaoSemHrVerao(idEmpresa)) {
                            //vDataHoraColeta = FormatarData.somarHorasNaData(vDataHoraColeta, -1);
                            vDataHoraAtual = FormatarData.somarHorasNaData(vDataHoraAtual, -1);
                        }
                        try {
                            vDataHoraColeta = Util.FormatarData.formataDateTime(dataHoraColeta);
                        } catch (Exception ex) {
                            Logger.getLogger(ServCarregaColetaFixa.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Calendar calendar = Calendar.getInstance();
                        int today = calendar.get(Calendar.DAY_OF_WEEK);
                        System.out.println("idColetador >"+idColetador+"idCliente >"+idCliente+" dias >"+dias);
                        if(dias.contains(today+"")){
                        int idColeta = Coleta.Controle.contrColeta.inserir(idCliente, idUsuario, idColetador, 0, idTipo, 2, vDataHoraAtual, vDataHoraColeta, "", 2, nomeBD);
                        Controle.ContrLogColeta.inserir(idColeta, idUsuario, nomeUsuario, "Coleta Fixa Carregada", nomeBD);
                        }                       
                    }
                    Coleta.Controle.contrLogRotaFixaCarregada.inserir(idUsuario, nomeBD);

                    sessao.setAttribute("msg", "Coletas Fixas Carregadas com Sucesso!");
                    //response.sendRedirect("Agencia/Coleta/acompanhamento.jsp");
                    response.sendRedirect(request.getHeader("referer"));

                } catch (SQLException ex) {
                    int idErro = ContrErroLog.inserir("ServCarregaColetaFixa", "SQLException", null, ex.toString());
                    sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + ";Ocorreu um erro inesperado!");
                    //response.sendRedirect("Agencia/Coleta/acompanhamento.jsp");
                    response.sendRedirect(request.getHeader("referer"));
                }

            } catch (Exception ex) {
                int idErro = ContrErroLog.inserir("HOITO - ServCarregaColetaFixa", "Exception", null, ex.toString());
                sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + ";Ocorreu um erro inesperado!");
                //response.sendRedirect("Agencia/Coleta/acompanhamento.jsp");
                response.sendRedirect(request.getHeader("referer"));
            }
        }
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Servlet;

import Controle.ContrAmarracao;
import Util.Conexao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RICARDINHO
 */
public class ServPreVendasAtualiza extends HttpServlet {

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
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServPreVendasAtualiza</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServPreVendasAtualiza at " + request.getContextPath() + "</h1>");
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
            response.sendRedirect("index.jsp?msgLog=3");
        } else {
            String nomeBD = request.getParameter("nomeBD");
            Connection con = Conexao.conectar(nomeBD);
            int cont = 0;
            try {
                String sqlDel = "UPDATE pre_venda SET inutilizada = 1 WHERE consolidado = 0 AND impresso = 1 AND inutilizada = 0 AND nomeServico <> 'SIMPLES' AND numObjeto <> 'avista' AND DATE(dataImpressao) < DATE_SUB(DATE(NOW()), INTERVAL 15 DAY);";
                PreparedStatement valores1 = con.prepareStatement(sqlDel);
                valores1.executeUpdate();

                PreparedStatement valores = con.prepareStatement("SELECT numObjeto "
                        + " FROM ("
                        + "       SELECT DISTINCT numObjeto FROM pre_venda WHERE consolidado = 0 AND impresso = 1 AND inutilizada = 0 AND nomeServico <> 'SIMPLES' AND numObjeto <> 'avista'"
                        + "       UNION ALL"
                        + "       SELECT DISTINCT numObjeto FROM movimentacao WHERE dataPostagem >= DATE_SUB(DATE(NOW()), INTERVAL 15 DAY)"
                        + ") AS tbl"
                        + " GROUP BY numObjeto HAVING COUNT(*) > 1;");
                ResultSet result = (ResultSet) valores.executeQuery();
                String objs = "";
                for (int i = 0; result.next(); i++) {
                    objs += "'" + result.getString("numObjeto") + "',";
                    cont++;
                }
                if (!objs.equals("")) {
                    objs = objs.substring(0, objs.lastIndexOf(","));
                    valores = con.prepareStatement("UPDATE pre_venda SET consolidado = 1 WHERE numObjeto IN (" + objs + ")");
                    valores.executeUpdate();
                }

                valores.close();
            } catch (SQLException e) {
                Logger.getLogger(ContrAmarracao.class.getName()).log(Level.WARNING, e.getMessage(), e);
            } finally {
                Conexao.desconectar(con);
            }

            sessao.setAttribute("msg", cont + " Etiquetas Atualizadas com Sucesso!");
            response.sendRedirect(request.getHeader("referer"));
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

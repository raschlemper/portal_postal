/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Util.CalculoEtiqueta;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RICARDINHO
 */
public class ServInserirFaixaEtiqueta extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServInserirFaixaEtiqueta</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServInserirFaixaEtiqueta at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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


        HttpSession sessao = request.getSession();
        String expira = (String) sessao.getAttribute("empresa");
        if (expira == null) {
            response.sendRedirect("index.jsp?msgLog=3");
        } else {
            String nomeBD = (String) sessao.getAttribute("empresa");
            int idUsuario = (Integer) sessao.getAttribute("idUsuario");
            String nomeUsuario = (String) sessao.getAttribute("nome");

            String aux[] = request.getParameter("servico").split(";");
            int servico = Integer.parseInt(aux[0]);
            String grupoServ = aux[1];
            int idCliente = Integer.parseInt(request.getParameter("idCliente"));

            String prefixo = request.getParameter("prefixo_inicial").toUpperCase();
            String sufixo = request.getParameter("sufixo_inicial");
            String numero = request.getParameter("faixa_inicial");
            int nIni = Integer.parseInt(numero.substring(0, 8));
            int nDigitoIni = Integer.parseInt(numero.substring(8, 9));

            String prefixo2 = request.getParameter("prefixo_final").toUpperCase();
            String sufixo2 = request.getParameter("sufixo_final");
            String numero2 = request.getParameter("faixa_final");
            int nFim = Integer.parseInt(numero2.substring(0, 8));
            int nDigitoFim = Integer.parseInt(numero2.substring(8, 9));

            int qtd = Integer.parseInt(request.getParameter("qtd"));
            String cartao = "";//request.getParameter("cartao");

            int nVerificaDigitoIni = Integer.parseInt(Util.CalculoEtiqueta.calculaDigito(nIni));
            int nVerificaDigitoFim = Integer.parseInt(Util.CalculoEtiqueta.calculaDigito(nFim));
            int nVerificaFim = nIni + qtd;

            if (nVerificaDigitoIni == nDigitoIni) {
                if (nVerificaDigitoFim == nDigitoFim) {
                    if (nFim == nVerificaFim - 1) {
                        if (!Controle.ContrClienteEtiquetas.verificaExistenciaFaixaEtiquetaBD(prefixo + "" + numero + "" + sufixo, prefixo2 + "" + numero2 + "" + sufixo2, nomeBD)) {

                            int idImportacao = Controle.ContrClienteEtiquetas.insereLog(prefixo + "" + numero + "" + sufixo, prefixo2 + "" + numero2 + "" + sufixo2, idCliente, idUsuario, nomeUsuario, qtd, grupoServ, "MANUAL", "PARA PORTAL POSTAL", nomeBD);

                            String sql = "INSERT INTO cliente_etiquetas (seqLogica, idImportacao, idCliente, codECT, grupoServico, cartaoPostagem) VALUES";
                            for (int num = nIni; num <= nFim; num++) {
                                String etiqueta = prefixo + "" + CalculoEtiqueta.concertaTamanhoNum(num) + "" + CalculoEtiqueta.calculaDigito(num) + "" + sufixo;
                                if (num == nFim) {
                                    sql += " ('" + etiqueta + "', " + idImportacao + ", " + idCliente + ", " + servico + ", '" + grupoServ + "', '" + cartao + "');";
                                } else {
                                    sql += " ('" + etiqueta + "', " + idImportacao + ", " + idCliente + ", " + servico + ", '" + grupoServ + "', '" + cartao + "'),";
                                }
                            }
                            Controle.ContrClienteEtiquetas.insereEtiquetas(sql, nomeBD);
                            sessao.setAttribute("msg", "Faixa de Etiqueta inserida com sucesso!");

                        } else {
                            sessao.setAttribute("msg", "Esta Faixa de Etiqueta já está inserida no banco de dados!");
                        }
                    } else {
                        sessao.setAttribute("msg", "Quantidade de Etiquetas não confere com as faixas digitadas!");
                    }
                } else {
                    sessao.setAttribute("msg", "Digito Verificador da Faixa Final não Confere!");
                }
            } else {
                sessao.setAttribute("msg", "Digito Verificador da Faixa Inicial não Confere!");
            }

            //response.sendRedirect("Agencia/Configuracao/cliente_etiquetas.jsp?idCliente=" + idCliente);
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

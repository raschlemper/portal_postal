/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Fernando
 */
public class ServGeraChancelaEtq extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String servico = request.getParameter("servico");
        String nome = request.getParameter("nome");
        String contrato = request.getParameter("contrato");
        String ano = request.getParameter("ano");
        String uf = request.getParameter("uf");
        
        if(contrato.trim().equals("9912278851")){
            nome = "EBAZAR.COM.BR LTDA";
        }

        contrato = contrato + "/" + ano + " - DR/" + uf; // MAX 24 CARACTERES

        //Set the mime type of the image
        response.setContentType("image/png");

        /// Pega imagen do cartão
        BufferedImage img = null;
        try {
            if ("PAC".equals(servico) || "PAX".equals(servico)) {
                img = ImageIO.read(new File(this.getServletContext().getRealPath("") + "/imagensNew/chancelas/CHANCELA_PAC.png"));
            } else if ("SEDEX".equals(servico) || "SEDEXC".equals(servico)) {
                img = ImageIO.read(new File(this.getServletContext().getRealPath("") + "/imagensNew/chancelas/CHANCELA_SEDEX.png"));
            } else if ("ESEDEX".equals(servico)) {
                img = ImageIO.read(new File(this.getServletContext().getRealPath("") + "/imagensNew/chancelas/CHANCELA_ESEDEX.png"));
            } else if ("SEDEX10".equals(servico)) {
                img = ImageIO.read(new File(this.getServletContext().getRealPath("") + "/imagensNew/chancelas/CHANCELA_SEDEX10.png"));
            } else if ("SEDEX12".equals(servico)) {
                img = ImageIO.read(new File(this.getServletContext().getRealPath("") + "/imagensNew/chancelas/CHANCELA_SEDEX12.png"));
            } else if ("CARTA".equals(servico)) {
                img = ImageIO.read(new File(this.getServletContext().getRealPath("") + "/imagensNew/chancelas/CHANCELA_CARTA.png"));
            }
        } catch (IOException e) {
            System.out.println("Falha ao carregar imagem de base da chancela: " + e);
        }

        int w = img.getWidth(null);
        int h = img.getHeight(null);
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bi.getGraphics();
        g.drawImage(img, 0, 0, null);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLACK);

        /**
         * ********************** ESCREVE DADOS *********************************
         */
        int ln = 40;
        int x = 415;
        int y = 180;
        if ("SEDEX".equals(servico) || "SEDEXC".equals(servico) || "ESEDEX".equals(servico) || "SEDEX10".equals(servico) || "SEDEX12".equals(servico)) {
            y = 165;
        } else if ("CARTA".equals(servico)) {
            y = 240;
        }

        g.setFont(new Font("ARIAL", Font.PLAIN, 29));
        FontMetrics fm2 = g.getFontMetrics();
        if (!"".equals(contrato)) {
            g.drawString(contrato, ((x - fm2.stringWidth(contrato)) / 2), y);
            y += ln;
        } // contrato

        g.setFont(new Font("ARIAL", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();
        if (!"".equals(nome)) {
            g.drawString(nome, ((x - fm.stringWidth(nome)) / 2), y);
            y += ln + 4;
        } //nome



        /**
         * ********************************************************************
         */
        g.dispose();

        try {
            //Write the image as a jpg
            //ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
            //ImageIO.write(bi, "png", new File(this.getServletContext().getRealPath("") + "/images/intelbras_previewa.png"));
            ImageIO.write(bi, "png", response.getOutputStream());
        } catch (IOException ioe) {
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

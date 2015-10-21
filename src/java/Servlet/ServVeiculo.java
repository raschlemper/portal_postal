/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Controle.ContrErroLog;
import Entidade.Veiculo;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author rafael
 */

@WebServlet("/veiculo")
public class ServVeiculo extends HttpServlet {
    
    private HttpSession sessao;
    private String expira;
    private String nomeBD;
    private String login;
    private String page;
    private String usuario;
    
    private enum actions{ ALL, GET, SAVE, DELETE };         
    
    @Override
    public String getServletInfo() {
        return "Servlet de controler dos veículos";
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        try {            
            servicePage(request);
            this.sessao = request.getSession();
            this.expira = (String) sessao.getAttribute("empresa");
            this.nomeBD = (String) sessao.getAttribute("nomeBD");
            this.usuario = (String) sessao.getAttribute("usuario");
            if(this.expira == null) { response.sendRedirect(this.login); }
            else if (usuario == null) { response.sendRedirect(this.login); }
            else { super.service(request, response); }
        } catch (Exception ex) {
            int idErro = ContrErroLog.inserir("Portal Postal - ServVeiculo", "Exception", null, ex.toString());
            this.sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            response.sendRedirect(request.getHeader("referer"));
        }
    }
    
    private void servicePage(HttpServletRequest request) {
        String context = request.getContextPath();
        this.login = context.concat("/index.jsp?msgLog=3");        
        this.page = context.concat("/NewTemplate/Cadastros/veiculo_lista_b.jsp");        
    }
    
    private String getAction(HttpServletRequest request) {
        return request.getParameter("action");
    } 
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {            
            String action = getAction(request);        
            if(action == null) { init(request, response); }
            else if(action.equalsIgnoreCase(actions.ALL.name())) { getAll(request, response); }
            else if(action.equalsIgnoreCase(actions.GET.name())) { get(request, response); }
            else if(action.equalsIgnoreCase(actions.SAVE.name())) { save(request, response); }
            else if(action.equalsIgnoreCase(actions.DELETE.name())) { delete(request, response); }
        } catch (Exception ex) {
            int idErro = ContrErroLog.inserir("Portal Postal - ServVeiculo", "Exception", null, ex.toString());
            this.sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            response.sendRedirect(request.getHeader("referer"));
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        processRequest(request, response);
    }
    
    private void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        response.sendRedirect(this.page);
    }
    
    private void getAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        List<Veiculo> listaVeiculos = Controle.ContrVeiculo.consultaTodos(this.nomeBD);
        JSONArray lista = new JSONArray(listaVeiculos);
        response.setContentType("application/json");
        response.getWriter().write(lista.toString());
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {       
        Veiculo veiculo = getVeiculoFromRequest(request);
        veiculo = Controle.ContrVeiculo.consulta(this.nomeBD, veiculo);
        JSONObject object = new JSONObject(veiculo);
        response.setContentType("application/json");
        response.getWriter().write(object.toString());
    }   
    
    private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        Veiculo veiculo = getVeiculoFromRequest(request);
        if(veiculo.getId() != null) { update(request, response); }
        else { create(request, response); }
    }
    
    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {               
        Veiculo veiculo = getVeiculoFromRequest(request);
        Controle.ContrVeiculo.inserir(this.nomeBD, veiculo);
        this.sessao.setAttribute("msg", "Veículo Inserido com sucesso!");
        response.sendRedirect(request.getHeader("referer"));        
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {               
        Veiculo veiculo = getVeiculoFromRequest(request);
        Controle.ContrVeiculo.alterar(this.nomeBD, veiculo);
        this.sessao.setAttribute("msg", "Veículo Alterado com sucesso! " + veiculo.getId());
        response.sendRedirect(request.getHeader("referer"));
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {      
        Veiculo veiculo = getVeiculoFromRequest(request);
        Controle.ContrVeiculo.limpar(this.nomeBD, veiculo);
        this.sessao.setAttribute("msg", "Veículo removido com sucesso! " + veiculo.getId());
        response.sendRedirect(request.getHeader("referer"));
    }
    
    private Veiculo getVeiculoFromRequest(HttpServletRequest request) {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(getIntegerParameter(request.getParameter("idVeiculo")));
        veiculo.setTipo(request.getParameter("tipo"));
        veiculo.setMarca(getJsonParameter(request.getParameter("marca"), "name"));
        veiculo.setModelo(getJsonParameter(request.getParameter("modelo"), "name"));
        veiculo.setPlaca(request.getParameter("placa"));
        veiculo.setAnoFabricacao(getIntegerParameter(request.getParameter("anoFabricacao")));
        veiculo.setAnoModelo(getIntegerParameter(request.getParameter("anoModelo")));
        veiculo.setChassis(request.getParameter("chassis"));
        veiculo.setRenavam(request.getParameter("renavam"));
        veiculo.setQuilometragem(getQuilometragemParameter(request.getParameter("quilometragem")));
        veiculo.setCombustivel(request.getParameter("combustivel"));
        veiculo.setStatus(request.getParameter("status"));
        veiculo.setSituacao(request.getParameter("situacao"));
        return veiculo;
    }
          
    private String getJsonParameter(String parameter, String campo) {
        if(parameter == null) return null;
        return new JSONObject(parameter).getString(campo);
    }
          
    private Integer getIntegerParameter(String parameter) {
        if(parameter == null || parameter.equals("")) return null;
        return Integer.parseInt(parameter);
    }
          
    private Integer getQuilometragemParameter(String parameter) {
        if(parameter == null || parameter.equals("")) return null;
        return getIntegerParameter(parameter.replace(".", "")); 
    }

}

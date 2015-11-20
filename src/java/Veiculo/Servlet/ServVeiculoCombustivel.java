/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Veiculo.Servlet;

import Controle.ContrErroLog;
import Veiculo.Controle.ContrVeiculoCombustivel;
import Veiculo.Entidade.VeiculoCombustivel;
import Veiculo.Entidade.VeiculoCombustivelDTO;
import Veiculo.Validacao.Validacao;
import Veiculo.Validacao.VeiculoCombustivelValidacao;
import Veiculo.builder.VeiculoCombustivelBuilder;
import java.io.IOException;
import java.util.ArrayList;
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

@WebServlet("/veiculo/combustivel")
public class ServVeiculoCombustivel extends HttpServlet {
    
    private HttpSession sessao;
    private String expira;
    private String nomeBD;
    private String login;
    private String page;
    private String usuario;
    
    private enum actions{ ALL, GET, SAVE, DELETE, LAST };         
    
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
            int idErro = ContrErroLog.inserir("Portal Postal - ServVeiculoCombustivel", "Exception", null, ex.toString());
            this.sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            response.sendRedirect(request.getHeader("referer"));
        }
    }
    
    private void servicePage(HttpServletRequest request) {
        String context = request.getContextPath();
        this.login = context.concat("/index.jsp?msgLog=3");        
        this.page = context.concat("/NewTemplate/veiculo/veiculo_combustivel_lista_b.jsp");        
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
            else if(action.equalsIgnoreCase(actions.LAST.name())) { getLastVeiculoCombustivel(request, response); }
        } catch (Exception ex) {
            int idErro = ContrErroLog.inserir("Portal Postal - ServVeiculoCombustivel", "Exception", null, ex.toString());
            this.sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            System.err.println(ex);
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
    
    private void getAll(HttpServletRequest request, HttpServletResponse response) throws Exception { 
        List<VeiculoCombustivel> listaVeiculos = ContrVeiculoCombustivel.consultaTodos(this.nomeBD);
        response.setContentType("application/json");
        List<VeiculoCombustivelDTO> listaDTO = new ArrayList<VeiculoCombustivelDTO>();
        for (VeiculoCombustivel veiculo : listaVeiculos) {
            listaDTO.add(getVeiculoCombustivelDTO(veiculo));
        }
        JSONArray lista = new JSONArray(listaDTO);
        response.getWriter().write(lista.toString());
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws Exception {      
        VeiculoCombustivel veiculo = getVeiculoFromRequest(request);
        veiculo = ContrVeiculoCombustivel.consulta(this.nomeBD, veiculo);
        response.setContentType("application/json");
        JSONObject object = null;
        if(veiculo == null) { object = new JSONObject(new VeiculoCombustivelDTO()); }
        else { object = new JSONObject(getVeiculoCombustivelDTO(veiculo)); }
        response.getWriter().write(object.toString());
    }   
    
    private void getLastVeiculoCombustivel(HttpServletRequest request, HttpServletResponse response) throws Exception {       
        VeiculoCombustivel veiculo = getVeiculoFromRequest(request);
        veiculo = ContrVeiculoCombustivel.consultaUltimoCombustivelCadastrado(this.nomeBD, veiculo.getVeiculo());
        response.setContentType("application/json");
        JSONObject object = null;
        if(veiculo == null) { object = new JSONObject(new VeiculoCombustivelDTO()); }
        else { object = new JSONObject(getVeiculoCombustivelDTO(veiculo)); }
        response.getWriter().write(object.toString());
    }   
    
    private void save(HttpServletRequest request, HttpServletResponse response) throws Exception {        
        if(!validation(request, response)) return;
        VeiculoCombustivel veiculo = getVeiculoFromRequest(request);        
        if(veiculo.getId() != null) { update(request, response); }
        else { create(request, response); }
    }
    
    private void create(HttpServletRequest request, HttpServletResponse response) throws Exception {               
        VeiculoCombustivel veiculo = getVeiculoFromRequest(request);
        veiculo = ContrVeiculoCombustivel.inserir(this.nomeBD, veiculo);
        this.sessao.setAttribute("msg", "Combustível do Veículo " + getMsgToClient(veiculo) + " Inserida com sucesso!");
        response.sendRedirect(request.getHeader("referer"));        
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {               
        VeiculoCombustivel veiculo = getVeiculoFromRequest(request);
        veiculo = ContrVeiculoCombustivel.alterar(this.nomeBD, veiculo);
        this.sessao.setAttribute("msg", "Combustível do Veículo " + getMsgToClient(veiculo) + " Alterada com sucesso! ");
        response.sendRedirect(request.getHeader("referer"));
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {      
        VeiculoCombustivel veiculo = getVeiculoFromRequest(request);
        veiculo = ContrVeiculoCombustivel.limpar(this.nomeBD, veiculo);
        this.sessao.setAttribute("msg", "Combustível do Veículo " + getMsgToClient(veiculo) + " removida com sucesso! ");
        response.sendRedirect(request.getHeader("referer"));
    }
    
    private VeiculoCombustivel getVeiculoFromRequest(HttpServletRequest request) throws Exception {
        VeiculoCombustivelBuilder builder = new VeiculoCombustivelBuilder();
        return builder.toEntidade(request);
    }
    
    private VeiculoCombustivelDTO getVeiculoCombustivelDTO(VeiculoCombustivel veiculo) throws Exception {
        VeiculoCombustivelBuilder builder = new VeiculoCombustivelBuilder();
        return builder.toDTO(veiculo);
    }
    
    private String getMsgToClient(VeiculoCombustivel veiculo) {
        return veiculo.getVeiculo().getModelo() + " (" +veiculo.getVeiculo().getPlaca() + ")";        
    }
    
    private boolean validation(HttpServletRequest request, HttpServletResponse response) throws Exception {       
        VeiculoCombustivel veiculo = getVeiculoFromRequest(request);
        Validacao validacao = new VeiculoCombustivelValidacao();
        if(!validacao.validar(veiculo)) {
            this.sessao.setAttribute("msg", validacao.getMsg());
            response.sendRedirect(request.getHeader("referer")); 
            return false;
        } 
        return true;
    }
    
}

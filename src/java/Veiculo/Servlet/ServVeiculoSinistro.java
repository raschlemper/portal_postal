/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Veiculo.Servlet;

import Controle.ContrErroLog;
import Veiculo.Controle.ContrVeiculoSinistro;
import Veiculo.Entidade.VeiculoSinistro;
import Veiculo.Entidade.VeiculoSinistroDTO;
import Veiculo.Validacao.Validacao;
import Veiculo.Validacao.VeiculoSinistroValidacao;
import Veiculo.builder.VeiculoSinistroBuilder;
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

@WebServlet("/veiculo/sinistro")
public class ServVeiculoSinistro extends HttpServlet {
    
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
            int idErro = ContrErroLog.inserir("Portal Postal - ServVeiculoSinistro", "Exception", null, ex.toString());
            this.sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            response.sendRedirect(request.getHeader("referer"));
        }
    }
    
    private void servicePage(HttpServletRequest request) {
        String context = request.getContextPath();
        this.login = context.concat("/index.jsp?msgLog=3");        
        this.page = context.concat("/NewTemplate/veiculo/veiculo_sinistro_lista_b.jsp");        
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
            int idErro = ContrErroLog.inserir("Portal Postal - ServVeiculoSinistro", "Exception", null, ex.toString());
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
        List<VeiculoSinistro> listaVeiculos = ContrVeiculoSinistro.consultaTodos(this.nomeBD);
        List<VeiculoSinistroDTO> listaDTO = new ArrayList<VeiculoSinistroDTO>();
        for (VeiculoSinistro veiculo : listaVeiculos) {
            listaDTO.add(getVeiculoSinistroDTO(veiculo));
        }
        JSONArray lista = new JSONArray(listaDTO);
        response.setContentType("application/json");
        response.getWriter().write(lista.toString());
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws Exception {       
        VeiculoSinistro veiculo = getVeiculoFromRequest(request);
        veiculo = ContrVeiculoSinistro.consulta(this.nomeBD, veiculo);
        JSONObject object = new JSONObject(getVeiculoSinistroDTO(veiculo));
        response.setContentType("application/json");
        response.getWriter().write(object.toString());
    }   
    
    private void save(HttpServletRequest request, HttpServletResponse response) throws Exception {                
        if(!validation(request, response)) return;
        VeiculoSinistro veiculo = getVeiculoFromRequest(request);
        if(veiculo.getId() != null) { update(request, response); }
        else { create(request, response); }
    }
    
    private void create(HttpServletRequest request, HttpServletResponse response) throws Exception {               
        VeiculoSinistro veiculo = getVeiculoFromRequest(request);
        veiculo = ContrVeiculoSinistro.inserir(this.nomeBD, veiculo);
        this.sessao.setAttribute("msg", "Sinistro do Veículo " + getMsgToClient(veiculo) + " Inserida com sucesso!");
        response.sendRedirect(request.getHeader("referer"));        
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {               
        VeiculoSinistro veiculo = getVeiculoFromRequest(request);
        veiculo = ContrVeiculoSinistro.alterar(this.nomeBD, veiculo);
        this.sessao.setAttribute("msg", "Sinistro do Veículo " + getMsgToClient(veiculo) + " Alterada com sucesso! ");
        response.sendRedirect(request.getHeader("referer"));
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {      
        VeiculoSinistro veiculo = getVeiculoFromRequest(request);
        veiculo = ContrVeiculoSinistro.limpar(this.nomeBD, veiculo);
        this.sessao.setAttribute("msg", "Sinistro do Veículo " + getMsgToClient(veiculo) + " removida com sucesso! ");
        response.sendRedirect(request.getHeader("referer"));
    }
    
    private VeiculoSinistro getVeiculoFromRequest(HttpServletRequest request) throws Exception {
        VeiculoSinistroBuilder builder = new VeiculoSinistroBuilder();
        return builder.toEntidade(request);
    }
    
    private VeiculoSinistroDTO getVeiculoSinistroDTO(VeiculoSinistro veiculo) throws Exception {
        VeiculoSinistroBuilder builder = new VeiculoSinistroBuilder();
        return builder.toDTO(veiculo);
    }
    
    private String getMsgToClient(VeiculoSinistro veiculo) {
        return veiculo.getVeiculo().getModelo() + " (" +veiculo.getVeiculo().getPlaca() + ")";        
    }
    
    private boolean validation(HttpServletRequest request, HttpServletResponse response) throws Exception {       
        VeiculoSinistro veiculo = getVeiculoFromRequest(request);
        Validacao validacao = new VeiculoSinistroValidacao();
        if(!validacao.validar(veiculo)) {
            this.sessao.setAttribute("msg", validacao.getMsg());
            response.sendRedirect(request.getHeader("referer")); 
            return false;
        } 
        return true;
    }
    
}

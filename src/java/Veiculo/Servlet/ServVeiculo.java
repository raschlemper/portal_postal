/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Veiculo.Servlet;

import Controle.ContrErroLog;
import Veiculo.Controle.ContrVeiculo;
import Veiculo.Entidade.Veiculo;
import Veiculo.Entidade.VeiculoDTO;
import Veiculo.Validacao.Validacao;
import Veiculo.Validacao.VeiculoValidacao;
import Veiculo.builder.VeiculoBuilder;
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

@WebServlet("/api/veiculo")
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
            sendMessageError(response, "SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!");
        }
    }
    
    private void servicePage(HttpServletRequest request) {
        String context = request.getContextPath();
        this.login = context.concat("/index.jsp?msgLog=3");        
        this.page = context.concat("/NewTemplate/veiculo/#/cadastro");        
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
            sendMessageError(response, "SYSTEM ERROR Nº: " + idErro + "<br/> Ocorreu um erro inesperado!");
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
        List<Veiculo> listaVeiculos = ContrVeiculo.consultaTodos(this.nomeBD);
        List<VeiculoDTO> listaDTO = new ArrayList<VeiculoDTO>();
        for (Veiculo veiculo : listaVeiculos) { listaDTO.add(getVeiculoDTO(veiculo)); }
        sendDataObject(response, listaDTO);
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws Exception {       
        Integer idVeiculo = Integer.parseInt(request.getParameter("idVeiculo"));
        Veiculo veiculo = ContrVeiculo.consulta(this.nomeBD, idVeiculo);
        sendDataObject(response, getVeiculoDTO(veiculo));
    }   
    
    private void save(HttpServletRequest request, HttpServletResponse response) throws Exception { 
        Veiculo veiculo = getVeiculoFromRequest(request);       
        if(!validation(veiculo, request, response)) return;
        if(veiculo.getId() != null) { update(veiculo, request, response); }
        else { create(veiculo, request, response); }
    }
    
    private void create(Veiculo veiculo, HttpServletRequest request, HttpServletResponse response) throws Exception {      
        veiculo = ContrVeiculo.inserir(this.nomeBD, veiculo);
        sendDataObject(response, getVeiculoDTO(veiculo));
    }
    
    private void update(Veiculo veiculo, HttpServletRequest request, HttpServletResponse response) throws Exception {    
        veiculo = ContrVeiculo.alterar(this.nomeBD, veiculo);
        sendDataObject(response, getVeiculoDTO(veiculo));
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {      
        Integer idVeiculo = Integer.parseInt(request.getParameter("idVeiculo"));
        Veiculo veiculo = ContrVeiculo.limpar(this.nomeBD, idVeiculo);
        sendDataObject(response, getVeiculoDTO(veiculo));
    }
    
    private boolean existeVeiculo(Veiculo veiculo) {
        List<Veiculo> veiculos = ContrVeiculo.consultaByPlaca(this.nomeBD, veiculo);
        if(veiculos.isEmpty()) return false;
        return true;
    }
    
    private Veiculo getVeiculoFromRequest(HttpServletRequest request) {
        VeiculoBuilder builder = new VeiculoBuilder();
        return builder.toEntidade(request);
    }
    
    private VeiculoDTO getVeiculoDTO(Veiculo veiculo) throws Exception {
        VeiculoBuilder builder = new VeiculoBuilder();
        return builder.toDTO(veiculo);
    }
    
    private boolean validation(Veiculo veiculo, HttpServletRequest request, HttpServletResponse response) throws Exception {  
        Validacao validacao = new VeiculoValidacao();
        if(!validacao.validar(veiculo)) {
            sendMessageError(response, validacao.getMsg());
            return false;
        } else if(existeVeiculo(veiculo)) {
            sendMessageError(response, "Este Veículo já foi cadastrado!");
            return false;
        } 
        return true;
    }
    
    private void sendDataObject(HttpServletResponse response, Object data) throws IOException {
        String object = null;
        if(data instanceof ArrayList) { object = new JSONArray(ArrayList.class.cast(data)).toString(); }
        else { object = new JSONObject(VeiculoDTO.class.cast(data)).toString(); }
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        response.getWriter().write(object);        
    }
    
    private void sendMessageError(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        JSONObject object = new JSONObject();
        object.put("error", message);
        response.getWriter().write(object.toString());        
    }

}

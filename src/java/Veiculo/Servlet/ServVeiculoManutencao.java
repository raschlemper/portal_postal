/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Veiculo.Servlet;

import Controle.ContrErroLog;
import Veiculo.Controle.ContrVeiculoManutencao;
import Veiculo.Entidade.Veiculo;
import Veiculo.Entidade.VeiculoManutencao;
import Veiculo.Entidade.VeiculoManutencaoDTO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

@WebServlet("/veiculo/manutencao")
public class ServVeiculoManutencao extends HttpServlet {
    
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
            int idErro = ContrErroLog.inserir("Portal Postal - ServVeiculoManutencao", "Exception", null, ex.toString());
            this.sessao.setAttribute("msg", "SYSTEM ERROR Nº: " + idErro + "; Ocorreu um erro inesperado!");
            response.sendRedirect(request.getHeader("referer"));
        }
    }
    
    private void servicePage(HttpServletRequest request) {
        String context = request.getContextPath();
        this.login = context.concat("/index.jsp?msgLog=3");        
        this.page = context.concat("/NewTemplate/veiculo/veiculo_manutencao_lista_b.jsp");        
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
            int idErro = ContrErroLog.inserir("Portal Postal - ServVeiculoManutencao", "Exception", null, ex.toString());
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
    
    private void getAll(HttpServletRequest request, HttpServletResponse response) throws Exception { 
        List<VeiculoManutencao> listaVeiculos = ContrVeiculoManutencao.consultaTodos(this.nomeBD);
        List<VeiculoManutencaoDTO> listaDTO = new ArrayList<VeiculoManutencaoDTO>();
        for (VeiculoManutencao veiculo : listaVeiculos) {
            listaDTO.add(getVeiculoManutencaoDTO(veiculo));
        }
        JSONArray lista = new JSONArray(listaDTO);
        response.setContentType("application/json");
        response.getWriter().write(lista.toString());
    }
    
    private void get(HttpServletRequest request, HttpServletResponse response) throws Exception {       
        VeiculoManutencao veiculo = getVeiculoFromRequest(request);
        veiculo = ContrVeiculoManutencao.consulta(this.nomeBD, veiculo);
        JSONObject object = new JSONObject(getVeiculoManutencaoDTO(veiculo));
        response.setContentType("application/json");
        response.getWriter().write(object.toString());
    }   
    
    private void save(HttpServletRequest request, HttpServletResponse response) throws Exception {        
        VeiculoManutencao veiculo = getVeiculoFromRequest(request);
        if(veiculo.getId() != null) { update(request, response); }
        else { create(request, response); }
    }
    
    private void create(HttpServletRequest request, HttpServletResponse response) throws Exception {               
        VeiculoManutencao veiculo = getVeiculoFromRequest(request);
        ContrVeiculoManutencao.inserir(this.nomeBD, veiculo);
        this.sessao.setAttribute("msg", "Manutenção do Veículo Inserida com sucesso!");
        response.sendRedirect(request.getHeader("referer"));        
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {               
        VeiculoManutencao veiculo = getVeiculoFromRequest(request);
        ContrVeiculoManutencao.alterar(this.nomeBD, veiculo);
        this.sessao.setAttribute("msg", "Manutenção do Veículo Alterada com sucesso! " + veiculo.getId());
        response.sendRedirect(request.getHeader("referer"));
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {      
        VeiculoManutencao veiculo = getVeiculoFromRequest(request);
        ContrVeiculoManutencao.limpar(this.nomeBD, veiculo);
        this.sessao.setAttribute("msg", "Manutenção do Veículo removida com sucesso! " + veiculo.getId());
        response.sendRedirect(request.getHeader("referer"));
    }
    
    private VeiculoManutencao getVeiculoFromRequest(HttpServletRequest request) throws Exception {
        VeiculoManutencao veiculoManutencao = new VeiculoManutencao();
        veiculoManutencao.setId(getIntegerParameter(request.getParameter("idVeiculoManutencao")));
        veiculoManutencao.setVeiculo(getVeiculo(request));
        veiculoManutencao.setTipo(request.getParameter("tipo"));
        veiculoManutencao.setQuilometragem(getQuilometragemParameter(request.getParameter("quilometragem")));        
        veiculoManutencao.setValor(getDoubleParameter(request.getParameter("valor")));        
        veiculoManutencao.setData(getDataParameter(request.getParameter("data")));       
        veiculoManutencao.setDataAgendamento(getDataParameter(request.getParameter("dataAgendamento")));       
        veiculoManutencao.setDataEntrega(getDataParameter(request.getParameter("dataEntrega")));
        veiculoManutencao.setDescricao(request.getParameter("descricao"));
        return veiculoManutencao;
    }
    
    private Veiculo getVeiculo(HttpServletRequest request) {
        Veiculo veiculo = new Veiculo(
            getIntegerParameter(request.getParameter("veiculo"))
        );
        return veiculo;
    }
          
    private Date getDataParameter(String parameter) throws Exception {
        if(parameter == null || parameter.equals("")) return null;
        return Util.FormatarData.getDateFromString(parameter, "dd/MM/yyyy");
    }
          
    private Integer getIntegerParameter(String parameter) {
        if(parameter == null || parameter.equals("")) return null;
        return Integer.parseInt(parameter);
    }
          
    private Double getDoubleParameter(String parameter) {
        if(parameter == null || parameter.equals("")) return null;
        return Double.parseDouble(parameter.replaceAll("\\.","").replace(",","."));
    }
          
    private Integer getQuilometragemParameter(String parameter) {
        if(parameter == null || parameter.equals("")) return null;
        return getIntegerParameter(parameter.replace(".", "")); 
    }
    
    private VeiculoManutencaoDTO getVeiculoManutencaoDTO(VeiculoManutencao veiculo) throws Exception {
        VeiculoManutencaoDTO dto = new VeiculoManutencaoDTO(
            veiculo.getId(),
            veiculo.getVeiculo().getId(),
            veiculo.getVeiculo().getPlaca(),
            veiculo.getTipo(),
            veiculo.getQuilometragem(),
            veiculo.getValor(),
            getDataDTO(veiculo.getData()),
            getDataDTO(veiculo.getDataAgendamento()),
            getDataDTO(veiculo.getDataEntrega()),
            veiculo.getDescricao()
        );
        return dto;        
    }
    
    private String getDataDTO(Date data) throws Exception {
        if(data == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(data);
    }
}

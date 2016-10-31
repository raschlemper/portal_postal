<%@page import="br.com.correios.bsb.sigep.master.bean.cliente.ServicoAdicionalERP"%><%@page import="java.util.ArrayList"%><%@page contentType="text/html" pageEncoding="UTF-8"%><%@page import="javax.net.ssl.SSLSession"%><%@page import="javax.net.ssl.HostnameVerifier"%><%@page import="javax.net.ssl.HttpsURLConnection"%><%@page import="javax.net.ssl.SSLContext"%><%@page import="java.security.cert.X509Certificate"%><%@page import="javax.net.ssl.TrustManager"%><%@page import="javax.net.ssl.X509TrustManager"%><%@page import="Entidade.Clientes"%><%@page import="Controle.contrCliente"%><%@page import="java.text.SimpleDateFormat"%><%@page import="br.com.correios.bsb.sigep.master.bean.cliente.AutenticacaoException"%><%@page import="br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException"%><%@page import="br.com.correios.bsb.sigep.master.bean.cliente.CartaoPostagemERP"%><%@page import="Util.FormataString"%><%@page import="br.com.correios.bsb.sigep.master.bean.cliente.ContratoERP"%><%@page import="java.util.Date"%><%@page import="br.com.correios.bsb.sigep.master.bean.cliente.ServicoERP"%><%@page import="java.util.List"%><%@page import="Entidade.empresas"%><%

    response.setHeader("Cache-Control", "no-cache");
    String nomeBD = (String) session.getAttribute("empresa");


    if (nomeBD != null) {
     String nomeUsuario =   request.getParameter("nomeUser");
    int idUsuario = Integer.parseInt(request.getParameter("idUser"));
    Controle.ContrLogAtualizacaoContrato.atualizarData(idUsuario, nomeUsuario, nomeBD);
    
    }
    
%>
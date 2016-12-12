<%@page import="br.com.correios.bsb.sigep.master.bean.cliente.ServicoAdicionalERP"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.net.ssl.SSLSession"%>
<%@page import="javax.net.ssl.HostnameVerifier"%>
<%@page import="javax.net.ssl.HttpsURLConnection"%>
<%@page import="javax.net.ssl.SSLContext"%>
<%@page import="java.security.cert.X509Certificate"%>
<%@page import="javax.net.ssl.TrustManager"%>
<%@page import="javax.net.ssl.X509TrustManager"%>
<%@page import="Entidade.Clientes"%>
<%@page import="Controle.contrCliente"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="br.com.correios.bsb.sigep.master.bean.cliente.AutenticacaoException"%>
<%@page import="br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException"%>
<%@page import="br.com.correios.bsb.sigep.master.bean.cliente.CartaoPostagemERP"%>
<%@page import="Util.FormataString"%>
<%@page import="br.com.correios.bsb.sigep.master.bean.cliente.ContratoERP"%>
<%@page import="java.util.Date"%>
<%@page import="br.com.correios.bsb.sigep.master.bean.cliente.ServicoERP"%>
<%@page import="java.util.List"%>
<%@page import="Entidade.empresas"%>
<%

    response.setHeader("Cache-Control", "no-cache");
    String nomeBD = (String) session.getAttribute("empresa");
    String numContrato = request.getParameter("numContrato");
    String cartaoPostagem = request.getParameter("cartaoPostagem");
    String login = request.getParameter("loginSigep").trim();

    String senhaSigep = request.getParameter("senhaSigep").trim();
    int idCliente = Integer.valueOf(request.getParameter("idCliente"));
    
   /* System.out.println(senhaSigep);
    System.out.println(login);
    System.out.println(idCliente);*/

    try {
        int codSenha = Integer.valueOf(senhaSigep);
        if (codSenha == idCliente) {
            Clientes cliente = Controle.contrCliente.consultaClienteById(Integer.valueOf(idCliente), nomeBD);
            senhaSigep = cliente.getSenha_sigep();
           // System.out.println("senha consultada "+senhaSigep);
        }
    } catch (NumberFormatException e) {

    }

    

    int statusC = 0;
    int codDir = 0;
    int anoContrato = 0;
    String ufContrato = "";
    String nomeSara = "";
    String cnpjSenha = "";
    String codAdm = "";
    Date dtVgFim = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    //List<ServicoERP> listaServico = null;
    String resultado = "erro;Falha ao efetuar a pesquisa!";
    String cartao = "";

    if (nomeBD != null) {
        //Clientes clie = contrCliente.consultaClienteById(idCliente, nomeBD);
        if (!login.equals("") && !senhaSigep.equals("")) {
            //String login = clie.getLogin_sigep();
            //String senha = clie.getSenha_sigep();
            //start web service invocation --
            try {
                // Create a trust manager that does not validate certificate chains
                TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
                };

                // Install the all-trusting trust manager
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

                // Create all-trusting host name verifier
                HostnameVerifier allHostsValid = new HostnameVerifier() {
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                };

                // Install the all-trusting host verifier
                HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

                try {
                    numContrato = Long.parseLong(numContrato) + "";
                } catch (Exception e) {
                    numContrato = "";
                }
                try {
                    cartaoPostagem = Long.parseLong(cartaoPostagem) + "";
                    cartao = FormataString.preencheCom(cartaoPostagem, "0", 10, 1);
                } catch (Exception e) {
                    cartaoPostagem = "";
                }

                br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
                br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
                br.com.correios.bsb.sigep.master.bean.cliente.ClienteERP cli = port.buscaCliente(numContrato, cartao, login, senhaSigep);

                nomeSara = cli.getNome().trim();
                cnpjSenha = cli.getCnpj().replaceAll("[./-]", "").substring(0, 8);
                String servicos = "<b>Servicos do Contrato no SigepWEB:</b> @ ";
                for (ContratoERP c : cli.getContratos()) {
                    anoContrato = c.getDataVigenciaInicio().getYear();
                    dtVgFim = c.getDataVigenciaFim().toGregorianCalendar().getTime();
                    codDir = Integer.parseInt(c.getCodigoDiretoria().trim());
                    ufContrato = FormataString.getUfDiretoria(codDir);
                    for (CartaoPostagemERP cp : c.getCartoesPostagem()) {
                        codAdm = cp.getCodigoAdministrativo();
                        statusC = Integer.parseInt(cp.getStatusCartaoPostagem().trim());
                        if (cp.getServicos() != null) {
                            List<ServicoERP> listaServico = cp.getServicos();
                            for (ServicoERP s : listaServico) {
                                servicos += "@" + s.getCodigo().trim() + " | " + s.getDescricao() + " [" + s.getId() + "]";
                                /*
                                System.out.println("vvvvvvvvvvvv");
                                System.out.println(s.getCodigo()+" - "+s.getId()+" | "+s.getDescricao());
                                System.out.println(s.getTipo1Codigo()+" - "+s.getTipo1Descricao());
                                System.out.println(s.getTipo2Codigo()+" - "+s.getTipo2Descricao());
                                System.out.println("*** Vigencia Servico ***");                                
                                System.out.println(s.getVigencia().getDataFinal().getMonth()+"/"+s.getVigencia().getDataFinal().getYear());
                                
                                List<ServicoAdicionalERP> list = s.getServicosAdicionais();
                                for(ServicoAdicionalERP ad: list){                                
                                    System.out.println("*** ADICIONAIS ***");
                                    System.out.println(ad.getCodigo()+" - "+ad.getId()+" | "+ad.getSigla()+" - "+ad.getDescricao());
                                }
                                
                                System.out.println("*** Servico Sigep ***");
                                System.out.println(s.getServicoSigep().getServico());
                                System.out.println(s.getServicoSigep().getCategoriaServico());
                                System.out.println(s.getServicoSigep().getSsiCoCodigoPostal());
                                System.out.println(s.getServicoSigep().getImitm());
                                System.out.println(s.getServicoSigep().getChancela().getDescricao());
                                System.out.println(s.getServicoSigep().isExigeDimensoes());
                                System.out.println(s.getServicoSigep().isExigeValorCobrar());                                  
                                System.out.println("^^^^^^^^^^^^");
                                 */
                            }
                        }
                    }
                }

                resultado = statusC + ";" + codDir + ";" + anoContrato + ";" + ufContrato + ";" + nomeSara + ";" + cnpjSenha + ";" + codAdm + ";" + sdf.format(dtVgFim) + ";" + cli.getCnpj() + ";" + servicos;
            } catch (SigepClienteException ex) {
                resultado = "erro;Mensagem do SigepWEB:<br/><br/>" + ex.getMessage();
            } catch (AutenticacaoException ex) {
                resultado = "erro;Falha de Autenticação no SigepWEB:<br/><br/>" + ex.getMessage();
            } catch (Exception ex) {
                resultado = "erro;Falha ao Pesquisar Contrato no SigepWEB!<br/>Mensagem do SigepWEB:<br/><br/>" + ex.getMessage();
            }
            //-- end web service invocation --
        } else {
            resultado = "erro;Este cliente não possui uma senha do SigepWEB!<br/><br/>Favor solicite e cadastre uma senha para o cliente.";
        }
    } else {
        resultado = "erro;Login Expirado!<br/><br/>Faça novamente o login no Portal Postal.";
    }
    response.getWriter().write(resultado);
%>
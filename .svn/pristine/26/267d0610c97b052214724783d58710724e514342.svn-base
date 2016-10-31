
import Util.FormataString;
import br.com.correios.bsb.sigep.master.bean.cliente.AutenticacaoException;
import br.com.correios.bsb.sigep.master.bean.cliente.CartaoPostagemERP;
import br.com.correios.bsb.sigep.master.bean.cliente.ClienteERP;
import br.com.correios.bsb.sigep.master.bean.cliente.ContratoERP;
import br.com.correios.bsb.sigep.master.bean.cliente.ServicoAdicionalERP;
import br.com.correios.bsb.sigep.master.bean.cliente.ServicoERP;
import br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import java.io.File;
import java.io.IOException;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class teste {

    public static String numContrato = "9912376876";
    public static String cartaoPostagem = "0071121943";
    public static String login = "9912376876";
    public static String senha = "2xlgp5";
    //int idCliente = Integer.parseInt(request.getParameter("idCliente"));

    public static int statusC = 0;
    public static int codDir = 0;
    public static int anoContrato = 0;
    public static String ufContrato = "";
    public static String nomeSara = "";
    public static String cnpjSenha = "";
    public static String codAdm = "";
    public static Date dtVgFim = null;
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    //List<ServicoERP> listaServico = null;
    public static String resultado = "erro;Falha ao efetuar a pesquisa!";
    public static String cartao = "";

    public static void testSigep() {

        //Clientes clie = contrCliente.consultaClienteById(idCliente, nomeBD);
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
                @Override
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
                System.out.println(cartao);
            } catch (Exception e) {
                cartaoPostagem = "";
            }

            br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
            br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
            // br.com.correios.bsb.sigep.master.bean.cliente.ClienteERP cli = port.buscaCliente(numContrato, cartao, login, senha);
            br.com.correios.bsb.sigep.master.bean.cliente.ClienteERP cli = port.buscaCliente("9912376876", "0071121943", "9912376876", "2xlgp5");

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
                            servicos += "@" + s.getCodigo().trim() + " | " + s.getDescricao();

                            System.out.println("vvvvvvvvvvvv");
                            System.out.println(s.getCodigo() + " - " + s.getId() + " | " + s.getDescricao());
                            System.out.println(s.getTipo1Codigo() + " - " + s.getTipo1Descricao());
                            System.out.println(s.getTipo2Codigo() + " - " + s.getTipo2Descricao());
                            System.out.println("*** Vigencia Servico ***");
                            System.out.println(s.getVigencia().getDataFinal().getMonth() + "/" + s.getVigencia().getDataFinal().getYear());

                            List<ServicoAdicionalERP> list = s.getServicosAdicionais();
                            for (ServicoAdicionalERP ad : list) {
                                System.out.println("*** ADICIONAIS ***");
                                System.out.println(ad.getCodigo() + " - " + ad.getId() + " | " + ad.getSigla() + " - " + ad.getDescricao());
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

                        }
                    }
                }
            }

            resultado = statusC + ";" + codDir + ";" + anoContrato + ";" + ufContrato + ";" + nomeSara + ";" + cnpjSenha + ";" + codAdm + ";" + sdf.format(dtVgFim) + ";" + cli.getCnpj() + ";" + servicos;
            System.out.println(resultado);
        } catch (SigepClienteException ex) {
            System.out.println("erro;Mensagem do SigepWEB:<br/><br/>" + ex.getMessage());
        } catch (AutenticacaoException ex) {
            System.out.println("erro;Falha de Autenticação no SigepWEB:<br/><br/>" + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("erro;Falha ao Pesquisar Contrato no SigepWEB!<br/>Mensagem do SigepWEB:<br/><br/>" + ex.getMessage());
        }
            //-- end web service invocation --

    }

    /* public static void main(String[] args)  {
     try {
            
     ContratoERP ct =  buscaContrato("9912376876", Long.parseLong("68"), login, senha);
            
     //testSigep();            
     //  40776375
     //String xml = solicitaXmlPlp(Long.parseLong("40776375"), login, senha);
     //System.out.println(xml);
     } catch (SigepClienteException ex) {
     System.out.println("SGP "+ex);
     } catch (AutenticacaoException ex) {
     System.out.println("AUT "+ex);
     }
        
     }*/
    public static void main(String[] args) throws IOException {
        SendGrid sendgrid = new SendGrid("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");

        SendGrid.Email email = new SendGrid.Email();
        email.addTo("ricardo@scc4.com.br");
        email.addTo("fernando@scc4.com.br");
        email.addTo("fernando@agfosmarcunha.com.br");
        email.addToName("FERNANDO LUIZ");
        email.addToName("FERNANDO LUIZ");
        email.addToName("FERNANDO LUIZ");
        
        email.setFrom("fernando@scc4.com.br");
        email.setReplyTo("no-reply@scc4.com.br");
        
        
        email.setSubject("Hello World");
        email.setText("My first email with SendGrid Java!");
        email.addAttachment("image.png", new File("C:\\Users\\Fernando\\Pictures\\Saved Pictures\\logo_.png"));
        try {
            SendGrid.Response response = sendgrid.send(email);
            System.out.println("response");
            System.out.println(response.getMessage());
        } catch (SendGridException e) {
            System.err.println("exception");
            System.err.println(e);
        }
    }

    private static Long fechaPlp(java.lang.String xml, java.lang.Long idPlpCliente, java.lang.String cartaoPostagem, java.lang.String faixaEtiquetas, java.lang.String usuario, java.lang.String senha) throws SigepClienteException, AutenticacaoException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.fechaPlp(xml, idPlpCliente, cartaoPostagem, faixaEtiquetas, usuario, senha);
    }

    private static Long fechaPlpVariosServicos(java.lang.String xml, java.lang.Long idPlpCliente, java.lang.String cartaoPostagem, java.util.List<java.lang.String> listaEtiquetas, java.lang.String usuario, java.lang.String senha) throws SigepClienteException, AutenticacaoException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.fechaPlpVariosServicos(xml, idPlpCliente, cartaoPostagem, listaEtiquetas, usuario, senha);
    }

    private static String solicitaXmlPlp(java.lang.Long idPlpMaster, java.lang.String usuario, java.lang.String senha) throws AutenticacaoException, SigepClienteException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.solicitaXmlPlp(idPlpMaster, usuario, senha);
    }

    private static String solicitaEtiquetas(java.lang.String tipoDestinatario, java.lang.String identificador, java.lang.Long idServico, java.lang.Integer qtdEtiquetas, java.lang.String usuario, java.lang.String senha) throws AutenticacaoException, SigepClienteException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.solicitaEtiquetas(tipoDestinatario, identificador, idServico, qtdEtiquetas, usuario, senha);
    }

    private static ClienteERP buscaCliente(java.lang.String idContrato, java.lang.String idCartaoPostagem, java.lang.String usuario, java.lang.String senha) throws SigepClienteException, AutenticacaoException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.buscaCliente(idContrato, idCartaoPostagem, usuario, senha);
    }

    private static ContratoERP buscaContrato(java.lang.String numero, long diretoria, java.lang.String usuario, java.lang.String senha) throws AutenticacaoException, SigepClienteException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.buscaContrato(numero, diretoria, usuario, senha);
    }

    private static boolean atualizaPLP(java.lang.Long idPlpMaster, java.lang.String numEtiqueta, java.lang.String usuario, java.lang.String senha, java.lang.String xml) throws AutenticacaoException, SigepClienteException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.atualizaPLP(idPlpMaster, numEtiqueta, usuario, senha, xml);
    }

}

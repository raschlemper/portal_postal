import Util.XTrustProvider;
import br.com.correios.bsb.sigep.master.bean.cliente.AssuntoPIMaster;
import br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente;
import br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService;
import br.com.correios.bsb.sigep.master.bean.cliente.AutenticacaoException;
import br.com.correios.bsb.sigep.master.bean.cliente.Cliente;
import br.com.correios.bsb.sigep.master.bean.cliente.Conta;
import br.com.correios.bsb.sigep.master.bean.cliente.Destinatario;
import br.com.correios.bsb.sigep.master.bean.cliente.MotivoPIMaster;
import br.com.correios.bsb.sigep.master.bean.cliente.PedidoInformacaoRegistro;
import br.com.correios.bsb.sigep.master.bean.cliente.Postagem;
import br.com.correios.bsb.sigep.master.bean.cliente.Remetente;
import br.com.correios.bsb.sigep.master.bean.cliente.Retorno;
import br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class teste {

    private static java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.Retorno> consultarPedidosInformacao(java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.PedidoInformacaoConsulta> pedidosInformacao, java.lang.String usuario, java.lang.String senha) throws SigepClienteException, AutenticacaoException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.consultarPedidosInformacao(pedidosInformacao, usuario, senha);
    }

    private static java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.AssuntoPIMaster> obterAssuntosPI() throws SigepClienteException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.obterAssuntosPI();
    }

    private static java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.MotivoPIMaster> obterMotivosPI() throws SigepClienteException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.obterMotivosPI();
    }

    private static java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.Retorno> registrarPedidosInformacao(java.util.List<br.com.correios.bsb.sigep.master.bean.cliente.PedidoInformacaoRegistro> pedidosInformacao, java.lang.String usuario, java.lang.String senha) throws SigepClienteException, AutenticacaoException {
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
        br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
        return port.registrarPedidosInformacao(pedidosInformacao, usuario, senha);
    }

    public static void main(String[] args) throws SigepClienteException {
        try {
            /*   List<AssuntoPIMaster> mpi = obterAssuntosPI();
        
             for (int i = 0; i < mpi.size(); i++) {
             System.out.println(mpi.get(i).getCodigo() +" - "+ mpi.get(i).getDescricao());
            
             }
      
             */
            //Cliente 
            String numeroContrato = "";
            String possuiContrato = "sim";
            Cliente cliente;
            cliente = new Cliente();
            cliente.setNumeroContrato(numeroContrato);
            cliente.setPossuiContrato(possuiContrato);

            String codigoRegistro = "JH258506913BR";

            //conta
            String codigoBanco = "001";
            String nomeBanco = "Banco do Brasil";
            String numeroAgencia = "0156";
            String numeroConta = "01245-5";
            Conta conta;
            conta = new Conta();
            conta.setCodigoBanco(codigoBanco);
            conta.setNomeBanco(nomeBanco);
            conta.setNumeroAgencia(numeroAgencia);
            conta.setNumeroConta(numeroConta);

            String conteudoObjeto = "";
            String cpfCnpj = "12781661000180";

            //destinataro
            Destinatario destinatario;
            destinatario = new Destinatario();
            destinatario.setCep("88015972");
            destinatario.setNome("Fulano de Tal");

            String embalagem = "";
        //motivos
        /*
             <option value="134">Conteúdo Avariado</option>
             <option value="133">Correspondência Violada</option>
             <option value="211">Destinatário não recebeu a correspondência</option>
             <option value="136">Objeto devolvido indevidamente</option>
             <option value="135">Objeto entregue com atraso</option>
             <option value="132">Objeto entregue indevidamente</option>
             <option value="148">Remetente não recebeu o AR</option>
        
             */
            int motivo = 211;
            String observacao = "obs";
            // postagem

            String agencia = "";
            String avisoRecebimento = "";
            String data = "";
            String local = "";
            String valorDeclarado = "";
            Postagem postagem = new Postagem();

            //remetente
            String email = "info@scc4.com.br";
            String empresa = "NZN COMERCIO DE ELETRONICOS LTDA";
            String fax = "33332700";
            Remetente remetente;
            remetente = new Remetente();
            remetente.setEmail(email);
            remetente.setEmpresa(empresa);
            remetente.setFax(fax);

            /*
             <option value="113">SEDEX Documento</option>
             <option value="115">SEDEX 10</option>
             <option value="408">SEDEX 12</option>
             <option value="260">SEDEX a Cobrar</option>
             <option value="335">E-SEDEX</option>
             <option value="123">Encomenda PAC</option>
             <option value="114">SEDEX Mercadoria</option>
             <option value="117">Vale Postal Nacional</option>
             */
            int servico = 113;
            String tipoDocumento = "cpf";

            //terminar   
            PedidoInformacaoRegistro pt = new PedidoInformacaoRegistro();
            pt.setId(Long.valueOf("1"));
            pt.setCliente(cliente);
            pt.setCodigoRegistro(codigoRegistro);
            pt.setConta(conta);
            pt.setConteudoObjeto(conteudoObjeto);
            pt.setCpfCnpj(cpfCnpj);
            pt.setDestinatario(destinatario);
            pt.setEmbalagem(embalagem);
            pt.setMotivo(motivo);
            pt.setObservacao(observacao);
            pt.setPostagem(postagem);
            pt.setServico(servico);
            pt.setTipoDocumento(tipoDocumento);
            List<PedidoInformacaoRegistro> lstpt = new ArrayList<PedidoInformacaoRegistro>();
            lstpt.add(pt);
            

            String usr = "rafael@zcod.com.br";//"13157345";
            String psw = "s1qmo0";//"12781661"
            System.out.println(lstpt.get(0).getCodigoRegistro());
            Util.XTrustProvider.install();
            List<Retorno> ret = registrarPedidosInformacao(lstpt, usr, psw);

            System.out.println(ret.get(0).getCodigoPI());

        } catch (AutenticacaoException ex) {
            System.out.println(ex.getMessage());
        } catch (SigepClienteException ex) {
            System.out.println("SigepClienteException - " + ex.getMessage() + " - " + ex.getFaultInfo());
        } catch (Exception ex) {
            System.out.println("Exception - " + ex.getMessage());
        }
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Controle.ContrCep;
import Entidade.Endereco;
import br.com.correios.bsb.sigep.master.bean.cliente.EnderecoERP;
import br.com.correios.bsb.sigep.master.bean.cliente.SQLException_Exception;
import br.com.correios.bsb.sigep.master.bean.cliente.SigepClienteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RICADINHO
 */
public class PesquisarCep {

    public static Endereco pesquisaCEP(String cep) {
            cep = cep.replaceAll("\\.", "").replaceAll("-", "");
            Endereco end = ContrCep.pesquisaCep(cep);
            if(end != null){
                if(end.getLogradouro().equals("CEP inexistente")){
                    try {
                        EnderecoERP e = consultaCEP(cep);
                        if(e != null){
                            String cidade = FormataString.removeAccentsToUpper(e.getCidade());
                            String bairro = FormataString.removeAccentsToUpper(e.getBairro());
                            String uf = FormataString.removeAccentsToUpper(e.getUf());
                            String logradouro = FormataString.removeAccentsToUpper(e.getEnd());
                            ContrCep.inserir(cep, uf, cidade, bairro, logradouro);
                            return new Endereco(bairro, cidade, logradouro, uf, cep);
                        }else{
                            return new Endereco("", "", "CEP inexistente", "", cep);
                        }
                    } catch (SigepClienteException ex) {
                        Logger.getLogger(PesquisarCep.class.getName()).log(Level.SEVERE, null, ex);
                        return new Endereco("", "", "CEP inexistente", "", cep);
                    } catch (SQLException_Exception ex) {
                        Logger.getLogger(PesquisarCep.class.getName()).log(Level.SEVERE, null, ex);
                        return new Endereco("", "", "CEP inexistente", "", cep);
                    }
                }else{
                    return end;
                }
            } else {
                try {
                    //QUANDO DA ERRO NO SQL
                    XTrustProvider.install();
                    EnderecoERP e = consultaCEP(cep);
                    if(e != null){
                        String cidade = FormataString.removeAccentsToUpper(e.getCidade());
                        String bairro = FormataString.removeAccentsToUpper(e.getBairro());
                        String uf = FormataString.removeAccentsToUpper(e.getUf());
                        String logradouro = FormataString.removeAccentsToUpper(e.getEnd());
                        ContrCep.inserir(cep, uf, cidade, bairro, logradouro);
                        return new Endereco(bairro, cidade, logradouro, uf, cep);
                    }else{
                        return new Endereco("", "", "CEP inexistente", "", cep);
                    }
                } catch (SigepClienteException ex) {
                    Logger.getLogger(PesquisarCep.class.getName()).log(Level.SEVERE, null, ex);
                    return new Endereco("", "", "CEP inexistente", "", cep);
                } catch (SQLException_Exception ex) {
                    Logger.getLogger(PesquisarCep.class.getName()).log(Level.SEVERE, null, ex);
                    return new Endereco("", "", "CEP inexistente", "", cep);
                }
            }
    }

    private static EnderecoERP consultaCEP(java.lang.String cep) throws  SigepClienteException, SQLException_Exception {
        //try {
            br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService service = new br.com.correios.bsb.sigep.master.bean.cliente.AtendeClienteService();
            br.com.correios.bsb.sigep.master.bean.cliente.AtendeCliente port = service.getAtendeClientePort();
            return port.consultaCEP(cep);
        /*} catch (SQLException_Exception ex) {
            Logger.getLogger(PesquisarCep.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }*/
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class Movimentacao {

    private String id;
    private String numCaixa;
    private String numVenda;
    private String seqVenda;
    private Date dataPostagem;
    private String descServico;
    private String numObjeto;
    private String destinatario;
    private String notaFiscal;
    private float peso;
    private String cep;
    private String paisDestino;
    private float valorServico;
    private float valorDestino;
    private float quantidade;
    private float valorDeclarado;
    private String departamento;
    private String codCliente;
    private String codSto;
    private String conteudoObjeto;
    private String contratoEct;
    private float altura;
    private float largura;
    private float comprimento;
    private String status;
    private Date dataEntrega;
    private String siglaServAdicionais;
    private String codigoEct;
    private int baixaAr;
    private String nomeRecebAr;
    private String dataBaixaAr;
    private int codStatus;
    private String cliente; // so usa quando tem join para pegar onome do cliente

    private int idPre_venda;
    private int idOS;
    
    private Date last_status_date;    
    private int last_status_code;
    private String last_status_type;
    private String last_status_name;
    private Date prazo_estimado;
    private Timestamp prazo_cumprido;

    /*<th><h3>Cliente</h3></th>
     <th><h3>Nº do Objeto</h3></th>
     <th><h3>Serviço</h3></th>                                        
     <th><h3>Destinatário</h3></th>
     <th><h3>CEP</h3></th>                                        
     <th width="40"><h3>AR</h3></th>                                        
     <th><h3>Impressa em</h3></th>
     <th><h3>Postada em</h3></th>
     <th width="120"><h3>Ultimo status</h3></th>
     <th class="nosort" width="40"><h3>PI</h3></th>*/
    public Movimentacao(Date dataPostagem, String descServico, String numObjeto, String destinatario, String cep, float valorServico, float valorDeclarado, String codCliente, String cliente, String contratoEct, String codigoEct, int codStatus, String status) {
        this.dataPostagem = dataPostagem;
        this.descServico = descServico;
        this.numObjeto = numObjeto;
        this.destinatario = destinatario;
        this.cep = cep;
        this.valorServico = valorServico;
        this.valorDeclarado = valorDeclarado;
        this.codCliente = codCliente;
        this.cliente = cliente;
        this.contratoEct = contratoEct;
        this.codigoEct = codigoEct;
        this.codStatus = codStatus;
        this.status = status;
    }

    public Movimentacao(Date dataPostagem, String descServico, float valorServico, float quantidade, String codCliente, String codigoEct) {
        this.dataPostagem = dataPostagem;
        this.descServico = descServico;
        this.valorServico = valorServico;
        this.quantidade = quantidade;
        this.codCliente = codCliente;
        this.codigoEct = codigoEct;
    }

    public Movimentacao(Date dataPostagem, String numObjeto, String destinatario, String cep, String departamento, String status, int baixaAr, String nomeRecebAr, String dataBaixaAr) {
        this.dataPostagem = dataPostagem;
        this.numObjeto = numObjeto;
        this.destinatario = destinatario;
        this.cep = cep;
        this.departamento = departamento;
        this.status = status;
        this.baixaAr = baixaAr;
        this.nomeRecebAr = nomeRecebAr;
        this.dataBaixaAr = dataBaixaAr;
    }

    public Movimentacao(Date dataPostagem, String descServico, String numObjeto, String destinatario, float peso, String cep, float valorServico, float quantidade, String departamento, String status, Date dataEntrega, String notaFiscal, String numVenda, String numCaixa, int codStatus) {
        this.dataPostagem = dataPostagem;
        this.descServico = descServico;
        this.numObjeto = numObjeto;
        this.destinatario = destinatario;
        this.peso = peso;
        this.cep = cep;
        this.valorServico = valorServico;
        this.quantidade = quantidade;
        this.departamento = departamento;
        this.status = status;
        this.dataEntrega = dataEntrega;
        this.notaFiscal = notaFiscal;
        this.numVenda = numVenda;
        this.numCaixa = numCaixa;
        this.codStatus = codStatus;
    }

    public Movimentacao(String id, Date dataPostagem, String descServico, String numObjeto, String destinatario, float peso, String cep, float valorServico, float quantidade, String departamento, String status, Date dataEntrega, String notaFiscal, String numVenda, String numCaixa, int codStatus, Date last_status_date, int last_status_code, String last_status_type, String last_status_name, Date prazo_estimado, Timestamp prazo_cumprido) {
        this.id = id;
        this.dataPostagem = dataPostagem;
        this.descServico = descServico;
        this.numObjeto = numObjeto;
        this.destinatario = destinatario;
        this.peso = peso;
        this.cep = cep;
        this.valorServico = valorServico;
        this.quantidade = quantidade;
        this.departamento = departamento;
        this.status = status;
        this.dataEntrega = dataEntrega;
        this.notaFiscal = notaFiscal;
        this.numVenda = numVenda;
        this.numCaixa = numCaixa;
        this.codStatus = codStatus;
        this.last_status_date = last_status_date;
        this.last_status_code = last_status_code;
        this.last_status_type = last_status_type;
        this.last_status_name = last_status_name;
        this.prazo_estimado = prazo_estimado;
        this.prazo_cumprido = prazo_cumprido;
    }
    public Movimentacao(String id, Date dataPostagem, String descServico, String numObjeto, String destinatario, float peso, String cep, float valorServico, float quantidade, String departamento, String status, Date dataEntrega, String notaFiscal, String numVenda, String numCaixa) {
        this.id = id;
        this.dataPostagem = dataPostagem;
        this.descServico = descServico;
        this.numObjeto = numObjeto;
        this.destinatario = destinatario;
        this.peso = peso;
        this.cep = cep;
        this.valorServico = valorServico;
        this.quantidade = quantidade;
        this.departamento = departamento;
        this.status = status;
        this.dataEntrega = dataEntrega;
        this.notaFiscal = notaFiscal;
        this.numVenda = numVenda;
        this.numCaixa = numCaixa;
    }

    public Movimentacao(String id, Date dataPostagem, String descServico, String numObjeto, String destinatario,
            float peso, String cep, float valorServico, float quantidade, String departamento, String status, Date dataEntrega,
            String notaFiscal, String numVenda, String numCaixa, float valorDeclarado, float valorDestino,
            String paisDestino, String contratoEct, String conteudoObjeto, String siglaServAdicionais,
            int codStatus, float altura, float largura, float comprimento, int idPre_venda, int idOS, 
            Date last_status_date, int last_status_code, String last_status_type, String last_status_name, Date prazo_estimado, Timestamp prazo_cumprido) {
        this.id = id;
        this.dataPostagem = dataPostagem;
        this.descServico = descServico;
        this.numObjeto = numObjeto;
        this.destinatario = destinatario;
        this.peso = peso;
        this.cep = cep;
        this.valorServico = valorServico;
        this.quantidade = quantidade;
        this.departamento = departamento;
        this.status = status;
        this.dataEntrega = dataEntrega;
        this.notaFiscal = notaFiscal;
        this.numVenda = numVenda;
        this.numCaixa = numCaixa;
        this.valorDeclarado = valorDeclarado;
        this.valorDestino = valorDestino;
        this.paisDestino = paisDestino;
        this.contratoEct = contratoEct;
        this.conteudoObjeto = conteudoObjeto;
        this.siglaServAdicionais = siglaServAdicionais;
        this.codStatus = codStatus;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.idPre_venda = idPre_venda;
        this.idOS = idOS;
        this.last_status_date = last_status_date;
        this.last_status_code = last_status_code;
        this.last_status_type = last_status_type;
        this.last_status_name = last_status_name;
        this.prazo_estimado = prazo_estimado;
        this.prazo_cumprido = prazo_cumprido;
    }

    public Movimentacao(String seqVenda, Date dataPostagem, String descServico, String numObjeto, String destinatario, String notaFiscal, float peso, String cep, float valorServico, float valorDestino, float quantidade, float valorDeclarado, String contratoEct, float altura, float largura, float comprimento, String siglaServAdicionais, String paisDestino, String codigoEct) {
        this.seqVenda = seqVenda;
        this.dataPostagem = dataPostagem;
        this.descServico = descServico;
        this.numObjeto = numObjeto;
        this.destinatario = destinatario;
        this.notaFiscal = notaFiscal;
        this.peso = peso;
        this.cep = cep;
        this.valorServico = valorServico;
        this.valorDestino = valorDestino;
        this.quantidade = quantidade;
        this.valorDeclarado = valorDeclarado;
        this.contratoEct = contratoEct;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.siglaServAdicionais = siglaServAdicionais;
        this.paisDestino = paisDestino;
        this.codigoEct = codigoEct;
    }

    public Movimentacao(String seqVenda, Date dataPostagem, String descServico, String numObjeto, String destinatario, String notaFiscal, float peso, String cep, float valorServico, float valorDestino, float quantidade, float valorDeclarado, String contratoEct, float altura, float largura, float comprimento, String siglaServAdicionais, String paisDestino, String codigoEct, String numCaixa, String numVenda) {
        this.seqVenda = seqVenda;
        this.dataPostagem = dataPostagem;
        this.descServico = descServico;
        this.numObjeto = numObjeto;
        this.destinatario = destinatario;
        this.notaFiscal = notaFiscal;
        this.peso = peso;
        this.cep = cep;
        this.valorServico = valorServico;
        this.valorDestino = valorDestino;
        this.quantidade = quantidade;
        this.valorDeclarado = valorDeclarado;
        this.contratoEct = contratoEct;
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
        this.siglaServAdicionais = siglaServAdicionais;
        this.paisDestino = paisDestino;
        this.codigoEct = codigoEct;
        this.numVenda = numVenda;
        this.numCaixa = numCaixa;
    }

    public Date getLast_status_date() {
        return last_status_date;
    }

    public void setLast_status_date(Date last_status_date) {
        this.last_status_date = last_status_date;
    }
    
    public int getLast_status_code() {
        return last_status_code;
    }

    public void setLast_status_code(int last_status_code) {
        this.last_status_code = last_status_code;
    }

    public String getLast_status_type() {
        return last_status_type;
    }

    public void setLast_status_type(String last_status_type) {
        this.last_status_type = last_status_type;
    }

    public String getLast_status_name() {
        return last_status_name;
    }

    public void setLast_status_name(String last_status_name) {
        this.last_status_name = last_status_name;
    }

    public Date getPrazo_estimado() {
        return prazo_estimado;
    }

    public void setPrazo_estimado(Date prazo_estimado) {
        this.prazo_estimado = prazo_estimado;
    }
    
    public Date getPrazo_cumprido_date() {
        if (prazo_cumprido == null || prazo_cumprido.equals("")) {
            return null;
        }
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = (java.util.Date) formatter.parse(formatter.format(prazo_cumprido));
        } catch (ParseException e) {
            //System.out.println(e.getMessage());
        }
        return date;
    }

    public Timestamp getPrazo_cumprido() {
        return prazo_cumprido;
    }

    public void setPrazo_cumprido(Timestamp prazo_cumprido) {
        this.prazo_cumprido = prazo_cumprido;
    }
        
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdPre_venda() {
        return idPre_venda;
    }

    public void setIdPre_venda(int idPre_venda) {
        this.idPre_venda = idPre_venda;
    }

    public int getIdOS() {
        return idOS;
    }

    public void setIdOS(int idOS) {
        this.idOS = idOS;
    }

    public int getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(int codStatus) {
        this.codStatus = codStatus;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getCodSto() {
        return codSto;
    }

    public void setCodSto(String codSto) {
        this.codSto = codSto;
    }

    public float getComprimento() {
        return comprimento;
    }

    public void setComprimento(float comprimento) {
        this.comprimento = comprimento;
    }

    public String getConteudoObjeto() {
        return conteudoObjeto;
    }

    public void setConteudoObjeto(String conteudoObjeto) {
        this.conteudoObjeto = conteudoObjeto;
    }

    public String getContratoEct() {
        return contratoEct;
    }

    public void setContratoEct(String contratoEct) {
        this.contratoEct = contratoEct;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public Date getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(Date dataPostagem) {
        this.dataPostagem = dataPostagem;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDescServico() {
        return descServico;
    }

    public void setDescServico(String descServico) {
        this.descServico = descServico;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public float getLargura() {
        return largura;
    }

    public void setLargura(float largura) {
        this.largura = largura;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getNumCaixa() {
        return numCaixa;
    }

    public void setNumCaixa(String numCaixa) {
        this.numCaixa = numCaixa;
    }

    public String getNumObjeto() {
        return numObjeto;
    }

    public void setNumObjeto(String numObjeto) {
        this.numObjeto = numObjeto;
    }

    public String getNumVenda() {
        return numVenda;
    }

    public void setNumVenda(String numVenda) {
        this.numVenda = numVenda;
    }

    public String getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(String paisDestino) {
        this.paisDestino = paisDestino;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public String getSeqVenda() {
        return seqVenda;
    }

    public void setSeqVenda(String seqVenda) {
        this.seqVenda = seqVenda;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getValorDeclarado() {
        return valorDeclarado;
    }

    public void setValorDeclarado(float valorDeclarado) {
        this.valorDeclarado = valorDeclarado;
    }

    public float getValorDestino() {
        return valorDestino;
    }

    public void setValorDestino(float valorDestino) {
        this.valorDestino = valorDestino;
    }

    public float getValorServico() {
        return valorServico;
    }

    public void setValorServico(float valorServico) {
        this.valorServico = valorServico;
    }

    public String getSiglaServAdicionais() {
        return siglaServAdicionais;
    }

    public void setSiglaServAdicionais(String siglaServAdicionais) {
        this.siglaServAdicionais = siglaServAdicionais;
    }

    public String getCodigoEct() {
        return codigoEct;
    }

    public void setCodigoEct(String codigoEct) {
        this.codigoEct = codigoEct;
    }

    public int getBaixaAr() {
        return baixaAr;
    }

    public void setBaixaAr(int baixaAr) {
        this.baixaAr = baixaAr;
    }

    public String getDataBaixaAr() {
        return dataBaixaAr;
    }

    public void setDataBaixaAr(String dataBaixaAr) {
        this.dataBaixaAr = dataBaixaAr;
    }

    public String getNomeRecebAr() {
        return nomeRecebAr;
    }

    public void setNomeRecebAr(String nomeRecebAr) {
        this.nomeRecebAr = nomeRecebAr;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

}

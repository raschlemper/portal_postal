/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.portalpostal.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Viviane
 */
@Entity
@Table(name = "cliente")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByCodigo", query = "SELECT c FROM Cliente c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "Cliente.findByNome", query = "SELECT c FROM Cliente c WHERE c.nome = :nome")})
public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "uf")
    private String uf;
    @Column(name = "cep")
    private Integer cep;
    @Column(name = "email")
    private String email;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "nomeFantasia")
    private String nomeFantasia;
    @Column(name = "complemento")
    private String complemento;
    @Column(name = "codSTO")
    private String codSTO;
    @Column(name = "metodoInsercao")
    private Integer metodoInsercao;
    @Column(name = "numero")
    private String numero;
    @Column(name = "url_logo")
    private String urlLogo;
    @Column(name = "temContrato")
    private Integer temContrato;
    @Column(name = "numContrato")
    private String numContrato;
    @Column(name = "anoContrato")
    private Integer anoContrato;
    @Column(name = "ufContrato")
    private String ufContrato;
    @Column(name = "nomeContrato")
    private String nomeContrato;
    @Column(name = "cartaoPostagem")
    private String cartaoPostagem;
    @Column(name = "usaEtiquetador")
    private Integer usaEtiquetador;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "envio_email")
    private Integer envioEmail;
    @Column(name = "login_correio")
    private String loginCorreio;
    @Column(name = "senha_correio")
    private String senhaCorreio;
    @Column(name = "login_reversa")
    private String loginReversa;
    @Column(name = "senha_reversa")
    private String senhaReversa;
    @Column(name = "cartao_reversa")
    private String cartaoReversa;
    @Column(name = "login_sigep")
    private String loginSigep;
    @Column(name = "senha_sigep")
    private String senhaSigep;
    @Column(name = "codAdministrativo")
    private String codAdministrativo;
    @Column(name = "dtVigenciaFimContrato")
    @Temporal(TemporalType.DATE)
    private Date dtVigenciaFimContrato;
    @Column(name = "codDiretoria")
    private Integer codDiretoria;
    @Column(name = "statusCartaoPostagem")
    private Integer statusCartaoPostagem;
    @Column(name = "nomeClienteSara")
    private String nomeClienteSara;
    @Column(name = "nome_etq")
    private Integer nomeEtq;
    @Column(name = "erro_atualizacao")
    private Integer erroAtualizacao;
    @Column(name = "dataHoraAtualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraAtualizacao;
    @Column(name = "idGrupoFaturamento")
    private Integer idGrupoFaturamento;
    @Column(name = "separar_destinatarios")
    private Integer separarDestinatarios;
    @Column(name = "ar_digital")
    private Integer arDigital;
    @Column(name = "ativo")
    private Short ativo;
    @Lob
    @Column(name = "obscli")
    private String obscli;

    @MapsId("codigo")
    @OneToMany(targetEntity = ClienteDepartamentos.class)
    @JoinColumn(name="idCliente",referencedColumnName = "codigo")
    private List<ClienteDepartamentos> departamentos;

    @MapsId("codigo")
    @JoinColumn(name="idCliente",referencedColumnName = "codigo")
    @OneToMany(targetEntity = ClienteContrato.class)
    private List<ClienteContrato> contratos;

    public Cliente() {
    }

    public Cliente(Integer codigo) {
        this.codigo = codigo;
    }

    public Cliente(Integer codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCodSTO() {
        return codSTO;
    }

    public void setCodSTO(String codSTO) {
        this.codSTO = codSTO;
    }

    public Integer getMetodoInsercao() {
        return metodoInsercao;
    }

    public void setMetodoInsercao(Integer metodoInsercao) {
        this.metodoInsercao = metodoInsercao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Integer getTemContrato() {
        return temContrato;
    }

    public void setTemContrato(Integer temContrato) {
        this.temContrato = temContrato;
    }

    public String getNumContrato() {
        return numContrato;
    }

    public void setNumContrato(String numContrato) {
        this.numContrato = numContrato;
    }

    public Integer getAnoContrato() {
        return anoContrato;
    }

    public void setAnoContrato(Integer anoContrato) {
        this.anoContrato = anoContrato;
    }

    public String getUfContrato() {
        return ufContrato;
    }

    public void setUfContrato(String ufContrato) {
        this.ufContrato = ufContrato;
    }

    public String getNomeContrato() {
        return nomeContrato;
    }

    public void setNomeContrato(String nomeContrato) {
        this.nomeContrato = nomeContrato;
    }

    public String getCartaoPostagem() {
        return cartaoPostagem;
    }

    public void setCartaoPostagem(String cartaoPostagem) {
        this.cartaoPostagem = cartaoPostagem;
    }

    public Integer getUsaEtiquetador() {
        return usaEtiquetador;
    }

    public void setUsaEtiquetador(Integer usaEtiquetador) {
        this.usaEtiquetador = usaEtiquetador;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getEnvioEmail() {
        return envioEmail;
    }

    public void setEnvioEmail(Integer envioEmail) {
        this.envioEmail = envioEmail;
    }

    public String getLoginCorreio() {
        return loginCorreio;
    }

    public void setLoginCorreio(String loginCorreio) {
        this.loginCorreio = loginCorreio;
    }

    public String getSenhaCorreio() {
        return senhaCorreio;
    }

    public void setSenhaCorreio(String senhaCorreio) {
        this.senhaCorreio = senhaCorreio;
    }

    public String getLoginReversa() {
        return loginReversa;
    }

    public void setLoginReversa(String loginReversa) {
        this.loginReversa = loginReversa;
    }

    public String getSenhaReversa() {
        return senhaReversa;
    }

    public void setSenhaReversa(String senhaReversa) {
        this.senhaReversa = senhaReversa;
    }

    public String getCartaoReversa() {
        return cartaoReversa;
    }

    public void setCartaoReversa(String cartaoReversa) {
        this.cartaoReversa = cartaoReversa;
    }

    public String getLoginSigep() {
        return loginSigep;
    }

    public void setLoginSigep(String loginSigep) {
        this.loginSigep = loginSigep;
    }

    public String getSenhaSigep() {
        return senhaSigep;
    }

    public void setSenhaSigep(String senhaSigep) {
        this.senhaSigep = senhaSigep;
    }

    public String getCodAdministrativo() {
        return codAdministrativo;
    }

    public void setCodAdministrativo(String codAdministrativo) {
        this.codAdministrativo = codAdministrativo;
    }

    public Date getDtVigenciaFimContrato() {
        return dtVigenciaFimContrato;
    }

    public void setDtVigenciaFimContrato(Date dtVigenciaFimContrato) {
        this.dtVigenciaFimContrato = dtVigenciaFimContrato;
    }

    public Integer getCodDiretoria() {
        return codDiretoria;
    }

    public void setCodDiretoria(Integer codDiretoria) {
        this.codDiretoria = codDiretoria;
    }

    public Integer getStatusCartaoPostagem() {
        return statusCartaoPostagem;
    }

    public void setStatusCartaoPostagem(Integer statusCartaoPostagem) {
        this.statusCartaoPostagem = statusCartaoPostagem;
    }

    public String getNomeClienteSara() {
        return nomeClienteSara;
    }

    public void setNomeClienteSara(String nomeClienteSara) {
        this.nomeClienteSara = nomeClienteSara;
    }

    public Integer getNomeEtq() {
        return nomeEtq;
    }

    public void setNomeEtq(Integer nomeEtq) {
        this.nomeEtq = nomeEtq;
    }

    public Integer getErroAtualizacao() {
        return erroAtualizacao;
    }

    public void setErroAtualizacao(Integer erroAtualizacao) {
        this.erroAtualizacao = erroAtualizacao;
    }

    public Date getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }

    public void setDataHoraAtualizacao(Date dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    public Integer getIdGrupoFaturamento() {
        return idGrupoFaturamento;
    }

    public void setIdGrupoFaturamento(Integer idGrupoFaturamento) {
        this.idGrupoFaturamento = idGrupoFaturamento;
    }

    public Integer getSepararDestinatarios() {
        return separarDestinatarios;
    }

    public void setSepararDestinatarios(Integer separarDestinatarios) {
        this.separarDestinatarios = separarDestinatarios;
    }

    public Integer getArDigital() {
        return arDigital;
    }

    public void setArDigital(Integer arDigital) {
        this.arDigital = arDigital;
    }

    public Short getAtivo() {
        return ativo;
    }

    public void setAtivo(Short ativo) {
        this.ativo = ativo;
    }

    public String getObscli() {
        return obscli;
    }

    public void setObscli(String obscli) {
        this.obscli = obscli;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.portalpostal.entity.Cliente[ codigo=" + codigo + " ]";
    }

    public List<ClienteDepartamentos> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<ClienteDepartamentos> departamentos) {
        this.departamentos = departamentos;
    }

    public List<ClienteContrato> getContratos() {
        return contratos;
    }

    public void setContratos(List<ClienteContrato> contratos) {
        this.contratos = contratos;
    }

}

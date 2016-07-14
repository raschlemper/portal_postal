/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidade;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class Clientes {

    private int codigo;
    private String nome;
    private String endereco;
    private String telefone;
    private String bairro;
    private String cidade;
    private String uf;
    private int cep;
    private String email;
    private String cnpj;
    private String nomeFantasia;
    private String complemento;
    private String senha;
    private String numero;
    private String url_logo;
    private int temContrato;
    private String numContrato;
    private int anoContrato;
    private String ufContrato;
    private String nomeContrato;
    private int usaEtiquetador;
    private double latitude;
    private double longitude;
    private String cartaoPostagem;
    private int envio_email;
    private String login_correio;
    private String senha_correio;
    private String codAdministrativo;
    private Date dtVigenciaFimContrato;
    private int codDiretoria;
    private int statusCartaoPostagem; //1 = ok, 0 = suspenso
    private String nomeClienteSara;
    private int nome_etq;
    private int erro_atualizacao;
    private Timestamp dataHoraAtualizacao;
    private String login_reversa;
    private String senha_reversa;
    private String cartao_reversa;
    private String login_sigep;
    private String senha_sigep;
    private int idGrupoFaturamento;
    private int ativo;
    private int separar_destinatarios;
    
    private float fat_mes;

    public Clientes(int codigo, String nome, float fat_mes) {
        this.codigo = codigo;
        this.nome = nome;
        this.fat_mes = fat_mes;
    }
    
    

    public Clientes(int codigo, String nome, String endereco, String telefone, String bairro, String cidade, String uf, int cep, String email, String cnpj, String nomeFantasia, String complemento, String senha, String numero, String url_logo, int temContrato, String numContrato, int anoContrato, String ufContrato, String nomeContrato, int usaEtiquetador, double latitude, double longitude, String cartaoPostagem, int envio_email, String login_correio, String senha_correio, String codAdministrativo, Date dtVigenciaFimContrato, int codDiretoria, int statusCartaoPostagem, String nomeClienteSara, int nome_etq, int erro_atualizacao, Timestamp dataHoraAtualizacao, String login_reversa, String senha_reversa, String cartao_reversa, String login_sigep, String senha_sigep, int idGrupoFaturamento, int ativo, int separar_destinatarios, float fat_mes) {
        this.codigo = codigo;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.email = email;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.complemento = complemento;
        this.senha = senha;
        this.numero = numero;
        this.url_logo = url_logo;
        this.temContrato = temContrato;
        this.numContrato = numContrato;
        this.anoContrato = anoContrato;
        this.ufContrato = ufContrato;
        this.nomeContrato = nomeContrato;
        this.usaEtiquetador = usaEtiquetador;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cartaoPostagem = cartaoPostagem;
        this.envio_email = envio_email;
        this.login_correio = login_correio;
        this.senha_correio = senha_correio;
        this.codAdministrativo = codAdministrativo;
        this.dtVigenciaFimContrato = dtVigenciaFimContrato;
        this.codDiretoria = codDiretoria;
        this.statusCartaoPostagem = statusCartaoPostagem;
        this.nomeClienteSara = nomeClienteSara;
        this.nome_etq = nome_etq;
        this.erro_atualizacao = erro_atualizacao;
        this.dataHoraAtualizacao = dataHoraAtualizacao;
        this.login_reversa = login_reversa;
        this.senha_reversa = senha_reversa;
        this.cartao_reversa = cartao_reversa;
        this.login_sigep = login_sigep;
        this.senha_sigep = senha_sigep;
        this.idGrupoFaturamento = idGrupoFaturamento;
        this.ativo = ativo;
        this.separar_destinatarios = separar_destinatarios;
        this.fat_mes = fat_mes;
    }
    
    
    

    public Clientes(ResultSet result) throws SQLException {

        this.codigo = result.getInt("codigo");
        this.nome = result.getString("nome");
        this.endereco = result.getString("endereco");
        this.telefone = result.getString("telefone");
        this.bairro = result.getString("bairro");
        this.cidade = result.getString("cidade");
        this.uf = result.getString("uf");
        this.cep = result.getInt("cep");
        this.email = result.getString("email");
        this.cnpj = result.getString("cnpj");
        this.nomeFantasia = result.getString("nomeFantasia");
        this.complemento = result.getString("complemento");
        this.numero = result.getString("numero");
        this.url_logo = result.getString("url_logo");
        this.temContrato = result.getInt("temContrato");
        this.numContrato = result.getString("numContrato");
        this.anoContrato = result.getInt("anoContrato");
        this.ufContrato = result.getString("ufContrato");
        this.nomeContrato = result.getString("nomeContrato");
        this.cartaoPostagem = result.getString("cartaoPostagem");
        this.usaEtiquetador = result.getInt("usaEtiquetador");
        this.latitude = result.getDouble("latitude");
        this.longitude = result.getDouble("longitude");
        this.envio_email = result.getInt("envio_email");
        this.login_correio = result.getString("login_correio");
        this.senha_correio = result.getString("senha_correio");
        this.codAdministrativo = result.getString("codAdministrativo");
        this.dtVigenciaFimContrato = result.getDate("dtVigenciaFimContrato");
        this.codDiretoria = result.getInt("codDiretoria");
        this.statusCartaoPostagem = result.getInt("statusCartaoPostagem");
        this.nomeClienteSara = result.getString("nomeClienteSara");
        this.nome_etq = result.getInt("nome_etq");
        this.erro_atualizacao = result.getInt("erro_atualizacao");
        this.dataHoraAtualizacao = result.getTimestamp("dataHoraAtualizacao");
        this.login_reversa = result.getString("login_reversa");
        this.senha_reversa = result.getString("senha_reversa");
        this.cartao_reversa = result.getString("cartao_reversa");
        this.login_sigep = result.getString("login_sigep");
        this.senha_sigep = result.getString("senha_sigep");
        this.idGrupoFaturamento = result.getInt("idGrupoFaturamento");
        this.ativo = result.getInt("ativo");
        this.separar_destinatarios = result.getInt("separar_destinatarios");              

    }
   

    public float getFat_mes() {
        return fat_mes;
    }

    public void setFat_mes(float fat_mes) {
        this.fat_mes = fat_mes;
    }

    
    
    public Clientes(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Clientes(String nome, String email, int codigo) {
        this.nome = nome;
        this.email = email;
        this.codigo = codigo;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    public int getSeparar_destinatarios() {
        return separar_destinatarios;
    }

    public void setSeparar_destinatarios(int separar_destinatarios) {
        this.separar_destinatarios = separar_destinatarios;
    }

    public int getIdGrupoFaturamento() {
        return idGrupoFaturamento;
    }

    public void setIdGrupoFaturamento(int idGrupoFaturamento) {
        this.idGrupoFaturamento = idGrupoFaturamento;
    }
    
    public String getCartao_reversa() {
        return cartao_reversa;
    }

    public void setCartao_reversa(String cartao_reversa) {
        this.cartao_reversa = cartao_reversa;
    }
    

    public String getLogin_sigep() {
        return login_sigep;
    }

    public void setLogin_sigep(String login_sigep) {
        this.login_sigep = login_sigep;
    }

    public String getSenha_sigep() {
        return senha_sigep;
    }

    public void setSenha_sigep(String senha_sigep) {
        this.senha_sigep = senha_sigep;
    }

    public String getLogin_reversa() {
        return login_reversa;
    }

    public void setLogin_reversa(String login_reversa) {
        this.login_reversa = login_reversa;
    }

    public String getSenha_reversa() {
        return senha_reversa;
    }

    public void setSenha_reversa(String senha_reversa) {
        this.senha_reversa = senha_reversa;
    }

    public int getErro_atualizacao() {
        return erro_atualizacao;
    }

    public void setErro_atualizacao(int erro_atualizacao) {
        this.erro_atualizacao = erro_atualizacao;
    }

    public Timestamp getDataHoraAtualizacao() {
        return dataHoraAtualizacao;
    }

    public void setDataHoraAtualizacao(Timestamp dataHoraAtualizacao) {
        this.dataHoraAtualizacao = dataHoraAtualizacao;
    }

    public int getEnvio_email() {
        return envio_email;
    }

    public void setEnvio_email(int envio_email) {
        this.envio_email = envio_email;
    }

    public String getLogin_correio() {
        return login_correio;
    }

    public void setLogin_correio(String login_correio) {
        this.login_correio = login_correio;
    }

    public String getSenha_correio() {
        return senha_correio;
    }

    public void setSenha_correio(String senha_correio) {
        this.senha_correio = senha_correio;
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

    public int getCodDiretoria() {
        return codDiretoria;
    }

    public void setCodDiretoria(int codDiretoria) {
        this.codDiretoria = codDiretoria;
    }

    public int getStatusCartaoPostagem() {
        return statusCartaoPostagem;
    }

    public void setStatusCartaoPostagem(int statusCartaoPostagem) {
        this.statusCartaoPostagem = statusCartaoPostagem;
    }

    public String getNomeClienteSara() {
        return nomeClienteSara;
    }

    public void setNomeClienteSara(String nomeClienteSara) {
        this.nomeClienteSara = nomeClienteSara;
    }

    public int getNome_etq() {
        return nome_etq;
    }

    public void setNome_etq(int nome_etq) {
        this.nome_etq = nome_etq;
    }

    public String getCartaoPostagem() {
        return cartaoPostagem;
    }

    public void setCartaoPostagem(String cartaoPostagem) {
        this.cartaoPostagem = cartaoPostagem;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getUsaEtiquetador() {
        return usaEtiquetador;
    }

    public void setUsaEtiquetador(int usaEtiquetador) {
        this.usaEtiquetador = usaEtiquetador;
    }

    public int getAnoContrato() {
        return anoContrato;
    }

    public void setAnoContrato(int anoContrato) {
        this.anoContrato = anoContrato;
    }

    public String getNomeContrato() {
        return nomeContrato;
    }

    public void setNomeContrato(String nomeContrato) {
        this.nomeContrato = nomeContrato;
    }

    public String getNumContrato() {
        return numContrato;
    }

    public void setNumContrato(String numContrato) {
        this.numContrato = numContrato;
    }

    public String getUfContrato() {
        return ufContrato;
    }

    public void setUfContrato(String ufContrato) {
        this.ufContrato = ufContrato;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getTemContrato() {
        return temContrato;
    }

    public void setTemContrato(int temContrato) {
        this.temContrato = temContrato;
    }

    public String getUrl_logo() {
        return url_logo;
    }

    public void setUrl_logo(String url_logo) {
        this.url_logo = url_logo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

}

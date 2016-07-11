package com.portalpostal.model;

import com.portalpostal.model.dd.TipoPessoaFornecedor;
import com.portalpostal.model.dd.TipoStatusFornecedor;
import java.util.List;

public class Fornecedor {
    
    private Integer idFornecedor;
    private String nomeFantasia;
    private String razaoSocial;
    private TipoStatusFornecedor status;
    private TipoPessoaFornecedor tipo;
    private String cpf;
    private String cnpj;
    private String inscricaoEstadual;
    private Fundacao fundacao;
    private CapitalSocial capitalSocial;
    private Categoria categoria;
    private Potencial potencial;
    private String telefone;
    private String celular;
    private String email;
    private String observacao;
    private Endereco endereco;
    private List<Lancamento> lancamentos;
    private List<LancamentoProgramado> lancamentosProgramados;
    
}

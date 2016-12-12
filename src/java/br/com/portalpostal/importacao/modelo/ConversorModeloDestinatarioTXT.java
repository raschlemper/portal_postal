package br.com.portalpostal.importacao.modelo;

import Emporium.Entity.MapeamentoLayout;
import br.com.portalpostal.entity.LayoutImportacao;
import br.com.portalpostal.exception.RegistroNaoEncontradoException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ConversorModeloDestinatarioTXT implements ConversorDestinatarioModelo {

    private final String layoutNaoEncontrado = "Modelo do layout não encontrado";
    private List<LayoutImportacao> layoutImportacao;

    public ConversorModeloDestinatarioTXT(String nomeModelo, EntityManager manager) {
        this.layoutImportacao = findModelo(nomeModelo, manager);
    }

     @Override
    public List<DestinatarioModelo> converter(List<String> linhasDoArquivo) {
        int contador = 1;
        int quantidadeLinhasPular = pularQuantidadeDeLinhas();
        List<DestinatarioModelo> destinatarios = new ArrayList<>();
        for (String linha : linhasDoArquivo) {
            if (contador > quantidadeLinhasPular) {
                destinatarios.add(criaDestinatario(linha));
            }
            contador++;
        }
        return destinatarios;
    }

     private int pularQuantidadeDeLinhas() {
        Integer quantidade = findAtribute(MapeamentoLayout.PARAMETROPULALINHA).getPosicaoInicial();
        return quantidade==null?0:quantidade;
    }

    public List<LayoutImportacao> findModelo(String nomeModelo, EntityManager manager) {
        TypedQuery<LayoutImportacao> query = manager.createNamedQuery("LayoutImportacao.findByName", LayoutImportacao.class);
        query.setParameter("nome", nomeModelo);
        layoutImportacao = query.getResultList();
        validaSeLayoutFoiEncontrado(layoutImportacao);
        return layoutImportacao;
    }

    private void validaSeLayoutFoiEncontrado(List<LayoutImportacao> layoutImportacao) throws RegistroNaoEncontradoException {
        if (layoutImportacao.isEmpty()) {
            throw new RegistroNaoEncontradoException(layoutNaoEncontrado);
        }
    }

    private LayoutImportacao findAtribute(String atributo) {
        for (LayoutImportacao layout : layoutImportacao) {
            if (layout.getAtributo().equals(atributo)) {
                return layout;
            }
        }
        return new LayoutImportacao();
    }

    private DestinatarioModelo criaDestinatario(String linha) {
        DestinatarioModelo destinatario = new DestinatarioModelo();
        destinatario.setNome(getNome(linha));
        destinatario.setEndereco(getLogradouro(linha));
        destinatario.setNumero(getNumero(linha));
        destinatario.setComplemento(getComplemento(linha));
        destinatario.setBairro(getBairro(linha));
        destinatario.setCidade(getCidade(linha));
        destinatario.setEstado(getEstado(linha));
        destinatario.setCep(getCep(linha));
        destinatario.setCelular(getCelular(linha));
        destinatario.setNumeroObjeto(getNumeroObjeto(linha));
        destinatario.setCodigoFinanceiro(getCodigoECT(linha));
        destinatario.setPeso(getPeso(linha));
        destinatario.setAltura(getAltura(linha));
        destinatario.setLargura(getLargura(linha));
        destinatario.setComprimento(getComprimento(linha));
        destinatario.setNota(getNotaFiscal(linha));
        destinatario.setSerieNota(getSerieNotaFiscal(linha));
        destinatario.setValorDeclarado(getValorDeclarado(linha));
        destinatario.setServicosAdicionais(getServicoAdicional(linha));
        destinatario.setEmail(getEmail(linha));
        destinatario.setConteudoDoObjeto(getConteudoObjeto(linha));
        destinatario.setQuantidadeVolumes(getQuantidadeVolume(linha));
        destinatario.setChaveCliente(getCodigoCliente(linha));
        destinatario.setObservacao(getObservacao(linha));
        return destinatario;
    }

    private String getNumeroObjeto(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.OBJETO, linha);
    }

    private String getNome(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.NOME, linha);
    }

    private String getCep(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.CEP, linha);
    }

    private String getLogradouro(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.LOGRADOURO, linha);
    }

    private String getNumero(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.NUMERO, linha);
    }

    private String getComplemento(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.COMPLEMENTO, linha);
    }

    private String getBairro(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.BAIRRO, linha);
    }

    private String getCidade(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.CIDADE, linha);
    }

    private String getEstado(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.UF, linha);
    }

    private String getCodigoECT(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.CODIGOECT, linha);
    }

    private String getServicoAdicional(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.SERVICOADICIONAL, linha);
    }

    private String getValorDeclarado(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.VALORDECLARADO, linha);
    }

    private String getNotaFiscal(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.NOTAFISCAL, linha);
    }

    private String getSerieNotaFiscal(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.SERIENOTAFISCAL, linha);
    }

    private String getQuantidadeVolume(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.QUANTIDADEVOLUME, linha);
    }

    private String getPeso(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.PESO, linha);
    }

    private String getAltura(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.ALTURA, linha);
    }

    private String getLargura(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.LARGURA, linha);
    }

    private String getComprimento(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.COMPRIMENTO, linha);
    }

    private String getCodigoCliente(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.IDENTIFICADOR, linha);
    }

    private String getConteudoObjeto(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.CONTEUDO, linha);
    }

    private String getEmail(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.EMAIL, linha);
    }

    private String getCelular(String linha) {
        return getAtributoDaLinhaDoArquivo(MapeamentoLayout.CELULAR, linha);
    }

    private String getObservacao(String linha) {
        StringBuilder observacao = new StringBuilder();
        observacao.append(getAtributoDaLinhaDoArquivo(MapeamentoLayout.OBSERVACAO, linha));
        observacao.append(" ");
        observacao.append(getAtributoDaLinhaDoArquivo(MapeamentoLayout.OUTRASOBSERVACAO, linha));
        return observacao.toString();
    }

    @Override
    public String getAtributoDaLinhaDoArquivo(String atributo, String linha) {
        try {
            return linha.substring(findAtribute(atributo).getPosicaoInicial(), findAtribute(atributo).getPosicaoFinal()).trim();
        } catch (Exception ex) {
            return "";
        }
    }

}

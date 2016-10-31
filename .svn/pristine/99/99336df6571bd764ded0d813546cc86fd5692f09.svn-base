package com.portalpostal.importacao.componentes;

import java.util.ArrayList;
import java.util.List;

public class ConversorDestinatarioModeloVip {

    private static final int[] POSICAO_NOME = {0, 50};
    private static final int[] POSICAO_ENDERECO = {50, 140};
    private static final int[] POSICAO_NUMERO = {140, 155};
    private static final int[] POSICAO_COMPLEMENTO = {155, 205};
    private static final int[] POSICAO_BAIRRO = {205, 255};
    private static final int[] POSICAO_CIDADE = {255, 345};
    private static final int[] POSICAO_ESTADO = {345, 347};
    private static final int[] POSICAO_CEP = {347, 387};
    private static final int[] POSICAO_TELEFONE = {387, 407};
    private static final int[] POSICAO_CODIGOFINANCEIRO = {407, 417};
    private static final int[] POSICAO_NUMEROOBJETO = {417, 430};
    private static final int[] POSICAO_PESO = {430, 436};
    private static final int[] POSICAO_ALTURA = {436, 441};
    private static final int[] POSICAO_LARGURA = {441, 446};
    private static final int[] POSICAO_COMPRIMENTO = {446, 451};
    private static final int[] POSICAO_CUBICO = {451, 457};
    private static final int[] POSICAO_NOTA = {457, 469};
    private static final int[] POSICAO_SERIENOTA = {469, 474};
    private static final int[] POSICAO_VALORDECLARADO = {474, 494};
    private static final int[] POSICAO_VALORCOBRAR = {494, 504};
    private static final int[] POSICAO_SERVICOSADICIONAIS = {504, 522};
    private static final int[] POSICAO_CONTRATO = {522, 533};
    private static final int[] POSICAO_ADMINISTRATIVO = {533, 544};
    private static final int[] POSICAO_CARTAO = {543, 555};
    private static final int[] POSICAO_OBSERVACAO = {655, 755};
    private static final int[] POSICAO_CONTEUDOOBJETO = {955, 1005};
    private static final int[] POSICAO_CODIGOVOLUME = {1005, 1009};
    private static final int[] POSICAO_QUANTIDADEVOLUME = {1009, 1013};
    private static final int[] POSICAO_CODIGOCLIENTEVISUAL = {1013, 1022};
    private static final int[] POSICAO_EMAIL = {555, 655};
    private static final int[] POSICAO_CELULAR = {1022, 1042};

    private static final int INDICE_INICIAL = 0;
    private static int INDICE_FINAL = 1;

    public List<DestinatarioModeloVip> converter(List<String> linhasDoArquivo) {
        List<DestinatarioModeloVip> destinatarios = new ArrayList<DestinatarioModeloVip>();
        for (String linha : linhasDoArquivo) {
            destinatarios.add(criaDestinatario(linha));
        }
        return destinatarios;
    }

    private DestinatarioModeloVip criaDestinatario(String linha) {
        DestinatarioModeloVip destinatario = new DestinatarioModeloVip();
        destinatario.setNome(getNome(linha));
        destinatario.setEndereco(getEndereco(linha));
        destinatario.setNumero(getNumero(linha));
        destinatario.setComplemento(getComplemento(linha));
        destinatario.setBairro(getBairro(linha));
        destinatario.setCidade(getCidade(linha));
        destinatario.setEstado(getEstado(linha));
        destinatario.setCep(getCep(linha));
        destinatario.setTelefone(getTelefone(linha));
        destinatario.setCodigoFinanceiro(getCodigoFinanceiro(linha));
        destinatario.setNumeroObjeto(getNumeroObjeto(linha));
        destinatario.setPeso(getPeso(linha));
        destinatario.setAltura(getAltura(linha));
        destinatario.setLargura(getLargura(linha));
        destinatario.setComprimento(getComprimento(linha));
        destinatario.setCubico(getCubico(linha));
        destinatario.setNota(getNota(linha));
        destinatario.setSerieNota(getSerieNota(linha));
        destinatario.setValorDeclarado(getValorDeclarado(linha));
        destinatario.setValorCobrar(getValorCobrar(linha));
        destinatario.setServicosAdicionais(getServicosAdicionais(linha));
        destinatario.setContrato(getContrato(linha));
        destinatario.setAdministrativo(getAdministrativo(linha));
        destinatario.setCartao(getCartao(linha));
        destinatario.setEmail(getEmail(linha));
        destinatario.setConteudoDoObjeto(getConteudoObjeto(linha));
        destinatario.setCodigoVolume(getCodigoVolume(linha));
        destinatario.setQuantidadeVolumes(getQuantidadeVolume(linha));
        destinatario.setCodigoClienteVisual(getCodigoVisual(linha));
        destinatario.setCelular(getCelular(linha));
        destinatario.setObservacao(getObservacao(linha));
        return destinatario;
    }

    private String getObservacao(String linha) {
        return linha.substring(POSICAO_OBSERVACAO[INDICE_INICIAL], POSICAO_OBSERVACAO[INDICE_FINAL]).trim();
    }

    private String getCelular(String linha) {
        return linha.substring(POSICAO_CELULAR[INDICE_INICIAL], POSICAO_CELULAR[INDICE_FINAL]).trim();
    }

    private String getCodigoVisual(String linha) {
        return linha.substring(POSICAO_CODIGOCLIENTEVISUAL[INDICE_INICIAL], POSICAO_CODIGOCLIENTEVISUAL[INDICE_FINAL]).trim();
    }

    private String getQuantidadeVolume(String linha) {
        return linha.substring(POSICAO_QUANTIDADEVOLUME[INDICE_INICIAL], POSICAO_QUANTIDADEVOLUME[INDICE_FINAL]).trim();
    }

    private String getCodigoVolume(String linha) {
        return linha.substring(POSICAO_CODIGOVOLUME[INDICE_INICIAL], POSICAO_CODIGOVOLUME[INDICE_FINAL]).trim();
    }

    private String getConteudoObjeto(String linha) {
        return linha.substring(POSICAO_CONTEUDOOBJETO[INDICE_INICIAL], POSICAO_CONTEUDOOBJETO[INDICE_FINAL]).trim();
    }

    private String getEmail(String linha) {
        return linha.substring(POSICAO_EMAIL[INDICE_INICIAL], POSICAO_EMAIL[INDICE_FINAL]).trim();
    }

    private String getCartao(String linha) {
        return linha.substring(POSICAO_CARTAO[INDICE_INICIAL], POSICAO_CARTAO[INDICE_FINAL]).trim();
    }

    private String getAdministrativo(String linha) {
        return linha.substring(POSICAO_ADMINISTRATIVO[INDICE_INICIAL], POSICAO_ADMINISTRATIVO[INDICE_FINAL]).trim();
    }

    private String getContrato(String linha) {
        return linha.substring(POSICAO_CONTRATO[INDICE_INICIAL], POSICAO_CONTRATO[INDICE_FINAL]).trim();
    }

    private String getServicosAdicionais(String linha) {
        return linha.substring(POSICAO_SERVICOSADICIONAIS[INDICE_INICIAL], POSICAO_SERVICOSADICIONAIS[INDICE_FINAL]).trim();
    }

    private String getValorCobrar(String linha) {
        return linha.substring(POSICAO_VALORCOBRAR[INDICE_INICIAL], POSICAO_VALORCOBRAR[INDICE_FINAL]).trim();
    }

    private String getValorDeclarado(String linha) {
        return linha.substring(POSICAO_VALORDECLARADO[INDICE_INICIAL], POSICAO_VALORDECLARADO[INDICE_FINAL]).trim();
    }

    private String getSerieNota(String linha) {
        return linha.substring(POSICAO_SERIENOTA[INDICE_INICIAL], POSICAO_SERIENOTA[INDICE_FINAL]).trim();
    }

    private String getNota(String linha) {
        return linha.substring(POSICAO_NOTA[INDICE_INICIAL], POSICAO_NOTA[INDICE_FINAL]).trim();
    }

    private String getCubico(String linha) {
        return linha.substring(POSICAO_CUBICO[INDICE_INICIAL], POSICAO_CUBICO[INDICE_FINAL]).trim();
    }

    private String getComprimento(String linha) {
        return linha.substring(POSICAO_COMPRIMENTO[INDICE_INICIAL], POSICAO_COMPRIMENTO[INDICE_FINAL]).trim();
    }

    private String getLargura(String linha) {
        return linha.substring(POSICAO_LARGURA[INDICE_INICIAL], POSICAO_LARGURA[INDICE_FINAL]).trim();
    }

    private String getAltura(String linha) {
        return linha.substring(POSICAO_ALTURA[INDICE_INICIAL], POSICAO_ALTURA[INDICE_FINAL]).trim();
    }

    private String getPeso(String linha) {
        return linha.substring(POSICAO_PESO[INDICE_INICIAL], POSICAO_PESO[INDICE_FINAL]).trim();
    }

    private String getNumeroObjeto(String linha) {
        return linha.substring(POSICAO_NUMEROOBJETO[INDICE_INICIAL], POSICAO_NUMEROOBJETO[INDICE_FINAL]).trim();
    }

    private String getCodigoFinanceiro(String linha) {
        return linha.substring(POSICAO_CODIGOFINANCEIRO[INDICE_INICIAL], POSICAO_CODIGOFINANCEIRO[INDICE_FINAL]).trim();
    }

    private String getTelefone(String linha) {
        return linha.substring(POSICAO_TELEFONE[INDICE_INICIAL], POSICAO_TELEFONE[INDICE_FINAL]).trim();
    }

    private String getCep(String linha) {
        return linha.substring(POSICAO_CEP[INDICE_INICIAL], POSICAO_CEP[INDICE_FINAL]).trim();
    }

    private String getEstado(String linha) {
        return linha.substring(POSICAO_ESTADO[INDICE_INICIAL], POSICAO_ESTADO[INDICE_FINAL]).trim();
    }

    private String getCidade(String linha) {
        return linha.substring(POSICAO_CIDADE[INDICE_INICIAL], POSICAO_CIDADE[INDICE_FINAL]).trim();
    }

    private String getBairro(String linha) {
        return linha.substring(POSICAO_BAIRRO[INDICE_INICIAL], POSICAO_BAIRRO[INDICE_FINAL]).trim();
    }

    private String getComplemento(String linha) {
        return linha.substring(POSICAO_COMPLEMENTO[INDICE_INICIAL], POSICAO_COMPLEMENTO[INDICE_FINAL]).trim();
    }

    private String getNumero(String linha) {
        return linha.substring(POSICAO_NUMERO[INDICE_INICIAL], POSICAO_NUMERO[INDICE_FINAL]).trim();
    }

    private String getEndereco(String linha) {
        return linha.substring(POSICAO_ENDERECO[INDICE_INICIAL], POSICAO_ENDERECO[INDICE_FINAL]).trim();
    }

    private String getNome(String linha) {
        return linha.substring(POSICAO_NOME[INDICE_INICIAL], POSICAO_NOME[INDICE_FINAL]).trim();
    }

}

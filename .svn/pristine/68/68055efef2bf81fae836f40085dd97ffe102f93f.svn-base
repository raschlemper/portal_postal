package br.com.portalpostal.importacao.modelo;

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
    private static final int[] POSICAO_OBSERVACAO2 = {755, 855};
    private static final int[] POSICAO_OBSERVACAO3 = {855, 955};
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
        StringBuilder observacao = new StringBuilder();
        observacao.append(linha.substring(POSICAO_OBSERVACAO[INDICE_INICIAL], POSICAO_OBSERVACAO[INDICE_FINAL]).trim());
        observacao.append(" ");
        observacao.append(linha.substring(POSICAO_OBSERVACAO2[INDICE_INICIAL], POSICAO_OBSERVACAO2[INDICE_FINAL]).trim());
        observacao.append(" ");
        observacao.append(linha.substring(POSICAO_OBSERVACAO3[INDICE_INICIAL], POSICAO_OBSERVACAO3[INDICE_FINAL]).trim());
        return observacao.toString();
    }

    private String getCelular(String linha) {
        return getAtributo(POSICAO_CELULAR,linha);
    }

    private String getCodigoVisual(String linha) {
        return getAtributo(POSICAO_CODIGOCLIENTEVISUAL,linha);
    }

    private String getQuantidadeVolume(String linha) {
        return getAtributo(POSICAO_QUANTIDADEVOLUME,linha);
    }

    private String getCodigoVolume(String linha) {
        return getAtributo(POSICAO_CODIGOVOLUME,linha);
    }

    private String getConteudoObjeto(String linha) {
        return getAtributo(POSICAO_CONTEUDOOBJETO,linha);
    }

    private String getEmail(String linha) {
        return getAtributo(POSICAO_EMAIL,linha);
    }

    private String getCartao(String linha) {
        return getAtributo(POSICAO_CARTAO,linha);
    }

    private String getAdministrativo(String linha) {
        return getAtributo(POSICAO_ADMINISTRATIVO,linha);
    }

    private String getContrato(String linha) {
        return getAtributo(POSICAO_CONTRATO,linha);
    }

    private String getServicosAdicionais(String linha) {
        return getAtributo(POSICAO_SERVICOSADICIONAIS,linha);
    }

    private String getValorCobrar(String linha) {
        return getAtributo(POSICAO_VALORCOBRAR,linha);
    }

    private String getValorDeclarado(String linha) {
        return getAtributo(POSICAO_VALORDECLARADO,linha);
    }

    private String getSerieNota(String linha) {
        return getAtributo(POSICAO_SERIENOTA,linha);
    }

    private String getNota(String linha) {
        return getAtributo(POSICAO_NOTA,linha);
    }

    private String getCubico(String linha) {
        return getAtributo(POSICAO_CUBICO,linha);
    }

    private String getComprimento(String linha) {
        return getAtributo(POSICAO_COMPRIMENTO,linha);
    }

    private String getLargura(String linha) {
        return getAtributo(POSICAO_LARGURA,linha);
    }

    private String getAltura(String linha) {
        return getAtributo(POSICAO_ALTURA,linha);
    }

    private String getPeso(String linha) {
        return getAtributo(POSICAO_PESO,linha);
    }

    private String getNumeroObjeto(String linha) {
        return getAtributo(POSICAO_NUMEROOBJETO,linha);
    }

    private String getCodigoFinanceiro(String linha) {
        String valor = getAtributo(POSICAO_CODIGOFINANCEIRO,linha);
        return valor.isEmpty()?"0":valor;
    }

    private String getTelefone(String linha) {
        return getAtributo(POSICAO_TELEFONE,linha);
    }

    private String getCep(String linha) {
        return getAtributo(POSICAO_CEP,linha);
    }

    private String getEstado(String linha) {
        return getAtributo(POSICAO_ESTADO,linha);
    }

    private String getCidade(String linha) {
        return getAtributo(POSICAO_CIDADE,linha);
    }

    private String getBairro(String linha) {
        return getAtributo(POSICAO_BAIRRO,linha);
    }

    private String getComplemento(String linha) {
        return getAtributo(POSICAO_COMPLEMENTO,linha);
    }

    private String getNumero(String linha) {
        return getAtributo(POSICAO_NUMERO,linha);
    }

    private String getEndereco(String linha) {
        return getAtributo(POSICAO_ENDERECO,linha);
    }

    private String getNome(String linha) {
        return getAtributo(POSICAO_NOME,linha);
    }

    private String getAtributo(int[] argumentos,String linha){
         try {
            return linha.substring(argumentos[INDICE_INICIAL],argumentos[INDICE_FINAL]).trim();
        } catch (Exception ex) {
            return "";
        }
    }

}

package br.com.portalpostal.prepostagem.dao;

import br.com.portalpostal.entity.PreVenda;
import br.com.portalpostal.entity.PreVendaDestinatario;
import br.com.portalpostal.entity.PreVendaDestinatarioPK;
import br.com.portalpostal.entity.PreVendaPK;
import br.com.portalpostal.importacao.modelo.DestinatarioModelo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConversorModeloImportacao {

    public static PreVenda converter(DestinatarioModelo destinatario) {
        ConversorModeloImportacao conversor = new ConversorModeloImportacao();
        return conversor.criaPreVenda(destinatario);
    }

    private PreVenda criaPreVenda(DestinatarioModelo destinatario) {
        PreVendaPK preVendaPK = new PreVendaPK();
        preVendaPK.setNumObjeto(destinatario.getNumeroObjeto());
        PreVenda preVenda = new PreVenda();
        preVenda.setPreVendaPK(preVendaPK);
        preVenda.setIdCliente(converteToInteger(destinatario.getCodigoCliente()));
        preVenda.setNomeServico(destinatario.getCodigoFinanceiro());
        preVenda.setPeso(converteToInteger(destinatario.getPeso()));
        preVenda.setAltura(converteToInteger(destinatario.getAltura()));
        preVenda.setLargura(converteToInteger(destinatario.getLargura()));
        preVenda.setComprimento(converteToInteger(destinatario.getComprimento()));
        preVenda.setObservacoes(destinatario.getObservacao());
        preVenda.setConteudo(destinatario.getConteudoDoObjeto());
        preVenda.setValorDeclarado(converteToFloat(destinatario.getValorDeclarado()));
        preVenda.setNotaFiscal(destinatario.getNota());
        preVenda.setSerieNotaFiscal(destinatario.getSerieNota());
        preVenda.setIdDepartamento(getCodigoDepartamento(destinatario.getDepartamento()));
        preVenda.setDestinatario(criaDestinatario(destinatario));
        preVenda.setDataPreVenda(Calendar.getInstance().getTime());
        preVenda.setEmailDestinatario(destinatario.getEmail());
        preVenda.setAvisoRecebimento(converteAR(destinatario.getServicosAdicionais()));
        preVenda.setMaoPropria(converteMP(destinatario.getServicosAdicionais()));
        preVenda.setValorDeclarado(converteVD(destinatario.getServicosAdicionais(), destinatario.getValorDeclarado()));
        preVenda.setPostaRestante(convertePostoRecente(destinatario.getServicosAdicionais()));
        preVenda.setRegistroModico(converteRegistroModico(destinatario.getServicosAdicionais()));
        preVenda.setRegistro(converteRegistro(destinatario.getServicosAdicionais()));
        preVenda.setSiglaPais("BR");
        preVenda.setAosCuidados("");
        preVenda.setInutilizada(0);
        preVenda.setUserPreVenda(0);
        preVenda.setNomePreVenda(destinatario.getNomeUsuario());
        preVenda.setIdRemetente(1);
        preVenda.setConsolidado(0);
        preVenda.setImpresso(0);
        preVenda.setImpressoAr(0);
        preVenda.setIdOs(0);
        preVenda.setValorCobrar(0f);
        preVenda.setIdPlp(0);
        preVenda.setIsSync(false);
        preVenda.setMetodoInsercao("LAYOUT IMPORTACAO");
        preVenda.setTipoEtiqueta("SigepWEB");

        return preVenda;
    }

    private PreVendaDestinatario criaDestinatario(DestinatarioModelo destinatario) {
        PreVendaDestinatario preVendaDestinatario = new PreVendaDestinatario();
        PreVendaDestinatarioPK preVendaDestinatarioPK = new PreVendaDestinatarioPK();
        preVendaDestinatario.setPreVendaDestinatarioPK(preVendaDestinatarioPK);
        preVendaDestinatario.setNome(destinatario.getNome());
        preVendaDestinatario.setCep(destinatario.getCep());
        preVendaDestinatario.setEndereco(destinatario.getEndereco());
        preVendaDestinatario.setNumero(destinatario.getNumero());
        preVendaDestinatario.setComplemento(destinatario.getComplemento());
        preVendaDestinatario.setBairro(destinatario.getBairro());
        preVendaDestinatario.setCidade(destinatario.getCidade());
        preVendaDestinatario.setUf(destinatario.getEstado());
        preVendaDestinatario.setEmail(destinatario.getEmail());
        preVendaDestinatario.setCelular(destinatario.getCelular());
        preVendaDestinatario.setEmpresa("");
        preVendaDestinatario.setCpfCnpj("");
        return preVendaDestinatario;

    }

    private Short convertePostoRecente(String adicionais) {
        return SeparaServicosAdicionais(adicionais).contains("PR") ? Short.valueOf("1") : Short.valueOf("0");
    }

    private int converteRegistro(String adicionais) {
        return SeparaServicosAdicionais(adicionais).contains("RG") ? 1 : 0;
    }

    private Short converteRegistroModico(String adicionais) {
        return SeparaServicosAdicionais(adicionais).contains("RM") ? Short.valueOf("1") : Short.valueOf("0");
    }

    private int converteMP(String adicionais) {
        return SeparaServicosAdicionais(adicionais).contains("MP") ? 1 : 0;
    }

    private float converteVD(String adicionais, String valorDeclarado) {
        float valor = 0;
        if (SeparaServicosAdicionais(adicionais).contains("VD")) {
            valor = converteToFloat(valorDeclarado);
        }
        return valor;
    }

    private int converteAR(String adicionais) {
        return SeparaServicosAdicionais(adicionais).contains("AR") ? 1 : 0;
    }

    private List<String> SeparaServicosAdicionais(String str) {
        List result = new ArrayList<>();
        int max = str.length();
        for (int i = 0; i < max; i += 2) {
            if (i % 2 == 0 && max >= i + 2) {
                result.add(str.substring(i, i + 2));
            }
        }
        return result;
    }

    private float converteToFloat(String valor) {
        valor = valor.replace("[^A-Za-z]", "");
        if (valor.contains(",")) {
            valor = valor.replaceAll("\\.", "").replaceAll(",", ".");
        }
        try {
            return Float.parseFloat(valor);
        } catch (NumberFormatException exception) {
            return 0f;
        }

    }

    private int converteToInteger(String quantidade) {

        try {
            return Integer.parseInt(quantidade.trim());
        } catch (NumberFormatException numberException) {
        }
        return 0;
    }

    private int getCodigoDepartamento(String departamento) {
        try {
            String[] valor = departamento.split(";");
            int idDepartamento = converteToInteger(valor[0]);
            return idDepartamento > 0 ? idDepartamento : 0;
        } catch (RuntimeException runException) {
            return 0;
        }
    }
}

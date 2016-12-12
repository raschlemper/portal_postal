/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Emporium.Entity;

import java.util.ArrayList;
import java.util.List;


public class MapeamentoLayout {

    public static final String PARAMETROPULALINHA="PARAMETROPULALINHA";
    public static final String NOMELAYOUT = "NOMELAYOUT";
    public static final String CELULAR = "CELULAR";
    public static final String EMAIL = "EMAIL";
    public static final String OBSERVACAO = "OBSERVACAO";
    public static final String OUTRASOBSERVACAO = "OUTRASOBSERVACAO";
    public static final String CONTEUDO = "CONTEUDO";
    public static final String COMPRIMENTO = "COMPRIMENTO";
    public static final String LARGURA = "LARGURA";
    public static final String ALTURA = "ALTURA";
    public static final String PESO = "PESO";
    public static final String NOTAFISCAL = "NOTAFISCAL";
    public static final String VALORDECLARADO = "VALORDECLARADO";
    public static final String SERVICOADICIONAL = "SERVICOADICIONAL";
    public static final String CODIGOECT = "CODIGOECT";
    public static final String UF = "UF";
    public static final String CIDADE = "CIDADE";
    public static final String BAIRRO = "BAIRRO";
    public static final String COMPLEMENTO = "COMPLEMENTO";
    public static final String NUMERO = "NUMERO";
    public static final String LOGRADOURO = "LOGRADOURO";
    public static final String CEP = "CEP";
    public static final String NOME = "NOME";
    public static final String IDENTIFICADOR = "IDENTIFICADOR";
    public static final String OBJETO = "OBJETO";
    public static final String FORMATO_CSV="CSV";
    public static final String FORMATO_TAB="TAB";
    public static final String SERIENOTAFISCAL="SERIENOTA";
    public static final String QUANTIDADEVOLUME="QUANTIDADEVOLUME";

    private static final int POSICAO_ATRIBUTO=0;
    private static final int POSICAO_INICIAL=1;
    private static final int POSICAO_FINAL=2;


    private List<String[]> atributos;
    private String nomeLayout;

    public MapeamentoLayout(List<String[]> atributos){
        this.atributos = atributos;
    }

    public List<LayoutImportacao> converter(String formato) {
        nomeLayout = getAtribute(NOMELAYOUT);
        List<LayoutImportacao> layout = new ArrayList<>();
        for (String[] valor : atributos) {
            if (!valor[POSICAO_ATRIBUTO].equals(MapeamentoLayout.NOMELAYOUT)) {
                layout.add(criaLayoutImportacao(nomeLayout, valor, formato));
            }
        }
        return layout;
    }

    private LayoutImportacao criaLayoutImportacao(String nomeLayout, String[] valor, String tipoLayout) {
            LayoutImportacao layoutImportacao = new LayoutImportacao();
            layoutImportacao.setNome(nomeLayout);
            layoutImportacao.setAtributo(valor[POSICAO_ATRIBUTO]);
            
            if(FORMATO_TAB.equals(tipoLayout)){
                layoutImportacao.setPosicaoInicial(valor[POSICAO_INICIAL]);
                layoutImportacao.setPosicaoFinal(valor[POSICAO_FINAL]);
                
            }else{
                layoutImportacao.setPosicao(valor[POSICAO_INICIAL]);
            }
            layoutImportacao.setTipo(tipoLayout);
        return layoutImportacao;
    }
    

    private String getAtribute(String atributo) {
        for (String[] valor : atributos) {
            if (valor[POSICAO_ATRIBUTO].equals(atributo)) {
                return valor[1];
            }
        }
        return null;
    }

    public String getNomeLayout(){
        return this.nomeLayout;
    }


}

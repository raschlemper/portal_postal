/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidade;

/**
 *
 * @author SCC4
 */
public class AmarracaoCep {

    private int idCoberturaAmarracao;
    private int idAmarracao;
    private int cepInicial;
    private int cepFinal;

    public AmarracaoCep(int idCoberturaAmarracao, int idAmarracao, int cepInicial, int cepFinal) {
        this.idCoberturaAmarracao = idCoberturaAmarracao;
        this.idAmarracao = idAmarracao;
        this.cepInicial = cepInicial;
        this.cepFinal = cepFinal;
    }

    public int getCepFinal() {
        return cepFinal;
    }

    public void setCepFinal(int cepFinal) {
        this.cepFinal = cepFinal;
    }

    public int getCepInicial() {
        return cepInicial;
    }

    public void setCepInicial(int cepInicial) {
        this.cepInicial = cepInicial;
    }

    public int getIdAmarracao() {
        return idAmarracao;
    }

    public void setIdAmarracao(int idAmarracao) {
        this.idAmarracao = idAmarracao;
    }

    public int getIdCoberturaAmarracao() {
        return idCoberturaAmarracao;
    }

    public void setIdCoberturaAmarracao(int idCoberturaAmarracao) {
        this.idCoberturaAmarracao = idCoberturaAmarracao;
    }

}

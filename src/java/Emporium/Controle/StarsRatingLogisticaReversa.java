package Emporium.Controle;

public enum StarsRatingLogisticaReversa {

    STARWHITE(1, "../../imagensNew/starwhite.png"),
    STARBLUE(2, "../../imagensNew/starblue.png"),
    STARGRAY(3, "../../imagensNew/stargrey.png"),
    STARRED(4, "../../imagensNew/starred.png"),
    STARYELLOW(5, "../../imagensNew/staryellow.png");

    private Integer codigo;
    private String path;

    StarsRatingLogisticaReversa(Integer codigo, String path) {
        this.codigo = codigo;
        this.path = path;
    }

    public Integer getCode() {
        for (StarsRatingLogisticaReversa situacao : StarsRatingLogisticaReversa.values()) {
            if (situacao.codigo == this.codigo) {
                return this.codigo;
            }
        }
        return null;
    }

    public String getPath() {
        for (StarsRatingLogisticaReversa situacao : StarsRatingLogisticaReversa.values()) {
            if (situacao.path == this.path) {
                return this.path;
            }
        }
        return null;
    }

    public StarsRatingLogisticaReversa getByCode(Integer codigo) {
        for (StarsRatingLogisticaReversa situacao : StarsRatingLogisticaReversa.values()) {
            if (situacao.codigo.equals(codigo)) {
                return situacao;
            }
        }
        return null;
    }

}

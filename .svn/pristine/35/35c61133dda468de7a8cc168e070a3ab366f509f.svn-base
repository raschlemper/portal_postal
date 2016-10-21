
package caixapostal.componentes;


public enum SituacaoObjetoInterno {

    PENDENTE(1,"PENDENTE"),
    ATUALIZAR_ENDERECO(2,"ATUALIZAR ENDEREÇO"),
    DEVOLVER(3,"DEVOLVER"),
    REENVIAR(4,"REENVIAR"),
    FINALIZADO(5,"FINALIZADO");

    private String status;
    private int codigo;
    
    SituacaoObjetoInterno(int codigo,String status){
        this.status = status;
        this.codigo=codigo;
    }

    public Integer getCode(){
        for (SituacaoObjetoInterno situacao : SituacaoObjetoInterno.values()) {
            if(situacao.codigo == this.codigo){
                return this.codigo;
            }
        }
        return null;
    }

    public String getValue() {
        for (SituacaoObjetoInterno situacao : SituacaoObjetoInterno.values()) {
            if (situacao.status == this.status) {
                return this.status;
            }
        }
        return null;
    }

    

}

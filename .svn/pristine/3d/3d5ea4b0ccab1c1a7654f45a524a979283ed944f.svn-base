package exportacao.telegrama;


public enum TelegramaStatus {
    PENDENTE(0,"Pendente"),ENVIADO(1,"Enviado"),FALHOU(-1,"Falhou");
    private int status;
    private String descricao;
    TelegramaStatus(int valor,String descricao){
        this.status = valor;
        this.descricao = descricao;
    }

    public TelegramaStatus getByCode(int code){
        for(TelegramaStatus status : TelegramaStatus.values()){
            if(status.getStatus()==code){
                return status;
            }
        }
        return TelegramaStatus.PENDENTE;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
   
}

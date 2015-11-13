package Veiculo.Validacao;

/**
 *
 * @author rafael
 */
public abstract class Validacao<E> {  
    
    private String msg = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }    
    
    protected boolean campoNotNull(Object value) {        
        if(value != null) return true;
        return false;
    }
    
    protected boolean campoBetween(Object value, comparatorInitial, comparatorFinal, msg) {
        if(value) {
            if(app.campoLessEqualThen(value, comparatorInitial) || app.campoMoreEqualThen(value, comparatorFinal)) {
                return false;                 
            }
        }
        return true;
    }
    
    protected boolean campoLessEqualThen(Object value, comparator) {
        var valueInt = parseInt(value.replace("\.", ""));
        var comparatorInt = parseInt(comparator.replace("\.", ""));
        if(valueInt >= comparatorInt) {
            alert(msg);
            return false;
        }
        return true;
    }; 
    
    protected boolean campoMoreEqualThen(Object value, Integer comparator) {
        var valueInt = parseInt(value.replace("\.", ""));
        var comparatorInt = parseInt(comparator.replace("\.", ""));
        if(valueInt <= comparatorInt) {
            alert(msg);
            return false;
        }
        return true;
    }; 
        
    public abstract boolean validar(E object);
    
}


package dashboard.view;

import java.util.List;

public class GridVendasServico {
    private Integer codigoECT;
    private List<VendasPorCliente> vendasMes;

    public Integer getCodigoECT() {
        return codigoECT;
    }

    public void setCodigoECT(Integer codigoECT) {
        this.codigoECT = codigoECT;
    }

    public List<VendasPorCliente> getVendasMes() {
        return vendasMes;
    }

    public void setVendasMes(List<VendasPorCliente> vendasMes) {
        this.vendasMes = vendasMes;
    }

    
}

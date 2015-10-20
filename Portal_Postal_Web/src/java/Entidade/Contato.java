/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidade;

/**
 *
 * @author Administrador
 */
public class Contato {

    private int idContato;
    private String contato;
    private int idEmpresa;
    private String email;
    private String foneramal;
    private String setor;

    public Contato(int idContato, String contato, int idEmpresa, String email, String foneramal, String setor) {
        this.idContato = idContato;
        this.contato = contato;
        this.idEmpresa = idEmpresa;
        this.email = email;
        this.foneramal = foneramal;
        this.setor = setor;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoneramal() {
        return foneramal;
    }

    public void setFoneramal(String foneramal) {
        this.foneramal = foneramal;
    }

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entidade;

/**
 *
 * @author Administrador
 */
public class Status {

    private int idStatus;
    private String status;

    public Status(int idStatus, String status) {
        this.idStatus = idStatus;
        this.status = status;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.models;

import java.io.Serializable;

/**
 *
 * @author Ricardo Bueno
 */
public class EstandarDTO<T> implements Serializable {
    
    private String estado;
    private String mensaje;
    private String tipo_operacion;
    private T cuerpo;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public T getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(T cuerpo) {
        this.cuerpo = cuerpo;
    }
    
    
}

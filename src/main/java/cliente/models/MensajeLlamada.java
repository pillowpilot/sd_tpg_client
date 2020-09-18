/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.models;

/**
 *
 * @author modesto
 */
public class MensajeLlamada {
     private String estado;
    private String mensaje;
    private String tipo_operacion;
    private String emisor;
    private String receptor;
    private String texto;

    public MensajeLlamada(String estado, String mensaje, String tipo_operacion, String emisor, String receptor, String texto) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.tipo_operacion = tipo_operacion;
        this.emisor = emisor;
        this.receptor = receptor;
        this.texto = texto;
    }

    public MensajeLlamada() {
    }

    public String getEstado() {
        return estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getTipo_operacion() {
        return tipo_operacion;
    }

    public String getEmisor() {
        return emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public String getTexto() {
        return texto;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setTipo_operacion(String tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    
}

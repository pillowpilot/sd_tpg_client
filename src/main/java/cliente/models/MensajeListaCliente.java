/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.models;

import java.util.ArrayList;

/**
 *
 * @author modesto
 */
public class MensajeListaCliente {

    private String estado;
    private String mensaje;
    private String tipo_operacion;
    private ArrayList<Contact> Contactos;
    private String texto;

    public void setTexto(String texto) {
        this.texto = texto;
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

    public void setContactos(ArrayList<Contact> Contactos) {
        this.Contactos = Contactos;
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

    public ArrayList<Contact> getContactos() {
        return Contactos;
    }

    public MensajeListaCliente() {
        
    }
    
    
    
}

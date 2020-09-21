/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.models;

import java.util.Date;

public class ClienteDTO {
    
    private Integer id;
    private String nombre;
    private String apellido;
    private String username;
    private String ip;
    private Integer puerto;

    public ClienteDTO(Integer id, String nombre, String apellido, String username, String ip, Integer puerto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.username = username;
        this.ip = ip;
        this.puerto = puerto;
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }

    public Integer getPuerto() {
        return puerto;
    }
    
    
    
}

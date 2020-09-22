/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;


import cliente.models.DataModel;
import cliente.models.MensajeLlamada;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author modesto
 */
public class IniciarLlamada implements Runnable{
    String ip;
    int puerto;
    Integer id_emisor;
    Integer id_receptor;
    String nombreUsuario_emisor;
    String nombreUsuario_receptor;
    DataModel datamodel;

    public DataModel getDatamodel() {
        return datamodel;
    }

    public void setDatamodel(DataModel datamodel) {
        this.datamodel = datamodel;
    }

    public String getIp() {
        return ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public Integer getEmisor() {
        return id_emisor;
    }

    public Integer getReceptor() {
        return id_receptor;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public void setEmisor(Integer emisor) {
        this.id_emisor = emisor;
    }

    public void setReceptor(Integer receptor) {
        this.id_receptor = receptor;
    }

    public void setNombreUsuario_emisor(String nombreUsuario_emisor) {
        this.nombreUsuario_emisor = nombreUsuario_emisor;
    }

    public void setNombreUsuario_receptor(String nombreUsuario_receptor) {
        this.nombreUsuario_receptor = nombreUsuario_receptor;
    }
    
    @Override
    public void run() {
        
        
        Socket s; 
        try {
            
            
            ObjectMapper jsonMapper = new ObjectMapper();
            
            MensajeLlamada mensaje = new MensajeLlamada("-1","ok","2",this.emisor,this.receptor,"") ;
            
            s = new Socket(ip, this.puerto);
            
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             
             String envio = jsonMapper.writeValueAsString(mensaje);
             
            out.println(envio);
            
            
            String recibo = in.readLine();
            mensaje=jsonMapper.readValue(recibo, MensajeLlamada.class);
            
            //0 Significa que se acpeta la llamada
            
            if(mensaje.getEstado()=="0"){
                out.close();
                in.close();
                Llamada conversacion = new Llamada(true,s,this.nombreUsuario_emisor,this.nombreUsuario_receptor,this.id_emisor,this.id_receptor,this.datamodel);
                conversacion.run();
                
            }else{
                    System.out.println("Llamada rechazada o cliente receptor ocupado");
            
            }
            out.close();
            in.close();
             
        } catch (IOException ex) {
            Logger.getLogger(IniciarLlamada.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        // obtaining input and out streams 
       
    }
    
    
}

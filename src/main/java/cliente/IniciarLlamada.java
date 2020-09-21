/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;


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
    Integer emisor;
    Integer receptor;
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
                Llamada conversacion = new Llamada(true,s,this.emisor,this.receptor);
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

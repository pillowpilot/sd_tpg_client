/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;


import cliente.models.MensajeLlamada;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author modesto
 */
public class Llamada implements Runnable{
    private boolean On_going_call;
    
    
    public static void main(String a[]) throws Exception {
       
    
    }
      @Override
    public void run() {
        boolean Call_On_going=true;
        BlockingQueue<String> pendingMessagesToSend = null;
        BlockingQueue<String> pendingMessagesToReceive = null;
        ObjectMapper jsonMapper = new ObjectMapper();
        TCPConnectionManager Conexion;
        
        Conexion = new TCPConnectionManager("127.1.0.0", Integer.SIZE, pendingMessagesToSend, pendingMessagesToReceive);
        Conexion.run();
        
        Scanner scn = new Scanner(System.in);
        String mnsjleido;
        
        MensajeLlamada mensajeR = new MensajeLlamada();
        
        MensajeLlamada mensajeE = new MensajeLlamada();
         mensajeE.setMensaje("ok");
         mensajeE.setTipo_operacion("5");
         mensajeE.setEstado("0");
        while(On_going_call){
            mnsjleido = scn.nextLine();
            
            
            mensajeE.setTexto(mnsjleido);
           
            
            try {
                String envia = jsonMapper.writeValueAsString(mensajeE);
                pendingMessagesToSend.add(envia);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(Llamada.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(pendingMessagesToReceive!=null){
                try {
                    String recibio = pendingMessagesToReceive.take();
                    mensajeR = jsonMapper.readValue(recibio, MensajeLlamada.class);
                    
                    
                    if(mensajeR.getTipo_operacion()=="4"){
                         System.out.println("Se ha cortado la llamada");    
                         this.stopCall();
                    }else{
                        System.out.println(mensajeR.getTexto());
                    
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Llamada.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(Llamada.class.getName()).log(Level.SEVERE, null, ex);
                }
                
              
                
            }
            
        }
    }
    private void stopCall(){
        this.On_going_call=false;
    }
}

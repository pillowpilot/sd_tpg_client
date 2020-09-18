/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;


import cliente.models.MensajeLlamada;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
    private Socket S;
    private String emisor;
    private String receptor;

    public Llamada(boolean On_going_call, Socket S, String emisor, String receptor) {
        this.On_going_call = On_going_call;
        this.S = S;
        this.emisor = emisor;
        this.receptor = receptor;
    }

    public Llamada() {
    }


 
    
    
    public static void main(String a[]) throws Exception {
       
    
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public void setOn_going_call(boolean On_going_call) {
        this.On_going_call = On_going_call;
    }

    public void setS(Socket S) {
        this.S = S;
    }

    public boolean isOn_going_call() {
        return On_going_call;
    }

    public Socket getS() {
        return S;
    }

    public String getEmisor() {
        return emisor;
    }

    public String getReceptor() {
        return receptor;
    }
      @Override
    public void run() {
        
        ObjectMapper jsonMapper = new ObjectMapper();     
                       
        Scanner scn = new Scanner(System.in);
            

          

        MensajeLlamada mensajeE = new MensajeLlamada();
        mensajeE.setEmisor(this.emisor);
        mensajeE.setReceptor(this.receptor);
        mensajeE.setMensaje("ok");
        mensajeE.setTipo_operacion("3");
        mensajeE.setEstado("0");

            
                
           Thread sendMessage = new Thread(new Runnable(){ 
            @Override
            public void run() { 
                String mnsjleido;
                 PrintWriter out;
                try {
                    out = new PrintWriter(S.getOutputStream(), true);
                    while (On_going_call) { 
                        
                        mnsjleido = scn.nextLine();
                        if(mnsjleido =="bye"){
                            mensajeE.setTipo_operacion("4");
                        }
                        mensajeE.setTexto(mnsjleido);
                        
                                       
                        try { 

                          String envia = jsonMapper.writeValueAsString(mensajeE);
                          out.println(envia);
                        } catch (IOException e) { 
                            e.printStackTrace(); 
                        } 
                    } 
                out.close();
                } catch (IOException ex) {
                    Logger.getLogger(Llamada.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } 
        }); 
                
             
             Thread readMessage = new Thread(new Runnable()  
        { 
            @Override
            public void run() { 
                BufferedReader in = null;
                try {
                    MensajeLlamada mensajeR = new MensajeLlamada();
                    in = new BufferedReader(new InputStreamReader(S.getInputStream()));
                    while (On_going_call) {
                        try {
                            
                            String recibio = in.readLine();
                            mensajeR = jsonMapper.readValue(recibio, MensajeLlamada.class);
                            if(mensajeR.getTipo_operacion()=="4"){
                                System.out.println("bye....Se ha cortado la llamada");
                                On_going_call=false;
                            }else{
                                System.out.println(mensajeR.getTexto());
                                
                            }
                        } catch (IOException e) {
                            
                            e.printStackTrace();
                        } 
                    }   in.close();
                } catch (IOException ex) {
                    Logger.getLogger(Llamada.class.getName()).log(Level.SEVERE, null, ex);
                } 
            } 
        }); 
               
                
                
                
                
              
            sendMessage.start();
            readMessage.start();
       
           
            
            
       
       
            
        }
     private void stopCall(){
        this.On_going_call=false;
        try {
            this.S.close();
        } catch (IOException ex) {
            Logger.getLogger(Llamada.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    }
   


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import cliente.models.ChatMessage;
import models.Contact;
import cliente.models.DataModel;
import cliente.models.MensajeLlamada;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;

/**
     * Proceso que se encarga de recibir los mensajes e imprimir en chat los mensajes necesarios.
     * 
     */
public class SocketListener implements Runnable{
    private DataModel datamodel;

    public DataModel getDatamodel() {
        return datamodel;
    }

    public void setDatamodel(DataModel datamodel) {
        this.datamodel = datamodel;
    }

    @Override
    public void run() {
        BufferedReader in = this.datamodel.getRECIBO();
        MensajeLlamada mensajeR = new MensajeLlamada();
        ObjectMapper jsonMapper = new ObjectMapper();  
        Contact servidor = new Contact("Servidor");
        
        while(true){
                    try {
                            
                            String recibio = in.readLine();
                            mensajeR = jsonMapper.readValue(recibio, MensajeLlamada.class);
                            
                            Contact origen = new Contact(mensajeR.getEmisor().toString());
                            
                            if(mensajeR.getTipo_operacion()=="2"){
                                if(mensajeR.getEstado()=="0"){
                                    System.out.println(mensajeR.getTexto());
                                    datamodel.setCallOngoing(true);
                                    datamodel.getChatMessages().add(new ChatMessage(servidor, "Se ha iniciado una llamada con:"+mensajeR.getEmisor()));
                                
                                }else{
                                    datamodel.setCallOngoing(false);
                                    datamodel.getChatMessages().add(new ChatMessage(servidor, "No se ha podido iniciar la llamada"));
                                
                                }
                               
                            }
                            if(mensajeR.getTipo_operacion()=="3"){
                               
                               datamodel.getChatMessages().add(new ChatMessage(origen, mensajeR.getTexto()));
                               
                            }
                            
                            if(mensajeR.getTipo_operacion()=="4"){
                                
                                datamodel.setCallOngoing(false);
                                datamodel.getChatMessages().add(new ChatMessage(servidor, "Se ha cortado la llamada"));
                            
                            }
                            
                        } catch (IOException e) {
                            
                            e.printStackTrace();
                        }
        
        }
        
    }
    
    public Contact searchContact(){
        Contact contacto = new Contact();
       
        
            
        return contacto;
        
    }
    
    
}

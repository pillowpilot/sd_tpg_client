/*  
    Envia una peticion de lista de clientes e imprime lo recibido
 */
package cliente;

import cliente.models.Contact;
import cliente.models.MensajeListaCliente;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;


/**
 *
 * @author modesto
 */
public class UDPListaContactos implements Runnable {
    private int puertoServidor;
    private String direccion_Servidor;

    public int getPuertoServidor() {
        return puertoServidor;
    }

    public String getDireccion_Servidor() {
        return direccion_Servidor;
    }

    public void setPuertoServidor(int puertoServidor) {
        this.puertoServidor = puertoServidor;
    }

    public void setDireccion_Servidor(String direccion_Servidor) {
        this.direccion_Servidor = direccion_Servidor;
    }

    public UDPListaContactos(int puertoServidor, String direccion_Servidor) {
        this.puertoServidor = puertoServidor;
        this.direccion_Servidor = direccion_Servidor;
    }
    
     public static void main(String a[]) throws Exception {
       
         
         
    }

    @Override
    public void run() {
       // UDPListaContactos clienteE = new UDPListaContactos(9876,"127.0.0.1");
       
         
         ObjectMapper JsonMapper = new ObjectMapper();
        try {
            
            BufferedReader inFromUser =
                    new BufferedReader(new InputStreamReader(System.in));

            DatagramSocket clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName(this.direccion_Servidor);
    

            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            
  
         
            
            MensajeListaCliente Contacts = new MensajeListaCliente();
            Contacts.setEstado("-1");
            Contacts.setMensaje("ok");
            Contacts.setTipo_operacion("1");
            
            String Data = JsonMapper.writeValueAsString(Contacts);
            sendData = Data.getBytes();
           System.out.println(Data);
            DatagramPacket sendPacket =
                    new DatagramPacket(sendData, sendData.length, IPAddress, this.puertoServidor);

            clientSocket.send(sendPacket);

            DatagramPacket receivePacket =
                    new DatagramPacket(receiveData, receiveData.length);


            clientSocket.setSoTimeout(10000);

            try {
     
                clientSocket.receive(receivePacket);

                String respuesta = new String(receivePacket.getData());
             
               
              
                Contacts = JsonMapper.readValue(respuesta, MensajeListaCliente.class);
                
               
                  //Imprime el arraylist de contactos
               System.out.println(Contacts.getContactos());
               

            } catch (SocketTimeoutException ste) {

                System.out.println("TimeOut: El paquete udp se asume perdido.");
            }
            clientSocket.close();
        } catch (UnknownHostException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

     
}

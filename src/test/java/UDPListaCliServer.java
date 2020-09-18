

import cliente.models.Contact;
import cliente.models.MensajeListaCliente;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author modesto
 */
public class UDPListaCliServer {
    public static void main(String[] a){
        
        // Variables
        int puertoServidor = 9876;
        ArrayList<Contact> contactos = new ArrayList<Contact>();
        
        try {
            //1) Creamos el socket Servidor de Datagramas (UDP)
            DatagramSocket serverSocket = new DatagramSocket(puertoServidor);
			System.out.println("Servidor Sistemas Distribuidos - UDP ");
			
            //2) buffer de datos a enviar y recibir
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

			
            //3) Servidor siempre esperando
            while (true) {

                receiveData = new byte[1024];

                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);


                System.out.println("Esperando a algun cliente... ");

                // 4) Receive LLAMADA BLOQUEANTE
                serverSocket.receive(receivePacket);
				
				System.out.println("________________________________________________");
                System.out.println("Aceptamos un paquete");

                // Datos recibidos e Identificamos quien nos envio
               String datoRecibido = new String(receivePacket.getData());

                ObjectMapper jsonMapper = new ObjectMapper();
               
              
                MensajeListaCliente mensaje = jsonMapper.readValue(datoRecibido, MensajeListaCliente.class);
               
              
                InetAddress IPAddress = receivePacket.getAddress();

                int port = receivePacket.getPort();
               //OJO a multiples llamadas al servidor, se repetiran los nombres
               Contact c1 = new Contact("Juan");
               Contact c2 = new Contact("Mirta");
               Contact c3 = new Contact("Carlos");
               contactos.add(c1);
               contactos.add(c2);
               contactos.add(c3);

                
                mensaje.setContactos(contactos);
                mensaje.setEstado("0");
                mensaje.setMensaje("ok");
                mensaje.setTipo_operacion("1");

                // Enviamos la respuesta inmediatamente a ese mismo cliente
                // Es no bloqueante
                sendData = jsonMapper.writeValueAsBytes(mensaje);
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress,port);

                serverSocket.send(sendPacket);

            }

        } catch (Exception ex) {
        	ex.printStackTrace();
            System.exit(1);
        }

    }
}

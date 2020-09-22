
import cliente.UDPListaContactos;
import cliente.models.ClienteDTO;
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
public class testListaClientes {
    
    public static void main(String[] args){
        UDPListaContactos func = new UDPListaContactos(9876,"127.0.0.1");
        ArrayList<ClienteDTO> lista = func.call();
        System.out.print(lista);
    }
}

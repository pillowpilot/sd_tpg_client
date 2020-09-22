/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import cliente.models.DataModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author modesto
 */
public class ConectarCliente {
    DataModel datamodel;
    int puerto;
    String ip;

    public ConectarCliente(DataModel datamodel, int puerto, String ip) {
        this.datamodel = datamodel;
        this.puerto = puerto;
        this.ip = ip;
    }

    public DataModel getDatamodel() {
        return datamodel;
    }

    public void setDatamodel(DataModel datamodel) {
        this.datamodel = datamodel;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    public void IniciarConexion(){
        try {
            Socket s =  new Socket(this.ip, this.puerto);
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            
            this.datamodel.setENVIO(out);
            this.datamodel.setRECIBO(in);
            this.datamodel.setSocket(s);
            
            
        } catch (IOException ex) {
            Logger.getLogger(ConectarCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
}


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public abstract class UserImpl implements User {

    private int port;
    private final String host = "localhost";
    private String ownerName;

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getHost() {
        return host;
    }

    private transient ServerSocket socket;
    private transient Socket serverConnection;

    public ServerSocket getSocket() {
        return socket;
    }
    
    @Override
    public String getOwnerName(){
        return  ownerName;
    }
    
    
    public void setOwnerName(String name){
        ownerName = name;
    }

    protected void handleConnection() {
        new Thread(
                () -> {

                    try {
                        ObjectInputStream scan = new 
                            ObjectInputStream(serverConnection.getInputStream());
                        Object o = null;
                        while ((o = scan.readObject()) != null) {
                            System.out.println((Message)o);
                            receiveMessage((Message)o);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }
        ).start();
    }

    protected void connect(int port) {
        try {
            this.port = port;
            socket = new ServerSocket(port);
            serverConnection = socket.accept();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void receiveMessage(Message message) {
        handleMessages(message);
    }
    
    protected abstract void handleMessages(Message message);
}

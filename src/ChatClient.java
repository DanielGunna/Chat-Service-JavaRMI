

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lipec
 */
public class ChatClient extends UserImpl {

    private Client client;
    private transient OnReceiveMessageListener listener;

    private boolean isConnected;

    public void setListener(OnReceiveMessageListener listener) {
        this.listener = listener;
    }

  
    public void connect() {
        try {
            client = (Client) Naming.lookup(Constants.CHAT_SERVER_URL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean sendMessage(String user, String message, MessageType messageType) {
        if (!isConnected) {
            connectServer();
        }
        try {
            client.sendMessage(getMessage(user, message, messageType), this);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private Message getMessage(String user, String message, MessageType messageType) {
        Message msg = new Message();
        msg.setContent(message);
        msg.setOwner(user);
        msg.setType(messageType);
        msg.setDate(new Date());
        return msg;
    }

    private void connectServer() {
        new Thread(() -> {
            connect(ThreadLocalRandom.current().nextInt(0, (int) Math.pow(2, 16)) + 1);
            isConnected = true;
            handleConnection();
        }
        ).start();

    }

    @Override
    protected void handleMessages(Message message) {
        if(message.getType() == MessageType.USERSLIST.getValue()){
            listener.onReceiveUser(message.getContent());
        }else if(message.getType() == MessageType.MESSAGE.getValue()) {
            listener.onReceiveMessage(message);
        }else if(message.getType() == MessageType.MAXCLIENTS.getValue()){
            listener.onMaxClients(message);
        }else if(message.getType() == MessageType.CLEARUSERLIST.getValue()){
            listener.onClearUsers();
        }
    }



}

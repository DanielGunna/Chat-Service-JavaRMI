

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.rmi.Naming;

/**
 *
 * @author felipesilva
 */
public class ChatService {
    
    private int maxClients;
    private ServiceListener listener;
    
    public interface ServiceListener{
        void onReceiveMessage(Message msg);
    };
    public ChatService(int maxClients, ServiceListener listener){
        this.maxClients = maxClients;
        this.listener = listener;
        
        
    }
    private Client client;

    public void startClient() {
        try { 
           client = new ClientImpl(maxClients,listener);
           Naming.rebind(Constants.CHAT_SERVER_URL,client);
            System.out.println("Server Ok");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

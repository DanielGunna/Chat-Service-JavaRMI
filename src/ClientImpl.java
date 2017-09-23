
import java.awt.List;
import java.io.IOException;
import java.net.Socket;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author felipesilva
 */
public class ClientImpl extends UnicastRemoteObject implements Client {

    private HashMap<String, UserInfo> users;

    private void handleNewUser(User user, Message message) {
        try {
            Socket userSocket = new Socket(user.getHost(), user.getPort());
            UserInfo info = new UserInfo(userSocket, new ArrayList<Message>(), user);
            // info.sendMessage(message);
            notifyNewUser(user);
            users.put(message.getOwner(), info);
            // new Timer().schedule( new  TimerTask() {
              //  @Override
             //   public void run() {
                   sendUsers(info);
             //   }
          //  },1000);
      
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ClientImpl() throws java.rmi.RemoteException {
        super();
        users = new HashMap<>();
    }

    @Override
    public void sendMessage(Message message, User user) throws java.rmi.RemoteException {
        if (message.getType() == MessageType.NICKNAME.getValue()) {
            new Thread(() -> {
                handleNewUser(user, message);
            }).start();

        } else {
            sendBroadast(user, message);
        }
    }

    private void notifyNewUser(User user) {
        for (Map.Entry<String, UserInfo> i : users.entrySet()) {
            Message msg = new Message();
            msg.setOwner("SERVER");
            msg.setType(MessageType.USERSLIST);
            msg.setContent(user.getOwnerName());
            msg.setDate(new Date());
            i.getValue().sendMessage(msg);
        }
    }

    private void sendBroadast(User user, Message message) {
        for (Map.Entry<String, UserInfo> i : users.entrySet()) {
            Message msg = new Message();
            i.getValue().sendMessage(message);
        }
    }

    private void sendUsers(UserInfo info) {
        for (Map.Entry<String, UserInfo> i : users.entrySet()) {
            Message msg = new Message();
            msg.setOwner("SERVER");
            msg.setType(MessageType.USERSLIST);
            msg.setContent(i.getValue().getUser().getOwnerName());
            msg.setDate(new Date());
            info.sendMessage(msg);
        }
    }

}

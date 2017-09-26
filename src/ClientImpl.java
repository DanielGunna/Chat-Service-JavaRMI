
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
    private int maxClients;
    private ChatService.ServiceListener listener;

    ClientImpl(int maxClients, ChatService.ServiceListener listener) throws java.rmi.RemoteException {
        this();
        this.maxClients = maxClients;
        this.listener = listener;
    }

    private void handleNewUser(User user, Message message) {
        try {
            Socket userSocket = new Socket(user.getHost(), user.getPort());
            UserInfo info = new UserInfo(userSocket, new ArrayList<Message>(), user);
            if (verifyMaxClients(info)) {
                // info.sendMessage(message);
                notifyNewUser(user);
                users.put(message.getOwner(), info);
                // new Timer().schedule( new  TimerTask() {
                //  @Override
                //   public void run() {
                sendUsers(info);
            }
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
        listener.onReceiveMessage(message);
        if (message.getType() == MessageType.NICKNAME.getValue()) {
            new Thread(() -> {
                handleNewUser(user, message);
            }).start();
        } else if (message.getType() == MessageType.MESSAGE.getValue()) {
            sendBroadast(user, message);
        } else if (message.getType() == MessageType.LOGOUT.getValue()) {
            disconnectUser(user, message);
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

    private boolean verifyMaxClients(UserInfo user) {
        if (users.size() == maxClients) {
            Message msg = new Message();
            msg.setOwner("SERVER");
            msg.setType(MessageType.MAXCLIENTS);
            msg.setContent("Numero maximo de clientes atingido!!");
            user.sendMessage(msg);
            return false;
        }
        return true;
    }

    private void disconnectUser(User user, Message message) {
        UserInfo info = users.get(user.getOwnerName());
        try {
            info.getSocket().close();
            users.remove(user.getOwnerName());
            sendUsersForAll();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendUsersForAll() {
        for (Map.Entry<String, UserInfo> i : users.entrySet()) {
            i.getValue().sendMessage(getClearUserList());
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    sendUsers(i.getValue());
                }
            }, 1000);
        }
    }

    private Message getClearUserList() {
        Message message = new Message();
        message.setContent("CLEAR USERS lIST");
        message.setOwner("SERVER");
        message.setType(MessageType.CLEARUSERLIST);
        return message;
    }

}

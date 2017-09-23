
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author lipec
 */
public class UserInfo {

    private Socket socket;
    private ArrayList<Message> messages;
    private User user;
    private ObjectOutputStream printStream;

    public UserInfo(Socket socket, ArrayList<Message> messages, User user) {
        this.socket = socket;
        this.messages = messages;
        this.user = user;
        initPrintStream();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void sendMessage(Message message) {
        try {
            printStream.writeObject(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initPrintStream() {
        try {
            printStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

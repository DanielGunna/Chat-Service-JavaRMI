
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lipec
 */
public interface  OnReceiveMessageListener  extends Serializable{
    void onReceiveUser(String name);
    void onReceiveMessage(Message msg);
}

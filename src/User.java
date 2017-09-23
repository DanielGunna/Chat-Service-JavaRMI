
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
interface User extends Serializable {

    void receiveMessage(Message message);

    int getPort();

    String getHost();
    
    String getOwnerName();

}

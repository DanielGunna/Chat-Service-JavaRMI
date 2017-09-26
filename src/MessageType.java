/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lipec
 */
public enum MessageType {
    NICKNAME(1),
    MESSAGE(2),
    USERSLIST(3),
    MAXCLIENTS(4),
    DISCONNECT(5), 
    LOGOUT(6), 
    CLEARUSERLIST(8),
    RENEWUSERSLIST(7);
    
   
    private final int value;
    MessageType(int value){
        this.value = value;
    }
    
    public int getValue(){return value;}
    
    
    
    
    
}

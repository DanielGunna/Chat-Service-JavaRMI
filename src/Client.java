

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


 

/**
 *
 * @author felipesilva
 */
public  interface Client extends java.rmi.Remote  {
    void sendMessage(Message message,User user)throws java.rmi.RemoteException;
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.rmi.RemoteException;

/**
 *
 * @author felipesilva
 */
public class ClientImpl implements Client{

    @Override
    public String sendMessage(String message) throws RemoteException {
        return message;
    }

    @Override
    public String receiveMessage(String message) throws RemoteException {
        return message;
    }
    
}

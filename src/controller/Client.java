/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author felipesilva
 */
public  interface Client extends Remote {
    String sendMessage(String message)throws RemoteException;
    String receiveMessage(String message) throws RemoteException;
}

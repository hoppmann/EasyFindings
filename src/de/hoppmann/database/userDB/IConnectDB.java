/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.database.userDB;

/**
 *
 * @author hoppmann
 */
public interface IConnectDB {
    
    
    public boolean connectDB(String dbPath, String user, String password);
    
    
    
    
    
    
}

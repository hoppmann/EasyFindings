/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB.snipletDB;

import de.hoppmann.database.userDB.ConnectUserDB;
import de.hoppmann.database.userDB.ConnectionHolder;
import de.hoppmann.database.userDB.DbOperations;
import de.hoppmann.database.userDB.UserDbNamings;
import de.hoppmann.database.userDB.interfaces.IGeneInfoRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author hoppmann
 */
public class DbGeneInfoRepository implements IGeneInfoRepository {
    



    // gene table
    private static final String GENE_TABLE = UserDbNamings.GENE_TABLE;
    private static final String GENE_NAME_COL = UserDbNamings.GENE_NAME_COL;
    private static final String GENE_INFO_COL = UserDbNamings.GENE_INFO_COL;

    
    
    
    
    
    
    
    
    @Override
    public List<String> getGeneList() {
	
	
	List<String> geneList = new LinkedList<>();
	
	String query = "SELECT " + GENE_NAME_COL + " FROM " + GENE_TABLE;

	
	ConnectUserDB.connectSqLiteUserDB();
	try {
            ResultSet rs = DbOperations.execute(query, ConnectionHolder.getInstance().getConnection());
            if (rs != null){
                while (rs.next()){
                    geneList.add(rs.getString(GENE_NAME_COL));
                }
            }
	} catch (SQLException ex) {
	    Logger.getLogger(DbGeneInfoRepository.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    ConnectUserDB.closeDB();
	}
	
	
	Collections.sort(geneList);
	return geneList;
	
	
    }

    
    
    
    
    
    
    
    
    @Override
    public VariantInfo getGeneInfo(VariantInfo varInfo) {
	
	
	String queryGeneInfoCmd = "select "+ GENE_INFO_COL + " from " + GENE_TABLE
		+ " where " + GENE_NAME_COL + " == '" + varInfo.getGeneName() + "'";

	
	
	try {
	    ConnectUserDB.connectSqLiteUserDB();
            ResultSet rs = DbOperations.execute(queryGeneInfoCmd, ConnectionHolder.getInstance().getConnection());

	    if (rs.next()){
		varInfo.setGeneInfo(rs.getString(GENE_INFO_COL));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(DbGeneInfoRepository.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    ConnectUserDB.closeDB();
	}
	
	
	return varInfo;
	
    }


    
    
    
    
    
    
     @Override
    public void saveGene(VariantInfo varInfo) {
	
	
	
	// prepare command 
	String addEntryCmd = "REPLACE INTO " + GENE_TABLE + " VALUES " 
		+ "( '" + varInfo.getGeneName() + "' , '" + varInfo.getGeneInfo() + "')";
	
	ConnectUserDB.connectSqLiteUserDB();
        DbOperations.execute(addEntryCmd, ConnectionHolder.getInstance().getConnection());
	ConnectUserDB.closeDB();
    }

    
    
    
    
    
    
    
    
    
    @Override
    public void removeGene(VariantInfo varInfo) {
	
	String removeCmd;
        removeCmd = "DELETE FROM " + GENE_TABLE
	    + " where gene = '" + varInfo.getGeneName() + "'";

	
	
	ConnectUserDB.connectSqLiteUserDB();
	DbOperations.execute(removeCmd, ConnectionHolder.getInstance().getConnection());
	ConnectUserDB.closeDB();
    }
    
    
    
    
    
    
    
    
    
    @Override
    public boolean isRepoValid() {
	
	return hasGeneTable();
	
    }

    
    
    
    
    
    
    
    @Override
    public boolean makeRepoValid() {

	boolean success = false;
	
	
	
	
	String createTableCmd = "create table " + GENE_TABLE
	    + " (" + GENE_NAME_COL + " VARCHAR(60) not NULL, "
	    + GENE_INFO_COL + " TEXT, "
	    + "PRIMARY KEY ( " + GENE_NAME_COL + " ))";
        
	
	ConnectUserDB.connectSqLiteUserDB();
	ResultSet rs = DbOperations.execute(createTableCmd, ConnectionHolder.getInstance().getConnection());
	ConnectUserDB.closeDB();
	
	    
	return hasGeneTable();
	
    }

    
    
    
    
    
    
    
    
    
    private boolean hasGeneTable() {
	
	boolean hasTable = false;
	
	boolean success = ConnectUserDB.connectSqLiteUserDB();
	
	if (success){
	    if (DbOperations.hasTable(GENE_TABLE, ConnectionHolder.getInstance().getConnection())){
		hasTable = true;
	    }
	}
	
	ConnectUserDB.closeDB();
	
	return hasTable;

	
    }

    
    
    
    
    

}

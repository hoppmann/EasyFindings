/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB.snipletDB;

import de.hoppmann.database.userDB.ConnectionHolder;
import de.hoppmann.database.userDB.DbOperations;
import de.hoppmann.database.userDB.UserDbNamings;
import de.hoppmann.database.userDB.interfaces.IVariantInfoRepository;
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
public class DbVarianInfoRepository implements IVariantInfoRepository{

    private final String GENE_COL = UserDbNamings.GENE_NAME_COL;
    private final String VAR_TABLE = UserDbNamings.VAR_TABLE;
    private final String VAR_COL = UserDbNamings.VAR_COL;
    private final String VAR_INFO_COL = UserDbNamings.VAR_INFO_COL;

    
    
    
    
    @Override
    public boolean isRepoValid() {
	    
	return hasVarTable();
	
    }

    
    
    
    @Override
    public boolean makeRepoValid() {
        String createTableCmd = "CREATE TABLE " + VAR_TABLE
		+ " (" + VAR_COL + " VARCHAR(60) not NULL, "
		+ GENE_COL + " VARCHAR(60), "
		+ VAR_INFO_COL + " TEXT, "
		+ "PRIMARY KEY (" + VAR_COL + "))";

	DbOperations.execute(createTableCmd, ConnectionHolder.getInstance().getConnection());
	
	return hasVarTable();
    }

    
    


    private boolean hasVarTable() {
	return DbOperations.hasTable(VAR_TABLE, ConnectionHolder.getInstance().getConnection());
    }
	    

    
    
    
    
    
    
    @Override
    public List<String> getVariantList(String geneName) {
	
	List<String> varList = new LinkedList<>();
	
	
	
	// prepare query to get all genes
	String query = "SELECT " + VAR_COL + " FROM " + VAR_TABLE + " WHERE " 
		+ GENE_COL + " = '" + geneName + "'";
	
	
	ResultSet rs = DbOperations.execute(query, ConnectionHolder.getInstance().getConnection());


	
	try {
	    while (rs.next()) {
		varList.add(rs.getString(VAR_COL));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(DbVarianInfoRepository.class.getName()).log(Level.SEVERE, null, ex);
	}
	

	
	// sort and return variant list
	Collections.sort(varList);
	return varList;
	
	
    }

    
    
    
    
    
    
    
    
    @Override
    public String getVariantInfo(String geneName, String varName) {
	

	
	// prepare query string
	String query = "SELECT " + VAR_INFO_COL + " FROM "
		+ VAR_TABLE + " WHERE " + GENE_COL + " = '" + geneName + "' AND "
		+ VAR_COL + " = '" + varName + "'";
	
	
	ResultSet rs = DbOperations.execute(query, ConnectionHolder.getInstance().getConnection());
	
	
	String varInfo = null;
	try {
	    if (rs.next()) {
		varInfo = rs.getString(VAR_INFO_COL);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(DbVarianInfoRepository.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	return varInfo;
    
    }

    
    
    
    
    
    
    
    
    @Override
    public void saveVariant(String geneName, String varName, String varInfo) {
	
	
	// replace ' by ` to avoid SQL errors
	varInfo = varInfo.replaceAll("'", "`");
	
	
	// prepare command
	String addEntryCmd = "REPLACE INTO " + VAR_TABLE + " VALUES " 
		+ "( " + "'" + varName + "'" 
		+ ", '" + geneName + "'" 
		+ ", '" + varInfo + "'" +  " )";
	DbOperations.execute(addEntryCmd, ConnectionHolder.getInstance().getConnection());
    }

    
    
    
    
    
    
    
    
    @Override
    public void removeVariant(String geneName, String varName) {

	    // prepare deletion query
	String delQuery = "DELETE from " + VAR_TABLE + " WHERE "
		+ GENE_COL + " = '" + geneName + "' AND " + VAR_COL
		+ "= '" + varName + "'";

	
	DbOperations.execute(delQuery, ConnectionHolder.getInstance().getConnection());

    }









}

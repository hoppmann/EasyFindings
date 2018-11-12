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
    public List<String> getVariantList(VariantInfo varInfo) {
	
	List<String> varList = new LinkedList<>();
	
	
	// prepare query to get all genes
	String query = "SELECT " + VAR_COL + " FROM " + VAR_TABLE + " WHERE " 
		+ GENE_COL + " = '" + varInfo.getGeneName() + "'";
	
	
	ResultSet rs = DbOperations.execute(query, ConnectionHolder.getInstance().getConnection());


	
	try {
            if (rs != null){
                while (rs.next()) {
                    varList.add(rs.getString(VAR_COL));
                }
            }
	} catch (SQLException ex) {
	    Logger.getLogger(DbVarianInfoRepository.class.getName()).log(Level.SEVERE, null, ex);
	}
	

	
	// sort and return variant list
	Collections.sort(varList);
	return varList;
	
	
    }

    
    
    
    
    
    
    
    
    @Override
    public VariantInfo getVariantInfo(VariantInfo varInfo) {
	

	
	// prepare query string
	String query = "SELECT " + VAR_INFO_COL + " FROM "
		+ VAR_TABLE + " WHERE " + GENE_COL + " = '" + varInfo.getGeneName() + "' AND "
		+ VAR_COL + " = '" + varInfo.getVarName() + "'";
	
	
	ResultSet rs = DbOperations.execute(query, ConnectionHolder.getInstance().getConnection());
	
	
	try {
	    if (rs.next()) {
		varInfo.setVarInfo(rs.getString(VAR_INFO_COL));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(DbVarianInfoRepository.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	return varInfo;
    
    }

    
    
    
    
    
    
    
    
    @Override
    public void saveVariant(VariantInfo varInfo) {
	
	
	// prepare command
	String addEntryCmd = "REPLACE INTO " + VAR_TABLE + " VALUES " 
		+ "( " + "'" + varInfo.getVarName() + "'" 
		+ ", '" + varInfo.getGeneName() + "'" 
		+ ", '" + varInfo.getVarInfo() + "'" +  " )";
	DbOperations.execute(addEntryCmd, ConnectionHolder.getInstance().getConnection());
    }

    
    
    
    
    
    
    
    
    @Override
    public void removeVariant(VariantInfo varInfo) {

	    // prepare deletion query
	String delQuery = "DELETE from " + VAR_TABLE + " WHERE "
		+ GENE_COL + " = '" + varInfo.getGeneName() + "' AND " + VAR_COL
		+ "= '" + varInfo.getVarName() + "'";

	
	DbOperations.execute(delQuery, ConnectionHolder.getInstance().getConnection());

    }









}

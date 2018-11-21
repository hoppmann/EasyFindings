/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB.PanelDB;

import de.hoppmann.database.userDB.ConnectionHolder;
import de.hoppmann.database.userDB.DbOperations;
import de.hoppmann.database.userDB.UserDbNamings;
import de.hoppmann.database.userDB.interfaces.IPanelRepository;
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
public class DbPanelRepository implements IPanelRepository{

    private static final String PANEL_TABLE = UserDbNamings.PANEL_TABLE;
    private static final String PANEL_NAME_COL = UserDbNamings.PANEL_NAME_KEY;
    private static final String PANEL_GENES_COL = UserDbNamings.PANEL_GENES_KEY;
    private static final String ID_COL = UserDbNamings.PANEL_ID_KEY;
    
    
    
    
    @Override
    public boolean isRepoValid() {
        return hasPanelTable();
    }
    
    
    
    
    
    
    
    
    
    

    @Override
    public boolean makeRepoValid() {
        
        
        String createTableCmd = "CREATE TABLE " + PANEL_TABLE + "( "
       		+ ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ PANEL_NAME_COL + " Varchar(60) NOT NULL UNIQUE, "
		+ PANEL_GENES_COL + " Varchar(60)";
        
        ResultSet rs = DbOperations.execute(createTableCmd, ConnectionHolder.getInstance().getConnection());
	
        
	return hasPanelTable();
    }
    
    
    
    
    
    
    
    

    @Override
    public List<String> getPanelNames() {
        
        
        List<String> panelNameList = new LinkedList<>();
        
        String query = "SELECT " + PANEL_NAME_COL + " FROM " + PANEL_TABLE;
        
        ResultSet rs = DbOperations.execute(query, ConnectionHolder.getInstance().getConnection());
        
        
       
        try {
            if (rs != null) {
                while (rs.next()) {

                    panelNameList.add(rs.getString(PANEL_NAME_COL));

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbPanelRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
        Collections.sort(panelNameList);
        return panelNameList;
        
    }
    
    
    
    
    
    
    
    
    

    @Override
    public void getPanelGenes(PanelInfo panelInfo) {
        
        
        String queryGeneInfoCmd = "select * from " + PANEL_TABLE
		+ " where " + PANEL_NAME_COL + " == '" + panelInfo.getPanelName() + "'";

        System.out.println(queryGeneInfoCmd);
	
	ResultSet rs = DbOperations.execute(queryGeneInfoCmd, ConnectionHolder.getInstance().getConnection());
	
	try {
	    if (rs.next()){
                panelInfo.setGeneList(rs.getString(PANEL_GENES_COL));
                System.out.println(panelInfo.getGeneList());
                panelInfo.setId(rs.getInt(ID_COL));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(DbPanelRepository.class.getName()).log(Level.SEVERE, null, ex);
	}
	
    }
    
    
    
    
    
    
    
    
    
    

    @Override
    public void newPanel(PanelInfo panelInfo) {

        
        // prepare query
	String insertCmd = "INSERT INTO " + PANEL_TABLE 
		+ " (" + PANEL_NAME_COL + ", " + PANEL_GENES_COL + ") " 
		+ " VALUES ( '" + panelInfo.getPanelName() + "', '" + panelInfo.getGeneListAsString() + "')";
        
	DbOperations.execute(insertCmd, ConnectionHolder.getInstance().getConnection());
        
    }
    
    
    
    
    
    
    
    
    

    @Override
    public void savePanel(PanelInfo panelInfo) {
        
        // perpare update command
	String updateCmd = "REPLACE INTO " + PANEL_TABLE + " VALUES ( '" + ", " + panelInfo.getId() + ", " + panelInfo.getPanelName() +
		"', '" + panelInfo.getGeneListAsString() + "')";

	DbOperations.execute(updateCmd, ConnectionHolder.getInstance().getConnection());
    
    }
    
    
    
    
    
    
    
    
    

    @Override
    public void removePanel(PanelInfo panelInfo) {
        
        // prepare cmd
	String removeCmd = "DELETE FROM " + PANEL_TABLE + " WHERE " + PANEL_NAME_COL + 
		" == '" + panelInfo.getPanelName() + "'";

	ResultSet rs = DbOperations.execute(removeCmd, ConnectionHolder.getInstance().getConnection());
	
        
    }

    
    
    
    
    
    
    
    
    private boolean hasPanelTable() {
	
	boolean hasTable = false;
	
	if (DbOperations.hasTable(PANEL_TABLE, ConnectionHolder.getInstance().getConnection())){
	    hasTable = true;
	}
	
	return hasTable;
    }

    
    
    
    
    
    
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

import de.hoppmann.operations.Database;
import java.io.File;

/**
 *
 * @author hoppmann
 */
public class GeneInfoDB extends Database {

  


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    private final String dbName = "geneInfos.db";
    private final String tableHg19 = "hg19";
    private File geneInfoDB;

	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    public GeneInfoDB() {
    
	// get current path of program
	String curDir = System.getProperty("user.dir");
	geneInfoDB = new File(curDir + File.separator + "DBs" + File.separator + dbName);

	// connect to geneInfo.db
	conn = connect(geneInfoDB);
	
	
    }
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

	
	
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////










}

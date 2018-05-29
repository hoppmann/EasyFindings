/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.misc;

import de.hoppmann.gui.Messneges.CommonErrors;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoppmann
 */
public class handleConfig {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    // general
    private String configFile = "config.txt";
    private Properties prop;
    private FileOutputStream out;
    
    
    private String geneCol;
    private String impactCol;
    private String pNom;
    private String cNom;
    
    
    private String inputFilePath;
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    public handleConfig() {


    }
    
    
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    
    
    
    
    
    
    //// load config data
    public void loadConfig() {
	try {
	    Properties prop = new Properties();
	    prop.load(getClass().getClassLoader().getResourceAsStream(configFile));
	    
	    
	    // read in config data
	    geneCol = prop.getProperty("geneCol");
	    impactCol = prop.getProperty("impactCol");
	    
	    
	    // path info
	    inputFilePath = prop.getProperty("inputPath");
	    
	} catch (IOException ex) {
	    Logger.getLogger(handleConfig.class.getName()).log(Level.SEVERE, null, ex);
	}

	
	
	
    }
	
	
    
    
    //// close stream
    public void close() {
	try {
	    out.close();
	} catch (IOException ex) {
	    Logger.getLogger(handleConfig.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    
    
    
//    //// store in config data
//    private void saveConfig() throws FileNotFoundException {
//	
//	Properties prop = new Properties();
//	FileOutputStream out = new FileOutputStream(configFile);
//	
//	
//	
//	
//	
//	
//	
//	
//	
//    }
    
    
    
    
    
    
    
    
    
    
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////

    public String getGeneCol() {
	return geneCol;
    }

    public void setGeneCol(String geneCol) {
	this.geneCol = geneCol;
	
    }

    public String getImpactCol() {
	return impactCol;
    }

    public void setImpactCol(String impactCol) {
	this.impactCol = impactCol;
    }

    public String getInputFilePath() {
	return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
	this.inputFilePath = inputFilePath;
    }

    public String getpNom() {
	return pNom;
    }

    public void setpNom(String pNom) {
	try {
	    this.pNom = pNom;
	    prop.store(out, pNom);
	} catch (IOException ex) {
	    Logger.getLogger(handleConfig.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public String getcNom() {
	return cNom;
    }

    public void setcNom(String cNom) {
	this.cNom = cNom;
    }

    
    







}

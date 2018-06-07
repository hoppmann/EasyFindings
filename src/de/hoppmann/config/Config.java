/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.config;

import java.io.FileInputStream;
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

public class Config {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    // general
    private final String configFile = "config.properties";
    private Properties prop;
    
    
    // columns
    private final String geneColKey = "geneCol";
    private String geneCol;
    private final String impactColKey = "impactCol";
    private String impactCol;
    private final String pNomenColKey = "proteinAnnotationCol";
    private String pNomenCol;
    private final String cNomenColKey = "cDnaAnnotationCol";
    private String cNomenCol;
    
    // misc
    private final String inputPathKey = "inputPath";
    private String inputPath;
    
    
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    public Config() {
	
	loadConfig();
	
    }
    
    
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
        
    //// load config data
    public void loadConfig() {
	
	prop = loadProp();
	
	// read in config data
	geneCol = prop.getProperty(geneColKey);
	impactCol = prop.getProperty(impactColKey);
	pNomenCol = prop.getProperty(pNomenColKey);
	cNomenCol = prop.getProperty(cNomenColKey);
	inputPath = prop.getProperty(inputPathKey);
	
	
	    
	    
	
	
    }
    
    
    
    
    
    
    // load property file
    private Properties loadProp() {
	
		Properties prop = null;
	
	try {
	    prop = new Properties();
	    FileInputStream streamIn = new FileInputStream(configFile);
	    prop.load(streamIn);
	    streamIn.close();
	    
	} catch (IOException ex) {
	    Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
	}

	return prop;
	
    }
    
    
    
    
    
    
    // store property file
    private void storeProp(Properties prop) {
	
	    FileOutputStream outStream = null;
	try {
	    

	    // save new Config
	    outStream = new FileOutputStream(configFile);
	    prop.store(outStream, null);
	    
	    
	    
	    
	    
	    
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    try {
		outStream.close();
	    } catch (IOException ex) {
		Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
    }
    
    
    

	
    
    
    
    
    
    
    //////// save in config file
    
    public void saveConfig(String key, String value){
	
//	// load current propety fiel
//	Properties prop = loadProp();
	
	// add new property
        prop.setProperty(key, value);
	
	// save new config file
	storeProp(prop);
	
    }
    
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////

    public String getGeneCol() {
	return geneCol;
    }

    public void setGeneCol(String geneCol) {
	this.geneCol = geneCol;
	saveConfig(geneColKey, geneCol);
    }

    public String getImpactCol() {
	return impactCol;
    }

    public void setImpactCol(String impactCol) {
	this.impactCol = impactCol;
	saveConfig(impactColKey, impactCol);
    }

    public String getpNomenCol() {
	return pNomenCol;
    }

    public void setpNomenCol(String pNomCol) {
	this.pNomenCol = pNomCol;
	saveConfig(pNomenColKey, pNomCol);
    }

    public String getcNomenCol() {
	return cNomenCol;
    }

    public void setcNomenCol(String cDnaAnnoCol) {
	this.cNomenCol = cDnaAnnoCol;
	saveConfig(cNomenColKey, cDnaAnnoCol);
    }

    public String getInputPath() {
	return inputPath;
    }

    public void setInputPath(String inputPath) {
	this.inputPath = inputPath;
	saveConfig(inputPathKey, inputPath);
    }

    







}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.config;

import java.io.File;
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
    private final String hgmdColKey = "hgmdCol";
    private String hgmdCol;
    private final String clinvarColKey = "clinvarCol";
    private String clinvarCol;
    private final String splice45ColKey = "splice45";
    private String splice45Col;
    private final String splice15ColKey = "splice15";
    private String splice15Col;
    private final String totPredColKey = "totalPredictions";
    private String totPredCol;
    private final String predScorColKey = "percentDamagingPredictions";
    private String predScoreCol;

    
    // misc
    private final String inputPathKey = "inputPath";
    private String inputPath;
    private final String dbPathKey = "dbPath";
    private String dbPath;
    private final String dbNameKey = "dbName";
    private String dbName;
    
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
    private void loadConfig() {
	
	prop = loadProp();
	
	// read in config data
	geneCol = prop.getProperty(geneColKey);
	impactCol = prop.getProperty(impactColKey);
	pNomenCol = prop.getProperty(pNomenColKey);
	cNomenCol = prop.getProperty(cNomenColKey);
	inputPath = prop.getProperty(inputPathKey);
	hgmdCol = prop.getProperty(hgmdColKey);
	clinvarCol = prop.getProperty(clinvarColKey);
	splice45Col = prop.getProperty(splice45ColKey);
	splice15Col = prop.getProperty(splice15ColKey);
	totPredCol = prop.getProperty(totPredColKey);
	predScoreCol = prop.getProperty(predScorColKey);
	dbPath = prop.getProperty(dbPathKey);
	dbName = prop.getProperty(dbNameKey);
	
	
	
	
	    
	    
	
	
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

    public String getHgmdCol() {
	return hgmdCol;
    }

    public void setHgmdCol(String hgmdCol) {
	this.hgmdCol = hgmdCol;
	saveConfig(hgmdColKey, hgmdCol);
    }

    public String getClinvarCol() {
	return clinvarCol;
    }

    public void setClinvarCol(String clinvarCol) {
	this.clinvarCol = clinvarCol;
	saveConfig(clinvarColKey, clinvarCol);
    }

    public String getSplice45Col() {
	return splice45Col;
    }

    public void setSplice45Col(String splice45Col) {
	this.splice45Col = splice45Col;
	saveConfig(splice45ColKey, splice45Col);
    }

    public String getSplice15Col() {
	return splice15Col;
    }

    public void setSplice15Col(String splice15Col) {
	this.splice15Col = splice15Col;
	saveConfig(splice15ColKey, splice15Col);
    }

    public String getTotPredCol() {
	return totPredCol;
    }

    public void setTotPredCol(String totPredCol) {
	this.totPredCol = totPredCol;
	saveConfig(totPredColKey, totPredCol);
    }

    public String getPredScoreCol() {
	return predScoreCol;
    }

    public void setPredScoreCol(String predScoreCol) {
	this.predScoreCol = predScoreCol;
	saveConfig(predScorColKey, predScoreCol);
    }

    public String getDbPath() {
	return dbPath;
    }

    public void setDbPath(String dbPath) {
	this.dbPath = dbPath;
	saveConfig(dbPathKey, dbPath);
    }

    public String getDbName() {
	return dbName;
    }

    public void setDbName(String dbName) {
	this.dbName = dbName;
	saveConfig(dbNameKey, dbName);
    }

    public void setDbFullPath(String dbFull) {
	
	File dbFile = new File(dbFull);
	// set dbPath
	setDbPath(dbFile.getParent());
	// set dbName
	setDbName(dbFile.getName());
	
    }

    public String getDbFullPath () {
	String fullPath = dbPath + File.separator + dbName;
	return fullPath;
    }







}

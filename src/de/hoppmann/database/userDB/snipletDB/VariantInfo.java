/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB.snipletDB;

/**
 *
 * @author hoppmann
 */
public class VariantInfo {

    private String geneName;
    private String geneInfo = "";

    private String varName = "";
    private String varInfo = "";

    public VariantInfo(String geneName) {
	setGeneName(geneName);
    }

    
    
    
    
    public String getGeneName() {
	return geneName;
    }

    public void setGeneName(String geneName) {
	this.geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
    }

    public String getGeneInfo() {
	return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
	geneInfo = geneInfo.replaceAll("'", "´");
	this.geneInfo = geneInfo;
    }

    public String getVarName() {
	return varName;
    }

    public void setVarName(String varName) {
	this.varName = varName.replaceAll("\\s+", "");;
    }

    public String getVarInfo() {
	return varInfo;
    }

    public void setVarInfo(String varInfo) {
	this.varInfo = varInfo.replaceAll("'", "´");
    }
    
    
    









}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.modelsAndData;

/**
 *
 * @author hoppmann
 */
public enum Catagory {
    
    
    
    PATHOGEN("Path", "Pathogen"), PROBPATHO("ProbPatho", "Wahrscheinlich Pathogen"), UNCLEAR("Unclear", "Unklar"),
    PROBBENIGN("ProbBenign", "Wahrscheinlich Benigne"), BENIGN("Benign", "Benigne");
	
	private final String value;
	private final String code;

	
	// constructor
	private Catagory(String code, String name) {
	    this.value = name;
	    this.code = code;
	}
	
	
	/////////////////////////////////
	//////// getter / setter ////////
	/////////////////////////////////

	public String getCatagoryName() {
	    return value;
	}

	public String getCatagoryCode() {
	    return code;
	}

	
	public static Catagory getCatagoryByCode(String catagoryCode) {
	    for (Catagory cat : Catagory.values()){
		if (cat.code.equals(catagoryCode)){
		    return cat;
		}
	    }

	    return null;

	}
	
	
	public static String getValueByCode(String catagoryCode) {
	    for (Catagory cat : Catagory.values()) {
		if (cat.code.equals(catagoryCode)) {
		    return cat.value;
		}
	    }
	    
	    return null;
	}
	

	public static String getPathoCode() {
	    return Catagory.PATHOGEN.code;
	}
	
	public static String getProbPathoCode(){
	    return Catagory.PROBPATHO.code;
	}
	
	public static String getUnclearCode() {
	    return Catagory.UNCLEAR.code;
	}
	
	public static String getProbBenignCod() {
	    return Catagory.PROBBENIGN.code;
	}
		
	
    
}

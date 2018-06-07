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
    
    
    
    PATHOGEN("Path", "Pathogen"), PROBPATH("ProbPath", "Wahrscheinlich Pathogen"), UNCLEAR("Unclear", "Unklar"),
    PROBBENIGN("ProbBenign", "Wahrscheinlich Benigne"), BENIGN("Benign", "Benigne");
	
	private final String name;
	private final String code;

	
	// constructor
	private Catagory(String code, String name) {
	    this.name = name;
	    this.code = code;
	}
	
	
	// getter / setter

	public String getCatagoryName() {
	    return name;
	}

	public String getCatagoryCode() {
	    return code;
	}

	public static String getByCode(String catagoryCode) {
	    for (Catagory cat : Catagory.values()){
		if (cat.code.equals(catagoryCode)){
		    return cat.code;
		}
	    }

	    return null;

	}

    
}

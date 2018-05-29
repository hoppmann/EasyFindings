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
    
    
    
    CAUSAL("causal", "causal"), PROBCAUSAL("proabaly causal", "proabaly causal"), UNKNOWN("unknown significance", "unknown significance");
	
	private final String text;
	private final String code;

	
	// constructor
	private Catagory(String text, String code) {
	    this.text = text;
	    this.code = code;
	}
	
	
	// getter / setter

	public String getCatagoryText() {
	    return text;
	}

	public String getCatagoryCode() {
	    return code;
	}

	public static Catagory getByCode(String catagoryCode) {
	    for (Catagory cat : Catagory.values()){
		if (cat.code.equals(catagoryCode)){
		    return cat;
		}
	    }
	    
	    return null;
	    
	}
    
    
    
}

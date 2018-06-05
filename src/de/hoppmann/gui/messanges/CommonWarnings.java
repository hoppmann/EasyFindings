/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.gui.messanges;

import javafx.scene.control.Alert;

/**
 *
 * @author hoppmann
 */
public class CommonWarnings {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////

	
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
	
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    public void openFileFirst() {
	Alert alert = new Alert(Alert.AlertType.WARNING);
	alert.setTitle("Warning");
	alert.setHeaderText(null);
	alert.setContentText("Open file first.");
	alert.showAndWait();
    }
	
    
    
    public void noDataAvailable() {
	Alert alert = new Alert(Alert.AlertType.WARNING);
	alert.setTitle("Warning");
	alert.setHeaderText(null);
	alert.setContentText("No data available.");
	alert.showAndWait();

    }
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////










}

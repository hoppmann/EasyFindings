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
public class CommonErrors {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////

	
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
	
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    public void cantOpen(String file) {
	Alert alert = new Alert(Alert.AlertType.ERROR);
	alert.setTitle("ERROR");
	alert.setHeaderText(null);
	alert.setContentText("Can't open file " + file);
	alert.showAndWait();
    }

	
    
    public void fileNotFound(String file) {
	
	Alert alert = new Alert(Alert.AlertType.ERROR);
	alert.setTitle("ERROR");
	alert.setHeaderText(null);
	alert.setContentText("File " + file + " not found.");
	alert.showAndWait();

    }
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////










}

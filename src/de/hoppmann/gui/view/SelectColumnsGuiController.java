/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.config.Config;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class SelectColumnsGuiController implements Initializable {

    
    
    ///////////////////////////
    //////// Variables ////////
    ///////////////////////////
    
    // define default
    private String geneCol = "gene";
    
    @FXML
    private ComboBox<String> col1 = new ComboBox<>();
    @FXML
    private ComboBox<String> col2 = new ComboBox<>();
    @FXML
    private ComboBox<String> col3 = new ComboBox<>();
    @FXML
    private ComboBox<String> col4 = new ComboBox<>();
    @FXML
    private ComboBox<String> col5 = new ComboBox<>();
    @FXML
    private ComboBox<String> col6 = new ComboBox<>();

    @FXML
    private Button closeButton;
    
    @FXML
    private ChoiceBox<String> choice = new ChoiceBox<>();
    
    private ObservableList<String> header;
    
    
    
    
    /////////////////////////
    //////// Methods ////////
    /////////////////////////

    @FXML
    private void closeButtonEvent(ActionEvent event) {
	Stage stage = (Stage) closeButton.getScene().getWindow();
	stage.close();
    }
    
    
    
    private boolean checkStringList(ObservableList<String> list, String searchStr) {
	boolean check = false;
	
	for (String str : list) {
	    
	    if (str.trim().equals(searchStr)){
		check=true;
	    }
	}
	return check;
    }
    
    

    /////////////////////////////
    //////// Constructor ////////
    /////////////////////////////

    public SelectColumnsGuiController(List<String> header) {
	this.header = FXCollections.observableArrayList(header);
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


	// init config file
	Config config = new Config();
	config.loadConfig();

	// prepare gene column
	col1.getItems().addAll(header);
	if (checkStringList(header, config.getGeneCol()) == true) {
	    col1.getSelectionModel().select(config.getGeneCol());
	}
	
	// prepare impact column
	col2.getItems().addAll(header);
	if (checkStringList(header, config.getImpactCol()) == true){
	    col2.getSelectionModel().select(config.getImpactCol());
	}
	
	
//	config.setpNom("WOWITWORKS");
//	config.
	
	
	
	
	// prepare 
	
//	
//	checkStringList(header, "gene");
//	System.out.println(checkStringList(header, "genes"));
//	col1.getSelectionModel().select("genes");
////	col1.getSelectionModel().select(config.getGeneCol());
    }    
    
}

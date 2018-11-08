/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.userDbView;

import de.hoppmann.database.userDB.interfaces.IAddressRepository;
import de.hoppmann.database.userDB.receiverDB.AddressInfo;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class AddressTabController implements Initializable {

    
    private IAddressRepository addressRepo;
    private AddressInfo aInfo;
    @FXML private MainViewUserDbController mainViewUserDbController;
    @FXML private TextField titleField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField zipCodeField;
    @FXML private TextField countryField;
    @FXML private ComboBox<String> nameBox = new ComboBox<>();
    @FXML private AnchorPane addressTab;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void fillAddressFields(String name){
	aInfo = addressRepo.retrieveAddressInfo(name);
	if (aInfo != null){
	    titleField.setText(aInfo.getTitle());
	    addressField.setText(aInfo.getAddress());
	    cityField.setText(aInfo.getCity());
	    zipCodeField.setText(aInfo.getZipCode());
	    countryField.setText(aInfo.getCountry());
	} 
    }
    
    
    
    
    
    
    
    
    private void fillNameBox(){
	List<String> nameList = addressRepo.getNameList();
	nameBox.getItems().clear();
	nameBox.getItems().addAll(nameList);
	TextFields.bindAutoCompletion(nameBox.getEditor(), nameList);
    }
    
    
    
    
    
    
    
    // reset address fields
    private void resetAddressInfo () {
	
	// set all values null
	aInfo.setTitle("");
	aInfo.setName("");
	aInfo.setAddress("");
	aInfo.setCity("");
	aInfo.setZipCode("");
	aInfo.setCountry("");

	
    }

    
    public void injectMainController(MainViewUserDbController mainViewUserDbController) {
	this.mainViewUserDbController = mainViewUserDbController;
    }
    
    
    
    
    
    
    
    
    public void init(IAddressRepository addressRepo) {
	this.addressRepo = addressRepo;
	
	if (addressRepo.isValidRepo()) {
	    fillNameBox();
	}
    }
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	
	
	nameBox.setEditable(true);
	nameBox.valueProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		fillAddressFields(newValue);
	    }
	});
	

    }    
    
}

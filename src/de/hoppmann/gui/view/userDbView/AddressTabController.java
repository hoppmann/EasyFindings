/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.userDbView;

import de.hoppmann.database.userDB.ConnectionBuilder;
import de.hoppmann.database.userDB.interfaces.IAddressRepository;
import de.hoppmann.database.userDB.receiverDB.AddressInfo;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    private Label infoLabel;
    @FXML private MainViewUserDbController mainViewUserDbController;
    @FXML private TextField titleField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField zipCodeField;
    @FXML private TextField countryField;
    @FXML private ComboBox<String> nameBox = new ComboBox<>();
    @FXML private AnchorPane addressTab;
    
    
    
    
    
    
    
    
    
    @FXML
    private void newButtonAction(ActionEvent e) {
	
	aInfo = new AddressInfo("", "", "", "", "", "", -1);
	updateAddressInfoFromUI();
	
	addressRepo.newAddress(aInfo);
	
	updateNameBox();
	
	infoLabel.setText(aInfo.getName() + " added to database.");
	
    }
    
    
    
    
    
    
    
    
    @FXML
    private void saveButtonAction(ActionEvent e) {
	
	if (aInfo != null){
	    updateAddressInfoFromUI();
	    addressRepo.saveAddress(aInfo);
	    updateNameBox();
	    infoLabel.setText(aInfo.getName() + " updated in database.");
	}
    }
    
    
    
    
    
    private void updateAddressInfoFromUI() {
	aInfo.setTitle(titleField.getText());
	aInfo.setName(nameBox.getValue());
	aInfo.setAddress(addressField.getText());
	aInfo.setCity(cityField.getText());
	aInfo.setZipCode(zipCodeField.getText());
	aInfo.setCity(cityField.getText());
    }
    
    
    
    
    
    
    @FXML
    private void removeButtonAction(ActionEvent e) {

	if (aInfo == null) {
	    return;
	}

	// double check if deletion is desired
	Alert deletionDialog = new Alert(Alert.AlertType.CONFIRMATION);
	deletionDialog.setTitle("Remove " + aInfo.getName() + " from database?");
	deletionDialog.setHeaderText(null);
	deletionDialog.setContentText("This deletion can't be undone.");
	deletionDialog.initOwner(infoLabel.getScene().getWindow());
	Optional<ButtonType> result = deletionDialog.showAndWait();

	if (result.get() == ButtonType.OK) {

	    addressRepo.removeAddress(aInfo);
	    String removedName = aInfo.getName();
	    aInfo = new AddressInfo("", "", "", "", "", "", -1);
	    fillAddressFields();
	    updateNameBox();
	    infoLabel.setText(removedName + " removed from database");
	}
    }
    
    
    
    
    
    private void fillAddressFields(){
	if (aInfo != null){
	    titleField.setText(aInfo.getTitle());
	    nameBox.getSelectionModel().select(aInfo.getName());
	    addressField.setText(aInfo.getAddress());
	    cityField.setText(aInfo.getCity());
	    zipCodeField.setText(aInfo.getZipCode());
	    countryField.setText(aInfo.getCountry());
	} 
    }
    
    
    
    
    
    
    
    
    private void updateNameBox(){
	List<String> nameList = addressRepo.getNameList();
	nameBox.getItems().clear();
	nameBox.getItems().addAll(nameList);
	TextFields.bindAutoCompletion(nameBox.getEditor(), nameList);
		
	if (aInfo != null) {
	    nameBox.getSelectionModel().select(aInfo.getName());
	}
    }
    
    
    
    
    
    
    
    
    
    

    
    public void injectMainController(MainViewUserDbController mainViewUserDbController) {
	this.mainViewUserDbController = mainViewUserDbController;
    }
    
    
    
    
    
    
    
    
    public void init(IAddressRepository addressRepo) {
	this.addressRepo = addressRepo;
	
	this.infoLabel = mainViewUserDbController.getInfoLabel();
            if (ConnectionBuilder.hasConnection()){
            if (!addressRepo.isValidRepo()) {
                addressRepo.makeRepoValid();
            }
            updateNameBox();
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
		if (addressRepo.retrieveAddressInfo(newValue) != null){
		    aInfo = addressRepo.retrieveAddressInfo(newValue);
		    fillAddressFields();
		}
	    }
	});
	

    }    
    
}

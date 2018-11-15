/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.userDbView;

import de.hoppmann.database.userDB.ConnectionBuilder;
import de.hoppmann.database.userDB.receiverDB.IAddressRepository;
import de.hoppmann.database.userDB.receiverDB.AddressInfo;
import de.hoppmann.database.userDB.receiverDB.IAddressInfo;
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
//    private AddressInfo adrInfo;
    private IAddressInfo aInfo;
    private Label infoLabel = new Label();
    @FXML private MainViewUserDbController mainViewUserDbController;
    @FXML private TextField titleField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField zipCodeField;
    @FXML private TextField countryField;
    @FXML private ComboBox<String> nameBox = new ComboBox<>();
    @FXML private AnchorPane addressTab;
    
    
    
    
    
    
    private void newInfoStoreage() {
        aInfo = new AddressInfo("", "", "", "", "", "", -1);
    }
    
    
    
    
    
    @FXML
    private void newButtonAction(ActionEvent e) {

        newInfoStoreage();
        
	updateAddressInfoFromUI();
	
	addressRepo.newAddress(aInfo);
	
	updateNameBox();
	
	infoLabel.setText(aInfo.getReceiverName() + " added to database.");
	
    }
    
    
    
    
    
    
    
    
    @FXML
    private void saveButtonAction(ActionEvent e) {
	
	if (aInfo != null){
	    updateAddressInfoFromUI();
	    addressRepo.saveAddress(aInfo);
	    updateNameBox();
	    infoLabel.setText(aInfo.getReceiverName() + " updated in database.");
	}
    }
    
    
    
    
    
    private void updateAddressInfoFromUI() {
	aInfo.setReceiverTitle(titleField.getText());
	aInfo.setReceiverName(nameBox.getValue());
	aInfo.setReceiverAddress(addressField.getText());
	aInfo.setReceiverCity(cityField.getText());
	aInfo.setReceiverZipCode(zipCodeField.getText());
	aInfo.setReceiverCity(cityField.getText());
    }
    
    
    
    
    
    
    @FXML
    private void removeButtonAction(ActionEvent e) {

	if (aInfo == null) {
	    return;
	}

	// double check if deletion is desired
	Alert deletionDialog = new Alert(Alert.AlertType.CONFIRMATION);
	deletionDialog.setTitle("Remove " + aInfo.getReceiverName() + " from database?");
	deletionDialog.setHeaderText(null);
	deletionDialog.setContentText("This deletion can't be undone.");
	deletionDialog.initOwner(infoLabel.getScene().getWindow());
	Optional<ButtonType> result = deletionDialog.showAndWait();

	if (result.get() == ButtonType.OK) {

	    addressRepo.removeAddress(aInfo);
	    String removedName = aInfo.getReceiverName();
            newInfoStoreage();
            fillAddressFields();
	    updateNameBox();
	    infoLabel.setText(removedName + " removed from database");
	}
    }
    
    
    
    
    
    private void fillAddressFields(){
	if (aInfo != null){
	    titleField.setText(aInfo.getReceiverTitle());
	    nameBox.getSelectionModel().select(aInfo.getReceiverName());
	    addressField.setText(aInfo.getReceiverAddress());
	    cityField.setText(aInfo.getReceiverCity());
	    zipCodeField.setText(aInfo.getReceiverZipCode());
	    countryField.setText(aInfo.getReceiverCountry());
	} 
    }
    
    
    
    
    
    
    
    
    private void updateNameBox(){
	List<String> nameList = addressRepo.getNameList();
	nameBox.getItems().clear();
	nameBox.getItems().addAll(nameList);
	TextFields.bindAutoCompletion(nameBox.getEditor(), nameList);
	if (aInfo != null) {
	    nameBox.getSelectionModel().select(aInfo.getReceiverName());
	}
    }
    
    
    
    
    
    
    
    
    
    

    
    public void injectMainController(MainViewUserDbController mainViewUserDbController) {
	this.mainViewUserDbController = mainViewUserDbController;
    }
    
    
    
    
    
    
    
    
    public void init(IAddressRepository addressRepo, Label infoLable) {
	this.addressRepo = addressRepo;
	this.infoLabel = infoLable;
        
        
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
		
                AddressInfo newInfo = new AddressInfo("", newValue, "", "", "", "", -1);
                boolean success = addressRepo.retrieveAddressInfo(newInfo);
                if (success) {
                    aInfo = newInfo;
                    fillAddressFields();
                }
                        
	    }
	});
	

    }    
    
}

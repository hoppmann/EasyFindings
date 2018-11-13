/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.userDbView;

import de.hoppmann.database.userDB.ConnectionBuilder;
import de.hoppmann.database.userDB.ConnectionHolder;
import de.hoppmann.database.userDB.interfaces.IVariantInfoRepository;
import de.hoppmann.database.userDB.snipletDB.VariantInfo;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class VariantInfoTabController implements Initializable {

    private IVariantInfoRepository varInfoRepo;
    private VariantInfo varInfo;
    @FXML private Label infoLabel;
    @FXML private VariantTabController variantTabController;
    @FXML private AnchorPane variantInfoTab;
    @FXML private ComboBox<String> varNameBox;
    @FXML private TextArea varInfoArea;
    
    
    
    
    
    
    
    
    @FXML
    private void saveVariant() {
	
	setVarInfoFromGui();

	varInfoRepo.saveVariant(varInfo);

	updateVarNameBox();
	
	infoLabel.setText("Updated: " + varInfo.getVarName());
	
    }
    
    
    
    
    
    
    
    
    @FXML
    private void removeVariant() {
	
	
	setVarInfoFromGui();

	
	
	// double check if  variant should be deleated
	Alert delDiag = new Alert(Alert.AlertType.CONFIRMATION);
	delDiag.setTitle("Remove " + varInfo.getVarName() + " from database?");
	delDiag.setHeaderText(null);
	delDiag.setContentText("This deletion can't be undone.");
	delDiag.initOwner(infoLabel.getScene().getWindow());
	Optional<ButtonType> result = delDiag.showAndWait();

	// remove var if desired
	if (result.get() == ButtonType.OK) {
	    varInfoRepo.removeVariant(varInfo);
	    // print out that variant was removed
	    infoLabel.setText("Removed: " + varInfo.getVarName());
	    // update variant name list
	    updateVarNameBox();

	}


	
    }
    
    
    
    @FXML
    private void varNameBoxAction (ActionEvent event) {
	
	setVarInfoFromGui();
	
	
	if (varInfo.getVarName() != null) {
	    varInfo = varInfoRepo.getVariantInfo(varInfo);
	    varInfoArea.setText(varInfo.getVarInfo());
	}
	
	
	
    }
    
    
    
    private void setVarInfoFromGui() {
	
	if (varNameBox.getValue() == null || varNameBox.getValue().equals("")){
	    return;
	} else {
	    varInfo.setVarName(varNameBox.getValue());
	}
	
	
	if (varInfoArea.getText() == null){
	    varInfo.setVarInfo("");
	} else {
	    varInfo.setVarInfo(varInfoArea.getText());
	}
    }
    
    
    
    
    
    
    
    private void updateVarNameBox() {
	
	List<String> varList = varInfoRepo.getVariantList(varInfo);
	
	varNameBox.getItems().clear();
	varNameBox.getItems().addAll(varList);

	if (!varInfo.getVarName().equals("") && varList.contains(varInfo.getVarName())) {
	    varNameBox.getSelectionModel().select(varInfo.getVarName());
	} else {
	    varNameBox.getSelectionModel().selectFirst();
	}
	varNameBox.setEditable(true);
	
	TextFields.bindAutoCompletion(varNameBox.getEditor(), varList);
	
    }

    
   
    
    
    
    
    
    
    
    
    public void inject (VariantTabController variantTabController,IVariantInfoRepository varInfoRepo) {
	this.variantTabController = variantTabController;
	this.varInfoRepo = varInfoRepo;
    }

    
    
    
    /**
     * init view with chosen gene name for selection
     * check dbTable for validity else achieve validity
     * @param varInfo 
     */

    public void init (VariantInfo varInfo) {
	this.varInfo = varInfo;
	this.infoLabel = variantTabController.getMainViewUserDbController().getInfoLabel();
	
        if (ConnectionBuilder.hasConnection()){
	    if (!varInfoRepo.isRepoValid()) {
                varInfoRepo.makeRepoValid();
            }
            updateVarNameBox();
        }
    }
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	


    }    
    
}

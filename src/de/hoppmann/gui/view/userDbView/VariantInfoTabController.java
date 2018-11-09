/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.userDbView;

import de.hoppmann.database.userDB.interfaces.IVariantInfoRepository;
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
    private String geneName;
    @FXML private Label infoLabel;
    @FXML private VariantTabController variantTabController;
    @FXML private AnchorPane variantInfoTab;
    @FXML private ComboBox<String> varNameBox;
    @FXML private TextArea varInfoArea;
    
    
    
    
    
    
    
    
    @FXML
    private void saveVariant() {
	
	String varName = varNameBox.getValue();
	

	varName = varNameBox.getValue();
	if (varName == null || varName.equals("")) {
	    infoLabel.setText("ERROR: No variant name given.");
	    return;
	}


	// remove all whitespace element from variant
	varName = varName.replaceAll("\\s+", "");

	
	// get variant info
	String varInfo;
	if (varInfoArea.getText() == null) {
	    varInfo = "";
	} else {
	    varInfo = varInfoArea.getText();
	}

	varInfoRepo.saveVariant(geneName, varName, varInfo);

	updateVarNameBox(varName);
    }
    
    
    
    @FXML
    private void removeVariant() {
	
	
	// retrieve var name 
	String varName = varNameBox.getValue();
	if (varName == null){
	    infoLabel.setText("ERROR: No variant chosen");
	}
	varName = varName.replaceAll("\\s+", "");

	
	
	// double check if  variant should be deleated
	Alert delDiag = new Alert(Alert.AlertType.CONFIRMATION);
	delDiag.setTitle("Remove " + varName + " from database?");
	delDiag.setHeaderText(null);
	delDiag.setContentText("This deletion can't be undone.");
	delDiag.initOwner(infoLabel.getScene().getWindow());
	Optional<ButtonType> result = delDiag.showAndWait();

	// remove var if desired
	if (result.get() == ButtonType.OK) {
	    varInfoRepo.removeVariant(geneName, varName);
	}

	// update variant name list
	updateVarNameBox(varName);

	// print out that variant was removed
	infoLabel.setText(varName + " removed");
	
    }
    
    
    
    @FXML
    private void varNameBoxAction (ActionEvent event) {
	
	String varName = varNameBox.getValue();
	
	if (varName != null) {
	    varInfoArea.setText(varInfoRepo.getVariantInfo(geneName, varName));
	}
	
	
	
    }
    
    
    
    
    
    
    
    private void updateVarNameBox(String varName) {
	
	List<String> varList = varInfoRepo.getVariantList(geneName);
	
	varNameBox.getItems().clear();
	varNameBox.getItems().addAll(varList);

	if (!varName.equals("") && varList.contains(varName)) {
	    varNameBox.getSelectionModel().select(varName);
	} else {
	    varNameBox.getSelectionModel().selectFirst();
	}
	
	varNameBox.setEditable(true);
	
	TextFields.bindAutoCompletion(varNameBox.getEditor(), varList);
	
    }

    
    
    
    
    
    public void injectVarTabController (VariantTabController variantTabController) {
	this.variantTabController = variantTabController;
    }

    public void setInfoLabel(Label infoLabel) {
	this.infoLabel = infoLabel;
    }

    public void setVarInfoRepo(IVariantInfoRepository varInfoRepo) {
	this.varInfoRepo = varInfoRepo;
    }
    
    

    public void init(String geneName) {
	init(geneName, "");
    }
    
    public void init (String geneName, String varName) {

	if (geneName != null){
	    geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
	}
	this.geneName = geneName;
	
	updateVarNameBox(varName);
    }
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	


    }    
    
}

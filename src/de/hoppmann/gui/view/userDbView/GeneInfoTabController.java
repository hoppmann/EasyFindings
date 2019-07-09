/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.userDbView;

import de.hoppmann.database.userDB.interfaces.IGeneInfoRepository;
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
public class GeneInfoTabController implements Initializable {

    
    private IGeneInfoRepository geneInfoRepository;
    private Label infoLabel;
    private VariantInfo varInfo;
    
    @FXML private FindingsTabController findingsTabController;
    @FXML private VariantTabController variantTabController;
    @FXML private VariantInfoTabController variantInfoTabController;
    @FXML private AnchorPane geneInfoTab;
    @FXML private ComboBox<String> geneNameBox;
    @FXML private TextArea geneInfoTextArea;
    
    
    
    
    
    
    
    private void setGeneInfoFromUI(){
	
	
	
	if (geneNameBox.getValue() == null || geneNameBox.getValue().equals("")){
	    return;
	} else {
	    varInfo = new VariantInfo(geneNameBox.getValue());
	}
	
	
	if (geneInfoTextArea.getText() == null){
	    varInfo.setGeneInfo("");
	} else {
	    varInfo.setGeneInfo(geneInfoTextArea.getText());
	}
	

	
    }
    
    
    
    
    
    
    
    @FXML
    private void saveButtonAction(ActionEvent e){
	
	setGeneInfoFromUI();

	// save gene info
	geneInfoRepository.saveGene(varInfo);
	
	// refresh gene combobox choice
	updateGeneNameBox(varInfo);
	
	infoLabel.setText("Info for gene " + varInfo.getGeneName() + " stored!");

	
    }
    
    
    
    
    
    
    
    @FXML
    private void removeButtonAction(ActionEvent e){

	setGeneInfoFromUI();

	
	
	// double check if deletion is desired
	Alert deletionDialog = new Alert(Alert.AlertType.CONFIRMATION);
	deletionDialog.setTitle("Remove " + varInfo.getGeneName() + " from database?");
	deletionDialog.setHeaderText(null);
	deletionDialog.setContentText("This deletion can't be undone.");
	deletionDialog.initOwner(infoLabel.getScene().getWindow());
	Optional<ButtonType> result = deletionDialog.showAndWait();

	if (result.get() == ButtonType.OK){
	    geneInfoRepository.removeGene(varInfo);
            
	    // update gene list
	    updateGeneNameBox(varInfo);
            geneInfoTextArea.setText("");

	    infoLabel.setText(varInfo.getGeneName() + " removed");
	}
	
	
	
	
    }
    
    
    
    
    
    
    
    
    
    @FXML
    private void geneBoxAction() {
	
	setGeneInfoFromUI();
	
	if (varInfo != null && varInfo.getGeneName() != null){
	    varInfo = geneInfoRepository.getGeneInfo(varInfo);
	    geneInfoTextArea.setText(varInfo.getGeneInfo());
            
            variantInfoTabController.init(varInfo);
            
	}
    } 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void updateGeneNameBox(VariantInfo varInfo) {
	
	String geneName = varInfo.getGeneName();
	List<String> geneList = geneInfoRepository.getGeneList();
	
	geneNameBox.getItems().clear();
	geneNameBox.getItems().addAll(geneList);
	
	
	if (!geneName.equals("") && geneList.contains(geneName)){
	    geneNameBox.getSelectionModel().select(geneName);
	} else {
	    geneNameBox.getSelectionModel().selectFirst();
	}
	
	geneNameBox.setEditable(true);
	
	TextFields.bindAutoCompletion(geneNameBox.getEditor(), geneInfoRepository.getGeneList());

    }
    
    
    
    protected void setNewGene (VariantInfo varInfo){
	this.varInfo = varInfo;
	geneNameBox.getSelectionModel().select(varInfo.getGeneName());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public void inject (VariantTabController variantTabController, FindingsTabController findingsTabController, 
	    VariantInfoTabController variantInfoTabController, IGeneInfoRepository geneInfoRepository){
        
	this.variantTabController = variantTabController;
        this.findingsTabController = findingsTabController;
        this.variantInfoTabController = variantInfoTabController;
        this.geneInfoRepository = geneInfoRepository;
    }
    
 
    
    
    
    /**
     * init view with chosen geneName for selection
     * check dbTable for validity else achieve validity
     * @param varInfo 
     */
    
    public void init(VariantInfo varInfo) {

	this.varInfo = varInfo;
	infoLabel = variantTabController.getMainViewUserDbController().getInfoLabel();

	if (!geneInfoRepository.isRepoValid()){
	    geneInfoRepository.makeRepoValid();
	}
	updateGeneNameBox(varInfo);
	
    }
    
    public VariantInfo getVarInfo() {
	return varInfo;
    }

    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	
	
	
    }    
    
}

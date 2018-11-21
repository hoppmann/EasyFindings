/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.userDbView;

import de.hoppmann.database.userDB.ConnectSQLite;
import de.hoppmann.database.userDB.ConnectUserDB;
import de.hoppmann.database.userDB.ConnectionBuilder;
import de.hoppmann.database.userDB.PanelDB.IPanelInfo;
import de.hoppmann.database.userDB.interfaces.IPanelRepository;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class PanelTabController implements Initializable {

    
    
    @FXML private AnchorPane panelTab;
    @FXML private ComboBox<String> panelBox = new ComboBox<>();
    @FXML private TextArea panelArea;
    private IPanelRepository panelRepo;
    private IPanelInfo panelInfo;
    private Label InfoLabel;
    
    
    
    
    
    
    @FXML 
    private void storePanelAction (ActionEvent event){
        if (panelBox.getValue() != null){
            panelInfo.setPanelName(panelBox.getValue());
            panelInfo.setGeneList(panelArea.getText());
            panelRepo.savePanel(panelInfo);
            
            InfoLabel.setText(panelInfo.getPanelName() + " updated in database.");
        }
        
        updatePanelBox();
    }
    
    
    
    
    
    
    
    @FXML
    private void removePanelAction (ActionEvent event){

        if (panelBox.getValue() != null){
            setPanelInfo(panelBox.getValue());
            panelRepo.removePanel(panelInfo);
            panelArea.setText("");
            
            InfoLabel.setText(panelInfo.getPanelName() + " removed from database.");
        }
        
        updatePanelBox();
        
    }
    
    
    
    
    
    @FXML
    private void panelBoxAction (ActionEvent event) {

        List<String> panelNames = panelRepo.getPanelNames();
                
        if (panelBox.getValue() != null && panelNames.contains(panelBox.getValue())){
            panelInfo.setPanelName(panelBox.getValue());
            panelRepo.getPanelGenes(panelInfo);
            panelArea.setText(panelInfo.getGeneListAsString());
        }
    }
    
    
    
    private void setPanelInfo() {
        setPanelInfo("");
    }
    private void setPanelInfo(String panelName){
        setPanelInfo(panelName, "");
    }
    private void setPanelInfo(String panelName, String geneList){
        panelInfo.setPanelName(panelName);
        panelInfo.setGeneList(geneList);

    }
    
    
    
    private void updatePanelBox(){
        
        List<String> panelNames = panelRepo.getPanelNames();
        
        panelBox.getItems().clear();
        panelBox.getItems().addAll(panelNames);
        if (panelInfo != null && panelNames.contains(panelInfo.getPanelName())){
            panelBox.getSelectionModel().select(panelInfo.getPanelName());
        } 
        
        panelBox.setEditable(true);
        
        TextFields.bindAutoCompletion(panelBox.getEditor(), panelNames);
    }
    
    
    
    
    
    
    public void init(IPanelRepository panelRepo, IPanelInfo panelInfo, Label infoLabel) {
        this.InfoLabel = infoLabel;
        this.panelRepo = panelRepo;
        this.panelInfo = panelInfo;
        
        
        
        boolean isConnected = false;
                
        if (ConnectionBuilder.hasConnection()){
            isConnected = true;
        } else {
            isConnected = new ConnectUserDB(new ConnectSQLite()).connectSqLiteUserDB();
        }
        

        
        if (isConnected){
            if (!panelRepo.isRepoValid()) {
                panelRepo.makeRepoValid();
            } 
        
            updatePanelBox();
        } 

        
        
        
    }
    
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        
        panelArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                panelInfo.setGeneList(newValue);
            }
        });

        
        
        
        
        
        
        
        panelBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                panelInfo.setPanelName(newValue);
            }
        });
        
        
        

    }    
    
}

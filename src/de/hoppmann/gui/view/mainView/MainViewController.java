/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.mainView;

import de.hoppmann.database.userDB.ConnectionBuilder;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class MainViewController implements Initializable {

    
    
    @FXML private Label infoLable;
    @FXML private Tab findingsTab;
    @FXML private Tab dataTab;
    @FXML private FindingsTabViewController findingsTabViewController;
    @FXML private DataTabViewController dataTabViewController;
    
    
    
    
    
    @FXML
    private void closeButtonAction (ActionEvent event) {
        
        if (ConnectionBuilder.hasConnection()) {
            ConnectionBuilder.closeConnection();
        }
        
        Stage stage = (Stage) infoLable.getScene().getWindow();
        stage.close();
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        findingsTabViewController.inject(this, infoLable);
        dataTabViewController.inject(this, infoLable);
        
        
        
        
        findingsTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    findingsTabViewController.init(dataTabViewController.getFindings());
                }
            }
        });



        
        


        dataTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    dataTabViewController.storeFindings();
                }
            }
        });
        


        
        

        dataTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    dataTabViewController.createTable();
                }
            }
        });
                
        
        
        
        
    }    
    
}

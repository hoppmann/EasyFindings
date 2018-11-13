/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.mainView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class FindingsTabViewController implements Initializable {

    @FXML private AnchorPane findingsTabView;
    @FXML private TableView findingsTable;
    private MainViewController mainViewController;
    private Label infoLabel;
    
    
    
    
    
    @FXML
    private void refreshButtonAciton(ActionEvent event){
        
        
        
    }
    
    
    
    
    
    
    
    @FXML
    private void columnsButtonAction(ActionEvent event){
        
    }
    
    
    
    
    
    
    @FXML
    private void reportButtonAction(ActionEvent event){
        
    }
    
    
    
    
    @FXML
    private void databaseButtonAction(ActionEvent event){
        
    }
    
    
    
    
    
    public void inject (MainViewController mainViewController, Label infoLabel){
        this.mainViewController = mainViewController;
        this.infoLabel = infoLabel;
    }
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

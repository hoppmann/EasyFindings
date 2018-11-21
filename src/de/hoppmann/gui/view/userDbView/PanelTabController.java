/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.userDbView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class PanelTabController implements Initializable {

    
    
    @FXML private AnchorPane panelTab;
    @FXML private ComboBox<String> panelBox = new ComboBox<>();
    @FXML private TextArea panelArea;
    
    
    
    
    
    
    @FXML
    private void newPanelAction (ActionEvent event) {
	System.out.println("TO BE IMPLEMENTED");
    }
    
    
    
    
    
    
    @FXML 
    private void storePanelAciton (ActionEvent event){
	System.out.println("TO BE IMPLEMENTED");
    }
    
    
    
    
    
    
    
    @FXML
    private void removePanelAction (ActionEvent event){
	System.out.println("TO BE IMPLEMENTED");
    }
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.reportView;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class EntryTabViewController implements Initializable {

    @FXML AnchorPane entryTabView;
    @FXML TextField coField;
    @FXML TextField patientField;
    @FXML TextField materialField;
    @FXML TextField indicationField;
    @FXML TextField assessmentField;
    @FXML TextField arrivalDateField;
    @FXML ComboBox senderBox;
    @FXML ComboBox diagMethodBox;
    @FXML ComboBox seqMethodBox;
    @FXML ComboBox genePanelBox;
    @FXML TextArea genePanelArea;
    
    
    
    
    
    @FXML 
    private void applyButtonAction(ActionEvent event) {
        System.out.println("TO BE IMPLEMENTED");
    }
    
    
    
    
    
    @FXML
    private void senderBoxAction(ActionEvent event) {
        System.out.println("TO BE IMPLEMENTED");
    }
    
    
    
    
    
    
    @FXML
    private void diagMethodBoxAction(ActionEvent event){
        System.out.println("TO BE IMPLEMENTED");
    }
    
    
    
    
    
    
    
    @FXML
    private void seqMethodBoxAction(ActionEvent event){
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

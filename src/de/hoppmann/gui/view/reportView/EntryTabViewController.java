/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.reportView;

import de.hoppmann.createReport.ReportRepository;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class EntryTabViewController implements Initializable {

    @FXML private AnchorPane entryTabView;
    @FXML private TextField coField;
    @FXML private TextField patientInfoField;
    @FXML private TextField materialField;
    @FXML private TextField indicationField;
    @FXML private TextField assessmentField;
    @FXML private TextField arrivalDateField;
    @FXML private ComboBox<String> senderBox = new ComboBox();
    @FXML private ComboBox<String> diagMethodBox = new ComboBox();
    @FXML private ComboBox<String> seqMethodBox = new ComboBox();
    @FXML private ComboBox<String> genePanelBox = new ComboBox();
    @FXML private TextArea genePanelArea;
    
    
    private ReportRepository repository;
    
    
    
    
    
    
    @FXML
    private void senderBoxAction(ActionEvent event) {
	repository.setSenderKey(senderBox.getValue());
    }
    
    
    
    
    
    
    @FXML
    private void diagMethodBoxAction(ActionEvent event){
        repository.setDiagMethodKey(diagMethodBox.getValue());
    }
    
    
    
    
    
    
    
    @FXML
    private void seqMethodBoxAction(ActionEvent event){
        repository.setSeqMethodKey(seqMethodBox.getValue());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    private Set createPanelList(String inputText) {
	
	// check if input not null if so split text into set (avoid duplications
	Set<String> geneList = new TreeSet<>();
	if (inputText != null) {
	    
	    // remove white spaces
	    List<String> tmpList = Arrays.asList(inputText.split("\n"));
	    for (String curGene : tmpList) {
	
		// skip empty lines or lines containing only white spaces
		if (curGene.replaceAll("\\s", "").equals("")){
		    continue;
		}
		geneList.add(curGene.replaceAll("\\s", ""));
	    }
	}
        
	return geneList;
    }

    
    
    
    
    
    
    
    
    public void init(ReportRepository data) {
        this.repository = data;
        
        genePanelArea.setText(String.join("\n", repository.getPanel()));
        coField.setText(repository.getReceiverCoLine());
        patientInfoField.setText(repository.getPatientInfo());
        materialField.setText(repository.getMaterial());
        indicationField.setText(repository.getIndication());
        assessmentField.setText(repository.getAssessment());
        arrivalDateField.setText(repository.getMaterialArrivalDate());
        
        
        
        senderBox.getItems().setAll(repository.getSender().keySet());
        senderBox.getSelectionModel().select(repository.getSenderKey());
        

        diagMethodBox.getItems().setAll(repository.getDiagMethod().keySet());
        diagMethodBox.getSelectionModel().select(repository.getDiagMethodKey());
        

        seqMethodBox.getItems().setAll(repository.getSeqMethod().keySet());
        seqMethodBox.getSelectionModel().select(repository.getSeqMethodKey());

        
        
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {




        coField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                repository.setReceiverCoLine(newValue);
            }
        });
       	coField.setTooltip(new Tooltip("Multiple entries seperated by \";\""));






        patientInfoField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                repository.setPatientInfo(newValue);
            }
        });






        materialField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                repository.setMaterial(newValue);
            }
        });




        
        indicationField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                repository.setIndication(newValue);
            }
        });




        
        assessmentField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                repository.setAssessment(newValue);
            }
        });

        
        


        arrivalDateField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                repository.setMaterialArrivalDate(newValue);
            }
        });

        
        



        genePanelArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                repository.setPanel(createPanelList(newValue));
            }
        });




    }    
    
}

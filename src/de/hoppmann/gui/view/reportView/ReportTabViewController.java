/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.reportView;

import de.hoppmann.config.Config;
import de.hoppmann.createReport.CreateReport;
import de.hoppmann.createReport.ReportRepository;
import de.hoppmann.gui.modelsAndData.FindingsRepository;
import de.hoppmann.gui.oldView.MainGuiController;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class ReportTabViewController implements Initializable {

    
    
    @FXML private AnchorPane reportTabView;
    @FXML private HTMLEditor reportEditor;
    
    
    private FindingsRepository findings;
    private ReportRepository reportRepo;
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void saveButtonAction(ActionEvent event){
        	

        // choose file where to save
	FileChooser chooser = new FileChooser();
	chooser.setTitle("Save report");
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word document (*.doc)", "*.doc"));
       	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("html document (*.html)", "*.html"));
	File fileOut = chooser.showSaveDialog(null);
	
	
	
	// check if choice was abborted
	if (fileOut != null) {

	    // check / add default ending
            // check if .doc or .html is chosen else add suffix to file
	    if (! fileOut.getName().contains(".doc") && ! fileOut.getName().contains(".html")) {
		fileOut = new File(fileOut.getAbsolutePath() + ".doc");
	    }
	    
            
	    // write report in file
	    BufferedWriter writer = null;
	    try {
		writer = new BufferedWriter(new FileWriter(fileOut));
		writer.write(reportRepo.getReport());
		writer.close();
	    } catch (IOException ex) {
		Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
		try {
		    writer.close();
		} catch (IOException ex) {
		    Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}



    }
    
    
    
    
    
    @FXML
    private void refreshButtonAction(ActionEvent event){
        createReport();
        reportEditor.setHtmlText(reportRepo.getReport());
    }
    
    
    
    
    
    
    
    @FXML
    private void templateButtonAction (ActionEvent event){
        Config config = Config.getInstance();
	
	// choose file
	FileChooser chooser = new FileChooser();
	chooser.setTitle("Select report template");
	
	if (new File(config.getHtmlTemplate()).exists()){
	    chooser.setInitialDirectory(new File(config.getHtmlTemplate()).getParentFile());
	} else {
	    chooser.setInitialDirectory(null);
	}
	
	
	File template = chooser.showOpenDialog(new Stage());
	
	
	if (template != null && template.exists()){
	    config.setHtmlTemplate(template.getAbsolutePath());
	}
    }
    
    
    
    
    
    
    
    @FXML
    private void closeButtonAction(ActionEvent event){
        Stage stage = (Stage) reportTabView.getScene().getWindow();
        stage.close();
    }
    
    
    
    
    
    
    
    
    
    private void createReport() {
    
        CreateReport createReport = new CreateReport(findings, reportRepo);

	// open template
	boolean templateOk = createReport.openTemplate();

        
	// prepare gene table
	createReport.prepareFindingGenesTable();
	
        
        if (templateOk) {
            createReport.replaceValues();
            reportRepo.setReport(createReport.getReport());
        } 
        
    }
    
    
    
    
    
    
    
    
    public void init(FindingsRepository findings, ReportRepository reportRepo) {
        this.findings = findings;
        this.reportRepo = reportRepo;
        createReport();
        reportEditor.setHtmlText(reportRepo.getReport());
    }
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        reportEditor.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                reportRepo.setReport(reportEditor.getHtmlText());
            }
        });
        
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.createReport.CreateReport;
import de.hoppmann.createReport.ReportDataModel;
import de.hoppmann.gui.modelsAndData.StoreFindings;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class ReportViewerController implements Initializable {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    //// FXML variables
    
    // editor tab 
    @FXML HTMLEditor htmlEditor;
    @FXML Tab ReportTab;
    
    
    // entry mask tab
    @FXML Tab entryMaskTab;
    @FXML TextField name;
    @FXML TextField street;
    @FXML TextField City;
    
    
    
    // html report tab
    @FXML Tab htmlReportTab;
    @FXML TextArea htmlTextArea;
    

    
    //// other variables    
    private String report;
    private StoreFindings findings;
    private ReportDataModel reportData;
    private CreateReport createReport; 
   
    
    
    ////////////////////////////////
    //////// initialization ////////
    ////////////////////////////////
    
    /*
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }    
    
    
    
     //// init editor and get initial report document
    public void init (StoreFindings findings) {
	
	// retrieve variables and instanciate variables
	this.findings = findings;
	reportData = new ReportDataModel();
	
	
	prepareInitialReport();
	
	// show initial report
	htmlEditor.setHtmlText(report);
	
	
	
	
    }
    
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    
    //// if a field was filled add it to reprot model
    @FXML
    private void nameEntryAction (ActionEvent event) {
	
	reportData.setReceiverName(name.getText());
	
    }
    
    
    
    
    
    
    //// handle switching to report tab
    @FXML
    private void chooseReportTabAction (ActionEvent event) {
	
	nameEntryAction(new ActionEvent());
	createReport.replaceValues();
	htmlEditor.setHtmlText(report);
	
	
    }
    
    
    
    
    //// close current window
    @FXML
    public void closeButtonAction(ActionEvent event) {
	
	Stage stage = (Stage) htmlEditor.getScene().getWindow();
	stage.close();
	
    }
    
    
    
    
    
    
    
    
    
    //// prepare initial report text
    private void prepareInitialReport() {
	//// load initial Report
	// load HTML template
	createReport = new CreateReport(findings, reportData);

	// open template
	createReport.openTemplate();

	// prepare gene table
	createReport.prepareFindingGenesTable();
	
	// replace values
	createReport.replaceValues();

	// get modified template as initial text
	report = createReport.getReport();
    }
    
    
    
    
    
    //    //// FXML Methods
//       private void saveReport(HTMLEditor editor) {
//        
//        FileChooser chooser = new FileChooser();
//        File fileOut = chooser.showSaveDialog(null);
//        System.out.println(fileOut.getAbsoluteFile());
//                
//        
//        
//        BufferedWriter writer = null;
//        try {
//            writer = new BufferedWriter(new FileWriter(fileOut));
//            writer.write(editor.getHtmlText());
//            writer.close();
//        } catch (IOException ex) {
//            Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                writer.close();
//            } catch (IOException ex) {
//                Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

    
    
    
   
    
    
   
    
    
    
    
    
    
    
    
    
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////
    
    
    
    
    
}

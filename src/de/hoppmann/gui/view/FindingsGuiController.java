/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.createReport.CreateReport;
import de.hoppmann.gui.messanges.CommonWarnings;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.operations.CreateTable;
import de.hoppmann.gui.modelsAndData.StoreFindings;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class FindingsGuiController implements Initializable {

    
    ///////////////////////////
    //////// Variables ////////
    ///////////////////////////
    
    // general variables
    private StoreFindings findings;
    
    
    
    // FXML Variables
    @FXML
    private Button closeButton;
    
    @FXML
    private TableView<TableData> findingsTable = new TableView<>();
    
    
    
    /////////////////////////
    //////// Methods ////////
    /////////////////////////

    
    
    
    
    

    //// FXML Methods
       private void saveReport(HTMLEditor editor) {
        
        FileChooser chooser = new FileChooser();
        File fileOut = chooser.showSaveDialog(null);
        System.out.println(fileOut.getAbsoluteFile());
                
        
        
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileOut));
            writer.write(editor.getHtmlText());
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

       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
    // Report button 
    @FXML
    private void showReportButtonAction (ActionEvent event) {
	
        // load HTML template
        CreateReport report = new CreateReport(findings);
        
        // open template
        report.openTemplate();
        
        // replace values
        report.replaceValues();
        
        // get modified template as initial text
        String initialText = report.getTemplate();
        
//        HTMLEditor htmlEditor = new HTMLEditor();
//        htmlEditor.setPrefHeight(245);
//        Scene scene = new Scene(htmlEditor);
//        Stage stage = new Stage();
//        htmlEditor.setHtmlText(initialText);
//        stage.setScene(scene);
//        stage.show();

        VBox root = new VBox();      
        root.setPadding(new Insets(8, 8, 8, 8));
        root.setSpacing(5);
        root.setAlignment(Pos.BOTTOM_LEFT);
        
        
        final HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setPrefHeight(800);
	
	
        htmlEditor.setHtmlText(initialText);       
 
        final TextArea htmlCode = new TextArea();
        htmlCode.setWrapText(true);
    
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("noborder-scroll-pane");
        scrollPane.setContent(htmlCode);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(180);
 
        Button showHTMLButton = new Button("Produce HTML Code");
        showHTMLButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent arg0) {
                htmlCode.setText(htmlEditor.getHtmlText());
            }
        });
        
        Button saveFindingButton = new Button("Save");
        root.setAlignment(Pos.CENTER_LEFT);
        saveFindingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveReport(htmlEditor);
            }
        });
        
        
        root.getChildren().addAll(htmlEditor, saveFindingButton, showHTMLButton, scrollPane);
        Scene scene = new Scene(root);
        scene.setRoot(root);
 
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        
        
        
//        
//	String INITIAL_TEXT = "Lorem ipsum dolor sit "
//            + "amet, consectetur adipiscing elit. Nam tortor felis, pulvinar "
//            + "aliquam sagittis gravida eu dolor. Etiam sit amet ipsum "
//            + "sem.";
	
	
//	try {
//	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HTMLReport.fxml"));
//	    
//	    
//	    
//	    
//	    // create new window
//	    Parent root = fxmlLoader.load();
//	    Stage stage = new Stage();
//	    stage.setTitle("Report");
//	    stage.setAlwaysOnTop(true);
//	    stage.setScene(new Scene(root));
//	   
//	    HTMLReportController controller = fxmlLoader.getController();
//
//	    System.out.println(INITIAL_TEXT);
//	    controller.init(INITIAL_TEXT);
//	    
//	    
//	    // open window
//	    stage.show();
//	} catch (IOException ex) {
//	    Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
//	}
  
	    
//	    HTMLEditor htmlEditor = new HTMLEditor();
//	    htmlEditor.setPrefHeight(245);
//	    Scene scene = new Scene(htmlEditor);
//	    Stage stage = new Stage();
//	    htmlEditor.setHtmlText(loadHtmlTemplate());
//	    stage.setScene(scene);
//	    stage.show();
//	    
//	    
	    
	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void checkDbButtonAction(ActionEvent event) {
	
	
	try {
	    // open database window
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CheckDB.fxml"));
	    
	    

	    // create new window
	    Parent root = fxmlLoader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Check databse");
	    stage.setAlwaysOnTop(true);
	    stage.setScene(new Scene(root));


	    // get controller
	    CheckDBController dbController = fxmlLoader.getController();
	    dbController.init(findings);

	    // show view
	    stage.showAndWait();
	    	    



	    
	    
	    
	} catch (IOException ex) {
	    Logger.getLogger(FindingsGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	
	
	
	
	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //// print report
    @FXML
    private void printReport (ActionEvent event) {
	
//	new Test(primaryStage, closeButton);
	
	
	
	
    }
    
    
    
    
    
    
    
    
    
    
    //// show report window
    @FXML
    private void showReportAction (ActionEvent event) {
	
	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void closeButtonAction (ActionEvent event) {
	
	Stage stage = (Stage) closeButton.getScene().getWindow();
	stage.close();
    }


    
    @FXML
    private void selectColumnsButton(ActionEvent event) {
	
	
	
	
	
	    
	try {
	    // open new window to show findings chosen so far
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SelectColumnsGui.fxml"));
	    
	    //// create controller for passing variables
	    SelectColumnsGuiController controller = new SelectColumnsGuiController(findings);
	    
	    
	    // create new window
	    fxmlLoader.setController(controller);
	    Parent root = fxmlLoader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Slect columns for findings");
	    stage.setScene(new Scene(root));
	    stage.initModality(Modality.APPLICATION_MODAL);
	    stage.show();
	    
	    
	} catch (IOException ex) {
	    Logger.getLogger(FindingsGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}


	
	
    }
    
    
    
    
    
    
    
    //// remove variants that got unselected
    @FXML
    private void removeButtonAction (ActionEvent event) {

	// remove lines that got unmarked
	for (Iterator<TableData> iter = findings.getStoredData().listIterator(); iter.hasNext();){
	    TableData entry = iter.next();
	    if (entry.isCausal() == false) {
		iter.remove();
	    }
	}
	
	
	// recreate table
	CreateTable createTable = new CreateTable(findingsTable);
	createTable.prepareTable(findings.getHeaderList());
	createTable.fillTable(findings.getStoredData());

    }
    
    
    
    
    
    
    //// general methods
    
    public void init(StoreFindings findings){

	// retrieve variables
	this.findings = findings;
	
	// set table options
	findingsTable.setEditable(true);
	// check if any findings are stored else close window
	if (findings == null) {
	    new CommonWarnings().noDataAvailable();
	    closeButtonAction(new ActionEvent());
	} else {
	    
	    // create Table	
	    CreateTable createTable = new CreateTable(findingsTable);
	    createTable.prepareTable(findings.getHeaderList());
	    createTable.fillTable(findings.getStoredData());

	}
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    /////////////////////////////////
    //////// Getter / Setter ////////
    /////////////////////////////////

    
    
    
}

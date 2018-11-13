/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.mainView;

import de.hoppmann.database.userDB.ConnectSQLite;
import de.hoppmann.gui.modelsAndData.FindingsRepository;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.gui.oldView.MainGuiController;
import de.hoppmann.gui.view.reportView.ReportViewerController;
import de.hoppmann.gui.view.selectColumnView.SelectColumnsGuiController;
import de.hoppmann.gui.view.userDbView.MainViewUserDbController;
import de.hoppmann.operations.CreateTable;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private FindingsRepository findings;
    
    
    
    
    
    
    
    
    @FXML
    private void refreshButtonAciton(ActionEvent event){
        refreshTable();
    }
    
    
    
    
    
    
    
    @FXML
    private void columnsButtonAction(ActionEvent event){
        
        if (findings == null) {
	    infoLabel.setText("Needs findings to open window.");
	    return;
	}
	
        
        
	
	try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/hoppmann/gui/view/selectColumnView/SelectColumnsGui.fxml"));
	    
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
	    Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    
    
    
    
    
    
    
    
    @FXML
    private void reportButtonAction(ActionEvent event){
        
        
        try {
	    // do nothing if findings == null
	    if (findings == null) {
		return;
	    }

	    // prepare new page
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/hoppmann/gui/view/reportView/ReportViewer.fxml"));

	    // create new window
	    Parent root = fxmlLoader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Report");
	    stage.setScene(new Scene(root));

	    // get controller of new window and pass findings list
	    ReportViewerController controller = fxmlLoader.getController();
	    controller.init(findings);

	    // show window
	    stage.show();

	} catch (IOException ex) {
	    Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}

    }
    
    
    
    
    
    
    
    
    @FXML
    private void databaseButtonAction(ActionEvent event){
        
        try {
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/hoppmann/gui/view/userDbView/MainViewUserDb.fxml"));


	    Parent root = fxmlLoader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Check database");
	    stage.setScene(new Scene(root));
	    stage.initModality(Modality.APPLICATION_MODAL);


    	    MainViewUserDbController controller = fxmlLoader.getController();
	    controller.init(new ConnectSQLite(), findings);

	    stage.show();

	    
	} catch (IOException ex) {
	    Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}
        
    }
    
    
    
    
    
    
    
    private void refreshTable() {
        
        if (findings != null) {

            // remove lines that got unmarked
            for (Iterator<TableData> iter = findings.getStoredData().listIterator(); iter.hasNext();) {
                TableData entry = iter.next();
                if (entry.isCausal() == false) {
                    iter.remove();
                }
            }

            findingsTable.setEditable(true);

            createTable();
        }
    }
    
    
    
    
    
    
    
    private void createTable() {
        CreateTable create = new CreateTable(findingsTable);
        create.prepareTable();
        create.fillHeader(findings.getHeaderList());
        create.fillTable(findings.getStoredData());

    }
    
    
    
    
    
    
    
    
    
    
    
    public void inject (MainViewController mainViewController, Label infoLabel){
        this.mainViewController = mainViewController;
        this.infoLabel = infoLabel;
    }
    
    
    
    
    public void init(FindingsRepository findings) {
        this.findings = findings;
        refreshTable();
    }
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.mainView;

import de.hoppmann.config.Config;
import de.hoppmann.gui.modelsAndData.FindingsRepository;
import de.hoppmann.gui.modelsAndData.InputRepository;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.openFile.CreateTable;
import de.hoppmann.openFile.LoadInputFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class DataTabViewController implements Initializable {

    @FXML private AnchorPane dataTabView;
    @FXML private TableView inputTable = new TableView();
    private MainViewController mainViewController;
    private Label infoLabel;
    private Label curWorkingFileLabel;
    private Config config = Config.getInstance();
    private LoadInputFile loadInput;
    private InputRepository inputRepository;
    private FindingsRepository findings = null;
    
    
    
    
    
    
    
    @FXML
    private void testButtonAction (ActionEvent e) {

        infoLabel.setText("NOTHING TO TEST");
        
        
        
    }
    
    
    
    
    
    
    
     
    
    @FXML
    private void browsButtonAction (ActionEvent event) {

        
        TableData curLine = (TableData) inputTable.getSelectionModel().getSelectedItem();
        
        if (curLine != null) {

            try {

                FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/de/hoppmann/gui/view/mainView/ChooseBrowserView.fxml"));
                Parent root = fXMLLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Open in Browser");
                stage.setScene(new Scene(root));

                ChooseBrowserViewController brwoserController = fXMLLoader.getController();
                brwoserController.init(curLine, inputRepository, infoLabel, inputTable);

                stage.show();

            } catch (IOException ex) {
                Logger.getLogger(DataTabViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            infoLabel.setText("No line chosen. Choose line first.");
        }
    }
    
    
    
    
    
    
    
    
    
    @FXML
    private void openButtonAction(ActionEvent event) {
        
        
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open input file.");
        
        if (new File(config.getInputPath()).exists()){
            chooser.setInitialDirectory(new File(config.getInputPath()));
        } else {
            chooser.setInitialDirectory(null);
        }

        File inputFile = chooser.showOpenDialog(new Stage());

        if (inputFile != null && inputFile.exists()){
	    inputRepository = new InputRepository();
            config.setInputPath(inputFile.getParent());
            loadInput = new LoadInputFile();
            loadInput.openFile(inputFile,inputRepository);
	    loadInput.catagorize(inputRepository);
            
	    curWorkingFileLabel.setText(inputFile.getName());
            
            createTable();
            
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    protected void createTable() {
        if (inputRepository != null){
            CreateTable table = new CreateTable(inputTable);
            table.prepareTable();
            table.fillHeader(inputRepository.getHeader());
            table.fillTable(inputRepository.getRowData());
//	    tableFilter = new TableFilter(inputTable);
        }
    }

    
    
    
    
    
        
    
    
    
    @FXML
    private void clearButtonAction(ActionEvent event){
        findings = null;
        inputRepository = null;
        inputTable.getColumns().clear();
        inputTable.getItems().clear();
        infoLabel.setText("Entries cleared.");
    }
    
    
    
    
    
    
    
    
    protected void storeFindings() {
        
        
        if (inputRepository != null) {
            
            if (findings == null){
                findings = new FindingsRepository(inputRepository.getHeader());
            }
            
            findings.storeFindings(inputRepository);
            infoLabel.setText("Findings saved.");
        }
        
                
    }
    
    
    
    
    
    
    public void inject (MainViewController mainViewController, Label infoLabel, Label curWorkingFileLabel) {
        this.mainViewController = mainViewController;
        this.infoLabel = infoLabel;
	this.curWorkingFileLabel = curWorkingFileLabel;
    }

    public FindingsRepository getFindings() {
        return findings;
    }

    public TableView getInputTable() {
        return inputTable;
    }

    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	
	



    }    
    
    
    
    
}

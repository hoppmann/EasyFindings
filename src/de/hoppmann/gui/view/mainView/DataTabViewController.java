/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.mainView;

import de.hoppmann.config.Config;
import de.hoppmann.gui.modelsAndData.FindingsRepository;
import de.hoppmann.gui.modelsAndData.InputRepository;
import de.hoppmann.openFile.CatagorizeAcmg;
import de.hoppmann.openFile.CreateTable;
import de.hoppmann.openFile.ICatagorize;
import de.hoppmann.openFile.LoadInputFile;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private InputRepository input;
    private FindingsRepository findings = null;
    private ICatagorize catagorize;
    
    
    
    
    
    @FXML
    private void testButtonAction (ActionEvent event) {
        System.out.println("NOTHING TO TEST");
        infoLabel.setText("NOTHING TO TEST");
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
	    input = new InputRepository();
            config.setInputPath(inputFile.getParent());
            loadInput = new LoadInputFile();
            loadInput.openFile(inputFile,input);
	    loadInput.catagorize(new CatagorizeAcmg(), input);
            
//            System.out.println(curWorkingFileLabel);
	    curWorkingFileLabel.setText(inputFile.getName());
            
            createTable();
            
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    protected void createTable() {
        if (input != null){
            CreateTable table = new CreateTable(inputTable);
            table.prepareTable();
            table.fillHeader(input.getHeader());
            table.fillTable(input.getRowData());
        }
    }

    
    
    
    
    
        
    
    
    
    @FXML
    private void clearButtonAction(ActionEvent event){
        findings = null;
        input = null;
        inputTable.getColumns().clear();
        inputTable.getItems().clear();
        infoLabel.setText("Entries cleared.");
    }
    
    
    
    
    
    
    
    
    protected void storeFindings() {
        
        
        if (input != null) {
            
            if (findings == null){
                findings = new FindingsRepository(input.getHeader());
            }
            
            findings.storeFindings(input);
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
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        inputTable = new TableView();

    }    
    
    
    
    
}

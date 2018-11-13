/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.mainView;

import de.hoppmann.config.Config;
import de.hoppmann.gui.modelsAndData.InputRepository;
import de.hoppmann.operations.CreateTable;
import de.hoppmann.operations.LoadInputFile;
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
    private Config config = Config.getInstance();
    private LoadInputFile loadInput;
    private InputRepository inputRepo;
    
    
    
    
    
    @FXML
    private void openButtonAction(ActionEvent event) {
        
        inputRepo = new InputRepository();
        
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open input file.");
        
        if (new File(config.getInputPath()).exists()){
            chooser.setInitialDirectory(new File(config.getInputPath()));
        } else {
            chooser.setInitialDirectory(null);
        }

        File inputFile = chooser.showOpenDialog(new Stage());

        
        if (inputFile != null && inputFile.exists()){
            config.setInputPath(inputFile.getParent());
            loadInput = new LoadInputFile();
            loadInput.openFile(inputFile,inputRepo);
            String warning = loadInput.catagorize(inputRepo);
            
            if(warning != "") {
                infoLabel.setText(warning);
                return;
            }
            
            
            createTable();
            
            infoLabel.setText(inputFile.getName() + " read in.");
            
        }
        
    }
    
    
        private void createTable(){
        CreateTable table = new CreateTable(inputTable);
        table.prepareTable(inputRepo.getHeader());
        table.fillTable(inputRepo.getRowData());

    }

    
    
    
    
    
        
    
    
    
    @FXML
    private void clearButtonAction(ActionEvent event){
        
        // reset objects
//	findings = null;
        loadInput = null;
	inputTable.getColumns().clear();
	inputTable.getItems().clear();
	infoLabel.setText("Entries cleared.");

        
        
    }
    
    
    
    
    
    @FXML
    private void testButtonAction (ActionEvent event) {
        
    }
    
    
    
    public void inject (MainViewController mainViewController, Label infoLabel) {
        this.mainViewController = mainViewController;
        this.infoLabel = infoLabel;
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        inputTable = new TableView();

    }    
    
}

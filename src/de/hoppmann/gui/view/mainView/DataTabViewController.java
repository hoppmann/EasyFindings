/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.mainView;

import de.hoppmann.config.Config;
import de.hoppmann.database.geneInfoDB.Hg19TableModel;
import de.hoppmann.database.geneInfoDB.Hg19TableRepository;
import de.hoppmann.gui.modelsAndData.FindingsRepository;
import de.hoppmann.gui.modelsAndData.InputRepository;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.openFile.CatagorizeAcmg;
import de.hoppmann.openFile.CreateTable;
import de.hoppmann.openFile.ICatagorize;
import de.hoppmann.openFile.LoadInputFile;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private InputRepository inputRepository;
    private FindingsRepository findings = null;
    private ICatagorize catagorize;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void testButtonAction (ActionEvent event) {

//	TableData curLine = (TableData) inputTable.getSelectionModel().getSelectedItem();
//
//	int geneColIndex = inputRepository.getHeader().indexOf(config.getGeneCol());
//	String curGene = curLine.getEntry(geneColIndex);
//	
//	
//	
//	Hg19TableRepository hg19Repo = new Hg19TableRepository();
//	Hg19TableModel hg19Data = new Hg19TableModel(curGene);
//	
//	hg19Repo.queryForGene(hg19Data);
//	System.out.println(hg19Data.getGeneMim());
	
	System.out.println("HERE");
	
	try {
	    Desktop desktop = Desktop.getDesktop();
//	    URI uri = new URI("http://www.google.de");
	    desktop.browse(new URI("http://www.google.de"));

	} catch (Exception ex) {
	    Logger.getLogger(DataTabViewController.class.getName()).log(Level.SEVERE, null, ex);
	}
	
//	
//	try {
//	    Desktop.getDesktop().browse(new URI("https:://google.de"));
//	} catch (URISyntaxException ex) {
//	    Logger.getLogger(DataTabViewController.class.getName()).log(Level.SEVERE, null, ex);
//	} catch (IOException ex) {
//	    Logger.getLogger(DataTabViewController.class.getName()).log(Level.SEVERE, null, ex);
//	}
	
	
	
	
	
	
//	System.out.println(input.getHeader().indexOf(config.getGeneCol()));
//	System.out.println(curLine.getInputLine());
	
	
	
	
	
		
//        System.out.println("NOTHING TO TEST");
//        infoLabel.setText("NOTHING TO TEST");
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
	    loadInput.catagorize(new CatagorizeAcmg(), inputRepository);
            
//            System.out.println(curWorkingFileLabel);
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
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	

	



    }    
    
    
    
    
}

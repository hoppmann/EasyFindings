/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.mainView;

import de.hoppmann.config.Config;
import de.hoppmann.database.geneInfoDB.Hg19TableModel;
import de.hoppmann.database.geneInfoDB.Hg19TableRepository;
import de.hoppmann.gui.modelsAndData.InputRepository;
import de.hoppmann.gui.modelsAndData.TableData;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class ChooseBrowserViewController implements Initializable {

    @FXML private ComboBox<String> geneMimCBox = new ComboBox<>();
    @FXML private ComboBox<String> dbSnpCBox = new ComboBox<>();
    @FXML private ComboBox<String> hgmdGeneNameCBox = new ComboBox<>();
    @FXML private ComboBox<String> refSeqGeneIdCBox = new ComboBox<>();
    
    
    private Config config = Config.getInstance();
    private TableData curLine;
    private InputRepository inputRepo;
    private Label infoLabel;
    private TableView<TableData> inputTable;
    
    private HashMap<String, String> geneMimMap = new HashMap<>();
    private HashMap<String, String> refSeqIdMap = new HashMap<>();
    
    
    
    
    
    
    
    @FXML
    private void refSeqButtonAction (ActionEvent event) {
        
        
        String refSeqURL = "https://www.ncbi.nlm.nih.gov/gene/";
        
        String refSeqGeneId = refSeqIdMap.get(refSeqGeneIdCBox.getValue());
        
        try {
            Desktop.getDesktop().browse(new URI(refSeqURL + refSeqGeneId));
        } catch (IOException ex) {
            Logger.getLogger(ChooseBrowserViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ChooseBrowserViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        closeButtonAction(new ActionEvent());
        
    }
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void hgmdButtonAction (ActionEvent event) {
        
        String hgmdURL = "https://portal.biobase-international.com/hgmd/pro/gene.php?gene=";
        
        String hgmdGeneName = hgmdGeneNameCBox.getValue();
        
        try {
            Desktop.getDesktop().browse(new URI(hgmdURL + hgmdGeneName));
        } catch (URISyntaxException ex) {
            Logger.getLogger(ChooseBrowserViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChooseBrowserViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        closeButtonAction(new ActionEvent());
        
    }
    
    
    
    
    
    
    
     
    @FXML
    private void omimButtonAction (ActionEvent event){
        
        String omimURL = "https://www.omim.org/entry/";
        
        String geneMimId = geneMimMap.get(geneMimCBox.getValue());
        
        try {
            Desktop.getDesktop().browse(new URI(omimURL + geneMimId));
        } catch (URISyntaxException ex) {
            Logger.getLogger(ChooseBrowserViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChooseBrowserViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        closeButtonAction(new ActionEvent());
    }
    
     
     
     
    
    
    @FXML
    private void dbSnpButtonAciton (ActionEvent event) {
        
        String rsId = dbSnpCBox.getValue();

        String dbSnpURL = "https://www.ncbi.nlm.nih.gov/snp/";
        try {
            Desktop.getDesktop().browse(new URI(dbSnpURL + rsId));
        } catch (URISyntaxException ex) {
            Logger.getLogger(DataTabViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DataTabViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        closeButtonAction(new ActionEvent());
    }
    
    
    
    
    
    
    @FXML
    private void closeButtonAction (ActionEvent event) {
        
        Stage stage = (Stage) dbSnpCBox.getScene().getWindow();
        stage.close();
        
    }
    
    
    
    @FXML
    private void refreshButtonAction(ActionEvent event) {
        curLine = inputTable.getSelectionModel().getSelectedItem();
        init(curLine);
        
    }
    
    
    
    
    
    
    /**
     * 
     * @param entry
     * @return 
     * Split on all possible deliminators
     */
    private ObservableList splitEntries(String entry){
        ObservableList<String> splitEntry = FXCollections.observableArrayList();
        
        for (String curSplit : entry.split(",|;")) {
            System.out.println(curSplit);
            if (curSplit != null && !curSplit.equals("")){
                splitEntry.add(curSplit);
            }
        }
//        splitEntry.addAll(entry.split(",|;"));
        
        return splitEntry;
        
    }
    
    
    
    
    
    
    
    
    /**
     * Fill comboboxes with choices from current line
     */
    private void fillBoxes (){
        
        // get indeces of interest
        int rsIdColIndex = inputRepo.getHeader().indexOf(config.getRsIdCol());
        int geneColIndex = inputRepo.getHeader().indexOf(config.getGeneCol());
        
        
        
        
        // get column entries
        Set<String> geneNames = new HashSet<>();
        geneNames.addAll(splitEntries(curLine.getEntry(geneColIndex)));
        
        String rsId = curLine.getEntry(rsIdColIndex);
        
        
//        List<String> geneMimList = new ArrayList<>();
        geneMimMap = new HashMap<>();
        refSeqIdMap = new HashMap<>();
        
        for (String curGene : geneNames){
            
            Hg19TableModel hg19Data = new Hg19TableModel(curGene);
            Hg19TableRepository hg19Repo = new Hg19TableRepository();
            hg19Repo.queryForGene(hg19Data);
            
            if (hg19Data.getGeneMim() != null){
                geneMimMap.put(curGene, hg19Data.getGeneMim());
            }
            
            if (hg19Data.getRefSeqId() != null) {
                refSeqIdMap.put(curGene, hg19Data.getRefSeqId());
            }
        }
        
        
        
        
        
        // fill boxes with choices
        
        geneMimCBox.getItems().setAll(geneMimMap.keySet());
        geneMimCBox.getSelectionModel().selectFirst();
        
        dbSnpCBox.getItems().setAll(splitEntries(rsId));
        dbSnpCBox.getSelectionModel().selectFirst();
        
        hgmdGeneNameCBox.getItems().setAll(geneNames);
        hgmdGeneNameCBox.getSelectionModel().selectFirst();
        
        
        refSeqGeneIdCBox.getItems().setAll(refSeqIdMap.keySet());
        refSeqGeneIdCBox.getSelectionModel().selectFirst();
        
        
        
        
    }
    
    
    
    
    public void init (TableData curLine, InputRepository inputRepo, Label infoLable, TableView<TableData> inputTable) {
        this.curLine = curLine;
        this.inputRepo = inputRepo;
        this.infoLabel = infoLable;
        this.inputTable = inputTable;
        
        fillBoxes();
    }
    
    
    private void init (TableData curLine){
        this.curLine = curLine;
        fillBoxes();
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dbSnpCBox.setEditable(true);
        geneMimCBox.setEditable(true);
        hgmdGeneNameCBox.setEditable(true);
        refSeqGeneIdCBox.setEditable(true);
        	
    }    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.createReport;

import de.hoppmann.config.Config;
import de.hoppmann.gui.modelsAndData.StoreFindings;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.gui.view.FindingsGuiController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author hoppmann
 */
public class CreateReport {
    
    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    private String template;
    private ReportDataModel model = new ReportDataModel();
    private StoreFindings findings;
    
    
    ////////////////////////////
    //////// costructor ////////
    ////////////////////////////

    public CreateReport(StoreFindings findings) {
        this.findings = findings;
        
    }

    
    
    
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    
    /////////////////////
    //// replaceValues values in template
    
    public void replaceValues() {

        model = new FillDataDummy().fillModel();
        
        
        // add sender address
        replace(model.getSenderPH(), model.getSender("MVZ"));
        
        
        // add current date
        replace(model.getDatePh(), "Freiburg, " + model.getDate());
                
        
        
        
        // add receiver address
        replace(model.getReveiverHeaderPH(), model.getReceiverHeader("MVZ"));
        replace(model.getReceiverNamePH(), model.getReceiverName());
        replace(model.getReceiverStreetPH(), model.getReceiverStreet());
        replace(model.getReceiverCityPH(), model.getReceiverCity());
        
        
        
        // add patient box
        replace(model.getDiagMethodPH(), model.getDiagMethod());
        replace(model.getPatientPH(), model.getPatient());
        replace(model.getMaterialPH(), model.getMaterial());
        replace(model.getIndicationPH(), model.getIndication());
                
        // add method box
        replace(model.getSeqMethodPH(), model.getSeqMehtod("NGS_KiKli"));
        
        
        // add causal genes
        prepareCausalGenes();
        
    }
    
    
    
    //// add causal genes to document
    private void prepareCausalGenes() {
        
        /* 
        for each gene in findings
            get gene name
            get cDNA nomenclature
            get impact 
            get rsID
        */
        
        System.out.println(findings.getStoredData().size());
        
        
        
        
        
        
        
        
//        for (TableData curFinding : findings.getStoredData()){
//            
//            String curGene = curFinding.getCatagory();
//            System.out.println(curGene);
//        }
//        System.out.println(findings.getStoredData().size());
        
        
    }
    
    
    
    
    
    
    
    
    
    

//// replace value and hand back template
    private void replace(String placeholder, String value) {
        template = template.replace(placeholder, value);
    }
    
    
    //////////////////
    //// open template
    
    public void openTemplate() {
        
        // check if path to template exists in config else show open dialoge
        File templateFile = null;
        Config config = new Config();
        
        if (config.getHtmlTemplate() == null || ! new File(config.getHtmlTemplate()).exists()) {
            templateFile = chooseTemplate();
            
            if (templateFile != null && templateFile.exists()) {
                config.setHtmlTemplate(templateFile.getAbsolutePath());
            } 
        
        } else {
            templateFile = new File(config.getHtmlTemplate());
        }
        

        ////// read in template file
        template = readInTemplate(templateFile);
                
    }
    
    
    
    
    
    
    //////////////////////
    //// read in HTML template
    public String readInTemplate (File file){
        
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException ex) {
            Logger.getLogger(CreateReport.class.getName()).log(Level.SEVERE, null, ex);
        }            
            
        
        return content;
        
    }
    



    
    ////////////////////
    //// choose Template
    public File chooseTemplate() {

        FileChooser chooser = new  FileChooser();
        chooser.setTitle("Choose HTML template.");
        File templateFile = chooser.showOpenDialog(new Stage());
        
        
        return templateFile;
    }
    
    
    ///////////////////////////////
    //////// getter/setter ////////
    ///////////////////////////////

    public String getTemplate() {
        return template;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}

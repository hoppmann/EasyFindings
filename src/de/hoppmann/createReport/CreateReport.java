/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.createReport;

import de.hoppmann.config.Config;
import de.hoppmann.gui.modelsAndData.Catagory;
import de.hoppmann.gui.modelsAndData.StoreFindings;
import de.hoppmann.gui.modelsAndData.TableData;
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
    private String report;
    private String reportTemplate;
    private ReportDataModel dataModel;
    private StoreFindings findings;
    private final Config config = Config.getInstance();
    
    
    ////////////////////////////
    //////// costructor ////////
    ////////////////////////////

    public CreateReport(StoreFindings findings, ReportDataModel dataModel) {
        this.findings = findings;
	this.dataModel = dataModel;
        
    }

    
    
    
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    
    /////////////////////
    //// replaceValues values in template
    
    public void replaceValues() {

	// reset report to template
	report = reportTemplate;
        
        // add sender address
        replace(dataModel.getSenderPH(), dataModel.getSender("MVZ"));
        
        
        // add current date
        replace(dataModel.getDatePh(), "Freiburg, " + dataModel.getDate());
                
        
        
        
        // add receiver address
        replace(dataModel.getReveiverHeaderPH(), dataModel.getReceiverHeader("MVZ"));
	replace(dataModel.getReceiverNamePH(), dataModel.getReceiverName());
        replace(dataModel.getReceiverStreetPH(), dataModel.getReceiverStreet());
        replace(dataModel.getReceiverCityPH(), dataModel.getReceiverCity());
        
        
        
        // add patient box
        replace(dataModel.getDiagMethodPH(), dataModel.getDiagMethod());
        replace(dataModel.getPatientPH(), dataModel.getPatient());
        replace(dataModel.getMaterialPH(), dataModel.getMaterial());
        replace(dataModel.getIndicationPH(), dataModel.getIndication());
                
        // add method box
        replace(dataModel.getSeqMethodPH(), dataModel.getSeqMehtod("NGS_KiKli"));
        
	
	
	// add causal genes
	replace(dataModel.getFindingsPH(), dataModel.getFindingsGeneTable());
	
	
    }
    
    
    
    //// add causal genes to document
    public void prepareFindingGenesTable() {

	String htmlGeneTable = null;

	/*
	if causal variants
	    -> positive findings
	else 
	    -> negative findings
	 */
	boolean positiveFinings = false;
	for (TableData curFinding : findings.getStoredData()) {

	    if (curFinding.getCatagory().equals(Catagory.getPathoCode())
		    || curFinding.getCatagory().equals(Catagory.getProbPathoCode())) {
		positiveFinings = true;
	    }

	}

	// 
	if (positiveFinings == true) {
	    PreparePositiveFindingsMethods positivFindings = new PreparePositiveFindingsMethods(findings);
	    htmlGeneTable = positivFindings.getHtmlGeneTable();

	} else {

	    // add negative finding
	    PrepareNegativeFindings negFindings = new PrepareNegativeFindings(findings);
	    htmlGeneTable = negFindings.getHtmlTable();
	}
	
	
	// store table in data model
	dataModel.setFindingsGeneTable(htmlGeneTable);

    }

    
    
    
    
    
    
    
    
    
    
    

//// replace value and hand back template
    private void replace(String placeholder, String value) {
        report = report.replace(placeholder, value);
    }
    
    
    //////////////////
    //// open template
    
    public void openTemplate() {
        
        // check if path to template exists in config else show open dialoge
        File templateFile = null;
        
        if (config.getHtmlTemplate() == null || ! new File(config.getHtmlTemplate()).exists()) {
            templateFile = chooseTemplate();
            
            if (templateFile != null && templateFile.exists()) {
                config.setHtmlTemplate(templateFile.getAbsolutePath());
            } 
        
        } else {
            templateFile = new File(config.getHtmlTemplate());
        }
        

        ////// read in template file
        reportTemplate = readInTemplate(templateFile);
                
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

    public String getReport() {
        return report;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}

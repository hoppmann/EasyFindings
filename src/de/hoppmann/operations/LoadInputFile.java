/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.operations;

import de.hoppmann.config.Config;
import de.hoppmann.gui.messanges.CommonErrors;
import de.hoppmann.gui.modelsAndData.TableData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author hoppmann
 */
public class LoadInputFile {

    


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    private final TableView<TableData> tableView;
    private final List<String> header = new ArrayList<>();
    private File file;
    private List<TableData> rowData;
    
    
    
    
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    
    public LoadInputFile(TableView inputTable) {
        
        // retrieve variables
        this.tableView = inputTable;
        
        
        // choose input file
        chooseFile();
        
        // check if file chosen. If so continue loading and prepareing table
        if (file != null) {
            openFile();
	    createTable();
        }
        
    }
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    
    
    //// choose file to open
    private void chooseFile() {
        
        // create chooser to choose file
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open input file");
	
	// if config path exists take config path else new path
	Config config = new Config();
	if (new File(config.getInputPath()).exists()){
	    chooser.setInitialDirectory(new File(config.getInputPath()));
	} else {
	    chooser.setInitialDirectory(null);
	}
        file = chooser.showOpenDialog(new Stage());
	
	// store last used path
	if (file.exists()) {
	    config.setInputPath(file.getParent());
	}
        
    }
    
    
    
    
    
    //// read in file
    private void openFile() {

        // prepare variables
        String line;
        rowData = new ArrayList<TableData>();
        try {

            // read in file
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
		
		// read in line split and save in row data object
		rowData.add(new TableData(new LinkedList<>(Arrays.asList(line.split("\t")))));
            }
            br.close();
        } catch (IOException iOException) {
	    new CommonErrors().cantOpen(file.toString());
        }
	
	// save header line seperately
	header.addAll(rowData.remove(0).getInputLine());
	

    }

    
    //// create table
    private void createTable() {
	CreateTable table = new CreateTable(tableView);
	table.prepareTable(header);
	table.fillTable(rowData);
    }
    
    

    
    

    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////

    public List<TableData> getRowData() {
	return rowData;
    }

    public List<String> getHeader() {
	return header;
    }

    

}

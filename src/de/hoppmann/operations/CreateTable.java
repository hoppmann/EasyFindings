/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.operations;

import de.hoppmann.gui.modelsAndData.Catagory;
import de.hoppmann.gui.modelsAndData.TableData;
import java.util.List;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.util.Callback;

/**
 *
 * @author hoppmann
 */
public class CreateTable {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    private final TableView<TableData> tableView;
    private TableColumn<TableData, Boolean> causalCol;
//    private TableColumn<TableData, Catagory> catagoryCol;
    private TableColumn<TableData, String> catagoryCol;
	
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////

    public CreateTable(TableView<TableData> tableView) {
	this.tableView = tableView;
    }


    
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
       //// prepare table

    public void prepareTable(List<String> header) {
        
	// clear old header
	tableView.getColumns().clear();
	
	
	
	//////////////////////////////////
	//////// add checkbox to tableView
	causalCol = new TableColumn<>("causal?");
	
	
	//  define cell value factory
	causalCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableData, Boolean>, ObservableValue<Boolean>>() {
	    
	    @Override
	    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<TableData, Boolean> param) {
		TableData tableData = param.getValue();
		SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(tableData.isCausal());
		
		
		
		// add listener to notice when checkbox is marked
		booleanProp.addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			tableData.setCausal(newValue);
		    }
		});
		
		
		
		return booleanProp;
	    }
	});
	
	// define cell factory
	causalCol.setCellFactory(new Callback<TableColumn<TableData, Boolean>, TableCell<TableData, Boolean>>() {
	    @Override
	    public TableCell<TableData, Boolean> call(TableColumn<TableData, Boolean> param) {
		CheckBoxTableCell<TableData, Boolean> cell = new CheckBoxTableCell<>();
		cell.setAlignment(Pos.CENTER);
		return cell;
	    }
	});
	causalCol.setEditable(true);
	
	// add column to table
	tableView.getColumns().add(causalCol);
		

	
	
	
	

	
	
	
	
	
	

	/////////////////////////////////////
	//////// add catagory dorpdown choice
	catagoryCol = new TableColumn<>("catagory");
	

	// define cell value factory
	catagoryCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableData, String>, ObservableValue<String>>() {
	    @Override
	    public ObservableValue<String> call(TableColumn.CellDataFeatures<TableData, String> param) {
		
		TableData tableData = param.getValue();
		
		// select catagory
		String catagoryString = tableData.getCatagory();
		String catagory = Catagory.getByCode(catagoryString);
		return new SimpleStringProperty(catagory);

	    }
	});
	
	
	// define cell factory
	ObservableList<String> catagoryList = FXCollections.observableArrayList();
	for (Catagory curCat : Catagory.values()) {
	    catagoryList.add(curCat.getCatagoryCode());
	}
	catagoryCol.setCellFactory(ComboBoxTableCell.forTableColumn(catagoryList));

	
	//// handle event of choosing a catagory
	catagoryCol.setOnEditCommit((event) -> {
	    TablePosition<TableData, String> pos = event.getTablePosition();
	    String newCatagory = event.getNewValue();
	    
	    int row = pos.getRow();
	    TableData tableData = event.getTableView().getItems().get(row);
	    tableData.setCatagory(newCatagory);
	    
	});
	
	
	// add column to table
	tableView.getColumns().add(catagoryCol);
	
	
	
	
	
	
	
	
	
	
	
	

	///////////////////////////////////
        //////// add header from input file
        for (int i = 0; i < header.size(); i++) {
            
	    // create final integer later needed
	    final int finalIdx = i;
	    
	    // create new table column and load header
	    TableColumn<TableData, String> column = new TableColumn<>(header.get(i));
	    column.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getEntry(finalIdx)));
	    
	    // add column to table
            tableView.getColumns().add(column);
        }
        
    }
	
	
    
    
    
    //// fill table
    public void fillTable (List<TableData> lines) {
        
	// clear old entries
	tableView.getItems().clear();
	
	lines.forEach(curLine -> {
	    tableView.getItems().add(curLine);
	});
        
    }
    
    
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////










}

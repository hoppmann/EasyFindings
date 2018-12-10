/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.openFile;

import de.hoppmann.gui.modelsAndData.Catagory;
import de.hoppmann.gui.modelsAndData.TableData;
import java.util.List;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
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

    public void prepareTable() {
        
	// clear old header
	tableView.getColumns().clear();
	tableView.getColumns().add(prepareCausalCol());	
	tableView.getColumns().add(prepareCatagoryCol());
	tableView.getColumns().add(prepareAcmgEvidenceCol());
	

	// creates conflict with copytToClipboard
//	tableView.getColumns().add(prepareAcmgCol());
	

	
	tableView.getSelectionModel().setCellSelectionEnabled(true);
	tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

	
        tableView.setEditable(true);
	
	final KeyCodeCombination keyCodeCopy =  new KeyCodeCombination(KeyCode.C, KeyCodeCombination.CONTROL_ANY);
	tableView.setOnKeyPressed((event) -> {
	    if (keyCodeCopy.match(event)){
		copySelectionToClipboard(tableView);
	    }
	});
	
		
	
    }
	
	
    
    
    
    public void copySelectionToClipboard(final TableView<?> table) {
	
	ObservableList<TablePosition> posList = table.getSelectionModel().getSelectedCells();
	StringBuilder clipboardString = new StringBuilder();
	int oldRow = -1;
	
	for (TablePosition p : posList){
	    int row = p.getRow();
	    int col = p.getColumn();
	    
	    Object cell = table.getColumns().get(col).getCellData(row);
	    
	    
	    if (cell == null) 
		cell = "";
	    if (oldRow == row)
		clipboardString.append('\t');
	    else if (oldRow != -1)
		clipboardString.append('\n');
	    
	    clipboardString.append(cell);
	    
	    oldRow = row;
	}
	

	final ClipboardContent clipboardContent = new ClipboardContent();
	clipboardContent.putString(clipboardString.toString());
	Clipboard.getSystemClipboard().setContent(clipboardContent);
	
	
    }  
    
    
    
    
    
    
    
    
    
    
    
    
    
    private TableColumn<TableData, String> prepareAcmgCol(){
	
	TableColumn<TableData, String> acmgCol = new TableColumn<>("ACMG");
	acmgCol.getColumns().addAll(prepareCatagoryCol(), prepareAcmgEvidenceCol());
	return acmgCol;
	
    }
    
    
    
    
    
    
    // add ACMG criteria list
    
    private TableColumn<TableData, String> prepareAcmgEvidenceCol() {
	
	TableColumn<TableData, String> acmgCol = new TableColumn<>("Evidence");

	
	
	acmgCol.setCellValueFactory((TableColumn.CellDataFeatures<TableData, String> param) -> {
	    
	    TableData tableData = param.getValue();
	    SimpleObjectProperty<String> stringProp = new SimpleObjectProperty<String>(tableData.getCatagoryEvidence());
	    
	    
	    stringProp.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
		tableData.setCatagoryEvidence(newValue);
	    });
	    
	    return stringProp;
	});
	
	
	
	
	
	acmgCol.setCellFactory(TextFieldTableCell.forTableColumn());
	
	return acmgCol;
    }
    
    
    
    
    
    
    
    /////////////////////////////////////////
    //////// prepare catagory dorpdown choice

    private TableColumn<TableData, Catagory> prepareCatagoryCol() {
        
        
        
        TableColumn<TableData, Catagory> catagoryCol = new TableColumn<>("catagory");
	


	// define cell value factory
	catagoryCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TableData, Catagory>, ObservableValue<Catagory>>() {
	    @Override
	    public ObservableValue<Catagory> call(TableColumn.CellDataFeatures<TableData, Catagory> param) {
		
		TableData tableData = param.getValue();
		String catagoryString = tableData.getCatagory();
		Catagory catagory = Catagory.getCatagoryByCode(catagoryString);
		return new SimpleObjectProperty<>(catagory);
	    }
	});
	

        
        
	// define cell factory
	ObservableList<Catagory> catagoryList = FXCollections.observableArrayList(Catagory.values());
	catagoryCol.setCellFactory(ComboBoxTableCell.forTableColumn(catagoryList));
	
        
        
	
	//  handle event of choosing a catagory
	catagoryCol.setOnEditCommit((event) -> {
	    TablePosition<TableData, Catagory> pos = event.getTablePosition();
	    Catagory newCatagory = event.getNewValue();
	    
	    int row = pos.getRow();
	    TableData tableData = event.getTableView().getItems().get(row);
	    tableData.setCatagory(newCatagory.getCatagoryCode());
		    
	});
        
        return catagoryCol;
                
        
    }
    
    
    
    
    
    
    
    
    
    
    
    //////////////////////////////////
    //////// add checkbox to tableView

    private TableColumn<TableData, Boolean> prepareCausalCol() {
        

        TableColumn<TableData, Boolean>	causalCol = new TableColumn<>("causal?");
	
	
        
        
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
        
        
        
        
        return causalCol;
    }
    
    
    
    
    
    
    
    
    
    
    
    public void fillHeader(List<String> header) {
        
        
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
    
    
	
    
    

}

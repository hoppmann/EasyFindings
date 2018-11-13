/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.gui.modelsAndData;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public class InputRepository {

    private final List<String> header;
    private List<TableData> rowData;


    /**
     * Repository for data from the input file
     */
    
    public InputRepository() {
        this.header = new ArrayList<>();
        this.rowData = new ArrayList<>();
    }

    
    

    public void addHeader(String curHeader){
        header.add(curHeader);
    }
    
    public List<String> getHeader() {
        return header;
    }

    public List<TableData> getRowData() {
        return rowData;
    }

    public void setRowData(List<TableData> rowData) {
        this.rowData = rowData;
    }

    
    
    
    
    
    

    
    
    
    
    
    
    
    
    

}

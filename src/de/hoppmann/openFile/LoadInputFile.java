/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.openFile;

import de.hoppmann.config.Config;
import de.hoppmann.gui.messanges.CommonErrors;
import de.hoppmann.gui.modelsAndData.InputRepository;
import de.hoppmann.gui.modelsAndData.TableData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;


/**
 *
 * @author hoppmann
 */
public class LoadInputFile {

    


    private Config config = Config.getInstance();


    
    
    
    
    //// read in file
    public void openFile(File file, InputRepository inputRepository) {

        // prepare variables
        String line;

        try {
            // read in file
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
		
		// read in line split and save in row data object
		inputRepository.getRowData().add(new TableData(new LinkedList<>(Arrays.asList(line.split("\t")))));
            }
            br.close();
        } catch (IOException iOException) {
	    new CommonErrors().cantOpen(file.toString());
        }
	
	// save header line seperately
	inputRepository.getHeader().addAll(inputRepository.getRowData().remove(0).getInputLine());
    }

    
    
    
    
    
    public void catagorize(InputRepository input){
	CatagorizeAcmg catagorizeAcmg = new CatagorizeAcmg();
	catagorizeAcmg.createCatagories(input);
	
    }
    
    
    


}

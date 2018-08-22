/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.HTMLEditor;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class HTMLReportController implements Initializable {

    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    @FXML
    HTMLEditor editor = new HTMLEditor();
    
    
    
    private final String INITIAL_TEXT = "<html><body>Lorem ipsum dolor sit "
    + "amet, consectetur adipiscing elit. Nam tortor felis, pulvinar "
    + "in scelerisque cursus, pulvinar at ante. Nulla consequat"
    + "congue lectus in sodales. Nullam eu est a felis ornare "
    + "bibendum et nec tellus. Vivamus non metus tempus augue auctor "
    + "ornare. Duis pulvinar justo ac purus adipiscing pulvinar. "
    + "Integer congue faucibus dapibus. Integer id nisl ut elit "
    + "aliquam sagittis gravida eu dolor. Etiam sit amet ipsum "
    + "sem.</body></html>";

    
    
    
    
    
    
    
    
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    
    
    
    
    
    
    
    
    
    
    //// Open Button
    @FXML
    public void openButtonAction (ActionEvent event) {
    
	editor.setHtmlText(INITIAL_TEXT);
	

    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //// file reader
    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
         
        try {
 
            bufferedReader = new BufferedReader(new FileReader(file));
             
            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
 
        } catch (FileNotFoundException ex) {
//            Logger.getLogger(JavaFX_HTMLeditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
//            Logger.getLogger(JavaFX_HTMLeditor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
//                Logger.getLogger(JavaFX_HTMLeditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
         
        return stringBuffer.toString();
    }
    
    
    
    
    
    public void init(String text) {
	
	editor = new HTMLEditor();
	editor.setHtmlText(text);
		
	
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
	
	
	editor = new HTMLEditor();
	editor.setHtmlText(INITIAL_TEXT);
	
	System.out.println(editor.getHtmlText());
	// init editor
//	editor = new HTMLEditor();
	
	
    }    
    
}

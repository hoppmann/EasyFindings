/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.reportView;

import de.hoppmann.createReport.ReportRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class HtmlReportViewController implements Initializable {

    @FXML private AnchorPane htmlReportView;
    @FXML private TextArea htmlReportArea;
    private ReportRepository reportRepo;
    
    
    
    
    
    
    
    public void init(ReportRepository reportRepo) {
        this.reportRepo = reportRepo;
        System.out.println("got here");
                
        htmlReportArea.setText(reportRepo.getReport());
    }
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        htmlReportArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                reportRepo.setReport(newValue);
            }
        });
         

    }    
    
}

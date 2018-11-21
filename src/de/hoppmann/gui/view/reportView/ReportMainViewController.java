/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.reportView;

import de.hoppmann.createReport.ReportRepository;
import de.hoppmann.database.userDB.receiverDB.DbAddressRepository;
import de.hoppmann.gui.modelsAndData.FindingsRepository;
import de.hoppmann.gui.view.userDbView.AddressTabController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class ReportMainViewController implements Initializable {

    
    @FXML private BillingReportTabViewController billingReportTabViewController;
    @FXML private EntryTabViewController entryTabViewController;
    @FXML private HtmlReportTabViewController htmlReportTabViewController;
    @FXML private ReportTabViewController reportTabViewController;
    @FXML private AddressTabController addressTabController;
    @FXML private Label infoLabel;
    
    @FXML private Tab reportTab;
    @FXML private Tab htmlReportTab;
    @FXML private Tab billingReportTab;
    @FXML private Tab addressesTab;
    @FXML private Tab entryMaskTab;
    
    private ReportRepository reportRepo = new ReportRepository();
    
    
    
    
    
    
    
    @FXML
    private void closeButtonAction () {
        Stage stage = (Stage) infoLabel.getScene().getWindow();
        stage.close();

    }
    
    
    
    
    
    
    
    
    
    public boolean init(FindingsRepository findings) {
    
        
        if (findings == null){
            return false;
        }
        reportTabViewController.init(findings, reportRepo);
        
        return true;
    }
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {




        reportTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    reportTabViewController.loadReport();
                }
            }
        });
        
        
        
        
        
        
        // add tab change listener
        htmlReportTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    htmlReportTabViewController.init(reportRepo);
                }
            }
        });

        
        
        
        
        
        
        billingReportTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    billingReportTabViewController.init(reportRepo);
                }
            }
        });


        
        
        
        
        
        
        addressesTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    boolean isConnected = addressTabController.init(new DbAddressRepository(), infoLabel, reportRepo);
                    if (!isConnected) {
                        infoLabel.setText("WARNING: No DB connected!");
                    }
                }
            }
        });

        
        addressesTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue){
                    addressTabController.updateAddressInfoFromUI();
                }
            }
        });
        
        
        
        
        
        
        
        
        entryMaskTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    entryTabViewController.init(reportRepo);
                }
            }
        });
        
        
        
        


    }    
    
}

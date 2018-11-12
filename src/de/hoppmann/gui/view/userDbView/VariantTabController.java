/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.userDbView;

import de.hoppmann.database.userDB.snipletDB.DbGeneInfoRepository;
import de.hoppmann.database.userDB.snipletDB.DbVarianInfoRepository;
import de.hoppmann.database.userDB.snipletDB.VariantInfo;
import de.hoppmann.gui.modelsAndData.FindingsRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class VariantTabController implements Initializable {

    private FindingsRepository findings;
    private VariantInfo varInfo = new VariantInfo("");
    @FXML private MainViewUserDbController mainViewUserDbController;
    @FXML private AnchorPane variantTab;
    @FXML private FindingsTabController findingsTabController;
    @FXML private GeneInfoTabController geneInfoTabController;
    @FXML private VariantInfoTabController variantInfoTabController;
    @FXML private Tab findingsInfoTabView;
    @FXML private Tab variantInfoTabView;
    @FXML private Tab geneInfoTabView;
    
    
    
    
    
    
    /**
     * lower views need to be initialized thus this method hands the init down
     */
    public void init(FindingsRepository findings) {
	this.findings = findings;
	
	
	varInfo = new VariantInfo("");
	
	findingsTabController.init(findings);
	
	geneInfoTabController.init(varInfo);

	variantInfoTabController.init(varInfo);

	
    }
    
    
    
    
    
    public void injectMainController(MainViewUserDbController mainViewUserDbController) {
	this.mainViewUserDbController = mainViewUserDbController;
    }

    public MainViewUserDbController getMainViewUserDbController() {
	return mainViewUserDbController;
    }
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	findingsTabController.injectVariantTabController(this);
        geneInfoTabController.inject(this, findingsTabController, variantInfoTabController, new DbGeneInfoRepository());
	variantInfoTabController.inject(this, new DbVarianInfoRepository());
	
	
	
	
	
	
	findingsInfoTabView.selectedProperty().addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		
		if (newValue) {
		    findingsTabController.init(findings);
		}

	    }
	});
	
	
	
	
	
	
	
	// switching to the tab
	geneInfoTabView.selectedProperty().addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		if (newValue) {
		    geneInfoTabController.init(varInfo);
                    varInfo = geneInfoTabController.getVarInfo();
                    variantInfoTabController.init(varInfo);

		}
	    }
	});
	
	
	
	
	
	
	// switching from the tab get varInfo object
	geneInfoTabView.selectedProperty().addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		if (! newValue){
		    varInfo = geneInfoTabController.getVarInfo();
		}
	    }
	});
	
	
	
	
	
	
    }    



    


    
}

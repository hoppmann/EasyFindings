/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.operations.testmatthias.view;

import de.hoppmann.operations.testmatthias.dbaccess.GeneInfoRepository;
import de.hoppmann.operations.testmatthias.model.GeneInfo;

/**
 *
 * @author hoppmann
 */
public class GeneInfoViewController {

    public void startUp() {
	// zeige das Dialogfeld (nur View)
    }

    // -.......
    
    private void saveGeneButtonPressed() {
	// ... hole das GeneInfo-Objekt aus dem UI heraus
	GeneInfo gi = fetchGeneInfoFromUi();
	// und speichere in der DB...
	repository.saveGeneInfo(gi);
    }
    
    public void setGeneInfoRepository(GeneInfoRepository repository) {
	this.repository = repository;
    }

    private GeneInfoRepository repository;

    private GeneInfo fetchGeneInfoFromUi() {
	GeneInfo gi = new GeneInfo();
//	gi.setGeneName(comboGeneName.getValue());
//	gi.setSniplet(textBoxSniplet.getText());
	return gi;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.operations.testmatthias.dbaccess;

import de.hoppmann.operations.testmatthias.model.GeneInfo;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public class DbGeneInfoRepository 
implements GeneInfoRepository {

    @Override
    public List<GeneInfo> loadAllGeneInfos() {
	// führe eine Select auf alle Gene aus,
	// und packe die Spalten in die GeneInfo Tabelle
	return new LinkedList<>();
    }

    @Override
    public void saveGeneInfo(GeneInfo geneInfo) {
	/*
	hole die Connection, und setze ein Insert oder ein Update ab.
	*/
    }

}

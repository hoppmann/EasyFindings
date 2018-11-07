/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.operations.testmatthias.dbaccess;

import de.hoppmann.operations.testmatthias.model.GeneInfo;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public class FileSystemGeneInfoRepository 
implements IGeneInfoRepository {

    @Override
    public List<GeneInfo> loadAllGeneInfos() {
	// lade die geneInfo.xml Datei und lese alle Gene
	return null;
    }

    @Override
    public void saveGeneInfo(GeneInfo geneInfo) {
	// speichere in die Datei
    }
    
}

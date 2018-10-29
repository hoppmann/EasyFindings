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
public interface GeneInfoRepository {
    
    public List<GeneInfo> loadAllGeneInfos();
    public void saveGeneInfo(GeneInfo geneInfo);
    
}

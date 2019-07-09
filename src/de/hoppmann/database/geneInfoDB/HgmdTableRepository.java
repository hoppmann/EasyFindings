/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.geneInfoDB;

import de.hoppmann.database.userDB.DbOperations;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoppmann
 */
public class HgmdTableRepository {

    
    // DB  related variables
    private File geneInfoDB;
    private final String dbName = "geneInfos.db";
    private final String tableHgmd = "HGMD";

    
    
    // Table related variables
    
    private final String idCol = "ID";
    private final String geneNameCol = "gene";
    private final String hgmdAccessionCol = "HGMD_Accession";
    private final String hgmdCodonChangeCol = "HGMD_Codon_Change";
    private final String hgmdAaChangeCol = "HGMD_amino_acid_change";
    private final String cNomenCol = "cNomen";
    private final String pNomenCol = "pNomen";
    private final String varClassCol = "Variant_class";
    private final String phenotypeCol = "Reported_phenotype";
    private final String referenceCol = "Reference";
    private final String chrCol = "chr";
    private final String hg38Col = "hg38";
    private final String hg19Col = "hg19";
    
    
    
    
    
    
    
    
     public List queryForGene (String geneName){
	
         List hgmdInfo = null;
         try {
	    boolean success = connectGeneInfoDB();
	    hgmdInfo = queryGene(geneName);
	} catch (SQLException ex) {
	    Logger.getLogger(HgmdTableRepository.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    closeGeneInfoDB();
	}
         
         return hgmdInfo;
    }

    
    
    
    private boolean connectGeneInfoDB() {
	// get current path of program
	String curDir = System.getProperty("user.dir");
	geneInfoDB = new File(curDir + File.separator + "DBs" + File.separator + dbName);

        boolean success = ConnectGeneInfoDb.connectGeneInfoDbSqLite();
	
	return success;
    }
    
    
    
    
    private void closeGeneInfoDB() {
	GeneInfoDbConnectionBuilder.closeConnection();
    }
    
    
    
    
    
     private List queryGene(String geneName) throws SQLException{
	
         
         
        List<IHgmdTableModel> hgmdInfo = new LinkedList<>();
	 
	String query = "select * "
		+ " from " + tableHgmd
		+ " where " + geneNameCol + " == '" + geneName + "'";
	
	ResultSet rs = DbOperations.execute(query, GeneInfoDbConnectionHolder.getInstance().getConnection());
                
	while (rs.next()){

            IHgmdTableModel hgmdModel = new HgmdTableModel();
            hgmdModel.setId(rs.getInt(idCol));
            hgmdModel.setGeneName(rs.getString(geneNameCol));
            hgmdModel.setHgmdAccession(rs.getString(hgmdAccessionCol));
            hgmdModel.setHgmdCodonChange(rs.getString(hgmdCodonChangeCol));
            hgmdModel.setHgmdAaChange(rs.getString(hgmdAaChangeCol));
            hgmdModel.setcNomen(rs.getString(cNomenCol));
            hgmdModel.setpNomen(rs.getString(pNomenCol));
            hgmdModel.setVarClassification(rs.getString(varClassCol));
            hgmdModel.setPhenotype(rs.getString(phenotypeCol));
            hgmdModel.setReference(rs.getString(referenceCol));
            hgmdModel.setChr(rs.getString(chrCol));
            hgmdModel.setHg38Pos(rs.getInt(hg38Col));
            hgmdModel.setHg19Pos(rs.getInt(hg19Col));

            hgmdInfo.add(hgmdModel);
	}
        
	
        return hgmdInfo;
    }

    



    
    

}

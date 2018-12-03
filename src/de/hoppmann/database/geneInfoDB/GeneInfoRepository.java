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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoppmann
 */
public class GeneInfoRepository {

    // DB related
    private File geneInfoDB;
    private final String dbName = "geneInfos.db";
    private final String tableHg19 = "hg19";

    
    
    // column variables
    private final String geneNameCol =  "geneName";
    private final String ncbiRefSeqCol = "NCBI_RefSeq";
    private final String transcriptLengthCol = "TranscriptLength";
    private final String chrCol = "chr";
    private final String genomicStartCol = "genomic_start";
    private final String genomicEnd = "genomic_end";
    private final String cytoLocationCol = "Cyto_Location";
    private final String geneMimCol = "GeneMim";
    private final String geneAliasCol = "Gene_Alias";
    private final String geneLongNameCol = "Gene_Long_Name";
    private final String entrezGeneIdCol = "Entrez_Gene_ID";
    private final String ensemblGeneIdCol = "Ensembl_Gene_ID";
    private final String mouseGeneIdCol = "Mouse_Gene_Symbol_ID";
    private final String phenoCol = "Phenotypes";
    private final String phenoMimCol = "PhenoMim";
    private final String arCol = "AR";
    private final String adCol = "AD";
    private final String xlrCol = "XLR";
    private final String xldCol = "XLD";
    private final String knownRecInfoCol = "Known_rec_info";
    private final String gdiCol = "GDI";
    private final String gdiPhredCol = "GDI_phred";
    private final String gdiAllCol = "GDI_all";
    private final String gdiAllMendelian = "GDI_all_mendelian";
    private final String exacPliCol = "ExAC_pLI";
    private final String exacPrecCol = "ExAC_pRec";
    private final String exacPnullCol = "ExAC_pNull";



    

    
    public void queryForGene (IGeneInfoModel geneInfoModel){
	try {
	    boolean success = connectGeneInfoDB();
	    queryGene(geneInfoModel);
	} catch (SQLException ex) {
	    Logger.getLogger(GeneInfoRepository.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    closeGeneInfoDB();
	}

    }

    
    
    
    private boolean connectGeneInfoDB() {
	// get current path of program
	String curDir = System.getProperty("user.dir");
	geneInfoDB = new File(curDir + File.separator + "DBs" + File.separator + dbName);

        
        boolean success = new ConnectGeneInfoDb(new ConnectGeneInfoSQLite()).connectGeneInfoDbSqLite();
	
	return success;
    }
    
    
    
    
    private void closeGeneInfoDB() {
	GeneInfoDbConnectionBuilder.closeConnection();
    }
    
    
    
    
    
    private void queryGene(IGeneInfoModel geneInfoModel) throws SQLException{
	
	
	String query = "select * "
		+ " from " + tableHg19
		+ " where " + geneNameCol + " == '" + geneInfoModel.getGeneName() + "'";
	
	
	ResultSet rs = DbOperations.execute(query, GeneInfoDbConnectionHolder.getInstance().getConnection());

	while (rs.next()){
	    geneInfoModel.setGeneName(rs.getString(geneNameCol));
	    geneInfoModel.setNcbiRefSeq(rs.getString(ncbiRefSeqCol));
	    geneInfoModel.setTranscriptLength(rs.getInt(transcriptLengthCol));
	    geneInfoModel.setChr(rs.getInt(chrCol));
	    geneInfoModel.setGenomicStart(rs.getInt(genomicStartCol));
	    geneInfoModel.setGenomicEnd(rs.getInt(genomicEnd));
	    geneInfoModel.setCytoLocation(rs.getString(cytoLocationCol));
	    geneInfoModel.setGeneMim(rs.getString(geneMimCol));
	    geneInfoModel.setGeneAlias(rs.getString(geneAliasCol));
	    geneInfoModel.setGeneLongName(geneLongNameCol);
	    geneInfoModel.setEntrezGeneId(rs.getString(entrezGeneIdCol));
	    geneInfoModel.setEnsemblGeneId(rs.getString(ensemblGeneIdCol));
	    geneInfoModel.setMouseGeneSymbolId(rs.getString(mouseGeneIdCol));
	    geneInfoModel.setPhenotypes(rs.getString(phenoCol));
	    geneInfoModel.setPhenoMim(rs.getString(phenoMimCol));
	    geneInfoModel.setAR(rs.getBoolean(arCol));
	    geneInfoModel.setAD(rs.getBoolean(adCol));
	    geneInfoModel.setXLR(rs.getBoolean(xlrCol));
	    geneInfoModel.setXLD(rs.getBoolean(xldCol));
	    geneInfoModel.setKnownRecInfo(rs.getString(knownRecInfoCol));
	    geneInfoModel.setGdi(rs.getDouble(gdiCol));
	    geneInfoModel.setGdiPhred(rs.getDouble(gdiPhredCol));
	    geneInfoModel.setGdiAll(rs.getString(gdiAllCol));
	    geneInfoModel.setGdiAllMendelian(rs.getString(gdiAllMendelian));
	    geneInfoModel.setExacPli(rs.getDouble(exacPliCol));
	    geneInfoModel.setExacPrec(rs.getDouble(exacPrecCol));
	    geneInfoModel.setExacPnull(rs.getDouble(exacPnullCol));
	}
    }

    









}

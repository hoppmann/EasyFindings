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
    private final String geneMimCol = "GeneMim";
    private final String arCol = "AR";
    private final String adCol = "AD";
    private final String xlrCol = "XLR";
    private final String xldCol = "XLD";
    private final String ncbiRefSeqCol = "NCBI_RefSeq";
    private final String transcriptLengthCol = "TranscriptLength";
    private final String phenoCol = "Phenotypes";
    private final String phenoMimCol = "PhenoMim";
    private final String knownRecInfoCol = "Known_rec_info";
    private final String gdiCol = "GDI";
    private final String gdiPhredCol = "GDI_phred";
    private final String gdiAllCol = "GDI_all";
    private final String gdiAllMendelian = "GDI_all_mendelian";
    private final String exacPliCol = "ExAC_pLI";
    private final String exacPrecCol = "ExAC_pRec";
    private final String exacPnullCol = "ExAC_pNull";



    
    
    IGeneInfoModel geneInfoModel;
    
    
    
    public GeneInfoRepository(IGeneInfoModel geneInfoModel) {
	this.geneInfoModel = geneInfoModel;
	
	
	connectGeneInfoDB();
	
	try {
	    queryGene();
	} catch (SQLException ex) {
	    Logger.getLogger(GeneInfoRepository.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
    }


    
    
    
    private void connectGeneInfoDB() {
	// get current path of program
	String curDir = System.getProperty("user.dir");
	geneInfoDB = new File(curDir + File.separator + "DBs" + File.separator + dbName);

        
        boolean success = new ConnectGeneInfoDb(new ConnectGeneInfoSQLite()).connectGeneInfoDbSqLite();
	
    }
    
    
    
    
    private void closeGeneInfoDB() {
	GeneInfoDbConnectionBuilder.closeConnection();
    }
    
    
    
    
    
    private void queryGene() throws SQLException{
	
	
	String query = "select * "
		+ " from " + tableHg19
		+ " where " + geneNameCol + " == '" + geneInfoModel.getGeneName() + "'";
	
	
	
	ResultSet rs = DbOperations.execute(query, GeneInfoDbConnectionHolder.getInstance().getConnection());
	
	while (rs.next()){
	    
	    
	    geneInfoModel.setNcbiRefSeq(rs.getString(ncbiRefSeqCol));
	    geneInfoModel.setTranscriptLength(rs.getInt(transcriptLengthCol));
	    
//	        private String geneName;
//    private String ncbiRefSeq;
//    private String transcriptLength;
//    private int chr;
//    private String genomicStart;
//    private String genomicEnd;
//    private String cytoLocation;
//    private String geneMim;
//    private String geneAlias;
//    private String geneLongName;
//    private String entrezGeneId;
//    private String ensemblGeneId;
//    private String mouseGeneSymbolId;
//    private String phenotypes;
//    private String phenoMim;
//    private boolean AR;
//    private boolean AD;
//    private boolean XLR;
//    private boolean XLD;
//    private String moi;
//    private String knownRecInfo;
//    private double gdi;
//    private double gdiPhred;
//    private String gdiAll;
//    private String gdiAllMendelian;
//    private double exacPli;
//    private double exacPrec;
//    private double exacPnull;

	    
	    
	    
	    
	    
	    
	}
	
	
	
	
//	// prepare query
//	String query = "select "
//		+ geneNameCol + ", "
//		+ geneMimCol + ", "
//		+ arCol + ", " + adCol + ", " + xlrCol + ", " + xldCol + ", "
//		+ ncbiRefSeqCol + ", "
//		+ codingRegionCol + ", "
//		+ phenoCol + ", "
//		+ phenoMimCol + ", "
//		+ knownRecInfoCol + ", "
//		+ gdiCol + ", "
//		+ gdiPhredCol + ", "
//		+ gdiAllCol + ", "
//		+ gdiAllMendelian + ", "
//		+ exacPliCol + ", "
//		+ exacPrecCol + ", "
//		+ exacPnullCol + ", "
//		+ " from " + tableHg19
//		+ " where " + geneNameCol + " == '" + curGene + "'";

		
		
		// query current gene and retrieve variables
		
//		ResultSet rs = DbOperations.execute(query, GeneInfoDbConnectionHolder.getInstance().getConnection());
//		while (rs.next()) {
//
//		    geneName = rs.getString(geneNameCol).toUpperCase();
//		    
//		    PreparePanelTable.GeneInfoModel geneInfoData = new PreparePanelTable.GeneInfoModel();
//		    geneInfos.put(geneName, geneInfoData);
//		    
//		    geneInfoData.setGeneMim(rs.getString(geneMimCol));
//		    geneInfoData.setNcbiRefSeq(rs.getString(ncbiRefSeqCol));
//		    geneInfoData.setCodingRegion(rs.getInt(codingRegionCol));
//		    geneInfoData.setPheno(rs.getString(phenoCol));
//		    geneInfoData.setPhenoMim(rs.getString(phenoMimCol));
//		}
	
	
	
    }

    









}

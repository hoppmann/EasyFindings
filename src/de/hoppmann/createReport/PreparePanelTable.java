/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

import de.hoppmann.operations.Database;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoppmann
 */
public class PreparePanelTable extends Database {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    private String panelTable;
    
    
    private List<String> panelGenes = new LinkedList<>();
    private final String dbName = "geneInfos.db";
    private final String tableHg19 = "hg19";
    private File geneInfoDB;

    private String geneName;
    protected String geneNameCol =  "geneName";
    protected String geneMimCol = "GeneMim";
    protected String arCol = "AR";
    protected String adCol = "AD";
    protected String xlrCol = "XLR";
    protected String xldCol = "XLD";
    protected String ncbiRefSeqCol = "NCBI_RefSeq";
    protected String codingRegionCol = "TranscriptLength";
    protected String phenoCol = "Phenotypes";
    protected String phenoMimCol = "PhenoMim";


    private Map<String, GeneInfoModel> geneInfos = new HashMap<String, GeneInfoModel>();
	
    
    
    
    
    
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////

    public PreparePanelTable() {
    
	
	// get current path of program
	String curDir = System.getProperty("user.dir");
	geneInfoDB = new File(curDir + File.separator + "DBs" + File.separator + dbName);

	// connect to geneInfo.db
	conn = connect(geneInfoDB);
	
    }
	
    
    
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    // guide method
    public void create(String inputText) {
	
	List<String> geneList = createPanelList(inputText);
	gatherGeneInfos(geneList);
	panelTable = prepareTable(geneList);
	
	
		
    }
    
    
    
    
    
    
    
    // form list for text field
    private List createPanelList(String inputText) {
	
	// check if input not null if  so split text into list 
	List<String> geneList = new LinkedList<>();
	if (inputText != null) {
	    
	    // remove white spaces
	    List<String> tmpList = Arrays.asList(inputText.split("\n"));
	    for (String curGene : tmpList) {
		// skip empty lines or lines containing only white spaces
		if (curGene.replaceAll("\\s", "").equals("")){
		    continue;
		}
		geneList.add(curGene.replaceAll("\\s", ""));
	    }
	}
	
	
	
	

	return geneList;
	
    }
	
    
    
    
    
    
    
    
    
    // collect infos for gene list
    private void gatherGeneInfos(List<String> geneList) {
	
	
	///////////////
	//// check if gene list contains new genes

	
	// check for equality
	Collections.sort(geneList);
	Collections.sort(panelGenes);
	
	if (panelGenes.equals(geneList)){
	   return;
	} else {
	    panelGenes = geneList;
	}
	
	
	
	// prepare query
	for (String curGene : panelGenes) {
	    try {
		
		curGene = curGene.toUpperCase();
		// prepare query
		String query = "select "
			+ geneNameCol + ", "
			+ geneMimCol + ", "
			+ arCol + ", " + adCol + ", " + xlrCol + ", " + xldCol + ", "
			+ ncbiRefSeqCol + ", "
			+ codingRegionCol + ", "
			+ phenoCol + ", "
			+ phenoMimCol
			+ " from " + tableHg19
			+ " where " + geneNameCol + " == '" + curGene + "'";
		
		
		
		// query current gene and retrieve variables
		
		ResultSet rs = execute(query);
		while (rs.next()) {

		    geneName = rs.getString(geneNameCol).toUpperCase();
		    
		    GeneInfoModel geneInfoData = new GeneInfoModel();
		    geneInfos.put(geneName, geneInfoData);
		    
		    geneInfoData.setGeneMim(rs.getString(geneMimCol));
		    geneInfoData.setNcbiRefSeq(rs.getString(ncbiRefSeqCol));
		    geneInfoData.setCodingRegion(rs.getInt(codingRegionCol));
		    geneInfoData.setPheno(rs.getString(phenoCol));
		    geneInfoData.setPhenoMim(rs.getString(phenoMimCol));
		    
		    // combine mois and store
		    StringBuilder moi = new StringBuilder();
		    if (rs.getBoolean(arCol)){
			moi.append("AR");
		    }
		    if (rs.getBoolean(adCol)) {
			moi.append("AD");
		    }
		    if (rs.getBoolean(xlrCol)){
			moi.append("XLR");
		    }
		    if (rs.getBoolean(xldCol)){
			moi.append("XLD");
		    }
		    geneInfoData.setMoi(moi.toString());
		    
		}
	    }
	    //
	    catch (SQLException ex) {
		Logger.getLogger(PreparePanelTable.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
    }
    
    
    
    
    
    
    
    // preapare table
    
    private String prepareTable(List<String> geneList) {
    
	// start HTML table
	List<String> elements = new LinkedList<>();


	// prepare size elements
	elements.add("<table style=\"width: 100%\" border=\"1\">");
	elements.add("\t<col style=\"width: 12.5%\">");
	elements.add("\t<col style=\"width: 12.5%\">");
	elements.add("\t<col style=\"width: 12.5%\">");
	elements.add("\t<col style=\"width: 12.5%\">");
	elements.add("\t<col style=\"width: 12.5%\">");
	elements.add("\t<col style=\"width: 12.5%\">");
	elements.add("\t<col style=\"width: 12.5%\">");
	
	// prepare body
	elements.add("\t<tbody>");
	elements.add("\t\t<tr>");
	
	// preapare header
	elements.add("\t\t\t<td>Gen</td>");
	elements.add("\t\t\t<td>OMIM Gen</td>");
	elements.add("\t\t\t<td>Vererbung</td>");
	elements.add("\t\t\t<td>NCBI RefSeq</td>");
	elements.add("\t\t\t<td>kodierender Bereich (kb)</td>");
	elements.add("\t\t\t<td>Erkrankung</td>");
	elements.add("\t\t\t<td>OMIM Erkrankung</td>");
	elements.add("\t\t</tr>");


	
	// add gene informations
	
	for (String curGene : geneList){


	    // make upper case
	    curGene = curGene.toUpperCase();
	    
	    // check if gene has entry
	    elements.add("\t\t<tr>");
	    elements.add("\t\t\t<td>" + curGene + "</td>");
	    if (geneInfos.get(curGene) != null) {

		elements.add("\t\t\t<td>" + geneInfos.get(curGene).getGeneMim() + "</td>");
		elements.add("\t\t\t<td>" + geneInfos.get(curGene).getMoi() + "</td>");
		elements.add("\t\t\t<td>" + geneInfos.get(curGene).getNcbiRefSeq() + "</td>");
		elements.add("\t\t\t<td>" + geneInfos.get(curGene).getCodingRegion() + "</td>");
		elements.add("\t\t\t<td>" + geneInfos.get(curGene).getPheno() + "</td>");
		elements.add("\t\t\t<td>" + geneInfos.get(curGene).getPhenoMim() + "</td>");
	    }
	    elements.add("\t\t</tr>");
	}

	elements.add("\t\t\t");
	elements.add("");
	elements.add("");
	elements.add("");
	elements.add("");
	
	
	elements.add("\t</tbody>");

	
	return String.join("\n", elements);
	
    }
    
    
    
    
    
    
    // add sum of coding region
    
    // add muliplyer
	
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////

    public String getPanelTable() {
	return panelTable;
    }


    
    
    
    
    
    ////////////////////////////////////////////////
    //////// internal class for datastorage ////////
    ////////////////////////////////////////////////



    protected class GeneInfoModel {
    
	///////////////////
	//// variables ////
	///////////////////
	
	private String geneMim;
	private String moi;
	private String ncbiRefSeq;
	private int codingRegion;
	private String pheno;
	private String phenoMim;

    
	//////////////////////////
	//// getter // setter ////
	//////////////////////////

	public String getGeneMim() {
	    return geneMim;
	}

	public void setGeneMim(String geneMim) {
	    this.geneMim = geneMim;
	}

	public String getMoi() {
	    return moi;
	}

	public void setMoi(String moi) {
	    this.moi = moi;
	}

	public String getNcbiRefSeq() {
	    return ncbiRefSeq;
	}

	public void setNcbiRefSeq(String ncbiRefSeq) {
	    this.ncbiRefSeq = ncbiRefSeq;
	}

	public int getCodingRegion() {
	    return codingRegion;
	}

	public void setCodingRegion(int codingRegion) {
	    this.codingRegion = codingRegion;
	}

	public String getPheno() {
	    return pheno;
	}

	public void setPheno(String pheno) {
	    this.pheno = pheno;
	}

	public String getPhenoMim() {
	    return phenoMim;
	}

	public void setPhenoMim(String phenoMim) {
	    this.phenoMim = phenoMim;
	}

	
	

	
	
	
	
    
    }






}

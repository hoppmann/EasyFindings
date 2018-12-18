/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

import de.hoppmann.database.geneInfoDB.Hg19TableModel;
import de.hoppmann.database.geneInfoDB.Hg19TableRepository;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author hoppmann
 */
public class PreparePanelTable {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    private String reportPanelTable;
    private String billingTable;
    
    
    private Set<String> alreadyQueriedList = new TreeSet<>();

    private Map<String, Hg19TableModel> geneInfos = new HashMap<String, Hg19TableModel>();
	
    
    
    
    
    
	
    
    
	
	

    ///////////////////////
    //////// guide methods
    
    // create table for reprort (no multiplier)
    public String createReportTable(Set panel) {
	
	gatherGeneInfos(panel);
	
	// if panel genes are given, prepare table, else delet table if exists
	reportPanelTable = "";
	if(panel.size() > 0){
	    reportPanelTable = prepareTable(panel, false);
	}
		
        return reportPanelTable;
    }
    
    
    
    
    
    // create table for billing (include multiplier)
    public String createBillingTable(Set panel) {
	gatherGeneInfos(panel);
	billingTable = prepareTable(panel, true);
        return billingTable;
    }
    
    
    
    


    
    
    
    
    
    
    
    
    // gather infos for gene list from database
    private void gatherGeneInfos(Set<String> geneSet) {
	
	
	///////////////
	//// check if gene list contains new genes

	
	/*
	check if already queried list equals new list
	if so skip database query
	*/
	if (alreadyQueriedList.equals(geneSet)){
	    return;
	}
	
	
	
	
	
	// get gene infos
	for (String curGene : geneSet) {
	   if (alreadyQueriedList.contains(curGene)) {
	       continue;
	   }
	    
	   
	    Hg19TableModel geneInfoData = new Hg19TableModel(curGene);
	   
	    Hg19TableRepository geneInfoRepo = new Hg19TableRepository();
	    geneInfoRepo.queryForGene(geneInfoData);
	    
	    geneInfos.put(curGene, geneInfoData);
	}
	
	
	// save new gene list for later comparisment
	alreadyQueriedList = geneSet;
	
	
    }
    
    
    
    
    
    
    
    // preapare table
    
    private String prepareTable(Set<String> geneList, boolean forBilling) {
    
	// start HTML table
	List<String> elements = new LinkedList<>();


	// prepare size elements
	elements.add("<h3>Untersuchte Gene</h3>");
	elements.add("<table style=\"width: 100%\" border=\"1\">");
	
	// prepare body
	elements.add("\t<tbody>");
	elements.add("\t\t<tr>");
	
	// preapare header
	elements.add("\t\t\t<td><small><small>Gen</small></small></td>");
	elements.add("\t\t\t<td><small><small>OMIM Gen</small></small></td>");
	elements.add("\t\t\t<td><small><small>Vererbung</small></small></td>");
	elements.add("\t\t\t<td><small><small>NCBI RefSeq</small></small></td>");
	elements.add("\t\t\t<td><small><small>kodierender Bereich (kb)</small></small></td>");
	if (forBilling){
	    	elements.add("\t\t\t<td><small><small>Muliplikator</small></small></td>");
	}
	elements.add("\t\t\t<td><small><small>Erkrankung</small></small></td>");
	elements.add("\t\t\t<td><small><small>OMIM Erkrankung</small></small></td>");
	elements.add("\t\t</tr>");


	
	// add gene informations
	int panelSize = 0;
	int totalMultiplier = 0;
	for (String curGene : geneList){


	    // make upper case
	    curGene = curGene.toUpperCase();
	    
	    // check if gene has entry
	    elements.add("\t\t<tr>");
	    elements.add("\t\t\t<td><small><small>" + curGene + "</small></small></td>");
	    if (geneInfos.get(curGene) != null) {

		
		
		// if phenotype data is available split in case of multiple entries
		String pheno = "";
		String phenoMim = "";
		if (geneInfos.get(curGene).getPhenotypes() != null){
		    List<String> split = Arrays.asList(geneInfos.get(curGene).getPhenotypes().split(";"));
		    pheno = String.join("<br>", split);
		}
		if (geneInfos.get(curGene).getPhenoMim() != null) {
		    List<String> split = Arrays.asList(geneInfos.get(curGene).getPhenoMim().split(";"));
		    phenoMim = String.join("<br>", split);
		}
		
		
		elements.add("\t\t\t<td><small><small>" + geneInfos.get(curGene).getGeneMim() + "</small></small></td>");
		elements.add("\t\t\t<td><small><small>" + geneInfos.get(curGene).getMoi() + "</small></small></td>");
		elements.add("\t\t\t<td><small><small>" + geneInfos.get(curGene).getNcbiRefSeq() + "</small></small></td>");
		elements.add("\t\t\t<td><small><small>" + (double) geneInfos.get(curGene).getTranscriptLength()/ 1000 + "</small></small></td>");
		
		// if table for billing add multiplier 
    		if(forBilling){
		    int multiplier = geneInfos.get(curGene).getTranscriptLength()/ 250;
		    elements.add("\t\t\t<td><small><small>" + multiplier + "</small></small></td>");
		    totalMultiplier += multiplier;
		}
		elements.add("\t\t\t<td><small><small>" + pheno + "</small></small></td>");
		elements.add("\t\t\t<td><small><small>" + phenoMim + "</small></small></td>");
		
		// add transcript size to panelsize
		panelSize += geneInfos.get(curGene).getTranscriptLength();
	    }
	    elements.add("\t\t</tr>");
	}

	
	// add sum of transcript length
	elements.add("\t\t</tr>");
	elements.add("\t\t\t<td><small><small><b>&sum;</b></small></small></td>");
	elements.add("\t\t\t<td><small><small><b></b></small></small></td>");
	elements.add("\t\t\t<td><small><small><b></b></small></small></td>");
	elements.add("\t\t\t<td><small><small><b></b></small></small></td>");
	elements.add("\t\t\t<td><small><small><b>" + (double) panelSize/1000 + "</b></small></small></td>");
	if (forBilling){
	    elements.add("\t\t\t<td><small><small><b>" + totalMultiplier + "</b></small></small></td>");

	}
	elements.add("\t\t\t<td><small><small><b></b></small></small></td>");
	elements.add("\t\t\t<td><small><small><b></b></small></small></td>");
	
	
	elements.add("\t</tbody>");

	
	return String.join("\n", elements);
	
    }
    
    
    

    
    
    
    
    
    
    
//    
//    ////////////////////////////////////////////////
//    //////// internal class for datastorage ////////
//    ////////////////////////////////////////////////
//
//
//
//    protected class Hg19TableModel {
//    
//	///////////////////
//	//// variables ////
//	///////////////////
//	
//	private String geneMim;
//	private String moi;
//	private String ncbiRefSeq;
//	private int codingRegion;
//	private String pheno;
//	private String phenoMim;
//
//    
//	//////////////////////////
//	//// getter // setter ////
//	//////////////////////////
//
//	public String getGeneMim() {
//	    return geneMim;
//	}
//
//	public void setGeneMim(String geneMim) {
//	    this.geneMim = geneMim;
//	}
//
//	public String getMoi() {
//	    return moi;
//	}
//
//	public void setMoi(String moi) {
//	    this.moi = moi;
//	}
//
//	public String getNcbiRefSeq() {
//	    return ncbiRefSeq;
//	}
//
//	public void setNcbiRefSeq(String ncbiRefSeq) {
//	    this.ncbiRefSeq = ncbiRefSeq;
//	}
//
//	public int getCodingRegion() {
//	    return codingRegion;
//	}
//
//	public void setCodingRegion(int codingRegion) {
//	    this.codingRegion = codingRegion;
//	}
//
//	public String getPheno() {
//	    return pheno;
//	}
//
//	public void setPheno(String pheno) {
//	    this.pheno = pheno;
//	}
//
//	public String getPhenoMim() {
//	    return phenoMim;
//	}
//
//	public void setPhenoMim(String phenoMim) {
//	    this.phenoMim = phenoMim;
//	}
//
//	
//	
//
//	
//	
//	
//	
//    
//    }






}

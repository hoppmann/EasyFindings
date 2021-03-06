/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.geneInfoDB;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public class Hg19TableModel implements IHg19TableModel{


    ///////////////////
    //// variables ////
    ///////////////////

    private String geneName;
    private String ncbiRefSeq;
    private int transcriptLength;
    private int chr;
    private int genomicStart;
    private int genomicEnd;
    private String cytoLocation;
    private String geneMim;
    private String geneAlias;
    private String geneLongName;
    private String entrezGeneId;
    private String ensemblGeneId;
    private String mouseGeneSymbolId;
    private String phenotypes;
    private String phenoMim;
    private boolean AR;
    private boolean AD;
    private boolean XLR;
    private boolean XLD;
    private String moi;
    private String knownRecInfo;
    private double gdi;
    private double gdiPhred;
    private String gdiAll;
    private String gdiAllMendelian;
    private double exacPli;
    private double exacPrec;
    private double exacPnull;
    private int codingRegion;
    private String ncbiRefSeqGeneId;

    
    
    public Hg19TableModel(String geneName) {
	this.geneName = geneName;
    }

    
    
    private void prepareMoiString () {
	List moiList = new LinkedList();
	if (AR) {moiList.add("AR");}
	if (AD) {moiList.add("AD");}
	if (XLR) {moiList.add("XLR");}
	if (XLD) {moiList.add("XLD");}
	
	moi = String.join(", ", moiList);
	
    }
    
    
    
    
    
    
    
    @Override
    public String getGeneName() {
	return geneName;
    }

    @Override
    public void setGeneName(String geneName) {
	geneName = geneName.toUpperCase();
	this.geneName = geneName;
    }

    @Override
    public String getNcbiRefSeq() {
	return ncbiRefSeq;
    }

    @Override
    public void setNcbiRefSeq(String ncbiRefSeq) {
	this.ncbiRefSeq = ncbiRefSeq;
    }

    @Override
    public int getTranscriptLength() {
	return transcriptLength;
    }

     @Override
   public void setTranscriptLength(int transcriptLength) {
	this.transcriptLength = transcriptLength;
    }

    @Override
    public int getChr() {
	return chr;
    }

    @Override
    public void setChr(int chr) {
	this.chr = chr;
    }

    @Override
    public int getGenomicStart() {
	return genomicStart;
    }

    @Override
    public void setGenomicStart(int genomicStart) {
	this.genomicStart = genomicStart;
    }

    @Override
    public int getGenomicEnd() {
	return genomicEnd;
    }

    @Override
    public void setGenomicEnd(int genomicEnd) {
	this.genomicEnd = genomicEnd;
    }

    @Override
    public String getCytoLocation() {
	return cytoLocation;
    }

    @Override
    public void setCytoLocation(String cytoLocation) {
	this.cytoLocation = cytoLocation;
    }

    @Override
    public String getGeneMim() {
	return geneMim;
    }

    @Override
    public void setGeneMim(String geneMim) {
	this.geneMim = geneMim;
    }

    @Override
    public String getGeneAlias() {
	return geneAlias;
    }

    @Override
    public void setGeneAlias(String geneAlias) {
	this.geneAlias = geneAlias;
    }

    @Override
    public String getGeneLongName() {
	return geneLongName;
    }

    @Override
    public void setGeneLongName(String geneLongName) {
	this.geneLongName = geneLongName;
    }

    @Override
    public String getEntrezGeneId() {
	return entrezGeneId;
    }

    @Override
    public void setEntrezGeneId(String entrezGeneId) {
	this.entrezGeneId = entrezGeneId;
    }

    @Override
    public String getEnsemblGeneId() {
	return ensemblGeneId;
    }

    @Override
    public void setEnsemblGeneId(String ensemblGeneId) {
	this.ensemblGeneId = ensemblGeneId;
    }

    @Override
    public String getMouseGeneSymbolId() {
	return mouseGeneSymbolId;
    }

    @Override
    public void setMouseGeneSymbolId(String mouseGeneSymbolId) {
	this.mouseGeneSymbolId = mouseGeneSymbolId;
    }

    @Override
    public String getPhenotypes() {
	return phenotypes;
    }

    @Override
    public void setPhenotypes(String phenotypes) {
	this.phenotypes = phenotypes;
    }

    @Override
    public String getPhenoMim() {
	return phenoMim;
    }

    @Override
    public void setPhenoMim(String phenoMim) {
	this.phenoMim = phenoMim;
    }

    @Override
    public boolean isAR() {
	return AR;
    }

    @Override
    public void setAR(boolean AR) {
	this.AR = AR;
    }

    @Override
    public boolean isAD() {
	return AD;
    }

    @Override
    public void setAD(boolean AD) {
	this.AD = AD;
    }

    @Override
    public boolean isXLR() {
	return XLR;
    }

    @Override
    public void setXLR(boolean XLR) {
	this.XLR = XLR;
    }

    @Override
    public boolean isXLD() {
	return XLD;
    }

    @Override
    public void setXLD(boolean XLD) {
	this.XLD = XLD;
    }

    @Override
    public String getMoi() {
	prepareMoiString();
	return moi;
    }

    @Override
    public void setMoi(String moi) {
	this.moi = moi;
    }

    @Override
    public String getKnownRecInfo() {
	return knownRecInfo;
    }

    @Override
    public void setKnownRecInfo(String knownRecInfo) {
	this.knownRecInfo = knownRecInfo;
    }

    @Override
    public double getGdi() {
	return gdi;
    }

    @Override
    public void setGdi(double gdi) {
	this.gdi = gdi;
    }

    @Override
    public double getGdiPhred() {
	return gdiPhred;
    }

    @Override
    public void setGdiPhred(double gdiPhred) {
	this.gdiPhred = gdiPhred;
    }

    @Override
    public String getGdiAll() {
	return gdiAll;
    }

    @Override
    public void setGdiAll(String gdiAll) {
	this.gdiAll = gdiAll;
    }

    @Override
    public String getGdiAllMendelian() {
	return gdiAllMendelian;
    }

    @Override
    public void setGdiAllMendelian(String gdiAllMendelian) {
	this.gdiAllMendelian = gdiAllMendelian;
    }

    @Override
    public double getExacPli() {
	return exacPli;
    }

    @Override
    public void setExacPli(double exacPli) {
	this.exacPli = exacPli;
    }
    @Override
    public double getExacPrec() {
	return exacPrec;
    }

    @Override
    public void setExacPrec(double exacPrec) {
	this.exacPrec = exacPrec;
    }

    @Override
    public double getExacPnull() {
	return exacPnull;
    }

    @Override
    public void setExacPnull(double exacPnull) {
	this.exacPnull = exacPnull;
    }

    @Override
    public void setRefSeqId(String refSeqId) {
        this.ncbiRefSeqGeneId = refSeqId;
    }

    @Override
    public String getRefSeqId() {
        return ncbiRefSeqGeneId;
    }

    
    








    





}

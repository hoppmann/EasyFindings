/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.geneInfoDB;

/**
 *
 * @author hoppmann
 */
public class HgmdTableModel implements IHgmdTableModel {

    private int id;
    private String geneName;
    private String hgmdAccession;
    private String hgmdCodonChange;
    private String hgmdAaChange;
    private String cNomen;
    private String pNomen;
    private String varClassification;
    private String phenotype;
    private String reference;
    private String chr;
    private int hg38Pos;
    private int hg19Pos;

    
    
    
    @Override
    public int getId() {
        return id;
    }
    
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getGeneName() {
        return geneName;
    }

    @Override
    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    @Override
    public String getHgmdAccession() {
        return hgmdAccession;
    }
    @Override
    public void setHgmdAccession(String hgmdAccession) {
        this.hgmdAccession = hgmdAccession;
    }

    @Override
    public String getHgmdCodonChange() {
        return hgmdCodonChange;
    }

    @Override
    public void setHgmdCodonChange(String hgmdCodonChange) {
        this.hgmdCodonChange = hgmdCodonChange;
    }

    @Override
    public String getHgmdAaChange() {
        return hgmdAaChange;
    }

    @Override
    public void setHgmdAaChange(String hgmdAaChange) {
        this.hgmdAaChange = hgmdAaChange;
    }

    @Override
    public String getcNomen() {
        return cNomen;
    }

    @Override
    public void setcNomen(String cNomen) {
        this.cNomen = cNomen;
    }

    @Override
    public String getpNomen() {
        return pNomen;
    }

    @Override
    public void setpNomen(String pNomen) {
        this.pNomen = pNomen;
    }
    
    @Override
    public String getVarClassification() {
        return varClassification;
    }

    @Override
    public void setVarClassification(String varClassification) {
        this.varClassification = varClassification;
    }

    @Override
    public String getPhenotype() {
        return phenotype;
    }

    @Override
    public void setPhenotype(String phenotype) {
        this.phenotype = phenotype;
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String getChr() {
        return chr;
    }

    @Override
    public void setChr(String chr) {
        this.chr = chr;
    }

    @Override
    public int getHg38Pos() {
        return hg38Pos;
    }

    @Override
    public void setHg38Pos(int hg38Pos) {
        this.hg38Pos = hg38Pos;
    }

    @Override
    public int getHg19Pos() {
        return hg19Pos;
    }

    @Override
    public void setHg19Pos(int hg19Pos) {
        this.hg19Pos = hg19Pos;
    }
    
    



    
    

}

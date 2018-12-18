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
public interface IHgmdTableModel {
    
    
    
    public int getId();

    public void setId(int id);

    public String getGeneName();

    public void setGeneName(String geneName);

    public String getHgmdAccession();

    public void setHgmdAccession(String hgmdAccession);

    public String getHgmdCodonChange();

    public void setHgmdCodonChange(String hgmdCodonChange);

    public String getHgmdAaChange();

    public void setHgmdAaChange(String hgmdAaChange);

    public String getcNomen();

    public void setcNomen(String cNomen);

    public String getpNomen();

    public void setpNomen(String pNomen);

    public String getVarClassification();

    public void setVarClassification(String varClassification);

    public String getPhenotype();

    public void setPhenotype(String phenotype);

    public String getReference();

    public void setReference(String reference);

    public String getChr();

    public void setChr(String chr);

    public int getHg38Pos();

    public void setHg38Pos(int hg38Pos);

    public int getHg19Pos();

    public void setHg19Pos(int hg19Pos);
    
    
    
    
}

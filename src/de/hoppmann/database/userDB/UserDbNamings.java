/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB;

/**
 *
 * @author hoppmann
 */
public class UserDbNamings {

    
    // gene info table
    public static final String GENE_TABLE = "genes";
    public static final String GENE_NAME_COL = "gene";
    public static final String GENE_INFO_COL = "geneInfo";

    
    
    
    // var info table
    public static final String VAR_TABLE = "variants";
    public static final String VAR_COL = "var";
    public static final String VAR_INFO_COL = "varInfo";

    
    
    
    
    // address table
    public static final String RECEIVER_TABLE = "receiver";
    public static final String ADDRESS_ID_KEY = "ID";
    public static final String TITLE_KEY = "title";
    public static final String NAME_KEY = "fullName";
    public static final String ORGANISATION_KEY = "organisation";
    public static final String INSTITUTE_KEY = "institute";
    public static final String ADDRESS_KEY = "Postal_address";
    public static final String ZIP_CODE_KEY = "zipCode";
    public static final String CITY_KEY = "city";
    public static final String COUNTRY_KEY = "countries";

    
    
    
    // pael table
    public static final String PANEL_TABLE = "panel";
    public static final String PANEL_NAME_KEY = "panelName";
    public static final String PANEL_GENES_KEY = "panelGenes";
    public static final String PANEL_ID_KEY = "ID";


}

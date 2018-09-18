/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

/**
 *
 * @author hoppmann
 */
public class FillDataDummy {




    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    
    
    ////////////////////////////
    //////// costructor ////////
    ////////////////////////////
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    public ReportDataModel fillModel () {
        ReportDataModel model = new ReportDataModel();
        
        // fill receiver adress
        model.setReceiverName("Dr. Maria Musterfrau");
        model.setReceiverStreet("Hinterm Mond 1");
        model.setReceiverCity("12345 Sonne");
        
        
        // fill patient box
        model.setDiagMethod("Multi-Gen-Panel-Analyse mittels Next Generation Sequencing (NGS)");
        model.setPatientInfo("Musterknabe, Heinz, *25.12.2009, unsere Referenz P007");
        model.setMaterial("EDTA-Blut, Eingang 21.09.2018");
        model.setIndication("V.a. Mentale Retardierung, autosomal dominant");
        
        
        
        
        
        
        
        
        
        
        
        
        return model;
    }
    
    
    
    
    ///////////////////////////////
    //////// getter/setter ////////
    ///////////////////////////////

}

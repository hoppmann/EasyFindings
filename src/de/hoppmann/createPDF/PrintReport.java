/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createPDF;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author hoppmann
 */
public class PrintReport extends JFrame {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////

	
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
	
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    public void showReport() throws JRException, ClassNotFoundException, SQLException, IOException {
	
	    String reportSrcFile = "NextReport.jrxml";
	    InputStream is = this.getClass().getResourceAsStream(reportSrcFile);

	    // compile jrxml file
	    JasperReport jasperReport = JasperCompileManager.compileReport(is);
		    
	    
	    // Fields for report
	    

	    
	    HashMap<String, Object> params = new HashMap<>();
	    
	    params.put("company", "UKL");
	    params.put("name", "Anselm");
	    
	    
	    
	    /**
	     * Add a table of two columns
	     * 1. gene name
	     * 2. gene description
	     * next column would be variant descrition.
	     */
	    
	    
	    params.put("GeneNameField", "GENE1");
	    params.put("GeneDescriptionField", "GeneText");



	    
	    ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
	    list.add(params);
	    
	    
	    JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
	    JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
	    
//	    File pdf = File.createTempFile("output.", "test.pdf", new File("~/"));
	    
//	    // print report as PDF
//	    File pdf = new File("/media/hoppmann/Data/tmp/test.pdf");
//	    JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdf));
	    
	    
	    // show report in viewer
	    JRViewer viewer = new JRViewer(print);
	    viewer.setOpaque(true);
	    viewer.setVisible(true);
	    this.add(viewer);
	    this.setSize(700, 500);
	    this.setVisible(true);
	    System.out.print("Done!");
	    



    }
	
	
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////










}

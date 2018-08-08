/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createPDF;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;




/**
 *
 * @author hoppmann
 */
public class CreateReport {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    public String dest = "/media/hoppmann/Data/tmp/test.pdf";
    private Document report;
    private PdfWriter pdfWriter;
    
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
	
    
    public CreateReport() throws FileNotFoundException, DocumentException, BadElementException, IOException {
	
	create();
	addHeader();
	closeReport();
	
    }
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    
    
    //////// add Paragraph
    
    public void addHeader() throws DocumentException, BadElementException, IOException  {
	
	
	
	
	// add header paragraph
	Paragraph logos = new Paragraph();
	logos.setIndentationLeft(200);
	System.out.println(logos.getIndentationLeft());


	
	///////////////
	//////// Load UKL logo
		String sep = File.separator;
	String uklLogoPath = System.getProperty("user.dir") + sep + "Vorlagen" + sep + "ZKJKinderklinikJPG.jpg";
	Image uklLogo = Image.getInstance(uklLogoPath);
	uklLogo.scalePercent(10);
	
	uklLogo.setAbsolutePosition(
		PageSize.A4.getWidth() - 160,
		PageSize.A4.getHeight() - 80
	);
	report.add(uklLogo);
	
	

	////////////////////
	//////// add address
	
	
	
	
	

	// add 
	logos.add("HALLO");
	report.add(logos);
	
	
	
	
	
    }
    
    
    
    
    
    //////////////////
    //////// open new PDF document 
    public void create() throws FileNotFoundException, DocumentException {
	

	// open PDF
	// prepare Document
	report = new Document();
	    
	// init PDF writer
	pdfWriter = PdfWriter.getInstance(report, new FileOutputStream(dest));
	
	// open Document
	report.open();
	
	}
	
    
    
    
    
    /////////////////
    //////// close report
    public void closeReport () {
	report.close();
	
    }
    



    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////










}

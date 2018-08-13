/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createPDF;

import com.itextpdf.layout.Canvas;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.TextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;




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
	
	// create reveiver for testing
	String[] receiver = {"Herr", "Anselm", "Hoppmann", "Uferstr 67", "79115", "Freiburg"};
	
	
	// create sender for testing
	String[] sender = {"Humangenetik", "PD Dr. Ekkehart Lausch", "Robert-Koch-Str. 3", "D-79106", "Freiburg"};
	
	
	PrepareHeader headerInfo = new PrepareHeader(sender, receiver);
	
	
	
	
	create();
	addHeader(headerInfo);
	closeReport();
	
    }
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    
    
    //////// add Paragraph
    
    public void addHeader(PrepareHeader headerInfo) throws DocumentException, BadElementException, IOException  {
	
	///////////////////////////////////////////////
	//////// starting new PDF from scratch ////////
	///////////////////////////////////////////////
	
	////////////////
	//// add logo Paragraph
	
	
	//// Load UKL logo
	String sep = File.separator;
	String uklLogoPath = System.getProperty("user.dir") + sep + "Vorlagen" + sep + "ZKJKinderklinikJPG.jpg";
	Image uklLogo = Image.getInstance(uklLogoPath);
	uklLogo.scalePercent(10);
	
	uklLogo.setAlignment(Image.RIGHT);
	
	//// prepare paragraph and add logo
	Paragraph logo = new Paragraph();
	logo.add(new Chunk(uklLogo, 0, 0, true));
	logo.setAlignment(Element.ALIGN_RIGHT);
	logo.setSpacingAfter(10);
	report.add(logo);
	
	
	

	

	////////////////////
	//////// add address
	
	
	//// create sender rectangles
	
	
	
	
	
	
	
	
	
	
	/** 
	 * create table of two columns. 
	 * create 2 cells and fill them with sender and receiver
	 */
	
	// create table
	PdfPTable addressTable = new PdfPTable(2);
	addressTable.setTotalWidth(report.getPageSize().getWidth() - 50);
	addressTable.setLockedWidth(true);
	
	
	// create sender
	PdfPCell senderCell = new PdfPCell();
	senderCell.setHorizontalAlignment(Element.ALIGN_CENTER);
	senderCell.setVerticalAlignment(Element.ALIGN_LEFT);
	senderCell.addElement(headerInfo.getSender());
	senderCell.setBorder(Rectangle.NO_BORDER);
		
	
	
	// create receiver
	PdfPCell receiverCell = new PdfPCell();
	receiverCell.setHorizontalAlignment(Element.ALIGN_TOP);
	receiverCell.setVerticalAlignment(Element.ALIGN_RIGHT);
	receiverCell.addElement(new Paragraph(headerInfo.getReceiver()));
	receiverCell.setBorder(Rectangle.NO_BORDER);
		
	
	
	
	
	
	
	// add both cells to table and add table to report
	addressTable.addCell(receiverCell);
	addressTable.addCell(senderCell);
	
	report.add(addressTable);
	
	
	
	
	
	

	
	
	
	
    }
    
    
    
    
    
    //////////////////
    //////// open new PDF document 
    public void create() throws FileNotFoundException, DocumentException {
	

	// open PDF
	// prepare Document
	report = new Document(PageSize.A4);
	report.setMargins(20, 20, 20, 20);
		
	    
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

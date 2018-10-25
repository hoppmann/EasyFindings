/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

import com.lowagie.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 *
 * @author hoppmann
 */
public class TestDoc {

    


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
	
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    
    public TestDoc(String htmlCode) {
	
	
	
	OutputStream os = null;
	try {
	    os = new FileOutputStream("test.pdf");
	    ITextRenderer renderer = new ITextRenderer();
	    renderer.setDocumentFromString(htmlCode);
	    renderer.layout();
	    renderer.createPDF(os);
	    os.close();
	    
	    } catch (FileNotFoundException ex) {
	    Logger.getLogger(TestDoc.class.getName()).log(Level.SEVERE, null, ex);
	} catch (DocumentException ex) {
	    Logger.getLogger(TestDoc.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    Logger.getLogger(TestDoc.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    try {
		os.close();
	    } catch (IOException ex) {
		Logger.getLogger(TestDoc.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
////	File file = new FileChooser().showOpenDialog(null);
////	System.out.println(file.getAbsoluteFile());
////
//
//	    
//	try {
//
//	    // open template
//	    XWPFDocument doc = new XWPFDocument(OPCPackage.open("/home/hoppmann/Dropbox/Arbeit/EasyFindings/template.docx"));
//
//
//
//
//
//	    
////	    // creat table for the letter head
////	    XWPFTable letterHead = doc.createTable(1,2);
////	    letterHead.setInsideHBorder(XWPFTable.XWPFBorderType.NONE, 0, 0, rgbColor);
////
////	    // add sender
////	    XWPFTableRow row = letterHead.getRow(0);
////	    row.getCell(0).setText("Receiver");
////	    row.getCell(1).setText("Sender");
//
//
//	    // save report
//	    FileOutputStream out = new FileOutputStream(new File("/home/hoppmann/Dropbox/Arbeit/EasyFindings/testReport.docx"));
//	    doc.write(out);
//	    out.close();
//
//
//	    
//	} catch (IOException ex) {
//	    Logger.getLogger(TestDoc.class.getName()).log(Level.SEVERE, null, ex);
//	} catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException ex) {
//	    Logger.getLogger(TestDoc.class.getName()).log(Level.SEVERE, null, ex);
//	}


//	try {
//	    XWPFWordExtractor docx = new XWPFWordExtractor(
//		    POIXMLDocument.openPackage("test.docx"));
//	    String docxText = docx.getText();
//	    
//	    System.out.println(docxText);
//
//
//	    
////	    XWPFDocument doc = new XWPFDocument();
////	    XWPFParagraph p = doc.createParagraph();
////	    XWPFRun r = p.createRun();
////	    r.setText("Hallo darkness my old friend");
////	    r.setBold(true);
////	    
////	    r = p.createRun();
////	    r.setText("Don't Panic");
////	    
////	try {
////	    FileOutputStream out = new FileOutputStream(new File("test-out.docx"));
////	    doc.write(out);
////	    out.close();
////	} catch (IOException ex) {
////	    Logger.getLogger(TestDoc.class.getName()).log(Level.SEVERE, null, ex);
////	}
//	} catch (XmlException ex) {
//	    Logger.getLogger(TestDoc.class.getName()).log(Level.SEVERE, null, ex);
//	} catch (OpenXML4JException ex) {
//	    Logger.getLogger(TestDoc.class.getName()).log(Level.SEVERE, null, ex);
//	} catch (IOException ex) {
//	    Logger.getLogger(TestDoc.class.getName()).log(Level.SEVERE, null, ex);
//	}
	




    }
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    
    // open new document
    
	
	
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////










}

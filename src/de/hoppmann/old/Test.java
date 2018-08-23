/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.old;

import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;


/**
 *
 * @author hoppmann
 */
public class Test {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////

	
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////

//    public Test(Stage primaryStage, Parent root) {
    public Test(Stage primaryStage, Parent node) {

//    public Test(Node node) {
	
	
	Printer printer = Printer.getDefaultPrinter();
	PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
	
	double scaleX = pageLayout.getPrintableWidth() /
		node.getBoundsInParent().getWidth();
	
	double scaleY = pageLayout.getPrintableHeight() /
		node.getBoundsInParent().getHeight();
	
	node.getTransforms().add(new Scale(scaleX, scaleY));
	
	
	

	PrinterJob job = PrinterJob.createPrinterJob();
	if (job != null) {
	    job.showPrintDialog(primaryStage);
	    boolean success = job.printPage(node);
	    
	    if (success) {
		job.endJob();
	    }
	}
	
	
	
//	PrinterJob job =  PrinterJob.createPrinterJob();
//	job.showPrintDialog(primaryStage);
//	
//	System.out.println(job.printerProperty());
//	
//	
//	job.printPage(root);
	
	
//	if (job != null) {
//	    job.showPrintDialog(primaryStage);
//	    job.printPage(root);
//	    job.endJob();
//	    
//	}
	
    }
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

	
	
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////










}

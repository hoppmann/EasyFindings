/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;

/**
 *
 * @author hoppmann
 */
public class PrepareHeader {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
	
    private String receiverSalutation;
    private String receiverName;
    private String receiverFirstName;
    private String receiverStreet;
    private String receiverPostal;
    private String receiverCity;
    
    
    private String senderInstitute;
    private String senderName;
    private String senderStreet;
    private String senderPostal;
    private String senderCity;
    
    //// defining Fonts
    private final Font bigBold = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private final Font smallBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

    private final Font smallHeaderBold = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    private final Font smallHeader = new Font(Font.FontFamily.HELVETICA, 10);
    
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
	
    public PrepareHeader(String[] sender, String[] receiver) {
	
	// retrieve sender info
	this.receiverSalutation = receiver[0];
	this.receiverFirstName = receiver[1];
	this.receiverName = receiver[2];
	this.receiverStreet = receiver [3];
	this.receiverPostal = receiver[4];
	this.receiverCity = receiver[5];
	
	
	// retrieve reveiver info
	
	this.senderInstitute = sender[0];
	this.senderName = sender[1];
	this.senderStreet = sender[2];
	this.senderPostal = sender[3];
	this.senderCity = sender[4];
	
	
	
	
    }

	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    
    //// report sender header
    public Paragraph getReceiver() {
	
	Paragraph receiver = new Paragraph();
	receiver.add("Receiver");
	receiver.add(Chunk.NEWLINE);
	receiver.add(Chunk.NEWLINE);
        receiver.add(new Chunk(receiverFirstName + " " + receiverName, smallBold));
	receiver.add(Chunk.NEWLINE);
	receiver.add(receiverStreet);
	receiver.add(Chunk.NEWLINE);
	receiver.add(receiverPostal + " " + receiverCity);
	
	
	
	// return paragraph
	return receiver;
	
	
	
    }
    
    
    
    //// report receiver header
    public Paragraph getSender() {
	
	
	Paragraph sender = new Paragraph(); 
	sender.setLeading(13);
	sender.add(new Chunk("Medizinisches Versorgungszentrum", smallHeaderBold));
	sender.add(Chunk.NEWLINE);
	sender.add(new Chunk("Strahlentherapie", smallHeader));
	sender.add(Chunk.NEWLINE);
	sender.add(new Chunk("Humangenetik", smallHeader));
	sender.add(Chunk.NEWLINE);
	sender.add(new Chunk("Transfusionsmedizin", smallHeader));
	
	
	//// main part
	sender.add("Sender");
	sender.add(Chunk.NEWLINE);
	sender.add(Chunk.NEWLINE);
	sender.add(new Chunk(senderInstitute, bigBold));
	sender.add(Chunk.NEWLINE);
	sender.add(new Chunk(senderName, smallBold));
	sender.add(Chunk.NEWLINE);
	sender.add(senderStreet);
	sender.add(Chunk.NEWLINE);
	sender.add(senderPostal + " " + senderCity);
		
	
	// return paragraph
	return sender;
	
    }
    
	
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////

    public String getReceiverSalutation() {
	return receiverSalutation;
    }

    public void setReceiverSalutation(String receiverSalutation) {
	this.receiverSalutation = receiverSalutation;
    }

    public String getReceiverName() {
	return receiverName;
    }

    public void setReceiverName(String receiverName) {
	this.receiverName = receiverName;
    }

    public String getReceiverFirstName() {
	return receiverFirstName;
    }

    public void setReceiverFirstName(String receiverFirstName) {
	this.receiverFirstName = receiverFirstName;
    }

    public String getReceiverStreet() {
	return receiverStreet;
    }

    public void setReceiverStreet(String receiverStreet) {
	this.receiverStreet = receiverStreet;
    }

    public String getReceiverPostal() {
	return receiverPostal;
    }

    public void setReceiverPostal(String receiverPostal) {
	this.receiverPostal = receiverPostal;
    }

    public String getReceiverCity() {
	return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
	this.receiverCity = receiverCity;
    }

    public String getSenderInstitute() {
	return senderInstitute;
    }

    public void setSenderInstitute(String senderInstitute) {
	this.senderInstitute = senderInstitute;
    }

    public String getSenderName() {
	return senderName;
    }

    public void setSenderName(String senderName) {
	this.senderName = senderName;
    }

    public String getSenderStreet() {
	return senderStreet;
    }

    public void setSenderStreet(String senderStreet) {
	this.senderStreet = senderStreet;
    }

    public String getSenderPostal() {
	return senderPostal;
    }

    public void setSenderPostal(String senderPostal) {
	this.senderPostal = senderPostal;
    }

    public String getSenderCity() {
	return senderCity;
    }

    public void setSenderCity(String senderCity) {
	this.senderCity = senderCity;
    }









}

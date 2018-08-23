/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hoppmann
 */
public class ReportDataModel {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    
    //// placeholder
    
    private final String senderPH = "${sender}";
    private final String receiverNamePH = "${receiverName}";
    private final String reveiverHeaderPH = "${reiceiverHeader}";
    private final String receiverStreetPH = "${receiverStreet}";
    private final String receiverCityPH = "${receiverCity}";
    private final String uklLogoPH = "${uklLogo}";
    private final String diagMethodPH = "${diagnostikMethod}";
    private final String patientPH = "${patient}";
    private final String materialPH = "${material}";
    private final String indicationPH = "${indication}";
    private final String datePh = "${date}";
//    private final String ;
//    private final String ;
//    private final String ;
//    private final String ;
//    private final String ;
//    private final String ;
//    private final String ;
//    private final String ;
//    private final String ;
    
    
    
    
    
    //// replacement values
    private String receiverName;
    private String receiverCity;
    private String receiverStreet;
    private String uklLogo;
    private String diagMethod;
    private String patient;
    private String material;
    private String indication;



//    private String 
//    private String 
//    private String 
//    private String 
//    private String 
//    private String 
//    private String 
//    private String 



    
    // sender variables
    private Map <String,String> sender = new HashMap<>();
    private Map <String, String> receiverHeader = new HashMap<>();
    
    
    
    
    ////////////////////////////
    //////// costructor ////////
    ////////////////////////////

    public ReportDataModel() {
        
        prepareSender();
        prepareReceiverHeader();
        
        
        
        
        
        
        


    }

    /////////////////////////
    //////// methods ////////
    /////////////////////////

    // get current date
    public String getDate() {
        LocalDate localDate = LocalDate.now();
        return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(localDate);
    }
    
    
    // get receiver header dependeing on the key
    public String getReceiverHeader(String key) {
        return receiverHeader.get(key);
    }
    
    // get sender depending on the key
    public String getSender(String key){
        return sender.get(key);
    }

    

    
    
    // prepare sender
    private void prepareSender () {

    // sender choices
    sender.put("MVZ", "<p><strong>Medizinisches Versorgungszentrum</strong><br />Strahlentherapie<br />Humangenetik<br />Transfusionsmedizin</p>\n" +
"<p><strong>Humangenetik<br /></strong><strong>PD Dr. Ekkehart Lausch<br /></strong>Sektionsleiter P&auml;diatrische Genetik<br /> Robert-Koch-Str. 3, D-79106 Freiburg<br />Tel&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; +49 (0)761 270-94440<br />Fax&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; +49 (0)761 270-94030<br />E-Mail&nbsp;&nbsp; ekkehart.lausch@uniklinik-freiburg.de</p>");
        
    }
    
    
    // prepare receiver header choice
    private void prepareReceiverHeader () {
        
        receiverHeader.put("MVZ", "<p><strong>MVZ des Universit&auml;tsklinikums Freiburg<br /></strong>Robert-Koch-Stra&szlig;e 3, 79106 Freiburg</p>");
        
    }
    
    
    


    ///////////////////////////////
    //////// getter/setter ////////
    ///////////////////////////////
    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getSenderPH() {
        return senderPH;
    }

    public String getReceiverStreet() {
        return receiverStreet;
    }

    public void setReceiverStreet(String receiverStreet) {
        this.receiverStreet = receiverStreet;
    }

    public String getUklLogo() {
        return uklLogo;
    }

    public void setUklLogo(String uklLogo) {
        this.uklLogo = uklLogo;
    }

    public String getDiagMethod() {
        return diagMethod;
    }

    public void setDiagMethod(String diagMethod) {
        this.diagMethod = diagMethod;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getIndication() {
        return indication;
    }

    public void setIndication(String indication) {
        this.indication = indication;
    }

    public Map<String, String> getSender() {
        return sender;
    }

    public void setSender(Map<String, String> sender) {
        this.sender = sender;
    }

    public String getReceiverNamePH() {
        return receiverNamePH;
    }

    public String getReveiverHeaderPH() {
        return reveiverHeaderPH;
    }

    public String getReceiverStreetPH() {
        return receiverStreetPH;
    }

    public String getReceiverCityPH() {
        return receiverCityPH;
    }

    public String getUklLogoPH() {
        return uklLogoPH;
    }

    public String getDiagMethodPH() {
        return diagMethodPH;
    }

    public String getPatientPH() {
        return patientPH;
    }

    public String getMaterialPH() {
        return materialPH;
    }

    public String getIndicationPH() {
        return indicationPH;
    }

    public String getDatePh() {
        return datePh;
    }
    
    
    
    
}

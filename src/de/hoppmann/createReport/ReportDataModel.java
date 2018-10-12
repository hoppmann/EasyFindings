/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    private final String reveiverHeaderPH = "${reiceiverHeader}";
    private final String receiverNamePH = "${receiverName}";
    private final String receiverCoLinePH= "${c/o}";
    private final String receiverStreetPH = "${receiverStreet}";
    private final String receiverCityPH = "${receiverCity}";
    private final String uklLogoPH = "${uklLogo}";
    private final String diagMethodPH = "${diagnostikMethod}";
    private final String patientPH = "${patient}";
    private final String materialPH = "${material}";
    private final String indicationPH = "${indication}";
    private final String datePh = "${date}";
    private final String seqMethodPH = "${seqMethod}";
    private final String findingsPH = "${findings}";
    private final String assessmentPH = "${conclusion}";
    
    
    
    //// replacement values
    private String receiverName = "Name";
    private String receiverStreet = "Straße";
    private String receiverCity = "Stadt";
    private String receiverCoLine = "";
    private String uklLogo = "NA";
    private String patientInfo = "Patient";
    private String material = "Material";
    private String indication = "Indikation";
    private String findingsGeneTable = "NA";
    private String assessment = "NA";


    
    // hashed variables
    private Map <String, String> sender = new HashMap<>();
    private String senderKey = "MVZ";
    private Map <String, String> receiverHeader = new HashMap<>();
    private String receiverHeaderKey = "MVZ";
    private Map <String, String> seqMethod = new HashMap<>();
    private String seqMethodKey = "NGS_KiKli";
    private Map <String, String> diagMethod = new HashMap<>();
    private String diagMethodKey = "NGS_panel";
    
    
    
    
    // gene panel information
    
    
    
    
    ////////////////////////////
    //////// costructor ////////
    ////////////////////////////

    public ReportDataModel() {
        
        prepareSender();
        prepareReceiverHeader();
        prepareSeqMethod();
	prepareDiagMethods();
        

    }

    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    // get current date
    public String getDate() {
        LocalDate localDate = LocalDate.now();
        return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(localDate);
    }
    
    
    
    
    
    //// prepare hash containing diagnostic methods
    private void prepareDiagMethods () {
	diagMethod.put("NGS_panel", "Multi-Gen-Panel-Analyse mittels Next Generation Sequencing (NGS)");
    }
    
    
    
    
    
    // prepare sender
    private void prepareSender () {

    // MVZ sender
    sender.put("MVZ", "<p>"
	    + "<strong>"
	    + "<font size = 1>"
	    + "Medizinisches Versorgungszentrum"
	    + "</strong><br>Strahlentherapie<br>Humangenetik<br>Transfusionsmedizin</font></p>"
	    + "<p><strong>Humangenetik<br></strong>"
	    + "<strong>PD Dr. Ekkehart Lausch<br>"
	    + "</strong>Sektionsleiter P&auml;diatrische Genetik<br> "
	    + "Robert-Koch-Str. 3, D-79106 Freiburg<br>"
	    + "Tel&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; +49 (0)761 270-94440<br>"
	    + "Fax&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; +49 (0)761 270-94030<br/>"
	    + "<small>E-Mail&nbsp;&nbsp;  ekkehart.lausch@uniklinik-freiburg.de</small></p>");
        
    
    sender.put("KiKli", "Another Adress");
    
    
    
    
    
    }
    
    
    
    
    
    
    // prepare receiver header choice
    private void prepareReceiverHeader () {
        
        receiverHeader.put("MVZ", "<p><strong>MVZ des Universit&auml;tsklinikums Freiburg<br /></strong>Robert-Koch-Stra&szlig;e 3, 79106 Freiburg</p>");
        
    }
    
    
    
    
    
    
    // prepar hash containing possilbe sequencing method descriptions
    private void prepareSeqMethod () {
        
        seqMethod.put("NGS_KiKli", "DNA-Extraktion. Multi-Gen-Panel-Analyse nach GOP-Ziffer 11513 (Panelgröße 22.689 kb). Die kodierenden Bereiche sowie die angrenzenden Intronbereiche der im Anhang aufgeführten Gene wurden mittels Agilent/SureSelect XT angereichert und anschließend mittels Hochdurchsatz-Sequenzierung auf einem Illumina/ NextSeq-System sequenziert. Eventuell festgestellte, bekannte Polymorphismen mit einer Frequenz ≥ 1% (rezessiver Erbgang) bzw. ≥ 0,2% (dominanter Erbgang) werden als Normalbefund gewertet. Die nachgewiesene pathogene Variante wurde mittels PCR-Amplifikation und direkter Sanger-Sequenzierung des betreffenden Exons und der angrenzenden Intronbereiche methodisch unabhängig bestätigt. Die Untersuchung wurde im Labor der Sektion für pädiatrische Genetik im Zentrum für Kinder- und Jugendmedizin (ZKJ) des Universitätsklinikums Freiburg durchgeführt.");
    }
    


    ///////////////////////////////
    //////// getter/setter ////////
    ///////////////////////////////
    
    
    //// Hash tables and related 
    
    public Map<String, String> getSender() {
        return sender;
    }

    public Map<String, String> getReceiverHeader() {
	return receiverHeader;
    }

    public Map<String, String> getSeqMethod() {
	return seqMethod;
    }

    public Map<String, String> getDiagMethod() {
	return diagMethod;
    }

    public String getSenderKey() {
	return senderKey;
    }

    public void setSenderKey(String senderKey) {
	this.senderKey = senderKey;
    }

    public String getReceiverHeaderKey() {
	return receiverHeaderKey;
    }

    public void setReceiverHeaderKey(String receiverHeaderKey) {
	this.receiverHeaderKey = receiverHeaderKey;
    }

    public String getSeqMethodKey() {
	return seqMethodKey;
    }

    public void setSeqMethodKey(String seqMethodKey) {
	this.seqMethodKey = seqMethodKey;
    }

    public String getDiagMethodKey() {
	return diagMethodKey;
    }

    public void setDiagMethodKey(String diagMethokey) {
	this.diagMethodKey = diagMethokey;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //// general getter and setter
    
    
    public String getReceiverName() {
        return receiverName;
    }

    public String getSeqMethodPH() {
        return seqMethodPH;
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

    public String getPatientInfo() {
        return patientInfo;
    }

    public void setPatientInfo(String patient) {
        this.patientInfo = patient;
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


    public String getFindingsGeneTable() {
	return findingsGeneTable;
    }

    public void setFindingsGeneTable(String findingsGeneTable) {
	this.findingsGeneTable = findingsGeneTable;
    }

    public String getReceiverCoLine() {
	return receiverCoLine;
    }

    public void setReceiverCoLine(String receiverCoLine) {
	this.receiverCoLine = receiverCoLine;
    }

    public String getAssessment() {
	return assessment;
    }

    public void setAssessment(String assessment) {
	this.assessment = assessment;
    }
    
    
    
    
    
    
    
    
    
    
    
    //////// place holder name getter

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

    public String getFindingsPH() {
	return findingsPH;
    }

    public String getReceiverCoLinePH() {
	return receiverCoLinePH;
    }

    public String getAssessmentPH() {
	return assessmentPH;
    }

    
    
    
    
    
    
    
    
}

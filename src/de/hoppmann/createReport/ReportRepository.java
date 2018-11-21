/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

import de.hoppmann.database.userDB.receiverDB.IAddressInfo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author hoppmann
 */
public class ReportRepository implements IAddressInfo{


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    
    //// placeholder
    private final String senderPH = "${sender}";
    private final String reveiverHeaderPH = "${reiceiverHeader}";
    private final String receiverAddressPH = "${receiverAddress}";
    private final String receiverCoLinePH= "${c/o}";
    private final String uklLogoPH = "${uklLogo}";
    private final String diagMethodPH = "${diagnostikMethod}";
    private final String patientPH = "${patient}";
    private final String materialPH = "${material}";
    private final String indicationPH = "${indication}";
    private final String datePh = "${date}";
    private final String seqMethodPH = "${seqMethod}";
    private final String findingsPH = "${findings}";
    private final String assessmentPH = "${conclusion}";
    private final String genePanelTablePH = "${genePanelTable}";
    private final String materialArrivalDatePH = "${materialReceival}";
    
    
    
    
    //// replacement values
    // Receiver address
    private int receiverID;
    private String receiverTitle = "";
    private String receiverName = "Name";
    private String receiverAddress = "Straße";
    private String receiverCity = "Stadt";
    private String receiverZipCode = "";
    private String receiverCountry = "";
    
    
    
    
    
    // Other Entries
    private String receiverCoLine = "";
    private String uklLogo = "NA";
    private String patientInfo = "Patient";
    private String material = "Material";
    private String indication = "Indikation";
    private String findingsGeneTable = "NA";
    private String assessment = "NA";
    private String genePanelTable = "NA";
    private String materialArrivalDate = "NA";


    
    // hashed variables
    private Map <String, String> sender = new HashMap<>();
    private String senderKey = "MVZ";
    private Map <String, String> receiverHeader = new HashMap<>();
    private Map <String, String> seqMethod = new HashMap<>();
    private String seqMethodKey = "NGS_KiKli";
    private Map <String, String> diagMethod = new HashMap<>();
    private String diagMethodKey = "NGS_panel";
    
    

    // gene panelGeneList information
//    private List genePanelList = new LinkedList();
    private Set panelGeneList = new TreeSet();
    private String panelName;


    
    




    // misc
    private String report;
    private String billingReport;





    
    
    ////////////////////////////
    //////// costructor ////////
    ////////////////////////////

    public ReportRepository() {
        
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
	    + "<font size = 1>"
	    + "<strong>Medizinisches Versorgungszentrum</strong>"
	    + "<br>Strahlentherapie"
	    + "<br>Humangenetik"
	    + "<br>Transfusionsmedizin"
	    + "</font></p>"
	    + "<p>"
	    + "<strong>Humangenetik</strong>"
	    + "<br><strong>PD Dr. Ekkehart Lausch"
	    + "<br></strong>Sektionsleiter Pädiatrische Genetik"
	    + "<br>Robert-Koch-Str. 3, D-79106 Freiburg"
	    + "<br>Tel&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; +49 (0)761 270-94440"
	    + "<br>Fax&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; +49 (0)761 270-94030"
	    + "<br><small>E-Mail&nbsp;&nbsp;  ekkehart.lausch@uniklinik-freiburg.de</small>"
	    + "</p>"); 
        
    
    sender.put("KiKli", "Another Adress");
    
    
    
    
    
    }
    
    
    
    
    
    
    // prepare receiver header choice
    private void prepareReceiverHeader () {
        
        receiverHeader.put("MVZ", "<p><small><strong>MVZ des Universitätsklinikums Freiburg<br /></strong>Robert-Koch-Straße 3, 79106 Freiburg</small></p>");
        
	
	receiverHeader.put("KiKli", "<p><small><strong>Something else</strong></small></p>");
	
	
    }
    
    
    
    
    
    
    // prepar hash containing possilbe sequencing method descriptions
    private void prepareSeqMethod () {
        
        seqMethod.put("NGS_KiKli", "DNA-Extraktion. Multi-Gen-Panel-Analyse nach GOP-Ziffer 11513 (Panelgröße 22.689 kb). Die kodierenden Bereiche sowie die angrenzenden Intronbereiche der im Anhang aufgeführten Gene wurden mittels Agilent/SureSelect XT angereichert und anschließend mittels Hochdurchsatz-Sequenzierung auf einem Illumina/ NextSeq-System sequenziert. Eventuell festgestellte, bekannte Polymorphismen mit einer Frequenz ≥ 1% (rezessiver Erbgang) bzw. ≥ 0,2% (dominanter Erbgang) werden als Normalbefund gewertet. Die nachgewiesene pathogene Variante wurde mittels PCR-Amplifikation und direkter Sanger-Sequenzierung des betreffenden Exons und der angrenzenden Intronbereiche methodisch unabhängig bestätigt. Die Untersuchung wurde im Labor der Sektion für pädiatrische Genetik im Zentrum für Kinder- und Jugendmedizin (ZKJ) des Universitätsklinikums Freiburg durchgeführt.");
    }
    

    
    
    
        @Override
    public void clearStorage() {
        
        receiverTitle = "";
        receiverName = "";
        receiverAddress = "";
        receiverCity = "";
        receiverZipCode = "";
        receiverCountry = "";
        receiverID = -1;
    }

    
    
    

    ///////////////////////////////
    //////// getter/setter ////////
    ///////////////////////////////
    
    
    //// Hash tables and related 
    
    public Map<String, String> getSender() {
        return sender;
    }

    
        public String getSenderKey() {
	return senderKey;
    }

    public void setSenderKey(String senderKey) {
	this.senderKey = senderKey;
    }

    
    public Map<String, String> getReceiverHeader() {
	return receiverHeader;
    }

    
    
    
    
    
    
    public String getSeqMethodKey() {
	return seqMethodKey;
    }

    public void setSeqMethodKey(String seqMethodKey) {
	this.seqMethodKey = seqMethodKey;
    }

    
    public Map<String, String> getSeqMethod() {
	return seqMethod;
    }

    
    
    
    
    
    public String getDiagMethodKey() {
	return diagMethodKey;
    }

    public void setDiagMethodKey(String diagMethokey) {
	this.diagMethodKey = diagMethokey;
    }

    public Map<String, String> getDiagMethod() {
	return diagMethod;
    }




    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    //// general getter and setter
    
    


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
	
	// replace ";" by "<br> -> for cases of multiple adresses
	this.receiverCoLine = receiverCoLine.replaceAll(";", "<br>");
    }

    public String getAssessment() {
	return assessment;
    }

    public void setAssessment(String assessment) {
	this.assessment = assessment;
    }

    public String getGenePanelTable() {
	return genePanelTable;
    }

    public void setGenePanelTable(String genePanelTable) {
	this.genePanelTable = genePanelTable;
    }

    public String getMaterialArrivalDate() {
	return materialArrivalDate;
    }

    public void setMaterialArrivalDate(String materialArrivalDate) {
	this.materialArrivalDate = materialArrivalDate;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /////////////////////
    //////// Address Part
    
    @Override
    public String getReceiverTitle() {
	return receiverTitle;
    }

    @Override
    public void setReceiverTitle(String title) {
	this.receiverTitle = title;
    }

    @Override
    public String getReceiverZipCode() {
	return receiverZipCode;
    }

    @Override
    public void setReceiverZipCode(String zipCode) {
	this.receiverZipCode = zipCode;
    }

    @Override
    public String getReceiverCountry() {
	return receiverCountry;
    }

    @Override
    public void setReceiverCountry(String country) {
	this.receiverCountry = country;
    }

    @Override
    public String getReceiverName() {
        return receiverName;
    }

    @Override
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public String getReceiverCity() {
        return receiverCity;
    }

    @Override
    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    
    @Override
    public String getReceiverAddress() {
        return receiverAddress;
    }

    @Override
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
    
        @Override
    public int getReceiverId() {
        return receiverID;
    }

    @Override
    public void setReceiverId(int receiverID) {
        this.receiverID = receiverID;
    }

    
    
    
    
    ////////////////////
    //////// Panel Genes

    public Set getPanel() {
        return panelGeneList;
    }

    public void setPanel(Set panel) {
        this.panelGeneList = panel;
    }

    public void addGeneToPanel (String newGene){
        panelGeneList.add(newGene);
    }
    
    
    
    
    ///////////////
    //////// Report
    
    
    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getBillingReport() {
        return billingReport;
    }

    public void setBillingReport(String billingReport) {
        this.billingReport = billingReport;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //////// place holder name getter
    public String getReveiverHeaderPH() {
        return reveiverHeaderPH;
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

    public String getGenePanelTablePH() {
	return genePanelTablePH;
    }

    public String getMaterialRecievalDatePH() {
	return materialArrivalDatePH;
    }

    public String getReceiverAddressPH() {
	return receiverAddressPH;
    }
 
    public String getSenderPH() {
        return senderPH;
    }

    public String getSeqMethodPH() {
        return seqMethodPH;
    }



    
    
    
    
    
    
    
    
    
    
    
    
    
    
}

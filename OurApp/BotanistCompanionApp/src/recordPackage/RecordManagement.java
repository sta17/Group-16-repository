package recordPackage;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import recordPackage.Record;
import recordPackage.Record.DAFORLEVEL;

public class RecordManagement {

	// Variables

	private Date time;
	private String userName;
	private String email;
	private int phoneNumber;

	private List<Record> records = new ArrayList<Record>();
	private Record currentRecord;

	public RecordManagement(List<Record> records) {
		super();
		this.records = records;
	}

	public RecordManagement() {
	}

	// General Methods for Class

	// methods on detailsSubmit

	public void setTime(Date date) {
		this.time = date;
	}

	public Date getTime() {
		return this.time;
	}

	public void setName(String name) {
		this.userName = name;
	}

	public String getName() {
		return userName;
	}

	public void setNumber(int number) {
		this.phoneNumber = number;
	}

	public int getNumber() {
		return this.phoneNumber;
	}

	public void setEmail(String mail) {
		this.email = mail;
	}

	public String getEmail() {
		return this.email;
	}

	// methods in recordInputActivity IN ORDER OF USE
	/*
	public void newRecord(String plant) {
		currentRecord = new Record(plant);
	}
 	*/
	public void setDAFOR(DAFORLEVEL dAFOR) {
		currentRecord.setDAFOR(dAFOR);
	}

	public DAFORLEVEL getDAFOR() {
		return currentRecord.getDAFOR();
	}

	public void setComment(String comment) {
		currentRecord.setComment(comment);
	}

	public String getComment() {
		return currentRecord.getComment();
	}

	public void setPlantLatin(String PlantLatin) {
		currentRecord.setPlantLatin(PlantLatin);
		;
	}

	public String getPlantLatin() {
		return currentRecord.getPlantLatin();
	}

	// LOCATION

	public double getLatitude() {
		return currentRecord.getLatitude();
	}

	public void setLatitude(double latitude) {
		currentRecord.setLatitude(latitude);
		;
	}

	public double getLongitude() {
		return currentRecord.getLongitude();
	}

	public void setLongitude(double longitude) {
		currentRecord.setLongitude(longitude);
		;
	}

	// Array management

	public void addRecordToList() {
		// Send to SQLite??
		records.add(currentRecord);
		currentRecord = new Record();
	}

	public List<Record> getAllRecords() {
		return records;
	}

	public void ImportRecordList(ArrayList<Record> records) {
		this.records = records;
	}

	public Record getCurrentRecord() {
		return currentRecord;
	}

	public void editARecord(Record currentRecord) {
		for (Iterator<Record> i = records.iterator(); i.hasNext();) {
			Record item = i.next();
			if (currentRecord == item) {
				records.remove(currentRecord);
				break;
			}
		}
		this.currentRecord = currentRecord;
	}

	// Setters and Getters for Current record

	public Date getEditdate() {
		return currentRecord.getEditdate();
	}

	public void setEditdate(Date editdate) {
		currentRecord.setEditdate(editdate);
	}

	public boolean isUploaded() {
		return currentRecord.isUploaded();
	}

	public void setUploaded(boolean uploaded) {
		currentRecord.setUploaded(uploaded);
	}

	public String getSceneIMGPath() {
		return currentRecord.getSceneIMGPath();
	}

	public void setSceneIMGPath(String sceneIMGPath) {
		currentRecord.setSceneIMGPath(sceneIMGPath);
		;
	}

	public String getSpecimenIMGPath() {
		return currentRecord.getSpecimenIMGPath();
	}

	public void setSpecimenIMGPath(String SpecimenIMGPath) {
		currentRecord.setSpecimenIMGPath(SpecimenIMGPath);
	}

	/**
	 * 
	 * @return true if success, false for anything else
	 */
	public boolean save() {
		return false;

	}

	/**
	 * 
	 * @return true if success, false for anything else
	 */
	public boolean load(){
		return false;
		
	}
	
}
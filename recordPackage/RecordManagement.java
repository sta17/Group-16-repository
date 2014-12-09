package recordPackage;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import recordPackage.Record.DAFORLEVEL;

public class RecordManagement {

	//Variables
	
	private List<Record> records = new ArrayList<Record>();
	private Record currentRecord;
	
	public RecordManagement(List<Record> records) {
		super();
		this.records = records;
	}

	//General Methods for Class
	
	public void newRecord(){
		currentRecord = new Record();
	}
	
	public void addRecordToList(){
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
		for(Iterator<Record> i = records.iterator(); i.hasNext(); ) {
			Record item = i.next();
			if(currentRecord == item){
				records.remove(currentRecord);
				break;
			}
		}
		this.currentRecord = currentRecord;
	}
	
	//Setters and Getters for Current record
	
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
	
	public String getComment() {
		return currentRecord.getComment();
	}

	public void setComment(String comment) {
		currentRecord.setComment(comment);
	}

	public String getPlantLatin() {
		return currentRecord.getPlantLatin();
	}

	public void setPlantLatin(String PlantLatin) {
		currentRecord.setPlantLatin(PlantLatin);;
	}
	
	public String getPlantCommon() {
		return currentRecord.getPlantCommon();
	}

	public void setPlantCommon(String PlantCommon) {
		currentRecord.setPlantCommon(PlantCommon);
	}

	public DAFORLEVEL getDAFOR() {
		return currentRecord.getDAFOR();
	}

	public void setDAFOR(DAFORLEVEL dAFOR) {
		currentRecord.setDAFOR(dAFOR);
	}

	public double getLatitude() {
		return currentRecord.getLatitude();
	}

	public void setLatitude(double latitude) {
		currentRecord.setLatitude(latitude);;
	}

	public double getLongitude() {
		return currentRecord.getLongitude();
	}

	public void setLongitude(double longitude) {
		currentRecord.setLongitude(longitude);;
	}

	public String getSceneIMGPath() {
		return currentRecord.getSceneIMGPath();
	}

	public void setSceneIMGPath(String sceneIMGPath) {
		currentRecord.setSceneIMGPath(sceneIMGPath);;
	}

	public String getSpecimenIMGPath() {
		return currentRecord.getSpecimenIMGPath();
	}

	public void setSpecimenIMGPath(String SpecimenIMGPath) {
		currentRecord.setSpecimenIMGPath(SpecimenIMGPath);
	}
	
}

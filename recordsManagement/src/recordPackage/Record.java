package recordPackage;

import java.io.File;
import java.util.Date;

public class Record{

	//Variables
	
	private String comment;
	private String PlantLatin;
	private String PlantCommon;
	private DAFORLEVEL DAFOR;
	private double latitude;
	private double longitude;
	private File SpecimenIMG;
	private File sceneIMG;
	private Date editdate = new Date();
	private boolean uploaded;

	//Constructors
	
	public Record(String comment, String PlantLatin, String PlantCommon, DAFORLEVEL dAFOR,
			double latitude, double longitude, File SpecimenIMG, File sceneIMG) {
		super();
		this.comment = comment;
		this.PlantLatin = PlantLatin;
		this.PlantCommon = PlantCommon;
		DAFOR = dAFOR;
		this.latitude = latitude;
		this.longitude = longitude;
		this.SpecimenIMG = SpecimenIMG;
		this.sceneIMG = sceneIMG;
	}
	
	public Record() {}
	
	//Setters and Getters
	
	public Date getEditdate() {
		return editdate;
	}

	public void setEditdate(Date editdate) {
		this.editdate = editdate;
	}

	public boolean isUploaded() {
		return uploaded;
	}

	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPlantLatin() {
		return PlantLatin;
	}

	public void setPlantLatin(String PlantLatin) {
		this.PlantLatin = PlantLatin;
	}
	
	public String getPlantCommon() {
		return PlantCommon;
	}

	public void setPlantCommon(String PlantCommon) {
		this.PlantCommon = PlantCommon;
	}

	public DAFORLEVEL getDAFOR() {
		return DAFOR;
	}

	public void setDAFOR(DAFORLEVEL dAFOR) {
		DAFOR = dAFOR;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public File getSceneIMG() {
		return sceneIMG;
	}

	public void setSceneIMG(File sceneIMG) {
		this.sceneIMG = sceneIMG;
	}

	public File getSpecimenIMG() {
		return SpecimenIMG;
	}

	public void setSpecimenIMG(File SpecimenIMG) {
		this.SpecimenIMG = SpecimenIMG;
	}
	
	// enum
	
	public enum DAFORLEVEL {
		Dominant
		,Abundant
		,Frequent
		,Occasional
		,Rare
	}
}
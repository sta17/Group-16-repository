package recordPackage;
import java.io.Serializable;
import java.util.Date;
public class Record implements Serializable{
//Variables
private String comment;
private String PlantLatin;
private String PlantCommon;
private DAFORLEVEL DAFOR;
private double latitude;
private double longitude;
private String SpecimenIMGPath;
private String sceneIMGPath;
private Date editdate = new Date();
private boolean uploaded;
//Constructors
public Record(String comment, String PlantLatin, String PlantCommon, DAFORLEVEL dAFOR,
double latitude, double longitude, String SpecimenIMGPath, String sceneIMGPath) {
super();
this.comment = comment;
this.PlantLatin = PlantLatin;
this.PlantCommon = PlantCommon;
DAFOR = dAFOR;
this.latitude = latitude;
this.longitude = longitude;
this.SpecimenIMGPath = SpecimenIMGPath;
this.sceneIMGPath = sceneIMGPath;
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
public String getSceneIMGPath() {
return sceneIMGPath;
}
public void setSceneIMGPath(String sceneIMGPath) {
this.sceneIMGPath = sceneIMGPath;
}
public String getSpecimenIMGPath() {
return SpecimenIMGPath;
}
public void setSpecimenIMGPath(String SpecimenIMGPath) {
this.SpecimenIMGPath = SpecimenIMGPath;
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
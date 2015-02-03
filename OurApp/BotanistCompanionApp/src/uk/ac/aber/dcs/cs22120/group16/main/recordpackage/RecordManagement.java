package uk.ac.aber.dcs.cs22120.group16.main.recordpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import android.os.Environment;
import uk.ac.aber.dcs.cs22120.group16.main.recordpackage.Record;
import uk.ac.aber.dcs.cs22120.group16.main.recordpackage.Record.DAFORLEVEL;

/**
 * 
 * @(#)EditRecord.java 2.2 2015-01-27
 * 
 * Copyright(c)2013 Aberystwyth University.
 * All rights reserved.
 * 
 * @author Steven(Sta17)
 * @since 1.0
 * @version 2.2
 * @see Record
 * 
 * This is for turning a record(class) into a row for a listview
 */
public class RecordManagement {

	// Variables

	private Date time;
	private String userName;
	private String email;
	private int phoneNumber;

	private ArrayList<Record> records = new ArrayList<Record>();
	private Record currentRecord;

	public RecordManagement(ArrayList<Record> records) {
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

	public void removeARecord(Record toremove){
		for (Iterator<Record> i = records.iterator(); i.hasNext();) {
			Record item = i.next();
			if (toremove == item) {
				records.remove(toremove);
				break;
			}
		}
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
     * Save mechanism, saves information in to a file, that matches parameter filename.
     * @param fileName - the name of the file to which the information is stored.
	 * @return 
	 * @throws IOException 
     */
    public boolean TempSave() throws IOException{
        try{
        	File storageDir = new File(Environment.getExternalStorageDirectory()
    				+ "/BotanistApp");
    		if (!storageDir.isDirectory() && !storageDir.exists()) {
    			File Directory = new File(Environment.getExternalStorageDirectory()
    					+ "/BotanistApp/");
    			Directory.mkdirs();
    		}
    		String FileName = "recordfile";
    		File recordfile = File.createTempFile(FileName, /* prefix */
    				".txt", /* suffix */
    				storageDir /* directory */
    		);
        	String path = recordfile.getPath();
        	
            PrintWriter outfile = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path)));
            outfile.println(records.size());
            for (Record r:records) {
                outfile.println(r.getComment());
                outfile.println(r.getLatitude());
                outfile.println(r.getLongitude());
                outfile.println(r.getPlantCommon());
                outfile.println(r.getPlantLatin());
                outfile.println(r.getRecordname());
                outfile.println(r.getSceneIMGPath());
                outfile.println(r.getSpecimenIMGPath());
                outfile.println(r.getEditdate());
                DAFORLEVEL daf = r.getDAFOR();
    			String input = daf.name();
                outfile.println(input);
                outfile.println(r.isUploaded());
            }
            outfile.close();
            return true;
        }
        catch (IOException e) {
            throw e;
        }
        catch(RuntimeException e) {
            throw e;
        }
    }

    /**
     * load mechanism, loads information from a file, that matches parameter filename.
     * @param fileName - the name of the file from which the information is retrieved.
     * @throws IOException 
     * @throws ParseException 
     */
    public boolean Tempload() throws IOException, ParseException{
        try{
        	File storageDir = new File(Environment.getExternalStorageDirectory()
    				+ "/BotanistApp");
        	
    		if (!storageDir.isDirectory() && !storageDir.exists()) {
    			File Directory = new File(Environment.getExternalStorageDirectory()
    					+ "/BotanistApp/");
    			Directory.mkdirs();
    			return false;
    		}
    		
    		String path = Environment.getExternalStorageDirectory()+ "/BotanistApp/recordfile.txt";
    		File f = new File(path);
    		if(!f.exists()){
    			return false;
    		}
        	
            Scanner infile =new Scanner(new InputStreamReader(new FileInputStream(path)));
            int num=infile.nextInt();infile.nextLine();
            if(num == 0){
            	return false;
            }
            
            for (int i=0;i<num;i++) {
                String comment = infile.nextLine();
                String latitudeString = infile.nextLine();
                String longtitudeString = infile.nextLine();
                String common = infile.nextLine();
                String latin = infile.nextLine();
                String recordname = infile.nextLine();
                String sceneIMGPath = infile.nextLine();
                String SpecimenIMGPath = infile.nextLine();
                String datestring = infile.nextLine();
                String daforstring = infile.nextLine();
                String booleanString = infile.nextLine();
                
                Record r = new Record();
                r.setComment(comment);
                r.setPlantCommon(common);
                r.setPlantLatin(latin);
                r.setRecordname(recordname);
                r.setSceneIMGPath(sceneIMGPath);
                r.setSpecimenIMGPath(SpecimenIMGPath);
                if(booleanString == "true"){
                	r.setUploaded(true);
                }else {
                	r.setUploaded(false);
                }
                double latitude= Double.parseDouble(latitudeString);
                r.setLatitude(latitude);
                double longtitude = Double.parseDouble(longtitudeString);
                r.setLongitude(longtitude);
                
                DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
                Date date = format.parse(datestring);
                r.setEditdate(date);
                
                if(daforstring == "Dominant"){
                	r.setDAFOR(Record.DAFORLEVEL.Dominant);
                } else if (daforstring == "Abundant") {
                	r.setDAFOR(Record.DAFORLEVEL.Abundant);
                } else if (daforstring == "Frequent") {
                	r.setDAFOR(Record.DAFORLEVEL.Frequent);
                } else if (daforstring == "Occasional") {
                	r.setDAFOR(Record.DAFORLEVEL.Occasional);
                } else {
                	r.setDAFOR(Record.DAFORLEVEL.Rare);
                }
                records.add(r);
            }
            infile.close();
            return true;
        }
        catch(IOException e) {
            throw e;
        }
        catch (InputMismatchException e) {
        	throw e;
        }
        catch(RuntimeException e) {
        	throw e;
        }
    }
	
}
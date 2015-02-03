package uk.ac.aber.dcs.cs221.group16.main.recordpackage;

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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import uk.ac.aber.dcs.cs221.group16.main.recordpackage.Record;
import uk.ac.aber.dcs.cs221.group16.main.recordpackage.Record.DAFORLEVEL;

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

	private static final String PREFS_NAME = "BOTANIST";
	private ArrayList<Record> records = new ArrayList<Record>();
	private Record currentRecord;

	/**
	 * constructor
	 * 
	 * @param records list to manipulate
	 */
	public RecordManagement(ArrayList<Record> records) {
		super();
		this.records = records;
	}

	/**
	 * constructor for when list not needed
	 */
	public RecordManagement() {
	}

	// setters and getters for the currentRecord variable

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

	// LOCATION setters and getters

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

	/**
	 * add the currentRecord to the recordlist
	 *  and the wipes it for safety
	 */
	public void addRecordToList() {
		// Send to SQLite??
		records.add(currentRecord);
		currentRecord = new Record();
	}

	/**
	 * 
	 * returns the current list held by this RecordManagement class
	 * 
	 * @return records the current record list
	 */
	public List<Record> getAllRecords() {
		return records;
	}

	/**
	 * imports a list to this RecordManagement class
	 * 
	 * @param records the recordlist
	 */
	public void ImportRecordList(ArrayList<Record> records) {
		this.records = records;
	}

	/**
	 * Returns the current record that this RecordManagement class is manipulating
	 * 
	 * @return currentRecord returns the current record that this RecordManagement class is manipulating
	 */
	public Record getCurrentRecord() {
		return currentRecord;
	}

	/**
	 * 
	 * removes the record sent thought method if the there is a matching record.
	 * 
	 * @param toremove removes matching record
	 */
	public void removeARecord(Record toremove){
		for (Iterator<Record> i = records.iterator(); i.hasNext();) {
			Record item = i.next();
			if (toremove == item) {
				records.remove(toremove);
				break;
			}
		}
	}
	
	/**
	 * sets the parameter to be the current record, checks if it already exit and then removes it, to prevent duplication.
	 * 
	 * @param currentRecord sets the current record
	 */
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

	public String getEditdate() {
		return currentRecord.getEditdate();
	}

	public void setEditdate(String editdate) {
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

	public String getSiteName() {
		return currentRecord.getSiteName();
	}

	public void setSiteName(String siteName) {
		currentRecord.setSiteName(siteName);
	}
	
	 /**
     * Save mechanism, saves information in to a file, checks if folder exists, 
     * if it does saves the file there.
     * 
     * @param fileName - the name of the file to which the information is stored.
	 * @return true if successfull, false if otherwise.
	 * @throws IOException rethrows the IOException one step up.
	 * @throws RuntimeException rethrows the IOException one step up.
     */
    public boolean TempSave(Context cont) throws IOException{
        try{
        	File storageDir = new File(Environment.getExternalStorageDirectory()
    				+ "/BotanistApp");
    		if (!storageDir.isDirectory() && !storageDir.exists()) {
    			File Directory = new File(Environment.getExternalStorageDirectory()
    					+ "/BotanistApp/");
    			Directory.mkdirs();
    		}
    		String FileName = "recordfile";
    		//File recordfile = File.createTempFile(FileName, /* prefix */
    		//		".txt", /* suffix */
    		//		storageDir /* directory */
    		//);
    		File recordfile = new File(storageDir+"/", FileName+".txt");
    		
        	String path = recordfile.getPath();
        	
            PrintWriter outfile = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path)));
            outfile.println(records.size());
            for (Record r:records) {
            	if((r.getComment() != null)){
            		outfile.println(r.getComment());
            	} else{
            		outfile.println("No comment");
            	}
                outfile.println(r.getLatitude());
                outfile.println(r.getLongitude());
                if((r.getPlantCommon() != null)){
                	outfile.println(r.getPlantCommon());
            	} else{
            		outfile.println("Not picked");
            	}
                if((r.getPlantLatin() != null)){
                	outfile.println(r.getPlantLatin());
            	} else{
            		outfile.println("Not picked");
            	}
                outfile.println(r.getRecordname());
                
                if((r.getSceneIMGPath() != null)){
                	outfile.println(r.getSceneIMGPath());
            	} else{
            		outfile.println("No image taken");
            	}
                if((r.getSpecimenIMGPath() != null)){
                	
                	outfile.println(r.getSpecimenIMGPath());
            	} else{
            		outfile.println("No image taken");
            	}
                
                outfile.println(r.getEditdate());
                
                String input;
                DAFORLEVEL daf = r.getDAFOR();
                input = daf.name();
                outfile.println(input);
                outfile.println(r.isUploaded());
                /*
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
                */
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
     * load mechanism, loads information from a file, checks if folder it is 
     * supposed to load from exists, if it does saves the file there.
     * 
     * @return true if successfull, false if otherwise.
	 * @throws IOException rethrows the IOException one step up.
	 * @throws InputMismatchException rethrows the IOException one step up.
	 * @throws RuntimeException rethrows the IOException one step up.
     */
    public boolean Tempload(Context cont) throws IOException, ParseException{
    	boolean retuner = false;
        try{
        	
        	File storageDir = new File(Environment.getExternalStorageDirectory()
    				+ "/BotanistApp");
        	
    		if (!storageDir.isDirectory() && !storageDir.exists()) {
    			File Directory = new File(Environment.getExternalStorageDirectory()
    					+ "/BotanistApp/");
    			Directory.mkdirs();
    			retuner = false;
    			return retuner;
    		}
    		
    		String path = Environment.getExternalStorageDirectory()+ "/BotanistApp/recordfile.txt";
    		File f = new File(path);
    		
    		if(!f.exists()){
    			retuner = false;
    			return retuner;
    		}
        	
            Scanner infile =new Scanner(new InputStreamReader(new FileInputStream(path)));
            int num = 0;
            num=infile.nextInt();infile.nextLine();
            if(num == 0){
            	retuner = false;
    			return retuner;
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
                
                //SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                //DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                //Date date = format.parse(datestring);
                //r.setEditdate(date);
                r.setEditdate(datestring);
                
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
                retuner = true;
    			return retuner;
            }
            infile.close();
            return retuner;
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
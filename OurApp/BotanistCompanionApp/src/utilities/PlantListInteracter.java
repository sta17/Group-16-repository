package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Contains the arraylist of all the words, of both version of latin and its common version.
 * 
 * @author Sta17
 * @version 25.01.2015
 */
public class PlantListInteracter{
    // instance variables - replace the example below with your own
    private ArrayList<Plant> plantlist;

    /**
     * Constructor for objects of class Plantlist
     */
    public PlantListInteracter() {
        // initialise instance variables
        plantlist = new ArrayList<Plant>();
    }

    /*===============================================================================================
     *                      Add, remove, toString + Support
     * ==============================================================================================
     */
    
    /**
     * Adds new plant to Plantlist, also check if plant already exists and if input is empty or whitespace.
     * @param String latin is the latin version of the plant.
     * @param String common is the common version of the plant.
     */
    public boolean addplant(String latin, String common) {
        if(common.isEmpty() || common.trim().isEmpty()|| latin.isEmpty() || latin.trim().isEmpty()){
            return false;
        }
        else if(uniqueplant(latin) == false || uniqueplant(common) == false){
            return false;
        }
        else{
            Plant tempplant = new Plant(common, latin);
            plantlist.add(tempplant);
            return true;
        }
    }

    /**
     * This is to prevent identical plants from cluttering up the plantlist.
     * @param phrace the phrace to be tested, to see if it already exist in plantlist
     * @return unique - true if it does exist and false if not.
     */
    private boolean uniqueplant(String phrace){
        for (Plant w:plantlist) {
            if (phrace.equalsIgnoreCase(w.getCommon()) || phrace.equalsIgnoreCase(w.getLatin()))  {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Enables a user to delete a plant from the plantlist arraylist.
     * Searches thought the arraylist to see if input matches arraylist content.
     * 
     * @param String plant is the plant you want to have removed.
     */
    public boolean removeplant(String plant) {
        for (Plant w:plantlist) {
            if (plant.equals(w.getLatin()) || plant.equals(w.getCommon()))  {     
                plantlist.remove(w);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Provides information on and plants of the Arraylist.
     * @return temp - arraylist size and all the plants in it.
     */
    public String toString() {
        Collections.sort(plantlist);// Sorts the array list
        String temp="";
        for (Plant w: plantlist) {
            temp=temp+w.toString()+"\n";
        }
        temp =  "Plantlist size is: " +plantlist.size()+"\n plants are:\n"+"\n"+temp;
        return temp;
    }

    public ArrayList<Plant> getAllPlants() {
		return plantlist;
	}
    
    /*===============================================================================================
     *                      Save, Load and Synchronise
     * ==============================================================================================
     */
    
    /**
     * this will override the phones own plant list, allowing for the phone to get an updated one
     * @param plantlist to update
     */
    public void Synchronise(ArrayList<Plant> plantlist){
    	this.plantlist = plantlist;
    }
    
    /**
     * Save mechanism, saves information in to a file, that matches parameter filename.
     * @param fileName - the name of the file to which the information is stored.
     */
    public boolean save(String fileName){
        try{
            PrintWriter outfile = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
            outfile.println(plantlist.size());
            for (Plant w:plantlist) {
                outfile.println(w.getCommon());
                outfile.println(w.getLatin());
            }
            System.out.println(fileName+" successfully saved.");
            outfile.close();
            return true;
        }
        catch (IOException e) {
        	return false;
        }
        catch(RuntimeException e) {
        	return false;
        }
    }

    /**
     * load mechanism, loads information from a file, that matches parameter filename.
     * @param fileName - the name of the file from which the information is retrieved.
     */
    public boolean load(String fileName){
        try{ 
            Scanner infile =new Scanner(new InputStreamReader(new FileInputStream(fileName)));
            int num=infile.nextInt();infile.nextLine();
            for (int i=0;i<num;i++) {
                String p=infile.nextLine();
                String e=infile.nextLine();

                Plant w=new Plant(p,e);
                plantlist.add(w);
            }
            Collections.sort(plantlist);// Sorts the array list
            infile.close();
            return true;
        }
        catch(IOException e) {
            return false;
        }
        catch (InputMismatchException e) {
        	return false;
        }
        catch(RuntimeException e) {
        	return false;
        }
    }
}
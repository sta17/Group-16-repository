package uk.ac.aber.dcs.cs22120.group16.main.plantpackage;

/**
 * 
 * @(#)EditRecord.java 2.2 2015-01-27
 * 
 * Copyright(c)2013 Aberystwyth University.
 * All rights reserved.
 * 
 * @author Steven(Sta17)
 * @since 2.0
 * @version 2.2
 * 
 *Basically just a class that handles the 2 names for the plant.
 *Based on a first year project.
 *
 */
public class Plant 
implements Comparable <Plant>{

	private String latin;
	private String common;

    /**
     * Constructor, used to create a new instance of the class Word.
     * 
     * @param  common this is the common version of the phrace or word
     * @param  latin this is the latin version of the phrace or word
     */
    public Plant(String common, String latin){
        this.latin = latin;
        this.common = common;
    }

    //=====================Comparing=============================

    /**
     * Overrides the compareTo method.
     * used for sorting object a to b.
     * @param w this is the word it wants to compare against.
     */
    public int compareTo(Plant w){
        return (this.latin).compareTo(w.latin);
    }
    
    //=====================get Methods=============================
    
    /**
     * gets the common string and returns it to caller.
     * @return common - the string containing common
     */
    public String getCommon(){
        return common;
    }

    /**
     * gets the latin string and returns it to caller.
     * @return latin - the string containing latin
     */
    public String getLatin(){
        return latin;
    }
}
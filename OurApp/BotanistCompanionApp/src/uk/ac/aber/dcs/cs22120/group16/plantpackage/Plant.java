package uk.ac.aber.dcs.cs22120.group16.plantpackage;

public class Plant implements Comparable <Plant>{

	private String latin;
	private String common;

    /**
     * Constructor, used to create a new instance of the class Word.
     * 
     * @param  pirate this is the common version of the phrace or word
     * @param  english this is the latin version of the phrace or word
     */
    public Plant(String common, String latin){
        this.latin = latin;
        this.common = common;
    }

    //=====================toString=============================

    /**
     * Used for listing the latin and its translation version.
     * @return returns - the latin and pirate version to the caller.
     */
    public String toString(){
        return "Latin version: "+latin+"\n Common version: "+common;
    }

    //=====================Diverse=============================

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
     * gets the pirate string and returns it to caller.
     * @return pirate - the string containing pirate
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
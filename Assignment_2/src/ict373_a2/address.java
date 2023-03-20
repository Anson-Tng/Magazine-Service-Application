package ict373_a2;

import java.io.Serializable;

/**
 * Assignment 2
 * @author Anson Ting Lik Yuan
 * Unit 			: ICT 373
 * Student Number               : 34212178
 * Date 			: 26 May 2022
 */

/**
 * 
 * address class.
 * 
 * This class is made to hold the information of the address.
 */

public class address implements Serializable{
	// Implemented serializable
    private String streetNumber;	// Street number
    private String streetName;		// Street name
    private String suburb;			// Suburb
    private String postCode;		// Post code
    
    // Default constructor
    public address(){
        this.streetNumber = "";
        this.streetName = "";
        this.suburb = "";
        this.postCode = "";
    }
    
    // Constructor with assign parameter values
    public address(String streetNum, String streetNam, String sub, String postC)throws Exception{
        this.streetNumber = streetNum;
        this.streetName = streetNam;
        this.suburb = sub;
        this.postCode = postC;
    }
    
    // Set method
    public void setStreetNumber(String sNumber)throws Exception{
        this.streetNumber = sNumber;
    }
    
    // Set method
    public void setStreetName(String sNam)throws Exception{
        this.streetName = sNam;
    }
    
    // Set method
    public void setSuburb(String s)throws Exception{
        this.suburb = s;
    }
    
    // Set method
    public void setPostCode(String pc)throws Exception{
        this.postCode = pc;
    }
    
    // Get method
    public String getStreetNumber(){
        return this.streetNumber;
    }
    // Get method
    public String getStreetName(){
        return this.streetName;
    }
    
    // Get method
    public String getSuburb(){
        return this.suburb;
    }
    // Get method
    public String getPostCode(){
        return this.postCode;
    }
    
    // overwritten toString()
    @Override
    public String toString(){
        return this.streetNumber+", "+this.streetName+", \n"+this.suburb+", "+this.postCode;
    }
}

package ict373_a2;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * Assignment 2
 * @author Anson Ting Lik Yuan
 * Unit 			: ICT 373
 * Date 			: 26 May 2022
 */

/**
 * 
 * Customer class.
 * 
 * This class includes the general information of the customer should have, and 
 * inherited to associate customer class and paying customer class.
 */
public class customer implements Serializable{
	// Implemented serializable
    private String name;		// Customer name
    private String email;		// Customer email
    private magazine mag;		// Customer mag
    private address adrs;		// Customer address
    private LocalDate addedDate;// Customer registered date
    
    // Default constructor
    public customer(){
        this.name ="";
        this.email="";
        this.mag = null;
        this.adrs = null;
        this.addedDate = null;
    }
    
    // Constructor with assign parameter values
    public customer(String name, String email,magazine supplementList, address newAdrs, LocalDate newDate)throws Exception{
        this.name = name;
        this.email = email;
        this.mag = supplementList;
        this.adrs = newAdrs;
        this.addedDate = newDate;
    }
    
    // Set method
    public void setName(String name)throws Exception{
        this.name = name;
    }
    
    // Set method
    public void setEmail(String email)throws Exception{
        this.email = email;
    }
    
    // Get method
    public String getName()throws Exception{
        return this.name;
    }
    
    // Get method
    public String getEmail()throws Exception{
        return this.email;
    }
    
    // Get method
    public magazine getList()throws Exception{
        return this.mag;
    }
    
    // Get list of supplement from magazine
    public String getSubList() throws Exception{
        String subList = mag.getInterests();
        return subList;
    }
    
    // get address from address class
    public address getAddress() throws Exception{
        return this.adrs;
    }
    
    // set adress 
    public void setAddress(address adrs){
        this.adrs = adrs;
    }
    
    // get the subscribed date
    public LocalDate getDateSub() throws Exception{
        return this.addedDate;
    }
    
    // Get the cost of magazine.
    public double getMagCost(){
        return this.mag.getMagCost();
    }
    
    // Get the total cost of supplements in the magazine object.
    public double getSupCost(){
        return this.mag.getSupTotal();
    }
    
    // Modified toString method.
    @Override
    public String toString(){
        return "Customer name       : "+this.name+"\n"+
               "Customer email      : "+this.email+"\n"+
               "Customer added date : "+this.addedDate+"\n"+
               this.mag;
    }
    
}

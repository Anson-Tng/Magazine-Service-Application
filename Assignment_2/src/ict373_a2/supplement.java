package ict373_a2;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Assignment 2
 * @author Anson Ting Lik Yuan
 * Unit 			: ICT 373
 * Student Number               : 34212178
 * Date 			: 26 May 2022
 */

/**
 * 
 * Supplement class.
 * 
 * supplement class holds all the available supplements of the magazine,
 * includes Games, Sports, Movies, and Tvs.
 * 
 */
public class supplement implements Serializable{
	// Implemented serializable
    private String supplementName;	// Supplement name
    private double weeklyCost;		// It's weekly cost
    private LocalDate date;			// Date of supplement added into database
    
    // Default constructor
    public supplement(){
        this.supplementName = null;
        this.weeklyCost = 0;
        this.date = null;
    }
    
    // constructor with assign parameter values
    public supplement(String name, double cost, LocalDate d)throws Exception{
        this.supplementName = name;
        this.weeklyCost = cost;
        this.date = d;
    }
    
    // Set method
    public void setSupplementName(String name)throws Exception{
        this.supplementName = name;
    }
    
    // Set method
    public void setWeeklyCost(double cost)throws Exception{
        this.weeklyCost = cost;
    }
    
    public void setDate(LocalDate d) throws Exception{
        this.date = d;
    }
    
    // Get method
    public String getSupplementName(){
        return this.supplementName;
    }
    
    // Get method
    public double getWeeklyCost(){
        return this.weeklyCost;
    }
    
    // Get method
    public LocalDate getDate(){
        return this.date;
    }
    
    // Modified toString method.
    @Override
    public String toString(){
        return  "\nSupplement            : "+this.supplementName
              + "\nWeeky cost            : "+this.weeklyCost+ "$"
              + "\nAdded date            : "+this.date+"\n";
              
    }
    
}
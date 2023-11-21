package ict373_a2;

import java.io.Serializable;

/**
 * Assignment 2
 * @author Anson Ting Lik Yuan
 * Unit 			: ICT 373
 * Date 			: 26 May 2022
 */

/**
 * 
 * CreditCard class.
 * 
 * This class is made to holds the information of paying customer paying with
 * credit card.
 */
public class creditCard implements Serializable{
	// Implemented serializable
    private String name;		// Name
    private String cardNumber;	// Credit card number
    private String expiryDate;	// Expiry date of the card
    
    // Default constructor
    public creditCard(){
        this.name = "";
        this.cardNumber = "";
        this.expiryDate = "";
    }
    
    // Constructor with assign parameter values.
    public creditCard(String nam, String cardN, String expDate)throws Exception{
        this.name = nam;
        this.cardNumber = cardN;
        this.expiryDate = expDate;
    }
    
    // Set method
    public void setName(String nam)throws Exception{
        this.name = nam;
    }
    
    // Set method
    public void setCardNumber(String cardNum)throws Exception{
        this.cardNumber = cardNum;
    }
    
    // Set method
    public void setExpiryDate(String expDate)throws Exception{
        this.expiryDate = expDate;
    }
    
    // Get method
    public String getName(){
        return this.name;
    }
    
    // Get method
    public String getCardNumber(){
        return this.cardNumber;
    }
    
    // Get method
    public String getExpiryDate(){
        return this.expiryDate;
    }
    
    // modified toString method.
    @Override
    public String toString(){
        return "\n==========   Credit Card   =========="+
               "\nName: "+this.name+
               "\nCard number: "+this.cardNumber+
               "\nExpiry date: "+this.expiryDate+
               "\n========== End Credit Card ==========\n\n";
    }
    
}

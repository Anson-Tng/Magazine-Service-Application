package ict373_a2;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Assignment 2
 * @author Anson Ting Lik Yuan
 * Unit 			: ICT 373
 * Date 			: 26 May 2022
 */

/**
 *
 * AssociateCustomer Class.
 *  This class is derived class of customer class, this class includes the 
 *  information about associate customer. 
 * 
 */
public class associateCustomer extends customer implements Serializable{
	// Extended from customer class and implemented serializable
    private double total;	// Contains the total cost of magazine and supplement
    private String paidBy;	// The email of people paying for him/her
    
    
    // Default constructor
    public associateCustomer(){
        super();
        this.paidBy = "";   // this variable contains the email of paying customer.
    }
    
    // Constructor with assign parameter values
    public associateCustomer(String name, String email,magazine supplementList, 
            address newAdrs, LocalDate newDate, String peoplePaid)throws Exception {
        super(name, email, supplementList, newAdrs, newDate);
        this.paidBy = peoplePaid;
    }
    
    // Set method
    public void setPaidBy(String peoplePaid){
        this.paidBy = peoplePaid;
    }
    
    // this method is included when removing a related paying customer from the system.
    public void removePaidBy(){
        this.paidBy = "Removed, please add a new payment method.";
    }
    
    // Get method
    public String getPaidBy(){
        return this.paidBy;
    }
    
    
    // Get method of all paying amount for associate customer him/herself
    public double getTotalAC(){
        double magP = getMagCost();
        double supP = getSupCost();
        return supP + magP ;
    }
    
    // Modified toString method.
    @Override
    public String toString(){
        try {
            return "====================== Associate Customer =========================="+
                    "\nCustomer name         : "+getName()+
                    "\nCustomer email        : "+getEmail()+"\n"
                    +getList()+
                    "\nSubscription paid by  : "+this.paidBy+"\n"+
                    "Total paid             : "+getTotalAC()+"$\n"+
                    " ====================== End Associate Customer ======================\n\n";
        } catch (Exception ex) {
            Logger.getLogger(associateCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}

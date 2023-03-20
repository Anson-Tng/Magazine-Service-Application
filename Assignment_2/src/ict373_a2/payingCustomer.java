package ict373_a2;

/**
 * Assignment 2
 * @author Anson Ting Lik Yuan
 * Unit 			: ICT 373
 * Student Number               : 34212178
 * Date 			: 26 May 2022
 */

/**
 *
 * PayingCustomer Class.
 *  This class is derived class of customer class, this class includes the 
 *  information about paying customer. 
 * 
 */
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class payingCustomer extends customer implements Serializable{
	// Implemented serializable
    private String payingType;	// Paying type, Credit card or bank account
    private List<Double> associateCusPrice = new ArrayList<Double>();	// List of paying customer's subscription price
    private List associateCusEmail = new ArrayList();					// List of paying customer's email
    private bankAccount bankAccountMethod;	// Bank account method
    private creditCard creditCardMethod;	// Credit card method
    
    // Default constructor
    public payingCustomer(){
        super();
        this.payingType = "";
        this.bankAccountMethod = new bankAccount();
        this.creditCardMethod = new creditCard();
        this.associateCusPrice = new ArrayList();
        this.associateCusEmail = new ArrayList();
    }
    
    //Default constructor with assign parameter values
    public payingCustomer(String name,String email,magazine supplementList, address newAdrs,
            LocalDate newDate, String payType, String paymentName, String numberOrBankName,
            String accountNumOrExpDate)throws Exception{
        super(name, email, supplementList, newAdrs, newDate);
        this.payingType = payType;
        if(accountNumOrExpDate.length()>7){
            this.bankAccountMethod = new bankAccount(paymentName, numberOrBankName, accountNumOrExpDate); 
        } else{
            this.creditCardMethod = new creditCard(paymentName, numberOrBankName, accountNumOrExpDate);
        }
        
        this.associateCusPrice = new ArrayList();
        this.associateCusEmail = new ArrayList();
        
    }
    
    // AddAssociateCustomer method is used for adding associate customer into a
    // existing paying customer.
    public void addAssociateCustomer(ArrayList<associateCustomer> ac, String email) throws Exception{
        for(int i=0;i<ac.size();i++){
            if(ac.get(i).getEmail().equalsIgnoreCase(email)){
                this.associateCusPrice.add(ac.get(i).getSupCost());
                this.associateCusEmail.add(ac.get(i).getEmail());
            }
        }
    }
    // RemoveAssociateCustomer method is used for removing associate customer from a
    // existing paying customer.
    public void removeAssociateCustomer(ArrayList<associateCustomer> ac, String email){
        if(this.associateCusEmail.size() == this.associateCusPrice.size()){
            for(int i=0;i<this.associateCusEmail.size();i++){
                if(this.associateCusEmail.get(i).toString().equalsIgnoreCase(email)){
                    this.associateCusEmail.remove(i);
                    this.associateCusPrice.remove(i);
                }
            }
        }
    }
    
    // Get method
    public String getPayingType(){
        return this.payingType;
    }
    
    // Get method if paying customer using bank account method
    public bankAccount getBaPayType(){
        return this.bankAccountMethod;
    }
    
    // Get method if paying customer using credit card method
    public creditCard getCcPayType(){
        return this.creditCardMethod;
    }
    
    // Set method if paying customer using credit card method
    public void setCcPayType(creditCard cc){
        this.creditCardMethod = cc;
    }
    
    // Set method if paying customer using bank account method
    public void setBaPayType(bankAccount ba){
        this.bankAccountMethod = ba;
    }
    
    // Get the list of associate customer that paying customer is paying for
    public String getPayingList(){
        String payingList ="";
        
        if(associateCusPrice.size() == associateCusEmail.size()){
            for(int i=0;i<associateCusPrice.size();i++){
                payingList += associateCusEmail.get(i)+"\n"+
                "Total price:"+(associateCusPrice.get(i)+8)+"$\n\n";
            }
        }
        return payingList;
    }
    
    // get the list of paying list
    public List getPayingAssociateCusList(){
        return this.associateCusEmail;  
    }
    
    // get the price list of paying customer list
    public List getAssociateCustomerPriceList(){
        return this.associateCusPrice;
    }
    
    // Get method of all paying amount for paying customer him/herself 
    private double getTotalPC(){
        double magP = getMagCost();
        double supP = getSupCost();
        double othersPrice = 0;
        for(int i=0;i<associateCusPrice.size();i++){
            othersPrice += (this.associateCusPrice.get(i));
            othersPrice += 8;
        }
        return magP + supP + othersPrice ;
    }
    
    // show credit card details or fund transfer detail
    public String showMethod(){
        if(this.bankAccountMethod == null){
            return this.creditCardMethod.toString();
        } else {
            return this.bankAccountMethod.toString();
        }
    }
    
    // Modified toString method.
    @Override
    public String toString(){
        String associateCus = "";
        if(associateCusPrice.size() == associateCusEmail.size()){
            for(int i=0;i<associateCusPrice.size();i++){
                associateCus +="Customer "+(i+1)+"            : "+associateCusEmail.get(i)+"\n"+
                "Total price           : "+(associateCusPrice.get(i)+8)+"$\n\n";
            }
        }       
        
        try {
            return "\n===================== Paying Customer ==========================="+
                    "\nCustomer name         : "+getName()+
                    "\nCustomer email         : "+getEmail()+"\n"
                    + getList() + "\nyou're paying for    :\n\n"+associateCus+
                    "\nTotal paid            : "+getTotalPC()+"$\n"+ showMethod()
                    +" ===================== End Paying Customer =======================\n\n";
        } catch (Exception ex) {
            Logger.getLogger(payingCustomer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
               
    }
    
}

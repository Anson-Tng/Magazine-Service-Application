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
 * Magazine class.
 * 
 * The name of magazine is set default and its only name as WeeklyFun magazine
 * and this class include the list of supplement, so customer can subscribe to 
 * multiple supplements for a magazine.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class magazine implements Serializable{
	// Implemented serializable
    private String magName = "WeeklyFun";	// Magazine name WeeklyFun								
    private double magCost = 8;				// Magazine default cost
    private List<supplement> supplementList = new ArrayList<supplement>(); // List of the supplements

    // Default constructor
    public magazine(){
        this.magName = "WeeklyFun";
        this.magCost = 8;
        //this.supplementList.add(new supplement("None",0,null));
    }
    
    // constructor with assign parameter values for one supplement
    public magazine(List<supplement> supList)throws Exception{
        this.supplementList = supList;
    }
    
    // Get method
    public String getMagName(){
        return this.magName;
    }
    
    // Get method
    public double getMagCost(){
        return this.magCost;
    }
    
    public void addSupplement(supplement sup){
        this.supplementList.add(sup);
    }
    
    // Get list of supplements from supplement class.
    public String getInterests()throws Exception{
        String subList ="";
        for(int i=0;i<this.supplementList.size();i++){
            subList += supplementList.get(i).getSupplementName()+
                    ' '+supplementList.get(i).getWeeklyCost()+"$\n\n";
        }
        return subList;
    }

    // Get total cost of subscribed supplements
    public double getSupTotal(){
        double total = 0;
        for(int i=0;i<this.supplementList.size();i++){          
            total +=this.supplementList.get(i).getWeeklyCost();
        }
        return total;
    }
    
    public List<supplement> getSupList(){
        return this.supplementList;
    }

    // Modified toString method.
    @Override
    public String toString(){
        return "\nMagazine name         : "+this.magName+
               "\nMagazine cost           : "+this.magCost+"$\n"+
               this.supplementList.toString().replace("[", "").replace("]", "").replace(",","");
    }
    
    
}

package ict373_a2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Assignment 2
 * @author Anson Ting Lik Yuan
 * Unit 			: ICT 373
 * Student Number               : 34212178
 * Date 			: 26 May 2022
 */

/**
 * 
 * View class.
 * 
 * This class handle most of the visual setting of the application, and includes
 * the buttons event handling.
 * 
 */

public class View {
    // Java fx componenets
    private Stage stage;
    private Scene scene;
    private Group root = new Group();
    private GridPane btnAndList = new GridPane();
    private GridPane infoPanel;
    private GridPane editPanel;
    private ScrollPane sP = new ScrollPane();
    
    // Main menu buttons
    private Button viewBtn;
    private Button createBtn;
    private Button editBtn;
    
    // Magazine service 
    magazineService ms = new magazineService();
    
    // Components for edit mode
    private TextField editName = new TextField();
    private TextField editEmail = new TextField();
    private TextField editPaidBy = new TextField();
    private Label editSupDate = new Label();
    private Button editAddressBtn = new Button("EditAdrs");
    private Button editSupplementBtn = new Button("EditSup");
    private Button editPayInfoBtn = new Button("EditPayInfo");
    private Button editPayForBtn = new Button("EditPayFor");
    private Button editDeleteBtn = new Button("Delete");
    private Button editSubmitBtn = new Button("Change");
    
    // Labels of customer / supplement information
    private Label name = new Label();
    private Label email = new Label();
    private Label address = new Label();
    private Label supplementList = new Label();
    private Label dateSubscribed = new Label();
    private Label customerType = new Label();
    
    
    private Label payingBy = new Label();
    private Label payingMethod = new Label();
    private Label payingFor = new Label();
    
    // Components for display stored info
    private Label ansName = new Label();
    private Label ansEmail = new Label();
    private Label ansAdrs = new Label();
    private Label ansSupList = new Label();
    private Label ansDateSub = new Label();
    private Label ansCusType = new Label();
    private Label ansPaidBy = new Label();
    private Label ansPaymentMethod = new Label();
    private Label ansPayingFor = new Label();
    private Button invoiceBtn = new Button("Invoices");
    private Button msgBtn = new Button("Notifcations");
    
    // List view of customers and supplements
    private ListView<String> cusListView;
    private ListView<String> supListView;
    private ListView<String> invoiceAndMsgView;
    
    // Array lists of magazine service system data
    private ArrayList<associateCustomer> acList; 
    private ArrayList<payingCustomer> pcList;
    private ArrayList<supplement> supList;
    
    // Variables for functions use
    private String nameToLookFor = "";
    private String editType = "";
    private int supListViewSize = 0;
    private int aCusListViewSize = 0;
    private int pCusListViewSize = 0;
    private int editPos = 0;
    private boolean isView;
    private boolean isCus;
    private boolean isPc;
    private boolean ac = false;
    
    //Font setting
    private final Font TitleFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 19);
    private final Font infoDataFont = Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 19);

    public View(Stage stage) throws Exception{
        this.stage = stage;
        buildView();
    }
    
    private void hBox(){
        // View, Create, and Edit buttons that displayed at main menu
        viewBtn = new Button("View");
        createBtn = new Button("Create");
        editBtn = new Button("Edit");
        
        viewBtn.setPrefSize(300, 40);
        
        createBtn.setPrefSize(300, 40);
        
        editBtn.setPrefSize(300, 40);
        
        HBox hbox = new HBox(50,viewBtn, createBtn, editBtn);
        
        btnAndList.add(hbox, 0, 1,20,3);
    }
    
    private void cusListDisplay() throws Exception{
        // This Function is use for refresh the list view of customer if 
        // there's update from the magazine serivce database.
        cusListView = new ListView<String>();
        Label cusL = new Label("Customer list");
        cusL.setFont(TitleFont);
        cusL.setLabelFor(cusListView);
        
        if(acList != null || pcList != null){
            for(int i=0;i<acList.size();i++){
                cusListView.getItems().add(acList.get(i).getEmail());
                this.aCusListViewSize ++;
            }

            for(int i=0;i<pcList.size();i++){
                cusListView.getItems().add(pcList.get(i).getEmail());
                this.pCusListViewSize ++;
            }
        }
        cusListView.setPrefSize(250, 200);
        
        btnAndList.add(cusListView, 0, 5, 6, 10);
        btnAndList.add(cusL, 1, 4, 4, 1);
    }
    
    private void supListDisplay(){
        // This Function is use for refresh the list view of supplement if 
        // there's update from the magazine serivce database.
        supListView = new ListView<String>();
        Label supL = new Label("Supplement list");
        supL.setFont(TitleFont);
        supL.setLabelFor(supListView);
        
        if(supList != null){
            for(int i=0;i<supList.size();i++){
                supListView.getItems().add(supList.get(i).getSupplementName().trim());
                this.supListViewSize++;
            }
        }
        
        supListView.setPrefSize(250, 200);
        btnAndList.add(supL, 1, 15, 5, 1);
        btnAndList.add(supListView, 0, 16, 6, 10);

        //INFO PANEL
        
    }
    
    private void informationPanel(){
        // This function includes the visual settings of the information panel
        infoPanel = new GridPane();
        
        // GridPane setting
        infoPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        infoPanel.setMinSize(700, 1000);
        infoPanel.setMaxSize(700, 1000);
        //infoPanel.setGridLinesVisible(true);
        infoPanel.setVgap(20);
        infoPanel.setHgap(50);
        
        //INFO PANEL
        Label infoP = new Label(" Information Panel");
        
        // Label setting
        infoPanel.add(infoP,0, 0, 4, 1);
        infoP.setPrefSize(200, 20);
        infoPanel.add(name,1, 1, 4, 1);
        name.setPrefSize(200, 20);
        infoPanel.add(email,1, 2, 4, 1);
        email.setPrefSize(200, 20);
        infoPanel.add(address,1, 4, 4, 1);
        address.setPrefSize(200, 20);
        infoPanel.add(supplementList,1, 8, 4, 1);
        supplementList.setPrefSize(200, 20);
        infoPanel.add(dateSubscribed,1, 18, 4, 1);
        dateSubscribed.setPrefSize(200, 20);
        infoPanel.add(customerType,1, 20, 4, 1);
        customerType.setPrefSize(200, 20);
        infoPanel.add(payingBy,1, 22, 4, 1);
        payingBy.setPrefSize(200, 20);
        infoPanel.add(payingMethod,1, 22, 4, 1);
        payingMethod.setPrefSize(200, 20);
        infoPanel.add(payingFor,1, 24, 4, 1);
        payingFor.setPrefSize(200, 20);
        
        
        
        // Data setting
        infoPanel.add(ansName,7, 1, 4, 1);
        ansName.setPrefSize(200, 20);
        infoPanel.add(ansEmail,7, 2, 4, 1);
        ansEmail.setPrefSize(200, 20);
        infoPanel.add(ansAdrs,7, 4, 4, 4);
        ansAdrs.setPrefSize(200, 80);
        ansAdrs.setAlignment(Pos.TOP_LEFT);
        infoPanel.add(ansSupList,7, 8, 4, 10);
        ansSupList.setPrefSize(200, 180);
        ansSupList.setAlignment(Pos.TOP_LEFT);
        infoPanel.add(ansDateSub,7, 18, 4, 1);
        ansDateSub.setPrefSize(200, 20);
        infoPanel.add(ansCusType,7, 20, 4, 1);
        ansCusType.setPrefSize(200, 20);
        infoPanel.add(ansPaidBy,7, 22, 4, 1);
        ansPaidBy.setPrefSize(200, 20);
        infoPanel.add(ansPaymentMethod,7, 22, 4, 1);
        ansPaymentMethod.setPrefSize(200, 20);
        infoPanel.add(ansPayingFor,7, 24, 4, 8);
        ansPayingFor.setPrefSize(200, 160);
        ansPayingFor.setAlignment(Pos.TOP_LEFT);
        
        // Customer info buttons
        infoPanel.add(invoiceBtn,2, 34, 4, 4);
        payingFor.setPrefSize(200, 40);
        infoPanel.add(msgBtn,7, 34, 4, 4);
        payingFor.setPrefSize(200, 40);
        invoiceBtn.setVisible(false);
        msgBtn.setVisible(false);
        
        
        // Font setting
        infoP.setFont(TitleFont);
        name.setFont(TitleFont);
        email.setFont(TitleFont);
        address.setFont(TitleFont);
        supplementList.setFont(TitleFont);
        dateSubscribed.setFont(TitleFont);
        customerType.setFont(TitleFont);
        payingBy.setFont(TitleFont);
        payingMethod.setFont(TitleFont);
        payingFor.setFont(TitleFont);
        ansName.setFont(infoDataFont);
        ansEmail.setFont(infoDataFont);
        ansAdrs.setFont(infoDataFont);
        ansSupList.setFont(infoDataFont);
        ansDateSub.setFont(infoDataFont);
        ansCusType.setFont(infoDataFont);  
        ansPaidBy.setFont(infoDataFont);
        ansPaymentMethod.setFont(infoDataFont);
        ansPayingFor.setFont(infoDataFont); 
        invoiceBtn.setFont(TitleFont);
        msgBtn.setFont(TitleFont);
    }
    
    private void editPanel(){
        // This function includes the visual settings of the edit panel
        editPanel = new GridPane();
        
        // GridPane setting
        editPanel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        editPanel.setMinSize(700, 1000);
        editPanel.setMaxSize(700, 1000);
        editPanel.setVgap(20);
        editPanel.setHgap(50);
        
        
        // Label settings
        Label editP = new Label(" Edit Panel");
        
        editPanel.add(editP,0, 0, 4, 1);
        editP.setPrefSize(200, 20);
        
        editPanel.add(name,1, 1, 4, 1);
        name.setPrefSize(200, 20);
        
        editPanel.add(email,1, 3, 4, 1);
        email.setPrefSize(200, 20);
        
        editPanel.add(address,1, 5, 4, 1);
        address.setPrefSize(200, 20);
        
        editPanel.add(supplementList,1, 8, 4, 1);
        supplementList.setPrefSize(200, 20);
        
        editPanel.add(dateSubscribed,1, 11, 4, 1);
        dateSubscribed.setPrefSize(200, 20);
        
        
        editPanel.add(customerType,1, 13, 4, 1);
        customerType.setPrefSize(200, 20);
        
        editPanel.add(payingBy,1, 15, 4, 1);
        payingBy.setPrefSize(200, 20);
        
        editPanel.add(payingMethod,1, 15, 4, 1);
        payingMethod.setPrefSize(200, 20);
        
        editPanel.add(payingFor,1, 17, 4, 1);
        payingFor.setPrefSize(200, 20);
        

        
        
        
        
        
        // Right side info / data setting
        editPanel.add(editName, 7, 1, 3, 1);
        editName.setPrefSize(150, 20);
        
        editPanel.add(editEmail, 7, 3, 3, 1);
        editEmail.setPrefSize(150, 20);
        
        editPanel.add(editSupDate,7, 5, 3, 2);
        editSupDate.setPrefSize(150, 20);
        
        editPanel.add(editAddressBtn,7, 5, 4, 2);
        editAddressBtn.setPrefSize(150, 40);
        
        editPanel.add(editSupplementBtn,7, 8, 3, 2);
        editSupplementBtn.setPrefSize(150, 40);
        
        editPanel.add(ansDateSub,7, 11, 3, 1);
        ansDateSub.setPrefSize(150, 20);
        
        editPanel.add(ansCusType,7, 13, 4, 1);
        ansCusType.setPrefSize(200, 20);
        
        editPanel.add(editPaidBy, 7, 15, 3, 1);
        editPaidBy.setPrefSize(150, 20);
        
        editPanel.add(editPayInfoBtn, 7, 15, 3, 2);
        editPayInfoBtn.setPrefSize(150, 40);
        
        editPanel.add(editPayForBtn, 7, 17, 3, 2);
        editPayForBtn.setPrefSize(150, 40);
        
        editPanel.add(editDeleteBtn, 2, 25, 3, 2);
        editDeleteBtn.setPrefSize(150, 40);
        
        editPanel.add(editSubmitBtn, 7, 25, 3, 2);
        editSubmitBtn.setPrefSize(150, 40);
        
        
        // Font setting
        ansDateSub.setFont(infoDataFont);
        ansCusType.setFont(infoDataFont);
        editSupDate.setFont(infoDataFont);
        editP.setFont(TitleFont);
        name.setFont(TitleFont);
        email.setFont(TitleFont);
        address.setFont(TitleFont);
        supplementList.setFont(TitleFont);
        dateSubscribed.setFont(TitleFont);
        customerType.setFont(TitleFont);
        payingBy.setFont(TitleFont);
        payingMethod.setFont(TitleFont);        
        payingFor.setFont(TitleFont);        
        editAddressBtn.setFont(TitleFont);
        editSupplementBtn.setFont(TitleFont);
        editPayInfoBtn.setFont(TitleFont);
        editPayForBtn.setFont(TitleFont);
        editDeleteBtn.setFont(TitleFont);
        editSubmitBtn.setFont(TitleFont);
        
        // all set to invisible by default, only visible when it's needed
        editName.setVisible(false);
        editEmail.setVisible(false);        
        editPaidBy.setVisible(false);
        editAddressBtn.setVisible(false);
        editSupplementBtn.setVisible(false);
        editPayInfoBtn.setVisible(false);     
        editPayForBtn.setVisible(false);        
        editDeleteBtn.setVisible(false);        
        editSubmitBtn.setVisible(false);        
    }
    
    private void clearInfoPanel(){
        // This function is use for clear out the data info and allows to
        // display the next selected data info.
        name.setText("");
        email.setText("");
        address.setText("");
        supplementList.setText("");
        dateSubscribed.setText("");
        customerType.setText("");
        payingBy.setText("");
        payingMethod.setText("");
        payingFor.setText("");
        
        ansName.setText("");
        ansEmail.setText("");
        ansAdrs.setText("");
        ansSupList.setText("");
        ansDateSub.setText("");
        ansCusType.setText("");  
        ansPaidBy.setText("");
        ansPaymentMethod.setText("");
        ansPayingFor.setText("");
    }
    
    private boolean isExist(String listName, String email){
        // Boolean check if customer already exist in the database.
       if(listName.equalsIgnoreCase("supList")){
           for(int i=0;i<this.supListViewSize;i++){
                if(supListView.getItems().get(i).equalsIgnoreCase(email)){
                    return true;
                }
            }
        return false;
       }
       
       if(listName.equalsIgnoreCase("cusList")){
           for(int i=0;i<this.aCusListViewSize+pCusListViewSize;i++){
                if(cusListView.getItems().get(i).equalsIgnoreCase(email)){
                    return true;
                }
            }
        return false;
       }   
       
       return false;
    }
    
    private void reloadFile() throws Exception{
        // Reload the data from magazine service system.
        this.acList = ms.getAcList();
        this.pcList = ms.getPcList();
        this.supList = ms.getSupList();
        
        // If data from magazine service system are more than current one, find
        // the new data and add in to this application.
        if(supList.size() >= this.supListViewSize){
            for(int i=0;i<supList.size();i++){
                if(!isExist("supList",supList.get(i).getSupplementName())){
                    supListView.getItems().add(supList.get(i).getSupplementName());
                    this.supListViewSize++;
                }
            }  
        }else if(this.supListViewSize >= supList.size()){
            boolean loopIn;
            for(int i=0;i<supListViewSize;i++){
                loopIn = false;
                for(int j=0;j<supList.size();j++){
                    if(supListView.getItems().get(i).equalsIgnoreCase(supList.get(j).getSupplementName())){
                        loopIn = true;
                    }
                }
                if(!loopIn){
                    supListView.getItems().remove(i);
                    supListViewSize --;
                }    
            }
        }
        
        // If data from magazine service system are more than current one, find
        // the new data and add in to this application.
        if(acList.size() >= aCusListViewSize){
            for(int i=0;i<acList.size();i++){
                if(!isExist("cusList",acList.get(i).getEmail())){
                    cusListView.getItems().add(acList.get(i).getEmail());
                    this.aCusListViewSize++;
                }
            }
        }
        
        // If data from magazine service system are less than current one, find
        // the new data and add in to this application.
        if(pcList.size() >= pCusListViewSize){
            for(int i=0;i<pcList.size();i++){
                if(!isExist("cusList",pcList.get(i).getEmail())){
                    cusListView.getItems().add(pcList.get(i).getEmail());
                    this.pCusListViewSize++;
                }
            }
        }
        
        // If data from magazine service system are less than current one, find
        // the new data and add in to this application.
        if(this.aCusListViewSize >= acList.size()){
            boolean loopIn;
            for(int i=0;i<(this.aCusListViewSize+this.pCusListViewSize);i++){
                loopIn = false;
                for(int j=0;j<acList.size();j++){
                    if(cusListView.getItems().get(i).equalsIgnoreCase(acList.get(j).getEmail())){
                        loopIn = true;
                    }
                }    
                for(int k=0;k<pcList.size();k++){
                    if(cusListView.getItems().get(i).equalsIgnoreCase(pcList.get(k).getEmail())){
                        loopIn = true;
                    }    
                }
                if(!loopIn){
                    cusListView.getItems().remove(i);
                    aCusListViewSize --;
                }
            }
        }
        
        // If data from magazine service system are less than current one, find
        // the new data and add in to this application.
        if(this.pCusListViewSize >= pcList.size()){
            boolean loopIn;
            for(int i=0;i<(this.aCusListViewSize+this.pCusListViewSize);i++){
                loopIn = false;
                for(int j=0;j<acList.size();j++){
                    if(cusListView.getItems().get(i).equalsIgnoreCase(acList.get(j).getEmail())){
                        loopIn = true;
                    }
                }    
                for(int k=0;k<pcList.size();k++){
                    if(cusListView.getItems().get(i).equalsIgnoreCase(pcList.get(k).getEmail())){
                        loopIn = true;
                    }    
                }
                if(!loopIn){
                    cusListView.getItems().remove(i);
                    pCusListViewSize --;
                }
            }
        }   
        
    }   
    
    private void printCus(String cusEmail) throws Exception{
        // Display the information of selected customer for view mode or edit mode
        clearInfoPanel();
        editSupDate.setVisible(false);
        name.setText("Customer name:");
        email.setText("Email address:");
        address.setText("Address:");
        supplementList.setText("Supplements list:");
        dateSubscribed.setText("Date subscribed:");
        customerType.setText("Customer type:");
        
        
            
            // Find if the customer is associate customer or paying customer
            int pos = 0;
            String cusType = "";
            for(int i=0;i<acList.size();i++){
                if(acList.get(i).getEmail().equalsIgnoreCase(cusEmail)){
                    pos = i;
                    cusType = "Associate Customer";
                }
            }
            for(int i=0;i<pcList.size();i++){
                if(pcList.get(i).getEmail().equalsIgnoreCase(cusEmail)){
                    pos = i;
                    cusType = "Paying Customer";
                }
            }
            
            
            if(isView){
            // View mode
            if(cusType.equalsIgnoreCase("Associate Customer")){
                invoiceBtn.setVisible(false);
                msgBtn.setVisible(true);
                payingBy.setText("Paying by:");
                payingMethod.setText("");
                payingFor.setText("");
                ansPaymentMethod.setText("");
                ansPayingFor.setText("");
                // Associate Customer info
                ansName.setText(acList.get(pos).getName());
                ansEmail.setText(acList.get(pos).getEmail()); 
                ansAdrs.setText(acList.get(pos).getAddress().toString());
                ansSupList.setText(acList.get(pos).getSubList());
                ansDateSub.setText(acList.get(pos).getDateSub().toString());
                ansCusType.setText(cusType);
                ansPaidBy.setText(acList.get(pos).getPaidBy());
                nameToLookFor = acList.get(pos).getEmail();
            }else{
                payingBy.setText("");
                payingMethod.setText("Paying method:");
                payingFor.setText("Paying for:");
                ansPaidBy.setText("");
                invoiceBtn.setVisible(true);
                msgBtn.setVisible(true);
                // Paying customer info
                ansName.setText(pcList.get(pos).getName());
                ansEmail.setText(pcList.get(pos).getEmail());
                ansAdrs.setText(pcList.get(pos).getAddress().toString());
                ansSupList.setText(pcList.get(pos).getSubList());
                ansDateSub.setText(pcList.get(pos).getDateSub().toString());
                ansCusType.setText(cusType);
                ansPaymentMethod.setText(pcList.get(pos).getPayingType());
                if(pcList.get(pos).getAssociateCustomerPriceList() != null){
                    ansPayingFor.setText(pcList.get(pos).getPayingList());
                }
                nameToLookFor = pcList.get(pos).getEmail();
            }
        }else{
            // Edit mode
            invoiceBtn.setVisible(false);
            msgBtn.setVisible(false);
            editName.setVisible(true);
            editEmail.setVisible(true);   
            editAddressBtn.setVisible(true);
            editSupplementBtn.setVisible(true);
            supplementList.setVisible(true);
            dateSubscribed.setVisible(true);
            editDeleteBtn.setVisible(true);        
            editSubmitBtn.setVisible(true);
            
            if(cusType.equalsIgnoreCase("Associate Customer")){
                /// Associate customer
                editPanel.getChildren().remove(editDeleteBtn);
                editPanel.add(editDeleteBtn, 2, 18, 3, 2);
                editPanel.getChildren().remove(editSubmitBtn);
                editPanel.add(editSubmitBtn, 7, 18, 3, 2);
                editPaidBy.setVisible(true);
                editPayInfoBtn.setVisible(false);
                editPayForBtn.setVisible(false);
                payingBy.setText("Paying by:");
                payingMethod.setText("");
                payingFor.setText("");
                
                editName.setText(acList.get(pos).getName());
                editEmail.setText(acList.get(pos).getEmail()); 
                ansDateSub.setText(acList.get(pos).getDateSub().toString());
                ansCusType.setText(cusType);
                editPaidBy.setText(acList.get(pos).getPaidBy());
            }else{
                /// Paying customer
                editPanel.getChildren().remove(editDeleteBtn);
                editPanel.add(editDeleteBtn, 2, 22, 3, 2);
                editPanel.getChildren().remove(editSubmitBtn);
                editPanel.add(editSubmitBtn, 7, 22, 3, 2);
                editPaidBy.setVisible(false);
                editPayInfoBtn.setVisible(true);
                editPayForBtn.setVisible(true);
                payingBy.setText("");
                payingMethod.setText("Paying method:");
                payingFor.setText("Paying for:");
                
                editName.setText(pcList.get(pos).getName());
                editEmail.setText(pcList.get(pos).getEmail());
                ansDateSub.setText(pcList.get(pos).getDateSub().toString());
                ansCusType.setText(cusType);
            }
        }
    }
   
    private void printSup(String supName) throws Exception{
        // Display the information of selected customer for view mode or edit mode
        clearInfoPanel();
        
        name.setText("Supplement name:");
        email.setText("Supplement cost:");
        address.setText("Date created:");

        int pos = 0;

        for(int i=0;i<supList.size();i++){
            if(supList.get(i).getSupplementName().equalsIgnoreCase(supName)){
            pos = i;
            }
        }
            
        if(isView){
        // View mode
            invoiceBtn.setVisible(false);
            msgBtn.setVisible(false);
            ansName.setText(supList.get(pos).getSupplementName());
            ansEmail.setText(supList.get(pos).getWeeklyCost()+"$");
            ansAdrs.setText(supList.get(pos).getDate().toString());
        }else{
            // Edit mode
            editPanel.getChildren().remove(editDeleteBtn);
            editPanel.add(editDeleteBtn, 2, 8, 3, 2);
            editPanel.getChildren().remove(editSubmitBtn);
            editPanel.add(editSubmitBtn, 7, 8, 3, 2);
            invoiceBtn.setVisible(false);
            msgBtn.setVisible(false);
            editAddressBtn.setVisible(false);
            editSupplementBtn.setVisible(false);
            supplementList.setVisible(false);
            dateSubscribed.setVisible(false);
            editPaidBy.setVisible(false);
            editPayInfoBtn.setVisible(false);
            editPayForBtn.setVisible(false);
            editDeleteBtn.setVisible(false);        
            editSubmitBtn.setVisible(false);
            editSupDate.setVisible(true);
            editName.setVisible(true);
            editEmail.setVisible(true);   
            editDeleteBtn.setVisible(true);        
            editSubmitBtn.setVisible(true);
            
            String cost = Double.toString(supList.get(pos).getWeeklyCost());
            
            editName.setText(supList.get(pos).getSupplementName());
            editEmail.setText(cost);
            editSupDate.setText(supList.get(pos).getDate().toString());
        }
    }
    
    private Stage newWindow(String item) throws Exception{
        // new window to print out customer's bill or messages.
        invoiceAndMsgView = new ListView<String>();
        Stage newWindow = new Stage();
        Scene sc;
        Group root1 = new Group();
        
        
        
        invoiceAndMsgView = ms.printList(nameToLookFor, item);
        
        invoiceAndMsgView.setMaxSize(494, 472);
        invoiceAndMsgView.setMinSize(494, 472);
        
        newWindow.setTitle(item);
        // Ideal width for the application
        newWindow.setWidth(500);//975  1036
        // Ideal height for the application
        newWindow.setHeight(500);//680  645
        // Disabled resizable for have best experience on designed resolution.
        newWindow.setResizable(false);
        root1.getChildren().add(invoiceAndMsgView);
        sc = new Scene(root1);
        newWindow.setScene(sc);
        return newWindow;
    }
    
    private Stage newEditWindow(String type) throws Exception{
        // new edit window for some of the information editing, like address,
        // supplement list, and paying informations.
        
        Stage newEditWindow = new Stage();
        Scene sc;
        GridPane gPEdit = new GridPane();
        ScrollPane sPEdit = new ScrollPane(gPEdit);
        Group rootEdit = new Group(sPEdit);
        // gridpane setting
        gPEdit.setAlignment(Pos.TOP_LEFT);
        gPEdit.setHgap(50);
        gPEdit.setVgap(20);
        gPEdit.setMinSize(500, 800);
        gPEdit.setMaxSize(500, 80);
        
        // All visual setting
        Button submitEditBtn = new Button("Submit");
        submitEditBtn.setPrefSize(150, 40);
        Button removeEditBtn = new Button("Remove");
        removeEditBtn.setPrefSize(150, 40);
        ListView<String> editListView = new ListView<String>();
        editListView.setPrefSize(300, 200);
        
        Label editLabelOne = new Label();
        editLabelOne.setPrefSize(150, 20);
        Label editLabelTwo = new Label();
        editLabelTwo.setPrefSize(150, 20);
        Label editLabelThree = new Label();
        editLabelThree.setPrefSize(150, 20);
        Label editLabelFour = new Label();
        editLabelFour.setPrefSize(150, 20);
        TextField editOne = new TextField();
        editOne.setPrefSize(200, 20);
        TextField editTwo = new TextField();
        editTwo.setPrefSize(200, 20);
        TextField editThree = new TextField();
        editThree.setPrefSize(200, 20);
        TextField editFour = new TextField();
        editFour.setPrefSize(200, 20);
        TextField longBarEdit = new TextField();
        longBarEdit.setPrefSize(200, 20);
        
        // Font setting
        submitEditBtn.setFont(TitleFont);
        removeEditBtn.setFont(TitleFont);
        editLabelOne.setFont(TitleFont);
        editLabelTwo.setFont(TitleFont);
        editLabelThree.setFont(TitleFont);
        editLabelFour.setFont(TitleFont);
        editOne.setFont(infoDataFont);
        editTwo.setFont(infoDataFont);
        editThree.setFont(infoDataFont);
        editFour.setFont(infoDataFont);
        longBarEdit.setFont(infoDataFont);
        
        
        
        // ListView type edit
        gPEdit.add(editListView, 2, 1, 6, 10);
        gPEdit.add(longBarEdit, 3, 12, 4, 1);
        editListView.setVisible(false);
        longBarEdit.setVisible(false);
        
        // TextField type edit
        gPEdit.add(editLabelOne, 1, 1, 3, 1);
        gPEdit.add(editOne, 5, 1, 4, 1);
        editLabelOne.setVisible(false);
        editOne.setVisible(false);
        
        gPEdit.add(editLabelTwo, 1, 3, 3, 1);
        gPEdit.add(editTwo, 5, 3, 4, 1);
        editLabelTwo.setVisible(false);
        editTwo.setVisible(false);
        
        gPEdit.add(editLabelThree, 1, 5, 3, 1);
        gPEdit.add(editThree, 5, 5, 4, 1);
        editLabelThree.setVisible(false);
        editThree.setVisible(false);
        
        gPEdit.add(editLabelFour, 1, 7, 3, 1);
        gPEdit.add(editFour, 5, 7, 4, 1);
        editLabelFour.setVisible(false);
        editFour.setVisible(false);
        
        // Buttons setting
        gPEdit.add(removeEditBtn, 1, 10, 3, 2);
        gPEdit.add(submitEditBtn, 5, 10, 3, 2);
        removeEditBtn.setVisible(false);
        submitEditBtn.setVisible(false);
        
        // find associate customer or paying customer
        for(int i=0;i<acList.size();i++){
            if(acList.get(i).getEmail().equalsIgnoreCase(cusListView.getSelectionModel().getSelectedItem())){
                editPos = i;
                ac = true;
            }
        }
        for(int i=0;i<pcList.size();i++){
            if(pcList.get(i).getEmail().equalsIgnoreCase(cusListView.getSelectionModel().getSelectedItem())){
                editPos = i;
                ac = false;
            }
        }
        
        
        // If editing the address
        if(type.equalsIgnoreCase("address")){
            editType = "address";
        gPEdit.getChildren().remove(removeEditBtn);
        gPEdit.getChildren().remove(submitEditBtn);
        gPEdit.add(removeEditBtn, 1, 10, 3, 2);
        gPEdit.add(submitEditBtn, 5, 10, 3, 2);
        editListView.setVisible(false);
        longBarEdit.setVisible(false);
        editLabelOne.setVisible(true);
        editOne.setVisible(true);
        editLabelTwo.setVisible(true);
        editTwo.setVisible(true);
        editLabelThree.setVisible(true);
        editThree.setVisible(true);
        editLabelFour.setVisible(true);
        editFour.setVisible(true);
        removeEditBtn.setVisible(false);
        submitEditBtn.setVisible(true);
        
        
        
        
        editLabelOne.setText("Street Number:");
        editLabelTwo.setText("Street Name:");
        editLabelThree.setText("Suburb:");
        editLabelFour.setText("Post code:");
        
        if(ac){
            editOne.setText(acList.get(editPos).getAddress().getStreetNumber());
            editTwo.setText(acList.get(editPos).getAddress().getStreetName());
            editThree.setText(acList.get(editPos).getAddress().getSuburb());
            editFour.setText(acList.get(editPos).getAddress().getPostCode());
        }else{
            editOne.setText(pcList.get(editPos).getAddress().getStreetNumber());
            editTwo.setText(pcList.get(editPos).getAddress().getStreetName());
            editThree.setText(pcList.get(editPos).getAddress().getSuburb());
            editFour.setText(pcList.get(editPos).getAddress().getPostCode());
        }
        
        // If editing the supplement
        }else if(type.equalsIgnoreCase("supplement")){
            editType = "supplement";
            gPEdit.getChildren().remove(removeEditBtn);
            gPEdit.getChildren().remove(submitEditBtn);
            gPEdit.add(removeEditBtn, 2, 14, 3, 2);
            gPEdit.add(submitEditBtn, 5, 14, 3, 2);
            editListView.setVisible(true);
            longBarEdit.setVisible(true);
            removeEditBtn.setVisible(true);
            submitEditBtn.setVisible(true);
            editLabelOne.setVisible(false);
            editOne.setVisible(false);
            editLabelTwo.setVisible(false);
            editTwo.setVisible(false);
            editLabelThree.setVisible(false);
            editThree.setVisible(false);
            editLabelFour.setVisible(false);
            editFour.setVisible(false);
            
            if(ac){
                for(int i=0;i<acList.get(editPos).getList().getSupList().size();i++){
                     editListView.getItems().add(acList.get(editPos).getList().getSupList().get(i).getSupplementName());
                }
            }else{
                for(int i=0;i<pcList.get(editPos).getList().getSupList().size();i++){
                     editListView.getItems().add(pcList.get(editPos).getList().getSupList().get(i).getSupplementName());
                }
            }
            
            // if editing the paying for
        }else if(type.equalsIgnoreCase("payingFor")){
            editType = "payingFor";
            gPEdit.getChildren().remove(removeEditBtn);
            gPEdit.getChildren().remove(submitEditBtn);
            gPEdit.add(removeEditBtn, 2, 14, 3, 2);
            gPEdit.add(submitEditBtn, 5, 14, 3, 2);
            editListView.setVisible(true);
            longBarEdit.setVisible(true);
            removeEditBtn.setVisible(true);
            submitEditBtn.setVisible(true);
            editLabelOne.setVisible(false);
            editOne.setVisible(false);
            editLabelTwo.setVisible(false);
            editTwo.setVisible(false);
            editLabelThree.setVisible(false);
            editThree.setVisible(false);
            editLabelFour.setVisible(false);
            editFour.setVisible(false);
            
            for(int i=0;i<pcList.get(editPos).getPayingAssociateCusList().size();i++){
                editListView.getItems().add(pcList.get(editPos).getPayingAssociateCusList().get(i).toString());
            }
            // if editing the paying information
        }else if(type.equalsIgnoreCase("payingInfo")){
            editType = "payingInfo";
            gPEdit.getChildren().remove(removeEditBtn);
            gPEdit.getChildren().remove(submitEditBtn);
            gPEdit.add(removeEditBtn, 1, 8, 3, 2);
            gPEdit.add(submitEditBtn, 5, 8, 3, 2);
            editListView.setVisible(false);
            longBarEdit.setVisible(false);
            editLabelOne.setVisible(true);
            editOne.setVisible(true);
            editLabelTwo.setVisible(true);
            editTwo.setVisible(true);
            editLabelThree.setVisible(true);
            editThree.setVisible(true);
            editLabelFour.setVisible(false);
            editFour.setVisible(false);
            removeEditBtn.setVisible(false);
            submitEditBtn.setVisible(true);
            
            if(pcList.get(editPos).getPayingType().equalsIgnoreCase("Credit card")){
                editLabelOne.setText("Name:");
                editLabelTwo.setText("Credit card:");
                editLabelThree.setText("Exp date:");
                editOne.setText(pcList.get(editPos).getCcPayType().getName());
                editTwo.setText(pcList.get(editPos).getCcPayType().getCardNumber());
                editThree.setText(pcList.get(editPos).getCcPayType().getExpiryDate());
            }else{
                editLabelOne.setText("Name:");
                editLabelTwo.setText("Bank name:");
                editLabelThree.setText("Account number:");
                editOne.setText(pcList.get(editPos).getBaPayType().getName());
                editTwo.setText(pcList.get(editPos).getBaPayType().getBankName());
                editThree.setText(pcList.get(editPos).getBaPayType().getAccountNumber());
            }
        }
        
        // Submit button to update the edited data
        submitEditBtn.setOnAction((ActionEvent event) -> {
            // edit payment info button
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    try {
                        if(editType.equalsIgnoreCase("address")){
                            // Address add
                            address adrs = new address(editOne.getText().trim(),editTwo.getText().trim(),
                            editThree.getText().trim(),editFour.getText().trim());
                            if(ac){
                                acList.get(editPos).setAddress(adrs);
                            }else{
                                pcList.get(editPos).setAddress(adrs);
                            }
                            doAlert(Alert.AlertType.INFORMATION,gPEdit.getScene().getWindow(),"DONE",
                                    "Information edited.");
                            reloadFile();
                        }else if(editType.equalsIgnoreCase("supplement")){
                            // Supplement add
                            boolean isExist = false;
                            boolean canAdd = true;
                            int supPos =0;
                            for(int i=0;i<supList.size();i++){
                                if(supList.get(i).getSupplementName().equalsIgnoreCase(longBarEdit.getText().trim())){
                                    isExist = true;
                                    supPos = i;
                                }
                            }
                            if(ac){
                                for(int i=0;i<acList.get(editPos).getList().getSupList().size();i++){
                                    if(acList.get(editPos).getList().getSupList().get(i).getSupplementName().equalsIgnoreCase(longBarEdit.getText().trim())){
                                        // ALERT
                                        canAdd = false;
                                    }
                                }
                                if(canAdd && isExist){
                                    editListView.getItems().add(supList.get(supPos).getSupplementName());
                                    acList.get(editPos).getList().addSupplement(supList.get(supPos));
                                    doAlert(Alert.AlertType.INFORMATION,gPEdit.getScene().getWindow(),"DONE",
                                    "Information edited.");
                                }else{
                                    doAlert(Alert.AlertType.WARNING,gPEdit.getScene().getWindow(),"Please",
                                    "add only the existing supplement.");
                                }
                                
                            }else{
                                for(int i=0;i<pcList.get(editPos).getList().getSupList().size();i++){
                                    if(pcList.get(editPos).getList().getSupList().get(i).getSupplementName().equalsIgnoreCase(longBarEdit.getText().trim())){
                                        // ALERT
                                        canAdd = false;
                                    }
                                }
                                if(canAdd && isExist){
                                    editListView.getItems().add(supList.get(supPos).getSupplementName());
                                    pcList.get(editPos).getList().addSupplement(supList.get(supPos));
                                }
                            }
                            reloadFile();
                        }else if(editType.equalsIgnoreCase("payingFor")){
                            // paying for edit, only paying customer
                            boolean isEditable = true;
                            boolean isExist = false;
                            
                            for(int i=0;i<pcList.get(editPos).getPayingAssociateCusList().size();i++){
                                if(longBarEdit.getText().trim().equalsIgnoreCase(pcList.get(editPos).getPayingAssociateCusList().get(i).toString())){
                                    // ALERT already in the list.
                                    isEditable = false;
                                }
                            }
                            
                            for(int i=0;i<acList.size();i++){
                                if(acList.get(i).getEmail().equalsIgnoreCase(longBarEdit.getText().trim())){
                                    isExist = true;
                                }
                            }
                            
                            if(isExist && isEditable){
                                editListView.getItems().add(longBarEdit.getText().trim());
                                pcList.get(editPos).addAssociateCustomer(acList, longBarEdit.getText().trim());
                            }else{
                                doAlert(Alert.AlertType.WARNING,gPEdit.getScene().getWindow(),"ERROR",
                                    "Please add only existing associate customer.");
                            }
                            reloadFile();
                        }else if(editType.equalsIgnoreCase("payingInfo")){
                            // edit paying info
                            if(pcList.get(editPos).getPayingType().equalsIgnoreCase("Credit card")){
                                creditCard newCard = new creditCard(editOne.getText().trim(),editTwo.getText().trim(),
                                editThree.getText().trim());
                                pcList.get(editPos).setCcPayType(newCard);
                            }else{
                                bankAccount newAcc = new bankAccount(editOne.getText().trim(),editTwo.getText().trim(),
                                editThree.getText().trim());
                                pcList.get(editPos).setBaPayType(newAcc);
                            }
                            doAlert(Alert.AlertType.INFORMATION,gPEdit.getScene().getWindow(),"DONE",
                                    "Information edited.");
                            reloadFile();
                        }
                    } catch (Exception ex) {
                        System.out.println("Edit window submit button error.");
                    }   
                });
            });
            t.start();
        });
        
        removeEditBtn.setOnAction((ActionEvent event) -> {
            // edit submit button to confirm and make the changes.
            try {
                if(editType.equalsIgnoreCase("payingFor")){
                    for(int i=0;i<pcList.get(editPos).getPayingAssociateCusList().size();i++){
                        if(editListView.getSelectionModel().getSelectedItem().equalsIgnoreCase(pcList.get(editPos).getPayingAssociateCusList().get(i).toString())){
                            pcList.get(editPos).getPayingAssociateCusList().remove(i);
                        }
                    }
                    editListView.getItems().remove(editListView.getSelectionModel().getSelectedIndex());
                }else if(editType.equalsIgnoreCase("supplement")){
                    if(ac){
                        for(int i=0;i<acList.get(editPos).getList().getSupList().size();i++){
                            if(acList.get(editPos).getList().getSupList().get(i).getSupplementName().equalsIgnoreCase(editListView.getSelectionModel().getSelectedItem().trim())){
                                acList.get(editPos).getList().getSupList().remove(i);
                                doAlert(Alert.AlertType.INFORMATION,gPEdit.getScene().getWindow(),"DONE",
                                    "REMOVED.");
                            }
                        }
                        editListView.getItems().remove(editListView.getSelectionModel().getSelectedIndex());
                    }else{
                        for(int i=0;i<pcList.get(editPos).getList().getSupList().size();i++){
                            if(pcList.get(editPos).getList().getSupList().get(i).getSupplementName().equalsIgnoreCase(editListView.getSelectionModel().getSelectedItem().trim())){
                                pcList.get(editPos).getList().getSupList().remove(i);
                                doAlert(Alert.AlertType.INFORMATION,gPEdit.getScene().getWindow(),"DONE",
                                    "REMOVED.");
                            }
                        }
                        editListView.getItems().remove(editListView.getSelectionModel().getSelectedIndex());
                    }
                }
                reloadFile();
            } catch (Exception ex) {
                System.out.println("Edit window remove button error.");
            }
        });

        
        sPEdit.setMinSize(510, 610);
        sPEdit.setMaxSize(510, 610);
        sPEdit.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sc= new Scene(rootEdit, 500, 600);
        newEditWindow.setResizable(false);
        newEditWindow.setScene(sc);
        return newEditWindow;
    }
    
    private void doAlert(Alert.AlertType alertType, Window owner, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
    private void buildView() throws Exception{
        // Alert for asking read file or create new
        Alert readOrNew = new Alert(AlertType.CONFIRMATION);
        readOrNew.setTitle("Read file?");
        readOrNew.setHeaderText("Read SER file or create new");
        readOrNew.setContentText("Please press OK to select SER file to read,"
                + " or press cancel to run as new file.");
        Optional<ButtonType> result = readOrNew.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            // file chooser to select file to read in
            // by default, this program only can read the designed data.txt
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new ExtensionFilter("SER File","*.ser"));
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            fc.setInitialDirectory(new File(currentPath+"\\src\\ict373_a2\\Data"));
            File selectedFile = fc.showOpenDialog(null);
            ms = magazineService.readSerialisedFile(selectedFile.toString());
        }
        
        // Call out all default components
        hBox();
        cusListDisplay();
        supListDisplay();
        informationPanel();
        
        // Put view mode as default page.
        //viewBtn.setDisable(true);
        isView = true;
        sP.setContent(infoPanel);
        
        cusListView.setOnMouseClicked((MouseEvent event) -> {
            // Customer list view action on mouse click with thread applied
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    try {
                        isCus = true;
                        String cusEmail = cusListView.getSelectionModel().getSelectedItem();
                        if(cusEmail != null){
                            printCus(cusEmail);
                        }
                    } catch (Exception ex) {
                        System.out.println("Customer list view error.");
                    }   
                });
            });
            t.start();
        });
        
        supListView.setOnMouseClicked((MouseEvent event) -> {
            // Supplement list view action on mouse click with thread applied
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    try {
                        isCus = false;
                        String supName = supListView.getSelectionModel().getSelectedItem();
                        if(supName != null){
                            printSup(supName);
                        }
                    } catch (Exception ex) {
                        System.out.println("Supplement list view error.");
                    }   
                });
            });
            t.start();
        });
        
        invoiceBtn.setOnAction((ActionEvent event) -> {
            // invoice button to open invoice list window.
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    Stage wd;
                    try {
                        wd = newWindow("Invoice");
                        wd.show();
                    } catch (Exception ex) {
                        System.out.println("Invoice button error.");
                    }
                });
            });
            t.start();
        });
        
        msgBtn.setOnAction((ActionEvent event) -> {
            // notification button to open list window.
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    Stage wd;
                    try {
                        wd = newWindow("Notification");
                        wd.show();
                    } catch (Exception ex) {
                        System.out.println("Message button error.");
                    }
                });
            });
            t.start();
        });
        
        createBtn.setOnAction((ActionEvent event) -> {
            // create mode button to open a create page.
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    Stage wd;
                    try {
                        createBtn.setDisable(true);
                        cusListView.setDisable(true);
                        supListView.setDisable(true);
                        viewBtn.setDisable(false);
                        createBtn.setDisable(false);
                        editBtn.setDisable(false);
                        wd = ms.createNew();
                        wd.initOwner(stage);
                        wd.initModality(Modality.WINDOW_MODAL);
                        wd.show();
                        
                    } catch (Exception ex) {
                        System.out.println("Create button error.");
                    }
                });
            });
            t.start();
        });
        
        viewBtn.setOnAction((ActionEvent event) -> {
            // view mode button that includes feature of refreshing data list
            try {
                isView = true;
                informationPanel();
                sP.setContent(infoPanel);
                viewBtn.setDisable(true);
                createBtn.setDisable(false);
                editBtn.setDisable(false);
                cusListView.setDisable(false);
                supListView.setDisable(false);
                
                ansName.setVisible(true);
                ansEmail.setVisible(true);
                ansAdrs.setVisible(true);
                ansSupList.setVisible(true);
                ansDateSub.setVisible(true);
                ansCusType.setVisible(true); 
                ansPaidBy.setVisible(true);
                ansPaymentMethod.setVisible(true);
                ansPayingFor.setVisible(true); 
                invoiceBtn.setVisible(true);
                msgBtn.setVisible(true);
                invoiceBtn.setVisible(true);
                msgBtn.setVisible(true);
                
                reloadFile();
            } catch (Exception ex) {
                System.out.println("View button error.");
            }
        });
        
        editBtn.setOnAction((ActionEvent event) -> {
            // edit mode to modify current data
            try {
                isView = false;
                editPanel();
                sP.setContent(editPanel);

                viewBtn.setDisable(false);
                createBtn.setDisable(false);
                editBtn.setDisable(true);
                cusListView.setDisable(false);
                supListView.setDisable(false);
                
                reloadFile();
            } catch (Exception ex) {
                System.out.println("Edit button error.");
            }
        });
        
        editAddressBtn.setOnAction((ActionEvent event) -> {
            // edit address button to open a new window for modify address
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    Stage wd;
                    try {
                            wd = newEditWindow("address");
                            wd.setTitle("Edit address");
                            wd.initOwner(stage);
                            wd.initModality(Modality.WINDOW_MODAL);
                            wd.show();
                        
                    } catch (Exception ex) {
                        System.out.println("Open eidt address error.");
                    }   
                });
            });
            t.start();
        });
        
        editSupplementBtn.setOnAction((ActionEvent event) -> {
            // edit supplement list button
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    Stage wd;
                    try {
                            wd = newEditWindow("supplement");
                            wd.setTitle("Edit supplement");
                            wd.initOwner(stage);
                            wd.initModality(Modality.WINDOW_MODAL);
                            wd.show();
                            
                    } catch (Exception ex) {
                        System.out.println("Open edit suplement error.");
                    }   
                });
            });
            t.start();
        });
        
        editPayInfoBtn.setOnAction((ActionEvent event) -> {
            // edit payment info button
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    Stage wd;
                    try {
                            wd = newEditWindow("payingInfo");
                            wd.setTitle("Edit paying info");
                            wd.initOwner(stage);
                            wd.initModality(Modality.WINDOW_MODAL);
                            wd.show();
                        
                    } catch (Exception ex) {
                        System.out.println("Open edit pay info error.");
                    }   
                });
            });
            t.start();
        });
        
        editPayForBtn.setOnAction((ActionEvent event) -> {
            // edit add or remove associate customer button
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    Stage wd;
                    try {
                            wd = newEditWindow("payingFor");
                            wd.setTitle("Edit associate list");
                            wd.initOwner(stage);
                            wd.initModality(Modality.WINDOW_MODAL);
                            wd.show();
                        
                    } catch (Exception ex) {
                        System.out.println("Open edit paying button error.");
                    }   
                });
            });
            t.start();
        });
        
        editDeleteBtn.setOnAction((ActionEvent event) -> {
            // delete button for deleting existing data
            try {
                isView = false;
                if(!isCus){
                    // DELETE SUPPLEMENT
                    ms.removeSupplement(supListView.getSelectionModel().getSelectedItem());
                    doAlert(Alert.AlertType.INFORMATION,root.getScene().getWindow(),"DONE",
                                    "Removed.");
                    reloadFile();
                }else{
                    // DELETE CUSTOMER
                    ms.removeCustomer(cusListView.getSelectionModel().getSelectedItem());
                    doAlert(Alert.AlertType.INFORMATION,root.getScene().getWindow(),"DONE",
                                    "Removed.");
                    reloadFile();
                }
            } catch (Exception ex) {
                System.out.println("Deletion error.");
            }
        });
        
        editSubmitBtn.setOnAction((ActionEvent event) -> {
            // edit submit button to confirm and make the changes.
            try {
                isView = false;
                if(!isCus){
                    double cost = Double.parseDouble(editEmail.getText().trim());
                    ms.updateSup(supListView.getSelectionModel().getSelectedItem(), 
                            editName.getText().trim(), cost);
                    reloadFile();
                }else{
                    ms.updateCus(cusListView.getSelectionModel().getSelectedItem().trim(),
                            editName.getText().trim(), editEmail.getText().trim(), editPaidBy.getText().trim());
                    reloadFile();
                }
                reloadFile();
            } catch (Exception ex) {
                System.out.println("Submit edit Customer or supplement info error.");
            }
        });
        

        sP.setLayoutX(310);
        sP.setLayoutY(90);
        sP.setMinSize(700, 505);
        sP.setMaxSize(700, 505);
        sP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        
        btnAndList.setBackground(new Background(new BackgroundFill(Color.YELLOWGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        btnAndList.setPadding(new Insets(10,10,10,10)); 
        //btnAndList.setGridLinesVisible(true);
        btnAndList.setVgap(20);
        btnAndList.setHgap(50);

        // Title
        stage.setTitle("Magazine Service System");
        // Ideal width for the application
        stage.setWidth(1026);//975  1036
        // Ideal height for the application
        stage.setHeight(633);//680  645
        // Disabled resizable for have best experience on designed resolution.
        stage.setResizable(false);
        
        root.getChildren().add(btnAndList);
        root.getChildren().add(sP);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            // ask for save file before exit the application
            try {
                event.consume();
                Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout!");
		alert.setContentText("Do you want to save before exiting?");
		
		if (alert.showAndWait().get() == ButtonType.OK){
                    FileChooser fc = new FileChooser();
                    fc.getExtensionFilters().add(new ExtensionFilter("SER File","*.ser"));
                    String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
                    fc.setInitialDirectory(new File(currentPath+"\\src\\ict373_a2\\Data"));
                    File saveFile = fc.showSaveDialog(stage);
                    magazineService.writeSerialisedFile(ms, saveFile.toString());
			System.out.println("You successfully logged out");
			stage.close();
		}else{
                    stage.close();
                }
            } catch (IOException ex) {
                System.out.println("Exit save file error.");
            }
        });
        stage.show();
    }
}
        
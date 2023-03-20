package ict373_a2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
 * magazineService class.
 * 
 * This class handle and interact with the data, like add new customer, delete customer
 * , add supplement, delete supplement, print bill, print messages, read and write the 
 * serialized data.
 * 
 */

public class magazineService implements Serializable{
	// Implemented serializable
    // Objects that will be use for manage magazine service system
    private magazine mag;	// Magazine			
    private ArrayList<associateCustomer> acList; // Arraylist of associate customer 
    private ArrayList<payingCustomer> pcList;	 // Arraylist of paying customer
    private ArrayList<supplement> supList;		 // Arraylist of supplements
    private ArrayList<String> supName = new ArrayList<String>(); // Arraylist of supplement name
    private ArrayList<Double> supCost = new ArrayList<Double>(); // Arraylist of supplement cost
    private LocalDate today = LocalDate.now(); // Today's date
    private String registerType =""; // Define which type of customer is registering
    
    // Font setting
    private final Font TitleFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 19);
    
    // Default constructor
    public magazineService(){
        this.acList = new ArrayList<associateCustomer>();
        this.pcList = new ArrayList<payingCustomer>();
        this.supList = new ArrayList<supplement>();
    }
    
    // Set method
    public void setAcList(ArrayList<associateCustomer> ac){
        this.acList = ac;
    }
    
    // Set method
    public void setPcList(ArrayList<payingCustomer> pc){
        this.pcList = pc;
    }
    
    // Set method
    public void setSupList(ArrayList<supplement> sp){
        this.supList = sp;
    }
    
    // Get method
    public ArrayList<associateCustomer> getAcList(){
        return this.acList;
    }
    
    // Get method
    public ArrayList<payingCustomer> getPcList(){
        return this.pcList;
    }
    
    // Get method
    public ArrayList<supplement> getSupList(){
        return this.supList;
    }
    
    public static void writeSerialisedFile(magazineService ms, String filePath) throws FileNotFoundException, IOException{
        // Write the data into serialized file. ( SER file)
        FileOutputStream fos = new FileOutputStream(filePath);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        ArrayList<Object> serialized = new ArrayList<Object>();
        
        // Put all data into object list
        serialized.add(ms.acList);  // 0
        serialized.add(ms.pcList);  // 1
        serialized.add(ms.supList); // 2
        oos.writeObject(serialized);
        oos.close();
        fos.close();
    }
    
    public static magazineService readSerialisedFile(String filePath) throws IOException, ClassNotFoundException{
        // Load all the data from the SER file.
        FileInputStream fis = new FileInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Object> deserialized = new ArrayList<Object>();
        magazineService ms = new magazineService();
        
        deserialized = (ArrayList<Object>)ois.readObject();
        // Read in the data into object with the order of how it was serialized.
        ms.setAcList((ArrayList<associateCustomer>)deserialized.get(0));    // 0
        ms.setPcList((ArrayList<payingCustomer>)deserialized.get(1));       // 1   
        ms.setSupList((ArrayList<supplement>)deserialized.get(2));          // 2
        ois.close();
        fis.close();
        return ms;
    }
    
    public ListView<String> printList(String cusName, String invoiceOrMsg) throws Exception{
        // This function is use for fill out the list of invoices or messages of the customer
        ListView<String> list = new ListView<String>();
         Stage infoWindow = new Stage();
         LocalDate fromDate = LocalDate.now();
         long dayBetween = 0;
         boolean isAc = true;
         String nam ="";
         
         if(invoiceOrMsg.equalsIgnoreCase("Invoice")){
             // Invoice only for Paying customer
            for(int i=0;i<pcList.size();i++){
                if(pcList.get(i).getEmail().equalsIgnoreCase(cusName)){
                    nam = pcList.get(i).getName();
                    fromDate = pcList.get(i).getDateSub();
                    dayBetween = ChronoUnit.DAYS.between(fromDate, today);
                    dayBetween = dayBetween / 30;
                    isAc = true;
                }
            }
            
         }else{
             // Message for both type customer
             for(int i=0;i<acList.size();i++){
                if(acList.get(i).getEmail().equalsIgnoreCase(cusName)){
					// Associate customer
                    nam = acList.get(i).getName();
                    fromDate = acList.get(i).getDateSub();
                    dayBetween = ChronoUnit.DAYS.between(fromDate, today);
                    dayBetween = dayBetween / 7;
                    isAc = true;
                }
            }
             for(int i=0;i<pcList.size();i++){
                if(pcList.get(i).getEmail().equalsIgnoreCase(cusName)){
					// Paying customer
                    nam = pcList.get(i).getName();
                    fromDate = pcList.get(i).getDateSub();
                    dayBetween = ChronoUnit.DAYS.between(fromDate, today);
                    dayBetween = dayBetween / 7;
                    isAc = false;
                }
            }
         }
        for(int i=0;i<dayBetween;i++){
            list.getItems().add(invoiceOrMsg+" #"+(i+1));
        }
        
        if(isAc || invoiceOrMsg.equalsIgnoreCase("Invoice")){
			// Invoices
            if(invoiceOrMsg.equalsIgnoreCase("Invoice")){
                infoWindow = infoWindow(list, "acList", "Invoice", nam, fromDate);
            }else{
                infoWindow = infoWindow(list, "acList", "Notification", nam, fromDate);
            }
        }else{
			// Messages
            infoWindow = infoWindow(list, "pcList", "Notification", nam, fromDate);
        }
        return list; 
    }
    
    private String printBill(String cName) throws Exception{
        // Bill toString method
        String emailSent = "Email sent to ";
        String ready = ", your monthly is ready!\nPlease check the bill below.\n\n";
        String info = "";
        for(int i=0;i<pcList.size();i++){
            if(pcList.get(i).getName().equalsIgnoreCase(cName)){
                info += emailSent+pcList.get(i).getEmail();
                info += pcList.get(i).toString();
            }
        }
        
        
        return info;
    }
    
    private String printMsg(String listName, String cName) throws Exception{
        // Message toString method
        String emailSent = "Email sent to ";
        String ready = ",\nGood news! The WeeklyFun magazine is available now!\n"
                + "plaese kindly check on the website and start browsing!\n\n";
        String info = "";
        if(listName.equalsIgnoreCase("acList")){
			// Associate customer
            for(int i=0;i<acList.size();i++){
                if(acList.get(i).getName().equalsIgnoreCase(cName)){
                    info += emailSent+acList.get(i).getEmail()+ready;
                }
            }
        }else{
            for(int i=0;i<pcList.size();i++){
				// Paying custoemr
                if(pcList.get(i).getName().equalsIgnoreCase(cName)){
                    info += emailSent+pcList.get(i).getEmail()+ready;
                }
            }
        }
        
        
        return info;
    }
    
    public Stage infoWindow(ListView<String> list, String cList, 
            String info, String cName, LocalDate fromDay){
        // The method of handles the interaction of customer bill or message list 
        Stage newWindow = new Stage();
        
        list.setOnMouseClicked((MouseEvent event) -> {
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    try {
                        Text text = new Text();
                        if(list.getItems().get(0) != null){
                            String num = list.getSelectionModel().getSelectedItem();
                            int number = Integer.parseInt(num.replaceAll("[^0-9]", ""));
                            
                            text.setText("");

                            if(info.equalsIgnoreCase("Invoice")){
                                // Invoices
                                text.setText(printBill(cName) +"\nDate sent " + fromDay.plus(number*30,ChronoUnit.DAYS));
                            }else{
                                // Messages
                                    text.setText(printMsg(cList, cName)+ "\nDate sent " + fromDay.plus(number*7,ChronoUnit.DAYS));
                            }
                        }
                        ScrollPane sP = new ScrollPane(text);
                        sP.setMinSize(410, 410);
                        sP.setMaxSize(410, 410);
                        sP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                        Group root = new Group(sP);
                        Scene sc = new Scene(root,400, 400);
                        newWindow.setResizable(false);
                        newWindow.setScene(sc);
                        newWindow.show();
                    } catch (Exception ex) {
                        System.out.println("Create invoice or message error.");
                    }   
                });
            });
            t.start();
        });
        return newWindow;
    }
    
    public Stage createNew(){
        // This method contains all the setting of create new customer or supplement
        // window.
        Stage createPage = new Stage();
        GridPane gP = new GridPane();
        gP.setAlignment(Pos.TOP_LEFT);
        gP.setHgap(50);
        gP.setVgap(20);
        gP.setMinSize(500, 800);
        gP.setMaxSize(500, 80);
        
        // Button setting
        Button createCusBtn = new Button("Customer");
        createCusBtn.setPrefSize(150, 40);
        
        Button createSupBtn = new Button("Supplement");
        createSupBtn.setPrefSize(150, 40);
        
        Button createAcBtn = new Button("Associate");
        createAcBtn.setPrefSize(150, 40);
        createAcBtn.setVisible(false);
        
        Button createPcBtn = new Button("Paying");
        createPcBtn.setPrefSize(150, 40);
        createPcBtn.setVisible(false);
        
        Button ccBtn = new Button("Credit card");
        ccBtn.setPrefSize(150, 40);
        ccBtn.setVisible(false);
        
        Button baBtn = new Button("Bank transfer");
        baBtn.setPrefSize(150, 40);
        baBtn.setVisible(false);
        
        Button submitSupBtn = new Button("Create");
        submitSupBtn.setPrefSize(150, 40);
        submitSupBtn.setVisible(false);
        
        Button submitCusBtn = new Button("Create");
        submitCusBtn.setPrefSize(150, 40);
        submitCusBtn.setVisible(false);
        
        
        gP.add(createCusBtn, 1, 1, 3, 2);
        gP.add(createSupBtn, 5, 1, 3, 2);
        gP.add(createAcBtn, 1, 1, 3, 2);
        gP.add(createPcBtn, 5, 1, 3, 2);
        gP.add(ccBtn, 1, 1, 3, 2);
        gP.add(baBtn, 5, 1, 3, 2);
        gP.add(submitSupBtn, 5, 8, 3, 2);
        gP.add(submitCusBtn, 5, 30, 3, 2);
        
        // Label and font setting
        Label name = new Label();
        name.setFont(TitleFont);
        name.setPrefSize(150, 20);
        
        Label costOrEmail = new Label();
        costOrEmail.setFont(TitleFont);
        costOrEmail.setPrefSize(150, 20);
        
        Label streetNum = new Label();
        streetNum.setFont(TitleFont);
        streetNum.setPrefSize(150, 20);
        
        Label streetName = new Label();
        streetName.setFont(TitleFont);
        streetName.setPrefSize(150, 20);
        
        Label suburb = new Label();
        suburb.setFont(TitleFont);
        suburb.setPrefSize(150, 20);
        
        Label postCode = new Label();
        postCode.setFont(TitleFont);
        postCode.setPrefSize(150, 20);
        
        Label sups = new Label();
        sups.setFont(TitleFont);
        sups.setPrefSize(150, 20);
        
        
        Label payName = new Label();
        payName.setFont(TitleFont);
        payName.setPrefSize(150, 20);
        
        Label creditCardOrBankTransfer = new Label();
        creditCardOrBankTransfer.setFont(TitleFont);
        creditCardOrBankTransfer.setPrefSize(150, 20);
        
        Label expDateOrAccNumber = new Label();
        expDateOrAccNumber.setFont(TitleFont);
        expDateOrAccNumber.setPrefSize(150, 20);
        
        //name.setText("Name:");
        gP.add(name, 1, 4, 3, 2);
        
        //costOrEmail.setText("Email:");
        gP.add(costOrEmail, 1, 6, 3, 2);
        
        //streetNum.setText("Street Number:");
        gP.add(streetNum, 1, 8, 3, 2);
        
        //streetName.setText("Street Name:");
        gP.add(streetName, 1, 10, 3, 2);
        
        //suburb.setText("Suburb:");
        gP.add(suburb, 1, 12, 3, 2);
        
        //postCode.setText("Post code:");
        gP.add(postCode, 1, 14, 3, 2);
        
        //sups.setText("Supplements:");
        gP.add(sups, 1, 16, 3, 2);
        
        //payName.setText("Name:");
        gP.add(payName, 1, 22, 3, 2);
        
        //creditCardOrBankTransfer.setText("Credit card:");
        gP.add(creditCardOrBankTransfer, 1, 24, 3, 2);
        
        //expDateOrAccNumber.setText("Exp date:");
        gP.add(expDateOrAccNumber, 1, 26, 3, 2);
        
        // For all customer
        TextField nameField = new TextField();
        nameField.setVisible(false);
        nameField.setPrefSize(150, 20);
        gP.add(nameField, 4, 4, 3, 2);
        
        TextField costOrEmailField = new TextField();
        costOrEmailField.setVisible(false);
        costOrEmailField.setPrefSize(150, 20);
        gP.add(costOrEmailField, 4, 6, 3, 2);
        
        TextField streetNumField = new TextField();
        streetNumField.setVisible(false);
        streetNumField.setPrefSize(150, 20);
        gP.add(streetNumField, 4, 8, 3, 2);
        
        TextField streetNameField = new TextField();
        streetNameField.setVisible(false);
        streetNameField.setPrefSize(150, 20);
        gP.add(streetNameField, 4, 10, 3, 2);
        
        TextField suburbField = new TextField();
        suburbField.setVisible(false);
        suburbField.setPrefSize(150, 20);
        gP.add(suburbField, 4, 12, 3, 2);
        
        TextField postCodeField = new TextField();
        postCodeField.setVisible(false);
        postCodeField.setPrefSize(150, 20);
        gP.add(postCodeField, 4, 14, 3, 2);
        
        // sup list
        ObservableList<String> supOption = FXCollections.observableArrayList();
        for(int i=0;i<supList.size();i++){
            supOption.add(supList.get(i).getSupplementName()+" "+supList.get(i).getWeeklyCost()+"$");
        }
        ListView<String> supView = new ListView<String>(supOption);
        supView.setVisible(false);
        supView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        supView.setPrefSize(200, 100);
        gP.add(supView, 4, 16, 4, 5);
        
        TextField payNameField = new TextField();
        payNameField.setVisible(false);
        payNameField.setPrefSize(150,20);
        gP.add(payNameField, 4, 22, 3, 2);
        
        TextField creditCardOrBankTransferField = new TextField();
        creditCardOrBankTransferField.setVisible(false);
        creditCardOrBankTransferField.setPrefSize(150,20);
        gP.add(creditCardOrBankTransferField, 4, 24, 3, 2);
        
        TextField expDateOrAccNumberField = new TextField();
        expDateOrAccNumberField.setVisible(false);
        expDateOrAccNumberField.setPrefSize(150,20);
        gP.add(expDateOrAccNumberField, 4, 26, 3, 2);
        
        
                
        //create supplement   
        createSupBtn.setOnAction((ActionEvent event) -> {
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    try {
                        createCusBtn.setVisible(false);
                        createSupBtn.setVisible(false);
                        name.setText("Name:");
                        costOrEmail.setText("Cost:");   
                        nameField.setVisible(true);
                        costOrEmailField.setVisible(true);
                        costOrEmailField.setPromptText("Number only");
                        submitSupBtn.setVisible(true);
                        
                    } catch (Exception ex) {
                        System.out.println("create supplement button error.");
                    }
                });
            });
            t.start();
        });        
                
        // create customer
        createCusBtn.setOnAction((ActionEvent event) -> {
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    try {
                        createCusBtn.setVisible(false);
                        createSupBtn.setVisible(false);
                        createAcBtn.setVisible(true);
                        createPcBtn.setVisible(true);
                          
                    } catch (Exception ex) {
                        System.out.println("Create customer button error.");
                    }
                });
            });
            t.start();
        });
        
        // create Associate customer
        createAcBtn.setOnAction((ActionEvent event) -> {
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    try {
                        this.registerType = "ac";
                        createCusBtn.setVisible(false);
                        createSupBtn.setVisible(false);
                        createAcBtn.setVisible(false);
                        createPcBtn.setVisible(false);
                        name.setText("Name:");
                        costOrEmail.setText("Email:"); 
                        streetNum.setText("Street Number:");
                        streetName.setText("Street Name:");
                        suburb.setText("Suburb:");
                        postCode.setText("Post code:");
                        sups.setText("Supplements:");
                        payName.setText("Paid by (email):");
                        
                        nameField.setVisible(true);
                        costOrEmailField.setVisible(true);
                        streetNumField.setVisible(true);
                        streetNameField.setVisible(true);
                        suburbField.setVisible(true);
                        postCodeField.setVisible(true);
                        supView.setVisible(true);
                        payNameField.setVisible(true);
                        submitCusBtn.setVisible(true);
                        
                    } catch (Exception ex) {
                        System.out.println("Create associate customer button error.");
                    }
                });
            });
            t.start();
        });
        
        // Create paying customer button
        createPcBtn.setOnAction((ActionEvent event) -> {
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    try {
                        createCusBtn.setVisible(false);
                        createSupBtn.setVisible(false);
                        createAcBtn.setVisible(false);
                        createPcBtn.setVisible(false);
                        ccBtn.setVisible(true);
                        baBtn.setVisible(true); 
                    } catch (Exception ex) {
                        System.out.println("Create paying customer error.");
                    }
                });
            });
            t.start();
        });        
                
        //create Paying customer with credit card
        ccBtn.setOnAction((ActionEvent event) -> {
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    try {
                        this.registerType = "pccc";
                        createCusBtn.setVisible(false);
                        createSupBtn.setVisible(false);
                        createAcBtn.setVisible(false);
                        createPcBtn.setVisible(false);
                        ccBtn.setVisible(false);
                        baBtn.setVisible(false);
                        name.setText("Name:");
                        costOrEmail.setText("Email:"); 
                        streetNum.setText("Street Number:");
                        streetName.setText("Street Name:");
                        suburb.setText("Suburb:");
                        postCode.setText("Post code:");
                        sups.setText("Supplements:");
                        payName.setText("Name:");
                        creditCardOrBankTransfer.setText("Credit card:");
                        expDateOrAccNumber.setText("Exp date:");
                        
                        nameField.setVisible(true);
                        costOrEmailField.setVisible(true);
                        streetNumField.setVisible(true);
                        streetNameField.setVisible(true);
                        suburbField.setVisible(true);
                        postCodeField.setVisible(true);
                        supView.setVisible(true);
                        payNameField.setVisible(true);
                        creditCardOrBankTransferField.setVisible(true);
                        creditCardOrBankTransferField.setPromptText("XXXX-XXXX-XXXX-XXXX");
                        expDateOrAccNumberField.setVisible(true);
                        expDateOrAccNumberField.setPromptText("MM/YY");
                        submitCusBtn.setVisible(true);
                        
                    } catch (Exception ex) {
                        System.out.println("create paying customer with credit card error.");
                    }
                });
            });
            t.start();
        });
        
        //create Paying customer with Bank transfer
        baBtn.setOnAction((ActionEvent event) -> {
            // Assign new thread to run 
            Thread t = new Thread(()->{
                Platform.runLater(()->{
                    try {
                        this.registerType = "pcba";
                        createCusBtn.setVisible(false);
                        createSupBtn.setVisible(false);
                        createAcBtn.setVisible(false);
                        createPcBtn.setVisible(false);
                        ccBtn.setVisible(false);
                        baBtn.setVisible(false);
                        name.setText("Name:");
                        costOrEmail.setText("Email:"); 
                        streetNum.setText("Street Number:");
                        streetName.setText("Street Name:");
                        suburb.setText("Suburb:");
                        postCode.setText("Post code:");
                        sups.setText("Supplements:");
                        payName.setText("Name:");
                        creditCardOrBankTransfer.setText("Bank name");
                        expDateOrAccNumber.setText("Account number:");
                        
                        nameField.setVisible(true);
                        costOrEmailField.setVisible(true);
                        streetNumField.setVisible(true);
                        streetNameField.setVisible(true);
                        suburbField.setVisible(true);
                        postCodeField.setVisible(true);
                        supView.setVisible(true);
                        payNameField.setVisible(true);
                        creditCardOrBankTransferField.setVisible(true);
                        expDateOrAccNumberField.setVisible(true);
                        expDateOrAccNumberField.setPromptText("XXX-XXX XXXXXX");
                        submitCusBtn.setVisible(true);
                        
                    } catch (Exception ex) {
                        System.out.println("Create paying customerw with bank transfer error.");
                    }
                });
            });
            t.start();
        });

        // Customer submit button
        submitCusBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try{
                    boolean error = false;

                    for(int i=0;i<acList.size();i++){
                        try {
                            // exist email validation
                            if(costOrEmailField.getText().equalsIgnoreCase(acList.get(i).getEmail())){
                                doAlert(Alert.AlertType.ERROR,gP.getScene().getWindow(),"ERROR","The Email "
                                        + "already registered.");
                                error = true;
                            }
                            if(costOrEmailField.getText().equalsIgnoreCase(pcList.get(i).getEmail())){
                                doAlert(Alert.AlertType.ERROR,gP.getScene().getWindow(),"ERROR","The Email "
                                        + "already registered.");
                                error = true;
                            }
                        } catch (Exception ex) {
                            System.out.println("Check exist boolean method error.");
                        }
                    }

                    // paying customer check
                    if(registerType.equalsIgnoreCase("ac")){
                        boolean isExist = false;
                        for(int i=0;i<pcList.size();i++){
                                
                                if(payNameField.getText().equalsIgnoreCase(pcList.get(i).getEmail())){
                                    isExist = true;
                                }
                        }
                        if(isExist){
                            error = false;
                        }else{
                            error = true;
                        }
                        if(error){
                            doAlert(Alert.AlertType.ERROR,gP.getScene().getWindow(),"ERROR","The paidby email "
                            + "not exist in the system.");
                            error = true;
                        }
                    }

                    // payment method blank check
                    if(registerType.equalsIgnoreCase("pccc") || registerType.equalsIgnoreCase("pcba")){
                        if(payNameField.getText().isEmpty() || creditCardOrBankTransferField.getText().isEmpty() ||
                                 expDateOrAccNumberField.getText().isEmpty()){
                            doAlert(Alert.AlertType.ERROR,gP.getScene().getWindow(),"ERROR","Please fill "
                                    + "out the payment method.");
                            error = true;
                        }
                    }

                    // email validation check
                    if(!costOrEmailField.getText().matches("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")){
                       error = true;
                       doAlert(Alert.AlertType.ERROR,gP.getScene().getWindow(),"ERROR","Please enter "
                                    + "a valid email.");
                    }

                    if(registerType.equalsIgnoreCase("pccc")){
                        if(creditCardOrBankTransferField.getText().length() <= 16){
                            error = true;
                            doAlert(Alert.AlertType.ERROR,gP.getScene().getWindow(),"ERROR","Please enter "
                                    + "a valid credit card number with dashs.");
                        }
                        if(expDateOrAccNumberField.getText().length() <= 4){
                            error = true;
                            doAlert(Alert.AlertType.ERROR,gP.getScene().getWindow(),"ERROR","Please enter "
                                    + "a valid expiry date with slash /.");
                        }
                    }



                    // make sure required info are not blank
                    if(nameField.getText().isEmpty() || costOrEmailField.getText().isEmpty() || streetNumField.getText().isEmpty()
                            || streetNameField.getText().isEmpty() || suburbField.getText().isEmpty() || postCodeField.getText().isEmpty()){
                            doAlert(Alert.AlertType.ERROR,gP.getScene().getWindow(),"ERROR","Please fill "
                                    + "out all the blank.");
                            error = true;
                    }


                    // If no error pops, go into create new
                    if(!error){

                        String name, email, streetNum, paidBy, payType, payName, 
                                creditCardOrBankName, expDateOrAccNum;
                        address adrs = new address();
                        List<supplement> supplementList = new ArrayList<supplement>();
                        magazine mag = new magazine();

                        LocalDate today = LocalDate.now();
                            // Initialise address object
                            adrs = new address(streetNumField.getText().trim(),
                                    streetNameField.getText().trim(),suburbField.getText().trim(),
                                    postCodeField.getText().trim());


                        ObservableList<String> selectedSups = supView.getSelectionModel().getSelectedItems();
                        // Initialise supplement list object
                        for(int i=0;i<selectedSups.size();i++){
                            for(int j=0;j<supList.size();j++){
                                String[] sup = null;
                                sup = selectedSups.get(i).split(" ");
                                if(sup[0].equalsIgnoreCase(supList.get(j).getSupplementName())){
                                        supplementList.add(new supplement(supList.get(j).getSupplementName(),
                                                supList.get(j).getWeeklyCost(),supList.get(j).getDate()));

                                }
                            }
                        }

                            // Initialise magazine object
                        mag = new magazine(supplementList);


                        name  = nameField.getText().trim();
                        email = costOrEmailField.getText().trim();

                        if(registerType.equalsIgnoreCase("ac")){
                            // Associate customer
                        paidBy = payNameField.getText().trim();

                        associateCustomer ac = new associateCustomer();
                        
                        ac = new associateCustomer(name, email,
                                mag, adrs, today, paidBy);


                        acList.add(ac);
                            for(int i=0;i<pcList.size();i++){
                                if(pcList.get(i).getEmail().equalsIgnoreCase(paidBy)){
                                    pcList.get(i).addAssociateCustomer(acList, email);
                                }
                            }
                        }else{
                            if(registerType.equalsIgnoreCase("pccc")){
                                // Paying customer with credit card
                                payName = payNameField.getText().trim();
                                creditCardOrBankName = creditCardOrBankTransferField.getText().trim();
                                expDateOrAccNum = expDateOrAccNumberField.getText().trim();
                                payingCustomer pc = new payingCustomer();
                                
                                    pc = new payingCustomer(name, email, mag, adrs, today,
                                            "Credit card", payName, creditCardOrBankName, expDateOrAccNum);

                                pcList.add(pc);
                            }else{
                                // Paying customer with Bank transfer
                                payName = payNameField.getText().trim();
                                creditCardOrBankName = creditCardOrBankTransferField.getText().trim();
                                expDateOrAccNum = expDateOrAccNumberField.getText().trim();
                                payingCustomer pc = new payingCustomer();
                                pc = new payingCustomer(name, email, mag, adrs, today,
                                        "Bank transfer", payName, creditCardOrBankName, expDateOrAccNum);
                                pcList.add(pc);
                            }
                        }
                    createPage.close();
                    }
                } catch (Exception ex) {
                        System.out.println("Add customer error.");
                }
            }
        });
        
        // Supplement submit button
        submitSupBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean error = false;
                for(int i=0;i<supList.size();i++){
                    if(nameField.getText().equalsIgnoreCase(supList.get(i).getSupplementName())){
                        doAlert(Alert.AlertType.ERROR,gP.getScene().getWindow(),"ERROR","The supplement "
                                + "already exist.");
                        error = true;
                    }
                    
                }
                if(nameField.getText().isEmpty() || costOrEmailField.getText().isEmpty()){
                        doAlert(Alert.AlertType.ERROR,gP.getScene().getWindow(),"ERROR","Please fill"
                                + "out all the blank.");
                        error = true;
                }
                
                if(!costOrEmailField.getText().trim().matches("-?\\d+(\\.\\d+)?")){
                    doAlert(Alert.AlertType.ERROR,gP.getScene().getWindow(),"ERROR","Please fill"
                                + " only number on cost.");
                        error = true;
                }
                
                if(!error){
                    double supCost = Double.parseDouble(costOrEmailField.getText().trim());
                    LocalDate today = LocalDate.now();
                    try {
                        supList.add(new supplement(nameField.getText().trim(), supCost, today));
                    } catch (Exception ex) {
                        System.out.println("Create supplement error.");
                    }
                    createPage.close();
                }
            }
        });
        
        
        ScrollPane sP = new ScrollPane(gP);
        sP.setMinSize(510, 610);
        sP.setMaxSize(510, 610);
        sP.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Group root = new Group(sP);
        Scene sc = new Scene(root,500,600);
        createPage.setTitle("Create new customer");
        createPage.setResizable(false);
        createPage.setScene(sc);  
        return createPage;
    }
    
    public void removeSupplement(String supName) throws Exception{
        // Remove supplement from the database, and remove it from all the customer
        for(int i=0;i<supList.size();i++){
            if(supList.get(i).getSupplementName().equalsIgnoreCase(supName)){
                
                for(int j=0;j<acList.size();j++){
                    for(int k=0;k<acList.get(j).getList().getSupList().size();k++){
                        if(acList.get(j).getList().getSupList().get(k).getSupplementName().equalsIgnoreCase(supList.get(i).getSupplementName())){
                            acList.get(j).getList().getSupList().remove(k);
                        }
                    }
                }
                for(int j=0;j<pcList.size();j++){
                    for(int k=0;k<pcList.get(j).getList().getSupList().size();k++){
                        if(pcList.get(j).getList().getSupList().get(k).getSupplementName().equalsIgnoreCase(supList.get(i).getSupplementName())){
                            pcList.get(j).getList().getSupList().remove(k);
                        }
                    }
                }
                supList.remove(i);
            }
        }
    }
    
    public void removeCustomer(String cusEmail) throws Exception{
        // remove associate or paying customer
        for(int i=0;i<acList.size();i++){
            if(acList.get(i).getEmail().equalsIgnoreCase(cusEmail)){
                acList.remove(i);
                
                for(int j=0;j<pcList.size();j++){
                   pcList.get(j).removeAssociateCustomer(acList, cusEmail);
                }
                
            }
        }
        for(int i=0;i<pcList.size();i++){
            if(pcList.get(i).getEmail().equalsIgnoreCase(cusEmail)){
                for(int j=0;j<acList.size();j++){
                    if(acList.get(j).getPaidBy().equalsIgnoreCase(pcList.get(i).getEmail())){
                        acList.get(j).removePaidBy();
                    }
                }
                pcList.remove(i);
            }
        }
    }
            
    public void updateSup(String oriName, String changeName, double changeCost) throws Exception{
        // Edit supplement
        for(int i=0;i<supList.size();i++){
            if(supList.get(i).getSupplementName().equalsIgnoreCase(oriName)){
                supList.get(i).setSupplementName(changeName);
                supList.get(i).setWeeklyCost(changeCost);
                
                for(int j=0;j<acList.size();j++){
                    for(int k=0;k<acList.get(j).getList().getSupList().size();k++){
                        if(acList.get(j).getList().getSupList().get(k).getSupplementName().equalsIgnoreCase(supList.get(i).getSupplementName())){
                            acList.get(j).getList().getSupList().get(k).setSupplementName(changeName);
                            acList.get(j).getList().getSupList().get(k).setWeeklyCost(changeCost);
                        }
                    }
                }
                
                for(int j=0;j<pcList.size();j++){
                    for(int k=0;k<pcList.get(j).getList().getSupList().size();k++){
                        if(pcList.get(j).getList().getSupList().get(k).getSupplementName().equalsIgnoreCase(supList.get(i).getSupplementName())){
                            pcList.get(j).getList().getSupList().get(k).setSupplementName(changeName);
                            pcList.get(j).getList().getSupList().get(k).setWeeklyCost(changeCost);
                        }
                    }
                }
            }
        }
        
    }
    
    public void updateCus(String oriEmail, String changeName, String changeEmail, String changePaidBy)throws Exception{
        // Edit associate or paying customer
        String oriPaidBy ="";
        int pos = 0;
        boolean isdone = false;
        boolean isAc = false;
        for(int i=0;i<acList.size();i++){
            // associate customer
            if(acList.get(i).getEmail().equalsIgnoreCase(oriEmail)){
                oriPaidBy = acList.get(i).getPaidBy().trim();
                pos = i;
                isAc = true;
            }
        }    
        for(int j=0;j<pcList.size();j++){
                    
            if(oriPaidBy.equalsIgnoreCase(changePaidBy) && !isdone && isAc){
            isdone = true;
             // default paid by is same to change paid by = no change, but update the email.
                for(int i=0;i<pcList.size();i++){
                    for(int k=0;k<pcList.get(i).getPayingAssociateCusList().size();k++){
                    // change info in pc 
                        if(pcList.get(i).getPayingAssociateCusList().get(k).toString().equalsIgnoreCase(oriEmail)){
                        pcList.get(i).getPayingAssociateCusList().set(k, changeEmail);
                        }
                    }
                }        
                
            }
           if(!oriPaidBy.equalsIgnoreCase(changePaidBy) && !isdone && isAc){
                // change paid by
                isdone = true;
                
                for(int a=0;a<pcList.size();a++){
                    for(int b=0;b<pcList.get(a).getPayingAssociateCusList().size();b++){
                        if(pcList.get(a).getPayingAssociateCusList().get(b).toString().equalsIgnoreCase(oriEmail)){

                        pcList.get(a).getPayingAssociateCusList().remove(b);
                        pcList.get(a).getAssociateCustomerPriceList().remove(b);
                        }
                        
                    }
                }
                
                for(int i=0;i<pcList.size();i++){
                    if(pcList.get(i).getEmail().equalsIgnoreCase(changePaidBy)){
                    pcList.get(i).addAssociateCustomer(acList, changeEmail);
                    }
         
                }
            
            }
                
        }
            
            acList.get(pos).setName(changeName);
            acList.get(pos).setEmail(changeEmail);
            acList.get(pos).setPaidBy(changePaidBy);
            
        
        if(!isAc){
            for(int i=0;i<pcList.size();i++){
                if(pcList.get(i).getEmail().equalsIgnoreCase(oriEmail)){

                    for(int j=0;j<acList.size();j++){
                        if(acList.get(j).getPaidBy().equalsIgnoreCase(oriEmail)){
                            acList.get(j).setPaidBy(changeEmail);
                        }
                    }
                    pcList.get(i).setName(changeName);
                    pcList.get(i).setEmail(changeEmail);
                }
            }   
        }
        
        
        
    }  
    
    private void doAlert(Alert.AlertType alertType, Window owner, String title, String message){
        // call to create an alert.
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}

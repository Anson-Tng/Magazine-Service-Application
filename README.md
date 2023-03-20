# Magazine-Service-Application
The Magazine service application has been enhanced by integrating its own graphical user interface using some of the assignment one source code. Now the application has a user-friendly interface with buttons and numerous available features. Users can examine a summary of all customer and supplement information, change the data, and create new customers or supplements for storage in the system.

# User Guide
• The ICT373_A2 project folder includes the libraries and the source package with eleven java files:

o address.java

o associateCustomer.java

o bankAccount.java

o creditCard.java

o customer.java

o ICT373_A2.java

o magazine.java

o magazineService.java

o payingCustomer.java

o supplement.java

o View.java

• The data for the system, SER file Original_data.ser is stored in src\ict373_a2\Data; the other SER file can be ignored or overwritten.

• To run the application, please open the ICT373_A2.java and click on the run project button or press F6 to use the program.

• The application will first pop up the small window to ask the user whether they want to read the file(Press ok and select Original_data.ser) or start a new file(Press cancel).

• After selecting, the main menu will pop up, and there are three modes for the user to choose, view mode, create mode, and edit mode. (Data will only show after pressing the view mode)

• If view mode is selected, users can press any email or supplement name from the left lists to check the selected detail information.

• If create mode is selected, the application will pop up another window to ask user wants to create which type of data, is supplement or customer; if the customer is chosen, then to ask is the associate customer or paying customer, if paying customer is chosen then to ask paying by credit card or bank transfer. After deciding what type to be adding, the user can start filling in the information and press submit to store it in the database.

• If edit mode is selected, the application will have a text field or button to fill in the new information when the user selected any of the existing supplements or customers from the list, then press the change button to make the changes, and the database will be updated after back to view mode.

• When the user decides to exit the application, a confirmation window will pop up to ask whether the user wants the save the data file or not; the data file will only be saved with the SER file type.

# Algorithm & UML
The application is created using an object-oriented design, which includes the planning of a technique for interacting with many other objects and the resolution of the software's structure. In this instance of developing a magazine service system with a graphical user interface, I aim to make the program as user-friendly as possible while minimising the source code.
The following concepts were utilised in the creation of this application:

• Supplement class stores the data of its name and cost.

• Magazine class contains its only name, WeeklyFun and the fixed cost of the magazine, and the list of supplements that customers can choose to add.

• Address class is used in customer class that stores the information of street number, street name, suburb, and postcode.

• Customer class contains all the basic identity information like name, email address, address, subscribed magazine, and the registered date.

• Associate customer class is inherited from customer class to store more associate customer information, like the email of people paying for them.

• Bank account class stores the data of bank account detail, and it is included in the paying customer to be one of the payment methods.

• Credit card class stores the data of credit card detail, and it is included in the paying customer to be one of the payment methods.

• Paying customer class is inherited from customer class to store more information of the paying customer, like the payment method, detail of paying information, and email of people them are paying for.

• Magazine service class contains the methods of managing all the classes above and putting them into an object in order to store it as manageable magazine service information, which includes adding new customers, removing customers, editing customer information, saving to file and reading the file.

• View class contains the configuration of all visual JavaFX components and assigns its function, like the size of the panel, size of the button, and the function of the button.

• In this design, the buttons from creating window and the editing panel are implemented by assigning each new thread to perform the multiple task tasks that can refresh the main menu list after the created or edited customer and supplement.

• The serialization and deserialization are implemented for this application, so the application can read in and write the designed style of the SER file.

• The email validation check, is exist the paying customer check, is exist the associate customer check, duplication check are implemented in this application.

• The subscribed or created date are auto generated after user created it, and it can’t be edited.

• The edit address, edit supplement, edit pay info, edit pay for buttons are designed with showing the new window to make the changes, and the changes will be updated after pressing the submit button.

# Limitations
• Bad input validation on credit card and bank transfer information.

• After creating a new customer or supplement, the list won’t refresh until the view mode is pressed

• The application is not made for resizable ready.

• Exception handling is not well designed, it is only displaying all the potential errors, only implemented print simply lines of words.

• The display of invoices and message are not looking good.

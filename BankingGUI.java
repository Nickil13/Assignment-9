/**
* This is the GUI for a Banking App where the user can create either a
* chequing account or a savings account, setting the transaction fee, balance
* and customer name, respectively. The id  for the customer is randomly generated as a number
* between 1000 and 9000.
* This information is saved to a file. Such that, if the file exists prior to
* running the app, the app will load its contents and display that account.
*
** Created by Dayan Jayasuriya, Nicki Lindstrom and Riley Schaff.
*/

// Last edited at 2:28 pm on March 27th by Nicki.

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.text.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.lang.NumberFormatException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.EOFException;
import javafx.scene.image.Image;

public class BankingGUI extends Application{
  private Customer accountHolder;
  private SavingsAccount aSavingsAccount;
  private ChequingAccount aChequingAccount;
  private double newDeposit = 0;
  private double newWithdrawl = 0;
  private double newTransactionFee = 0;
  private double accountBalance;
  private double amount;
  private Label statBalance = new Label("");
  private Label errorFileLabel = new Label("");
  private Label titleLabel = new Label("");
  private Label accountHolderName= new Label("");// = new Label
  private Label accountHolderID = new Label("");// = new Label("Account ID: " );//+ accountHolder.getID());
  private Label transactionFeeLabel = new Label("");
  private TextField transactionFeeField = new TextField("Enter the transaction fee.");
  private Label invalidDepositWithdraw = new Label ("");
  private String accountType = "";
  private String customerName;
  private Stage stage;
  private int id;
  public final int hBoxWidth = 6;
  public final int vBoxWidth = 30;
  public final int groupHeight = 300;
  public final int groupWidth = 500;
  public final int textFieldWidth = 100;
  public final int mathSign = 1;
  public final int s1FontSize = 12;
  private  TextField customerNameField = new TextField("Enter the name of the customer.");
  private TextField startBalanceField = new TextField("Enter the starting balance.");
  private TextField entry = new TextField("Enter Withdrawal or Deposit Amount");
  private HBox transactionFeeHBox = new HBox(hBoxWidth);//hbox for transaction fee for chequing account
  private String fileName = "";


  /**
  * Method to creat a new savings account.
  */
  public void userSetSavingsAccount(){

    userNewAccountHolder();
    aSavingsAccount = new SavingsAccount(accountHolder, accountBalance);
    accountBalance = aSavingsAccount.getBalance();
    System.out.println("New savings account " + "accountHolder " + accountHolder + "accountBalance" + accountBalance);
  }

  /**
  * Method to create a new chequing account.
  */
  public void userSetChequingAccount(){

    userNewAccountHolder();
    aChequingAccount = new ChequingAccount(accountHolder, accountBalance, newTransactionFee);
    accountBalance = aChequingAccount.getBalance();
    System.out.println("New chequing account " + "accountHolder " + accountHolder + "newTransactionFee" + newTransactionFee + "accountBalance"+ accountBalance);
  }

  /**
  * Method to create a new account and set it as the current account.
  * depending on what the user has chosen to create in the GUI.
  */
  public void createAccount(){
    Random random = new Random();
    int newId = random.nextInt(8999) + 1000;
    accountBalance = Double.parseDouble(startBalanceField.getText());
    setAccountHolder(customerNameField.getText(),newId);
    if(accountType.equals("Savings Account")){
      userSetSavingsAccount();
      updateAccountTypeSavings();
      aSavingsAccount.getCustomer().toString();
      System.out.println("Savings account");

    }else if(accountType.equals("Chequing Account")){
      newTransactionFee = Double.parseDouble(transactionFeeField.getText());
      userSetChequingAccount();
      updateAccountTypeChequing();
      aChequingAccount.getCustomer().toString();
      System.out.println("Chequing account");
    }
    updateAccountLabels();
    writeToFile("bankAccount.txt");
    System.out.println("Account written to file.");
  }

  /**
  * Method to create a new account holder.
  *
  */
  public void userNewAccountHolder() {
    //int id;
    //String name;
    Button newAccountButton = new Button("Submit");
    VBox newAccountVBox = new VBox(vBoxWidth);
    TextField customerNameText = new TextField ("Enter the customer name and click Submit");
    newAccountVBox.getChildren().addAll(customerNameText, newAccountButton);
    /*System.out.println("Enter a customer Name: ");
    Scanner keyName = new Scanner(System.in);
    name = keyName.next() +" "+keyName.next().trim();*/

    /**
    * Method to handle the user submitting a new customer name.
    */
    newAccountButton.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        //https://stackoverflow.com/questions/32534601/java-gettting-a-random-number-from-100-to-999
        Random random = new Random();
        // generate random number between 0 and 8999 then add 1000 to get between 1000 and 9999.
        int id = random.nextInt(8999) + 1000;
        String name = customerNameText.getText();
        setAccountHolder(name, id);
        //  setCurrentBankAccount(bankAccount);
      }
    });
  }

  /**
  * Method to set the account holder.
  *
  * @param: name the account holder's name of type String.
  * @param: id the randomly generated account ID of type int
  */
  public void setAccountHolder(String name, int id){
    accountHolder = new Customer (name, id);
  }

  /**
  * Method to set the accountType to savings account.
  */
  public void updateAccountTypeSavings(){
    accountType = "Savings Account";
    titleLabel.setText("Create a New " + accountType);
  }

  /**
  * Method to set the accountType to chequing account.
  */
  public void updateAccountTypeChequing(){
    accountType = "Chequing Account";
    titleLabel.setText("Create a New " + accountType);
  }

  /**
  * Method to update the labels associated with the account on the third scene.
  */
  public void updateAccountLabels(){
    accountHolderName.setText("Account Holder Name: " + accountHolder.getName());
    accountHolderID.setText("Account ID: " + accountHolder.getID());
    statBalance.setText("Balance: " + accountBalance);
    accountHolderName.setFont(Font.font("Verdana", FontWeight.BOLD,s1FontSize));
    accountHolderID.setFont(Font.font("Verdana", FontWeight.BOLD, s1FontSize));
    statBalance.setFont(Font.font("Verdana", FontWeight.BOLD, s1FontSize));
    transactionFeeLabel.setText("Transaction Fee: "+ newTransactionFee);
    transactionFeeLabel.setFont(Font.font("Verdana", FontWeight.BOLD, s1FontSize));
  }

  /**
  * Method to update the balance label.
  * @param: balance the updated account balance as a type double.
  */
  public void setBalanceLabel(double balance){
    statBalance.setText("Balance: " + Double.toString(balance));
    statBalance.setFont(Font.font("Verdana", FontWeight.BOLD, s1FontSize));
  }

  /**
  * Method to update the balance label.
  */
  public void settransactionFeeHBox(){
    transactionFeeLabel.setText("Transaction Fee: ");
    transactionFeeHBox.getChildren().addAll(transactionFeeLabel, transactionFeeField);
    transactionFeeField.setPrefWidth(textFieldWidth*2);
  }

  /**
  * Method to clear the invalid deposit or withdraw method
  */
  public void updateValidDepositWithdraw(){
    invalidDepositWithdraw.setText("");
  }

  /**
  * Method to inform user of invalid number for deposit or withdraw.
  */
  public void updateInvalidDepositWithdrawNumber(){
    invalidDepositWithdraw.setText("Invalid entry. Please enter a positive number.");
  }

  /**
  * Method to inform user of invalid type for deposit or withdraw.
  */
  public void updateInvalidDepositWithdrawType(){
    invalidDepositWithdraw.setText("Invalid entry. Please enter it as a numerical number.");
  }


  public void readFromFile(String theFileName){
    String fileName = theFileName;
    //ObjectInputStream inputStream = null;
    try{
      //inputStream = new ObjectInputStream(new FileInputStream(fileName));
      Scanner reader = new Scanner(new FileInputStream(fileName));
    }catch(IOException e){
      System.out.println("Error opening output file"+fileName);
    }
    //ChequingAccount readOne = null;
    //SavingsAccount readTwo = null;

    try{
      Scanner reader = new Scanner(new FileInputStream(fileName));
      reader.useDelimiter(":|; ");
      String line = reader.next().trim();
      line = reader.next().trim();
      //readTwo = (SavingsAccount)inputStream.readObject();
      if (line.equals("Savings Account")){
        System.out.println(line);
        accountType = line;
        line = reader.next().trim();
        line = reader.next().trim();
        System.out.println(line);
        String line2 = reader.next().trim();
        int lineID = Integer.parseInt(reader.next().trim()); //https://stackoverflow.com/questions/23851668/converting-a-string-with-spaces-to-an-integer-in-java
        System.out.println(line2);
        accountHolder =new Customer((line+""), lineID);
        //id = Integer.parseInt(line);;
        line = reader.next().trim();
        line = reader.next().trim();
        System.out.println(line);
        accountBalance = Double.parseDouble(line);
        updateAccountLabels();
        userSetSavingsAccount();
      } else if (line.equals("Chequing Account")){
        System.out.println(line);
        accountType = line;
        line = reader.next().trim();
        line = reader.next().trim();
        System.out.println(line);
        String line2 = reader.next().trim();
        int lineID = Integer.parseInt(reader.next().trim()); //https://stackoverflow.com/questions/23851668/converting-a-string-with-spaces-to-an-integer-in-java
        System.out.println(line2);
        accountHolder =new Customer((line+""), lineID);
        //id = Integer.parseInt(line);;
        line = reader.next().trim();
        line = reader.next().trim();
        System.out.println(line);
        accountBalance = Double.parseDouble(line);
        line = reader.next().trim();
        line = reader.next().trim();
        System.out.println(line);
        newTransactionFee = Double.parseDouble(line);
        userSetChequingAccount();
        settransactionFeeHBox();
        updateAccountLabels();
        System.out.println("accountType"+accountType+"accountHolder" +accountHolder+ "accountBalance"+accountBalance);

      }

      System.out.println("End of reading from file.");
      reader.close();

      System.out.println();
    }catch(FileNotFoundException e){
      System.out.println("Problem opening file"+ fileName);
    }
  }

  /*
  * Method that writes the banking information to a file.
  * @param: theFileName the name of the file to write to as a String.
  */
  public void writeToFile(String theFileName){
    String fileName = theFileName;

    try{
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
    }catch(IOException e){
      System.out.println("Error opening output file" + fileName);


    }try{
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
      if(accountType.equals("Savings Account")||accountType.equals(" Savings Account")){
        writer.write("Type: " +accountType+": ");
        System.out.println("Type: " +accountType+": ");

      writer.write(accountHolder+": ");
      System.out.println("Account Holder: " +accountHolder+": ");
      writer.write("Balance:" +accountBalance+": ");
      System.out.println("Balance:" +accountBalance+": ");
    }else if (accountType.equals("Chequing Account")||accountType.equals(" Chequing Account")){
      writer.write("Type: " +accountType+": ");
      writer.write(accountHolder+": ");
      writer.write("Balance: " +accountBalance+": ");
      System.out.println("Account Holder: " +accountHolder+": ");
      System.out.println("Balance:" +accountBalance+": ");
      writer.write("Transaction Fee: "+newTransactionFee+": ");
      System.out.println("Transaction Fee: " +newTransactionFee+": ");
    }
      writer.close();
    }catch(FileNotFoundException e){
      System.out.println("Problem opening the file" + fileName);
    }catch(IOException e){
      System.out.println("Problem with output to file"+ fileName);
    }
  }

  /*
  * Method that handles clicking the deposit button.
  */
  public class HandleDepositClick implements EventHandler<ActionEvent>{
    public HandleDepositClick(){}
    public void handle(ActionEvent event){
      try{
        amount = (Double.parseDouble(entry.getText()));
        if(amount> 0){
          if(accountType.equals("Savings Account") || accountType.equals(" Savings Account")){
            aSavingsAccount.deposit(amount);
            accountBalance = aSavingsAccount.getBalance();
            setBalanceLabel(accountBalance);
            updateValidDepositWithdraw();
            writeToFile(fileName);
          } else{
            aChequingAccount.deposit(amount);
            accountBalance = aChequingAccount.getBalance();
            setBalanceLabel(accountBalance);
            updateValidDepositWithdraw();
            writeToFile(fileName);
          }
        } else if ((amount) <= 0){
          updateInvalidDepositWithdrawNumber();
        }
      }
      catch(NumberFormatException e){
        updateInvalidDepositWithdrawType();
      }


    }
  }

  /*
  * Method that handles clicking the withdraw button.
  */
  public class HandleWithdrawalClick implements EventHandler<ActionEvent>{
    public HandleWithdrawalClick(){}
    public void handle(ActionEvent event){
      try{
        amount = (Double.parseDouble(entry.getText()));
        if(amount> 0){
          if(accountType.equals("Savings Account")|| accountType.equals(" Savings Account")){
            aSavingsAccount.withdraw(amount);
            accountBalance = aSavingsAccount.getBalance();
            setBalanceLabel(accountBalance);
            updateValidDepositWithdraw();
            writeToFile(fileName);
          } else{
            aChequingAccount.withdraw(amount);
            accountBalance = aChequingAccount.getBalance();
            setBalanceLabel(accountBalance);
            updateValidDepositWithdraw();
            writeToFile(fileName);
          }
        }else if ((amount) <= 0){
            updateInvalidDepositWithdrawNumber();
          }
        }
        catch(NumberFormatException e){
          updateInvalidDepositWithdrawType();
        }
    }
  }

  /**
  * Start method.
  */
  public void start(Stage primaryStage){
    stage = primaryStage;
    fileName = "bankAccount.txt";
    File fileObject = new File(fileName);
    boolean exists = false;
    if(!fileObject.exists()){
      System.out.println("No file by that name.");
    }else{
      System.out.println("File exists.");
      exists = true;
    }
    if(!fileObject.canRead()){
      System.out.println("Not allowed to read the file.");
    }else{
      System.out.println("File is readable.");
    }
    // If the file exists, read from it.
    if(exists == true){
      readFromFile(fileName);
    }

    //Background for all the scenes:
    Image blueScene = new Image("blueScene.jpeg",1000,1000,true,false);
    Background blueBackground = new Background(new BackgroundImage(blueScene,
    BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,
    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)));
    //Source: https://www.hdwallpapers.in/hd_desktop_blue-wallpapers.html
    // Info: https://stackoverflow.com/questions/37619396/how-to-set-image-to-a-scene-background-in-javafx

    /*
    **First screen of the GUI (Create a new Account)
    */

    //VBox for the first scene
    VBox accountCreationVBox = new VBox(vBoxWidth/2);
    accountCreationVBox.setAlignment(Pos.CENTER);

    VBox chooseAccountVBox = new VBox(vBoxWidth);
    chooseAccountVBox.setAlignment(Pos.CENTER);

    Label createAccount = new Label("Create an Account");
    createAccount.setFont(Font.font("Verdana",FontWeight.BOLD,16));

    Label createMessage = new Label("Please select the type of account you would like to create.");
    chooseAccountVBox.getChildren().addAll(createAccount,createMessage);

    Button savingsAccountButton = new Button("New Savings Account");
    Button chequingAccountButton = new Button("New Chequing Account");


    accountCreationVBox.getChildren().addAll(chooseAccountVBox, savingsAccountButton, chequingAccountButton, errorFileLabel);

    // Create a border pane for the first scene
    BorderPane borderPane1 = new BorderPane();
    borderPane1.setCenter(accountCreationVBox);
    borderPane1.setBackground(blueBackground);

    //Create the initial scene
    Scene scene1 = new Scene(borderPane1, groupWidth, groupHeight);


    /*
    ** Second screen of the GUI (Submit Account information)
    */

    HBox custNameHBox = new HBox(hBoxWidth);
    Label customerNameLabel = new Label("Customer name: ");

    custNameHBox.getChildren().addAll(customerNameLabel, customerNameField);
    custNameHBox.setAlignment(Pos.CENTER);
    customerNameField.setPrefWidth(textFieldWidth*2);

    // third hbox for start Balance and corresponding textField
    HBox startBalanceHBox = new HBox(hBoxWidth);
    Label startBalanceLabel = new Label("Start balance: ");

    startBalanceField.setPrefWidth(textFieldWidth*2);
    startBalanceHBox.getChildren().addAll(startBalanceLabel, startBalanceField);
    startBalanceHBox.setAlignment(Pos.CENTER);

    transactionFeeHBox.setAlignment(Pos.CENTER);

    titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
    titleLabel.setAlignment(Pos.CENTER);

    Button createButton = new Button("Create");

    // A VBox containing all boxes and buttons for Scene 2.
    VBox vBoxS2 = new VBox(vBoxWidth);
    vBoxS2.setAlignment(Pos.CENTER);
    vBoxS2.getChildren().addAll(titleLabel, transactionFeeHBox, custNameHBox,
    startBalanceHBox, createButton);


    BorderPane borderPane2 = new BorderPane();
    borderPane2.setCenter(vBoxS2);
    borderPane2.setBackground(blueBackground);

    Scene scene2 = new Scene(borderPane2, groupWidth, groupHeight);


    /*
    ** Third Screen of the GUI (Account information)
    */

    HBox sceneTitleHBox = new HBox(hBoxWidth);
    Label sceneTitle = new Label("Customer's "+ accountType);
    sceneTitle.setFont(Font.font("Verdana",FontWeight.BOLD,16));
    sceneTitleHBox.getChildren().add(sceneTitle);
    sceneTitleHBox.setAlignment(Pos.CENTER);
    sceneTitleHBox.setPadding(new Insets(10));

    //Hbox containing the balance and a label for it
    HBox balanceBox = new HBox(hBoxWidth);
    balanceBox.getChildren().add(statBalance);

    //Vbox containing Customer info
    VBox accountHolderBox = new VBox(vBoxWidth/2);
    accountHolderBox.getChildren().addAll(accountHolderName, accountHolderID);



    //Vbox Containing the user input area
    VBox changeInMoney = new VBox();
    entry.setMaxWidth(200);
    changeInMoney.getChildren().add(entry);

    accountHolderBox.setAlignment(Pos.CENTER);
    balanceBox.setAlignment(Pos.CENTER);

    changeInMoney.setAlignment(Pos.CENTER);

    //HBox Containing Buttons for withdrawl and deposit
    HBox buttons = new HBox();
    buttons.setAlignment(Pos.CENTER);
    Button withdrawal = new Button("Withdraw");
    Button deposit = new Button("Deposit");
    buttons.getChildren().addAll(withdrawal, deposit);

    // Deposits money into the current account.
    deposit.setOnAction(new HandleDepositClick());

    // Withdraw money from the current account.
    withdrawal.setOnAction(new HandleWithdrawalClick());


    // A VBox containing all boxes and buttons for Scene 3.
    VBox vBoxS3 = new VBox(vBoxWidth/2);
    vBoxS3.getChildren().addAll(sceneTitleHBox,accountHolderBox, balanceBox, buttons, changeInMoney, invalidDepositWithdraw);


    BorderPane borderPane3 = new BorderPane();
    borderPane3.setBackground(blueBackground);
    borderPane3.setCenter(vBoxS3);


    Scene scene3 = new Scene(borderPane3, groupWidth, groupHeight);



    /**
    * Button action to create a new savings account.
    */
    savingsAccountButton.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        updateAccountTypeSavings();
        primaryStage.setScene(scene2);
      }
    });

    /**
    * Button action to create a new chequing account.
    */
    chequingAccountButton.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        updateAccountTypeChequing();
        settransactionFeeHBox();
        primaryStage.setScene(scene2);
      }
    });

    /**
    * Handle when createButton pressed to create a new account.
    */
    createButton.setOnAction(new EventHandler<ActionEvent>(){
      public void handle(ActionEvent event){
        createAccount();
        primaryStage.setScene(scene3);
      }
    });


    primaryStage.setTitle("Banking GUI");
    // If a file exists from which to load information, load it and change
    // scene to see the Account Information.
    if(exists==false){
      primaryStage.setScene(scene1);
    }else{
      primaryStage.setScene(scene3);
    }
    primaryStage.show();

  }

}

/**
* We ran out of time for integrating the required components for Assignment 9.
* This is the GUI portion of the assignment without any file handling.
*/

// Last edited at 1:08 pm on March 24th by Nicki.

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
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
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;

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
  private double newStartBalance = 0.0;
  private Label accountHolderName= new Label("");// = new Label
  private Label accountHolderID = new Label("");// = new Label("Account ID: " );//+ accountHolder.getID());
  private Label transactionFeeLabel = new Label("");
  private TextField transactionFeeField = new TextField("Enter the transaction fee.");
  private Label invalidDepositWithdraw = new Label ("");
  private String accountType = "";
  private String customerName;
  public final int hBoxWidth = 6;
  public final int vBoxWidth = 30;
  public final int groupHeight = 300;
  public final int groupWidth = 500;
  public final int textFieldWidth = 100;
  public final int mathSign = 1;
  public final int s1FontSize = 12;
  private HBox transactionFeeHBox = new HBox(hBoxWidth);//hbox for transaction fee for chequing account
  private String type;


  /**
  * Method to creat a new savings account.
  */
  public void userSetSavingsAccount(){

    userNewAccountHolder();
    aSavingsAccount = new SavingsAccount(accountHolder, newStartBalance);
    accountBalance = aSavingsAccount.getBalance();
    System.out.println("New savings account " + "accountHolder " + accountHolder + "newStartBalance" + newStartBalance);
  }

  /**
  * Method to create a new chequing account.
  */
  public void userSetChequingAccount(){

    userNewAccountHolder();
    aChequingAccount = new ChequingAccount(accountHolder, newStartBalance, newTransactionFee);
    accountBalance = aChequingAccount.getBalance();
    System.out.println("New chequing account " + "accountHolder " + accountHolder + "newTransactionFee" + newTransactionFee + "newStartBalance"+ newStartBalance);
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
    name = keyName.next() +" "+keyName.next();*/

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
    ObjectInputStream inputStream = null;
    try{
      inputStream = new ObjectInputStream(new FileInputStream(fileName));
    }catch(IOException e){
      System.out.println("Error opening output file"+fileName);
    }
    ChequingAccount readOne = null;
    SavingsAccount readTwo = null;

    try{
      readTwo = (SavingsAccount)inputStream.readObject();
      if(readTwo!=null){
        aSavingsAccount = readTwo;
        System.out.println("We have a savings account.");
        Customer aGuy = aSavingsAccount.getCustomer();
        double hisBalance = aSavingsAccount.getBalance();
        System.out.println(hisBalance);
        System.out.println(aGuy.toString());

      }
      readOne = (ChequingAccount)inputStream.readObject();
      if(readOne!=null){
        aChequingAccount = readOne;
          System.out.println("We have a chequing account.");
          Customer aGuy = aChequingAccount.getCustomer();
          double hisBalance = aChequingAccount.getBalance();
          System.out.println(hisBalance);
          System.out.println(aGuy.toString());
      }


      System.out.println("End of reading from file.");
      inputStream.close();

      System.out.println();
    }catch(FileNotFoundException e){
      System.out.println("Problem opening file"+ fileName);
    }catch(EOFException e){
      System.out.println("Problem reading1 the file" + fileName);
    }catch(IOException e){
      System.out.println("Problem reading2 the file" + fileName);
    }catch(ClassNotFoundException e){
      System.out.println("Class was not found");
    }
  }
  public void writeToFile(String theFileName){
    String fileName = theFileName;
    ObjectOutputStream outputStream = null;
    try{
      outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
    }catch(IOException e){
      System.out.println("Error opening output file" + fileName);
    }try{
      outputStream.writeObject(aSavingsAccount);
      outputStream.writeObject(aChequingAccount);
      outputStream.close();
    }catch(FileNotFoundException e){
      System.out.println("Problem opening the file" + fileName);
    }catch(IOException e){
      System.out.println("Problem with output to file"+ fileName);
    }
  }
  /**
  * Start method.
  */
  public void start(Stage primaryStage){
    String fileName = "bankAccount.records";
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

    // If the file does not exist, create it and write to it.
    }

    //First screen
    //VBox for the first scene
    VBox accountCreationVBox = new VBox(vBoxWidth/2);
    TextField fileField = new TextField(".txt file");
    Button submitFile = new Button("Submit file name");
    HBox accountCreationHBox = new HBox(hBoxWidth);
    accountCreationHBox.getChildren().addAll(submitFile, fileField);
    Button savingsAccountButton = new Button("New Savings Account");
    Button chequingAccountButton = new Button("New Chequing Account");


    accountCreationVBox.getChildren().addAll(accountCreationHBox, savingsAccountButton, chequingAccountButton, errorFileLabel);

    fileField.setAlignment(Pos.CENTER);
    fileField.setPrefWidth(textFieldWidth*2);
    submitFile.setAlignment(Pos.CENTER);
    savingsAccountButton.setAlignment(Pos.CENTER);
    chequingAccountButton.setAlignment(Pos.CENTER);

    // create a border pane for the first scene
    BorderPane borderPane1 = new BorderPane();
    borderPane1.setCenter(accountCreationVBox);

    //Create the initial scene
    Scene scene1 = new Scene(borderPane1, groupWidth, groupHeight);



    //Vbox containing all the buttons and hboxes
    VBox vBox = new VBox(vBoxWidth/2);

    //Hbox containing the balance and a label for it
    HBox balanceBox = new HBox(hBoxWidth);
    balanceBox.getChildren().add(statBalance);

    //Vbox containing Customer info
    VBox accountHolderBox = new VBox(vBoxWidth/2);
    accountHolderBox.getChildren().addAll(accountHolderName, accountHolderID);

    //HBox Containing Buttons for withdrawl and deposit
    HBox buttons = new HBox();
    Button withdrawal = new Button("Withdraw");
    Button deposit = new Button("Deposit");


    //Vbox Containing the user input area
    VBox changeInMoney = new VBox();
    TextField entry = new TextField("Enter Withdrawal or Deposit Amount");
    entry.setPrefWidth(textFieldWidth);
    changeInMoney.getChildren().add(entry);

    /*Create actions for buttons
    Deposit entry into current bank account's balance
    Source of technique was Chapter 8, Walter Savitch Java: An Introduction to Problem Solving and Programming (8th Edition)
    */
    deposit.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        //get value of deposit
        try{
          amount = (Double.parseDouble(entry.getText()));
          if(amount> 0){
            if(accountType.equals("Savings Account")){
              aSavingsAccount.deposit(amount);
              accountBalance = aSavingsAccount.getBalance();
              setBalanceLabel(accountBalance);
              updateValidDepositWithdraw();
            } else{
              aChequingAccount.deposit(amount);
              accountBalance = aChequingAccount.getBalance();
              setBalanceLabel(accountBalance);
              updateValidDepositWithdraw();
            }
          } else if ((amount) <= 0){
            updateInvalidDepositWithdrawNumber();
          }
        }
        catch(NumberFormatException e){
          updateInvalidDepositWithdrawType();
        }

      }
    });

    //withdraw entry from current bank account's balance
    withdrawal.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        //get value of deposit
        try{
          amount = (Double.parseDouble(entry.getText()));
          if(amount> 0){
            if(accountType.equals("Savings Account")){
              aSavingsAccount.withdraw(amount);
              accountBalance = aSavingsAccount.getBalance();
              setBalanceLabel(accountBalance);
              updateValidDepositWithdraw();
            } else{
              aChequingAccount.withdraw(amount);
              accountBalance = aChequingAccount.getBalance();
              setBalanceLabel(accountBalance);
              updateValidDepositWithdraw();
            }
          }else if ((amount) <= 0){
              updateInvalidDepositWithdrawNumber();
            }
          }
          catch(NumberFormatException e){
            updateInvalidDepositWithdrawType();
          }

        }
      });


    // second hbox for Customer name and corresponding textField
    HBox custNameHBox = new HBox(hBoxWidth);
    Label customerNameLabel = new Label("Customer name: ");
    TextField customerNameField = new TextField("Enter the name of the customer.");
    custNameHBox.getChildren().addAll(customerNameLabel, customerNameField);
    customerNameField.setPrefWidth(textFieldWidth*2);

    // third hbox for start Balance and corresponding textField
    HBox startBalanceHBox = new HBox(hBoxWidth);
    Label startBalanceLabel = new Label("Start balance: ");
    TextField startBalanceField = new TextField("Enter the starting balance.");
    startBalanceField.setPrefWidth(textFieldWidth*2);
    startBalanceHBox.getChildren().addAll(startBalanceLabel, startBalanceField);


    // Create a Vbox and add the hboxes to the Vbox
    VBox vBoxS2 = new VBox(vBoxWidth);

    //  a label for the new scene
    titleLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

    titleLabel.setAlignment(Pos.CENTER);

    // create button
    Button createButton = new Button("Create");


    // add the boxes and the buttons to the vBox
    vBoxS2.getChildren().addAll(titleLabel, transactionFeeHBox, custNameHBox,
    startBalanceHBox, createButton);


    //combine all boxes into scene
    buttons.getChildren().addAll(withdrawal, deposit);
    vBox.getChildren().addAll(accountHolderBox, balanceBox, buttons, changeInMoney, invalidDepositWithdraw);
    accountHolderBox.setAlignment(Pos.CENTER);
    balanceBox.setAlignment(Pos.CENTER);
    buttons.setAlignment(Pos.CENTER);
    changeInMoney.setAlignment(Pos.CENTER);

    // create a border pane for the third scene
    BorderPane borderPane3 = new BorderPane();
    borderPane3.setCenter(vBox);



    // add another borderPane
    BorderPane borderPane2 = new BorderPane();

    // align the boxes in scene 2
    transactionFeeHBox.setAlignment(Pos.CENTER);
    custNameHBox.setAlignment(Pos.CENTER);
    startBalanceHBox.setAlignment(Pos.CENTER);
    vBoxS2.setAlignment(Pos.CENTER);
    borderPane2.setCenter(vBoxS2);

    // create the second scene
    Scene scene2 = new Scene(borderPane2, groupWidth, groupHeight);

    // create a third scene
    Scene scene3 = new Scene(borderPane3, groupWidth, groupHeight);

    /*
     Button action to submit the name of a .txt file.

    submitFile.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        String file = fileField.getText();
        /* try to get banking information from a file the user might provide.
        Adapted from CPSC 219 ReadMe.java example.
        try{
          BufferedReader bankingfile = new BufferedReader(new FileReader(file + ".txt"));
          String line = bankingfile.readLine();
          while (line !=null){
            line = bankingfile.readLine();
          }
          primaryStage.setScene(scene2);
        }catch (FileNotFoundException e){
          errorFileLabel.setText("The file "+ file+".txt"+ " does not exist.");

        }catch (IOException e){
          errorFileLabel.setText("The file "+ file+".txt"+ " is the wrong format.");
        }

      }
    });*/

    /**
    * Button action to create a new savings account.
    */
    savingsAccountButton.setOnAction(new EventHandler<ActionEvent>(){
      @Override
      public void handle(ActionEvent event){
        updateAccountTypeSavings();
        type = "savings";
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
        type = "chequing";
        settransactionFeeHBox();
        primaryStage.setScene(scene2);
      }
    });

    /**
    * Handle when creatButton pressed to create a new account.
    */
    createButton.setOnAction(new EventHandler<ActionEvent>(){
      public void handle(ActionEvent event){
        if (accountType.equals("Savings Account")){
          //https://stackoverflow.com/questions/32534601/java-gettting-a-random-number-from-100-to-999
          Random random = new Random();
          // generate random number between 0 and 8999 then add 1000 to get between 1000 and 9999.
          int id = random.nextInt(8999) + 1000;
          newStartBalance = Double.parseDouble(startBalanceField.getText());
          newStartBalance = Double.parseDouble(startBalanceField.getText());


          primaryStage.setScene(scene3);
          setAccountHolder(customerNameField.getText(), id);
          userSetSavingsAccount();
          updateAccountLabels();
          writeToFile("bankAccount.records");
          aSavingsAccount.getCustomer().toString();
          System.out.println("Savings account written to file.");
        } else if(accountType.equals("Chequing Account")){
          //https://stackoverflow.com/questions/32534601/java-gettting-a-random-number-from-100-to-999
          Random random = new Random();
          // generate random number between 0 and 8999 then add 1000 to get between 1000 and 9999.
          int id = random.nextInt(8999) + 1000;
          newStartBalance = Double.parseDouble(startBalanceField.getText());
          newTransactionFee = Double.parseDouble(transactionFeeField.getText());
          primaryStage.setScene(scene3);
          setAccountHolder(customerNameField.getText(), id);
          userSetChequingAccount();
          updateAccountLabels();
          writeToFile("bankAccount.records");
          System.out.println("Chequing account written to file.");
        }
      }
    });


    primaryStage.setTitle("Banking GUI");
    primaryStage.setScene(scene1);
    primaryStage.show();

  }

}

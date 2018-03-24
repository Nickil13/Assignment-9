/** This class works as a banking account with balances that can be checked, deposited
* into, and withdrawn from. This version is made by Dayan Jayasuriya, Nicki Lindstrom,
* and Riley Schaaf.
* Used for Assignment 6
* Last updated 28/FEB/2018 18:49 by Dayan - Changed for privacy leaks
* removed the unused sections
*/
import java.io.Serializable;
public abstract class BankAccount implements Serializable {
  private Customer accountHolder;
  private double balance = 0.00;
  //private double overdraftAmount = 100.0;


  /**
  * Default constructor for BankAccount.
  */
  public BankAccount() {
  }

  /**
  * Constructor for a BankAccount with customer and balance parameters.
  */
  public BankAccount(Customer accountHolder, double startBalance) {
    setBalance(startBalance);
    setCustomer(accountHolder);
  }


  /**
  * Check the current balance of the bank account.
  * @return: the account balance as a double.
  */
  public double getBalance() {
    return balance;
  }

  /**
  * Set the balance of the bank account
  * @param: the balance the account will be set to as a double.
  */
  protected void setBalance(double newBalance) {
    balance = newBalance;
  }

  /**
  * Get the current customer.
  * @return: the accountholder is returned as an object in the Customer class.
  */

  public Customer getCustomer() {
    return new Customer(accountHolder);
  }

  /**
  * Set a new customer as the account holder.
  * @param: the new accountholder as a Customer.
  */
  public void setCustomer(Customer newCustomer) {
    accountHolder = new Customer(newCustomer);
  }

  /**
  * Make a deposit to the bank account.
  * @param: the amount of money to be deposited as a double.
  */
  public void deposit (double amount) {
    if (amount >= 0.0) {
      balance += amount;
    }

    else {
      balance = balance;
    }
  }

  /**
  * Withraw from the bank account.
  * @param: the amount of money to be withdrawn as a double.
  */
  public void withdraw(double amount) {
    double minimumBalance = 0;
      // if statement to check the withdrawl does not put the account into overdraft.
    if ((getBalance() - amount) >= (minimumBalance)) {
      setBalance(getBalance() - amount);
    }
  }

  /**
  * Transfer money to another BankAccount
  * @param: the amount of money to be transferred as a double and the account the money
  * will be transferred to as a type BankAccount.
  */
  public void transfer(double amount, BankAccount toAccount) {
    double currentBalance = getBalance();
    withdraw(amount);
    if (currentBalance != getBalance()) {
      toAccount.deposit(amount);
    }
  }

  /**
  * Get the monthly bank fees and interest.
  */
  protected abstract double getMonthlyFeesAndInterest();

  /**
  * Update bank account balance taking into account monthly bank fees and interest.
  */
  public void monthEndUpdate(){
    balance += getMonthlyFeesAndInterest() ;
  }
}

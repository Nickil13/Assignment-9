/** This class works as a part of the BankAccount class.
* The ChequingAccount allows for overDrafts to occur and charges a
* fee for them.
* This version is made by Dayan Jayasuriya, Nicki Lindstrom,
* and Riley Schaaf.
*/
public class ChequingAccount extends BankAccount {
  private double overdraftfee;
  private double overdraftAmount = -100;
  public static final double MINIMUM_BALANCE = 0.0;
  public static final double PENALTY_RATE = 0.2;

/**
* Defualt chequing account constructor
*/
public ChequingAccount(){};

  /**
  * Constructor for ChequingAccount that takes a double as the new transaction fee.
  */
  public ChequingAccount(double transactionFee) {
    setOverdraftFee(transactionFee);
  }

  /**
  * Constructor for ChequingAccount that takes and sets an account holder, starting chequing account balance,
  * and overdraft fee.
  */
  public ChequingAccount(Customer accountHolder, double startBalance,
                          double transactionFee) {
    super(accountHolder, startBalance);
    setOverdraftFee(transactionFee);
  }

  /**
  * Getter methods for overdraft fee and threshold for overdraft to kick in.
  * @return: getOverdraftFee returns the overdraft fees on the account as a double.
  * @return: getOverdraftAmount: returns the threshold for overdraft to kick in as a double.
  */
  public double getOverdraftFee() {
    return overdraftfee;
  }
  public double getOverdraftAmount() {
    return overdraftAmount;
  }

  /**
  * Setter Methods for overdraft fee and threshold for overdraft to kick in.
  * @param: setOverdraftFee: the fee charged if overdraft kicks in as a double.
  * @param: setOverdraftAmount: the balance when overdraft kicks in as a double.
  */
  public void setOverdraftFee(double fee) {
    overdraftfee = fee;
  }
  public void setOverdraftAmount(double amount) {
    overdraftAmount = -amount;
  }

  /**
  * Withdraw method that overrides the withdraw method in BankAccount
  * Allows for negative withdrawls
  * @param: the amount of money that wants to be withdrawn from the account as a double.
  */
  public void withdraw(double amount) {

    double currentBalance = getBalance();
    //if after withdrawl the balance is above 0, go ahead and withdraw the money
    if ((currentBalance - amount) >= 0.0) {
        setBalance(currentBalance - amount);
      }
    /* if after withdrawl the balance is below 0, check the balance does not violate
    the overdraft amount. If it does not, allow the withdrawl and impose the
    fee*/
    else if ((currentBalance - amount) >= getOverdraftAmount()){
        setBalance(currentBalance - (amount + getOverdraftFee()));
      }
  }

  /**
  * Gets the monthly bank fees and interest
  * @return: the penalty fee if the account balance is too low at the end of the month as a double.
  */
  protected double getMonthlyFeesAndInterest(){
    double monthlyFeesAndInterest = 0.0;
    if (getBalance() < MINIMUM_BALANCE){
      monthlyFeesAndInterest = getBalance() * PENALTY_RATE;
    }
    return monthlyFeesAndInterest;
  }
}

/**
* Customer class representing a bank customer.
* By Dayan Jayasuriya, Nicki Lindstrom and Riley Schaaf
*
*/
import java.io.Serializable;
public final class Customer implements Serializable {
  private String name;
  private int id;


  /**
  * Constructor for name and ID input
  */
  public Customer(String aName, int anId) {
    name = aName;
    id = anId;
  }


  /**
  * Copy constructor for the Customer class.
  */
  public Customer (Customer oldCustomer){
    name = (oldCustomer.getName());
    id = (oldCustomer.getID());
  }

  /**
  * Getter method for name
  * @return: getName: name the name of the customer as a string.
  */
  public String getName() {
    String copyName = new String(name);
    return copyName;
  }

  /*
  * NOW COMMENTED OUT
  * Setter and Getter methods for name
  * @param: setName: newName the name of the customer as a string.
  */
  /*public final void setName(String newName) {
    String copyName = new String(newName);
    name = copyName;
  }*/

  /**
  * Getter method for customerID
  * @return: getid: id the customer ID as an integer.
  */
  public int getID() {
    return id;
  }


  /*
  * NOW COMMENTED OUT
  * Setter method for customerID
  * @param: setid: newCustomerID the customer ID as an integer.
  */
/*  public final void setID(int newCustomerID) {
    id = newCustomerID;
  }*/

  /**
  * Convert instance variables to a combined string
  * @return: varString the customer name and ID in a statement as type String.
  */
  public String toString() {
    String varString = ("Customer name: " + getName() + "; "
    + "Customer ID: " + getID());
    return varString;
  }
}

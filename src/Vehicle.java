

public abstract class Vehicle {
 private String uniqueID;
 private int amountOfTakenPlace;



  public String getUniqueID() {
    return uniqueID;
  }


  public int getAmountOfTakenPlace() {
    return amountOfTakenPlace;
  }


  abstract String getInfo();
}

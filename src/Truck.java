import java.util.UUID;

//грузовой автомоьбиль
public class Truck extends Vehicle {
  private String id;
  private int amountOfTakenPlace;

  public Truck() {
    this.id = UUID.randomUUID().toString();
    this.amountOfTakenPlace = 2;
  }

  public String getId() {
    return id;
  }

  public int getAmountOfTakenPlace() {
    return amountOfTakenPlace;
  }

  @Override
  String getInfo() {
    return "Грузовой автомобиль c id=" + id;
  }
}

import java.util.UUID;
//легковой автомобиль
public class Car extends Vehicle  {

  private String id;
  private int amountOfTakenPlace;

  public Car() {
    this.id = UUID.randomUUID().toString();
    this.amountOfTakenPlace = 1;
  }

  public String getId() {
    return id;
  }

  public int getAmountOfTakenPlace() {
    return amountOfTakenPlace;
  }

  @Override
  String getInfo() {
    return "Легковой автомобиль c id="+id;
  }


}

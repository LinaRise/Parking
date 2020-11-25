import java.awt.*;
import java.util.*;

public class Test {
  private long vehicleGenerationTime = new Date().getTime();
  private long vehicleLeaveParkingTime = new Date().getTime();
  private long vehicleParkingTime = new Date().getTime();
  ArrayList<Vehicle> queueParked = new ArrayList<>();
  ArrayDeque<Vehicle> queueIn = new ArrayDeque<>();
  int inputInterval;
  int outputInterval;
  int maxQueueSize;
  Parking parking;
  Timer timer = new Timer();
  private Vehicle vehicle;
  int parkingTakenPlace;
 final int infoAboutParkingState  = 5;

  static int[] vehiclesToGenerate = {0, 1};
  static int[] frequency = {7, 3};

  public Test(int inputInterval, int outputInterval, int parkingSize, int maxQueueSize) {
    this.inputInterval = inputInterval;
    this.outputInterval = outputInterval;
    this.maxQueueSize = maxQueueSize;
    parking = new Parking(parkingSize);
  }

  void runParkingSimulator() {
    Date generationTime = Utils.getRandomTime(vehicleGenerationTime, inputInterval);
    vehicleGenerationTime = generationTime.getTime();
    Date leaveParkingTime = Utils.getRandomTime(vehicleLeaveParkingTime, inputInterval);
    vehicleLeaveParkingTime = leaveParkingTime.getTime();
    Date parkingTime = Utils.getRandomTime(vehicleLeaveParkingTime, inputInterval);
    vehicleParkingTime = parkingTime.getTime();
    timer.schedule(new CreateNewVehicle(), generationTime);
    timer.schedule(new VehicleParking(), parkingTime);
    timer.schedule(new VehicleLeaveParking(), leaveParkingTime);
    timer.schedule(new ParkingInfo(), 0, infoAboutParkingState * 1000);
  }
//создание нового авто и помещение в очередь
  private class CreateNewVehicle extends TimerTask {
    @Override
    public void run() {
      if (queueIn.size() < maxQueueSize) {
        Date generationTime = Utils.getRandomTime(vehicleGenerationTime, inputInterval);
        vehicleGenerationTime = generationTime.getTime();
        vehicle = generateNewValue();
        System.out.println(vehicleAddedToQueue());
        queueIn.add(vehicle);
        timer.schedule(new CreateNewVehicle(), generationTime);
      } else {
        Toolkit.getDefaultToolkit().beep();
        System.out.println("Программа закончила работу");
        System.exit(0);
      }
    }
  }

  //удаление автомобиля с праковки
  private class VehicleLeaveParking extends TimerTask {
    @Override
    public void run() {
      Date leavingTime = Utils.getRandomTime(vehicleLeaveParkingTime, outputInterval);
      vehicleLeaveParkingTime = leavingTime.getTime();
      if (!queueParked.isEmpty()) {
        int index = Utils.getRandomBetween(0, queueParked.size());
        vehicle = queueParked.get(index);
        queueParked.remove(index);
        System.out.println(vehicleLeftParking());
        parkingTakenPlace -= vehicle.getAmountOfTakenPlace();
      }
      timer.schedule(new VehicleLeaveParking(), leavingTime);
    }
  }
  //парковка автомобиля
  private class VehicleParking extends TimerTask {
    @Override
    public void run() {
      if (!queueIn.isEmpty()) {
        vehicle = queueIn.pop();
        queueParked.add(vehicle);
        parkingTakenPlace += vehicle.getAmountOfTakenPlace();
        System.out.println(vehicleParked());
      }
      timer.schedule(new VehicleParking(), 0, 1);
    }
  }
  // информирование о состоянии парковки
  private class ParkingInfo extends TimerTask {
    @Override
    public void run() {
      System.out.println("\nСвободных мест: " + (parking.getParkingSize() - parkingTakenPlace) + "\n" +
              "Занятно мест: " + parkingTakenPlace + "\n" + getEachVehicleClassAmount() +

              "Автомобилей, ожидающих в очереди: " + queueIn.size()
              + "\n");
    }
  }

// создание нового авто
  private static Vehicle generateNewValue() {
    int value = Utils.getRandomVehicle(vehiclesToGenerate, frequency, vehiclesToGenerate.length);
    if (value == 0) return new Car();
    else return new Truck();
  }

  public String vehicleAddedToQueue() {
    return vehicle.getInfo() + " встал в очередь на въезд";
  }

  public String vehicleParked() {
    return vehicle.getInfo() + " припарковался";
  }

  public String vehicleLeftParking() {
    return vehicle.getInfo() + " покинул парковку";
  }

  public String getEachVehicleClassAmount() {
    int carsAmount = 0;
    int trucksAmount = 0;
    if (!queueParked.isEmpty()) {
      Object[] parkedArray = queueParked.toArray();
      for (Object o : parkedArray) {
        if (o instanceof Car)
          carsAmount++;
        else trucksAmount++;
      }
    }
    return "(из них " + carsAmount + " легковых и " + trucksAmount + " грузовых авто)\n";
  }


  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    boolean flag = false;
    int parkingPlaces = 0;
    boolean done = false;
    while (!done) {
      System.out.print("\nПожалуйста, введите размер парковки: ");
      try {
        parkingPlaces = input.nextInt();
        if (parkingPlaces > 0) {
          done = true;
        } else {
          System.out.println("\n\tВведите положительное число!");
        }
      } catch (InputMismatchException e) {
        System.out.println("\n\tЧисло должно быть целым!");
        input.nextLine();
      }
    }
    Parking parking = new Parking(parkingPlaces);


    done = false;
    int vehiclesMaxQueue = 0;
    while (!done) {
      System.out.print("\nПожалуйста, введите максимальную длину очереди автомобилей ожидающих въезда на парковку: ");
      try {
        vehiclesMaxQueue = input.nextInt();
        if (vehiclesMaxQueue > 0) {
          done = true;
        } else {
          System.out.println("\n\tВведите положительное число!");
        }
      } catch (InputMismatchException e) {
        System.out.println("\n\tЧисло должно быть целым!");
        input.nextLine();
      }
    }


    done = false;
    int inputInterval = 0;
    while (!done) {
      System.out.print("\nПожалуйста, введите интервал генерации входящих автомобилей в секундах: ");
      try {
        inputInterval = input.nextInt();
        if (inputInterval > 0) {
          done = true;
        } else {
          System.out.println("\n\tВведите положительное число!");
        }
      } catch (InputMismatchException e) {
        System.out.println("\n\tЧисло должно быть целым!");
        input.nextLine();
      }
    }

    done = false;
    int outputInterval = 0;
    while (!done) {
      System.out.print("\nПожалуйста, введите интервал генерации выходящих автомобилей в секундах: ");
      try {
        outputInterval = input.nextInt();
        if (outputInterval > 0) {
          done = true;
        } else {
          System.out.println("\n\tВведите положительное число!");
        }
      } catch (InputMismatchException e) {
        System.out.println("\n\tЧисло должно быть целым!");
        input.nextLine();
      }
    }

    Test test = new Test(inputInterval, outputInterval, parkingPlaces, vehiclesMaxQueue);
    test.runParkingSimulator();
  }
}

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class Utils {

  private static final Random RANDOM = new Random();

  //возврат слчайного времени в промежутке
  static Date getRandomTime(long previousTime, int period) {
    Date date = new Date();
    date.setTime(previousTime);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.SECOND, period);
    return new Date(ThreadLocalRandom.current()
            .nextLong(previousTime, calendar.getTimeInMillis()));
  }

  //полчуение случайного числа из интрвала
 static int getRandomBetween(int minIncluded, int maxExcluded) {
    return RANDOM.nextInt(maxExcluded - minIncluded) + minIncluded;
  }

  //для рандомной генерации типа автомобиля
  static int findCeil(int[] arr, int r, int l, int h)
  {
    int mid;
    while (l < h)
    {
      mid = l + ((h - l) >> 1);
      if(r > arr[mid])
        l = mid + 1;
      else
        h = mid;
    }
    return (arr[l] >= r) ? l : -1;
  }
  //случайная генерация типа автомобиля, основанная на заданной частоте генерации каждого элемента
  static int getRandomVehicle(int[] arr, int[] freq, int n)
  {
    int[] prefix = new int[n];
    int i;
    prefix[0] = freq[0];
    for (i = 1; i < n; ++i)
      prefix[i] = prefix[i - 1] + freq[i];
    int r = ((int)(Math.random()*(323567)) % prefix[n - 1]) + 1;
    int indexc = findCeil(prefix, r, 0, n - 1);
    return arr[indexc];
  }

}
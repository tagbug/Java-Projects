package SortAlgorithms;

import java.util.Random;
import static SortAlgorithms.Sort.method.*;

public class Main {
    public static void main(String[] args) {
        int N = 1000;
        Integer[] intArr = new Integer[N];
        Long[] longArr = new Long[N];
        Float[] floatArr = new Float[N];
        Double[] doubleArr = new Double[N];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < N; i++) {
            longArr[i] = random.nextLong();
            intArr[i] = random.nextInt();
            floatArr[i] = random.nextFloat();
            doubleArr[i] = random.nextDouble();
        }

        Sort.timing(insert, intArr);
        Sort.timing(insert, longArr);
        Sort.timing(insert, floatArr);
        Sort.timing(insert, doubleArr);

        System.out.printf("\n😅🐮(●'◡'●)");
    }
}

package bitonic.helpers;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HelperFunctions {

    // https://stackoverflow.com/questions/1519736/random-shuffling-of-an-array
    public static void shuffleArray(int[] array)
    {
        int index;
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--)
        {
            index = random.nextInt(i + 1);
            if (index != i)
            {
                array[index] ^= array[i];
                array[i] ^= array[index];
                array[index] ^= array[i];
            }
        }
    }

    public static Boolean bitonic(int[] array) {
        if (array == null) return false;
        if (array.length < 4) return true;
        Boolean dir;// false is decreasing, true is increasing
        int pos = 0, switches = 0;
        while (pos < array.length) {
            if (array[pos] != array[array.length - 1])
                break;
            pos++;
        }
        if (pos == array.length) return true;
        //pos here is the first element that differs from the last
        dir = array[pos] > array[array.length - 1];
        while (pos < array.length - 1 && switches <= 2) {
            if ((array[pos + 1] != array[pos]) &&
                    ((array[pos + 1] <= array[pos]) == dir)) {
                dir ^= true;
                switches++;
            }
            pos++;
        }
        return switches <= 2;
    }
}

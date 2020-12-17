package lab12;
import java.lang.Thread;
import java.util.*;

public class Array {
    static final int size = 10000000;
    static final int half = size / 2;

    public static float[] createArray() {
        float[] arr = new float[size];
        Arrays.fill(arr,1);
        return arr;
    }

    public static void methodOne(float[] arr) {
        long a = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время работы 1 метода: " + (System.currentTimeMillis() - a));
    }

    public static void methodTwo(float[] arr) {
        float[] a1 = new float[half];
        float[] a2 = new float[half];
        long b = System.currentTimeMillis();
        System.arraycopy(arr, 0, a1, 0, half);
        System.arraycopy(arr, half, a2, 0, half);

        Thread mOne = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a1.length; i++) {
                    a1[i] = (float)(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        mOne.start();

        Thread mTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < a2.length; i++) {
                    a2[i] = (float)(a2[i] * Math.sin(0.2f + (i+half) / 5) * Math.cos(0.2f + (i+half) / 5) * Math.cos(0.4f + (i+half) / 2));
                }
            }
        });
        mTwo.start();

        System.arraycopy(a1, 0, arr, 0, half);
        System.arraycopy(a2, 0, arr, half, half);
        System.out.println("Время работы 2 метода: " + (System.currentTimeMillis() - b));
    }

    public static void main(String[] args) {
        methodOne(createArray());
        methodTwo(createArray());
    }
}
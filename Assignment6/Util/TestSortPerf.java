package Util;

import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

import Util.ArrayList;

public class TestSortPerf {
    public static void main(String[] args) {
        Random rand = new Random();
        Integer[] arr = new Integer[10000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(arr.length);
        }
        testSort(arr);
    }

    public static void testSort(Integer... integers) {
        ArrayList<Integer> list = new ArrayList<>();
        list.comparator = (a, b) -> a - b;
        list.addAll(integers);
        list.sort(0, list.size() - 1);
        Arrays.sort(integers);
        assertArrayEquals(integers, list.toArray());
    }
}
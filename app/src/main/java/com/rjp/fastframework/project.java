package com.rjp.fastframework;

public class project {
    public static void main(String[] args) {

//        int[] ints = oneCount(10);
//        for (int anInt : ints) {
//            System.out.println(anInt);
//        }
        System.out.println(count(Integer.MAX_VALUE - 2));
    }

    public interface X {
        void a();
    }

    public interface Y {
        void a();
    }

    public static int count(int n) {
        int count = 0;
        while (n != 0) {
            if ((n & 1) == 1) {
                count++;
            }
            n = n >> 1;
        }
        return count;
    }

    public static int[] oneCount(int n) {
        int[] res = new int[n + 1];
        int pow = 1, p = 1;
        for (int i = 1; i <= n; i++) {
            if (i == pow) {
                res[i] = 1;
                pow = pow << 1;
                p = 1;
            } else {
                res[i] = res[p++] + 1;
            }
        }
        return res;
    }
}

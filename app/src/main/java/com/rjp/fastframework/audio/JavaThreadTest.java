package com.rjp.fastframework.audio;

public class JavaThreadTest {

    public static void main(String[] args) {
        LooperThread t1 = new LooperThread("t-1");
        LooperThread t2 = new LooperThread("t-2");
        t1.start();
        t2.start();
    }

    public static class LooperThread extends Thread{
        private int a = 100;

        public LooperThread(String name){
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                a++;
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t = " + Thread.currentThread().getName() + " | " + a);
            }
        }
    }
}

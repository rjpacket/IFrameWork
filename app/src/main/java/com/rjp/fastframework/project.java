package com.rjp.fastframework;

public class project {
    public static void main(String[] args) {

        class A{
            public A(){
                System.out.println("A init");
            }

            public void a(){
                System.out.println("aa");
            }
        }

        class B extends A{
            public B(){
                System.out.println("B init");
            }

            public void a(){
                System.out.println("ba");
            }
        }

        class C extends B{

            @Override
            public void a() {
                System.out.println("ca");
            }
        }

        A c = new C();
        c.a();
    }


}

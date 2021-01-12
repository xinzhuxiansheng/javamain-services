package com.javamain.kafkaClient.demo;

public class yzhoutest {
    public static void main(String[] args) {
//        B b  = new B();
//        b.setF3("f3");
//        b.setF4("f4");
//
//        A a = b;
//        a.fun1();




    }

    public static abstract class A{
        private String f1;
        private String f2;

        public String getF1() {
            return f1;
        }

        public void setF1(String f1) {
            this.f1 = f1;
        }

        public String getF2() {
            return f2;
        }

        public void setF2(String f2) {
            this.f2 = f2;
        }

        public abstract void fun1();
    }

    public static class B extends A{
        private String f3;
        private String f4;

        private String result;

        public String getF3() {
            return f3;
        }

        public void setF3(String f3) {
            this.f3 = f3;
        }

        public String getF4() {
            return f4;
        }

        public void setF4(String f4) {
            this.f4 = f4;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        @Override
        public void fun1() {
            result = f3+f4;
            System.out.println(result);
        }
    }
}




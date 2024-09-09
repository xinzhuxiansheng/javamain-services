package com.javamain.proxy.p01;


import java.lang.reflect.Proxy;

public class TestMain {
    /**
     * JDK动态代理测试类
     */
    public static void main(String[] args) {
//        Car car = new Car();
//        InvocationHandler h = new TimeHandler(car);
//        Class<?> cls = car.getClass();
//        /**
//         * loader  类加载器
//         * interfaces  实现接口
//         * h InvocationHandler
//         */
//        Moveable m = (Moveable) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), h);
//        m.move();

        Moveable car = new Car();
        Moveable proxy = (Moveable) Proxy.newProxyInstance(car.getClass().getClassLoader(),
                car.getClass().getInterfaces(),
                new TimeHandler(car));
        proxy.move();

        // 保存代理类的字节码到文件
        //saveProxyClassToFile("$Proxy0.class");
    }

    /**
     * sun.misc.ProxyGenerator 类在 JDK 9 及更高版本中已被移除。在这些版本中，您需要使用第三方库，如 ASM 或 Javassist，生成动态代理类的字节码
     */
//    private static void saveProxyClassToFile(String fileName) {
//        byte[] classBytes = ProxyGenerator.generateProxyClass(
//                "$Proxy0",
//                new Class<?>[]{Moveable.class}
//        );
//
//        Path outputPath = Paths.get(fileName);
//        try (FileOutputStream fos = new FileOutputStream(outputPath.toFile())) {
//            fos.write(classBytes);
//            System.out.println("Proxy class saved to: " + outputPath.toAbsolutePath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

<<<<<<< HEAD
//package com.javamain.java.bytecode.example01;
//
//import com.sun.tools.javap.JavapTask;
//
//import java.io.IOException;
//
//public class RunJavaCompiler {
//    public static String commonPath = "/Users/xxxx/Code/JAVA/yzhou/javamain-services/javamain-jdk/src/test/java/";
//    public static String sourcePath = commonPath + "com/javamain/java/bytecode/example01/TestGetSetAnnotation.java";
//    public static String destPath = "/Users/xxxx/Code/JAVA/yzhou/javamain-services/javamain-jdk/output";
//
//    // 在com.sun.tools.javac.main.JavaCompiler的desugar()方法中加入System.out.println()
//    public static void main(String args[]) throws IOException {
//       // compile();
//		//decompile();
//    }
//
//    public static void compile() {
////		javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
////		int rc = compiler.run(null, null, null,
////             new String[]{
////				"-d",destPath,
////				sourcePath
////		     }
////		);
////		System.out.println("Result code: " + rc);
//
//        com.sun.tools.javac.main.Main compiler = new com.sun.tools.javac.main.Main("javac");
//        String[] args = new String[]{
//                "-sourcepath", ".",
//                "-d", destPath,
//                sourcePath
//        };
//        int rc = compiler.compile(args).exitCode;
//        System.out.println("Result code: " + rc);
//    }
//
////    public static void decompile() {
////        String[] args = new String[]{
////                "-verbose",
////                destPath + "/lesson1/TestClassInitializer.class"
////        };
////
////        JavapTask t = new JavapTask();
////        int rc = t.run(args);
////        System.exit(rc);
////    }
//
//}
//
//
//
//
//
=======
//package com.javamain.java.bytecode.example01;
//
//import com.sun.tools.javap.JavapTask;
//
//import java.io.IOException;
//
//public class RunJavaCompiler {
//    public static String commonPath = "/Users/xxxx/Code/JAVA/yzhou/javamain-services/javamain-jdk/src/test/java/";
//    public static String sourcePath = commonPath + "com/javamain/java/bytecode/example01/TestGetSetAnnotation.java";
//    public static String destPath = "/Users/xxxx/Code/JAVA/yzhou/javamain-services/javamain-jdk/output";
//
//    // 在com.sun.tools.javac.main.JavaCompiler的desugar()方法中加入System.out.println()
//    public static void main(String args[]) throws IOException {
//       // compile();
//		//decompile();
//    }
//
//    public static void compile() {
////		javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
////		int rc = compiler.run(null, null, null,
////             new String[]{
////				"-d",destPath,
////				sourcePath
////		     }
////		);
////		System.out.println("Result code: " + rc);
//
//        com.sun.tools.javac.main.Main compiler = new com.sun.tools.javac.main.Main("javac");
//        String[] args = new String[]{
//                "-sourcepath", ".",
//                "-d", destPath,
//                sourcePath
//        };
//        int rc = compiler.compile(args).exitCode;
//        System.out.println("Result code: " + rc);
//    }
//
////    public static void decompile() {
////        String[] args = new String[]{
////                "-verbose",
////                destPath + "/lesson1/TestClassInitializer.class"
////        };
////
////        JavapTask t = new JavapTask();
////        int rc = t.run(args);
////        System.exit(rc);
////    }
//
//}
//
//
//
//
//
>>>>>>> 6d96e7e0c1420f234e11ca49b02863b7c5f79a7c

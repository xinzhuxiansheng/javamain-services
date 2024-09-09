package yzhou.javamain.agent.attach01;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

public class LoadTimeAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Premain-Class: " + LoadTimeAgent.class.getName());
        ClassFileTransformer transformer = new ASMTransformer();
        inst.addTransformer(transformer);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        ClassFileTransformer transformer = new ASMTransformer();
        try {
            inst.addTransformer(transformer, true);
            Class<?> targetClass = Class.forName("yzhou.javamain.agent.printpid.HelloWorld");
            inst.retransformClasses(targetClass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            inst.removeTransformer(transformer);
        }
    }
}
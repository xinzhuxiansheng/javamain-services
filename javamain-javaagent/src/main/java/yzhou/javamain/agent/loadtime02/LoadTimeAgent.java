package yzhou.javamain.agent.loadtime02;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

public class LoadTimeAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("Premain-Class: " + LoadTimeAgent.class.getName());
        ClassFileTransformer transformer = new ASMTransformer();
        inst.addTransformer(transformer);
    }
}
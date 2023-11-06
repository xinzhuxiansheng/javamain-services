package yzhou.javamain.agent.loadtime02;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class ASMTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        if (className == null) return null;
        if (className.startsWith("java")) return null;
        if (className.startsWith("javax")) return null;
        if (className.startsWith("jdk")) return null;
        if (className.startsWith("sun")) return null;
        if (className.startsWith("org")) return null;
        if (className.startsWith("com")) return null;
        //if (className.startsWith("javamain")) return null;

        System.out.println("candidate className: " + className);

        if (className.equals("yzhou/javamain/agent/printpid/HelloWorld")) {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = new MethodInfoVisitor(cw);

            int parsingOptions = 0;
            cr.accept(cv, parsingOptions);

            return cw.toByteArray();
        }

        return null;
    }
}

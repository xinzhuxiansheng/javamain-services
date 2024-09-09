package yzhou.javamain.agent.loadtime01;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.util.Formatter;

public class InfoTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        StringBuilder sb = new StringBuilder();
        Formatter fm = new Formatter(sb);
        fm.format("ClassName: %s%n", className);
        fm.format("    ClassLoader: %s%n", loader);
        fm.format("    ClassBeingRedefined: %s%n", classBeingRedefined);
        fm.format("    ProtectionDomain: %s%n", protectionDomain);
        System.out.println(sb.toString());

        return null;
    }
}

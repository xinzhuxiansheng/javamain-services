package yzhou.javamain.agent.loadtime02;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class MethodInfoVisitor extends ClassVisitor {
    private String owner;

    public MethodInfoVisitor(ClassVisitor classVisitor) {
        super(Const.ASM_VERSION, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.owner = name;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if (mv != null && !name.equals("<init>") && !name.equals("<clinit>")) {
            mv = new MethodInfoAdapter(mv, owner, access, name, descriptor);
        }
        return mv;
    }
}

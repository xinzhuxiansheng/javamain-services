package yzhou.javamain.agent.attach01;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

public class MethodInfoAdapter extends MethodVisitor {
    private final String owner;
    private final int methodAccess;
    private final String methodName;
    private final String methodDesc;

    public MethodInfoAdapter(MethodVisitor methodVisitor, String owner,
                             int methodAccess, String methodName, String methodDesc) {
        super(Const.ASM_VERSION, methodVisitor);
        this.owner = owner;
        this.methodAccess = methodAccess;
        this.methodName = methodName;
        this.methodDesc = methodDesc;
    }

    @Override
    public void visitCode() {
        if (mv != null) {
            String line = String.format("Method Enter: %s.%s:%s", owner, methodName, methodDesc);
            printMessage(line);

            int slotIndex = (methodAccess & Opcodes.ACC_STATIC) != 0 ? 0 : 1;
            Type methodType = Type.getMethodType(methodDesc);
            Type[] argumentTypes = methodType.getArgumentTypes();
            for (Type t : argumentTypes) {
                int sort = t.getSort();
                int size = t.getSize();
                int opcode = t.getOpcode(Opcodes.ILOAD);
                super.visitVarInsn(opcode, slotIndex);

                if (sort >= Type.BOOLEAN && sort <= Type.DOUBLE) {
                    String desc = t.getDescriptor();
                    printValueOnStack("(" + desc + ")V");
                }
                else {
                    printValueOnStack("(Ljava/lang/Object;)V");
                }
                slotIndex += size;
            }
        }

        super.visitCode();
    }

    private void printMessage(String str) {
        super.visitLdcInsn(str);
        super.visitMethodInsn(Opcodes.INVOKESTATIC, "yzhou/javamain/agent/utils/ParameterUtils", "printText", "(Ljava/lang/String;)V", false);
    }

    private void printValueOnStack(String descriptor) {
        super.visitMethodInsn(Opcodes.INVOKESTATIC, "yzhou/javamain/agent/utils/ParameterUtils", "printValueOnStack", descriptor, false);
    }

    private void printStackTrace() {
        super.visitMethodInsn(Opcodes.INVOKESTATIC, "yzhou/javamain/agent/utils/ParameterUtils", "printStackTrace", "()V", false);
    }
}

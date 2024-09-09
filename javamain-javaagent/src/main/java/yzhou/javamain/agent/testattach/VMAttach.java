package yzhou.javamain.agent.testattach;

import com.sun.tools.attach.VirtualMachine;

public class VMAttach {
    public static void main(String[] args) throws Exception {
        String agent = "/Users/a/Code/Java/javamain-services/javamain-javaagent/target/javamain-javaagent-1.0-SNAPSHOT-jar-with-dependencies.jar";
        System.out.println("Agent Path: " + agent);
//        List<VirtualMachineDescriptor> vmds = VirtualMachine.list();
//        for (VirtualMachineDescriptor vmd : vmds) {
//            if (vmd.displayName().equals("Program")) {
//                VirtualMachine vm = VirtualMachine.attach(vmd.id());
//                System.out.println("Load Agent");
//                vm.loadAgent(agent);
//                System.out.println("Detach");
//                vm.detach();
//            }
//        }

        // attach方法参数为目标应用程序的进程号
        VirtualMachine vm = VirtualMachine.attach("41432");
        // 请用你自己的agent绝对地址，替换这个
        vm.loadAgent("/Users/a/Code/Java/javamain-services/javamain-javaagent/target/javamain-javaagent-1.0-SNAPSHOT-jar-with-dependencies.jar");
    }
}


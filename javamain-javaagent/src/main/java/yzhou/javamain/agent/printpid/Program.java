package yzhou.javamain.agent.printpid;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

public class Program {
    public static void main(String[] args) throws Exception {
        // (1) print process id
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(nameOfRunningVM);

        // (2) count down
        int count = 600;
        for (int i = 0; i < count; i++) {
            System.out.println(HelloWorld.add(i, i + 1));
            TimeUnit.SECONDS.sleep(1);
        }
    }
}

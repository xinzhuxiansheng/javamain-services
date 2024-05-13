package com.javamain.flink.jobstatusmonitor;

import com.javamain.flink.jobstatus.k8s.FlinkonK8sUtils;
import org.junit.Test;

public class K8sUtilsTest {
    @Test
    public void testListFlinkDeployments(){
        FlinkonK8sUtils.listFlinkDeployments();
    }

    @Test
    public void testListFlinkSessionJobs(){
        FlinkonK8sUtils.listFlinkSessionJobs();
    }

}

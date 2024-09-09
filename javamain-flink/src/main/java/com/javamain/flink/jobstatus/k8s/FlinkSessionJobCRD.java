package com.javamain.flink.jobstatus.k8s;

import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Kind;
import io.fabric8.kubernetes.model.annotation.Plural;
import io.fabric8.kubernetes.model.annotation.Version;
import org.apache.flink.kubernetes.operator.api.spec.FlinkDeploymentSpec;
import org.apache.flink.kubernetes.operator.api.status.FlinkDeploymentStatus;

@Group(FlinkCRDConstants.Group)
@Version(FlinkCRDConstants.Version)
@Plural(FlinkCRDConstants.Plural_Flinksessionjobs)
@Kind(FlinkCRDConstants.Kind_FlinkSessionJob)
public class FlinkSessionJobCRD extends CustomResource<FlinkDeploymentSpec, FlinkDeploymentStatus>{

}

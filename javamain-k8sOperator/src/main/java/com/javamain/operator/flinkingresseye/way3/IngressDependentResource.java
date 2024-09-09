package com.javamain.operator.flinkingresseye.way3;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.networking.v1.*;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@KubernetesDependent
public class IngressDependentResource extends CRUDKubernetesDependentResource<Ingress, Service> {
    private static final Logger logger = LoggerFactory.getLogger(IngressDependentResource.class);
    public IngressDependentResource() {
        super(Ingress.class);
    }
    @Override
    protected Ingress desired(Service primary, Context<Service> context) {
        logger.info("into IngressDependentResource#desired()");
        return buildIngressFromService(primary);
    }
    private Ingress buildIngressFromService(Service service) {
        String serviceName = service.getMetadata().getName();
        String namespace = service.getMetadata().getNamespace();
        Map<String, String> labels = service.getMetadata().getLabels();
        String jobName = labels.get("app");

        // 设置注释
        Map<String, String> annotations = new HashMap<>();
        annotations.put("nginx.ingress.kubernetes.io/rewrite-target", "/$2");

        // 构建 Ingress metadata
        ObjectMeta ingressMetadata = new ObjectMetaBuilder()
                .withName(jobName)
                .withNamespace(namespace)
                .withAnnotations(annotations)
                .build();

        String path = MessageFormat.format("/{0}/{1}(/|$)(.*)", namespace, jobName);
        ServiceBackendPort port = new ServiceBackendPort();
        port.setNumber(8081);

        // 构建 Ingress spec
        IngressSpec ingressSpec = new IngressSpecBuilder()
                .withIngressClassName("nginx")
                .withRules(new IngressRuleBuilder()
                        .withHost("flink.k8s.io")
                        .withHttp(new HTTPIngressRuleValueBuilder()
                                .withPaths(new HTTPIngressPathBuilder()
                                        .withPath(path)
                                        .withPathType("ImplementationSpecific")
                                        .withNewBackend()
                                        .withService(new IngressServiceBackendBuilder()
                                                .withName(serviceName)
                                                .withPort(port)
                                                .build())
                                        .endBackend()
                                        .build())
                                .build())
                        .build())
                .build();

        // 构建 Ingress
        return new IngressBuilder()
                .withMetadata(ingressMetadata)
                .withSpec(ingressSpec)
                .build();
    }
}

package com.javamain.operator.flinkingresseye.way1;

import com.javamain.operator.flinkingresseye.K8sClientSingleton;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.networking.v1.*;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.javaoperatorsdk.operator.api.reconciler.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@ControllerConfiguration(namespaces = Constants.WATCH_ALL_NAMESPACES)
public class FlinkIngressReconciler implements Reconciler<Service>, ErrorStatusHandler<Service>, Cleaner<Service> {
    private static final Logger logger = LoggerFactory.getLogger(FlinkIngressReconciler.class);

    @Override
    public UpdateControl<Service> reconcile(Service service, Context<Service> context) {
        if (service == null) {
            return UpdateControl.noUpdate();
        }
        String serviceName = service.getMetadata().getName();
        String namespace = service.getMetadata().getNamespace();
        /*
            因为 Flink Job 会创建 2个 service，所以，根据 “-rest” 特征, 去掉一个
            为啥选用，-rest ，因为 在 Ingress 绑定 rest service, 还有 它绑定时 Flink Web UI
         */
        if (!serviceName.contains("-rest")) {
            return UpdateControl.noUpdate();
        }
        if (!isFlinkServiceAndCheckLabelsAppIsValid(service)) {
            return UpdateControl.noUpdate();
        }
        String jobName = getServiceLabelValue(service, "app");

        Ingress existingIngress = getIngressByName(namespace, jobName).get();
        if (existingIngress != null) {
            // 若存在，也不去更新
            return UpdateControl.noUpdate();
        }
        logger.info("Creating Ingress for Service: {}", serviceName);
        // 构建或更新 Ingress 资源
        Ingress ingress = buildIngressFromService(service);
        K8sClientSingleton.getKubernetesClient().
                network().v1().ingresses().inNamespace(namespace).create(ingress);
        return UpdateControl.noUpdate();
    }

    @Override
    public ErrorStatusUpdateControl<Service> updateErrorStatus(Service service, Context<Service> context, Exception e) {
        // 错误处理逻辑
        return ErrorStatusUpdateControl.noStatusUpdate();
    }

    /*
        检查是否是 Flink Service
        type: flink-native-kubernetes
     */
    private boolean isFlinkServiceAndCheckLabelsAppIsValid(Service service) {
        Map<String, String> labels = getServiceLabels(service);
        boolean isFlinkService = labels != null &&
                labels.containsKey("type") &&
                "flink-native-kubernetes".equalsIgnoreCase(labels.get("type"));
        /*
            观察 Flink Kubernetes Operator 的 ingress 创建的 name 与 Flink Job name 一样，
            所以取 labels 的 app 值 赋值给 ingress name
         */
        boolean appIsValid = labels.containsKey("app") && StringUtils.isNotBlank(labels.get("app"));
        return isFlinkService && appIsValid;
    }

    private Map<String, String> getServiceLabels(Service service) {
        return service.getMetadata().getLabels();
    }

    private Resource<Ingress> getIngressByName(String namespace, String jobName) {
        Resource<Ingress> ingressResource = K8sClientSingleton.getKubernetesClient().
                network().
                v1().
                ingresses().
                inNamespace(namespace).
                withName(jobName);
        return ingressResource;
    }

    /**
     * 获取给定 Service 的某个标签值
     */
    public String getServiceLabelValue(Service service, String labelKey) {
        Map<String, String> labels = getServiceLabels(service);
        return labels != null ? labels.get(labelKey) : null;
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

    @Override
    public DeleteControl cleanup(Service service, Context<Service> context) {
        if (isFlinkServiceAndCheckLabelsAppIsValid(service)) {
            String namespace = service.getMetadata().getNamespace();
            String serviceName = service.getMetadata().getName();
            if (!serviceName.contains("-rest")) {
                return DeleteControl.defaultDelete();
            }
            String jobName = getServiceLabelValue(service, "app");
            Ingress existingIngress = getIngressByName(namespace, jobName).get();
            K8sClientSingleton.getKubernetesClient().
                    network().
                    v1().ingresses().inNamespace(namespace)
                    .withName(jobName)
                    .delete(existingIngress);
            logger.info("ingress was deleted");
        }
        // 注意：若不符合过滤条件，则按照 默认删除方式，确保移除 finalizer
        return DeleteControl.defaultDelete();
    }
}


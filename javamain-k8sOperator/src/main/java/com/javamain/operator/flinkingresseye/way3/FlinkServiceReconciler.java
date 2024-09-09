//package com.javamain.operator.flinkingresseye.way3;
//
//import com.javamain.operator.flinkingresseye.K8sClientSingleton;
//import io.fabric8.kubernetes.api.model.Service;
//import io.fabric8.kubernetes.api.model.networking.v1.Ingress;
//import io.fabric8.kubernetes.client.dsl.Resource;
//import io.javaoperatorsdk.operator.api.config.informer.InformerConfiguration;
//import io.javaoperatorsdk.operator.api.reconciler.*;
//import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
//import io.javaoperatorsdk.operator.processing.event.ResourceID;
//import io.javaoperatorsdk.operator.processing.event.source.SecondaryToPrimaryMapper;
//import io.javaoperatorsdk.operator.processing.event.source.informer.InformerEventSource;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Map;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@ControllerConfiguration(
//        dependents = {
//                @Dependent(type = IngressDependentResource.class)
//        }
//)
//public class FlinkServiceReconciler implements Reconciler<Service>
//        , EventSourceInitializer<Service>
//{
//    private static final Logger logger = LoggerFactory.getLogger(FlinkServiceReconciler.class);
//
//    @Override
//    public UpdateControl<Service> reconcile(Service service, Context<Service> context) {
//        logger.info("into FlinkServiceReconciler#reconcile()");
//
//        if (service == null) {
//            return UpdateControl.noUpdate();
//        }
//        String serviceName = service.getMetadata().getName();
//        String namespace = service.getMetadata().getNamespace();
//        /*
//            因为 Flink Job 会创建 2个 service，所以，根据 “-rest” 特征, 去掉一个
//            为啥选用，-rest ，因为 在 Ingress 绑定 rest service, 还有 它绑定时 Flink Web UI
//         */
//        if (!serviceName.contains("-rest")) {
//            return UpdateControl.noUpdate();
//        }
//        if (!isFlinkServiceAndCheckLabelsAppIsValid(service)) {
//            return UpdateControl.noUpdate();
//        }
//        String jobName = getServiceLabelValue(service, "app");
//
//        Ingress existingIngress = getIngressByName(namespace, jobName).get();
//        if (existingIngress == null) {
//            logger.info("not found Ingress resource, then create new ");
//            return UpdateControl.updateResource(service);
//        }
//        return UpdateControl.noUpdate();
//    }
//
//    private boolean isFlinkServiceAndCheckLabelsAppIsValid(Service service) {
//        Map<String, String> labels = getServiceLabels(service);
//        boolean isFlinkService = labels != null &&
//                labels.containsKey("type") &&
//                "flink-native-kubernetes".equalsIgnoreCase(labels.get("type"));
//        /*
//            观察 Flink Kubernetes Operator 的 ingress 创建的 name 与 Flink Job name 一样，
//            所以取 labels 的 app 值 赋值给 ingress name
//         */
//        boolean appIsValid = labels.containsKey("app") && StringUtils.isNotBlank(labels.get("app"));
//        return isFlinkService && appIsValid;
//    }
//
//    private Map<String, String> getServiceLabels(Service service) {
//        return service.getMetadata().getLabels();
//    }
//
//    private Resource<Ingress> getIngressByName(String namespace, String jobName) {
//        Resource<Ingress> ingressResource = K8sClientSingleton.getKubernetesClient().
//                network().
//                v1().
//                ingresses().
//                inNamespace(namespace).
//                withName(jobName);
//        return ingressResource;
//    }
//
//    public String getServiceLabelValue(Service service, String labelKey) {
//        Map<String, String> labels = getServiceLabels(service);
//        return labels != null ? labels.get(labelKey) : null;
//    }
//
//    @Override
//    public Map<String, EventSource> prepareEventSources(EventSourceContext<Service> eventSourceContext) {
//        final SecondaryToPrimaryMapper<Service> webappsMatchingTomcatName =
//                (Service t) -> eventSourceContext.getPrimaryCache()
//                        .list(service -> service.getMetadata().getName().equals(t.getMetadata().getName()))
//                        .map(ResourceID::fromResource)
//                        .collect(Collectors.toSet());
//
//        InformerConfiguration<Service> configuration =
//                InformerConfiguration.from(Service.class, eventSourceContext)
//                        .withSecondaryToPrimaryMapper(webappsMatchingTomcatName)
//                        .withPrimaryToSecondaryMapper(
//                                (Service primary) -> Set.of(new ResourceID(
//                                        primary.getMetadata().getUid(),
//                                        primary.getMetadata().getNamespace())))
//                        .build();
//        return EventSourceInitializer
//                .nameEventSources(new InformerEventSource<>(configuration, eventSourceContext));
//    }
//}
//

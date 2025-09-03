package com.javamain.calcite.memory;

import io.fabric8.kubernetes.api.model.ContainerStatus;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryDataMain {
  private static final Logger logger = LoggerFactory.getLogger(QueryDataMain.class);

  public static void main(String[] args) throws SQLException {
    List<PodInfo> podInfos = getPodInfos();

    podInfos.forEach(System.out::println);

    PodInfoCalciteManager podInfoCalciteManager = new PodInfoCalciteManager();
    podInfoCalciteManager.updatePodInfoList(podInfos);
    List<PodInfo> result = podInfoCalciteManager.executeQuery("select * from podInfoSchema.podInfoTable where namespace like '%flink%'");
//  List<PodInfo> result = podInfoCalciteManager.executeQuery("SELECT * FROM \"podInfoSchema\".\"podInfoTable\"");
    if (result != null) {
      result.forEach(System.out::println);
    }
  }

  private static List<PodInfo> getPodInfos() {
    List<PodInfo> podInfos = new ArrayList<>();
    try {
      // 创建 Kubernetes Client 实例
      KubernetesClient client = new KubernetesClientBuilder().build();

      // 获取命名空间列表
      NamespaceList namespaceList = client.namespaces().list();

      // 打印命名空间信息
      logger.info("Available namespaces:");
      namespaceList.getItems().forEach(namespace -> {
        logger.info("Namespace: {}", namespace.getMetadata().getName());
      });

      // 获取所有Pod信息
      logger.info("Pod Information:");
      PodList podList = client.pods().inAnyNamespace().list();

      for (Pod pod : podList.getItems()) {
        String namespace = pod.getMetadata().getNamespace();
        String podName = pod.getMetadata().getName();
        String podIP = pod.getStatus().getPodIP() != null ? pod.getStatus().getPodIP() : "";
        String node = pod.getSpec().getNodeName() != null ? pod.getSpec().getNodeName() : "";
        String status = pod.getStatus().getPhase();

        // 计算Pod的Age
        Date creationTimestamp = pod.getMetadata().getCreationTimestamp() != null ?
                Date.from(Instant.parse(pod.getMetadata().getCreationTimestamp())) : null;
        String age = calculateAge(creationTimestamp);

        // 计算Ready状态容器数
        int readyContainers = 0;
        int totalContainers = 0;
        int restartCount = 0;

        if (pod.getStatus().getContainerStatuses() != null) {
          totalContainers = pod.getStatus().getContainerStatuses().size();
          for (ContainerStatus containerStatus : pod.getStatus().getContainerStatuses()) {
            if (containerStatus.getReady()) {
              readyContainers++;
            }
            restartCount += containerStatus.getRestartCount();
          }
        }

        String ready = readyContainers + "/" + totalContainers;

        // 创建PodInfo对象
        PodInfo podInfo = new PodInfo(namespace, podName, ready, status, restartCount, age, podIP, node);
        podInfos.add(podInfo);
      }

      // 关闭客户端
      client.close();
    } catch (Exception e) {
      logger.error("Error while connecting to Kubernetes", e);
    }
    return podInfos;
  }

  private static String calculateAge(Date creationTimestamp) {
    if (creationTimestamp == null) {
      return "Unknown";
    }

    Duration duration = Duration.between(creationTimestamp.toInstant(), Instant.now());
    long days = duration.toDays();
    long hours = duration.minusDays(days).toHours();
    long minutes = duration.minusDays(days).minusHours(hours).toMinutes();

    if (days > 0) {
      return days + "d" + hours + "h";
    } else if (hours > 0) {
      return hours + "h" + minutes + "m";
    } else {
      return minutes + "m";
    }
  }
}
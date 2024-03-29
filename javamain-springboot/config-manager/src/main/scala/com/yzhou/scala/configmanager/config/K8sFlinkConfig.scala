package com.yzhou.scala.configmanager.config

/**
 * Flink kubernetes Configuration
 *
 * @author Al-assad
 */
object K8sFlinkConfig {

  val jobStatusTrkTaskTimeoutSec: InternalOption = InternalOption(
    key = "streamx.flink-k8s.tracking.polling-task-timeout-sec.job-status",
    defaultValue = 120L,
    classType = classOf[java.lang.Long],
    description = "run timeout seconds of single flink-k8s metrics tracking task")

  val metricTrkTaskTimeoutSec: InternalOption = InternalOption(
    key = "streamx.flink-k8s.tracking.polling-task-timeout-sec.cluster-metric",
    defaultValue = 120L,
    classType = classOf[java.lang.Long],
    description = "run timeout seconds of single flink-k8s job status tracking task")

  val jobStatueTrkTaskIntervalSec: InternalOption = InternalOption(
    key = "streamx.flink-k8s.tracking.polling-interval-sec.job-status",
    defaultValue = 5L,
    classType = classOf[java.lang.Long],
    description = "interval seconds between two single flink-k8s metrics tracking task")

  val metricTrkTaskIntervalSec: InternalOption = InternalOption(
    key = "streamx.flink-k8s.tracking.polling-interval-sec.cluster-metric",
    defaultValue = 10L,
    classType = classOf[java.lang.Long],
    description = "interval seconds between two single flink-k8s metrics tracking task")

  val silentStateJobKeepTrackingSec: InternalOption = InternalOption(
    key = "streamx.flink-k8s.tracking.silent-state-keep-sec",
    defaultValue = 60,
    classType = classOf[java.lang.Integer],
    description = "retained tracking time for SILENT state flink tasks")

  /**
   * kubernetes default namespace
   */
  val DEFAULT_KUBERNETES_NAMESPACE = "default"

}

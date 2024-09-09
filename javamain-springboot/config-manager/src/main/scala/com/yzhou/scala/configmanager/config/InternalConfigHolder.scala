package com.yzhou.scala.configmanager.config

import com.yzhou.scala.configmanager.util.{Logger, SystemPropertyUtils}

import java.util
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.{Nonnull, Nullable}
import scala.collection.JavaConversions._
import scala.language.postfixOps

/**
 * Thread-safe configuration storage containers.
 * All configurations will be automatically initialized from the spring
 * configuration items of the same name.
 *
 * @author Al-assad
 */
object InternalConfigHolder extends Logger {

  private val initialCapacity = 45

  /**
   * configuration values storage (key -> value)
   */
  private val confData = new ConcurrentHashMap[String, Any](initialCapacity)

  /**
   * configuration key options storage (key -> ConfigOption)
   */
  private val confOptions = new ConcurrentHashMap[String, InternalOption](initialCapacity)

  /**
   * Initialize the ConfigHub.
   */
  {
    Seq(K8sFlinkConfig)
  }

  /**
   * Register the ConfigOption
   */
  private[config] def register(@Nonnull conf: InternalOption): Unit = {
    confOptions.put(conf.key, conf)
    if (conf.defaultValue != null) {
      confData.put(conf.key, conf.defaultValue)
    }
  }

  /**
   * Get configuration value via ConfigOption.
   *
   * When using this api, the type must be explicitly declared and the relevant type will be
   * automatically converted to some extent.
   * 1) in scala:
   * val result: Long = ConfigHub.get(K8sFlinkConfig.sglMetricTrkTaskTimeoutSec)
   * 2) in java:
   * Long result = ConfigHub.get(K8sFlinkConfig.sglMetricTrkTaskTimeoutSec());
   *
   * @return return the defaultValue of ConfigOption when the value has not been overwritten.
   */
  @Nonnull
  def get[T](@Nonnull conf: InternalOption): T = {
    confData.get(conf.key) match {
      case null =>
        SystemPropertyUtils.get(conf.key) match {
          case v if v != null => Converter.convert[T](v, conf.classType)
          case _ => conf.defaultValue.asInstanceOf[T]
        }
      case v: T => v
    }
  }

  /**
   * Get configuration value via key.
   *
   * When using this api, the type must be explicitly declared and the relevant type will be
   * automatically converted to some extent.
   * 1) in scala:
   * val result: Long = ConfigHub.get("streamx.flink-k8s.tracking.polling-task-timeout-sec.cluster-metric")
   * 2) in java:
   * Long result = ConfigHub.get("streamx.flink-k8s.tracking.polling-task-timeout-sec.cluster-metric");
   *
   * @throws IllegalArgumentException when the key has not been registered to ConfigHub.
   * @return return the defaultValue of ConfigOption when the value has not been overwritten.
   */
  @throws[IllegalArgumentException]
  @Nonnull
  def get[T](@Nonnull key: String): T = {
    confData.get(key) match {
      case null =>
        confOptions.get(key) match {
          case null =>
            val config = getConfig(key)
            SystemPropertyUtils.get(key) match {
              case v if v != null => Converter.convert[T](v, config.classType)
              case _ => throw new IllegalArgumentException(s"config key has not been registered: $key")
            }
          case conf: InternalOption => conf.defaultValue.asInstanceOf[T]
        }
      case v: T => v
    }
  }

  /**
   * Get registered ConfigOption by key.
   *
   * @return nullable
   */
  @Nullable
  def getConfig(key: String): InternalOption = {
    confOptions.get(key)
  }

  /**
   * Get keys of all registered ConfigOption.
   */
  @Nonnull
  def keys(): util.Set[String] = {
    val map = new util.HashMap[String, InternalOption](confOptions.size())
    map.putAll(confOptions)
    map.keySet()
  }

  /**
   * Overwritten configuration value.
   *
   * @param conf  should not be null.
   * @param value the type of value should be same as conf.classType.
   * @throws IllegalArgumentException when the conf has not been registered,
   *                                  or the value type is not same as conf.classType.
   */
  @throws[IllegalArgumentException]
  def set(@Nonnull conf: InternalOption, value: Any): Unit = {
    if (!confOptions.containsKey(conf.key)) {
      throw new IllegalArgumentException(s"config key has not been registered: $conf")
    }
    value match {
      case null => confData.remove(conf.key)
      case v if conf.classType != v.getClass =>
        throw new IllegalArgumentException(
          s"config value type is not match of ${conf.key}, required: ${conf.classType}, actual: ${v.getClass}")
      case v =>
        SystemPropertyUtils.set(conf.key, v.toString)
        confData.put(conf.key, v)
    }
  }

  /**
   * log the current configuration info.
   */
  def log(): Unit = {
    val configKeys = keys()
    logInfo(
      s"""registered configs:
         |ConfigHub collected configs: ${configKeys.size}
         |  ${configKeys.map(key => s"$key = ${if (key.contains("password")) "********" else get(key)}").mkString("\n  ")}""".stripMargin)
  }



}


object Converter {

  def convert[T](v: String, classType: Class[_]): T = {
    classType match {
      case c if c == classOf[String] => v.asInstanceOf[T]
      case c if c == classOf[Int] => v.toInt.asInstanceOf[T]
      case c if c == classOf[Long] => v.toLong.asInstanceOf[T]
      case c if c == classOf[Boolean] => v.toBoolean.asInstanceOf[T]
      case c if c == classOf[Float] => v.toFloat.asInstanceOf[T]
      case c if c == classOf[Double] => v.toDouble.asInstanceOf[T]
      case c if c == classOf[java.lang.String] => v.asInstanceOf[T]
      case c if c == classOf[java.lang.Integer] => java.lang.Integer.valueOf(v).asInstanceOf[T]
      case c if c == classOf[java.lang.Long] => java.lang.Long.valueOf(v).asInstanceOf[T]
      case c if c == classOf[java.lang.Boolean] => java.lang.Boolean.valueOf(v).asInstanceOf[T]
      case c if c == classOf[java.lang.Float] => java.lang.Float.valueOf(v).asInstanceOf[T]
      case c if c == classOf[java.lang.Double] => java.lang.Double.valueOf(v).asInstanceOf[T]
      case _ =>
        throw new IllegalArgumentException(s"Unsupported type: $classType")
    }
  }
}


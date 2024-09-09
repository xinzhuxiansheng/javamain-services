package com.yzhou.scala.configmanager.config

/**
 *
 * Internal use of the system
 *
 * @param key          key of configuration that consistent with the spring config.
 * @param defaultValue default value of configuration that <b>should not be null</b>.
 * @param classType    the class type of value. <b>please use java class type</b>.
 * @param description  description of configuration.
 * @author Al-assad
 */
case class InternalOption(key: String,
                          defaultValue: Any,
                          classType: Class[_],
                          description: String = "") {
  InternalConfigHolder.register(this)
}

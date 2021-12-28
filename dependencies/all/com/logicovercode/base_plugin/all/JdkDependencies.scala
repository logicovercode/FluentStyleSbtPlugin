package com.logicovercode.base_plugin.all

import com.logicovercode.base_plugin.Version
import sbt._
import sbt.librarymanagement.ModuleID

trait JdkDependencies {

  val gson_2_8_6 = Version("2.8.6")
  def gson(version: Version = gson_2_8_6): ModuleID = gson(version.version)
  @Deprecated
  def gson(version: String): ModuleID = "com.google.code.gson" % "gson" % version

  val jwtApi_0_11_2 = Version("0.11.2")
  def jjwt_api(version: Version = jwtApi_0_11_2): ModuleID = jjwt_api(version.version)
  @Deprecated
  def jjwt_api(version: String): ModuleID = "io.jsonwebtoken" % "jjwt-api" % version

  def jjwt_impl(version: Version = jwtApi_0_11_2): ModuleID = jjwt_impl(version.version)
  @Deprecated
  def jjwt_impl(version: String): ModuleID = "io.jsonwebtoken" % "jjwt-impl" % version

  def jjwt_jackson(version: Version = jwtApi_0_11_2): ModuleID = jjwt_jackson(version.version)
  @Deprecated
  def jjwt_jackson(version: String): ModuleID = "io.jsonwebtoken" % "jjwt-jackson" % version

  def jjwt_gson(version: Version = jwtApi_0_11_2): ModuleID = jjwt_gson(version.version)
  @Deprecated
  def jjwt_gson(version: String): ModuleID = "io.jsonwebtoken" % "jjwt-gson" % version

  val vavr_0_10_3 = Version("8.0.23")
  def vavr(version: Version = vavr_0_10_3): ModuleID = vavr(version.version)
  @Deprecated
  def vavr(version: String): ModuleID = "io.vavr" % "vavr" % "0.10.3"

  val mySqlConnector_8_0_23 = Version("8.0.23")
  def mysql_connector_java(version: Version = mySqlConnector_8_0_23): ModuleID = mysql_connector_java(version.version)
  @Deprecated
  def mysql_connector_java(version: String): ModuleID = "mysql" % "mysql-connector-java" % version
}

package com.logicovercode.base_plugin.all

import com.logicovercode.base_plugin.sboot.AllSpringDependencies
import com.logicovercode.base_plugin.spark.{SparkDependencies, SparkDeprecatedDeps}
import com.logicovercode.bsbt.module_id.JvmModuleID
import sbt._
import sbt.librarymanagement.ModuleID

trait AllDependencies
    extends SparkDeprecatedDeps
    with SparkDependencies
    with AllSpringDependencies
    with JdkDependencies
    with Jdk11Dependencies
    with ScalaDependencies
    with DockerDependencies{

  // Versions
  lazy val akkaVersion = "2.6.15"
  def akkaactor(version: String = akkaVersion): ModuleID = {
    "com.typesafe.akka" %% "akka-actor" % version
  }

  def akkacluster(version: String = akkaVersion): ModuleID = {
    "com.typesafe.akka" %% "akka-cluster" % version
  }

  def akkatest(version: String = akkaVersion): ModuleID = {
    "com.typesafe.akka" %% "akka-testkit" % version
  }

  def akkastream(version: String = akkaVersion): ModuleID = {
    "com.typesafe.akka" %% "akka-stream" % version
  }

  @Deprecated
  def akkaclient(version: String = "2.4.17"): ModuleID = {
    "com.typesafe.akka" %% "akka-cluster-tools" % version
  }

  @Deprecated
  def typesafeConfig(version: String = "1.3.4"): ModuleID = {
    "com.typesafe" % "config" % version
  }

  @Deprecated
  def logging(version: String = "default"): Seq[JvmModuleID] = {
    Seq(
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
      "ch.qos.logback" % "logback-classic" % "1.1.2"
    ).map(JvmModuleID(_, None))
  }

  @Deprecated
  def junit(version: String = "default"): ModuleID = {
    "junit" % "junit" % "4.12"
//    Seq("org.hamcrest" % "hamcrest-all" % "1.3",
//      "pl.pragmatists" % "JUnitParams" % "1.0.4",
//      "junit" % "junit" % "4.12")
  }

  @Deprecated
  def scalaz(version: String = "7.2.30"): ModuleID = {
    "org.scalaz" %% "scalaz-core" % version
  }

  @Deprecated
  def scalalib(version: String = "2.11.7"): ModuleID = {
    "org.scala-lang" % "scala-library" % version
  }

  @Deprecated
  def scalalib12(version: String = "2.12.2"): ModuleID = {
    "org.scala-lang" % "scala-library" % version
  }

  @Deprecated
  def mockito(version: String = "1.8.4"): ModuleID = {
    "org.mockito" % "mockito-all" % version
  }

  @Deprecated
  def easymock(version: String = "3.4"): ModuleID = {
    "org.easymock" % "easymock" % version
  }

  @Deprecated
  def scalamock(version: String = "3.6.0"): ModuleID = {
    "org.scalamock" %% "scalamock-scalatest-support" % version
  }

  @Deprecated
  def guava(version: String = "11.0.2"): ModuleID = {
    "com.google.guava" % "guava" % version
  }

  @Deprecated
  def guice(version: String = "3.0"): ModuleID = {
    "com.google.inject" % "guice" % version
  }

  @Deprecated
  def akkaconfig(version: String = "1.3.3"): ModuleID = {
    "com.typesafe" % "config" % "1.3.3"
  }
}

package com.logicovercode.base_plugin.all

import com.logicovercode.base_plugin.Version
import com.logicovercode.base_plugin.scala.{Http4sDependencies, TpolecatDoobieDependencies}
import sbt.{ModuleID, _}

trait ScalaDependencies extends TpolecatDoobieDependencies with Http4sDependencies {

  val catsCore_2_4_1 = Version("2.4.1")
  def cats_core(version: Version = catsCore_2_4_1): ModuleID = cats_core(version.version)
  @Deprecated
  def cats_core(version: String): ModuleID = "org.typelevel" %% "cats-core" % version

  val catsEffect_2_3_1 = Version("2.3.1")
  def cats_effect(version: Version = catsEffect_2_3_1): ModuleID = cats_effect(version.version)
  @Deprecated
  def cats_effect(version: String): ModuleID = "org.typelevel" %% "cats-effect" % version

  val scalajHttp_2_4_2 = Version("2.4.2")
  def scalaj_http(version: Version = scalajHttp_2_4_2): ModuleID = scalaj_http(version.version)
  @Deprecated
  def scalaj_http(version: String): ModuleID = "org.scalaj" %% "scalaj-http" % version

  val scalaTest_3_2_9 = Version("3.2.9")
  @Deprecated
  val scalaTest_3_2_7 = Version("3.2.7")
  def scala_test(version: Version = scalaTest_3_2_9): ModuleID = scalatest(version.version)
  @Deprecated
  def scalatest(version: Version = scalaTest_3_2_9): ModuleID = scalatest(version.version)
  @Deprecated
  def scalatest(version: String): ModuleID = "org.scalatest" %% "scalatest" % version

  val scalaTestFreeSpec_3_2_7 = Version("3.2.7")
  def scala_test_free_spec(version: Version = scalaTestFreeSpec_3_2_7): ModuleID = scala_test_free_spec(version.version)
  @Deprecated
  def scala_test_free_spec(version: String): ModuleID = "org.scalatest" %% "scalatest" % version

  val betterFiles_3_9_1 = Version("3.9.1")
  def better_files(version: Version = betterFiles_3_9_1): ModuleID = better_files(version.version)
  @Deprecated
  def better_files(version: String): ModuleID = "com.github.pathikrit" %% "better-files" % version

  val scalaJava8Compat_0_9_1 = Version("0.9.1")
  def scala_java8_compat(version: Version = scalaJava8Compat_0_9_1): ModuleID = scala_java8_compat(version.version)
  @Deprecated
  def scala_java8_compat(version: String): ModuleID = "org.scala-lang.modules" %% "scala-java8-compat" % version

  val jacksonModuleScala_2_12_1 = Version("2.13.0")
  def jackson_module_scala(version: Version = jacksonModuleScala_2_12_1): ModuleID = jackson_module_scala(version.version)
  @Deprecated
  def jackson_module_scala(version: String): ModuleID = "com.fasterxml.jackson.module" %% "jackson-module-scala" % version

  val scalaParallelCollections_1_0_0 = Version("1.0.0")
  def scala_parallel_collections(version: Version = scalaParallelCollections_1_0_0): ModuleID = scala_parallel_collections(version.version)
  @Deprecated
  def scala_parallel_collections(version: String): ModuleID = "org.scala-lang.modules" %% "scala-parallel-collections" % version

  val playJson_2_9_2 = Version("2.9.2")
  def play_json(version: Version = playJson_2_9_2): ModuleID = play_json(version.version)
  @Deprecated
  def play_json(version: String): ModuleID = "com.typesafe.play" %% "play-json" % version

  val logbackClassic_1_2_3 = Version("1.2.3")
  def logback_classic(version: Version = logbackClassic_1_2_3): ModuleID = logback_classic(version.version)
  @Deprecated
  def logback_classic(version: String): ModuleID = "ch.qos.logback" % "logback-classic" % version

  val bce6_6_0 = Version("6.0")
  def bcel(version: Version = bce6_6_0): ModuleID = bcel(version.version)
  @Deprecated
  def bcel(version: String): ModuleID = "org.apache.bcel" % "bcel" % version
}

package com.logicovercode.base_plugin.all

import com.logicovercode.base_plugin.Version
import sbt._
import sbt.librarymanagement.ModuleID

trait DockerDependencies {

  val testContainers_1_15_2 = Version("1.15.2")
  def jdbc_test_containers(version: Version = testContainers_1_15_2): ModuleID = jdbc_test_containers(version.version)
  @Deprecated
  def jdbc_test_containers(version: String): ModuleID = "org.testcontainers" % "jdbc" % version

  val jdbcTestContainers_1_15_2 = testContainers_1_15_2
  def test_containers(version: Version = jdbcTestContainers_1_15_2): ModuleID = test_containers(version.version)
  @Deprecated
  def test_containers(version: String): ModuleID = "org.testcontainers" % "testcontainers" % version

  val dockerCore_Latest = Version("0.0.004")
  def docker_core(version: Version = dockerCore_Latest): ModuleID = docker_core(version.version)
  @Deprecated
  def docker_core(version: String): ModuleID = "com.logicovercode" %% "docker-core" % version

  val dockerDefinitions_Latest = Version("0.0.004")
  def docker_definitions(version: Version = dockerDefinitions_Latest): ModuleID = docker_definitions(version.version)
  @Deprecated
  def docker_definitions(version: String): ModuleID = "com.logicovercode" %% "docker-definitions" % version
}

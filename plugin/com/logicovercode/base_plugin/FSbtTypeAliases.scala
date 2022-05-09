package com.logicovercode.base_plugin

import com.logicovercode.bsbt.java_module.JavaBuild
import com.logicovercode.bsbt.scala_module.ScalaBuild
import com.logicovercode.wdocker.ContainerDefinition
import sbt._

trait FSbtTypeAliases {

  @Deprecated
  val SbtBuild = ScalaBuild
  //type SbtBuild = ScalaBuildSettings

  val JBuild = JavaBuild

  val SBuild = ScalaBuild

  val DockerContainer = ContainerDefinition
  val MicroService = com.logicovercode.fsbt.commons.MicroService
  //type SBuild = ScalaBuildSettings

}
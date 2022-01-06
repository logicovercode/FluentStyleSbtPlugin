package com.logicovercode.base_plugin

import com.logicovercode.bsbt.java_module.JavaBuild
import com.logicovercode.bsbt.scala_module.ScalaBuild
import sbt._

trait FSbtTypeAliases {

  @Deprecated
  val SbtBuild = ScalaBuild
  //type SbtBuild = ScalaBuildSettings

  val JBuild = JavaBuild

  val SBuild = ScalaBuild
  //type SBuild = ScalaBuildSettings

  def fsbt_commons(): ModuleID = {
    "com.logicovercode" %% "fsbt-commons" % "0.0.001"
  }

  def docker_definitions(): ModuleID = {
    "com.logicovercode" %% "docker-definitions" % "0.0.004"
  }
}
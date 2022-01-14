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

}
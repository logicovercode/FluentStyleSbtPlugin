package com.logicovercode.base_plugin.scala

import com.logicovercode.base_plugin.Version
import sbt.librarymanagement.ModuleID
import sbt._

trait Http4sDependencies {


  val http4s_0_21_16 = Version("0.21.16")

  case class Http4sDependencies(version: Version = http4s_0_21_16) {
    def http4s_dsl(): ModuleID = "org.http4s" %% "http4s-dsl" % version.version
    def http4s_blaze_server(): ModuleID = "org.http4s"      %% "http4s-blaze-server" % version.version
  }
}

trait TpolecatDoobieDependencies{

  final val doobie_0_12_1 = Version("0.12.1")

  case class DoobieDependencies(version: Version = doobie_0_12_1) {
    def core(): ModuleID = {
      "org.tpolecat" %% "doobie-core" % version.version
    }

    def specs2(): ModuleID = {
      "org.tpolecat" %% "doobie-specs2" % version.version
    }

    def postgres(): ModuleID = {
      "org.tpolecat" %% "doobie-postgres" % version.version
    }
  }
}
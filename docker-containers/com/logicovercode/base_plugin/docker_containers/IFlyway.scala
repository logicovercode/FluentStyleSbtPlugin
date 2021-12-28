package com.logicovercode.base_plugin.docker_containers

import io.github.davidmweber.FlywayPlugin.autoImport.{flywayBaselineVersion, _}
import sbt.Def

trait IFlyway {

  protected def flyWaySettings(
      _flywayUrl: String = "",
      _flywayUser: String = "",
      _flywayPassword: String,
      _flywayLocations: Seq[String]
  ): Set[Def.Setting[_]] = {
    Set(
      flywayUrl := _flywayUrl,
      flywayUser := _flywayUser,
      flywayPassword := _flywayPassword,
      flywayLocations := _flywayLocations,

      // Necessary for initializing metadata table
      flywayBaselineOnMigrate := true,
      flywayBaselineVersion := "0"
    )
  }
}

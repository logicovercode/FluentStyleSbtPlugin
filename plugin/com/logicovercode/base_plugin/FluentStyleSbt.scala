package com.logicovercode.base_plugin

import com.logicovercode.base_plugin.all.AllDependencies
import com.logicovercode.base_plugin.docker_containers.{MySqlSettings, PostgresSettings, SshClusterSettings}
import com.logicovercode.base_plugin.licenses.Licenses
import com.logicovercode.base_plugin.proto.ProtoSettings
import com.logicovercode.base_plugin.resolvers.SbtResolvers
import com.logicovercode.base_plugin.spark.SparkDeprecatedSettings
import com.logicovercode.bsbt.BuilderStyleBuild
import sbt.{AutoPlugin, PluginTrigger, Plugins}

object FluentStyleSbt extends AutoPlugin {

  object autoImport
      extends SparkDeprecatedSettings
      with AllDependencies
      with SbtResolvers
      with Licenses
      with MySqlSettings
      with PostgresSettings
      with SshClusterSettings
      with ProtoSettings
      with ModuleBuildExtSettings

  override def trigger: PluginTrigger = Plugins.noTrigger

  override def requires: Plugins = BuilderStyleBuild

  override lazy val projectSettings = super.projectSettings
}

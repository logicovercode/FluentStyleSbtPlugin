package com.logicovercode.base_plugin

import com.logicovercode.bsbt.BuilderStyleBuild
import com.logicovercode.fsbt.commons.dependencies.AllDependencies
import com.logicovercode.fsbt.commons.licenses.LicenseProvider
import com.logicovercode.fsbt.commons.resolvers.ResolverProvider
import com.logicovercode.fsbt.commons.services.{ClusterServicesProvider, MySqlServiceProvider, PostgresSqlServiceProvider}
import sbt.{AutoPlugin, PluginTrigger, Plugins}

object FluentStyleSbt extends AutoPlugin {

  object autoImport
    extends AllDependencies
      with MySqlServiceProvider
      with PostgresSqlServiceProvider
      with ClusterServicesProvider
      with LicenseProvider
      with ResolverProvider
      //with ProtoSettings
      with FSbtTypeAliases

  override def trigger: PluginTrigger = Plugins.noTrigger

  override def requires: Plugins = BuilderStyleBuild

  override lazy val projectSettings = super.projectSettings
}

package com.logicovercode.base_plugin.proto

import sbt._
import sbtprotoc.ProtocPlugin.autoImport.PB

trait ProtoBridgeSettings {

  def protoSourceDirectorySettings(sourceDirs: String*): Set[Def.Setting[_]] = {
    val protoSourceDirectories = sourceDirs.map(new File(_))

    val _settings: Seq[Def.Setting[_]] = Seq(
      Compile / PB.protoSources := protoSourceDirectories
    )
    _settings.toSet
  }

  def protoJavaTargetDirSettings(protoBufferCodeGenDir: String, grpcCodeGenDir: Option[String]): Set[Def.Setting[_]] = {

    val _settings: Seq[Def.Setting[_]] = Seq(
      Compile / PB.targets := (grpcCodeGenDir match {
        case Some(grpcDir) => Seq(PB.gens.java -> new File(protoBufferCodeGenDir), PB.gens.plugin("grpc-java") -> new File(grpcDir))
        case None          => Seq(PB.gens.java -> new File(protoBufferCodeGenDir))
      })
    )
    _settings.toSet
  }

  def protoScalaTargetDirSettings(codeGenDir: String, generateGrpcCode: Boolean): Set[Def.Setting[_]] = {
    val _settings: Seq[Def.Setting[_]] = Seq(
      Compile / PB.targets := Seq(
        scalapb.gen(grpc = generateGrpcCode) -> new File(codeGenDir)
      )
    )
    _settings.toSet
  }
}

package com.logicovercode.base_plugin.proto

import com.logicovercode.base_plugin.Version
import com.logicovercode.bsbt.build.Build
import com.logicovercode.bsbt.java_module.JavaBuild
import com.logicovercode.bsbt.scala_module.ScalaBuild
import sbt.{Def, _}
import sbtprotoc.ProtocPlugin.autoImport.AsProtocPlugin

trait ProtoSettings {

  val grpcVersion_1_36_0 = Version("1.36.0")

  case class GrpcDependencies(version: Version = grpcVersion_1_36_0) {
    def protoGenGrpcJavaAsProtocPlugin(): ModuleID = {
      ("io.grpc" % "protoc-gen-grpc-java" % version.version) asProtocPlugin ()
    }

    def grpcAll(): ModuleID = {
      "io.grpc" % "grpc-all" % version.version
    }
  }

  trait BuildProtoSettings[T <: Build[T]] extends  ProtoBridgeSettings {
    def protoJavaTargetDir(protoBufferCodeGenDir: String): T

    def protoJavaTargetDir(protoBufferCodeGenDir: String, grpcCodeGenDir: String): T

    def protoSourceDirectories(sourceDirs: String*): T

    def protoScalaTargetDir(codeGenDir: String, generateGrpcCode: Boolean = false): T
  }

  implicit class JavaBuildSettingsExtension(javaBuild: JavaBuild) extends BuildProtoSettings[JavaBuild] with ProtoBridgeSettings {
    override def protoJavaTargetDir(protoBufferCodeGenDir: String): JavaBuild = {
      val _settings = protoJavaTargetDirSettings(protoBufferCodeGenDir, None)
      JavaBuild(javaBuild.sbtSettings ++ _settings)
    }

    override def protoJavaTargetDir(protoBufferCodeGenDir: String, grpcCodeGenDir: String): JavaBuild = {
      val _settings = protoJavaTargetDirSettings(protoBufferCodeGenDir, Option(grpcCodeGenDir))
      JavaBuild(javaBuild.sbtSettings ++ _settings)
    }

    override def protoSourceDirectories(sourceDirs: String*): JavaBuild = {
      val _settings = protoSourceDirectorySettings(sourceDirs: _*)
      JavaBuild(javaBuild.sbtSettings ++ _settings)
    }

    override def protoScalaTargetDir(codeGenDir: String, generateGrpcCode: Boolean): JavaBuild = {
      val _settings = protoScalaTargetDirSettings(codeGenDir, generateGrpcCode)
      JavaBuild(javaBuild.sbtSettings ++ _settings)
    }
  }

  implicit class ScalaBuildSettingsExtension(scalaBuild: ScalaBuild) extends BuildProtoSettings[ScalaBuild] {
    override def protoJavaTargetDir(protoBufferCodeGenDir: String): ScalaBuild = {
      val _settings = protoJavaTargetDirSettings(protoBufferCodeGenDir, None)
      ScalaBuild(scalaBuild.sbtSettings ++ _settings)
    }

    override def protoJavaTargetDir(protoBufferCodeGenDir: String, grpcCodeGenDir: String): ScalaBuild = {
      val _settings = protoJavaTargetDirSettings(protoBufferCodeGenDir, Option(grpcCodeGenDir))
      ScalaBuild(scalaBuild.sbtSettings ++ _settings)
    }

    override def protoSourceDirectories(sourceDirs: String*): ScalaBuild = {
      val _settings = protoSourceDirectorySettings(sourceDirs: _*)
      ScalaBuild(scalaBuild.sbtSettings ++ _settings)
    }

    override def protoScalaTargetDir(codeGenDir: String, generateGrpcCode: Boolean): ScalaBuild = {
      val _settings = protoScalaTargetDirSettings(codeGenDir, generateGrpcCode)
      ScalaBuild(scalaBuild.sbtSettings ++ _settings)
    }
  }
}

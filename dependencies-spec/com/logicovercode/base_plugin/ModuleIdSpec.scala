package com.logicovercode.base_plugin

import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers
import sbt._

class ModuleIdSpec extends AnyFlatSpecLike with Matchers {

  "configurations" should "be None when no scope is passed" in {
    val scala_test = "org.scalatest" %% "scalatest" % "3.2.7"
    scala_test.configurations should be(None)
  }

  it should "be Some(Test) when Test scope is passed" in {
    val scala_test = "org.scalatest" %% "scalatest" % "3.2.7" % Test
    val ref = scala_test.configurations
    scala_test.configurations should be( Some("test") )
  }

  it can "not be overridden on moduleId having existing configuration" in {
    val scalaTestInProvidedScope = "org.scalatest" %% "scalatest" % "3.2.7" % Provided
    scalaTestInProvidedScope.configurations should be( Some("provided") )

    intercept[IllegalArgumentException]( scalaTestInProvidedScope % Compile )
  }

  it can "be overridden on moduleId having None configurations" in {
    val scalaTest = "org.scalatest" %% "scalatest" % "3.2.7"
    scalaTest.configurations should be( None )

    val newScalaTest = scalaTest % Compile

    newScalaTest.configurations should be( Option("compile") )
  }
}

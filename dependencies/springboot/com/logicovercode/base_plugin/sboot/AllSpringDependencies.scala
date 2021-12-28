package com.logicovercode.base_plugin.sboot

import sbt.librarymanagement.ModuleID
import sbt._

trait AllSpringDependencies {

  case class SpringBootVersion(value: String)

  def springCore(version: String = "4.3.2.RELEASE"): ModuleID = {
    "org.springframework" % "spring-context" % version
  }

  val springBoot_2_3_0 = SpringBootVersion("2.3.0.RELEASE")

  @Deprecated
  val springBootVersion_2_3_0 = SpringBootVersion("2.3.0.RELEASE")

  @Deprecated
  val springBootVersion_2_2_5 = SpringBootVersion("2.2.5.RELEASE")

  case class SpringBootDependencies(releaseTag: SpringBootVersion) {

    def starterParent(): ModuleID = {
      "org.springframework.boot" % "spring-boot-starter-parent" % releaseTag.value pomOnly ()
    }

    def starterWeb(): ModuleID = {
      "org.springframework.boot" % "spring-boot-starter-web" % releaseTag.value
    }

    def starterDataJpa(): ModuleID = {
      "org.springframework.boot" % "spring-boot-starter-data-jpa" % releaseTag.value
    }

    def starterSecurity(): ModuleID = {
      "org.springframework.boot" % "spring-boot-starter-security" % releaseTag.value
    }

    def starterJdbc(): ModuleID = {
      "org.springframework.boot" % "spring-boot-starter-jdbc" % releaseTag.value
    }

    def starterTest(): ModuleID = {
      "org.springframework.boot" % "spring-boot-starter-test" % releaseTag.value
    }

    def postgres(): ModuleID = {
      "org.postgresql" % "postgresql" % "42.2.12"
    }

    def flywayCore(): ModuleID = {
      "org.flywaydb" % "flyway-core" % "6.4.1"
    }
  }

  val springCloudGreenWichRelease = "Greenwich.RELEASE"
  val latestSpriingCloudReleaseTag = springCloudGreenWichRelease

  case class SpringCloud(
      releaseTag: SpringBootVersion = springBootVersion_2_2_5
  ) {

    val versionsMap = Map("Greenwich.RELEASE" -> "2.1.0.RELEASE")

    def dependencies(): ModuleID = {
      "org.springframework.cloud" % "spring-cloud-dependencies" % releaseTag.value pomOnly ()
    }

    def starterOAuth2(): ModuleID = {
      "org.springframework.cloud" % "spring-cloud-starter-oauth2" % versionsMap(
        releaseTag.value
      )
    }

    def starterSecurity(): ModuleID = {
      "org.springframework.cloud" % "spring-cloud-starter-security" % versionsMap(
        releaseTag.value
      )
    }
  }
}

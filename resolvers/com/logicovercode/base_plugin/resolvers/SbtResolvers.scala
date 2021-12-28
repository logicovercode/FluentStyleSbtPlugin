package com.logicovercode.base_plugin.resolvers

import sbt._

trait SbtResolvers {

  val typesafe =
    "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  val cloudera =
    "Cloudera Rel Repository" at "https://repository.cloudera.com/content/repositories/releases/"
  val maven = "Maven Repository" at "https://repo1.maven.org/maven2"
  val springMileStones =
    "Spring Milestones" at "https://repo.spring.io/milestone/"
}

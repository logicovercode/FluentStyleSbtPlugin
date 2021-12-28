import org.apache.ivy.core.module.descriptor.License
import com.logicovercode.bsbt.scala_module.ScalaBuild

sbtPlugin := true

val license = new License("MIT", "https://opensource.org/licenses/MIT")

/*this will automatically fetch dependency-tree for sbt projects that depends on LogicAndCode*/
addDependencyTreePlugin

val techLead = Developer(
  "techLead",
  "techLead",
  "techlead@logicovercode.com",
  url("https://github.com/logicovercode")
)
val githubRepo = GithubRepo("logicovercode", "FluentStyleSbtPlugin")

val moduleBuild = ScalaBuild("com.logicovercode", "fluent-style-sbt", "0.0.521")
  .sourceDirectories(
    "dependencies/model",
    "dependencies/spark",
    "dependencies/spark_deprecated",
    "dependencies/springboot",
    "dependencies/all",
    "licenses",
    "resolvers",
    "plugin",
    "docker-containers",
    "proto-grpc-support"
  )
  //TODO : this dependency is for docker (make this dependency conditional, depending on jdk version)
  .dependencies(
    "javax.activation" % "activation" % "1.1.1",
    "com.logicovercode" %% "docker-definitions" % "0.0.002"
  )
  .sbtPlugins(
    "com.logicovercode" %% "fluent-style-sbt-core" % "0.0.420",
    /*this will automatically fetch flyway-sbt, sbt-pack for sbt projects that depends on fluent-style-sbt*/
    "io.github.davidmweber" % "flyway-sbt" % "6.5.0",
    "org.xerial.sbt" % "sbt-pack" % "0.13",
//    "org.scalameta" % "sbt-scalafmt" % "2.4.0",
//    "com.github.cb372" % "sbt-explicit-dependencies" % "0.2.16"
  )
  .sbtPlugins("com.thesamet" % "sbt-protoc" % "1.0.0")
  .dependencies("com.thesamet.scalapb" %% "compilerplugin" % "0.9.0")
  .testDependencies( "org.scalatest" %% "scalatest" % "3.2.7" )
  .testSourceDirectories("dependencies-spec", "docker-containers-spec")
  .argsRequiredForPublishing(
    List(techLead),
    license,
    githubRepo.homePageUrl,
    githubRepo.scmInfo(),
    Opts.resolver.sonatypeStaging
  )

val basePluginProject = (project in file("."))
  .settings( moduleBuild.settings )


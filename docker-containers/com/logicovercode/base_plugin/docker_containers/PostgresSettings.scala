package com.logicovercode.base_plugin.docker_containers

import com.logicovercode.bsbt.docker.service.SbtServiceDescription
import com.logicovercode.wdocker.api.DockerContext
import com.logicovercode.wdocker.{ContainerDefinition, DockerNetwork, DockerReadyChecker}
import sbt.Def

import scala.concurrent.duration.DurationInt

trait PostgresSettings  {

  object PostgresService extends IFlyway {

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        dbInitDirectory: String
    ): SbtServiceDescription = {

      postgresSbtServiceDescription(containerName, databaseName, userName, dbPassword, 5432, DockerNetwork("bridge"), Option(dbInitDirectory), 5, 5)
    }

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        hostPort: Int,
        dbInitDirectory: String
    ): SbtServiceDescription = {

      postgresSbtServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, DockerNetwork("bridge"), Option(dbInitDirectory), 5, 5)
    }

    def apply(containerName: String, databaseName: String, userName: String, dbPassword: String): SbtServiceDescription = {

      postgresSbtServiceDescription(containerName, databaseName, userName, dbPassword, 5432, DockerNetwork("bridge"), None, 5, 5)
    }

    def apply(containerName: String, databaseName: String, userName: String, dbPassword: String, hostPort: Int): SbtServiceDescription = {

      postgresSbtServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, DockerNetwork("bridge"), None, 5, 5)
    }

    def apply(containerName: String, databaseName: String, userName: String, dbPassword: String, hostPort: Int, network : DockerNetwork): SbtServiceDescription = {

      postgresSbtServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, network, None, 5, 5)
    }

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        hostPort: Int,
        network: DockerNetwork,
        dbIntDir: Option[String],
        imagePullTimeoutInMinutes: Int,
        containerStartTimeoutInMinutes: Int
    ): SbtServiceDescription = {

      postgresSbtServiceDescription(
        containerName,
        databaseName,
        userName,
        dbPassword,
        hostPort,
        network,
        dbIntDir,
        imagePullTimeoutInMinutes,
        containerStartTimeoutInMinutes
      )
    }

    private def postgresSbtServiceDescription(
        containerName: String,
        databaseName: String,
        dbUserName: String,
        dbPassword: String,
        hostPort: Int,
        network: DockerNetwork,
        dbInitDirectory: Option[String],
        imagePullTimeoutInMinutes: Int,
        containerStartTimeoutInMinutes: Int
    ): SbtServiceDescription = {
      val imageName: String = "postgres:latest"
      val dbContainerPort = 5432

      val dbContainer = ContainerDefinition(None, "postgres", "latest", Option(containerName))
        .withEnv(
          s"POSTGRES_DB=$databaseName",
          s"POSTGRES_USER=$dbUserName",
          s"POSTGRES_PASSWORD=$dbPassword"
        )
        .withPorts(dbContainerPort -> Some(hostPort))
        .withNetworkMode(network)
        .withReadyChecker(
          DockerReadyChecker.LogLineContains(
            s"database system is ready to accept connections"
          )
        )

      val flywaySettings = dbInitDirectory match {

        case Some(dir) => {
          val host = "localhost"
          val url = s"jdbc:postgresql://${host}:$hostPort/$databaseName"
          flyWaySettings(
            url,
            dbUserName,
            dbPassword,
            Seq(s"filesystem:$dir")
          )
        }
        case None => Set()
      }

      SbtServiceDescription(dbContainer, flywaySettings.toSet, imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
    }
  }

  case class PostgresFlyway(databaseName: String,
                            dbUserName: String,
                            dbPassword: String,
                            hostPort: Int, dbInitDirectory : String) extends IFlyway{

    def settings : Set[Def.Setting[_]] = {
      val host = "localhost"
      val url = s"jdbc:postgresql://${host}:$hostPort/$databaseName"

      flyWaySettings(url,dbUserName,
        dbPassword,
        Seq(s"filesystem:$dbInitDirectory"))
    }
  }
}

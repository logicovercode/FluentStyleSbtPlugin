package com.logicovercode.base_plugin.docker_containers

import com.logicovercode.bsbt.docker.model.{DockerInfra, ServiceDescription}
import com.logicovercode.wdocker.{ContainerDefinition, DockerNetwork, DockerReadyChecker}
import sbt.Def

import scala.concurrent.duration.DurationInt

trait PostgresSettings extends DockerInfra  {

  object PostgresService extends IFlyway {

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        dbInitDirectory: String
    ): ServiceDescription = {

      postgresServiceDescription(containerName, databaseName, userName, dbPassword, 5432, DockerNetwork("bridge"), Option(dbInitDirectory), 5, 5)
    }

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        hostPort: Int,
        dbInitDirectory: String
    ): ServiceDescription = {

      postgresServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, DockerNetwork("bridge"), Option(dbInitDirectory), 5, 5)
    }

    def apply(containerName: String, databaseName: String, userName: String, dbPassword: String): ServiceDescription = {

      postgresServiceDescription(containerName, databaseName, userName, dbPassword, 5432, DockerNetwork("bridge"), None, 5, 5)
    }

    def apply(containerName: String, databaseName: String, userName: String, dbPassword: String, hostPort: Int): ServiceDescription = {

      postgresServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, DockerNetwork("bridge"), None, 5, 5)
    }

    def apply(containerName: String, databaseName: String, userName: String, dbPassword: String, hostPort: Int, network : DockerNetwork): ServiceDescription = {

      postgresServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, network, None, 5, 5)
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
    ): ServiceDescription = {

      postgresServiceDescription(
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

    private def postgresServiceDescription(
        containerName: String,
        databaseName: String,
        dbUserName: String,
        dbPassword: String,
        hostPort: Int,
        network: DockerNetwork,
        dbInitDirectory: Option[String],
        imagePullTimeoutInMinutes: Int,
        containerStartTimeoutInMinutes: Int
    ): ServiceDescription = {
      val imageName: String = "postgres:latest"
      val dbContainerPort = 5432

      val dbContainer = ContainerDefinition(imageName, Option(containerName))
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

      ServiceDescription(dbContainer, flywaySettings.toSet, imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
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

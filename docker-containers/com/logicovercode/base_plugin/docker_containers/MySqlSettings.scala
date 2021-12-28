package com.logicovercode.base_plugin.docker_containers

import com.logicovercode.docker.db.MySqlDbDefinition
import com.logicovercode.bsbt.docker.model.{DockerInfra, ServiceDescription}
import com.logicovercode.wdocker.DockerNetwork
import sbt.Def

import scala.concurrent.duration.DurationInt

trait MySqlSettings extends DockerInfra  {

  object MySqlService extends IFlyway {

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        dbInitDirectory: String
    ): ServiceDescription = {

      mysqlServiceDescription(containerName, databaseName, userName, dbPassword, 3306, DockerNetwork("bridge"), Option(dbInitDirectory), 5, 5)
    }

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        hostPort: Int,
        dbInitDirectory: String
    ): ServiceDescription = {

      mysqlServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, DockerNetwork("bridge"), Option(dbInitDirectory), 5, 5)
    }

    def apply(containerName: String, databaseName: String, userName: String, dbPassword: String): ServiceDescription = {

      mysqlServiceDescription(containerName, databaseName, userName, dbPassword, 3306, DockerNetwork("bridge"), None, 5, 5)
    }

    def apply(containerName: String, databaseName: String, userName: String, dbPassword: String, hostPort: Int): ServiceDescription = {

      mysqlServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, DockerNetwork("bridge"), None, 5, 5)
    }

    def apply(
        containerName: String,
        databaseName: String,
        userName: String,
        dbPassword: String,
        hostPort: Int,
        network: DockerNetwork
    ): ServiceDescription = {

      mysqlServiceDescription(containerName, databaseName, userName, dbPassword, hostPort, network, None, 5, 5)
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

      mysqlServiceDescription(
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

    private def mysqlServiceDescription(
        containerName: String,
        databaseName: String,
        dbUserName: String,
        dbPassword: String,
        hostPort: Int,
        sqlDockerNetwork: DockerNetwork,
        dbInitDirectory: Option[String],
        imagePullTimeoutInMinutes: Int,
        containerStartTimeoutInMinutes: Int
    ): ServiceDescription = {

      val rootUserPassword = "RootPass@123"

      val mysqlContainer = MySqlDbDefinition(
        containerName, databaseName, rootUserPassword,
        dbUserName, dbPassword,
        hostPort, 3306,
        sqlDockerNetwork
      )

      val flywaySettings = dbInitDirectory match {

        case Some(dir) => {
          val host = "localhost"
          val url = s"jdbc:mysql://${host}:$hostPort/$databaseName"
          flyWaySettings(
            url,
            dbUserName,
            dbPassword,
            Seq(s"filesystem:$dir")
          )
        }
        case None => Set()
      }

      ServiceDescription(mysqlContainer, flywaySettings.toSet, imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
    }
  }

  case class MySqlFlyway(databaseName: String, dbUserName: String, dbPassword: String, hostPort: Int, dbInitDirectory: String)
      extends IFlyway {

    def settings: Set[Def.Setting[_]] = {
      val host = "localhost"
      val url = s"jdbc:mysql://${host}:$hostPort/$databaseName"

      flyWaySettings(url, dbUserName, dbPassword, Seq(s"filesystem:$dbInitDirectory"))
    }
  }
}

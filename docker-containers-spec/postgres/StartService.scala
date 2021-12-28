package postgres

import com.logicovercode.bsbt.docker.DockerSystem
import com.logicovercode.bsbt.docker.model.{DockerInfra, MicroService}
import com.logicovercode.bsbt.docker.utils.DockerServiceOperations

import scala.util.{Failure, Success, Try}

object StartService extends PostgresSettings with DockerInfra{

  def main(args: Array[String]): Unit = {

    println("start of main")

    val postgresService = PostgresService("challenge-db", "app", "postgres", "pass", 5432)

    val service = MicroService( Seq(postgresService) )

    val allNetworks = (for {
      containerDefinition <- service.serviceDescriptions
      network = containerDefinition.container.networkMode
    } yield network).flatten

    implicit val dockerClient = DockerInfra.dockerClient

    val status = for{
      nets <- Try(allNetworks)
      createTries = nets.map(n => DockerSystem.createNonExistingNetwork(n))
      createTry <- Try( createTries.map(_.get) )
    } yield (createTry)

    status match {
      case Success(s) => println(s)
      case Failure(ex) => ex.printStackTrace()
    }

    DockerServiceOperations.startService(service)

    println("end of main")
  }
}

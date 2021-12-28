package hive

import com.logicovercode.bsbt.docker.DockerSystem
import com.logicovercode.bsbt.docker.model.DockerInfra
import com.logicovercode.bsbt.docker.utils.DockerServiceOperations
import com.logicovercode.wdocker.DockerNetwork

import scala.util.{Failure, Success, Try}

object StartService extends SshClusterSettings with DockerInfra{

  val cluster = {

    val hnet = DockerNetwork("hnet", Option("192.168.33.0/16"))

    ClusterBuilder(hnet).addMaster().buildHiveCluster()

  }


  def main(args: Array[String]): Unit = {

    println("start of main")

    val service = CreateHiveClusterService.hiveClusterService(cluster, 5, 5)

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

    val sqlService =

    DockerServiceOperations.startService(service)
    println("end of main")
  }
}

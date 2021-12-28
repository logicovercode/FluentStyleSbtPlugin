package com.logicovercode.base_plugin.docker_containers

import com.logicovercode.wdocker.DockerNetwork

trait ImplicitDockerConversions {


  implicit class StringDockerExtension(networkForThisContainer : String){
    def network = DockerNetwork(networkForThisContainer)
  }
}

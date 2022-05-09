package com.logicovercode.base_plugin

import com.logicovercode.docker.cluster._
import com.logicovercode.fsbt.commons.SbtService
import com.logicovercode.fsbt.commons.services.MicroServicesProvider

trait MicroServicesHandler extends MicroServicesProvider{
  implicit class ClusterExtension(cluster: Cluster) {

    def configurableAttributes(imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int): SbtService = {
      cluster.clusterType match {
        case SshCluster => CreateSshMicroService.sshMicroService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case RootSshCluster =>
          CreateRootSshMicroService.rootSshMicroService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case HdfsCluster => CreateHdfsMicroService.hdfsMicroService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case HiveCluster => CreateHiveMicroService.hiveMicroService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case SparkCluster =>
          CreateSparkMicroService.sparkMicroService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case KafkaCluster =>
          CreateKafkaMicroService.kafkaMicroService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
      }
    }
  }
}

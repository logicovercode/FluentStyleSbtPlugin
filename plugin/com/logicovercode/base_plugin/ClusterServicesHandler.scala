package com.logicovercode.base_plugin

import com.logicovercode.docker.cluster.{Cluster, HdfsCluster, HiveCluster, KafkaCluster, RootSshCluster, SparkCluster, SshCluster}
import com.logicovercode.fsbt.commons.SbtMicroservice
import com.logicovercode.fsbt.commons.services.ClusterServicesProvider

trait ClusterServicesHandler extends ClusterServicesProvider{
  implicit class ClusterExtension(cluster: Cluster) {

    def configurableAttributes(imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int): SbtMicroservice = {
      cluster.clusterType match {
        case SshCluster => CreateSshClusterService.sshClusterService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case RootSshCluster =>
          CreateRootSshClusterService.rootSshClusterService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case HdfsCluster => CreateHdfsClusterService.hdfsClusterService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case HiveCluster => CreateHiveClusterService.hiveClusterService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case SparkCluster =>
          CreateSparkClusterService.sparkClusterService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case KafkaCluster =>
          CreateKafkaClusterService.kafkaClusterService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
      }
    }
  }
}

package com.logicovercode.base_plugin.docker_containers

import com.github.dockerjava.api.model.Capability
import com.logicovercode.bsbt.docker.service.{MicroService, SbtServiceDescription}
import com.logicovercode.docker.cluster._
import com.logicovercode.docker.db.MySqlDbDefinition
import com.logicovercode.docker.hdfs.ClusterNodeHdfsExtension
import com.logicovercode.docker.hive.ClusterNodeHiveExtension
import com.logicovercode.docker.kafka.ClusterNodeKafkaExtension
import com.logicovercode.docker.spark.ClusterNodeSparkExtension
import com.logicovercode.docker.ssh.{ClusterNodeRootSshExtension, ClusterNodeSshExtension}
import com.logicovercode.wdocker.HostConfig

import scala.concurrent.duration.DurationInt

trait SshClusterSettings extends ClusterBuilderDefinitions{

  object CreateRootSshClusterService extends ClusterNodeRootSshExtension{
    def rootSshClusterService(cluster: Cluster, imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int) : MicroService = {

      val masterNodeDescription = SbtServiceDescription(cluster.masterNode.sshNodeDefinition( Option("root") ), Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)

      val slaveDescriptionSet = cluster.workerNodes.map { slaveNode =>
        SbtServiceDescription(slaveNode.sshNodeDefinition( Option("root") ), Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      }

      MicroService( Seq(masterNodeDescription) ++ slaveDescriptionSet )
    }
  }

  object CreateSshClusterService extends ClusterNodeSshExtension{
    def sshClusterService(cluster: Cluster, imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int) : MicroService = {

      val masterNodeDescription = SbtServiceDescription(cluster.masterNode.sshNodeDefinition( Option("hduser") ), Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)

      val slaveDescriptionSet = cluster.workerNodes.map { slaveNode =>
        SbtServiceDescription(slaveNode.sshNodeDefinition( Option("hduser") ), Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      }

      MicroService( Seq(masterNodeDescription) ++ slaveDescriptionSet )
    }
  }

  object CreateKafkaClusterService extends ClusterNodeKafkaExtension{
    def kafkaClusterService(cluster: Cluster, imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int) : MicroService = {

      val masterNodeDescription = SbtServiceDescription(cluster.masterNode.kafkaNodeDefinition( Option("kuser") ), Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)

      val slaveDescriptionSet = cluster.workerNodes.map { slaveNode =>
        SbtServiceDescription(slaveNode.kafkaNodeDefinition( Option("hduser") ), Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      }

      MicroService( Seq(masterNodeDescription) ++ slaveDescriptionSet )
    }
  }

  object CreateHdfsClusterService extends ClusterNodeHdfsExtension{
    def hdfsClusterService(cluster: Cluster, imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int) : MicroService = {

      var dataNodeHttpPort = 6661
      val masterNodeDescription = SbtServiceDescription(cluster.masterNode.hdfsMasterNodeDefinition( Option(dataNodeHttpPort), Option("hduser") ), Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)

      val slaveDescriptionSet = cluster.workerNodes.map { slaveNode =>
        dataNodeHttpPort = dataNodeHttpPort + 1
        SbtServiceDescription(slaveNode.hdfsSlaveNodeDefinition( Option(dataNodeHttpPort), Option("hduser") ), Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      }

      MicroService( Seq(masterNodeDescription) ++ slaveDescriptionSet )
    }
  }

  object CreateSparkClusterService extends ClusterNodeSparkExtension{
    def sparkClusterService(cluster: Cluster, imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int) : MicroService = {

      val mysqlIp = cluster.lastUsedIp()

      val sysNiceHostConfig = HostConfig( capabilities = Option( Seq(Capability.SYS_NICE) ) )
      val mysqlContainer = MySqlDbDefinition(
        "hive-mysql-db", "metastore", "Root@123",
        "hive", "hivepswd",
        4444, 3306,
        cluster.dockerNetwork
      ).withIp(mysqlIp).withHostConfig(sysNiceHostConfig)

      var dataNodeHttpPort = 6661
      val masterNodeDescription = SbtServiceDescription(cluster.masterNode.sparkMasterNodeDefinition(Option(dataNodeHttpPort), Option("hduser")), Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)

      val slaveDescriptionSet = cluster.workerNodes.map { slaveNode =>
        dataNodeHttpPort = dataNodeHttpPort + 1
        SbtServiceDescription(slaveNode.sparkWorkerNodeDefinition(Option(dataNodeHttpPort), Option("hduser")), Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      }

      val mysqlSbtServiceDescription = SbtServiceDescription(mysqlContainer, Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      MicroService( Seq(masterNodeDescription) ++ Seq(mysqlSbtServiceDescription) ++ slaveDescriptionSet )
    }
  }

  object CreateHiveClusterService extends ClusterNodeHiveExtension{
    def hiveClusterService(cluster: Cluster, imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int) : MicroService = {

      val mysqlIp = cluster.lastUsedIp()

      val sysNiceHostConfig = HostConfig( capabilities = Option( Seq(Capability.SYS_NICE) ) )
      val mysqlContainer = MySqlDbDefinition(
        "hive-mysql-db", "metastore", "Root@123",
        "hive", "hivepswd",
        4444, 3306,
        cluster.dockerNetwork
      ).withIp(mysqlIp).withHostConfig(sysNiceHostConfig)

      var dataNodeHttpPort = 6661
      val masterNodeDescription = SbtServiceDescription(cluster.masterNode.hdfsMasterNodeDefinition( Option(dataNodeHttpPort), Option("hduser") ), Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)


      val slaveDescriptionSet = cluster.workerNodes.map { slaveNode =>
        dataNodeHttpPort = dataNodeHttpPort + 1
        SbtServiceDescription(slaveNode.hdfsSlaveNodeDefinition( Option(dataNodeHttpPort), Option("hduser") ), Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      }

      val mysqlSbtServiceDescription = SbtServiceDescription(mysqlContainer, Set(), imagePullTimeoutInMinutes.minutes, containerStartTimeoutInMinutes.minutes)
      MicroService( Seq(masterNodeDescription) ++ Seq(mysqlSbtServiceDescription) ++ slaveDescriptionSet)
    }
  }

  implicit class ClusterExtension(cluster : Cluster){

    def serviceTimeouts(imagePullTimeoutInMinutes: Int, containerStartTimeoutInMinutes: Int): MicroService ={
      cluster.clusterType match {
        case SshCluster => CreateSshClusterService.sshClusterService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case RootSshCluster => CreateRootSshClusterService.rootSshClusterService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case HdfsCluster => CreateHdfsClusterService.hdfsClusterService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case HiveCluster => CreateHiveClusterService.hiveClusterService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case SparkCluster => CreateSparkClusterService.sparkClusterService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
        case KafkaCluster => CreateKafkaClusterService.kafkaClusterService(cluster, imagePullTimeoutInMinutes, containerStartTimeoutInMinutes)
      }
    }
  }
}

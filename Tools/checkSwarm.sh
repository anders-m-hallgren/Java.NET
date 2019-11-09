#!/bin/sh
function isSwarmNode(){
    if [ "$(docker info | grep Swarm | sed 's/Swarm: //g')" == "inactive" ]; then
	echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
        echo "This node is not a swarm node. Use "docker swarm init" or "docker swarm join" to connect this node to swarm and try again"
	echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
        return -1
    else
        echo "This node is a swarm node"
        return 0 
    fi
} 
function checkSwarmState(){
case "$(docker info --format '{{.Swarm.LocalNodeState}}')" in
  inactive)
    echo "Node is not in a swarm cluster";;
  pending)
    echo "Node is not in a swarm cluster";;
  active)
    echo "Node is in a swarm cluster";;
  locked)
    echo "Node is in a locked swarm cluster";;
  error)
    echo "Node is in an error state";;
  *)
    echo "Unknown state $(docker info --format '{{.Swarm.LocalNodeState}}')";;
esac
}
function checkSwarmManager(){
  if [ "$(docker info --format '{{.Swarm.LocalNodeState}}')" = "active" -a "$(docker info --format '{{.Swarm.ControlAvailable}}')" = "true" ]; then
    echo "node is a manager"
    docker node ls
  else
    echo "node is not a manager"
  fi
}
function checkSwarmWorker() {
  if [ "$(docker info --format '{{.Swarm.LocalNodeState}}')" = "active" \ -a "$(docker info --format '{{.Swarm.ControlAvailable}}')" = "false" ];
  then 
    echo "node is a worker"
  else
    echo "node is not a worker"
  fi
}
#checkSwarmState
#checkSwarmWorker
#checkSwarmManager
isSwarmNode

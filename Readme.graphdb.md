docker service inspect app_graphdb

docker run --network app --name gremlinconsole --rm -it tinkerpop/gremlin-console

cluster = Cluster.build("10.0.33.2").port(8182).create()
:remote connect tinkerpop.server cluster

:> g.V().count()

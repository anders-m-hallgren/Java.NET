def addItUp(x, y) { x + y }
def globals = [:]

globals << [hook : [
  onStartUp: { ctx ->
    ctx.logger.info("Loading graph data from graph/graphdb.json")
    graph.io(GraphSONIo.build()).readGraph('graph/graphdb.json')
  }
] as LifeCycleHook]

globals << [g : graph.traversal().withStrategies(ReferenceElementStrategy.instance())]

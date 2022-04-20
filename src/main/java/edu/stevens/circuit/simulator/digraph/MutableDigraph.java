package edu.stevens.circuit.simulator.digraph;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MutableDigraph<E, N> implements Digraph<E, N> {

    private final Function<N, Object> idNodeExtractor;
    private final Function<E, Object> idEdgeExtractor;
    private final Map<Object, GraphNode<E, N>> nodesById;
    private final Map<Object, GraphEdge<E, N>> edgesById;

    public MutableDigraph() {
        this.nodesById = new HashMap<>();
        this.edgesById = new HashMap<>();
        this.idNodeExtractor = (n) -> n;
        this.idEdgeExtractor = (e) -> e;
    }

    public MutableDigraph(Function<N, Object> idNodeExtractor,
            Function<E, Object> idEdgeExtractor) {
        this.nodesById = new HashMap<>();
        this.edgesById = new HashMap<>();
        this.idNodeExtractor = idNodeExtractor;
        this.idEdgeExtractor = idEdgeExtractor;
    }


    public GraphNode<E, N> createNode(N node) {
        Object id = idNodeExtractor.apply(node);
        GraphNode<E, N> graphNode = new GraphNode<>(node, id);
        nodesById.put(id, graphNode);
        return graphNode;
    }

    public void createEdge(E edge, GraphNode<E, N> graphNodeSource,
            GraphNode<E, N> graphNodeTarget) {
        Object id = idEdgeExtractor.apply(edge);
        GraphEdge<E, N> graphEdge = new GraphEdge<>(edge, id, graphNodeSource, graphNodeTarget);
        edgesById.put(id, graphEdge);

    }

    @Override
    public Set<N> getNodes() {
        return nodesById.values().stream().map(GraphNode::getData)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<E> getEdges() {
        return edgesById.values().stream().map(GraphEdge::getData)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Optional<N> findNodeById(Object id) {
        return Optional.ofNullable(nodesById.get(id)).map(GraphNode::getData);
    }

    @Override
    public Optional<N> findSourceNodeByEdge(E edge) {
        return Optional.ofNullable(edgesById.get(idEdgeExtractor.apply(edge)))
                .map(GraphEdge::getSource).map(GraphNode::getData);
    }

    @Override
    public Optional<N> findTargetNodeByEdge(E edge) {
        return Optional.ofNullable(edgesById.get(idEdgeExtractor.apply(edge)))
                .map(GraphEdge::getTarget).map(GraphNode::getData);
    }

    @Override
    public Optional<E> findEdgeById(Object id) {
        return Optional.ofNullable(edgesById.get(id)).map(GraphEdge::getData);
    }

    @Override
    public Collection<E> findIncomingEdgesByNode(N node) {
        GraphNode<E, N> graphNode = nodesById.get(idNodeExtractor.apply(node));
        if (graphNode == null) {
            return Collections.emptySet();
        }
        return graphNode.inEdges().stream().map(GraphEdge::getData)
                .collect(Collectors.toUnmodifiableSet());

    }

    @Override
    public Collection<E> findOutgoingEdgesByNode(N node) {
        GraphNode<E, N> graphNode = nodesById.get(idNodeExtractor.apply(node));
        if (graphNode == null) {
            return Collections.emptySet();
        }
        return graphNode.outEdges().stream().map(GraphEdge::getData)
                .collect(Collectors.toUnmodifiableSet());
    }
}

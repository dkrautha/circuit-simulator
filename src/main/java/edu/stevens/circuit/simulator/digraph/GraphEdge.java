package edu.stevens.circuit.simulator.digraph;

import java.util.Objects;

/**
 * Implementation for a generic edge in a directional graph.
 *
 * @param <E> Typ of the underlying data representing an edge
 * @param <N> Typ of the underlying data representing a node
 */
public class GraphEdge<E, N> {
    private final E data;
    private final Object id;
    private final GraphNode<E, N> source;
    private final GraphNode<E, N> target;

    public GraphEdge(E data, Object id, GraphNode<E, N> source, GraphNode<E, N> target) {
        Objects.requireNonNull(data);
        Objects.requireNonNull(id);
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
        this.data = data;
        this.id = id;
        this.source = source;
        this.target = target;
        // set relations
        this.source.addOutEdge(this);
        this.target.addInEdge(this);
    }

    E getData() {
        return data;
    }

    Object getId() {
        return id;
    }

    GraphNode<E, N> getSource() {
        return source;
    }

    GraphNode<E, N> getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        @SuppressWarnings("unchecked")
        GraphEdge<E, N> other = (GraphEdge<E, N>) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }



}

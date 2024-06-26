package socialNetworkApplication;

import java.util.Iterator;

/**
 * An interface for a vertex in a graph.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.0
 */
public interface UserInterface<T> {
    /**
     * Gets this vertex's label.
     * 
     * @return The object that labels the vertex.
     */
    public T getName();

    public T getPassword();

    public void setPassword(T password);

    public T getStatus();

    public void setStatus(T status);

    public void setName(T name);

    public T getImg();

    public void setImg(T img);

    /**
     * Connects this vertex and a given vertex with an unweighted edge. The two
     * vertices cannot be the same, and must not already have this edge between
     * them. In a directed graph, the edge points toward the given vertex.
     * 
     * @param endVertex A vertex in the graph that ends the edge.
     * @return True if the edge is added, or false if not.
     */
    public boolean connect(UserInterface<T> endVertex);

    /**
     * Creates an iterator of this vertex's neighbors by following all edges that
     * begin at this vertex.
     * 
     * @return An iterator of the neighboring vertices of this vertex.
     */
    public Iterator<UserInterface<T>> getNeighborIterator();

    /**
     * Iterates through a list of friends
     * 
     * @return Friends iterator
     */
    public Iterator<User<T>.Edge> getFriendsIterator();

    /**
     * Sees whether this vertex has at least one neighbor.
     * 
     * @return True if the vertex has a neighbor.
     */
    public boolean hasNeighbor();
} // end VertexInterface
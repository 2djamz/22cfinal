package socialNetworkApplication;

import java.util.Iterator;

/**
 * A class that implements the ADT undirected graph.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.0
 */
public class UndirectedGraph<T> extends SocialNetwork<T> implements SocialNetworkInterface<T> {
	public UndirectedGraph() {
		super();
	} // end default constructor

	public boolean addEdge(T begin, T end) {
		return super.addEdge(begin, end) && super.addEdge(end, begin);
		// edge count is twice its correct value due to calling addEdge twice
	} // end addEdge

	public int getNumberOfEdges() {
		return super.getNumberOfEdges() / 2;
	} // end getNumberOfEdges

} // end UndirectedGraph


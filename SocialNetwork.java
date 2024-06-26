package socialNetworkApplication;

import java.util.Iterator;

//start:june5
//end:

public class SocialNetwork<T> implements BasicGraphInterface<T> {
    private DictionaryInterface<T, UserInterface<T>> vertices;
    private int edgeCount;

    public SocialNetwork() {
        vertices = new LinkedDictionary<>();
        edgeCount = 0;
    }
    // **vertices: A dictionary that maps each vertex label 
    // of type T to a UserInterface<T> object.

    protected void resetVertices() {
        Iterator<UserInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext()) {
            UserInterface<T> nextVertex = vertexIterator.next();
        }
    }

    // edgeCount: An integer that keeps track of the number of edges in the graph.
    public void displayEdges() {
        System.out.println("\nAll users and their friends:");
        Iterator<UserInterface<T>> vertexIterator = vertices.getValueIterator();
        while (vertexIterator.hasNext()) {
            ((User<T>) (vertexIterator.next())).display();
        }
    }
// This method displays the friends of a specific user.
// It gets the user's friends iterator and displays the first friend.
    public void displayFriends(T name) {
        System.out.println("Here's a list of all " + name + "'s friends:");
        UserInterface<T> user = getUser(name);
        if (user != null) {
            Iterator<User<T>.Edge> vertexIterator = user.getFriendsIterator();
            if (!vertexIterator.hasNext()) {
                System.out.println(name + " has no friends.");
            } else {
                while (vertexIterator.hasNext()) {
                    vertexIterator.next().display();
                }
            }
        } else {
            System.out.println(name + " does not exist in the network.");
        }
    }
    
//This method lists the friends of the friends of a specific user.
//It adds all friends to a linked list. Error handling for 
    public void displayFriendsOfFriends(T name) {
        UserInterface<T> user = getUser(name);
        if (user != null) {
            LinkedListWithIterator<User<T>.Edge> list = new LinkedListWithIterator<>();  // Specified Edge as the type parameter
            Iterator<User<T>.Edge> vertexIterator = user.getFriendsIterator();
            if (!vertexIterator.hasNext()) {
                System.out.println(name + " has no friends.");
            } else {
                while (vertexIterator.hasNext()) {
                    list.add(vertexIterator.next());
                }
                System.out.println("Here's a list of all " + name + "'s friends of friends:");
                for (User<T>.Edge friend : list) {
                    System.out.print(friend.getUser() + " ");
                }
                System.out.println();
            }
        } else {
            System.out.println(name + " does not exist in the network.");
        }
    }

    //This method adds a new vertex (user) to the graph. 
    //It returns true if the addition was successful (i.e., if addOutcome is null).
    public boolean addVertex(T vertexLabel) {
        UserInterface<T> addOutcome = vertices.add(vertexLabel, new User<>(vertexLabel));
        return addOutcome == null; // Was addition to dictonary successfull?
    }

    //This method adds an edge (friendship) between two vertices.
    //It returns true if the edge was successfully added.
    public boolean addEdge(T begin, T end) {
        boolean result = false;
        UserInterface<T> beginVertex = vertices.getValue(begin); // value is an object!!!
        UserInterface<T> endVertex = vertices.getValue(end); // by reference
        if ((beginVertex != null) && (endVertex != null)) {
            result = beginVertex.connect(endVertex);
        }
        if (result) {
            edgeCount++;
        }
        return result;
    }
    //This method checks if there is an edge between two vertices. 
    //It returns true if an edge exists.
    public boolean hasEdge(T begin, T end) {
        boolean found = false;
        UserInterface<T> beginVertex = vertices.getValue(begin);
        UserInterface<T> endVertex = vertices.getValue(end);
        if ((beginVertex != null) && (endVertex != null)) {
            Iterator<UserInterface<T>> neighbors = beginVertex.getNeighborIterator();
            while (!found && neighbors.hasNext()) {
                UserInterface<T> nextNeighbor = neighbors.next();
                if (endVertex.equals(nextNeighbor)) {
                    found = true;
                }
            }
        }
        return found;
    }

    // This method retrieves a UserInterface<T> object from the dictionary based on the user's label.
    public UserInterface<T> getUser(T user) {
        return vertices.getValue(user);
    }

    // This method renames a user by removing the old key and adding a new key with the same value.
    public void renameKey(T existingUser, T newName) {
        vertices.add(newName, vertices.remove(existingUser));
    }

    // This method checks if a vertex exists in the graph.
    public boolean hasVertex(T vertex) {
        return vertices.contains(vertex);
    }
    // check if graph is empty
    public boolean isEmpty() {
        return vertices.isEmpty();
    }
    // return vertices
    public int getNumberOfVertices() {
        return vertices.getSize();
    }
    // return edges
    public int getNumberOfEdges() {
        return edgeCount;
    }
    // This method clears the graph, removing all vertices and edges.
    public void clear() {
        vertices.clear();
        edgeCount = 0;
    }
}
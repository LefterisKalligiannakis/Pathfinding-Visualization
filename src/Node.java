/*
    Node class for Dijkstra algorithm visualization.

    @author Eleftherios Kalligiannakis
 */

import java.awt.*;
import java.util.ArrayList;

public class Node {
    boolean isWall = false;
    ArrayList<Node> adjList;
    Color color;
    private int x, y;


    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.adjList = new ArrayList<>();
        color = Color.white;
    }


    public boolean equals(Node n) {
        return this.x == n.getX() && this.y == n.getY();
    }

    // Getters
    public int getX() {
        return x;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ArrayList<Node> getAdjList() {
        return adjList;
    }

    // Add connections to both nodes for an undirected graph
    public void addAdj(Node node) {
        this.adjList.add(node);
        node.adjList.add(this);
    }


}

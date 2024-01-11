/*
    Contains the bfs pathfinding algorithm as well as some other methods
    for visualizing the process of finding the path and showing it.

    @author Eleftherios Kalligiannakis
 */


import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


public class BfsPathfinder {
    Graph graph;
    Frame frame;
    Node start;
    Node end;


    public BfsPathfinder(Frame frame) {
        this.frame = frame;
        this.graph = new Graph(24, 24, frame);
    }

    /*
        Visualize process of bfs by coloring nodes
        on the frame based on what the algorithm is doing with
        them.
     */
    public void visualizeBFS(Node start, Node end) {
        Queue<Node> queue = new LinkedList<>();
        LinkedList<Node> visited = new LinkedList<>();


        queue.add(start);
        visited.add(start);

        Timer timer = new Timer(1, e -> {
            if (!queue.isEmpty()) {
                Node current = queue.poll();

                if (current.equals(end)) {
                    ((Timer) e.getSource()).stop();
                    showPath(start, end);
                    frame.isComplete = true;
                    return;
                }

                for (Node neighbor : current.getAdjList()) {
                    if (!visited.contains(neighbor) && !queue.contains(neighbor) && !neighbor.isWall) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                        if (!start.equals(neighbor) && !end.equals(neighbor))
                            neighbor.color = Color.CYAN;
                    }
                }
                if (!current.equals(start))
                    current.color = Color.GREEN;
                frame.repaint();

            } else {
                ((Timer) e.getSource()).stop();
                System.out.println("No path found");
                frame.isComplete = true;
            }
        });
        timer.start();

    }

    public void showPath(Node start, Node end) {
        List<Node> path = bfs(start, end);

        if (!path.isEmpty()) {
            Timer timer = new Timer(3, new ActionListener() {
                private final Iterator<Node> iterator = path.iterator();

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (iterator.hasNext()) {
                        Node currentNode = iterator.next();
                        if (!currentNode.equals(start) && !currentNode.equals(end))
                            currentNode.color = Color.YELLOW; // Adjust the color as needed
                        frame.repaint();
                    } else {
                        ((Timer) e.getSource()).stop();
                    }
                }
            });

            timer.start();
        } else {
            System.out.println("No path found");
        }
    }

    private List<Node> bfs(Node start, Node destination) {
        List<Node> path = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        Map<Node, Node> parentMap = new HashMap<>();

        queue.add(start);
        parentMap.put(start, null);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.equals(destination)) {
                reconstructPath(destination, parentMap, path);
                return path;
            }

            for (Node neighbor : current.getAdjList()) {
                if (!parentMap.containsKey(neighbor) && !neighbor.isWall) {
                    queue.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        // No path found
        return Collections.emptyList();
    }

    private void reconstructPath(Node destination, Map<Node, Node> parentMap, List<Node> path) {
        Node current = destination;

        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }

        // Reverse the path to start from the source
        Collections.reverse(path);
    }

}

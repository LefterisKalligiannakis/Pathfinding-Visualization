/*
    Frame class for pathfinding visualization.
    Each box represents a node in the graph, by clicking a node you can
    remove all verticies looking to it, essentially making it a wall in the graph.
    s+click, adds the start node
    e+click, adds the end node

    @author Eleftherios Kalligiannakis
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    private final int WIDTH = 855;
    private final int HEIGHT = 875;
    private final int size = 35; // square size
    private final BfsPathfinder pathfinder;
    private final OptionHandler optionhandler;
    JFrame window;
    boolean isComplete = false;
    boolean isRan = false;
    boolean diagonals = false;
    boolean visualize = false;
    private boolean isMousePressed = false;
    private int rolloverx = Integer.MIN_VALUE;
    private int rollovery = Integer.MIN_VALUE;
    private int key = 0; // pressed key


    public Frame() {

        pathfinder = new BfsPathfinder(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow(true);
        requestFocus();


        optionhandler = new OptionHandler();

        add(optionhandler);
        repaint();

        window = new JFrame();
        window.setSize(WIDTH, HEIGHT);
        window.setTitle("Pathfinding Visualization");
        window.setVisible(true);
        window.setContentPane(this);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        setLayout(null);
        window.addKeyListener(this);


        optionhandler.setBounds(20, 790, 186, 33); // Adjust the position and size as needed
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int row = 0, col = 0;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);


        for (int i = 0; i < this.getHeight(); i += size) {
            for (int j = 0; j < this.getWidth(); j += size) {
                g.setColor(pathfinder.graph.getNode(row, col).color);
                g.fillRect(i, j, size - 1, size - 1);
                col++;
            }
            col = 0;
            row++;
        }

    }


    void reset() {
        pathfinder.graph = new Graph(pathfinder.graph.getSizeX(), pathfinder.graph.getSizeX(), this);
        pathfinder.start = null;
        pathfinder.end = null;
        isComplete = false;
        isRan = false;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            if (!isRan && visualize) {
                pathfinder.visualizeBFS(pathfinder.start, pathfinder.end);
                isRan = true;
            }

            if (!isRan) {
                pathfinder.showPath(pathfinder.start, pathfinder.end);
            }
        }

        if (key == KeyEvent.VK_BACK_SPACE) {
            reset();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        key = 0;
    }

    /*
    Detects mouse movement and calculates the square the mouse is over using the
    mouse's coordinates.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        // mouse coordinates x
        int mx = e.getX();
        // mouse coordinates y
        int my = e.getY();
        rolloverx = mx / size;
        rollovery = my / size;
        if (diagonals != optionhandler.diagonals.isSelected()) {
            diagonals = optionhandler.diagonals.isSelected();
            reset();
            requestFocus();
        }
        if (visualize != optionhandler.visualize.isSelected()) {
            visualize = optionhandler.visualize.isSelected();
            requestFocus();
        }
    }


    /*
        Click to add wall
     */
    @Override
    public void mousePressed(MouseEvent e) {
        isMousePressed = true; // to check if mouse is pressed for click and drag

        if (key == KeyEvent.VK_S) {
            pathfinder.start = pathfinder.graph.getNode(rolloverx, rollovery);
            pathfinder.graph.getNode(rolloverx, rollovery).color = Color.BLUE;
        } else if (key == KeyEvent.VK_E) {
            pathfinder.end = pathfinder.graph.getNode(rolloverx, rollovery);
            pathfinder.graph.getNode(rolloverx, rollovery).color = Color.RED;
        } else {
            if (e.getButton() == MouseEvent.BUTTON1) {
                pathfinder.graph.makeWall(rolloverx, rollovery);
            } else if (e.getButton() == MouseEvent.BUTTON3)
                pathfinder.graph.removeWall(rolloverx, rollovery);
        }

        repaint();
    }

    /*
        Click and drag functionality for deleting and adding walls.
        When mouse is clicked and dragged it continuously calculates
        the square the mouse is over.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (isMousePressed) {
            rolloverx = e.getX() / size;
            rollovery = e.getY() / size;

            int modifiers = e.getModifiersEx(); //e.getButton() doesn't work for some reason...

            if ((modifiers & InputEvent.BUTTON1_DOWN_MASK) != 0 && key != KeyEvent.VK_S && key != KeyEvent.VK_E) {
                pathfinder.graph.makeWall(rolloverx, rollovery);
            } else if ((modifiers & InputEvent.BUTTON3_DOWN_MASK) != 0 && key != KeyEvent.VK_S && key != KeyEvent.VK_E) {
                pathfinder.graph.removeWall(rolloverx, rollovery);
            }

            repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        isMousePressed = false;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}

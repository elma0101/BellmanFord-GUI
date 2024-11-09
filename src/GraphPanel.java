import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


class GraphPanel extends JPanel {
    private  List<Node> nodes = new ArrayList<>();
    private  List<Edge> edges = new ArrayList<>();
    private HashMap<Integer, String> map = new HashMap<>();
    private String mode = "node";
    private Node selectedNode = null;
    public int[] distances ;
    public int[] predecesseur ;
    int counter=0;

    public GraphPanel() {

        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ("node".equals(mode)) {
                    String valueInput = JOptionPane.showInputDialog("Enter value for this node:");
                    nodes.add(new Node(e.getX(), e.getY(),valueInput));

                    //the source node with different color
                    if (counter == 0) {
                        Graphics g = getGraphics();
                        g.setColor(Color.GREEN);
                        g.fillOval(e.getX() - Node.RADIUS / 2, e.getY() - Node.RADIUS / 2, Node.RADIUS, Node.RADIUS);
                        g.setColor(Color.BLACK);
                        g.drawOval(e.getX() - Node.RADIUS / 2, e.getY() - Node.RADIUS / 2, Node.RADIUS, Node.RADIUS);
                    }

                    Node n=nodes.get(nodes.size()-1);
                    repaint();
                    if (!map.containsValue(n.value)) {
                        map.put(counter, n.value);
                        counter++;
                    }

                } else if ("edge".equals(mode)) {
                    for (Node node : nodes) {
                        if (node.contains(e.getPoint())) {
                            if (selectedNode == null) {
                                selectedNode = node;
                            } else {
                                String valueInput = JOptionPane.showInputDialog("Enter value for this edge:");
                                try {
                                    int value = Integer.parseInt(valueInput);
                                    edges.add(new Edge(selectedNode, node,value));
                                    selectedNode = null;
                                    repaint();
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
                                }

                            }
                            break;
                        }
                    }
                }
            }
        });
    }
    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        // Draw edges
        for (Edge edge : edges) {
            edge.draw(g);
            g.drawString(String.valueOf(edge.value),
                    (edge.start.x + edge.end.x) / 2,
                    (edge.start.y + edge.end.y) / 2);
        }

        // Draw nodes
        for (Node node : nodes) {
            if (node == nodes.get(0)) {

                g.setColor(Color.green);
                g.fillOval(node.x - Node.RADIUS / 2, node.y - Node.RADIUS / 2, Node.RADIUS, Node.RADIUS);
                g.setColor(Color.BLACK);
                g.drawOval(node.x - Node.RADIUS / 2, node.y - Node.RADIUS / 2, Node.RADIUS, Node.RADIUS);

            } else {
                node.draw(g);

            }
            g.drawString(String.valueOf(node.value), node.x-4, node.y+4);

        }
    }

    public void afficherChemin(int[] predecesseur, int j, Graphics g) {



        if (predecesseur[j] == -1) {
            return;
        }
        afficherChemin(predecesseur, predecesseur[j], g);

        // Color the optimal path
        Node startNode = nodes.get(predecesseur[j]);
        Node endNode = nodes.get(j);
        for (Edge edge : edges) {
            if (edge.start == startNode && edge.end == endNode) {
                edge.color = "red";
                break;
            }
        }

        
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        int width = getWidth();
        int height = getHeight();
        int gridSize = 20;

        // Draw vertical lines
        for (int x = 0; x < width; x += gridSize)
        {
            g.drawLine(x, 0, x, height);
        }
        // Draw horizontal lines
         for (int y = 0; y < height; y += gridSize) {
            g.drawLine(0, y, width, y);
        }
    }

    public  void showDetails(){
        displayShortestPathDetails(distances);
    }

    public void displayShortestPathDetails(int[] distances) {
        StringBuilder result = new StringBuilder("Shortest path distances from node "+map.get(0)+" to:\n");
        for (int i = 1; i < distances.length; i++) {
            result.append("Node ").append(map.get(i)).append(": ");
            if (distances[i] == Integer.MAX_VALUE) {
                result.append("Aucun chemin existant \n");
            } else {
                result.append("Distance : ").append(distances[i]).append("\n");
            }
        }
        JOptionPane.showMessageDialog(this, result.toString(), "Shortest Path Results", JOptionPane.INFORMATION_MESSAGE);
    }


    public void calculateShortestPath() {

        //Source node is always 0
        String source = map.get(0);

        for (Edge edge : edges) {
            edge.color = "black";
        }


        //Ask for the destination node
        String destination = JOptionPane.showInputDialog("Enter the destination node:");
        int destinationIndex = -1;
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(destination)) {
                destinationIndex = entry.getKey();
                break;
            }
        }



        // If the graph is empty, display an error message
        if (nodes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Graph is empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }




        // Initialize distances array with a large value (indicating infinity)
        int numNodes = nodes.size();
        distances = new int[numNodes];
        predecesseur = new int[numNodes];
        for (int i = 0; i < numNodes; i++) {
            distances[i] = Integer.MAX_VALUE;
            predecesseur[i] = -1;
        }

        // Set the distance to the source node (e.g., node 0) to 0
        distances[0] = 0;

        // Relax all edges (numNodes - 1) times
        for (int i = 1; i < numNodes; i++) {
            for (Edge edge : edges) {
                String u = edge.start.value;
                String v = edge.end.value;
                int uIndex = -1;
                int vIndex = -1;
                int weight = edge.value;

                for (Map.Entry<Integer, String> entry : map.entrySet()) {
                    if (entry.getValue().equals(u)) {
                        uIndex = entry.getKey();
                        break;
                    }
                }

                for (Map.Entry<Integer, String> entry : map.entrySet()) {
                    if (entry.getValue().equals(v)) {
                        vIndex = entry.getKey();
                        break;
                    }
                }

                // If the source node's distance is not infinity and can be minimized
                if (distances[uIndex] != Integer.MAX_VALUE && distances[uIndex] + weight < distances[vIndex]) {
                    distances[vIndex] = distances[uIndex] + weight;
                    predecesseur[vIndex] = uIndex;
                }
            }
        }

        // Check for negative-weight cycles
        for (Edge edge : edges) {
            String u = edge.start.value;
            String v = edge.end.value;
            int uIndex = -1;
            int vIndex = -1;
            int weight = edge.value;

            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                if (entry.getValue().equals(u)) {
                    uIndex = entry.getKey();
                    break;
                }
            }

            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                if (entry.getValue().equals(v)) {
                    vIndex = entry.getKey();
                    break;
                }
            }

            if (distances[uIndex] != Integer.MAX_VALUE && distances[uIndex] + weight < distances[vIndex]) {
                JOptionPane.showMessageDialog(this, "Graph contains a negative-weight cycle");
                return;
            }
        }



        // Display the shortest path distances from the source node when clicking on a button on the top left corner

        Graphics g = getGraphics();
        //colorer le chemin optimal


        afficherChemin(predecesseur, destinationIndex, g);
        repaint();


    }


    public void resetGraph() {

        nodes= new ArrayList<>();
        edges= new ArrayList<>();
        map = new HashMap<>();
        counter=0;
        distances = null;
        predecesseur = null;



        repaint();

        JOptionPane.showMessageDialog(this, "Graph has been reset.","Reset Graph", JOptionPane.INFORMATION_MESSAGE);
    }
}

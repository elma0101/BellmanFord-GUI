import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


class GraphPanel extends JPanel {
    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();
    private String mode = "node";
    private Node selectedNode = null;

    public GraphPanel() {
        setBackground(Color.WHITE);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if ("node".equals(mode)) {
                    String valueInput = JOptionPane.showInputDialog("Enter value for this node:");
                    nodes.add(new Node(e.getX(), e.getY(),valueInput));
                    repaint();
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

        // Draw edges
        for (Edge edge : edges) {
            edge.draw(g);
            g.drawString(String.valueOf(edge.value),
                    (edge.start.x + edge.end.x) / 2,
                    (edge.start.y + edge.end.y) / 2);
        }

        // Draw nodes
        for (Node node : nodes) {
            node.draw(g);
            g.drawString(String.valueOf(node.value), node.x-4, node.y+4);
        }
    }
}

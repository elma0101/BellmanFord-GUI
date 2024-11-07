import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class GraphApp extends JFrame {
    private final GraphPanel graphPanel = new GraphPanel();
    private final JButton nodeButton = new JButton("Add Node");
    private final JButton edgeButton = new JButton("Add Edge");

    public GraphApp() {
        super("Bellman-Ford");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nodeButton);
        buttonPanel.add(edgeButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(graphPanel, BorderLayout.CENTER);

        // Button action listeners
        nodeButton.addActionListener(e -> graphPanel.setMode("node"));
        edgeButton.addActionListener(e -> graphPanel.setMode("edge"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphApp app = new GraphApp();
            app.setVisible(true);
        });
    }
}
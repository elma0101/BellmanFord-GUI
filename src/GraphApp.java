import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GraphApp extends JFrame {
    private final GraphPanel graphPanel = new GraphPanel();
    private final JButton nodeButton = new JButton("Ajouter noeud");
    private final JButton edgeButton = new JButton("Ajouter arête");
    private final JButton calculateButton = new JButton("Calculer PPC");
    private final JButton resetButton = new JButton("Réinitialiser");
    private JButton Detailsbutton = new JButton("Details");;

    public GraphApp() {
        super("Bellman-Ford");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);



        //center the frame
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(2,2));
        setLayout(new BorderLayout(2,2));

        //nodebutton styling
        nodeButton.setBackground(new Color(46, 139, 87));
        nodeButton.setForeground(Color.WHITE);
        nodeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                nodeButton.setBackground(new Color(34, 100, 62));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                nodeButton.setBackground(new Color(46, 139, 87));
            }
        });

        //edgebutton styling
        edgeButton.setBackground(new Color(184, 134, 11));
        edgeButton.setForeground(Color.WHITE);
        edgeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                edgeButton.setBackground(new Color(218, 165, 32));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                edgeButton.setBackground(new Color(184, 134, 11));
            }
        });

        //calculatebutton styling
        calculateButton.setBackground(new Color(106, 90, 205));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                calculateButton.setBackground(new Color(123, 104, 238));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                calculateButton.setBackground(new Color(106, 90, 205));
            }
        });

        //resetbutton styling
        resetButton.setBackground(new Color(220, 20, 60));
        resetButton.setForeground(Color.WHITE);
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resetButton.setBackground(new Color(178, 34, 34));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetButton.setBackground(new Color(220, 20, 60));
            }
        });


        JPanel buttonPanel = new JPanel(new GridLayout(2,2));


        buttonPanel.add(nodeButton);
        buttonPanel.add(edgeButton);
        buttonPanel.add(calculateButton);
        buttonPanel.add(resetButton);



        Detailsbutton.setVisible(true);
        Detailsbutton.setBounds(0, 0, 100, 30);


        add(buttonPanel, BorderLayout.SOUTH);
        add(graphPanel, BorderLayout.CENTER);
        add(Detailsbutton, BorderLayout.NORTH);

        Detailsbutton.setVisible(false);

        // Button action listeners
        nodeButton.addActionListener(e -> graphPanel.setMode("node"));
        edgeButton.addActionListener(e -> graphPanel.setMode("edge"));
        calculateButton.addActionListener(e -> {
            graphPanel.calculateShortestPath();
            Detailsbutton.setVisible(true);
        });
        resetButton.addActionListener(e -> {
            graphPanel.resetGraph();
            Detailsbutton.setVisible(false);
         }
        );
        Detailsbutton.addActionListener(e -> graphPanel.showDetails());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GraphApp app = new GraphApp();
            app.setVisible(true);
        });
    }
}
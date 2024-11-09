import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


class Node {
    public static final int RADIUS = 40;
    public final int x, y;
    public String value;

    public Node(int x, int y,String value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(x - RADIUS / 2, y - RADIUS / 2, RADIUS, RADIUS);
        g.setColor(Color.BLACK);
        g.drawOval(x - RADIUS / 2, y - RADIUS / 2, RADIUS, RADIUS);


    }

    public boolean contains(Point p) {
        return p.distance(x, y) < RADIUS;
    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


class Edge {
    public final Node start, end;
    public int value;

    public Edge(Node start, Node end,int value) {
        this.start = start;
        this.end = end;
        this.value = value;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(start.x, start.y, end.x, end.y);
    }
}
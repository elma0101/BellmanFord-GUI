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

        int arrowSize = 5;

        g.setColor(Color.BLACK);
        g.drawLine(start.x, start.y, end.x, end.y);

        //draw arrow head
        Graphics2D g2 = (Graphics2D) g.create();
        int dx = end.x - start.x;
        int dy = end.y - start.y;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        g2.translate(start.x, start.y);
        g2.rotate(angle);
        g2.drawLine(0, 0, len, 0);
        g2.fillPolygon(new int[]{len -15, len - arrowSize -35, len - arrowSize -35, len -15},
                new int[]{0, -arrowSize, arrowSize, 0}, 4);
        g2.dispose();



    }
}
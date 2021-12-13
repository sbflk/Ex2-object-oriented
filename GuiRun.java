import api.EdgeData;
import api.NodeData;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

public class GuiRun extends JFrame implements ActionListener {

    private DirectedWeightedGraphAlgorithmsClass algo;
    int x_size;
    int y_size;


    // all the buttons
    private MenuItem connect;
    private MenuItem removeNode;
    private MenuItem center;
    private MenuItem shortest_path_dist;
    private MenuItem shortest_path;
    private MenuItem is_connected;
    private MenuItem tsp;

    public GuiRun(DirectedWeightedGraphAlgorithmsClass algo) {
        //setVisible(true);
        this.algo = algo;
        this.x_size = 800;
        this.y_size = 800;
        initFrame();
        addMenu();
        //paint(getGraphics());
    }

    private void initFrame() {
        this.setSize(x_size, y_size);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addMenu() {
        MenuBar menubar = new MenuBar();
        Menu load = new Menu("Load");
        Menu save = new Menu("Save");
        Menu edit = new Menu("Edit");
        Menu run = new Menu("Run");
        menubar.add(load);
        menubar.add(save);
        menubar.add(edit);
        menubar.add(run);
        this.setMenuBar(menubar);

        connect = new MenuItem("Connect");
        removeNode = new MenuItem("Remove Node");
        edit.add(connect);
        edit.add(removeNode);
        center = new MenuItem("Center");
        shortest_path = new MenuItem("Shortest Path");
        shortest_path_dist = new MenuItem("Shortest Path Dist");
        tsp = new MenuItem("Tsp");
        is_connected = new MenuItem(" Is Connected");
        run.add(center);
        run.add(shortest_path_dist);
        run.add(shortest_path);
        run.add(tsp);
        run.add(is_connected);
    }


    public void paint(Graphics g){
        g.setColor(Color.RED);
        Iterator<NodeData> iter = algo.getGraph().nodeIter();
        NodeData n = null;
        double max_y = 0;
        double min_y = Double.MAX_VALUE;
        double max_x = 0;
        double min_x = Double.MAX_VALUE;
        while (iter.hasNext()){
            n = iter.next();
            if (max_x < (n.getLocation().x())){
                max_x = (n.getLocation().x());
            }
            else if (min_x > (n.getLocation().x())){
                min_x = (n.getLocation().x());
            }
            if (max_y < (n.getLocation().y())){
                max_y = (n.getLocation().y());
            }
            else if (min_y > (n.getLocation().y())){
                min_y = (n.getLocation().y());
            }
        }
        min_x -= 0.000625;
        min_y -= 0.000625;
        max_x += 0.000625;
        max_y += 0.000625;
        Iterator<NodeData> iter1 = algo.getGraph().nodeIter();
        NodeData n1;
        while (iter1.hasNext()) {
            n1 = iter1.next();
            double x = (n1.getLocation().x()-min_x) /(max_x - min_x);
            double y = (n1.getLocation().y()-min_y) /(max_y - min_y);
            int final_x = (int) (x*x_size);
            int final_y = (int) (y*y_size);
            g.drawOval(final_x, final_y,10,10);
            Iterator<EdgeData> node_edges = algo.getGraph().edgeIter(n1.getKey());
            EdgeData e;
            while (node_edges.hasNext()){
                e = node_edges.next();
                NodeData dest = algo.getGraph().getNode(e.getDest());
                double dest_x = (dest.getLocation().x()-min_x) /(max_x - min_x);
                double dest_y = (dest.getLocation().y()-min_y) /(max_y - min_y);
                int dest_final_x = (int) (dest_x*x_size);
                int dest_final_y = (int) (dest_y*y_size);
                g.drawLine(final_x + 5,final_y + 5,dest_final_x + 5,dest_final_y + 5);
                draw_arrow(g, final_x + 5,final_y + 5,dest_final_x + 5,dest_final_y + 5, 6,5);
                g.setColor(Color.RED);
            }
        }
    }

    public void draw_arrow(Graphics g, int x1, int y1, int x2, int y2, int d, int h){
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm*cos - ym*sin + x1;
        ym = xm*sin + ym*cos + y1;
        xm = x;

        x = xn*cos - yn*sin + x1;
        yn = xn*sin + yn*cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};
        g.setColor(Color.BLACK);
        g.fillPolygon(xpoints,ypoints,3);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
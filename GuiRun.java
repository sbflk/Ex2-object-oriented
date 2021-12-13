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

    public GuiRun(DirectedWeightedGraphAlgorithmsClass algo) {
        //setVisible(true);
        this.algo = algo;
        this.x_size = 800;
        this.y_size = 800;
        initFrame();
        addMenu();
        //DrawGraph(algo);
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
                ArrayList<Integer> triangle_points = new ArrayList<>();
                if ((final_x > dest_final_x && final_y > dest_final_y) || (final_x < dest_final_x && final_y < dest_final_y)){
                    triangle_points.add(dest_final_x + 3);
                    triangle_points.add(dest_final_y + 3);
                    triangle_points.add(dest_final_x - 3);
                    triangle_points.add(dest_final_y - 3);
                }
                else{
                    triangle_points.add(dest_final_x + 3);
                    triangle_points.add(dest_final_y - 3);
                    triangle_points.add(dest_final_x - 3);
                    triangle_points.add(dest_final_y + 3);
                }
                int[] x_points = {dest_final_x + 5,triangle_points.get(0),triangle_points.get(2)};
                int[] y_points = {dest_final_y + 5,triangle_points.get(1),triangle_points.get(3)};
                g.setColor(Color.BLACK);
                g.drawPolygon(x_points,y_points,3);
                g.setColor(Color.RED);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }
}
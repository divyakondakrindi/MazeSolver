package GUI;

import GraphAlgorithm.DFS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;


public class View extends JFrame implements ActionListener, MouseListener {
    private int [][] maze = {
             //first colum and last colum is for complete black screen
            {1,1,1,1,1,1,1,1,1,1,1,1,1}, // this row is for complete black screen
            {1,0,1,0,1,0,1,0,0,0,0,0,1},
            {1,0,1,0,0,0,1,0,1,1,1,0,1},
            {1,0,0,0,1,1,1,0,0,0,0,0,1},
            {1,0,1,0,0,0,0,0,1,1,1,0,1},
            {1,0,1,0,1,1,1,0,1,0,0,0,1},
            {1,0,1,0,1,0,0,0,1,1,1,0,1},
            {1,0,1,0,1,1,1,0,1,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,1,9,1}, //9 -> to indicate the end point
            {1,1,1,1,1,1,1,1,1,1,1,1,1} //this row is for complete black screen
    };
    //the no of rows = 8, no of columns = 11 ->where we have maze(path) ->combination of black and white
    private int[]target = {8,11};
    //which will contain the path of maze which we get from dfs algorithm.
    private List<Integer> path = new ArrayList<Integer>();
    JButton SubmitButton;
    JButton clearButton;
    JButton cancelButton;

    View(){
       this.setTitle("Maze Solver");
       this.setSize(520,500);
       this.setLayout(null);
       //if we want the gui to be in the middle of the screen we use setlocation
       this.setLocationRelativeTo(null);
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       SubmitButton = new JButton("Submit");
       SubmitButton.addActionListener(this);
       SubmitButton.setBounds(80,400,80,30);
       clearButton = new JButton("Clear");
       clearButton.addActionListener(this);
       clearButton.setBounds(240,400,80,30);
       cancelButton = new JButton("Cancel");
       cancelButton.addActionListener(this);
       cancelButton.setBounds(400,400,80,30);
       this.addMouseListener(this);
       this.add(SubmitButton);
       this.add(clearButton);
       this.add(cancelButton);
    }
    //to paint the matrix
    public void paint(Graphics g){
        super.paint(g);
        for(int row =0; row<maze.length; row++){
            for(int column=0; column<maze[row].length; column++){
                Color color;
                switch(maze[row][column]){
                    case 1: color = Color.BLACK; break;
                    case 9: color = Color.RED; break;
                    default: color = Color.WHITE;
                }
                g.setColor(color);
                g.fillRect(40*column,40*row,40,40);
                //border of rectangle
                g.setColor(Color.BLACK);
                // to draw rectangle
                g.drawRect(40*column,40*row,40,40);

            }
        }
         // filling the fina path to green 
        for(int i=0; i<path.size(); i +=2){
            int pathx = path.get(i); // xposition
            int pathy = path.get(i+1); //yposition
            g.setColor(Color.green);
            g.fillRect(40*pathy, 40*pathx,40,40);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == SubmitButton){
            try{
               boolean result = DFS.searchpath(maze,1,1,path);
               this.repaint();
            }
            catch (Exception excp){
               JOptionPane.showMessageDialog(null, "some Error Occured");
            }
        }
        if(e.getSource() == cancelButton){
            //to capture response we will use Joption.pane
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit","submit",JOptionPane.YES_NO_OPTION);
            if(response == 0){
                System.exit(0);
            }
        }
        if(e.getSource() == clearButton){
            for(int row =0; row< maze.length; row++){
                for(int column =0; column<maze[0].length; column++){
                    if(maze[row][column] == 2){
                        maze[row][column] =0;
                    }
                }
            }
            path.clear(); // removes all the co-ordinates that are  present in path array list;
            this.repaint();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e){
        if((e.getX()>=0 && e.getX()<= maze[0].length*40 && e.getY() >=0 && e.getY()<= maze.length*40)){
            int x = e.getY()/40; // to get the coordinates
            int y = e.getX()/40;
            if(maze[x][y] == 1){
                return;
            }
            Graphics g = getGraphics();
            g.setColor(Color.WHITE); // earlier end point must be set to white.
            //first we have stored the target array with rows and column
            // 40*target[1,0] -> will take you to the point(440,320) ->position of default end point
            g.fillRect(40*target[1], 40*target[0],40,40);
            g.setColor(Color.RED);
            g.fillRect(40*y,40*x, 40,40);
            maze[target[0]][target[1]] = 0; // the previous end point's value 9 must be changed to 0(make red to white box)
            maze[x][y] = 9;
            //updating the target array to new red box
            target[0] =x;
            target[1] =y;

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {
        View view = new View();
        view.setVisible(true);
    }
}

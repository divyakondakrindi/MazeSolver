package GraphAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class DFS {
    //Depth first search Algorithm
    public static boolean searchpath(int maze[][], int x, int y, List<Integer>path){
        // if you are at end point add those co-ordinates to list
        if(maze[x][y] == 9){
            path.add(x);
            path.add(y);
            return true;
        }
        // if the box is white we may have path from start to end.
        if(maze[x][y] == 0){
            maze[x][y] =2; //to know that we have visited
            int dx[] = new int[]{1,0,-1,0}; // x position
            int dy[] = new int[]{0,1,0,-1}; // y position in order to move from left right top bottom
            for(int d=0; d<dx.length; d++){
                int newx = x+dx[d];
                int newy = y+dy[d];
                if(newx >=0 && newx < maze.length && newy >=0 && newy < maze[0].length &&searchpath(maze, newx,newy,path)){
                    path.add(x);
                    path.add(y);
                    return true;
                }
            }

        }
        // if the box is black then their is no path from start to end
        return  false;
    }

    public static void main(String[] args) {
        DFS obj = new DFS();
        // temporary maze to check the algorithm
        int maze[][] = {
                {0,1,0},
                {0,1,9},
                {0,1,1}
        };
        List<Integer> path = new ArrayList<>();
        boolean reach = searchpath(maze, 0,0,path);
        System.out.println(reach);
    }
}

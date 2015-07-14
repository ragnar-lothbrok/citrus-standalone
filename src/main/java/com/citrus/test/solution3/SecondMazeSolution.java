package com.citrus.test.solution3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SecondMazeSolution {

    private static ArrayList<Point> shortestPath = new ArrayList<Point>();

    public static void main(String args[]) throws Exception {
        BufferedReader bufferReader = null;
        try {
            bufferReader = new BufferedReader(new FileReader(new File(SecondMazeSolution.class.getResource("mazeInput.txt")
                    .getFile())));
            while (bufferReader.readLine().indexOf("=====") != -1) {
                String line = bufferReader.readLine();
                String str[] = line.split(",");
                Point startPoint = new Point(Integer.parseInt(str[0]), Integer.parseInt(str[1]), 1);

                line = bufferReader.readLine().trim();
                str = line.split(",");
                Point endPoint = new Point(Integer.parseInt(str[0]), Integer.parseInt(str[1]), 0);

                line = bufferReader.readLine().trim();
                int numOfRows = Integer.parseInt(line);

                line = bufferReader.readLine().trim();
                int numOfCols = Integer.parseInt(line);

                short[][] shortArr = new short[numOfRows][numOfCols];
                for (int i = 0; i < numOfRows; i++) {
                    line = bufferReader.readLine().trim();
                    str = line.split(" ");
                    for (int j = 0; j < numOfCols; j++) {
                        shortArr[i][j] = Byte.parseByte(str[j]);
                    }
                }
                if (shortArr[startPoint.getRow()][startPoint.getCol()] == 1
                        || shortArr[endPoint.getRow()][endPoint.getCol()] == 1) {
                    System.out.println("Not Possible.");
                } else {
                    // Will keep the status of visited nodes.
                    byte[][] visited = new byte[numOfRows][numOfCols];
                    List<Point> shortestPath = findShortest(shortArr, visited, startPoint, endPoint);
                    if (shortestPath == null || shortestPath.size() == 0) {
                        System.out.println("Not Possible");
                    } else {
                        //Will format the output in required format.
                        StringBuilder builder = new StringBuilder();
                        Iterator<Point> iterator = shortestPath.iterator();
                        while (iterator.hasNext()) {
                            Point point = iterator.next();
                            if (builder.length() == 0) {
                                builder.append(point.getRow()).append(",").append(point.getCol());
                            } else {
                                builder.append("; ").append(point.getRow()).append(",").append(point.getCol());
                            }
                        }
                        System.out.println(builder.toString());
                    }
                }
            }
        } catch (Exception exception) {

        } finally {
            if (bufferReader != null)
                bufferReader.close();
        }
    }

    /**
     * Method to display adjacency matrix
     * 
     * @param shortArr
     */
    @SuppressWarnings("unused")
    private static void displayMaze(short[][] shortArr) {
        for (int i = 0; i < shortArr.length; i++) {
            for (int j = 0; j < shortArr[0].length; j++) {
                System.out.print(shortArr[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Method which will return shortest path if possible.
     * 
     * @param shortArr
     * @param visited
     * @param startPoint
     * @param endPoint
     * @return
     */
    private static List<Point> findShortest(short[][] shortArr, byte[][] visited, Point startPoint, Point endPoint) {
        shortestPath.clear();
        Point point;
        Queue<Point> queue = new LinkedList<Point>();
        queue.add(startPoint);
        visited[startPoint.getRow()][startPoint.getCol()] = -1;
        List<Point> shortestPath = null;
        int length = Integer.MAX_VALUE;
        if (startPoint.equals(endPoint)) {
            startPoint.getNeighbourList().add(endPoint);
            shortestPath = startPoint.getNeighbourList();
        } else {
            while (!queue.isEmpty()) {
                Point temp = queue.remove();
                if (temp != null && temp.getRow() == endPoint.getRow() && temp.getCol() == endPoint.getCol()) {
                    if (length > temp.getDistance()) {
                        length = temp.getDistance();
                        temp.getNeighbourList().add(temp);
                        shortestPath = temp.getNeighbourList();
                    }
                }
                if (temp != null) {
                    if (temp.getCol() + 1 < shortArr[0].length && visited[temp.getRow()][temp.getCol() + 1] != -1
                            && shortArr[temp.getRow()][temp.getCol() + 1] != 1) {
                        visited[temp.getRow()][temp.getCol() + 1] = -1;
                        point = new Point(temp.getRow(), temp.getCol() + 1, temp.getDistance() + 1);
                        if (temp.getNeighbourList().size() != 0)
                            point.getNeighbourList().addAll(temp.getNeighbourList());
                        point.getNeighbourList().add(temp);
                        queue.add(point);
                    }
                    
                    if (temp.getRow() + 1 < shortArr.length && visited[temp.getRow() + 1][temp.getCol()] != -1
                            && shortArr[temp.getRow() + 1][temp.getCol()] != 1) {
                        visited[temp.getRow() + 1][temp.getCol()] =-1;
                        point = new Point(temp.getRow() + 1, temp.getCol(), temp.getDistance() + 1);
                        if (temp.getNeighbourList().size() != 0)
                            point.getNeighbourList().addAll(temp.getNeighbourList());
                        point.getNeighbourList().add(temp);
                        queue.add(point);
                    }
                    
                    if (temp.getCol() - 1 >= 0 && visited[temp.getRow()][temp.getCol() - 1] != -1
                            && shortArr[temp.getRow()][temp.getCol() - 1] != 1) {
                        visited[temp.getRow()][temp.getCol() - 1]=-1;
                        point = new Point(temp.getRow(), temp.getCol() - 1, temp.getDistance() + 1);
                        if (temp.getNeighbourList().size() != 0)
                            point.getNeighbourList().addAll(temp.getNeighbourList());
                        point.getNeighbourList().add(temp);
                        queue.add(point);
                    }
                    
                    if (temp.getRow() - 1 >= 0 && visited[temp.getRow() - 1][temp.getCol()] != -1
                            && shortArr[temp.getRow() - 1][temp.getCol()] != 1) {
                        visited[temp.getRow() - 1][temp.getCol()] =-1;
                        point = new Point(temp.getRow() - 1, temp.getCol(), temp.getDistance() + 1);
                        if (temp.getNeighbourList().size() != 0)
                            point.getNeighbourList().addAll(temp.getNeighbourList());
                        point.getNeighbourList().add(temp);
                        queue.add(point);
                    }
                }
            }
        }
        return shortestPath;
    }
}

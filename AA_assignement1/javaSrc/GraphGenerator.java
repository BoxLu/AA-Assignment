import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GraphGenerator {
    private static final String BASE_GRAPH_FILE = "facebook_combined.txt";
    private static final String HD_TEST_GRAPH = "HD_base_graph.txt";
    private static final String MD_TEST_GRAPH = "MD_base_graph.txt";
    private static final String LD_TEST_GRAPH = "LD_base_graph.txt";
    private static final int HD_INDICATOR = 1;
    private static final int MD_INDICATOR = 2;
    private static final int LD_INDICATOR = 3;
    private static final int VERTEX_LIMIT = 500;

    private ArrayList<String> vertexList = new ArrayList<>();
    private ArrayList<String> edgeList = new ArrayList<>();

    public static void main(String[] args) {
        GraphGenerator graphGenerator = new GraphGenerator();
        graphGenerator.createGraphHD();
    }

    private void createGraphHD() {
        Scanner readFile = null;
        try {
            readFile = new Scanner(new File(BASE_GRAPH_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != readFile) {
            String edge;
//            String[] token;
            int count = 0;
            while ((edge = readFile.nextLine()) != null) {
                if (count >= VERTEX_LIMIT) {
                    break;
                }
                edgeList.add(edge);
                count++;
                /*token = edge.split("\\s");
                String vertex = token[0];
                if (!vertexList.contains(vertex)) {
                    vertexList.add(vertex);
                }*/
            }
            System.out.println("HD BASE GRAPH GENERATE!");
            readFile.close();
        }
        printToFile(HD_TEST_GRAPH, edgeList);
    }

    private void createGraphMD() {
        Scanner readFile = null;
        try {
            readFile = new Scanner(new File(BASE_GRAPH_FILE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != readFile) {
            String edge;
//            String token[];
            int count = 0;
            boolean addEdge = true;
            while ((edge = readFile.nextLine()) != null) {
                if (count >= VERTEX_LIMIT) {
                    break;
                }
                if (addEdge) {
                    edgeList.add(edge);
                    addEdge = false;
                } else {
                    addEdge = true;
                }
                count++;
            }
            System.out.println("MD BASE GRAPH GENERATE!");
            readFile.close();
        }
        printToFile(MD_TEST_GRAPH, edgeList);
    }

    private void createGraphLD() {

    }

    private void printToFile(String filename, List<String> printElement) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != printWriter) {
            for(String e : printElement) {
                printWriter.println(e);
            }
            printWriter.close();
        }
    }
}

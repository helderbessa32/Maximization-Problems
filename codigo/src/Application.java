import java.util.*;
import Graph.*;
import Utils.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import Graph.*;
import Utils.*;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        Scanner reader = new Scanner(System.in);

        System.out.println("1. Grupo não se pode separar.");
        System.out.println("2. Grupo pode-se separar.");
        System.out.println("=============================\n");

        int userInput = reader.nextInt();

        switch (userInput) {
            case 1:
                firstScenery();
                break;
            case 2:
                secondScenery();
                break;
            default:
                break;
        }
    }

    public static void firstScenery() {

        long startTime = System.nanoTime();
        
        Graph graph = new Graph();
        ArrayList<Pair<ArrayList<Node>, Integer>> solutions = new ArrayList<>();
        ArrayList<Node> maxCapacityPath = new ArrayList<>();

        Utils.readFromFile(graph);

        int maxPeople = Algorithms.CaminhosCapacidadeMaxima(graph, maxCapacityPath);
        Pair<ArrayList<Node>, Integer> solution = Algorithms.BFS_N(graph);
        solutions.add(solution);
        while(true) {
            if (!(solution.getV1().size() < maxCapacityPath.size())) break;
            solution = Algorithms.BFS_N(graph);
            if(solution.getV1().size() <= 1) {
                break;
            }
            solutions.add(solution);
        }

        System.out.println(">>>Caminho que maximiza numero de amigos: " + maxCapacityPath + " => " + maxPeople);
        long endTime = System.nanoTime();
        String st = String.format("Time: %3.3f seconds\n", (double) (endTime - startTime) / 1000000000);
        System.out.print(st);
        System.out.println(">Caminhos mais curtos: ");
        for(Pair<ArrayList<Node>, Integer> _solution : solutions) {
            System.out.println("\t->" + _solution.getV1() + " => " + _solution.getV2());
        }
    }

    public static void secondScenery() {

        Scanner reader = new Scanner(System.in);

        System.out.println("1. Encaminhamento para um grupo dada a sua dimensão");
        System.out.println("2. Dimensão máxima do grupo possível");
        System.out.println("===================================================");

        int userInput = reader.nextInt();

        switch (userInput) {
            case 1:
                System.out.print("Quantos amigos tem o grupo? ");
                userInput = reader.nextInt();
                calculateGroupRoute(userInput);
                break;
            case 2:
                maxGroupDimention();
                break;
            default:
                break;
        }
    }

    public static void calculateGroupRoute(int groupDimention) {

        Graph graph = new Graph();
        Graph rGraph = new Graph();

        Node specialNode = new Node(0);
        graph.getNodes().add(specialNode);
        Utils.readFromFile(graph);

        graph.addEdge(specialNode, graph.getNodes().get(1), groupDimention, 0,false);
        graph.createResidualGraph(rGraph);
        Collections.sort(rGraph.getNodes(), Comparator.comparingInt((Node n) -> n.getValue()));

        System.out.println(Algorithms.Edmonds_Karp(rGraph));
    }

    public static void maxGroupDimention() {

        Graph graph = new Graph();
        Graph rGraph = new Graph();

        Utils.readFromFile(graph);
        graph.createResidualGraph(rGraph);

        int flow = Algorithms.Edmonds_Karp(rGraph);

        ArrayList<Node> path = Algorithms.getPathForSecondScenery(rGraph);

        System.out.println("Max number of people in the group: " + flow);

        while(path != null) {
            path = Algorithms.getPathForSecondScenery(rGraph);
            System.out.println(path);
        }
    }
}

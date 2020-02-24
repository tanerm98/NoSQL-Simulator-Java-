/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;

/**
 * Contains the whole database.
 * @author taner
 */
public class DataBase {
    String dbName;
    int noNodes, maxCap;
    
    ArrayList <Node> nodes;
    HashMap <String, Entity> entities;
    
    /**
     * Creates a new database with the given characteristics (name, capacity).
     * @param dbName
     * @param noNodes
     * @param maxCap
     */
    public DataBase(String dbName, int noNodes, int maxCap) {
        this.dbName = dbName;
        this.noNodes = noNodes;
        this.maxCap = maxCap;
        
        this.nodes = new ArrayList<Node>(noNodes);
        for (int i = 0; i < noNodes; i++) {
            Node nou = new Node(maxCap);
            nodes.add(i, nou);
        }
        
        this.entities = new HashMap<>();
    }
    
    
}

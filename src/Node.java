/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;

/**
 * Contains a node with all its instances.
 * @author taner
 */
public class Node {
    
    int maxCap;
    LinkedList<Instance> instances;

    /**
     * Creates a new node with the given maximum capacity.
     * @param maxCap
     */
    public Node(int maxCap) {
        this.maxCap = maxCap;
        this.instances = new LinkedList();
    }
    
   
}

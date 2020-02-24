/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.io.*;

/**
 * Reads all the input and executes all the commands.
 * @author taner
 */
public class Comenzi {
    
    Scanner in;
    FileWriter out;
    
    String comanda, comm, dump;
    Scanner com;
    
    int maxCap = 0;
    
    DataBase DB = new DataBase(null, 0, 0);

    /**
     * Opens the input file and the output file.
     * @param inputFile
     * @throws IOException
     */
    public Comenzi(String inputFile) throws IOException {
        File input = new File(inputFile);
        this.in = new Scanner(input);
        this.out = new FileWriter(inputFile + "_out");
        
    }
    
    /**
     * Reads all the commands and executes them or calls the specific method;
     * Also closes the output file.
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public void executa() throws IOException, CloneNotSupportedException {
        
        while (in.hasNextLine()) {
            
            comm = in.nextLine();
            com = new Scanner(comm);
            
            if (com.hasNext()) {
                comanda = com.next();
            } else {
                out.flush();
                out.close();
                return;
            }
            
            if (comanda.equals("CREATEDB")) {
                String dbName = com.next();
                int noNodes = com.nextInt();
                this.maxCap = com.nextInt();
                
                DB = new DataBase(dbName, noNodes, this.maxCap);
            }
            
            if (comanda.equals("CREATE")) {
                String entityName = com.next();
                int RF = com.nextInt();
                int noAtr = com.nextInt();
                
                Entity nou = new Entity(RF, noAtr);
                for (int i = 0; i < noAtr; i++) {
                    nou.atributes.put(com.next(), com.next());
                }
                
                DB.entities.put(entityName, nou);
            }
            
            if (comanda.equals("INSERT")) {
                insertInstance(com);
            }
            
            if (comanda.equals("DELETE")) {
                deleteInstance(com);
            }
            
            if (comanda.equals("UPDATE")) {
                updateInstance(com);
            }
            
            if (comanda.equals("GET")) {
                getInstances(com);
            }
            
            if (comanda.equals("SNAPSHOTDB")) {
                fatala();
            }
            
            if (comanda.equals("CLEANUP")) {
                String dbName = com.next();
                cleanup();
            }
        }
        
        out.flush();
        out.close();
    }
    
    /**
     * Inserts a new instance (given by the input);
     * Creates new nodes if there are not enough in order to insert all the
     * copies of the instance.
     * @param com
     * @throws CloneNotSupportedException
     */
    public void insertInstance (Scanner com) throws CloneNotSupportedException {

        String entityName = com.next();
        
        Entity ent = DB.entities.get(entityName);
        int nrAtr = ent.noAtr;
        int replic = ent.RF;
        
        Instance inst = new Instance(entityName);
        GenericAtribute atrib;
        
        for (int i = 0; i < nrAtr; i++) {
            String value = (new ArrayList<String>(ent.atributes.values())).get(i);
            
            if (value.equals("String")) {
                atrib = new StrinAt(com.next());
            } else if (value.equals("Integer")) {
                atrib = new IntAt(com.next());
            } else {
                atrib = new FloatAt(com.next());
            }
            
            inst.atributes.add(atrib);
        }
        
        int i = replic;

        for (int j = 0; j < DB.nodes.size(); j++) {
            if (i == 0) {
                return;
            }
            if (DB.nodes.get(j).instances.size() < DB.nodes.get(j).maxCap) {
                Instance instCopy = (Instance)inst.clone();
                DB.nodes.get(j).instances.add(0, instCopy);
                i--;
            }
        }
        
        for (int j = 0; j < i; j++) {
            scaleaza();
        }
        
        int l = i;
        for (int j = DB.nodes.size() - l; j < DB.nodes.size(); j++) {
            if (i == 0) {
                return;
            }
            if (DB.nodes.get(j).instances.size() < DB.nodes.get(j).maxCap) {
                Instance instCopy = (Instance)inst.clone();
                DB.nodes.get(j).instances.add(0, instCopy);
                i--;
            }
        }
            
    }
    
    /**
     * Deletes all the copies of an instance (given by the input).
     * @param com
     * @throws IOException
     */
    public void deleteInstance(Scanner com) throws IOException {
        
        String entityName = com.next();
        String key = com.next();
        
        Entity ent = DB.entities.get(entityName);
        if (ent == null) {
            out.write("NO INSTANCE TO DELETE\n");
            return;
        }
        
        int gasit = 0;
        for (int j = 0; j < DB.nodes.size(); j++) {
            for (int i = 0; i < DB.nodes.get(j).instances.size(); i++) {
                if (entityName.equals(DB.nodes.get(j).instances.get(i).entName)) {
                    if (key.equals(DB.nodes.get(j).instances.get(i).atributes.get(0).get())) {
                        DB.nodes.get(j).instances.remove(i);
                        gasit++;
                    }
                }
            }
        }
        if (gasit == 0) {
            out.write("NO INSTANCE TO DELETE\n");
            return;
        }
        
    }
    
    /**
     * Updates all the copies of a certain instance read from input with new
     * attributes; It also updates the timestamp for all of them.
     * @param com
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public void updateInstance(Scanner com) throws IOException, CloneNotSupportedException {
        
        String entityName = com.next();
        String key = com.next();
        
        Entity ent = DB.entities.get(entityName);
        if (ent == null) {
            return;
        }
        Instance inst = null;
        
        for (int j = 0; j < DB.nodes.size(); j++) {
            for (int i = 0; i < DB.nodes.get(j).instances.size(); i++) {
                if (entityName.equals(DB.nodes.get(j).instances.get(i).entName)) {
                    if (key.equals(DB.nodes.get(j).instances.get(i).atributes.get(0).get())) {
                        inst = (Instance)DB.nodes.get(j).instances.get(i).clone();
                    }
                }
            }
        }
        if (inst == null) {
            return;
        }
        
        String atrName = com.next();
        String atr = com.next();
        
        for (int i = 0; i < ent.atributes.size(); i++) {
            
            String name = (new ArrayList<String>(ent.atributes.keySet())).get(i);
            String type = (new ArrayList<String>(ent.atributes.values())).get(i);
            
            if (atrName.equals(name)) {
                
                GenericAtribute atrib = null;
                
                if (type.equals("String")) {
                    atrib = new StrinAt(atr);
                } else if (type.equals("Integer")) {
                    atrib = new IntAt(atr);
                } else {
                    atrib = new FloatAt(atr);
                }
                
                inst.atributes.set(i, atrib);
                
                if (com.hasNext()) {
                    atrName = com.next();
                    atr = com.next();
                }
            }
        }
        
        for (int j = 0; j < DB.nodes.size(); j++) {
            for (int i = 0; i < DB.nodes.get(j).instances.size(); i++) {
                if (entityName.equals(DB.nodes.get(j).instances.get(i).entName)) {
                    if (key.equals(DB.nodes.get(j).instances.get(i).atributes.get(0).get())) {
                        Instance instCopy = (Instance)inst.clone();
                        instCopy.timeStamp = (new Date()).getTime();
                        DB.nodes.get(j).instances.remove(i);
                        DB.nodes.get(j).instances.add(0, instCopy);
                    }
                }
            }
        }
    }
    
    /**
     * Returns the requested instance (read from input) along with all its
     * attributes and the nodes that contain it.
     * @param com
     * @throws IOException
     * @throws CloneNotSupportedException
     */
    public void getInstances(Scanner com) throws IOException, CloneNotSupportedException {
        
        String entityName = com.next();
        String key = com.next();
        
        Entity ent = DB.entities.get(entityName);
        if (ent == null) {
            out.write("NO INSTANCE FOUND\n");
            return;
        }
        
        Instance inst = null;
        
        int gasit = 0;
        for (int j = 0; j < DB.nodes.size(); j++) {
            for (int i = 0; i < DB.nodes.get(j).instances.size(); i++) {
                if (entityName.equals(DB.nodes.get(j).instances.get(i).entName)) {
                    if (key.equals(DB.nodes.get(j).instances.get(i).atributes.get(0).get())) {
                        out.write("Nod" + (j + 1) + " ");
                        inst = (Instance)DB.nodes.get(j).instances.get(i).clone();
                        gasit = 1;
                    }
                }
            }
        }
        if (gasit == 0) {
            out.write("NO INSTANCE FOUND\n");
            return;
        } else {
            out.write(entityName + " ");
            
            for (int i = 0; i < ent.atributes.size(); i++) {
            
                String name = (new ArrayList<String>(ent.atributes.keySet())).get(i);
                
                out.write(name + ":");
                out.write(inst.atributes.get(i).get());
                
                if (i < ent.atributes.size() - 1) {
                    out.write(" ");
                }
            }
            
            out.write("\n");
        }
        
    }
    
    /**
     * Prints all the nodes of the database that are not empty, along with the
     * instances (and the attributes) they contain.
     * @throws IOException
     */
    public void fatala() throws IOException {
        boolean empty = true;
        
        for (int j = 0; j < DB.nodes.size(); j++) {
            if (!DB.nodes.get(j).instances.isEmpty()) {
                empty = false;
                out.write("Nod" + (j + 1) + "\n");
            } else {
                continue;
            }
            for (int i = 0; i < DB.nodes.get(j).instances.size(); i++) {
                String entityName = DB.nodes.get(j).instances.get(i).entName;
                out.write(entityName + " ");
                Entity ent = DB.entities.get(entityName);
                
                for (int k = 0; k < DB.nodes.get(j).instances.get(i).atributes.size(); k++) {
                    String name = (new ArrayList<String>(ent.atributes.keySet())).get(k);
                    
                    out.write(name + ":");
                    out.write(DB.nodes.get(j).instances.get(i).atributes.get(k).get());
                    
                    if (k < DB.nodes.get(j).instances.get(i).atributes.size() - 1) {
                        out.write(" ");
                    }
                }
                
                out.write("\n");
            }
            
        }
        if (empty) {
            out.write("EMPTY DB\n");
        }
    }
    
    /**
     * Deletes all the instances from the database that are older than a certain
     * timestamp read from input.
     */
    public void cleanup() {
        
        long timestamp = com.nextLong();
        
        for (int j = 0; j < DB.nodes.size(); j++) {
            
            if (DB.nodes.get(j).instances.isEmpty()) {
                continue;
            }
            
           for (int i = 0; i < DB.nodes.get(j).instances.size(); i++) {
                if (DB.nodes.get(j).instances.get(i).timeStamp < timestamp) {
                    DB.nodes.get(j).instances.remove(i);
                    i--;
                }
            }
            
        }
    }
    
    /**
     * Creates a new node in the database with the same capacity as the others.
     */
    public void scaleaza () {
        
        Node nou = new Node(this.maxCap);
        DB.nodes.add(nou);
        DB.noNodes++;
        
    }
}

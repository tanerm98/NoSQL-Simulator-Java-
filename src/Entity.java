/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;

/**
 * Contains an entity with all its attributes.
 * @author taner
 */
public class Entity {
    
    int RF;
    int noAtr;
    LinkedHashMap <String, String> atributes;

    /**
     * Creates a new entity with the given replication factor and number of
     * attributes.
     * @param RF
     * @param noAtr
     */
    public Entity(int RF, int noAtr) {
        
        this.RF = RF;
        this.noAtr = noAtr;
        
        atributes = new LinkedHashMap<>(noAtr);
    }
    
}

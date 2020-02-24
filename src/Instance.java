/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.util.Date;

/**
 * Contains an instance with all its attributes and characteristics.
 * @author taner
 */
public class Instance implements Cloneable{
    
    String entName;
    LinkedList<GenericAtribute> atributes;
    long timeStamp;

    /**
     * Creates a new instance with the given name and the current timestamp.
     * @param name
     */
    public Instance(String name) {
        this.timeStamp = (new Date()).getTime();
        this.entName = name;
        atributes = new LinkedList<>();
    }
    
    @Override
    public Object clone()throws CloneNotSupportedException{  
        return super.clone();  
    }
    
}

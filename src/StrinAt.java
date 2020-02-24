/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Contains a String attribute.
 * @author taner
 */
public class StrinAt implements GenericAtribute {
    String sir;

    /**
     * Creates a new String attribute.
     * @param sir
     */
    public StrinAt(String sir) {
        this.sir = sir;
    }
    
    @Override
    public String get() {
        return sir;
    }
    
}

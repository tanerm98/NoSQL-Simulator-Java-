/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Contains an integer attribute.
 * @author taner
 */
public class IntAt implements GenericAtribute {
    int x;

    /**
     * Creates a new Integer attribute.
     * @param sir
     */
    public IntAt(String sir) {
        this.x = Integer.parseInt(sir);
    }
    
    @Override
    public String get() {
        return Integer.toString(x);
    }
    
}

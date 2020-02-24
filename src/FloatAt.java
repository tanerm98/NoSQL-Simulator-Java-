/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.text.DecimalFormat;

/**
 * Contains a Float attribute.
 * @author taner
 */
public class FloatAt implements GenericAtribute {
    float x;
    DecimalFormat mod = new DecimalFormat("#.##");

    /**
     * Creates a new Float attribute.
     * @param sir
     */
    public FloatAt(String sir) {
        this.x = Float.parseFloat(sir);
        //mod.setMinimumFractionDigits(2);
    }
    
    @Override
    public String get() {
        return mod.format(x).toString();
    }
    
}
